#!/usr/bin/env bash
# The six-map lattice diagnostic (VM): BOT against REF (default g_iter12) on CentralLake A, RandomSoup1 A, MtDoom A,
# Squares B, TwoLakeLand B, IceCream A, seed 7, the six games in parallel; prints each result and BOT's economy at
# r500/1000/1500/2000 (soup, HQ, miners, landscapers, drones, refineries, vaporators, schools, centers), then DIAGDONE.
#   tools/vm-run.sh diag78f 'tools/lattice-diag.sh cand78 l78f'
set -uo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
BOT="${1:?bot}"; TAG="${2:?tag}"; REF="${REF:-g_iter12}"; mkdir -p diag
one () {
  local MAP=$1 SIDE=$2 P
  if [ "$SIDE" = A ]; then P="$BOT $REF"; else P="$REF $BOT"; fi
  { GAME_SEED=7 LOG_OUT=diag/$TAG-$MAP.log tools/run-dev.sh $P $MAP diag/$TAG-$MAP.bc20 -Dbc.server.robot-player-to-system-out=true 2>&1 | tail -1 | sed "s/^/$MAP $BOT as $SIDE: /" | cut -c1-70
    tools/replay-dump.sh diag/$TAG-$MAP.bc20 --every 500 2>/dev/null | grep -a "^r\(500\|1000\|1500\|2000\) " | grep -o "^r[0-9]*\|$SIDE: soup=[0-9]* HQ=[^ ]* [^ ]* M=[0-9]* L=[0-9]* Dr=[0-9]* R=[0-9]* V=[0-9]* DS=[0-9]* FC=[0-9]*" | paste - - | sed "s/^/  /"
  } > diag/$TAG-$MAP.out
}
for spec in "CentralLake A" "RandomSoup1 A" "MtDoom A" "Squares B" "TwoLakeLand B" "IceCream A"; do one $spec & done; wait
for m in CentralLake RandomSoup1 MtDoom Squares TwoLakeLand IceCream; do cat diag/$TAG-$m.out; done
echo DIAGDONE
