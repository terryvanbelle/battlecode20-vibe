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

    @Override protected void turn() throws GameActionException {
        sense(); reportDrone(); readBlock(); probeEdges();
        // shoot the nearest enemy drone in range
        RobotInfo target = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type != RobotType.DELIVERY_DRONE) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd && rc.canShootUnit(e.ID)) { bd = d; target = e; } }
        if (target != null) { rc.shootUnit(target.ID); Debug.log("@shoot id=" + target.ID + " d2=" + bd); }

        int miners = 0, landscapersAdj = 0;
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.MINER) miners++; else if (f.type == RobotType.LANDSCAPER && onRing(f.location)) landscapersAdj++; }
        // early burst by count built (miners roam out of sight, so the sensed count cannot cap anything);
        // then one more miner per MINER_REPLENISH rounds while rich, up to a hard total
        boolean want = built < C.MINERS_EARLY
            || (built < C.MINERS_MAX && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS / 2)
            || (built < C.MINERS_TOTAL && round - lastBuild >= C.MINER_REPLENISH && rc.getTeamSoup() >= C.MINER_SOUP_RESERVE && landscapersAdj < C.WALL_LANDSCAPERS / 2);
        if (want && tryBuild(RobotType.MINER, null)) { built++; lastBuild = round; }

        if ((!postedLoc && round >= 2) || round % 100 == 50) postedLoc = post(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y));
        else if ((!postedOrigin || round % 100 == 25) && MapState.originKnown()) postedOrigin = post(Comms.make(Comms.MAP_ORIGIN, round, us, MapState.minX, MapState.minY));
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners + " ring=" + landscapersAdj + " buried=" + rc.getDirtCarrying() + " sym=" + MapState.sym);
    }
}
