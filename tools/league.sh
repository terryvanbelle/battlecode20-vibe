#!/usr/bin/env bash
# The league backtest (PROMPTS 58): does self-play against a fixed population of our own builds and archetypes rank
# builds the way the ladder does? Gate g13 said no for the plain mirror (g_iter13 REJECTed 24-39 against g_iter12, +30
# on the ladder). Every build plays the SAME cells (opponent map side seed), so the builds' records are paired by cell;
# tools/league.py then compares each opponent's and the whole league's ranking of the builds with their ladder ratings.
#   BUILDS="g_iter13 g_iter12" OPPS="arch_rush g_iter5" K=8 SEED=1 tools/league.sh
# Our own builds only (the gauntlet refuses external bots without SCRIM=1). Output: gauntlet/<stamp>-league<SEED>-<build>/
set -euo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILDS="${BUILDS:?BUILDS: our builds to rank}"; OPPS="${OPPS:?OPPS: the league}"; K="${K:-8}"; SEED="${SEED:-1}"
MAXJOBS="${MAXJOBS:-6}"; CLASSES="${CLASSES:-$REPO/build/league-classes}"
CELLS="$REPO/gauntlet/league$SEED-cells.txt"; mkdir -p "$REPO/gauntlet"
python3 - "$K" "$SEED" "$OPPS" "$(tr '\n' ' ' < "$REPO/tools/bc20-maps.txt")" > "$CELLS" <<'PY'
import random, sys
k = int(sys.argv[1]); random.seed(int(sys.argv[2])); opps = sys.argv[3].split(); maps = sys.argv[4].split()
for o in opps:
    for _ in range(k): print(o, random.choice(maps), random.choice("AB"), random.randrange(1, 2**31))
PY
first=1
for B in $BUILDS; do
  CELLS="$CELLS" BOT="$B" TAG="league$SEED-$B" CLASSES="$CLASSES" MAXJOBS="$MAXJOBS" SKIP_COMPILE=$([ $first = 1 ] && echo 0 || echo 1) \
    "$REPO/tools/gauntlet.sh" | tail -1
  first=0
done
python3 "$REPO/tools/league.py" "$SEED"
