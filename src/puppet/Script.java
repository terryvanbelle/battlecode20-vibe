package puppet;

/**
 * The fixture's encoding, read side (PROMPTS 59-60). tools/puppet.py writes it; test/puppet/PuppetTest.java and
 * tools/test_tools.py pin the contract with test/puppet/golden.properties.
 *
 * idx: header [version=1] [side 0/1] [hqx<<6|hqy] [(w-1)<<6|(h-1)], then 4 chars per robot sorted by
 *      (type, keyRound, x, y): [type<<12|keyRound] [x<<6|y] [offHi] [offLo], then a sentinel (type 15) whose
 *      offset is |dat|. A robot's script is dat[off, next record's off).
 * script: [nAct], nAct events of 2 chars [op<<12|round] [arg<<12|x<<6|y], then TX records of 16 chars
 *      [PRE|round] [fee] [w0hi w0lo .. w6hi w6lo]; PRE (0x8000) marks a record sent before the robot's act (a
 *      builder that submitted, then built), the others go after it.
 * Pure functions over the two strings; charAt only (indexOf and split are O(n) under the instrumenter).
 */
public final strictfp class Script {
    private Script() {}

    public static final int VERSION = 1;
    public static final int MOVE = 0, PLACE = 1, MINE = 2, DIG = 3, DEPOSIT = 4, DEP_SOUP = 5, BUILD = 6,
                            PICK_OWN = 7, PICK_OTHER = 8, DROP = 9, SHOOT = 10, DIE = 11;
    /** A robot's first turn may come this many rounds after its key round (held at birth, or drift). */
    public static final int WINDOW = 20;
    /** The nearest-tile fallback: a builder that had to use another adjacent tile. */
    public static final int NEAR_D2 = 8;

    public static int version(String idx) { return idx.charAt(0); }
    public static int side(String idx) { return idx.charAt(1); }
    public static int hqX(String idx) { return idx.charAt(2) >> 6; }
    public static int hqY(String idx) { return idx.charAt(2) & 63; }
    public static int width(String idx) { return (idx.charAt(3) >> 6) + 1; }
    public static int height(String idx) { return (idx.charAt(3) & 63) + 1; }
    /** Robots in the index (the sentinel excluded). */
    public static int records(String idx) { return (idx.length() - 4) / 4 - 1; }
    public static int type(String idx, int i) { return idx.charAt(4 + 4 * i) >> 12; }
    public static int keyRound(String idx, int i) { return idx.charAt(4 + 4 * i) & 0xFFF; }
    public static int keyX(String idx, int i) { return idx.charAt(5 + 4 * i) >> 6; }
    public static int keyY(String idx, int i) { return idx.charAt(5 + 4 * i) & 63; }
    public static int offset(String idx, int i) { return idx.charAt(6 + 4 * i) << 16 | idx.charAt(7 + 4 * i); }

    /**
     * The record of a robot of type t whose first turn is round r0 at (x, y): the last record with (type, keyRound)
     * <= (t, r0), scanning back while keyRound >= r0 - WINDOW; an exact tile wins (the largest keyRound first),
     * else the nearest tile within NEAR_D2 (ties to the largest keyRound). -1 if none.
     */
    public static int find(String idx, int t, int r0, int x, int y) {
        int key = t << 12 | Math.min(r0, 0xFFF);
        int lo = 0, hi = records(idx) - 1, last = -1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            if (idx.charAt(4 + 4 * mid) <= key) { last = mid; lo = mid + 1; } else hi = mid - 1;
        }
        int best = -1, bestD = NEAR_D2 + 1;
        for (int i = last; i >= 0; i--) {
            int c = idx.charAt(4 + 4 * i);
            if ((c >> 12) != t || (c & 0xFFF) < r0 - WINDOW) break;
            int p = idx.charAt(5 + 4 * i), dx = (p >> 6) - x, dy = (p & 63) - y, d = dx * dx + dy * dy;
            if (d == 0) return i;
            if (d < bestD) { bestD = d; best = i; }
        }
        return best;
    }

    // ---- a robot's script, as a char[] s
    public static int events(char[] s) { return s[0]; }
    public static int op(char[] s, int i) { return s[1 + 2 * i] >> 12; }
    public static int round(char[] s, int i) { return s[1 + 2 * i] & 0xFFF; }
    public static int arg(char[] s, int i) { return s[2 + 2 * i] >> 12; }
    public static int x(char[] s, int i) { return (s[2 + 2 * i] >> 6) & 63; }
    public static int y(char[] s, int i) { return s[2 + 2 * i] & 63; }
    /** Index of the first TX record. */
    public static int txStart(char[] s) { return 1 + 2 * s[0]; }
    public static final int PRE = 0x8000;
    public static int txRound(char[] s, int t) { return s[t] & 0xFFF; }
    public static boolean txPre(char[] s, int t) { return (s[t] & PRE) != 0; }
    public static int txFee(char[] s, int t) { return s[t + 1]; }
    public static int[] txMessage(char[] s, int t) {
        return new int[]{s[t + 2] << 16 | s[t + 3], s[t + 4] << 16 | s[t + 5], s[t + 6] << 16 | s[t + 7], s[t + 8] << 16 | s[t + 9],
                         s[t + 10] << 16 | s[t + 11], s[t + 12] << 16 | s[t + 13], s[t + 14] << 16 | s[t + 15]};
    }
    public static final int TX_LEN = 16;
}
