# Training Algorithm

How this project turns a season's rules into a strong bot, in terms that do not depend on the
season. Written fresh for battlecode20-vibe after reading the four predecessors
(battlecode22-vibe, battlecode26-vibe, battlecode25-vibe, battlecode21-vibe) and anicolao/bcenv,
weighting the later projects higher. It keeps what those loops proved and drops what they
themselves retired. `RULES.md` is the game, `TRAINING_LOG.md` is the record, `METHOD.md` in the
2021 project is the fuller account of why the instruments look this way.

## 0. Standing constraints (from the project owner)

1. No post-mortem from the current contest year is read, first- or second-hand.
2. External bots are downloaded as opponents; their source is never read (`BENCHMARK.md`).
3. No game against an external bot is reviewed -- replay, trace, log, board or reason -- until we
   beat that bot at least 20% of the time. Until then only its score is visible. The tooling
   enforces this and fails closed.
4. External bots are played only as **scrimmages**: random map from the released corpus, random
   side, rotating opponents drawn from the rating band around the build in play. Our own snapshots
   and archetypes may be played any way we like.
5. The ladder is built from our games only; external bots never play each other. Ratings are a
   batch Bradley-Terry fit on the Elo scale with each of our builds rated separately (`tools/elolib.py`).
6. Every user prompt is recorded verbatim in `PROMPTS.md`; every commit is pushed.
7. Unit tests for the bot and for every tool, run after every change to either.

## 1. What the loop optimises, and with what

The objective is absolute strength against people who did not write our code. Nothing we build
ourselves can measure that directly, so the loop keeps three instruments and never confuses them:

| instrument | answers | cannot see |
|---|---|---|
| **mirror gate**: candidate vs incumbent snapshot, random map and side, sequential test | is this one change better than what it replaces | any weakness both builds share; anything the incumbent never punishes |
| **archetype spars**: hand-built opponents that each do one thing the field does to us | does the change survive a rush / a siege / a hunt the mirror never mounts | everything else |
| **scrimmage ladder**: rated blocks against the external field, contest rules | are we actually stronger; what the field punishes | *why*, for bots below the 20% line |

The gate decides; the ladder is the consequence, not a test. A real team cannot scrimmage
without submitting, so every accepted build is submitted (a scrimmage block) and earns its own
rating. A build rated clearly below the previous submission is withdrawn (section 4.5).

