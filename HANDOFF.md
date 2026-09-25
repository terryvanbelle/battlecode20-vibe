# Handoff -- the state of the loop

Read `CLAUDE.md`, then `TRAINING_ALGORITHM.md`, `RULES.md`, this file, then the tail of
`TRAINING_LOG.md`.

## State at the 2026-09-25 evening handoff (switch back to Opus 5.5; read this block first)

- **Incumbent: `src/g_iter10`** (g_iter9 + Iteration 50, the frozen-miner fix; accepted 2026-09-25 evening). **Submitted:
  240 games, 103-137, 1743 +- 47, rank 17 of 84** (the best submission; +25 on g_iter9, inside the error bars). Previous submission g_iter9: 1723 +- 48, rank 19 of 83 after 240 games. Nothing is gated. The ladder rows `cand43b`, `cand49b`, `cand47d` are explained in
  PROMPTS 32-33 and in the transcript: cand43b IS g_iter9 (the same code, its 96 arm games against a pool 250 points
  weaker; merged, 1739 +- 42 on 336 games); 47d was REJECTED paired 2-14; 49b sat under the twelve-pair floor.
- **The owner's standing loop:** a cron prompt every 30 minutes ("task check. If the VM is idle and nothing is in
  the workqueue, start a new idea. Otherwise, carry on as before"). Record every user prompt in PROMPTS.md (next is
  36). Push after every commit; `post-block.sh` refreshes `progress/field-score.png` after every posted block (the
  owner watches it, PROMPTS 28-31).
