package bot;

import battlecode.common.*;

/**
 * Landscaper. WALL: take the nearest free tile of the ring around our HQ, then forever: dig any
 * dirt off the HQ, else deposit carried dirt on our own tile (raising the wall), else dig from an
 * adjacent tile outside the ring that holds no building of ours. ATTACK (when the ring is full):
 * walk to the enemy HQ (sighted or the symmetry guess) and bury the first enemy building met.
 * HELPER (Iteration 3, when the ring is full): stand at Chebyshev 2 from the HQ, keep the own tile
 * above the water, otherwise dig from a tile further out and deposit onto the lowest adjacent ring
 * tile -- doubling the wall's growth and filling ring tiles no seat can reach.
 */
public strictfp class Landscaper extends Robot {
    private MapLocation seat;                 // our ring tile, once chosen
    private boolean attacker = false, helper = false;
    private MapLocation post;                 // helper station at distance 2
    private final MapLocation[] badSeat = new MapLocation[8]; private int nBad = 0;
    private int helperDeps = 0;
    private int digs = 0, deposits = 0, hqDigs = 0, buryDeposits = 0, equalised = 0, borrowed = 0;

    Landscaper(RobotController rc) { super(rc); nav.stallLimit = 30; }
    private boolean forward = false;   // Iteration 63: born at our forward school beside the enemy HQ
    @Override protected void init() throws GameActionException {
        super.init();
        RobotInfo[] en = rc.senseNearbyRobots(-1, us.opponent()); boolean hq = false;
        for (int i = en.length; --i >= 0;) if (en[i].type == RobotType.HQ) { MapState.enemyHQ = en[i].location; hq = true; }
        if (hq && (MapState.home == null || Nav.cheb(rc.getLocation(), MapState.home) > 6)) { forward = true; attacker = true; Debug.log("@rushattacker"); }
    }

    @Override protected void turn() throws GameActionException {
        int b0 = Clock.getBytecodeNum();
        try { turn2(); } finally { int b1 = Clock.getBytecodeNum(); if (b1 > 8500) Debug.log("@bcprof start=" + b0 + " total=" + b1 + " seat=" + (seat != null) + " helper=" + helper + " attacker=" + attacker + " atSeat=" + (seat != null && seat.equals(loc)) + " atPost=" + (post != null && post.equals(loc))); }
    }
    private void turn2() throws GameActionException {
        sense(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat seat=" + seat + " attacker=" + attacker + " helper=" + helper + " helperDeps=" + helperDeps + " eq=" + equalised + " borrow=" + borrowed + " digs=" + digs + " deps=" + deposits + " hqDigs=" + hqDigs + " bury=" + buryDeposits + " elev=" + rc.senseElevation(loc));
        if (forward) { attack(); return; }   // Iteration 63
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        // Iteration 78 stage 9: the eight ring seats first, as g_iter12 (the rush answer: stage 8 died to rushes at r174-308 on
        // three maps of six); every other home landscaper works the lattice
        if (!attacker && !helper && (seat == null || (!seat.equals(loc) && occupiedByOther(seat)))) seat = pickSeat(home);
        if (!attacker && seat == null) { lattice(home); return; }
        if (!attacker && !helper) {
            if (seat != null && !seat.equals(loc) && nav.target() == seat && nav.stalled()) { if (nBad < 8) badSeat[nBad++] = seat; Debug.log("@badseat " + seat); seat = null; }
            if (seat == null || !seat.equals(loc) && occupiedByOther(seat)) seat = pickSeat(home);
            if (seat == null) { post = pickPost(home); if (post != null) { helper = true; Debug.log("@helper post=" + post); } else { attacker = true; Debug.log("@attacker ring full"); } }
        }
        if (attacker) { attack(); return; }
        if (helper) { help(home); return; }
        if (!loc.equals(seat)) { if (floodDanger() && climb()) return; nav.setTarget(seat); nav.step(); if (loc.equals(seat)) Debug.log("@seated at=" + seat); return; }
        wall(home);
    }

    private MapLocation latSpot;
    private boolean schoolDoor(MapLocation n) throws GameActionException {
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.DESIGN_SCHOOL || !f.location.isAdjacentTo(n)) continue;
            if (rc.canSenseLocation(f.location) && rc.canSenseLocation(n) && rc.senseElevation(n) >= rc.senseElevation(f.location) + 2) return true; }
        return false;
    }
    /** Iteration 78: work the lattice. Dig the HQ out; with dirt, raise the lowest grid tile beside us that is below the
     *  target (our own tile included); without, dig a free cell beside us (a pit), else a grid tile far above the
     *  target, else walk to the lowest grid tile in sight. */
    private void lattice(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && loc.isAdjacentTo(home) && rc.canDigDirt(loc.directionTo(home)) && rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit) { rc.digDirt(loc.directionTo(home)); hqDigs++; digs++; return; }
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.getDirtCarrying() > 0 && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return; } }
        // stage 3: the HQ being buried is everyone's job -- a free tile beside it, now (stage 2 died to the rush at r279, no dig)
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && !loc.isAdjacentTo(home)) { if (rc.getDirtCarrying() > 0) { for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (!loc.add(d).equals(home) && !isGrid(loc.add(d)) && !isLot(loc.add(d)) && rc.canDepositDirt(d)) { rc.depositDirt(d); return; } } } nav.setTarget(home); nav.step(); return; }
        int target = gridTarget(round);
        if (!isGrid(loc) || Nav.cheb(loc, home) > C.LATTICE_R) {   // get onto the lattice
            MapLocation best = null; int bd = 1 << 30;
            for (int dx = -C.LATTICE_R; dx <= C.LATTICE_R; dx++) for (int dy = -C.LATTICE_R; dy <= C.LATTICE_R; dy++) {
                MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!isGrid(t) || !rc.onTheMap(t)) continue;
                if (rc.canSenseLocation(t) && (rc.senseFlooding(t) || rc.isLocationOccupied(t))) continue;
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; best = t; } }
            if (best != null) { nav.setTarget(best); nav.step(); } return;
        }
        if (rc.getDirtCarrying() > 0) {
            Direction bestD = null; int be = target;
            if (rc.senseElevation(loc) < be && !schoolDoor(loc)) { be = rc.senseElevation(loc); bestD = Direction.CENTER; }
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!(isGrid(n) || isLot(n)) || !rc.canSenseLocation(n) || !rc.canDepositDirt(d)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                if (isGrid(n) && schoolDoor(n)) continue;   // stage 7: a school's doors stay within 3 of it (the first school was boxed in at eight landscapers)
                int e = rc.senseElevation(n) + (isLot(n) ? 2 : 0); if (e < be) { be = e; bestD = d; } }   // stage 2: lots too, kept 2 under the grid
            if (bestD != null && rc.canDepositDirt(bestD)) { rc.depositDirt(bestD); deposits++; return; }
        }
        if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit) {
            Direction bestD = null; int bs = Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!rc.onTheMap(n) || n.equals(home) || !rc.canDigDirt(d)) continue;
                RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && (r.type.isBuilding() || r.team == us)) continue;
                int e = rc.senseElevation(n), s;
                if (isPit(n)) s = -e;                                      // a pit: the highest first (stage 2: pits only, never a lot)
                else if (isGrid(n) && e > target + 20) s = 100000 - e;     // a grid tile far above the target
                else if (Nav.cheb(n, home) > C.LATTICE_R) s = 200000 - e;  // outside the lattice
                else continue;
                if (s < bs) { bs = s; bestD = d; } }
            if (bestD != null) { rc.digDirt(bestD); digs++; return; }
        }
        // nothing to do here: walk the grid toward its lowest tile in sight (re-chosen every 20 rounds)
        if (latSpot == null || loc.equals(latSpot) || round % 20 == id % 20) {
            MapLocation best = null; int be = Integer.MAX_VALUE;
            for (int dx = -C.LATTICE_R; dx <= C.LATTICE_R; dx++) for (int dy = -C.LATTICE_R; dy <= C.LATTICE_R; dy++) {
                MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!isGrid(t) || !rc.canSenseLocation(t) || rc.isLocationOccupied(t)) continue;
                int e = rc.senseElevation(t) * 4 + loc.distanceSquaredTo(t); if (e < be) { be = e; best = t; } }
            latSpot = best;
        }
        if (latSpot != null) { nav.setTarget(latSpot); nav.step(); }
    }

    private boolean occupiedByOther(MapLocation l) throws GameActionException {
        if (!rc.canSenseLocation(l)) return false;
        RobotInfo r = rc.senseRobotAtLocation(l);
        return r != null && r.ID != id;
    }

    /** The nearest ring tile that is free (or holds only a unit that will move on), not flooded, and
     *  reachable: within 3 of the HQ's elevation. A natural cliff on the ring (Hourglass has 99s) is
     *  already a wall and cannot be climbed; five landscapers once waited under one for 500 rounds. */
    private MapLocation pickSeat(MapLocation home) throws GameActionException {
        MapLocation best = null; int bd = 1 << 30;
        int hqElev = rc.canSenseLocation(home) ? rc.senseElevation(home) : rc.senseElevation(loc);
        for (int i = 8; --i >= 0;) {
            MapLocation t = home.add(DIRS[i]);
            if (!rc.onTheMap(t)) continue;
            boolean bad = false; for (int k = nBad; --k >= 0;) if (badSeat[k].equals(t)) { bad = true; break; }
            if (bad || !exposed(t)) continue;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                int e = rc.senseElevation(t);
                if (e - hqElev > GameConstants.MAX_DIRT_DIFFERENCE || hqElev - e > GameConstants.MAX_DIRT_DIFFERENCE) {
                    RobotInfo r0 = rc.senseRobotAtLocation(t);
                    if (r0 == null || r0.type != RobotType.LANDSCAPER || r0.team != us) continue;   // a raised seat with our landscaper on it is taken; an empty cliff is unreachable
                }
                RobotInfo r = rc.senseRobotAtLocation(t);
                if (r != null && r.ID != id && (r.type == RobotType.LANDSCAPER && r.team == us || r.type.isBuilding())) continue;
            }
            int d = loc.distanceSquaredTo(t) + nextInt(2);
            if (d < bd) { bd = d; best = t; }
        }
        return best;
    }

    /** A free tile at Chebyshev 2 from the HQ, next to the LOWEST ring tile it can feed (then nearest). */
    private final MapLocation[] badPost = new MapLocation[8]; private int nBadPost = 0;
    private MapLocation pickPost(MapLocation home) throws GameActionException {
        MapLocation best = null; long bs = Long.MAX_VALUE; int myE = rc.senseElevation(loc);
        for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue;
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t)) continue;
            boolean bad = false; for (int k = nBadPost; --k >= 0;) if (badPost[k].equals(t)) { bad = true; break; }
            if (bad) continue;
            int lowest = Integer.MAX_VALUE;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                int e = rc.senseElevation(t); if (Math.abs(e - myE) > 6) continue;
                RobotInfo r = rc.senseRobotAtLocation(t);
                if (r != null && r.ID != id && (r.type.isBuilding() || (r.type == RobotType.LANDSCAPER && r.team == us))) continue;
                for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (onRing(n) && exposed(n) && rc.canSenseLocation(n)) lowest = Math.min(lowest, rc.senseElevation(n)); }
                if (lowest == Integer.MAX_VALUE) continue;   // a post that touches no exposed ring tile feeds nothing
            }
            long s = (long) (lowest == Integer.MAX_VALUE ? 0 : lowest) * 10000 + loc.distanceSquaredTo(t) + nextInt(2);
            if (s < bs) { bs = s; best = t; }
        }
        return best;
    }

    private void help(MapLocation home) throws GameActionException {
        if (!loc.equals(post)) {
            if (occupiedByOther(post)) { RobotInfo r = rc.canSenseLocation(post) ? rc.senseRobotAtLocation(post) : null; if (r != null && (r.type.isBuilding() || r.type == RobotType.LANDSCAPER)) { post = pickPost(home); if (post == null) { helper = false; attacker = true; return; } } }
            if (floodDanger() && climb()) return;
            if (nav.target() == post && nav.stalled()) {   // Iteration 8: a post never reached is struck off and another picked; the attack only when none is left (Iteration 7 sent every stalled helper to attack: gate 21-43)
                if (nBadPost < 8) badPost[nBadPost++] = post; Debug.log("@badpost " + post);
                post = pickPost(home); if (post == null) { helper = false; attacker = true; }
                return;
            }
            nav.setTarget(post); nav.step(); if (loc.equals(post)) Debug.log("@posted at=" + post); return;
        }
        if (!rc.isReady()) return;
        // Iteration 25: a free ring tile next door that we can climb is a seat going spare -- take it
        if (round % 3 == id % 3) {
            int myE = rc.senseElevation(loc);
            for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (!onRing(n) || !exposed(n) || !rc.canSenseLocation(n) || rc.senseFlooding(n) || rc.senseRobotAtLocation(n) != null) continue;
                if (Math.abs(rc.senseElevation(n) - myE) > GameConstants.MAX_DIRT_DIFFERENCE) continue;
                seat = n; helper = false; post = null; Debug.log("@reseat at=" + n); return; }
        }
        // 1. keep our own tile above the water that is coming
        boolean lowSelf = rc.senseElevation(loc) < waterLevel(round + 60) + 2;
        if (rc.getDirtCarrying() > 0 && lowSelf && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
        // 2. feed the lowest adjacent ring tile (never a building)
        if (rc.getDirtCarrying() > 0) {
            Direction bestD = null; int be = Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !exposed(n) || !rc.canDepositDirt(d)) continue;
                RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && r.type.isBuilding()) continue;
                if (round < C.SEATS_BY && (r == null || r.type != RobotType.LANDSCAPER || r.team != us)) continue;   // Iteration 25: never raise an unseated tile early
                int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
            if (bestD != null) { rc.depositDirt(bestD); helperDeps++; return; }
        }
        // 3. dig from a tile outside both rings (lowest first), never under a building or the HQ
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || Nav.cheb(n, home) <= 2 || !rc.canDigDirt(d) || doorstep(n)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && (r.type.isBuilding() || r.team == us)) continue;
            int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
        if (bestD == null && rc.canDigDirt(Direction.CENTER) && rc.senseElevation(loc) > waterLevel(round + 200) + 3) bestD = Direction.CENTER;   // nothing outside: eat our own margin
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }

    private void wall(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        Direction toHQ = loc.directionTo(home);
        if (round >= C.SEATS_BY && seatWalk(home)) return;   // Iteration 41: an open ring tile with no seat beside it gets one
        // 1. the HQ is being buried: dig it out
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && rc.canDigDirt(toHQ)) { rc.digDirt(toHQ); hqDigs++; digs++; Debug.log("@hqdig buried=" + hqInfo.dirtCarrying); return; }
        // 2. an enemy building or unit adjacent: bury it (deposit) if we carry, it is cheap denial
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.getDirtCarrying() > 0 && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return; } }
        // 3. raise the LOWEST of {our tile, adjacent ring tiles}: the flood gets in through the lowest ring
        //    tile, so a wall is worth its minimum (Iteration 3; seats sat at 415 beside 163 before this)
        if (rc.getDirtCarrying() > 0) {
            int myE = rc.senseElevation(loc); Direction bestD = Direction.CENTER; int be = exposed(loc) ? myE : Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canSenseLocation(n) || !exposed(n)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                if (round < C.SEATS_BY && (r == null || r.type != RobotType.LANDSCAPER || r.team != us)) continue;   // Iteration 25: an unseated tile stays climbable
                int e = rc.senseElevation(n); if (e < be - C.WALL_LEVEL_SLACK) { be = e; bestD = d; } }
            if (be != Integer.MAX_VALUE && rc.canDepositDirt(bestD)) { rc.depositDirt(bestD); deposits++; if (bestD != Direction.CENTER) equalised++; return; }
        }
        // 4. dig from outside the ring: the lowest adjacent tile that is not the HQ, not a ring tile, not one of our buildings
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || onRing(n) || n.equals(home) || !rc.canDigDirt(d) || doorstep(n)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.team == us) continue;   // never under our own units: digging a helper's tile makes it re-raise itself, a zero-sum loop
            int e = rc.senseElevation(n) + (r != null ? 1000 : 0);   // prefer empty tiles
            if (e < be) { be = e; bestD = d; }
        }
        // 5. a corner seat on the map edge has no outside tile: borrow from the tallest adjacent ring tile
        if (bestD == null) { int myE = rc.senseElevation(loc); int bh = myE + C.WALL_BORROW_MARGIN;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canDigDirt(d)) continue; int e = rc.senseElevation(n); if (e > bh) { bh = e; bestD = d; } }
            if (bestD != null) borrowed++; }
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }

    /** Iteration 41, the seat walk (seat census, 2026-09-25: of 52 flood-round losses with a wall (4+ landscapers) and
     *  an open ring tile at r500, 51 had no landscaper of ours adjacent to the open tile -- the seats had clustered on
     *  the near side, and step 3 feeds only adjacent tiles). After SEATS_BY, a seat two tiles from an open ring tile that no seat touches steps onto the raised,
     *  empty ring tile between them, from where step 3 feeds it (through a miner standing on it if need be). */
    private boolean seatWalk(MapLocation home) throws GameActionException {
        int myE = rc.senseElevation(loc);
        for (int i = 8; --i >= 0;) {
            MapLocation t = home.add(DIRS[i]);
            if (!rc.onTheMap(t) || !exposed(t) || Nav.cheb(t, loc) != 2 || !rc.canSenseLocation(t)) continue;
            boolean low = rc.senseFlooding(t) || rc.senseElevation(t) < myE - GameConstants.MAX_DIRT_DIFFERENCE;
            if (!low) continue;
            RobotInfo on = rc.senseRobotAtLocation(t); if (on != null && (on.type.isBuilding() || (on.type == RobotType.LANDSCAPER && on.team == us))) continue;
            boolean tended = false;
            for (int k = 8; --k >= 0;) { MapLocation n = t.add(DIRS[k]); if (!onRing(n) || !rc.canSenseLocation(n)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type == RobotType.LANDSCAPER && r.team == us) { tended = true; break; } }
            if (tended) continue;
            Direction feed = null; int fe = Integer.MAX_VALUE;
            for (int k = 8; --k >= 0;) { Direction d = DIRS[k]; MapLocation n = loc.add(d);
                if (!onRing(n) || Nav.cheb(n, t) != 1 || !rc.canSenseLocation(n) || rc.senseFlooding(n)) continue;
                int e = rc.senseElevation(n);
                if (Math.abs(e - myE) <= GameConstants.MAX_DIRT_DIFFERENCE) { if (rc.canMove(d)) { rc.move(d); seat = n; loc = rc.getLocation(); Debug.log("@seatwalk to=" + n + " for=" + t); return true; } continue; }
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                if (e < myE && e < fe && rc.canDepositDirt(d)) { fe = e; feed = d; } }
            // 41b: the tile between us is too low to step onto (Spiral as A: 29 beside a seat at 147): raise it level first
            if (feed != null && rc.getDirtCarrying() > 0) { rc.depositDirt(feed); deposits++; Debug.log("@seatfeed " + loc.add(feed) + " e=" + fe + " for=" + t); return true; }
        }
        return false;
    }

    /** Iteration 42: a tile beside one of our buildings (not the HQ) is its doorstep -- the school spawns onto it, the
     *  refinery is reached over it. Dug to -9 it is neither (GSF as B, 2026-09-25: the school's last dry neighbour was a
     *  seat's pit, two miners stood on the other two, and no landscaper came out after r300 in any of 14 losses). */
    private boolean doorstep(MapLocation n) throws GameActionException {
        // 42b: only the buildings that spawn (every building's doorstep cost RandomSoup1 22% of ring); 42c: and only
        // when the building would be left with fewer than three other tiles it can spawn onto (dry, within 3 of its
        // elevation) -- the school keeps a door, the seats keep their dig tiles (42b cost RandomSoup1 12%)
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i];
            if ((f.type != RobotType.DESIGN_SCHOOL && f.type != RobotType.FULFILLMENT_CENTER) || Nav.cheb(f.location, n) > 1) continue;
            int fe = rc.canSenseLocation(f.location) ? rc.senseElevation(f.location) : 0, doors = 0;
            for (int k = 8; --k >= 0;) { MapLocation m = f.location.add(DIRS[k]); if (m.equals(n) || !rc.onTheMap(m) || !rc.canSenseLocation(m) || rc.senseFlooding(m)) continue;
                if (Math.abs(rc.senseElevation(m) - fe) <= GameConstants.MAX_DIRT_DIFFERENCE) doors++; }
            if (doors < 3) return true; }
        return false;
    }

    private void attack() throws GameActionException {
        if (floodDanger() && climb()) return;
        // bury an adjacent enemy building; dig from anything else adjacent when empty-handed
        RobotInfo tgt = null;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && (tgt == null || e.type == RobotType.HQ || loc.distanceSquaredTo(e.location) < loc.distanceSquaredTo(tgt.location))) tgt = e; }
        if (tgt != null && rc.isReady()) {
            if (loc.isAdjacentTo(tgt.location)) {
                Direction d = loc.directionTo(tgt.location);
                if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(d)) { rc.depositDirt(d); buryDeposits++; Debug.log("@bury t=" + tgt.type.ordinal() + " onIt=" + tgt.dirtCarrying); return; }
                for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (n.equals(tgt.location) || !rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && r.type.isBuilding()) continue; rc.digDirt(dd); digs++; return; }
                return;
            }
            nav.setTarget(tgt.location); nav.step(); return;
        }
        // carry a load before walking over
        if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit && rc.isReady() && (MapState.home == null || Nav.cheb(loc, MapState.home) > 2)) {
            for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (!rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null) continue; rc.digDirt(dd); digs++; return; }
        }
        MapLocation g = MapState.enemyHQGuess();
        if (g == null) g = MapState.center();
        nav.setTarget(g); nav.step();
    }
}
