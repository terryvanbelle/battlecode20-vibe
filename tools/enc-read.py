#!/usr/bin/env python3
"""The enclosure diagnostic reader: tools/enc-read.py <replay.bc20> <A|B> [log]
Prints, for that side: bodies and buildings by round, the shell (Chebyshev 2) and the interior (Chebyshev 1)
minimum and count of dry tiles at r500-3000, and the log tags of the enclosure roles when a log is given."""
import re, subprocess, sys, os
rep, side = sys.argv[1], sys.argv[2]; log = sys.argv[3] if len(sys.argv) > 3 else None
here = os.path.dirname(os.path.abspath(__file__))
def dump(*flags):
    return subprocess.run([os.path.join(here, 'replay-dump.sh'), rep, *flags], capture_output=True, text=True, errors='replace').stdout
rows = []
for l in dump('--every', '100', '--to', '3200').splitlines():
    m = re.match(r'r(\d+) ', l)
    if not m or int(m[1]) not in (300, 500, 700, 1000, 1500, 2000, 2500, 3000): continue
    p = l.split('| %s: ' % side)
    if len(p) < 2: continue
    q = re.search(r'soup=(\d+) HQ=(\S+)\(buried (\d+)\).*?M=(\d+) L=(\d+) Dr=(\d+) R=(\d+) V=(\d+) DS=(\d+) FC=(\d+) NG=(\d+)', p[1])
    if q: rows.append('r%s:%s%s L%s M%s Dr%s V%s DS%s FC%s NG%s soup%s' % (m[1], q[2][:2], q[3], q[5], q[4], q[6], q[8], q[9], q[10], q[11], q[1]))
print('  ' + ' | '.join(rows))
for d in (2, 1):
    for l in dump('--ring', '100', '--ringd', str(d)).splitlines():
        m = re.match(r'RING r(\d+) ', l)
        if not m or int(m[1]) not in (300, 500, 1000, 2000, 3000): continue
        part = l.split('%s:' % side)
        if len(part) < 2: continue
        seg = part[1].split('|')[0].strip()
        q = re.search(r'min=(-?\d+) \((\d+) tiles\)', part[1])
        tiles = seg.split(); dry = sum(1 for t in tiles if not t.endswith('F'))
        print('  d%d r%s min=%s dry=%d/%s [%s]' % (d, m[1], q[1] if q else '?', dry, q[2] if q else '?', seg[:80]))
if log:
    txt = open(log, errors='replace').read()
    def cnt(tag): return len(re.findall(r'^\[%s:.*%s' % (side, tag), txt, re.M))
    print('  held %d (d=3 %d) lifted %d yard-waits %d feeders %d attackers %d badtile %d builds-inside %d' % (
        cnt('@held'), cnt('@held.*d=3'), cnt('@lift'), cnt('@yard'), cnt('@feeder'), cnt('@attacker'), cnt('@badtile'), cnt('@build.*inside')))
