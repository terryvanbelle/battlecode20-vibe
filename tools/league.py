#!/usr/bin/env python3
"""Summarise a league backtest (tools/league.sh, PROMPTS 58): each build's win rate against every league opponent on the
same cells, the league score (mean over opponents), and how well each opponent -- and the whole league -- orders the
builds the way the ladder does.
  tools/league.py 1            # league seed 1: reads gauntlet/*-league1-<build>/results.csv (the latest per build)
Agreement = Spearman rank correlation with the ladder rating, and the share of build pairs ordered as the ladder orders
them, counted over the pairs the ladder separates by more than its combined standard error."""
import csv, glob, math, os, sys, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import elolib

def spearman(xs, ys):
    def ranks(v):
        order = sorted(range(len(v)), key=lambda i: v[i]); r = [0.0] * len(v); i = 0
        while i < len(v):
            j = i
            while j + 1 < len(v) and v[order[j + 1]] == v[order[i]]: j += 1
            for t in range(i, j + 1): r[order[t]] = (i + j) / 2
            i = j + 1
        return r
    rx, ry = ranks(xs), ranks(ys); n = len(xs)
    mx, my = sum(rx) / n, sum(ry) / n
    num = sum((a - mx) * (b - my) for a, b in zip(rx, ry))
    den = math.sqrt(sum((a - mx) ** 2 for a in rx) * sum((b - my) ** 2 for b in ry))
    return num / den if den else float('nan')

def load(seed):
    runs = {}
    for d in sorted(glob.glob(os.path.join(elolib.REPO, 'gauntlet', f'*-league{seed}-*'))):
        f = os.path.join(d, 'results.csv')
        if os.path.exists(f): runs[d.split(f'-league{seed}-', 1)[1]] = f   # later stamps replace earlier ones
    rec = collections.defaultdict(lambda: [0, 0])   # (build, opp) -> [wins, games]
    for b, f in runs.items():
        for r in csv.DictReader(open(f)):
            if r['bot_result'] not in ('win', 'loss'): continue
            rec[b, r['opponent']][1] += 1; rec[b, r['opponent']][0] += r['bot_result'] == 'win'
    return sorted(runs), rec

def main():
    seed = sys.argv[1] if len(sys.argv) > 1 else '1'
    builds, rec = load(seed)
    R, SE, _, _ = elolib.fit(elolib.load())
    builds = [b for b in builds if ('us:' + b) in R]
    if len(builds) < 3: print(f"league{seed}: fewer than three ladder-rated builds played"); return
    opps = sorted({o for (_, o) in rec})
    lad = {b: R['us:' + b] for b in builds}; se = {b: SE['us:' + b] for b in builds}
    rate = {(b, o): rec[b, o][0] / rec[b, o][1] for b in builds for o in opps if rec[b, o][1]}
    score = {b: sum(rate.get((b, o), 0) for o in opps) / len(opps) for b in builds}
    builds.sort(key=lambda b: -lad[b])
    def agree(val):
        ok = n = 0
        for i, a in enumerate(builds):
            for b in builds[i + 1:]:
                if abs(lad[a] - lad[b]) <= math.hypot(se[a], se[b]) or val[a] == val[b]: continue
                n += 1; ok += (val[a] > val[b]) == (lad[a] > lad[b])
        return f"{ok}/{n}" if n else '-'
    print(f"league{seed}: {len(builds)} builds x {len(opps)} opponents, "
          f"{sum(rec[b, o][1] for b in builds for o in opps)} games")
    print(f"{'build':10s} {'ladder':>7s} {'league':>7s}  " + ' '.join(f"{o[-8:]:>8s}" for o in opps))
    for b in builds:
        print(f"{b:10s} {lad[b]:7.0f} {score[b]:7.1%}  " + ' '.join(f"{rate.get((b, o), float('nan')):8.0%}" for o in opps))
    xs = [lad[b] for b in builds]
    print(f"{'agreement':10s} {'':7s} {spearman(xs, [score[b] for b in builds]):7.2f}  "
          + ' '.join(f"{spearman(xs, [rate.get((b, o), 0) for b in builds]):8.2f}" for o in opps))
    print(f"{'pairs':10s} {'':7s} {agree(score):>7s}  " + ' '.join(f"{agree({b: rate.get((b, o), 0) for b in builds}):>8s}" for o in opps))

if __name__ == '__main__':
    main()