- **VM idle. The enclosure program is CLOSED** (TRAINING_LOG "THE ENCLOSURE PROGRAM IS CLOSED"): stage 36 (interior feeders) added +60 of shell at r3000; with every hole closed the shell stands 715 below g_iter9's wall and g_iter9 outlives it on all three maps. `src/arch_enclosure` is kept as a reference and sparring archetype. The loop returns to the incumbent g_iter9. **`census9` read** (TRAINING_LOG "The production census"): ten starved maps (Spiral, GSF, Hills, Climb, DidAMonkeyMakeThis, AMaze, CosmicBackgroundRadiation, Islands, Maze, TheHighGround) where g_iter9 has 3-9 landscapers at r500 and mines 130-400 by r300. `diagecon` read: every miner freezes by r300 on Spiral and GSF (a soup target alternating with the explore target resets the navigator's stall counter every turn). **Iteration 50 (`src/cand50`, `src/bot` = cand50): the miner never falls through to explore while a soup target stands.** Diagnostic read (mining after r300 3-6x on Spiral; the mechanism fires). **ACCEPTED: gate50 32-9 discordant at 112 pairs -> `src/g_iter10`** (`src/bot` = g_iter10). Submission blocks `sub10` played and posted. **Iteration 51 (`src/cand51`, `src/bot` = cand51): the school needs three doors** -- diagnostic on Spiral A flipped a loss to a win (ten landscapers from five). `gate51` (`BOT=cand51 REF=g_iter10 N=320 tools/mirror.sh`) is queued on the VM behind sub10.
- **The enclosure program (`src/arch_enclosure`, thirty stages; DESIGN.md "The enclosure" has the state and the
  arithmetic, TRAINING_LOG "The enclosure, stage 1" onward has every defect and fix):** the shell holds, the interior
  stays at ground, every drone lifts waiting bodies through the gate onto the shell and the outer ring, outer holders
  feed inward, corner HQs get a nine-tile shell, eight miners, four drones. Best results: alive against g_iter9 to
  r3140 on RandomSoup1 (stage 27), r3108 on Prison (stage 26), r3031 on Squares (stage 27) -- and g_iter9's HQ
  outlives ours on every map. **Why (stages 26-27, measured):** every holder is at capacity (50 digs a hundred
  rounds, all deposited); the shell takes 11-12 dirt a round on 16 tiles (0.7 a tile), g_iter9's wall 8.8 on 8
  tiles (1.1 a tile): 79 at r300, 738 at r1000, 1,844 at r2000, 2,535 at r3000 against our 351/1,207/1,720. Inflow
  is the number of holders adjacent to the structure that dig an EXTERNAL tile, times half; chains add nothing.
  The field's top bots reach 2,500-3,600 with 40-54 bodies: 24 outer holders digging Chebyshev 4 plus inner holders
  digging an interior quarry (an interior pit never floods), all placed before the outer ring floods (r950-1250 by
  map). Our bodies capped at 27-33 because the inner holders dug the outer ring into pits nobody could stand in.
  Stage 28 (stands by parity) regressed and was reverted; stage 29 (quarry every free interior tile) killed the
  building sites (no center: no drones, RandomSoup1 drowned at r948); stage 30 (three quarry tiles opposite the
  school from r500, inner holders never dig outside) was worse everywhere (the shell 291 at r2000: the quarry
  reaches five holders and takes the center's spawn tiles). **Stage 31 = stage 27's digging back** (inner holders
  dig the outer ring, no quarry) with the scouting lap and the landed rule kept: the best known configuration;
  its confirming diagnostic is `diagenc31` (expect RandomSoup1 ~r3140, ~31 bodies, ~1,200 at r2000). Then, in order: bodies on all 24 outer tiles before the flood (lift rate, soup is no longer the limit),
  interior feeders on free interior tiles, burying the center and vaporator after r1000 for feeder tiles. **Not a
  candidate yet**: it loses the wall race to the incumbent; a gate would refute it. The honest question for the
  next session: the enclosure needs about twice the wall's bodies per height by geometry, and the field's bots have
  them; if the sprint (bodies on the outer ring by r950) cannot be made, the program should be closed and its
  cheap lessons ported (none of them apply to the wall design directly).
- **Open questions for the owner (unchanged):** field-only mechanisms vs rule 5 (the home guard 48b); the
  twelve-pair floor (42c 12-2 kept, 49b 9-0 out).
- Gotchas learned this session: `MapState` statics are per robot (each robot is its own sandbox) -- cache the
  gate per robot, never assume another robot's view; a drone cannot fly over units, so the gate needs its three
  outer neighbours free (the approach); a body that loses its tile takes the shell tile it stands on ("landed") --
  filter that; `--robot ID` in replay-dump prints events, not positions; `pgrep -f` on the VM matches the ssh
  command itself (use the `[b]racket` trick); python anchor edits must grep the exact text first.

## State at the 2026-09-24 evening restart (switch to Fable 5.1; superseded above where they differ)

- **Incumbent and submission: `src/g_iter7`** (Iterations 33+34b accepted 62-34 over g_iter6 on 2026-09-25: a miner
  deposits only a 70-soup load or once the soup in reach is gone; a full miner whose walk to a drop-off stalls builds a
  refinery where it stands; the builder's walks pause on a stall). `src/bot` = g_iter7. Ladder blocks run `BOT=g_iter7`
  until its rating interval is about +-40 (roughly 300 games), then stop (owner, PROMPTS 26).
- **Ladder (batch Bradley-Terry over distinct games, `tools/elo.py --build B`):** g_iter6 1740 +- 41 after 432 games
  (blocks 50-58; 376 distinct), rank 17 of 72; g_iter5 1743 +- 33 (618 distinct of 720). Level: the mirror gain has not
  shown on the ladder yet.
- **VM idle. `src/bot` = g_iter7.** Iteration 40c closed: the gate's 34-46 was the draw (74 of 80 pairs concordant,
  discordant 4-2; TRAINING_LOG), the arm null (57-39, 1729 +- 77, flood-round losses 6 of 96 at the incumbent's
  rate, each with a ring tile unseated at r500). **The gate is paired now** (`mirror.sh` `PAIRED=1`, `tools/paired.sh`,
  TRAINING_ALGORITHM 4.4): the SPRT counts discordant pairs only, N counts pairs, cap 320.
- **ACCEPTED: `src/g_iter8`** = g_iter7 + the seat walk (Iteration 41b: a seat two from an open ring tile no seat
  touches feeds the tile between level, steps onto it and feeds the open tile). Gate paired 14-0 discordant in 320
  pairs (not one against), arm 1 flood-round loss of 96 (the finding). **Submitted: 240 band games, 108-132, 1727 +-
  49, rank 16 of 79, flood-round losses 4 of 240** (g_iter7: 1734 +- 44, 19 of 288). The roster tier and
  ONSET-merged follow g_iter8. Level with g_iter7 on the ladder: the flood-round deaths became wall races the band
  mostly wins. Diagnostics: Hills B and Spiral A, flood-round deaths in the control
  (r931/r932), become wall races lost at r2981/r2823 (TRAINING_LOG, Iteration 41). **Arm posted: flood-round losses
  1 of 96 (the finding; g_iter7 6-7), 51-45, 1737 +- 76.** The gate decides the rest.
- **Iteration 42c (the doorstep) in the gate:** seats never dig a tile beside the school or the fulfillment center
  when the building would be left fewer than three doors (the school's spawn tiles were a pit and two boxed miners
  on GSF; the unlimited forms 42/42b cost RandomSoup1 22%/12%), and no building goes on a ring tile (a refinery
  there drowned the HQ on seed 1). `src/bot` = `src/cand42c` (on g_iter8). Arm posted (49-47, 1710 +- 76,
  0 flood-round losses). **Gate 42c at 640 pairs: 12-2 discordant (p = 0.013): PROVISIONAL.** `src/bot` = g_iter8 + 42c
  (= `src/cand42c`); the next candidate stacks on it and its paired gate runs against g_iter8 (the incumbent stays
  g_iter8). A stack that reaches ACCEPT is snapshotted; one that REJECTS loses its newest member.
- **ACCEPTED: `src/g_iter9`** = g_iter8 + 42c (the school's doorstep; no building on the ring) + 43b (landscaper banks
  300/700 -> 200: bodies 200 rounds sooner, the HQ keeps its miner reserve). Gate paired ACCEPT 30-8 discordant at
  144 pairs; arm 64-32, 1771 +- 79, late losses 10 of 96 (bar 14). **Running on the VM:** `regr9` (vs arch_swarm,
  quick set) and the submission blocks `sub9-1..5` (post each with `tools/post-block.sh <run> g_iter9`; the roster
  tier and ONSET-merged follow g_iter9). **Submitted: 240 games, 78-162, rating 1735 +- 48, rank 17 of 81 -- level
  with g_iter8 (1719 +- 49); the pool had moved up to mvpatel/winkelmantanner and the locked raiders.** `src/bot` =
  g_iter9. Iteration 44 (six early miners) refuted at the diagnostic (ring 4-13% lower on three maps of four);
  Iteration 46 (no miner without income) inert on this base (the HQ cannot spawn once the ring is up).
- **Iteration 47d (the keeper: a parked miner, a keeper helper, one net gun at 16 standing r1200-1900) closed:**
  gate paired 2-14 discordant in 320 pairs (REJECT by the cap rule) and the arm at 192 games 33% raid-window losses
  against g_iter9's 35% (the first 96 games' 22 was the pool's draw). One gun does not turn a raid of 8-25 drones.
  `src/cand47d` kept. The raid line's remaining form is the home guard (Iteration 48b, `src/cand48b`: drones
  that never hunt stay at Chebyshev 3-4 and lift what lands within the box) -- **shelved**: no cost in the mirror,
  but no sparring partner reproduces the band's raid (seats lifted under fire, landscapers dropped on the freed
  tiles), so the claim has not been seen to fire and rule 5 forbids the test. **A question for the owner:** for a
  field-only mechanism whose code path fires (pickups of strays) but whose claimed case exists only in locked
  opponents' games, may the arm (96-192 band games, the raid-window count) stand in for the diagnostic? Or should
  the next tool be a faithful raider archetype (drones that charge the HQ's gun, lift seats, carry landscapers in
  behind)? `src/bot` = g_iter9. **Iteration 49b (helpers feed the ring
  to the end once the water outruns a body, `HELPER_HOLD` 230) in the gate and the arm:** the trace showed twelve
  helpers putting 77 dirt each into posts lost 150 rounds later; the rule gained +40 of ring on RandomSoup1.
  `src/bot` = `src/cand49b`. Arm posted (42-54, 1751 +- 74, late losses 25 of 96: null on the bar of 22).
  Gate 49b 9-0 discordant in 640 pairs -- under the twelve-pair floor, **not kept** (`src/cand49b`); one wall race
  in seventy is not what the late losses are lost by. `src/bot` = g_iter9. **VM idle.**
