# DESIGN.md -- bot architecture

The bot lives in `src/bot/` (Java 8, package `bot`). Accepted iterations are frozen to
`src/g_iterN/` by `tools/snapshot.sh`; sparring archetypes to `src/arch_*`. This file is the
standing description of how the code is organised and why; findings go to `TRAINING_LOG.md` and
`LEARNINGS.md`, rules to `RULES.md`.

## Constraints that shape the code

- **Bytecode budgets** (HQ 20k, units 10k, net gun 7k, other buildings 5k) are hard. Sensing
  calls cost 100, blockchain reads and writes 100, exceptions 500, array allocation its length.
  `java.util` counts as our own code, so hot paths use arrays and unrolled loops.
- **Static state is per robot.** Each robot runs its own copy of every class; the only shared
  memory is the blockchain (7 ints per message, 7 messages per round, both teams together, fee in
  soup, readable one round later).
- **Coordinates are offset randomly**; the map size is given, the origin is probed.
- **Water kills**: `canMove` does not check flooding, so every walker step goes through
  `safeTile`/`Nav.legal`, which does.
- **Determinism**: no `Math.random`; a per-robot LCG seeded from the id.
- **Play symmetry**: every tie-break among directions or targets is relative to the robot's own
  geometry (toward a target, by score), never a fixed compass order or "first sensed"
  (`senseNearbyRobots` is row-major).

## Layout

| file | role |
|---|---|
| `RobotPlayer.java` | entry point: builds the `Robot` subclass for the type and runs its loop |
| `Robot.java` | base class: turn loop, bytecode monitor, RNG, sensing cache, `tryMove`/`tryBuild`/`fleeFrom` |
| `HQ.java` | builds miners, shoots drones, posts its location |
| `Miner.java` | mine nearest soup, carry home, wander |
| `Landscaper.java`, `Drone.java`, `NetGun.java`, `DesignSchool.java`, `FulfillmentCenter.java`, `Building.java` | one controller per type (several are still idle at Iteration 0) |
| `Nav.java` | greedy Chebyshev step with Euclidean tie-break, oscillation guard, bug fallback; water-aware |
| `MapState.java` | size, probed origin, HQs, remembered elevation, symmetry hypotheses and pruning |
| `Comms.java` | blockchain codec with an authenticator word (team + round salted hash) |
| `Debug.java` | `@tag k=v` log lines for the replay dumper |
| `C.java` | tunable constants, one place, each with the measurement that set it |

## The turn loop (`Robot.loop`)

```
while (true) {
  round0 = rc.getRoundNum();
  try { turn(); } catch (Exception e) { Debug.exception(e); }
  monitor: rc.getRoundNum() != round0 -> overrun; else record Clock.getBytecodeNum(), near-miss at 90%
  Clock.yield();
}
```

Every robot prints `@bc t=<type> used= max= near= over=` every 100 turns and on every overrun;
`tools/replay-dump.sh --logs '@bc'` and the engine's own `bytecodesUsed` field cross-check.

## Instrumentation conventions

- `Debug.log("@tag k=v ...")` at the decision point, once per decision, so a mechanism's firing
  is a `grep` count. Tags in use are listed at the top of `Debug.java`.
- The gauntlet silences the opponent's stdout, so our lines are the only logs in a replay.
