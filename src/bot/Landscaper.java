package bot;

import battlecode.common.*;

/**
 * Landscaper, four roles (Iteration 6, the citadel). The wall is the ring of tiles at Chebyshev
 * C.RING from the HQ; everything inside it is the pocket, which can never flood once the ring is
 * sealed (the flood only spreads from a flooded neighbour), so the pocket holds the buildings and is
 * also an infinite dirt mine.
 *
 *  SEAT    on an exposed ring tile: raise the LOWEST exposed tile among itself and its ring
 *          neighbours (the wall is worth its minimum); dig from outside, else from the pocket,
 *          else borrow from a taller ring neighbour.
 *  HELPER  on a distance RING+1 corner or midpoint: keep itself just above the coming water,
 *          feed the lowest adjacent exposed ring tile, dig from further out.
 *  INNER   born inside after the ring rose: wait on the spawn tile for a drone to lift it over the
 *          wall (it must not dig its own tile: the school spawns onto it); feed the ring while
 *          waiting, dig the HQ out when it is buried.
 *  ATTACK  nothing else to do outside: walk to the enemy HQ guess and bury the first enemy building.
 */
public strictfp class Landscaper extends Robot {
    private static final int SEAT = 0, HELPER = 1, INNER = 2, ATTACK = 3, NONE = -1;
    private int role = NONE;
    private MapLocation seat, post;
    private final MapLocation[] badSeat = new MapLocation[16]; private int nBad = 0;
    private int digs = 0, deposits = 0, hqDigs = 0, buryDeposits = 0, equalised = 0, borrowed = 0, helperDeps = 0, innerDeps = 0, idle = 0;

    Landscaper(RobotController rc) { super(rc); nav.stallLimit = 30; }

    @Override protected void turn() throws GameActionException {
        int b0 = Clock.getBytecodeNum();
        try { turn2(); } finally { int b1 = Clock.getBytecodeNum(); if (b1 > 8500) Debug.log("@bcprof start=" + b0 + " total=" + b1 + " role=" + role + " atSeat=" + (seat != null && seat.equals(loc)) + " atPost=" + (post != null && post.equals(loc))); }
    }

    private void turn2() throws GameActionException {
        sense(); reportDrone(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat role=" + role + " seat=" + seat + " post=" + post + " digs=" + digs + " deps=" + deposits + " eq=" + equalised + " borrow=" + borrowed + " helperDeps=" + helperDeps + " innerDeps=" + innerDeps + " hqDigs=" + hqDigs + " bury=" + buryDeposits + " idle=" + idle + " elev=" + rc.senseElevation(loc));
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        if (role == INNER && Nav.cheb(loc, home) >= C.RING) {   // a drone lifted us out: the tile it chose is ours
            Debug.log("@ferried to=" + loc);
            if (onRing(loc)) { role = SEAT; seat = loc; }
            else if (Nav.cheb(loc, home) == C.RING + 1) { role = HELPER; post = loc; }
            else role = NONE;
        }
        if (role == NONE || role == SEAT && seat != null && !seat.equals(loc) && occupiedByOther(seat)) chooseRole(home);
        boolean ready = rc.isReady();
        switch (role) {
            case SEAT: seatTurn(home); break;
            case HELPER: helperTurn(home); break;
            case INNER: innerTurn(home); break;
            default: attack(); break;
        }
        if (ready && rc.isReady() && role != ATTACK) idle++;
    }

    // ---------------------------------------------------------------- roles
    private void chooseRole(MapLocation home) throws GameActionException {
        if (seat != null && !seat.equals(loc) && nav.target() == seat && nav.stalled()) { if (nBad < 16) badSeat[nBad++] = seat; Debug.log("@badseat " + seat); seat = null; }
        boolean inside = Nav.cheb(loc, home) < C.RING;
        if (inside && sealedFromHere(home)) { role = INNER; seat = null; Debug.log("@inner sealed"); return; }
        seat = pickSeat(home);
        if (seat != null) { role = SEAT; return; }
        if (inside) { role = INNER; Debug.log("@inner ring full"); return; }
        post = pickPost(home);
        if (post != null) { role = HELPER; Debug.log("@helper post=" + post); return; }
        role = ATTACK; Debug.log("@attacker ring full");
    }

    /** From inside: is every ring tile next to me more than 3 above my tile? Then I cannot get out. */
    private boolean sealedFromHere(MapLocation home) throws GameActionException {
        int myE = rc.senseElevation(loc);
        for (int i = 8; --i >= 0;) {
            MapLocation n = loc.add(DIRS[i]); if (!onRing(n) || isGate(n) || !rc.canSenseLocation(n)) continue;   // the gate is the drone's hover tile, never a way out
            if (Math.abs(rc.senseElevation(n) - myE) > GameConstants.MAX_DIRT_DIFFERENCE) continue;
            if (rc.isLocationOccupied(n)) continue;   // a seat (or anything else) in the way
            return false;
        }
        return true;
    }

    private boolean occupiedByOther(MapLocation l) throws GameActionException {
        if (!rc.canSenseLocation(l)) return false;
        RobotInfo r = rc.senseRobotAtLocation(l);
        return r != null && r.ID != id;
    }

    /** The nearest exposed ring tile that is free (or holds a unit that will move), not flooded, reachable (within 3 of our own elevation). */
    private MapLocation pickSeat(MapLocation home) throws GameActionException {
        MapLocation best = null; int bd = 1 << 30; int myE = rc.senseElevation(loc);
        for (int dx = -C.RING; dx <= C.RING; dx++) for (int dy = -C.RING; dy <= C.RING; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != C.RING) continue;
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t) || !exposed(t) || isGate(t)) continue;
            boolean bad = false; for (int k = nBad; --k >= 0;) if (badSeat[k].equals(t)) { bad = true; break; }
            if (bad) continue;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                RobotInfo r0 = rc.senseRobotAtLocation(t);
                if (r0 != null && r0.ID != id && (r0.type == RobotType.LANDSCAPER && r0.team == us || r0.type.isBuilding())) continue;
                if (Math.abs(rc.senseElevation(t) - myE) > GameConstants.MAX_DIRT_DIFFERENCE && !t.equals(loc)) continue;   // an empty cliff is unreachable
            }
            int d = loc.distanceSquaredTo(t) + nextInt(2);
            if (d < bd) { bd = d; best = t; }
        }
        return best;
    }

    /** A free tile at Chebyshev RING+1 on a corner (first) or an edge midpoint, next to the lowest exposed ring tile it can feed. */
    private MapLocation pickPost(MapLocation home) throws GameActionException {
        MapLocation best = null; long bs = Long.MAX_VALUE; int myE = rc.senseElevation(loc); int d1 = C.RING + 1;
        for (int dx = -d1; dx <= d1; dx++) for (int dy = -d1; dy <= d1; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != d1) continue;
            boolean corner = Math.abs(dx) == d1 && Math.abs(dy) == d1, mid = dx == 0 || dy == 0;   // preferred: they block the fewest dig sources
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t)) continue;
            int lowest = Integer.MAX_VALUE;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                int e = rc.senseElevation(t); if (Math.abs(e - myE) > 6) continue;
                RobotInfo r = rc.senseRobotAtLocation(t);
                if (r != null && r.ID != id && (r.type.isBuilding() || (r.type == RobotType.LANDSCAPER && r.team == us))) continue;
                for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (onRing(n) && exposed(n) && rc.canSenseLocation(n)) lowest = Math.min(lowest, rc.senseElevation(n)); }
                if (lowest == Integer.MAX_VALUE) continue;
            }
            long s = (long) (lowest == Integer.MAX_VALUE ? 0 : lowest) * 10000 + (corner ? 0 : mid ? 2000 : 4000) + loc.distanceSquaredTo(t) + nextInt(2) - (t.equals(loc) ? 6000 : 0);   // a ferried landscaper keeps the tile it was dropped on
            if (s < bs) { bs = s; best = t; }
        }
        return best;
    }

    // ---------------------------------------------------------------- shared actions
    /** Deposit onto the lowest exposed ring tile adjacent to us (or CENTER when allowed and lowest). Returns true if it did. */
    private boolean feedLowest(boolean selfAllowed) throws GameActionException { return feedLowest(selfAllowed, Integer.MAX_VALUE); }
    /** cap: only tiles below it may be fed (before WALL_START the ring stays within 3 of the HQ so the school can still spawn over it). */
    private boolean feedLowest(boolean selfAllowed, int cap) throws GameActionException {
        if (rc.getDirtCarrying() <= 0) return false;
        int myE = rc.senseElevation(loc); Direction bestD = null; int be = Integer.MAX_VALUE;
        if (selfAllowed && myE < cap) { bestD = Direction.CENTER; be = myE; }
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canSenseLocation(n) || !exposed(n)) continue;
            RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
            int e = rc.senseElevation(n); if (e < cap && e < be - (selfAllowed ? C.WALL_LEVEL_SLACK : 0)) { be = e; bestD = d; } }
        if (bestD == null || !rc.canDepositDirt(bestD)) return false;
        rc.depositDirt(bestD); deposits++; if (bestD != Direction.CENTER) equalised++; return true;
    }

    /** Dig from an adjacent tile: outside the ring first (empty, then enemy, then friend), then the pocket; never the HQ, a building or a ring tile. */
    private boolean digSource(MapLocation home) throws GameActionException {
        Direction bestD = null; long be = Long.MAX_VALUE;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || onRing(n) || n.equals(home) || !rc.canDigDirt(d)) continue;
            boolean pocket = Nav.cheb(n, home) < C.RING;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.type.isBuilding()) continue;
            long e = rc.senseElevation(n) + (r == null ? 0 : r.team == us ? 100000 : 1000) + (pocket ? 1000000 : 0);
            if (e < be) { be = e; bestD = d; }
        }
        if (bestD == null) return false;
        rc.digDirt(bestD); digs++; return true;
    }

    /** A seat with nothing to dig from borrows from the tallest ring neighbour at least WALL_BORROW_MARGIN taller. */
    private boolean borrow() throws GameActionException {
        int myE = rc.senseElevation(loc); int bh = myE + C.WALL_BORROW_MARGIN; Direction bestD = null;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canDigDirt(d)) continue; int e = rc.senseElevation(n); if (e > bh) { bh = e; bestD = d; } }
        if (bestD == null) return false;
        rc.digDirt(bestD); digs++; borrowed++; return true;
    }

    private boolean digOutHQ(MapLocation home) throws GameActionException {
        if (hqInfo == null || hqInfo.dirtCarrying <= 0 || !loc.isAdjacentTo(home)) return false;
        Direction d = loc.directionTo(home);
        if (!rc.canDigDirt(d)) return false;
        rc.digDirt(d); hqDigs++; digs++; Debug.log("@hqdig buried=" + hqInfo.dirtCarrying); return true;
    }

    private boolean buryAdjacentEnemy() throws GameActionException {
        if (rc.getDirtCarrying() <= 0) return false;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return true; } }
        return false;
    }

    // ---------------------------------------------------------------- turns
    private void seatTurn(MapLocation home) throws GameActionException {
        if (!loc.equals(seat)) { if (floodDanger() && climb()) return; nav.setTarget(seat); nav.step(); if (loc.equals(seat)) Debug.log("@seated at=" + seat); return; }
        if (!rc.isReady()) return;
        int cap = Integer.MAX_VALUE;
        if (round < C.WALL_START && rc.canSenseLocation(home)) cap = rc.senseElevation(home) + GameConstants.MAX_DIRT_DIFFERENCE;   // level to HQ+3 at most: the pocket must still climb over us
        if (digOutHQ(home) || buryAdjacentEnemy() || feedLowest(true, cap)) return;
        if (cap == Integer.MAX_VALUE) {
            if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
            if (digSource(home) || borrow()) return;
        } else if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit) digSource(home);   // pre-dig, then wait
    }

    private void helperTurn(MapLocation home) throws GameActionException {
        if (!loc.equals(post)) {
            if (occupiedByOther(post)) { RobotInfo r = rc.canSenseLocation(post) ? rc.senseRobotAtLocation(post) : null; if (r != null && (r.type.isBuilding() || r.type == RobotType.LANDSCAPER)) { post = pickPost(home); if (post == null) { role = ATTACK; return; } } }
            if (floodDanger() && climb()) return;
            if (nav.target() == post && nav.stalled()) { Debug.log("@badpost " + post); post = null; role = ATTACK; return; }   // Iteration 5 lost six of sixteen landscapers to posts they never reached
            nav.setTarget(post); nav.step(); if (loc.equals(post)) Debug.log("@posted at=" + post); return;
        }
        if (!rc.isReady()) return;
        boolean lowSelf = rc.senseElevation(loc) < waterLevel(round + 60) + 2;
        if (rc.getDirtCarrying() > 0 && lowSelf && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
        if (feedLowest(false)) { helperDeps++; return; }
        // dig from further out: any adjacent tile beyond the ring, no building, no friend
        Direction bestD = null; long be = Long.MAX_VALUE;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || Nav.cheb(n, home) <= C.RING || !rc.canDigDirt(d)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && (r.type.isBuilding() || r.team == us)) continue;
            long e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
        if (bestD == null && rc.canDigDirt(Direction.CENTER) && rc.senseElevation(loc) > waterLevel(round + 200) + 3) bestD = Direction.CENTER;
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }

    private void innerTurn(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        if (digOutHQ(home) || buryAdjacentEnemy()) return;
        if (rc.getDirtCarrying() > 0 && feedLowest(false)) { innerDeps++; return; }
        // wait ON the spawn tile: every other pocket tile is a building site. (The school cannot spawn while we stand
        // there, which is the ferry's natural throttle.) Never dig: a hole under the spawn tile stops the school.
        if (!isSpawnTile(loc) && MapState.gateF != null && loc.isAdjacentTo(MapState.gateF) && !rc.isLocationOccupied(MapState.gateF)) tryMove(loc.directionTo(MapState.gateF));
    }

    private void attack() throws GameActionException {
        if (floodDanger() && climb()) return;
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
        if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit && rc.isReady() && Nav.cheb(loc, MapState.home) > C.RING + 1) {
            for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (!rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null) continue; rc.digDirt(dd); digs++; return; }
        }
        MapLocation g = MapState.enemyHQGuess();
        if (g == null) g = MapState.center();
        nav.setTarget(g); nav.step();
    }
}
