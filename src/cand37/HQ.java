package cand37;

import battlecode.common.*;

/**
 * The HQ. Builds MINERS_EARLY miners at once, then more while the bank allows and the ring is
 * still open; shoots the nearest enemy drone; posts its location in round 2 (so late-born units
 * that never see it can find home) and the map origin once a unit has found it.
 */
public strictfp class HQ extends Robot {
    private int built = 0, lastBuild = -1000;
    private boolean postedLoc = false, postedOrigin = false;

    HQ(RobotController rc) { super(rc); MapState.setHome(rc.getLocation()); }

    /** Iteration 36: the site is a Chebyshev-3 tile, dry, within 6 of our height, whose stand (Chebyshev 4, beyond it) is
     *  on the map, dry and within 3 of our height, with at least two dry distance-2 posts beside it within 3 of our
     *  height; the one farthest from our refinery and school (so it reserves nothing they need). */
    private void chooseSite() throws GameActionException {
        int e0 = rc.senseElevation(loc); MapLocation best = null; int bs = -1; int nb = 0;
        MapLocation[] blds = new MapLocation[8];
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if ((f.type == RobotType.REFINERY || f.type == RobotType.DESIGN_SCHOOL) && nb < 8) blds[nb++] = f.location; }
        if (nb == 0 && round < 300) return;   // wait until the builder's buildings stand
        for (int dx = -3; dx <= 3; dx++) for (int dy = -3; dy <= 3; dy++) {
            if (Math.max(Math.abs(dx), Math.abs(dy)) != 3) continue;
            MapLocation s = loc.translate(dx, dy);
            if (!rc.onTheMap(s) || !rc.canSenseLocation(s) || rc.senseFlooding(s) || Math.abs(rc.senseElevation(s) - e0) > 6) continue;
            if (rc.senseRobotAtLocation(s) != null && rc.senseRobotAtLocation(s).type.isBuilding()) continue;
            MapLocation st = siteStand(s, loc);
            if (!rc.onTheMap(st) || !rc.canSenseLocation(st) || rc.senseFlooding(st) || Math.abs(rc.senseElevation(st) - e0) > C.SITE_STAND) continue;
            int dry = 0; for (int i = 8; --i >= 0;) { MapLocation n = st.add(DIRS[i]); if (Nav.cheb(n, loc) >= 4 && rc.onTheMap(n) && rc.canSenseLocation(n) && !rc.senseFlooding(n)) dry++; }
            if (dry < 2) continue;   // 37: the mason digs from the tiles beyond the stand; water there and it stands idle
            int posts = 0;
            for (int i = 8; --i >= 0;) { MapLocation p = s.add(DIRS[i]); if (Nav.cheb(p, loc) != 2 || !rc.onTheMap(p) || !rc.canSenseLocation(p) || rc.senseFlooding(p) || Math.abs(rc.senseElevation(p) - e0) > 3) continue;
                RobotInfo r = rc.senseRobotAtLocation(p); if (r != null && r.type.isBuilding()) continue; posts++; }
            if (posts < 2) continue;
            int d = 1 << 30; for (int i = nb; --i >= 0;) d = Math.min(d, s.distanceSquaredTo(blds[i]));
            int score = d * 4 + posts;
            if (score > bs) { bs = score; best = s; }
        }
        MapState.site = best; Debug.log("@site " + best);
    }

    @Override protected void turn() throws GameActionException {
        sense(); readBlock(); probeEdges();
        // shoot the nearest enemy drone in range
        RobotInfo target = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type != RobotType.DELIVERY_DRONE) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd && rc.canShootUnit(e.ID)) { bd = d; target = e; } }
        if (target != null) { rc.shootUnit(target.ID); Debug.log("@shoot id=" + target.ID + " d2=" + bd); }

        int miners = 0, landscapersAdj = 0;
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.MINER) miners++; else if (f.type == RobotType.LANDSCAPER && onRing(f.location)) landscapersAdj++; }
        // early burst by count built (miners roam out of sight, so the sensed count cannot cap anything);
        // then one more miner per MINER_REPLENISH rounds while rich, up to a hard total
        boolean want = built < C.MINERS_EARLY
            || (built < C.MINERS_MAX && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS)
            || (built < C.MINERS_TOTAL && round - lastBuild >= C.MINER_REPLENISH && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS);
        // Iteration 29: under a rush, no miner until our school stands (the 70s go to the school and its landscapers)
        if (want && rushSeen()) { boolean school = false; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DESIGN_SCHOOL) { school = true; break; } if (!school) want = false; }
        if (want && tryBuild(RobotType.MINER, null)) { built++; lastBuild = round; }

        // Iteration 7: re-post every 100 rounds so robots born late (and the drones) learn home, the origin and the enemy HQ
        if ((!postedLoc || round % 100 == 50) && round >= 2) postedLoc = post(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y));
        else if ((!postedOrigin || round % 100 == 25) && MapState.originKnown()) postedOrigin = post(Comms.make(Comms.MAP_ORIGIN, round, us, MapState.minX, MapState.minY));
        else if (round % 100 == 75 && MapState.enemyHQ != null) post(Comms.make(Comms.ENEMY_HQ, round, us, MapState.enemyHQ.x, MapState.enemyHQ.y));
        if (MapState.site == null && round >= 100 && round <= 300 && round % 10 == 0) chooseSite();
        if (MapState.site != null && round % 10 == 5 && round <= C.SITE_POST_UNTIL) {
            MapLocation s = MapState.site; int bits = 0, e0 = rc.senseElevation(loc);
            if (rc.canSenseLocation(s)) { RobotInfo r = rc.senseRobotAtLocation(s); boolean built = r != null && r.type.isBuilding();
                if (built || rc.senseElevation(s) >= e0 + C.SITE_RAISE) bits |= 1; if (built) bits |= 2; }
            MapLocation st = siteStand(s, loc);
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.LANDSCAPER && f.location.equals(st)) { bits |= 4; break; } }
            MapState.siteRaised = (bits & 1) != 0; MapState.siteBuilt = (bits & 2) != 0; MapState.siteManned = (bits & 4) != 0; MapState.siteRound = round;
            post(Comms.make(Comms.SCHOOL_SITE, round, us, s.x, s.y, bits));
        }
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
