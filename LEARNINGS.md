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
- **A robot only ever reads the previous round's block** (`readBlock` = `getBlock(round-1)`), and each role reads on
  one residue mod 3. A message posted once is seen by a third of the units alive that round and by nobody born later.
  Anything that must reach every unit is re-posted every 10 rounds and newborns scan 12-20 blocks back (`readBack`).
  Found 2026-09-24 when no helper ever took the perch post (Iteration 24).
- **Units parked or wandering near the base cost the wall.** Helpers dig only tiles at Chebyshev 3 that hold no friend;
  guards flying between random points crossed them (Prison: 16 guards, ring 1303 vs 1601; fixed slots at Chebyshev 6:
  1585 vs 1601), and the incumbent's own vaporators and net guns at distance 3 take the same tiles (builder parked:
  1823 vs 1595). Buildings cannot be raised afterwards: dirt on a building buries it, so ground is raised first.
- **A raised tile is a magnet**: miners fleeing the flood climb to the highest dry tile in reach and never leave, and
  a landscaper depositing under a unit lifts it. Reserved tiles need both an exclusion in `climb()` and a step-off.
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

- **A 2020 game costs about one CPU-minute**, even at 3,000 rounds: 65 scrimmages took 8 minutes on
  the 8-core VM at 6 in parallel (2026-09-23), against ~6 CPU-minutes in 2021. The engine's cost is
  per robot-turn and 2020 armies are 10-30 robots, not hundreds. A 240-game gate is under an hour.
- **Raised wall tiles trap units against the map edge.** A miner between the rising ring and the edge
  can never leave (elevation difference > 3) and holds a ring seat forever; the tile stays low and
  drowns the HQ at the flood round (MoreCowbell, block 2).
- **A ring tile that touches no tile outside the ring never floods.** The flood spreads only from a
  flooded neighbour (`GameWorld.floodfill`), so a ring tile enclosed by the other ring tiles, the HQ
  and the map edge (a corner HQ) stays dry at elevation 3 under 1,000 of water. Dirt spent on it is
  wasted, and a wall is worth the minimum of its *exposed* tiles (MoreCowbell, `--ring` instrument).
- **A wall grows at 0.5 elevation per landscaper per round** (one dig, one deposit), spread over the
  exposed tiles; the water rises faster than that from about w = 100 (r2520) and faster than 1/round
  from w = 360 (r2700), so every wall fails between r2500 and r3100 and the race is decided by the
  higher exposed minimum at that moment. Six seats plus six helpers reached 1805 at r3000 against
  1367 for eight lone seats (ALandDivided diagnostic).
- **Some ring tiles are natural cliffs** (Hourglass has elevation-99 tiles adjacent to the HQ): they
  are walls already and unreachable; a seat rule must skip them or every spare landscaper waits
  under one.

- **A role with a fixed list of stations needs a stall exit.** Iteration 5 gave helpers eight
  posts (distance-2 corners and midpoints); an edge HQ has three, and every surplus helper walked
  toward a taken or unreachable post for 3,000 rounds: six of sixteen landscapers with 0 digs, the
  wall a quarter lower, gate 8-24. The single-map A/B diagnostic (a central HQ with room for all
  eight) could not show it. Diagnose on an edge HQ too, and count idle units per role at r600.
- **Vaporators yield 2 soup per turn** (`RobotType.VAPORATOR`, RULES.md), a 250-round payback on
  500; three of them fund a landscaper every 25 rounds. Not the 7 of a miner's dig.

- **Seats dig a moat.** A seat digs the tiles just outside the ring; within 100 rounds they are
  pits 10 deep and no walker can pass, so any ring tile not seated by then stays empty for the
  game (Soup, Islands, TheHighGround in the citadel sweeps: a contiguous arc at the far side of
  the ring never rose). Late seats must be flown in, or the ring must fill before the moat forms.
- **A pocket tile boxed in by two buildings is a trap.** The citadel's spawn tile F touches only the
  school and the center on the pocket side; a builder that parked there could never leave and
  blocked every spawn (Soup diagnostic: no drones all game).

