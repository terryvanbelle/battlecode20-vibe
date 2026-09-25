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
        int vaps = 0; if (home != null) for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.VAPORATOR && Nav.cheb(friends[i].location, home) <= 1) vaps++;
        int reserve = built >= 8 && vaps < 1 && rc.getRoundNum() >= 250 ? C.VAPORATOR_BANK : 0;   // stage 23: until one stands (INSIDE_MAX 3 leaves no room for a second; the bank sat on the school for 350 rounds)   // stage 11: after eight bodies, leave the builder its vaporator money until two stand inside (the field runs four by r700; we ran none and starved)
        boolean want = built < C.LANDSCAPERS_MAX && waiting < 2 && soup >= RobotType.LANDSCAPER.cost + reserve;   // stage 26: no helper bank (it slowed bodies at r700-950, the last rounds with a dry outer ring)   // the enclosure: bodies all game
        boolean did = want && tryBuild(RobotType.LANDSCAPER, MapState.home); if (did) built++;
        if (rc.getRoundNum() % 50 == 0) { StringBuilder sb = new StringBuilder(); for (int i = nFriend; --i >= 0;) if (Nav.cheb(friends[i].location, home) <= 1) sb.append(friends[i].type.ordinal()).append('@').append(friends[i].location).append(' ');
            Debug.log("@school built=" + built + " waiting=" + waiting + " soup=" + soup + " want=" + want + " did=" + did + " inside=" + sb); }
    }
}
