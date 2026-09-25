# Handoff -- the state of the loop

Read `CLAUDE.md`, then `TRAINING_ALGORITHM.md`, `RULES.md`, this file, then the tail of
`TRAINING_LOG.md`.

## State at the 2026-09-24 evening restart (switch to Fable 5.1; read this block first)

- **Incumbent and submission: `src/g_iter7`** (Iterations 33+34b accepted 62-34 over g_iter6 on 2026-09-25: a miner
  deposits only a 70-soup load or once the soup in reach is gone; a full miner whose walk to a drop-off stalls builds a
  refinery where it stands; the builder's walks pause on a stall). `src/bot` = g_iter7. Ladder blocks run `BOT=g_iter7`
  until its rating interval is about +-40 (roughly 300 games), then stop (owner, PROMPTS 26).
- **Ladder (batch Bradley-Terry over distinct games, `tools/elo.py --build B`):** g_iter6 1740 +- 41 after 432 games
  (blocks 50-58; 376 distinct), rank 17 of 72; g_iter5 1743 +- 33 (618 distinct of 720). Level: the mirror gain has not
  shown on the ladder yet.
- **VM:** idle. `src/bot` = g_iter7. Iteration 37 (one net gun on the raised site) refuted: ladder arm 19 raid-window
  losses of 96 (g_iter7's rate exactly), gate 2-14 in its first batch (`src/cand37`). Iterations 35 and 36 closed
  earlier today. The two structural lines (bodies at the wall after the flood; the timed raids) both come down to
  spending the post-flood bank where it matters, and every form tried costs the pre-flood wall more than it returns.
- **Why the post-flood bank is unspendable as the base stands** (2026-09-25, end of session): the HQ makes only
  miners and the engine refuses a spawn more than 3 above the builder, so a ring at 600 leaves the HQ nothing to
  spawn onto; the school, center and refineries are under water by r700-900; a producer on raised ground (24, 36)
  or a gun there (31, 37) costs the pre-flood wall more than the mirror allows. The structural answer is the one
  DESIGN.md names (a raised tier the producers stand on, built by the wall's own bodies) and it is a several-session
  program; the next session should design it on paper first, from the census numbers, not code it in an evening.
- **The flood-round deaths** (27 of 159 losses in blocks 50-56: GSF, Hills, Spiral, Toothpaste, Climb): Iteration 38 (seats
  spare the walkway to unseated tiles before r400, `src/cand38`) seated two more tiles on Climb but not the corner
  one, changed nothing on Hills, and cost 6% of ring on RandomSoup1 -- not gated. The next form must act only where a
  seat-seeker is stalled (TRAINING_LOG "Iteration 38").
- **A question for the owner (no action taken):** three acceptances in a row (25, 29b, 33+34b) gained 55-65% in the mirror
  and 2-3 points on the ladder; two raid answers (31, 37) were priced out by the mirror at 12-27% while their ladder
  arms were nulls. The mirror sees only what the incumbent lacks. Should a candidate that is neutral in the mirror
  (45-55%) and beats the incumbent's rate on a pre-registered ladder arm be acceptable? Today's rule (TRAINING_ALGORITHM
  4.4) says no unless the mirror reaches 53%.
- **The session loop** was `/loop 30m task check. If the VM is idle and nothing is in the workqueue, start a new idea.
  Otherwise, carry on as before` -- re-create it. Keep two ladder blocks running side by side when no gate needs the VM;
  every concurrent run needs its own class tree (`CLASSES=build/classes-<name>`) or gauntlet.sh refuses.
- **This session (Opus 5.5, PROMPTS 14-23):** ladder rating rebuilt as a batch fit (owner approved); calibration done
  (all 65 ladder bots met); Iterations 28 (rejected 24-40), 28b (provisional 129-111), 28c (rejected 92-100), 29
  (voided: bug), 29b (ACCEPTED 72-40 -> g_iter6), 30 (rejected 58-70); new sparring partner `src/arch_rush` (poortho's
  early school-by-our-HQ rush; kills g_iter5 on Europe at r256).
- **Open lines, in order:**
  1. **Bodies at the wall after the flood** (TRAINING_LOG "The wall ceiling census", 2026-09-25): seats and helpers run at
     the dig-deposit ceiling; the ring is limited by the 4.7 helpers alive per game at r1000-2000 (16 posts), and the
     bank g_iter7 leaves unspent (3,000-12,000 by r700-1000) can buy replacements only from a school that outlives the
     flood beside posts it can reach. The structural candidate: a school on ground raised before r700 next to the
     distance-2 posts (DESIGN.md's plateau, reduced to one school and its posts). First job: the geometry census on
     the corpus -- which posts stay reachable from a raised distance-3 tile after r1000. Closed forms not to repeat:
     Iterations 12, 13, 24, 30, 31, 35, 36 (ledger): the replacement school (36) built and spawned but one body a game
     did not pay for its cost; a form that refills many posts, or raises bodies that never die, is what is left.
  2. **A producer that outlives the flood** (the same line, older notes). Every reviewable loss of blocks 48-50 ends with no miner, school or center and
     1,200-10,700 soup unspent; the HQ cannot spawn once its eight ring tiles are seated (r300 on). Closed forms, do not
     repeat: more helpers (Iteration 12), a second school after r700 (13), guards bought on a perch (24), more drones and
     net guns from the bank (30). An untried form must say why it avoids each of those failures.
  3. **Climb-type maps:** after g_iter6 the miners are free, but six landscapers cannot reach the west seats
     (`@badseat`) because digging beside the east seats turns row 39 into a cliff (`diag/cut-Climb.bc20`).
  4. **The timed raids** (TRAINING_LOG "Mirror gains and the field"): benzyx r1217-1228 and r1615-1624, team4 r1565-1582,
     mvpatel r1896-1932, cormackikkert r2139-2292 -- drones lift the seats, landscapers bury the HQ, the HQ's one shot a
     round is the only defence; a third of the band's losses. Drones as guards are closed (10, 23, 24); gun perches (31)
     were priced far below the gate by what they reserved, not by the guns. A gun form that reserves nothing is the
     open question -- Iteration 37 (one gun on a raised site, nothing reserved) answered it: arm null (19 of 96, the
     incumbent's rate), gate 2-14. Any pre-flood diversion is priced out by the mirror; a defence must cost nothing
     before r700.
- Iterations 23, 24, 26, 27 closed earlier on 2026-09-24; TRAINING_LOG.md has every entry above with numbers.

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
- Two runs on the VM at once: give each its own class tree (`CLASSES=build/classes-<name>`); `mirror.sh` already uses
  `build/mirror-classes` unless told otherwise. Without it `gauntlet.sh` refuses (block 49 was lost to this once).
- A `( while ...; do sleep; done; cmd ) &` queued from a Bash tool call dies with the call's shell; run queued
  follow-ups with the tool's background mode instead (block 46's study was silently skipped this way).
- The engine replays a pairing identically unless `-Dbc.game.seed` differs (patched in, 2026-09-24): `gauntlet.sh`
  seeds every game and records the seed; `run-dev.sh` keeps the map seed unless `GAME_SEED` is set. The VM's
  `engine/engine.jar` must be the patched build (copy it from the driver; `engine/VERSION` says when it was built).
- The engine refuses to spawn a robot on a tile more than 3 higher or lower than the builder's (`assertCanBuildRobot`);
  site choices must use the builder's own height, not the HQ's.
- 2020-specific: `rc.canMove` does not check water; `senseNearbyRobots` is row-major; a robot
  built this round acts next round; the blockchain block for round r is readable from r+1.
