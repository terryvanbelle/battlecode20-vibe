package cand40c;

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
    protected RobotInfo hqInfo;             // our HQ if in sight (its dirtCarrying is how buried it is)
    protected boolean avoidRing = false;    // miners/drones: never step onto the wall ring (Iteration 2: miners fleeing the flood took the landscapers' seats)
    protected boolean ringSeen = false;     // a friendly landscaper is on a ring tile

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

    /** Once, before the first turn: find home (the HQ is within 24 r2 of every building we make near it). */
    protected void init() throws GameActionException {
        RobotInfo[] adj = rc.senseNearbyRobots(-1, us);
        for (int i = adj.length; --i >= 0;) if (adj[i].type == RobotType.HQ) { MapState.setHome(adj[i].location); break; }
        probeEdges();
    }

    /** One turn of this robot's logic. */
    protected abstract void turn() throws GameActionException;

    // ---- shared helpers ----

    /** Sense everything once and bucket it. 100 + ~12 per robot bytecodes. */
    protected void sense() {
        nearby = rc.senseNearbyRobots();
        nEnemy = nFriend = nCow = 0; nearestEnemy = null; nearestEnemyD2 = 1 << 30; hqInfo = null; ringSeen = false;
        for (int i = nearby.length; --i >= 0;) {
            RobotInfo r = nearby[i];
            if (r.team == us) { if (nFriend < 64) friends[nFriend++] = r; if (r.type == RobotType.HQ) { MapState.setHome(r.location); hqInfo = r; } else if (r.type == RobotType.LANDSCAPER && onRing(r.location)) ringSeen = true; }
            else if (r.team == them) {
                if (nEnemy < 64) enemies[nEnemy++] = r;
                int d = loc.distanceSquaredTo(r.location);
                if (d < nearestEnemyD2) { nearestEnemyD2 = d; nearestEnemy = r; }
                if (r.type == RobotType.HQ) { if (MapState.enemyHQ == null) Debug.log("@sight enemyHQ=" + r.location); MapState.sightEnemyHQ(r.location); }
            } else if (nCow < 8) cows[nCow++] = r;
        }
        try { shareSightings(); } catch (GameActionException e) { }
        // Iteration 7: a symmetry hypothesis whose image is in sight and holds no enemy HQ is dead (the citadel's raid flew to a wrong image)
        if (MapState.enemyHQ == null && MapState.symCount() > 1) { MapLocation g = MapState.enemyHQGuess(); if (g != null && rc.canSenseLocation(g)) { MapState.pruneEmpty(g); Debug.log("@prune empty=" + g + " sym=" + MapState.sym); } }
    }

    /** Read last round's block and absorb what our team posted. 100 bytecodes + ~30 per message. */
    protected void readBlock() throws GameActionException {
        if (round < 2) return;
        Transaction[] block = rc.getBlock(round - 1);
        for (int i = block.length; --i >= 0;) {
            int[] m = block[i].getMessage();
            if (!Comms.ours(m, round - 1, us)) continue;
            switch (m[0]) {
                case Comms.HQ_LOC: MapState.setHome(new MapLocation(m[1], m[2])); break;
                case Comms.ENEMY_HQ: MapState.sightEnemyHQ(new MapLocation(m[1], m[2])); break;
                case Comms.MAP_ORIGIN: if (!MapState.originKnown()) { MapState.minX = m[1]; MapState.minY = m[2]; } break;
                default: break;
            }
        }
    }

    /** Post a message for 1 soup if we can. */
    protected boolean post(int[] m) throws GameActionException {
        if (!rc.canSubmitTransaction(m, 1)) return false;
        rc.submitTransaction(m, 1); return true;
    }

    /**
     * Find the map origin by probing rc.onTheMap at the sensing radius (5 bytecodes a probe):
     * when a probe falls off the map, walk inward to the exact edge. Needs at most one edge per
     * axis because the size is known.
     */
    protected void probeEdges() {
        if (MapState.originKnown()) return;
        int r = (int) Math.sqrt(type.sensorRadiusSquared);
        if (MapState.minX < 0) {
            if (!rc.onTheMap(new MapLocation(loc.x - r, loc.y))) { int x = loc.x - r; while (!rc.onTheMap(new MapLocation(x, loc.y))) x++; MapState.edgeFound(0, x); }
            else if (!rc.onTheMap(new MapLocation(loc.x + r, loc.y))) { int x = loc.x + r; while (!rc.onTheMap(new MapLocation(x, loc.y))) x--; MapState.edgeFound(1, x); }
        }
        if (MapState.minY < 0) {
            if (!rc.onTheMap(new MapLocation(loc.x, loc.y - r))) { int y = loc.y - r; while (!rc.onTheMap(new MapLocation(loc.x, y))) y++; MapState.edgeFound(2, y); }
            else if (!rc.onTheMap(new MapLocation(loc.x, loc.y + r))) { int y = loc.y + r; while (!rc.onTheMap(new MapLocation(loc.x, y))) y--; MapState.edgeFound(3, y); }
        }
        if (MapState.originKnown()) { Debug.log("@origin x=" + MapState.minX + " y=" + MapState.minY); postOrigin = true; }
    }
    protected boolean postOrigin = false, postedEnemyHQ = false;
    /** Iteration 7: the prober posts the origin once, the sighter the enemy HQ once; the HQ re-posts both every 100 rounds. */
    protected void shareSightings() throws GameActionException {
        if (postOrigin && MapState.originKnown() && type != RobotType.HQ) postOrigin = !post(Comms.make(Comms.MAP_ORIGIN, round, us, MapState.minX, MapState.minY));
        if (!postedEnemyHQ && MapState.enemyHQ != null && type != RobotType.HQ) postedEnemyHQ = post(Comms.make(Comms.ENEMY_HQ, round, us, MapState.enemyHQ.x, MapState.enemyHQ.y));
    }

    /** Remember the terrain within Chebyshev 1 (9 tiles) for symmetry pruning; ~60 bytecodes a tile. */
    protected void observeTerrain() throws GameActionException {
        if (!MapState.originKnown() || MapState.symKnown() >= 0) return;
        for (int dx = -1; dx <= 1; dx++) for (int dy = -1; dy <= 1; dy++) {
            MapLocation l = new MapLocation(loc.x + dx, loc.y + dy);
            if (!rc.canSenseLocation(l)) continue;
            MapState.observe(l, rc.senseElevation(l), rc.senseFlooding(l));
        }
    }

    /** The engine's water level at round r. */
    public static double waterLevel(int r) { return Math.exp(0.0028 * r - 1.38 * Math.sin(0.00157 * r - 1.73) + 1.38 * Math.sin(-1.73)) - 1; }

    /** Is stepping onto l safe for a walker: sensed, not flooded (canMove does NOT check water). */
    protected boolean safeTile(MapLocation l) throws GameActionException {
        if (avoidRing && onRing(l)) return false;
        return rc.canSenseLocation(l) && !rc.senseFlooding(l);
    }
    /** May this robot stand on l at all (ring rule for flyers too: a drone parked on a seat blocks it). */
    protected boolean allowedTile(MapLocation l) { return !(avoidRing && onRing(l)); }

    /** Will my own tile be under water within FLOOD_LOOKAHEAD rounds, given a flooded neighbour? */
    protected boolean floodDanger() throws GameActionException {
        if (type.canFly()) return false;
        if (rc.senseElevation(loc) > waterLevel(round + C.FLOOD_LOOKAHEAD)) return false;
        for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (rc.canSenseLocation(n) && rc.senseFlooding(n)) return true; }
        return false;
    }

    /** Step to the highest safe adjacent tile. */
    protected boolean climb() throws GameActionException {
        if (!rc.isReady()) return false;
        Direction best = null; int be = Integer.MIN_VALUE;
        for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (!rc.canMove(d)) continue; MapLocation n = loc.add(d); if (!safeTile(n)) continue; int e = rc.senseElevation(n); if (e > be) { be = e; best = d; } }
        if (best == null) return false;
        rc.move(best); loc = rc.getLocation(); Debug.log("@climb to=" + loc + " e=" + be); return true;
    }

    protected boolean tryMove(Direction d) throws GameActionException {
        if (d == null || d == Direction.CENTER || !rc.canMove(d)) return false;
        if (!allowedTile(loc.add(d)) || (!type.canFly() && !safeTile(loc.add(d)))) return false;
        rc.move(d); loc = rc.getLocation(); return true;
    }

    /** Build type in the free direction nearest `toward` (relative tie-break; random when null). */
    protected boolean tryBuild(RobotType t, MapLocation toward) throws GameActionException {
        if (!rc.isReady() || rc.getTeamSoup() < t.cost) return false;
        Direction best = null; int bd = 1 << 30;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i];
            if (!rc.canBuildRobot(t, d)) continue;
            MapLocation n = loc.add(d);
            if (t != RobotType.DELIVERY_DRONE && rc.senseFlooding(n)) continue;
            int s = toward == null ? nextInt(64) : n.distanceSquaredTo(toward);
            if (s < bd) { bd = s; best = d; }
        }
        if (best == null) return false;
        rc.buildRobot(t, best);
        Debug.log("@build t=" + t.ordinal() + " at=" + loc.add(best) + " soup=" + rc.getTeamSoup());
        return true;
    }

    /** Step to the free adjacent tile that maximises distance from `threat` (flee). */
    protected boolean fleeFrom(MapLocation threat) throws GameActionException {
        if (!rc.isReady()) return false;
        Direction best = null; int bs = -1;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; if (!rc.canMove(d)) continue;
            MapLocation n = loc.add(d);
            if (!allowedTile(n) || (!type.canFly() && !safeTile(n))) continue;
            int s = n.distanceSquaredTo(threat);
            if (s > bs) { bs = s; best = d; }
        }
        return best != null && tryMove(best);
    }

    /** Iteration 29: an enemy design school or landscaper near our HQ early (poortho buries the HQ by r120). */
    protected boolean rushSeen() {
        if (round >= C.RUSH_UNTIL || MapState.home == null) return false;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i];
            if ((e.type == RobotType.DESIGN_SCHOOL || e.type == RobotType.LANDSCAPER) && e.location.distanceSquaredTo(MapState.home) <= C.RUSH_D2) return true; }
        return false;
    }
    /** Is l one of the 8 tiles around our HQ (the wall ring)? */
    protected static boolean onRing(MapLocation l) { return MapState.home != null && Nav.cheb(l, MapState.home) == 1; }
    /** Can the flood reach ring tile l at all: does it touch any on-map tile outside the ring? A tile enclosed by the
     *  other ring tiles, the HQ and the map edge never floods (the flood spreads only from a flooded neighbour), so
     *  dirt spent on it is wasted -- a quarter of ours was, on MoreCowbell. */
    protected boolean exposed(MapLocation l) {
        for (int i = 8; --i >= 0;) { MapLocation n = l.add(DIRS[i]); if (!n.equals(MapState.home) && !onRing(n) && rc.onTheMap(n)) return true; }
        return false;
    }
}
