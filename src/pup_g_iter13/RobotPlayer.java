package pup_g_iter13;

import battlecode.common.*;

/** Puppet on g_iter13 (PROMPTS 59-60): the fixture's side until the cutoff, then g_iter13. */
public strictfp class RobotPlayer {
    public static void run(RobotController rc) throws GameActionException {
        if (puppet.Puppet.play(rc)) g_iter13.MapState.setHome(puppet.Puppet.home);
        g_iter13.RobotPlayer.run(rc);
    }
}
