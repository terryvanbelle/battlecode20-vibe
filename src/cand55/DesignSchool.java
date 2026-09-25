package cand55;

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
        boolean did = want && tryBuild(RobotType.LANDSCAPER, MapState.home); if (did) built++;
        if (want && !did && rc.getRoundNum() % 10 == 0) {   // trace: why a school with the soup does not spawn
            StringBuilder sb = new StringBuilder(); int e0 = rc.senseElevation(loc);
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!rc.onTheMap(n)) { sb.append(" off"); continue; }
                RobotInfo r = rc.senseRobotAtLocation(n); sb.append(' ').append(d.ordinal()).append(':').append(rc.senseElevation(n) - e0).append(rc.senseFlooding(n) ? "F" : "").append(r == null ? "" : (r.team == rc.getTeam() ? "o" : "x") + r.type.ordinal()); }
            Debug.log("@schoolshut e=" + e0 + " dirt=" + rc.getDirtCarrying() + " soup=" + soup + sb); }
    }
}
