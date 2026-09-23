package bot;

import battlecode.common.Team;

/** The blockchain codec: our messages authenticate, everything else is rejected. Run via tools/unit-tests.sh */
public class CommsTest {
    static int fails = 0;
    static void check(boolean ok, String what) { if (!ok) { fails++; System.out.println("FAIL " + what); } }

    public static void main(String[] a) {
        int[] m = Comms.make(Comms.HQ_LOC, 12, Team.A, 10345, 20777);
        check(m.length == 7, "message is 7 ints");
        check(m[0] == Comms.HQ_LOC && m[1] == 10345 && m[2] == 20777 && m[3] == 0, "type and payload laid out");
        check(Comms.ours(m, 12, Team.A), "authenticates for the team and round it was made for");
        check(!Comms.ours(m, 13, Team.A), "a different round is rejected");
        check(!Comms.ours(m, 12, Team.B), "the other team is rejected");
        int[] t = m.clone(); t[1]++;
        check(!Comms.ours(t, 12, Team.A), "a tampered payload is rejected");
        check(!Comms.ours(null, 12, Team.A) && !Comms.ours(new int[6], 12, Team.A), "null and short messages are rejected");
        // salts differ per team even with identical payloads
        check(Comms.make(Comms.SOUP, 5, Team.A, 1, 2, 3)[6] != Comms.make(Comms.SOUP, 5, Team.B, 1, 2, 3)[6], "team salt changes the auth word");
        // a flat opponent message (all zeros) never authenticates by accident for rounds 1..2000
        int hits = 0; for (int r = 1; r <= 2000; r++) if (Comms.ours(new int[7], r, Team.A)) hits++;
        check(hits == 0, "zero message never authenticates, hits=" + hits);
        System.out.println(fails == 0 ? "CommsTest OK" : "CommsTest FAILED " + fails);
        if (fails != 0) System.exit(1);
    }
}
