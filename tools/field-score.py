#!/usr/bin/env python3
# run with tools/.venv/bin/python3 on the driver (numpy, matplotlib live in the venv)
"""Field score over time, with a linear fit extrapolated one and two weeks out (PROMPTS 28).
   tools/field-score.py [--plot progress/field-score.png]
Points: every build in progress/ELO.md's table, dated by its first scrimmage block in progress/games.csv.
The fit is over the submissions (g_iterN) only; candidates are shown hollow. Field score is a build's expected
score against every ladder bot, one game each (tools/elolib.field_score)."""
import argparse, csv, re, os, sys, datetime as dt
import numpy as np
here = os.path.dirname(os.path.abspath(__file__)); repo = os.path.dirname(here)
a = argparse.ArgumentParser(); a.add_argument('--plot', default=os.path.join(repo, 'progress', 'field-score.png')); o = a.parse_args()
scores = {}
for l in open(os.path.join(repo, 'progress', 'ELO.md')):
    m = re.match(r'\| (\S+) \| (\d+) \+- (\d+) \| .*\| ([\d.]+)% \|\s*$', l)
    if m: scores[m[1]] = (float(m[4]), int(m[2]), int(m[3]))
first = {}
for r in csv.DictReader(open(os.path.join(repo, 'progress', 'games.csv'))):
    for b in (r['teamA'], r['teamB']):
        if b.startswith('us:'):
            d = dt.datetime.strptime(r['run'][:15], '%Y%m%d-%H%M%S'); first[b[3:]] = min(first.get(b[3:], d), d)
pts = sorted((first[b], b, s) for b, s in scores.items() if b in first)
if not pts: sys.exit('no dated builds')
subs = [(d, b, s) for d, b, s in pts if re.match(r'g_iter\d+$', b)]
t0 = min(d for d, _, _ in pts)
def days(d): return (d - t0).total_seconds() / 86400
xs = np.array([days(d) for d, _, _ in subs]); ys = np.array([s[0] for _, _, s in subs])
slope, icpt = np.polyfit(xs, ys, 1)
now = dt.datetime.utcnow(); x_now = days(now)
pred = {k: icpt + slope * (x_now + k) for k in (7, 14)}
print('builds:', ', '.join(f'{b} {s[0]:.1f}% ({d:%m-%d %H:%M})' for d, b, s in pts))
print(f'fit over {len(subs)} submissions: {slope:+.2f} points/day; now {icpt + slope * x_now:.1f}%, +7d {pred[7]:.1f}%, +14d {pred[14]:.1f}%')
# a second fit over the last day's submissions: the first day's climb from 62% is not the current rate, and 100% is the ceiling
recent = [(d, b, s) for d, b, s in subs if (subs[-1][0] - d).total_seconds() <= 86400]
slope2 = icpt2 = None
if len(recent) >= 3:
    xr = np.array([days(d) for d, _, _ in recent]); yr = np.array([s[0] for _, _, s in recent]); slope2, icpt2 = np.polyfit(xr, yr, 1)
    print(f'fit over the last day ({len(recent)} submissions): {slope2:+.2f} points/day; +7d {icpt2 + slope2 * (x_now + 7):.1f}%, +14d {icpt2 + slope2 * (x_now + 14):.1f}%')
import matplotlib; matplotlib.use('Agg'); import matplotlib.pyplot as plt
fig, ax = plt.subplots(figsize=(9.5, 5.2))
cand = [(d, b, s) for d, b, s in pts if (d, b, s) not in subs]
ax.errorbar([d for d, _, _ in subs], ys, fmt='o', color='tab:blue', ms=6, label='submission (g_iterN)', zorder=3)
if cand: ax.plot([d for d, _, _ in cand], [s[0] for _, _, s in cand], 'o', mfc='white', mec='tab:gray', ms=5, label='candidate (arm only)')
for d, b, s in subs: ax.annotate(b, (d, s[0]), textcoords='offset points', xytext=(4, 5), fontsize=7)
xfit = np.linspace(0, x_now, 50); ax.plot([t0 + dt.timedelta(days=x) for x in xfit], icpt + slope * xfit, '-', color='tab:blue', lw=1.2, label=f'linear fit: {slope:+.2f} pts/day')
xext = np.linspace(x_now, x_now + 14, 50); ax.plot([t0 + dt.timedelta(days=x) for x in xext], icpt + slope * xext, '--', color='tab:blue', lw=1.2, label='extrapolation')
for k in (7, 14):
    d = now + dt.timedelta(days=k); ax.plot([d], [pred[k]], 's', color='tab:red', ms=6)
    ax.annotate(f'+{k}d: {pred[k]:.1f}%', (d, pred[k]), textcoords='offset points', xytext=(-8, 8), fontsize=8, ha='right', color='tab:red')
if slope2 is not None:
    xr2 = np.linspace(days(recent[0][0]), x_now + 14, 60); ax.plot([t0 + dt.timedelta(days=x) for x in xr2], icpt2 + slope2 * xr2, '--', color='tab:gray', lw=1.0, label=f'last day only: {slope2:+.2f} pts/day')
ax.axhline(100, color='0.7', lw=0.8); ax.annotate('100% is the ceiling', (t0, 100), textcoords='offset points', xytext=(3, -10), fontsize=7, color='0.4')
ax.axvline(now, color='0.6', lw=0.8, ls=':'); ax.annotate('now', (now, ax.get_ylim()[0]), textcoords='offset points', xytext=(3, 3), fontsize=7, color='0.4')
ax.set_ylabel('field score: expected score vs every ladder bot (%)'); ax.set_xlabel('first scrimmage block (UTC)')
ax.set_title('Field score by build, with a linear fit over the submissions extrapolated one and two weeks')
ax.grid(alpha=.3); ax.legend(fontsize=8, loc='lower right'); fig.autofmt_xdate(); fig.tight_layout(); fig.savefig(o.plot, dpi=120); print('wrote', o.plot)
