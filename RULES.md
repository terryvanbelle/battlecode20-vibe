# RULES.md -- Battlecode 2020 ("Soup"), digested and engine-checked

Spec version 2020.2.0.3 (`reference/battlecode20/specs/specs.md`). Every line tagged `[E]` was
read in the engine source (`engine/src/main/battlecode/...` at commit 7618f6b, which has no
engine changes after tag v2.0.3); `[S]` is from the spec only. When they disagree, the engine wins.

## The board

- Grid 32x32 to 64x64 `[E GameConstants]`, origin offset randomly (`LiveMap.origin`); nothing
  absolute may be assumed. `rc.getMapWidth()/getMapHeight()` are free, the origin is not exposed:
  `rc.onTheMap()` (5 bytecodes) finds edges.
- Every tile has **elevation** (dirt, int), **soup** (int), **pollution**, **flooded** flag. Map
  and both HQs are horizontally, vertically or rotationally symmetric `[S]`; cows start at
  symmetric positions and use the same movement seed `[S changelog 1.1.1]`.
- **Water level** `w(r) = e^(0.0028r - 1.38 sin(0.00157r - 1.73) + 1.38 sin(-1.73)) - 1`
  `[E GameConstants.getWaterLevel]`. Elevation floods at: 0->r0, 1->r256, 3->r677, 5->r1210,
  10->r1771, 25->r2143, 50->r2348, 100->r2524, 1000->r3019.
- **Flooding** happens at the END of every round `[E GameWorld.processEndOfRound]`: for every
  flooded tile, each of its 8 neighbours with `dirt < waterLevel` becomes flooded. One ring per
  round, so a flood front advances one tile per round. A non-flying robot on a tile that floods
  dies `[E setFloodStatus]`. Depositing dirt to `dirt >= waterLevel` resurfaces a tile
  `[E tryResurface]`. `MIN_WATER_ELEVATION = Integer.MIN_VALUE/2`; every map has one such tile.
- HQ starts at effective elevation 2-5, never adjacent to deep water; >= 1000 soup near it `[S]`.
- Round cap `GAME_MAX_NUMBER_OF_ROUNDS = 10000` and maps carry no other limit
  `[E GameMapIO: rounds = GAME_MAX_NUMBER_OF_ROUNDS]`. In practice a game ends when an HQ floods;
  a well-walled HQ can push that past r2500.

## Turn order and cooldowns

- Robots act in spawn order every round `[E ObjectInfo.eachDynamicBodyByExecOrder]`; the
  execution list is snapshotted at the start of the round, so a robot built this round acts
  next round. A robot held by a drone is `blocked` and takes no turn (its cooldown is frozen).
- Cooldown: at the start of a robot's turn `cd = max(0, cd - 1)` `[E InternalRobot.processBeginningOfTurn]`;
  it may act iff `cd < 1`; an action adds `actionCooldown * (1 + P/2000)` where P is the pollution
  on the robot's tile at the moment of the action `[E addCooldownTurns]`. New robots start with
  `cd = 10` `[E buildRobot]`; the HQ starts at 0.
- Base cooldowns: everything 1, drone 1.5, cow 2. So miners/landscapers act every round in clean
  air, drones twice in three rounds; at P = 2000 every action takes twice as long.
- Sensor radius: `round(base * 1/(1+P/4000)^2)` `[E getCurrentSensorRadiusSquared]`. Bases: HQ 48,
  miner 35, all other buildings/units 24, cow 10000.
- `senseNearbyRobots` returns bounding-box scan order (x outer, y inner), NOT by distance
  `[E GameWorld.getAllLocationsWithinRadiusSquared]`. Never take the first hit; pick the best.

## Robots

