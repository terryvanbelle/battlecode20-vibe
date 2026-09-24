package arch_plateau;

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
                     : built < C.LANDSCAPERS_MAX && soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost;   // plateau: the third tier at the helpers' bank
        if (want && tryBuild(RobotType.LANDSCAPER, MapState.home)) { int[] m = Comms.make(Comms.SLOT, round, us, built, lastBuilt.x, lastBuilt.y); if (rc.canSubmitTransaction(m, 5)) rc.submitTransaction(m, 5); built++; }   // fee 5: at fee 1 only a quarter of the posts were minted   // the plateau: the newborn reads its slot next round
    }
}
