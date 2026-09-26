package cand75;

import battlecode.common.*;

/** Net gun: shoot the nearest enemy drone in range. */
public strictfp class NetGun extends Robot {
    NetGun(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        RobotInfo best = null; int bd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type != RobotType.DELIVERY_DRONE) continue; int d = loc.distanceSquaredTo(e.location); if (d < bd && rc.canShootUnit(e.ID)) { bd = d; best = e; } }
        if (best != null) { rc.shootUnit(best.ID); Debug.log("@shoot id=" + best.ID); }
    }
}
