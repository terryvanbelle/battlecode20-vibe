#!/usr/bin/env bash
# Puppet pairs in parallel (PROMPTS 66-67, 71-72): every cell x every build at once -- meant for the VM
# (tools/vm-run.sh <tag> 'tools/pair-batch.sh ...'), where the driver would play them one at a time.
#   tools/pair-batch.sh <cells.txt> <out-tag> <pcut> <build>...
# cells.txt lines: "<fixture.properties> <P-side> <map> <seed>" (what `FIXTURE_ONLY=1 tools/puppet.sh pair` prints).
# Our side is live from r1 (a later handover loses birth-order roles such as the builder miner); the recorded side P is
# scripted to <pcut>, then plays g_iter13. Each build needs its shim src/pup_<build> (tools/puppet.sh pair makes them).
# Output: gauntlet/<out-tag>/<map>-<build>.bc20 / .log; prints each result line and PAIRDONE.
set -uo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"; cd "$REPO"
CELLS="$1" TAG="$2" PCUT="$3"; shift 3
OUT="gauntlet/$TAG"; rm -rf "$OUT"; mkdir -p "$OUT"
while read -r FX PS MAP SEED; do
  [ -n "${FX:-}" ] || continue
  FX="$REPO/build/puppets/$(basename "$FX")"
  for B in "$@"; do
    [ -d "src/pup_$B" ] || { echo "!! no src/pup_$B" >&2; continue; }
    if [ "$PS" = A ]; then A=pup_g_iter13; BB=pup_$B; else A=pup_$B; BB=pup_g_iter13; fi
    ( GAME_CONFIG="$FX" GAME_OPTS="-Dbc.testing.pup.cutoff=1 -Dbc.testing.pup.$PS.cutoff=$PCUT" GAME_SEED="$SEED" \
        LOG_OUT="$OUT/$MAP-$B.log" DEV_OUT="build/pb-$TAG-$MAP-$B" timeout 3000 \
        tools/run-dev.sh "$A" "$BB" "$MAP" "$OUT/$MAP-$B.bc20" -Dbc.server.robot-player-to-system-out=true >/dev/null 2>&1
      echo "$MAP $B $(grep -a 'wins (round' "$OUT/$MAP-$B.log" | sed 's/.*\] *//')" ) &
  done
done < "$CELLS"
wait; echo PAIRDONE
