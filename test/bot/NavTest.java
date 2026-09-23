package bot;

import battlecode.common.MapLocation;

/** Pure geometry used by Nav. Run via tools/unit-tests.sh */
public class NavTest {
    static int fails = 0;
    static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL " + what); } }
    public static void main(String[] a) {
        check(Nav.cheb(new MapLocation(0, 0), new MapLocation(3, -5)) == 5, "chebyshev takes the larger axis");
        check(Nav.cheb(new MapLocation(7, 7), new MapLocation(7, 7)) == 0, "zero at the target");
        check(Nav.cheb(new MapLocation(-2, 1), new MapLocation(1, 1)) == 3, "negative coordinates");
        check(C.SOUP_RETURN <= 100 && C.SOUP_RETURN > 0, "a miner returns before its 100-soup limit");
        check(C.MAX_MINERS >= 1, "the HQ builds at least one miner");
        System.out.println(fails == 0 ? "NavTest OK" : "NavTest FAILED " + fails);
        if (fails != 0) System.exit(1);
    }
}
