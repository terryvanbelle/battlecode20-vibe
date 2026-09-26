#!/usr/bin/env bash
# Mine a scrimmage block for hypotheses: the games are already paid for; this is where the next
# candidate comes from.
#   tools/scrim-study.sh gauntlet/<run>-scrim-bot [sample]
# For every replay of the block (wins and losses; every opponent since PROMPTS 45):
# per-round team aggregates every 50 rounds to r1200 -> <run>/study.tsv, and the navigation
# summary -> <run>/nav.tsv. Then tools/scrim-study.py prints medians, us against them.
set -euo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN="${1:?usage: scrim-study.sh <run-dir> [sample]}"; SAMPLE="${2:-0}"
COLS="soup hq hqBuried hqElev worth miners landscapers drones refineries vaporators schools centers netguns spawned spawnCost died drowned shot buriedDeaths mines soupDeps refines digs dirtDeps pickups drops shots moves cov aba bcOver"
OUT="$RUN/study.tsv"; NAV="$RUN/nav.tsv"
{ printf 'game\topp\tmap\twon\tround'; for s in us th; do for c in $COLS; do printf '\t%s_%s' "$s" "$c"; done; done; printf '\twater\tflooded\tpollution\n'; } > "$OUT"
printf 'opp\tmap\twon\tside\tus_cov\tth_cov\tus_moves\tth_moves\tus_meanMoves\tth_meanMoves\tus_aba\tth_aba\tus_firstHQ\tth_firstHQ\n' > "$NAV"
n=0
for f in "$RUN"/losses/*.bc20 "$RUN"/replays/*.bc20; do
  [ -e "$f" ] || continue
  case "$f" in *"/losses/"*) won=0;; *) won=1;; esac
  n=$((n+1)); [ "$SAMPLE" -gt 0 ] && [ "$n" -gt "$SAMPLE" ] && break
  b=$(basename "$f" .bc20); opp=${b%%__*}; rest=${b#*__}; map=${rest%%__*}; side=${b##*bot}
  U=$side; T=$([ "$side" = A ] && echo B || echo A)
  if ! nice -n 10 "$REPO/tools/replay-dump.sh" "$f" --metrics --every 1000 >/dev/null 2>&1; then
    rc=$?
    if [ "$rc" = 3 ]; then echo "  -- skipped $b: BENCHMARK.md rule 2, this opponent's games may not be reviewed" >&2
    else echo "  !! skipped $b (replay-dump failed, exit $rc)" >&2; fi
    continue
  fi
  nice -n 10 "$REPO/tools/replay-dump.sh" "$f" --navstats 2>/dev/null | grep "^  nav " | \
    sed -E 's/^  nav ([^:]+): moves=([0-9]+) aba=[0-9]+ \(([0-9.]+)%\) coverage=([0-9.]+)%.*firstEnemyHQContact=r(-?[0-9]+).*meanMoves=([0-9.]+).*/\1 \2 \3 \4 \5 \6/' | \
    awk -v opp="$opp" -v map="$map" -v side="$side" -v won="$won" '
      { if (NR==1) { a=$0 } else { b=$0 } }
      END { split(a,x," ");
            if (x[1] ~ /^(bot|g_iter)/) { split(a,u," "); split(b,t," ") } else { split(b,u," "); split(a,t," ") }
            printf "%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n", opp, map, won, side, u[4], t[4], u[2], t[2], u[6], t[6], u[3], t[3], u[5], t[5] }' >> "$NAV"
  nice -n 10 "$REPO/tools/replay-dump.sh" "$f" --metrics 2>/dev/null | awk -F, -v U="$U" -v T="$T" -v game="$b" -v opp="$opp" -v map="$map" -v won="$won" -v cols="$COLS" '
    BEGIN{nc=split(cols,C," ")}
    NR==1{for(i=1;i<=NF;i++)h[$i]=i; next}
    $1%50==0 && $1>=50 && $1<=1200 {printf "%s\t%s\t%s\t%s\t%s", game, opp, map, won, $1;
      for(k=1;k<=nc;k++) printf "\t%s", $h[U"_"C[k]]; for(k=1;k<=nc;k++) printf "\t%s", $h[T"_"C[k]];
      printf "\t%s\t%s\t%s\n", $h["water"], $h["flooded"], $h["pollution"] }' >> "$OUT"
  echo "  studied $b" >&2
done
echo "wrote $OUT ($(( $(wc -l < "$OUT") - 1 )) rows from $n replays)"
"$REPO/tools/scrim-study.py" "$OUT"
"$REPO/tools/scrim-study.py" "$NAV" --nav
