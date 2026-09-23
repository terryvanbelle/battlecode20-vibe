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
    private int pickups = 0, drops = 0, raidKills = 0;
    private boolean raiding = false;

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2 || round >= C.RAID_ROUND - 100) readBlock(); probeEdges();   // every round near the raid: the HQ re-posts the enemy HQ once per 100
        if (round % 100 == 0) Debug.log("@dronestat at=" + loc + " pickups=" + pickups + " drops=" + drops + " raidKills=" + raidKills + " raiding=" + raiding + " holding=" + rc.isCurrentlyHoldingUnit() + " guess=" + MapState.enemyHQGuess());
        // remember water
        if (water == null || round % 5 == 0) { MapLocation[] near = nearWater(); if (near != null) water = near[0]; }
        // danger: an enemy gun in sight
        MapLocation gun = null; int gd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot()) { int d = loc.distanceSquaredTo(e.location); if (d < gd) { gd = d; gun = e.location; } } }
        boolean raidTime = round >= C.RAID_ROUND && MapState.enemyHQGuess() != null;
        if (gun != null && gd <= (raidTime ? 15 : 24) && !raiding && fleeFrom(gun)) return;   // at raid time only the true shooting range: scouting needs r2 16-24, and the rally lies past the HQ for some
        if (rc.isCurrentlyHoldingUnit()) {
            // drop into adjacent water, else fly toward water (or drop anywhere after a long carry)
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // pick up
        RobotInfo tgt = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp()) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd) { bd = d; tgt = e; } }
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; if (raiding) raidKills++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID + " raid=" + raiding); return; }
            if (raiding || gun == null || tgt.location.distanceSquaredTo(gun) > 15) { nav.setTarget(tgt.location); nav.step(); return; }
        }
        // Iteration 9: the late raid. Scout the enemy HQ guess from just outside gun range (sense r2 24 > shoot r2 15) so a
        // wrong symmetry image is pruned; gather at RAID_RALLY; charge once RAID_SIZE are together or a friend already charged.
        if (raidTime) {
            MapLocation ehq = MapState.enemyHQGuess();
            if (MapState.enemyHQ == null) { if (!rc.canSenseLocation(ehq)) approachSafely(ehq); return; }   // look at the guess from outside gun range
            int near = 0; boolean follow = false;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.DELIVERY_DRONE) continue; near++; if (Nav.cheb(f.location, ehq) < C.RAID_RALLY - 1) follow = true; }
            raiding = near + 1 >= C.RAID_SIZE || follow || round >= C.RAID_LATEST;
            if (raiding) { approachSafely(ehq); return; }   // close in outside gun range until a seat is in sight; the pickup loop above then dashes for it (7 drones flying at the HQ itself were shot one a round at d2 9)
            MapLocation h = MapState.home != null ? MapState.home : loc;
            int dx = Integer.signum(h.x - ehq.x), dy = Integer.signum(h.y - ehq.y);
            MapLocation rally = new MapLocation(ehq.x + dx * C.RAID_RALLY, ehq.y + dy * C.RAID_RALLY);
            if (loc.distanceSquaredTo(rally) > 8) { nav.setTarget(rally); nav.step(); }
            return;
        }
        // patrol: between home and the enemy HQ guess
        if (patrol == null || loc.distanceSquaredTo(patrol) <= 4) {
            MapLocation g = MapState.enemyHQGuess(), h = MapState.home;
            if (g != null && h != null) { int t = nextInt(5); patrol = new MapLocation(h.x + (g.x - h.x) * t / 5, h.y + (g.y - h.y) * t / 5); }
            else patrol = new MapLocation(loc.x + nextInt(21) - 10, loc.y + nextInt(21) - 10);
        }
        nav.setTarget(patrol); if (!nav.step()) patrol = null;
    }

    /** One step toward t onto a tile outside r2 15 of it (the HQ's and a net gun's reach); a diagonal step from 25 can land at 13. */
    private void approachSafely(MapLocation t) throws GameActionException {
        if (!rc.isReady()) return;
        Direction bestD = null; int bdd = loc.distanceSquaredTo(t);
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!rc.canMove(d) || !allowedTile(n)) continue; int dd = n.distanceSquaredTo(t); if (dd > 15 && dd < bdd) { bdd = dd; bestD = d; } }
        if (bestD != null) { rc.move(bestD); loc = rc.getLocation(); }
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
