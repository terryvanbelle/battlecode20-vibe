package bot;

import battlecode.common.*;

/**
 * Base controller: the turn loop, the bytecode monitor, a per-robot RNG, the sensing cache and
 * the shared helpers. Every static field is per robot (each robot runs its own copy of the
 * class tree), so static state is free per-robot memory, never team memory.
 */
public abstract strictfp class Robot {
    protected final RobotController rc;
    protected final Team us, them;
    protected final RobotType type;
    protected final int id;
    protected int round, birth;
    protected MapLocation loc;
    protected int rng;                      // LCG state seeded from the id: deterministic, uncorrelated with team
    protected final Nav nav;

    // per-turn sensing cache
    protected RobotInfo[] nearby;
    protected int nEnemy, nFriend, nCow;
    protected final RobotInfo[] enemies = new RobotInfo[64], friends = new RobotInfo[64], cows = new RobotInfo[8];
    protected RobotInfo nearestEnemy; protected int nearestEnemyD2;

    // bytecode monitor
    private int bcMax = 0, bcOver = 0, bcNear = 0, turns = 0;
    private final int bcLimit;

    /** 8 compass directions; iterate with a relative tie-break, never as a fixed preference order. */
    static final Direction[] DIRS = {Direction.NORTH, Direction.NORTHEAST, Direction.EAST, Direction.SOUTHEAST,
                                     Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST};

    Robot(RobotController rc) {
        this.rc = rc; us = rc.getTeam(); them = us.opponent(); type = rc.getType(); id = rc.getID();
        rng = id * 1103515245 + 12345;
        bcLimit = type.bytecodeLimit;
        birth = rc.getRoundNum();
        loc = rc.getLocation();
        MapState.init(rc);
        nav = new Nav(rc, this);
    }

    /** Uniform-ish int in [0, n). */
    protected int nextInt(int n) { rng = rng * 1103515245 + 12345; return ((rng >>> 8) & 0x7fffffff) % n; }

    public final void loop() {
        try { init(); } catch (Exception e) { Debug.exception(e); }
        while (true) {
            int r0 = rc.getRoundNum();
            round = r0; loc = rc.getLocation();
            try {
                turn();
            } catch (Exception e) { Debug.exception(e); }
            int used = Clock.getBytecodeNum();
            turns++;
            boolean overran = rc.getRoundNum() != r0;
            if (overran) bcOver++;
            else { if (used > bcMax) bcMax = used; if (used > bcLimit - bcLimit / 10) bcNear++; }
            if (turns % C.BC_REPORT_EVERY == 0 || overran)
                Debug.log("@bc t=" + type.ordinal() + " used=" + used + " max=" + bcMax + " near=" + bcNear + " over=" + bcOver);
            Clock.yield();
        }
    }

    /** Once, before the first turn. */
    protected void init() throws GameActionException {
        // the building that made us is adjacent; the HQ is what we most want to know
        RobotInfo[] adj = rc.senseNearbyRobots(2, us);
        for (int i = adj.length; --i >= 0;) if (adj[i].type == RobotType.HQ) { MapState.setHome(adj[i].location); break; }
    }

    /** One turn of this robot's logic. */
    protected abstract void turn() throws GameActionException;

    // ---- shared helpers ----

    /** Sense everything once and bucket it. 100 + ~12 per robot bytecodes. */
    protected void sense() {
        nearby = rc.senseNearbyRobots();
        nEnemy = nFriend = nCow = 0; nearestEnemy = null; nearestEnemyD2 = 1 << 30;
        for (int i = nearby.length; --i >= 0;) {
            RobotInfo r = nearby[i];
            if (r.team == us) { if (nFriend < 64) friends[nFriend++] = r; if (r.type == RobotType.HQ) MapState.setHome(r.location); }
            else if (r.team == them) {
                if (nEnemy < 64) enemies[nEnemy++] = r;
                int d = loc.distanceSquaredTo(r.location);
                if (d < nearestEnemyD2) { nearestEnemyD2 = d; nearestEnemy = r; }
                if (r.type == RobotType.HQ) MapState.sightEnemyHQ(r.location);
            } else if (nCow < 8) cows[nCow++] = r;
        }
    }

    /** Is stepping onto l safe for a walker: on the map, not flooded (canMove does NOT check water). */
    protected boolean safeTile(MapLocation l) throws GameActionException {
        return rc.canSenseLocation(l) && !rc.senseFlooding(l);
    }

    protected boolean tryMove(Direction d) throws GameActionException {
        if (d == null || d == Direction.CENTER || !rc.canMove(d)) return false;
        if (!type.canFly() && !safeTile(loc.add(d))) return false;
        rc.move(d); loc = rc.getLocation(); return true;
    }

    /** Build type in the first free direction, preferring the one nearest `toward` (relative tie-break). */
    protected boolean tryBuild(RobotType t, MapLocation toward) throws GameActionException {
        if (!rc.isReady() || rc.getTeamSoup() < t.cost) return false;
        Direction best = null; int bd = 1 << 30;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i];
            if (!rc.canBuildRobot(t, d)) continue;
            MapLocation n = loc.add(d);
            int s = toward == null ? nextInt(64) : n.distanceSquaredTo(toward);
            if (s < bd) { bd = s; best = d; }
        }
        if (best == null) return false;
        rc.buildRobot(t, best);
        Debug.log("@build t=" + t.ordinal() + " at=" + loc.add(best));
        return true;
    }

    /** Step to the free adjacent tile that maximises distance from `threat` (flee). */
    protected boolean fleeFrom(MapLocation threat) throws GameActionException {
        if (!rc.isReady()) return false;
        Direction best = null; int bs = -1;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; if (!rc.canMove(d)) continue;
            MapLocation n = loc.add(d);
            if (!type.canFly() && !safeTile(n)) continue;
            int s = n.distanceSquaredTo(threat);
            if (s > bs) { bs = s; best = d; }
        }
        return best != null && tryMove(best);
    }
}
