package g_iter0;

import battlecode.common.*;

/** The HQ: builds miners, shoots drones, posts its location once. Iteration 0. */
public strictfp class HQ extends Robot {
    private int built = 0;
    private boolean posted = false;

    HQ(RobotController rc) { super(rc); MapState.setHome(rc.getLocation()); }

    @Override protected void turn() throws GameActionException {
        sense();
        // shoot the nearest enemy drone in range
        for (int i = nEnemy; --i >= 0;) {
            RobotInfo e = enemies[i];
            if (e.type == RobotType.DELIVERY_DRONE && rc.canShootUnit(e.ID)) { rc.shootUnit(e.ID); Debug.log("@shoot id=" + e.ID); break; }
        }
        int miners = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.MINER) miners++;
        if (built < C.MAX_MINERS || (miners < 2 && rc.getTeamSoup() >= 2 * RobotType.MINER.cost)) {
            if (tryBuild(RobotType.MINER, null)) built++;
        }
        if (!posted && round > 1 && rc.canSubmitTransaction(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y), 1)) {
            rc.submitTransaction(Comms.make(Comms.HQ_LOC, round, us, loc.x, loc.y), 1); posted = true;
        }
        if (round % 100 == 0) Debug.log("@econ soup=" + rc.getTeamSoup() + " built=" + built + " minersSeen=" + miners);
    }
}
