package bot;

import battlecode.common.*;

/**
 * Movement for walkers and flyers. A step is legal when the engine says so (adjacent, free,
 * elevation within 3 for walkers) AND the tile is not flooded for walkers: rc.canMove does not
 * check water and a walker that moves onto water dies (RULES.md). The greedy step minimises the
 * Chebyshev distance to the target with a small Euclidean tie-break (relative, never a compass
 * order); an oscillation guard forbids the last few tiles; after several turns without progress
 * it switches to bug wall-following with a per-robot handedness until it gets closer than ever.
 */
public final strictfp class Nav {
    private final RobotController rc;
    private final Robot bot;
    private MapLocation target;
    private final MapLocation[] recent = new MapLocation[6]; private int recentI = 0;
    private int stuck = 0;
    private int noProgress = 0;            // turns since bestDist last improved
    public static final int STALL = 10;    // after this many, the target is treated as unreachable
    public int stallLimit = STALL;         // per robot: landscapers waiting for a seat use a longer one
    private int bestDist = 1 << 30;
    private boolean bugging = false; private final boolean rightHanded;
    public int steps = 0, blocked = 0, bugSteps = 0;   // counters for @nav logs

    Nav(RobotController rc, Robot bot) { this.rc = rc; this.bot = bot; rightHanded = bot.nextInt(2) == 0; }

    /** Walkable groups among a tile's 8 neighbours, clockwise from NORTH (open[i]: walkable; elev[i]: its height).
     *  Neighbours i and i+1 touch; two orthogonal neighbours (i, i+2 with i even) touch across the corner between
     *  them. Two tiles join only if their heights differ by at most 3. A building on a tile whose neighbours form
     *  more than one group may cut the only path between them (Climb, 2026-09-24: the refinery on the one tile of
     *  the Chebyshev-2 arc that bypassed the ring sealed five miners behind the HQ). */
    public static int groups(boolean[] open, int[] elev) {
        int[] p = {0, 1, 2, 3, 4, 5, 6, 7};
        for (int i = 0; i < 8; i++) {
            if (!open[i]) continue;
            int j = (i + 1) & 7; if (open[j] && Math.abs(elev[i] - elev[j]) <= 3) join(p, i, j);
            if ((i & 1) == 0) { j = (i + 2) & 7; if (open[j] && Math.abs(elev[i] - elev[j]) <= 3) join(p, i, j); }
        }
        int n = 0; for (int i = 0; i < 8; i++) if (open[i] && find(p, i) == i) n++;
        return n;
    }
    private static int find(int[] p, int i) { while (p[i] != i) i = p[i]; return i; }
    private static void join(int[] p, int a, int b) { a = find(p, a); b = find(p, b); if (a != b) p[a] = b; }
    public static int cheb(MapLocation a, MapLocation b) { int dx = a.x - b.x, dy = a.y - b.y; if (dx < 0) dx = -dx; if (dy < 0) dy = -dy; return dx > dy ? dx : dy; }

    public void setTarget(MapLocation t) {
        if (t == null) { target = null; return; }
        if (target == null || !target.equals(t)) { target = t; bestDist = 1 << 30; stuck = 0; noProgress = 0; bugging = false; }
    }
    public MapLocation target() { return target; }
    public boolean isBugging() { return bugging; }
    /** True once STALL turns have passed without ever getting closer to the target than before. */
    public boolean stalled() { return noProgress >= stallLimit; }

    private boolean isRecent(MapLocation l) { for (int i = 6; --i >= 0;) if (recent[i] != null && recent[i].equals(l)) return true; return false; }
    private void remember(MapLocation l) { recent[recentI] = l; recentI = (recentI + 1) % 6; }

    private boolean legal(Direction d, MapLocation n) throws GameActionException {
        if (!rc.canMove(d) || !bot.allowedTile(n)) return false;
        if (bot.type.canFly()) return true;
        return rc.canSenseLocation(n) && !rc.senseFlooding(n);
    }

    /** One step toward the target. Returns true if moved. */
    public boolean step() throws GameActionException {
        if (target == null || !rc.isReady()) return false;
        MapLocation me = rc.getLocation();
        int d0 = cheb(me, target);
        if (d0 == 0) return false;
        if (bugging) return bugStep(me);
        Direction best = null; int bestScore = 1 << 30;
        for (int i = 8; --i >= 0;) {
            Direction d = Robot.DIRS[i];
            MapLocation n = me.add(d);
            if (isRecent(n) || !legal(d, n)) continue;
            int score = cheb(n, target) * 1000 + n.distanceSquaredTo(target);   // Chebyshev first, Euclidean tie-break
            if (score < bestScore) { bestScore = score; best = d; }
        }
        if (best == null) { blocked++; noProgress++; if (++stuck >= 2) bugging = true; return false; }
        MapLocation n = me.add(best);
        int d1 = cheb(n, target);
        if (d1 < bestDist) { bestDist = d1; stuck = 0; noProgress = 0; } else { noProgress++; if (++stuck >= 4) bugging = true; }
        remember(me); rc.move(best); bot.loc = rc.getLocation(); steps++;
        return true;
    }

    /** Bug: rotate from the target direction with the wall on one side; leave once closer than ever before. */
    private boolean bugStep(MapLocation me) throws GameActionException {
        Direction d = me.directionTo(target);
        for (int i = 0; i < 8; i++) {
            MapLocation n = me.add(d);
            if (!isRecent(n) && legal(d, n)) {
                remember(me); rc.move(d); bot.loc = rc.getLocation(); steps++; bugSteps++;
                int d1 = cheb(n, target);
                if (d1 < bestDist) { bestDist = d1; stuck = 0; noProgress = 0; bugging = false; } else noProgress++;
                return true;
            }
            d = rightHanded ? d.rotateRight() : d.rotateLeft();
        }
        d = me.directionTo(target);   // boxed in: allow a recent tile
        for (int i = 0; i < 8; i++) { MapLocation n = me.add(d); if (legal(d, n)) { rc.move(d); bot.loc = rc.getLocation(); steps++; bugSteps++; return true; } d = rightHanded ? d.rotateRight() : d.rotateLeft(); }
        blocked++; stuck++; noProgress++;
        return false;
    }
}
