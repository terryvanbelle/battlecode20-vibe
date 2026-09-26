package cand81s8;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    private int born = -1; private int announced = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        sense();
        if (born < 0) born = rc.getRoundNum();
        if (announced < 3 && post(Comms.make(Comms.FC_UP, rc.getRoundNum(), us, loc.x, loc.y))) announced++;   // Iteration 81 stage 5: three rounds running (a miner reads one block in three)
        // Iteration 58: a center born before r350 is the rush center (the normal one comes after the first vaporator);
        // its first three drones come at cost whether or not the rusher is in its sight -- it stands on the far side
        // (Iteration 57's saw nothing from there and bought one drone: gate57r 21-13)
        boolean rush = (rushSeen() || born < 350) && built < 3;
        boolean want = built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + (rush ? 0 : rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
