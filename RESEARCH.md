# RESEARCH.md -- what recurs across Battlecode years (2020 excluded)

Cross-year findings kept for the moment the loop runs out of local ideas
(`TRAINING_ALGORITHM.md` section 7). The season being played is **2020**, so every 2020
post-mortem is out of bounds, first- or second-hand. This file is built from the predecessor
projects' syntheses of 2019, 2021, 2022, 2023, 2024, 2025 and 2026 sources.

**Disclosure.** The predecessor documents this project was required to read
(`battlecode21-vibe/RESEARCH.md`, `battlecode22-vibe/RESEARCH.md`, `battlecode26-vibe/RESEARCH.md`,
`bcenv/HISTORICAL_LEARNINGS.md`) cite a few 2020 teams for *process* advice (build
infrastructure first; basics done well beat elaborate coordination; rewrite when the strategy
changes; test a counter against the thing it counters; prioritise by expected win rate). Those
lines were read as part of that mandatory review before this file's rule could be applied; the
2020 section of the bcenv history and every line mentioning 2020 in the other two files were
filtered out before reading. Nothing about 2020 strategy, units or maps was read, no 2020
post-mortem will be opened, and every strategic idea for this season is derived from the rules,
the engine and our own measurements.

## 1. Process (transfers best)

- **Infrastructure first, strategy second.** Navigation, communication, resource gathering and
  the test harness rarely change when the strategy does (SPAARK 2025, 4 Musketeers 2023).
- **A parallel local runner is table stakes**; teams copy each other's (SPAARK 2025 traces theirs
  to Producing Perfection 2022). A first-year team's top regret was not having one (2023).
- **A handful of games is not a measurement** (don't @ me 2023). Full sets, both sides, old
  versions *and* outside opponents; live opponents outrank frozen ones (SPAARK 2025); not
  scrimming is how you miss a meta shift (Oak's Last Disciple 2019).
- **Robustness over optimality**: "make it work, make it right, make it fast" (2023); the winners
  perform the basics really well.
- **Micro over macro**: "a slightly improved macro might add 5%, micro 30-50%" (Gone Fishin' 2023).
- **Do not fix everything a replay shows**; pick one or two changes and do them very well.
- **Root-cause single losses** from replays; a corner-case null pointer cost a seeded match
  (4 Musketeers 2023).
- **Overnight volume finds real gains** (SPAARK 2025); **never submit an untested last-minute
  change** (SPAARK, twice).
- **Nontransitivity is real**: beating A which beats B says nothing about B.

## 2. Perennial mechanics

- **Maps are symmetric** (rotation or a reflection) and inferring which, then extrapolating the
  unseen half, is "Battlecode 101" (The Kragle 2025, Gone Fishin' 2023 who assumed rotation and
  were burned). Scouting toward the centre disambiguates fastest.
- **Bytecode forbids textbook search**: bug navigation on binary passability, greedy movement on
  graded terrain, an unrolled fixed-radius local search where affordable, a turn stack to escape
  concave traps, friendlies as soft obstacles, randomised tie-breaks as the last resort.
- **Communication is a scarce, structured resource**: sectors instead of coordinates, batched
  writes, a local cache refreshed lazily; define what happens when a writer dies (2022).
- **Kite after every attack** where cooldowns allow; attack even blind; retreat only near death
  and as a group; target by kills-per-turn; step onto favourable terrain after acting.
- **Emergent coordination beats commanded coordination**: spawn order as a formation, repulsion
  fields for exploration, a heading term to avoid re-sweeping (2021 wololo, 2023 Gone Fishin').
- **Identify the real scarce resource** and deny it: occupy spawn tiles, partially complete a
  shared objective, kill production before combat units (2021, 2025).
- **Adapt rush vs turtle per map** from measured signals (distance, passability, resource
  density), never at compile time (2023, 2025).
- **Balance patches invalidate razor-thin edges**; keep mechanics decoupled from the strategy.
- **State machines with remembered return points**; goal objects with start/run/stop conditions.
- **Economic discipline**: fewer wasted actions beats a better planner (confused 2025).

## 3. What the predecessor projects measured (method, not season)

- The accept gate must be able to resolve the effects you can actually build: a 48-cell panel
  resolved nothing under 75%; a sequential test on random maps accepted the next candidate.
- A mechanism that compiles and runs is not a mechanism that fires; count the decision.
- Correlate to generate hypotheses, diagnose to filter them; act only on early-round onsets.
- Self-play gains need not transfer; repairs of defects and removals of binding caps transferred,
  reallocations mostly did not.
- Every instrument built was wrong on first use: run it on a case whose answer you know.

## 4. Sources

All at `battlecode.org/assets/files/postmortem-<year>-<team>.pdf` unless noted. 2019: smite,
Oak's Last Disciple, Big Red Battlecode, Double J. 2021: Baby Ducks (web), wololo, 3 Musketeers.
2022: 5 Musketeers. 2023: Gone Fishin', 4 Musketeers, don't @ me, no thoughts head empty.
2024: cout for clout, muskellunge. 2025: Just Woke Up, confused, Om Nom, SPAARK, The Kragle.
2026: food, Generalized Stroke's Theorem. Plus Ivan Geffner's undated "A Guide to Battlecode".
**No 2020 source is used.**
