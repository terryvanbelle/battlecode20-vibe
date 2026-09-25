package replaydump;

import battlecode.schema.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

/**
 * Battlecode 2020 replay (.bc20) -> text. The project's microscope.
 *
 * Reads the gzipped FlatBuffers GameWrapper and reconstructs every robot and every tile round by
 * round from the engine's own event stream: spawns, moves, deaths, the fourteen Action codes,
 * dirt/water/soup changes, global pollution, blockchain traffic, per-robot bytecodes and the
 * robots' own System.out lines. Prints only engine-level facts.
 *
 * Usage: ReplayDump <file.bc20> [flags]
 *   --every N        team aggregate line every N rounds (default 50; 0 = off)
 *   --from R --to R  print every event in this round window
 *   --robot ID       per-turn track of one robot
 *   --map N          ASCII board every N rounds;  --map-at R  board once at round R (repeatable)
 *   --logs PATTERN   robot System.out lines matching PATTERN (regex), inside --from/--to (default: all)
 *   --logs-team A|B  restrict --logs to one team
 *   --metrics        CSV of per-round team aggregates (every 10 rounds unless --every)
 *   --bytecode       per-type bytecode summary (max used, rounds at/over the limit)
 *   --navstats       moves, A-B-A oscillations, coverage, first contact with the enemy HQ
 *   --threat A|B     CSV every 25 rounds: enemy landscapers/drones near that team's HQ, HQ buried dirt
 *   --ring N         every N rounds, each HQ's ring tiles: elevation (F if flooded), min, and the water level
 *   --ringd D        Chebyshev distance of the ring for --ring (default 1; 2 for the Iteration 6 citadel)
 *   --elev-at R      elevation grid (two chars per tile, clipped to -9..99, "~~" water) at round R
 *   --quiet          suppress aggregates
 *
 * Team ids in the file: 0 neutral (cows), 1 = A, 2 = B. Locations are absolute (the map origin is
 * random); the board and the event lines print coordinates relative to minCorner.
 */
public class ReplayDump {
    static int every = 50, mapEvery = 0, fromRound = -1, toRound = -1, trackId = -1;
    static boolean metrics = false, quiet = false, bytecodeSummary = false, navStats = false;
    static int threatTeam = 0, ringEvery = 0, ringD = 1;
    static Pattern logPat = null; static int logsTeam = -1;
    static TreeSet<Integer> mapAt = new TreeSet<>(), elevAt = new TreeSet<>();
    static int seatTeam = 0; static TreeSet<Integer> seatAt = new TreeSet<>();   // --seats A|B --seats-at N: the unseated ring tiles and what is around them

    static final class Robot {
        int id, team, x, y, spawnRound; byte type; boolean alive = true;
        int bytecodes, bcOver, moves, buried;      // buried: dirt on this building
        int digs, deposits, mines, soupDeposits, pickups, drops, shots;
    }
    static final String[] TYPE = {"HQ", "MINER", "REFINERY", "VAPORATOR", "DESIGN", "FULFIL", "LANDSCAPER", "DRONE", "NETGUN", "COW"};
    static final int[] LIMIT = {20000, 10000, 5000, 5000, 5000, 5000, 10000, 10000, 7000, 0};
    static final int[] COST = {0, 70, 200, 500, 150, 150, 150, 150, 250, 0};
    static final char[][] GLYPH = {{'?','?','?','?','?','?','?','?','?','c'}, {'H','M','R','V','D','F','L','A','N','c'}, {'h','m','r','v','d','f','l','a','n','c'}};
    static Map<Integer, Robot> bots = new HashMap<>();
    static int minX, minY, width, height, maxRounds;
    static int[] dirt; static boolean[] water; static int[] soup;
    static int globalPollution = 0, flooded = 0, lastRound = 0;
    static String mapName = "?";
    static String[] teamName = {"neutral", "A", "B"}, teamPkg = {"", "?", "?"};

    // per-team cumulative counters
    static int[] teamSoup = new int[3];
    static long[] spawned = new long[3], spawnCost = new long[3], died = new long[3], drowned = new long[3], shotDown = new long[3], buriedDeaths = new long[3], excDeaths = new long[3],
                  mines = new long[3], soupDeps = new long[3], refines = new long[3], digs = new long[3], dirtDeps = new long[3], pickups = new long[3], drops = new long[3], shots = new long[3],
                  moves = new long[3], aba = new long[3], bcOverRounds = new long[3];
    static long msgsMinted = 0, msgsSubmitted = 0, feesPaid = 0;
    static int[][] bcMax = new int[3][10]; static long[][] bcOverByType = new long[3][10];
    static boolean[][] visited; static int[] firstContact = {-1, -1, -1};
    static long[] unitsLong = new long[3], unitsIdle = new long[3], unitMoves = new long[3];
    static Map<Integer, int[]> prev2 = new HashMap<>();

