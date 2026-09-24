package cand32;

import battlecode.common.*;

/**
 * Blockchain codec. A message is 7 ints; both teams share the chain and messages carry no sender,
 * so word 6 is an authenticator: a hash of words 0-5, the round it was posted in and a team salt.
 * A message that fails the check is ignored. Words: [0] type, [1..5] payload, [6] auth.
 * Costs: submit 100 bytecodes + the fee (soup); read 100 per block.
 */
public final strictfp class Comms {
    private Comms() {}

    public static final int HQ_LOC = 1;        // payload: x, y of our HQ
    public static final int ENEMY_HQ = 2;      // payload: x, y of the enemy HQ (sighted)
    public static final int SOUP = 3;          // payload: x, y, amount (a soup deposit worth walking to)
    public static final int MAP_ORIGIN = 4;    // payload: minX, minY
    public static final int SYMMETRY = 5;      // payload: surviving hypothesis bits

    static final int SALT = 0x5eed2020;

    public static int auth(int[] m, int round, Team team) {
        int h = SALT ^ (team.ordinal() * 0x9E3779B9) ^ (round * 0x85EBCA6B);
        for (int i = 0; i < 6; i++) { h ^= m[i]; h *= 0x27D4EB2F; h ^= h >>> 15; }
        return h;
    }

    /** Build a message of the given type; payload may be shorter than 5. */
    public static int[] make(int type, int round, Team team, int... payload) {
        int[] m = new int[7];
        m[0] = type;
        for (int i = 0; i < payload.length && i < 5; i++) m[1 + i] = payload[i];
        m[6] = auth(m, round, team);
        return m;
    }

    /** True if m was posted by `team` in round `round` (the block index it appears in). */
    public static boolean ours(int[] m, int round, Team team) {
        return m != null && m.length == 7 && m[6] == auth(m, round, team);
    }
}
