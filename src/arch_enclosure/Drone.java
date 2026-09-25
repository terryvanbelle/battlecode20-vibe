package arch_enclosure;

import battlecode.common.*;

/**
 * The enclosure's drone: the ELEVATOR and the guard. A landscaper waiting in the yard (a ring tile) cannot climb
 * onto a shell tile once the shell stands 3 above the ground; a drone lifts it and drops it on the nearest free
 * shell tile (Chebyshev 2, then 3 beside a held one). Otherwise it patrols Chebyshev 3-4 around the HQ, lifting
 * anything of the enemy's within the box and drowning it, and never enters the ring or the circle except on the
 * way to a pickup (a hovering drone occupies its tile).
 */
public strictfp class Drone extends Robot {
    private MapLocation water, patrol, liftTarget;
    private boolean holdingFriend = false, chasing = false;
    private int pickups = 0, drops = 0, lifts = 0;

    Drone(RobotController rc) { super(rc); avoidRing = true; }
    @Override protected boolean allowedTile(MapLocation l) { MapLocation h = MapState.home; if (h == null) return true; int d = Nav.cheb(l, h); return d >= 3 || (d <= 2 && (chasing || holdingFriend)); }

    @Override protected void turn() throws GameActionException {
        sense(); if (round % 3 == 2) readBlock(); probeEdges();
        if (round % 100 == 0) Debug.log("@dronestat pickups=" + pickups + " drops=" + drops + " lifts=" + lifts + " holding=" + rc.isCurrentlyHoldingUnit());
        if (water == null || round % 5 == 0) { MapLocation[] near = nearWater(); if (near != null) water = near[0]; }
        MapLocation home = MapState.home;
        MapLocation gun = null; int gd = 1 << 30;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (e.type.canShoot()) { int d = loc.distanceSquaredTo(e.location); if (d < gd) { gd = d; gun = e.location; } } }
        if (gun != null && gd <= 24 && !holdingFriend && fleeFrom(gun)) return;
        // the elevator, carrying: drop on the target shell tile (or any free shell tile beside us if the target is gone)
        if (rc.isCurrentlyHoldingUnit() && holdingFriend) {
            if (liftTarget != null && (!rc.canSenseLocation(liftTarget) || rc.isLocationOccupied(liftTarget))) liftTarget = null;
            if (liftTarget == null) liftTarget = freeShell(home);
            if (liftTarget == null) liftTarget = outsideTile(home);
            if (liftTarget == null) { for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, home) >= 1 && rc.canDropUnit(d) && !rc.senseFlooding(n)) { rc.dropUnit(d); holdingFriend = false; drops++; Debug.log("@lift-drop anywhere at=" + n); return; } } nav.setTarget(home); nav.step(); return; }
            if (loc.isAdjacentTo(liftTarget)) { Direction d = loc.directionTo(liftTarget); if (rc.canDropUnit(d)) { rc.dropUnit(d); holdingFriend = false; lifts++; Debug.log("@lift to=" + liftTarget + " d=" + Nav.cheb(liftTarget, home)); liftTarget = null; return; } }
            nav.setTarget(liftTarget); nav.step(); return;
        }
        if (rc.isCurrentlyHoldingUnit()) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // stage 10: a miner trapped inside (not the builder: the one beside a building it is building for) is lifted out to
        // the nearest free dry tile at Chebyshev 3 or more -- it was born after the shell closed and stands on the yard
        if (home != null && !rc.isCurrentlyHoldingUnit()) {
            RobotInfo m = null; int md = 1 << 30;
            MapLocation sch = null; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DESIGN_SCHOOL && Nav.cheb(friends[i].location, home) == 1) sch = friends[i].location;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.MINER || Nav.cheb(f.location, home) > 1 || sch == null || Nav.cheb(f.location, sch) > 1) continue; int d = loc.distanceSquaredTo(f.location); if (d < md) { md = d; m = f; } }   // stage 14: only a miner on the yard (the builder never stands there)
            if (m != null) {
                MapLocation gate = gate(home);
                if (rc.canPickUpUnit(m.ID)) { rc.pickUpUnit(m.ID); holdingFriend = true; liftTarget = outsideTile(home); Debug.log("@lift-miner id=" + m.ID + " to=" + liftTarget); return; }
                chasing = true; nav.setTarget(gate != null ? gate : m.location); nav.step(); chasing = false; return;
            }
        }
        // the elevator, empty: a landscaper of ours in the yard with a free shell tile to go to
        if (home != null) {
            RobotInfo w = null; int wd = 1 << 30;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.LANDSCAPER || Nav.cheb(f.location, home) != 1) continue; int d = loc.distanceSquaredTo(f.location); if (d < wd) { wd = d; w = f; } }
            if (w != null) {
                MapLocation t = freeShell(home);
                if (t != null) {
                    holdingFriend = true;   // set before the move so allowedTile lets us into the circle
                    if (rc.canPickUpUnit(w.ID)) { rc.pickUpUnit(w.ID); liftTarget = t; pickups++; Debug.log("@lift-up id=" + w.ID + " for=" + t); return; }
                    MapLocation gate = gate(home); holdingFriend = false; chasing = true; nav.setTarget(gate != null ? gate : w.location); nav.step(); chasing = false; return;
                }
            }
        }
        // stage 8: a drone at rest occupies its tile -- on the shell it makes a hole nobody can hold. Out to Chebyshev 3 first.
        if (home != null && Nav.cheb(loc, home) <= 2) {   // stage 9: to the nearest free on-map tile at Chebyshev 3 (a corner HQ has no tile straight out)
            MapLocation out = null; int od = 1 << 30;
            for (int dx = -3; dx <= 3; dx++) for (int dy = -3; dy <= 3; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 3) continue; MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                if (!rc.onTheMap(t) || (rc.canSenseLocation(t) && rc.isLocationOccupied(t))) continue; int d = loc.distanceSquaredTo(t); if (d < od) { od = d; out = t; } }
            if (out != null) { chasing = true; nav.setTarget(out); boolean moved = nav.step(); chasing = false; if (moved) return; }
        }
        // the guard: anything of theirs within the box, landscapers on the ring or beside the HQ first
        RobotInfo tgt = null; long bs = Long.MAX_VALUE;
        for (int i = nEnemy; --i >= 0;) { RobotInfo e = enemies[i]; if (!e.type.canBePickedUp() || home == null || Nav.cheb(e.location, home) > C.GUARD_BOX + 2) continue;
            int hd = Nav.cheb(e.location, home);
            long s = (e.type == RobotType.LANDSCAPER ? 0 : 1000000L) + (hd <= 1 ? 0 : 10000L) + loc.distanceSquaredTo(e.location);
            if (s < bs) { bs = s; tgt = e; } }
        chasing = tgt != null;
        if (tgt != null) {
            if (rc.canPickUpUnit(tgt.ID)) { rc.pickUpUnit(tgt.ID); pickups++; Debug.log("@pickup t=" + tgt.type.ordinal() + " id=" + tgt.ID + " home=" + Nav.cheb(tgt.location, home)); return; }
            if (gun == null || tgt.location.distanceSquaredTo(gun) > 15) { nav.setTarget(tgt.location); nav.step(); return; }
        }
        if (patrol == null || loc.distanceSquaredTo(patrol) <= 2 || (home != null && (Nav.cheb(patrol, home) > C.GUARD_BOX || Nav.cheb(patrol, home) < 3))) {
            if (home != null) { for (int t = 0; t < 8; t++) { MapLocation p = new MapLocation(home.x + nextInt(2 * C.GUARD_BOX + 1) - C.GUARD_BOX, home.y + nextInt(2 * C.GUARD_BOX + 1) - C.GUARD_BOX); if (Nav.cheb(p, home) >= 3) { patrol = p; break; } } }
            else patrol = new MapLocation(loc.x + nextInt(9) - 4, loc.y + nextInt(9) - 4);
        }
        if (patrol != null) { nav.setTarget(patrol); if (!nav.step()) patrol = null; }
    }

    private int minersInside(MapLocation home) { int n = 0; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.MINER && Nav.cheb(friends[i].location, home) <= 1) n++; return n; }
    /** The nearest free dry tile at Chebyshev 3-5 from the HQ, for a miner lifted out. */
    private MapLocation outsideTile(MapLocation home) throws GameActionException {
        MapLocation best = null; int bd = 1 << 30;
        for (int dx = -5; dx <= 5; dx++) for (int dy = -5; dy <= 5; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) < 3) continue; MapLocation t = new MapLocation(home.x + dx, home.y + dy);
            if (!rc.onTheMap(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t) || rc.isLocationOccupied(t)) continue; int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; best = t; } }
        return best;
    }
    /** Stage 8: the gate -- the shell tile straight out from the school (Chebyshev 2, beside the school's yard). Holders never
     *  take it, their neighbours raise it, and the elevator lifts from it without entering the interior. */
    private MapLocation gate(MapLocation home) {
        for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type == RobotType.DESIGN_SCHOOL && Nav.cheb(f.location, home) == 1) {
            MapLocation g = new MapLocation(home.x + 2 * (f.location.x - home.x), home.y + 2 * (f.location.y - home.y));
            if (rc.onTheMap(g)) return g;
            // stage 14: a corner HQ's gate may be off the map (Prison: no lift in 2,000 rounds) -- the nearest on-map shell tile beside the school's yard
            MapLocation best = null; int bd = 1 << 30;
            for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue; MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!rc.onTheMap(t) || Nav.cheb(t, f.location) > 2) continue; int d = t.distanceSquaredTo(f.location); if (d < bd) { bd = d; best = t; } }
            return best; } }
        return null;
    }

    /** The nearest free, dry shell tile: Chebyshev 2 first, then 3 beside a held 2. */
    private MapLocation freeShell(MapLocation home) throws GameActionException {
        if (home == null) return null;
        MapLocation best = null; int bd = 1 << 30;
        for (int ring = 2; ring <= 3 && best == null; ring++)
            for (int dx = -ring; dx <= ring; dx++) for (int dy = -ring; dy <= ring; dy++) {
                if (Math.max(Math.abs(dx), Math.abs(dy)) != ring) continue;
                MapLocation t = new MapLocation(home.x + dx, home.y + dy);
                if (!rc.onTheMap(t) || !rc.canSenseLocation(t) || rc.senseFlooding(t) || rc.isLocationOccupied(t)) continue;
                if (ring == 2 && t.equals(gate(home))) continue;   // stage 8: the gate stays free
                if (ring == 3) { boolean held = false; for (int i = 8; --i >= 0;) { MapLocation n = t.add(DIRS[i]); if (Nav.cheb(n, home) != 2 || !rc.canSenseLocation(n)) continue; RobotInfo r = rc.senseRobotAtLocation(n); if (r != null && r.type == RobotType.LANDSCAPER && r.team == us) { held = true; break; } } if (!held) continue; }
                int d = loc.distanceSquaredTo(t); if (d < bd) { bd = d; best = t; }
            }
        return best;
    }

    private MapLocation[] nearWater() throws GameActionException {
        MapLocation best = null; int bd = 1 << 30;
        for (int dx = -4; dx <= 4; dx++) for (int dy = -4; dy <= 4; dy++) {
            MapLocation l = new MapLocation(loc.x + dx, loc.y + dy);
            if (!rc.canSenseLocation(l) || !rc.senseFlooding(l)) continue;
            int d = dx * dx + dy * dy; if (d < bd) { bd = d; best = l; }
        }
        return best == null ? null : new MapLocation[]{best};
    }
}
