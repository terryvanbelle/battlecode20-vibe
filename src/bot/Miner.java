package bot;

import battlecode.common.*;

/**
 * Miner. Two jobs decided at birth by build order: the first miner the HQ makes is the
 * BUILDER (refinery, design school, then vaporators / net gun / fulfillment center as the
 * bank allows); every other miner is a WORKER (mine the nearest known soup, carry it to the
 * nearest refinery or the HQ, explore when nothing is known).
 */
public strictfp class Miner extends Robot {
    private boolean builder;
    private final MapLocation[] soupMem = new MapLocation[C.SOUP_MEMORY]; private int nSoup = 0;
    private MapLocation explore;
    private MapLocation soupTarget;                 // sticky: kept until reached, emptied or found unreachable
    private final MapLocation[] bad = new MapLocation[C.SOUP_BAD]; private int nBad = 0;   // unreachable soup regions (Chebyshev 2 around each)
    private MapLocation refinery;                   // nearest known place to deposit (a refinery); HQ is the fallback
    private int builtRefinery = 0, builtSchool = 0, builtVap = 0, builtNet = 0, builtFC = 0;
    private int deposits = 0, mined = 0, explores = 0, exploreFails = 0, unreachable = 0;

    Miner(RobotController rc) { super(rc); }

    @Override protected void init() throws GameActionException {
        super.init();
        // the builder is the HQ's first miner: built in round 1, so its first turn (this constructor) is round 2.
        // (The HQ acts before its children every round, so by the time miner #1 looks around miner #2 exists;
        // "nothing else in sight" was wrong and no builder was ever chosen in the first diagnostic.)
        builder = birth == 2 && MapState.home != null;
        Debug.log("@miner builder=" + builder);
    }

    @Override protected void turn() throws GameActionException {
        sense(); int b0 = Clock.getBytecodeNum(); if (round % 3 == 0) readBlock(); int b1 = Clock.getBytecodeNum(); probeEdges(); MapState.markSeen(loc);
        if (round % 8 == id % 8 && Clock.getBytecodeNum() < 3000) observeTerrain();
        int b2 = Clock.getBytecodeNum();
        if (round % 100 == 0) Debug.log("@minerstat builder=" + builder + " mined=" + mined + " deposits=" + deposits + " explores=" + explores + " soupMem=" + nSoup + " unreachable=" + unreachable);
        rememberSoup(); int b3 = Clock.getBytecodeNum();
        try { turn2(); } finally { int b4 = Clock.getBytecodeNum(); if (b4 > 8000) Debug.log("@bcprof sense=" + b0 + " block=" + (b1 - b0) + " terrain=" + (b2 - b1) + " soup=" + (b3 - b2) + " act=" + (b4 - b3) + " bug=" + nav.isBugging()); }
    }
    private void turn2() throws GameActionException {
        for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.REFINERY && (refinery == null || loc.distanceSquaredTo(friends[i].location) < loc.distanceSquaredTo(refinery))) refinery = friends[i].location;
        avoidRing = refinery != null || ringSeen;   // deposit at the HQ only while the wall has not started
        if (avoidRing && onRing(loc) && rc.isReady()) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (!onRing(loc.add(d)) && !loc.add(d).equals(MapState.home) && tryMove(d)) { Debug.log("@offring"); return; } }
            // boxed in (a corner seat on the map edge has only ring tiles and the HQ as neighbours): slide along the ring
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (onRing(n) && rc.canMove(d) && rc.canSenseLocation(n) && !rc.senseFlooding(n)) { rc.move(d); loc = rc.getLocation(); Debug.log("@offring slide"); return; } }
        }
        if (floodDanger() && climb()) return;
        if (nearestEnemy != null && nearestEnemy.type == RobotType.DELIVERY_DRONE && nearestEnemyD2 <= 8 && fleeFrom(nearestEnemy.location)) return;
        if (builder && round >= C.PARK_ROUND) { parkAndGun(); return; }   // Iteration 16
        if (builder && build()) return;
        work();
    }

    // ---------------------------------------------------------------- builder
    /** Iteration 16: the builder survives the flood on the highest dry tile near the HQ and keeps a net gun standing. */
    private MapLocation perch; private int lateGuns = 0;
    private void parkAndGun() throws GameActionException {
        MapLocation home = MapState.home;
        if (round >= C.GUN_ROUND && rc.isReady() && lateGuns < C.LATE_GUNS_MAX && rc.getTeamSoup() >= RobotType.NET_GUN.cost + C.LATE_GUN_BANK) {
            boolean gunSeen = false; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.NET_GUN) { gunSeen = true; break; }
            if (!gunSeen) {
                Direction bestD = null; int be = -1;
                for (int i = 8; --i >= 0;) {
                    Direction d = DIRS[i]; MapLocation n = loc.add(d);
                    if (!rc.onTheMap(n) || onRing(n) || n.equals(home) || !rc.canBuildRobot(RobotType.NET_GUN, d) || rc.senseFlooding(n)) continue;
                    int e = rc.senseElevation(n); if (e < waterLevel(round + C.GUN_LIFE)) continue;
                    if (e > be) { be = e; bestD = d; }
                }
                if (bestD != null) { rc.buildRobot(RobotType.NET_GUN, bestD); lateGuns++; Debug.log("@build t=8 at=" + loc.add(bestD) + " soup=" + rc.getTeamSoup() + " late=true e=" + be); return; }
                else if (round % 100 == 0) Debug.log("@nogunsite at=" + loc + " e=" + rc.senseElevation(loc) + " need=" + (int) Math.ceil(waterLevel(round + C.GUN_LIFE)));
            }
        }
        if (perch == null || round % 50 == 0 || (rc.canSenseLocation(perch) && rc.senseFlooding(perch))) {
            MapLocation best = perch; int be = perch != null && rc.canSenseLocation(perch) && !rc.senseFlooding(perch) ? rc.senseElevation(perch) + 2 : Integer.MIN_VALUE;
            for (int dx = -C.PARK_RADIUS; dx <= C.PARK_RADIUS; dx++) for (int dy = -C.PARK_RADIUS; dy <= C.PARK_RADIUS; dy++) {
                MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                if (Nav.cheb(t, home) < 2 || !rc.onTheMap(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t)) continue;
                RobotInfo r = rc.senseRobotAtLocation(t); if (r != null && r.ID != id) continue;
                int e = rc.senseElevation(t); if (Math.abs(e - rc.senseElevation(loc)) > 3 && !t.equals(loc)) continue;
                if (e > be) { be = e; best = t; }
            }
            if (best != null) perch = best;
        }
        if (perch == null) { if (floodDanger() && climb()) return; if (Nav.cheb(loc, home) > C.PARK_RADIUS) { nav.setTarget(home); nav.step(); } return; }
        if (!loc.equals(perch)) { if (floodDanger() && climb()) return; nav.setTarget(perch); nav.step(); if (loc.equals(perch)) Debug.log("@perched at=" + perch + " e=" + rc.senseElevation(perch)); }
    }

    private boolean build() throws GameActionException {
        MapLocation home = MapState.home; if (home == null) return false;
        int soup = rc.getTeamSoup();
        RobotType want = null;
        if (builtRefinery == 0 && soup >= RobotType.REFINERY.cost) want = RobotType.REFINERY;
        else if (builtRefinery > 0 && builtSchool == 0 && soup >= RobotType.DESIGN_SCHOOL.cost) want = RobotType.DESIGN_SCHOOL;
        else if (builtSchool > 0 && builtVap < C.VAPORATORS_MAX && soup >= C.VAPORATOR_BANK) want = RobotType.VAPORATOR;
        else if (builtVap > 0 && builtFC == 0 && soup >= C.FC_BANK + RobotType.FULFILLMENT_CENTER.cost) want = RobotType.FULFILLMENT_CENTER;   // Iteration 2's early center gated at 52%: back to after the first vaporator
        else if (builtVap > 0 && builtNet < C.NETGUNS_MAX && soup >= C.NETGUN_BANK + RobotType.NET_GUN.cost) want = RobotType.NET_GUN;
        if (want == null) return false;
        // site: a tile at Chebyshev BUILD_DIST from home, or further out for later buildings
        int dist = want == RobotType.REFINERY || want == RobotType.DESIGN_SCHOOL ? C.BUILD_DIST : C.BUILD_DIST + 1 + (builtVap + builtNet + builtFC) / 4;
        if (Nav.cheb(loc, home) != dist) {
            // walk to the nearest tile at that distance
            MapLocation best = null; int bd = 1 << 30;
            for (int dx = -dist; dx <= dist; dx++) for (int dy = -dist; dy <= dist; dy++) {
                if (Math.max(Math.abs(dx), Math.abs(dy)) != dist) continue;
                MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                if (!rc.onTheMap(t)) continue;
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; best = t; }
            }
            if (best == null) return false;
            nav.setTarget(best); nav.step(); return true;
        }
        // on the circle: build on an adjacent tile that is also on the circle (never inward: the ring must stay free)
        Direction bestD = null; int bs = 1 << 30;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (Nav.cheb(n, home) < dist || !rc.canBuildRobot(want, d) || rc.senseFlooding(n)) continue;
            int s = -rc.senseElevation(n) * 100 + n.distanceSquaredTo(MapState.center()) + nextInt(3);   // Iteration 2: highest tile first, then toward the centre
            if (s < bs) { bs = s; bestD = d; }
        }
        if (bestD == null) { nav.setTarget(home.add(DIRS[nextInt(8)]).add(DIRS[nextInt(8)])); nav.step(); return true; }
        rc.buildRobot(want, bestD);
        Debug.log("@build t=" + want.ordinal() + " at=" + loc.add(bestD) + " soup=" + rc.getTeamSoup());
        if (want == RobotType.REFINERY) { builtRefinery++; refinery = loc.add(bestD); }
        else if (want == RobotType.DESIGN_SCHOOL) builtSchool++;
        else if (want == RobotType.VAPORATOR) builtVap++;
        else if (want == RobotType.NET_GUN) builtNet++;
        else builtFC++;
        return true;
    }

    // ---------------------------------------------------------------- worker
    /** Sample the visible soup into memory: a strided pass over at most SOUP_SCAN tiles (the full array can be
     *  100+ tiles and a full O(tiles x memory) scan overran the 10k budget in the first diagnostic). */
    private void rememberSoup() throws GameActionException {
        if (nSoup >= C.SOUP_MEMORY || (round & 1) != (id & 1) || Clock.getBytecodeNum() > 4500) return;
        MapLocation[] s = rc.senseNearbySoup();
        int stride = s.length / C.SOUP_SCAN + 1, start = nextInt(stride);
        for (int i = start; i < s.length && nSoup < C.SOUP_MEMORY; i += stride) {
            boolean known = false; for (int k = nSoup; --k >= 0;) if (soupMem[k].equals(s[i])) { known = true; break; }
            if (!known && !nearBad(s[i]) && !rc.senseFlooding(s[i])) soupMem[nSoup++] = s[i];
        }
    }
    private boolean nearBad(MapLocation l) { for (int k = nBad; --k >= 0;) if (Nav.cheb(bad[k], l) <= 2) return true; return false; }
    /** Blacklist a region: drop every remembered tile within Chebyshev 2 of l and refuse to re-learn them. */
    private void markBad(MapLocation l) {
        bad[nBad < C.SOUP_BAD ? nBad++ : nextInt(C.SOUP_BAD)] = l;
        for (int k = nSoup; --k >= 0;) if (Nav.cheb(soupMem[k], l) <= 2) soupMem[k] = soupMem[--nSoup];
    }
    private void forgetSoup(MapLocation l) { for (int k = nSoup; --k >= 0;) if (soupMem[k].equals(l)) { soupMem[k] = soupMem[--nSoup]; return; } }

    private void work() throws GameActionException {
        int carrying = rc.getSoupCarrying();
        // 1. deposit if we can
        if (carrying > 0) for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (rc.canDepositSoup(d)) { rc.depositSoup(d, carrying); deposits++; Debug.log("@deposit n=" + carrying); return; } }
        // 2. full: go home (refinery if known, else the HQ)
        if (carrying >= C.SOUP_RETURN) {
            MapLocation dep = refinery != null ? refinery : MapState.home;
            if (dep != null) { nav.setTarget(dep); nav.step(); return; }
        }
        // 3. mine the richest adjacent soup
        if (rc.isReady()) {
            Direction bestD = null; int best = 0;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (rc.canMineSoup(d)) { int s = rc.senseSoup(loc.add(d)); if (s > best) { best = s; bestD = d; } } }
            if (rc.canMineSoup(Direction.CENTER) && rc.senseSoup(loc) > best) bestD = Direction.CENTER;
            if (bestD != null) { rc.mineSoup(bestD); mined++; return; }
        }
        // 4. walk to a remembered soup tile. The target is STICKY: re-picking the nearest every turn
        //    resets the navigator whenever "nearest" changes, so a miner circling an unreachable plateau
        //    never registers a stall (diagnostic 3 was byte-identical to diagnostic 2 for that reason).
        for (int k = nSoup; --k >= 0;) { MapLocation s = soupMem[k]; if (rc.canSenseLocation(s) && rc.senseSoup(s) == 0) forgetSoup(s); }
        if (soupTarget != null) { boolean still = false; for (int k = nSoup; --k >= 0;) if (soupMem[k].equals(soupTarget)) { still = true; break; } if (!still) soupTarget = null; }
        if (soupTarget == null) { int bd = 1 << 30; for (int k = nSoup; --k >= 0;) { int d = loc.distanceSquaredTo(soupMem[k]); if (d < bd) { bd = d; soupTarget = soupMem[k]; } } }
        if (soupTarget != null) {
            nav.setTarget(soupTarget);
            if (nav.stalled()) { markBad(soupTarget); unreachable++; Debug.log("@unreachable soup=" + soupTarget); soupTarget = null; }
            else if (nav.step()) return;
        }
        // 5. explore: a far point in a direction the map still has room in
        // 5. explore: the nearest sector never stood in; give a target up only on arrival or a navigator stall
        if (explore != null && nav.target() == explore && nav.stalled()) { MapState.markBad(explore); explore = null; exploreFails++; }
        if (explore == null || Nav.cheb(loc, explore) <= 3) { explore = pickExplore(); explores++; Debug.log("@explore to=" + explore + " unseen=" + MapState.sectorsUnseen()); }
        nav.setTarget(explore);
        nav.step();
    }

    private MapLocation pickExplore() {
        MapLocation s = MapState.nextSector(loc, id);
        if (s != null) return s;
        if (MapState.originKnown()) return new MapLocation(MapState.minX + nextInt(MapState.width), MapState.minY + nextInt(MapState.height));
        return new MapLocation(loc.x + nextInt(41) - 20, loc.y + nextInt(41) - 20);
    }
}
