package bot;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0;
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        int soup = rc.getTeamSoup();
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.VAPORATOR_BANK + RobotType.LANDSCAPER.cost;
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
