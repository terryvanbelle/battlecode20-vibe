package cand54;

import battlecode.common.*;

/** Entry point: pick the controller for this robot's type and run its loop forever. */
public strictfp class RobotPlayer {
    public static void run(RobotController rc) {
        Robot r;
        switch (rc.getType()) {
            case HQ: r = new HQ(rc); break;
            case MINER: r = new Miner(rc); break;
            case DESIGN_SCHOOL: r = new DesignSchool(rc); break;
            case FULFILLMENT_CENTER: r = new FulfillmentCenter(rc); break;
            case LANDSCAPER: r = new Landscaper(rc); break;
            case DELIVERY_DRONE: r = new Drone(rc); break;
            case NET_GUN: r = new NetGun(rc); break;
            default: r = new Building(rc); break;   // refinery, vaporator: nothing to do but breathe
        }
        r.loop();
    }
}
