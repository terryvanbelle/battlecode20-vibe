package g_iter16;

import battlecode.common.*;

/**
 * The HQ. Builds MINERS_EARLY miners at once, then more while the bank allows and the ring is
 * still open; shoots enemy drones (carriers of ours first, Robot.shootDrone); posts its location in round 2 (so late-born units
 * that never see it can find home) and the map origin once a unit has found it.
 */
public strictfp class HQ extends Robot {
    private int built = 0, lastBuild = -1000;
    private boolean postedLoc = false, postedOrigin = false;

    HQ(RobotController rc) { super(rc); MapState.setHome(rc.getLocation()); }

    @Override protected void turn() throws GameActionException {
        sense(); readBlock(); probeEdges();
        shootDrone();   // R1 stage 17: carriers of ours over dry ground first (Robot.shootDrone)

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
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
