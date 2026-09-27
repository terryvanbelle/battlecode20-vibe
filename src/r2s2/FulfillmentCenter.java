package r2s2;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    private int born = -1;
    private boolean rushFC = false;   // r2: born under a rush at home
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        int soup = rc.getTeamSoup();
        sense(); readReserve();
        if (born < 0) born = rc.getRoundNum();
        if (!rushFC && round - born <= 3 && round < C.RUSH_HOME_UNTIL + C.HQ_ALARM_MAX && (rushHome() || round < MapState.reserveUntil)) { rushFC = true; Debug.log("@rushfc born=" + born); }
        // Iteration 58: a center born before r350 is the rush center (the normal one comes after the first vaporator);
        // its first three drones come at cost whether or not the rusher is in its sight -- it stands on the far side
        // (Iteration 57's saw nothing from there and bought one drone: gate57r 21-13)
        boolean rush = (rushSeen() || born < 350) && built < 3;
        int cap = rc.getRoundNum() >= C.RAID_BUILD_FROM ? C.RAID_DRONES_MAX : C.DRONES_MAX;   // Iteration 61
        boolean want = built < cap && soup >= RobotType.DELIVERY_DRONE.cost + (rush ? 0 : rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : built >= C.DRONES_MAX ? 600 : C.DRONE_RESERVE);   // stage 9: raid drones only from a bank above 600 (the vaporators and schools first)
        if (want && tryBuild(RobotType.DELIVERY_DRONE, rushFC ? MapState.rushSchool : null)) built++;
        // r2: until its first RUSH_DRONES_FIRST drones exist every other spender keeps a drone's 150 (offence only for the first)
        if (rushFC && built < C.RUSH_DRONES_FIRST && round - born < C.RESERVE_MAX && round % 3 == 2) postReserve(built == 0);
    }
}
