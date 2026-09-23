# tools/

**Games in volume run on the `battlecode-dev` VM, never on the driver** (see `SETUP.md`).
Everything runs with bare `java` (JDK 8 at `~/jdk/jdk8u504-b01`, exported by `tools/lib.sh`).

| script | purpose |
|---|---|
| `build-engine.sh` | clone/patch/build the 2020 engine from `battlecode/battlecode20`, stage `engine/` (jar, deps, 52 maps, `bc20-maps.txt`) |
| `lib.sh` | shared: JDK/classpath, `run_game`, `parse_result`, `compile_src` |
| `run-match.sh A B map [replay]` | one headless game, prints `RESULT <winner> <round> <reason>` |
| `run-dev.sh A B map [replay]` | like `run-match.sh` but from a private compile (`build/dev-classes`); `LOG_OUT=file` keeps the engine stdout; the engine seed is fixed, so the same pairing replays the same game until the code changes |
| `gauntlet.sh` | BOT vs OPPONENTS on MAPS/MAPSET (`full`, `quick` 12, `screen` 4), both sides, parallel; writes `gauntlet/<run>/results.csv`, `summary.txt`, `losses/`; `CELLS=file` plays given cells; `CLASSES=build/x` compiles privately; refuses external opponents unless `SCRIM=1` |
| `mirror.sh` (BOT, REF, N, BATCH, W0/L0) | the accept gate: candidate vs incumbent, random map and side per game, batches of 16, `sprt.py` after each |
| `sprt.py <wins> <losses>` | sequential probability ratio test, H0 p=0.50 vs H1 p=0.58: ACCEPT / REJECT / CONTINUE |
| `scrim.sh` (BOT, N, POOL, SEED) | the only way to play an external bot: random map and side, rotating opponents from the Elo pool |
| `scrim-record.py <run> --label <build>` | appends a block to `progress/games.csv` |
| `elo.py [--pool N --explore K] [--build B] [--established N]` | the Elo ladder from our scrimmages only: `progress/ELO.md`, `elo.png`, and the challenge pool |
| `snapshot.sh name [archetype]` | freeze `src/bot` as `src/<name>` |
| `replay-dump.sh replay [flags]` | replay -> text: `--every`, `--from/--to`, `--robot`, `--map/--map-at`, `--logs REGEX --logs-team A`, `--metrics`, `--bytecode`, `--navstats`, `--threat A` |
| `bench-compile.sh` | compile every benchmark repo without displaying source; writes `manifest.tsv` |
| `bench-select.py [--all|--table]` | name-only pick of each repo's final bot |
| `tier-check.sh replay` | enforces the replay-access tiers of `BENCHMARK.md` (fails closed) |
| `vm.sh`, `vm-sync.sh`, `vm-run.sh <log> '<cmd>'`, `vm-tail.sh`, `vm-collect.sh <run>`, `vm-stop.sh` | the VM handles |
| `unit-tests.sh` | compile and run `test/bot/*Test.java` and `tools/test_tools.py` |
| `mapinfo/MapInfo.java` | the map corpus table `tools/mapdata.csv` |

Rules of the road: never read benchmark source; never review a game against a bot we beat under
20%; never `pkill -f` a pattern that appears in your own command line; do not edit `gauntlet.sh`
while a run is in flight (it re-executes from a private copy).
