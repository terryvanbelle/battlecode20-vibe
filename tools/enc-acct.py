"""The enclosure holders' dirt accounting: tools/enc-acct.py <log> <A|B> <hqx> <hqy>
Per holder per 100 rounds, grouped by the held tile's Chebyshev distance from the HQ: digs, own-tile deposits (self),
deposits on neighbours (fed) and reclaims, over fixed windows. Reads the @wallstat tags (every 100 rounds)."""
import re, sys, collections
log, side, hx, hy = sys.argv[1], sys.argv[2], int(sys.argv[3]), int(sys.argv[4])
rows = collections.defaultdict(dict)
for l in open(log, errors='replace'):
    m = re.match(r'\[%s:LANDSCAPER#(\d+)@(\d+)\] @wallstat tile=(\[(-?\d+), (-?\d+)\]|null).*digs=(\d+) deps=(\d+) self=(\d+) fed=(\d+) hqDigs=(\d+) reclaimed=(\d+)' % side, l)
    if not m: continue
    rid, r = int(m[1]), int(m[2]); tile = None if m[3] == 'null' else (int(m[4]), int(m[5]))
    rows[rid][r] = (tile, int(m[6]), int(m[8]), int(m[9]), int(m[11]))
for a, b in ((300, 500), (500, 700), (700, 1000), (1000, 1500), (1500, 2000), (2000, 2500), (2500, 2900)):
    g = collections.defaultdict(lambda: [0, 0, 0, 0, 0])
    for rid, d in rows.items():
        if a not in d or b not in d: continue
        t = d[b][0]; cls = 'none' if t is None else 'd%d' % max(abs(t[0] - hx), abs(t[1] - hy))
        x = g[cls]; x[0] += 1
        for i in range(1, 5): x[i] += d[b][i] - d[a][i]
    print('r%d-%d:' % (a, b), '  '.join('%s n=%d digs=%.0f/100r self=%.0f fed=%.0f recl=%.0f' % (c, x[0], 100 * x[1] / x[0] / (b - a), 100 * x[2] / x[0] / (b - a), 100 * x[3] / x[0] / (b - a), 100 * x[4] / x[0] / (b - a)) for c, x in sorted(g.items())))
