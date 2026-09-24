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
        check(C.MINERS_MAX >= C.MINERS_EARLY && C.MINERS_EARLY >= 1, "the HQ builds at least one miner");
        // groups(): clockwise from N. Open field: one group.
        boolean[] all = {true, true, true, true, true, true, true, true}; int[] flat = new int[8];
        check(Nav.groups(all, flat) == 1, "groups: open field is one group");
        // Climb: the site (5,39) on the top edge; N, NE, NW off the map, S and SW the ring: E+SE vs W
        boolean[] climb = {false, false, true, true, false, false, true, false};
        check(Nav.groups(climb, flat) == 2, "groups: an arc split by the site");
        // a site on an open Chebyshev-2 circle: SW, S, SE are ring; W and E still join through the outside
        boolean[] circle = {true, true, true, false, false, false, true, true};
        check(Nav.groups(circle, flat) == 1, "groups: a closed circle stays one group");
        // orthogonal neighbours touch across a blocked corner
        boolean[] corner = {true, false, true, false, false, false, false, false};
        check(Nav.groups(corner, flat) == 1, "groups: N and E touch across NE");
        // a 4-step cliff between the two sides splits them
        int[] cliff = {0, 0, 0, 0, 0, 0, 0, 0}; cliff[2] = 4; cliff[3] = 4; boolean[] ne = {true, true, true, true, false, false, false, false};
        check(Nav.groups(ne, cliff) == 2, "groups: a 4-high step splits");
        check(Nav.groups(new boolean[8], flat) == 0, "groups: no open neighbour, no group");
        System.out.println(fails == 0 ? "NavTest OK" : "NavTest FAILED " + fails);
        if (fails != 0) System.exit(1);
    }
}
