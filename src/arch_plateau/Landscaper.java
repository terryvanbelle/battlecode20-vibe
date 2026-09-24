package arch_plateau;

import battlecode.common.*;

/**
 * Landscaper, the plateau roles (2026-09-24, DESIGN.md "The plateau"). A landscaper holds one tile at
 * Chebyshev 1..TIER_MAX from the HQ: the nearest free one it can reach and keep dry, ring first. It
 * keeps its own tile above the water, then feeds INWARD -- a seat raises the lowest of itself and its
 * ring neighbours, a tier-2 holder the lowest adjacent exposed ring tile, a tier-3 holder the lowest
 * adjacent tier-2 tile -- and digs outward, never under a friend unless that tile has FEED_MARGIN to
 * spare. Only with no tile left does it attack. A held tile that floods is resurfaced from next door.
 */
public strictfp class Landscaper extends Robot {
    private MapLocation post; private int tier = 0;
    private boolean attacker = false;
    private final MapLocation[] bad = new MapLocation[12]; private int nBad = 0;
    private int digs = 0, deposits = 0, fed = 0, hqDigs = 0, buryDeposits = 0, borrowed = 0, resurfaced = 0;

    Landscaper(RobotController rc) { super(rc); nav.stallLimit = 30; }

    @Override protected void turn() throws GameActionException {
        int b0 = Clock.getBytecodeNum();
        try { turn2(); } finally { int b1 = Clock.getBytecodeNum(); if (b1 > 8500) Debug.log("@bcprof start=" + b0 + " total=" + b1 + " tier=" + tier + " atPost=" + (post != null && post.equals(loc))); }
    }

    private void turn2() throws GameActionException {
        sense(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat tier=" + tier + " post=" + post + " attacker=" + attacker + " digs=" + digs + " deps=" + deposits + " fed=" + fed + " borrow=" + borrowed + " resurf=" + resurfaced + " hqDigs=" + hqDigs + " bury=" + buryDeposits + " elev=" + rc.senseElevation(loc));
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        if (attacker) { attack(); return; }
        if (post == null || (!post.equals(loc) && occupiedByFriend(post))) { post = pickTile(home); if (post == null) { attacker = true; Debug.log("@attacker no tile"); attack(); return; } tier = Nav.cheb(post, home); Debug.log("@hold t=" + tier + " at=" + post); }
        if (!loc.equals(post)) { approach(home); return; }
        hold(home);
    }

    /** Do we stand on a dry tile adjacent to l? (A flooded tile is resurfaced only from next door.) */
    private boolean holdsDryNeighbour(MapLocation l) throws GameActionException { return loc.isAdjacentTo(l) && !rc.senseFlooding(loc) && post != null && post.equals(loc); }
    /** A friendly landscaper nearer to l than we are, and not standing on a tile of its own (so it is heading somewhere): leave l to it. */
    private boolean contested(MapLocation l) {
        int d = loc.distanceSquaredTo(l);
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.LANDSCAPER) continue; int fd = f.location.distanceSquaredTo(l); if (fd < d && Nav.cheb(f.location, MapState.home) > C.TIER_MAX) return true; }
        return false;
    }
    private int exposedRing = -1;
    private int exposedRingTiles(MapLocation home) { if (exposedRing < 0) { exposedRing = 0; for (int k = 8; --k >= 0;) { MapLocation t = home.add(DIRS[k]); if (rc.onTheMap(t) && exposed(t)) exposedRing++; } } return exposedRing; }
    /** Ring tiles held by landscapers of ours, as seen from here. */
    private int ringCount() { int n = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.LANDSCAPER && onRing(friends[i].location)) n++; if (onRing(loc)) n++; return n; }

    private boolean nextToOurBuilding(MapLocation l) {
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type.isBuilding() && f.type != RobotType.HQ && f.location.isAdjacentTo(l)) return true; }
        return false;
    }

    private boolean occupiedByFriend(MapLocation l) throws GameActionException {
        if (!rc.canSenseLocation(l)) return false;
        RobotInfo r = rc.senseRobotAtLocation(l);
        return r != null && r.ID != id && r.team == us && (r.type == RobotType.LANDSCAPER || r.type.isBuilding());
    }

    /** The nearest free tile at Chebyshev 1..TIER_MAX that we can reach and keep dry; ring tiles first, then the tier whose inner neighbour is lowest. */
    private MapLocation pickTile(MapLocation home) throws GameActionException {
        MapLocation best = null; long bs = Long.MAX_VALUE; int myE = rc.senseElevation(loc); double water = waterLevel(round);
        for (int dx = -C.TIER_MAX; dx <= C.TIER_MAX; dx++) for (int dy = -C.TIER_MAX; dy <= C.TIER_MAX; dy++) {
            int t = Math.max(Math.abs(dx), Math.abs(dy)); if (t == 0) continue;
            MapLocation l = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(l)) continue;
            if (t == 1 && !exposed(l)) continue;
            boolean isBad = false; for (int k = nBad; --k >= 0;) if (bad[k].equals(l)) { isBad = true; break; }
            if (isBad) continue;
            if (t == 3 && ringCount() < exposedRingTiles(home) - 1) continue;   // tier 3 opens once the ring is seated
            long s = (long) t * 1000000L;
            if (rc.canSenseLocation(l)) {
                int e = rc.senseElevation(l);
                if (rc.senseFlooding(l)) { if (t == 1 || e < water - C.SHALLOW || !holdsDryNeighbour(l)) continue; s += 300000; }   // shallow, and only from a dry tile we already hold next to it
                else if (Math.abs(e - myE) > GameConstants.MAX_DIRT_DIFFERENCE && !l.equals(loc)) continue;   // a cliff or a raised seat: not for us
                if (contested(l)) continue;   // another landscaper of ours is closer to it and not yet holding anything: the claim is theirs
                RobotInfo r = rc.senseRobotAtLocation(l);
                if (r != null && r.ID != id && (r.type.isBuilding() || (r.type == RobotType.LANDSCAPER && r.team == us))) continue;
                if (t > 1 && nextToOurBuilding(l)) continue;   // leave the school, the center and the rest their spawn room (gate 18: Constriction had two landscapers all game)
                if (t > 1) {   // prefer the tile whose inner neighbour is lowest: dirt goes where the wall is weakest
                    int lowest = Integer.MAX_VALUE;
                    for (int i = 8; --i >= 0;) { MapLocation n = l.add(DIRS[i]); if (Nav.cheb(n, home) == t - 1 && !n.equals(home) && rc.canSenseLocation(n) && (t > 2 || exposed(n))) lowest = Math.min(lowest, rc.senseElevation(n)); }
                    if (lowest == Integer.MAX_VALUE) continue;
                    s += (long) lowest * 100;
                }
            }
            s += loc.distanceSquaredTo(l) + nextInt(2);
            if (s < bs) { bs = s; best = l; }
        }
        return best;
    }

    /** Walk to the held tile; resurface it from next door if it is under water; strike it off if the walk stalls. */
    private void approach(MapLocation home) throws GameActionException {
        if (rc.canSenseLocation(post) && rc.senseFlooding(post) && loc.isAdjacentTo(post)) {
            if (!rc.isReady()) return;
            Direction d = loc.directionTo(post);
            if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(d)) { rc.depositDirt(d); deposits++; resurfaced++; return; }
            if (digOutward(home, Nav.cheb(loc, home))) return;
            return;
        }
        if (floodDanger() && climb()) return;
        if (nav.target() == post && nav.stalled()) { if (nBad < 12) bad[nBad++] = post; Debug.log("@badtile " + post); post = null; return; }
        nav.setTarget(post); nav.step(); if (loc.equals(post)) Debug.log("@posted t=" + tier + " at=" + post);
    }

    /** At the held tile: HQ first, enemies next, own tile above the water, then feed inward, then dig outward. */
    private void hold(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        if (tier == 1 && hqInfo != null && hqInfo.dirtCarrying > 0 && rc.canDigDirt(loc.directionTo(home))) { rc.digDirt(loc.directionTo(home)); hqDigs++; digs++; Debug.log("@hqdig buried=" + hqInfo.dirtCarrying); return; }
        if (rc.getDirtCarrying() > 0) {
            for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return; } }
            int myE = rc.senseElevation(loc);
            if (tier > 1 && myE < waterLevel(round + 60) + 2 && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
            // feed inward
            Direction bestD = null; int be = Integer.MAX_VALUE;
            if (tier == 1 && exposed(loc)) { bestD = Direction.CENTER; be = myE; }
            for (int i = 8; --i >= 0;) {
                Direction d = DIRS[i]; MapLocation n = loc.add(d);
                if (Nav.cheb(n, home) != tier - (tier == 1 ? 0 : 1) || n.equals(home) || !rc.canSenseLocation(n)) continue;
                if (tier <= 2 && !exposed(n)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                int e = rc.senseElevation(n); if (e < be - (tier == 1 ? C.WALL_LEVEL_SLACK : 0)) { be = e; bestD = d; }
            }
            if (bestD != null && rc.canDepositDirt(bestD)) { rc.depositDirt(bestD); deposits++; if (bestD != Direction.CENTER) fed++; return; }
            if (rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; return; }
            return;
        }
        if (digOutward(home, tier)) return;
        if (tier == 1) {   // a corner seat with nothing outside: borrow from the tallest ring neighbour
            int bh = rc.senseElevation(loc) + C.WALL_BORROW_MARGIN; Direction bestD = null;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!onRing(n) || !rc.canDigDirt(d)) continue; int e = rc.senseElevation(n); if (e > bh) { bh = e; bestD = d; } }
            if (bestD != null) { rc.digDirt(bestD); digs++; borrowed++; return; }
        }
        if (tier == C.TIER_MAX && rc.canDigDirt(Direction.CENTER) && rc.senseElevation(loc) > waterLevel(round + 200) + 3) { rc.digDirt(Direction.CENTER); digs++; }
    }

    /** Dig the lowest adjacent tile farther out than `from` that is not the HQ, a ring tile or a building; under a friend only from its margin. */
    private boolean digOutward(MapLocation home, int from) throws GameActionException {
        Direction bestD = null; long be = Long.MAX_VALUE; double keep = waterLevel(round + 60) + 2 + C.FEED_MARGIN;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || n.equals(home) || onRing(n) || Nav.cheb(n, home) <= from || !rc.canDigDirt(d)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.type.isBuilding()) continue;
            int e = rc.senseElevation(n);
            long s = e;
            if (r != null && r.team == us) { if (e < keep) continue; s = 500 - e; }   // a friend's tile: only from the margin, tallest first
            else if (r != null) s += 1000;
            if (s < be) { be = s; bestD = d; }
        }
        if (bestD == null) return false;
        rc.digDirt(bestD); digs++; return true;
    }

    private void attack() throws GameActionException {
        if (floodDanger() && climb()) return;
        RobotInfo tgt = null;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && (tgt == null || e.type == RobotType.HQ || loc.distanceSquaredTo(e.location) < loc.distanceSquaredTo(tgt.location))) tgt = e; }
        if (tgt != null && rc.isReady()) {
            if (loc.isAdjacentTo(tgt.location)) {
                Direction d = loc.directionTo(tgt.location);
                if (rc.getDirtCarrying() > 0 && rc.canDepositDirt(d)) { rc.depositDirt(d); buryDeposits++; return; }
                for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (n.equals(tgt.location) || !rc.canDigDirt(dd)) continue; RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null; if (r != null && r.type.isBuilding()) continue; rc.digDirt(dd); digs++; return; }
                return;
            }
            nav.setTarget(tgt.location); nav.step(); return;
        }
        if (round % 50 == 0 && MapState.home != null) { post = pickTile(MapState.home); if (post != null) { attacker = false; tier = Nav.cheb(post, MapState.home); return; } }   // a tile freed up: go home
        MapLocation g = MapState.enemyHQGuess(); if (g == null) g = MapState.center();
        nav.setTarget(g); nav.step();
    }
}
