#!/usr/bin/env bash
# The late wall race, per game (VM): for every replay of the runs that ended at r2000 or later, our and their ring minimum
# and landscapers at r1500 / 2000 / 2500 and the end round -> CSV on stdout (our side from the replay name's bot letter).
#   tools/late-race.sh gauntlet/<run> [...] > late.csv
set -uo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."
one () {
  local f=$1 b s o res; b=$(basename "$f" .bc20); s=${b##*bot}; o=$([ "$s" = A ] && echo B || echo A); case "$f" in */losses/*) res=loss;; *) res=win;; esac
  { tools/replay-dump.sh "$f" --ring 250 2>/dev/null; echo "@@METRICS"; tools/replay-dump.sh "$f" --metrics --every 250 2>/dev/null; } | python3 -c "
import sys,re,csv
s,o,b,res=sys.argv[1:5]; txt=sys.stdin.read(); ring_part,met=txt.split('@@METRICS',1)
ring={}
for l in ring_part.splitlines():
    m=re.match(r'RING r(\d+)\s.*A: .*?min=(-?\d+).*B: .*?min=(-?\d+)',l)
    if m: ring[int(m[1])]={'A':int(m[2]),'B':int(m[3])}
rows=list(csv.reader(l for l in met.splitlines() if l and not l.startswith('#'))); h=rows[0]; ix={c:i for i,c in enumerate(h)}
end=int(rows[-1][0])
if end<2000: sys.exit()
out=[b,res,s,str(end)]
for R in (1500,2000,2500):
    r=[x for x in rows[1:] if int(x[0])==R]; g=ring.get(R,{})
    out+=[str(g.get(s,'')),str(g.get(o,'')), r[0][ix[s+'_landscapers']] if r else '', r[0][ix[o+'_landscapers']] if r else '']
print(','.join(out))" "$s" "$o" "$b" "$res"
}
export -f one
echo "game,result,side,end,ourRing1500,theirRing1500,ourL1500,theirL1500,ourRing2000,theirRing2000,ourL2000,theirL2000,ourRing2500,theirRing2500,ourL2500,theirL2500"
for d in "$@"; do ls "$d"/losses/*.bc20 "$d"/replays/*.bc20 2>/dev/null; done | sort -u | xargs -P 8 -I{} bash -c 'one {}'
