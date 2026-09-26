# battlecode20-vibe: session rules

Read `TRAINING_ALGORITHM.md` (the loop), `RULES.md` (the game, engine-checked), `SETUP.md`
before running anything, and `HANDOFF.md` for the state of the loop.

1. **Games in volume run only on the VM `battlecode-dev`** through `tools/vm-run.sh`. The driver
   (2 vCPU, 2 GB) hosts this session and may play one small diagnostic game at a time.
2. **Push after every commit**; **record every user prompt verbatim in `PROMPTS.md`**
   (`## <n>. <date>` then the text).
3. **External bots' source is never read** (`BENCHMARK.md` rule 1). **Their games may be reviewed
   at any win rate** (PROMPTS 45, 2026-09-26: the 20% rule is retired; `tools/tier-check.sh` only
   reports the tier now).
4. **External bots are played only as scrimmages** (`tools/scrim.sh`: random map, random side,
   rotating opponents drawn from the rating band around us -- the graded pool approved 2026-09-24,
   PROMPTS 8-9; `POOLMODE=above` is the old just-above-us pool -- seeded from never-played bots
   with `EXPLORE=n`; the field is calibrated by playing never-played bots two games each -- all 65
   are placed, PROMPTS 9-10). Builds are graded by the batch Bradley-Terry rating of `tools/elo.py
   --build`, never by raw win rate across pools (PROMPTS 15-17). Never choose a map or a side
   against a benchmark bot. Our own snapshots and archetypes are unrestricted.
5. **No test before a diagnostic game shows the mechanism firing** (`TRAINING_ALGORITHM.md` 4.3).
6. **`tools/unit-tests.sh` after every change** to the bot or to any tool.
7. Bot changes need no approval; design decisions are the loop's to make.
8. Nothing stale stays in the repository: a chart or document that no longer matches the data is
   regenerated or deleted.
9. Post-mortems from 2020 are never read, first- or second-hand.
