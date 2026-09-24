#!/usr/bin/env bash
# Build the Battlecode 2020 engine from source and stage it under engine/.
#
# WHY: the official artefacts are gone. The scaffold fetched the engine from GitHub Packages with a
# read token published at 2020.battlecode.org/access.txt; that URL is dead. The engine source is
# public (battlecode/battlecode20), so we build it. Dependencies that rotted: `jcenter()` (shut down)
# and `net.sf.jsi:jsi:1.1.0-SNAPSHOT` (sonatype snapshots purged); jcenter -> mavenCentral, jsi is
# compiled from aled/jsi's source into a flatDir jar. Same recipe as battlecode21-vibe's.
#
#   tools/build-engine.sh                  # clone (if needed), patch, build, stage
#   ENGINE_REF=<sha> tools/build-engine.sh
#
# Output: engine/engine.jar, engine/lib/*.jar (runtime deps), engine/maps/*.map20, engine/VERSION,
# tools/bc20-maps.txt. engine/ is gitignored; re-run on a fresh checkout.
set -euo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC="${ENGINE_SRC:-$HOME/projects/vibe/reference/battlecode20}"
JSI_SRC="${JSI_SRC:-$HOME/projects/vibe/reference/jsi}"
ENGINE_REF="${ENGINE_REF:-7618f6b}"        # master head at clone time, 2026-09-23 (tag v2.0.3 = spec 2020.2.0.3)
export JAVA_HOME="${JAVA_HOME:-$HOME/jdk/jdk8u504-b01}"
export PATH="$JAVA_HOME/bin:$PATH"
java -version 2>&1 | grep -q '1\.8' || { echo "!! need JDK 8 at $JAVA_HOME" >&2; exit 1; }

[ -d "$SRC/.git" ] || git clone -q https://github.com/battlecode/battlecode20.git "$SRC"
[ -d "$JSI_SRC/.git" ] || git clone -q https://github.com/aled/jsi.git "$JSI_SRC"
( cd "$SRC" && git checkout -q "$ENGINE_REF" 2>/dev/null || true )

# --- jsi -------------------------------------------------------------------
LIBS="$SRC/engine/libs"; mkdir -p "$LIBS"
mvn_get () { local f="$LIBS/$2-$3.jar"; [ -f "$f" ] || curl -fsSL -o "$f" "https://repo1.maven.org/maven2/$1/$2/$3/$2-$3.jar"; }
mvn_get net/sf/trove4j trove4j 3.0.3
mvn_get org/slf4j slf4j-api 1.7.21
if [ ! -f "$LIBS/jsi-1.1.0-SNAPSHOT.jar" ]; then
  if [ -f "$HOME/projects/vibe/reference/battlecode21/engine/libs/jsi-1.1.0-SNAPSHOT.jar" ]; then
    cp "$HOME/projects/vibe/reference/battlecode21/engine/libs/jsi-1.1.0-SNAPSHOT.jar" "$LIBS/"
  else
    tmp="$(mktemp -d)"
    javac -nowarn -d "$tmp" -cp "$LIBS/trove4j-3.0.3.jar:$LIBS/slf4j-api-1.7.21.jar" $(find "$JSI_SRC/src/main/java" -name '*.java')
    ( cd "$tmp" && jar cf "$LIBS/jsi-1.1.0-SNAPSHOT.jar" net ); rm -rf "$tmp"
  fi
fi

# --- patch the build (idempotent) --------------------------------------------
cd "$SRC"
python3 - <<'PY'
def patch(p, subs):
    s = open(p).read(); o = s
    for a, b in subs: s = s.replace(a, b)
    if s != o: open(p, 'w').write(s)
# 2026-09-24 (PROMPTS 25): the engine seeds robot IDs and every sandboxed Random from the map file's seed, so the
# same pairing on the same map and side replays the same game (38 of 38 repeated ladder pairings were identical;
# a 240-game mirror gate had at most 104 distinct games). -Dbc.game.seed=<int> overrides it per game.
patch('engine/src/main/battlecode/world/LiveMap.java', [('    public int getSeed() {\n        return seed;',
      '    public int getSeed() {\n        return Integer.getInteger("bc.game.seed", seed);')])
patch('engine/build.gradle', [
    ('  jcenter()\n  mavenCentral()\n', '  mavenCentral()\n  flatDir { dirs "libs" }\n'),
    ("[group: 'net.sf.jsi', name: 'jsi', version: '1.1.0-SNAPSHOT'],", "[name: 'jsi-1.1.0-SNAPSHOT'],"),
])
s = open('engine/build.gradle').read()
if 'printClasspath' not in s:
    open('engine/build.gradle', 'a').write('\ntask printClasspath {\n  doLast { println sourceSets.main.runtimeClasspath.getAsPath() }\n}\n')
patch('build.gradle', [('repositories {\n    jcenter()\n}', 'repositories {\n    mavenCentral()\n}'),
                       ('project(":internal-test-bots")', 'project(":example-bots")'), ("project(':internal-test-bots')", "project(':example-bots')"),
                       ('":internal-test-bots:build"', '":example-bots:build"'), ("':internal-test-bots:build'", "':example-bots:build'")])
patch('example-bots/build.gradle', [('  jcenter()\n', ''), ('  maven {url "https://oss.sonatype.org/content/repositories/snapshots/"}\n', '')])
s = open('settings.gradle').read()
s = '\n'.join(l for l in s.splitlines() if 'internal-test-bots' not in l) + '\n'
open('settings.gradle', 'w').write(s)
gp = open('gradle.properties').read() if __import__('os').path.exists('gradle.properties') else ''
if 'org.gradle.jvmargs' not in gp: open('gradle.properties', 'a').write('\norg.gradle.jvmargs=-Xmx600m\n')
# reuse the gradle 5.6.2 distribution already cached on this machine (no 100 MB download)
patch('gradle/wrapper/gradle-wrapper.properties', [('gradle-6.0.1-bin.zip', 'gradle-5.6.2-bin.zip')])
PY

# --- build -------------------------------------------------------------------
./gradlew --no-daemon -q :engine:build -x test -x javadoc 2>&1 | grep -v '^warning\|^Note:' || true
[ -f engine/build/libs/engine.jar ] || { echo "!! engine build failed" >&2; exit 1; }
CP="$(./gradlew --no-daemon -q :engine:printClasspath | tail -1)"

# --- stage -------------------------------------------------------------------
OUT="${ENGINE_OUT:-$REPO/engine}"; rm -rf "$OUT"; mkdir -p "$OUT/lib" "$OUT/maps"   # ENGINE_OUT: stage elsewhere (a swap while games use engine/)
cp engine/build/libs/engine.jar "$OUT/engine.jar"
echo "$CP" | tr ':' '\n' | grep '\.jar$' | grep -v 'tools.jar' | while read -r j; do cp "$j" "$OUT/lib/"; done
cp engine/src/main/battlecode/world/resources/*.map20 "$OUT/maps/"
ls engine/src/main/battlecode/world/resources/*.map20 | xargs -n1 basename | sed 's/\.map20$//' | sort > "$REPO/tools/bc20-maps.txt"
{ echo "engine source: battlecode/battlecode20 @ $(git rev-parse HEAD)"; echo "spec version: $(grep -o 'Current version: [0-9.]*' specs/specs.md | head -1)"; echo "built: $(date -u +%FT%TZ) with $(java -version 2>&1 | head -1)"; } > "$OUT/VERSION"
cat "$OUT/VERSION"; ls "$OUT/lib" | wc -l; echo "maps: $(wc -l < "$REPO/tools/bc20-maps.txt")"
