# tools/

**Games in volume run on the `battlecode-dev` VM, never on the driver** (see `SETUP.md`).
Everything runs with bare `java` (JDK 8 at `~/jdk/jdk8u504-b01`, exported by `tools/lib.sh`).

| script | purpose |
|---|---|
| `build-engine.sh` | clone/patch/build the 2020 engine from `battlecode/battlecode20`, stage `engine/` (jar, deps, 52 maps, `bc20-maps.txt`) |
| `lib.sh` | shared: JDK/classpath, `run_game`, `parse_result`, `compile_src` |
| `run-match.sh A B map [replay]` | one headless game, prints `RESULT <winner> <round> <reason>` |
| `run-dev.sh A B map [replay]` | like `run-match.sh` but from a private compile (`build/dev-classes`); `LOG_OUT=file` keeps the engine stdout; the engine seed is fixed, so the same pairing replays the same game until the code changes |
| `gauntlet.sh` | BOT vs OPPONENTS on MAPS/MAPSET (`full`, `quick` 12, `screen` 4), both sides, parallel, a fresh engine seed per game (recorded in the `seed` column; a cell line's 4th field fixes it); writes `gauntlet/<run>/results.csv`, `summary.txt`, `losses/`; `CELLS=file` plays given cells; `CLASSES=build/x` compiles privately; refuses external opponents unless `SCRIM=1` |
| `replay-dump.sh r.bc20 --elev-at N --elev-raw` | the board's dirt at round N with the numbers under the robots too (the enclosure census) |
| `replay-dump.sh r.bc20 --seats A\|B --seats-at N` | the open ring tiles of that team's HQ at round N: occupant, nearest own landscaper and miner, enemies within 3, the outward neighbours' dirt; a summary line with landscapers and miners alive (the seat census, TRAINING_LOG 2026-09-25) |
| `mirror.sh` (BOT, REF, N, BATCH, W0/L0, PAIRED, OPP) | the accept gate: random map, side and seed per cell, batches of 16; `PAIRED=1` (default) plays each cell as the candidate vs the incumbent and the incumbent vs itself (`paired.sh`), N counts pairs and `sprt.py` runs on the discordant pairs only; `PAIRED=0` is the old unpaired mirror |
| `field-score.py [--plot --tau]` | `progress/field-score.png`: every build's field score over time; the submissions' ratings fitted with a diminishing-returns curve (R0 + a ln(1 + t/tau)) and projected to the ends of the project's first and second weeks (Sep 30, Oct 7) with a 95% band, mapped to field scores through the current ladder fit; run by `post-block.sh` after every block (PROMPTS 28-30, 46) |
| `paired.sh cells.txt` (BOT, REF, MAXJOBS, LOGTAG) | paired replay of a gate's cells (map, side, seed): the candidate game and the incumbent against itself on the same seed; fixed seeds make the engine deterministic, so a pair is concordant (identical to the round) wherever the change did not fire, and only the discordant pairs judge it (sign test); `tags` counts `LOGTAG` lines the candidate printed |
| `sprt.py <wins> <losses>` | sequential probability ratio test, H0 p=0.50 vs H1 p=0.58: ACCEPT / REJECT / CONTINUE |
| `scrim.sh` (BOT, N, POOL, SEED) | the only way to play an external bot: random map and side, rotating opponents from the rating band around `BOT` (default); `POOLSIZE=0 EXPLORE=n` is a calibration block of never-played bots; `CHALLENGE=1`, `POOLMODE=above`/`established` select the old pools |
| `scrim-record.py <run> --label <build>` | appends a block to `progress/games.csv` (with each game's seed) |
| `elo.py [--band N --as B] [--explore K] [--build B] [--challenge N] [--pool N] [--established N]` | the ladder from our scrimmages only, by the batch Bradley-Terry fit of `elolib.py` (each build its own player; a repeated pairing with the same seed counts once): `progress/ELO.md`, `elo.png`; `--build B` prints a build's grade (record, rating +- 95%, rank, field score); `--band N` = the N rated bots nearest build B's rating, the pool `scrim.sh` uses by default |
| `snapshot.sh name [archetype]` | freeze `src/bot` as `src/<name>` |
| `replay-dump.sh replay [flags]` | replay -> text: `--every`, `--from/--to`, `--robot`, `--map/--map-at`, `--logs REGEX --logs-team A`, `--metrics`, `--bytecode`, `--navstats`, `--threat A` |
| `bench-compile.sh` | compile every benchmark repo without displaying source; writes `manifest.tsv` |
| `bench-select.py [--all|--table]` | name-only pick of each repo's final bot |
| `tier-check.sh replay` | reports a replay's opponent tier from `BENCHMARK.md` (informational since PROMPTS 45; never refuses) |
| `vm.sh`, `vm-sync.sh`, `vm-run.sh <log> '<cmd>'`, `vm-tail.sh`, `vm-collect.sh <run>`, `vm-stop.sh` | the VM handles |
| `unit-tests.sh` | compile and run `test/bot/*Test.java` and `tools/test_tools.py` |
| `mapinfo/MapInfo.java` | the map corpus table `tools/mapdata.csv` |
| `post-block.sh <run> <label>` | after a scrimmage block: collect, record, ratings, roster, study, correlate, onset (one command) |
| `scrim-study.sh <run>` | the block study: `--metrics` and `--navstats` for every replay -> `study.tsv`, `nav.tsv`; `scrim-study.py` prints medians |
| `correlate.py <run> [--round N]` | raw and within-opponent correlation of each metric with the result |
| `onset.py <run> [--md --plot]` | per metric, the first round at which its lead correlates with the result |
| `onset-merged.sh <build>` | the same over every recorded block of one build (`progress/ONSET-merged.md`; 786 games of g_iter6 give a noise floor of 0.07 against one block's 0.30) |
| `polarity.py`, `statlib.py`, `derived.py` | orientation, statistics and derived metrics shared by the two above; tested by `test_metrics.py` |
| `compare.py base cand` | game-by-game diff of two gauntlet runs |
| `log-scan.sh <run>` | every `@tag` log line of our side for every game of a block, one pass |
| `bench-roster.py` | regenerate the roster table in `BENCHMARK.md` from `progress/games.csv` |

Rules of the road: never read benchmark source; never review a game against a bot we beat under
20%; never `pkill -f` a pattern that appears in your own command line; do not edit `gauntlet.sh`
while a run is in flight (it re-executes from a private copy).

- `tools/enc-acct.py <log> <A|B> <hqx> <hqy>` -- the enclosure holders' dirt accounting per 100 rounds by tile distance (digs, own-tile, fed, reclaimed), from the @wallstat tags.
