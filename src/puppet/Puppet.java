package puppet;

import battlecode.common.*;

/**
 * Replay puppet runtime (PROMPTS 59-60; DESIGN.md "Puppet"). A robot of a pup_<base> team finds its recorded script
 * in the fixture (the engine's -c file, read through System.getProperty), plays it open-loop until the cutoff
 * round (-Dbc.testing.pup.cutoff, never in the fixture), and hands over to <base> in the same turn.
 *
 * Exact in an identical game: every act fires on its recorded round from its recorded tile, because the world, the
 * cooldowns and the execution order are the recording's. Drift (the other side played differently) is absorbed by
 * greedy steps, retargeting and drop thresholds, logged with @pup lines; an exact game prints none before handover.
 */
public final strictfp class Puppet {
    private Puppet() {}

    /** The puppet side's HQ as recorded; the shim primes the base's MapState with it at handover. */
    public static MapLocation home;

    private static RobotController rc;
    private static RobotType type;
    private static Team us, them;
    private static char[] s;                 // this robot's script
    private static int n, ai, ti;            // events, next event, next TX record
    private static int cutoff, fails, txFails, maxLag, dropped;
    private static int px, py;               // the recorded position (key tile, then every MOVE and PLACE)
    private static boolean flyer, txLateSaid, heldOwn;   // heldOwn: the unit held was picked by PICK_OWN
    /** Bytecodes kept after the messages: after the act (the turn's end), before it (the act still fits, ~700). */
    private static final int TX_RESERVE = 600, PRE_RESERVE = 1300;
    private static final Direction[] DIRS = {Direction.NORTH, Direction.NORTHEAST, Direction.EAST, Direction.SOUTHEAST,
                                             Direction.SOUTH, Direction.SOUTHWEST, Direction.WEST, Direction.NORTHWEST};
    private static final RobotType[] TYPES = RobotType.values();
    private static final String[] OPNAME = {"MOVE", "PLACE", "MINE", "DIG", "DEPOSIT", "DEP_SOUP", "BUILD", "PICK_OWN", "PICK_OTHER", "DROP", "SHOOT", "DIE"};

    /**
     * Plays this robot's recorded script until the cutoff. Returns true at handover (the base plays on from here,
     * same turn), false for a robot born at or after the cutoff (the base's from birth). Resigns the team when the
     * fixture is missing or is not this game's: a misconfigured puppet must not look like a game.
     */
    public static boolean play(RobotController rc) throws GameActionException {
        Puppet.rc = rc;
        type = rc.getType(); us = rc.getTeam(); them = us.opponent();
        // PROMPTS 66: a two-sided fixture scripts both teams to the cutoff (keys bc.testing.pup.A.* / .B.*), and both hand
        // over there at once -- ours to the candidate, theirs to the base; a one-sided fixture uses the plain keys
        String t = us == Team.A ? "A" : "B";
        String idx = System.getProperty("bc.testing.pup." + t + ".idx"), dat = System.getProperty("bc.testing.pup." + t + ".dat");
        if (idx == null) { idx = System.getProperty("bc.testing.pup.idx"); dat = System.getProperty("bc.testing.pup.dat"); }
        if (idx == null || dat == null) { System.out.println("@pup nofixture"); resign(); }
        MapLocation loc = rc.getLocation();
        if (idx.length() < 8 || Script.version(idx) != Script.VERSION || Script.side(idx) != us.ordinal()
                || Script.width(idx) != rc.getMapWidth() || Script.height(idx) != rc.getMapHeight()
                || (type == RobotType.HQ && (loc.x != Script.hqX(idx) || loc.y != Script.hqY(idx)))) {
            System.out.println("@pup wrongfixture"); resign();
        }
        home = new MapLocation(Script.hqX(idx), Script.hqY(idx));
        cutoff = Integer.parseInt(System.getProperty("bc.testing.pup." + t + ".cutoff", System.getProperty("bc.testing.pup.cutoff", "99999")));   // PROMPTS 67: a per-team cutoff
        int r0 = rc.getRoundNum();
        if (r0 >= cutoff) return false;
        int rec = Script.find(idx, type.ordinal(), r0, loc.x, loc.y);
        if (rec < 0) {
            System.out.println("@pup miss " + type.ordinal() + " r" + r0 + " " + loc.x + "," + loc.y);
            while (rc.getRoundNum() < cutoff) Clock.yield();
            return handover();
        }
        s = dat.substring(Script.offset(idx, rec), Script.offset(idx, rec + 1)).toCharArray();
        n = Script.events(s); ai = 0; ti = Script.txStart(s);
        while (ti < s.length && Script.txRound(s, ti) < r0) ti += Script.TX_LEN;   // never before this robot existed (a fallback match)
        px = Script.keyX(idx, rec); py = Script.keyY(idx, rec);
        flyer = type == RobotType.DELIVERY_DRONE;
        while (true) {
            int now = rc.getRoundNum();
            try {
                // handover; a drone holding a unit finishes the ferry first (its recorded DROP, at most 50 rounds), then
                // puts down a unit of its own team on a dry tile (the base's drone drowns whatever it holds)
                if (now >= cutoff && !(now < cutoff + 50 && rc.isCurrentlyHoldingUnit() && dropAhead())) {
                    if (!rc.isCurrentlyHoldingUnit() || !heldOwn || dropDry()) return handover();
                    if (now >= cutoff + 100) { System.out.println("@pup hocargo r" + now); return handover(); }
                } else {
                    if (now < cutoff) tx(now, true);
                    step(now);
                    if (now < cutoff) tx(now, false);
                }
            } catch (GameActionException e) { System.out.println("@pup exc " + e.getType().ordinal() + " r" + now); }
            Clock.yield();
        }
    }

    /** A DROP among the next events (the ferry in progress). */
    private static boolean dropAhead() {
        for (int i = ai, e = Math.min(n, ai + 60); i < e; i++) {
            int op = Script.op(s, i);
            if (op == Script.DROP) return true;
            if (op != Script.MOVE && op != Script.PLACE) return false;
        }
        return false;
    }

    /** Drops the unit held on an adjacent dry tile, else steps toward the recorded HQ (land). True once dropped. */
    private static boolean dropDry() throws GameActionException {
        if (!rc.isReady()) return false;
        MapLocation loc = rc.getLocation();
        for (int i = 0; i < 8; i++) {
            Direction d = DIRS[i];
            if (rc.canDropUnit(d) && !rc.senseFlooding(loc.add(d))) { rc.dropUnit(d); heldOwn = false; return true; }
        }
        greedy(home, false);
        return false;
    }

    private static boolean handover() {
        System.out.println("@pup ho " + maxLag + " " + dropped);
        return true;
    }

    private static void resign() {
        rc.resign();
        while (true) Clock.yield();
    }

    /** One turn: the free events (no cooldown), then at most one act when ready and due. */
    private static void step(int now) throws GameActionException {
        while (ai < n) {
            if (Script.round(s, ai) > now) return;
            int op = Script.op(s, ai);
            if (op == Script.PLACE) { px = Script.x(s, ai); py = Script.y(s, ai); ai++; continue; }
            if (op == Script.MOVE) {
                MapLocation l = rc.getLocation();
                if (l.x == Script.x(s, ai) && l.y == Script.y(s, ai)) { px = l.x; py = l.y; ai++; continue; }
            } else if (op == Script.DROP) {
                if (!rc.isCurrentlyHoldingUnit()) { ai++; continue; }
            } else if (op == Script.PICK_OWN || op == Script.PICK_OTHER) {
                if (rc.isCurrentlyHoldingUnit()) { ai++; continue; }
            } else if (op == Script.DIE) rc.disintegrate();
            break;
        }
        if (ai >= n || !rc.isReady()) return;
        int rr = Script.round(s, ai), op = Script.op(s, ai);
        if (op == Script.BUILD && now - rr > Script.WINDOW) {           // the newborn could no longer find its record
            System.out.println("@pup drop BUILD r" + rr + " lag" + (now - rr));
            dropped++; ai++; fails = 0;
            return;
        }
        int res = attempt(op, Script.arg(s, ai), Script.x(s, ai), Script.y(s, ai));
        if (res > 0) {
            if (op == Script.MOVE) { px = Script.x(s, ai); py = Script.y(s, ai); }
            if (now - rr > maxLag) maxLag = now - rr;
            ai++; fails = 0;
            if (ai < n && Script.op(s, ai) == Script.DIE && Script.round(s, ai) <= now) rc.disintegrate();
        } else if (res < 0 && ++fails >= (op == Script.MOVE ? 8 : 3)) {
            System.out.println("@pup drop " + OPNAME[op] + " r" + rr + " lag" + (now - rr));
            dropped++; ai++; fails = 0;
        }
    }

    /** 1 done, 0 waiting (not a failure), -1 failed this turn. */
    private static int attempt(int op, int arg, int x, int y) throws GameActionException {
        MapLocation loc = rc.getLocation(), t = new MapLocation(x, y);
        int d2 = loc.distanceSquaredTo(t);
        Direction d = loc.directionTo(t);
        switch (op) {
            case Script.MOVE:
                if (d2 <= 2) return tryMove(d, arg == 1) ? 1 : -1;
                greedy(t, arg == 1);
                return -1;
            case Script.MINE:
                if (d2 > 2) return approach();
                if (rc.canMineSoup(d)) { rc.mineSoup(d); return 1; }
                return -1;
            case Script.DIG:
                if (d2 > 2) return approach();
                if (rc.canDigDirt(d)) { rc.digDirt(d); return 1; }
                return -1;
            case Script.DEPOSIT:
                if (d2 > 2) return approach();
                if (rc.canDepositDirt(d)) { rc.depositDirt(d); return 1; }
                return -1;
            case Script.DEP_SOUP:
                if (d2 > 2 || d2 == 0) return approach();
                if (rc.canDepositSoup(d)) { rc.depositSoup(d, rc.getSoupCarrying()); return 1; }
                return -1;
            case Script.BUILD: {
                if (d2 > 2 || d2 == 0) return approach();
                RobotType bt = TYPES[arg];
                if (rc.canBuildRobot(bt, d)) { rc.buildRobot(bt, d); return 1; }
                if (rc.getTeamSoup() < bt.cost) return 0;
                if (fails > 0) {                                        // after one counted failure: the nearest other tile
                    Direction l = d, r = d;
                    for (int k = 0; k < 4; k++) {
                        l = l.rotateLeft(); r = r.rotateRight();
                        if (rc.canBuildRobot(bt, l)) { rc.buildRobot(bt, l); return 1; }
                        if (k < 3 && rc.canBuildRobot(bt, r)) { rc.buildRobot(bt, r); return 1; }
                    }
                }
                return -1;
            }
            case Script.PICK_OWN: case Script.PICK_OTHER: {
                if (d2 > 3) return approach();
                int id = pickTarget(op == Script.PICK_OWN, arg, t);
                if (id < 0) return -1;
                rc.pickUpUnit(id); heldOwn = op == Script.PICK_OWN; return 1;
            }
            case Script.DROP:
                if (d2 > 2 || d2 == 0) return approach();
                if (rc.canDropUnit(d)) { rc.dropUnit(d); return 1; }
                return -1;
            case Script.SHOOT: {
                if (rc.canSenseLocation(t)) {
                    RobotInfo ri = rc.senseRobotAtLocation(t);
                    if (ri != null && ri.type == RobotType.DELIVERY_DRONE && rc.canShootUnit(ri.ID)) { rc.shootUnit(ri.ID); return 1; }
                }
                RobotInfo[] rs = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, them);
                int best = -1, bd = 1 << 30;
                for (int i = rs.length; --i >= 0;) {
                    RobotInfo ri = rs[i];
                    if (ri.type != RobotType.DELIVERY_DRONE) continue;
                    int dd = ri.location.distanceSquaredTo(t);
                    if (dd < bd && rc.canShootUnit(ri.ID)) { bd = dd; best = ri.ID; }
                }
                if (best < 0) return -1;
                rc.shootUnit(best); return 1;
            }
            default:
                return -1;
        }
    }

    /** The unit at t if it matches (team, type); else the nearest pickable match within reach: OWN the same type,
     *  OTHER the recorded type first, else an enemy miner or landscaper; a cow is any cow. */
    private static int pickTarget(boolean own, int ty, MapLocation t) throws GameActionException {
        if (rc.canSenseLocation(t)) {
            RobotInfo ri = rc.senseRobotAtLocation(t);
            if (ri != null && ri.type.ordinal() == ty && (own ? ri.team == us : ri.team != us) && rc.canPickUpUnit(ri.ID)) return ri.ID;
        }
        boolean cow = ty == RobotType.COW.ordinal();
        RobotInfo[] rs = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, own ? us : cow ? Team.NEUTRAL : them);
        int best = -1, bd = 1 << 30; boolean bestTyped = false;
        for (int i = rs.length; --i >= 0;) {
            RobotInfo ri = rs[i];
            boolean typed = ri.type.ordinal() == ty;
            if (!typed && (own || cow || !ri.type.canBePickedUp())) continue;
            int dd = ri.location.distanceSquaredTo(t);
            if (((typed && !bestTyped) || (typed == bestTyped && dd < bd)) && rc.canPickUpUnit(ri.ID)) { best = ri.ID; bd = dd; bestTyped = typed; }
        }
        return best;
    }

    /** Out of reach: step toward the recorded position (buildings cannot). Always a failed turn. */
    private static int approach() throws GameActionException {
        if (type.canMove()) greedy(new MapLocation(px, py), false);
        return -1;
    }

    private static boolean greedy(MapLocation t, boolean water) throws GameActionException {
        MapLocation loc = rc.getLocation();
        if (loc.equals(t)) return false;
        Direction d = loc.directionTo(t);
        return tryMove(d, water) || tryMove(d.rotateLeft(), water) || tryMove(d.rotateRight(), water);
    }

    private static boolean tryMove(Direction d, boolean water) throws GameActionException {
        if (!rc.canMove(d)) return false;
        if (!flyer && !water && rc.senseFlooding(rc.adjacentLocation(d))) return false;
        rc.move(d);
        return true;
    }

    /**
     * Submits the TX records due, in order: before the act (pre) the late ones and this round's PRE records, after it
     * the rest due by now. What does not fit above the reserve goes next turn (@pup txlate); a fee the team cannot
     * pay waits (after the act, three turns, then @pup txdrop).
     */
    private static void tx(int now, boolean pre) throws GameActionException {
        char[] s = Puppet.s;                                           // inlined reads; ~270 bytecodes a message
        int reserve = pre ? PRE_RESERVE : TX_RESERVE;
        while (ti < s.length) {
            int c = s[ti], r = c & 0xFFF;                              // Script.txRound / txPre
            if (r > now || (pre && r == now && c < Script.PRE)) return;
            if (Clock.getBytecodesLeft() < reserve) {
                if (!txLateSaid) { System.out.println("@pup txlate r" + now); txLateSaid = true; }
                return;
            }
            int t = ti, fee = s[t + 1];
            if (rc.getTeamSoup() >= fee) {
                rc.submitTransaction(new int[]{s[t + 2] << 16 | s[t + 3], s[t + 4] << 16 | s[t + 5], s[t + 6] << 16 | s[t + 7],
                        s[t + 8] << 16 | s[t + 9], s[t + 10] << 16 | s[t + 11], s[t + 12] << 16 | s[t + 13], s[t + 14] << 16 | s[t + 15]}, fee);
                ti = t + Script.TX_LEN; txFails = 0;
            }
            else if (!pre && ++txFails >= 3) { System.out.println("@pup txdrop r" + r); ti += Script.TX_LEN; txFails = 0; }
            else return;
        }
    }
}
