# Handoff -- the state of the loop

Read `CLAUDE.md`, then `TRAINING_ALGORITHM.md`, `RULES.md`, this file, then the tail of
`TRAINING_LOG.md`.

## State (2026-09-23, evening)

- Phase 0 complete: engine on the driver and the VM, runner/gate/ladder tools ported and tested, replay dumper, unit tests.
- Incumbent and submission: `src/g_iter2` (accepted 29-3 over `g_iter1`). Iterations 3, 4 and 5 were rejected at the
  gate (TRAINING_LOG.md); the last lesson is that a role with a fixed station list needs a stall exit.
- Iteration 6, the citadel (ring at Chebyshev 2, buildings sealed inside, drone ferry and raid), is closed after ten
  24-game sweeps at 4-9/24 against g_iter2 (TRAINING_LOG ledger); its code is `src/arch_citadel`, its tools
  `tools/citadel-diag.sh` and `replay-dump.sh --ringd`.
- **Incumbent and submission: `src/g_iter4`** (Iteration 9, provisional: 135-105 inconclusive above the 53% keep line
  over g_iter3 on 2026-09-23; the late drone raid). `src/g_iter3` (Iteration 8, accepted 39-9 over g_iter2) is the
  fallback if a submission block disappoints.
- `src/bot` = g_iter4 + idle-turn instrumentation for Iteration 12 (the seats' idle census). Iteration 10 (home-defence
  drones) rejected 120-120; Iteration 11 (builder site fixes + spawn room) inconclusive 125-115, not kept.
- Sparring: `tools/gauntlet.sh` with `OPPONENTS=arch_drone MAPSET=quick` measures swarm vulnerability (run `spar1`).
- Ladder: g_iter2 is 37/156 (24%) on the strong field over blocks 1-6; Elo 1372, rank 66/66 (`progress/ELO.md`).
- Benchmarks: 285 packages from 96 repos compiled (`~/projects/vibe/bc20-benchmarks/manifest.tsv`);
  the ladder field is `tools/ladder-bots.txt` (one bot per repo, chosen by name).
- Standing loop (owner, session cron every 30 min): keep the VM busy; if idle with nothing queued, start a new idea.

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
