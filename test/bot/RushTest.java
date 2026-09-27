package bot;

import battlecode.common.*;

/** r2: the rush-at-home trigger and the bank reserve. Run via tools/unit-tests.sh */
public class RushTest {
    static int fails = 0;
    static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL " + what); } }

    public static void main(String[] a) {
        MapLocation h = new MapLocation(20, 20);
        check(Robot.rushHomeHit(RobotType.DESIGN_SCHOOL, new MapLocation(23, 20), h), "a school at Chebyshev 3 (arch_rush) is a rush at home");
        check(Robot.rushHomeHit(RobotType.DESIGN_SCHOOL, new MapLocation(24, 16), h), "a school at Chebyshev 4 counts");
        check(!Robot.rushHomeHit(RobotType.DESIGN_SCHOOL, new MapLocation(25, 20), h), "a school at Chebyshev 5 does not");
        check(Robot.rushHomeHit(RobotType.LANDSCAPER, new MapLocation(22, 18), h), "a landscaper at Chebyshev 2 counts");
        check(!Robot.rushHomeHit(RobotType.LANDSCAPER, new MapLocation(23, 21), h), "a landscaper at Chebyshev 3 does not");
        check(!Robot.rushHomeHit(RobotType.MINER, new MapLocation(21, 20), h), "a miner on the ring does not");
        check(!Robot.rushHomeHit(RobotType.FULFILLMENT_CENTER, new MapLocation(21, 21), h), "a center does not");
        check(Robot.reserved(100, 108, 200, 150), "150 of 200 would leave less than a drone: wait");
        check(!Robot.reserved(100, 108, 300, 150), "300 leaves a drone's 150: build");
        check(!Robot.reserved(108, 108, 200, 150), "the reserve ends at its round");
        check(Robot.reserved(100, 108, 219, 70), "a miner at 219 waits");
        check(!Robot.reserved(100, 108, 220, 70), "a miner at 220 builds");
        check(!Robot.reserved(100, 0, 150, 150), "no reserve: build");
        int[] m = Comms.make(Comms.RESERVE, 57, Team.A, 65, 1);
        check(Comms.ours(m, 57, Team.A) && m[0] == Comms.RESERVE && m[1] == 65 && m[2] == 1, "a RESERVE post authenticates and carries until/all");
        MapState.reserveUntil = 0; MapState.reserveAllUntil = 0;
        Robot.absorbReserve(m);
        check(MapState.reserveUntil == 65 && MapState.reserveAllUntil == 65, "an all-post holds offence too");
        Robot.absorbReserve(Comms.make(Comms.RESERVE, 60, Team.A, 68, 0));
        check(MapState.reserveUntil == 68 && MapState.reserveAllUntil == 65, "a home-only post extends only the home reserve");
        Robot.absorbReserve(Comms.make(Comms.RESERVE, 61, Team.A, 62, 1));
        check(MapState.reserveUntil == 68 && MapState.reserveAllUntil == 65, "an older post never shortens a reserve");
        System.out.println(fails == 0 ? "RushTest OK" : "RushTest FAILED " + fails);
        if (fails != 0) System.exit(1);
    }
}
