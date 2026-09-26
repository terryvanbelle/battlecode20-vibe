package cand78;

import battlecode.common.*;

/**
 * Miner. Two jobs decided at birth by build order: the first miner the HQ makes is the
 * BUILDER (refinery, design school, then vaporators / net gun / fulfillment center as the
 * bank allows); every other miner is a WORKER (mine the nearest known soup, carry it to the
 * nearest refinery or the HQ, explore when nothing is known).
 */
public strictfp class Miner extends Robot {
    private int lastSchoolRound = -1000, wantSince = 0; private RobotType lastWant = null;
    private boolean builder, rusher, planted; private int probeRounds = 0, probeSign = -1;
    private final MapLocation[] soupMem = new MapLocation[C.SOUP_MEMORY]; private int nSoup = 0;
    private MapLocation explore;
    private MapLocation soupTarget;                 // sticky: kept until reached, emptied or found unreachable
    private final MapLocation[] bad = new MapLocation[C.SOUP_BAD]; private int nBad = 0;   // unreachable soup regions (Chebyshev 2 around each)
    private MapLocation refinery;                   // nearest known place to deposit (a refinery); HQ is the fallback
    private int builtRefinery = 0, builtSchool = 0, builtVap = 0, builtNet = 0, builtFC = 0;
    private int refineryBadUntil = 0, homeStalls = 0, buildPause = 0;   // Iteration 34: stall handling for the walks home and the builder's
    private int deposits = 0, mined = 0, explores = 0, exploreFails = 0, unreachable = 0;

    Miner(RobotController rc) { super(rc); }

    @Override protected void init() throws GameActionException {
        super.init();
        // the builder is the HQ's first miner: built in round 1, so its first turn (this constructor) is round 2.
        // (The HQ acts before its children every round, so by the time miner #1 looks around miner #2 exists;
        // "nothing else in sight" was wrong and no builder was ever chosen in the first diagnostic.)
        builder = birth == 2 && MapState.home != null;
        // Iteration 63 (PROMPTS 40-41: use the tactic that beats us): poortho's rush, ours. The HQ's second miner walks to
        // the enemy HQ (the symmetry guesses, pruned on sight), plants a design school beside its ring, and the school's
        // landscapers bury the HQ. poortho does it to us in 9 of 240 ladder games.
        rusher = birth == 3 && MapState.home != null;
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
        if (refineryBadUntil > 0 && round >= refineryBadUntil) { refineryBadUntil = 0; }
        avoidRing = refinery != null && refineryBadUntil == 0;   // Iteration 29: with no refinery (the school came first under a rush) the HQ is the only drop-off, ring or not; Iteration 34: likewise while the refinery is unreachable
        if (avoidRing && onRing(loc) && rc.isReady()) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (!onRing(loc.add(d)) && !loc.add(d).equals(MapState.home) && tryMove(d)) { Debug.log("@offring"); return; } }
            // boxed in (a corner seat on the map edge has only ring tiles and the HQ as neighbours): slide along the ring
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (onRing(n) && rc.canMove(d) && rc.canSenseLocation(n) && !rc.senseFlooding(n)) { rc.move(d); loc = rc.getLocation(); Debug.log("@offring slide"); return; } }
        }
        if (floodDanger() && climb()) return;
        if (nearestEnemy != null && nearestEnemy.type == RobotType.DELIVERY_DRONE && nearestEnemyD2 <= 8 && fleeFrom(nearestEnemy.location)) return;
        if (rusher && !planted && round < C.RUSH_GIVEUP) { rush(); return; }
        if (builder && build()) return;
        work();
    }

    private void rush() throws GameActionException {
        for (int i = nEnemy; --i >= 0;) if (enemies[i].type == RobotType.HQ) MapState.enemyHQ = enemies[i].location;
        MapLocation t = MapState.enemyHQGuess();
        if (t == null) {   // origin unknown: probe along an axis, reversing every 20 rounds
            boolean xAxis = MapState.minX < 0; probeRounds++;
            if (probeRounds > 20) { probeRounds = 0; probeSign = -probeSign; }
            MapLocation p = xAxis ? new MapLocation(loc.x + 8 * probeSign, loc.y) : new MapLocation(loc.x, loc.y + 8 * probeSign);
            nav.setTarget(p); nav.step(); return;
        }
        if (MapState.enemyHQ != null && loc.distanceSquaredTo(t) <= 8) {
            if (rc.getTeamSoup() >= RobotType.DESIGN_SCHOOL.cost && tryBuild(RobotType.DESIGN_SCHOOL, t)) { planted = true; Debug.log("@rushplant at=" + loc + " hq=" + t); }
            return;
        }
        nav.setTarget(t); if (nav.stalled()) { MapState.pruneEmpty(t); }
        nav.step();
    }

    /** Iteration 78: build on a free cell of the lattice beside us; else walk to a grid tile beside the nearest free cell. */
    private boolean latticeBuild(RobotType want, MapLocation home) throws GameActionException {
        if (!rc.isReady()) return true;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!isLot(n) || Nav.cheb(n, home) < 2 || !rc.canBuildRobot(want, d)) continue;
            if ((want == RobotType.VAPORATOR || want == RobotType.FULFILLMENT_CENTER || (want == RobotType.DESIGN_SCHOOL && builtSchool > 0)) && rc.senseElevation(n) < gridTarget(round) + C.LOT_ABOVE - 1) continue;   // stage 11-12: on a raised pad
            rc.buildRobot(want, d); lastWant = null; Debug.log("@build t=" + want.ordinal() + " at=" + n + " cell soup=" + rc.getTeamSoup());
            if (want == RobotType.REFINERY) { builtRefinery++; refinery = n; } else if (want == RobotType.DESIGN_SCHOOL) builtSchool++; else if (want == RobotType.VAPORATOR) builtVap++; else if (want == RobotType.NET_GUN) builtNet++; else builtFC++;
            return true; }
        MapLocation best = null; int bd = 1 << 30;
        for (int dx = -C.LATTICE_R; dx <= C.LATTICE_R; dx++) for (int dy = -C.LATTICE_R; dy <= C.LATTICE_R; dy++) {
            MapLocation c = new MapLocation(home.x + dx, home.y + dy); if (!isLot(c) || Nav.cheb(c, home) < 2 || !rc.onTheMap(c)) continue;
            if (rc.canSenseLocation(c) && (rc.isLocationOccupied(c) || rc.senseFlooding(c))) continue;
            int d = loc.distanceSquaredTo(c); if (d < bd) { bd = d; best = c; } }
        if (best == null) return false;
        // stage 2: stand on a grid tile beside the lot (a lot's own tile is not a place to stand while building on it)
        MapLocation stand = null; int sd = 1 << 30; for (int i = 8; --i >= 0;) { MapLocation g = best.add(DIRS[i]); if (!isGrid(g) || !rc.onTheMap(g)) continue; if (rc.canSenseLocation(g) && rc.isLocationOccupied(g) && !g.equals(loc)) continue; int d = loc.distanceSquaredTo(g); if (d < sd) { sd = d; stand = g; } }
        if (stand != null) best = stand;
        nav.setTarget(best); if (nav.stalled()) { buildPause = round + 30; return false; } nav.step(); return true;
    }

    // ---------------------------------------------------------------- builder
    private boolean build() throws GameActionException {
        MapLocation home = MapState.home; if (home == null) return false;
        if (round < buildPause) return false;   // Iteration 34: a walk that stalled (the builder boxed in) pauses building
        int soup = rc.getTeamSoup();
        RobotType want = null;
        boolean rush = builtSchool == 0 && rushSeen();
        if (rush) { if (soup < RobotType.DESIGN_SCHOOL.cost) return false; want = RobotType.DESIGN_SCHOOL; Debug.log("@rush school"); }   // Iteration 29: the school before the refinery
        // Iteration 54: under a rush, the center right after the school -- drones lift the rusher's landscapers away (the
        // school's doors close under them: g_iter10 vs arch_rush on InADitch, four landscapers and dead at r292)
        else if (builtSchool > 0 && builtFC == 0 && rushSeen() && soup >= RobotType.FULFILLMENT_CENTER.cost) { want = RobotType.FULFILLMENT_CENTER; Debug.log("@rush center"); }
        else if (builtRefinery == 0 && soup >= RobotType.REFINERY.cost) want = RobotType.REFINERY;
        else if (builtRefinery > 0 && builtSchool == 0 && soup >= RobotType.DESIGN_SCHOOL.cost) want = RobotType.DESIGN_SCHOOL;
        else if (builtSchool == 1 && round >= 400 && soup >= RobotType.DESIGN_SCHOOL.cost + 50) { want = RobotType.DESIGN_SCHOOL; }   // stage 15: a second school on a pad before the flood takes the first (it stood at ground: CentralLake floods at r677)
        else if (builtSchool > 0 && builtSchool < 4 && soup >= 1000 && round - lastSchoolRound > 150) { want = RobotType.DESIGN_SCHOOL; lastSchoolRound = round; }   // stage 6: another school on a fresh lot when the bank piles up (the first is boxed in by the rising grid: eight landscapers and 15,000 soup idle)
        else if (builtFC > 0 && builtFC < 4 && soup >= 2000 && round >= 600 && round - lastSchoolRound > 100) { want = RobotType.FULFILLMENT_CENTER; lastSchoolRound = round; }   // stage 8: more centers for the raid when the bank piles up
        else if (builtVap >= 4 && builtFC == 0 && soup >= RobotType.FULFILLMENT_CENTER.cost) want = RobotType.FULFILLMENT_CENTER;   // stage 9: the first center once four vaporators stand (it came after the vaporators, i.e. never: RandomSoup1 banked 39,000 with no drone)
        else if (builtSchool > 0 && builtVap < C.VAPORATORS_MAX && soup >= C.VAPORATOR_BANK) want = RobotType.VAPORATOR;
        else if (builtVap > 0 && builtFC == 0 && soup >= C.FC_BANK + RobotType.FULFILLMENT_CENTER.cost) want = RobotType.FULFILLMENT_CENTER;   // Iteration 2's early center gated at 52%: back to after the first vaporator
        else if (builtVap > 0 && builtNet < C.NETGUNS_MAX && soup >= C.NETGUN_BANK + RobotType.NET_GUN.cost) want = RobotType.NET_GUN;
        if (want == null) return false;
        // stage 10: on a lot, but for no more than 40 rounds per building (Squares: the lots sat on 20-99 cliffs, the builder
        // walked for 900 rounds after its refinery and no school was ever built); then the old placement
        if (want != lastWant) { lastWant = want; wantSince = round; }
        boolean mayFallBack = want == RobotType.REFINERY || (want == RobotType.DESIGN_SCHOOL && builtSchool == 0);   // stage 12: only the refinery and the first school leave the lattice (a vaporator, school or center at ground drowns at the flood)
        if ((want != RobotType.FULFILLMENT_CENTER || !rushSeen()) && (round - wantSince < 40 || !mayFallBack)) return latticeBuild(want, home);   // Iteration 78: every building on a cell
        if (want == RobotType.FULFILLMENT_CENTER && rushSeen()) return rushBuild(want, home);   // Iteration 57: the rush center where the rusher is not (Iteration 54's never found a site)
        // site: a tile at Chebyshev BUILD_DIST from home, or further out for later buildings
        int dist = want == RobotType.REFINERY || want == RobotType.DESIGN_SCHOOL ? C.BUILD_DIST : C.BUILD_DIST + 1 + (builtVap + builtNet + builtFC) / 4;
        boolean outward = dist == C.BUILD_DIST && !rush;
        if (outward) {
            // Iteration 28b: choose once, among all circle tiles with an outward site, the one whose outward site is
            // highest (then nearest), and walk there for up to 40 rounds before building where we stand.
            if (stand == null) {
                int bd = 1 << 30;
                for (int dx = -dist; dx <= dist; dx++) for (int dy = -dist; dy <= dist; dy++) {
                    if (Math.max(Math.abs(dx), Math.abs(dy)) != dist) continue;
                    MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                    if (!rc.onTheMap(t)) continue;
                    // reachable: within two climbable steps of the HQ's height (a 99-high wall tile is not a stand)
                    if (rc.canSenseLocation(t) && rc.canSenseLocation(home) && Math.abs(rc.senseElevation(t) - rc.senseElevation(home)) > 6) continue;
                    int e = outwardElev(t, home, dist); if (e == Integer.MIN_VALUE) continue;
                    int d = loc.distanceSquaredTo(t) - 100 * Math.min(e, 30);
                    if (d < bd) { bd = d; stand = t; }
                }
                standSince = round;
                if (stand != null) Debug.log("@stand " + stand);
            }
            if (stand != null && !loc.equals(stand) && round - standSince < 40) { nav.setTarget(stand); if (nav.stalled()) { buildPause = round + 50; Debug.log("@build stalled"); return false; } nav.step(); return true; }
        }
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
            nav.setTarget(best); if (nav.stalled()) { buildPause = round + 50; Debug.log("@build stalled"); return false; }   // Iteration 34
            nav.step(); return true;
        }
        // on the circle: build on an adjacent tile that is also on the circle (never inward: the ring must stay free)
        // Iteration 28b: the refinery and school go outward of the BUILD_DIST circle when they can. That circle is the
        // miners' only way round the ring and the landscapers' helper posts; on an edge HQ it is an arc, and a building
        // on it sealed five miners behind the HQ on Climb (Iteration 28). The circle is used only when nothing outward is free.
        Direction bestD = null; int bs = 1 << 30;
        for (int pass = 0; pass < 2 && bestD == null; pass++)
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (Nav.cheb(n, home) < dist + (pass == 0 && dist == C.BUILD_DIST ? 1 : 0) || !rc.canBuildRobot(want, d) || rc.senseFlooding(n)) continue;
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

    private MapLocation rushStand = null; private int rushSince = 0;
    /** Iteration 56: under a rush the school goes on the far side of the HQ from the rusher -- three out, away from the
     *  centroid of the enemy landscapers and school in sight. Against arch_rush g_iter10 lost 37 of 72 paired cells, 22
     *  before r1300, and in each replay read (MtDoom, FourLakeLand, CowFarm) the school, built beside the rusher's
     *  landscapers, was buried by r150-200 and the side froze at three or four landscapers. */
    private boolean rushBuild(RobotType bt, MapLocation home) throws GameActionException {
        int sx = 0, sy = 0, n = 0;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type == RobotType.LANDSCAPER || e.type == RobotType.DESIGN_SCHOOL) { sx += e.location.x; sy += e.location.y; n++; } }
        MapLocation c = n == 0 ? (MapState.enemyHQ != null ? MapState.enemyHQ : MapState.center()) : new MapLocation(sx / n, sy / n);
        if (rushStand == null || round - rushSince > 60) {
            MapLocation best = null; int bd = -1;
            for (int dx = -3; dx <= 3; dx++) for (int dy = -3; dy <= 3; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 3) continue;
                MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!rc.onTheMap(t)) continue;
                if (rc.canSenseLocation(t) && (rc.senseFlooding(t) || Math.abs(rc.senseElevation(t) - rc.senseElevation(loc)) > 6)) continue;
                int d = t.distanceSquaredTo(c); if (d > bd) { bd = d; best = t; } }
            rushStand = best; rushSince = round; Debug.log("@rushstand " + rushStand + " from=" + c);
        }
        if (rushStand != null && Nav.cheb(loc, rushStand) > 1 && round - rushSince < 40) { nav.setTarget(rushStand); nav.step(); return true; }
        Direction bestD = null; int bs = -1;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation t = loc.add(d);
            if (Nav.cheb(t, home) < 2 || !rc.canBuildRobot(bt, d) || rc.senseFlooding(t)) continue;
            int s = t.distanceSquaredTo(c) * 4 + (Nav.cheb(t, home) <= 3 ? 50 : 0); if (s > bs) { bs = s; bestD = d; } }
        if (bestD == null) { if (rushStand != null) { nav.setTarget(rushStand); nav.step(); } return true; }
        rc.buildRobot(bt, bestD); if (bt == RobotType.FULFILLMENT_CENTER) builtFC++; else builtSchool++; rushStand = null;
        Debug.log("@build t=" + bt.ordinal() + " at=" + loc.add(bestD) + " rush soup=" + rc.getTeamSoup());
        return true;
    }
    private MapLocation stand = null; private int standSince = 0;   // Iteration 28b: where the builder builds the refinery and school
    /** The highest outward neighbour of l that a builder standing on l could build on (the engine refuses a spawn more
     *  than 3 from the builder's height); unsensed counts 0, flooded is skipped; MIN_VALUE if there is none. */
    private int outwardElev(MapLocation l, MapLocation home, int dist) {
        int best = Integer.MIN_VALUE, e0 = Integer.MIN_VALUE;
        try { if (rc.canSenseLocation(l)) e0 = rc.senseElevation(l); } catch (GameActionException ex) { }
        for (int i = 8; --i >= 0;) {
            MapLocation n = l.add(DIRS[i]); if (Nav.cheb(n, home) <= dist || !rc.onTheMap(n)) continue;
            int e = 0;
            try { if (rc.canSenseLocation(n)) { if (rc.senseFlooding(n)) continue; e = rc.senseElevation(n); } } catch (GameActionException ex) { continue; }
            if (e0 != Integer.MIN_VALUE && Math.abs(e - e0) > 3) continue;
            if (e > best) best = e;
        }
        return best;
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
        // 1. deposit if we can -- Iteration 33: only a load worth the action (SOUP_RETURN), or whatever we carry once
        //    there is nothing adjacent left to mine (we are about to walk anyway). Miners beside the HQ deposited every
        //    7 soup: 13-22 deposit actions per 30 mining actions by r100 in the traced ladder losses.
        if (carrying > 0) {
            boolean soupHere = false;
            if (carrying < C.SOUP_RETURN) { for (int i = 8; --i >= 0;) if (rc.canMineSoup(DIRS[i])) { soupHere = true; break; } if (!soupHere && rc.canMineSoup(Direction.CENTER)) soupHere = true; }
            if (carrying >= C.SOUP_RETURN || !soupHere)
                for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (rc.canDepositSoup(d)) { rc.depositSoup(d, carrying); deposits++; Debug.log("@deposit n=" + carrying); return; } }
        }
        // 2. full: go home (refinery if known, else the HQ). Iteration 34: this walk had no stall handling, and in the
        //    reviewable losses of blocks 50-69 47% of the miners alive at r300-500 mined nothing in between -- a full
        //    miner walking for ever toward a drop-off it cannot reach (Islands2: circling beside our own HQ, the
        //    refinery cut off and the ring forbidden). Stalled toward the refinery: use the HQ (the ring allowed) for
        //    200 rounds; stalled toward the HQ as well: drop the load's claim on us and go on mining.
        if (carrying >= C.SOUP_RETURN) {
            MapLocation dep = refinery != null && refineryBadUntil == 0 ? refinery : MapState.home;
            if (dep != null) {
                nav.setTarget(dep);
                if (nav.stalled()) {
                    // 34b: the drop-off is cut off (the seats' and helpers' pits) -- a refinery here, if none is in sight
                    // and the bank allows, is the field's answer (they run 3-4 refineries by r300 to our 1)
                    boolean near = false; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.REFINERY && friends[i].location.distanceSquaredTo(loc) <= 24) { near = true; break; }
                    if (!near && rc.getTeamSoup() >= RobotType.REFINERY.cost && tryBuild(RobotType.REFINERY, null)) { Debug.log("@refinery built here"); return; }
                    if (dep == refinery) { refineryBadUntil = round + 200; nav.setTarget(MapState.home); Debug.log("@refinery unreachable"); }
                    else { homeStalls++; Debug.log("@home unreachable"); }
                }
                if (nav.step()) return;
                if (homeStalls < 3 || round % 4 == 0) return;   // keep trying; a little of the time, mine instead
            }
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
            else { if (!nav.step() && round % 25 == 0) Debug.log("@soupwait " + soupTarget); return; }   // Iteration 50: never fall through to explore while a soup target stands -- the explore target reset the navigator every turn, the stall never counted, and the miner froze with soup in memory (Spiral, GSF: every miner's mined count identical at r300 and r500)
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
