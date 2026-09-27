package puppet;

import battlecode.schema.*;
import com.google.flatbuffers.Table;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;

/**
 * Replay (.bc20) -> raw event lines, the input of tools/puppet.py (PROMPTS 59-60).
 *
 * One line per record, each vector in its own (engine append) order. The order ACROSS vectors of a round
 * is meaningless: moves, actions, dirt and soup changes are separate vectors, and only the in-vector order
 * is the order in which the engine appended them. Absolute coordinates; team 1 = A, 2 = B, 0 = neutral.
 *
 *   H <pkgA> <pkgB> <map> <w> <h> <seed> <minX> <minY>     seed = GameMap.randomSeed (the -Dbc.game.seed used)
 *   B <id> <team> <type> <x> <y>                            initial bodies, map order
 *   F <x> <y>                                               initially flooded tiles
 *   R <round> <soupA> <soupB>                               starts a round; soups at the END of the round
 *   S <id> <team> <type> <x> <y>                            spawnedBodies
 *   M <id> <x> <y>                                          movedIDs / movedLocs
 *   A <actor> <action> <target>                             actionIDs / actions / actionTargets (schema Action codes)
 *   D <x> <y> <delta>                                       dirtChangedLocs / dirtChanges
 *   W <x> <y>                                               waterChangedLocs (a flood-status toggle)
 *   U <x> <y> <delta>                                       soupChangedLocs / soupChanges
 *   X <id>                                                  diedIDs
 *   T <cost> <w0> .. <w6>                                   newMessages (submission order): raw int vector at vtable 38, costs at 36
 *   K <cost> <w0> .. <w6>                                   broadcastedMessages (the minted block): raw at 42, costs at 40
 *   C <id> <bytecodes>                                      bytecodeIDs / bytecodesUsed
 *   E <winner> <rounds>                                     MatchFooter
 *
 * The messages are read raw: the engine writes an int vector of char codes (messages joined by ' ',
 * words by '_') where the schema declares [string], so the generated accessor returns garbage.
 */
public class RawEvents {
    /** Raw access to a Round table's fields; placed on the round by EventWrapper.e(Table). */
    static final class Raw extends Table {
        int[] ints(int vt) {
            int o = __offset(vt); if (o == 0) return new int[0];
            int n = __vector_len(o), v = __vector(o); int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = bb.getInt(v + 4 * i);
            return a;
        }
    }

