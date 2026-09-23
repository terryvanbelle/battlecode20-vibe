# Training log

Append-only. One entry per attempt: target, trace, pre-registration, diagnostic counters, gate
numbers, verdict, what was learned. Dates are UTC. The closed-directions ledger and the
functional-area map are at the end and are updated in place.

## Phase 0 -- foundation (2026-09-23)

**Read.** battlecode21-vibe (weighted highest: METHOD, LEARNINGS, TRAINING_ALGORITHM, DESIGN,
POSTMORTEM, HANDOFF, BENCHMARK, PROMPTS, tools), battlecode25-vibe (TRAINING_ALGORITHM,
METHODS, OBJECTIVE, MULTI_AGENT, TRAINING_CASES), battlecode26-vibe and battlecode22-vibe
(TRAINING_ALGORITHM, LEARNINGS, RESEARCH filtered of 2020 lines), anicolao/bcenv (README,
VISION, AGENTS, PROMPTS, INITIAL_DESIGN_SKETCH, HISTORICAL_LEARNINGS minus its 2020 section).
The spec (2020.2.0.3) and the engine source (`GameWorld`, `InternalRobot`,
`RobotControllerImpl`, `RobotType`, `GameConstants`, `Transaction`, `ObjectInfo`, `LiveMap`,
`GameMapIO`, `GameMaker`, the schema, `MethodCosts.txt`, `RoboPrintStream`) -> `RULES.md`.

**Engine.** Built from source (`tools/build-engine.sh`; the official artefacts are gone; master
has no engine change after tag v2.0.3). 52 maps staged; `tools/bc20-maps.txt`.

**Benchmarks.** GitHub searched by name, description, creation date and 2020-only API names:
96 candidate repos cloned to `~/projects/vibe/bc20-benchmarks` (`_repos.txt`), none read.
`battlecode-archive` holds no 2020 bots. Compilation in progress (`tools/bench-compile.sh`).

**Instruments.** Ported from 2021 with season substitutions asserted: `lib.sh`, `gauntlet.sh`,
`mirror.sh`, `sprt.py`, `scrim.sh`, `elo.py`/`elolib.py`, `scrim-record.py`, `snapshot.sh`,
`run-match.sh`, `run-dev.sh`, `vm*.sh`, `bench-compile.sh` + `BenchCompiler.java`,
`bench-select.py`, `tier-check.sh`, `unit-tests.sh`. New for 2020: `replaydump/ReplayDump.java`
(31 metrics per team per row, water/flood/pollution/blockchain, threat CSV, board with elevation
bands), `replay-dump.sh`.

**Iteration 0.** `src/bot`: HQ builds up to 6 miners and shoots drones; miners mine adjacent
soup, carry it home at 70, wander otherwise; every other type idle; the turn loop with the
bytecode monitor; `Nav` (water-aware greedy + bug), `MapState` (probed origin, three symmetry
hypotheses pruned by elevation), `Comms` (authenticated 7-int messages). Unit tests: `CommsTest`,
`MapStateTest`, `NavTest` (a wrong expectation in MapStateTest was itself caught by the test).

Smoke game, `bot` (A) vs `examplefuncsplayer` on `maptestsmall`, driver, 13 s wall:

```
RESULT A 257 tiebreak (net worth)   both HQs at elevation 1 flood at r257 (water 1.0 at r256)
A: soup 3242, 6 miners, 464 mines, 0 moves (soup is adjacent on this map), HQ 3297 / miner 5154 bytecodes, over=0
B: soup 67, 4 miners, 9 mines, 443 moves, first enemy-HQ contact r78
```

Determinism: the same pairing twice gives byte-identical replays.

**What the smoke game already says about the season.** An unprotected HQ dies to the flood at
the round its elevation is reached (elevation 1 -> r257; the real maps promise 2-5 -> r256 to
r1210). Everything else is downstream of keeping the HQ dry, which only landscapers can do.

