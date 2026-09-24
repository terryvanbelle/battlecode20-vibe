# Handoff -- the state of the loop

Read `CLAUDE.md`, then `TRAINING_ALGORITHM.md`, `RULES.md`, this file, then the tail of
`TRAINING_LOG.md`.

## State (2026-09-24, after the restart; read this block first)

- **Incumbent and submission: `src/g_iter6`** (Iteration 29b accepted 72-40 over g_iter5, 2026-09-24: 28b's refinery and
  school outward of the Chebyshev-2 circle, the rush response, miners off the ring only once a refinery exists).
  `src/bot` = g_iter6. Ladder blocks now run `BOT=g_iter6`.
- **Ladder fixed (PROMPTS 14-16):** calibration done (calib1, 88/96; all 65 ladder bots met -- the list has 65, not
  220). Ratings are now a batch Bradley-Terry fit with each build its own player (`tools/elolib.py`); the old
  sequential Elo had put us at rank 4 after the easy calibration games. g_iter5's final grade over 720 ladder games:
  1745 +- 30, rank 16 of 71 players, field score 72.8%. Withdrawal now compares ratings.
- **Running on the VM:** blocks 55 and 56 (`tools/scrim.sh`, BOT=g_iter6, the band). g_iter6 1720 +- 48 after blocks
  50-54 (240 games), level with g_iter5 (1743 +- 30); archetype regression passed (vs `arch_swarm` 21/24, g_iter5 19/24).
  Iteration 30 (lift the drone and net-gun caps above a 1000 bank) was rejected 58-70; `src/bot` = g_iter6. Post blocks with `tools/post-block.sh <run> g_iter6`;
  give a concurrent run its own class tree (`CLASSES=build/classes-<name>`) or gauntlet.sh refuses. The session loop is
  `/loop 30m task check. If the VM is idle and nothing is in the workqueue, start a new idea. Otherwise, carry on as before`.
- **Open lines, in order:** (1) map-dead games: the idle miners were sealed behind the HQ by our own refinery
  (Iteration 28 rejected 24-40; 28b folded into g_iter6). Next on Climb: six landscapers cannot reach the west seats (`@badseat`) because digging
  beside the east seats turns row 39 into a cliff, so the west ring floods at r500 (`diag/cut-Climb.bc20`). (2) poortho's rush: g_iter6 answers it; `src/arch_rush` is the sparring partner (g_iter6 13/24, g_iter5 12/24). (3) The unspent bank: every reviewable loss of blocks 48-50 ends with no miner, school or center and 1,200-10,700 soup
  unspent (TRAINING_LOG "Block 50 and the unspent bank"); more drones and guns did not help (Iteration 30): the
  missing piece is a producer that outlives the flood. (4) Candidates are judged on the
  band by rating, not raw win rate.
- Iterations 23 (home guard), 24 (the perch, `src/arch_perch`), 26 (miners first) and 27 closed 2026-09-24; TRAINING_LOG.md.

## State (2026-09-23, evening; superseded above where they differ)

- Phase 0 complete: engine on the driver and the VM, runner/gate/ladder tools ported and tested, replay dumper, unit tests.
- Incumbent and submission: `src/g_iter2` (accepted 29-3 over `g_iter1`). Iterations 3, 4 and 5 were rejected at the
  gate (TRAINING_LOG.md); the last lesson is that a role with a fixed station list needs a stall exit.
- Iteration 6, the citadel (ring at Chebyshev 2, buildings sealed inside, drone ferry and raid), is closed after ten
  24-game sweeps at 4-9/24 against g_iter2 (TRAINING_LOG ledger); its code is `src/arch_citadel`, its tools
  `tools/citadel-diag.sh` and `replay-dump.sh --ringd`.
- **Iteration 24, the perch (2026-09-24): closed** -- gate 51-61, ladder 11/48 against g_iter3's 21.9%. Code in
  `src/arch_perch` (a helper raises three Chebyshev-3 tiles to 32 before the flood; a miner on one builds a center and a
  vaporator on the others, alive to r2200; post-flood soup buys up to 16 guards on fixed slots at Chebyshev 6). All
  mechanisms work (TRAINING_LOG.md "Iteration 24" lists the nine fixes it took); the ladder's r1000-2000 losses did not
  move. Reopen only with evidence about what the guards met in those games (the study table of `cand24`).
