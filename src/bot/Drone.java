package bot;

import battlecode.common.*;

/**
 * Delivery drone. Carrying an enemy unit: fly to the nearest known water and drop it in. Empty:
 * pick up any enemy miner/landscaper (or a cow) within reach, else patrol between our HQ and the
 * enemy HQ guess looking for one. Never enters the shooting radius (r2 15) of an enemy HQ or net
 * gun that is in sight.
 */
public strictfp class Drone extends Robot {
    private MapLocation water;                 // nearest flooded tile seen
    private MapLocation patrol;
    private int pickups = 0, drops = 0, homePickups = 0;

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat at=" + loc + " pickups=" + pickups + " home=" + homePickups + " drops=" + drops + " holding=" + rc.isCurrentlyHoldingUnit());
        // remember water
        if (water == null || round % 5 == 0) { MapLocation[] near = nearWater(); if (near != null) water = near[0]; }
        // danger: an enemy gun in sight
        MapLocation gun = null; int gd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot()) { int d = loc.distanceSquaredTo(e.location); if (d < gd) { gd = d; gun = e.location; } } }
        if (gun != null && gd <= 24 && fleeFrom(gun)) return;
        if (rc.isCurrentlyHoldingUnit()) {
            // drop into adjacent water, else fly toward water (or drop anywhere after a long carry)
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // pick up. Iteration 23: from HOME_ROUND a drone is a home guard -- only enemies within CHASE_RADIUS of the HQ,
        // landscapers before miners, anything on the ring or beside the HQ first.
        MapLocation home = MapState.home; boolean guard = round >= C.HOME_ROUND && home != null;
        RobotInfo tgt = null; long bd = Long.MAX_VALUE;
        for (int i = nEnemy; --i >= 0;) {
            RobotInfo e = enemies[i]; if (!e.type.canBePickedUp()) continue;
            int hc = home == null ? 99 : Nav.cheb(e.location, home);
            if (guard && hc > C.CHASE_RADIUS) continue;
            long d = loc.distanceSquaredTo(e.location) + (guard && e.type != RobotType.LANDSCAPER ? 1000 : 0) - (guard && hc <= 1 ? 500 : 0);
            if (d < bd) { bd = d; tgt = e; }
        }
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; if (guard) homePickups++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID + " guard=" + guard + " hc=" + Nav.cheb(tgt.location, home == null ? loc : home)); return; }
            if (gun == null || tgt.location.distanceSquaredTo(gun) > 15) { nav.setTarget(tgt.location); nav.step(); return; }
        }
        // patrol: a guard walks the annulus GUARD_INNER..GUARD_OUTER round the HQ; before that the line from home to the enemy HQ guess
        if (patrol == null || loc.distanceSquaredTo(patrol) <= (guard ? 2 : 4) || (guard && Nav.cheb(patrol, home) > C.GUARD_OUTER)) {
            MapLocation g = MapState.enemyHQGuess(), h = home;
            if (guard) {
                int r = C.GUARD_INNER + nextInt(C.GUARD_OUTER - C.GUARD_INNER + 1), k = nextInt(8 * r);
                int dx, dy; if (k < 2 * r) { dx = -r + k; dy = -r; } else if (k < 4 * r) { dx = r; dy = -r + (k - 2 * r); } else if (k < 6 * r) { dx = r - (k - 4 * r); dy = r; } else { dx = -r; dy = r - (k - 6 * r); }
                patrol = new MapLocation(h.x + dx, h.y + dy);
                if (!rc.onTheMap(patrol)) patrol = h;
            }
            else if (g != null && h != null) { int t = nextInt(5); patrol = new MapLocation(h.x + (g.x - h.x) * t / 5, h.y + (g.y - h.y) * t / 5); }
            else patrol = new MapLocation(loc.x + nextInt(21) - 10, loc.y + nextInt(21) - 10);
        }
        nav.setTarget(patrol); if (!nav.step()) patrol = null;
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
