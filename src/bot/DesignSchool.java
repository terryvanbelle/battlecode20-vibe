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
                     : built < C.LANDSCAPERS_MAX && soup >= C.INNER_BANK + RobotType.LANDSCAPER.cost;   // inner workers: the school is sealed in with the vaporators
        if (MapState.school == null && MapState.home != null) MapState.setSchool(loc, rc);   // a robot never senses itself
        if (!want) return;
        // Iteration 6: a ring tile first (an instant seat), then the spawn tile F (the ferry lifts it out once the wall is up);
        // a landscaper born on any other pocket tile is boxed in by the buildings, so those only before the wall starts.
        for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (onRing(n) && !isGate(n) && rc.canBuildRobot(RobotType.LANDSCAPER, DIRS[i])) { rc.buildRobot(RobotType.LANDSCAPER, DIRS[i]); built++; Debug.log("@build t=6 at=" + n + " soup=" + rc.getTeamSoup()); return; } }
        if (MapState.gateF != null) { Direction d = loc.directionTo(MapState.gateF); if (rc.canBuildRobot(RobotType.LANDSCAPER, d)) { rc.buildRobot(RobotType.LANDSCAPER, d); built++; Debug.log("@build t=6 at=" + MapState.gateF + " soup=" + rc.getTeamSoup()); return; } }
        if (rc.getRoundNum() < C.WALL_START && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;
    }
}