**The ladder grade** (owner approved 2026-09-24, PROMPTS 8-17). Every game of ours goes into one
batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`): all games at once, so play order does
not matter, and each build is its own player, so one build's games never move another's rating.
A build's grade is three numbers from `tools/elo.py --build B`: its rating with a 95% interval, its
rank among the ladder bots and our builds, and its **field score**, the expected score against
every ladder bot one game each. Raw win rates are never compared across builds, because each
build met a different pool. Blocks draw from the **band**: the 8 rated bots nearest the playing
build's rating on either side (`tools/scrim.sh` default), where results are informative both
ways. A bot never played is placed by a calibration block, two games each
(`POOLSIZE=0 EXPLORE=n N=2n`); all 65 ladder bots have been placed.

## 2. Phase 0: instruments before strategy

Nothing strategic is written until each of these has a passing check, in this order.

1. **Rules digest** (`RULES.md`), every mechanic tagged with where it was verified in the engine
   source. The API is swept for methods the bot does not call at iteration 5, every 10 after,
   and whenever the loop stalls.
2. **Engine and runner**: the engine built from source, one game with bare `java` (no build
   daemon), parallel games within the machine's memory, a result line per game
   `(opponent, map, side) -> winner, rounds, reason`, replays of losses kept. Games run on the
   compute VM, never on the driver.
3. **Determinism check**: identical code twice must give identical results; the only allowed
   variance is the engine's own final coin flip.
4. **Replay reader**: replay -> text. Per-round team aggregates and the season's score terms as
   CSV, an event window, one robot's life, an ASCII board, our own log lines, per-type bytecode
   maxima and overruns, navigation statistics (moves, oscillation, coverage, first contact).
   This is the microscope; every hypothesis is checked through it.
5. **Bytecode monitor in the bot** from the first line: round number before and after the turn
   (overrun), `Clock.getBytecodeNum()` against the limit (near miss), reported in the logs and
   summarised by the replay reader for every run.
6. **Snapshot, mirror and sparring tools**: freeze `src/bot` as a renamed package; play any two
   packages; the mirror harness with the sequential test built in.
7. **Unit tests** for the bot's pure logic (encodings, geometry, map knowledge, the tuning
   constants' invariants) and for every analysis script (synthetic inputs, integrity checks on
   live data). One command runs all of them.
8. **Charts** in `progress/`: the ladder (every rating with its 95% interval), and the onset ladder (which
   metric predicts the result earliest). Regenerated on every accept; nothing stale stays.

**The basics, each with its own diagnostic before it is called done:**

| capability | diagnostic |
|---|---|
| economy | income per round against the engine's theoretical curve; idle resource; production actions per 100 rounds |
| navigation | rounds from spawn to a far target on a maze map; share of turns oscillating (A-B-A) or blocked |
| exploration | share of tiles seen by round N; round of first enemy-base sighting |
| symmetry | round at which the correct symmetry is identified; false commits |
| combat | kills per loss against a scripted brawler; retreats that survive; kite success where the rules allow it |
| bytecode | overruns and near misses per game: zero tolerated |

Iteration 0 is the smallest legal bot that moves a unit and logs the monitor. It is snapshotted
and pushed through every instrument so the instruments are proven on something trivial.

## 3. Where candidates come from

Three sources, used in rotation so no single one dries up:

- **Absolute degeneracy in our own replays**, needing no opponent: a resource pinned in a band,
  production falling to zero, a unit oscillating, a building never used, a bytecode overrun.
- **The census of a scrimmage block**: every replay we are allowed to read, wins and losses,
  reduced to per-round metrics for both sides. `tools/correlate.py` gives each metric's
  correlation with the result within opponent-and-map pairs; `tools/onset.py` gives the first
  round at which that correlation appears and holds. **Act on the earliest onset, never on
  late-round correlations** (by r600 everything correlates with winning because the winner is
  ahead on everything). A correlation is a place to look, not a mechanism: the opponents' unit mix
  correlated strongly with their wins in 2021, and a bot rebuilt around that mix lost 0-24.
- **The capability gap**: a doctrine visible in the allowed games that we never produce, an API
  method the bot never calls, or a perennial lever from `RESEARCH.md` (symmetry inference,
  emergent rather than commanded coordination, micro before macro, rush/turtle adaptivity).

Keep a functional-area map in the log (economy, production mix, navigation, exploration, combat,
communication, defence, map adaptation). After three consecutive rejects in one area the next
candidate must come from another area or from the structural track.

## 4. The funnel: five stages, cheapest first

Every candidate passes through these in order, and most die early. That is the design.

1. **Read.** Trace the motivating replay with the reader before forming a hypothesis. Enumerate
   the mechanisms that could produce the symptom ("chooses badly" and "never sees it" leave the
   same trace) and find which the data supports. Check the same symptom in a second game.
2. **Pre-register** in the log before touching code: the mechanism; the **decision-point
   counter** that proves it fired (count the choice, not its downstream effect); reachability
   (the branch is taken at observed values, the choice set has more than one member, the property
   optimised is visible at the scale of the decision); trigger frequency across other games;
   the price, costed against what it displaces rather than against zero; the ledger grep; the
   gate; the falsifier; for a numeric change, a dose ladder with a byte-identical zero arm.
3. **Diagnose.** One logged game of the candidate against the incumbent on a small map where the
   mechanism can fire. Grep the pre-registered counters. **No test starts until the mechanism
   demonstrably fires and acts as claimed.** Three 2021 candidates were implemented, compiled and
   completely inert; only the counters showed it. Check `over=` for every robot type: an overrun
   candidate gates at 50% whatever it is worth.
4. **Gate.** The mirror under a sequential probability ratio test: candidate vs incumbent,
   random map and random side per game, batches of 16, `H0 p=0.50` against `H1 p=0.58`,
   `alpha = beta = 0.05`, cap 240 games. ACCEPT snapshots; REJECT reverts; inconclusive at the
   cap keeps the change **provisionally** if it read >= 53% over >= 200 games (no snapshot, no
   submission) and further candidates stack on it, each tested against the incumbent. A stack
   that reaches ACCEPT is snapshotted; one that reaches REJECT loses its newest member.
   A change built to answer something only the external field does (a rush, a swarm) is
   pre-registered with a **second arm** against the archetype that has that property; a null in
   the mirror plus a win in that arm is a finding, a null in both is a reject. Never reinterpret a
   null after seeing it.
5. **Submit.** Snapshot `src/g_iterN`; regression against the archetypes; a 48-game scrimmage
   block under contest rules; record it, rebuild the ladder and the roster tiers; mine the
   block (census, correlation, onset) for the next candidate; update the ledger and the state
   section of `HANDOFF.md`; commit and push. Withdraw the build if the upper end of its rating's
   95% interval falls below the previous submission's rating (raw win rates are not compared: they
   depend on the pool each build met).

### Budget rules

- A diagnostic costs one small-map game; a gate 80-240 games; a submission 48. State every
  gate in games on a named opponent, never as a margin.
- Play informative cells only: a stage-0 diff on cells no build has ever flipped is wasted.
- Read a batch only when it is complete; games ending early are the losses (or the wins), so a
  partial tally is biased.
- Two runs never share a class tree; a runner refuses to recompile a tree that live games read.
- Prune the VM disk after every block is fetched; check `df` before launching a gate.

## 5. Incremental and structural, interleaved

Both tracks are legitimate and both are scheduled. Incremental: one parameter or one narrow
mechanism from a traced loss; cheap, mostly rejected, the source of calibration knowledge.
Structural: a new mechanic, a re-architected subsystem, a different opening, a from-scratch
rewrite on the same infrastructure; named from a capability gap rather than one game; verified
with the same funnel. At least one structural attempt in every four, immediately after the
three-rejects rule fires, and whenever the ladder has been flat for five accepts. The
predecessors' record says repairs of our own defects and removals of a binding cap transfer to
the ladder; reallocations of a resource mostly do not.

## 6. Records

- `TRAINING_LOG.md`: append-only; one entry per attempt with target, trace, pre-registration,
  diagnostic counters, gate numbers, decision, and what was learned. In-flight runs are named by
  run id and gate so a fresh session can resume. It carries the closed-directions ledger: each
  closed avenue with the measurement, its kind (refuted / priced below the gate / blocked on a
  prerequisite / engine-impossible) and a checkable re-open condition.
- `LEARNINGS.md`: durable lessons by theme, each naming its measurement; a consistency pass
  every ten accepts.
- `BENCHMARK.md`: the external field, tiers per build; the tier governs replay access.
- `HANDOFF.md`: the state of the loop and the gotchas that cost time.
- `progress/`: `ELO.md` (ratings and each build's grade), `elo.png` (every rating with its 95%
  interval), `ONSET.md`, `onset-ladder.png`, `games.csv` (every scrimmage), `METRICS.md`.
- `PROMPTS.md`: every user prompt, verbatim.

## 7. When the loop stalls

In order, not skipping to the last:

1. Ablate what is already carried: gate each accepted feature off in turn and gate it. Features
   accepted on thin margins are often worth nothing; failure-mode preventers are often worth the
   most. When the ladder drops after a run of accepts, ablate pairwise and let the ancestry name
   the pair.
2. Sweep the API for methods the bot never calls.
3. Re-read the allowed ladder games for what we were not looking for last time.
4. Re-read `RESEARCH.md` (other years' post-mortems).
5. A structural attempt, then a rewrite.

Regularities that fill ledgers: metrics that move without converting to wins; survival bought
with inactivity; and the winner's recurring profile of capability preserved at zero marginal
cost -- standing defences, spending idle resources, removing pure waste. Waiting on a run is
work; stopping because nothing comes to mind is not.
