#!/usr/bin/env bash
# The spending-window census (VM): for every replay of the given scrimmage runs, our side's soup, miners, landscapers,
# schools, centers and drones at r300-900, the game's end round and result -> CSV on stdout. Our side is the
# replay name's bot letter (opponent__map__botA.bc20 = we are A); losses/ and replays/ are both read.
#   tools/window-census.sh gauntlet/<run> [gauntlet/<run> ...] > census.csv
set -uo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
one () {
  local f=$1 b s res; b=$(basename "$f" .bc20); s=${b##*bot}; case "$f" in */losses/*) res=loss;; *) res=win;; esac
  tools/replay-dump.sh "$f" --metrics --every 100 2>/dev/null | python3 -c "
import sys,csv
s=sys.argv[1]; rows=list(csv.reader(l for l in sys.stdin if not l.startswith('#'))); h=rows[0]; ix={c:i for i,c in enumerate(h)}
end=rows[-1][0]; out=[sys.argv[2], sys.argv[3], s, end]
for R in (300,400,500,600,700,800,900):
    r=[x for x in rows[1:] if int(x[0])==R]
    out += [r[0][ix[s+'_'+c]] for c in ('soup','miners','landscapers','schools','centers','drones')] if r else ['']*6
print(','.join(out))" "$s" "$b" "$res"
}
export -f one
echo "game,result,side,end,$(for R in 300 400 500 600 700 800 900; do printf 'soup%s,M%s,L%s,DS%s,FC%s,Dr%s,' $R $R $R $R $R $R; done | sed 's/,$//')"
for d in "$@"; do ls "$d"/losses/*.bc20 "$d"/replays/*.bc20 2>/dev/null; done | sort -u | xargs -P 8 -I{} bash -c 'one {}'