## Iteration 1 -- the foundation (structural; 2026-09-23)

**Target.** Iteration 0 cannot survive the flood: both HQs drown at the round their elevation is
reached (smoke game; `tools/mapdata.csv`: 43 of 52 maps have HQ elevation 3-5, i.e. r677-r1210).
Everything is downstream of keeping the HQ dry.

**Mechanism (pre-registered counters in brackets).** HQ builds 4 miners at once, then more while
rich and the ring is open, hard cap 16 [`@build t=1`]. The HQ's first miner (its first turn is
round 2) is the BUILDER: refinery and design school at Chebyshev 2 from the HQ, then vaporators,
net guns and a fulfillment center as the bank allows [`@build t=2/4/3/8/5`]. Workers mine the
nearest remembered soup with a sticky target, carry it to the nearest refinery or the HQ, and
explore unseen 8x8 sectors otherwise [`@deposit`, `@explore`, `@unreachable`]. The design school
builds 8 landscapers, who each take the nearest free ring tile and raise it forever, digging the
HQ out first if it is being buried [`@seated`, `@wallstat`, `@hqdig`]; landscapers beyond 8
walk to the enemy HQ guess and bury the first enemy building they meet [`@attacker`, `@bury`].
Drones (after a fulfillment center) drop enemy units into water and avoid guns [`@pickup`,
`@drown`]. Every walker steps only onto unflooded tiles and climbs when its tile is about to
flood [`@climb`].

**Diagnostics (driver, `tools/run-dev.sh bot g_iter0 <map>`), six runs to make it fire:**

| run | map | what the counters showed | fix |
|---|---|---|---|
| 1 | ALandDivided | 99 miners built; builder never chosen; 8030 explore picks; 190 caught exceptions with the overrun signature (a legal move rejected) | count built not sensed; builder = birth round 2; do not re-pick on a blocked step; sample the soup scan (it was O(tiles x memory) on 100+ tiles) |
| 2 | WateredDown | builder fires, refinery+school up, 3 landscapers seated and at elevation 24-40 by r400; 0 exceptions; but 29 deposits in 466 rounds and three miners with 0 mines | -- |
| 3 | WateredDown | byte-identical to run 2: the new stall rule never fired because the nearest-soup target changed every turn and reset it | sticky soup target |
| 4 | WateredDown | stall fires 3 times, still 0 mines for three miners: the memory refills from the same unreachable plateau | blacklist a Chebyshev-2 region per stall |
| 5 | Hourglass | **8 landscapers by r200, wall past elevation 40, our HQ survives r677, the enemy HQ drowns: win by HQ destroyed at r682**; 2 vaporators; coverage 13% vs 16.5% for a random walker; miner bytecode max 5.5k | -- |
| 5 | WateredDown | coverage 22.8% vs 38%; miner overrun x15 (periodic tasks coinciding) | sector exploration (nearest unseen 8x8 sector, given up only on stall); bytecode guards on the periodic tasks |
| 6 | WateredDown | coverage 37.5%, 4 ring seats by r400, deposits 40 (from 29), 1 overrun left | -- |

WateredDown is a deliberately soup-poor map (its near soup sits on an elevation-8 plateau); the
random walker still out-mines us there and wins the drowned-HQ tiebreak on unit count (21 miners
to our 15). Noted as the first open question, not fixed here.

**Gate.** `BOT=bot REF=g_iter0 N=64 tools/mirror.sh` on the VM (run `gate1`): **SPRT_ACCEPT 31-1**
(batch 1 15-1, batch 2 16-0). The loss is WateredDown again (drowned-HQ tiebreak on unit count,
the known open question). 12 of 16 first-batch wins were by the enemy HQ drowning while ours
stood; game lengths 466-1210 rounds; 32 games at 6 in parallel took ~12 minutes, i.e. about 2.3
VM-minutes per game against a weak opponent. **Snapshot `g_iter1`.**

