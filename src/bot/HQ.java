package bot;

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

    /** Iteration 31: a gun tile is a Chebyshev-3 tile on the map, dry, within 6 of our height, with a Chebyshev-3
     *  neighbour (the stand) that is dry and within 4 of our height. Opposite pairs first (E/W, N/S, the diagonals);
     *  else the best single site. The HQ senses r2 48, so every candidate is in sight. */
    private void chooseGunSites() throws GameActionException {
        int e0 = rc.senseElevation(loc);
        int[][] pairs = {{3, 0, -3, 0}, {0, 3, 0, -3}, {3, 3, -3, -3}, {3, -3, -3, 3}};
        MapLocation first = null;
        for (int[] p : pairs) {
            MapLocation a = gunOk(loc.translate(p[0], p[1]), e0), b = gunOk(loc.translate(p[2], p[3]), e0);
            if (a != null && b != null) { MapState.guns[0] = a; MapState.guns[1] = b; break; }
            if (first == null) first = a != null ? a : b;
        }
        if (MapState.guns[0] == null) MapState.guns[0] = first;
        Debug.log("@gunsites " + MapState.guns[0] + " " + MapState.guns[1]);
    }
    private MapLocation gunOk(MapLocation g, int e0) throws GameActionException {
        if (!rc.onTheMap(g) || !rc.canSenseLocation(g) || rc.senseFlooding(g)) return null;
        int e = rc.senseElevation(g); if (Math.abs(e - e0) > 6) return null;
        for (int i = 8; --i >= 0;) { MapLocation s = g.add(DIRS[i]);
            if (Nav.cheb(s, loc) != 3 || !rc.onTheMap(s) || !rc.canSenseLocation(s) || rc.senseFlooding(s)) continue;
            if (Math.abs(rc.senseElevation(s) - e0) <= 4) return g; }
        return null;
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
        if (round == 3) chooseGunSites();
        if (round % 20 == 5 && round <= C.GUN_POST_UNTIL && (MapState.guns[0] != null || MapState.guns[1] != null))
            post(Comms.make(Comms.GUN_SITES, round, us, MapState.guns[0] == null ? -1 : MapState.guns[0].x, MapState.guns[0] == null ? -1 : MapState.guns[0].y,
                                                       MapState.guns[1] == null ? -1 : MapState.guns[1].x, MapState.guns[1] == null ? -1 : MapState.guns[1].y));
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
