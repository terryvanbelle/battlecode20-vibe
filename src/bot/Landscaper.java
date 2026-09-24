package bot;

import battlecode.common.*;

/**
 * Landscaper. WALL: take the nearest free tile of the ring around our HQ, then forever: dig any
 * dirt off the HQ, else deposit carried dirt on our own tile (raising the wall), else dig from an
 * adjacent tile outside the ring that holds no building of ours. ATTACK (when the ring is full):
 * walk to the enemy HQ (sighted or the symmetry guess) and bury the first enemy building met.
 * HELPER (Iteration 3, when the ring is full): stand at Chebyshev 2 from the HQ, keep the own tile
 * above the water, otherwise dig from a tile further out and deposit onto the lowest adjacent ring
 * tile -- doubling the wall's growth and filling ring tiles no seat can reach.
 */
public strictfp class Landscaper extends Robot {
    private MapLocation seat;                 // our ring tile, once chosen
    private boolean attacker = false, helper = false, gunner = false;
    private MapLocation gunSite, gunStandTile;   // Iteration 31: the gunner raises gunSite to hqElev+GUN_RAISE standing on gunStandTile (raised to hqElev+GUN_STAND)
    private MapLocation post;                 // helper station at distance 2
    private final MapLocation[] badSeat = new MapLocation[8]; private int nBad = 0;
    private int helperDeps = 0;
    private int digs = 0, deposits = 0, hqDigs = 0, buryDeposits = 0, equalised = 0, borrowed = 0, gunDeps = 0;
    /** Iteration 31: the stand of gun tile g is its Chebyshev-3 neighbour (of the HQ) with the higher elevation. */
    protected MapLocation gunStand(MapLocation g, MapLocation home) throws GameActionException {
        MapLocation best = null; int be = Integer.MIN_VALUE;
        for (int i = 8; --i >= 0;) { MapLocation s = g.add(DIRS[i]); if (Nav.cheb(s, home) != 3 || !rc.onTheMap(s) || !rc.canSenseLocation(s) || rc.senseFlooding(s)) continue;
            int e = rc.senseElevation(s); if (e > be) { be = e; best = s; } }
        return best;
    }
    /** Iteration 31: a posted gun tile that is dry, unbuilt, below target and has no gunner beside it yet -> claim it. */
    private boolean pickGun(MapLocation home) throws GameActionException {
        int e0 = rc.canSenseLocation(home) ? rc.senseElevation(home) : rc.senseElevation(loc);
        for (int k = 0; k < 2; k++) { MapLocation g = MapState.guns[k]; if (g == null || !rc.canSenseLocation(g) || rc.senseFlooding(g)) continue;
            RobotInfo r = rc.senseRobotAtLocation(g); if (r != null && r.type.isBuilding()) continue;
            if (rc.senseElevation(g) >= e0 + C.GUN_RAISE) continue;
            boolean taken = false;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.LANDSCAPER && f.ID != id && f.location.isAdjacentTo(g) && Nav.cheb(f.location, home) == 3) taken = true; }
            if (taken) continue;
            MapLocation st = gunStand(g, home); if (st == null) continue;
            gunSite = g; gunStandTile = st; return true; }
        return false;
    }
    /** The gunner: stand on the stand tile; raise the gun tile to hqElev+GUN_RAISE (never more than 3 above the stand),
     *  the stand under itself to hqElev+GUN_STAND; dig from outside (Chebyshev >= 4). Done, or the site lost: a helper. */
    private void gun(MapLocation home) throws GameActionException {
        int e0 = rc.canSenseLocation(home) ? rc.senseElevation(home) : rc.senseElevation(loc);
        boolean lost = !rc.canSenseLocation(gunSite) || rc.senseFlooding(gunSite);
        if (!lost) { RobotInfo r = rc.senseRobotAtLocation(gunSite); if (r != null && r.type.isBuilding()) lost = true; }
        boolean done = !lost && rc.senseElevation(gunSite) >= e0 + C.GUN_RAISE && rc.canSenseLocation(gunStandTile) && rc.senseElevation(gunStandTile) >= e0 + C.GUN_STAND;
        if (lost || done) { Debug.log("@gunner " + (done ? "done" : "lost") + " site=" + gunSite); gunner = false; gunSite = null; return; }
        if (!loc.equals(gunStandTile)) {
            if (occupiedByOther(gunStandTile)) { RobotInfo r = rc.senseRobotAtLocation(gunStandTile); if (r != null && r.type == RobotType.LANDSCAPER && r.team == us) { gunner = false; gunSite = null; return; } }
            if (nav.target() == gunStandTile && nav.stalled()) { gunner = false; gunSite = null; Debug.log("@gunner stalled"); return; }
            if (floodDanger() && climb()) return;
            nav.setTarget(gunStandTile); nav.step(); return;
        }
        if (!rc.isReady()) return;
        int eg = rc.senseElevation(gunSite), es = rc.senseElevation(loc); Direction dg = loc.directionTo(gunSite);
        if (rc.getDirtCarrying() > 0) {
            if (eg < e0 + C.GUN_RAISE && eg < es + 3 && rc.canDepositDirt(dg)) { rc.depositDirt(dg); gunDeps++; return; }
            if (es < e0 + C.GUN_STAND && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); gunDeps++; return; }
            if (eg < e0 + C.GUN_RAISE && rc.canDepositDirt(dg)) { rc.depositDirt(dg); gunDeps++; return; }
        }
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || Nav.cheb(n, home) <= 3 || !rc.canDigDirt(d) || MapState.isGunSite(n)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null) continue;
            int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
        if (bestD == null) { for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.onTheMap(n) && Nav.cheb(n, home) == 3 && !n.equals(gunSite) && rc.canDigDirt(d) && rc.senseRobotAtLocation(n) == null) { bestD = d; break; } } }   // nothing outside: the other Chebyshev-3 neighbour
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }
    private boolean isGunStand(MapLocation n, MapLocation home) throws GameActionException {
        for (int k = 2; --k >= 0;) { MapLocation g = MapState.guns[k]; if (g != null && n.isAdjacentTo(g) && Nav.cheb(n, home) == 3 && n.equals(gunStand(g, home))) return true; }
        return false;
    }

    Landscaper(RobotController rc) { super(rc); nav.stallLimit = 30; }

    @Override protected void turn() throws GameActionException {
        int b0 = Clock.getBytecodeNum();
        try { turn2(); } finally { int b1 = Clock.getBytecodeNum(); if (b1 > 8500) Debug.log("@bcprof start=" + b0 + " total=" + b1 + " seat=" + (seat != null) + " helper=" + helper + " attacker=" + attacker + " atSeat=" + (seat != null && seat.equals(loc)) + " atPost=" + (post != null && post.equals(loc))); }
    }
    private void turn2() throws GameActionException {
        sense(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat seat=" + seat + " attacker=" + attacker + " helper=" + helper + " helperDeps=" + helperDeps + " eq=" + equalised + " borrow=" + borrowed + " digs=" + digs + " deps=" + deposits + " hqDigs=" + hqDigs + " bury=" + buryDeposits + " gunDeps=" + gunDeps + " gunner=" + gunner + " elev=" + rc.senseElevation(loc));
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        if (!attacker && !helper) {
            if (seat != null && !seat.equals(loc) && nav.target() == seat && nav.stalled()) { if (nBad < 8) badSeat[nBad++] = seat; Debug.log("@badseat " + seat); seat = null; }
            if (seat == null || !seat.equals(loc) && occupiedByOther(seat)) seat = pickSeat(home);
            if (seat == null && pickGun(home)) { gunner = true; Debug.log("@gunner site=" + gunSite + " stand=" + gunStandTile); }
            else if (seat == null) { post = pickPost(home); if (post != null) { helper = true; Debug.log("@helper post=" + post); } else { attacker = true; Debug.log("@attacker ring full"); } }
        }
        if (gunner) { gun(home); return; }
        if (attacker) { attack(); return; }
        if (helper) { help(home); return; }
        if (!loc.equals(seat)) { if (floodDanger() && climb()) return; nav.setTarget(seat); nav.step(); if (loc.equals(seat)) Debug.log("@seated at=" + seat); return; }
        wall(home);
    }

    private boolean occupiedByOther(MapLocation l) throws GameActionException {
        if (!rc.canSenseLocation(l)) return false;
        RobotInfo r = rc.senseRobotAtLocation(l);
        return r != null && r.ID != id;
    }

    /** The nearest ring tile that is free (or holds only a unit that will move on), not flooded, and
     *  reachable: within 3 of the HQ's elevation. A natural cliff on the ring (Hourglass has 99s) is
     *  already a wall and cannot be climbed; five landscapers once waited under one for 500 rounds. */
    private MapLocation pickSeat(MapLocation home) throws GameActionException {
        MapLocation best = null; int bd = 1 << 30;
        int hqElev = rc.canSenseLocation(home) ? rc.senseElevation(home) : rc.senseElevation(loc);
        for (int i = 8; --i >= 0;) {
            MapLocation t = home.add(DIRS[i]);
            if (!rc.onTheMap(t)) continue;
            boolean bad = false; for (int k = nBad; --k >= 0;) if (badSeat[k].equals(t)) { bad = true; break; }
            if (bad || !exposed(t)) continue;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                int e = rc.senseElevation(t);
                if (e - hqElev > GameConstants.MAX_DIRT_DIFFERENCE || hqElev - e > GameConstants.MAX_DIRT_DIFFERENCE) {
                    RobotInfo r0 = rc.senseRobotAtLocation(t);
                    if (r0 == null || r0.type != RobotType.LANDSCAPER || r0.team != us) continue;   // a raised seat with our landscaper on it is taken; an empty cliff is unreachable
                }
                RobotInfo r = rc.senseRobotAtLocation(t);
                if (r != null && r.ID != id && (r.type == RobotType.LANDSCAPER && r.team == us || r.type.isBuilding())) continue;
            }
            int d = loc.distanceSquaredTo(t) + nextInt(2);
            if (d < bd) { bd = d; best = t; }
        }
        return best;
    }

    /** A free tile at Chebyshev 2 from the HQ, next to the LOWEST ring tile it can feed (then nearest). */
    private final MapLocation[] badPost = new MapLocation[8]; private int nBadPost = 0;
    private MapLocation pickPost(MapLocation home) throws GameActionException {
        MapLocation best = null; long bs = Long.MAX_VALUE; int myE = rc.senseElevation(loc);
        for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue;
            MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t)) continue;
            boolean bad = false; for (int k = nBadPost; --k >= 0;) if (badPost[k].equals(t)) { bad = true; break; }
            if (bad) continue;
            int lowest = Integer.MAX_VALUE;
            if (rc.canSenseLocation(t)) {
                if (rc.senseFlooding(t)) continue;
                int e = rc.senseElevation(t); if (Math.abs(e - myE) > 6) continue;
                RobotInfo r = rc.senseRobotAtLocation(t);
                if (r != null && r.ID != id && (r.type.isBuilding() || (r.type == RobotType.LANDSCAPER && r.team == us))) continue;
                for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (onRing(n) && exposed(n) && rc.canSenseLocation(n)) lowest = Math.min(lowest, rc.senseElevation(n)); }
                if (lowest == Integer.MAX_VALUE) continue;   // a post that touches no exposed ring tile feeds nothing
            }
            long s = (long) (lowest == Integer.MAX_VALUE ? 0 : lowest) * 10000 + loc.distanceSquaredTo(t) + nextInt(2);
            if (s < bs) { bs = s; best = t; }
        }
        return best;
    }

    private void help(MapLocation home) throws GameActionException {
        if (!loc.equals(post)) {
            if (occupiedByOther(post)) { RobotInfo r = rc.canSenseLocation(post) ? rc.senseRobotAtLocation(post) : null; if (r != null && (r.type.isBuilding() || r.type == RobotType.LANDSCAPER)) { post = pickPost(home); if (post == null) { helper = false; attacker = true; return; } } }
            if (floodDanger() && climb()) return;
            if (nav.target() == post && nav.stalled()) {   // Iteration 8: a post never reached is struck off and another picked; the attack only when none is left (Iteration 7 sent every stalled helper to attack: gate 21-43)
                if (nBadPost < 8) badPost[nBadPost++] = post; Debug.log("@badpost " + post);
                post = pickPost(home); if (post == null) { helper = false; attacker = true; }
                return;
            }
            nav.setTarget(post); nav.step(); if (loc.equals(post)) Debug.log("@posted at=" + post); return;
        }
        if (!rc.isReady()) return;
        // Iteration 25: a free ring tile next door that we can climb is a seat going spare -- take it
        if (round % 3 == id % 3) {
            int myE = rc.senseElevation(loc);
            for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (!onRing(n) || !exposed(n) || !rc.canSenseLocation(n) || rc.senseFlooding(n) || rc.senseRobotAtLocation(n) != null) continue;
                if (Math.abs(rc.senseElevation(n) - myE) > GameConstants.MAX_DIRT_DIFFERENCE) continue;
                seat = n; helper = false; post = null; Debug.log("@reseat at=" + n); return; }
        }
        // 1. keep our own tile above the water that is coming
        boolean lowSelf = rc.senseElevation(loc) < waterLevel(round + 60) + 2;
        if (rc.getDirtCarrying() > 0 && lowSelf && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
        // 2. feed the lowest adjacent ring tile (never a building)
        if (rc.getDirtCarrying() > 0) {
            Direction bestD = null; int be = Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !exposed(n) || !rc.canDepositDirt(d)) continue;
                RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && r.type.isBuilding()) continue;
                if (round < C.SEATS_BY && (r == null || r.type != RobotType.LANDSCAPER || r.team != us)) continue;   // Iteration 25: never raise an unseated tile early
                int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
            if (bestD != null) { rc.depositDirt(bestD); helperDeps++; return; }
        }
        // 3. dig from a tile outside both rings (lowest first), never under a building or the HQ
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || Nav.cheb(n, home) <= 2 || !rc.canDigDirt(d) || MapState.isGunSite(n) || isGunStand(n, home)) continue;   // Iteration 31: never dig a gun tile or its stand
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && (r.type.isBuilding() || r.team == us)) continue;
            int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
        if (bestD == null && rc.canDigDirt(Direction.CENTER) && rc.senseElevation(loc) > waterLevel(round + 200) + 3) bestD = Direction.CENTER;   // nothing outside: eat our own margin
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }

    private void wall(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        Direction toHQ = loc.directionTo(home);
        // 1. the HQ is being buried: dig it out
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && rc.canDigDirt(toHQ)) { rc.digDirt(toHQ); hqDigs++; digs++; Debug.log("@hqdig buried=" + hqInfo.dirtCarrying); return; }
        // 2. an enemy building or unit adjacent: bury it (deposit) if we carry, it is cheap denial
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.getDirtCarrying() > 0 && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return; } }
        // 3. raise the LOWEST of {our tile, adjacent ring tiles}: the flood gets in through the lowest ring
        //    tile, so a wall is worth its minimum (Iteration 3; seats sat at 415 beside 163 before this)
        if (rc.getDirtCarrying() > 0) {
            int myE = rc.senseElevation(loc); Direction bestD = Direction.CENTER; int be = exposed(loc) ? myE : Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canSenseLocation(n) || !exposed(n)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                if (round < C.SEATS_BY && (r == null || r.type != RobotType.LANDSCAPER || r.team != us)) continue;   // Iteration 25: an unseated tile stays climbable
                int e = rc.senseElevation(n); if (e < be - C.WALL_LEVEL_SLACK) { be = e; bestD = d; } }
            if (be != Integer.MAX_VALUE && rc.canDepositDirt(bestD)) { rc.depositDirt(bestD); deposits++; if (bestD != Direction.CENTER) equalised++; return; }
        }
        // 4. dig from outside the ring: the lowest adjacent tile that is not the HQ, not a ring tile, not one of our buildings
        Direction bestD = null; int be = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || onRing(n) || n.equals(home) || !rc.canDigDirt(d)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.team == us) continue;   // never under our own units: digging a helper's tile makes it re-raise itself, a zero-sum loop
            int e = rc.senseElevation(n) + (r != null ? 1000 : 0);   // prefer empty tiles
            if (e < be) { be = e; bestD = d; }
        }
        // 5. a corner seat on the map edge has no outside tile: borrow from the tallest adjacent ring tile
        if (bestD == null) { int myE = rc.senseElevation(loc); int bh = myE + C.WALL_BORROW_MARGIN;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canDigDirt(d)) continue; int e = rc.senseElevation(n); if (e > bh) { bh = e; bestD = d; } }
            if (bestD != null) borrowed++; }
        if (bestD != null) { rc.digDirt(bestD); digs++; }
    }

    private void attack() throws GameActionException {
        if (floodDanger() && climb()) return;
        // bury an adjacent enemy building; dig from anything else adjacent when empty-handed
        RobotInfo tgt = null;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && (tgt == null || e.type == RobotType.HQ || loc.distanceSquaredTo(e.location) < loc.distanceSquaredTo(tgt.location))) tgt = e; }
        if (tgt != null && rc.isReady()) {
            if (loc.isAdjacentTo(tgt.location)) {
                Direction d = loc.directionTo(tgt.location);
                if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(d)) { rc.depositDirt(d); buryDeposits++; Debug.log("@bury t=" + tgt.type.ordinal() + " onIt=" + tgt.dirtCarrying); return; }
                for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (n.equals(tgt.location) || !rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && r.type.isBuilding()) continue; rc.digDirt(dd); digs++; return; }
                return;
            }
            nav.setTarget(tgt.location); nav.step(); return;
        }
        // carry a load before walking over
        if (rc.getDirtCarrying() < RobotType.LANDSCAPER.dirtLimit && rc.isReady() && Nav.cheb(loc, MapState.home) > 2) {
            for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (!rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null) continue; rc.digDirt(dd); digs++; return; }
        }
        MapLocation g = MapState.enemyHQGuess();
        if (g == null) g = MapState.center();
        nav.setTarget(g); nav.step();
    }
}
