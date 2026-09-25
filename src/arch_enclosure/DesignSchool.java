package arch_enclosure;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0;
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        int soup = rc.getTeamSoup();
        int waiting = 0; MapLocation home = MapState.home;   // stage 7: no more than two bodies waiting inside -- waiters stood on the center's spawn tiles and no drone was ever built
        if (home != null) for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.LANDSCAPER && Nav.cheb(friends[i].location, home) <= 1) waiting++;
        boolean want = built < C.LANDSCAPERS_MAX && waiting < 2 && soup >= RobotType.LANDSCAPER.cost + (built < 16 ? 0 : C.HELPER_BANK);   // the enclosure: bodies all game
        boolean did = want && tryBuild(RobotType.LANDSCAPER, MapState.home); if (did) built++;
        if (rc.getRoundNum() % 50 == 0) { StringBuilder sb = new StringBuilder(); for (int i = nFriend; --i >= 0;) if (Nav.cheb(friends[i].location, home) <= 1) sb.append(friends[i].type.ordinal()).append('@').append(friends[i].location).append(' ');
            Debug.log("@school built=" + built + " waiting=" + waiting + " soup=" + soup + " want=" + want + " did=" + did + " inside=" + sb); }
    }
}
