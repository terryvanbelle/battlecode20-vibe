package cand31;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0;
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock();
        int soup = rc.getTeamSoup() - (MapState.gunPending() && built >= C.WALL_LANDSCAPERS ? RobotType.NET_GUN.cost : 0);   // Iteration 31: a raised gun site has first call on the bank
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.ATTACKER_BANK + RobotType.LANDSCAPER.cost;
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
