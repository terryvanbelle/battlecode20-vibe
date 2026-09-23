# LEARNINGS.md

Durable lessons by theme, each naming the measurement or the engine line behind it. A lesson
without one is a belief and is marked as such. `TRAINING_LOG.md` is the chronological record.

## Engine and sandbox (Battlecode 2020)

- **`rc.canMove` does not check water.** A walker that moves onto a flooded tile disintegrates
  (`RobotControllerImpl.move`). Every walker step must check `senseFlooding` first.
- **Flooding advances one ring per round at the end of the round** (`GameWorld.floodfill`,
  called from `processEndOfRound` after `updateWaterLevel`). A tile floods when it is adjacent
  to a flooded tile and `dirt < waterLevel`; it resurfaces when dirt is deposited to
  `dirt >= waterLevel`.
- **An HQ dies at the round its elevation is reached** if the flood can get adjacent to it:
  smoke game, both HQs at elevation 1, both dead at r257 (water level reaches 1.0 at r256).
  Real maps promise HQ elevation 2-5, i.e. r256-r1210.
- **Robots act in spawn order, from a snapshot taken at the start of the round**
  (`ObjectInfo.eachDynamicBodyByExecOrder`): a robot built this round acts next round.
- **Blockchain timing**: messages submitted during round r are minted at the end of r (top 7 by
  fee, ties by a deterministic random id) and readable with `getBlock(r)` from round r+1
  (`getBlock` requires `round < currentRound`). Losers stay queued; the fee is never refunded.
- **Pollution from a refinery lasts one round**: it is added at the end of the refinery's turn and
  removed at the start of its next turn (`InternalRobot.processEndOfTurn`, `GameWorld.updateRobot`).
- **Only the final coin flip is non-deterministic** (`setWinnerArbitrary`): two identical games
  gave byte-identical replays.
- **`senseNearbyRobots` returns bounding-box scan order**, not by distance.
- **No indicator strings this year**; `System.out` lines go into the replay `logs` field with a
  `[A:MINER#id@round] ` header, per-team byte cap set by
  `bc.server.robot-player-replay-file-per-team-limit-bytes` (we set 4 MB).
- **Bytecode**: sensing calls 100, blockchain read/write 100, exceptions 500. Iteration 0's miner
  peaks at 5.2k of 10k, HQ at 3.3k of 20k.

## Infrastructure

- A 32x32 game where both HQs flood at r257 takes 13 s on the 2-core driver; expect minutes for
  walled 64x64 games. Plan evaluations on the VM.
- The engine's official artefacts are gone; building from source needs three patches
  (`tools/build-engine.sh`) and JDK 8.

## Strategy (this season) -- measured

(nothing yet)

## Method

See `TRAINING_ALGORITHM.md`; the portable account of why is `METHOD.md` in battlecode21-vibe.
