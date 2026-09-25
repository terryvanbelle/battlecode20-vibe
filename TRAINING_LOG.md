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

## Iteration 4 -- net guns against the drone swarm (2026-09-23)

**Target (traces of block 4's losses to unlocked bots, `--ring` and `--threat`).** Seven losses read:
- mvpatel2000 on ALandDivided: our ring frozen at 513/383 from r1500 while theirs grew 950 -> 1308;
  at r925 **17 enemy drones** within net-gun range of our HQ and 3 of our 8 seats still manned.
  Drones pluck landscapers off the ring and the wall stops. WaterBot vs winkelmantanner: ring frozen
  at 530 from r2500 with 14-32 drones about. TwoLakeLand vs mvpatel2000: three seats at 660 against
  1120 for the rest, three drones present.
- benzyx on MoreCowbell: lost at r1620 with water at 7.8 and a ring above 385 -- the HQ was buried:
  an enemy landscaper dropped onto an emptied seat (1 adjacent enemy landscaper, 5 drones at r1600).
- Soup and Showerhead: pure wall rate, 1893 against 1371 and 1067 against 654 at r3000/r2750.
- Sheet4 vs laurenschneider: their ring reads -6271 .. -7278 and is dry at r3000: they wall at
  distance 2 and dig the enclosed inner ring as an infinite dirt mine.

**Mechanism.** Every robot that sees an enemy drone posts ENEMY_DRONE to the chain at most once per
40 rounds [`@dronepost`]; the builder makes one net gun right after the school at a bank of 350,
and up to four while a drone was reported in the last 150 rounds (bank 100 above cost), all on the
distance-2 circle where r2 15 covers the whole ring [`@build t=8`]. The HQ and net guns already
shoot the nearest drone [`shots` in the replay].

**Instruments.** The mirror twin has no drones, so the mirror can only be a regression check.
Second arm, pre-registered: `src/arch_drone` = g_iter2 with the early fulfillment center, 30 drones
and landscaper-first pickups. Compare candidate vs arch_drone against g_iter2 vs arch_drone on the
quick set both sides (24 cells each); the candidate must win more, and its games must show net guns
built before r400 and enemy drone deaths (`shot` in the study) in the tens.

**Running the partner before trusting it (METHOD 6b-ii) took six games.** arch_drone v1 built 16
drones that made 4 pickups and never came within net-gun range of the enemy HQ: our drone code
flees anything that shoots, and the twin inherited it. v2 (no fear, hunt the ring): still none at
the ring -- the enemy-HQ guess degenerated to the map centre. v3 (prune a hypothesis on arriving
at an empty guess): 34,000 prunes, sym cycling 7->0->7, because the guess WAS the centre: late-born
drones had no `home` (born beside a fulfillment center out of the HQ's sensor range) and the HQ
posted HQ_LOC once, at round 2. v4/v5: home and MAP_ORIGIN re-posted every 100 rounds and read every
round while unknown (a real defect of the bot, now fixed in the candidate too): 12 pickups and 12
drownings, all 17 drones eventually shot by the incumbent's HQ, still no drone within r2 15 of the
enemy HQ at any sampled round. The partner pressures miners, not the wall; the real swarms hover
outside gun range and pounce. Accepted as a weaker-than-real arm and pre-registered as such: the
A/B reads `drowned` and `shot`, not the win column alone.

**Runs.** Mirror regression `gate4` (bot vs g_iter2, SPRT); A/B arms `ab-inc` (g_iter2 vs
arch_drone) and `ab-cand` (bot vs arch_drone), quick set both sides.

**Verdict: `gate4` SPRT_REJECT 24-40 (37.5%) after four batches.** The unconditional first net gun
(250 soup at ~r190) costs almost two landscapers at the moment the wall is being manned, and the
wall race is that sensitive: in a mirror without drones the gun is pure cost. The A/B arm did not
rescue it either (candidate losing long games to arch_drone). REJECTED as a bundle. Kept for the
next candidate at zero mirror cost: guns only while a drone has been reported in the last 150
rounds (a defence the mirror cannot price; its value is read on the ladder), and the home/origin
re-posting, which is a plain defect repair.

**Found while diagnosing (iter4b -> iter4c).** The ring was uneven again -- three north tiles at
334 against 520 at r1000 -- because helpers posted at distance 2 stood on the only tiles the
edge seats could dig from, and seats were forbidden to dig under friends. Helpers now post on the
four distance-2 corners first and the four edge midpoints second (never the off-centre tiles),
and a seat digs under a friend before it borrows. Ring at r1000: **459-464 level** (min +125,
+37%); r2000: 981-985 against 720-1195 before.

**A/B arms:** `ab-inc` g_iter2 vs arch_drone 15/24, `ab-cand` (Iteration 4) vs arch_drone 13/24.
The gun bought nothing even against the drone partner. Reject confirmed on both instruments.

## Iteration 5 -- helper posts, alert-only guns, comms repairs (2026-09-23)

Candidate = g_iter2 + helper posts on the distance-2 corners/midpoints + seats dig under friends
before borrowing + net guns only within 150 rounds of a drone report + HQ_LOC/MAP_ORIGIN re-posted
every 100 rounds and read while unknown. Diagnostic vs g_iter2 on ALandDivided: ring 558 / 1297 /
1787 against 506 / 1242 / 1688 at r1000/2000/3000, won r3131. Gate `gate5` (mirror vs g_iter2,
SPRT). Falsifier: below 53% at the cap.

**Gate 5: REJECT, 8-24 (25%)** -- batch 1 7-9, batch 2 1-15. Every loss but one was the wall race
(HQ drowned r2870-3110); at r2000 the candidate's ring minimum trailed g_iter2's by 300-400
(TwoLakeLand 995 vs 1331, CentralLake 964 vs 1350) and its digs by a quarter (12.3k vs 16.4k).
Logged re-run on TwoLakeLand (edge HQ, five exposed ring tiles): at r600 five seats and five posted
helpers worked; **six of the sixteen landscapers had 0 digs**, all helpers walking to posts they
never reached -- an edge HQ has three corner/midpoint posts, and a helper whose post was taken or
unreachable re-picked forever (22 `@helper post` for 14 `@posted`, 7 `@badseat`). The A/B
diagnostic hid it: ALandDivided's HQ has room for all eight posts. Lesson: a role with a fixed
station list needs a stall exit (LEARNINGS). Iteration 5 reverted; the helper idea itself is
unfalsified and lives on in Iteration 6 with a stall exit and any distance-3 tile allowed.

## Iteration 6 -- the citadel (big swing, 2026-09-23)

**Design.** The wall moves to Chebyshev 2 (16 tiles, `C.RING`); the eight tiles inside are a pocket
that can never flood once the ring is sealed, so the design school, the fulfillment center, three
vaporators and a net gun live there and survive the r700 flood that drowns every enemy building.
The refinery stays outside (distance 3). One pocket tile F (next to the school) is left free as the
spawn tile; the ring tile beyond it, G, gets no seat, so a drone can hover on G, lift a landscaper
born on F and drop it on the lowest free exposed ring tile, or on a dry distance-3 post beside the
lowest ring tile (the ferry). Before `WALL_START` (r400) seats only level the ring to HQ+3 so units
born inside can still climb out; the HQ stops spawning miners at r400; the center comes before the
vaporators and builds two ferry drones at once. Helpers stand on any distance-3 tile (corners and
midpoints preferred) and leave for the attack when their post stalls (the Iteration 5 lesson).

**Why it could win.** After r700 an ordinary wall has the landscapers it has; the citadel's school
keeps spawning (2 soup/turn per vaporator plus whatever the outside refinery banked), the ferry puts
each new landscaper where the wall is lowest, and the center's drones survive to pluck enemy seats
once their net guns have drowned. **Why it could lose.** Sixteen tiles halve the per-tile rate of the
same landscapers, so until the ferry adds bodies the wall is lower; the pocket layout is fragile (a
waiting landscaper on the wrong tile blocks a building; a seat on G blocks the lift).

**Pre-registered counters** (diagnostics vs g_iter2 on ALandDivided, TwoLakeLand, MoreCowbell):
`@ferry`/`@lift` > 0 by r800; ring minimum (`--ring --ringd 2`) above water at r1000 and above
g_iter2's minimum at r2500; `idle` per landscaper at r600 below 100 for seats; buildings alive at
r1000 (metrics V, DS, FC > 0). Gate: SPRT mirror vs g_iter2. Falsifier: below 53% at the cap.

**Outcome: closed (ledger).** Ten 24-game quick-set sweeps against g_iter2 (`cit1`..`cit10`, both
sides of the 12 maps) scored 9, 5, 6, 7, 8, 6, 4, 5, 4, 6 of 24 as the mechanisms were made to
work one by one: the ferry (24 lifts on ALandDivided once the spawn tile F, the gate G and a
parking tile existed), the seal (coverage-based seats and no pre-dig moat took the early deaths
from r257-935 to r2700+ on most maps), the raid (drones reach the enemy ring only after the
symmetry image is scouted from outside gun range). What did not move: the wall race. Sixteen
tiles halve the per-tile rate of the same landscapers (ALandDivided r3000: 1199 against
g_iter2's 1400-1600), and the post-flood economy the design relies on is three vaporators, 6 soup
a round -- one drone per 25 rounds, which never adds up to the wave of 16 that the enemy HQ's
one shot a round requires (10 alive at r2500, 25 built). The one win by raid (ALandDivided r2820,
12 pickups) came from an early drone cluster that later versions could not repeat. The code is
kept in `src/arch_citadel`; the transferable pieces (chain-shared origin and enemy HQ, hypothesis
pruning on sight, a stall exit for helper posts, the seats' moat, the spawn-tile trap) went into
LEARNINGS and Iteration 7.

## Iteration 7 -- incumbent plus the citadel's transferable pieces (2026-09-23)

Candidate = g_iter2 + (a) the prober posts the map origin once and the sighter the enemy HQ
once; the HQ re-posts HQ_LOC, MAP_ORIGIN and ENEMY_HQ every 100 rounds; a robot that can see the
current enemy-HQ guess and finds no HQ prunes that hypothesis; (b) a helper whose post stalls
becomes an attacker instead of walking forever. Expected effect: attackers and drones reach the
real enemy HQ (fewer wasted units on the wrong image), no idle helpers. Diagnostics vs g_iter2 on
TwoLakeLand (edge HQ) and ALandDivided: counters `@prune`, `@sight`, `@badpost`; helper `digs=0`
count at r600 must be 0. Gate `gate7`: SPRT mirror vs g_iter2. Falsifier: below 53% at the cap.

**Gate 7: REJECT, 21-43 (33%)** -- batches 6-10, 5-11, 6-10, 4-12. In the losses our wall grew at
half the incumbent's rate (CentralLake r1000: 235 vs 557; AMaze 246 vs 422) with 3-6 landscapers
within r2 8 of the HQ against the incumbent's 8 seats plus helpers: the stall exit sent every helper
whose walk to its post paused for 30 rounds off to attack, and the wall lost its second ring. The
chain sharing (fee 1 a post) is not implicated but is untested on its own.

## Iteration 8 -- the stall exit done right (2026-09-23)

Candidate = Iteration 7 with the helper's stall exit replaced: a post not reached in 30 rounds is
struck off (up to eight), another is picked, and only with none left does the helper attack.
Diagnostic vs g_iter2 on CentralLake and TwoLakeLand: `@badpost` fires, helpers within r2 8 at
r1000 equal the incumbent's. Gate `gate8`: SPRT mirror vs g_iter2. Falsifier: below 53% at the cap.

**Gate 8: ACCEPT, 39-9 (81%)** -- batches 14-2, 11-5, 14-2. Snapshot `src/g_iter3` (taken from the
VM's gated copy); submission block `block8` (`BOT=g_iter3 N=60 tools/scrim.sh`). The accepted
change is Iteration 7's chain sharing and pruning plus the struck-off posts; the 81% against
g_iter2 says the incumbent had been losing helpers to unreachable posts all along (the Iteration 5
diagnosis, now fixed the right way).

## Iteration 9 -- the late drone raid (2026-09-23, prepared while gate 8 runs)

Trace: both mhahn2003 losses in block 6 (now reviewable, 3/3 in block 7) were wall races lost by
190 (Soup, 1046 vs 1239 at r3000) and 60 (RealArt, 1190 vs 1251) while our 5 drones patrolled all
game and 2,000+ soup sat unspent. After the flood only the enemy HQ shoots, one drone a round.
Candidate = Iteration 8 + drones that from r2300 (`C.RAID_ROUND`) scout the enemy HQ guess from
outside gun range (pruning a wrong image), gather 7 tiles from it and, three together, charge to
lift seats off the ring and drop them in the water. Three seats fewer for the last 600 rounds is
about 200 of wall height: the margin of both traced losses.

Diagnostics (RealArt vs g_iter2, six reruns): the first versions lost every drone -- fleeing at r2 24
while the HQ shoots to 15 left them oscillating at the sense edge; a diagonal approach step from
25 lands at 13; and a drone that arrives in range dies before its pickup cooldown clears, because
the HQ acts first (seven shot at r2 9, no lift). Final form: scout the guess from outside range,
lift landscapers that have a perch tile outside r2 15 (corner helpers at Chebyshev 2 from a tile at
18), and from `RAID_LATEST` (2450) gamble on the seats in pairs. Result: 4 lifts by 7 drones, the
enemy's landscapers at the wall 10 -> 6 (r2600) -> 2 (r2800), their ring 70 lower at r3000; Soup
had no drones at all (no center, poor map), no effect and no cost. Gate `gate9` vs `REF=g_iter3`.
Falsifier: below 53% at the cap.

**Gate 9: INCONCLUSIVE 135-105 (56.2%) at the 240 cap** -- batches 10-6, 7-9, 6-10, 9-7, 10-6, 7-9,
8-8, 12-4, ..., 10-6; LLR +1.73 against bounds of 2.94. Above the 53% keep line: **provisional**,
snapshot `src/g_iter4` (from the VM's gated copy); submission block `block10` (`BOT=g_iter4 N=60`).
The raid fires only in games where a center and drones exist before r700, which is why the mirror
effect is a few points rather than the diagnostic's 70 of wall height.

## Iteration 10 -- home-defence drones (2026-09-23, prepared while gate 9 runs)

Traces, the 24 reviewable g_iter3 losses of blocks 8-9: five games (battlecode20-team4 x4 and
others) end at r1550-1580 when 11-22 enemy drones lift our seats and drop 7-11 landscapers on the
freed ring tiles, burying the HQ in 25 rounds; in another two enemy landscapers squat on our ring
tiles all game and the wall's minimum stays at 560; in a third (Swirl, corner HQ) one landscaper is
left of 17 because attackers wander into the flood. Meanwhile our 5-19 drones patrol toward the
enemy and die. Candidate = Iteration 9 + drones that before the raid patrol a Chebyshev-4 box
around the HQ and lift any enemy landscaper or miner within 8 of it (landscapers first, those on
the ring or beside the HQ first), dropping it in water. Diagnostics vs g_iter3 on IsThisProcedural
and CentralLake: `@pickup home=true` > 0 when g_iter3's attackers arrive; drones alive at r2000
higher than g_iter3's own.

Diagnostics found a second, larger defect on the way: on IsThisProcedural the A side never built a
school (soup 5,134 idle at r700, no seats, drowned r717) because the builder's nearest circle tile
was a 99-high cliff it walked at for 600 rounds. Added to the candidate: the builder skips circle
tiles it cannot climb or that stalled it, and widens the circle after 25 turns with nothing
buildable. Rerun: school at r110, ring 807 at r2500, won r2933. The mirror game had no intruders,
so the defence itself fired nowhere (0 pickups); the gate carries both pieces. Gate `gate10` vs
`REF=g_iter4`. Falsifier: below 53%.

**Gate 10 (relaunch): REJECT at 120-120 (50.0%)** -- the LLR crossed the lower bound on the last batch
(batches 6-10, 3-13 in the aborted run; then 10-6, ..., 7-9, 5-11). Home defence plus the builder
fixes is neutral in the mirror: the mirror has no drone swarms to defend against, and half the
drones hunting restored what the first attempt lost. The defence piece is dropped; the builder
pieces (cliff and stall exits, circle widening) are carried into Iteration 11, where they were
diagnosed (IsThisProcedural r717 -> r2933).

## Iteration 11 -- spawn room for the school (2026-09-23, prepared while gate 10 runs)

Trace: in both Iteration 10 mirror diagnostics (FourLakeLand, MtDoom vs g_iter4) our school
stopped spawning at r400 with 3,500-5,000 soup idle: its neighbours were 99-high cliffs, the
refinery, ring seats and water, so no adjacent tile within 3 of its elevation was free. The
incumbent's school, placed by the same rule with different tie-breaks, kept spawning to r700
(17-20 landscapers to our 14) and won the wall race by 450. Candidate = Iteration 10 + the builder
scores a school or center site by the number of tiles it could spawn onto (dry, level within 3,
not the ring or HQ, no building), 300 per tile, ahead of height and centrality. Diagnostic: the
same two maps, `@build t=6` counts per 100 rounds on both sides comparable; the wall gap closes.
Result: MtDoom flipped to a win (our school now spawns to r900, 17 landscapers to 16, ring 1860 vs
1839); FourLakeLand unchanged (a different site, still boxed by lakes and cliffs by r400, 14 vs 17).
Composition: g_iter4 + the builder's cliff/stall exits and circle widening + spawn-room scoring; the
drones are g_iter4's. Gate `gate11` vs `REF=g_iter4`, launched 23:05. Falsifier: below 53%.

**Gate 11: INCONCLUSIVE 125-115 (52.1%) at the cap** -- below the 53% keep line: not kept, `src/bot`
back to g_iter4. The builder fixes fire only on maps with cliffs or lakes at the circle (a handful of
the random draw), an effect a 240-game mirror cannot resolve; nothing suggests harm. They stay
available to ride along with a later candidate.

## Iteration 12 -- where the seats' turns go (2026-09-23, late)

Mirror games end r3050-3180 with margins of 10-50 rounds, so the wall's growth rate is the lever
the gate can see. Earlier blocks measured 0.35-0.43 deposits per landscaper-round against the 0.5
ceiling (dig, deposit, dig, ...). Step one is an idle census: every landscaper counts its ready
turns that ended without dig or deposit, by role and by reason (no dig source, no target under the
slack rule, waiting on borrow, walking, blocked). One diagnostic, then the top reason becomes the
candidate.

Census (Prison and CentralLake vs g_iter4, r1500 and r2500): seats have digs = deposits to within
one and **no idle turns** (1,134 digs in 2,350 seated rounds is the dig-deposit ceiling); helpers
likewise (one boxed in by water had 464 no-dig turns). The 0.35-0.43 figure averaged in attackers
and walkers. So the intake of the ring is capped at 0.5 per landscaper adjacent to it: 8 seats +
8 helpers = 8 dirt a round over 8 tiles. The only way up is more bodies next to the ring, and the
soup for them sits idle (2,000-5,000 by r2500 in most games).

Candidate = g_iter4 + a three-tier wall: `WALL_HELPERS` 16 (every distance-2 tile), 8 feeders at
distance 3 (`FEEDERS`, after the helpers, bank 300) that keep their own tile above the water and
pile dirt onto the lowest adjacent distance-2 tile, and seats that may dig under a helper while
its tile stays `FEED_MARGIN` (4) above the helper's own water margin -- so the feeders' dirt is
what the seats dig and the helpers never have to re-raise. Ceiling: (8 + 16) x 0.5 = 12 dirt a
round, +50%. Diagnostic: Prison and CentralLake vs g_iter4; feeders posted, `fed` counts, ring at
r2500 above the incumbent's by 20% or more. Result: CentralLake won r3115 with the ring at 2041 vs
1574 at r3000 (+30%); Prison won r3117 at 1634 vs 1578 (three tiles lagged at 1418; 354 struck-off
posts, so the helper churn on that map is a cost). No feeder was ever posted -- the 16 helper posts
absorb every surplus landscaper first -- so the measured effect is the wider helper ring plus
digging under helpers. Gate `gate12` vs `REF=g_iter4`, launched 23:55. Falsifier: below 53%.

Sparring baseline (run `spar1`, 2026-09-23): g_iter4 beats `arch_drone` 17/24 on the quick set.

**Gate 12: REJECT 42-54 (44%)** after six batches. In the losses both walls are within 1-3% (1367 vs
1405, 1813 vs 1858) with the same landscaper count at the wall (8-17 both): the extra posts are
never filled, because the soup that could fill them arrives after r700 when the school has drowned,
and the wider ring only adds post churn (354 struck-off posts on Prison). Reverted.

## Iteration 13 -- the second school (2026-09-24)

The lever the census left: the bank at r700 is 700-3,000 soup with nothing to spend it on, while
the wall's intake is 0.5 per body next to the ring. A school alive after the flood turns that bank
into 5-20 helpers. Candidate = g_iter4 + (a) from r600 the builder miner parks on the highest dry
tile within Chebyshev 2-4 of the HQ (it must survive the flood); (b) from `SECOND_SCHOOL_ROUND`
(720), with no school of ours in sight and 150 soup, it builds a design school on an adjacent dry
tile that will stay above the water for 150 rounds; the school's landscapers walk to free
distance-2 posts (dry at elevation 4-5 until r930-1210) and keep them dry themselves. Diagnostic:
Prison and CentralLake vs g_iter4 -- `@build t=4` after r700, landscapers at the wall at r1500
above the incumbent's, ring at r2500 higher.

Diagnostics: the first run never perched (the perch search only looked at tiles in sight, and the
builder was mining far away; it drowned on CentralLake) and yet CentralLake was a rout, 2301 vs
1442 -- the parked builder no longer sank the late soup into vaporators and a center that drown at
r700, and the school turned it into landscapers instead. Two fixes: the builder walks home when no
perch is in sight, and no vaporator is built after r300 (`VAPORATOR_LAST_ROUND`: 2 soup a round
on 500 cannot pay back before the flood). Rerun: Prison flipped to a win (perched r619 at 5; second
school r966 with 2,205 soup, 13 landscapers r900-1000; ring 1672 vs 1590), CentralLake 2380 vs
1675. Gate `gate13` vs `REF=g_iter4`, launched 00:45. Falsifier: below 53%.

Gate 13 at 36-44 after five batches (00:45-01:20). Six losses fetched: on poor maps neither side
gets a second school and our side has one or two landscapers fewer (the builder stops mining at
r600, 13 vs 15 spawned); on DisproportionatelySmallGap the second school built 16 more than the
free posts (31 landscapers at r1000, 15 at r1500 -- the surplus went attacking into the flood)
and the wall still lost 1825 vs 1911. Two refinements under test before a relaunch: park at r660,
and a school born after r720 builds at most `SECOND_SCHOOL_MAX` (8).

**Gate 13: REJECT 51-61 (45.5%)** after seven batches. The refined candidate (park at r660, a
post-flood school builds at most 8) won its diagnostics -- DidAMonkeyMakeThis, a poor map where the
first version had lost, now 677 vs 545; Prison 1666 vs 1589 -- and goes to `gate14` vs g_iter4
(launched 01:40). Falsifier: below 53%. Note for later: on Prison the first school survived to
r1210 and 2,460 soup sat idle at r1000 because `LANDSCAPERS_MAX` (24) was reached; more bodies
need more posts, which is the Iteration 12 problem from the other side.

**Gate 14: REJECT 103-105 (49.5%)** after thirteen batches -- neutral. The second school is closed
(ledger): where it fires the bank buys landscapers for posts that do not exist, and where it does
not fire the parked builder costs a miner's last hundred rounds. `src/bot` back to g_iter4.

## Iteration 14 -- the helpers' last stand (2026-09-24)

From about r2600 the water rises faster than 0.5 a round, so a helper that keeps its own tile at
water+2 spends every deposit on a tile that floods anyway (Prison: 24 landscapers at r1000, 12 at
r1500, 5 at r2500). Candidate = g_iter4 + from `LAST_STAND_ROUND` (2600) helpers stop raising
their own tile and put every deposit on the ring until they drown. Eight helpers x ~100 deposits
is ~100 of wall height at the moment mirror games are decided by 10-50. Diagnostic: Prison and
CentralLake vs g_iter4 -- helper deposits onto the ring r2500-3000 above the incumbent's, ring at
r3000 higher. Result: on Prison our ring grew 108 from r2500 to r2700 against the incumbent's 61,
and at r2900 the unstuck tiles led 1631 vs 1562 (one seat tile stuck at 1615 lost the game by 7);
helpers on both sides drown by r2700 (the water outruns them from r2550). CentralLake is not
informative: side A wins it by 900 whatever the candidate. Gate `gate15` vs `REF=g_iter4`,
launched 02:35. Falsifier: below 53%.

**Gate 15: REJECT 57-71 (44.5%)** after eight batches. Helpers that stop raising their tile at r2600
drown within a few rounds; the incumbent's keep theirs above water to about r2700 and, it turns
out, still feed the ring in between (the self-raise only fires when the tile is below the margin).
Ledger. `src/bot` back to g_iter4.

**Five rejections in a row (Iterations 10-14).** Every one moved a mirror diagnostic and none moved
the mirror gate. The wall race between two copies of the same economy is a coin flip decided by
map and side; the mirror cannot see the things the ladder losses are made of (schools alive after
the flood, drone swarms, rushes). Next: an external-facing candidate measured against external
evidence -- the sparring archetypes and the challenge pool itself -- as TRAINING_ALGORITHM allows.

Probe (Hourglass, CowFarm mirrors): the ring tiles that never rise are cliffs whose ring neighbours
are cliffs too, or lake tiles no seat borders -- no seat is adjacent to feed them. Not a bug in
the feeding rule; Hourglass ends at r2525 for both sides on its 100-high tile and goes to the
tiebreak. Archetype note: `arch_drone` never reaches our ring (its 7 wins in `spar1` were wall races
and Hourglass), so it is no swarm arm; a real one would need the raid drones' wave logic.

## Iteration 15 -- a fifth early miner (2026-09-24)

An economy parameter the mirror can price: `MINERS_EARLY` 4 -> 5. Diagnostic: Prison, Soup,
TwoLakeLand vs g_iter4 -- soup and landscapers at r400/r700 above the incumbent's, ring at r2500.
Gate `gate16` vs `REF=g_iter3` (g_iter4 withdrawn meanwhile). Falsifier: below 53%.

## Block 12 -- g_iter4 against the challenge pool (run block12, 2026-09-24)

48 games: **9/48 (19%)**. `mvpatel2000.qual` 2/3, `wpine215` 2/4, `IvanGeffner` 2/4; cumulative for
g_iter4 34/156 (22%). The provisional raid has not paid on the ladder; if gate 13 accepts, its
snapshot supersedes it anyway.

## Block 5 -- g_iter2 against the challenge pool (run block5, 2026-09-23)

48 games, `elo.py --challenge 20` (bots that beat us at least half the time, fewest games first):
**8/48 (16.7%)**. `cs454-w20-team3.playbot` 3/3, `laurenschneider` 2/3, `mhahn2003` 2/3,
`poortho.stable_seeding_bot` 1/3 (first win, unlocked); 0/3 against AngusRitossa, EmaPajic,
IvanGeffner, awesomelemonade, battlecode20-team4, benzyx, cormackikkert, mvpatel2000, rzhan11,
uvafan, winkelmantanner. Cumulative for g_iter2 on the strong field: 25/108 (23%).

## Block 6 -- g_iter2 against the challenge pool (run block6, 2026-09-23)

48 games, challenge pool of 16: **12/48 (25%)**. `laurenschneider` 2/3, `cormackikkert` 2/3; first
wins against `IvanGeffner.finalbota`, `AngusRitossa.newbot`, `battlecode20-team4.finalbota`,
`rzhan11.quals_bot`, `uvafan.v14_final_bot`, `wpine215.stardustv2`, `ronniesong0809.finalbota`
(1/3 each); 0/3 against awesomelemonade, EmaPajic, mvpatel2000, poortho, mhahn2003, benzyx.
Cumulative for g_iter2 on the strong field: 37/156 (24%). Elo 1372, rank 66 of 66.

## Block 7 -- g_iter2 against the challenge pool (run block7, 2026-09-23)

48 games, challenge pool of 16: **10/48 (20.8%)**. `mhahn2003.nonrush` 3/3 (unlocked for review),
`wpine215.stardustv2` 2/3; 1/3 against winkelmantanner, rzhan11, ronniesong0809, poortho, benzyx;
0/3 against the other nine. Cumulative for g_iter2 on the strong field: 47/204 (23%). Elo 1390,
rank 66 of 66 (377 rated games).

## Block 8 -- g_iter3 submission block (run block8, 2026-09-23)

60 games, challenge pool of 16: **16/60 (26.7%)**. `laurenschneider` 3/4, `benzyx.seeding` 3/4 (first
wins, unlocked), `mvpatel2000.qual` 2/4, `battlecode20-team4` 2/4; 1/4 against wpine215, poortho,
IvanGeffner, EmaPajic; 0/4 against rzhan11, ronniesong0809, cormackikkert, awesomelemonade,
AngusRitossa. g_iter2's cumulative was 47/204 (23%). Elo 1376 (K=32 drifts with the pool's own
games), rank 66 of 66.

## Block 9 -- g_iter3 against the challenge pool (run block9, 2026-09-23)

48 games: **12/48 (25%)**. `mhahn2003.nonrush` 3/3, `benzyx.seeding` 3/3; 1/3 against wpine215,
poortho, mvpatel2000, cormackikkert (first win), battlecode20-team4, awesomelemonade (first win);
0/3 against the other eight. Cumulative for g_iter3 on the strong field: 28/108 (26%) against
g_iter2's 23%. Elo 1358 (the pool's own games keep drifting our K=32 rating; the win rates are the
measure). Gate `gate9` (Iteration 9, the late raid, vs g_iter3) launched at 20:45.

**Gate 10, first attempt: aborted at 9-23 (28%)** after two batches -- a second launch of the same gate
(a waiter and a manual start both fired) re-synced the tools under the running one and both died.
The 32 games are informative anyway: every loss a wall race decided by a few tiles at r3050-3180,
and in each our eight drones made **zero** pickups all game while the incumbent's made 1-5,
killing miners and landscapers before the flood. Home defence forfeited that attrition. Fix: the
drones split by id parity, half defend, half hunt as before. Relaunch as `gate10` after the
diagnostic (FourLakeLand, MtDoom vs g_iter4: pickups on both sides comparable).

## Block 10 -- g_iter4 submission block (run block10, 2026-09-23)

60 games: **15/60 (25%)**. `wpine215.stardustv2` 3/4, `winkelmantanner` 2/4, `poortho` 2/4,
`cormackikkert` 2/4, `benzyx` 2/4; 1/4 against ronniesong0809, laurenschneider, battlecode20-team4,
AngusRitossa (first win); 0/4 against uvafan, rzhan11, mvpatel2000, awesomelemonade, IvanGeffner,
EmaPajic. Elo 1432, **rank 60 of 66** (first move off the bottom). g_iter3 was 28/108 (26%).

## Block 11 -- g_iter4 against the challenge pool (run block11, 2026-09-23)

48 games: **10/48 (21%)**. `wpine215` 2/3, `poortho` 2/3; 1/3-1/4 against benzyx, battlecode20-team4,
IvanGeffner, winkelmantanner, rzhan11, cormackikkert; 0 against the rest. Cumulative for g_iter4:
25/108 (23%), no better than g_iter3's 26% -- the provisional raid earns nothing measurable on the
ladder so far (it needs a center before r700, which the strong maps rarely allow). Elo 1389, rank 65.

Gate 10 relaunched at 22:25 with the parity split and the defenders on the 5-7 annulus (the mirror
diagnostics on FourLakeLand and MtDoom were single games decided by building placement luck: our
school boxed in by 99-cliffs, the refinery and water stopped spawning at r400 on both runs).

**Gate 16: REJECT 56-72 (43.8%)** after eight batches (vs g_iter3). One constant changed and the
mirror says 44%. Six rejections in a row, four of them at 44-46%, is either a run of genuinely bad
ideas or a harness that is not fair to the `bot` package. **Control run `control1`** (04:50):
`src/bot` made an exact copy of g_iter3, mirror vs g_iter3, 96 games. Expected 50%; a result
outside 40-60% means the gate has been measuring the harness, and every verdict since gate 10
is suspect. **Result: 49-47 after 96 games** (6-10, 10-6, 8-8, 8-8, then 17-15). The harness is
fair; the six rejections stand. (The engine gives the silenced side a no-op print stream and the
other a stream into the replay; whatever that costs, it does not show at 96 games.)

## Archetype `arch_swarm` (2026-09-24)

The field's burial swarm, built for the second arm the algorithm asks of a defence candidate:
g_iter3 with the center right after the school, thirty drones on a 100 reserve, and from r1200
the citadel's wave raid (scout the HQ guess from outside r2 15, gather eight at Chebyshev 7,
charge together or at r1500, lift the nearest landscaper, drop it in the water). Baseline `spar2`:
g_iter3 vs arch_swarm on the quick set: **12/24**, every game a wall race to r2900-3190 -- the
waves lift too few seats to matter (the pair gamble again) and nothing gets buried. The field's
swarm does something more: it carries its own landscapers onto the freed ring tiles and buries the
HQ in 25 rounds. The archetype needs that ferry before it is an arm.

Ferry added (drones with nothing left to lift fetch a helper from home and drop it beside the
enemy HQ; a landscaper that finds itself within 2 of the enemy HQ buries). Diagnostics vs g_iter3:
Prison, the swarm won r3028 -- six seats lifted at r1200-1300 (13 -> 7 at the wall), one helper
delivered, no burial; CentralLake lost with 0 lifts and 8 carriers shot. It lifts seats the way the
field does at r1550; it does not yet bury. Good enough for an arm on seat loss.

## Iteration 16 -- the late guns (2026-09-24)

Every net gun drowns at r700 and the HQ alone shoots one drone a round; blocks 8-9 lost five
games to swarms that lift the seats and bury the HQ at r1550-2100. Candidate = g_iter3 + the
builder parks on the highest dry tile within 4 of the HQ from r660 and from r900, whenever no net
gun of ours is in sight and the bank allows, builds one on an adjacent tile that will stay above
the water for 300 rounds (up to six over the game). Two arms: mirror vs g_iter3 (expect neutral or
a small gain from shooting its hunting drones) and `arch_swarm` (baseline 12/24; expect more).
Diagnostics: Prison and CentralLake vs arch_swarm -- guns built after r900, our shots > 0, seats
at the wall after r1300 above the baseline's 7. Falsifier: mirror below 47% or the arm no better.

## Blocks 13-14 -- g_iter4 against the challenge pool (2026-09-24)

Block 13 (24 games) 6/24; block 14 (48 games) **6/48 (12.5%)**. Cumulative for g_iter4: 46/228
(20.2%, Wilson 95% 15.4-25.6%) against g_iter3's 28/108 (25.9%, 18.7-34.8%). **Withdrawn**: the
rule (TRAINING_ALGORITHM 4.5) withdraws a build whose block's Wilson upper bound falls below the
previous submission's point estimate, and 25.6 < 25.9. `src/g_iter3` is the submission and the
incumbent again; the late raid goes to the ledger as provisional-then-withdrawn. Gate 16 was
restarted against `REF=g_iter3` with Iteration 15 rebased on g_iter3 (04:05).

## Block 15 -- g_iter3 against the challenge pool (run block15, 2026-09-24)

48 games: **6/48 (12.5%)**; cumulative for g_iter3 34/156 (21.8%). The challenge pool now holds
only bots that beat us at least half the time, and the rotation keeps tightening it; both builds
drift down together. Elo 1263, rank 66.

Iteration 16 first diagnostics (vs arch_swarm, Prison and CentralLake): both won but no gun was ever
built -- the builder perched on its own tile (no log) and no adjacent tile stood 300 rounds above
the water (`GUN_LIFE`); Prison has nothing 5 high near the HQ. `GUN_LIFE` 60: a gun standing
through one wave has earned its 250, and the builder rebuilds when it drowns.

Second diagnostics (`GUN_LIFE` 60): still no gun. The builder's perch is a distance-2 tile at 5;
every neighbour is a ring tile (excluded), another helper's tile (occupied) or a distance-3 tile at
3-4 that the water reaches within 60 rounds. After the flood the only dry ground near the HQ is
the ring and the helpers' own tiles, and a gun on either costs a wall body. **Closed** (ledger):
post-flood guns need raised ground the ring-1 design does not have. `src/bot` back to g_iter3.

## What the field does that we do not (2026-09-24, from the blocks 8-9 traces)

battlecode20-team4 at r1000: 33 miners, 44 landscapers, 44 drones, **44 vaporators**, 5 schools,
5 centers -- 88 soup a round from vaporators alone, all of it alive at r1000 and r1500, so its
buildings stand on ground the water never reaches. laurenschneider and cormackikkert show the same
shape at smaller scale (V 6-18, DS 1-3, NG 1-5 at r2000). The pattern is a **raised base**: the
ground under the buildings is lifted before the flood, the vaporators pay for themselves in 250
rounds, and the economy runs to the end. Our ring-1 design raises only the ring; everything we own
outside it drowns at r700 and our soup sits idle from then on. The citadel (Iteration 6) reached
for this with the wrong geometry (a sealed pocket, 16 wall tiles). The next structural candidate
is the vaporator plateau: seats on the ring as now, helpers on the 16 distance-2 tiles feeding the
ring, feeders on distance 3 piling dirt onto the helper tiles so they rise as a plateau, the
builder parked on it, vaporators and a school built on it at height once it is dry for good --
production and guns that survive the flood. It is days of work and needs the seal-by-r700 of the
ring-1 design to be kept intact; it goes in HANDOFF as the plan.

Plateau stage-one measurement (Iteration 12's code vs g_iter3, Prison and TwoLakeLand): the
distance-2 helper tiles track water+2 (22 at r2000, 119 at r2500), the seats' dig sources are pits
at -800 to -2300, and no feeder was ever built -- the pre-flood soup runs out at about 24
landscapers. A plateau needs an economy first. `spar3` (g_iter3 vs arch_swarm with its ferry):
**15/24**; the carriers get shot, so the ferry made the archetype weaker than the plain waves
(12/24). Arm baseline: 15/24.

## Iteration 17 -- the economy regime (2026-09-24)

team4 at r1000: 33 miners, 44 vaporators; we mine about 30% of a map's soup with 8 miners and
leave the rest. Candidate = g_iter3 with `MINERS_EARLY` 8, `MINERS_MAX` 20, `MINERS_TOTAL` 30,
`MINER_REPLENISH` 20, `MINER_SOUP_RESERVE` 100, `VAPORATOR_BANK` 400, `VAPORATORS_MAX` 8,
`DRONES_MAX` 20. The wall code is untouched; the question is whether a three-times economy buys
more landscapers, vaporators and drones before r700 than the 5th miner of Iteration 15 cost.
Diagnostic: Prison, Soup, TwoLakeLand vs g_iter3 -- soup and landscapers at r400/r700, ring at
r2500. Gate `gate17` vs `REF=g_iter3`. Falsifier: below 53%.

Diagnostics: Soup won with the ring 1698 vs 1373 (+24%); Prison lost (24 miners crowded a corner
HQ, two seats never reached their tiles, 6,165 soup idle at r1000); TwoLakeLand lost 1485 vs 1944
(22 miners drowned at r700 having mined less than they cost). Tuned (16 miners, reserve 250):
Prison still lost the same way with 4,866 soup idle. **Not gated.** The economy can be tripled on
some maps, but the only sink that decides the mirror is the wall, and the wall takes 24 bodies;
everything past that piles up. Ledger: the sink comes first (the plateau), then the economy.

## Block 16 -- g_iter3 against the challenge pool (run block16, 2026-09-24)

48 games: **10/48 (21%)**; cumulative for g_iter3 44/204 (21.6%).

## Plateau stages 2+3, first measurement (2026-09-24)

Iteration 12's tiers (16 helpers, 8 feeders) with Iteration 17's economy (16 miners, early
vaporators), vs g_iter3. Soup: the economy did not scale on a soup-poor map (8 miners, no
vaporator, 13 landscapers, no feeder posted; ring 1357 vs the incumbent's on the same footing) --
the miner cap is not the constraint there, soup on the ground is. Prison pending; a 24-game
quick-set sweep (`plat2`) shows the map spread. Prison: the economy scaled (34 landscapers, 8
vaporators, 13 drones at r1000) and was thrown away -- 22 landscapers died by r2000 as attackers
with no post, no feeder was ever posted (the 16 helper posts took everyone and churned 49 times),
two corner ring tiles stayed unseated behind the miner crowd, the tier stayed at water+2. `plat2`:
**7/24** vs g_iter3. `src/bot` back to g_iter3. What the plateau needs before its next measurement:
surplus landscapers that take and raise their own distance-3 tile instead of attacking; seats that
are never blocked by miners on a corner HQ; feeders that get posted before helpers fill every
distance-2 tile (or helpers that raise their tile toward the ring once fed by a feeder). The plan
in HANDOFF is updated with these.

Plateau revision (feeders on any distance-3 tile, shallow flooded ones resurfaced from next door):
Prison again -- 48 landscapers at r1000, 437 struck-off posts, not one feeder assigned (`pickPost`
always returns one of the 16 distance-2 tiles, which then proves unreachable), the distance-3 tier
dug to -3000, 36 landscapers dead by r2000. The Iteration 12 role code cannot be patched into a
plateau; the roles need a rewrite around tiles a landscaper can hold: pick the nearest free tile it
can reach and keep dry, at distance 2 or 3, and feed inward. That rewrite is the plateau's stage 2
and the first job of the next session. `src/bot` back to g_iter3.

## Iteration 18 -- the plateau, stage 1: tile-holding roles (2026-09-24)

`Landscaper.java` rewritten (DESIGN.md "The plateau"): every landscaper holds the nearest free tile
at Chebyshev 1-3 it can reach and keep dry (ring first; a shallow flooded tile is resurfaced from
next door), keeps its own tile above the water, feeds inward (seat: lowest of self and ring
neighbours; tier 2: lowest adjacent exposed ring tile; tier 3: lowest adjacent tier-2 tile), digs
outward and under a friend only from that tile's margin, and attacks only with no tile left (and
comes back when one frees). Economy and everything else as g_iter3; `LANDSCAPERS_MAX` 48, the third
tier at the helpers' bank. Diagnostics vs g_iter3: **Prison won, ring 1535 vs 1330 (+15%)** with 43
landscapers at r1000 and tier-2 tiles at 270 where tier 3 fed them; **Soup won, 1449 vs 1322
(+10%)** with 13 landscapers (4 seats, 9 tier 2, no deaths). Cost seen: seat churn on Prison (776
re-holds, 276 struck tiles) and 27 deaths by r2000. Gate `gate18` vs `REF=g_iter3`, launched 09:35.
Falsifier: below 53%.

**Gate 18: REJECT 42-54 (44%)** after six batches, the first batch 2-14. Losses fetched: on
Constriction our school built two landscapers all game -- tier-2 holders stood on every tile around
it; elsewhere one or two ring tiles lagged (175 vs 228 at r600) because the early seat claims came
late for the same reason. Fix: no holder takes a tile adjacent to one of our buildings (the HQ
excepted). Re-diagnosed: Constriction now a **win at r1547** (the incumbent's own school is boxed in
there: 3 landscapers), BeachFrontProperty a close loss with an even ring (1609 vs 1680, 13 v 13).
Gate `gate19` vs `REF=g_iter3`, launched 10:35. Falsifier: below 53%.

**Gate 19: REJECT 25-39 (39%)** after four batches. The plateau roles alone lose the mirror; the
code is kept as `src/arch_plateau` (the plan's stage 1) and `src/bot` is g_iter3 again. Six losses
traced: our ring lags from r1000 (BeachFrontProperty 367 vs 502, CentralSoup 555 vs 682) with the
same or fewer bodies at the wall and far more deaths (BeachFront 32 of 43 spawned dead by r1000
against 11 of 26; CentralSoup 32 vs 23) and heavy claim churn (116-192 `@hold t=1`, 27-72 struck
tiles per game). The roles as written race for tiles -- several units claim the same one, all but
one re-pick, and units walking to shallow flooded tier-3 tiles drown on the way -- and every
re-pick is rounds of digging lost. For the plateau's next attempt: claims must be stable (a unit
keeps its target until it is physically taken, and picks among tiles no other unit is walking to),
tier 3 opens only after the ring is seated, and a flooded tile is never a target unless the unit
already holds a dry neighbour. Diagnostics pre-registered: deaths by r1000 no higher than the
incumbent's, `@hold` re-picks under two per landscaper.

Fixes applied (stable claims: a tile another landscaper is nearer to and heading for is left to it;
tier 3 only once the ring is seated; a flooded tile only from a dry tile we already hold next to
it). Diagnostics vs g_iter3: **BeachFrontProperty won 1607 vs 1459**, deaths 15 vs 18, 1.5 re-picks
per landscaper (both criteria met); **CentralSoup lost 1776 vs 1881**, deaths 29 vs 19, 5.7 re-picks
(neither met -- the claim race persists among units already inside the tiers). Gate `gate20` vs
`REF=g_iter3`, launched 11:35. Falsifier: below 53%.

**Gate 20: REJECT 15-33 (31%)** after three batches. Three gates on the plateau's stage 1 (42-54,
25-39, 15-33): the tile-holding roles lose the mirror however the claims are tuned, while winning
chosen diagnostics by 10-15%. Closed for this session (ledger); `src/arch_plateau` keeps the code
and `src/bot` is g_iter3.

Plateau claims settled on arrival, tier 3 by the water level (`arch_plateau`, not gated): quick-set
sweep `plat7` vs g_iter3 **8/24**. The claim scheme was not the whole story; the stage-1 roles stay
in the ledger until the deaths are traced game by game (the sweep's per-game table needs the
gauntlet's replay directory, which the collector does not keep for wins -- a tool gap noted for
the next session).

## Iteration 21 -- soup-driven miners (2026-09-24)

Iteration 17 showed a tripled economy wins on rich maps (Soup +24% ring) and loses on poor ones
(TwoLakeLand: 22 miners drowned unpaid). Candidate = g_iter3 + a miner with six or more soup
tiles in memory posts a SOUP report (fee 1, once per 50 rounds, before r600); the HQ raises its
miner cap by the reports of the last 150 rounds (up to 8 extra). Rich maps get up to 16 miners,
poor maps keep 8. Diagnostic: Soup, TwoLakeLand, Prison vs g_iter3 -- reports and cap on Soup,
none on TwoLakeLand, ring at r2500. Result: the reports flow and the cap rises to 16 on both maps
(TwoLakeLand's miners report too), but not one extra miner is built: **once the eight seats sit on
the ring at about r150 the HQ has no free adjacent tile to spawn onto**. The miner count is fixed
by r150, so the only economy lever is `MINERS_EARLY` (Iteration 15, 44%). Not gated; ledger.

## Iteration 22 -- the spawn gap (2026-09-24)

From Iteration 21's finding: the HQ cannot spawn once the ring is seated. Candidate = Iteration 21
(soup-driven miner cap) + one ring tile, the exposed tile farthest from the map centre, kept
unseated until r300 (`GAP_ROUND`) so the HQ can build the extra miners the reports justify; it
costs that tile about 75 of height early, which the seats' equalising rule pays back. Diagnostic:
Soup, TwoLakeLand, Prison vs g_iter3 -- miners above 8 by r300 on the soup-rich maps, the gap
seated by r350, ring at r2500. Result: refuted at the diagnostic. On TwoLakeLand the gap works
(15 miners built) and the miners cost the wall -- 11 landscapers to 15, ring 1262 vs 1655; on Soup
the soup memory never fills before r300 (no report, no extra miner) and the unfed gap tile alone
drags the ring's minimum to 958 vs 1352, because the seats' equalising rule pours the whole ring's
dirt into it after r300. Not gated; ledger. `src/bot` back to g_iter3.

## Plateau claims, second revision: slots (arch_plateau, 2026-09-24)

The school posts `SLOT(k, x, y)` the round it builds; the newborn takes slot k of a canonical list.
Sweep `plat8` vs g_iter3 with every replay kept: **10/24**. Per game: only 3-16 of 10-48
landscapers received a slot (fee-1 posts lose the mint race), and the rest churned as before --
Prison 431 and 473 re-picks among 43 units, ring 1078 vs 1436 and 1434 vs 745 (a win and a loss on
the same map by side). Deaths track the incumbent's now. Two more changes before the next sweep:
slot posts at fee 5, and a unit that has re-picked three times attacks instead (bounded churn).
Sweep `plat9` after block 25.

Sweep `plat9` (slots at fee 5, churn bounded at three re-picks): **9/24**. Three sweeps of the
slot-claim revision at 8-10/24; the pre-gate rule (deaths and churn no worse than the incumbent
on the quick set) is not met, so nothing goes to the gate. The plateau's stage 1 stays in the
ledger; the next attempt starts from the role design, not the claim rule.

Sweep `plat10` (slots read from the last two blocks -- the sandbox constructs a robot a round late):
**9/24**, but the table is finally informative: slot coverage is near complete (22 of 25, 24 of 41,
25 of 39 newborns), deaths match the incumbent's, and the roles still lose at equal bodies (Squares
A: 14 landscapers, ring 1705 vs 2212). Cause read off the code: with all sixteen distance-2 tiles
held, a seat has no free tile to dig from -- it may dig under a friend only from a margin the (few)
feeders never build -- so the ring starves while the incumbent's eight helpers leave eight dig
sources. Fix: tier 2 is the eight corner and midpoint tiles only. Sweep `plat11`.

Sweep `plat11` (tier 2 restricted to corners and midpoints): **5/24**, the worst yet -- Squares A
1114 vs 2136 at equal bodies, Prison 793 vs 1416 with 42 landscapers and 136 attackers. With the
same structure as the incumbent (8 seats, 8 helpers) the rewritten roles lose by 40%, so the gap
is in the rewrite's wall mechanics, not in the tiers; finding it needs a turn-by-turn comparison
of one seat under each implementation, which is the next session's first plateau task if the line
is reopened. Four sweeps of the slot revision at 5-10/24; **the plateau stays in the ledger** and
`src/arch_plateau` holds the code and this table.

Turn-by-turn comparison on the plat11 Squares loss: every one of our landscapers digs and deposits
at the ceiling (about 900 of each by r2000, the same as the incumbent's), and the deficit is not
efficiency at all -- **three of the eight ring tiles were never held**. Slots are static: the first
holders of those tiles were lost (a stall, a churn bound) and every later newborn had a tier-2 or
tier-3 slot, so nobody came back for the ring; the empty tiles were fed only by their neighbours'
equalising and set the ring's minimum (897 vs 1747). Fix: a newborn takes the nearest free ring
tile it can still climb before consulting its slot. Sweep `plat12` after block 27.

Sweep `plat12` (ring-first at birth): **3/24**, and the table shows the same thing on almost every
map: five of eight ring tiles held at r2000. The ring-first check ran once, at birth; a claimant
that stalled on the way (the school and refinery sit on the path) struck the tile off and never
came back, and no later unit looked at the ring again. Fix: every unit not on the ring re-checks
for a free climbable ring tile every ten rounds. Sweep `plat13`.

Sweep `plat13` (standing ring-first check): **2/24**, still five of eight seats on nearly every
map. The re-check cannot help after r150: an unheld ring tile is fed by its neighbours' equalising
to within two of them, so it is hundreds high and unclimbable from any other tile; the eight seats
must be won in the first hundred rounds or never. g_iter3 wins all eight with the same "nearest
free ring tile" rule and the rewrite wins five, and the reason is not in any table so far. **The
plateau line is closed for the session** (seven sweeps of the slot revision at 2-10/24); the open
question for the next session is a step-by-step comparison of the first eight newborns' seat
choices under `arch_plateau/Landscaper.pickTile`/`freeRingTile` and `g_iter3/Landscaper.pickSeat`
on Squares, where three claimants fail.

**Answered by the trace (2026-09-24, `diag/seats-Squares.*`, both bots with seat events on system-out).**
The premise was wrong: on Squares g_iter3 also seats only five ring tiles (seated at r100, 131,
137, 150, 150; the sixth and later newborns become helpers, two of them after a `@badseat`). The
difference is what happens to the unseated tiles. g_iter3's helpers stand outside the ring and pour
every load into it, so all eight tiles rise together (28 26 23 28 26 30 28 30 at r200) and the
ring reaches 1510 at r2000. The plateau rewrite's claimants chase the same tiles (two newborns
pick [11,32] at r79 and r85; three pick [11,30] at r110, r123 and r137, none reaches it, all
three strike it off around r160-180 and re-pick tiles that are already held), and its holders'
inward feeding raises the unheld tiles above the ground (5, 8, 10 at r110; 10-15 at r140) before
anyone arrives, so they are unclimbable from r140 while one tile with no held neighbour stays at
4 until r220. The dirt that does arrive is spread over tiers 2 and 3 as well: the plateau ring
stands at 864 at r2000 (57% of g_iter3's), the same halving the citadel showed with 16 tiles. So
the plateau does not lose on seat count; it loses on where the dirt goes. Reopening it means
keeping every load on the eight ring tiles until the ring is safe (no tier-2 or tier-3 self-raise
before ~r1500) and letting claimants hold a tile in place without ever re-picking; anything else
is the citadel again.

## Iteration 23 -- the home guard (2026-09-24)

Where the ladder games go (g_iter3, 780 games, from `progress/games.csv`): 608 of 616 losses are our
HQ destroyed, and they end in the middle of the game -- 41 before r500, 50 by r1000, **274 between
r1000 and r2000**, 151 by r3000, 96 after. The 45 reviewable mid-game losses (study tables) end at
r1216-1217 (benzyx) and r1563-1571 (team4): timed raids of 25-140 drones with 40-160 landscapers
and 30-100 vaporators behind them, while we hold 6-14 landscapers, **0 drones** and 1,300 idle
soup at r1200. Iteration 10's trace already named the mechanism (drones lift our seats, 7-11
landscapers dropped on the freed tiles bury the HQ in 25 rounds); it died in the mirror, which has
no raid to defend against, and its drones were split half hunting.

Second fact: g_iter3 built a fulfillment center in **18 of 248** reviewable ladder games. The
center comes after the first vaporator (650 bank) and the ladder's peak pre-flood soup reaches 650
in 38% of games, 500 in 51%, 400 in 81%. Our drones are not dying on the ladder; they are never
built.

Candidate = g_iter3 with (1) the center right after the school at a 250 bank (Iteration 2's
placing; its 52% gate was the mirror pricing drones that hunt); (2) from r550 the center buys
drones down to a 300 reserve up to 16 -- soup unspent at r700 is never spent (base income only
after the flood); (3) from r900 every drone is a home guard: it patrols the annulus 3-5 round the
HQ and lifts any enemy unit within Chebyshev 8 of it, landscapers first, those on the ring or
beside the HQ first, and drops it in the water that surrounds the ring after r700. Before r900
drones hunt as g_iter3's do (Iteration 10's first attempt lost the mirror's pre-flood attrition).
Falsifiers: mirror below 47% (the drones cost helpers, and the ladder cannot recover a wall that
loses the mirror by more); the second arm, `arch_raider` (arch_swarm whose wave reaches our ring
before fetching cargo and ferries any of its landscapers onto freed ring tiles from r1000), must
show the guard lifting the cargo; and the judge that matters is a ladder block against g_iter3's
21.6%, since the mirror cannot see a defence against a raid it never makes.

Diagnostics so far: vs arch_swarm on CentralLake and IsThisProcedural the swarm never raided (its
ferry waits at home for helpers it no longer has) and on CentralLake we built no center at all:
an enemy landscaper buried our school at r241 and the builder died at r301, so 1,100 soup sat idle
from r700 (a single point of failure worth its own iteration: 14 of 258 ladder games show 650+
soup at r500 and nothing built). Mirror on TwoLakeLand with the early center: 2 drones, ring 1726
vs 1783 (-3%), lost -- the drones' cost in the mirror, as expected.

The raider had to be built before the guard could be seen: arch_swarm's wave fetches cargo only
when it has helpers (it has none by r1000), its carriers steered onto the seat tile itself (a tile
drones never enter), charged one at a time into the HQ's one-shot-a-round, and fled at r2 15 with
the cargo aboard. `arch_raider` now splits by id parity (even lift, odd carry a landscaper of our
own, seats included), assembles 8 in sight 80 rounds after RAID_ROUND (1000), and charges without
fleeing. Even so, against a shooting HQ nothing landed on CentralLake (carriers arrive in file and
die at cheb 3). So the mechanism check silenced our HQ (a throwaway `diag_bot` copy with the shot
disabled, deleted afterwards) on Prison, where the candidate has 16 drones: the raider lifted all
15 of our landscapers and delivered 7 onto ring tile [4,4]; the guards lifted 6 of them within one
or two rounds of landing (`@deliver` r1193 -> `@pickup guard=true hc=2` r1194, and so on) and
drowned them, and the HQ stood to the end with 0-1 dirt on it. **Mechanism shown.** Gate `gate23`
(mirror vs g_iter3, cap 240) and the second arm (`arm23`: bot vs arch_raider on the quick set,
`arm23ref`: g_iter3 vs arch_raider) launched together.

**Gate 23: REJECT at 16-32 (33%)** after three batches (4-12, 8-8, 4-12). Arm: bot vs arch_raider
**14/24**, g_iter3 vs arch_raider 15/24. The mirror prices sixteen pre-flood drones as sixteen
helpers not built (Prison diagnostic: ring 1339 vs 1516, -12%), and the raider cannot land cargo
against a shooting HQ, so the guard never fired in the arm either. Ledger: refuted as built. What
survives: the guard role itself (shown on Prison) and the finding that our drones are never built
on the ladder. The drones must come from soup the mirror does not value -- the post-flood soup
(1,300 idle at r1200), which needs a center that outlives the flood: Iteration 24.

## Iteration 24 -- the perch (2026-09-24)

Gate 23 said pre-flood drones are helpers not built; the ladder says our drones are never built at
all. The soup that nobody values is the post-flood soup (base income, 1,300 idle by r1200), and it
needs a center that outlives the flood. Buildings cannot be raised (dirt on a building buries it),
so the ground goes up first. Candidate = g_iter3 + the guard role of Iteration 23 + **the perch**:
the HQ picks a cardinal direction at r80 (once the school stands) such that P = home+2d, B =
home+3d and F, V = B +- perp are on the map, dry, B within 3 of the HQ's ground, F and V within 6 of
B, none beside a building, B as far from the map centre as possible; posts P on the chain at fee 3
every 10 rounds until r600 (a unit only ever reads the previous round's block, so newborns also
scan 12 blocks back). The helper nearest P takes it (the **mason**) and, before feeding the ring,
raises F and V (never more than 3 above B) and B once the builder stands on it, to the water level
of r2200 plus 2 (32). Seats never dig P; the mason refills it from next door if it was dug into a
pit before they knew. The builder walks onto B at r420, rides up, and builds the center on F when
both are within 2 of the target (r567 on TwoLakeLand), then the vaporator on V at 500 soup. After
r700 the center buys a drone for every 150 soup (16 at most, saving 350 for the vaporator until
r1000); drones born after the flood are guards from birth and hold fixed slots on the square at
Chebyshev 6 (wandering guards crossed the helpers' dig tiles: Prison ring 1303 vs 1601). Miners
never climb the perch as a flood refuge (one parked on V and blocked the vaporator).

Diagnostics (TwoLakeLand, Prison vs g_iter3): the perch at 30-32 by r600 on both; the center
spawns guards from r700 to r2200 (11 on TwoLakeLand from base income; 16 by r1000 on Prison with
the vaporator up at r588); ring 1801 vs 1780 and 1585 vs 1601 with fixed guard slots. Seven
mechanisms had to be fixed on the way (chain schedule, post crowding, the pit, the mason's dirt
source, guard wandering, the vaporator's reserve, the refugee miner) -- each visible only in the
replay. Side finding: on Prison the incumbent's own post-r400 building spree (six vaporators and
two net guns at distance 3, on the helpers' dig tiles) costs it 13% of ring; a parked builder
scored 1823 vs 1595.

Three more fixes before the gate: the mason raises F and V only when nobody stands on them and
non-builder miners step off any perch tile (a miner rode V up and blocked the vaporator); any
miner that finds B empty from r420 takes it, and a builder that arrives to find B held defers (the
builder died at r279 in one run); the HQ posts the perch until r700 so the center, born at
r520-650, learns it and saves 350 for the vaporator (born after the last post it bought a drone
with every 150). Final TwoLakeLand: successor on B at r464, center r640, vaporator r927, 11 guards
at r1500 and 16 at r2000, ring 1874 vs 1900. **Mechanism shown.** Gate `gate24` (mirror vs g_iter3,
cap 240) launched; the ladder block of the candidate follows block 35, since the mirror cannot
price a defence against a raid it never makes.

**Gate 24: REJECT at 51-61 (45.5%)** after seven batches (batches 4-7 all 7-9 or 9-7; every game a wall
race settled after r3000). The mirror prices the perch at about 4.5 points: a miner parked from
r420, ~90 loads of ring dirt, the center's 150 at r640. Decision rule written before the ladder
block reads out: the candidate is kept provisional only if `cand24` (48 games against the
challenge pool) scores at least 16/48 (33%) against g_iter3's 21.9% over 1,116 games; below that
the perch is closed as priced below the gate and the guard's value on the ladder is answered.

**`cand24`: 11/48 (22.9%)** -- g_iter3's 21.9%. Losses by end round: 3 before r500, 1 by r1000, **18
between r1000 and r2000**, 10 by r3000, 5 after; the raid window is untouched (49% of losses, against
45% for g_iter3). Wins 4/4 against wpine215, 2/4 EmaPajic, 1/4 each against cormackikkert, benzyx
and team4, 0/4 against winkelmantanner, uvafan, ronniesong and IvanGeffner. **Closed: priced below
the gate (45.5%) and no ladder gain.** Code kept as `src/arch_perch`; `src/bot` is g_iter3 again.
Whether the guards were absent or merely outnumbered in the mid-game losses is read from the
block's study table: **absent**. The perch center stood at r700 in 4 of the 21 reviewable games
(CentralLake, WaterBot, Constriction, FourLakeLand); in the six reviewable mid-game losses (team4
x3 at r1547-1573, EmaPajic x2, benzyx RandomSoup1) we had no center and no drone. Where the perch
did fire it did not save the game either: WaterBot, 15 guards and 13 pickups against a 10-drone
benzyx raid, lost at r1628; CentralLake, 16 guards, lost at r2890. The perch depends on a cardinal
block of dry, level tiles beside the HQ and on a mason and a builder both surviving to r650, and
the ladder's maps rarely give all of that.

## Iteration 25 -- seats first (2026-09-24)

Evidence: the HQ's `@econ ring=` count at r700 (landscapers on the eight ring tiles) in the last
fourteen reviewable g_iter3 loss replays: 6, 4, 4, 4, 4, 4, 3, 5, 3, 2, 6 (three games had no r700).
The mirror diagnostics show the same: five seats on TwoLakeLand and Squares. The Squares seat
trace (after plat13) gave the mechanism: the first five seats sit down at r100-150, equalise the
ring tiles beside them (slack 2) and the helpers pour their loads onto the lowest exposed ring
tile, which is exactly the unseated one; by r150 it stands 4 or more above the ground and the
sixth newborn strikes it off (`@badseat`) and becomes a helper. Three open tiles beside the HQ
for the rest of the game, and three fewer diggers on the ring.

Candidate = g_iter3 with (1) until SEATS_BY (r400) a seat equalises, and a helper feeds, only ring
tiles that hold one of our landscapers -- an unseated tile stays at ground level and climbable;
(2) a helper whose post touches a free ring tile within 3 of its own elevation takes it as a seat
(`@reseat`). Cost: the late tiles start rising at r200-400 instead of r100 and the school builds
16 landscapers as before, so a reseat converts a helper into a seat. Falsifier: fewer than 8 seats
at r700 in the diagnostics, or a gate below 50% (the change is aimed at the mirror as much as at
the ladder: a seat is a digger for 2,500 rounds). Diagnostics: TwoLakeLand, Squares vs g_iter3.

Diagnostics. TwoLakeLand: **8 seats from r200** (g_iter3: 5, three `@badseat`), no seat struck off,
ring 575 vs 501 at r1000 and **1919 vs 1587 at r3000 (+21%)**. Gate `gate25` launched on that.
Squares: 8 seats (g_iter3: 5) but ring 563 vs 719 at r1000 and 2099 vs 2310 at r3000 (-9%): with
the ring closed at r200 the HQ can spawn no more miners (6 against 8 with replenishment), soup 145
against 316 at r200, 11 landscapers against 16 by r500 -- g_iter3's economy has been living off
its own open seats. Seats-first removes the urgency of seating, so the economy can come first:
variant `cand25b` = the same with MINERS_EARLY 8 (all eight miners before the ring closes), in
diagnostics on Squares and TwoLakeLand while the gate runs.

**Gate 25: ACCEPT at 45-19 (70.3%)** after four batches (11-5, 12-4, 10-6, 11-5). Snapshot
`src/g_iter5` (the VM's gated `src/bot`, identical to the driver's) is the incumbent and the
submission. First accepted change since Iteration 8; the flood-defence area's streak of six
rejects ends on a change that took no new mechanism, only the withholding of one: nobody raises
a ring tile that has no seat on it yet.

## Iteration 26 -- miners first (2026-09-24, `cand25b`)

Squares showed the cost of a full ring at r200: the HQ can spawn no more miners (every neighbour
seated), and g_iter3's economy had been living off its own empty seats (6-8 miners against
g_iter5's 6 falling to 3). Seats-first removed the urgency of seating, so the economy can be built
before the ring closes. Candidate = g_iter5 with MINERS_EARLY 8 (the HQ's first eight builds are
miners; the school seats them afterwards, and seats-first keeps the tiles climbable). Diagnostics
vs g_iter3: Squares **8 seats, ring 2387 vs 2251 (+6%)** where g_iter5 alone scored -9%; TwoLakeLand
TwoLakeLand 8 seats, ring 1959 vs 1869 (+5%). Both maps above the incumbent line. Gate `gate26`
(`cand25b` vs g_iter5, mirror, cap 240) launched beside blocks 36 and 37.

**Gate 26: REJECT at 50-62 (44.6%)** after seven batches. Eight miners before the school delays the
seats and the wall on random maps by more than the extra soup buys; the two chosen diagnostic maps
had said +5% and +6% against g_iter3, not against g_iter5. Kind: refuted. The code was g_iter5
with MINERS_EARLY 8 and is not kept.

## Where the early losses go (2026-09-24, study tables of the g_iter3 and g_iter5 blocks)

Thirty-nine reviewable losses end before r1000, in two shapes. (1) **poortho's rush**, 18 games:
our HQ carries 3-23 dirt at r100 with 0-3 landscapers of ours alive and 2-5 of theirs beside it,
and dies at r119-250; no drone is involved. Our school comes at r61-75 after the refinery, the
first landscaper at r85-100, and four rushers bury faster than one seat digs. (2) **map-dead
games**, 13: on Climb, Hills and GSF our landscaper count is 0-3 through r500 whoever the
opponent is, and the HQ drowns at r931-932 when the water reaches its elevation. Those maps are
0/14, 0/17 and 0/15 on the ladder, with TheHighGround 0/20, MtDoom 0/24 and Prison 0/24 beside
them. Candidates in order: the map-dead games first (a school that is never built or never
spawns is a defect, and it is worth three maps), then the rush (school before refinery).

Climb, g_iter5 mirror: the school (r84) has free spawn tiles and spawns twice all game. Soup sits at
84-175 from r100 to r500: the map's soup lies behind 99-high cliffs, the miners mine 39 soup in the
first hundred rounds and nothing after, and the HQ keeps replenishing miners (13 built, 910 soup)
because the ring is short and soup touches 200 every 60 rounds -- the school, which acts after
the HQ, never sees 150. Ring tiles at 2, 4 and 7 beside seats at 44; the tile at 2 floods at
r500 and the HQ at 4 drowns at r931. The other side spawned 8 and still lost 3 seats to cliff
tiles it could not climb.

## Iteration 27 -- no miners for a starved map (2026-09-24)

Hills showed the Climb shape (soup 100-200 to r500, 10-11 miners built, the school spawns 2-4,
ring tiles at 2 flood at r500 beside seats at 135); GSF in the g_iter5 mirror is not map-dead
(5-7 seats, no flooded tile), so seats-first already covers it. Candidate = g_iter5 with the HQ's
replacement miners gated on a bank of 350 instead of 200, so a school with a seat to fill always
gets its 150 first. Diagnostics on Climb and Hills came out identical to the g_iter5 mirror to the
last digit: the HQ built no replacement miner before r500 in either version (soup never reached
200), and the school spawned twice on Climb because soup reached 150 only twice by r500. The
binding constraint is income: five miners sit idle with twelve soup tiles in memory while the
map's soup lies up a staircase of 3-high steps. Why they do not walk there is the open question
of the map-dead line. **Not gated; `src/bot` back to g_iter5.** Kind: uninformative as built.

## Iteration 30 -- spend the bank (2026-09-24)

**Evidence**: the census above; in the benzyx RandomSoup1 loss the HQ's ring was full from r300
(`@econ ring=8`, 5 miners built all game), and at r800 the school, center and builder were still
alive with 1,513 soup while the caps held drones at 8 and net guns at 2. **Candidate** = g_iter6 +
above a bank of 1,000 the drone cap lifts to 40 and the net-gun cap to 6 (`C.RICH_BANK`).
**Diagnostic** (vs g_iter6, RandomSoup1): 28 drones against 7 by r1000, won r3200. **Gate `gate30`:
58-70, SPRT REJECT.** More drones and guns from the bank lose the mirror; `src/bot` back to g_iter6.
Kind: refuted as built -- the bank is not short of drones; what it lacks is a producer that
outlives the flood.

Blocks 51-52 (g_iter6): **28/48** and **38/48**; g_iter6 1747 +- 62 after 144 games, rank 16 of 72,
field score 73.2% (g_iter5 1739 +- 30). Blocks 53-54 (`20260924-190807`, `-190813`): **16/48** and
**18/48**; g_iter6 1720 +- 48 after 240 games, rank 17 of 72, field score 71.3%, level with g_iter5
(1743 +- 30); the upper end (1768) is above g_iter5's rating, so no withdrawal.

Blocks 55-56 (`20260924-193834`, `-193842`): **31/48** and **28/48**; g_iter6 1736 +- 40 after 336
games, rank 17 of 72, field score 72.4%; g_iter5 1742 +- 30. Blocks 57-58 (`20260924-194653`, `-194718`):
**28/48** and **24/48**; on distinct games g_iter6 is 1740 +- 43, g_iter5 1743 +- 33 (see "The fixed seed").
Blocks 59-60 (`20260924-204807`, `-204833`, the first seeded blocks): see the ladder table for the
running total. Blocks 61-69 (`20260924-210822` to `-2150xx`) recorded: the last incumbent blocks (owner, PROMPTS 26).

## Iteration 40 -- the walkway, two parts (2026-09-25, on g_iter7)

**Candidate** = g_iter7 + (a) from r250 to r400 a seat never digs a circle tile beside an exposed ring tile that no
landscaper of ours holds (38's rule, but only once a tile has stayed unseated past r250 -- before that every tile is
unseated and 38 paid 6% of ring on ordinary maps); (b) a miner that has had no legal step for 20 rounds and stands
beside an unseated ring tile steps onto it before r400 (it can go nowhere else, and it is the cork in the walkway
to that seat: two miners held Climb's south way west all game). Diagnostics (VM, controls from `diag38`/`diag39`):
Climb both ways, GSF, Hills, RandomSoup1 -- `@badseat` and `@uncork` counts, the ring at r500 without a flooded
tile, the HQ alive past the flood round, RandomSoup1 within 5% of the control (2445 as A).

**Diagnostics.** Hills as B: the ring at r500 reads 136 134 74 136 102 136 133 71 -- **sealed** where the control's
reads 135 97 2F 150 27 153 150 25 -- and the candidate wins the game at r931 that the control loses to the flood
(`@uncork` 13). Climb as A: 2F 23 26 29 127 27 126 129 against 2F 24 27 4 109 7 109 112, seven seats for five, the
west corner still under water, both sides drown, the candidate takes the tiebreak (`@uncork` 24); as B seven for
five likewise (`@uncork` 27). RandomSoup1 as A 2492 against the control's 2445 (`@uncork` 10). GSF as A: one ring
tile at 4 at r500 (the control's lowest 97), ring 1046 against 1304 at r3000 and the game lost at r3032 where the
control wins -- the seat-starving cost of part (a) on a soup-poor map, as with 38. The two parts are read apart:
**40b** is part (b) alone (`WALKWAY_FROM` 400, so (a) never applies), the same five games.

**40b (part (b) alone):** Hills as B sealed again (147 145 74 147 116 147 144 71) and won at r931 -- part (b) is what
seals Hills; Climb as B five tiles at 145 for the control's three at 108 (the corner still 2F); RandomSoup1 2492
against 2445. **GSF as A is part (b)'s cost too**: the ring tile at 4 at r500, 1060 against 1304 at r3000, the game
lost -- the miner stepped onto a ring tile twenty times over the game (`@uncork` 20) and, boxed in, stayed there
blocking the seat each time. **40c**: the step only when a landscaper of ours is within 2 (someone waiting to
pass), and never twice in 100 rounds; the same five games.

**40c fires everywhere it should and costs nothing measurable.** Hills as B: sealed (147 145 74 147 116 147 144 71,
five steps) and won at r931 where the control drowns. Climb as B: 118 115 98 115 96 26 24 2F against the control's
110 108 7 108 4 26 24 2F -- two more seats (eight steps), won on the tiebreak. Climb as A: 2F 24 26 30 132 27 132 134
against 2F 24 27 4 109 7 109 112 -- the tile at 4 seated (30), the two at 109 raised to 132, the corner still 2F,
lost on the tiebreak either way (ten steps). GSF as A: every tile seated by r500 (min 96), 1181 against the
control's 1304 at r3000 (-9%, inside the 7-18% the control run puts on side effects; 40b's -19% is gone), won at
r3032 (one step). RandomSoup1 as A: 2446 against 2445, no step taken. The mechanism fires on the maps that carry
the flood-round deaths and stays quiet elsewhere. Snapshot `src/cand40c`; **`gate40c`** (mirror vs g_iter7, seeded,
cap 240) and **`arm40c-a`/`arm40c-b`** (96 seeded band games as `us:cand40c`). Pre-registered for the arm:
g_iter7 loses at the map's flood round in 6.6% of its ladder games (19 of 288; g_iter6 9.1%), so 96 games expect
6-7 such losses; a finding at 2 or fewer (P about 0.03 at the incumbent's rate). The arm's power is thin -- the
gate decides, the arm says whether the flood-round deaths moved.

**Gate 40c: REJECT at 34-46 (42.5%)** after five batches (11-5, 8-8, 5-11, 6-10, 4-12). The losses are late: 43
of 46 end past r2900 with the HQ destroyed -- a wall shorter than g_iter7's, not flood-round deaths (one of those,
against two flood-round wins). Worst maps InADitch 1-4, RandomSoup2 1-4, then eight maps at 0-2. The diagnostic
maps were the wrong sample: on RandomSoup1 the step never fired, but on maps with soup beside the ring a miner
*mining* stands still for 20 rounds too, and with a landscaper within 2 (the seats are there) it steps onto the
ring -- "boxed in" was measured as "not moved", which a working miner also is. Reproduction next (the seeds are
recorded): InADitch as A (seed 625931922) and RandomSoup2 as B (seed 158101714), the step logged with the soup
carried and the free directions, against g_iter7 vs itself on the same seed.

**The reproduction overturned the reading, and the gate with it.** Both loss seeds replayed with no step taken,
and the candidate's game was the control's game to the tile (InADitch A: 2018...1825 at r3000 both ways, lost
r3142 both ways; RandomSoup2 B: 1980 both ways, lost r3164 both ways). The engine is deterministic under a fixed
seed, so wherever the change does not fire the candidate plays the incumbent's exact game, and the mirror scores
a coin flip decided by the map, the side and the seed. **`tools/paired.sh`** replays a gate's cells twice, the
candidate game and the incumbent against itself on the same seed. Over gate 40c's 80 cells: **74 pairs concordant**
(30 wins, 44 losses -- g_iter7 against itself lost 55% of these draws from the candidate's side), **discordant 4-2
in the candidate's favour** (sign test p=0.69), the step fired in 29 games, 56 pairs identical to the end round.
The 34-46 was the draw, not the change. The gate is now paired (`mirror.sh` `PAIRED=1`, TRAINING_ALGORITHM 4.4):
the SPRT counts discordant pairs only. Earlier gates that ended near 50% (28b 129-111, 30 58-70, 40c) carried
this noise; the wide rejects (31 7-25, 31b 13-35, 37 2-14) did not need the pairing to be read.

**Arm 40c: 57-39, rating 1729 +- 77 (g_iter7 1734 +- 44), flood-round losses 6 of 96** -- GSF r933, Egg r934,
Hills r931, WateredDown r467 twice, maptestsmall r257 -- the incumbent's rate exactly (6-7 expected; the finding
needed 2). The three reviewable ones all have a ring tile unseated at r500 (GSF as A min 4, Egg as A min 4, Hills as
B a flooded corner tile): against a live opponent the tile stays open with the step in the code. The step seals
the tile only where a boxed-in miner was the block, and on the ladder the block is something else -- the next
census question (what stands on, or fails to reach, the unseated tile at r300-500 in every flood-round loss).
**40c is closed: gate null (paired 4-2), arm null.** Code kept as `src/cand40c`; `src/bot` is g_iter7.

## The seat census (2026-09-25): why the ring tile is open at r500 in every flood-round loss

`tools/replay-dump.sh <replay> --seats A|B --seats-at 300 --seats-at 400 --seats-at 500` prints every ring tile of
our HQ under 10 (or flooded) at the round with its occupant, the nearest own landscaper and miner, the enemies
within 3 and the dirt of its outward neighbours, plus a summary (own landscapers and miners alive). Run over every
flood-round loss of g_iter6, g_iter7, cand37 and cand40c with a replay on the driver (118 games, 102 distinct once
the old fixed-seed repeats are folded; every opponent passed `tier-check.sh`). Read at r500:

| class | games | maps | what it is |
|---|---|---|---|
| few landscapers (3 or fewer alive at r500) | 28 | GSF 14, Hills 7, Spiral 3 | the wall never started: on GSF as B, g_iter6 has 0 landscapers and 7-11 miners at r500 in every case |
| a miner of ours on the open tile | 26 | Spiral 9, Hills 8, Toothpaste 3, DidAMonkeyMakeThis 2 | the tile was empty at r300 and r400; the miner climbed onto it fleeing the flood (its outward neighbours read `F F F` at r500), sits at dirt 3-4 between seats at 100+, and cannot leave; 2-5 own landscapers within 3 |
| a landscaper within 2, not seating | 18 | Climb 13 | the known Climb cliff (Iteration 39's tile-by-tile reading) |
| the tile's outward neighbours all flooded, nobody on it | 7 | Hills 4, Spiral 3 | unreachable from outside; from the ring only by climbing down 100 tiles |
| the game ended before r500 | 22 | WateredDown 10, maptestsmall 3 | maps that flood at r467 / r257: a different race |
| an enemy on the tile | 1 | RealArt | |

The classes share one cause. Seats already feed a low adjacent ring tile after r400 (`wall()` step 3 raises the
lowest of its own tile and its ring neighbours, through a miner standing there if need be: the control `ctrl41` on
Spiral as B had a miner on the corner at r400 and the tile sealed by r500). But **of the 52 losses with a wall (4+
landscapers) and an open tile at r500, 51 have no landscaper of ours adjacent to the open tile** (Spiral 13, Climb
13, Hills 12, GSF 4, Toothpaste 3): the seats took the nearest free tiles, clustered on the near side, and raised
the tiles next to them from their seats -- so the far tile's ring neighbours are high and empty, nobody stands
beside it, and nobody can (it is 100 below, and its outward side is under water). The other line is **GSF as B**,
where g_iter6 had 0 landscapers and 7-11 miners at r500 in every loss (g_iter7 3-4): a production failure to
diagnose on its own.

## Iteration 41 -- the seat walk (2026-09-25, on g_iter7)

**Change** (`Landscaper.seatWalk()`, first thing in `wall()` after `SEATS_BY`): a seat two tiles from an open ring
tile (exposed; flooded or more than 3 below the seat; not a building or a seated landscaper of ours) that no
landscaper of ours touches steps onto the raised, empty ring tile between them (climbable, not flooded), and from
there step 3 feeds the open tile. `@seatwalk` logs each step. **Diagnostic** (VM, seed 41, against `ctrl41`): Hills as
B (the control's corner is open at r500 with the nearest seat at 2, and it drowns at r931), Spiral as A (open
corner at r500, seat at 2), GSF as B, Toothpaste as B, RandomSoup1 as A (the cost check). Pre-registered: the walk
fires on Hills and Spiral, the tile is sealed by r600, the HQ outlives r931 on Hills; RandomSoup1's ring at r3000
within the control band.

**Diagnostic 41.** Hills as B: one walk at r523 (the seat steps beside the corner), the corner sealed by r600, the
HQ alive past r931 where the control drowns; the game then runs to r2981 and is lost on the wall (225-269 on the
fed side against the opponent's 282-296 at r1000) -- a flood-round death turned into a wall race. Spiral as A: **no
walk**: the corner is open at r500-600 with the nearest seat at 2, but the tile between them stands at 29 beside a
seat at 147 (step 3 has been feeding the seat's four low neighbours in lockstep since r400) and cannot be stepped
onto. GSF as B, Toothpaste as B, RandomSoup1 as A: nothing to walk to, the games identical to the controls to the
tile. **41b**: when the tile between is too low to step onto, the seat feeds it level first (`@seatfeed`), then
walks; Spiral A, Hills B, GSF B again.

**Diagnostic 41b.** Spiral as A: the feed starts at r400 (369 deposits on the tile between), the walk at r794, all
three open tiles sealed by r900 and the HQ alive past the flood round; but the seat that fed two tiles grew
neither, the ring reads 53 188 101 189 225 51 225 227 at r1000 and 253-383 at r2000, and the game is lost at r2823
-- where the control drowns at r932 (the earlier reading of the control log put Toothpaste's r3143 on Spiral; the
replay says r932). Hills as B: the same game as 41 (the feed ran from r400, the walk still at r523, sealed by r600,
lost at r2981 against the control's r931). GSF as B: nothing fires, identical to the control. **The form turns
both flood-round deaths into wall races lost 1,900-2,000 rounds later**; against g_iter7's full wall the surviving
HQ still loses, so what it is worth is a ladder question (the field's walls are weaker). Snapshot `src/cand41b`;
**`gate41b`** (the first paired gate: N=320 pairs, `LOGTAG=@seat`) and **`arm41b-a`/`arm41b-b`** (96 seeded band
games as `us:cand41b`; pre-registered: 2 or fewer flood-round losses of 96 against g_iter7's 6-7).

**Arm 41b: 51-45 (53%), rating 1737 +- 76 (g_iter7 1734 +- 44), flood-round losses 1 of 96** (Islands2 r932) --
**the pre-registered finding** (the threshold was 2; g_iter7's rate gives 6-7, P(1 or fewer) about 0.013; cand40c,
the last candidate on this line, had 6). The seat walk removes the flood-round death from the ladder; the rating
is level with the incumbent's inside the arm's interval.

**Gate 41b (paired, 320 pairs): discordant 14-0, concordant 154-152 -- INCONCLUSIVE by the SPRT bound, ACCEPTED by
the cap rule.** The SPRT's `H1 p=0.58` was written for the unpaired mirror and its bound (LLR 2.94) needs 20-0
on discordant pairs; the sign test on 14-0 is p = 0.0001 (the cap rule asks p < 0.10 on 12 or more), and the arm
holds the pre-registered finding. The fourteen: GSF as A twice (the control drowns at r932-933, the candidate wins
at r2970), Climb and Hills as A (the control loses the r931 tiebreak, the candidate wins it), Hourglass as A three
times (the r2525 tiebreak flipped), InADitch four times (wall races won by 15-30 rounds), and the last batch's
four. Not one pair went the other way in 320: where the walk does not fire the game is the incumbent's, and where
it fires it has not yet lost one. **Accepted: `src/g_iter8` = g_iter7 + the seat walk (Iterations 41/41b).**
Archetype regression `regr8`: **24/24** vs `arch_swarm` on the quick set (g_iter7 21/24). Submission blocks
`sub8-1..5` (240 seeded band games as `us:g_iter8`).

**g_iter8 after four blocks (192 games): 82-110 (42.7%), rating 1712 +- 54, rank 21 of 78; flood-round losses 3
of 192** (Europe r932, and WateredDown r466 and maptestsmall r258, maps that flood before r500) against g_iter7's
19 of 288 (6.6% to 1.6%). The rating sits inside g_iter7's interval (1734 +- 44) and no withdrawal applies (the
upper end 1766 is above 1734); the flood-round deaths went and the rating did not move -- the seat walk turns them
into wall races the band's opponents win more often than not. The fifth block is posted separately (the driver's
disk filled during its collection: 2,451 loss replays and logs of g_iter1-g_iter5 runs deleted, the tables kept).
**All five blocks (240 games): 108-132 (45.0%), rating 1727 +- 49, rank 16 of 79; flood-round losses 4 of 240**
(Europe r932 twice, WateredDown r466, maptestsmall r258). Level with g_iter7 (1734 +- 44) on the ladder; the roster
tier and `ONSET-merged.md` now follow g_iter8.

## Iteration 42 -- the doorstep (2026-09-25, on g_iter8)

**Why.** The census's other class: 28 flood-round losses with 3 or fewer landscapers at r500, 14 of them GSF. In
every GSF loss mining stops at r250-300 (the lowlands flood: 122 to 1,780 tiles under water) and the school stops
producing while the HQ goes on buying miners (spawned 15 to 22 by r900, 7-9 alive, none mining). The two g_iter7
losses on GSF as B (`eggag32.BrutalPigeonBot`, allowed) show why the school stops: it stands at elevation 0 three
tiles from the HQ, and by r400 its neighbours are water on four sides, the refinery, a seat's pit dug to -9, and two
miners boxed in on the last two dry tiles (water, the pit, the buildings and the raised seats around them) --
nowhere to spawn, for 500 rounds. The same board in both games. A seed search on the VM (g_iter8 against itself
on GSF, seeds 1-8) found one flood-round death, as B on seed 1, and it is a third thing: **our own refinery on a
ring tile** (Iteration 34's stall refinery, built through `tryBuild` which never excluded the ring), which no seat
can raise; the HQ drowns through it at r932.

**Change (two rules).** (1) `Landscaper.doorstep(n)`: a tile beside one of our buildings (not the HQ) is never dug,
in `wall()` step 4 and `help()` step 3 -- the school's spawn tiles and the refinery's approach stay level. (2)
`Robot.tryBuild` never places a building on a ring tile. **Diagnostic** (seed 1, GSF): as B the school's east side
reads 4 4 4 at r400 where the control has -9 -9 -9, landscapers 8 by r700 against 7, and the HQ lives to r2968
where the control drowns at r932 (the refinery tile floods either way; why the candidate's HQ survives it is not
read yet); as A identical to the control. Snapshot `src/cand42`; **`gate42`** (paired, 320 pairs) and
**`arm42-a`/`arm42-b`** (96 band games as `us:cand42`; no count is pre-registered -- the few-landscaper losses are
1-2 per 96 games, below what an arm can see; the rating and the gate decide). RandomSoup1 as A on seed 41 is the
cost check against `ctrl41` (2642 at r3000).

**Cost check failed: RandomSoup1 as A 578 at r1000 and 2056 at r3000 against the control's 765 and 2642 (-22%).**
Every building's neighbours as doorsteps (refineries, vaporators, net guns at Chebyshev 3 make most of the seats'
dig tiles at 2 untouchable) starve the seats. `gate42` and its arms were stopped in their first batch (nothing
recorded). **42b**: the doorstep is only the school's and the fulfillment center's (the buildings that spawn);
`src/bot` = g_iter8 + 42b; the same checks again (RandomSoup1 A, GSF B seed 1, Toothpaste B).

**42b:** GSF as B seed 1: the school's east side level at r400 (one pit, at the far corner), every tile sealed by
r700, the HQ alive to r2922 where the control drowns at r932. Toothpaste as B level with the control. But
**RandomSoup1 as A 677 / 2315 against 765 / 2642 (-12%) and the game lost (r3198) where the control wins**: the
school on RandomSoup1 stands on high ground all game, so its doorstep costs the seats three dig tiles for 3,000
rounds. **42c**: the doorstep holds only while the building would otherwise be left with fewer than three other
tiles it can spawn onto (dry, within 3 of its elevation) -- the school keeps a door and the seats keep their dig
tiles wherever the school has room. RandomSoup1 A, GSF B seed 1, Hills A again.

**42c fires where it should and costs nothing measurable.** RandomSoup1 as A: identical to the control to the
tile (765 / 2641, won r3219). Hills as A: the ring identical to the control at r500 and r1000. GSF as B seed 1: the
school keeps its doors (one pit at its far corner at r400), every tile sealed by r700, the ring 317 at r1000 against
42b's 203, the HQ alive to r2922 where the control drowns at r932. Snapshot `src/cand42c` (the doorstep with the
three-door limit, and no building on a ring tile); **`gate42c`** (paired vs g_iter8, 320 pairs) and
**`arm42c-a`/`arm42c-b`** (96 band games as `us:cand42c`; no count pre-registered, the gate and the rating decide).

**Arm 42c: 49-47, rating 1710 +- 76 (g_iter8 1727 +- 49), flood-round losses 0 of 96.** Level; the doorstep's
few-landscaper case is 1-2 games in 96 and the arm cannot see it.

**Gate 42c, 320 pairs: discordant 7-1, concordant 158-154 -- INCONCLUSIVE, and below the cap rule's twelve
discordant pairs** (the sign test on 8 says p = 0.07). The doorstep changes one mirror game in forty. A second
block of 320 pairs is pre-registered now (`gate42c-2`, the record carried with `W0=7 L0=1`): the reading at 640
pairs is the sign test on every discordant pair, twelve or more required, p < 0.01 an accept and p < 0.10 a
provisional keep -- the same rule at twice the cap, declared before the block, not after.

**Gate 42c at 640 pairs: discordant 12-2, concordant 314-312 -- PROVISIONAL** (sign test p = 0.013 on 14
discordant pairs: past the twelve-pair floor and under 0.10, not under 0.01). The doorstep changed 14 games in 640
and won 12 of them; the arm was level with no flood-round loss. **Kept provisionally: `src/bot` = g_iter8 + 42c
(`src/cand42c`), no snapshot, no submission; the next candidate stacks on it and is tested against g_iter8.**

## What ends a game, and the bank that waits (2026-09-25)

g_iter8's 132 ladder losses: 86 in the raid window (r700-2900; `team4` 28 and `EmaPajic` 26, both locked at 10%,
`cormackikkert` 16, `benzyx` 11), 36 late (r2900+: `laurenschneider` 14, `poortho` 10), 6 early, 4 at the flood
round. A late game ends when the water (`e^(0.0028 r ...)`: 911 at r3000, 1,496 at r3100, about 2,600 at r3220)
passes the lower ring; the walls stand at 2,000-2,650 at r3000, so the margin is 10-50 rounds and the whole race is
the dirt put on the ring before r2750, when the helpers drown (19 to 8 on RandomSoup1, 13 to 7 on Toothpaste; a
body alone cannot outdig the water past a level of about 180, r2350). Intake is bodies adjacent to the ring times
0.5 a round, and every one of those bodies is bought before the school drowns.

**The bank they wait for.** The school builds landscapers 9-16 only above `HELPER_BANK` 300 + 150 and 17-24 only
above `ATTACKER_BANK` 700 + 150; both reserves were for guns and drones that are never bought (the bank ends at
12,000). On RandomSoup1 the bank sits at 400-850 from r250 to r500 while landscapers 17-24 trickle out one per
fifty rounds (16 at r250, 24 at r550); on Toothpaste as A it hovers at 380-770 all the way to r700 and **landscapers
14-24 are never built** (13 all game, the game lost; the other side had 20 by r600). Eight bodies 200 rounds
sooner is 800 dirt, a hundred of ring.

## Iteration 43 -- no bank (2026-09-25, stacked on g_iter8 + 42c)

**Change:** `HELPER_BANK` 300 -> 0, `ATTACKER_BANK` 700 -> 0: a landscaper whenever 150 soup is there, up to
`LANDSCAPERS_MAX` 24. A numeric change; the zero arm is the paired control. **Diagnostic** (VM, candidate and
g_iter8 against g_iter8 on the same seed): RandomSoup1 A and Toothpaste A (seed 41), GSF B (seed 1), Hills B (seed
41) -- landscapers at r300-700, the ring at r1000 and r3000. Pre-registered: 24 landscapers by r400 on RandomSoup1
(g_iter8: r550) and more than 13 on Toothpaste A by r700; the ring above the control's at r3000 on both.

**Diagnostic 43: mixed.** RandomSoup1 as A: 24 landscapers by r350 (the control r550), ring 801 / 2758 against
765 / 2642 (+4%), won. Toothpaste as A: 16 landscapers by r450 (the control 15, and 13 from r550), but 11 alive
after the flood against 13, ring 465 / 1573 against 501 / 1772 (**-11%**), lost 25 rounds sooner. GSF B and Hills B
identical to the controls (the bank never reached 450 there). In both changed games the school's spending kept the
bank under 150 and **the HQ never saw its 200-soup reserve again: one to two fewer miners, mining down 14-19%**
(RandomSoup1 1,342 against 1,649). The bodies came early but the economy behind them thinned, and on Toothpaste
two more helpers drowned at r650-750. Bar not met (the ring on both). **43b**: both banks at 200 -- the school
builds at 350 and leaves the HQ its reserve; RandomSoup1 A, Toothpaste A, and Prison B (a third map) against
g_iter8.

**43b fires and the ring rises on every map.** RandomSoup1 as A 763 / 2719 against 765 / 2642 (+3% at r3000, won
r3226); Toothpaste as A 543 / 1972 against 501 / 1772 (**+11%**) and **won at r3118 where the control loses at
r3143**; Prison as B 484 / 1597 against 475 / 1587 (level, lost either way). Snapshot `src/cand43b` = g_iter8 + 42c
(provisional) + 43b; **`gate43b`** (paired vs g_iter8, 320 pairs -- the stack against the incumbent) and
**`arm43b-a`/`arm43b-b`** (96 band games as `us:cand43b`; pre-registered: rating above g_iter8's 1727, and the
late losses (r2900+) fewer than g_iter8's 36 of 240 pro rata, that is 14 or fewer of 96).

**Arm 43b: 64-32 (66.7%), rating 1771 +- 79 (g_iter8 1727 +- 49), late losses 10 of 96 (the bar was 14),
flood-round losses 2.** Both pre-registered arm criteria met; the best block record of any build (g_iter8's best
of five was 24-24).

**Gate 43b: ACCEPT at 144 pairs, discordant 30-8 (79%), concordant 60-46** -- the first paired gate to reach the
SPRT bound, in nine batches. The bank change touches most games (38 discordant pairs in 144, against 42c's 14 in
640) and wins four of five it touches. **Accepted: `src/g_iter9` = g_iter8 + 42c (the doorstep, provisional until
now, carried in by the stack) + 43b (banks at 200).** Archetype regression `regr9`: **21/24** vs `arch_swarm`
(g_iter8 24/24, g_iter7 21/24). Submission blocks `sub9-1..5` (240 seeded band games as `us:g_iter9`) played;
posted below.

## Iteration 44 -- six early miners (2026-09-25, on g_iter9)

**Why.** With the banks at 200 the 24 bodies arrive as fast as the soup does: r350 on RandomSoup1, but 13-16 at
r450 on Toothpaste and 5-7 all game on GSF, where the lowlands flood at r250-300 and mining stops. The onset
tables put net worth at r150 and mines at r200 first among every signal. The HQ builds `MINERS_EARLY` 4 before
anything else, then one per 60 rounds above a 200 bank up to 8 alive. **Dose:** `MINERS_EARLY` 4 -> 6 (two more
miners, 140 soup, at r1-r20, before the school). **Diagnostic** (VM, candidate and g_iter9 against g_iter9, same
seed): Toothpaste A and RandomSoup1 A (seed 41), GSF B (seed 1), Spiral B (seed 41) -- landscapers at r300-700,
the ring at r1000 and r3000. Pre-registered: more landscapers at r450 on Toothpaste and GSF; the ring not below
the control's at r3000 on RandomSoup1.

**Refuted at the diagnostic.** Toothpaste as A: landscapers 8 / 13 / 18 at r300 / 400 / 500 against the control's
12 / 16 / 22 (the two miners' 140 soup came out of the school's, and the HQ then kept 12 miners alive against 9),
ring 508 against 583 at r1000 (-13%; the game itself ended at r1641 when the opponent's own corner tile drowned
it, a side effect). RandomSoup1 as A 764 / 2641 against 787 / 2744 (-4%). GSF as B the same seven landscapers and
the game lost at r2767 against r2922. Spiral as B alone better (5-6 landscapers against 3-4, ring 148 against 95
at r1000), lost either way. Three of four down: more early miners buy income the school cannot turn into bodies
before the lowlands flood. **`src/bot` back to g_iter9.** Not gated.

## The raids, read again (2026-09-25)

g_iter8's raid-window losses are 65% of its losses, and 54 of the 86 are against `team4` and `EmaPajic`, both
locked at 10% (never reviewed). The allowed raiders show the shape (`--threat`): **benzyx** sends 4-5 drones at
r1200 that carry off 3-5 of our landscapers (15 to 10, 10 to 7), then 8-9 drones with 1-3 landscapers at r1600,
and the HQ is buried within twenty rounds (r1618-1631): the seats are lifted off the ring, the enemy landscapers
dropped where they stood. **cormackikkert** shows nothing within 15 of our HQ until r2100 and kills it at
r2132-2215. Nothing but shots stops a pickup (the HQ fires once a round), and every gun form was priced by the
unpaired mirror (31 at 7-25, 31b 13-35, 37 at 2-14 in one batch). **`gate37p`**: `cand37` (one gun on a raised
site) against g_iter7 again, paired, 320 pairs -- was the 2-14 the draw or the gun?

## Iteration 45 -- seats by the map's own flood round (2026-09-25, on g_iter9)

**Why.** Three of g_iter8's four flood-round deaths are on maptestsmall (r258) and WateredDown (r466): the HQ
floods before `SEATS_BY` 400, when unseated tiles may first be raised and the seat walk begins. **Change:**
`MapState.floodRound(e)` inverts the water curve; `seatsBy` = `SEATS_BY`, or 150 rounds before the HQ's own flood
round when that is sooner (floor 100); the three `SEATS_BY` reads in `Landscaper` use it (`@seatsby` logged).
**Diagnostic** (VM, seed 41, vs g_iter9, controls g_iter9 vs itself): WateredDown and maptestsmall, both sides --
`@seatsby` 314 and 106, the walk firing before the flood round, open tiles at r150-450, the HQ outliving it.

**Refuted as built.** WateredDown (HQ at 2, `seatsBy` 314): as A the seats walked and fed 65 times from r314 and
the HQ **drowned at r468 with two tiles open, where the control has one open at r450 and lives to r2967** -- with
five landscapers for eight tiles, feeding the neighbours from r314 drains the few seats there are; as B (89
walks) sealed by r450 and won at r2967, as the control does. maptestsmall (HQ at 1, `seatsBy` 106): as A sealed
at r150 where the control still had one tile open, then lost at r3057 against the control's r3113; as B won at
r2988 against the control's r3113 -- noise either way. The early walk helps nothing and can cost the HQ; the
three flood-round deaths on these maps are bodies, not timing. `src/bot` back to g_iter9. Not gated.

**`gate37p` (the gun on a site, paired against g_iter7): 0-10 discordant after 32 pairs** -- the old 2-14 was the
gun, not the draw. The unpaired mirror's wide rejects stand.

## Iteration 46 -- no miner without income (2026-09-25, on g_iter9)

**Why.** On GSF the lowlands flood at r250-300 and mining stops for good (the mines count flat from r300 in every
GSF loss and in the controls), yet the HQ goes on buying a miner per 60 rounds above a 200 bank (16 to 21 spawned by
r700 on seed 1 as B), each 70 soup the school wanted for a seat (six to eight seats there, never all eight). The HQ
cannot see the mines; it can see the team soup rise. **Change:** the HQ replenishes a miner only while the team
soup rose within the last `MINER_INCOME_WINDOW` (100) rounds (`@nomines` logged; the first `MINERS_EARLY` are
unconditional). **Diagnostic** (VM, seed 1 and 41, vs g_iter9): GSF B and Spiral B (mining stops at r300: expect
`@nomines`, fewer spawns, more seats); Toothpaste A and RandomSoup1 A (mining runs to the flood: expect no change).

**Inert on this base.** The first form never fired: every team's soup rises by 1 a round (base income), which the
rule read as mining; with the threshold above that (`46b`) `@nomines` fires from r300-400 on GSF and Spiral -- and
**all four games are identical to the controls to the tile.** On g_iter9 the ring is up by r300-400 and the HQ,
whose only spawn tiles are the ring tiles, cannot place a miner anyway (a spawn is refused more than 3 above the
builder); the miners it bought for nothing in the g_iter6/g_iter7 GSF losses were bought while their ring was still
at 4, and the doorstep (42c) has since taken that case away. Nothing to gate. `src/bot` back to g_iter9.

**g_iter9 submitted (240 games): 78-162 (32.5%), rating 1735 +- 48, rank 17 of 81** (g_iter8 1719 +- 49 on the
same fit); losses 89 in the raid window, 56 late, 10 early, 7 at the flood round. The raw record is far under the
arm's 64-32 because the pool moved: the arm, drawn for an unrated candidate, met `yaonam` (12-0), `mhahn2003`
(10-2) and `wpine215` (10-2); the submission's band held `mvpatel2000` (6-24), `winkelmantanner` (7-23) and the two
locked raiders (9-51) instead. The rating reads through the pool: **level with g_iter8; no withdrawal** (the
interval's top, 1783, is above 1719). Three acceptances on this line (walk, doorstep, bank) removed the flood-round
deaths and raised the ring; the ladder has not moved, because the band's losses are raids (37% of games) and
walls taller than ours (23%).

The raid clocks in g_iter9's 240 games: `team4` r1562-1603 (26 losses), `EmaPajic` r1525-1570 (20),
`cormackikkert` r2135-2262 (14), `benzyx` r1220 and r1610 (12), `mvpatel2000` r1893 (12): 84 losses, 35% of all
games, on five clocks between r1525 and r2262. `gate37p` closed REJECT at 4-21 discordant in 112 pairs: the site machinery's
price was real. The bot already builds two net guns before the flood, on the ground, where they drown by r930-1200.

## Iteration 47 -- the keeper (2026-09-25, on g_iter9)

**Form.** One miner parks at r600 on a free tile at Chebyshev 3 beside a helper's post (`@park`); that helper
becomes its keeper: after its own tile it raises the miner's tile and one site tile beside both to
`waterLevel(round + 800) + 2`, the lower first (`@keep`), and no helper digs either; from r1150 the parked miner
builds a net gun on the site when it will stay dry 700 rounds and 450 soup is banked (`@keepergun`), a second
after the first if a second site can be raised. Nothing else is reserved; the keeper is one helper's output.
A gun at 27 stands to r2143, over four of the five clocks; the HQ and one gun are two shots a round against
the 8-9 drones that lift the seats. **Diagnostic** (VM, seed 41, vs g_iter9, controls g_iter9 vs itself):
RandomSoup1 A, Toothpaste A, Prison B, Hills B -- a park by r700, the gun up by r1300 and standing at r1600 and
r2000, the ring at r3000 within 5% of the control. Then the arm: raid-window losses at most 22 of 96 (g_iter9's
rate gives 34).

**Diagnostic 47: the parts fire, two defects.** RandomSoup1 as A: **three** miners parked at r606-607 beside three
helpers, 55 keeps, two guns at r1150 (elevation 16) -- and both guns gone by r1300, the ring 2355 against 2744
(**-14%**), the game lost where the control wins. Prison as B: one park at r643, two guns at r1150 and r1188
(16 and 20), gone by r1400; ring -3%. Toothpaste as A: one park, one keep, no gun, the ring level. Hills as B:
no miner alive to park (identical to the control). The guns die 150-250 rounds after they stand, 400 rounds before
the water reaches 16: **the keeper buried them** -- its site stayed the gun's tile, and its dirt went on the gun
(15 buries a net gun). And every miner that saw a helper parked, one keeper per park. **47b**: only the builder
parks (one keeper), and a site with anything standing on it is given up for the next tile. RandomSoup1 A, Prison B,
Toothpaste A, Spiral B again.

**47b.** Prison as B: **the form works** -- one park at r608, the gun at r1150 on a site at 16, standing from r1200
to r1900 (the water reaches 16 at about r1950), the ring 1752 against 1812 (-3%), the game lost either way. But
RandomSoup1 as A: **no park** (the builder is out at its vaporator sites and never sees a helper) and yet 15 keeps --
a keeper found a stray miner standing beside its post and spent its dirt on that miner's tile and a site, 2508
against 2744 (**-9%**), the game lost where the control wins. Toothpaste A and Spiral B: no park, identical to the
controls. **47c**: a keeper acts only for a miner that has stood on the same tile beside it for 20 rounds (the
stationarity is the park), and the builder walks home when no helper is in sight. RandomSoup1 A, Prison B,
Toothpaste A, and Squares A (with its control).

**47c: the form works on three maps of four.** RandomSoup1 A: park r650, gun at r1150 (16), standing r1200-1900,
ring 2626 against 2744 (-4%), won as the control does. Prison B: park r608, gun r1150, standing to r1900, -3%.
Toothpaste A: no park (the builder never reached a helper), identical. **Squares A: park r617 but the gun only at
r1710, on a site at 94** -- the keeper's target `waterLevel(round + 800) + 2` is 13 at r1150 but 92 at r1700 and
keeps growing, and the keeper poured its dirt into two tiles chasing it: ring 1872 against 2085 (**-10%**), lost
where the control wins. **47d**: the kept height is capped at 42 (dry to about r2290, past cormackikkert's clock),
so the keeper's whole job is about a hundred dirt; RandomSoup1 A, Prison B, Squares A, Toothpaste A, Hourglass B.

**47d.** RandomSoup1 A: park r650, gun r1150 (16), standing r1200-1900, ring 2717 against 2744 (**-1%**), won.
Prison B: the same clock, -1%, lost either way. Squares A: park r617 but **no gun** (450 soup never banked on a poor
map after the builder stopped mining), ring 1970 against 2085 (-5.5%), lost by one round where the control wins.
Toothpaste A: no park, identical. Hourglass B: park r620, no keeps (three landscapers), identical. Where the gun
comes it costs 1% of ring; where it does not, the parked builder's mining is the cost. Snapshot `src/cand47d`.
**`gate47d`** (paired vs g_iter9, 320 pairs) and **`arm47d-a`/`arm47d-b`** (96 band games as `us:cand47d`).
**Pre-registered before either runs:** the mirror cannot see a gun's worth (g_iter9 never raids), only its price;
a paired REJECT refutes the form whatever the arm says (as it did 31 and 37); a null at the cap hands the decision
to the arm: raid-window losses (r700-2900, not the flood round) at most 22 of 96 (g_iter9's 35% gives 34; the
chance of 22 or fewer at that rate is about 1%) and the rating within g_iter9's interval accepts it as g_iter10.

**Arm 47d: 43-53 (44.8%), rating 1765 +- 74 (g_iter9 1735 +- 48), raid-window losses 22 of 96 -- on the bar**
(`mvpatel2000` 6, `EmaPajic` 5, `cormackikkert` 4, `benzyx` 3, `winkelmantanner` 3); late 21, early 7, flood-round
3. **Gate 47d at 160 pairs: discordant 0-7**, the gun built in 2-6 games of each 16 (`fired`): the mirror sees only
the price, and every flip so far is against. Two more arm blocks (`arm47d-c`/`-d`) are queued so the field's
answer rests on 192 games whatever the gate says -- the raid answers are the one line where the mirror and the
ladder disagree by construction, and the owner's question in HANDOFF needs the numbers.

**Gate 47d at 320 pairs: discordant 2-14, concordant 155-149, the gun built in 80 games of 320 -- REJECT by the
cap rule** (sign test p = 0.004 against, 16 discordant). As pre-registered, the form is refuted whatever the arm
says: `src/bot` back to g_iter9, the code kept as `src/cand47d`. What the mirror priced: one helper's dirt on two
tiles to 42, a miner that stops mining at r600, 250 soup -- about 1-5% of ring, which loses two wall races in
sixteen where the incumbent never raids. What it could not price is in the arm (192 games, posted below).

**Arm 47d at 192 games: 73-119 (38%), rating 1742 +- 53 (g_iter9 1727 +- 48 on the same fit), raid-window losses
64 of 192 (33%)** -- the second 96 games had 42 (`EmaPajic` 16, `cormackikkert` 14, `team4` 11, `benzyx` 10,
`mvpatel2000` 9 over the 192), and the first block's 22 was the pool's draw. One gun at 16, standing r1200-1900,
does not turn a raid of 8-25 drones; the mirror and the ladder agree. **47d closed.** The owner's question stays
open in principle (a field-only change the mirror can only price) but this was not the case that needed it.

## Iteration 48 -- the home guard on the idle bank (2026-09-25, on g_iter9)

**Why.** The bot already buys a fulfillment center after its first vaporator and up to eight drones, and the
drones patrol toward the enemy HQ and die by r1000 (Iteration 10's trace; `Dr=0` at r1000 in every control). The
raid drops 1-11 landscapers on the ring tiles its drones have just cleared, and the HQ is buried in 15-25 rounds; a
drone of ours beside the ring lifts a landscaper in one action and drowns it in the next. Iterations 10 and 23
tried the guard and died in the unpaired mirror with their drones "split half hunting" and the center rarely
built for lack of bank; the bank is not the problem now. **Change:** drones never hunt -- they patrol a box of
Chebyshev 4 around our HQ and lift anything of the enemy's within the box plus 2, landscapers on the ring or beside
the HQ first. Nothing else changes: the same center, the same drones, the same soup. **Diagnostic** (VM, seed 41):
against `arch_drone` (the raider archetype) on RandomSoup1 A and Prison B, with g_iter9 against the same as the
control -- `@pickup home=0/1` counts, our HQ alive where the control's dies, drones alive at r1500; and against
g_iter9 on RandomSoup1 A and Toothpaste B for the ring cost (identical is the expectation).

**Diagnostic 48: the wrong archetype, and a 30% cost.** `arch_drone` never dropped anything near our HQ (no
pickup either way; our eight drones alive all game at home). Against g_iter9 on RandomSoup1 A the ring read
**1922 against 2744 (-30%)** and the game was lost: a hovering drone occupies its tile like anything else, and
eight of them patrolling anywhere within 4 of the HQ sat on seats' ring tiles and helpers' posts. **48b**: drones
never enter the ring, and the circle only on the way to a pickup; the patrol is the annulus at 3-4. The raider
archetype is `arch_raider` (carriers fetch our landscapers and drop theirs beside our HQ at r1000): RandomSoup1 A
and Prison B against it, with g_iter9 controls; RandomSoup1 A and Toothpaste B against g_iter9 for the cost.

**48b: no cost, and nothing to see.** Against g_iter9: RandomSoup1 A 2737 against 2744, Toothpaste B identical.
Against `arch_raider`: its carriers lifted ten of our landscapers at r1000-1100 on RandomSoup1 (24 to 14, in the
control too) and our HQ lived either way; our drones made one pickup, none of them beside the HQ, the control's
hunters made one and four. The archetype takes our seats but never puts a landscaper on the ring, so the guard's
one job -- lifting what lands beside the HQ -- has not been seen to fire. Next: make `arch_raider` do what the
band's raiders do (drop its landscapers on the ring tiles its carriers have just cleared, and bury), then the
diagnostic again; without that, no test.

**48c: the archetype cannot be made to do it in an afternoon.** `arch_raider` with its lifters told to take seats
first and its carriers to hold out for a ring tile: 0 deliveries and 0 seats lifted in three games (its drones
will not enter the ring or face the HQ's gun, and its carriers never come within drop range); it still lifts 4-7
helpers from the posts, and our HQ lives either way. The band's raid -- 8-25 drones that lift the seats under the
HQ's fire and drop 1-11 landscapers on the freed tiles -- is not something our sparring partners do. **48 is
shelved, not refuted**: the guard costs nothing in the mirror (identical games) and its code path fires (our
drones lift strays within the box), but the claim -- lifting what lands beside the HQ during a raid -- has not
been seen, and rule 5 says no test without it. Code kept as `src/cand48b`; `src/bot` back to g_iter9. A faithful
raider archetype (drones that charge the HQ's gun, lift seats, and carry landscapers in behind) is the prerequisite
for any raid answer, and it is the next tool to build if this line is to move.

## Iteration 49 -- feed to the end (2026-09-25, on g_iter9)

**Why.** Helpers hold their posts (Chebyshev 2, kept at the water 60 rounds out plus 2) and drown together at
r2700-2750 (19 to 8 on RandomSoup1, 13 to 7 on Toothpaste, 12 to 7 on Prison): from about r2600 the water rises
faster than one body digs (0.0028 x 160 = 0.45 a round against 0.5), so the last 100-150 rounds of a helper's
life go entirely into a post that is lost anyway. **Change:** a helper keeps its post only while the water 60
rounds out is under `HELPER_HOLD` 160; past that it feeds the ring and digs until the water takes it. Expected: the
helpers die 60-100 rounds sooner and the ring gains 40-80 by r3000 -- five to eight rounds at the end, where the
mirror's margins are 10-50. **Diagnostic** (VM, seed 41, vs g_iter9): RandomSoup1 A, Toothpaste A, Prison B, Squares
B (with its control) -- landscapers alive r2500-2900, the ring at r2800-3100, the end round.

**Inert as built.** All four games identical to their controls to the tile and the round (RandomSoup1 A: 20
landscapers to r2700, 8 from r2750, the ring 2594 / 2744 / 2794 at r2700 / 3000 / 3100 both ways; Squares, Prison,
Toothpaste the same). The rule should have turned the helpers' self-maintenance off from about r2580 and changed
their deposits for their last hundred rounds; it changed nothing, so the twelve bodies that die at r2700-2750 are
not spending those rounds where the model put them (the drowning is sudden, not a slow falling-behind: the posts
are held to the round the water passes them). What they do in r2600-2750 needs a trace before any form here is
worth a gate; the prize is five to eight rounds at the end. `src/bot` back to g_iter9. Not gated.

**The trace** (g_iter9, RandomSoup1 A, every helper logged every 25 rounds from r2500): twelve helpers on their
posts, each rising 12-13 per 25 rounds (the 0.5 a round ceiling), `lowSelf` true from r2525 on, three above the
water at r2700 (219 against 216) and all gone by r2725. From r2550 the water 60 rounds out is beyond them and
every deposit goes into a post the water takes 150 rounds later: **77 dirt a helper, 930 in all, 116 of ring** --
the water climbs 7 a round at r3200, so that is about sixteen rounds at the end, not five. The rule's retest with
the trace (`trace49b`) says whether it reaches the helpers at all.

**It does.** With `HELPER_HOLD` 160 the helpers stop at r2575 (155 high), feed the ring, and drown together at
r2640 instead of r2740; the ring reads **2634 / 2784 / 2834 at r2700 / 3000 / 3100 against the control's 2594 / 2744 /
2794 (+40)**, the game won at r3221 as before. (The first diagnostic's identical games are unexplained; this one
carries the trace that proves the rule ran.) Sixty rounds of feeding bought 40 of ring; the hold at 160 stops them
75 rounds before the water outruns a body (0.5 a round at a level of about 178, r2650, which is 230 sixty rounds
out). **49b: `HELPER_HOLD` 230**, snapshot `src/cand49b`; **`gate49b`** (paired vs g_iter9) and **`arm49b-a`/`-b`**.
Pre-registered: the gain is a taller ring in the last 500 rounds, which the mirror sees directly; the gate decides,
the arm reads the late losses (g_iter9: 56 of 240, 22 or fewer of 96 is the bar).

**Arm 49b: 42-54, rating 1751 +- 74 (g_iter9 1727 +- 48), late losses 25 of 96 (the bar was 22; g_iter9's rate gives 22.4),
raid-window 27, early 2.** Null on the arm. Gate 49b at 160 pairs: discordant 3-0 -- the change flips one game in
fifty, all its way so far.

**Gate 49b, 320 pairs: discordant 8-0, concordant 149-163 -- INCONCLUSIVE, under the twelve-pair floor** (the sign
test on 8-0 says p = 0.008). As with 42c, a second block of 320 pairs is pre-registered now (`gate49b-2`, the record
carried with `W0=8 L0=0`): the reading at 640 pairs is the sign test on every discordant pair, twelve or more
required, p < 0.01 an accept and p < 0.10 a provisional keep.

**Gate 49b at 640 pairs: discordant 9-0, concordant 309-322 -- under the twelve-pair floor; not kept.** Nine flips
in 640 pairs, every one its way (the sign test alone would say p = 0.004), one new flip in the whole second block;
the arm null on the late losses. By the rule as written the change is neither accepted nor provisional, and the
rule is not rewritten after the count. What it says about the form: +40 of ring in the last 500 rounds turns one
wall race in seventy, and the ladder's late losses are lost by more than that. Code kept as `src/cand49b`;
`src/bot` back to g_iter9. The twelve-pair floor has now sat on both sides of two candidates (42c 12-2 kept, 49b
9-0 not) -- a note for the owner, not a change.

## What the late losses are lost by (2026-09-25)

g_iter9's 39 reviewable late losses (r2900+), both rings at r2800: **the opponent's lowest tile is a median 38%
above ours** (quartiles 23% and 56%): `winkelmantanner` 15 games at a median 51% (682-1796 against 2031-2338),
`mvpatel2000` 12 at 54% (1009-1513 against 1346-2099), `poortho` 8 at 34%; only `benzyx` (2) and `cormackikkert`
(1) lose it close. Our ring in these games stands at 700-1500 at r2800 where the mirror's stands at 2400-2600:
the wall race on the ladder is not lost by the forty of ring an endgame rule buys, it is lost by half a wall --
bodies missing after the flood, on maps and against opponents that take them. A body census of these games
(landscapers and drones alive at r700-2000, both sides) follows.

**The body census (56 late losses, both sides):**

| round | our landscapers (median) | theirs | our drones | theirs |
|---|---|---|---|---|
| r700 | 16 | 20 | 0 | 4 |
| r1000 | 14 | 29 | 0 | 5 |
| r1500 | 13 | 40 | 0 | 9 |
| r2000 | 7 | 40 | 0 | 8 |

By opponent at r1000 / r2000, ours against theirs: `laurenschneider` 16/27 and 14/40; `mvpatel2000` 5/34 and 1/54
(32 drones at r1500); `winkelmantanner` 20/30 and 0/43 (**124 drones at r1500**); `poortho` 11/28 and 10/28.
**Their landscaper count rises after the flood -- from 29 at r1000 to 40 at r2000 -- while ours falls from 14 to
7; they produce bodies through the flood, and the two with drone fleets carry ours off.** Our drones: none, in
any of the 56 games (the center is never built on the ladder). This is the wall race in one table: the intake
ceiling is 0.5 dirt a body a round and they have three to six times the bodies at the ring after r1500. The
two structural lines HANDOFF has carried all session -- a producer that outlives the flood, and something that
stops a pickup -- are the whole of the late losses, and the raid losses besides. Where their schools stand
(count, elevation, distance from their HQ at r1000-2000) is the next census, from the same replays.

## The enclosure (2026-09-25): what the field does that we do not

The same 56 replays, their side: a school in 51 of 56 games at r1000 and 40 at r1500, net guns in 43 (median 2-3),
vaporators median 4. And the board (`--elev-raw`, `laurenschneider` on CentralLake, their HQ at (33,33), r1000,
r1500 and r2500 identical):

```
 36 ~~~~99999999999999~~~~        99 = 99 or more (clipped)
 35 ~~~~99999999999999~~~~
 34 ~~~~9999-9 3-99999~~~~        the 7x7 block: Chebyshev 2 and 3 all at 99+ by r1000,
 33 ~~~~999910 3 39999~~~~        the HQ's own ring at 3, 10, 11 and -9 (dug), the HQ at 3
 32 ~~~~9999-911-99999~~~~
 31 ~~~~99999999999999~~~~
 30 ~~~~99999999999999~~~~
```

The glyph view of the same board at r1500 shows the block solid with landscapers, three net guns and a
vaporator standing on the ring tiles at ground level, and drones overhead. **Flooding spreads only from a
flooded neighbour** (RULES.md), so a complete dry shell at Chebyshev 2-3 keeps everything inside dry at any
elevation, for ever: the HQ keeps its spawn tiles, the school on a ring tile keeps spawning, the vaporators keep
paying, the guns keep shooting from inside the wall, and the interior is the quarry -- dug to -9 and lower, never
flooded, always reachable, so the shell's dirt comes from inside as well as out and bodies inside the shell feed
the shell from behind. That is how they stand 40-54 landscapers and 30-124 drones at r1500-2000 against our 7-14
and none: not a taller wall, a wall around a working base.

**We raise the ring itself.** Eight seats on the HQ's own tiles at 2,600 wall the HQ off from its spawn tiles
by r400, drown the school, the center and the refineries at cheb 3 by r700-1000, and leave the helpers at cheb 2
holding posts at the water's edge until it takes them at r2750. Every acceptance this session (the walk, the
doorstep, the bank) tuned that design; every structural try (guns on a site, the keeper, the guard) fought the
fact that nothing of ours outlives the flood. DESIGN.md's plateau raised the producers; the field does not raise
them, it encloses them. **The next program is the enclosure**, written up in DESIGN.md and HANDOFF.

## The enclosure, stage 1: `arch_enclosure` (2026-09-25, from g_iter9)

The archetype is g_iter9 with the wall rewritten: no seats; a landscaper takes the nearest free shell tile
(Chebyshev 2, then 3 beside a held 2) within 3 of its own elevation, keeps it at the water 60 rounds out plus 2,
equalises the lowest adjacent shell tile, and digs the quarry first (ring tiles, never the HQ or a building), then
outside; a landscaper inside a closed shell is a feeder (quarry to the lowest adjacent shell tile). The school,
center, guns and vaporators are built on ring tiles from beside them (at most six, the yard stays free); the
refinery stays outside; miners may cross the ring; the school builds to 48. First diagnostic (`diagenc1`, seed
41, against g_iter9): RandomSoup1 A, Toothpaste B, Prison A, GSF B -- tiles held at 2 and 3, feeders, the shell's
minimum at r500-3000, the interior, bodies and buildings by round, the HQ's fate.

**Stage 1 fails as it should, and says what stage 2 is.** The shell never closed: 8, 4, 0 and 2 tiles held of
16, and the HQ drowned at r2822, r2527, r1241 and r932. Landscapers stopped at 9-11 from r300 with 1,800-16,000
soup in the bank: **the school on the ring is boxed in** -- its neighbours are the HQ, two ring tiles and five
shell tiles, and once those are held or built on it has nowhere to spawn (on Prison five vaporators filled the
ring around it). And a body spawned inside cannot climb onto a shell tile once the shell is more than 3 above the
ground. The field's answer is on their own boards: their landscaper count rose 27 to 40 between r1000 and r1500
with the block already at 99+ and no bodies inside at r1500 -- **the drones are the elevator**, lifting new
landscapers from the yard onto free shell tiles (4-9 drones in every one of their bases, 124 in winkelmantanner's).
Stage 2: the yard (the two ring tiles beside the school stay free, other buildings take the rest, at most four),
no feeders (a body inside waits in the yard), and drones from a center inside that lift a waiting landscaper onto
the nearest free shell tile, Chebyshev 2 first and then 3 beside a held tile. `tools/enc-read.py` reads the
diagnostic.

**Stage 2: the principle holds; the machine does not run yet.** RandomSoup1 as A: **all 16 shell tiles dry from
r300 to r2000** (16 / 40 / 109 / 255 at r300 / 500 / 1000 / 2000 with eight holders equalising the rest), **the
interior dry all game** with three ring tiles quarried to -3,473, the HQ alive to r2815 -- where the shell, rising
0.15 a round, is overtopped. Prison as A the same shape (13 tiles at 253-261, three at 5, alive to r2814);
Toothpaste as B four tiles never rose and the interior flooded at r1000; GSF as B two holders, drowned at r932.
What is missing is intake: 8-11 landscapers all game with 2,000-11,000 soup banked, because (1) the builder is
outside the shell once it rises and builds nothing more inside (no center on RandomSoup1; on Prison the center
came at r300 and eight drones followed but lifted nobody), (2) the school stops spawning at 7-11 (its yard is
walked on), (3) the walkers that take no tile never log a stall, (4) the quarry has no floor. Stage 3: a quarry
floor at -10 (then dig the flooded outside), the builder stays inside from the school on, the yard kept for spawns
and lifts only, and a trace of every landscaper without a tile.

**Stage 3: a loop.** With the interior never dug the holders dug the flooded outer tiles and, equalising onto "the
lowest adjacent shell tile" -- which was the outer tile they had just dug -- put every load straight back (380 digs,
380 deposits, 2 on themselves, per holder); the shell sat at 4-6 for a thousand rounds. The walkers circled tiles
miners stood on (a miner did not count as an occupant), and the HQ kept spawning miners into the interior until
three stood in it. Stage 4: equalise only onto a held shell tile at our distance or nearer; the quarry back with a
floor at -9 (the field's depth); a holder with margin to spare raises the highest outer tile beside it toward dry
land for a newcomer (reclaim); any robot counts as an occupant; no miner replenishment.

**Stage 4: the held tiles rise (Prison: six tiles at 146-163 by r1000, the loop is gone) but the school stops
at 6-11 bodies again -- the quarry dug its yard.** Every ring tile went to -9 by r300 (RandomSoup1 seven of
eight), and a school at 5 cannot spawn onto a tile at -9 (a spawn is refused more than 3 away). The field's one
-9 tile is the exception, not the rule. Stage 5: the quarry is only the ring tiles not beside the school or the
center; the rest of the interior stays at ground for spawns and the elevator.

**Stage 5: the machine runs.** Bodies 16-18 (the school spawns again), the elevator lifted 15 on Prison (twelve
waited in the yard and were carried out), held tiles at 64-127 by r1000. Still no HQ past r1241: **holes** --
three or four inner-shell tiles at ground on every map (nobody holds them, nobody feeds them since stage 4
equalises only onto held tiles, walkers to them stall) and the interior floods through the first one the water
reaches. RandomSoup1 also had no center (the builder stood beside the school, where every tile is the yard).
Stage 6: equalise onto any inner-shell tile at our distance, held or not (never a dig source, so no loop); the
builder stands opposite the school; a landscaper set down by the elevator holds where it lands.

**Stage 6: the interior holds.** RandomSoup1 as A: all 16 inner-shell tiles dry from r300 to r2000 (33 / 80 / 210
at r500 / 1000 / 2000), the interior dry all game, **the HQ alive to r2735**; Prison as A: 16 dry, 25 / 45 / 78, alive
to r2516; Toothpaste as B (HQ at 2, the lowlands flood by r450) five holes at r500 and the school drowned. The
enclosure now does what the field's does -- until the water outgrows a shell that rises only 0.08-0.1 a round per
tile with 11-14 holders, against the field's 0.34 with 27 bodies on 40 tiles. The next questions are intake: what
the holders' turns go to (digs and deposits per holder), why the elevator lifted 2 and 0 (13 and 27 waited in the
yard; Prison built a center and no drone in 2,000 rounds -- its spawn tiles are where the waiters stand), and the
outer shell (Chebyshev 3), which nobody has reclaimed.

**Stage 7: twice the intake, and the holes have a cause.** With reclaiming rationed the inner shell reads 425-447
at r2000 on RandomSoup1 (twice stage 6) and the HQ lives to r2924; Prison's held tiles 440-465, alive to r2937.
The turn accounting: 390 digs a holder per thousand rounds, of which 280 had gone to reclaiming outer tiles. The
holes: **our own drones at rest on shell tiles** (the boards show them at Chebyshev 2 after a chase), and a tile
with a drone on it is one no holder can take; Prison's three holes stayed at 5-34 beside neighbours at 458. And
the elevator lifted 2 and 5 with 13 and 122 waits: once the inner shell is complete a drone cannot reach the yard
either -- a drone occupies a tile like anything else, and every way in is held. Stage 8: a drone at rest anywhere
within 2 of the HQ leaves at once; and **the gate** -- the shell tile straight out from the school is never held,
its neighbours raise it, and the elevator lifts a waiter from there, beside the yard, without entering.

**Stage 8: the elevator runs.** RandomSoup1 as A: 18 lifts, 21 landscapers, two of them holding outer tiles at
290; the inner shell 532-545 at r2000 (0.27 a round per tile), the HQ alive to r2986. Prison as A: still 12 bodies,
5 lifts, three holes at 5 -- its HQ is in the map's corner and the drones' exit vector points off the map, so they
stayed on the shell. Squares as B: 8 bodies all game, five miners trapped in the interior blocking the school's
yard (the enclosure let miners cross the ring and the shell closed behind them). Stage 9: drones leave to the
nearest on-map tile at Chebyshev 3; miners keep out of the interior once a refinery stands (the builder lives
there).

## Iteration 39 -- the stalled seat-seeker fills the pit (2026-09-25, on g_iter7)

**Evidence:** the flood-round census (78 of 89 such losses have an unseated ring tile at r500; GSF, Hills, Spiral,
Climb, WateredDown, Toothpaste) and Iteration 38's reading of Climb: the walkway to the unseated tiles is a circle
tile the seats dug to -9. Iteration 38 forbade the digging and paid 6% of ring on ordinary maps. **Candidate** =
g_iter7 + a landscaper whose walk to its seat stalls looks for a pit toward the seat (a neighbour more than 3
below it, not a ring tile) and fills it -- deposits into it, digging from its highest other neighbour -- for at
most 60 rounds before striking the seat off as before. It acts only where a seat-seeker is stuck, so ordinary
maps never see it. Diagnostics (VM, with controls): Climb both ways, GSF, Hills, RandomSoup1 -- `@bridge` and
`@badseat` counts, the ring at r500 without a flooded tile, the HQ alive past the flood round, RandomSoup1's
ring against the control.

**Diagnostics:** `@bridge` fired in no game; Climb both ways, GSF, Hills and RandomSoup1 are identical to their
controls to the digit (RandomSoup1 2445, Climb 2F 24 27 4 109 7 109 112). The stalled seat-seekers do not stand
beside a pit that lies toward their seat -- wherever they stall, it is not the case the rule was written for.
**Not gated; uninformative as built.** Code kept as `src/cand39`; `src/bot` is g_iter7. Before another form: trace
one `@badseat` landscaper on Climb -- its tile at the stall and the eight tiles round it.

Traced (Climb, #11374, seat (4,38) west of the school): born at (5,38) with the pit (4,39) in front of it, the
navigator bug-walked east instead -- (8,37) at r165, (28,36) by r190, up the staircase -- and stalled thirty tiles
from any pit. The check has to run before the walk. **39b**: when every neighbour closer to the seat is either a
pit or unwalkable, fill the pit first; the same diagnostics.

**39b diagnostics:** the bridge fires (Climb as A 4 times, as B 12, GSF 2, RandomSoup1 1) and the rings at r500
on Climb are the controls' to the digit -- the filled pits led nobody to a seat -- while on GSF the candidate left
a ring tile at 4 the control had at 97 and lost at r933 a game the control wins at r3024: seekers were diverted
into bridging where a longer walk existed. RandomSoup1 2513 vs the control's 2445. **Not gated; refuted as
built.** Code kept as `src/cand39b`; `src/bot` is g_iter7. The flood-round line stays open with three forms
closed (38, 39, 39b); the next one must first show, on Climb, which tile the last seat-seeker needs and what
stands in its way, tile by tile, before any rule is written.

Tile by tile (Climb, `diag/cut-Climb.bc20`, HQ (4,37) at 4, the map's cliff along row 35, the map's edge along row
39): the six unseated ring tiles are the west and north ones, all at 2-4 and walkable from the west. Ten
landscapers try them in turn (`@badseat` 51 times: [4,38] first, then [5,36], [3,36], [3,37], [3,38]) and every one
ends a helper on the east side. From the school at (6,38) the north way west runs through (5,39)-(4,39): (4,39) is
the pit the seat at (5,38) dug (-9 by r200). The south way runs through (6,36)-(5,36): (6,36) and (7,36) are held
from r100 to the end by two of our own miners, boxed in by the cliff, the ring, the pit at (6,37) and the
refinery -- Iteration 32's trap, on the only other walkway. Two closed forms meet here: the seats' pits (38) and
the miners on the circle (32). A form for this map needs both: a seat leaves the circle tile beside an unseated
ring tile alone only once that tile has stayed unseated past r250 (38 paid 6% because early on every tile is
unseated), and a boxed-in miner beside an unseated ring tile steps onto it to free the way (it can go nowhere
else). Both are cheap to write; neither was tried.

## Iteration 38 -- seats spare the walkway (2026-09-25, on g_iter7)

**Evidence.** Flood-round deaths were 27 of the 159 losses of blocks 50-56 (GSF, Hills, Spiral, Toothpaste, Climb:
the HQ drowns at the map's `hqFloodRound` through a ring tile nobody seated). Climb, read (`diag/cut-Climb.bc20`,
HQ at (4,37), elevation 4, against the map's south cliff): the school stands east of the HQ; the three west ring
tiles are at 2 and never seated (`@badseat` 51 times on the six west and north tiles); the ring at r500 reads
2F 2F 2F 29 52 26 132 131 and the HQ drowns at r931. The only walkway from the east to the west ring on that map
is the two circle tiles north of the HQ, (4,39) and (5,39), and the east seats dig exactly those -- their lowest
non-ring neighbours -- to -6 by r150 and -9 by r350. Landscapers born at the school then walk at a cliff for ever.

**Candidate** = g_iter7 + before `SEATS_BY` (r400) a seat never digs a circle tile that touches an exposed ring
tile not held by one of our landscapers: the walkway stays until the seats are in. It costs nothing after r400
and, on maps where the seats fill by r200, almost nothing before. Diagnostics (VM): Climb both ways and Hills --
`@badseat` counts, the ring at r500 (no tile under water), the HQ alive past the flood round -- and RandomSoup1
plus controls on Climb and Hills (g_iter7 vs itself) for the ring at r3000.

**Diagnostics** (VM, with controls). Climb as A: the ring at r500 reads 2F 24 26 72 73 75 74 75 against the control's
2F 24 27 4 109 7 109 112 -- seven tiles seated instead of five, `@badseat` 20 against 17 -- and the west corner tile
is still under water, so the HQ drowns at r931 as before. Climb as B: identical to the control (the B side's walkway
is not the dug one). Hills as A: one flooded tile either way, the HQ drowns at r931 either way. RandomSoup1 as A:
ring 2290 at r3000 against the control's 2445 (-6%): the seats' dig restriction before r400 costs on ordinary maps.
**Not gated; uninformative as built.** Code kept as `src/cand38`; `src/bot` is g_iter7. The last unseated tile on
Climb needs its own trace (the corner beside the map's water), and the rule must not touch maps whose seats fill by
r200 -- a form that spares only walkways a landscaper is actually walking, or that a stalled seat-seeker asks for.

Census (VM, every reviewable loss of g_iter6 and g_iter7 ending within 3 rounds of the map's flood round): 89
games, **78 of them with at least one ring tile under 10 at r500** -- the flood-round death is an unseated tile in
nearly every case; GSF 19, Hills 18, Spiral 15, Climb 13, WateredDown 7, Toothpaste 5. Five maps of 52 carry it.

## Iteration 37 -- one gun on the site (2026-09-25, on g_iter7)

**Why now.** The raid table (five band opponents with clocks, a third of the band's losses) and two closed forms:
drones as guards (10, 23, 24) and the gun perches (31), which the mirror priced at 22-27% -- by what they reserved
(two sites and two stands off limits to buildings, the bank held back, gun tiles nobody could stand on or dig,
helpers pulled off their posts), not by the guns. Iteration 36 built site machinery that reserves nothing: one
Chebyshev-3 tile chosen at r100-300 far from the refinery and the school, raised to hqElev+6 by the ninth
landscaper from a Chebyshev-4 stand, built on from the stand. With a school on it the ring came out 7-12% below
the control; the school's mason also raised three posts (nine more loads) and the seats spared them.

**Candidate** = g_iter7 + that site with a **net gun** on it (250 soup, built when the site reads hqElev+6, by the
builder or the nearest miner in sight), the mason raising only the site and the stand (nine loads), nothing else
reserved, no bank held back, and nobody digs a ring tile (a genuine defect found on the way). One gun plus the
HQ is two shots a round against the raid's 17-40 drones -- not a wall, a price.

**Pre-registration.** Diagnostic: the gun standing from before r700 to about r1700 on Prison, RandomSoup1 and
Squares, and the ring at r3000 read against `ctrl7` (g_iter7 vs itself, same map and side: Prison A 2109,
RandomSoup1 A 2445, Squares B 2182) -- within 5% is the bar. Arm 1: the mirror vs g_iter7 (a small loss is
expected: 250 soup and a landscaper's thirty rounds). Arm 2: two seeded band blocks as `us:cand37`; a finding if
the losses in the raid windows (r1000-2300, not within 3 rounds of the map's `hqFloodRound`) are at most **12 of
96**. Counted before the arm runs: g_iter7 lost 58 of its 288 band games (blocks 70-75) in that window, 20.1%,
19.3 expected in 96 (cormackikkert 17, benzyx 14, EmaPajic 14, mvpatel 9); the chance of 12 or fewer at that
rate is about 4%. (The bar of 6 first written here assumed a lower rate and is withdrawn before any arm data.)
A null in both arms is a reject.

**Diagnostics** (VM, against `ctrl7`). Prison as A: site r140, mason done r297, the gun built r313, standing to
r1700 (three guns at r700, one at r1700, none at r2000 as designed), ring 2177 vs the control's 2109 (+3%).
RandomSoup1 as A: mason done r234, gun built r474, standing r1200-1700, ring 2798 vs 2445. Squares as B: two masons
claimed and neither finished, no gun, ring 2030 vs 2182 (-7%): the tiles beyond the stand are water on Squares, so
a mason there has nothing to dig from and stands idle -- two fixes, a mason that cannot dig for 20 rounds becomes
a helper, and the HQ requires two dry tiles beyond the stand when it chooses the site. Mechanism shown on two
maps of three, the ring within the bar on both. Snapshot `src/cand37`; **`gate37`** (mirror vs g_iter7, seeded)
and **`arm37a`/`arm37b`** (two seeded band blocks as `us:cand37`) running together.

**Arm 2: 46/96, raid-window losses 19 of 96 -- g_iter7's rate to the game (19.3 expected); null.** EmaPajic took
nine of them at r1528-1768 (its clock is about r1550), benzyx four at r1223-1639, cormackikkert three at r2006-2152.
**Arm 1 (`gate37`): 2-14 in the first batch**, the mirror pricing the site machinery as it priced the gun perches,
whatever stands on the site; stopped there, both arms null. **Closed: refuted.** Code kept as `src/cand37`;
`src/bot` is g_iter7. The ledger's reading of the raid line after 10, 23, 24, 31 and 37: one gun is a drop against
17-40 drones, and every pre-flood diversion of a landscaper's rounds or a tile beside the ring is priced by the
mirror at far more than the raids return on the ladder. A raid answer that the mirror can tolerate has to cost
nothing before the flood -- which leaves the bank after r700 (thousands, unspent) and nothing that can spend it
where the raid lands. That is the same wall as the post-flood producer, from the other side.

## Mirror gains and the field (2026-09-25)

g_iter7 against g_iter6 on the same opponents, weighted by g_iter7's games (distinct games, blocks 70-75 against
blocks 50-69): **44.9% vs 42.4%** -- a mirror result of 64.6% became two and a half points against the field.
Per opponent: laurenschneider 50% vs 36% (36 games), poortho 38% vs 30%, EmaPajic 37% vs 16%, wpine215 77% vs
76%, mhahn2003 75% vs 78%, benzyx 36% vs 41%, **cormackikkert 27% vs 44%** (36 vs 109 games, p about 0.06). The
same happened with g_iter5 over g_iter3 and g_iter6 over g_iter5: what the mirror rewards is what the incumbent
lacks, and the field lacks less of it. The ladder arm belongs in every acceptance, not only the raid answers.

cormackikkert, read: 17 of g_iter7's 26 losses to it end at r2139-2292 (DoesNotExist, block 70: our ring 1076 to
their 743 at r2000, then 17 drones, 185 pickups over the game, 30 dirt onto our HQ in the last 40 rounds, the HQ
shooting 37). It is the timed raid again, a thousand rounds after benzyx's, against a wall that was winning. The
raid table now reads: benzyx r1217-1228 and r1615-1624, team4 r1565-1582, mvpatel r1896-1932, cormackikkert
r2139-2292 -- five opponents of the band, each with a clock, and the HQ's one shot a round is the whole defence.

## The wall ceiling census (2026-09-25)

Every landscaper's `@wallstat` counters at r1000 and r2000 in g_iter7's 145 reviewable ladder games (blocks
70-75): **seats (n=1,105) and helpers (n=677) both run at the ceiling** -- a median of 1,000 actions per 1,000
rounds (500 digs, 500 deposits; p25 986 and 996), and only 10 seats and 6 helpers below 700. Nothing at the wall
is idle. What the ring gets is bodies: 7.6 seats and **4.7 helpers alive per game** at r1000-2000 against 16
posts, because helpers die after the flood (drowned, lifted) and the school that made them is under water by
r700. At 0.5 dirt per body-round that is 6 dirt a round over eight tiles -- the +750 per thousand rounds the
diagnostics show -- while a full second ring (8 + 16 bodies) would give 12. Iteration 35 (16 helpers before
the flood) did not change it because the pre-flood posts fill either way and the losses come later.

**Read with the rest:** g_iter7 banks 3,000-12,000 soup by r700-1000 that nothing spends; the wall race
(39% of its losses end after r3000) is decided by how many bodies stand beside the ring after r1000; every
form of buying those bodies tried so far failed on where the producer stands (Iteration 13's second school
on ground that floods, 24's perch that carried a center instead of a school, the plateau's claim churn).
The next structural candidate is the one DESIGN.md names: a school on ground raised before r700, beside
posts it can reach after the flood, spending the bank on helpers as they die. It has two prerequisites the
earlier attempts lacked and now hold -- the soup arrives before r700 (33+34b) and the seats are all taken
by r400 (25). The first job is the geometry: which distance-2 posts stay reachable from a raised
distance-3 tile after r1000, on the corpus.
Geometry census (the r1 elevation grids of 51 maps, 102 HQs): posts within 3 of the HQ's height that have a
distance-3 neighbour within 3 of the post and 6 of the HQ -- median 16 per HQ, 88 HQs with at least 8, two with
none (InADitch, the HQ in a pit at -5), Climb and Swirl with 3-4. Before the game digs the ground, nearly every
HQ has the geometry.

## Iteration 36 -- the replacement school (2026-09-25, on g_iter7)

**Candidate.** At r100 (refinery and first school placed) the HQ picks one Chebyshev-3 tile S: dry, within 6 of
its height, with a Chebyshev-4 stand beyond it within 3, at least two dry distance-2 posts adjacent to it, and
as far from the refinery and the school as possible; it posts S every 10 rounds to r800. The ninth landscaper
(or a helper that finds S unclaimed) is the mason: stands on the stand, raises S to hqElev+6 and the stand to
hqElev+3, digging from Chebyshev 4, then becomes a helper. Once S reads hqElev+6 the builder (or the nearest
miner) builds the second design school on S from the stand. From r650 that school builds a landscaper onto a
free adjacent post whenever the bank holds 250, and never otherwise: the replacement takes the post (a seat
if one is free and climbable, else the post). Nothing is reserved from other buildings except S and its stand,
no tile is off limits to stand on, and the mason is drawn after the eight seats and eight helpers are filled.

**Why this form is not a closed one.** Iteration 13's second school stood on ground that flooded and built
landscapers for posts that did not exist; 24's perch carried a center; 31 reserved tiles the base needed. This
school stands at hqElev+6 (dry to about r1700), spawns only onto a post that is free, and the posts beside it
stay dry because their helpers keep them so. Diagnostic (VM): RandomSoup1 and Prison vs g_iter7 -- the second
school alive at r1500, helpers alive at r1000-2000 above the incumbent's own, ring at r2000-3000 higher.

**Diagnostics** (VM; RandomSoup1 both ways, Prison vs g_iter7). The site machinery works: site chosen at r100-140,
mason done at r234 and r319, the second school built at r433 and r363 and standing to r1500 on Prison (DS=2 from
r700). **Not one replacement was spawned in three games.** Prison, read from the board: the school at (1,5) is
hqElev+6 = 11; its three distance-2 neighbours are two posts held by live helpers all game and one tile the seats
dug to -9. A post that came free would sit at hqElev+2 or so (its helper keeps it at water+2), 4 or more below
the school, and the engine refuses a spawn more than 3 below the builder -- the same rule that shaped the gun
perch. On RandomSoup1 as B five masons claimed the site and none finished (no stall logged: each found the stand
taken or gave the role up silently). Rings: RandomSoup1 2539 vs 2610 and 2721 vs 2827 (lost both), Prison 2184
vs 1780 (won, the side that wins there regardless). **Paused, not gated;** code kept as `src/cand36`, `src/bot` is
g_iter7. What the next form needs: the mason raises the two or three posts beside the site to hqElev+3 as well
(9 loads), so a freed post is within 3 of the school; the school may spawn onto its Chebyshev-3 neighbours too,
which the helpers must then not dig; and the capacity question -- two or three posts per school -- says two
sites on opposite sides, or the school placed where the most posts touch it. The claim churn (five masons)
wants the HQ's manned bit read before a claim, as the gun perch found.

**36b** (the mason steps onto the site and raises the posts beside it to hqElev+3; seats never dig those posts;
claims throttled): the replacements fire -- Prison as B 17 spawned (L=40 at r1000), RandomSoup1 as A 26 -- and
the ring is **lower**: Prison 1367 vs 2190 (two tiles at 890 against 964 on the others at r2000), RandomSoup1
2449 vs 2630; Prison as A 0 replacements (the posts beside the site never freed), ring 1979 vs 1779. Read from the
board: a newborn on a post looks for a seat first, stalls under a cliff, strikes posts off and ends an attacker;
attackers "carry a load before walking over" by digging any empty adjacent tile -- including a dead seat's ring
tile, which they dug down beside the school. Both are fixed in **36c** (a newborn beside the school holds its own
post; nobody digs a ring tile or the posts beside the school), diagnostics running.

**36c**: Prison as B is the same game as 36b's to the digit (17 replacements, ring 1367 vs 2190): the HQ stops
posting the site at r800, so a replacement born after that never learns it, hunts a seat and ends an attacker as
before -- the fix did not reach them. RandomSoup1 as A: 6 replacements, one held its post, ring 2467 vs 2630.
Prison as A: no replacement (the posts beside the site never free), 1979 vs 1779. Three rounds of diagnostics
say the same thing: a second school on raised ground can be built and can spawn, and the bodies it spawns do
not become ring height. **Closed for this session, not gated**; code kept as `src/cand36c`, `src/bot` is
g_iter7. If reopened: the site posted until r2000 (the replacements' whole life), replacements limited to the
posts the mason raised, and the question answered first whether a helper born at r800 on a raised post adds
dirt to the ring at all -- the r1000-2000 census says a live helper does 500 deposits a thousand rounds, so the
bodies here must be dying or standing somewhere the ring cannot see.

Answered from the one replacement that held its post (RandomSoup1, #12074, born r706 on (16,7)): it fed the ring
40 loads by r800, 139 by r1000, 337 by r1400 -- 50 deposits a hundred rounds, the ceiling -- and stood on a post
that rose from 7 to 9 with the water, alive past r1600. The bodies work; only one of 24 replacements became one,
because the HQ stops posting the site at r800 and a replacement born after that never learns it. **36d**: the
site posted until r2000, and the diagnostics again.

**36d: no change** -- Prison as B the same game to the digit (17 replacements, none a helper, 1383 vs 2190). The
posting window was not the reason. Read again: `Robot.loop` calls `init()` before it sets `round`, so the newborn's
scan of the last 25 blocks ran from round 1 to round 0 and read nothing; every landscaper born after the first
posts learned the site only by the every-third-round block read, if at all (the gun perch's newborn scan had the
same flaw). **36e**: the scan reads `rc.getRoundNum()`; diagnostics on Prison both ways, RandomSoup1, Squares.

**36e**: the newborns now hold their posts -- and there is only one replacement per game, because the school
spawns only onto a free post and the posts beside it free once. Rings at r3000, candidate vs the incumbent's side:
Prison as B 1566 vs 2190 (two enclosed tiles lower, as before), Prison as A 1969 vs 1787, RandomSoup1 as A 2502
vs 2706, Squares as B 1952 vs 2189. The same side wins Prison whatever the build, so these are read against a
control -- g_iter7 against itself on the same maps (`ctrl7`), which this line never had.

**Control (`ctrl7`, g_iter7 vs g_iter7, rings at r3000):** Prison A 2109 / B 1780; RandomSoup1 A 2445 / B 2616;
Squares A 2329 / B 2182 -- the side effects are 7-18%, as large as anything the candidates showed. Read against
it, 36e's ring is **7% lower** as Prison A (1969 vs 2109), **12% lower** as Prison B (1566 vs 1780), **11% lower**
as Squares B (1952 vs 2182) and 2% higher as RandomSoup1 A (2502 vs 2445). One replacement a game does not pay
for the mason's rounds, the undug tiles beside the site and the school itself. **Closed: refuted by controlled
diagnostics, not gated.** Code kept as `src/cand36e`; `src/bot` is g_iter7. Method lesson written into
TRAINING_ALGORITHM 4.3: a diagnostic's intermediate is read against the incumbent playing itself on the same map
and side, never against the opposite side of the same game.



## Iteration 35 -- sixteen helpers on the new economy (2026-09-25, not gated)

Iteration 12's re-open condition was soup before the school drowns; g_iter7 banks 3,000-12,000 by r700-1000.
Candidate = g_iter7 + `WALL_HELPERS` 16 (every distance-2 post). Diagnostics (VM, four games, ring minimum at
r3000, candidate against the incumbent's own side): RandomSoup1 as A 2446 vs 2739, as B 2727 vs 2532; Prison as A
2089 vs 1893; Squares as B 2174 vs 2264 -- two up, two down, and in every game the side that won the ring race
was the same side regardless of build. Both builds reach `LANDSCAPERS_MAX` (24) by r700; the sixteen posts fill
(L=24) and the ring grows no faster: eight seats plus sixteen distance-2 posts are every tile adjacent to the
ring, so the intake ceiling (0.5 dirt per body-round, about 1.5 height a round over eight tiles) is reached
either way, and the eight attackers the incumbent sends instead cost nothing the ring can see. **Not gated;
`src/bot` back to g_iter7.** Kind: uninformative as built. The wall race is a ceiling contest: the next census
is where seats and helpers lose turns against that ceiling (Iteration 12's idle census, on g_iter7's own games).

## Iteration 34 -- a full miner that cannot get home (2026-09-25, stacked on 33)

**Census** (VM, the 311 reviewable losses of blocks 50-69, every miner's `@minerstat` at r300 and r500): of
1,903 miners alive at both rounds, **895 (47%) mined nothing between them**; 249 losses had an idle miner and
103 had every miner idle. By map: GSF 105 idle miners, Islands2 52, Toothpaste 50, TheHighGround 48, Climb 48,
Hourglass 40, AMaze 39, Egg 35, Squares 34, Maze 33 -- the map-dead maps first, then everywhere.

**Traces.** Islands2 (laurenschneider, block 59) miner #11226: r280-520 on a four-tile circuit beside our own
HQ, no log line of any kind, `unreachable=0`. GSF (cormackikkert, block 50) miners #10140 and the builder
#12669: on one tile from r280 to r520, no log line. Neither reaches the code that marks soup unreachable
(step 4 of `work`) or the explore step: they are in step 2, a full load walking to a drop-off -- the refinery
cut off by the seats' pits, and the HQ forbidden because the ring is (`avoidRing` once a refinery exists) --
which had no stall handling; the builder's walks to its circle and stand had none either. Iteration 33 (loads
of 70 before a deposit) makes this worse, which is why its mining actions rose and its bank did not.

**Candidate** = cand33 + in step 2, a stall toward the refinery marks it unreachable for 200 rounds and sends
the miner to the HQ with the ring allowed (Iteration 29's rule); a stall toward the HQ as well lets the miner
mine a quarter of the time instead; a builder whose walk stalls pauses building for 50 rounds. Diagnostic
(VM): Islands2, GSF, RandomSoup1 vs g_iter6 -- idle miners r300-500 per side and the stall tags; expected: idle
miners well below the incumbent's own side, mining past r300. Gate: the mirror vs g_iter6 (the stack 33+34).

**Diagnostics** (VM). First form (stall toward the refinery -> the HQ with the ring allowed): on Islands2 every one
of our eight miners was still idle from r300 -- by then the ring is a cliff, so the HQ is no drop-off either, and
a full miner with nowhere to unload is dead weight for the rest of the game (Iteration 33's 70-soup loads make
it worse). **34b**: a full miner whose walk to a drop-off stalls builds a refinery where it stands when no
refinery is within its sight and the bank holds 200 -- the field's pattern (3-4 refineries by r300 to our 1).
Candidate (33+34b) against g_iter6's own side, mining actions by r600 / bank at r600 / idle miners r300-500:
Islands2 800 vs 444, 2,583 vs 311, 6 of 8 vs 5 of 8 (the island's soup runs out at r400 for both); GSF 292 vs
231 (the map-dead map: unchanged, 3 of 3 idle both sides); RandomSoup1 1,414 vs 947, 4,521 vs 813, 0 of 9 vs 1
of 8; Prison 886 vs 711, 588 vs 766, 2 of 11 vs 5 of 11. Refineries built on the spot: 1, 0, 2, 6. Four wins
of four. Snapshot `src/cand34`; **`gate34`** (the stack vs g_iter6, seeded) running.

**Gate 34: ACCEPT at 62-34 (64.6%)** after six batches, every game seeded and distinct (8-8, 11-5, 12-4, 11-5,
10-6, 10-6). Snapshot **`src/g_iter7`** (the VM's gated `src/bot`, checksum identical to the driver's and to
`src/cand34`) is the incumbent and the submission: g_iter6 + a miner deposits only a load of 70 or once the
soup within reach is gone (33) + a full miner whose walk to a drop-off stalls builds a refinery where it
stands (34b) + the builder's walks pause on a stall. Blocks 70-71 (`BOT=g_iter7`, seeded band blocks) and the
archetype regression `regr7` (vs `arch_swarm`, quick set; g_iter6 was 21/24) running. The third acceptance
since the restart: seats first (25), the rush answer with buildings off the bypass (29b), and now the miners
kept working.

Archetype regression `regr7`: **21/24** vs `arch_swarm` (g_iter6 21/24). Blocks 70-71 (`20260925-003836`, `-003842`):
**29/48** and **31/48**; g_iter7 rating 1766 +- 77, rank 16 of 74 after 96 games. Blocks 72-73 (`20260925-010839`, `-0108xx`) recorded: g_iter7 rating 1746 +- 54, rank 16 of 74 after 192 games. Blocks 74-75 (`20260925-012550`, `-012603`): 16/48 and 26/48; g_iter7 rating 1745 +- 44, rank 16 of 74 after 288 games: the incumbent blocks stop here (owner, PROMPTS 26).

## Iteration 33 -- a deposit worth the action (2026-09-24)

**Evidence** (the Iteration 32 traces): a miner beside the HQ or a refinery deposits whatever it carries every
turn it can, so a miner mining next to a drop-off spends an action on every 7 soup: 13-22 deposit actions per
30 mining actions by r100 in the traced ladder losses (RandomSoup1, Islands2), against the field's one deposit
per 14 mining actions. **Candidate** = g_iter6 + a miner deposits only a load of at least `SOUP_RETURN` (70), or
whatever it carries once no soup is left within reach (it is about to walk anyway).

**Diagnostics** (VM; RandomSoup1 both ways, Prison): mining actions by r300 on the same side, candidate against
the incumbent's own -- side A 557 vs 432, side B 865 vs 696 (RandomSoup1); Prison 479 vs 454. The bank runs
lower early (soup in the miners' hands: r100 42 vs 110 and 128 vs 178) and level by r300 on side B (521 vs 551).
Mechanism shown (fewer deposits, more mining); the mirror prices the timing. Snapshot `src/cand33`; **`gate33`**
(mirror vs g_iter6, seeded) running.

**Gate 33: 132-108 (55.0%) over 240 seeded games, SPRT inconclusive: kept provisionally** (TRAINING_ALGORITHM 4.4:
at least 53% over at least 200; the first gate whose 240 games are all distinct). Not snapshotted or submitted;
the next candidate stacks on it (`src/bot` = cand33).

## Iteration 32 -- miners off the circle (2026-09-24, PROMPTS 25)

**Evidence.** The merged onset (786 games) puts net worth (us-them) at r150 and mines, robots, spawned at r200
before everything else. The census at those rounds (blocks 50-69, medians): we field 8 miners to the field's 4
and out-mine it early (309 mining actions to 231 by r200), yet per miner we do 39 actions to their 61, and in
the games we lose the opponent's mining overtakes ours between r300 and r500 (483 to 416). Two reviewable
losses traced: RandomSoup1 (poortho, block 62) -- our eight miners' `mined` counters stop at r200 (155 by
r100, 210 by r200, 210 at r300) and **not one miner moves between r200 and r400** (55 mining actions, 4
deposits, 0 moves in the whole team); miner #10821 stood on (20,7), a Chebyshev-2 tile, from r100 to r300.
Islands2 (laurenschneider, block 59): the same freeze from r300 (mined 82/51/62/20 at r300 and at r500),
coverage 1.4% of the map against their 14.5%, 2,447 moves against 16,651. The seats dig the Chebyshev-2
circle into pits (their dirt source) and the helpers dig Chebyshev 3; a miner standing on the circle between
them has no legal step (ring, pits, buildings) and stays there for the rest of the game. Iteration 28 met the
same trap made by the refinery; this is the general form.

**Candidate** = g_iter6 + miners keep off the Chebyshev-2 circle once a refinery exists (the builder from the
moment its school stands), stepping outward if caught on it. Deposits at a refinery at Chebyshev 3 (28b) are
made from Chebyshev 4. Diagnostic: RandomSoup1 vs g_iter6 -- mining actions past r200 and moves in r200-400
against the incumbent's own. Gate: the mirror (an economy change is priced there); no second arm.

**Diagnostics** (on the VM: the driver's two cores were shared with a study and a compile took ten minutes).
First run: newborn miners could no longer step off the ring (the only non-ring neighbours are circle tiles),
sat on seats, the ring never sealed and the HQ drowned at r945 -- the circle is now a transit tile for a
miner leaving the ring, and a miner caught on the circle with nothing outward open slides along it. Then,
RandomSoup1 vs g_iter6 both ways and Islands2: the candidate's miners never stop (RandomSoup1 973 and 1,200
mining actions by r900 in the two games; the incumbent's own side 1,375 and 1,296 -- no freeze in those
seeds); on Islands2 the incumbent's miners froze at 258 from r200 to r600 as in the ladder loss while the
candidate's went on to 349 and the candidate won (r2998). The five-miner start of the first RandomSoup1 game
was the seed, not the rule: with the sides swapped the candidate built nine. Mechanism shown. Snapshot
`src/cand32`; **`gate32`** (mirror vs g_iter6, seeded, six jobs) running.

**Gate 32: REJECT at 77-83 (48%)** after ten batches -- neutral. The freeze is real and gone, but the
mirror's wall race does not pay for it: a circle-free miner mines the near-HQ soup from one tile further out,
walks round the circle to deposit, and the freeze itself only bites in some games and seeds (the incumbent's
own side did not freeze in two of three diagnostic games). Kind: refuted as built (priced at zero). Re-open
as a narrower form: free only a miner that has had no legal step for 20 rounds (it cannot move, so the form
must be prevention -- seats leaving the four axis tiles of the circle undug and helpers the tile outward of
each, a gate per side), and test on the ladder as well as the mirror, since the freeze showed in ladder
losses against strong economies. Code kept as `src/cand32`; `src/bot` is g_iter6 again.

## Iteration 31 -- gun perches (2026-09-24, PROMPTS 24-25)

**Where the losses are** (blocks 50-56, 336 games, 159 losses; scores and map data only, so every
opponent counts): by end round 12 before r500, 21 by r1000, **42 between r1000 and r2000 (8 wins
there)**, 36 by r3000, 48 after (106 wins after r3000: the long wall race is ours). 27 losses end
within 3 rounds of the map's `hqFloodRound` (GSF, Hills, Spiral x3, Toothpaste x4: the map-dead
line). The other mid-game losses end at rounds fixed by the opponent, not the map: benzyx r1217-1228
and r1615-1624, team4 r1565-1582, mvpatel r1896-1932 -- timed raids, as Iterations 10 and 23 found
for g_iter3 (then 274 of 616 losses; now 42 of 159, 26%).

**The raid, traced** (benzyx, reviewable; MoreCowbell r1596-1623 and OmgThisIsProcedural
r1201-1217): our ring is sealed at 500-740 on every tile; 20-40 drones arrive together, lift all
eight seats in 5-8 rounds (each dropped in water), drop their own landscapers on the freed ring
tiles, and bury the HQ (50 dirt) in 10-15 rounds. The HQ shoots one drone a round throughout (17-43
kills per game) and it is not enough. In the 16 distinct reviewable mid-game losses, 11 are this
raid against a sealed ring; 5 are flooded or unseated ring tiles (Toothpaste, Hourglass, Climb).

**Onset evidence** (owner, PROMPTS 25; `onset.py` over the merged study tables of blocks 50-56, 213
games, noise floor 0.14 instead of one block's 0.30): earliest risers are landscapers (us-them) from
r350 (peak +0.53), robots r350, digs and vaporators r500 (+0.52, +0.32), dirt deposits r550;
**pickups (us-them) from r750 (+0.32)** -- being lifted predicts losing from r750 on; drones
(us-them) never rise above +0.20 and net guns +0.18 (we rarely have either past r700). Read: the
body count at the wall is the earliest signal (the economy line, still open); the raid signal is
real and later; drone counts by themselves do not predict, which is consistent with Iterations 10,
23 and 24 (drones as guards: closed).

**Candidate** = g_iter6 + two net guns that outlive the flood, in a form not yet tried: the HQ
picks at r3 two Chebyshev-3 tiles on opposite sides (E/W, N/S, then the diagonals; dry, within 6
of its height, each with a dry Chebyshev-3 neighbour as a stand) and posts them (`Comms.GUN_SITES`,
r5 and every 20 rounds to r800); the first landscaper born after the eight seats claims a site
(the **gunner**): stands on the stand, raises the gun tile to hqElev+6 (never more than 3 above
its own tile) and the stand under itself to hqElev+3, digging from Chebyshev 4, then becomes a
helper; the builder, once a site reads hqElev+6, walks onto the stand and builds the gun (250,
before vaporators); nobody digs a gun tile or its stand and no other building takes one. A gun
tile at hqElev+6 (9 on most maps) floods at about r1720, after benzyx's r1220 and r1620 raids
and team4's r1567. 80 of the 102 HQs on the corpus have such an opposite pair (elevation census,
2026-09-24). Cost: about 9 loads of dirt and one landscaper for ~30 rounds, plus 500 soup.

**Pre-registration.** Arm 1: the mirror vs g_iter6 (`gate31`, seeded games); the mirror never raids,
so it prices only the cost -- a reject there is expected and not decisive. Arm 2 (the only raiding
opponents are on the ladder; `arch_swarm` beat g_iter6 only in r3000+ wall races): two 48-game band
blocks of the candidate as `us:cand31`, seeded. **A finding if the candidate loses at most 5 of the
96 games between r1000 and r2000** (g_iter6: 42 of 336, 12.5%; the binomial chance of <= 5 of 96
at that rate is about 2%) and its distinct-game win rate is within noise of g_iter6's; a null in
both arms is a reject. Diagnostic before either: a gun standing on a raised tile by r700, dry at
r1600, on a map where g_iter6 lost to the raid (`diag/gun-Omg.bc20`, vs arch_swarm).

**Diagnostics** (nine runs, OmgThisIsProcedural and RandomSoup1 vs arch_swarm; the fixes each one forced):
(1) helpers learned the sites too late to take the gunner role -- newborns now scan the last 25 blocks and helpers
re-check every 15 rounds; (2) the builder read the post after placing the refinery on a stand -- the HQ posts at r5
and every 10 rounds; (3) the stand was chosen as the highest neighbour, a 10-high tile nobody could climb -- within 3
of the HQ's height; (4) the gunner re-entered the role block every turn and got seated on the ring -- guarded; (5) the
gunner refused a site out of its sight -- it walks there and looks; (6) the builder was boxed in by seat-dug pits on a
bypass tile for 300 rounds -- any miner may build, the nearest in sight first; (7) miners queued on the gun tile to
reach the refinery -- gun tiles are off limits to stand on; (8) an arriving miner's own-tile nav step walked it off the
stand before it was ready -- it waits; (9) a stand at Chebyshev 3 was unreachable from the far side because the seats'
pits cut the base into compartments -- the stand is the Chebyshev-4 tile beyond the gun, on open ground, and only
the gunner may stand on it. Final state: RandomSoup1 -- sites raised r281 and r309, the east gun built r306 (the west
site raised but its gun missed: the nearest miner looped on the pits north of the base); OmgThisIsProcedural -- gun
built r680 (r601 in an earlier run), dry to r1700, the HQ ring 805 at r1600. One gun per game is reliable, two is not
yet; the mirror prices what stands. arch_swarm never approached a gun (it flees them by design), so the shooting is
read from the ladder arm's benzyx replays. Snapshot `src/cand31`; **`gate31`** (mirror vs g_iter6, seeded, cap 240) and
**`arm31a`/`arm31b`** (two 48-game band blocks as `us:cand31`) running together on the VM.

**Gate 31: REJECT at 7-25 (22%)** after two batches. The loss on BeachFrontProperty (team B, r3090, a wall
race): our gun stood from r300, but landscapers 11-13 against g_iter6's 12-16 and mining 501 against 568.
The reserve that holds 250 for a raised site stays on for as long as the site has no gun -- the second site
was raised and never built -- so the school built helpers only above 550 and attackers above 950, and the HQ
its replacement miners only above 450, for the whole game. The guns cost nothing like 78% of games; the
throttle did. Kind: refuted as built. The ladder arm (`arm31a`/`arm31b`) runs to completion: it says whether
the guns bite against real raids, which decides whether a 31b (the reserve and the builder's site duty bounded
to before r700 and to 150 rounds after a site is raised) is worth a gate.

**Onset, blocks 50-69 merged** (`progress/ONSET-merged.md`, 786 games, noise floor 0.07): the earliest risers
are now **net worth (us-them) at r150 (+0.44)** and mines, robots, spawned at r200 (+0.40 to +0.52), before
landscapers at r350 (+0.58) and digs at r550 (+0.59); pickups (us-them) rise at r550 (+0.37); drones, net guns,
vaporators, miners and soup never pass +0.30. The early economy -- how much has been mined by r200 -- is the
earliest signal in the whole record; the raid signal follows it by 400 rounds.

**Arm 2 (`arm31a`/`arm31b`, 96 seeded band games as `us:cand31`): 52/96 (54%), rating 1701 +- 76; mid-game
losses 6 of 96 -- a null by the letter (the threshold was 5)**, but two of the six are flood-round deaths on
map-dead maps (DidAMonkeyMakeThis r1210, Toothpaste r1226), not raids, and the other four are benzyx on
Infinity three times (r1217-1220) and laurenschneider on Climb r1547. Against g_iter6's 12.5% mid-game loss
rate the expected count was 12. On Infinity (HQ at elevation 0, everything drowns by r600) the gun stood from
r600 and was gone before the raid. Both arms null: **cand31 is refuted as built**; the bounded form
**31b** (`src/cand31b`: no reserve, builder duty or gunner after r700) is in `gate31b` on the strength of the
arm's direction; if that gate is not a clear reject it gets the arm again with the flood-round deaths excluded.

**Gate 31b: REJECT at 13-35 (27%)** after three batches: bounding the reserve did not recover the mirror, so the
reserve was not the main cost. What remains in the candidate: two gun tiles and their stands reserved from
buildings (the refinery and school fell back onto the Chebyshev-2 circle in the diagnostics, Iteration 28's
sealed-miner failure), gun tiles and stands off limits to stand on or dig, helpers leaving their posts to
become gunners (13 claims a game), miners leaving the soup to build. Each is small; the mirror's wall race
is decided by small margins (BeachFrontProperty: 11-13 landscapers against 12-16). **Closed: priced far below
the gate (22%, 27%), and the ladder arm null (6 mid-game losses of 96 against a threshold of 5).** Re-open
only with a placement that reserves nothing the base needs (a site chosen after the refinery and school
stand, on the side away from them) and a gunner drawn from the attackers rather than the helpers. Code kept
as `src/cand31` and `src/cand31b`; `src/bot` is g_iter6 again.

## The fixed seed (2026-09-24, PROMPTS 24-25)

Four benzyx MoreCowbell losses in blocks 50-56 were the same game to the round. The engine seeds its
robot IDs and every sandboxed `Random` from the map file's seed (`LiveMap.getSeed`), so a pairing on
the same map and side replays identically: of 336 games in blocks 50-56, 38 pairings recurred and
all 38 gave identical results (41 games were repeats); over `games.csv`, 491 of 2,825 games were
repeats -- 320 of g_iter3's 1,116. The mirror gate draws map and side with replacement from 104
cells, so a 240-game gate has at most 104 distinct games and its SPRT counts repeats as evidence:
gate29b's 160 games held 83 distinct cells (77 repeats, 71 identical); the distinct-cell record
was 48-35 (58%), the same direction as the reported 72-40 but far weaker.

**Fix:** `tools/build-engine.sh` patches `LiveMap.getSeed` to honour `-Dbc.game.seed`; `gauntlet.sh`
draws a random seed per game (the cell's 4th field when given) and records it as an 8th column;
`scrim-record.py` carries it into `games.csv` (new `seed` column, empty for every game before
today); `elolib.fit` counts one game per (teamA, teamB, map, seed). `run-dev.sh`/`run-match.sh`
keep the map seed unless `GAME_SEED` is set, so diagnostics stay reproducible. Ratings on distinct
games: g_iter6 1740 +- 43 (295 of 336), g_iter5 1743 +- 33 (618 of 720), g_iter3 1700 +- 32
(796 of 1,116): the order is unchanged, the intervals were overstated. Every gate before today
was overconfident in the same way; none is re-run (their directions stand), but from block 59 and
the next gate on, every game is a new game. Verified on the VM (`seedtest2`): g_iter6 vs g_iter5 on
maptestsmall with seeds 1, 2, 1 gave 3109 (win), 3094 (loss), 3109 (win).

## Block 50 and the unspent bank (2026-09-24)

**Block 50** (`20260924-180832-scrim-g_iter6`, g_iter6's first): **18/48**; g_iter6 1677 +- 107 (one
block; the upper end is above g_iter5's 1740, so no withdrawal). Archetype regression (`regr6`/`regr5`,
quick set vs `arch_swarm`): g_iter6 21/24, g_iter5 19/24. team4 won five games at r1565-1570 (a
timed attack), but it is locked at 17% against the latest build: not reviewed.

**Census of the reviewable losses of blocks 48-50** (final line of each replay, our side): 30 of 30
listed end with M=0, DS=0, FC=0 (one M=10 on Climb) and a bank of **1,230 to 10,671 soup**, 18 of them
above 2,500. benzyx on RandomSoup1: V=6, DS=1, FC=1, NG=2 at r700, all gone by r1000 (flooded
283 -> 942 tiles), then 3,688 soup unspent at death (r1226) against 129 enemy drones. The HQ is
the only producer left, and with the ring seated it has no free tile to spawn on. **Next line:**
a producer that survives the flood (the perch of Iteration 24 did this but spent on fixed guards;
this time the question is what the bank should buy -- helpers for the wall, or drones/net guns
against the r1200-2000 drone wave), starting with where each of these losses' buildings died.

## Iteration 29 -- rush response, stacked on 28b (2026-09-24)

**Gate `gate28c`: 92-100, SPRT REJECT**: the stack loses its newest member; `src/bot` is back to 28b.

**Trace** (open line 2; poortho is a target-tier opponent at 33%, so its losses are reviewable):
block 45 on Europe, lost at r119. poortho plants a design school beside our HQ at ~r55, its
landscapers bury the HQ from r85 (50 dirt by r119). We had 168 soup at r50 but built the refinery
(r70) and then the school (r80); two landscapers dug against five.

**Archetype `src/arch_rush`** (new sparring partner, from g_iter5): the HQ's second miner reads the
enemy HQ from the enemy's round-2 chain post (the g_iter family's own codec; a stand-in for the
field's scouting), walks there, plants a school within distance^2 18 at 150 soup (r57 on Europe,
like poortho); the builder holds its buildings until r150; the forward school builds 10
landscapers that attack. g_iter5 as team A on Europe: HQ buried, dead at r256.

**Candidate** = 28b + `Robot.rushSeen` (an enemy school or landscaper within distance^2 64 of our
HQ before r400): the builder builds the school before the refinery, and the HQ builds no miner
until our school stands. Diagnostic vs arch_rush on Europe (team A): the HQ held its miners (6 vs
g_iter5's 8), the school came at r64 instead of r86 and away from the enemy school; HQ buried at
most 4, won r932 on tiebreak. (The builder's own trigger did not fire: the enemy school was out of
its sight; the HQ's did.)

**Pre-registration.** Arm 1: mirror vs g_iter5 (`gate29`). Arm 2: vs `arch_rush`, MAPSET=quick,
both sides, 24 games for the candidate (`rush29c`) and 24 for g_iter5 (`rush29b`); a finding if the
candidate wins at least 5 more. A null in both is a reject.

**Arm 2 result: candidate 8/24 (`rush29c`), g_iter5 12/24 (`rush29b`).** Early deaths (< r400) 6
against 5; the candidate lost both maptestsmall games g_iter5 won. Diagnostic (maptestsmall, team
A): the rush trigger fired at r42 and the school stood at r43, but income stopped (soup 129-148
from r80 to r100, two landscapers): with the school before the refinery, a seated landscaper set
`ringSeen`, miners kept off the ring, and the HQ was the only drop-off. **Bug, not a verdict.** Fix
(29b): miners avoid the ring only once a refinery exists. Same game: six landscapers by r140, HQ
never buried, won r257. `gate29` (the buggy candidate, 92-68 at the stop) is **voided**; `gate29b`
and `rush29d` run the fixed candidate under the same pre-registration (g_iter5's 12/24 baseline
stands: neither side of it changed).

**Gate `gate29b`: ACCEPT at 72-40 (64.3%)** after seven batches. Arm 2 (`rush29d`): 13/24 against
g_iter5's 12/24, a null there: the rush response does not measurably beat `arch_rush`, and the
mirror's gain comes from the stack as a whole (28b's outward buildings plus the rush response and
the drop-off fix). Snapshot **`src/g_iter6`** (the VM's gated `src/bot`, checksums identical) is the
incumbent and the submission; block 50 is its first ladder block, and `regr6` plays g_iter6 and
g_iter5 against `arch_swarm` (quick set) as the archetype regression.

## Iteration 28b -- refinery and school off the Chebyshev-2 circle (2026-09-24)

**Candidate** = g_iter5 + the builder chooses once, among the Chebyshev-2 tiles within 6 of the
HQ's height, the stand whose buildable outward neighbour (Chebyshev 3, within 3 of the stand's
height, as the engine requires) is highest, walks there for up to 40 rounds, and builds the
refinery and school outward; the circle itself only when nothing outward is free.

**Diagnostics** (vs g_iter5): Swirl unchanged from g_iter5's own choice (refinery (1,37), school
(0,37), both at distance 3; won r3038). Climb: stand (6,38), refinery (7,37) off the arc, miners
free; the school drifted west to (1,37) and flooded; HQ alive to r1547 instead of r931, 1929 digs
by then instead of 487 at r931. Three versions were needed: the nearest stand (west, height 2,
flooded), a stand scored by unreachable 99-high wall tiles, then the height limits above.

**Gate `gate28b`: 129-111 (53.8%) over 240, SPRT inconclusive: kept provisionally** (TRAINING_ALGORITHM
4.4: >= 53% over >= 200; code at commit 79fd4a5). Strong on ALandDivided 6/6, GSF 7/7, Swirl 5/5,
Constriction 6/6; Toothpaste 0/7, CentralSoup 0/5, Climb 0/5.

**Iteration 28c** (stacked revision, tested against g_iter5): Toothpaste diagnostic showed the
stand scored mainly by height was an unclimbable pillar (height 8 among 2s) and the refinery came at
r131 instead of r57. Now the nearest stand wins (a higher outward site is worth at most 8, against
4 per unit of squared distance), and the 15-round walk limit counts walking rounds only: on Climb
the builder reached its stand, went back to mining while soup was short, and when soup returned the
wall-clock limit had run out and it built on the arc. Diagnostics: Climb refinery (7,37), HQ alive to
r1547; Toothpaste refinery r96 at distance 3, lost r2964 (28b's version won r3056 on the second
try); Swirl distance 3, won r3044. **Gate `gate28c`** running.

## Blocks 42-49 -- g_iter5 on the band (2026-09-24)

Block 42 (`20260924-145032`): **24/48**. Block 43 (`20260924-150252`): **30/48**. g_iter5 now
1714 +- 40, rank 16 of 71, field score 70.7%.

Block 44 (`20260924-153525`): **29/48**. Block 45 (`20260924-160816`): **34/48**. g_iter5 now
1738 +- 35, rank 16 of 71, field score 72.4%.

Block 46 (`20260924-163339`): **19/48**. Block 47 (`20260924-164120`): **28/48**. Block 48
(`20260924-170808`): **21/48**. Block 49 (`20260924-175104`): **23/48**. g_iter5 final: 1745 +- 30,
rank 16 of 71, field score 72.8%, 375/720 on the ladder.

## Block 41 -- g_iter5 on the band (run 20260924-143804-scrim-g_iter5, 2026-09-24)

48 games: **34/48 (71%)**. g_iter5 now 1705 +- 47, rank 16 of 71, field score 70.1%. Block 42 running.

## Iteration 28 -- a building must not cut the miners' path (2026-09-24)

**Trace** (open line 1, map-dead games; `diag/iter27-Climb.bc20`, miner #12659): Climb gives each
base a strip four rows high against the map edge, with a 3-high staircase climbing east. Miners
avoid the eight ring tiles once a refinery exists, so the only way past the HQ is the Chebyshev-2
arc, and the builder put the refinery on that arc at (5,39) at r46. Every miner west of the HQ was
sealed in: #12659 mined until r63, then walked a 12-tile loop behind the HQ carrying its soup to
r900 (mined 10, deposited 0); team mines stayed at 111 from r100 to r500.

**Candidate** = g_iter5 + `Miner.cutsPath`: the builder skips a site whose walkable neighbours
(not ring, not HQ, not a building, within 3 of the site's height) fall into more than one group
(`Nav.groups`, unit-tested). On an open map the Chebyshev-2 circle is a closed loop and nothing
changes; on an edge HQ the arc's inner tiles are refused.

**Diagnostic** (`diag/cut-Climb.bc20`, vs g_iter5): the refinery went to (7,37) and the school to
(6,38). Mechanism shown:

| r900, us | g_iter5 (iter27 diag) | candidate |
|---|---|---|
| mines | 196 | 337 |
| landscapers | 2 | 13 |
| dirt deposited | 459 | 800 |

The game still ends at r931: the three west ring tiles (height 2) flood at r500, and the HQ
(height 4) drowns when the water reaches 4. Only two landscapers ever seat; six pick west seats
they cannot reach (`@badseat`), because digging beside the east seats turns row 39 into a cliff.
That is the next defect on this map.

**Gate `gate28`: 24-40, SPRT REJECT.** Losses spread over 20 maps; Swirl 0/5, TheHighGround 0/3,
Climb 0/3. Diagnostic on Swirl (`diag/cut-Swirl.bc20`): corner HQ, three ring tiles; the group test
refused g_iter5's distance-3 sites on rough ground and the school went onto the Chebyshev-2 arc
(1,38), a helper post; our ring reached half of g_iter5's height all game (r1500: 316 vs 731).
Kind: refuted as built -- a local group test is the wrong filter; the arc itself is the thing to
keep free.

## Block 40 -- g_iter5 on the band (run 20260924-140716-scrim-g_iter5, 2026-09-24)

48 games against the 8 bots rated nearest g_iter5: **25/48 (52%)**, as a centred band should give.
g_iter5 now 1690 +- 52, rank 16 of 71, field score 68.9%. Block 41 running.

## Calibration and the ladder fix (calib1 + tools, 2026-09-24, PROMPTS 14-16)

**calib1** (`20260924-133731-scrim-g_iter5`): g_iter5 against the 48 ladder bots never played, two
games each: **88/96**. The ladder list has 65 bots (one per repo), not 220, so this one block
covered the field: all 65 are now met. The old sequential Elo (K=32, one `us` rating inherited by
every build) then put us at rank 4 of 66, Elo 1776, above bots with 104-9 records against us: the
96 easy games came last in play order and each moved `us` up against bots still at 1500.

**Fix (owner approved, PROMPTS 15-16):** `tools/elolib.py` now rates by a batch Bradley-Terry fit
over every game, on the Elo scale, with each of our builds its own player and a weak prior (one
virtual win and loss against a 1500 anchor). Order does not matter, and a build's rating comes only
from its own games. `tools/elo.py` reports each build's rating with a 95% interval, its rank, and
its field score (expected score against all 65 bots, one game each); `--band`/`--pool` centre on
the build named by `--as` (scrim.sh passes `$BOT`). The withdrawal rule in TRAINING_ALGORITHM.md
now compares ratings, not raw win rates, which depended on the pool each build met.

| build | rating | rank of 71 | games | record | field score |
|---|---|---|---|---|---|
| g_iter3 | 1693 +- 27 | 15 | 1116 | 244-872 | 69.4% |
| g_iter5 | 1684 +- 60 | 16 | 240 | 108-132 | 68.7% |
| iter24 | 1682 +- 124 | 17 | 48 | 11-37 | 68.6% |
| g_iter4 | 1669 +- 60 | 20 | 228 | 46-182 | 67.6% |
| g_iter2 | 1635 +- 63 | 21 | 204 | 47-157 | 64.8% |

The true grade: about rank 15 of 65 bots, with 14 bots above us. g_iter5 and g_iter3 are level on
the ladder (g_iter5's mirror-gate win over g_iter3 stands). Block 40 is the first on the corrected
band (bots rated 1567-1787).

## The graded ladder (owner decision, 2026-09-24, PROMPTS 7-9)

Asked for our progress against a fixed roster, the answer was that the pool "just above us" had
drifted to bots that beat us 70-99% of the time (we are rank 65 of 66 rated, 11 of the 15 pool
regulars over 70%), so a 48-game block moves 4-14 wins on noise and most of its games are locked
from review. The owner approved a graded ladder and, games being fast now, asked for enough
games to find the true ladder grade. `tools/elo.py --band N` draws the N rated bots nearest to us
on either side; `scrim.sh` uses it by default (`POOLMODE=above` restores the old pool) and seeds
it with `EXPLORE=n` never-played bots. Plan: calibration blocks of g_iter5 against 48
never-played bots at a time, two games each (`POOLSIZE=0 EXPLORE=48 N=96`), until the 220
untested bots are placed; then blocks on the band as before.

## Block 39 -- g_iter5 against the challenge pool (run block39, 2026-09-24)

48 games: **8/48 (17%)**; cumulative for g_iter5 24/144 (16.7%; Wilson upper bound 23.6%, still above g_iter3's 21.9%, so not withdrawn -- but the last block on the old pool; the calibration blocks restate both bots on the band).

## Block 38 -- g_iter5 against the challenge pool (run block38, 2026-09-24)

48 games: **9/48 (19%)**; cumulative for g_iter5 16/96 (16.7%, Wilson upper bound about 25%, above g_iter3's 21.9%: not withdrawn). Elo 1304, rank 65 of 66.

## Block 37 -- g_iter5 against the challenge pool (run block37, 2026-09-24)

48 games: **7/48 (14.6%)**; the first block of the new incumbent (g_iter3's single blocks ranged
4-14 of 48 around 21.6%). Losses by end round: 5 before r500, 2 by r1000, 19 between r1000 and
r2000, 9 by r3000, 6 after -- the raid window holds 46% of the losses, as before. Provisional
policy applies: withdraw if the Wilson upper bound of the accumulated g_iter5 blocks falls below
g_iter3's 21.6%.

## Block 36 -- g_iter3 against the challenge pool (run block36, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 256/1164 (22.0%). The last g_iter3 block; the ladder now measures g_iter5.

## Block 35 -- g_iter3 against the challenge pool (run block35, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 244/1116 (21.8%). Elo 1329, rank 65 of 66.

## Block 34 -- g_iter3 against the challenge pool (run block34, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 232/1068 (21.7%). Elo 1354, rank 65 of 66.

## Block 33 -- g_iter3 against the challenge pool (run block33, 2026-09-24)

48 games: **10/48 (20%)**; cumulative for g_iter3 220/1020 (21.5%). Elo 1310, rank 65 of 66.

## Block 32 -- g_iter3 against the challenge pool (run block32, 2026-09-24)

48 games: **9/48 (19%)**; cumulative for g_iter3 210/972 (21.6%).

## Block 31 -- g_iter3 against the challenge pool (run block31, 2026-09-24)

48 games: **9/48 (19%)**; cumulative for g_iter3 201/924 (21.8%). Elo 1297, rank 65 of 66.

## Block 30 -- g_iter3 against the challenge pool (run block30, 2026-09-24)

48 games: **13/48 (27%)**; cumulative for g_iter3 192/876 (21.9%). Elo 1403, rank 62 of 66.

## Block 29 -- g_iter3 against the challenge pool (run block29, 2026-09-24)

48 games: **11/48 (23%)**; cumulative for g_iter3 179/828 (21.6%). Elo 1379, rank 64 of 66.

## Block 28 -- g_iter3 against the challenge pool (run block28, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 168/780 (21.5%). Elo 1361, rank 65 of 66.

## Block 27 -- g_iter3 against the challenge pool (run block27, 2026-09-24)

48 games: **6/48 (12.5%)**; cumulative for g_iter3 156/732 (21.3%).

## Block 26 -- g_iter3 against the challenge pool (run block26, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 150/684 (21.9%).

## Block 25 -- g_iter3 against the challenge pool (run block25, 2026-09-24)

48 games: **4/48 (8%)**; cumulative for g_iter3 138/636 (21.7%). The challenge pool keeps
tightening toward the bots that beat us most; single blocks swing from 4 to 14 of 48.

## Block 24 -- g_iter3 against the challenge pool (run block24, 2026-09-24)

48 games: **14/48 (29%)**; cumulative for g_iter3 134/588 (22.8%).

## Block 23 -- g_iter3 against the challenge pool (run block23, 2026-09-24)

48 games: **14/48 (29%)**; cumulative for g_iter3 120/540 (22.2%).

## Block 22 -- g_iter3 against the challenge pool (run block22, 2026-09-24)

48 games: **8/48 (17%)**; cumulative for g_iter3 106/492 (21.5%). Elo 1314, rank 66.

## Block 21 -- g_iter3 against the challenge pool (run block21, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 98/444 (22.1%).

## Block 20 -- g_iter3 against the challenge pool (run block20, 2026-09-24)

48 games: **6/48**; cumulative for g_iter3 86/396 (21.7%).

## Block 19 -- g_iter3 against the challenge pool (run block19, 2026-09-24)

48 games: **12/48 (25%)**; cumulative for g_iter3 80/348 (23%).

## Block 17 -- g_iter3 against the challenge pool (run block17, 2026-09-24)

48 games: **11/48 (23%)**; cumulative for g_iter3 55/252 (21.8%).

## Block 18 -- g_iter3 against the challenge pool (run block18, 2026-09-24)

48 games: **13/48**; cumulative for g_iter3 68/300.

## Ledger (closed directions)

- **Miners first (Iteration 26, 2026-09-24)** -- g_iter5 with MINERS_EARLY 8. Gate 50-62 vs g_iter5.
  Kind: refuted. The closed ring stops miner spawns, but building the miners first costs more wall
  than they earn; the HQ's replenishment through open seats was cheap because it was late.
- **The perch (Iteration 24, 2026-09-24)** -- three tiles raised to 32 before the flood by one helper, a
  miner on one builds a center and a vaporator on the others (alive to r2200), post-flood soup buys
  up to 16 guards on fixed slots. Gate 51-61 vs g_iter3; ladder 11/48 vs 21.9%. Kind: priced below
  the gate, no ladder gain. Every mechanism shown in diagnostics (TwoLakeLand, Prison); the raid
  window (r1000-2000) still holds half the losses. Code `src/arch_perch`.
- **The home guard (Iteration 23, 2026-09-24)** -- early center, drones bought down to a 300 reserve
  from r550 up to 16, every drone a home guard from r900. Gate 16-32 vs g_iter3; arm vs arch_raider
  14/24 against g_iter3's 15/24. Kind: refuted as built. Pre-flood drones are helpers not built;
  the guard itself works (Prison, HQ silenced: 6 of 7 landed raiders lifted within two rounds).
  Reopen only with drones bought from post-flood soup (a center that outlives the flood).
- **The economy regime (Iteration 17, 2026-09-24)** -- 8-24 miners, early vaporators, 20 drones on
  g_iter3. Diagnostics 1-2 with 5,000-6,000 soup idle at r1000. Kind: blocked on the sink. Reopen
  together with the plateau (a place to spend soup after r700).

- **The spawn gap (Iteration 22, 2026-09-24)** -- one ring tile kept free until r300 so the HQ can
  spawn soup-driven miners. Refuted at the diagnostic: more miners after r150 cost more wall than
  they mine (TwoLakeLand 1262 vs 1655), and an unfed ring tile costs the ring its minimum.
- **Soup-driven miners (Iteration 21, 2026-09-24)** -- blocked: the HQ cannot spawn after the ring
  is seated (r150), so no report can add a miner. Kind: blocked on geometry. Reopen only with a
  spawn gap in the ring (one seat held back until r300) -- which costs wall.
- **The plateau, stage 1: tile-holding landscaper roles (Iterations 18-20, 2026-09-24)** -- gates
  42-54, 25-39, 15-33 vs g_iter3. Kind: refuted as built. The roles win single diagnostics by
  10-15% and lose the random-map mirror by 10-20 points; the losses show claim races, drownings on
  the way to tiles, and more deaths than the incumbent. Reopen with a claim scheme that never
  re-picks (a unit keeps the first tile it reaches) and with tiers opened by the water level, not
  by counts; and measure deaths and re-picks on ten maps before any gate.
- **The late guns (Iteration 16, 2026-09-24)** -- a parked builder rebuilding net guns after the
  flood. Blocked on geometry: no dry tile near the HQ that is not the ring or a helper's own tile.
  Kind: blocked. Reopen with a raised base.

- **The late drone raid (Iteration 9, 2026-09-23/24)** -- provisional at 135-105 over g_iter3,
  withdrawn after 228 ladder games at 20.2% (upper bound 25.6%) against g_iter3's 25.9%. Kind:
  priced below the gate on the ladder. It needs a center before r700, which the strong maps rarely
  allow, and the drones it keeps alive were worth more hunting miners.
- **The helpers' last stand (Iteration 14, 2026-09-24)** -- helpers stop self-raising from r2600.
  Gate 57-71 vs g_iter4. Kind: refuted. They drown at once and the incumbent's helpers were feeding
  the ring anyway between self-raises.
- **The second school (Iteration 13, 2026-09-24)** -- a parked builder rebuilds the school after the
  flood on the idle bank. Gates 51-61 and 103-105 vs g_iter4. Kind: priced below the gate. The bank
  buys landscapers but not posts for them (16 at distance 2, all taken by r700), and parking the
  builder costs a miner's income on poor maps. Reopen only with more posts (a dry third tier).

- **The citadel (ring at Chebyshev 2 with the buildings sealed inside; Iteration 6, 2026-09-23)** --
  refuted at the sweep stage: 4-9 of 24 against g_iter2 over ten sweeps after every mechanism was
  shown to work in diagnostics. Kind: priced below the gate. Reason: 16 tiles halve the wall rate
  and the sealed economy (6 soup a round) cannot fund a drone wave. Reopen only with a post-flood
  income an order of magnitude larger (more vaporators than a pocket holds) or a wall that needs
  fewer than 16 tiles.

## Functional-area map

| area | last attempt | consecutive rejects |
|---|---|---|
| economy (miners, builder, sites) | Iteration 15 (reject 56-72) | 2 |
| flood defence (wall) | Iteration 25 (ACCEPT 45-19) | 0 |
| navigation | Iteration 1 | 0 |
| exploration / symmetry | Iteration 1 | 0 |
| drones / combat | Iteration 10 (reject 120-120) | 1 |
| communication | Iteration 8 (ACCEPT 39-9) | 0 |
