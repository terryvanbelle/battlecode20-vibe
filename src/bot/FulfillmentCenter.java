package bot;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        if (built < C.DRONES_MAX && rc.getTeamSoup() >= RobotType.DELIVERY_DRONE.cost + 100 && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
