package arch_enclosure;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        boolean want = built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + (built == 0 ? 0 : rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);   // stage 23: the first drone as soon as it is affordable -- two waiters block the school until it comes (r200-r400 on Squares)
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
