package arch_enclosure;

/** Tunable constants, one place. Each one names the measurement that set it, once there is one. */
public final class C {
    private C() {}
    public static final boolean DEBUG = true;         // @tag logging into the replay (silenced per team by the gauntlet)
    public static final int BC_REPORT_EVERY = 100;    // rounds between @bc monitor lines per robot
    public static final int ARCHETYPE = 0;            // sparring-partner switch, set by tools/snapshot.sh

    // --- economy (Iteration 1: unmeasured starting values)
    public static final int MINERS_EARLY = 4;         // miners the HQ builds before anything else is affordable
    public static final int MINERS_MAX = 8;           // never more live miners than this (stage 27: eight, as the wall bot -- its income was three times ours at r300, 24 bodies to our 10; the ones born inside are lifted out)
    public static final int MINERS_TOTAL = 8;          // stage 10: four miners and no more -- every miner the HQ spawned after r200 was born inside the shell and stood on the school's yard for the rest of the game         // hard cap on miners ever built by the HQ
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
    public static final int DRONES_MAX = 4;           // Iteration 2: drones fly over the flood and count at the tiebreak (stage 26: four -- eight cost 1,200 soup, eight bodies, in the rounds that decide how many tiles get held)
    public static final int DRONE_RESERVE = 250;      // the center keeps this much soup back
    public static final int DRONE_ROUND = 400;        // Iteration 3: before this round drones need a bank of DRONE_EARLY_BANK (helpers first)
    public static final int DRONE_EARLY_BANK = 800;

    // --- wall
    public static final int WALL_LANDSCAPERS = 8;     // one per ring tile
    public static final int WALL_HELPERS = 8;         // Iteration 3: a second ring at distance 2 feeding dirt onto the seats
    public static final int HELPER_BANK = 200;        // Iteration 43b: was 300, 43 tried 0 (the HQ starved of miners); 200 leaves the HQ its MINER_SOUP_RESERVE -- the bank it guarded is never spent (12,000 unspent at r3000)
    public static final int LANDSCAPERS_MAX = 48;     // the enclosure: 16 inner shell + 24 outer + feeders
    public static final int SHELL_FROM = 250;         // stage 22: miners keep off Chebyshev 2 from this round (the shell is being held by then; before it their corridors run through it)
    public static final int SHELL_CLIMB = 3;          // a shell tile is takeable within this of our own elevation
    public static final int SHELL_SLACK = 2;          // equalise an adjacent shell tile only when it is this much below ours
    public static final int QUARRY_FLOOR = -9;        // stage 4: the interior is dug down to this and no further (the field's boards read -9)
    public static final int RECLAIM_MARGIN = 20;      // stage 4: a holder this far above its need raises the highest outer tile beside it toward dry land (stage 7: 20, and only every third turn -- reclaiming took 70% of the holders' dirt)
    public static final int LIFT_UNTIL = 1000;        // stage 33: no lift after this round and no drone over the gate -- the outer ring has flooded by then, and a drone hovering on the gate keeps its neighbours' dirt off it (the gate 1,309 at r3000 against 1,730 for the rest: the HQ drowned through it at r3081)
    public static final int RECLAIM_UNTIL = 2000;     // stage 23: no reclaiming after this round (from r2700 the holders reclaimed the sea instead of raising the shell: +9 in 250 rounds, drowned at r2967)
    public static final int RECLAIM_DEPTH = 15;       // stage 23: reclaim only an outer tile within this much of dry land (the pits are bottomless)
    public static final int INSIDE_MAX = 3;           // buildings on the ring besides the school (stage 14: three -- with four and the builder the yard had one tile, and a waiter on it stopped every spawn)
    public static final int GUARD_BOX = 4;            // drones patrol within this of the HQ
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
