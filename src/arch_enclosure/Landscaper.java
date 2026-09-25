package arch_enclosure;

import battlecode.common.*;

/**
 * The enclosure (DESIGN.md, 2026-09-25). No seats: the HQ's ring is the interior and stays at ground level.
 * HOLDER: take the nearest free tile of the SHELL (Chebyshev 2 from the HQ; Chebyshev 3 once the inner shell is
 * full), keep it above the water 60 rounds out, then equalise the lowest adjacent shell tile, digging from the
 * interior (the quarry: ring tiles, never the HQ or a building) or from outside. FEEDER: a landscaper inside a
 * closed shell digs the quarry and piles it on the lowest adjacent shell tile. Flooding spreads only from a
 * flooded neighbour, so a complete shell keeps the interior dry at any elevation, for ever.
 */
public strictfp class Landscaper extends Robot {
    private MapLocation tile;                 // our shell tile, once chosen
    private boolean feeder = false, attacker = false, waiting = false;
    private final MapLocation[] bad = new MapLocation[8]; private int nBad = 0;
    private int digs = 0, deposits = 0, hqDigs = 0, buryDeposits = 0, fed = 0, selfDeps = 0, reclaimed = 0;

    Landscaper(RobotController rc) { super(rc); nav.stallLimit = 30; }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 1) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@wallstat tile=" + tile + " feeder=" + feeder + " attacker=" + attacker + " digs=" + digs + " deps=" + deposits + " self=" + selfDeps + " fed=" + fed + " hqDigs=" + hqDigs + " reclaimed=" + reclaimed);
        MapLocation home = MapState.home;
        if (home == null) { nav.setTarget(null); return; }
        if (attacker) { attack(); return; }
        if (feeder) { feed(home); return; }
        if (tile != null && !tile.equals(loc) && nav.target() == tile && nav.stalled()) { if (nBad < 8) bad[nBad++] = tile; Debug.log("@badtile " + tile); tile = null; }
        // stage 3: a tile we stand beside but cannot climb is a cliff to us: give it up now, not after thirty stalls
        if (tile != null && !tile.equals(loc) && Nav.cheb(loc, tile) == 1 && rc.canSenseLocation(tile) && Math.abs(rc.senseElevation(tile) - rc.senseElevation(loc)) > GameConstants.MAX_DIRT_DIFFERENCE) { if (nBad < 8) bad[nBad++] = tile; Debug.log("@cliff " + tile); tile = null; }
        if (tile != null && !tile.equals(loc) && round % 50 == 0) Debug.log("@walk to=" + tile + " d=" + Nav.cheb(loc, tile) + " here=" + Nav.cheb(loc, home) + " myE=" + rc.senseElevation(loc) + (rc.canSenseLocation(tile) ? " tE=" + rc.senseElevation(tile) : ""));
        if (tile == null && shell(loc)) { tile = loc; Debug.log("@held " + tile + " d=" + Nav.cheb(loc, home) + " (landed)"); }   // stage 6: set down by the elevator (stage 12: at Chebyshev 3 too)
        if (tile == null || (!tile.equals(loc) && occupiedByOther(tile))) tile = pickShell(home);
        if (tile == null) {
            // stage 2: no feeders -- a body inside waits in the yard for a drone to lift it onto the shell; one outside attacks
            if (inside(loc, home)) { if (!waiting) { waiting = true; Debug.log("@yard at=" + loc); }
                // stage 15: wait on the yard tile beside the gate, where the elevator can reach us without coming in
                MapLocation g = gateTile(home);
                if (g != null && Nav.cheb(loc, g) > 1 && rc.isReady()) {   // stage 23: navigate to the nearest free yard tile (one step was not enough: on Prison the waiter was boxed in two tiles from the yard for 2,000 rounds)
                    MapLocation y = null; int yd = 1 << 30;
                    for (int i = 8; --i >= 0;) { MapLocation n = home.add(DIRS[i]); if (!rc.onTheMap(n) || Nav.cheb(n, g) != 1 || occupiedByOther(n)) continue; int d = loc.distanceSquaredTo(n); if (d < yd) { yd = d; y = n; } }
                    if (y != null) { nav.setTarget(y); nav.step(); } }
                return; }
            attacker = true; Debug.log("@attacker shell full"); attack(); return;
        }
        waiting = false;
        if (!loc.equals(tile)) { if (floodDanger() && climb()) return; nav.setTarget(tile); nav.step(); if (loc.equals(tile)) Debug.log("@held " + tile + " d=" + Nav.cheb(tile, home)); return; }
        hold(home);
    }

    private boolean occupiedByOther(MapLocation l) throws GameActionException {
        if (!rc.canSenseLocation(l)) return false;
        RobotInfo r = rc.senseRobotAtLocation(l);
        return r != null && r.ID != id;   // stage 4: anything standing there (a miner too: walkers circled tiles miners stood on)
    }
    private boolean shell(MapLocation l) { int d = Nav.cheb(l, MapState.home); return (d == 2 && isShell(l, MapState.home)) || d == 3; }

    /** The nearest free shell tile we can reach: Chebyshev 2 first; Chebyshev 3 only beside a held Chebyshev-2 tile
     *  (the outer shell grows from the inner one). Within 3 of our own elevation, or of the HQ's early on. */
    private MapLocation pickShell(MapLocation home) throws GameActionException {
        MapLocation best = null; int bd = 1 << 30; int myE = rc.senseElevation(loc);
        for (int ring = 2; ring <= 3 && best == null; ring++) {
            for (int dx = -ring; dx <= ring; dx++) for (int dy = -ring; dy <= ring; dy++) {
                if (Math.max(Math.abs(dx), Math.abs(dy)) != ring) continue;
                MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                if (!rc.onTheMap(t)) continue;
                boolean b = false; for (int k = nBad; --k >= 0;) if (bad[k].equals(t)) { b = true; break; }
                if (b) continue;
                if (ring == 2 && t.equals(gateTile(home))) continue;   // stage 8: the gate is the drones' way in and out
                if (ring == 2 && !isShell(t, home)) continue;   // stage 26: not the edge side of a corner enclosure
                if (ring == 3 && gateTile(home) != null && Nav.cheb(t, gateTile(home)) <= 1) continue;
                if (ring == 3 && Nav.cheb(loc, home) <= 1) continue;   // stage 24: not from inside (the shell is in the way; Prison's waiters walked at an outer tile for 90 rounds)   // stage 23: the three outer tiles beside the gate are the elevator's approach (holders set down there sealed the gate: no lift after r1328)
                if (rc.canSenseLocation(t)) {
                    if (rc.senseFlooding(t)) continue;
                    if (Math.abs(rc.senseElevation(t) - myE) > C.SHELL_CLIMB) continue;   // a raised tile nobody holds is a cliff to us
                    if (occupiedByOther(t)) continue;
                    if (ring == 3 && !besideHeld(t, home)) continue;
                }
                int d = loc.distanceSquaredTo(t) + nextInt(2);
                if (d < bd) { bd = d; best = t; }
            }
        }
        return best;
    }
    private MapLocation gateTile(MapLocation home) {
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.DESIGN_SCHOOL && Nav.cheb(f.location, home) == 1) { MapState.gate = new MapLocation(home.x + 2 * (f.location.x - home.x), home.y + 2 * (f.location.y - home.y)); return MapState.gate; } }
        return MapState.gate;   // stage 26: cached once seen
    }
    private boolean besideHeld(MapLocation t, MapLocation home) throws GameActionException {
        for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (Nav.cheb(n, home) != 2 || !rc.canSenseLocation(n)) continue;
            RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type == RobotType.LANDSCAPER && r.team == us) return true; }
        return false;
    }

    /** On our shell tile. 1 dig the HQ out; 2 bury an adjacent enemy building; 3 keep our tile above the water 60
     *  rounds out; 4 equalise the lowest adjacent shell tile below our own; 5 dig: the quarry first, then outside. */
    private void hold(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && loc.isAdjacentTo(home) && rc.canDigDirt(loc.directionTo(home))) { rc.digDirt(loc.directionTo(home)); hqDigs++; digs++; return; }
        if (buryEnemy()) return;
        int myE = rc.senseElevation(loc); int need = (int) waterLevel(round + 60) + 2; int myD = Nav.cheb(loc, home);
        if (rc.getDirtCarrying() > 0) {
            if (myE < need && rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; selfDeps++; return; }
            // stage 25: an outer holder feeds the inner shell -- the lowest inner tile beside it -- and keeps its own tile
            // only just above the water (14 outer holders on RandomSoup1 raised their own tiles to 300-500 and drowned at
            // r2700-2900 with the inner shell at 0.5 a tile a round: 14,000 dirt that was 875 an inner tile)
            if (myD == 3) { Direction bestD = null; int be = Integer.MAX_VALUE;
                for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, home) != 2 || !rc.canSenseLocation(n) || !rc.canDepositDirt(d)) continue;
                    RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                    int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
                if (bestD != null) { rc.depositDirt(bestD); deposits++; fed++; return; } }
            // equalise: the lowest shell tile beside us at our distance or nearer, held or not (stage 6: a hole in the
            // inner shell floods the interior; tiles at our distance are never our dig source, so there is no loop)
            Direction bestD = null; int be = myE - C.SHELL_SLACK;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!shell(n) || Nav.cheb(n, home) > myD || !rc.canSenseLocation(n) || !rc.canDepositDirt(d)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
            if (bestD != null) { rc.depositDirt(bestD); deposits++; fed++; return; }
            // reclaim: with margin to spare, raise the highest outer tile beside us that is not yet dry land for a newcomer
            // stage 18: reclaim every turn until one outer tile beside us is dry land (the elevator had nowhere to set bodies down:
            // 172 waits for 10 lifts), then every third turn
            boolean dryOuter = false; for (int i = 8; --i >= 0;) { MapLocation n = loc.add(DIRS[i]); if (Nav.cheb(n, home) == myD + 1 && Nav.cheb(n, home) <= 3 && rc.canSenseLocation(n) && !rc.senseFlooding(n) && rc.senseElevation(n) >= (int) waterLevel(round + 60) + 2) { dryOuter = true; break; } }
            if (round < C.RECLAIM_UNTIL && myE >= need + C.RECLAIM_MARGIN && (round % 3 == 0 || !dryOuter)) { bestD = null; int bh = Integer.MIN_VALUE; int dry = (int) waterLevel(round + 60) + 2;   // stage 23: not after RECLAIM_UNTIL
                for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, home) != myD + 1 || Nav.cheb(n, home) > 3 || !rc.canSenseLocation(n) || !rc.canDepositDirt(d)) continue;
                    if (rc.senseRobotAtLocation(n) != null) continue; int e = rc.senseElevation(n); if (e >= dry || e < dry - C.RECLAIM_DEPTH) continue; if (e > bh) { bh = e; bestD = d; } }   // stage 23: never a pit
                if (bestD != null) { rc.depositDirt(bestD); deposits++; reclaimed++; return; } }
            if (rc.canDepositDirt(Direction.CENTER)) { rc.depositDirt(Direction.CENTER); deposits++; selfDeps++; return; }
        }
        Direction d = digSource(home);
        if (d != null) { rc.digDirt(d); digs++; }
    }

    /** Inside a closed shell: dig the quarry, pile it on the lowest adjacent shell tile (or dig the HQ out). */
    private void feed(MapLocation home) throws GameActionException {
        if (!rc.isReady()) return;
        if (hqInfo != null && hqInfo.dirtCarrying > 0 && loc.isAdjacentTo(home) && rc.canDigDirt(loc.directionTo(home))) { rc.digDirt(loc.directionTo(home)); hqDigs++; digs++; return; }
        if (rc.getDirtCarrying() > 0) {
            Direction bestD = null; int be = Integer.MAX_VALUE;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (!shell(n) || !rc.canSenseLocation(n) || !rc.canDepositDirt(d)) continue;
                RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type.isBuilding()) continue;
                int e = rc.senseElevation(n); if (e < be) { be = e; bestD = d; } }
            if (bestD != null) { rc.depositDirt(bestD); deposits++; fed++; return; }
        }
        Direction d = digSource(home);
        if (d != null) { rc.digDirt(d); digs++; return; }
        // nothing adjacent to dig or feed: step to another interior tile
        for (int i = 8; --i >= 0;) { Direction dd = DIRS[i]; MapLocation n = loc.add(dd); if (Nav.cheb(n, home) == 1 && rc.canMove(dd) && !rc.senseFlooding(n)) { rc.move(dd); loc = rc.getLocation(); return; } }
    }

    /** Where to dig: a quarry tile (Chebyshev 1, not the HQ, no building), else an outside tile (beyond the shell),
     *  else a shell tile far above the water that no friend holds; never under a building of ours. */
    private Direction digSource(MapLocation home) throws GameActionException {
        Direction best = null; int bs = Integer.MAX_VALUE;
        for (int i = 8; --i >= 0;) {
            Direction d = DIRS[i]; MapLocation n = loc.add(d);
            if (!rc.onTheMap(n) || n.equals(home) || !rc.canDigDirt(d)) continue;
            RobotInfo r = rc.canSenseLocation(n) ? rc.senseRobotAtLocation(n) : null;
            if (r != null && r.team == us && (r.type.isBuilding() || r.type == RobotType.LANDSCAPER)) continue;   // never a building of ours, never under a holder
            int cd = Nav.cheb(n, home); int e = rc.senseElevation(n);
            int s;
            if (cd <= 1) continue;   // stage 12: no quarry at all -- every ring tile dug to -9 was a vaporator site lost (a building needs its tile within 3 of the builder), and the flooded outside is a source without end
            else if (cd > Nav.cheb(loc, home)) s = e;                    // outside: lowest first (under water is fine)
            else if (shell(n) && e > waterLevel(round + 200) + C.SHELL_SLACK + 3) s = 5000 - e;   // a shell tile with margin to spare, unheld
            else continue;
            if (r != null) s += 100;                                    // prefer empty tiles
            if (s < bs) { bs = s; best = d; }
        }
        if (best == null && rc.canDigDirt(Direction.CENTER) && rc.senseElevation(loc) > waterLevel(round + 200) + 3) best = Direction.CENTER;
        return best;
    }

    /** Stage 5: a tile beside the school or the center is their yard -- spawns and lifts need it at ground level. */
    private boolean besideSpawner(MapLocation n) {
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if ((f.type == RobotType.DESIGN_SCHOOL || f.type == RobotType.FULFILLMENT_CENTER) && Nav.cheb(f.location, n) <= 1) return true; }
        return false;
    }

    private boolean buryEnemy() throws GameActionException {
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.isBuilding() && loc.isAdjacentTo(e.location) && rc.getDirtCarrying() > 0 && rc.canDepositDirt(loc.directionTo(e.location))) { rc.depositDirt(loc.directionTo(e.location)); buryDeposits++; return true; } }
        return false;
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
        MapLocation g = MapState.enemyHQ != null ? MapState.enemyHQ : MapState.enemyHQGuess();
        if (g != null) { nav.setTarget(g); nav.step(); }
    }
}
