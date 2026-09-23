package bot;

/** Tunable constants, one place. Each one names the measurement that set it, once there is one. */
public final class C {
    private C() {}
    public static final boolean DEBUG = true;         // @tag logging into the replay (silenced per team by the gauntlet)
    public static final int BC_REPORT_EVERY = 100;    // rounds between @bc monitor lines per robot
    public static final int ARCHETYPE = 0;            // sparring-partner switch, set by tools/snapshot.sh

    // Iteration 0 economy
    public static final int MAX_MINERS = 6;           // the HQ builds miners up to this count of live ones it can see + built
    public static final int SOUP_RETURN = 70;         // a miner heads home to deposit at this much carried soup
}
