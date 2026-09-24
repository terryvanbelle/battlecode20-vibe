# battlecode20-vibe

A [Battlecode 2020](https://battlecode.org) ("Soup": miners, landscapers, delivery drones and a
rising flood) bot, developed under an evidence-driven training loop by one human directing one AI
(Claude Code). Practice project, not a tournament entry; the fifth in the series after
[battlecode22-vibe](https://github.com/terryvanbelle/battlecode22-vibe),
[battlecode26-vibe](https://github.com/terryvanbelle/battlecode26-vibe),
[battlecode25-vibe](https://github.com/terryvanbelle/battlecode25-vibe) and
[battlecode21-vibe](https://github.com/terryvanbelle/battlecode21-vibe).

| file | what |
|---|---|
| `TRAINING_ALGORITHM.md` | the loop, in year-agnostic terms |
| `RULES.md` | the game, digested and checked against the engine source |
| `DESIGN.md` | how the bot is organised |
| `BENCHMARK.md` | the external opponents and the rules for using them |
| `RESEARCH.md` | what recurs across other years' post-mortems |
| `TRAINING_LOG.md` | every attempt, its pre-registration, its numbers, its verdict |
| `LEARNINGS.md` | durable lessons with the measurement behind each |
| `HANDOFF.md` | the state of the loop for a fresh session |
| `SETUP.md` | the two machines and how games run |
| `PROMPTS.md` | every instruction from the human, verbatim |
| `progress/` | the ladder (Bradley-Terry ratings) and the onset chart |
| `tools/` | runner, gate, ladder, replay dumper, tests (`tools/README.md`) |
| `src/bot` | the bot; `src/g_iterN` accepted snapshots; `src/arch_*` sparring partners |
