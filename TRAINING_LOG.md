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
| flood defence (wall) | Iterations 18-20, the plateau (reject x3) | 6 |
| navigation | Iteration 1 | 0 |
| exploration / symmetry | Iteration 1 | 0 |
| drones / combat | Iteration 10 (reject 120-120) | 1 |
| communication | Iteration 8 (ACCEPT 39-9) | 0 |
