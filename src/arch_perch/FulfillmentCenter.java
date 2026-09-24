package arch_perch;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        if (round == birth) { sense(); readBack(20); }   // Iteration 24: a perch center learns home (in sight) and the perch (posted every 10 rounds until r600)
        int soup = rc.getTeamSoup();
        int round = rc.getRoundNum();
        int reserve = C.DRONE_LATE_RESERVE;
        if (round < C.VAPORATOR_FIRST_UNTIL && MapState.perch != null && MapState.home != null) {   // Iteration 24: the vaporator (2 soup a round for 1,500 rounds) comes before the drones
            MapLocation v = MapState.perchV();
            RobotInfo r = rc.canSenseLocation(v) ? rc.senseRobotAtLocation(v) : null;
            if (r == null || r.type != RobotType.VAPORATOR) reserve = RobotType.VAPORATOR.cost - RobotType.DELIVERY_DRONE.cost;
        }
        boolean want = round >= 700 ? built < C.DRONES_LATE_MAX && soup >= RobotType.DELIVERY_DRONE.cost + reserve   // Iteration 24: a center that outlived the flood spends the idle soup on guards
                     : built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + Math.max(reserve, round < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
