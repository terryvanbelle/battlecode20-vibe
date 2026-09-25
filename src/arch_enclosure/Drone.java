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
    private int droppedId = -1, droppedUntil = 0;
    private MapLocation water, patrol, liftTarget, lastTarget, scoutTarget, scout; private int lastTargetUntil = 0, scoutI = 0, scoutRest = 0;
    private boolean holdingFriend = false, chasing = false;
    private int pickups = 0, drops = 0, lifts = 0;

    Drone(RobotController rc) { super(rc); avoidRing = true; }
    @Override protected boolean allowedTile(MapLocation l) { MapLocation h = MapState.home; if (h == null) return true; int d = Nav.cheb(l, h); return d >= 3 || (d == 2 && (chasing || holdingFriend || exiting)) || (d <= 1 && exiting); }   // stage 15: never inside except on the way out (born there)
    private boolean exiting = false;

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
            if (liftTarget == null) {   // stage 24: never on the gate or its approach (a body set down on the gate at r1068 sealed it for the rest of the game); the yard is fine; else keep holding and wait on the approach
                MapLocation g = gate(home);
                for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (Nav.cheb(n, home) >= 1 && rc.canDropUnit(d) && !rc.senseFlooding(n) && (g == null || Nav.cheb(n, g) > 1 || Nav.cheb(n, home) <= 1)) { rc.dropUnit(d); holdingFriend = false; drops++; RobotInfo r = rc.senseRobotAtLocation(n); if (r != null) { droppedId = r.ID; droppedUntil = round + 60; } Debug.log("@lift-drop anywhere at=" + n); return; } }
                if (g != null) { chasing = true; nav.setTarget(g); nav.step(); chasing = false; } return; }
            if (loc.isAdjacentTo(liftTarget)) { Direction d = loc.directionTo(liftTarget); if (rc.canDropUnit(d)) { rc.dropUnit(d); holdingFriend = false; lifts++; Debug.log("@lift to=" + liftTarget + " d=" + Nav.cheb(liftTarget, home)); liftTarget = null; return; } }
            nav.setTarget(liftTarget); nav.step(); return;
        }
        if (rc.isCurrentlyHoldingUnit()) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; MapLocation n = loc.add(d); if (rc.canDropUnit(d) && rc.senseFlooding(n)) { rc.dropUnit(d); drops++; Debug.log("@drown at=" + n); return; } }
            if (water != null) { nav.setTarget(water); if (nav.step()) return; }
            MapLocation[] near = nearWater(); if (near != null) { water = near[0]; nav.setTarget(water); nav.step(); return; }
            nav.setTarget(MapState.center()); nav.step(); return;
        }
        // stage 19: the elevator is the drone nearest the gate (lowest id on a tie), and it keeps station outside the gate
        MapLocation gate0 = home == null ? null : gate(home);
        boolean elevator = true;
        if (gate0 != null) { int myd = loc.distanceSquaredTo(gate0); for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.DELIVERY_DRONE) continue; int fd = f.location.distanceSquaredTo(gate0); if (fd < myd || (fd == myd && f.ID < id)) { elevator = false; break; } } }
        else { for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DELIVERY_DRONE && friends[i].ID < id) { elevator = false; break; } }
        if (elevator && round % 25 == 0 && round >= 500) Debug.log("@elev at=" + loc + " d=" + (home == null ? -1 : Nav.cheb(loc, home)) + " holding=" + rc.isCurrentlyHoldingUnit() + " friend=" + holdingFriend + " target=" + liftTarget + " gate=" + gate(home) + " free=" + freeShell(home));
        // stage 20: a drone inside leaves before it does anything else (the yard tiles beside the gate were blocked by drones
        // that had come in chasing and stayed: the waiters could not reach the gate)
        if (home != null && Nav.cheb(loc, home) <= 1) {
            MapLocation g = gate(home); exiting = true; nav.setTarget(g != null ? g : home.add(DIRS[nextInt(8)]).add(DIRS[nextInt(8)])); boolean moved = nav.step(); exiting = false; if (moved) return; }
        // stage 10: a miner trapped inside (not the builder: the one beside a building it is building for) is lifted out to
        // the nearest free dry tile at Chebyshev 3 or more -- it was born after the shell closed and stands on the yard
        // stage 35: a miner of ours on a shell tile is lifted out at any round (three stood on RandomSoup1's west shell from
        // r700 to the end: no holder there, that side 400 below the rest); the builder never leaves the interior, so never
        if (home != null && !rc.isCurrentlyHoldingUnit()) {
            RobotInfo m = null; int md = 1 << 30;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.MINER || Nav.cheb(f.location, home) != 2) continue; int d = loc.distanceSquaredTo(f.location); if (d < md) { md = d; m = f; } }
            if (m != null && md <= 8) {
                if (rc.canPickUpUnit(m.ID)) { rc.pickUpUnit(m.ID); holdingFriend = true; liftTarget = outsideTile(home); Debug.log("@lift-shellminer id=" + m.ID + " to=" + liftTarget); return; }
                chasing = true; nav.setTarget(m.location); nav.step(); chasing = false; return; }
        }
        if (home != null && !rc.isCurrentlyHoldingUnit() && elevator && round < 500) {   // stage 20: the elevator's job, nobody else's; stage 22: only before r500 (the builder was lifted out and drowned at r1000, and no miner is born inside after the first four)
            RobotInfo m = null; int md = 1 << 30;
            MapLocation sch = null; for (int i = nFriend; --i >= 0;) if (friends[i].type == RobotType.DESIGN_SCHOOL && Nav.cheb(friends[i].location, home) == 1) sch = friends[i].location;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.MINER || Nav.cheb(f.location, home) > 1 || sch == null || Nav.cheb(f.location, sch) > 1) continue; int d = loc.distanceSquaredTo(f.location); if (d < md) { md = d; m = f; } }   // stage 14: only a miner on the yard (the builder never stands there)
            if (m != null) {
                MapLocation gate = gate(home);
                if (rc.canPickUpUnit(m.ID)) { rc.pickUpUnit(m.ID); holdingFriend = true; liftTarget = outsideTile(home); Debug.log("@lift-miner id=" + m.ID + " to=" + liftTarget); return; }
                chasing = true; nav.setTarget(gate != null ? gate : m.location); nav.step(); chasing = false; return;
            }
        }
        // stage 17: one elevator -- the drone with the lowest id in sight; eight of them queued on the gate and it never rose
        // the elevator, empty: a landscaper of ours in the yard with a free shell tile to go to
        if (home != null) {   // stage 27: every drone lifts (stage 33's cutoff at r1000 reverted in 34: Squares' bodies come after r1500) (one elevator gave a lift every 25 rounds; the outer ring is dry until r950 and the bodies have to be on it by then)
            RobotInfo w = null; int wd = 1 << 30;
            for (int i = nFriend; --i >= 0;) { RobotInfo f = friends[i]; if (f.type != RobotType.LANDSCAPER || Nav.cheb(f.location, home) != 1 || (f.ID == droppedId && round < droppedUntil)) continue; int d = loc.distanceSquaredTo(f.location); if (d < wd) { wd = d; w = f; } }
            if (w != null) {
                MapLocation t = freeShell(home); MapLocation gate = gate(home);
                // stage 26: a target seen while scouting is remembered; none in sight from the gate, the elevator flies the
                // four corners at Chebyshev 4 to find one (the station sees one side of the shell: free=null for 2,000 rounds
                // with dry tiles on the far side)
                if (scoutTarget != null && rc.canSenseLocation(scoutTarget) && (rc.isLocationOccupied(scoutTarget) || rc.senseFlooding(scoutTarget) || (lastTarget != null && round < lastTargetUntil && scoutTarget.equals(lastTarget)))) scoutTarget = null;
                if (t != null) scoutTarget = t; else t = scoutTarget;
                if (round % 100 == 0) Debug.log("@gate " + gate + " waiter=" + w.location + " target=" + t);
                if (t == null && gate != null && Nav.cheb(w.location, gate) <= 1) {
                    // stage 28: one lap of the on-map corners at Chebyshev 3, then a hundred rounds on station (stage 26's lap
                    // flew at Chebyshev 4 with off-map corners folded onto shell tiles it may not enter: Prison's elevator
                    // circled for 400 rounds and lifted three)
                    MapLocation station = new MapLocation(gate.x + (gate.x - home.x) / 2, gate.y + (gate.y - home.y) / 2);
                    if (round < scoutRest) { if (rc.onTheMap(station) && !loc.equals(station)) { nav.setTarget(station); nav.step(); } return; }
                    if (scout == null || loc.distanceSquaredTo(scout) <= 2) {
                        scout = null;
                        while (scout == null && scoutI < 4) { int sx = (scoutI & 1) == 0 ? -3 : 3, sy = (scoutI & 2) == 0 ? -3 : 3; scoutI++; MapLocation c = new MapLocation(home.x + sx, home.y + sy); if (rc.onTheMap(c)) scout = c; }
                        if (scout == null) { scoutI = 0; scoutRest = round + 100; return; } }
                    if (round % 25 == 0) Debug.log("@scout to=" + scout + " waiter=" + w.location);
                    nav.setTarget(scout); nav.step(); return; }
                if (t != null && gate != null && Nav.cheb(w.location, gate) <= 1) {   // stage 16: only when the waiter stands beside the gate -- a drone on the gate keeps it from being raised
                    holdingFriend = true;
                    if (rc.canPickUpUnit(w.ID)) { rc.pickUpUnit(w.ID); liftTarget = t; lastTarget = t; lastTargetUntil = round + 30; pickups++; Debug.log("@lift-up id=" + w.ID + " for=" + t); return; }
                    holdingFriend = false; chasing = true; nav.setTarget(gate); boolean moved = nav.step(); chasing = false; if (round % 10 == 0) Debug.log("@chase at=" + loc + " gate=" + gate + " moved=" + moved + " waiter=" + w.location); return;
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
        // stage 19: the idle elevator waits on the tile straight out from the gate (Chebyshev 3), where it sees the yard
        if (elevator && gate0 != null && !rc.isCurrentlyHoldingUnit()) {
            MapLocation station = new MapLocation(gate0.x + (gate0.x - home.x) / 2, gate0.y + (gate0.y - home.y) / 2);
            if (!rc.onTheMap(station)) { station = null; int sd = 1 << 30; for (int dx = -3; dx <= 3; dx++) for (int dy = -3; dy <= 3; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 3) continue; MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!rc.onTheMap(t)) continue; int d = t.distanceSquaredTo(gate0); if (d < sd) { sd = d; station = t; } } }
            if (station != null && !loc.equals(station)) { nav.setTarget(station); nav.step(); return; }
            if (station != null) return;
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
            if (rc.onTheMap(g)) { MapState.gate = g; return g; }
            // stage 14: a corner HQ's gate may be off the map (Prison: no lift in 2,000 rounds) -- the nearest on-map shell tile beside the school's yard
            MapLocation best = null; int bd = 1 << 30;
            for (int dx = -2; dx <= 2; dx++) for (int dy = -2; dy <= 2; dy++) { if (Math.max(Math.abs(dx), Math.abs(dy)) != 2) continue; MapLocation t = new MapLocation(home.x + dx, home.y + dy); if (!rc.onTheMap(t) || Nav.cheb(t, f.location) > 2) continue; int d = t.distanceSquaredTo(f.location); if (d < bd) { bd = d; best = t; } }
            MapState.gate = best; return best; } }
        return MapState.gate;   // stage 26: cached once seen -- a drone on the far side set a body down on the gate at r988
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
                if (ring == 2 && !isShell(t, home)) continue;   // stage 26: the edge side of a corner enclosure is interior
                if (ring == 3 && gate(home) != null && Nav.cheb(t, gate(home)) <= 1) continue;   // stage 23: the approach to the gate stays free too
                if (lastTarget != null && round < lastTargetUntil && t.equals(lastTarget)) continue;   // stage 24: a body is on its way there
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