| type | cost | health (dirt) | sensor r2 | cd | bytecode | built by | does |
|---|---|---|---|---|---|---|---|
| HQ | -- | 50 | 48 | 1 | 20000 | -- | builds miners; refines (cap 20/turn); shoots drones (r2 15) |
| MINER | 70 | -- | 35 | 1 | 10000 | HQ | mines 7 soup/action (carries 100); deposits; builds all other buildings |
| REFINERY | 200 | 15 | 24 | 1 | 5000 | miner | refines up to 20 soup/turn from its store; +1 global pollution and +500 local (r2 35) for one round per refining turn |
| VAPORATOR | 500 | 15 | 24 | 1 | 5000 | miner | +2 soup/turn for free; -1 global pollution/turn; x0.8 local pollution (r2 35) |
| DESIGN_SCHOOL | 150 | 15 | 24 | 1 | 5000 | miner | builds landscapers |
| FULFILLMENT_CENTER | 150 | 15 | 24 | 1 | 5000 | miner | builds drones |
| LANDSCAPER | 150 | -- | 24 | 1 | 10000 | design school | dig/deposit 1 dirt per action (carries 25) |
| DELIVERY_DRONE | 150 | -- | 24 | 1.5 | 10000 | fulfillment center | flies over water; picks up / drops units and cows (pickup r2 3) |
| NET_GUN | 250 | 15 | 24 | 1 | 7000 | miner | shoots drones within r2 15 |
| COW | -- | -- | -- | 2 | 0 | map | neutral; +2000 pollution within r2 15; random walk |

All from `RobotType` `[E]`. Buildings never move and keep their elevation; a building dies when
`dirtCarrying >= dirtLimit` `[E InternalRobot.addDirtCarrying]` and its dirt is then added to the
tile (`addDirt(-1, loc, dirt)` in `destroyRobot`), so a buried HQ leaves a +50 mound.

### Actions, exactly `[E RobotControllerImpl]`

- **move(dir)**: adjacent, on map, unoccupied, `|dirt diff| <= 3` unless flying, ready. Moving a
  non-flyer onto a flooded tile kills it (`disintegrate`). Cooldown charged from the tile LEFT.
- **buildRobot(type, dir)**: spawner type matches, team soup >= cost, target on map, unoccupied,
  not flooded (drones may spawn on water), `|dirt diff| <= 3` (drones exempt), ready. Soup is
  deducted, the newborn has cooldown 10. Buildings can be built on any adjacent tile.
- **mineSoup(dir)**: miner, carrying < 100, tile on map with soup > 0; takes
  `min(7, soup there, room)`. Mining its own tile: `Direction.CENTER`.
- **depositSoup(dir, amount)**: miner with soup, adjacent refinery or HQ (any team? -- the check
  is `adjacentRobot.getType().canRefine()`, team is NOT checked `[E assertCanDepositSoup]`).
  Refinery refines up to 20 per turn at the END of its own turn; HQ likewise.
- **digDirt(dir)**: landscaper, carrying < 25, target on map; from a building only if that
  building carries dirt (you cannot dig under a building). Digging under a unit or on water or
  the landscaper's own tile lowers that tile by 1.
- **depositDirt(dir)**: landscaper with dirt; onto a building it buries it (health 15, HQ 50),
  onto a tile it raises it by 1 and may resurface it. Buries ANY building including your own.
- **pickUpUnit(id)**: drone not holding, target is miner/landscaper/cow, within r2 3, not
  already held, ready. The held unit moves with the drone and is `blocked`.
- **dropUnit(dir)**: adjacent, unoccupied (flooded is fine), ready; a non-flyer dropped on water
  dies. A dying drone drops its cargo on its own tile (`destroyRobot`).
- **shootUnit(id)**: HQ or net gun, target is a drone within r2 15, ready. Kills it (its cargo
  drops where the drone was).
- **submitTransaction(int[7], cost)**: cost >= 1 and <= team soup; no cooldown, 100 bytecodes.
- **getBlock(round)**: `1 <= round < current round`; 100 bytecodes.
- `disintegrate()` throws `RobotDeathException`; `resign()` kills the whole team.

## Economy `[E]`

- Team soup starts at 200, +1 per round base income (`processBeginningOfRound`).
- A miner action yields 7 raw soup; a refinery/HQ converts at most 20 per turn from what it holds
  (unlimited store). Vaporator: +2 per turn forever (250-round payback on 500).
