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
