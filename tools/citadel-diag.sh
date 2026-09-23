#!/usr/bin/env bash
# Summarise a citadel diagnostic (Iteration 6): tools/citadel-diag.sh <map>  (reads diag/citadel-<map>.{run,log,bc20})
M="${1:?map}"; R="diag/citadel-$M.run"; L="diag/citadel-$M.log"; B="diag/citadel-$M.bc20"
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"; cd "$REPO"
grep RESULT "$R" | tail -1
tools/replay-dump.sh "$B" --ring 100 --ringd 2 2>/dev/null | grep -E 'RING r(400|600|700|1000|2000|2500|3000) ' | cut -c1-118
grep -oE '@(badseat|badpost|helper post|attacker|seated|posted|inner[^ ]*|ferried|lift|ferry to|build t=[0-9]+)' "$L" | sort | uniq -c | tr '\n' ';'; echo
tools/replay-dump.sh "$B" --metrics --every 100 2>/dev/null | grep -E '^r(700|1000|2000|3000) ' | cut -c1-200
grep -E '^\[A:.*@(600|1000|2000)\] @(econ|dronestat)' "$L" | sed 's/^\[A://' | cut -c1-140 | sort | head -14
echo "-- roles at r2000 (count role seat digs idle):"
grep -E '^\[A:.*@2000\] @wallstat' "$L" | sed 's/^\[A:LANDSCAPER#//' | awk '{print $2,$5,$13}' | sort | uniq -c | sort -rn | head -12
echo "-- overruns:"; tools/replay-dump.sh "$B" --from 1 --to 3200 2>/dev/null | grep 'BYTECODE-OVERRUN bot:' | awk '{print $3}' | sed 's/#.*//' | sort | uniq -c