**Open after Iteration 1** (candidates, not yet pre-registered): (a) on maps where the wall cannot
be finished before the HQ's flood round, or the map drowns anyway, the tiebreak is unit count, and
cheap miners win it; (b) refineries are placed at the HQ, so far soup is a long walk; (c) soup on
plateaus needs landscapers or drones to reach; (d) one miner bytecode overrun per game remains.

## Block 1 -- g_iter1 against the whole field (2026-09-23, run 20260923-142354-scrim-bot)

65 scrimmages, one per ladder bot, random map and side: **45-20 (69.2%)**. 57 games ended by an
HQ drowning or being buried, 8 by the robot-count tiebreak. Game lengths: 36 under r1000, 15 at
r2750-r3100 (both sides walled). The block took ~50 VM-minutes at 6 games in parallel.

Elo after the block is uninformative (one game per bot: every bot we lost to sits at 1516, every
bot we beat at 1484, us at 1573); the informative fact is the split. The 20 bots that beat us are
all `locked` (0/1), so the study saw wins only, by rule. Block 2 (60 games, three per bot) is
against exactly those 20, to find which of them unlock at 20%.

Own-side reading of the 45 allowed games: our HQ was never buried; median coverage 22% against
their 8%; median r400 worth 3200 against their 600. Nothing degenerate stands out in wins.

## Iteration 2 -- spend the bank before the flood, and drones (2026-09-23)

**Target (own degeneracy, from the 45 allowed games of block 1).** By r900 our median miner count
is 1 and our refinery/vaporators have drowned outside the wall; the fulfillment center was never
built (it waited for a vaporator), so we fielded zero drones all game; the bank sat unspent
(median 1338 soup at r900). Our HQ still outlasts the opponent's in long games because the wall
keeps rising.

**Mechanism.** The builder places the fulfillment center right after the design school at a bank
of 350 [`@build t=5`], then vaporators; the center builds drones whenever the bank exceeds 400, up
to 24 [`@build t=7`]; the school's surplus landscapers need a bank of 500 instead of 650. Building
sites prefer the highest tile on the circle. Drones drop enemy units into water [`@pickup`,
`@drown`] and never park on the ring.

**Diagnostics (driver, `run-dev.sh bot g_iter1 Hourglass`):**

| run | what the counters showed | fix |
|---|---|---|
| 2a | FC r276, 3 drones, 7 drownings -- but the wall race was LOST at r2521: 5 of 8 landscapers died off the ring at r677. Miners fleeing the flood had climbed onto the raised ring tiles (the highest ground) and taken the seats | miners and drones treat ring tiles as forbidden once a refinery exists or a landscaper is on the ring |
| 2b | still 3 seats; HQ drowned at r682. Five landscapers all chose seat (18,21), a natural elevation-99 cliff on the ring, unreachable from 3; a miner already standing on a low ring tile stayed and let the flood in | seats must be within 3 of the HQ's elevation (a cliff is a wall already); a miner steps off the ring when the rule applies |
| 2c | **all 4 reachable seats taken; wall held to r2525 (water 100); FC + 4 drones, 7 enemy units drowned; won the robot-count tiebreak 8-? against g_iter1 at r2525** | -- |

Both seat defects are Iteration 1 defects exposed by a longer game; the g_iter1 snapshot carries
them.

**Gate.** `BOT=bot REF=g_iter1 N=240 tools/mirror.sh` (run `gate2`): **SPRT_INCONCLUSIVE 125-115
(52.1%)** at the cap, below the 53% keep line -> the bundle is not kept. 240 games took ~2 h 10 min
(mirror games run to r2500-r3100). Reading: the seat repairs are real (traced twice) but the early
drones did nothing in the mirror (12 drones, 0 pickups on MoreCowbell in a later diagnostic) and
cost the bank that Iteration 1's twin spends on attackers. Split for Iteration 3: keep the seat
repairs, drop the early drones, and fix the wall race itself.