    static PrintWriter out;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) { System.err.println("usage: RawEvents <replay.bc20>"); System.exit(2); }
        byte[] raw = Files.readAllBytes(new File(args[0]).toPath());
        if (raw.length > 1 && (raw[0] & 0xff) == 0x1f && (raw[1] & 0xff) == 0x8b) {
            try (GZIPInputStream g = new GZIPInputStream(new ByteArrayInputStream(raw)); ByteArrayOutputStream o = new ByteArrayOutputStream()) {
                byte[] b = new byte[1 << 16]; int n; while ((n = g.read(b)) > 0) o.write(b, 0, n); raw = o.toByteArray();
            }
        }
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out, "US-ASCII"), 1 << 16));
        GameWrapper gw = GameWrapper.getRootAsGameWrapper(ByteBuffer.wrap(raw));
        String[] pkg = {"-", "-", "-"};
        int matches = 0;
        for (int i = 0; i < gw.eventsLength(); i++) {
            EventWrapper ew = gw.events(i);
            switch (ew.eType()) {
                case Event.GameHeader: {
                    GameHeader h = (GameHeader) ew.e(new GameHeader());
                    for (int t = 0; t < h.teamsLength(); t++) { TeamData td = h.teams(t); if (td.teamID() >= 1 && td.teamID() <= 2) pkg[td.teamID()] = td.packageName(); }
                    break; }
                case Event.MatchHeader: {
                    if (++matches > 1) { System.err.println("RawEvents: more than one match in " + args[0]); System.exit(3); }
                    GameMap m = ((MatchHeader) ew.e(new MatchHeader())).map();
                    int minX = m.minCorner().x(), minY = m.minCorner().y();
                    out.printf("H %s %s %s %d %d %d %d %d%n", pkg[1], pkg[2], m.name(), m.maxCorner().x() - minX, m.maxCorner().y() - minY, m.randomSeed(), minX, minY);
                    SpawnedBodyTable sb = m.bodies(); VecTable l = sb.locs();
                    for (int k = 0; k < sb.robotIDsLength(); k++) out.printf("B %d %d %d %d %d%n", sb.robotIDs(k), sb.teamIDs(k), sb.types(k), l.xs(k), l.ys(k));
                    int w = m.maxCorner().x() - minX;
                    for (int k = 0; k < m.waterLength(); k++) if (m.water(k)) out.printf("F %d %d%n", k % w + minX, k / w + minY);
                    break; }
                case Event.Round: round((Round) ew.e(new Round()), (Raw) ew.e(new Raw())); break;
                case Event.MatchFooter: {
                    MatchFooter f = (MatchFooter) ew.e(new MatchFooter());
                    out.printf("E %d %d%n", f.winner(), f.totalRounds());
                    break; }
                default: break;
            }
        }
        out.flush();
        if (out.checkError()) System.exit(1);
    }

    static void round(Round rd, Raw raw) {
        int[] soup = new int[3];
        for (int i = 0; i < rd.teamIDsLength(); i++) { int t = rd.teamIDs(i); if (t >= 1 && t <= 2) soup[t] = rd.teamSoups(i); }
        out.printf("R %d %d %d%n", rd.roundID(), soup[1], soup[2]);
        SpawnedBodyTable sb = rd.spawnedBodies();
        if (sb != null) { VecTable l = sb.locs(); for (int k = 0; k < sb.robotIDsLength(); k++) out.printf("S %d %d %d %d %d%n", sb.robotIDs(k), sb.teamIDs(k), sb.types(k), l.xs(k), l.ys(k)); }
        VecTable ml = rd.movedLocs();
        for (int k = 0; k < rd.movedIDsLength(); k++) out.printf("M %d %d %d%n", rd.movedIDs(k), ml.xs(k), ml.ys(k));
        for (int k = 0; k < rd.actionIDsLength(); k++) out.printf("A %d %d %d%n", rd.actionIDs(k), rd.actions(k), rd.actionTargets(k));
        VecTable dl = rd.dirtChangedLocs();
        for (int k = 0; k < rd.dirtChangesLength(); k++) out.printf("D %d %d %d%n", dl.xs(k), dl.ys(k), rd.dirtChanges(k));
        VecTable wl = rd.waterChangedLocs();
        if (wl != null) for (int k = 0; k < wl.xsLength(); k++) out.printf("W %d %d%n", wl.xs(k), wl.ys(k));
        VecTable sl = rd.soupChangedLocs();
        for (int k = 0; k < rd.soupChangesLength(); k++) out.printf("U %d %d %d%n", sl.xs(k), sl.ys(k), rd.soupChanges(k));
        for (int k = 0; k < rd.diedIDsLength(); k++) out.printf("X %d%n", rd.diedIDs(k));
        messages('T', raw.ints(38), raw.ints(36), rd.roundID());
        messages('K', raw.ints(42), raw.ints(40), rd.roundID());
        for (int k = 0; k < rd.bytecodeIDsLength(); k++) out.printf("C %d %d%n", rd.bytecodeIDs(k), rd.bytecodesUsed(k));
    }

    /** chars: the messages' serialized words joined by '_', each message followed by ' '. */
    static void messages(char tag, int[] chars, int[] costs, int round) {
        int n = 0, start = 0;
        for (int i = 0; i <= chars.length; i++) {
            if (i < chars.length && chars[i] != ' ') continue;
            if (i > start) {
                StringBuilder s = new StringBuilder().append(tag).append(' ').append(n < costs.length ? costs[n] : -1).append(' ');
                for (int j = start; j < i; j++) { char c = (char) chars[j]; s.append(c == '_' ? ' ' : c); }
                out.println(s);
                n++;
            }
            start = i + 1;
        }
        if (n != costs.length) { System.err.printf("RawEvents: r%d %c: %d messages but %d costs%n", round, tag, n, costs.length); System.exit(4); }
    }
}
