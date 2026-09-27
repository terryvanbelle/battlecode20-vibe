package r2s3;

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
    private boolean charging = false;
    private MapLocation guardAt;   // r2: the rush school (or the last enemy landscaper) near home this rush drone waits beside

    Drone(RobotController rc) { super(rc); avoidRing = true; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " holding=" + rc.isCurrentlyHoldingUnit() + " shield=" + (MapState.home != null && Nav.cheb(loc, MapState.home) == 2));
        if (round >= C.SHIELD_FROM && MapState.home != null && shield()) return;
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
        // r2: the rush guard (before the shield): an enemy landscaper within RUSH_GUARD of our HQ first, nearest the HQ first (the
        // one burying it), then an enemy miner on the ring; a drone born before r350 then waits beside the rush school, whose
        // newborns cannot act for ten rounds
        if (MapState.home != null && round < C.SHIELD_FROM && guard()) return;
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

    private boolean guard() throws GameActionException {
        MapLocation h = MapState.home; RobotInfo g = null; int gs = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; int c = Nav.cheb(e.location, h);
            if (e.type == RobotType.DESIGN_SCHOOL && c <= C.RUSH_HOME_DS && birth < 350) guardAt = e.location;
            if (!(e.type == RobotType.LANDSCAPER && c <= C.RUSH_GUARD) && !(e.type == RobotType.MINER && c == 1)) continue;
            int s = c * 1000 + loc.distanceSquaredTo(e.location); if (s < gs) { gs = s; g = e; } }
        if (g != null) {
            if (birth < 350 && g.type == RobotType.LANDSCAPER && guardAt == null) guardAt = g.location;
            if (rc.canPickUpUnit(g.ID)) { rc.pickUpUnit(g.ID); pickups++; Debug.log("@rushpick id=" + g.ID + " t=" + g.type.ordinal() + " dHQ=" + Nav.cheb(g.location, h)); return true; }
            nav.setTarget(g.location); nav.step(); return true;
        }
        if (guardAt == null) return false;
        if (rc.canSenseLocation(guardAt)) { RobotInfo s = rc.senseRobotAtLocation(guardAt); if ((s == null || s.team != them || s.type != RobotType.DESIGN_SCHOOL) && loc.distanceSquaredTo(guardAt) <= 2) guardAt = null; }
        if (guardAt != null && loc.distanceSquaredTo(guardAt) > 2) { nav.setTarget(guardAt); nav.step(); }
        return guardAt != null;
    }

    /** Iteration 81: hold a flooded Chebyshev-2 tile of our HQ. On one: never move again; lift an enemy unit beside us and keep
     *  it (a held unit is blocked). Otherwise fly to the nearest free one, or wait five out while none is flooded and free. */
    private MapLocation slot; private boolean posted = false;
    private boolean shield() throws GameActionException {
        MapLocation h = MapState.home;
        if (Nav.cheb(loc, h) == 2 && rc.senseFlooding(loc)) {   // stage 7: flooded tiles only (a dry one is a helper's post or its climb out of the flood)
            if (!posted) { posted = true; slot = loc; Debug.log("@shield at=" + loc); }
            if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canBePickedUp() && rc.canPickUpUnit(e.ID)) { rc.pickUpUnit(e.ID); pickups++; Debug.log("@shieldpick t=" + e.type.ordinal()); return true; } }
            return true;
        }
        if (rc.isCurrentlyHoldingUnit()) return false;   // drown the cargo first
        if (slot == null || (rc.canSenseLocation(slot) && rc.isLocationOccupied(slot)) || round % 10 == id % 10) {
            slot = null; int bd = 1 << 30;
            for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue;
                MapLocation t = new MapLocation(h.x + dx, h.y + dy); if (!rc.onTheMap(t) || !rc.canSenseLocation(t) || !rc.senseFlooding(t)) continue;
                if (rc.isLocationOccupied(t)) continue;
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; slot = t; } }
        }
        if (slot == null && round >= C.RAID_FROM && raid()) return true;   // Iteration 87: no free slot -- raid the enemy wall (winkelmantanner's tactic)
        if (slot == null) {   // stage 7: nothing flooded and free yet -- wait five out, clear of the helpers' tiles
            if (Nav.cheb(loc, h) != 5) { MapLocation w = new MapLocation(h.x + 5 * Integer.signum(loc.x - h.x == 0 ? 1 : loc.x - h.x), h.y + 5 * Integer.signum(loc.y - h.y)); nav.setTarget(w); nav.step(); }
            return true;
        }
        if (loc.isAdjacentTo(slot)) { Direction d = loc.directionTo(slot); if (rc.canMove(d)) { rc.move(d); return true; } return true; }
        nav.setTarget(slot); nav.step(); return true;
    }

    /** Iteration 61: gather beside the enemy HQ out of its gun's reach; when RAID_MIN of ours are in sight, charge the ring
     *  and lift a landscaper off it (the carry then drowns it, as any carry does). */
    private boolean raid() throws GameActionException {
        MapLocation eh = MapState.enemyHQ != null ? MapState.enemyHQ : MapState.enemyHQGuess(); if (eh == null) return false;
        int friendsNear = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DELIVERY_DRONE) friendsNear++;
        if (!charging && friendsNear + 1 >= C.RAID_MIN && loc.distanceSquaredTo(eh) <= 64) { charging = true; Debug.log("@charge with=" + (friendsNear + 1)); }
        if (charging && friendsNear + 1 < 4) charging = false;   // the swarm is gone: regroup
        if (charging) {
            // anything of theirs in reach first (the ring's seats are shielded by helpers at Chebyshev 2: lift those, then
            // the seats; the first form aimed at the seats and lifted nothing in 18 charges)
            for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if ((e.type == RobotType.LANDSCAPER || e.type == RobotType.MINER) && rc.canPickUpUnit(e.ID)) { rc.pickUpUnit(e.ID); pickups++; Debug.log("@raidpick id=" + e.ID + " dHQ=" + e.location.distanceSquaredTo(eh)); return true; } }
            RobotInfo tgt = null; int bd = 1 << 30;
            for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type != RobotType.LANDSCAPER && e.type != RobotType.MINER) continue; if (e.location.distanceSquaredTo(eh) > 18) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd) { bd = d; tgt = e; } }
            if (tgt != null) { if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; Debug.log("@raidpick id=" + tgt.ID + " dHQ=" + tgt.location.distanceSquaredTo(eh)); return true; }
                nav.setTarget(tgt.location); nav.step(); return true; }
            nav.setTarget(eh); nav.step(); return true;
        }
        // rally: 7-8 out from the enemy HQ on our side, outside its gun
        MapLocation h = MapState.home; int dx = Integer.signum(h.x - eh.x), dy = Integer.signum(h.y - eh.y);
        MapLocation rally = new MapLocation(eh.x + 5 * dx, eh.y + 5 * dy);
        if (loc.distanceSquaredTo(rally) > 8) { nav.setTarget(rally); nav.step(); }
        return true;
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
