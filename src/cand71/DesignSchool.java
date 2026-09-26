package cand71;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0;
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
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
