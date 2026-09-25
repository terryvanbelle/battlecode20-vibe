package g_iter9;

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

    // --- wall
    public static final int WALL_LANDSCAPERS = 8;     // one per ring tile
    public static final int WALL_HELPERS = 8;         // Iteration 3: a second ring at distance 2 feeding dirt onto the seats
    public static final int HELPER_BANK = 200;        // Iteration 43b: was 300, 43 tried 0 (the HQ starved of miners); 200 leaves the HQ its MINER_SOUP_RESERVE -- the bank it guarded is never spent (12,000 unspent at r3000)
    public static final int LANDSCAPERS_MAX = 24;     // the surplus attacks
    public static final int ATTACKER_BANK = 200;      // Iteration 43b: was 700, 43 tried 0 -- landscapers 17-24 came 200 rounds late on RandomSoup1 and never on Toothpaste as A
    public static final int RUSH_UNTIL = 400;          // Iteration 29: an enemy school or landscaper this close to our HQ before this round is a rush
    public static final int RUSH_D2 = 64;
    public static final int BUILD_DIST = 2;           // Chebyshev distance from the HQ at which buildings go (ring is 1)
    // Iteration 25: seats first. Reviewable ladder losses show 2-6 of 8 seats at r700 (median 4): a newborn's ring tile
    // was raised by its seated neighbours' equalising and by the helpers' feeding before it arrived, and a tile 4 above
    // the ground is unclimbable for good. Until SEATS_BY nobody raises an unoccupied ring tile, and a helper beside a
    // free, climbable ring tile takes the seat.
    public static final int SEATS_BY = 400;
    public static final int WALL_LEVEL_SLACK = 2;     // a seat raises a neighbouring ring tile when it is more than this below itself
    public static final int WALL_BORROW_MARGIN = 10;  // a seat with no outside dirt digs from a ring neighbour at least this much taller

    // --- flood safety
    public static final int FLOOD_LOOKAHEAD = 10;     // a walker leaves a tile the water will reach within this many rounds
}
