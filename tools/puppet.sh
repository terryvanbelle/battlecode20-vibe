#!/usr/bin/env bash
# Replay puppet (PROMPTS 59-60; DESIGN.md "Puppet"): one side of a recorded game replayed from its replay up to a
# cutoff round, then played by one of our archetypes (src/pup_<base>). The logic is tools/puppet.py; this wraps the
# flatbuffer reader (tools/puppet/RawEvents.java, compiled on demand into build/puppet-<sha12>/) and the games.
#
#   tools/puppet.sh extract <replay.bc20> [--side A|B] [--name NAME]      -> build/puppets/NAME.properties
#   tools/puppet.sh play <fixture> <base> <cutoff> <opponent> [out.bc20]   one game on the recorded map, side and seed
#   tools/puppet.sh check <fixture> [base=g_iter13] [--cutoff 99999] [--as PKG] [--out out.bc20]
#                                                                           the recorded O package (or PKG, identical to it
#                                                                           but for its package name) against the puppet, then diff
#   tools/puppet.sh diff <recorded.bc20> <new.bc20> --side P [--log game.log] [--cutoff c] [--rounds]
#   tools/puppet.sh cells <fixture> <cutoff>...                             lines for OPP=pup_<base> tools/paired.sh
#   tools/puppet.sh raw <replay.bc20>                                       the raw event lines
#
# play/check run ONE game on this machine through run-dev.sh; they wait (at most 20 minutes) while another engine runs.
set -euo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"; source "$REPO/tools/lib.sh"
CMD="${1:-}"; [ $# -gt 0 ] && shift
PY="$REPO/tools/puppet.py"

rawevents () {   # rawevents <replay> : raw lines on stdout
  local CP SHA OUT; CP="$(engine_cp)"
  SHA="$(sha1sum "$REPO/tools/puppet/RawEvents.java" | cut -c1-12)"; OUT="$REPO/build/puppet-$SHA"
  if [ ! -f "$OUT/puppet/RawEvents.class" ]; then mkdir -p "$OUT"; javac -nowarn -d "$OUT" -cp "$CP" "$REPO/tools/puppet/RawEvents.java"; fi
  java -Xmx"${DUMP_XMX:-512m}" -cp "$OUT:$CP" puppet.RawEvents "$1"
}

refuse_replay () {
  case "$1" in *.bc20) ;; *) echo "!! not a .bc20: $1" >&2; exit 3;; esac
  case "$(realpath "$1")/" in */bc20-benchmarks/*) echo "!! refused: $1 is under bc20-benchmarks (replays only; CLAUDE.md rule 3)" >&2; exit 3;; esac
  [ -f "$1" ] || { echo "!! no such replay: $1" >&2; exit 3; }
}

meta () {   # meta <fixture> <key> : a provenance field of the fixture's comment lines
  sed -n 's/^#.*[[:space:]]'"$2"'=\([^[:space:]]*\).*/\1/p' "$1" | head -1
}

wait_engine () {   # a bounded wait for another game on this machine to finish
  local i=0
  until ! engine_busy; do
    [ $i -ge 240 ] && { echo "!! another engine has run for 20 minutes; refusing to start a second game here" >&2; exit 4; }
    [ $i -eq 0 ] && echo "waiting for the running game to finish ..." >&2
    i=$((i + 1)); sleep 5
  done
}

play_game () {   # play_game <fixture> <puppet team> <other team> <cutoff> <out.bc20> <log>
  local FIX="$1" PUP="$2" OTHER="$3" CUT="$4" OUTR="$5" LOG="$6" SIDE MAP SEED A B
  SIDE="$(meta "$FIX" side)"; MAP="$(meta "$FIX" map)"; SEED="$(meta "$FIX" seed)"
  [ -n "$SIDE" ] && [ -n "$MAP" ] && [ -n "$SEED" ] || { echo "!! $FIX lacks side/map/seed provenance" >&2; exit 3; }
  if [ "$SIDE" = A ]; then A="$PUP"; B="$OTHER"; else A="$OTHER"; B="$PUP"; fi
  wait_engine
  echo "game: $A (A) vs $B (B) on $MAP seed $SEED, cutoff $CUT -> $OUTR" >&2
  GAME_TIMEOUT="${GAME_TIMEOUT:-5400}" GAME_CONFIG="$(realpath "$FIX")" GAME_SEED="$SEED" LOG_OUT="$LOG" GAME_OPTS="-Dbc.testing.pup.cutoff=$CUT" \
    "$REPO/tools/run-dev.sh" "$A" "$B" "$MAP" "$OUTR" -Dbc.server.robot-player-to-system-out=true
}

pup_counts () {   # @pup line counts of the puppet side in a game log
  local SIDE="$1" LOG="$2"
  printf 'puppet log:'; for t in nofixture wrongfixture miss drop txlate txdrop exc hocargo ho; do printf ' %s %s' "$t" "$(grep -ac "^\[$SIDE:[^]]*\] @pup $t" "$LOG" || true)"; done; echo
  grep -a "^\[$SIDE:[^]]*\] @pup" "$LOG" | head -${PUP_LINES:-20} || true
  local EXC PEXC; EXC=$(grep -ac "Exception\|exception" "$LOG" || true); PEXC=$(grep -ac "^\[$SIDE:[^]]*\] .*Exception" "$LOG" || true)
  echo "log lines mentioning an exception: $EXC (puppet side $PEXC)"
}

