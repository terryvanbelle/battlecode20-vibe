package bot;

/** Tunable constants, one place. Each one names the measurement that set it, once there is one. */
public final class C {
    private C() {}
    public static final boolean DEBUG = true;         // @tag logging into the replay (silenced per team by the gauntlet)
    public static final int BC_REPORT_EVERY = 100;    // rounds between @bc monitor lines per robot
    public static final int ARCHETYPE = 0;            // sparring-partner switch, set by tools/snapshot.sh

    // --- economy (Iteration 1: unmeasured starting values)
    public static final int MINERS_EARLY = 4;         // miners the HQ builds before anything else is affordable
    public static final int MINERS_MAX = 8;           // never more live miners than this (each costs a wall-ring build slot)
    public static final int MINERS_TOTAL = 16;         // hard cap on miners ever built by the HQ
    public static final int MINER_REPLENISH = 60;     // rounds between replacement miners after MINERS_MAX
    public static final int MINER_SOUP_RESERVE = 200; // after MINERS_EARLY the HQ builds a miner only above this bank
    public static final int SOUP_RETURN = 70;         // a miner heads home to deposit at this much carried soup
    public static final int SOUP_MEMORY = 12;         // remembered soup tiles per miner
    public static final int SOUP_SCAN = 12;           // visible soup tiles sampled per scan (bytecode)
    public static final int SOUP_BAD = 8;             // unreachable soup regions a miner remembers
    public static final int VAPORATOR_BANK = 650;     // a builder buys a vaporator when the bank exceeds this
    public static final int FC_EARLY_BANK = 350;      // Iteration 2: the fulfillment center comes right after the school, at this bank
    public static final int VAPORATORS_MAX = 6;
    public static final int NETGUN_BANK = 400;        // ... a net gun (after the first vaporator) above this
    public static final int NETGUNS_MAX = 2;
    public static final int FC_BANK = 500;            // ... a fulfillment center above this, once the wall has started
    public static final int DRONES_MAX = 8;           // Iteration 2: drones fly over the flood and count at the tiebreak
    public static final int DRONE_RESERVE = 250;      // the center keeps this much soup back
    public static final int DRONE_ROUND = 400;        // Iteration 3: before this round drones need a bank of DRONE_EARLY_BANK (helpers first)
    public static final int DRONE_EARLY_BANK = 800;
    // Iteration 23: the home guard. The ladder's reviewable mid-game losses (45 of 167, ending at r1216-1217 and
    // r1563-1571 against benzyx and team4) are timed raids of 20-140 drones that lift our seats and drop landscapers
    // on the ring; our drones are never built on the ladder (a center in 18 of 248 games) and 1,300 soup sits idle from
    // r700. From HOME_ROUND every drone guards a box round the HQ and lifts whatever lands within CHASE_RADIUS of it.
    // (Gate 23 rejected buying the drones before the flood; Iteration 24 buys them after it, from the perch.)
    public static final int HOME_ROUND = 900;
    // Iteration 24: the perch. Three tiles at Chebyshev 3 (B for the builder, F for a center, V for a vaporator), all
    // adjacent to the helper post P at Chebyshev 2 on the side away from the map centre, raised by P's helper (the
    // mason) to the water level of PERCH_UNTIL before the flood. The builder climbs onto B at PERCH_ROUND, rides up as
    // the mason raises B under it, and builds the center and the vaporator from there; they outlive the flood by 1,500
    // rounds and turn the idle post-flood soup into drones for the guard.
    public static final int PERCH_UNTIL = 2200;       // the perch stays dry until about this round (water 30)
    public static final int PERCH_ROUND = 420;        // the builder goes to B from this round (F and V stay within 3 of B until it stands there)
    public static final int PERCH_PICK_ROUND = 80;    // the HQ picks the perch once the school stands (its spawn tiles are known)
    public static final int PERCH_GIVEUP_ROUND = 650;  // the builder leaves an unraised perch at this round and builds as before
    public static final int PERCH_STALL = 40;         // builder turns stalled on the way to B before giving the perch up
    public static final int DRONE_LATE_RESERVE = 0;   // after the flood every 150 soup is a drone ...
    public static final int DRONES_LATE_MAX = 16;     // ... up to this many from the perch center (40 on Prison blocked the helpers' digging)
    public static final int CHASE_RADIUS = 8;         // Chebyshev from the HQ
    public static final int GUARD_INNER = 5, GUARD_OUTER = 7;   // the patrol annulus: distance 2 is the helpers' tier and 3-4 their dig sources (Prison: 40 parked drones starved the wall, ring 1040 vs 1598)


    // --- wall
    public static final int WALL_LANDSCAPERS = 8;     // one per ring tile
    public static final int WALL_HELPERS = 8;         // Iteration 3: a second ring at distance 2 feeding dirt onto the seats
    public static final int HELPER_BANK = 300;        // helpers are built above this bank (after the 8 seats)
    public static final int LANDSCAPERS_MAX = 24;     // the surplus attacks
    public static final int ATTACKER_BANK = 700;      // surplus landscapers only above this bank
    public static final int BUILD_DIST = 2;           // Chebyshev distance from the HQ at which buildings go (ring is 1)
    public static final int WALL_LEVEL_SLACK = 2;     // a seat raises a neighbouring ring tile when it is more than this below itself
    public static final int WALL_BORROW_MARGIN = 10;  // a seat with no outside dirt digs from a ring neighbour at least this much taller

    // --- flood safety
    public static final int FLOOD_LOOKAHEAD = 10;     // a walker leaves a tile the water will reach within this many rounds
}
