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

## The citadel (Iteration 6)

The wall is the ring at Chebyshev `C.RING` (2) around the HQ. The eight tiles inside are the
pocket: once the ring is sealed nothing inside can flood (`GameWorld.floodfill` spreads only from
a flooded neighbour), so the pocket holds the design school, the fulfillment center, three
vaporators and a net gun, and the refinery stays outside at `C.REFINERY_DIST`. The layout is
derived from the school's tile by every robot that sees it (`MapState.setSchool`):

```
   . . G . .        G  gate: the ring tile no seat takes; a drone hovers there
   . . F . .        F  spawn tile (pocket): the school and the center spawn onto it, nothing is built on it
   . C S H .        S  school, C  center (F's other pocket neighbour, `fcSlot`), H  HQ
   . . . . .        the other pocket tiles: vaporators, the net gun, the parked builder
```

Roles: seats (`Landscaper.SEAT`) on exposed ring tiles raise the lowest of their ring
neighbourhood; helpers (`HELPER`) on distance-3 tiles feed the ring from outside and leave for the
attack when their post stalls; a landscaper born on F after the ring rose (`INNER`) waits for the
ferry. Before `C.WALL_START` seats only level the ring to HQ+3 so units born inside can still walk
out; the HQ stops spawning miners then. The ferry (`Drone`): a drone on G lifts the landscaper on F
and drops it on the lowest free exposed ring tile, else a dry distance-3 tile; from `C.GATE_CLOSE`
the gate itself is the last target (a 16th seat), which also tells the school to stop. After
`C.RAID_ROUND` the center builds drones without cap; they gather at `C.RAID_RALLY` from the enemy
HQ guess and charge when `C.RAID_SIZE` are together, lifting enemy seats into the water.

## The enclosure (planned, 2026-09-25; supersedes the plateau below)

What the field's top bots build (TRAINING_LOG "The enclosure": 56 late losses, both boards read): a **shell** of
landscapers at Chebyshev 2 (16 tiles) and 3 (24 tiles) around the HQ, each holding its own tile above the water,
and an **interior** at Chebyshev 0-1 that is never raised. Flooding spreads only from a flooded neighbour, so a
complete shell keeps the interior dry at any elevation: the HQ spawns miners onto its ring tiles all game, a
school on a ring tile spawns landscapers all game, vaporators there pay all game, net guns there cover the shell
(r2 15 reaches Chebyshev 3), and the interior is the **quarry** -- ring tiles dug to -9 and below, never flooded,
always in reach, so the shell is fed from inside by bodies that need no tile of their own. Their count: 27
landscapers at r1000, 40 at r2000, 30-124 drones; ours 14 and 7, none.

What changes for us: the seats go. Roles become **shell-holder** (a Chebyshev-2 tile, later a Chebyshev-3 tile
outside a held one; keep it at water(round+60)+2, dig outward or from the quarry, feed the lowest adjacent shell
tile), **feeder** (inside the shell: dig a quarry tile, deposit on the lowest adjacent shell tile), and the
buildings inside: refinery, school, center, two guns, vaporators on ring tiles, one ring tile kept free as the
spawn yard. Intake is no longer bodies adjacent to eight tiles: a 16-tile shell with 16 holders and 8 feeders
takes 12 dirt a round, the same as today, but production never stops, so it grows -- and the guns and drones are
inside. Costs to price: the shell has twice the tiles of the ring (the same dirt buys half the height until the
feeders and the new bodies pay), and the shell must be complete before the water reaches Chebyshev 2 (r250-700 by
map), or the interior floods and the HQ with it. The first build is an archetype (`arch_enclosure`) played
against g_iter9 in the mirror and read for the shell's completion round and its height at r1000-3000; then the
bot. This is a multi-session program; every stage needs its diagnostic before its gate.

**State after twenty-seven stages (2026-09-25, `src/arch_enclosure`, TRAINING_LOG "The enclosure, stage 1" onward):**
the inner shell closes and holds (16 tiles, level to within ten, 1,200 at r2000 on RandomSoup1), the interior stays
at ground all game with the school, the center and a vaporator on it; every drone lifts waiting landscapers out
through the gate (the shell tile straight out from the school; its three outer neighbours are the elevator's
approach and are never held) onto shell tiles, then onto outer tiles at Chebyshev 3; outer holders keep their own
tile just above the water and put everything else on the lowest inner tile beside them; the yard is the two
interior tiles beside the gate, the builder never idles there, the school goes where the gate's approach is on the
map, eight miners (the ones born inside are lifted out), four drones; a corner HQ's shell is the region's edge
toward the map (a Chebyshev-2 tile with an on-map neighbour at 3): nine tiles on Prison, the edge side interior.
Alive against g_iter9 to r3140 (RandomSoup1), r2997-3108 (Prison), r3031 (Squares); g_iter9's HQ outlives ours on
every map.

