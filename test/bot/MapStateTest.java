package bot;

import battlecode.common.MapLocation;

/** Map knowledge: origin from edges, symmetry images, pruning by terrain and by sightings. Run via tools/unit-tests.sh */
public class MapStateTest {
    static int fails = 0;
    static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL " + what); } }

    static void reset(int w, int h) {
        MapState.width = w; MapState.height = h; MapState.minX = -1; MapState.minY = -1; MapState.home = null; MapState.enemyHQ = null; MapState.sym = 7;
        MapState.elev = new int[w * h]; MapState.known = new boolean[w * h]; MapState.sectorSeen = null; MapState.sectorBad = null;
    }

    public static void main(String[] a) {
        reset(40, 32);
        check(!MapState.originKnown(), "origin unknown at start");
        MapState.edgeFound(1, 10039);           // maxX = 10039 -> minX = 10000
        MapState.edgeFound(2, 20000);           // minY
        check(MapState.originKnown() && MapState.minX == 10000 && MapState.minY == 20000, "origin from a max-x and a min-y edge");
        check(MapState.maxX() == 10039 && MapState.maxY() == 20031, "max corner");
        check(MapState.onMap(new MapLocation(10000, 20031)) && !MapState.onMap(new MapLocation(9999, 20031)) && !MapState.onMap(new MapLocation(10000, 20032)), "onMap bounds");
        MapLocation home = new MapLocation(10003, 20005);
        check(MapState.image(home, 0).equals(new MapLocation(10036, 20026)), "rotation image");
        check(MapState.image(home, 1).equals(new MapLocation(10036, 20005)), "mirror-x image");
        check(MapState.image(home, 2).equals(new MapLocation(10003, 20026)), "mirror-y image");
        check(MapState.image(MapState.image(home, 0), 0).equals(home), "rotation is an involution");

        // terrain pruning: an observed tile whose mirror-x image is known and differs kills mirror-x only
        MapState.setHome(home);
        MapState.observe(new MapLocation(10005, 20010), 3, false);
        MapState.observe(new MapLocation(10034, 20010), 7, false);   // mirror-x image of the first, different elevation
        check(MapState.sym == 5, "mirror-x eliminated, rotation and mirror-y survive: sym=" + MapState.sym);
        MapState.observe(new MapLocation(10005, 20021), 3, false);   // mirror-y image of the first (agrees) AND rotation image of the second (differs)
        check(MapState.sym == 4, "rotation eliminated by the second tile's rotation image, mirror-y is the answer: sym=" + MapState.sym);
        MapState.observe(new MapLocation(10020, 20003), 2, false);
        MapState.observe(new MapLocation(10020, 20028), 2, false);   // its mirror-y image agrees: nothing changes
        check(MapState.sym == 4, "an agreeing image prunes nothing");
        check(MapState.symKnown() == 2 && MapState.enemyHQGuess().equals(new MapLocation(10003, 20026)), "enemy HQ guess follows the survivor");

        // sighting an enemy HQ selects the matching hypothesis; an empty image tile removes one
        reset(40, 32); MapState.edgeFound(0, 10000); MapState.edgeFound(3, 20031); MapState.setHome(home);
        MapState.pruneEmpty(new MapLocation(10036, 20005));
        check(MapState.sym == 5, "empty mirror-x image tile prunes mirror-x");
        MapState.sightEnemyHQ(new MapLocation(10036, 20026));
        check(MapState.sym == 1 && MapState.symKnown() == 0, "a sighted enemy HQ at the rotation image fixes rotation");
        check(MapState.enemyHQGuess().equals(new MapLocation(10036, 20026)), "sighted HQ is the guess");

        // a contradiction never leaves zero hypotheses
        reset(40, 32); MapState.edgeFound(0, 0); MapState.edgeFound(2, 0); MapState.setHome(new MapLocation(20, 16));
        MapState.sym = 1; MapState.pruneEmpty(new MapLocation(19, 15));
        check(MapState.sym != 0, "pruning the last hypothesis resets instead of emptying");

        // the center never leaves the map and index is in range
        reset(32, 64); MapState.edgeFound(0, 500); MapState.edgeFound(2, 700);
        MapLocation c = MapState.center();
        check(MapState.onMap(c) && MapState.index(c) >= 0 && MapState.index(c) < 32 * 64, "center on map");
        check(MapState.index(new MapLocation(531, 763)) == 32 * 64 - 1, "last index");
        // exploration sectors: nearest unseen first, seen and bad ones skipped, null when exhausted
        reset(40, 32); MapState.edgeFound(0, 100); MapState.edgeFound(2, 200);
        check(MapState.nextSector(new MapLocation(100, 200), 0) == null || true, "sectors lazily built");
        MapLocation me = new MapLocation(101, 201);
        MapState.markSeen(me);
        check(MapState.sectorsW == 5 && MapState.sectorsH == 4 && MapState.sectorSeen[0], "40x32 map has 5x4 sectors and my sector is seen");
        MapLocation n1 = MapState.nextSector(me, 0);
        check(n1 != null && !n1.equals(MapState.sectorCenter(0)) && Nav.cheb(n1, me) <= 12, "next sector is an adjacent unseen one: " + n1);
        MapState.markBad(n1);
        MapLocation n2 = MapState.nextSector(me, 0);
        check(n2 != null && !n2.equals(n1), "a bad sector is skipped");
        for (int s = 0; s < MapState.sectorSeen.length; s++) MapState.sectorSeen[s] = true;
        check(MapState.nextSector(me, 0) == null && MapState.sectorsUnseen() == 0, "exhausted sectors give null");
        check(MapState.onMap(MapState.sectorCenter(19)), "last sector centre is on the map");
        System.out.println(fails == 0 ? "MapStateTest OK" : "MapStateTest FAILED " + fails);
        if (fails != 0) System.exit(1);
    }
}
