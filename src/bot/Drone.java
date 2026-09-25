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
    private int pickups = 0, drops = 0;

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " holding=" + rc.isCurrentlyHoldingUnit());
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
        // Iteration 48, the home guard: the raids that take 35% of the band's games lift our seats with 8-25 drones and drop
        // 1-11 landscapers on the freed ring tiles, burying the HQ in 15-25 rounds. A drone that hunts toward the enemy is
        // dead by r1000; one that stays within GUARD_BOX of the HQ lifts what lands on the ring. Landscapers on the ring or
        // beside the HQ first, then anything of theirs within the box.
        MapLocation home = MapState.home;
        RobotInfo tgt = null; long bs = Long.MAX_VALUE;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp() || home == null || Nav.cheb(e.location, home) > C.GUARD_BOX + 2) continue;
            int hd = home == null ? 9 : Nav.cheb(e.location, home);
            long s = (e.type == RobotType.LANDSCAPER ? 0 : 1000000L) + (hd <= 1 ? 0 : 10000L) + loc.distanceSquaredTo(e.location);
            if (s < bs) { bs = s; tgt = e; } }
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID + " home=" + Nav.cheb(tgt.location, home)); return; }
            if (gun == null || tgt.location.distanceSquaredTo(gun) > 15) { nav.setTarget(tgt.location); nav.step(); return; }
        }
        // patrol: a box around our HQ
        if (patrol == null || loc.distanceSquaredTo(patrol) <= 2 || (home != null && Nav.cheb(patrol, home) > C.GUARD_BOX)) {
            if (home != null) patrol = new MapLocation(home.x + nextInt(2 * C.GUARD_BOX + 1) - C.GUARD_BOX, home.y + nextInt(2 * C.GUARD_BOX + 1) - C.GUARD_BOX);
            else patrol = new MapLocation(loc.x + nextInt(9) - 4, loc.y + nextInt(9) - 4);
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
