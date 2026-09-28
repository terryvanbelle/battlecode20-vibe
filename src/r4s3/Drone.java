package r4s3;

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
        if (MapState.home != null && quarry()) return;   // R4 stage 3: the quarry tiles are the shield's first slots, from birth
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

    /** R4 stage 3 (r4s1 on the ladder: every seat borders an open quarry pit, and an enemy drone over a pit lifts the seat
     *  beside it -- winkelmantanner keeps a drone over each of its own): hold a quarry tile, flooded or dry, from birth. On
     *  one: never move again (the seats and helpers dig the pit under us, Landscaper wall() and help()), and lift an enemy
     *  unit beside us and keep it, as the shield does. Otherwise fly to the nearest quarry tile on the map not seen
     *  occupied; none free: the old rules (a rush in sight and a carry first). */
    private MapLocation qslot; private boolean qposted = false; private int qk = 0;
    private static final int[] QX = {2, -1, -2, 1}, QY = {1, 2, -1, -2};   // Robot.isQuarry's four offsets
    private final int[] qOcc = {-10000, -10000, -10000, -10000};
    private boolean quarry() throws GameActionException {
        MapLocation h = MapState.home;
        if (ferry && rc.isCurrentlyHoldingUnit()) {   // our own unit, lifted out of the quarry: set it down on the best dry tile 3+ out
            Direction best = null; int be = Integer.MIN_VALUE;
            if (rc.isReady()) for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d);
                if (Nav.cheb(n, h) < 3 || !rc.canSenseLocation(n) || rc.senseFlooding(n) || !rc.canDropUnit(d)) continue;
                int e = rc.senseElevation(n); if (e > be) { be = e; best = d; } }
            if (best != null) { rc.dropUnit(best); ferry = false; Debug.log("@qdrone ferried to=" + loc.add(best) + " e=" + be); return true; }
            MapLocation out = new MapLocation(h.x + 4 * (loc.x >= h.x ? 1 : -1), h.y + 4 * (loc.y >= h.y ? 1 : -1));
            nav.setTarget(out); nav.step(); return true;
        }
        ferry = false;
        if (isQuarry(loc)) {
            if (!qposted) { qposted = true; Debug.log("@qdrone at=" + loc + " flooded=" + rc.senseFlooding(loc) + " e=" + rc.senseElevation(loc)); }
            if (!rc.isCurrentlyHoldingUnit() && rc.isReady()) for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canBePickedUp() && rc.canPickUpUnit(e.ID)) { rc.pickUpUnit(e.ID); pickups++; Debug.log("@qdrone pick t=" + e.type.ordinal()); return true; } }
            return true;
        }
        if (qposted) { qposted = false; Debug.log("@qdrone off at=" + loc); }
        if (rc.isCurrentlyHoldingUnit() || rushSeen()) return false;   // drown the cargo / lift the rusher first
        // a quarry tile seen occupied is not a slot for QOCC_MEMORY rounds unless seen free (our quarry drones never move, so
        // the rest of the air force does not fly home to look again)
        for (int k = 4; --k >= 0;) { MapLocation t = new MapLocation(h.x + QX[k], h.y + QY[k]); if (rc.canSenseLocation(t)) qOcc[k] = rc.isLocationOccupied(t) && trappedOurs(t) == null ? round : -10000; }
        if (qslot == null || round - qOcc[qk] < C.QOCC_MEMORY || round % 10 == id % 10) {
            qslot = null; int bd = 1 << 30;
            for (int k = 4; --k >= 0;) {
                MapLocation t = new MapLocation(h.x + QX[k], h.y + QY[k]); if (!rc.onTheMap(t) || round - qOcc[k] < C.QOCC_MEMORY) continue;
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; qslot = t; qk = k; } }
        }
        if (qslot == null) return false;
        if (loc.isAdjacentTo(qslot)) { Direction d = loc.directionTo(qslot);
            RobotInfo r = trappedOurs(qslot);
            if (r != null) { if (rc.canPickUpUnit(r.ID)) { rc.pickUpUnit(r.ID); ferry = true; Debug.log("@qdrone lift t=" + r.type.ordinal() + " from=" + qslot); } return true; }
            if (rc.canMove(d)) rc.move(d); return true; }
        nav.setTarget(qslot); nav.step(); return true;
    }
    private boolean ferry = false;
    /** CentralLake seed 7: our miner stood on a quarry tile from r251 (mining beside it), the seats dug the pit under it, and it
     *  held the slot, walled in, until it drowned at r1414. Our miner or landscaper on a quarry tile with no dry, free
     *  neighbour within 3 of its height is trapped: returned (a drone lifts it out), else null. */
    private RobotInfo trappedOurs(MapLocation t) throws GameActionException {
        if (!rc.canSenseLocation(t)) return null;
        RobotInfo r = rc.senseRobotAtLocation(t); if (r == null || r.team != us || !r.type.canBePickedUp()) return null;
        int e0 = rc.senseElevation(t);
        for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (!rc.canSenseLocation(n) || rc.senseFlooding(n) || rc.isLocationOccupied(n)) continue;
            if (Math.abs(rc.senseElevation(n) - e0) <= GameConstants.MAX_DIRT_DIFFERENCE) return null; }
        return r;
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
