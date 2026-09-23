package bot;

import battlecode.common.*;

/**
 * Delivery drone. Carrying an enemy unit: fly to the nearest known water and drop it in. Carrying
 * one of ours (Iteration 6, the ferry): a landscaper born inside the sealed pocket is lifted off the
 * spawn tile from the gate ring tile and dropped on the lowest free exposed ring tile, else on a dry
 * free tile at distance RING+1 next to the wall. Empty: ferry first, then pick up any enemy
 * miner/landscaper (or a cow) within reach, else patrol between our HQ and the enemy HQ guess.
 * Never enters the shooting radius (r2 15) of an enemy HQ or net gun that is in sight.
 */
public strictfp class Drone extends Robot {
    private MapLocation water;                 // nearest flooded tile seen
    private MapLocation patrol;
    private boolean holdingFriend = false;
    private MapLocation ferryTarget; private int ferryTargetRound = -100;
    private int pickups = 0, drops = 0, ferried = 0;

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); reportDrone(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " ferried=" + ferried + " holding=" + rc.isCurrentlyHoldingUnit());
        // remember water
        if (water == null || round % 5 == 0) { MapLocation[] near = nearWater(); if (near != null) water = near[0]; }
        // danger: an enemy gun in sight
        MapLocation gun = null; int gd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot()) { int d = loc.distanceSquaredTo(e.location); if (d < gd) { gd = d; gun = e.location; } } }
        if (gun != null && gd <= 24 && !holdingFriend && fleeFrom(gun)) return;
        if (rc.isCurrentlyHoldingUnit()) {
            if (holdingFriend) { ferry(); return; }
            // drop into adjacent water, else fly toward water (or drop anywhere after a long carry)
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        holdingFriend = false;
        // ferry: a landscaper waiting inside the pocket, and somewhere to put it
        RobotInfo waiting = null;
        if (MapState.home != null && MapState.gateG != null)
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.LANDSCAPER && Nav.cheb(f.location, MapState.home) < C.RING) { waiting = f; break; } }
        if (waiting != null) {
            if (rc.canPickUpUnit(waiting.ID) && ferryTargetFresh() != null) { rc.pickUpUnit(waiting.ID); holdingFriend = true; pickups++; Debug.log("@lift id=" + waiting.ID + " from=" + waiting.location); return; }
            if (!loc.equals(MapState.gateG)) { nav.setTarget(MapState.gateG); nav.step(); }
            return;   // hover on the gate until the landscaper reaches F and a tile frees up
        }
        // pick up
        RobotInfo tgt = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp()) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd) { bd = d; tgt = e; } }
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID); return; }
            if (gun == null || tgt.location.distanceSquaredTo(gun) > 15) { nav.setTarget(tgt.location); nav.step(); return; }
        }
        // patrol: between home and the enemy HQ guess
        if (patrol == null || loc.distanceSquaredTo(patrol) <= 4) {
            MapLocation g = MapState.enemyHQGuess(), h = MapState.home;
            if (g != null && h != null) { int t = nextInt(5); patrol = new MapLocation(h.x + (g.x - h.x) * t / 5, h.y + (g.y - h.y) * t / 5); }
            else patrol = new MapLocation(loc.x + nextInt(21) - 10, loc.y + nextInt(21) - 10);
        }
        nav.setTarget(patrol); if (!nav.step()) patrol = null;
    }

    /** Carry a landscaper of ours to the ferry target and drop it there. */
    private void ferry() throws GameActionException {
        MapLocation t = ferryTargetFresh();
        if (t == null) { if (MapState.gateG != null && !loc.equals(MapState.gateG)) { nav.setTarget(MapState.gateG); nav.step(); } return; }
        if (loc.isAdjacentTo(t)) {
            Direction d = loc.directionTo(t);
            if (rc.canDropUnit(d) && !rc.senseFlooding(t) && !rc.isLocationOccupied(t)) { rc.dropUnit(d); drops++; ferried++; holdingFriend = false; Debug.log("@ferry to=" + t); ferryTarget = null; return; }
            ferryTarget = null; return;   // taken or flooded meanwhile: re-pick next turn
        }
        nav.setTarget(t); if (!nav.step()) ferryTarget = null;
    }

    /** The ferry target, recomputed every 10 rounds: the lowest free exposed ring tile (not the gate), else a dry free
     *  tile at RING+1 beside the lowest exposed ring tile it can reach. */
    private MapLocation ferryTargetFresh() throws GameActionException {
        if (ferryTarget != null && round - ferryTargetRound < 10) return ferryTarget;
        ferryTargetRound = round; ferryTarget = null;
        MapLocation home = MapState.home; if (home == null) return null;
        MapLocation best = null; long bs = Long.MAX_VALUE;
        for (int dx = -C.RING; dx <= C.RING; dx++) for (int dy = -C.RING; dy <= C.RING; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != C.RING) continue;
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t) || isGate(t) || !exposed(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t) || rc.isLocationOccupied(t)) continue;
            long s = (long) rc.senseElevation(t) * 100 + loc.distanceSquaredTo(t);
            if (s < bs) { bs = s; best = t; }
        }
        if (best != null) { ferryTarget = best; return best; }
        int d1 = C.RING + 1;
        for (int dx = -d1; dx <= d1; dx++) for (int dy = -d1; dy <= d1; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != d1) continue;
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t) || rc.isLocationOccupied(t)) continue;
            int e = rc.senseElevation(t);
            if (e < waterLevel(round + 30)) continue;   // it would drown before it could raise itself
            boolean corner = Math.abs(dx) == d1 && Math.abs(dy) == d1, mid = dx == 0 || dy == 0;
            long s = (corner ? 0 : mid ? 2000 : 4000) + loc.distanceSquaredTo(t) - e;   // cheap: corners, then midpoints, then near and high
            if (s < bs) { bs = s; best = t; }
        }
        ferryTarget = best; return best;
    }

    /** The nearest flooded tile in sight, or null. */
    private MapLocation[] nearWater() throws GameActionException {
        MapLocation best = null; int bd = 1 << 30;
        for (int dx = -4; dx <= 4; dx++) for (int dy = -4; dy <= 4; dy++) {
            MapLocation l = new MapLocation(loc.x + dx, loc.y + dy);
            if (!rc.canSenseLocation(l) || !rc.senseFlooding(l)) continue;
            int d = dx * dx + dy * dy; if (d < bd) { bd = d; best = l; }
        }
        return best == null ? null : new MapLocation[]{best};
    }
}