case "$CMD" in
  raw)
    refuse_replay "$1"; rawevents "$1" ;;
  extract)
    R="$1"; shift; refuse_replay "$R"
    TMP="$(mktemp)"; trap 'rm -f "$TMP"' EXIT
    rawevents "$R" > "$TMP"
    python3 "$PY" extract --raw "$TMP" --replay "$R" "$@" ;;
  diff)
    REC="$1" NEW="$2"; shift 2; refuse_replay "$REC"; refuse_replay "$NEW"
    T1="$(mktemp)"; T2="$(mktemp)"; trap 'rm -f "$T1" "$T2"' EXIT
    rawevents "$REC" > "$T1"; rawevents "$NEW" > "$T2"
    python3 "$PY" diff --rec "$T1" --new "$T2" "$@" ;;
  cells)
    python3 "$PY" cells "$@" ;;
  play)
    FIX="$1" BASE="$2" CUT="$3" OPPN="$4"; OUTR="${5:-$REPO/matches/pup_$BASE-$(basename "$FIX" .properties)-c$CUT.bc20}"
    [ -f "$FIX" ] && [ -d "$REPO/src/pup_$BASE" ] || { echo "!! need a fixture and src/pup_$BASE" >&2; exit 3; }
    [ -d "$REPO/src/$OPPN" ] || { echo "!! $OPPN is not one of our packages (a puppet plays only our own bots)" >&2; exit 3; }
    LOG="${OUTR%.bc20}.log"
    play_game "$FIX" "pup_$BASE" "$OPPN" "$CUT" "$OUTR" "$LOG"
    pup_counts "$(meta "$FIX" side)" "$LOG"
    echo "replay $OUTR  log $LOG" ;;
  check)
    FIX="$1"; shift; BASE=g_iter13; CUT=99999; AS=""; OUTR=""
    while [ $# -gt 0 ]; do case "$1" in
      --cutoff) CUT="$2"; shift 2;; --as) AS="$2"; shift 2;; --out) OUTR="$2"; shift 2;;
      base=*) BASE="${1#base=}"; shift;; *) BASE="$1"; shift;; esac; done
    [ -f "$FIX" ] && [ -d "$REPO/src/pup_$BASE" ] || { echo "!! need a fixture and src/pup_$BASE" >&2; exit 3; }
    REC="$REPO/$(meta "$FIX" replay)"; [ -f "$REC" ] || REC="$(meta "$FIX" replay)"
    O="$(meta "$FIX" O)"; SIDE="$(meta "$FIX" side)"; RUN="$(meta "$FIX" run)"; ENG="$(meta "$FIX" engine)"
    [ -f "$REC" ] || { echo "!! the recorded replay $(meta "$FIX" replay) is gone" >&2; exit 3; }
    # eligibility: O is ours and unchanged since the recording, the engine is the recording's
    [ -d "$REPO/src/$O" ] || { echo "!! ineligible: the recorded O package $O is not in src/" >&2; exit 3; }
    [ "$O" != bot ] || { echo "!! ineligible: O is src/bot, which changes; snapshot it" >&2; exit 3; }
    STAMP="$(printf '%s' "$RUN" | sed -n 's/^\([0-9]\{8\}\)-\([0-9]\{2\}\)\([0-9]\{2\}\)\([0-9]\{2\}\).*/\1 \2:\3:\4/p')"
    [ -n "$STAMP" ] || { echo "!! ineligible: run '$RUN' has no stamp" >&2; exit 3; }
    RUNT="$(date -u -d "$STAMP" +%s)"; CT="$(git -C "$REPO" log -1 --format=%ct -- "src/$O")"
    [ -z "$(git -C "$REPO" status --porcelain -- "src/$O")" ] || { echo "!! ineligible: src/$O has uncommitted changes" >&2; exit 3; }
    [ -n "$CT" ] && [ "$CT" -le "$RUNT" ] || { echo "!! ineligible: src/$O changed after the run ($RUN)" >&2; exit 3; }
    [ "$(head -1 "$ENGINE_DIR/VERSION" | tr ' ' _)" = "$ENG" ] || { echo "!! ineligible: engine/VERSION is not the fixture's ($ENG)" >&2; exit 3; }
    PLAY="$O"
    if [ -n "$AS" ]; then   # the same code under another package name (e.g. g_iter13 = r1s15)
      diff -q <(cd "$REPO/src/$O" && for f in *.java; do echo "== $f"; sed "s/\b$O\b/PKG/g" "$f"; done) \
              <(cd "$REPO/src/$AS" && for f in *.java; do echo "== $f"; sed "s/\b$AS\b/PKG/g" "$f"; done) >/dev/null \
        || { echo "!! ineligible: src/$AS is not src/$O under another name" >&2; exit 3; }
      PLAY="$AS"
    fi
    OUTR="${OUTR:-$REPO/matches/check-pup_$BASE-$(basename "$FIX" .properties)-c$CUT.bc20}"; LOG="${OUTR%.bc20}.log"
    play_game "$FIX" "pup_$BASE" "$PLAY" "$CUT" "$OUTR" "$LOG"
    pup_counts "$SIDE" "$LOG"
    T1="$(mktemp)"; T2="$(mktemp)"; trap 'rm -f "$T1" "$T2"' EXIT
    rawevents "$REC" > "$T1"; rawevents "$OUTR" > "$T2"
    set +e; python3 "$PY" diff --rec "$T1" --new "$T2" --side "$SIDE" --log "$LOG" --cutoff "$CUT" --fixture "$FIX"; RC=$?; set -e
    echo "replay $OUTR  log $LOG"; exit $RC ;;
  *)
    sed -n '2,15p' "$0"; exit 2 ;;
esac