- Pollution: global level `G >= 0` (+1 per refining turn of each refinery/HQ, -1 per vaporator turn,
  floored at 0). Local: at the END of a polluter's turn its effect is added
  (`addLocalPollution`) and it is removed again at the start of its next turn (or when it dies), so
  a refinery's +500 within r2 35 is present for the other robots' turns in between
  `[E InternalRobot.processEndOfTurn / GameWorld.updateRobot]`. Tile pollution =
  `round((G + sum additive) * prod multiplicative)`. Cows: +2000 within r2 15, always.
- Blockchain: transactions go into a priority queue ordered by cost DESC, then by a per-robot
  random id DESC (the RNG is a static `Random(mapSeed)` re-created in every RobotControllerImpl
  constructor, so it is deterministic), then message text `[E Transaction.compareTo]`. At the end
  of each round the top 7 are removed and become block `round`; readable from round+1 on. The
  losers stay queued and keep competing; the fee is paid on submission, never refunded. Both teams
  share the one queue: a fee of 1 is enough while the opponent posts fewer than 7 per round.
  Messages carry no sender or team; 7 ints per message.

## Ending and tie-breaks `[E processEndOfRound]`

Checked at the end of a round when either HQ is destroyed or round >= 9999:
1. the team whose HQ survives; 2. more robots (`QUANTITY_OVER_QUALITY`); 3. higher net worth
(team soup + sum of unit costs, `QUALITY_OVER_QUANTITY`); 4. more transactions minted
(`GOSSIP_GIRL`); 5. highest robot id (`HIGHBORN`); 6. `Math.random()` -- the only
non-determinism in the engine.

Engine result line (`Server.getWinnerString`): `<name> (A|B) wins (round N)` then `Reason: ...`.

## Bytecode `[E MethodCosts.txt]`

Limits: HQ 20000, net gun 7000, other buildings 5000, units 10000. Costs: `senseNearbyRobots`,
`senseNearbySoup`, `getBlock`, `submitTransaction` 100 each; `senseRobotAtLocation`,
`isLocationOccupied` 20; `senseRobot` 25; `canMove/canBuild/canMine/...` 10; `onTheMap`,
`canSenseLocation` 5; `senseElevation/senseSoup/sensePollution/senseFlooding` 1; `getBlock` and
`submitTransaction` do not touch the cooldown. Exceptions cost 500. Array allocation costs its
length. `java.util` counts as your own code. `String.indexOf/contains` cost their real length.
An overrun pauses the robot mid-instruction and resumes NEXT round: no exception, the rest of
the turn simply happens a round late `[S]`.

Allowed: `java.io java.lang java.math java.util java.util.function/regex/stream java.text
battlecode.common scala.*`; `System` only `out`, `arraycopy`, `getProperty("bc.testing.*")`.
No `getClass`, no reflection, no `Class.forName`, no `wait/notify`. Heap > 8 MB may explode.

## Replay file (.bc20) `[E GameMaker, schema/Round]`

Per round: `teamSoups`, moved ids+locs, spawned bodies (id, team, type, loc), died ids, actions
(`MINE_SOUP DEPOSIT_SOUP REFINE_SOUP DIG_DIRT DEPOSIT_DIRT PICK_UNIT DROP_UNIT SPAWN_UNIT SHOOT
DIE_DROWN DIE_SHOT DIE_TOO_MUCH_DIRT DIE_SUICIDE DIE_EXCEPTION`) with target ids, dirt/water/soup
changes, global pollution, local pollution table, new and broadcast messages with costs,
indicator dots/lines, `logs` (robot `System.out`, each line prefixed `[A:MINER#123@45] `),
bytecodes used per robot. The match header carries the whole map (dirt, water, soup,
pollution, bodies, initial water level, seed). Team id 1 = A, 2 = B, 0 = neutral (cows).

## Things the API does not give you

- No `setIndicatorString` this year: only dots and lines. Our logging goes through `System.out`
  into the replay `logs` field (kept per team by `bc.server.robot-player-replay-file-per-team-limit-bytes`).
- No `getRobotCount`, no team-wide counters: census is by blockchain or by sensing.
- No symmetry type in the API; infer it from terrain (dirt/water/soup) and the HQ positions.