**The arithmetic that bounds it (stage 26-27):** every holder digs 50 dirt a hundred rounds and deposits all of it;
the shell takes 11-12 dirt a round from 28-31 holders, 0.7 a tile a round, against g_iter9's wall at 8.8 a round
on 8 tiles, 1.1 a tile (79 at r300, 738 at r1000, 1,844 at r2000, 2,535 at r3000; ours 351 at r1000, 1,207 at
r2000, 1,720 at r3000). Inflow is the count of holders adjacent to the structure that dig an external tile, times
half; chains (a body feeding a body's tile) add nothing. The wall's 8 tiles are adjacent to 24 tiles, the shell's
16 to 48 minus the 8 interior ones, and a shell twice the size needs twice the bodies per height. The bodies are
capped by where they can stand: the outer ring floods at r950-1250 by map, and after that only reclaimed tiles
take a body. Stage 28 splits the outer ring into pits (dug) and stands (never dug, cheap to reclaim) by parity.
What remains open, in order: bodies on the stands before the flood (the sprint: g_iter9 has 24 landscapers at
r300, we 12), interior feeders on the free interior tiles digging an interior quarry (an interior pit never
floods), burying the center and the vaporator after r1000 for two more feeder tiles, and only then whether the
enclosure can beat the wall at all -- its edge is production through the flood, and that needs stands.

## The plateau (planned, 2026-09-24; superseded above)

The next structural candidate; see TRAINING_LOG.md "What the field does that we do not" and
HANDOFF.md for the measurements behind it. Roles are rewritten around **tiles a landscaper can
hold** rather than fixed post lists:

- A landscaper without a job picks the nearest tile at Chebyshev 1-3 from the HQ that it can reach
  (within 3 of its own elevation along a dry path), that no friend holds, and that it can keep dry
  (elevation now at or above the water, or shallow enough to resurface from next door). Ring tiles
  first, then distance 2, then distance 3. No attackers while such a tile exists.
- Every holder keeps its own tile at water(round+60)+2, then feeds **inward**: a seat raises the
  lowest of itself and its ring neighbours; a distance-2 holder feeds the lowest adjacent ring tile;
  a distance-3 holder piles onto the lowest adjacent distance-2 tile. Digging is always outward
  (never a tile a friend holds unless its margin over the water exceeds `FEED_MARGIN`).
- The distance-2 tier therefore rises with the dirt the distance-3 tier delivers, and its holders
  spend their own turns on the ring. The tier becomes the plateau: the builder parks on it from
  r660 and, once a plateau tile has stayed dry for 200 rounds, builds vaporators, a school and a
  net gun on the highest plateau tiles.
- The economy that pays for 32+ bodies before r700 is Iteration 17's (16 miners, vaporators from
  r150) with miners kept off the ring approaches on corner HQs.

Claims (revised after gates 18-20): a landscaper picks a tile once and keeps walking to it whatever
other units do; only on arrival, if the tile is taken, does it pick again from where it stands. No
unit yields to a "closer" unit (that rule made units re-pick while walking). Tier 3 opens when the
water passes 2, the same round for everyone. Before any gate: a quick-set sweep with logs, and
deaths by r1000 plus re-picks per landscaper compared with the incumbent's on every map.

Claims, second revision (deterministic slots): the school posts `SLOT(k, x, y)` on the chain the
round it builds its k-th landscaper; the newborn reads the block next round, finds its own tile in
it and takes slot k of a canonical list every landscaper computes alike -- exposed ring tiles in
direction order, then distance 2, then distance 3, skipping tiles off the map, cliffs, flooded tiles
and tiles beside our buildings. No two units ever head for the same tile; a unit whose slot proves
unreachable falls back to the nearest-free rule once.

Stage gates: (1) roles alone vs g_iter3 -- every surplus body holds a tile, none attacks; (2) with
the economy -- distance-2 tier above 30 at r1000; (3) with the buildings -- a school alive at r1500;
then the mirror gate and the `arch_swarm` arm.

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
