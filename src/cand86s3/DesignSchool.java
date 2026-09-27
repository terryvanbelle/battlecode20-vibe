package cand86s3;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0, ordered = 0, pendingId = -1, pendingPosts = 0;
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        int soup = rc.getTeamSoup();
        // Iteration 63: the forward school (the enemy HQ in sight and ours not) spawns attackers beside the enemy HQ
        MapLocation eh = null; for (int i = nEnemy; --i >= 0;) if (enemies[i].type == RobotType.HQ) eh = enemies[i].location;
        if (eh != null && (MapState.home == null || Nav.cheb(loc, MapState.home) > 6)) {
            if (built < C.RUSH_LANDSCAPERS && soup >= RobotType.LANDSCAPER.cost && tryBuild(RobotType.LANDSCAPER, eh)) built++;
            return;
        }
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.ATTACKER_BANK + RobotType.LANDSCAPER.cost;
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) {
            built++;
            // Iteration 86 stage 1e: after the eight seats, the next KEEP_MASONS newborns are the keep's masons -- the school is the
            // one robot that knows the count (1c's count in sight made 2 masons on 8 maps; 1d's chain count, 16-18 a game)
            if (built > C.WALL_LANDSCAPERS && ordered < C.KEEP_MASONS) {
                int nid = -1; for (RobotInfo r : rc.senseNearbyRobots(2, us)) if (r.type == RobotType.LANDSCAPER && r.ID > nid) nid = r.ID;
                if (nid >= 0) { ordered++; pendingId = nid; pendingPosts = 0; }
            }
        }
        if (pendingId >= 0 && pendingPosts < 3 && post(Comms.make(Comms.MASON_ORDER, rc.getRoundNum(), us, pendingId))) { pendingPosts++; if (pendingPosts == 3) pendingId = -1; }
    }
}
