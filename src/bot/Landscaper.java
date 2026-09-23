package bot;

import battlecode.common.*;

/**
 * Landscaper. WALL: take the nearest free tile of the ring around our HQ, then forever: dig any
 * dirt off the HQ, else deposit carried dirt on our own tile (raising the wall), else dig from an
 * adjacent tile outside the ring that holds no building of ours. ATTACK (when the ring is full):
 * walk to the enemy HQ (sighted or the symmetry guess) and bury the first enemy building met.
 */
public strictfp class Landscaper extends Robot {
    private MapLocation seat;                 // our ring tile, once chosen
    private boolean attacker = false;
    private int digs = 0, deposits = 0, hqDigs = 0, buryDeposits = 0;

    Landscaper(RobotController rc) { super(rc); }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat seat=" + seat + " attacker=" + attacker + " digs=" + digs + " deps=" + deposits + " hqDigs=" + hqDigs + " bury=" + buryDeposits + " elev=" + rc.senseElevation(loc));
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        if (!attacker) {
            if (seat == null || !seat.equals(loc) && occupiedByOther(seat)) seat = pickSeat(home);
            if (seat == null) { attacker = true; Debug.log("@attacker ring full"); }
        }
        if (attacker) { attack(); return; }
        if (!loc.equals(seat)) { if (floodDanger() && climb()) return; nav.setTarget(seat); nav.step(); if (loc.equals(seat)) Debug.log("@seated at=" + seat); return; }
        wall(home);
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

    private void wall(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        Direction toHQ = loc.directionTo(home);
        // 1. the HQ is being buried: dig it out
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && rc.canDigDirt(toHQ)) { rc.digDirt(toHQ); hqDigs++; digs++; Debug.log("@hqdig buried=" + hqInfo.dirtCarrying); return; }
        // 2. an enemy building or unit adjacent: bury it (deposit) if we carry, it is cheap denial
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.getDirtCarrying() > 0 && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return; } }
        // 3. raise our own tile
        if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
        // 4. dig from outside the ring: the lowest adjacent tile that is not the HQ, not a ring tile, not one of our buildings
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || onRing(n) || n.equals(home) || !rc.canDigDirt(d)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.team == us && r.type.isBuilding()) continue;
            int e = rc.senseElevation(n) + (r != null ? 1000 : 0);   // prefer empty tiles
            if (e < be) { be = e; bestD = d; }
        }
        if (bestD != null) { rc.digDirt(bestD); digs++; }
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
        if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit && rc.isReady() && Nav.cheb(loc, MapState.home) > 2) {
            for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (!rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null) continue; rc.digDirt(dd); digs++; return; }
        }
        MapLocation g = MapState.enemyHQGuess();
        if (g == null) g = MapState.center();
        nav.setTarget(g); nav.step();
    }
}
