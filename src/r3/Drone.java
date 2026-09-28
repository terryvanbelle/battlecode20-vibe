package r3;

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

    /** R3: a drone born away from home (the ark's center stands where the miner was) learns home from the HQ's own post
     *  (every 100 rounds at r%100 == 50, minted within a round or two) rather than from the one block in three it reads --
     *  the first ark drones knew no home for 80 rounds and the miner drowned under them (Soup, r1170-1250). */
    @Override protected void init() throws GameActionException {
        super.init();
        int r0 = rc.getRoundNum(); if (r0 < C.ARK_FROM) return;   // the early game stays g_iter13's
        for (int b = r0 - 1 - ((r0 - 1 - 50) % 100 + 100) % 100; MapState.home == null && b >= 2 && b > r0 - 300 && Clock.getBytecodeNum() < 8000; b -= 100)
            for (int k = 0; k < 3 && b + k < r0 && MapState.home == null; k++) {
                Transaction[] block = rc.getBlock(b + k);
                for (int i = block.length; --i >= 0;) { int[] m = block[i].getMessage(); if (Comms.ours(m, b + k, us) && m[0] == Comms.HQ_LOC) { MapState.setHome(new MapLocation(m[1], m[2])); break; } }
            }
    }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " holding=" + rc.isCurrentlyHoldingUnit() + " shield=" + (MapState.home != null && Nav.cheb(loc, MapState.home) == 2));
        if (round >= C.ARK_FROM && ark()) return;   // R3: the ark comes before the shield
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

    // ---------------------------------------------------------------- R3, the ark
    private int arkId = -1, arkSeen = 0, arkLifts = 0, arkDrops = 0, arkHoverAt = -100; private boolean arkHeld = false, arkSetDown = false;
    private MapLocation arkAt, arkT, arkS, arkHover, arkSchool;
    /** R3. Adopt the nearest miner of ours in sight that stands where the water of ARK_LOOK rounds ahead will be (none another
     *  drone of ours is nearer); stay beside it; lift it once the water of ARK_LIFT_LOOK rounds is above its tile and the front
     *  within two (held, it cannot drown). Carrying: see arkCarry. After a drop the miner is escorted the same way. */
    private boolean ark() throws GameActionException {
        MapLocation h = MapState.home;
        if (arkId >= 0 && round % 100 == 0) Debug.log("@arkstat id=" + arkId + " held=" + arkHeld + " lifts=" + arkLifts + " drops=" + arkDrops + " soup=" + rc.getTeamSoup());
        if (rc.isCurrentlyHoldingUnit()) return arkHeld && arkCarry(h);   // an enemy in our hold: the old code drowns it
        arkHeld = false;
        RobotInfo m = null;
        if (arkId >= 0) {
            if (rc.canSenseRobot(arkId)) { m = rc.senseRobot(arkId); arkSeen = round; arkAt = m.location;
                // another drone holds it (a held unit's tile is its carrier's), or stands nearer it: it is theirs (Soup: three drones
                // adopted one miner and two followed its carrier for 1,800 rounds)
                RobotInfo on = rc.senseRobotAtLocation(m.location);
                if ((on != null && on.ID != m.ID) || otherDroneNearer(m.location)) { Debug.log("@arklost id=" + arkId + " other"); arkId = -1; return false; } }
            else if (round - arkSeen > 20) { Debug.log("@arklost id=" + arkId + " at=" + arkAt); arkId = -1; return false; }
            else { nav.setTarget(arkAt); nav.step(); return true; }
        } else {
            int bd = 1 << 30;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.MINER) continue; int d = loc.distanceSquaredTo(f.location); if (d < bd && arkDanger(f.location, C.ARK_LOOK)) { bd = d; m = f; } }
            if (m == null) return false;
            if (otherDroneNearer(m.location)) return false;
            arkId = m.ID; arkSeen = round; arkAt = m.location; Debug.log("@arkadopt id=" + m.ID + " at=" + m.location + " e=" + rc.senseElevation(m.location) + " w=" + (int) waterLevel(round));
        }
        if (arkDanger(m.location, C.ARK_LIFT_LOOK) && floodNear(m.location, 2)) {
            if (rc.canPickUpUnit(m.ID)) { rc.pickUpUnit(m.ID); arkHeld = true; arkSetDown = false; arkLifts++; arkT = null; Debug.log("@arklift id=" + m.ID + " at=" + m.location + " e=" + rc.senseElevation(m.location) + " w=" + (int) waterLevel(round) + " dHQ=" + (h == null ? -1 : Nav.cheb(m.location, h)) + " soup=" + rc.getTeamSoup()); return true; }
            nav.setTarget(m.location); nav.step(); return true;
        }
        // set down and its school stands beside it: lift it again (safe in the hold, and its tile is the school's door)
        if (arkSetDown) for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DESIGN_SCHOOL && friends[i].location.isAdjacentTo(m.location)) {
            if (rc.canPickUpUnit(m.ID)) { rc.pickUpUnit(m.ID); arkHeld = true; arkSetDown = false; arkLifts++; arkT = null; arkSchool = friends[i].location; Debug.log("@arklift id=" + m.ID + " at=" + m.location + " school=" + friends[i].location + " soup=" + rc.getTeamSoup()); return true; }
            if (loc.distanceSquaredTo(m.location) > 2) { nav.setTarget(m.location); nav.step(); } return true; }
        // escort: beside it (a pickup needs r2 3); once set down, two out and off the dry tiles beside it (the school site, the doors)
        if (loc.distanceSquaredTo(m.location) > (arkSetDown ? 8 : 2)) { nav.setTarget(m.location); nav.step(); }
        else if (arkSetDown && loc.isAdjacentTo(m.location) && !rc.senseFlooding(loc) && rc.isReady()) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, m.location) == 2 && rc.canMove(d) && allowedTile(n)) { rc.move(d); loc = rc.getLocation(); break; } }
        }
        return true;
    }

    /** R3: carrying our miner. Away from an enemy gun in sight; with ARK_DROP_BANK banked and no working school of ours in sight,
     *  set it down beside a school site near home (arkFind); else hover five out from home, a new tile every 12 rounds. */
    private boolean arkCarry(MapLocation h) throws GameActionException {
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot() && loc.distanceSquaredTo(e.location) <= 24) { fleeFrom(e.location); return true; } }
        if (h == null) return true;   // no home known yet: hold it where we are
        // our last school: stay two from it while it stands and can spawn (hovering three out from home, the carrier lost sight of
        // it and set down a second school 65 rounds after the first, Eagles pair c700)
        if (arkSchool != null) {
            if (rc.canSenseLocation(arkSchool)) { RobotInfo r = rc.senseRobotAtLocation(arkSchool);
                if (r == null || r.team != us || r.type != RobotType.DESIGN_SCHOOL || !hasDoor(arkSchool, loc)) { Debug.log("@arkschoolgone at=" + arkSchool + " standing=" + (r != null)); arkSchool = null; } }
            if (arkSchool != null) { int c = Nav.cheb(loc, arkSchool); if (c <= 2) fleeFrom(arkSchool); else if (c > 3) { nav.setTarget(arkSchool); nav.step(); } return true; }
        }
        if (rc.getTeamSoup() >= C.ARK_DROP_BANK && Nav.cheb(loc, h) <= 6 && !schoolWorking()) {
            if (arkT == null || round % 4 == 0) arkFind(h);
            if (arkT != null) {
                if (loc.isAdjacentTo(arkT)) {
                    Direction d = loc.directionTo(arkT);
                    if (rc.canDropUnit(d)) { rc.dropUnit(d); arkHeld = false; arkSetDown = true; arkDrops++; Debug.log("@arkdrop at=" + arkT + " e=" + rc.senseElevation(arkT) + " site=" + arkS + " es=" + rc.senseElevation(arkS) + " dHQ=" + Nav.cheb(arkS, h) + " w=" + (int) waterLevel(round) + " soup=" + rc.getTeamSoup()); arkT = null; return true; }
                    return true;
                }
                nav.setTarget(arkT); nav.step(); return true;
            }
        }
        // hover five out: three out stood on the helpers' dig tiles (they never dig under a unit of ours) and the ring rose 55 less
        // by r2000 than g_iter13's (Soup pair c900, three carriers)
        if (arkHover == null || round - arkHoverAt >= 12 || loc.equals(arkHover)) {
            int t = nextInt(40), dx, dy;   // one of the 40 tiles five out
            if (t < 11) { dx = t - 5; dy = -5; } else if (t < 22) { dx = t - 16; dy = 5; } else if (t < 31) { dx = -5; dy = t - 26; } else { dx = 5; dy = t - 35; }
            arkHover = new MapLocation(h.x + dx, h.y + dy); arkHoverAt = round;
        }
        nav.setTarget(arkHover); nav.step();
        return true;
    }

    /** R3: a drone of ours nearer l than we are (ties to the lower id), so that one drone adopts a miner. */
    private boolean otherDroneNearer(MapLocation l) {
        int me = loc.distanceSquaredTo(l);
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.DELIVERY_DRONE) continue; int d = f.location.distanceSquaredTo(l); if (d < me || (d == me && f.ID < id)) return true; }
        return false;
    }

    /** R3: the drop. T: a free dry tile 1-4 out from home that stays dry ARK_SITE_LIFE rounds (the miner stands there); S: a
     *  neighbour of T 1-3 out, free and dry as long, within 3 of T (the build rule), with a door for the landscapers (a free dry
     *  neighbour within 3 of it; T counts: the miner is lifted again once its school stands); nearest the ring first, then the
     *  highest, then nearest us; a door on a seat or a helper post is worth one ring of distance. Ring tiles only when free, and last (a school on one stops that tile until
     *  a seat buries it): late homes are islands of the ring and its posts in the flood (13 of 20 late losses read had no free
     *  dry pair off the ring at r1500; the lifters strip the ring by r2000, freeRing 8 in six of them). */
    private void arkFind(MapLocation h) throws GameActionException {
        arkT = null; arkS = null; int best = Integer.MAX_VALUE; double wl = waterLevel(round + C.ARK_SITE_LIFE);
        for (int dx = -4; dx <= 4; dx++) for (int dy = -4; dy <= 4; dy++) {
            if (dx == 0 && dy == 0) continue;
            if (Clock.getBytecodeNum() > 8500) return;
            MapLocation t = new MapLocation(h.x + dx, h.y + dy);
            if (!rc.canSenseLocation(t) || rc.senseFlooding(t) || rc.isLocationOccupied(t)) continue;
            int et = rc.senseElevation(t); if (et < wl) continue;
            int tr = onRing(t) ? 100 : 0;
            for (int i = 8; --i >= 0;) { MapLocation s = t.add(DIRS[i]); int cs = Nav.cheb(s, h);
                if (cs < 1 || cs > 3 || s.equals(loc) || !rc.canSenseLocation(s) || rc.senseFlooding(s) || rc.isLocationOccupied(s)) continue;
                int es = rc.senseElevation(s); if (es < wl || Math.abs(es - et) > GameConstants.MAX_DIRT_DIFFERENCE) continue;
                int score = tr + (cs == 1 ? 400 : cs * 100) - Math.min(es, 60) + t.distanceSquaredTo(loc);
                if (score >= best) continue;
                int doors = 0; boolean post = false;
                for (int k = 8; --k >= 0;) { MapLocation d = s.add(DIRS[k]); if (d.equals(h) || !rc.canSenseLocation(d) || rc.senseFlooding(d) || Math.abs(rc.senseElevation(d) - es) > GameConstants.MAX_DIRT_DIFFERENCE) continue;
                    if (!d.equals(loc) && rc.isLocationOccupied(d)) continue; doors++; if (Nav.cheb(d, h) <= 2) post = true; }
                if (doors == 0) continue;
                if (!post) score += 100;
                if (score < best) { best = score; arkT = t; arkS = s; } }
        }
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