## Block 2 -- g_iter1 against the 20 bots that beat it (run 20260923-144021-scrim-bot)

60 scrimmages, three per bot: **12-48 (20%)**; 59 ended by an HQ drowning, 1 by tiebreak. Elo
after 125 games: 1291, rank 66 of 66 (we played the strongest 20 three times each). Unlocked at
>= 20%: `yaonam.Robot_1`, `VinayaBhat.team10pdx`, `Tim-gubski.AngryWaffleMaker`,
`TeamSerpentine.noodleBot` (2/3 each), `mhahn2003.nonrush`, `cs454-w20-team3.playbot`,
`cormackikkert.whyPermutator`, `benzyx.seeding` (1/3). Twelve bots stay locked at 0/3.
The block took 7 minutes on the VM: about one CPU-minute a game, five times cheaper than 2021.

**Census of the 24 unlocked games (12-12; noise floor 0.41).** Earliest and strongest at r200,
within-opponent: `landscapers (us-them)` +0.62 (loss median gap 2.0 against 4.5 in wins: the
opponent has more landscapers in the games we lose), `units (us-them)` +0.54, `spawned` +0.50.
At r900 in losses we sit on 3201 soup (2544 in wins), 0 vaporators (drowned) against their 3, 0
pickups against their 3.5, robots 16 against their 17.5. Our HQ is never buried, theirs never
buried: every loss is a drowning.

**The twelve losses split in two.** Six end at the map's flood round (r683-r939): the wall did not
hold. Six are wall races lost at r1211-r3090. Traced `VinayaBhat.team10pdx` on MoreCowbell
(lost r689): six landscapers seated and at elevation 210-232 by r600, but the two ring tiles in
the map corner held **miners**, trapped between the rising wall and the map edge since r400 and
never able to leave; those tiles stayed at 3 and drowned the HQ at r688. This is the seat defect
Iteration 2 repairs (miners forbidden from the ring, and stepping off it before the wall rises).

**Next candidates from this census (not yet pre-registered).** (a) The long-race losses: their
landscaper count at r200 is the top correlate; a second ring of helpers depositing onto the seats
would double the wall's growth (each seat gains 0.5/round from its own landscaper). (b) Vaporators
that survive the flood (theirs: 3 at r900, ours: 0), i.e. sites inside a wider wall or on natural
high ground. (c) Trapped units in general: anything caught between the wall and an edge.

## Iteration 3 -- the wall race (2026-09-23)

**Target (census + trace).** Their landscaper count at r200 is the top correlate of our losses
(+0.62 within-opponent); six of twelve unlocked losses are wall races lost at r1211-r3090. A new
dumper mode `--ring N` prints both rings' eight elevations, minimum and the water level, and made the
mechanism visible in one line: on MoreCowbell at r3000 our ring stood level at 1176 while the
opponent's exposed tiles stood at 1392-1429 **with one tile still at elevation 3** -- enclosed by
the other ring tiles, the HQ and the map edge, it can never flood, so it never needed dirt. We had
spent a quarter of our dirt levelling two such pockets.

**Mechanism.** (1) Seats are exposed ring tiles only (a tile touching some on-map tile outside the
ring). (2) A seated landscaper raises the LOWEST exposed tile among itself and its ring neighbours
(slack 2) instead of always its own [`eq=` in `@wallstat`]. (3) A seat with no outside dirt
borrows from a ring neighbour at least 10 taller [`borrow=`]. (4) Helpers (landscapers 9-16, bank
300) post at distance 2 next to the lowest exposed ring tile, keep themselves just above the coming
water, and feed that tile [`@helper`, `@posted`, `helperDeps=`]. (5) A seat the navigator cannot
reach is blacklisted [`@badseat`]. Plus Iteration 2's seat repairs and high building sites; the
early fulfillment center is reverted to Iteration 1's order.

