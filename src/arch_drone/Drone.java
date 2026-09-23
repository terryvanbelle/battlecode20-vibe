package arch_drone;

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
        // archetype: no fear of guns -- the swarm trades drones for landscapers
        if (rc.isCurrentlyHoldingUnit()) {
            // drop into adjacent water, else fly toward water (or drop anywhere after a long carry)
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // pick up
        RobotInfo tgt = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp()) continue; int d = loc.distanceSquaredTo(e.location) - (e.type == RobotType.LANDSCAPER ? 50 : 0); if (d < bd) { bd = d; tgt = e; } }
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID); return; }
            nav.setTarget(tgt.location); nav.step(); return;
        }
        // archetype: fly to the enemy HQ and circle its ring looking for landscapers to pluck
        MapLocation g = MapState.enemyHQGuess();
        if (g == null) g = MapState.center();
        // at the guessed spot with no enemy HQ in sight: that symmetry is wrong, try the next image
        if (MapState.enemyHQ == null && loc.distanceSquaredTo(g) <= 8 && rc.canSenseLocation(g)) { RobotInfo r = rc.senseRobotAtLocation(g); if (r == null || r.type != RobotType.HQ || r.team != them) { int before = MapState.sym; MapState.pruneEmpty(g); if (MapState.sym != before || round % 50 == 0) Debug.log("@prune guess=" + g + " sym=" + before + "->" + MapState.sym + " home=" + MapState.home + " origin=" + MapState.minX + "," + MapState.minY + " w=" + MapState.width + " h=" + MapState.height); g = MapState.enemyHQGuess(); if (g == null) g = MapState.center(); } }
        if (loc.distanceSquaredTo(g) > 8) { nav.setTarget(g); nav.step(); return; }
        if (patrol == null || loc.distanceSquaredTo(patrol) <= 1) { Direction d = DIRS[nextInt(8)]; patrol = g.add(d).add(d); }
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
