package bot;

import battlecode.common.*;

/**
 * The HQ. Builds MINERS_EARLY miners at once, then more while the bank allows and the ring is
 * still open; shoots the nearest enemy drone; posts its location in round 2 (so late-born units
 * that never see it can find home) and the map origin once a unit has found it.
 */
public strictfp class HQ extends Robot {
    private int built = 0, lastBuild = -1000;
    private boolean postedLoc = false, postedOrigin = false, perchPicked = false, postedPerch = false;

    HQ(RobotController rc) { super(rc); MapState.setHome(rc.getLocation()); }

    /** Iteration 24: the perch -- a cardinal direction d such that P = home+2d, B = home+3d and F, V = B +- perp are on the map,
     *  dry, B within 3 of our ground, F and V within 6 of B, none beside the school or the refinery (their spawn tiles), and
     *  B as far from the map centre as possible. */
    private void pickPerch() throws GameActionException {
        MapLocation best = null; long bs = Long.MAX_VALUE; int hE = rc.senseElevation(loc);
        int[][] cardinal = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        for (int k = 0; k < 4; k++) {
            int dx = cardinal[k][0], dy = cardinal[k][1];
            MapLocation p = new MapLocation(loc.x + 2 * dx, loc.y + 2 * dy), b = new MapLocation(loc.x + 3 * dx, loc.y + 3 * dy);
            MapLocation f = new MapLocation(b.x + dy, b.y - dx), v = new MapLocation(b.x - dy, b.y + dx);
            MapLocation[] tiles = {p, b, f, v}; boolean ok = true; int eB = 0;
            for (int i = 0; i < 4 && ok; i++) {
                MapLocation t = tiles[i];
                if (!rc.onTheMap(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t)) { ok = false; break; }
                RobotInfo r = rc.senseRobotAtLocation(t); if (r != null && r.type.isBuilding()) { ok = false; break; }
                int e = rc.senseElevation(t);
                if (i == 1) { eB = e; if (Math.abs(e - hE) > 3) ok = false; }
                else if (i >= 2 && Math.abs(e - eB) > 6) ok = false;
                for (int j = nFriend; --j >= 0;) { RobotInfo fr = friends[j]; if (fr.type.isBuilding() && Nav.cheb(fr.location, t) <= 1 && i >= 1) { ok = false; break; } }
            }
            if (!ok) continue;
            long s = MapState.originKnown() ? -(long) b.distanceSquaredTo(MapState.center()) : k;
            if (s < bs) { bs = s; best = p; }
        }
        MapState.perch = best;
        Debug.log("@perch p=" + best + (best != null ? " b=" + MapState.perchB() + " f=" + MapState.perchF() + " v=" + MapState.perchV() + " target=" + MapState.perchTarget() : ""));
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
        if (want && tryBuild(RobotType.MINER, null)) { built++; lastBuild = round; }

        // Iteration 7: re-post every 100 rounds so robots born late (and the drones) learn home, the origin and the enemy HQ
        if (MapState.perch != null && round < 600 && (!postedPerch || round % 10 == 0)) postedPerch = post(Comms.make(Comms.PERCH, round, us, MapState.perch.x, MapState.perch.y), 3);   // Iteration 24: every 10 rounds; newborns scan 12 blocks back
        else if ((!postedLoc || round % 100 == 50) && round >= 2) postedLoc = post(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y));
        else if ((!postedOrigin || round % 100 == 25) && MapState.originKnown()) postedOrigin = post(Comms.make(Comms.MAP_ORIGIN, round, us, MapState.minX, MapState.minY));
        else if (round % 100 == 75 && MapState.enemyHQ != null) post(Comms.make(Comms.ENEMY_HQ, round, us, MapState.enemyHQ.x, MapState.enemyHQ.y));
        if (!perchPicked && round >= C.PERCH_PICK_ROUND && (MapState.originKnown() || round >= C.PERCH_PICK_ROUND + 40)) { perchPicked = true; pickPerch(); }
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
