#!/usr/bin/env python3
"""Summarise a study.tsv from tools/scrim-study.sh: medians per round and per opponent, us vs them."""
import csv, sys, os, statistics as st, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from derived import add_derived
NAV = '--nav' in sys.argv
rows = list(csv.DictReader(open(sys.argv[1]), delimiter='\t'))
def num(x):
    try: return float(x)
    except: return None
def med(rs, k):
    v = [num(r[k]) for r in rs]; v = [x for x in v if x is not None]
    return st.median(v) if v else float('nan')
if NAV:
    print(f"\n== exploration ({len(rows)} games)   [coverage = share of tiles the team ever stood on]")
    print(f"{'':22s} {'coverage%':>11s} {'moves':>11s} {'moves/unit':>12s} {'aba%':>8s} {'firstEnemyHQ':>13s}")
    for who, p in (('us (median)', 'us_'), ('them (median)', 'th_')):
        print(f"{who:22s} {med(rows,p+'cov'):11.1f} {med(rows,p+'moves'):11.0f} {med(rows,p+'meanMoves'):12.1f} {med(rows,p+'aba'):8.1f} {med(rows,p+'firstHQ'):13.0f}")
    by = collections.defaultdict(list)
    for r in rows: by[r['opp']].append(r)
    print("  per opponent (our coverage / theirs, won):")
    for o, v in sorted(by.items(), key=lambda kv: med(kv[1], 'us_cov') - med(kv[1], 'th_cov')):
        print(f"    {o[:30]:30s} {med(v,'us_cov'):5.1f}% / {med(v,'th_cov'):5.1f}%   won {sum(1 for r in v if r['won']=='1')}/{len(v)}")
    sys.exit(0)
add_derived(rows)
for rnd in ('200', '400', '600', '900'):
    rs = [r for r in rows if r['round'] == rnd]
    if not rs: continue
    w = sum(1 for r in rs if r['won'] == '1')
    print(f"\n== r{rnd}  ({len(rs)} games, {w} won)")
    cols = ['soup', 'worth', 'robots', 'miners', 'landscapers', 'drones', 'vaporators', 'netguns', 'hqBuried', 'mines', 'digs', 'pickups', 'died', 'cov']
    print(f"{'':16s}" + "".join(f"{c:>11s}" for c in cols))
    for who, p in (('us (median)', 'us_'), ('them (median)', 'th_')):
        print(f"{who:16s}" + "".join(f"{med(rs, p + c):11.1f}" for c in cols))
    for grp, name in ((lambda r: r['won'] == '1', 'wins: us'), (lambda r: r['won'] == '0', 'losses: us')):
        sub = [r for r in rs if grp(r)]
        if sub: print(f"{name:16s}" + "".join(f"{med(sub, 'us_' + c):11.1f}" for c in cols))
by = collections.defaultdict(list)
for r in rows:
    if r['round'] == '400': by[r['opp']].append(r)
if by:
    print("\n== per opponent at r400 (median): our worth / theirs, our robots / theirs, HQ buried us / them")
    for o, v in sorted(by.items(), key=lambda kv: med(kv[1], 'th_worth') / max(1, med(kv[1], 'us_worth')), reverse=True):
        print(f"  {o[:28]:28s} {med(v,'us_worth'):7.0f} / {med(v,'th_worth'):7.0f}   robots {med(v,'us_robots'):4.0f} / {med(v,'th_robots'):4.0f}   buried {med(v,'us_hqBuried'):3.0f} / {med(v,'th_hqBuried'):3.0f}   won {sum(1 for r in v if r['won']=='1')}/{len(v)}")
