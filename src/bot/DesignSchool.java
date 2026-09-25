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
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.ATTACKER_BANK + RobotType.LANDSCAPER.cost;
        if (round % 3 == 2) readBlock();
        if (MapState.site != null && loc.equals(MapState.site)) {   // Iteration 36: the replacement school
            if (round < C.SCHOOL2_FROM || rc.getTeamSoup() < C.SCHOOL2_BANK || !rc.isReady()) return;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation p = loc.add(d);
                if (Nav.cheb(p, MapState.home) != 2 || !rc.onTheMap(p) || !rc.canSenseLocation(p) || rc.senseFlooding(p)) continue;
                if (rc.canBuildRobot(RobotType.LANDSCAPER, d)) { rc.buildRobot(RobotType.LANDSCAPER, d); built++; Debug.log("@replacement post=" + p + " soup=" + rc.getTeamSoup()); return; } }
            return;
        }
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
