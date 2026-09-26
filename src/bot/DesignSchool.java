package bot;

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
        // Iteration 72 (poortho's economy): after the eight seats, hold 500 for the builder's vaporators until six stand (before r800)
        int vaps = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.VAPORATOR) vaps++;
        if (built >= C.WALL_LANDSCAPERS && vaps < 6 && rc.getRoundNum() < 500) soup -= C.VAPORATOR_BANK;   // to r500 only (to r800 held TwoForOne at eight landscapers with 880 idle once its vaporators drowned)
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.ATTACKER_BANK + RobotType.LANDSCAPER.cost;
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
