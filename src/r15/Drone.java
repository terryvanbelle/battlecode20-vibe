package r15;

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

    /** Iteration 81: hold a flooded Chebyshev-2 tile of our HQ. On one: never move again; lift an enemy unit beside us and keep
     *  it (a held unit is blocked). Otherwise fly to the nearest free one, or circle five out while none is flooded and free.
     *  R1 stage 17b: the flooded C2 tiles are remembered by coordinates (the flood never recedes) and a tile seen occupied is
     *  skipped for SHIELD_OCC_MEMORY rounds, so a drone away from home still knows its slots; the wait circles the base
     *  (g_iter13 waited on one side and saw only the near half of C2). */
    private MapLocation slot; private boolean posted = false;
    private int c2Flooded = 0; private final int[] c2Occ = new int[25];   // bit / index (dx+2)*5+(dy+2)
    private boolean shield() throws GameActionException {
        MapLocation h = MapState.home;
        if (Nav.cheb(loc, h) == 2 && rc.senseFlooding(loc)) {   // stage 7: flooded tiles only (a dry one is a helper's post or its climb out of the flood)
            if (!posted) { posted = true; slot = loc; Debug.log("@shield at=" + loc); }
            if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canBePickedUp() && rc.canPickUpUnit(e.ID)) { rc.pickUpUnit(e.ID); pickups++; Debug.log("@shieldpick t=" + e.type.ordinal()); return true; } }
            return true;
        }
        if (rc.isCurrentlyHoldingUnit()) return false;   // drown the cargo first
        if (charging && raid()) return true;               // a charge in progress is finished first
        if (Nav.cheb(loc, h) <= 7) for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue;
            MapLocation t = new MapLocation(h.x + dx, h.y + dy); if (!rc.canSenseLocation(t)) continue;
            int b = (dx + 2) * 5 + (dy + 2);
            if (rc.senseFlooding(t)) c2Flooded |= 1 << b;
            if (rc.isLocationOccupied(t)) c2Occ[b] = round;
        }
        if (slot == null || (rc.canSenseLocation(slot) && rc.isLocationOccupied(slot)) || round % 10 == id % 10) {
            slot = null; int bd = 1 << 30;
            for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue;
                int b = (dx + 2) * 5 + (dy + 2); if ((c2Flooded & (1 << b)) == 0) continue;
                MapLocation t = new MapLocation(h.x + dx, h.y + dy);
                if (rc.canSenseLocation(t) ? rc.isLocationOccupied(t) : round - c2Occ[b] < C.SHIELD_OCC_MEMORY) continue;
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; slot = t; } }
        }
        if (slot == null && round >= C.RAID_FROM && round - raidRest > C.RAID_REST && raid()) return true;   // Iteration 87: no free slot -- raid the enemy wall (winkelmantanner's tactic)
        if (slot == null) {   // stage 7: nothing flooded and free yet -- circle five out, clear of the helpers' tiles
            Direction b = DIRS[(id + round / 6) % 8]; MapLocation w = new MapLocation(h.x + 5 * b.dx, h.y + 5 * b.dy);
            if (loc.distanceSquaredTo(w) > 2) { nav.setTarget(w); nav.step(); }
            return true;
        }
        if (loc.isAdjacentTo(slot)) { Direction d = loc.directionTo(slot); if (rc.canMove(d)) { rc.move(d); return true; } return true; }
        nav.setTarget(slot); nav.step(); return true;
    }

    /** Iteration 61: gather beside the enemy HQ out of its gun's reach; when RAID_MIN of ours are in sight, charge the ring
     *  and lift a landscaper off it (the carry then drowns it, as any carry does). */
    private int raidStart = -1, raidRest = -1000;
    private boolean raid() throws GameActionException {
        MapLocation eh = MapState.enemyHQ != null ? MapState.enemyHQ : MapState.enemyHQGuess(); if (eh == null) return false;
        // R1 stage 17b: a rally that never charges comes home after RAID_WAIT rounds (11 of 17 drones idled at the rally
        // through cormackikkert's r2103 wave) and rests RAID_REST rounds at the shield
        if (raidStart < 0) raidStart = round;
        if (!charging && round - raidStart > C.RAID_WAIT) { raidStart = -1; raidRest = round; Debug.log("@raidhome"); return false; }
        int friendsNear = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DELIVERY_DRONE) friendsNear++;
        if (!charging && friendsNear + 1 >= C.RAID_MIN && loc.distanceSquaredTo(eh) <= 64) { charging = true; Debug.log("@charge with=" + (friendsNear + 1)); }
        if (charging && friendsNear + 1 < 4) { charging = false; raidStart = round; }   // the swarm is gone: regroup
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
