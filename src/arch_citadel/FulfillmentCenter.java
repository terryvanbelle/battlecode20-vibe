package arch_citadel;

import battlecode.common.*;

/** Fulfillment center: builds drones while the bank allows, up to DRONES_MAX. */
public strictfp class FulfillmentCenter extends Robot {
    private int built = 0;
    FulfillmentCenter(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense(); int soup = rc.getTeamSoup();
        boolean want = built < C.FERRY_DRONES ? soup >= RobotType.DELIVERY_DRONE.cost   // Iteration 6: the ferry drones come first, whatever the round
                     : rc.getRoundNum() >= C.RAID_ROUND ? soup >= RobotType.DELIVERY_DRONE.cost + C.DRONE_RESERVE
                     : built < C.DRONES_MAX && soup >= RobotType.DELIVERY_DRONE.cost + (rc.getRoundNum() < C.DRONE_ROUND ? C.DRONE_EARLY_BANK : C.DRONE_RESERVE);
        if (!want) return;
        for (int i = 8; --i >= 0;) {   // F or a ring tile; never another pocket tile (those are building sites)
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (MapState.home != null && Nav.cheb(n, MapState.home) < C.RING && !isSpawnTile(n)) continue;
            if (rc.canBuildRobot(RobotType.DELIVERY_DRONE, d)) { rc.buildRobot(RobotType.DELIVERY_DRONE, d); built++; Debug.log("@build t=7 at=" + n + " soup=" + rc.getTeamSoup()); return; }
        }
    }
}
