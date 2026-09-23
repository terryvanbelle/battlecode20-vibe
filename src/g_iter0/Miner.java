package g_iter0;

import battlecode.common.*;

/** Miner, Iteration 0: mine the nearest visible soup, walk it home to the HQ, wander when there is none. */
public strictfp class Miner extends Robot {
    private MapLocation wander;

    Miner(RobotController rc) { super(rc); }

    @Override protected void turn() throws GameActionException {
        sense();
        int carrying = rc.getSoupCarrying();
        MapLocation home = MapState.home;
        // 1. deposit if full and adjacent to the HQ (or a refinery)
        if (carrying > 0) {
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (rc.canDepositSoup(d)) { rc.depositSoup(d, carrying); Debug.log("@deposit n=" + carrying); return; } }
        }
        if (carrying >= C.SOUP_RETURN && home != null) { nav.setTarget(home); nav.step(); return; }
        // 2. mine adjacent soup
        if (rc.isReady()) {
            Direction bestD = null; int best = 0;
            for (int i = 8; --i >= 0;) { Direction d = DIRS[i]; if (rc.canMineSoup(d)) { int s = rc.senseSoup(loc.add(d)); if (s > best) { best = s; bestD = d; } } }
            if (rc.canMineSoup(Direction.CENTER) && rc.senseSoup(loc) > best) bestD = Direction.CENTER;
            if (bestD != null) { rc.mineSoup(bestD); return; }
        }
        // 3. walk to the nearest visible soup
        MapLocation[] soup = rc.senseNearbySoup();
        MapLocation tgt = null; int bd = 1 << 30;
        for (int i = soup.length; --i >= 0;) { int d = loc.distanceSquaredTo(soup[i]); if (d < bd && (rc.canSenseLocation(soup[i]) && !rc.senseFlooding(soup[i]))) { bd = d; tgt = soup[i]; } }
        if (tgt != null) { nav.setTarget(tgt); if (nav.step()) return; }
        // 4. wander: pick a far random point and walk there
        if (wander == null || loc.distanceSquaredTo(wander) <= 4 || nav.isBugging() && nextInt(8) == 0)
            wander = new MapLocation(loc.x + nextInt(31) - 15, loc.y + nextInt(31) - 15);
        nav.setTarget(wander);
        if (!nav.step()) wander = null;
    }
}
