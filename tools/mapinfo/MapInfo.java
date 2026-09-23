package mapinfo;

import battlecode.common.*;
import battlecode.world.GameMapIO;
import battlecode.world.LiveMap;

import java.io.File;

/**
 * The map corpus table: one CSV row per .map20 in engine/maps.
 *   java mapinfo.MapInfo engine/maps [names...]  > tools/mapdata.csv
 * Columns: map,w,h,hqElevA,hqElevB,hqDist2,hqFloodRound,cows,soupTotal,soupNearHQ(r2 100),flooded0,water0,symmetry,minElev,maxElev
 * hqFloodRound is the round at which the water level first exceeds the HQ's elevation (an
 * unprotected HQ dies then if the flood can reach it), from GameConstants.getWaterLevel.
 */
public class MapInfo {
    public static void main(String[] a) throws Exception {
        File dir = new File(a[0]);
        String[] names;
        if (a.length > 1) { names = new String[a.length - 1]; System.arraycopy(a, 1, names, 0, names.length); }
        else { File[] fs = dir.listFiles((d, n) -> n.endsWith(".map20")); java.util.Arrays.sort(fs); names = new String[fs.length]; for (int i = 0; i < fs.length; i++) names[i] = fs[i].getName().replace(".map20", ""); }
        System.out.println("map,w,h,hqElevA,hqElevB,hqDist2,hqFloodRound,cows,soupTotal,soupNearHQ,flooded0,water0,symmetry,minElev,maxElev");
        for (String n : names) {
            LiveMap m = GameMapIO.loadMap(n, dir);
            int w = m.getWidth(), h = m.getHeight(); MapLocation o = m.getOrigin();
            int[] dirt = m.getDirtArray(); boolean[] water = m.getWaterArray(); int[] soup = m.getSoupArray();
            MapLocation hqA = null, hqB = null; int cows = 0;
            for (RobotInfo r : m.getInitialBodies()) { if (r.type == RobotType.HQ) { if (r.team == Team.A) hqA = r.location; else hqB = r.location; } else if (r.type == RobotType.COW) cows++; }
            long soupTotal = 0, soupNear = 0; int flooded = 0, minE = Integer.MAX_VALUE, maxE = Integer.MIN_VALUE;
            for (int y = 0; y < h; y++) for (int x = 0; x < w; x++) {
                int i = x + y * w; soupTotal += soup[i]; if (water[i]) flooded++;
                if (dirt[i] > -1000000) { minE = Math.min(minE, dirt[i]); maxE = Math.max(maxE, dirt[i]); }
                if (hqA != null && new MapLocation(o.x + x, o.y + y).distanceSquaredTo(hqA) <= 100) soupNear += soup[i];
            }
            int eA = hqA == null ? 0 : dirt[(hqA.x - o.x) + (hqA.y - o.y) * w], eB = hqB == null ? 0 : dirt[(hqB.x - o.x) + (hqB.y - o.y) * w];
            int floodRound = 0; while (floodRound < 10000 && GameConstants.getWaterLevel(floodRound) <= eA) floodRound++;
            boolean rot = true, hor = true, ver = true;
            for (int x = 0; x < w; x++) for (int y = 0; y < h; y++) {
                int i = x + y * w, r = (w - 1 - x) + (h - 1 - y) * w, hh = (w - 1 - x) + y * w, v = x + (h - 1 - y) * w;
                if (dirt[i] != dirt[r] || water[i] != water[r] || soup[i] != soup[r]) rot = false;
                if (dirt[i] != dirt[hh] || water[i] != water[hh] || soup[i] != soup[hh]) hor = false;
                if (dirt[i] != dirt[v] || water[i] != water[v] || soup[i] != soup[v]) ver = false;
            }
            String sym = (rot ? "R" : "") + (hor ? "X" : "") + (ver ? "Y" : "");
            System.out.printf("%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%d,%d%n", n, w, h, eA, eB, hqA == null || hqB == null ? -1 : hqA.distanceSquaredTo(hqB), floodRound, cows, soupTotal, soupNear, flooded, m.getWaterLevel(), sym.isEmpty() ? "none" : sym, minE, maxE);
        }
    }
}
