# Handoff -- the state of the loop

Read `CLAUDE.md`, then `TRAINING_ALGORITHM.md`, `RULES.md`, this file, then the tail of
`TRAINING_LOG.md`.

## State (2026-09-23)

- Phase 0 in progress. Engine built and staged on the driver; not yet on the VM.
- `src/bot` = Iteration 0 (snapshot `src/g_iter0`). No accepted iteration yet, no ladder games.
- Benchmark repos cloned (96); `tools/bench-compile.sh` running or done -- check
  `~/projects/vibe/bc20-benchmarks/manifest.tsv` and `build/bench-compile.log`.
- Next: (1) finish the benchmark compile, `tools/bench-select.py --table`, write
  `tools/ladder-bots.txt`; (2) `tools/vm-sync.sh` (pushes JDK, engine, benchmark classes, repo);
  (3) a quick-set gauntlet of `bot` vs `examplefuncsplayer` on the VM to time games; (4) the
  first scrimmage block of `g_iter0` to seed the Elo ladder; (5) Iteration 1: the foundation
  (HQ wall, real economy, exploration) as a structural candidate.

## Gotchas already known (from 2021, still true here)

- `pgrep -c battlecode.server.Main` counts two processes per game (the `timeout` wrapper).
- Kill `mirror.sh`/`gauntlet.sh` scripts, not their `xargs`; `gauntlet.sh` re-execs itself as
  `.reexec-gauntlet.<pid>`, so wait on the run directory's `summary.txt`, not on a process name.
- Never read a running batch as a result.
- `vm-sync.sh` replaces `src tools test progress` on the VM at every `vm-run.sh`.
- `git checkout` cannot undo a committed change to `src/bot`; restore from the snapshot with
  `for f in src/g_iterN/*.java; do sed 's/^package g_iterN;/package bot;/' "$f" > src/bot/$(basename $f); done`.
- 2020-specific: `rc.canMove` does not check water; `senseNearbyRobots` is row-major; a robot
  built this round acts next round; the blockchain block for round r is readable from r+1.
