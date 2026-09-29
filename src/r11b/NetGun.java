package r11b;

import battlecode.common.*;

/** Net gun: shoot enemy drones, carriers of ours first (Robot.shootDrone). */
public strictfp class NetGun extends Robot {
    NetGun(RobotController rc) { super(rc); }
    @Override protected void turn() throws GameActionException {
        sense();
        shootDrone();
    }
}