- **A wall of 16 tiles grows at half the rate of a wall of 8** with the same landscapers, and no
  economy that fits in the pocket behind it (three vaporators, 6 soup a round) buys that back: the
  citadel's ring reached 1199 at r3000 against 1400-1600 for the incumbent's ring of 8 (ten
  24-game sweeps, 4-9 wins). The wall race is per-tile rate; extra tiles are pure cost.
- **A drone raid needs a wave, not a trickle.** The enemy HQ shoots one drone a round and its
  range covers the last five rounds of the approach; drones arriving one at a time die one at a
  time (25 built, 3 pickups). A wave of 16 would work and costs 2,400 soup at once.
- **A symmetry guess must be looked at before anything gathers around it.** Raiders rallied for
  1,500 rounds beside the rotation image of our HQ; the real one was 20 tiles away. Fly to within
  sense range (r2 24) of the guess, outside shooting range (r2 15), and let `pruneEmpty` work.
- **Robots born inside a sealed area know nothing**: no origin (no edge in sight), no enemy HQ,
  so no guess at all. Whoever learns a map fact must post it once; the HQ must re-post it.

- **The HQ (and a net gun) shoots to r2 15, sees to 48, and acts before our units each round.** A
  drone that ends its move inside 15 is dead before its pickup cooldown clears; a diagonal step from
  r2 25 lands at 13. Approach onto tiles with r2 > 15 only, and lift from a perch tile outside 15
  (a corner helper at Chebyshev 2 has one at 18). Seats cost a drone each (Iteration 9, six reruns).
- **A "nearest tile" target must be filtered for reachability.** The builder's nearest circle tile on
  IsThisProcedural was a 99-high cliff; it walked at it for 600 rounds with 5,000 soup idle and no
  school. Any walker choosing a target by distance needs an elevation check and a stall exit.

- **The eight tiles around a spawner are infrastructure.** Any role that parks units near the HQ
  (helpers, tier holders, parked builders, hovering drones) will sooner or later stand on every tile
  a design school or center can spawn onto, and production stops without a log line: Constriction
  had two landscapers all game (gate 18, 2-14 in its first batch), the incumbent itself has three
  there. Every station picker must skip tiles adjacent to our own buildings.

- **Two chosen diagnostics are not a forecast.** The plateau roles won Prison by 15% and Soup by 10%
  and then lost the random-map mirror 42-54, 25-39 and 15-33. A diagnostic proves a mechanism fires;
  only the gate prices it. Before a gate, run the candidate on ten random maps and count deaths and
  role churn against the incumbent -- the two numbers that predicted every plateau loss.
- **Claim races are the default failure of any "nearest free tile" rule.** With sixteen units and
  forty-eight tiles, several units head for the same tile, all but one re-pick, and each re-pick is
  rounds of digging lost; the incumbent's fixed eight seats never race. A claim must be settled
  once (first to reach keeps it) or assigned deterministically.

- **The HQ stops spawning the moment the ring is seated.** Its eight neighbours are the wall; from
  about r150 no miner can be built whatever the bank says. Every economy decision is made in the
  first 150 rounds (Iteration 21: a rising miner cap changed nothing).

## Infrastructure

- A 32x32 game where both HQs flood at r257 takes 13 s on the 2-core driver; expect minutes for
  walled 64x64 games. Plan evaluations on the VM.
- The engine's official artefacts are gone; building from source needs three patches
  (`tools/build-engine.sh`) and JDK 8.

## Strategy (this season) -- measured

- **The strong bots keep producing after the flood; we stop at r700.** Block 6, the three reviewable
  losses (laurenschneider on WateredDown, winkelmantanner on Hourglass, cormackikkert on Squares):
  their design schools, vaporators and net guns are alive at r1000-2000 (DS 1-3, V 1-9, NG 1-5) and
  their landscaper count keeps rising (22-25 at r2000) while ours falls to 4 as the school drowns
  (DS=0 from r700) and our soup sits unspent (600-1,600). Their buildings stand above the water,
  so they were placed on raised or naturally high tiles late, by a miner standing at that height.
  Squares was decided by a 32-drone swarm at r2100 that plucked nine seats and buried the HQ in 30
  rounds; drones fly, so they are the flood-proof army and the flood-proof threat.
