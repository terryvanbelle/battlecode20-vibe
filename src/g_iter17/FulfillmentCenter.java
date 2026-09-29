package g_iter17;

import battlecode.common.*;

/** Fulfillment center: the quarry's drones first (R4 stage 3), then drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    private int born = -1;
    private int posts = 0, lastPost = -1000, postedN = -1;   // R4 stage 3: CENTER_UP posts (and the quarry drone count last posted)
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        sense();
        if (born < 0) born = rc.getRoundNum();
        // Iteration 58: a center born before r350 is the rush center (the normal one comes after the first vaporator);
        // its first three drones come at cost whether or not the rusher is in its sight -- it stands on the far side
        // (Iteration 57's saw nothing from there and bought one drone: gate57r 21-13)
        boolean rush = (rushSeen() || born < 350) && built < 3;
        // R4 stage 3: say that we stand (one center: the miners build none while one is heard of), then the quarry's drones
        // first -- QUARRY_DRONES of them at QDRONE_BANK (the school, acting before us every round, has its helper first)
        if (round % 3 == 2 && (posts < 2 || round - lastPost >= C.QFC_REPOST || postedN != Math.min(built, C.QUARRY_DRONES)) && post(Comms.make(Comms.CENTER_UP, round, us, loc.x, loc.y, Math.min(built, C.QUARRY_DRONES)))) { posts++; lastPost = round; postedN = Math.min(built, C.QUARRY_DRONES); }
        if (!rush && built < C.QUARRY_DRONES) {
            if (soup >= C.QDRONE_BANK && tryBuild(RobotType.DELIVERY_DRONE, MapState.home)) { built++; Debug.log("@qfc drone n=" + built + " soup=" + rc.getTeamSoup()); }
            return;
        }
        int cap = rc.getRoundNum() >= C.RAID_BUILD_FROM ? C.RAID_DRONES_MAX : C.DRONES_MAX;   // Iteration 61
        boolean want = built < cap && soup >= RobotType.DELIVERY_DRONE.cost + (rush ? 0 : rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : built >= C.DRONES_MAX ? 600 : C.DRONE_RESERVE);   // stage 9: raid drones only from a bank above 600 (the vaporators and schools first)
        if (want && tryBuild(RobotType.DELIVERY_DRONE, null)) built++;
    }
}
