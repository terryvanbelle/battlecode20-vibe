package cand54;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        sense();
        boolean rush = rushSeen() && built < 3;   // Iteration 54: the first three drones at cost while a rush is in sight
        boolean want = built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + (rush ? 0 : rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
