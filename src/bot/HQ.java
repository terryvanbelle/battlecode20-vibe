package bot;

import battlecode.common.*;

/**
 * The HQ. Builds MINERS_EARLY miners at once, then more while the bank allows and the ring is
 * still open; shoots the nearest enemy drone; posts its location in round 2 (so late-born units
 * that never see it can find home) and the map origin once a unit has found it.
 */
public strictfp class HQ extends Robot {
    private int built = 0, lastBuild = -1000;
    private int lastSoup = 0, lastIncome = 0;   // Iteration 46
    private boolean postedLoc = false, postedOrigin = false;

    HQ(RobotController rc) { super(rc); MapState.setHome(rc.getLocation()); }

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
        // Iteration 46: a miner is worth buying only while the mines pay -- on GSF the lowlands flood at r250-300, the mines
        // count stops, and the HQ went on buying a miner per 60 rounds (16 to 21 spawned by r700) that mined nothing,
        // while the school wanted the soup for seats. Income = the team soup rising between two of our turns.
        int soupNow = rc.getTeamSoup(); if (soupNow > lastSoup) lastIncome = round; lastSoup = soupNow;
        boolean paying = round - lastIncome < C.MINER_INCOME_WINDOW;
        boolean want = built < C.MINERS_EARLY
            || (built < C.MINERS_MAX && paying && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS)
            || (built < C.MINERS_TOTAL && paying && round - lastBuild >= C.MINER_REPLENISH && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS);
        if (!paying && round % 100 == 0) Debug.log("@nomines since=" + lastIncome);
        // Iteration 29: under a rush, no miner until our school stands (the 70s go to the school and its landscapers)
        if (want && rushSeen()) { boolean school = false; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DESIGN_SCHOOL) { school = true; break; } if (!school) want = false; }
        if (want && tryBuild(RobotType.MINER, null)) { built++; lastBuild = round; }

        // Iteration 7: re-post every 100 rounds so robots born late (and the drones) learn home, the origin and the enemy HQ
        if ((!postedLoc || round % 100 == 50) && round >= 2) postedLoc = post(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y));
        else if ((!postedOrigin || round % 100 == 25) && MapState.originKnown()) postedOrigin = post(Comms.make(Comms.MAP_ORIGIN, round, us, MapState.minX, MapState.minY));
        else if (round % 100 == 75 && MapState.enemyHQ != null) post(Comms.make(Comms.ENEMY_HQ, round, us, MapState.enemyHQ.x, MapState.enemyHQ.y));
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
