# Environment setup

Two GCP machines, project `tvanbelle-vibecode`, zone `us-west1-b` (unchanged from the 2021 project):

| machine | type | role |
|---|---|---|
| `claude-driver` | e2-small, 2 vCPU / 2 GB | hosts the Claude Code session, the repo, replay analysis, plots. Runs at most one small diagnostic game. |
| `battlecode-dev` | e2-standard-8, 8 vCPU / 31 GB, 20 GB disk | runs every gauntlet, gate and scrimmage block. Shared with the other years' projects. |

A 2020 game on `maptestsmall` (32x32, both HQs flooding at r256) takes 13 s on the driver; a
walled 64x64 game runs to r2500+ and several minutes. Everything that spawns
`battlecode.server.Main` in volume belongs on the VM.

## Layout (identical on both machines)

```
~/jdk/jdk8u504-b01                       JDK 8 (the engine is Java 8; tools/lib.sh exports it)
~/projects/vibe/2020                     this repo; engine/ is built here and gitignored
~/projects/vibe/2020/engine              engine.jar, lib/, maps/ (52 .map20), VERSION  (tools/build-engine.sh)
~/projects/vibe/bc20-benchmarks          benchmark repos (driver only; never read); _classes/ + manifest.tsv synced to the VM
~/projects/vibe/reference/battlecode20   engine source (the truth for RULES.md)
```

## Building the engine (once per machine)

`tools/build-engine.sh` clones `battlecode/battlecode20`, patches the rotted build (jcenter,
the jsi snapshot, a missing subproject), builds with JDK 8 and Gradle 5.6.2 (already cached in
`~/.gradle`) and stages `engine/`. Official downloads are dead. ~4 minutes on the driver.

## Running

- `tools/run-match.sh A B map [replay]` -- one headless game with bare `java`, prints
  `RESULT <A|B> <round> <reason>`.
- `tools/run-dev.sh` -- the same from a private compile (`build/dev-classes`), safe beside a
  gauntlet; `LOG_OUT=file` keeps the engine stdout (our `@tag` lines need
  `-Dbc.server.robot-player-to-system-out=true`).
- `tools/replay-dump.sh replay.bc20 [flags]` -- the microscope (see `tools/replaydump/ReplayDump.java`).
- `tools/gauntlet.sh`, `tools/mirror.sh`, `tools/scrim.sh` -- volume runners, meant for the VM
  through `tools/vm-run.sh <log> '<cmd>'`; follow with `tools/vm-tail.sh`, fetch with
  `tools/vm-collect.sh <run-id>`, stop the idle VM with `tools/vm-stop.sh`.
- `tools/unit-tests.sh` -- bot tests (`test/bot/*Test.java`) and tool tests (`tools/test_tools.py`).

`tools/vm-sync.sh` replaces the VM's `src tools test progress` with the driver's on every
`vm-run.sh`; anything a run writes into those directories on the VM is lost at the next launch.
Keep the VM at 7 games or fewer at once; two runs must never share a class tree
(`CLASSES=build/<private>`). Check `df -h ~` on the VM before a gate: a gate keeps every loss.

Reaching the VM: `tools/vm.sh` uses plain ssh with `~/.ssh/google_compute_engine` and caches the
external IP; `ensure_vm` starts the instance when it is stopped.