    public static void main(String[] args) throws Exception {
        if (args.length < 1) { System.err.println("usage: ReplayDump <file.bc20> [flags]"); System.exit(2); }
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "--every": every = Integer.parseInt(args[++i]); break;
                case "--map": mapEvery = Integer.parseInt(args[++i]); break;
                case "--map-at": mapAt.add(Integer.parseInt(args[++i])); break;
                case "--elev-at": elevAt.add(Integer.parseInt(args[++i])); quiet = true; break;
                case "--ring": ringEvery = Integer.parseInt(args[++i]); quiet = true; break;
                case "--seats": seatTeam = args[++i].equals("A") ? 1 : 2; quiet = true; break;
                case "--seats-at": seatAt.add(Integer.parseInt(args[++i])); break;
                case "--ringd": ringD = Integer.parseInt(args[++i]); break;
                case "--from": fromRound = Integer.parseInt(args[++i]); break;
                case "--to": toRound = Integer.parseInt(args[++i]); break;
                case "--robot": trackId = Integer.parseInt(args[++i]); break;
                case "--logs": logPat = Pattern.compile(args[++i]); break;
                case "--logs-team": logsTeam = args[++i].equals("A") ? 1 : 2; break;
                case "--metrics": metrics = true; quiet = true; break;
                case "--bytecode": bytecodeSummary = true; break;
                case "--navstats": navStats = true; break;
                case "--threat": threatTeam = args[++i].equals("A") ? 1 : 2; quiet = true; break;
                case "--quiet": quiet = true; break;
                default: System.err.println("unknown flag " + args[i]); System.exit(2);
            }
        }
        if (metrics && every == 50) every = 10;
        byte[] raw = Files.readAllBytes(new File(args[0]).toPath());
        if ((raw[0] & 0xff) == 0x1f && (raw[1] & 0xff) == 0x8b) {
            try (GZIPInputStream g = new GZIPInputStream(new ByteArrayInputStream(raw)); ByteArrayOutputStream o = new ByteArrayOutputStream()) {
                byte[] b = new byte[1 << 16]; int n; while ((n = g.read(b)) > 0) o.write(b, 0, n); raw = o.toByteArray();
            }
        }
        GameWrapper gw = GameWrapper.getRootAsGameWrapper(ByteBuffer.wrap(raw));
        for (int i = 0; i < gw.eventsLength(); i++) {
            EventWrapper ew = gw.events(i);
            switch (ew.eType()) {
                case Event.GameHeader: onGameHeader((GameHeader) ew.e(new GameHeader())); break;
                case Event.MatchHeader: onMatchHeader((MatchHeader) ew.e(new MatchHeader())); break;
                case Event.Round: onRound((Round) ew.e(new Round())); break;
                case Event.MatchFooter: onMatchFooter((MatchFooter) ew.e(new MatchFooter())); break;
                default: break;
            }
        }
    }

    static void onGameHeader(GameHeader h) {
        for (int i = 0; i < h.teamsLength(); i++) {
            TeamData t = h.teams(i);
            if (t.teamID() >= 1 && t.teamID() <= 2) { teamName[t.teamID()] = t.name(); teamPkg[t.teamID()] = t.packageName(); }
        }
        if (!metrics && threatTeam == 0) System.out.printf("GAME spec=%s  A=%s (%s)  B=%s (%s)%n", h.specVersion(), teamName[1], teamPkg[1], teamName[2], teamPkg[2]);
    }

    static void onMatchHeader(MatchHeader mh) {
        GameMap m = mh.map();
        mapName = m.name(); maxRounds = mh.maxRounds();
        minX = m.minCorner().x(); minY = m.minCorner().y();
        width = m.maxCorner().x() - minX; height = m.maxCorner().y() - minY;
        dirt = new int[width * height]; water = new boolean[width * height]; soup = new int[width * height];
        for (int i = 0; i < m.dirtLength(); i++) dirt[i] = m.dirt(i);
        for (int i = 0; i < m.waterLength(); i++) water[i] = m.water(i);
        for (int i = 0; i < m.soupLength(); i++) soup[i] = m.soup(i);
        flooded = 0; for (boolean w : water) if (w) flooded++;
        bots.clear(); prev2.clear(); visited = new boolean[3][width * height];
        spawnBodies(m.bodies(), 0);
        if (threatTeam != 0) { System.out.println("round,hqAlive,hqBuried,enemyLandscapersR8,enemyDronesR15,enemyMinersR8,ownLandscapersR8"); return; }
        if (metrics) { printMetricsHeader(); return; }
        long totalSoup = 0; int cows = 0; for (int s : soup) totalSoup += s; for (Robot r : bots.values()) if (r.type == 9) cows++;
        System.out.printf("MATCH map=%s size=%dx%d origin=(%d,%d) maxRounds=%d initialWater=%d flooded=%d soup=%d cows=%d symmetry=%s%n",
            mapName, width, height, minX, minY, maxRounds, m.initialWater(), flooded, totalSoup, cows, detectSymmetry());
        for (Robot r : sortedBots()) if (r.type == 0)
            System.out.printf("  HQ #%d team=%s at rel(%d,%d) elevation=%d%n", r.id, teamName[r.team], r.x - minX, r.y - minY, dirt[(r.x - minX) + (r.y - minY) * width]);
    }

    static String detectSymmetry() {
        boolean rot = true, hor = true, ver = true;
        for (int x = 0; x < width && (rot || hor || ver); x++) for (int y = 0; y < height; y++) {
            int i = x + y * width, r = (width - 1 - x) + (height - 1 - y) * width, h = (width - 1 - x) + y * width, v = x + (height - 1 - y) * width;
            if (dirt[i] != dirt[r] || water[i] != water[r] || soup[i] != soup[r]) rot = false;
            if (dirt[i] != dirt[h] || water[i] != water[h] || soup[i] != soup[h]) hor = false;
            if (dirt[i] != dirt[v] || water[i] != water[v] || soup[i] != soup[v]) ver = false;
        }
        StringBuilder s = new StringBuilder();
        if (rot) s.append("rotation "); if (hor) s.append("mirror-x "); if (ver) s.append("mirror-y ");
        return s.length() == 0 ? "none?" : s.toString().trim();
    }

    static void spawnBodies(SpawnedBodyTable sb, int round) {
        if (sb == null) return;
        VecTable locs = sb.locs();
        for (int i = 0; i < sb.robotIDsLength(); i++) {
            Robot r = new Robot();
            r.id = sb.robotIDs(i); r.team = sb.teamIDs(i); r.type = sb.types(i);
            r.x = locs.xs(i); r.y = locs.ys(i); r.spawnRound = round;
            bots.put(r.id, r); markVisit(r);
            spawned[r.team]++; spawnCost[r.team] += COST[Math.min(r.type, 9)];
            if (inWindow(round) || r.id == trackId) System.out.printf("  r%d SPAWN %s%n", round, desc(r));
        }
    }

    static int idx(int x, int y) { int px = x - minX, py = y - minY; return (px < 0 || py < 0 || px >= width || py >= height) ? -1 : px + py * width; }

    static void onRound(Round rd) {
        int round = rd.roundID(); lastRound = round;
        for (int i = 0; i < rd.teamIDsLength(); i++) { int t = rd.teamIDs(i); if (t >= 1 && t <= 2) teamSoup[t] = rd.teamSoups(i); }
        globalPollution = rd.globalPollution();
        // moves
        VecTable ml = rd.movedLocs();
        for (int i = 0; i < rd.movedIDsLength(); i++) {
            Robot r = bots.get(rd.movedIDs(i));
            if (r == null) continue;
            int nx = ml.xs(i), ny = ml.ys(i);
            if (nx == r.x && ny == r.y) continue;                     // a held unit "moves" with its drone: same tile, skip
            int[] pv = prev2.get(r.id);
            if (pv != null && pv[0] == nx && pv[1] == ny) aba[r.team]++;
            if (pv == null) { pv = new int[4]; prev2.put(r.id, pv); }
            pv[0] = pv[2]; pv[1] = pv[3]; pv[2] = r.x; pv[3] = r.y;
            r.x = nx; r.y = ny; r.moves++; moves[r.team]++; markVisit(r);
            if (navStats && firstContact[r.team] < 0 && r.team > 0) for (Robot e : bots.values()) if (e.type == 0 && e.team > 0 && e.team != r.team && d2(e, r) <= 35) { firstContact[r.team] = round; break; }
            if (r.id == trackId && inWindowOrAll(round)) System.out.printf("  r%d MOVE #%d -> (%d,%d)%n", round, r.id, r.x - minX, r.y - minY);
        }
        spawnBodies(rd.spawnedBodies(), round);
        // actions
        for (int i = 0; i < rd.actionIDsLength(); i++) {
            int id = rd.actionIDs(i); byte a = rd.actions(i); int tgt = rd.actionTargets(i);
            Robot r = bots.get(id); int team = r == null ? 0 : r.team; Robot t = bots.get(tgt);
            boolean show = inWindow(round) || (r != null && r.id == trackId && inWindowOrAll(round));
            switch (a) {
                case Action.MINE_SOUP: mines[team]++; if (r != null) r.mines++; if (show) System.out.printf("  r%d MINE %s%n", round, desc(r)); break;
                case Action.DEPOSIT_SOUP: soupDeps[team]++; if (r != null) r.soupDeposits++; if (show) System.out.printf("  r%d DEPOSIT_SOUP %s -> %s%n", round, desc(r), desc(t)); break;
                case Action.REFINE_SOUP: refines[team]++; break;
                case Action.DIG_DIRT: digs[team]++; if (r != null) r.digs++; if (t != null && tgt >= 0) t.buried = Math.max(0, t.buried - 1);
                    if (show) System.out.printf("  r%d DIG %s%s%n", round, desc(r), tgt >= 0 ? " from " + desc(t) : ""); break;
                case Action.DEPOSIT_DIRT: dirtDeps[team]++; if (r != null) r.deposits++; if (t != null && tgt >= 0) t.buried++;
                    if (show || (t != null && t.type == 0 && !quiet && inWindowOrAll(round) && trackId < 0 && fromRound >= 0)) System.out.printf("  r%d DEPOSIT_DIRT %s%s%n", round, desc(r), tgt >= 0 ? " onto " + desc(t) + " buried=" + t.buried : ""); break;
                case Action.PICK_UNIT: pickups[team]++; if (r != null) r.pickups++; if (show) System.out.printf("  r%d PICK %s -> %s%n", round, desc(r), desc(t)); break;
                case Action.DROP_UNIT: drops[team]++; if (r != null) r.drops++; if (show) System.out.printf("  r%d DROP %s -> %s%n", round, desc(r), desc(t)); break;
                case Action.SPAWN_UNIT: if (show) System.out.printf("  r%d BUILD %s -> %s%n", round, desc(r), desc(t)); break;
                case Action.SHOOT: shots[team]++; if (r != null) r.shots++; if (show || inWindow(round)) System.out.printf("  r%d SHOOT %s -> %s%n", round, desc(r), desc(t)); break;
                case Action.DIE_DROWN: drowned[team]++; if (show) System.out.printf("  r%d DROWNED %s%n", round, desc(r)); break;
                case Action.DIE_SHOT: shotDown[team]++; break;
                case Action.DIE_TOO_MUCH_DIRT: buriedDeaths[team]++; if (show) System.out.printf("  r%d BURIED %s%n", round, desc(r)); break;
                case Action.DIE_SUICIDE: break;
                case Action.DIE_EXCEPTION: excDeaths[team]++; System.out.printf("  r%d DIE_EXCEPTION %s%n", round, desc(r)); break;
                default: break;
            }
        }
        // terrain
        VecTable dl = rd.dirtChangedLocs();
        for (int i = 0; i < rd.dirtChangesLength(); i++) { int k = idx(dl.xs(i), dl.ys(i)); if (k >= 0) dirt[k] += rd.dirtChanges(i); }
        VecTable wl = rd.waterChangedLocs();
        if (wl != null) for (int i = 0; i < wl.xsLength(); i++) { int k = idx(wl.xs(i), wl.ys(i)); if (k >= 0) { water[k] = !water[k]; flooded += water[k] ? 1 : -1; } }
        VecTable sl = rd.soupChangedLocs();
        for (int i = 0; i < rd.soupChangesLength(); i++) { int k = idx(sl.xs(i), sl.ys(i)); if (k >= 0) soup[k] += rd.soupChanges(i); }
        // blockchain
        msgsSubmitted += rd.newMessagesLength(); for (int i = 0; i < rd.newMessagesCostsLength(); i++) feesPaid += rd.newMessagesCosts(i);
        msgsMinted += rd.broadcastedMessagesLength();
        if (inWindow(round)) for (int i = 0; i < rd.broadcastedMessagesLength(); i++) { String msg; try { msg = rd.broadcastedMessages(i); } catch (RuntimeException e) { msg = "?"; } System.out.printf("  r%d BLOCK cost=%d %s%n", round, i < rd.broadcastedMessagesCostsLength() ? rd.broadcastedMessagesCosts(i) : -1, msg); }
        // bytecodes
        for (int i = 0; i < rd.bytecodeIDsLength(); i++) {
            Robot r = bots.get(rd.bytecodeIDs(i)); if (r == null) continue;
            r.bytecodes = rd.bytecodesUsed(i); int t = Math.min(r.type, 9);
            bcMax[r.team][t] = Math.max(bcMax[r.team][t], r.bytecodes);
            if (LIMIT[t] > 0 && r.bytecodes >= LIMIT[t]) { r.bcOver++; bcOverRounds[r.team]++; bcOverByType[r.team][t]++;
                if (inWindow(round) || bytecodeSummary) System.out.printf("  r%d BYTECODE-OVERRUN %s used=%d limit=%d%n", round, desc(r), r.bytecodes, LIMIT[t]); }
        }
        // deaths
        for (int i = 0; i < rd.diedIDsLength(); i++) {
            Robot r = bots.get(rd.diedIDs(i));
            if (r != null) { r.alive = false; died[r.team]++; if (navStats) tallyUnit(r, round);
                if (inWindow(round) || r.id == trackId) System.out.printf("  r%d DIED %s (lived %d rounds)%n", round, desc(r), round - r.spawnRound);
                bots.remove(r.id); }
        }
        // logs
        if (logPat != null && inWindowOrAll(round)) {
            String logs = rd.logs();
            if (logs != null && !logs.isEmpty()) for (String line : logs.split("\n")) {
                if (logsTeam == 1 && !line.startsWith("[A:")) continue;
                if (logsTeam == 2 && !line.startsWith("[B:")) continue;
                if (logPat.matcher(line).find()) System.out.println("  LOG " + line);
            }
        }
        if (trackId >= 0 && inWindowOrAll(round)) { Robot r = bots.get(trackId); if (r != null && round % Math.max(1, every) == 0) System.out.printf("  r%d TRACK %s bc=%d%n", round, desc(r), r.bytecodes); }
        if (threatTeam != 0) { if (round % 25 == 0) printThreatRow(round); return; }
        if (metrics) { if (round % every == 0) printMetricsRow(round); return; }
        if (!quiet && every > 0 && round % every == 0) printAggregate(round);
        if ((mapEvery > 0 && round % mapEvery == 0) || mapAt.contains(round)) printBoard(round);
        if (elevAt.contains(round)) printElev(round);
        if (ringEvery > 0 && round % ringEvery == 0) printRing(round);
        if (seatTeam != 0 && seatAt.contains(round)) printSeats(round);
    }

    /** --seats: every ring tile of our HQ under 10 (or flooded) at the round, with its occupant, the nearest own
     *  landscaper and miner (Chebyshev), own landscapers and enemies within 3, and the dirt of the outward tiles
     *  (Chebyshev 2 from the HQ, adjacent to the seat) a seat-seeker would stand on to reach it. */
    static void printSeats(int round) {
        Robot hq = null; for (Robot r : bots.values()) if (r.type == 0 && r.team == seatTeam) hq = r;
        if (hq == null) { System.out.printf("SEAT r%d hq dead%n", round); return; }
        int open = 0;
        for (int dx = -1; dx <= 1; dx++) for (int dy = -1; dy <= 1; dy++) {
            if (dx == 0 && dy == 0) continue;
            int x = hq.x + dx, y = hq.y + dy, k = idx(x, y); if (k < 0) continue;
            if (dirt[k] >= 10 && !water[k]) continue;
            open++;
            Robot occ = null; int nl = 99, nm = 99, l3 = 0, e3 = 0;
            for (Robot r : bots.values()) {
                int d = Math.max(Math.abs(r.x - x), Math.abs(r.y - y));
                if (d == 0) occ = r;
                if (r.team == seatTeam && r.type == 6) { nl = Math.min(nl, d); if (d <= 3) l3++; }
                if (r.team == seatTeam && r.type == 1) nm = Math.min(nm, d);
                if (r.team != seatTeam && r.team != 0 && d <= 3) e3++;
            }
            StringBuilder out = new StringBuilder();
            for (int ox = -1; ox <= 1; ox++) for (int oy = -1; oy <= 1; oy++) {
                int tx = x + ox, ty = y + oy; if (Math.max(Math.abs(tx - hq.x), Math.abs(ty - hq.y)) != 2) continue;
                int t = idx(tx, ty); out.append(' ').append(t < 0 ? "X" : (water[t] ? "F" : String.valueOf(dirt[t])));
            }
            System.out.printf("SEAT r%d (%d,%d) dirt=%d%s occ=%s ownL@%d ownL<=3=%d ownM@%d enemy<=3=%d out=[%s ]%n", round, dx, dy, dirt[k], water[k] ? "F" : "",
                occ == null ? "-" : (occ.team == seatTeam ? "own" : "enemy") + ":" + TYPE[Math.min(occ.type, 9)], nl, l3, nm, e3, out);
        }
        int ownL = 0, ownM = 0, near = 0;
        for (Robot r : bots.values()) { if (r.team == seatTeam && r.type == 6) ownL++; if (r.team == seatTeam && r.type == 1) ownM++;
            if (r.team != seatTeam && r.team != 0 && Math.max(Math.abs(r.x - hq.x), Math.abs(r.y - hq.y)) <= 5) near++; }
        System.out.printf("SEAT r%d summary open=%d ownL=%d ownM=%d enemy<=5ofHQ=%d%n", round, open, ownL, ownM, near);
    }

    /** --ring: the wall race in one line per round: both HQs' ring tiles, their minimum and the water. */
    static void printRing(int round) {
        StringBuilder s = new StringBuilder(String.format("RING r%-5d water=%7.1f", round, waterLevel(round)));
        for (int t = 1; t <= 2; t++) {
            Robot hq = null; for (Robot r : bots.values()) if (r.type == 0 && r.team == t) hq = r;
            s.append(String.format("  %s:", t == 1 ? "A" : "B"));
            if (hq == null) { s.append(" dead"); continue; }
            int min = Integer.MAX_VALUE, n = 0; StringBuilder e = new StringBuilder();
            for (int dx = -ringD; dx <= ringD; dx++) for (int dy = -ringD; dy <= ringD; dy++) {
                if (Math.max(Math.abs(dx), Math.abs(dy)) != ringD) continue;
                int k = idx(hq.x + dx, hq.y + dy); if (k < 0) continue;
                n++; e.append(' ').append(dirt[k]).append(water[k] ? "F" : "");
                if (dirt[k] < min) min = dirt[k];
            }
            s.append(e).append(String.format(" | min=%d (%d tiles) hqElev=%d", min, n, dirt[idx(hq.x, hq.y)]));
        }
        System.out.println(s);
    }

    static void printElev(int round) {
        System.out.printf("ELEV r%d %s (%dx%d) water=%.1f  two chars per tile: ~~ water, -9..99 elevation (clipped), capital letter = robot (A team upper, B lower)%n", round, mapName, width, height, waterLevel(round));
        for (int y = height - 1; y >= 0; y--) {
            StringBuilder s = new StringBuilder(String.format("%3d ", y));
            for (int x = 0; x < width; x++) { int k = x + y * width; int e = Math.max(-9, Math.min(99, dirt[k]));
                Robot occ = null; for (Robot r : bots.values()) if (r.x - minX == x && r.y - minY == y) { occ = r; break; }
                if (occ != null) s.append(GLYPH[occ.team][Math.min(occ.type, 9)]).append(water[k] ? '~' : ' ');
                else if (water[k]) s.append("~~"); else s.append(String.format("%2d", e)); }
            System.out.println(s);
        }
        StringBuilder ax = new StringBuilder("    "); for (int x = 0; x < width; x++) ax.append(x % 5 == 0 ? String.format("%-2d", x % 100) : "  "); System.out.println(ax);
    }

    static void onMatchFooter(MatchFooter f) {
        int w = f.winner();
        if (metrics) { if (f.totalRounds() % every != 0) printMetricsRow(f.totalRounds()); System.out.printf("# winner=%s rounds=%d%n", teamName[w], f.totalRounds()); return; }
        if (threatTeam != 0) { System.out.printf("# winner=%s rounds=%d%n", teamName[w], f.totalRounds()); return; }
        if (every <= 0 || f.totalRounds() % every != 0) printAggregate(f.totalRounds());
        System.out.printf("RESULT winner=%s (%s) after %d rounds%n", w == 1 ? "A" : w == 2 ? "B" : "?", teamName[w], f.totalRounds());
        if (navStats) { for (Robot r : bots.values()) tallyUnit(r, lastRound); printNavStats(); }
        if (bytecodeSummary) for (int t = 1; t <= 2; t++) {
            StringBuilder s = new StringBuilder("  bytecode " + teamName[t] + ":");
            for (int k = 0; k < 9; k++) if (bcMax[t][k] > 0) s.append(String.format(" %s max=%d over=%d", TYPE[k], bcMax[t][k], bcOverByType[t][k]));
            System.out.println(s);
        }
    }

    static int d2(Robot a, Robot b) { return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y); }
    static void tallyUnit(Robot r, int round) {
        if (r.team == 0 || r.type == 0 || r.type == 2 || r.type == 3 || r.type == 4 || r.type == 5 || r.type == 8 || round - r.spawnRound < 100) return;
        unitsLong[r.team]++; unitMoves[r.team] += r.moves; if (r.moves < 5) unitsIdle[r.team]++;
    }
    static void markVisit(Robot r) { if (visited == null) return; int k = idx(r.x, r.y); if (k >= 0) visited[r.team][k] = true; }
    static long coverageOf(int t) { if (visited == null || width * height == 0) return 0; int c = 0; for (boolean b : visited[t]) if (b) c++; return Math.round(1000.0 * c / (width * height)); }
    static void printNavStats() {
        for (int t = 1; t <= 2; t++)
            System.out.printf("  nav %s: moves=%d aba=%d (%.1f%%) coverage=%.1f%% firstEnemyHQContact=r%d unitsLived100=%d idle(<5 moves)=%d meanMoves=%.1f%n", teamName[t], moves[t], aba[t], moves[t] > 0 ? 100.0 * aba[t] / moves[t] : 0, coverageOf(t) / 10.0, firstContact[t], unitsLong[t], unitsIdle[t], unitsLong[t] > 0 ? (double) unitMoves[t] / unitsLong[t] : 0);
    }

    static boolean inWindow(int round) { return fromRound >= 0 && round >= fromRound && (toRound < 0 || round <= toRound); }
    static boolean inWindowOrAll(int round) { return fromRound < 0 || inWindow(round); }
    static String desc(Robot r) {
        if (r == null) return "#? (gone)";
        String extra = r.type == 0 || (r.type >= 2 && r.type <= 5) || r.type == 8 ? " buried=" + r.buried : "";
        return String.format("%s:%s#%d@(%d,%d)%s", teamName[r.team], TYPE[Math.min(r.type, 9)], r.id, r.x - minX, r.y - minY, extra);
    }
    static List<Robot> sortedBots() { List<Robot> l = new ArrayList<>(bots.values()); l.sort(Comparator.comparingInt(a -> a.id)); return l; }

    static final class Agg { int[] n = new int[10]; boolean hq; int hqBuried, hqElev; long worth; }
    static Agg[] aggregate() {
        Agg[] a = {new Agg(), new Agg(), new Agg()};
        for (Robot r : bots.values()) { Agg g = a[r.team]; int t = Math.min(r.type, 9); g.n[t]++; g.worth += COST[t];
            if (t == 0) { g.hq = true; g.hqBuried = r.buried; int k = idx(r.x, r.y); g.hqElev = k >= 0 ? dirt[k] : 0; } }
        return a;
    }
    static void printAggregate(int round) {
        Agg[] a = aggregate();
        System.out.printf("r%-4d water=%.1f flooded=%d pollution=%d | ", round, waterLevel(round), flooded, globalPollution);
        for (int t = 1; t <= 2; t++) { Agg g = a[t];
            System.out.printf("%s: soup=%d HQ=%s(buried %d) M=%d L=%d Dr=%d R=%d V=%d DS=%d FC=%d NG=%d spawned=%d died=%d mines=%d digs=%d deps=%d picks=%d shots=%d%s",
                t == 1 ? "A" : "B", teamSoup[t], g.hq ? "up" : "DEAD", g.hqBuried, g.n[1], g.n[6], g.n[7], g.n[2], g.n[3], g.n[4], g.n[5], g.n[8], spawned[t], died[t], mines[t], digs[t], dirtDeps[t], pickups[t], shots[t], t == 1 ? " | " : ""); }
        System.out.printf(" | minted=%d%n", msgsMinted);
    }
    static double waterLevel(int r) { return Math.exp(0.0028 * r - 1.38 * Math.sin(0.00157 * r - 1.73) + 1.38 * Math.sin(-1.73)) - 1; }

    static final String[] COLS = {"soup", "hq", "hqBuried", "hqElev", "worth", "miners", "landscapers", "drones", "refineries", "vaporators", "schools", "centers", "netguns", "spawned", "spawnCost", "died", "drowned", "shot", "buriedDeaths", "mines", "soupDeps", "refines", "digs", "dirtDeps", "pickups", "drops", "shots", "moves", "cov", "aba", "bcOver"};
    static void printMetricsHeader() {
        StringBuilder s = new StringBuilder("round");
        for (String t : new String[]{"A", "B"}) for (String c : COLS) s.append(',').append(t).append('_').append(c);
        s.append(",water,flooded,pollution,minted,submitted,fees");
        System.out.println(s);
    }
    static void printMetricsRow(int round) {
        Agg[] a = aggregate(); StringBuilder s = new StringBuilder().append(round);
        for (int t = 1; t <= 2; t++) { Agg g = a[t];
            for (long v : new long[]{teamSoup[t], g.hq ? 1 : 0, g.hqBuried, g.hqElev, g.worth + teamSoup[t], g.n[1], g.n[6], g.n[7], g.n[2], g.n[3], g.n[4], g.n[5], g.n[8], spawned[t], spawnCost[t], died[t], drowned[t], shotDown[t], buriedDeaths[t], mines[t], soupDeps[t], refines[t], digs[t], dirtDeps[t], pickups[t], drops[t], shots[t], moves[t], coverageOf(t), aba[t], bcOverRounds[t]})
                s.append(',').append(v); }
        s.append(',').append(String.format("%.2f", waterLevel(round))).append(',').append(flooded).append(',').append(globalPollution).append(',').append(msgsMinted).append(',').append(msgsSubmitted).append(',').append(feesPaid);
        System.out.println(s);
    }

    /** --threat: what is closing on the watched team's HQ. r2 8 is adjacent-ish (a landscaper must be adjacent to bury), r2 15 is net-gun range. */
    static void printThreatRow(int round) {
        Robot hq = null; for (Robot r : bots.values()) if (r.type == 0 && r.team == threatTeam) hq = r;
        int eL = 0, eD = 0, eM = 0, oL = 0;
        if (hq != null) for (Robot r : bots.values()) { if (!r.alive || r.team == 0 || r == hq) continue; int d = d2(r, hq);
            if (r.team != threatTeam) { if (r.type == 6 && d <= 8) eL++; if (r.type == 7 && d <= 15) eD++; if (r.type == 1 && d <= 8) eM++; }
            else if (r.type == 6 && d <= 8) oL++; }
        System.out.printf("%d,%d,%d,%d,%d,%d,%d%n", round, hq == null ? 0 : 1, hq == null ? 0 : hq.buried, eL, eD, eM, oL);
    }

    static void printBoard(int round) {
        char[][] g = new char[height][width];
        for (int y = 0; y < height; y++) for (int x = 0; x < width; x++) {
            int k = x + y * width;
            g[y][x] = water[k] ? '~' : soup[k] > 0 ? '$' : dirt[k] >= 10 ? '^' : dirt[k] >= 5 ? '=' : dirt[k] >= 3 ? '-' : '.';
        }
        for (Robot r : bots.values()) { int x = r.x - minX, y = r.y - minY; if (x >= 0 && x < width && y >= 0 && y < height) g[y][x] = GLYPH[r.team][Math.min(r.type, 9)]; }
        System.out.printf("BOARD r%d %s (%dx%d) water=%.1f  A=UPPER B=lower: H hq M miner L landscaper A drone R refinery V vaporator D school F fulfil N netgun c cow; ~ water $ soup . <3 - 3-4 = 5-9 ^ >=10%n", round, mapName, width, height, waterLevel(round));
        for (int y = height - 1; y >= 0; y--) { StringBuilder s = new StringBuilder(String.format("%3d ", y)); for (int x = 0; x < width; x++) s.append(g[y][x]); System.out.println(s); }
        StringBuilder ax = new StringBuilder("    "); for (int x = 0; x < width; x++) ax.append(x % 10 == 0 ? (char) ('0' + (x / 10) % 10) : ' '); System.out.println(ax);
    }
}
