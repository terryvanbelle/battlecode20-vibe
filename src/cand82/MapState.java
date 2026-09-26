package cand82;

import battlecode.common.*;

/**
 * What this robot knows about the map: its size (given), its origin (probed), the HQs and the
 * symmetry. The three hypotheses are rotation, mirror-x (x -> minX+maxX-x) and mirror-y; a
 * hypothesis dies when a sensed tile's elevation or water differs from what we remember at its
 * image, or when the image of our HQ is seen to hold no enemy HQ. Pure functions here are unit
 * tested (test/bot/MapStateTest.java); everything is static because state is per robot.
 */
public final strictfp class MapState {
    private MapState() {}

    public static int width, height;
    public static int minX = -1, minY = -1;          // origin; -1 unknown (the map corner is random)
    public static MapLocation home;                  // our HQ
    public static MapLocation enemyHQ;               // confirmed by sight
    public static boolean enemyRushSeen = false;     // Iteration 82 v2: our HQ has announced an enemy rush
    public static int rushPlantRound = -1;           // Iteration 82: the round our forward school was planted (-1: not yet)
    public static int sym = 7;                       // surviving hypotheses: bit0 rotation, bit1 mirror-x, bit2 mirror-y

    // remembered terrain, indexed by (x - minX) + (y - minY) * width once the origin is known
    public static int[] elev;                        // Integer.MIN_VALUE = unknown
    public static boolean[] known;

    public static void init(RobotController rc) {
        width = rc.getMapWidth(); height = rc.getMapHeight();
        // Iteration 63: the released corpus has its origin at (0,0) -- 47 of 47 maps read from replays (2026-09-26); the
        // edge probe still runs and corrects it if a map ever says otherwise. poortho's rusher is at our HQ by r45;
        // ours, probing for edges, learned the origin at r182 (GSF) and r525 (RandomSoup1).
        if (C.ASSUME_ORIGIN) { minX = 0; minY = 0; }
        if (elev == null) { elev = new int[width * height]; known = new boolean[width * height]; }
    }
    public static void setHome(MapLocation l) { if (home == null) home = l; }
    public static boolean originKnown() { return minX >= 0 && minY >= 0; }
    public static int maxX() { return minX + width - 1; }
    public static int maxY() { return minY + height - 1; }
    public static MapLocation center() { return new MapLocation(minX + width / 2, minY + height / 2); }
    public static int index(MapLocation l) { return (l.x - minX) + (l.y - minY) * width; }
    public static boolean onMap(MapLocation l) { return l.x >= minX && l.y >= minY && l.x <= maxX() && l.y <= maxY(); }

    /** Record an edge: side 0 = minX, 1 = maxX, 2 = minY, 3 = maxY (from rc.onTheMap probing). */
    public static void edgeFound(int side, int coord) {
        switch (side) {
            case 0: minX = coord; break;
            case 1: minX = coord - width + 1; break;
            case 2: minY = coord; break;
            default: minY = coord - height + 1; break;
        }
    }

    /** Image of l under hypothesis h (0 rotation, 1 mirror-x, 2 mirror-y). Needs the origin. */
    public static MapLocation image(MapLocation l, int h) {
        switch (h) {
            case 0: return new MapLocation(minX + maxX() - l.x, minY + maxY() - l.y);
            case 1: return new MapLocation(minX + maxX() - l.x, l.y);
            default: return new MapLocation(l.x, minY + maxY() - l.y);
        }
    }

    /** Remember a sensed tile and prune hypotheses whose image we already know and which disagrees.
     *  Allocation-free: the three image indices are arithmetic on (x, y) (the object version cost
     *  ~245 bytecodes a tile and put miners over their budget 120 times in one game). */
    public static void observe(MapLocation l, int elevation, boolean flooded) {
        if (!originKnown()) return;
        int x = l.x - minX, y = l.y - minY;
        if (x < 0 || y < 0 || x >= width || y >= height) return;
        int i = x + y * width;
        elev[i] = elevation; known[i] = true;
        if (sym == 1 || sym == 2 || sym == 4) return;
        int rx = width - 1 - x, ry = height - 1 - y;
        if ((sym & 1) != 0) { int j = rx + ry * width; if (j != i && known[j] && elev[j] != elevation) sym &= ~1; }
        if ((sym & 2) != 0) { int j = rx + y * width; if (j != i && known[j] && elev[j] != elevation) sym &= ~2; }
        if ((sym & 4) != 0) { int j = x + ry * width; if (j != i && known[j] && elev[j] != elevation) sym &= ~4; }
        if (sym == 0) sym = 7;   // contradiction (should not happen on a legal map): start over rather than stay empty
    }

    /** A sensed tile with no enemy HQ on it cannot be our HQ's image. */
    public static void pruneEmpty(MapLocation t) {
        if (!originKnown() || home == null) return;
        for (int h = 0; h < 3; h++) if ((sym & (1 << h)) != 0 && image(home, h).equals(t)) sym &= ~(1 << h);
        if (sym == 0) sym = 7;
    }

    public static void sightEnemyHQ(MapLocation l) {
        enemyHQ = l;
        if (!originKnown() || home == null) return;
        int keep = 0;
        for (int h = 0; h < 3; h++) if ((sym & (1 << h)) != 0 && image(home, h).equals(l)) keep |= 1 << h;
        if (keep != 0) sym = keep;
    }

    // ---- exploration sectors (SECTOR x SECTOR tiles); seen = this robot has stood in it; bad = it stalled trying to reach it
    public static final int SECTOR = 8;
    public static int sectorsW, sectorsH;
    public static boolean[] sectorSeen, sectorBad;
    public static int sectorOf(MapLocation l) { return (l.x - minX) / SECTOR + ((l.y - minY) / SECTOR) * sectorsW; }
    public static MapLocation sectorCenter(int s) { return new MapLocation(minX + (s % sectorsW) * SECTOR + SECTOR / 2, minY + (s / sectorsW) * SECTOR + SECTOR / 2); }
    private static void ensureSectors() {
        if (sectorSeen != null || !originKnown()) return;
        sectorsW = (width + SECTOR - 1) / SECTOR; sectorsH = (height + SECTOR - 1) / SECTOR;
        sectorSeen = new boolean[sectorsW * sectorsH]; sectorBad = new boolean[sectorsW * sectorsH];
    }
    public static void markSeen(MapLocation l) { ensureSectors(); if (sectorSeen != null && onMap(l)) sectorSeen[sectorOf(l)] = true; }
    public static void markBad(MapLocation l) { ensureSectors(); if (sectorBad != null && onMap(l)) sectorBad[sectorOf(l)] = true; }
    /** The centre of the nearest sector this robot has neither seen nor given up on, or null. `salt` breaks ties. */
    public static MapLocation nextSector(MapLocation from, int salt) {
        ensureSectors(); if (sectorSeen == null) return null;
        int best = -1, bd = 1 << 30;
        for (int s = sectorSeen.length; --s >= 0;) {
            if (sectorSeen[s] || sectorBad[s]) continue;
            MapLocation c = sectorCenter(s);
            int d = from.distanceSquaredTo(c) + ((s * 31 + salt) & 15);
            if (d < bd) { bd = d; best = s; }
        }
        return best < 0 ? null : sectorCenter(best);
    }
    public static int sectorsUnseen() { ensureSectors(); if (sectorSeen == null) return -1; int n = 0; for (boolean b : sectorSeen) if (!b) n++; return n; }

    public static int symCount() { return Integer.bitCount(sym); }
    /** The single surviving hypothesis, or -1. */
    public static int symKnown() { return sym == 1 ? 0 : sym == 2 ? 1 : sym == 4 ? 2 : -1; }

    /** Best guess of the enemy HQ: sighted, else the image of home under the lowest surviving hypothesis. */
    public static MapLocation enemyHQGuess() {
        if (enemyHQ != null) return enemyHQ;
        if (!originKnown() || home == null) return null;
        for (int h = 0; h < 3; h++) if ((sym & (1 << h)) != 0) return image(home, h);
        return null;
    }
}
