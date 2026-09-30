package r14a;

import battlecode.common.*;

/** Design school: builds landscapers up to the wall count, then a surplus of attackers while rich. */
public strictfp class DesignSchool extends Robot {
    private int built = 0, pendingId = -1, pendingPosts = 0;
    private int paused = 0;   // R4 stage 3: rounds yielded to the quarry's center and drones
    DesignSchool(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        int soup = rc.getTeamSoup();
        // Iteration 63: the forward school (the enemy HQ in sight and ours not) spawns attackers beside the enemy HQ
        MapLocation eh = null; for (int i = nEnemy; --i >= 0;) if (enemies[i].type == RobotType.HQ) eh = enemies[i].location;
        if (eh != null && (MapState.home == null || Nav.cheb(loc, MapState.home) > 6)) {
            if (built < C.RUSH_LANDSCAPERS && soup >= RobotType.LANDSCAPER.cost && tryBuild(RobotType.LANDSCAPER, eh)) built++;
            return;
        }
        // Iteration 78: after eight, two landscapers per vaporator standing (ronniesong grows both together)
        int vaps = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.VAPORATOR) vaps++;
        // stage 14: no pacing (it deadlocked); stage 16: a hard cap instead -- winkelmantanner stops at ~24 landscapers from r500
        // and banks for vaporators (1,559 soup at r500); with no cap the school spent every 150 and no 500 ever stood (stage 15: V 0 on six maps)
        if (built >= 24 && rc.getRoundNum() >= 400 && rc.getRoundNum() < 1100) return;
        // R1 stage 3 (CentralLake traced: 37 landscapers by r500 from two schools, the bank never at 500, no vaporator tried):
        // past sixteen, a landscaper only with a vaporator's 500 banked on top of its own 150 -- the vaporators come first
        // R4 stage 3: past the seats and helpers, the quarry's center and drones before any lattice worker (C.QUARRY_PAUSE)
        // (a yield, not a stop: the quarry's unpaid cost -- a center not heard of, the drones not yet bought -- stays in the bank
        // and the school spends above it; CentralLake seed 7 with a stop: 2,118 soup idle at r400, the school frozen at twenty)
        boolean yieldQ = false;
        if (built >= C.WALL_LANDSCAPERS + C.WALL_HELPERS && rc.getRoundNum() >= C.QFC_FROM && MapState.qDrones < C.QUARRY_DRONES && paused < C.QUARRY_PAUSE) {   // from QFC_FROM (CentralLake: yielding from r253 held ~1,000 soup idle to r400)
            readBlock();
            int owed = (rc.getRoundNum() - MapState.fcRound < C.QFC_MEMORY ? 0 : RobotType.FULFILLMENT_CENTER.cost) + (C.QUARRY_DRONES - MapState.qDrones) * RobotType.DELIVERY_DRONE.cost;
            if (MapState.qDrones < C.QUARRY_DRONES && soup < owed + C.ATTACKER_BANK + RobotType.LANDSCAPER.cost) { yieldQ = true; if (paused++ == 0) Debug.log("@qfc school yields built=" + built + " soup=" + soup + " owed=" + owed); } }
        if (!yieldQ && built >= 24 && soup < C.VAPORATOR_BANK + RobotType.LANDSCAPER.cost) return;   // stage 9: from 24, not 16 (broadr1: poor maps starved the wall -- 4-8 landscapers on GSF, Egg, Islands2)
        boolean want = built < C.WALL_LANDSCAPERS ? soup >= RobotType.LANDSCAPER.cost
                     : built < C.WALL_LANDSCAPERS + C.WALL_HELPERS ? soup >= C.HELPER_BANK + RobotType.LANDSCAPER.cost
                     : built < C.LANDSCAPERS_MAX && soup >= C.ATTACKER_BANK + RobotType.LANDSCAPER.cost;
        if (want && !yieldQ && tryBuild(RobotType.LANDSCAPER, MapState.home)) {
            built++;
            if (built > C.WALL_LANDSCAPERS + C.WALL_HELPERS) {   // R1 stage 11: the ones past sixteen are the lattice's
                int nid = -1; for (RobotInfo r : rc.senseNearbyRobots(2, us)) if (r.type == RobotType.LANDSCAPER && r.ID > nid) nid = r.ID;
                if (nid >= 0) { pendingId = nid; pendingPosts = 0; }
            }
        }
        if (pendingId >= 0 && pendingPosts < 3 && post(Comms.make(Comms.LATTICE_ORDER, rc.getRoundNum(), us, pendingId))) { pendingPosts++; if (pendingPosts == 3) pendingId = -1; }
    }
}
