package puppet;

import battlecode.common.Team;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * The fixture contract, Java side (PROMPTS 59-60): Script decodes test/puppet/golden.properties, which
 * tools/test_tools.py regenerates byte for byte from tools/puppet.py; find() keeps its window and fallback; our
 * Comms.auth gives the value tools/puppet.py's port asserts. Run by tools/unit-tests.sh.
 */
public class PuppetTest {
    static int fails = 0;
    static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL " + what); } }

    public static void main(String[] args) throws Exception {
        String path = args.length > 0 ? args[0] : "test/puppet/golden.properties";
        Properties p = new Properties();
        try (FileInputStream f = new FileInputStream(path)) { p.load(f); }
        String idx = p.getProperty("bc.testing.pup.idx"), dat = p.getProperty("bc.testing.pup.dat");
        check(idx != null && dat != null && p.size() == 2, "golden: exactly the idx and dat keys");
        check(p.getProperty("bc.testing.pup.cutoff") == null, "golden: the cutoff is never in the fixture");

        // header
        check(Script.version(idx) == 1 && Script.side(idx) == 1, "header: version 1, side B");
        check(Script.hqX(idx) == 5 && Script.hqY(idx) == 5, "header: hq (5,5)");
        check(Script.width(idx) == 64 && Script.height(idx) == 64, "header: 64x64");
        check(Script.records(idx) == 4, "idx: four robots and a sentinel");
        check(Script.offset(idx, Script.records(idx)) == dat.length(), "idx: the sentinel's offset is |dat|");
        for (int i = 1; i < Script.records(idx); i++)
            check(idx.charAt(4 + 4 * (i - 1)) <= idx.charAt(4 + 4 * i), "idx: sorted by (type, keyRound) at " + i);

        // find: exact, delayed within the window, nearest tile, misses
        int hq = Script.find(idx, 0, 1, 5, 5), m1 = Script.find(idx, 1, 4, 6, 6), m2 = Script.find(idx, 1, 4, 9, 9), dr = Script.find(idx, 7, 30, 10, 12);
        check(hq == 0 && m1 == 1 && m2 == 2 && dr == 3, "find: exact keys " + hq + " " + m1 + " " + m2 + " " + dr);
        check(Script.find(idx, 7, 50, 10, 12) == 3, "find: first turn 20 rounds after the key round");
        check(Script.find(idx, 7, 51, 10, 12) == -1, "find: 21 rounds late is a miss");
        check(Script.find(idx, 7, 29, 10, 12) == -1, "find: never a key round after the first turn");
        check(Script.find(idx, 1, 4, 8, 9) == 2, "find: the nearest tile (d2 1) when no tile is exact");
        check(Script.find(idx, 1, 5, 4, 4) == 1, "find: d2 8 is within the fallback");
        check(Script.find(idx, 1, 4, 3, 3) == -1, "find: d2 18 is a miss");
        check(Script.find(idx, 6, 4, 6, 6) == -1, "find: the type must match");

        // scripts
        char[] s = script(idx, dat, hq);
        check(Script.events(s) == 1 && Script.op(s, 0) == Script.BUILD && Script.round(s, 0) == 3 && Script.arg(s, 0) == 1
              && Script.x(s, 0) == 6 && Script.y(s, 0) == 6, "hq: BUILD MINER r3 (6,6)");
        int t = Script.txStart(s);
        int[] m = Script.txMessage(s, t);
        check(Script.txRound(s, t) == 1 && Script.txFee(s, t) == 1 && m.length == 7 && m[0] == 1 && m[5] == 6 && m[6] == -7, "hq: TX r1 fee 1");
        check(!Script.txPre(s, t), "hq: the first TX goes after the act");
        t += Script.TX_LEN; m = Script.txMessage(s, t);
        check(Script.txRound(s, t) == 3 && Script.txPre(s, t) && Script.txFee(s, t) == 2 && m[0] == 7 && m[6] == 7, "hq: TX r3 fee 2 before the act (PRE masked off the round)");
        check(t + Script.TX_LEN == s.length, "hq: two TX records");
        s = script(idx, dat, m1);
        check(Script.events(s) == 3 && Script.op(s, 0) == Script.MOVE && Script.op(s, 1) == Script.MINE && Script.op(s, 2) == Script.DIE
              && Script.round(s, 2) == 20 && Script.txStart(s) == s.length, "miner: MOVE MINE DIE, no TX");
        s = script(idx, dat, m2);
        check(Script.op(s, 0) == Script.MOVE && Script.arg(s, 0) == 1 && Script.x(s, 0) == 10, "miner 2: MOVE may enter water");
        s = script(idx, dat, dr);
        check(Script.events(s) == 3 && Script.op(s, 0) == Script.PICK_OTHER && Script.arg(s, 0) == 6 && Script.x(s, 0) == 11 && Script.y(s, 0) == 12,
              "drone: PICK_OTHER landscaper at (11,12)");
        check(Script.op(s, 1) == Script.DROP && Script.x(s, 1) == 63 && Script.y(s, 1) == 0, "drone: DROP at (63,0)");
        check(Script.op(s, 2) == Script.PLACE && Script.round(s, 2) == 4095 && Script.x(s, 2) == 63 && Script.y(s, 2) == 63, "drone: PLACE r4095 (63,63)");
        t = Script.txStart(s); m = Script.txMessage(s, t);
        check(Script.txRound(s, t) == 45 && !Script.txPre(s, t) && Script.txFee(s, t) == 65535, "drone: TX r45 fee 65535");
        check(m[0] == 65536 && m[1] == -1 && m[2] == 0 && m[3] == 70000 && m[4] == Integer.MAX_VALUE && m[5] == Integer.MIN_VALUE && m[6] == 42,
              "drone: TX words round-trip through the hi/lo chars");

        // our auth, the value tools/puppet.py's port must give (test_tools.py)
        check(g_iter13.Comms.auth(new int[]{1, 2, 3, 4, 5, 6, 0}, 100, Team.B) == 1669466758, "auth: golden value");

        System.out.println("PuppetTest: " + (fails == 0 ? "OK" : "FAILED " + fails));
        if (fails > 0) System.exit(1);
    }

    static char[] script(String idx, String dat, int rec) {
        return dat.substring(Script.offset(idx, rec), Script.offset(idx, rec + 1)).toCharArray();
    }
}
