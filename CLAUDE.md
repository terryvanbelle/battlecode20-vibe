# battlecode20-vibe: session rules

Read `TRAINING_ALGORITHM.md` (the loop), `RULES.md` (the game, engine-checked), `SETUP.md`
before running anything, and `HANDOFF.md` for the state of the loop.

1. **Games in volume run only on the VM `battlecode-dev`** through `tools/vm-run.sh`. The driver
   (2 vCPU, 2 GB) hosts this session and may play one small diagnostic game at a time.
2. **Push after every commit**; **record every user prompt verbatim in `PROMPTS.md`**
   (`## <n>. <date>` then the text).
3. **External bots are never read** (`BENCHMARK.md` rule 1) and **their games are never
   reviewed below a 20% win rate** (rule 2; `tools/tier-check.sh` fails closed).
4. **External bots are played only as scrimmages** (`tools/scrim.sh`: random map, random side,
   rotating opponents drawn from the rating band around us -- the graded pool approved 2026-09-24,
   PROMPTS 8-9; `POOLMODE=above` is the old just-above-us pool -- seeded from never-played bots
   with `EXPLORE=n`; the ladder grade is calibrated by playing the never-played bots, two games
   each, until the field is covered -- games run fast enough now, PROMPTS 9-10). Never choose a map or a side
   against a benchmark bot. Our own snapshots and archetypes are unrestricted.
5. **No test before a diagnostic game shows the mechanism firing** (`TRAINING_ALGORITHM.md` 4.3).
6. **`tools/unit-tests.sh` after every change** to the bot or to any tool.
7. Bot changes need no approval; design decisions are the loop's to make.
8. Nothing stale stays in the repository: a chart or document that no longer matches the data is
   regenerated or deleted.
9. Post-mortems from 2020 are never read, first- or second-hand.