- **`progress/field-score.png`** (PROMPTS 28-30): every build's field score over time, the submissions' ratings
  fitted with R0 + a ln(1 + t/1d) and projected 7 and 14 days out with a 95% band, mapped to field scores through
  the ladder fit; `post-block.sh` refreshes it after every block. The owner watches it.
- **THE FINDING OF THE SESSION (TRAINING_LOG "The enclosure", DESIGN.md "The enclosure"):** the field's top bots
  do not raise the HQ's ring; they hold a shell at Chebyshev 2-3 and keep the interior at ground level, where the
  school, the center, vaporators and net guns live all game and the ring tiles are the quarry -- flooding spreads
  only from a flooded neighbour. They stand 40-54 landscapers and 30-124 drones at r1500-2000 against our 7-14 and
  none, and win the late games by half a wall. Every line this session tuned or fought our design; this replaces it.
  **The program is under way: `src/arch_enclosure`, twenty-eight stages on 2026-09-25** (TRAINING_LOG "The
  enclosure, stage 1" onward; DESIGN.md "The enclosure" has the state and the arithmetic). The shell holds, the
  interior stays at ground, every drone lifts through the gate, outer holders feed inward, corner HQs get a
  nine-tile shell, eight miners; alive to r3000-3140 against g_iter9 on three maps and still behind its wall on
  every one (its 8 tiles take 1.1 a tile a round from 17 bodies; our 16 take 0.7 from 31, at capacity). The bound
  is bodies per tile, and bodies are bound by dry tiles to stand on. Next: stands filled before the flood (the
  sprint), interior feeders, burying buildings for feeder tiles. **Not a candidate yet** -- it loses the wall
  race to the incumbent by 100-300 rounds on every map; the gate would refute it. Diagnostics: `tools/vm-run.sh
  diagencN '... tools/run-dev.sh arch_enclosure g_iter9 <map> ...; python3 tools/enc-read.py <replay> <side>
  <log>'` (`enc-read.py` prints bodies, buildings, the shell at 2 and the interior at 1 by round, and the role
  tags; the per-holder dirt accounting script is in the transcript, `acct.py`: digs/self/fed/reclaimed per 100
  rounds by tile distance).
- **VM:** the stage diagnostics only (three games, ~25 minutes); nothing gated. `src/bot` = g_iter9.
- **Iteration 40c as gated:** `gate40c` (Iteration 40c, `src/bot` = `src/cand40c`: a miner boxed in for 20 rounds with a
  landscaper of ours within 2 steps onto an empty ring tile, once per 100 rounds, before r400; mirror vs g_iter7,
  seeded, cap 240) and its ladder arm `arm40c-a`/`arm40c-b` (`BOT=cand40c`, post with `tools/post-block.sh <run>
  cand40c`; a finding at 2 or fewer flood-round losses of 96, g_iter7's rate gives 6-7). Diagnostics: Hills sealed
  and won, Climb B two more seats, GSF within the control band, RandomSoup1 level (TRAINING_LOG, Iteration 40).
- **Before 40c:** `src/bot` was g_iter7. Iterations 38 (seats spare the walkway; -6% ring elsewhere), 39 (never fired) and 39b (bridges
  fired, no seat gained, GSF lost) were not gated (`src/cand38`, `src/cand39`, `src/cand39b`). Iteration 37 (one net gun on the raised site) refuted: ladder arm 19 raid-window
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
  one, changed nothing on Hills, and cost 6% of ring on RandomSoup1 -- not gated. The tile-by-tile reading of Climb (end of the
  Iteration 39 entry) names the two-part form that was not tried: seats spare a circle tile beside a ring tile still
  unseated past r250, and a boxed-in miner beside an unseated ring tile steps onto it.
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
