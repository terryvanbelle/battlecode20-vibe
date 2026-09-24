package arch_swarm;

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
    private boolean raiding = false, holdingFriend = false;

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " holding=" + rc.isCurrentlyHoldingUnit());
        // remember water
        if (water == null || round % 5 == 0) { MapLocation[] near = nearWater(); if (near != null) water = near[0]; }
        // danger: an enemy gun in sight
        MapLocation gun = null; int gd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot()) { int d = loc.distanceSquaredTo(e.location); if (d < gd) { gd = d; gun = e.location; } } }
        boolean raidTime = round >= C.RAID_ROUND && MapState.enemyHQGuess() != null;
        if (gun != null && gd <= (raidTime ? 15 : 24) && !raiding && fleeFrom(gun)) return;
        if (rc.isCurrentlyHoldingUnit() && holdingFriend) {   // arch_swarm: deliver one of our landscapers onto a free tile beside the enemy HQ
            MapLocation ehq = MapState.enemyHQ != null ? MapState.enemyHQ : MapState.enemyHQGuess();
            if (ehq == null) { holdingFriend = false; return; }
            Direction bestD = null; int bd = 1 << 30;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, ehq) > 2 || !rc.canDropUnit(d) || rc.senseFlooding(n)) continue; int dd = n.distanceSquaredTo(ehq); if (dd < bd) { bd = dd; bestD = d; } }
            if (bestD != null) { rc.dropUnit(bestD); drops++; holdingFriend = false; Debug.log("@deliver at=" + loc.add(bestD)); return; }
            nav.setTarget(ehq); nav.step(); return;
        }
        holdingFriend = false;
        if (rc.isCurrentlyHoldingUnit()) {
            // drop into adjacent water, else fly toward water (or drop anywhere after a long carry)
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // arch_swarm: the wave. Scout the guess from outside gun range, gather at RAID_RALLY, charge together (or after
        // RAID_LATEST), dash for the nearest landscaper in sight and drop it in the water; the HQ shoots one a round.
        if (raidTime) {
            MapLocation ehq = MapState.enemyHQGuess();
            if (MapState.enemyHQ == null) { if (!rc.canSenseLocation(ehq)) approachSafely(ehq); return; }
            int near = 0; boolean follow = false;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.DELIVERY_DRONE) continue; near++; if (Nav.cheb(f.location, ehq) < C.RAID_RALLY - 1) follow = true; }
            raiding = near + 1 >= C.RAID_SIZE || follow || round >= C.RAID_LATEST;
            if (raiding) {
                RobotInfo v = null; int vd = 1 << 30;
                for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp()) continue; if (rc.canPickUpUnit(e.ID)) { rc.pickUpUnit(e.ID); pickups++; raidKills++; Debug.log("@pickup t=" + e.type.ordinal() + " id=" + e.ID + " raid=true"); return; } int d = loc.distanceSquaredTo(e.location) + (e.type == RobotType.LANDSCAPER ? 0 : 100); if (d < vd) { vd = d; v = e; } }
                if (v != null) { nav.setTarget(v.location); nav.step(); return; }
                // nothing left to lift here: fetch one of our helpers from home and drop it on their ring
                MapLocation home = MapState.home;
                if (home != null) {
                    for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.LANDSCAPER && Nav.cheb(f.location, home) == 2 && rc.canPickUpUnit(f.ID)) { rc.pickUpUnit(f.ID); holdingFriend = true; Debug.log("@lift-helper id=" + f.ID); return; } }
                    if (Nav.cheb(loc, home) > 3) { nav.setTarget(home); nav.step(); return; }
                    for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.LANDSCAPER && Nav.cheb(f.location, home) == 2) { nav.setTarget(f.location); nav.step(); return; } }
                }
                approachSafely(ehq); return;
            }
            MapLocation h = MapState.home != null ? MapState.home : loc;
            int dx = Integer.signum(h.x - ehq.x), dy = Integer.signum(h.y - ehq.y);
            MapLocation rally = new MapLocation(ehq.x + dx * C.RAID_RALLY, ehq.y + dy * C.RAID_RALLY);
            if (loc.distanceSquaredTo(rally) > 8) { nav.setTarget(rally); nav.step(); }
            return;
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

    /** One step toward t onto a tile outside r2 15 of it (the HQ's reach). */
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