**Diagnostics** (driver, vs `g_iter1`): 3a MoreCowbell won r689 but helpers 0 (drones ate the
bank; miner bytecode over x116, terrain observation at 6.1k a turn); 3b drones after r400 or bank
800, allocation-free 9-tile observation (miner max 7.5k, 0 over); 3c/3d lost the race at r3057-3089
with rings 1176 level against 1392-1429 exposed; 3e state bug (a helper still holding a seat);
3f MoreCowbell won r689 (their trapped-miner hole), but ALandDivided (a normal map) still lost the
race 1139 against 1367: ten `@badseat` blacklists (a seat blocked for ten turns by a passing unit)
sent landscapers off as attackers, and seats dug from under the helpers' feet, which then re-raised
their own tiles -- a zero-sum loop. 3g (seat stall limit 30; never dig under a friendly unit):
**6 seats + 6 helpers (330-390 deposits each), ring level at 576/1315/1805 at r1000/2000/3000
against 367/867/1367; won at r3090 by drowning their HQ.** Landscaper bytecode max 10003, 4
overruns in 3090 rounds (to watch).

**Gate.** `BOT=bot REF=g_iter1 N=240 tools/mirror.sh` (run `gate3`): **SPRT_ACCEPT 29-3 (90.6%)**
in two batches (13-3, 16-0). **Snapshot `g_iter2`**, taken from the VM's synced copy (the gated
bytes); the local tree additionally carries a behaviour-neutral cache of ring exposure.

## Block 3 -- g_iter1 against the 12 rated just above us (run 20260923-152327-scrim-g_iter1)

40-8 (83%). The "nearest above us" pool, taken while we sat at rank 66 after block 2, was twelve
bots we had beaten in block 1 (rated 1484-1516), so the block re-measured the easy end of the field
and lifted the rating to 1675 (rank 1 of 66) without saying anything new. Lesson for the pool rule:
after a block against the strong end, the next pool must not be drawn from a rating that block just
depressed; use the bots we have the fewest games against among those that beat us.

## Block 4 -- g_iter2 submission (run block4)

60 games against the 20 bots that beat g_iter1 in block 1 (`elo.py --pool 20`). Veto rule: withdraw
if the block's Wilson upper bound falls below g_iter1's 12/60 point estimate on the same field.
**Result: 17/60 (28.3%, Wilson 18.5-40.8%)** against g_iter1's 12/60 (20.0%) on the same field:
the submission stands. 59 of 60 games ended by an HQ drowning. Newly unlocked:
`mvpatel2000.qual` 1/3, `winkelmantanner.tannerplayer` 1/3, `laurenschneider.pdx_team_one` 2/3;
`VinayaBhat.team10pdx` and `Tim-gubski.AngryWaffleMaker` 3/3. Still 0/3: AngusRitossa,
awesomelemonade, IvanGeffner, battlecode20-team4, EmaPajic, poortho, ronniesong0809, rzhan11,
uvafan, wpine215, cormackikkert. Elo 1354 (rank 66) after 233 games: the rating tracks who we
choose to play, so the per-field comparison above is the standing that matters.

**Census (27 unlocked games, 17-10, noise floor 0.38).** Nothing at r200 clears the floor by much
(`cov (us-them)` +0.41, `mines (us-them)` +0.38 within-opponent). The r900 medians say where the
gap is: their worth 4327 against our 2338, their robots 23 against our 12, their vaporators 2 and
net guns 1 against our 0, their pickups 5 against our 0, our miners 0.5 alive. Their economy
survives the flood; ours does not, and our bank (778) sits unspent.

## Ledger (closed directions)

(empty)

## Functional-area map

| area | last attempt | consecutive rejects |
|---|---|---|
| economy | Iteration 1 | 0 |
| flood defence | Iteration 1 | 0 |
| navigation | Iteration 1 | 0 |
| exploration / symmetry | Iteration 1 | 0 |
| drones / combat | -- | 0 |
| communication | -- | 0 |
