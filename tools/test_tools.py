#!/usr/bin/env python3
"""Tests for the python tools: the SPRT, the Elo bookkeeping, the scrimmage recorder, the benchmark
selector. Synthetic inputs; runs from tools/unit-tests.sh. Every check names what it protects."""
import math, os, subprocess, sys, tempfile, csv, importlib.util
HERE = os.path.dirname(os.path.abspath(__file__))
fails = 0
def check(ok, what):
    global fails
    if not ok: fails += 1; print('FAIL', what)

# --- sprt.py: the verdict is a pure function of (wins, losses)
def sprt(w, l, extra=()):
    out = subprocess.run([sys.executable, os.path.join(HERE, 'sprt.py'), str(w), str(l), *extra], capture_output=True, text=True).stdout
    return out.strip().split()[-1]
check(sprt(0, 0) == 'CONTINUE', 'sprt: no games is CONTINUE')
check(sprt(60, 20) == 'ACCEPT', 'sprt: 60-20 accepts')
check(sprt(20, 60) == 'REJECT', 'sprt: 20-60 rejects')
check(sprt(8, 8) == 'CONTINUE', 'sprt: 8-8 continues')
# the boundaries are the log-likelihood ratio thresholds log(beta/(1-alpha)) and log((1-beta)/alpha)
llr = lambda w, l, p0=.5, p1=.58: w * math.log(p1 / p0) + l * math.log((1 - p1) / (1 - p0))
hi = math.log(0.95 / 0.05)
n = next(n for n in range(1, 400) if llr(n, 0) >= hi)
check(sprt(n, 0) == 'ACCEPT' and sprt(n - 1, 0) == 'CONTINUE', f'sprt: accept boundary at {n} straight wins')

# --- elolib: ratings move in the right direction and a never-met bot stays at 1500
spec = importlib.util.spec_from_file_location('elolib', os.path.join(HERE, 'elolib.py')); elolib = importlib.util.module_from_spec(spec); spec.loader.exec_module(elolib)
rows = [dict(run='r1', seq=0, teamA='us:g0', teamB='x.bot', map='M', winner='A', rounds='100', reason=''),
        dict(run='r1', seq=1, teamA='y.bot', teamB='us:g0', map='M', winner='B', rounds='100', reason=''),
        dict(run='r1', seq=2, teamA='us:g0', teamB='x.bot', map='M', winner='B', rounds='100', reason='')]
R, games, wins, hist = elolib.ratings(rows)
check(games['us'] == 3 and games['x.bot'] == 2 and games['y.bot'] == 1, 'elo: games counted per player')
check(wins['us'] == 2 and wins['x.bot'] == 1, 'elo: wins counted')
check(R['y.bot'] < 1500 < R['us'], 'elo: a loser drops and the winner rises')
check(R['never.met'] == 1500, 'elo: unmet bot is 1500')
check(abs(elolib.expected(1500, 1500) - 0.5) < 1e-9 and elolib.expected(1900, 1500) > 0.9, 'elo: expected score')
check(len(hist) == 3, 'elo: one history point per game of ours')
check(elolib.pid('us:anything') == 'us' and elolib.pid('a.b') == 'a.b', 'elo: our builds share one rating')

# --- scrim-record.py: reads a gauntlet results.csv into games.csv rows, idempotent per run
with tempfile.TemporaryDirectory() as d:
    run = os.path.join(d, '20260101-000000-scrim-bot'); os.makedirs(run)
    open(os.path.join(run, 'results.csv'), 'w').write('opponent,map,bot_side,winner_side,rounds,bot_result,reason\n'
        'x.bot,Maze,A,A,500,win,HQ destroyed\nx.bot,Soup,B,A,700,loss,HQ destroyed\ny.bot,Maze,B,?,?,unknown,timeout\n')
    env = dict(os.environ, PYTHONPATH=HERE)
    # point elolib at a temp games.csv by copying the module into a fake repo
    fake = os.path.join(d, 'repo', 'tools'); os.makedirs(fake); os.makedirs(os.path.join(d, 'repo', 'progress'))
    for f in ('elolib.py', 'scrim-record.py'): open(os.path.join(fake, f), 'w').write(open(os.path.join(HERE, f)).read())
    r1 = subprocess.run([sys.executable, os.path.join(fake, 'scrim-record.py'), run, '--label', 'g0'], capture_output=True, text=True)
    r2 = subprocess.run([sys.executable, os.path.join(fake, 'scrim-record.py'), run, '--label', 'g0'], capture_output=True, text=True)
    rows = list(csv.DictReader(open(os.path.join(d, 'repo', 'progress', 'games.csv'))))
    check(len(rows) == 2, f'scrim-record: two decided games recorded, unknown skipped ({len(rows)})')
    check(rows[0]['teamA'] == 'us:g0' and rows[0]['winner'] == 'A' and rows[1]['teamA'] == 'x.bot' and rows[1]['winner'] == 'A', 'scrim-record: sides and winners preserved')
    check('already recorded' in r2.stdout, 'scrim-record: second run is a no-op')

# --- bench-select.py: name-only scoring prefers finals over sprints and drops test packages
spec = importlib.util.spec_from_file_location('bsel', os.path.join(HERE, 'bench-select.py')); bsel = importlib.util.module_from_spec(spec); spec.loader.exec_module(bsel)
check(bsel.score('finalbot') > bsel.score('sprintbot') > bsel.score('v1'), 'bench-select: final > sprint > v1')
check(bsel.JUNK.search('testplayer') and bsel.JUNK.search('donothing') and not bsel.JUNK.search('finalbot'), 'bench-select: junk filter')

print('test_tools: %s' % ('OK' if fails == 0 else f'FAILED {fails}'))
sys.exit(1 if fails else 0)
