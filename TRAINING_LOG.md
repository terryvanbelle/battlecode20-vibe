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

## Ledger (closed directions)

(empty)

## Functional-area map

| area | last attempt | consecutive rejects |
|---|---|---|
| economy | -- | 0 |
| flood defence | -- | 0 |
| navigation | -- | 0 |
| exploration / symmetry | -- | 0 |
| drones / combat | -- | 0 |
| communication | -- | 0 |
