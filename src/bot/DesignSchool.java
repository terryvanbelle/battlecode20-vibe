package bot;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0;
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        int soup = rc.getTeamSoup();
        // the school acts before the center (built first) and would spend every 150 as it came: leave the center its
        // drone while the ferry is being set up (Soup diagnostic: 25 landscapers, no drone all game)
        boolean fcSeen = false; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.FULFILLMENT_CENTER) { fcSeen = true; break; }
        if (fcSeen && rc.getRoundNum() < C.GATE_CLOSE) soup -= RobotType.DELIVERY_DRONE.cost;
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.INNER_BANK + RobotType.LANDSCAPER.cost;   // inner workers: the school is sealed in with the vaporators
        if (MapState.school == null && MapState.home != null) { MapState.setSchool(loc, rc); Debug.log("@layout S=" + loc + " F=" + MapState.gateF + " G=" + MapState.gateG + " fc=" + MapState.fcSlot + " park=" + MapState.park); }   // a robot never senses itself
        if (!want) return;
        if (rc.getRoundNum() >= C.GATE_CLOSE && MapState.gateF != null) return;   // the outside is flooding: no more ferry targets, and a landscaper on F would block the drones
        // Iteration 6: a ring tile first (an instant seat), then the spawn tile F (the ferry lifts it out once the wall is up);
        // a landscaper born on any other pocket tile is boxed in by the buildings, so those only before the wall starts.
        for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (onRing(n) && !isGate(n) && rc.canBuildRobot(RobotType.LANDSCAPER, DIRS[i])) { rc.buildRobot(RobotType.LANDSCAPER, DIRS[i]); built++; Debug.log("@build t=6 at=" + n + " soup=" + rc.getTeamSoup()); return; } }
        if (MapState.gateF != null) { Direction d = loc.directionTo(MapState.gateF); if (rc.canBuildRobot(RobotType.LANDSCAPER, d)) { rc.buildRobot(RobotType.LANDSCAPER, d); built++; Debug.log("@build t=6 at=" + MapState.gateF + " soup=" + rc.getTeamSoup()); return; } }
        if (MapState.gateF == null && tryBuild(RobotType.LANDSCAPER, MapState.home)) built++;   // only before the layout is known: any other pocket tile is a box
    }
}
