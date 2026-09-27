#!/usr/bin/env python3
"""Replay puppet, the logic half (PROMPTS 59-60; DESIGN.md "Puppet").

A puppet (`src/pup_<base>`) replays ONE side of a recorded game from its replay up to a cutoff round, then plays
<base>. This file turns the raw event lines of tools/puppet/RawEvents.java into the fixture the runtime reads
(`bc.testing.pup.idx` / `bc.testing.pup.dat`), compares two replays round by round, and prints paired cells.
tools/puppet.sh is the entry point; the subcommands here read raw lines, never a .bc20.

  puppet.py extract --raw RAW --replay R [--side A|B] [--name N] [--out-dir D]   RAW: file or '-'
  puppet.py diff --rec RAW1 --new RAW2 --side A|B [--log game.log] [--cutoff C] [--rounds]
  puppet.py cells FIXTURE CUTOFF...

Every target is a tile, never an id (ids shift as soon as either side builds differently). A robot's script is
found by its key (type, first-turn round, first-turn tile); see keys() below.
"""
import argparse, collections, datetime, hashlib, itertools, os, re, subprocess, sys

HERE = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.dirname(HERE)
M32 = 0xFFFFFFFF
SALT = 0x5eed2020

# schema codes
HQ, MINER, REFINERY, VAPORATOR, SCHOOL, CENTER, LANDSCAPER, DRONE, NETGUN, COW = range(10)
TYPE = ['HQ', 'MINER', 'REFINERY', 'VAPORATOR', 'SCHOOL', 'CENTER', 'LANDSCAPER', 'DRONE', 'NETGUN', 'COW']
BUILDINGS = {HQ, REFINERY, VAPORATOR, SCHOOL, CENTER, NETGUN}
A_MINE, A_DEP_SOUP, A_REFINE, A_DIG, A_DEPOSIT, A_PICK, A_DROP, A_SPAWN, A_SHOOT = range(9)
# puppet ops (the shared contract with src/puppet/Script.java)
MOVE, PLACE, MINE, DIG, DEPOSIT, DEP_SOUP, BUILD, PICK_OWN, PICK_OTHER, DROP, SHOOT, DIE = range(12)
OPS = ['MOVE', 'PLACE', 'MINE', 'DIG', 'DEPOSIT', 'DEP_SOUP', 'BUILD', 'PICK_OWN', 'PICK_OTHER', 'DROP', 'SHOOT', 'DIE']
PHASE = {PLACE: 0, DIE: 2}   # within a round: a drop that frees the robot, then its act, then its death
# messages one robot submits in a round, pre-act and post-act together: floor((bytecodeLimit - 600 reserve - ~700 for the
# turn's act and loop) / 270): 262-298 bytecodes per message on turns carrying 10 or more, a fitted slope of 270
# (Constriction r1-2427, 596 carrying turns against the same robot's turns without; src/puppet/Puppet.tx)
BYTECODE_LIMIT = {HQ: 20000, MINER: 10000, REFINERY: 5000, VAPORATOR: 5000, SCHOOL: 5000, CENTER: 5000, LANDSCAPER: 10000, DRONE: 10000, NETGUN: 7000, COW: 0}
TX_COST, TX_RESERVE, TX_TURN = 270, 600, 700
TX_CAP = {t: max(0, (L - TX_RESERVE - TX_TURN) // TX_COST) for t, L in BYTECODE_LIMIT.items()}
PRE = 0x8000     # a TX record's round char flag: sent at the start of the turn, before the act (Script.txPre)
VERSION = 1
FIXTURE_KEYS = ('bc.testing.pup.idx', 'bc.testing.pup.dat')


class PuppetError(Exception):
    pass


def fail(msg):
    raise PuppetError(msg)


# ---------------------------------------------------------------------------------------------------- raw lines

class Round:
    __slots__ = ('r', 'soup', 'S', 'M', 'A', 'D', 'W', 'U', 'X', 'T', 'K', 'C')

    def __init__(self, r, soup):
        self.r, self.soup = r, soup
        self.S, self.M, self.A, self.D, self.W, self.U, self.X, self.T, self.K, self.C = ([] for _ in range(10))


class Game:
    def __init__(self):
        self.header = None      # dict(pkg=[_, A, B], map, w, h, seed, minx, miny)
        self.bodies = []        # (id, team, type, x, y)
        self.water = set()      # initially flooded tiles
        self.rounds = []
        self.end = None         # (winner, rounds)


def parse(lines):
    """Raw lines (RawEvents format) -> Game."""
    g = Game(); cur = None
    for ln in lines:
        f = ln.split()
        if not f: continue
        t = f[0]
        if t == 'R':
            cur = Round(int(f[1]), (0, int(f[2]), int(f[3]))); g.rounds.append(cur)
        elif t == 'M': cur.M.append((int(f[1]), int(f[2]), int(f[3])))
        elif t == 'C': cur.C.append((int(f[1]), int(f[2])))
        elif t == 'A': cur.A.append((int(f[1]), int(f[2]), int(f[3])))
        elif t == 'D': cur.D.append((int(f[1]), int(f[2]), int(f[3])))
        elif t == 'U': cur.U.append((int(f[1]), int(f[2]), int(f[3])))
        elif t == 'S': cur.S.append(tuple(int(v) for v in f[1:6]))
        elif t == 'X': cur.X.append(int(f[1]))
        elif t == 'W': cur.W.append((int(f[1]), int(f[2])))
        elif t in 'TK':
            (cur.T if t == 'T' else cur.K).append((int(f[1]), tuple(int(v) for v in f[2:])))
        elif t == 'B': g.bodies.append(tuple(int(v) for v in f[1:6]))
        elif t == 'F': g.water.add((int(f[1]), int(f[2])))
        elif t == 'H':
            g.header = dict(pkg=['-', f[1], f[2]], map=f[3], w=int(f[4]), h=int(f[5]), seed=int(f[6]), minx=int(f[7]), miny=int(f[8]))
        elif t == 'E': g.end = (int(f[1]), int(f[2]))
        else: fail('unknown raw line: ' + ln.strip())
    if g.header is None: fail('no H line: not a RawEvents stream')
    return g


def read_raw(path):
    fh = sys.stdin if path == '-' else open(path)
    try: return parse(fh)
    finally:
        if fh is not sys.stdin: fh.close()


# ---------------------------------------------------------------------------------------------------- auth

def s32(v):
    v &= M32
    return v - (1 << 32) if v & 0x80000000 else v


def auth(m, rnd, team_ordinal):
    """Our Comms.auth (every src/*/Comms.java; pinned by test_tools.py), 32-bit wrap."""
    h = (SALT ^ (team_ordinal * 0x9E3779B9) ^ (rnd * 0x85EBCA6B)) & M32
    for i in range(6):
        h ^= m[i] & M32
        h = (h * 0x27D4EB2F) & M32
        h ^= h >> 15
    return s32(h)


def ours_msg(words, rnd, team_ordinal):
    return len(words) == 7 and (words[6] == auth(words, rnd, team_ordinal) or words[6] == auth(words, rnd - 1, team_ordinal))


# ---------------------------------------------------------------------------------------------------- extraction

def cheb(a, b): return max(abs(a[0] - b[0]), abs(a[1] - b[1]))
def d2(a, b): return (a[0] - b[0]) ** 2 + (a[1] - b[1]) ** 2


# ---------------------------------------------------------------------------------------------------- the chain

MASK48 = (1 << 48) - 1


class ChainModel:
    """The engine's blockchain: RobotControllerImpl's static Random(seed), re-created at every robot construction and
    drawn once per submitTransaction; GameWorld's PriorityQueue<Transaction> (java.util binary heap) polled 7 times
    at the end of each round, Transaction.compareTo = (cost desc, other.id - this.id as an int, serialized text)."""

    def __init__(self, seed):
        self.seed = seed
        self.ids = []

    def id(self, d):
        """The d-th nextInt() of a fresh Random(seed)."""
        while len(self.ids) <= d:
            if not self.ids: self._s = (self.seed ^ 0x5DEECE66D) & MASK48
            self._s = (self._s * 0x5DEECE66D + 0xB) & MASK48
            self.ids.append(s32(self._s >> 16))
        return self.ids[d]

    @staticmethod
    def cmp(a, b):   # a.compareTo(b); a, b = (cost, id, text)
        if b[0] != a[0]: return b[0] - a[0]
        if b[1] != a[1]: return s32(b[1] - a[1])
        x, y = a[2], b[2]
        for i in range(min(len(x), len(y))):
            if x[i] != y[i]: return ord(x[i]) - ord(y[i])
        return len(x) - len(y)

    def offer(self, q, x):
        q.append(x); k = len(q) - 1
        while k > 0:
            p = (k - 1) >> 1
            if self.cmp(x, q[p]) >= 0: break
            q[k] = q[p]; k = p
        q[k] = x

    def poll(self, q):
        res = q[0]; x = q.pop()
        if q:
            size = len(q); k = 0; half = size >> 1
            while k < half:
                child = 2 * k + 1; c = q[child]; right = child + 1
                if right < size and self.cmp(c, q[right]) > 0: child = right; c = q[child]
                if self.cmp(x, c) <= 0: break
                q[k] = c; k = child
            q[k] = x
        return res

    def step(self, d, heap, rd, cs):
        """One round under placement cs -> (d, heap) after it, or None if the minted block is not the recorded one."""
        q = list(heap); done = 0
        for k, (cost, words) in enumerate(rd.T):
            if cs[k] > done: d = 0; done = cs[k]
            self.offer(q, (cost, self.id(d), '_'.join(map(str, words)), words)); d += 1
        if len(rd.S) > done: d = 0
        for (cost, words) in rd.K:
            if not q: return None
            x = self.poll(q)
            if x[0] != cost or x[3] != tuple(words): return None
        if q and len(rd.K) < 7: return None
        return d, q

    def layer(self, prev, rd, feasible, beam, budget):
        """The states after round rd reached from the states prev (a realizable placement preferred, later positions
        first), at most `beam` of them; [] if no placement reproduces the recorded block within `budget` steps."""
        m, nS = len(rd.T), len(rd.S)
        nxt = []; seen = set(); steps = 0
        combos = (tuple(reversed(c)) for c in itertools.combinations_with_replacement(range(nS, -1, -1), m)) if m else iter([()])
        combos = list(itertools.islice(combos, budget))                 # non-decreasing, latest placements first
        ok = [c for c in combos if feasible(rd.r, c)]
        for pool in (ok, combos):
            for pi, (d0, heap, _, _) in enumerate(prev):
                for c in pool:
                    steps += 1
                    if steps > budget: break
                    res = self.step(d0, heap, rd, c)
                    if res is None: continue
                    key = (res[0], tuple((x[0], x[1], x[2]) for x in res[1]))
                    if key in seen: continue
                    seen.add(key); nxt.append((res[0], res[1], pi, c))
                    if len(nxt) >= beam: break
                if len(nxt) >= beam or steps > budget: break
            if nxt: break
        return nxt

    @staticmethod
    def draws_after(rounds, j):
        """The possible id draws since the last robot construction at the end of rounds[j], when the placement of
        the last round with a spawn is unknown: every submission of that round after its last spawn (0..|T|), then
        every submission of the later rounds; before any spawn, every submission since the game started."""
        n = 0
        for t in range(j, -1, -1):
            if rounds[t].S: return [n + x for x in range(len(rounds[t].T) + 1)]
            n += len(rounds[t].T)
        return [n]

    def search(self, rounds, feasible, beam=48, budget=200000):
        """Beam search over the rounds -> ({round: placement} along paths that reproduce every recorded block,
        restarts). At a round no placement reproduces (or whose search exceeds `budget` steps) the path breaks; a
        fresh beam restarts after the next round that leaves the queue empty (cumulative T minus K is 0), whose
        state is known but for the id draws since the last spawn (draws_after, one state each). Rounds in a break
        are absent from the path."""
        path, restarts = {}, 0
        pending, empty_after = 0, []            # the queue's size after each round, from the recorded counts
        for rd in rounds:
            pending += len(rd.T) - len(rd.K); empty_after.append(pending == 0)
        start, layers, i = 0, [[(0, [], None, None)]], 0
        while True:
            nxt = self.layer(layers[-1], rounds[i], feasible, beam, budget) if i < len(rounds) else []
            if nxt:
                layers.append(nxt); i += 1; continue
            k = 0                               # the segment [start, i) along one surviving state
            for li in range(len(layers) - 1, 0, -1):
                d, heap, parent, c = layers[li][k]
                path[rounds[start + li - 1].r] = c; k = parent
            if i >= len(rounds): break
            j = next((j for j in range(i, len(rounds)) if empty_after[j]), None)
            if j is None or j + 1 >= len(rounds): break
            start = i = j + 1; restarts += 1
            layers = [[(d, [], None, None) for d in self.draws_after(rounds, j)]]
        return path, restarts


class Bot:
    __slots__ = ('id', 'team', 'type', 'pos', 'alive', 'held', 'spawn', 'exe', 'ev', 'tx', 'held_first', 'release', 'spawn_pos', 'death')

    def __init__(self, id, team, type, pos, spawn, exe):
        self.id, self.team, self.type, self.pos, self.spawn, self.exe = id, team, type, pos, spawn, exe
        self.spawn_pos = pos
        self.alive, self.held, self.death = True, None, None
        self.ev, self.tx = [], []
        self.held_first, self.release = False, None


class Extraction:
    """One pass over the rounds for side P (1 = A, 2 = B). O = the other side. Messages are attributed by our auth:
    msg_team is the side whose auth decides ('O' when O is ours, 'P' when P is ours)."""

    def __init__(self, g, P, msg_by):
        self.g, self.P, self.O = g, P, 3 - P
        self.msg_by = msg_by        # 'O' or 'P'
        self.bots = {}
        self.exe = 0
        self.warn = collections.Counter()
        self.first_warn = {}
        self.stats = collections.Counter()
        self.tx_rounds = collections.Counter()
        self.flooded = set(g.water)
        self.msg_rounds = {}     # r -> (P flags per T line, builder exe per spawn, free P robots by exe)
        self.chain_notes = collections.Counter()
        for (i, t, ty, x, y) in g.bodies:
            self.add(i, t, ty, (x, y), 0)

    def add(self, i, t, ty, pos, spawn):
        b = Bot(i, t, ty, pos, spawn, self.exe); self.exe += 1; self.bots[i] = b; return b

    def note(self, what, r, detail=''):
        self.warn[what] += 1
        self.first_warn.setdefault(what, 'r%d %s' % (r, detail))

    def ev(self, b, op, r, tile, arg=0):
        if b is not None and b.team == self.P:
            b.ev.append((op, r, arg, tile[0], tile[1]))
            self.stats[OPS[op]] += 1

    def run(self):
        for rd in self.g.rounds:
            self.round(rd)
        self.assign_messages()
        return self

    def round(self, rd):
        r, bots, P = rd.r, self.bots, self.P
        start = {i: b.pos for i, b in bots.items() if b.alive}
        held_start = {i: b.held for i, b in bots.items() if b.alive}
        died = set(rd.X)
        # 1. spawns, paired with SPAWN_UNIT by id
        newborn = {}
        for (i, t, ty, x, y) in rd.S:
            if i in bots: fail('r%d: spawned id %d already exists' % (r, i))
            newborn[i] = self.add(i, t, ty, (x, y), r)
        spawn_acts = 0
        for (actor, act, tgt) in rd.A:
            if act != A_SPAWN: continue
            spawn_acts += 1
            nb = newborn.get(tgt)
            if nb is None: fail('r%d: SPAWN_UNIT by #%d targets #%d, not spawned this round' % (r, actor, tgt))
            a = bots.get(actor)
            if a is None or cheb(start.get(actor, a.pos), nb.pos) != 1: fail('r%d: #%d builds #%d out of reach' % (r, actor, tgt))
            self.ev(a, BUILD, r, nb.pos, nb.type)
        if spawn_acts != len(rd.S): fail('r%d: %d spawned bodies but %d SPAWN_UNIT actions' % (r, len(rd.S), spawn_acts))
        # 2. pick and drop sequences per target, in action order
        seq = collections.defaultdict(list)
        for k, (actor, act, tgt) in enumerate(rd.A):
            if act in (A_PICK, A_DROP): seq[tgt].append((act, actor, k))
        mv = collections.OrderedDict()
        for (i, x, y) in rd.M: mv.setdefault(i, []).append((x, y))
        final = {}
        for i, e in mv.items(): final[i] = e[-1]
        def final_pos(i): return final.get(i, start.get(i, bots[i].pos if i in bots else None))
        # a newborn is held at its first turn if picked in its spawn round, or at spawn+1 by an older drone
        for tgt, s in seq.items():
            u = bots.get(tgt)
            if u is None or u.team != P or u.held_first: continue
            for (act, actor, k) in s:
                if act == A_PICK and (u.spawn == r or (u.spawn == r - 1 and bots[actor].exe < u.exe)): u.held_first = True
                break
        # 3. move classification
        pick_tile, drop_tile, death_drops = {}, {}, set()
        wet_drop = set()            # units dropped onto a flooded tile: they drown at the drop
        held_now = {}
        positions = collections.defaultdict(list)
        for i, p in start.items(): positions[i].append(p)
        for i, entries in mv.items():
            u = bots.get(i)
            if u is None: fail('r%d: move of unknown #%d' % (r, i))
            positions[i].extend(entries)
            held = held_start.get(i)
            carried_from_start = held is not None
            followed, own, k, s = False, 0, 0, seq.get(i, [])
            cur = start.get(i, u.pos)
            for p in entries:
                nxt = s[k] if k < len(s) else None
                if held is not None:
                    c = held
                    c_moved = carried_from_start and c in mv
                    if nxt is not None and nxt[0] == A_DROP and nxt[1] == c and not (c_moved and not followed):
                        # the drop entry: a regular drop (c made no move) or a death drop (after c's move, if any)
                        death = c in died and p == final_pos(c)
                        if death: death_drops.add(nxt[2])
                        else:
                            if c_moved: fail('r%d: #%d dropped by #%d, which also moved' % (r, i, c))
                            if cheb(final_pos(c), p) != 1: fail('r%d: drop of #%d at %s not adjacent to #%d' % (r, i, p, c))
                        drop_tile[nxt[2]] = p
                        if p in self.flooded: wet_drop.add(i)
                        k += 1; held = None; carried_from_start = False
                        if i not in died and u.team == P:
                            self.ev(u, PLACE, r, p)
                            if u.held_first and u.release is None: u.release = (r, p)
                    else:
                        if not c_moved or followed: fail('r%d: #%d moved while held by #%d, which did not move' % (r, i, c))
                        followed = True
                else:
                    if nxt is not None and nxt[0] == A_PICK and p == start.get(nxt[1]):
                        pick_tile[nxt[2]] = cur
                        held = nxt[1]; carried_from_start = False; followed = False; k += 1
                    else:
                        own += 1
                        if cheb(cur, p) != 1: fail('r%d: own move of #%d %s -> %s is not adjacent' % (r, i, cur, p))
                        self.ev(u, MOVE, r, p, 1 if i in died else 0)
                cur = p
            if own > 1: fail('r%d: #%d made %d own moves' % (r, i, own))
            if k != len(s): fail('r%d: #%d has %d pick/drop actions but %d matched move entries' % (r, i, len(s), k))
            u.pos = cur; held_now[i] = held
        for i, h in held_now.items(): bots[i].held = h
        for tgt, s in seq.items():
            if tgt not in mv: fail('r%d: pick/drop of #%d without a move entry' % (r, tgt))
        # 4. actions, second walk: dirt and soup pointers
        dp = sp = 0
        shot, buried = set(), set()
        spill = None                # (action index, tile) of the last (-1, DEPOSIT_DIRT, -1): a dead robot's dirt
        for k, (actor, act, tgt) in enumerate(rd.A):
            a = bots.get(actor)
            ps = positions.get(actor, [])
            def reach(tile, rr):
                if actor == -1 or any(d2(q, tile) <= rr for q in ps): return
                fail('r%d: #%d %s at %s out of reach of %s' % (r, actor, act, tile, ps))
            if act == A_MINE:
                if sp >= len(rd.U): fail('r%d: MINE by #%d without a soup change' % (r, actor))
                x, y, d = rd.U[sp]; sp += 1
                if d >= 0: fail('r%d: MINE soup change %d >= 0' % (r, d))
                reach((x, y), 2); self.ev(a, MINE, r, (x, y))
            elif act == A_DEP_SOUP:
                t = bots[tgt]; reach(t.pos, 2); self.ev(a, DEP_SOUP, r, t.pos)
            elif act == A_DIG or act == A_DEPOSIT:
                if tgt == -1:
                    if dp >= len(rd.D): fail('r%d: %s by #%d without a dirt change' % (r, 'DIG' if act == A_DIG else 'DEPOSIT', actor))
                    x, y, d = rd.D[dp]; dp += 1; tile = (x, y)
                    if actor == -1:
                        if act != A_DEPOSIT or d <= 0: fail('r%d: actor -1 dirt change %d' % (r, d))
                        spill = (k, tile)
                        continue
                    if (act == A_DIG and d != -1) or (act == A_DEPOSIT and d != 1): fail('r%d: dirt change %d for %d by #%d' % (r, d, act, actor))
                else:
                    tile = bots[tgt].pos
                    # a burial: the deposit that fills a building destroys it first, and its dirt spills onto its
                    # tile as (-1, DEPOSIT_DIRT, -1), recorded just before the deposit (GameWorld.addDirt)
                    if act == A_DEPOSIT and actor != -1 and spill == (k - 1, tile): buried.add(tgt)
                    if actor == -1: continue
                reach(tile, 2); self.ev(a, DIG if act == A_DIG else DEPOSIT, r, tile)
            elif act == A_PICK:
                t = bots[tgt]; tile = pick_tile.get(k)
                if tile is None: fail('r%d: pick of #%d by #%d has no pickup entry' % (r, tgt, actor))
                reach(tile, 3)
                self.ev(a, PICK_OWN if t.team == (a.team if a else -9) else PICK_OTHER, r, tile, t.type)
            elif act == A_DROP:
                if k in death_drops: continue
                tile = drop_tile.get(k)
                if tile is None: fail('r%d: drop of #%d by #%d has no drop entry' % (r, tgt, actor))
                reach(tile, 2); self.ev(a, DROP, r, tile)
            elif act == A_SHOOT:
                shot.add(tgt); tile = final_pos(tgt); reach(tile, 15); self.ev(a, SHOOT, r, tile)
        if dp != len(rd.D): fail('r%d: %d dirt changes, %d consumed by dig/deposit actions' % (r, len(rd.D), dp))
        if sp != len(rd.U): fail('r%d: %d soup changes, %d consumed by MINE actions' % (r, len(rd.U), sp))
        # 5. deaths; a P robot whose death nothing in the record explains disintegrated at its turn (walked into
        # water -- move() kills before recording the move -- or called disintegrate()): the DIE op. The explanations:
        # shot, dropped onto a flooded tile, buried (a building, with its dirt spill), or drowned by the end-of-round
        # flood: GameWorld.floodfill runs after every turn, so its deaths are a suffix of diedIDs, each a non-flying
        # robot on a tile this round's flood covered (in W and flooded at the end), walked back from the end
        toggled = collections.Counter(rd.W)
        flips = {t for t, n in toggled.items() if n % 2}
        covered = {t for t in toggled if (t in self.flooded) != (t in flips)}       # flooded at the round's end
        flood_dead, taken = set(), set()
        for i in reversed(rd.X):
            b = bots.get(i)
            if b is None or b.type == DRONE or b.held is not None or b.pos not in covered or b.pos in taken: break
            flood_dead.add(i); taken.add(b.pos)
        for i in rd.X:
            b = bots.get(i)
            if b is None: fail('r%d: unknown robot #%d died' % (r, i))
            if b.team == P and b.type != HQ:
                explained = i in shot or i in wet_drop or i in flood_dead or (b.type in BUILDINGS and i in buried)
                if not explained:
                    if b.spawn >= r: self.note('newborn died unexplained', r, '#%d' % i)
                    elif b.held is not None: self.note('held robot died unexplained', r, '#%d' % i)
                    else: self.ev(b, DIE, r, b.pos)
            b.alive = False; b.death = r
        for b in bots.values():
            if b.held is not None and not bots[b.held].alive: b.held = None
        self.flooded ^= flips
        # 6. messages: P's go to the tail carrier of the round
        P_msgs, mine_flags = [], []
        for (cost, words) in rd.T:
            if len(words) != 7: fail('r%d: message with %d words' % (r, len(words)))
            if self.msg_by == 'O': mine = not ours_msg(words, r, self.O - 1)
            else: mine = ours_msg(words, r, P - 1)
            mine_flags.append(mine)
            if mine: P_msgs.append((cost, words))
        self.stats['msgs P'] += len(P_msgs); self.stats['msgs O'] += len(rd.T) - len(P_msgs)
        if P_msgs:
            for (cost, words) in P_msgs:
                if not 1 <= cost < 65536: fail('r%d: fee %d does not fit' % (r, cost))
            # assigned after the pass (assign_messages): the free P robots of the round in execution order, and the
            # execution index of each spawn's builder (spawns happen in builder order and re-seed the id Random)
            builder = {tgt: actor for (actor, act, tgt) in rd.A if act == A_SPAWN}
            touched = set(seq.keys())
            free = sorted((b for b in bots.values() if b.team == P and b.alive and b.spawn < r and held_start.get(b.id) is None
                           and b.id not in touched), key=lambda b: b.exe)
            if not free: fail('r%d: no free P robot to carry %d messages' % (r, len(P_msgs)))
            self.msg_rounds[r] = (mine_flags, [bots[builder[i]].exe for (i, _, _, _, _) in rd.S], free)

    def assign_messages(self):
        """Which P robot submits each P message, and in which order.

        The replay does not say who submitted a message. What O can see of P's messages is the minted blocks, in
        order, and that order is decided by the transaction ids: a static Random(seed) re-created at every robot
        construction (every spawn) and drawn once per submission, compared in a PriorityQueue by
        (fee desc, other.id - this.id with int overflow, text). ChainModel replays that queue; for each round the
        search keeps the placements of the round's submissions among its spawns (how many spawns precede each
        message) that reproduce the recorded blocks, and that free P robots can realize (allocate). Fees are then
        paid between the same spawns as in the recording, so every build sees the recorded soup. A round the search
        cannot place falls back to the tail carriers (every build sees at least the recorded soup; the block order
        there is approximate) and is counted in the notes; the search restarts at the next empty queue."""
        g = self.g
        path, restarts = ChainModel(g.header['seed']).search(g.rounds, self.feasible)
        if restarts: self.chain_notes['chain model restarts (at an empty queue)'] = restarts
        for rd in g.rounds:
            r = rd.r
            if r not in self.msg_rounds: continue
            cs = path.get(r)
            plan = self.allocate(r, cs) if cs is not None else None
            if plan is None:
                self.chain_notes['rounds on the tail carriers (block order approximate)'] += 1
                self.note('rounds on the tail carriers (block order approximate)', r)
                plan = self.allocate_tail(r)
            else: self.chain_notes['rounds placed exactly'] += 1
            n = 0
            for b, pre, ks in plan:
                if pre: self.stats['carriers before their act'] += 1
                for k in ks: b.tx.append(((r | PRE) if pre else r,) + tuple(rd.T[k]))
                if ks: self.stats['carriers'] += 1
                n += len(ks)
            self.tx_rounds[r] = self.tx_rounds.get(r, 0) + n

    def feasible(self, r, cs):
        """A placement (spawns before each T line) the free P robots of round r can realize."""
        return r not in self.msg_rounds or self.allocate(r, cs) is not None

    @staticmethod
    def fill(ks, chosen):
        """ks (T indices, in order) over chosen [(bot, pre, capacity)] in execution order: each to its capacity, the
        last takes the rest -> [(bot, pre, ks)]."""
        out, i = [], 0
        for n, (b, pre, cap) in enumerate(chosen):
            take = len(ks) - i if n == len(chosen) - 1 else min(cap, len(ks) - i)
            out.append((b, pre, ks[i:i + take])); i += take
        return out

    def allocate(self, r, cs):
        """The carriers of round r's P messages under placement cs -> [(bot, pre, [T index])] in submission order,
        or None when the free P robots cannot realize it. Message k with c = cs[k] spawns before it goes to the
        latest free P robots acting in [builder of spawn c-1, builder of spawn c), filled by bytecode capacity
        (TX_CAP per robot and round, shared by all its messages of the round); when they fall short, the builder of
        spawn c itself carries the rest before its act (pre: it submits, then builds -- usually the HQ). Within one
        interval O's messages must precede P's (O's submitter, usually its HQ, acts early)."""
        flags, builders, free = self.msg_rounds[r]
        groups, last_p = collections.OrderedDict(), {}
        for k, f in enumerate(flags):
            c = cs[k]
            if f: groups.setdefault(c, []).append(k); last_p[c] = k
            elif c in last_p: return None               # an O message after a P one in the same interval
        used, plan = collections.Counter(), []
        for c, ks in groups.items():
            lo = builders[c - 1] if c > 0 else -1
            hi = builders[c] if c < len(builders) else 1 << 30
            chosen, cap = [], 0
            for b in reversed([b for b in free if lo <= b.exe < hi]):
                room = TX_CAP[b.type] - used[b.id]
                if room <= 0: continue
                chosen.append((b, False, room)); cap += room
                if cap >= len(ks): break
            chosen.sort(key=lambda x: x[0].exe)
            if cap < len(ks):
                pre = next((b for b in free if b.exe == hi), None)
                room = TX_CAP[pre.type] - used[pre.id] if pre is not None else 0
                if cap + room < len(ks): return None
                chosen.append((pre, True, room))
            for b, pre, ks_b in self.fill(ks, chosen):
                used[b.id] += len(ks_b); plan.append((b, pre, ks_b))
        return plan

    def allocate_tail(self, r):
        """Round r's P messages to the latest free robots at or after the round's last P builder (all free robots if
        none), over capacity sent a turn late -> [(bot, False, [T index])]."""
        flags, builders, free = self.msg_rounds[r]
        ks = [k for k, f in enumerate(flags) if f]
        lo = builders[-1] if builders else -1
        chosen, cap = [], 0
        for b in reversed([b for b in free if lo <= b.exe] or free):
            chosen.append((b, False, TX_CAP[b.type])); cap += TX_CAP[b.type]
            if cap >= len(ks): break
        if cap < len(ks): self.note('messages over the carriers\' capacity (sent a turn late)', r, '%d > %d' % (len(ks), cap))
        chosen.sort(key=lambda x: x[0].exe)
        return self.fill(ks, chosen)

    # ------------------------------------------------------------------ keys and encoding
    def keys(self):
        """Key per P robot that takes a turn (events or not: an unkeyed robot would fall back on a neighbour's
        record and replay it): HQ (0, 1, tile); a newborn held at its first turn: (type, round, tile) of its first
        release alive (none if never); otherwise (type, spawn round + 1, spawn tile)."""
        out = {}
        for b in self.bots.values():
            if b.team != self.P or b.type == COW: continue
            if b.death is not None and b.death <= b.spawn:
                if b.ev or b.tx: fail('robot #%d has a script but died in its spawn round' % b.id)
                continue                                    # never took a turn
            if b.type == HQ: k = (HQ, 1) + b.spawn_pos
            elif b.held_first:
                if b.release is None:
                    if b.tx: fail('robot #%d carries messages but was never free' % b.id)
                    if b.ev: self.note('held at first turn and never released', b.spawn, '#%d' % b.id)
                    continue
                k = (b.type, b.release[0]) + b.release[1]
            else: k = (b.type, b.spawn + 1) + b.spawn_pos
            if k in out: fail('key collision %s: #%d and #%d' % (k, out[k].id, b.id))
            out[k] = b
        return out

    def hq(self):
        for b in self.bots.values():
            if b.team == self.P and b.type == HQ: return b.spawn_pos
        fail('no HQ for side %d' % self.P)


def ch(v):
    if not 0 <= v < 65536: fail('value %d does not fit a char' % v)
    return chr(v)


def encode(P, w, h, hq, robots):
    """robots: {(type, keyRound, x, y): (events, txs)} -> (idx, dat) strings (the contract of Script.java)."""
    for (t, kr, x, y) in robots:
        if not (0 <= kr < 4096 and 0 <= x < 64 and 0 <= y < 64): fail('key %s out of range' % ((t, kr, x, y),))
    if not (1 <= w <= 64 and 1 <= h <= 64): fail('map %dx%d out of range' % (w, h))
    idx = [ch(VERSION), ch(P - 1), ch(hq[0] << 6 | hq[1]), ch((w - 1) << 6 | (h - 1))]
    dat = []
    for key in sorted(robots):
        t, kr, x, y = key
        evs, txs = robots[key]
        off = len(dat)
        idx += [ch(t << 12 | kr), ch(x << 6 | y), ch(off >> 16), ch(off & 0xFFFF)]
        dat.append(ch(len(evs)))
        for (op, rnd, arg, ex, ey) in evs:
            if not (0 <= rnd < 4096 and 0 <= ex < 64 and 0 <= ey < 64 and 0 <= arg < 16): fail('event %s out of range' % ((op, rnd, arg, ex, ey),))
            dat += [ch(op << 12 | rnd), ch(arg << 12 | ex << 6 | ey)]
        for (rnd, fee, words) in txs:
            dat += [ch(rnd), ch(fee)]
            for wv in words: wv &= M32; dat += [ch(wv >> 16), ch(wv & 0xFFFF)]
    off = len(dat)
    idx += [ch(15 << 12), ch(0), ch(off >> 16), ch(off & 0xFFFF)]
    return ''.join(idx), ''.join(dat)


def decode(idx, dat):
    """Inverse of encode (tests and the diff's key table): -> (P, w, h, hq, {key: (events, txs)})."""
    v, side, hq, wh = (ord(c) for c in idx[:4])
    if v != VERSION: fail('fixture version %d' % v)
    n = (len(idx) - 4) // 4 - 1
    recs = {}
    for i in range(n):
        c0, c1, o1, o2 = (ord(c) for c in idx[4 + 4 * i: 8 + 4 * i])
        nxt = ord(idx[4 + 4 * (i + 1) + 2]) << 16 | ord(idx[4 + 4 * (i + 1) + 3])
        off = o1 << 16 | o2
        s = [ord(c) for c in dat[off:nxt]]
        na = s[0]; evs = []; txs = []
        for j in range(na):
            a, b = s[1 + 2 * j], s[2 + 2 * j]
            evs.append((a >> 12, a & 0xFFF, b >> 12, (b >> 6) & 63, b & 63))
        j = 1 + 2 * na
        while j < len(s):
            words = tuple(s32(s[j + 2 + 2 * q] << 16 | s[j + 3 + 2 * q]) for q in range(7))
            txs.append((s[j], s[j + 1], words)); j += 16
        recs[(c0 >> 12, c0 & 0xFFF, c1 >> 6, c1 & 63)] = (evs, txs)
    return side + 1, (wh >> 6) + 1, (wh & 63) + 1, (hq >> 6, hq & 63), recs


def fixture_text(idx, dat, comments):
    out = [('# ' + c) for c in comments]
    for key, val in zip(FIXTURE_KEYS, (idx, dat)):
        out.append(key + '=' + ''.join('\\u%04x' % ord(c) for c in val))
    return '\n'.join(out) + '\n'


def read_fixture(path):
    """-> (comments dict, idx, dat)."""
    meta, vals = {}, {}
    for ln in open(path, encoding='ascii'):
        ln = ln.rstrip('\n')
        if ln.startswith('#'):
            for m in re.finditer(r'(\S+?)=(\S+)', ln): meta[m.group(1)] = m.group(2)
        elif '=' in ln:
            k, v = ln.split('=', 1)
            vals[k] = re.sub(r'\\u([0-9a-fA-F]{4})', lambda m: chr(int(m.group(1), 16)), v)
    if set(vals) != set(FIXTURE_KEYS): fail('%s: keys %s, expected exactly %s' % (path, sorted(vals), FIXTURE_KEYS))
    return meta, vals[FIXTURE_KEYS[0]], vals[FIXTURE_KEYS[1]]


def our_packages():
    src = os.path.join(REPO, 'src')
    return {d for d in os.listdir(src) if os.path.isdir(os.path.join(src, d))}


def sha1_file(path, n=None):
    h = hashlib.sha1()
    with open(path, 'rb') as f:
        for blk in iter(lambda: f.read(1 << 20), b''): h.update(blk)
    return h.hexdigest()[:n] if n else h.hexdigest()


def extract(g, side=None, ours=None):
    """-> (Extraction, P). side: 1/2 or None (the side whose package is not ours)."""
    ours = our_packages() if ours is None else ours
    H = g.header
    if H['minx'] != 0 or H['miny'] != 0: fail('map origin (%d,%d) is not (0,0)' % (H['minx'], H['miny']))
    mine = [H['pkg'][t] in ours for t in (1, 2)]
    if side is None:
        if mine[0] == mine[1]: fail('both or neither side is ours (%s, %s): give --side' % (H['pkg'][1], H['pkg'][2]))
        side = 1 if not mine[0] else 2
    O = 3 - side
    if mine[O - 1]: msg_by = 'O'
    elif mine[side - 1]: msg_by = 'P'
    else: fail('neither side is ours: messages cannot be attributed')
    return Extraction(g, side, msg_by).run(), side


def cmd_extract(a):
    rp = os.path.realpath(a.replay)
    if not rp.endswith('.bc20'): fail('not a .bc20: ' + a.replay)
    if '/bc20-benchmarks/' in rp + '/': fail('refused: %s is under bc20-benchmarks (replays only; CLAUDE.md rule 3)' % a.replay)
    g = read_raw(a.raw)
    side = {'A': 1, 'B': 2}[a.side] if a.side else None
    ex, P = extract(g, side)
    H = g.header
    keys = ex.keys()
    robots = {k: (sorted(b.ev, key=lambda e: (e[1], PHASE.get(e[0], 1))), b.tx) for k, b in keys.items()}
    idx, dat = encode(P, H['w'], H['h'], ex.hq(), robots)
    sha = sha1_file(rp)
    Pname, Oname = H['pkg'][P], H['pkg'][3 - P]
    name = a.name or '%s__%s__%s-%s' % (Pname, H['map'], 'AB'[P - 1], sha[:8])
    run = os.path.basename(os.path.dirname(os.path.dirname(rp))) if os.path.basename(os.path.dirname(rp)) in ('losses', 'replays') else os.path.basename(os.path.dirname(rp))
    engine = open(os.path.join(REPO, 'engine', 'VERSION')).readline().strip().replace(' ', '_')
    winner, rounds = g.end if g.end else (0, len(g.rounds))
    comments = ['replay=%s sha1=%s run=%s map=%s seed=%d side=%s P=%s O=%s rounds=%d winner=%s' % (
                    os.path.relpath(rp, REPO) if rp.startswith(REPO + '/') else rp, sha, run, H['map'], H['seed'], 'AB'[P - 1], Pname, Oname, rounds, '?AB'[winner]),
                'engine=%s rawevents=%s puppet.py=%s extracted=%s' % (
                    engine, sha1_file(os.path.join(HERE, 'puppet', 'RawEvents.java'), 12), sha1_file(os.path.abspath(__file__), 12),
                    datetime.datetime.utcnow().strftime('%Y-%m-%dT%H:%M:%SZ'))]
    os.makedirs(a.out_dir, exist_ok=True)
    out = os.path.join(a.out_dir, name + '.properties')
    txt = fixture_text(idx, dat, comments)
    with open(out, 'w', encoding='ascii') as f: f.write(txt)
    nev = sum(len(e) for e, _ in robots.values()); ntx = sum(len(t) for _, t in robots.values())
    print('fixture %s' % os.path.relpath(out, REPO) if out.startswith(REPO) else out)
    print('  %s' % comments[0]); print('  %s' % comments[1])
    print('  P=%s (%s): %d robots keyed, %d events, %d messages on %d rounds (max %d in one round), idx %d chars, dat %d chars, file %d bytes' % (
        'AB'[P - 1], Pname, len(robots), nev, ntx, len(ex.tx_rounds), max(ex.tx_rounds.values() or [0]), len(idx), len(dat), len(txt)))
    print('  events: ' + ' '.join('%s=%d' % (k, v) for k, v in sorted(ex.stats.items())))
    print('  longest script: %d events; messages attributed by %s auth; message rounds: %s' % (
        max([len(e) for e, _ in robots.values()] or [0]), ex.msg_by, ', '.join('%s %d' % (k, v) for k, v in sorted(ex.chain_notes.items())) or 'none'))
    for w, n in sorted(ex.warn.items()): print('  note: %s x%d (first %s)' % (w, n, ex.first_warn[w]))
    return 0


# ---------------------------------------------------------------------------------------------------- diff

class Summary:
    """Per-round comparable facts of one replay, keyed by (team, type, spawn round, spawn tile), never by id."""

    def __init__(self, g):
        self.g = g
        self.rounds = {}
        self.ids = []        # spawn id sequence, for 'ids equal to'
        bots = {}
        key = {}
        for (i, t, ty, x, y) in g.bodies:
            bots[i] = [t, ty, (x, y), True]; key[i] = (t, ty, 0, x, y)
        for rd in g.rounds:
            r = rd.r
            for (i, t, ty, x, y) in rd.S:
                bots[i] = [t, ty, (x, y), True]; key[i] = (t, ty, r, x, y); self.ids.append((r, i))
            pos_before = {i: b[2] for i, b in bots.items()}
            for (i, x, y) in rd.M:
                if i in bots: bots[i][2] = (x, y)
            dp = sp = 0; acts = {1: [], 2: []}
            for (actor, act, tgt) in rd.A:
                t = bots[actor][0] if actor in bots else 0
                if act in (A_DIG, A_DEPOSIT) and tgt == -1:
                    tgt_d = ('tile',) + tuple(rd.D[dp][:2]) if dp < len(rd.D) else ('tile?',); dp += 1
                elif act == A_MINE:
                    tgt_d = ('tile',) + tuple(rd.U[sp][:2]) if sp < len(rd.U) else ('tile?',); sp += 1
                elif act == A_SPAWN: tgt_d = key.get(tgt)
                elif act == A_REFINE or tgt == -1: tgt_d = None
                else: tgt_d = key.get(tgt)
                if t in acts: acts[t].append((key.get(actor), act, tgt_d))
            spawns = {1: collections.Counter(), 2: collections.Counter()}
            for (i, t, ty, x, y) in rd.S:
                if t in spawns: spawns[t][(ty, x, y)] += 1
            deaths = {1: collections.Counter(), 2: collections.Counter()}
            for i in rd.X:
                if i in bots:
                    b = bots[i]
                    if b[0] in deaths: deaths[b[0]][(b[1],) + b[2]] += 1
                    b[3] = False
            for i in [i for i, b in bots.items() if not b[3]]: del bots[i]
            pos = {1: collections.Counter(), 2: collections.Counter()}
            for b in bots.values():
                if b[0] in pos: pos[b[0]][(b[1],) + b[2]] += 1
            bc = {1: {}, 2: {}}
            for (i, n) in rd.C:
                k = key.get(i)
                if k and k[0] in bc: bc[k[0]][k] = n
            self.rounds[r] = dict(soup=rd.soup, acts=acts, spawns=spawns, deaths=deaths, pos=pos, bc=bc,
                                  block=list(rd.K), sub=collections.Counter(rd.T), nsub=len(rd.T), key=key)


def first_diff(a, b, P, O, cutoff=None):
    """-> dict category -> first round differing (or None), plus per-round detail."""
    first = collections.OrderedDict((c, None) for c in ('P pos', 'P acts', 'P soup', 'P spawns', 'P deaths', 'O pos', 'O acts', 'O soup', 'O spawns', 'O deaths', 'O bytecode', 'block'))
    per_round = []
    last = max(max(a.rounds or [0]), max(b.rounds or [0]))
    for r in range(1, last + 1):
        x, y = a.rounds.get(r), b.rounds.get(r)
        diffs = {}
        if x is None or y is None:
            diffs['end'] = 1
        else:
            for side, t in (('P', P), ('O', O)):
                if x['pos'][t] != y['pos'][t]: diffs[side + ' pos'] = sum(((x['pos'][t] - y['pos'][t]) + (y['pos'][t] - x['pos'][t])).values())
                if x['acts'][t] != y['acts'][t]: diffs[side + ' acts'] = max(1, sum(1 for u, v in zip(x['acts'][t], y['acts'][t]) if u != v) + abs(len(x['acts'][t]) - len(y['acts'][t])))
                if x['soup'][t] != y['soup'][t]: diffs[side + ' soup'] = 1
                if x['spawns'][t] != y['spawns'][t]: diffs[side + ' spawns'] = 1
                if x['deaths'][t] != y['deaths'][t]: diffs[side + ' deaths'] = 1
            if x['bc'][O] != y['bc'][O]: diffs['O bytecode'] = sum(1 for k in set(x['bc'][O]) | set(y['bc'][O]) if x['bc'][O].get(k) != y['bc'][O].get(k))
            if x['block'] != y['block']: diffs['block'] = 1 if collections.Counter(x['block']) == collections.Counter(y['block']) else 2   # 1: order only (getBlock shows it)
        for c in diffs:
            if c in first and first[c] is None: first[c] = r
        per_round.append((r, diffs))
    return first, per_round


def ids_equal_to(a, b):
    n = 0
    for (ra, ia), (rb, ib) in zip(a.ids, b.ids):
        if ia != ib or ra != rb: return ra - 1 if ra == rb else min(ra, rb) - 1
        n = ra
    return None if len(a.ids) == len(b.ids) else n


def pup_log(path, side, r):
    """@pup lines of the P side (from a game log with robot output on) at round r, and totals."""
    if not path or not os.path.exists(path): return [], collections.Counter()
    at, tot = [], collections.Counter()
    pat = re.compile(r'^\[%s:(\w+)#(\d+)@(\d+)\] (@pup .*)' % side)
    for ln in open(path, errors='replace'):
        m = pat.match(ln)
        if not m: continue
        tag = m.group(4).split()[1] if len(m.group(4).split()) > 1 else '?'
        tot[tag] += 1
        if tag == 'ho' and len(m.group(4).split()) >= 4:
            tot['maxlag'] = max(tot['maxlag'], int(m.group(4).split()[2]))
        if int(m.group(3)) == r: at.append(ln.strip())
    return at, tot


def cmd_diff(a):
    P = {'A': 1, 'B': 2}[a.side]; O = 3 - P
    ga, gb = read_raw(a.rec), read_raw(a.new)
    sa, sb = Summary(ga), Summary(gb)
    first, per_round = first_diff(sa, sb, P, O)
    H = ga.header
    ea, eb = ga.end or (0, 0), gb.end or (0, 0)
    same = ea == eb
    print('fixture %s (P=%s %s, O=%s %s, %s seed %d, recorded %s r%d)' % (a.fixture or '-', 'AB'[P - 1], H['pkg'][P], 'AB'[O - 1], gb.header['pkg'][O],
                                                                        H['map'], H['seed'], '?AB'[ea[0]], ea[1]))
    print('result: new %s r%d, recorded %s r%d -- %s' % ('?AB'[eb[0]], eb[1], '?AB'[ea[0]], ea[1], 'SAME' if same else 'DIFFERENT'))
    fr = lambda c: ('r%d' % first[c]) if first[c] else '-'
    print('first divergence  P: pos %s  acts %s  soup %s  spawns %s  deaths %s   O: pos %s  acts %s  soup %s  spawns %s  deaths %s  bytecode %s   block %s   ids equal to %s' % (
        fr('P pos'), fr('P acts'), fr('P soup'), fr('P spawns'), fr('P deaths'), fr('O pos'), fr('O acts'), fr('O soup'), fr('O spawns'), fr('O deaths'),
        fr('O bytecode'), fr('block'), ('r%d' % ids_equal_to(sa, sb)) if ids_equal_to(sa, sb) is not None else 'end'))
    earliest = min([v for v in first.values() if v] or [None]) if any(first.values()) else None
    rc = 0
    if earliest is None and same:
        print('cause: none -- identical')
    else:
        r = earliest if earliest is not None else min(ea[1], eb[1])
        diffs = dict(per_round[r - 1][1]) if earliest is not None else {}
        lines, tot = pup_log(a.log, 'AB'[P - 1], r)
        if a.cutoff is not None and r >= a.cutoff:
            cause = 'after cutoff (r%d >= %d)' % (r, a.cutoff); rc = 1
        elif any(k.startswith('P ') for k in diffs):
            x, y = sa.rounds[r]['acts'][P], sb.rounds[r]['acts'][P]
            detail = next(((u, v) for u, v in zip(x, y) if u != v), (x[len(y)] if len(x) > len(y) else None, y[len(x)] if len(y) > len(x) else None))
            u = detail[0] or detail[1]
            desc = ('%s by %s@(%d,%d)' % (u[1], TYPE[u[0][1]], u[0][3], u[0][4])) if u and u[0] else str(detail)
            cause = 'puppet: %s rec r%d (%s)' % (desc, r, ', '.join('%s %s' % (k, v) for k, v in sorted(diffs.items())))
            rc = 1
        elif 'block' in diffs:
            nsub = sa.rounds[r]['nsub']
            ties = collections.Counter(c for c, _ in sa.rounds[r]['sub'].elements())
            cause = 'r%d block %s (%d submitted, fees %s)' % (r, 'order' if diffs['block'] == 1 else 'content', nsub, dict(ties)); rc = 1
        elif 'O bytecode' in diffs and not any(k.endswith('acts') for k in diffs):
            cause = 'O read something different (r%d, %d robots)' % (r, diffs['O bytecode']); rc = 1
        else:
            cause = 'UNEXPLAINED at r%d: %s' % (r, ', '.join('%s %s' % (k, v) for k, v in sorted(diffs.items()))); rc = 2
        # a block difference earlier than the first P act difference explains an O-side change that follows it
        if rc == 2 and first['block'] and first['block'] <= r: cause += ' (block differs from r%d)' % first['block']; rc = 1
        print('cause: ' + cause)
        for ln in lines[:10]: print('  ' + ln)
        if a.log:
            print('puppet log: drop %d miss %d txlate %d txdrop %d ho %d maxlag %d nofixture %d wrongfixture %d' % (
                tot['drop'], tot['miss'], tot['txlate'], tot['txdrop'], tot['ho'], tot['maxlag'], tot['nofixture'], tot['wrongfixture']))
    if a.log and rc == 0:
        _, tot = pup_log(a.log, 'AB'[P - 1], -1)
        print('puppet log: drop %d miss %d txlate %d txdrop %d ho %d maxlag %d' % (tot['drop'], tot['miss'], tot['txlate'], tot['txdrop'], tot['ho'], tot['maxlag']))
    if a.rounds:
        for r, diffs in per_round:
            p = sum(v for k, v in diffs.items() if k.startswith('P ')); o = sum(v for k, v in diffs.items() if not k.startswith('P '))
            print('r%d P:%s O:%s' % (r, 'ok' if not p else 'diff(%d)' % p, 'ok' if not o else 'diff(%d)' % o))
    if not same and rc == 0: rc = 2
    return rc


# ---------------------------------------------------------------------------------------------------- cells

def cmd_cells(a):
    meta, idx, dat = read_fixture(a.fixture)
    side = meta.get('side')
    if side not in ('A', 'B') or 'map' not in meta or 'seed' not in meta: fail('fixture %s lacks side/map/seed provenance' % a.fixture)
    sideO = 'B' if side == 'A' else 'A'
    for c in a.cutoffs:
        print('%s %s %s %s %d' % (meta['map'], sideO, meta['seed'], os.path.abspath(a.fixture), int(c)))
    return 0


def main(argv=None):
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    sub = ap.add_subparsers(dest='cmd', required=True)
    e = sub.add_parser('extract'); e.add_argument('--raw', required=True); e.add_argument('--replay', required=True)
    e.add_argument('--side', choices=('A', 'B')); e.add_argument('--name'); e.add_argument('--out-dir', default=os.path.join(REPO, 'build', 'puppets'))
    d = sub.add_parser('diff'); d.add_argument('--rec', required=True); d.add_argument('--new', required=True); d.add_argument('--side', required=True, choices=('A', 'B'))
    d.add_argument('--log'); d.add_argument('--cutoff', type=int); d.add_argument('--rounds', action='store_true'); d.add_argument('--fixture')
    c = sub.add_parser('cells'); c.add_argument('fixture'); c.add_argument('cutoffs', nargs='+')
    a = ap.parse_args(argv)
    try:
        return {'extract': cmd_extract, 'diff': cmd_diff, 'cells': cmd_cells}[a.cmd](a)
    except PuppetError as ex:
        print('puppet: ' + str(ex), file=sys.stderr)
        return 3


if __name__ == '__main__':
    sys.exit(main())
