package bot;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        int round = rc.getRoundNum();
        boolean want = round >= 700 ? built < C.DRONES_LATE_MAX && soup >= RobotType.DELIVERY_DRONE.cost + C.DRONE_LATE_RESERVE   // Iteration 24: a center that outlived the flood spends the idle soup on guards
                     : built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + (round < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
