"""Shared ladder bookkeeping over progress/games.csv (one row per scrimmage, in play order).
Columns: run,seq,teamA,teamB,map,winner(A|B),rounds,reason. Our team appears as 'us:<build>'.

Ratings are a batch Bradley-Terry fit over every game at once (2026-09-24, PROMPTS 15-16), on the Elo
scale (400 points = 10:1 odds), and each of our builds is its own player. The old sequential K=32 Elo,
with one 'us' rating inherited by every build, depended on play order: 96 easy calibration games lifted
'us' from rank 65 to rank 4, above bots with 104-9 records against us. The fit has no order, and a
build's rating comes only from its own games. A weak prior (one virtual win and one loss against a
1500 anchor) keeps unbeaten or winless records finite; the anchor fixes the scale at 1500."""
import csv, os, math, collections
REPO = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
GAMES = os.path.join(REPO, 'progress', 'games.csv')
HDR = ['run', 'seq', 'teamA', 'teamB', 'map', 'winner', 'rounds', 'reason']
SCALE = 400 / math.log(10)
def is_ours(name): return name.startswith('us:')
def build_of(name): return name[3:] if is_ours(name) else None
def load():
    return list(csv.DictReader(open(GAMES))) if os.path.exists(GAMES) else []
def expected(ra, rb): return 1 / (1 + 10 ** ((rb - ra) / 400))
def fit(rows, prior=1.0, iters=3000, tol=1e-10):
    """Bradley-Terry by minorise-maximise. -> (R, SE, games, wins): rating, standard error (Elo points,
    from the diagonal of the Fisher information), games and wins per player. Players are the names in
    teamA/teamB as written ('us:g_iter5' is a player, 'us:g_iter3' another)."""
    W = collections.Counter(); games = collections.Counter(); N = collections.defaultdict(collections.Counter)
    for r in rows:
        a, b = r['teamA'], r['teamB']; win = a if r['winner'] == 'A' else b
        W[win] += 1; games[a] += 1; games[b] += 1; N[a][b] += 1; N[b][a] += 1
    s = {p: 1.0 for p in games}
    for _ in range(iters):
        new = {}
        for p in s:
            den = sum(n / (s[p] + s[q]) for q, n in N[p].items()) + 2 * prior / (s[p] + 1)
            new[p] = (W[p] + prior) / den
        delta = max(abs(math.log(new[p] / s[p])) for p in s) if s else 0
        s = new
        if delta < tol: break
    R = collections.defaultdict(lambda: 1500.0); SE = collections.defaultdict(lambda: float('inf'))
    for p in s:
        R[p] = 1500 + SCALE * math.log(s[p])
        info = sum(n * s[p] * s[q] / (s[p] + s[q]) ** 2 for q, n in N[p].items()) + 2 * prior * s[p] / (s[p] + 1) ** 2
        SE[p] = SCALE / math.sqrt(info)
    return R, SE, games, W
def field_score(R, player, field):
    """Expected score of `player` against every bot of `field`, one game each."""
    return sum(expected(R[player], R[b]) for b in field) / len(field) if field else 0.0
def current_build(rows):
    """The build of our most recent game (the incumbent in play)."""
    for r in reversed(rows):
        for t in (r['teamA'], r['teamB']):
            if is_ours(t): return build_of(t)
    return None
def ladder_bots():
    p = os.path.join(REPO, 'tools', 'ladder-bots.txt')
    return [l.strip() for l in open(p) if l.strip() and not l.startswith('#')]
def append(rows):
    new = not os.path.exists(GAMES)
    with open(GAMES, 'a', newline='') as fh:
        w = csv.DictWriter(fh, fieldnames=HDR)
        if new: w.writeheader()
        for r in rows: w.writerow(r)