- **A helper that stalls must not be thrown away.** Iteration 7 sent any helper whose walk paused
  30 rounds to attack; 3-6 landscapers stayed at the wall against the incumbent's 8+8, gate 21-43.

## Method

- **Read a diagnostic against a control, not against the opponent's side.** g_iter7 against itself gives
  rings of 2109 vs 1780 on Prison and 2445 vs 2616 on RandomSoup1 at r3000: the side effect is 7-18%. Four
  rounds of Iteration 36 diagnostics compared the candidate's ring with the incumbent's on the other side
  and read noise as signal both ways (2026-09-25).

- **A fixed engine seed makes repeated pairings the same game.** 491 of 2,825 ladder games were exact
  repeats (29% of g_iter3's), and a 240-game mirror gate held at most 104 distinct games, so every
  sequential test was overconfident. Seed every game and count a repeated cell once (2026-09-24).

- **Enclose the base; do not raise it.** Flooding spreads only from a flooded neighbour, so a complete dry shell
  at Chebyshev 2-3 keeps the HQ's ring dry at ground level for ever: the field's top bots keep their school,
  vaporators and guns on the ring tiles, dig the ring as a quarry, and stand 40-54 landscapers and 30-124 drones
  after the flood to our 7-14 and none. Raising the ring itself (our design since Iteration 3) walls the HQ off
  from its own spawn tiles and drowns every producer by r1000 (2026-09-25, 56 late-loss boards).

- **A reserve nobody spends is bodies nobody has.** The school held 300 then 700 soup back before landscapers
  9-16 and 17-24 (reserves from the gun and drone lines, long closed), so the last eight bodies came 200 rounds late
  on RandomSoup1 and never on Toothpaste as A, while the bank ended every game at 2,000-12,000 unspent. At 200 (the
  HQ's miner reserve intact; at 0 it starved) the paired gate accepted 30-8 and the arm read 64-32 (Iteration 43b,
  g_iter9, 2026-09-25). The wall race is dirt on the ring before r2750; bodies bought before the flood are the
  only lever left on it.

- **Every open ring tile needs a seat beside it.** Of 52 flood-round losses with a wall, 51 had the open tile at
  r500 with no landscaper of ours adjacent: seats take the nearest free tiles and raise their neighbours from where
  they sit, so the far tile is 100 below with its outward side under water and nobody who can reach it. A seat
  two away that feeds the tile between level and steps onto it removed the flood-round death from the ladder
  (1 of 96 against 6-7) and won 14 paired mirror games to 0 (Iteration 41b, g_iter8, 2026-09-25).

- **Pair the mirror: the incumbent against itself on the same seed.** Under a fixed seed the engine is
  deterministic, so a candidate plays the incumbent's exact game wherever its change does not fire, and an
  unpaired mirror scores the map-side-seed draw: gate 40c read 34-46 (REJECT) with 74 of 80 pairs concordant
  and the discordant 4-2 for the candidate. Only discordant pairs judge a change (2026-09-25, `tools/paired.sh`).

- **Rate the ladder with a batch fit, each build its own player.** A sequential K=32 Elo with one
  rating shared by every build depends on play order: 96 calibration games against unplaced bots
  (88 wins) lifted us from rank 65 to rank 4 of 66, above bots 104-9 against us. The batch
  Bradley-Terry fit of the same 2009 games puts g_iter5 at rank 16 of 71 (2026-09-24). A pool
  drawn only from bots above us had also drifted to bots that beat us 70-99%, where a 48-game block
  moves on noise: draw from the band on both sides.

See `TRAINING_ALGORITHM.md`; the portable account of why is `METHOD.md` in battlecode21-vibe.