- **Incumbent and submission: `src/g_iter5`** (Iteration 25 "seats first", accepted 45-19 over g_iter3 on 2026-09-24:
  until r400 nobody raises a ring tile without a seat on it, and a helper beside a free climbable ring tile takes it;
  8 seats instead of 4-5). Ladder blocks now run `BOT=g_iter5`. Iteration 26 (MINERS_EARLY 8) was rejected 50-62
  against it; a closed ring cannot spawn miners, but building them first costs more wall than they earn.
- Previous incumbent `src/g_iter3`** (Iteration 8, accepted 39-9 over g_iter2). `src/g_iter4` (the late raid,
  provisional) was withdrawn on 2026-09-24 after 228 ladder games at 20% against g_iter3's 26%.
- Plateau, final state 2026-09-24: seven sweeps of the slot revision at 2-10/24. The Squares seat trace (TRAINING_LOG.md
  after plat13) showed that g_iter3 also seats only five ring tiles there; it wins because its helpers put every load on
  the eight ring tiles (1510 at r2000) while the plateau spreads dirt over tiers 2-3 (864) and its holders' inward
  feeding makes the unheld tiles unclimbable by r140. If reopened: no tier-2/3 self-raise before ~r1500, and claimants
  never re-pick.
- Plateau claims were revised twice more after the gates (arrival-settled, then deterministic slots posted by the school
  and read from the last two blocks at birth): sweeps `plat7`-`plat11` 8, 10, 9, 9, 5 of 24 vs g_iter3 with per-game
  tables (slots, churn, deaths, ring minima) in TRAINING_LOG.md. At the incumbent's own structure the rewritten roles
  lose by 40%: the wall mechanics of `arch_plateau/Landscaper.java` are worse than g_iter3's `wall()`/`help()`, and a
  turn-by-turn comparison of one seat under each is the first job if the line is reopened.
- Plateau stage 1 (tile-holding roles) gated three times: 42-54, 25-39, 15-33 vs g_iter3 (ledger). Code in
  `src/arch_plateau`. The next attempt needs a claim scheme with no re-picks and tiers opened by the water level.
- `src/bot` is an exact copy of g_iter3. Iterations 10-16 (2026-09-23/24) were all rejected, inconclusive below 53%, or
  blocked; a control run (g_iter3 vs itself, 49-47) confirmed the mirror gate is fair. The next structural candidate is
  the **vaporator plateau** (TRAINING_LOG.md "What the field does that we do not"): raise the distance-2 tier as a
  plateau with distance-3 feeders, park the builder on it, put vaporators, a school and net guns on it at height.
- **The plateau plan (next structural candidate, several sessions).** Order of work: (1) a seal-by-r700 ring of 8 seats
  exactly as g_iter3; (2) 16 helpers on the distance-2 tiles feeding the ring, 8+ feeders at distance 3 piling dirt on
  the helper tiles so the tier rises as a plateau (Iteration 12's code in commit 64e1839, which never got its feeders
  because the soup ran out); (3) the economy to pay for 32+ bodies before r700 -- 16 miners and early vaporators
  (Iteration 17's constants, with miners kept off the ring approaches: two seats were blocked on a corner HQ); (4) the
  builder parked on the plateau, building vaporators, a school and net guns on it once its tiles stay dry for good;
  (5) landscapers born after r700 take distance-3 tiles they raise themselves. Each stage has a diagnostic (ring and
  tier heights via `replay-dump.sh --ring --ringd 2`, `@build` by round) and the whole goes to the mirror gate plus the
  `arch_swarm` arm (baseline g_iter3 15/24). The field's version: team4 at r1000 had 33 miners, 44 vaporators, 44
  drones, 5 schools, all alive.
- Plateau findings so far (2026-09-24, `plat2` 7/24 vs g_iter3 with Iteration 12 tiers + Iteration 17 economy): the
  economy scales on rich maps (Prison: 34 landscapers, 8 vaporators at r1000) but the bodies die as attackers; helpers
  take all 16 distance-2 posts and no feeder is ever posted; corner HQs lose two seats to the miner crowd. Fix those
  three before measuring the plateau again.
- Archetypes: `src/arch_swarm` (early center, 30 drones, wave raid from r1200 that lifts seats and ferries helpers onto
  the enemy ring) is the second arm for defence candidates; `spar2` baseline g_iter3 12/24 before its ferry, `spar3`
  after it. `src/arch_drone` never reaches our ring and is no swarm arm.
- Sparring: `tools/gauntlet.sh` with `OPPONENTS=arch_drone MAPSET=quick` measures swarm vulnerability (run `spar1`).
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
