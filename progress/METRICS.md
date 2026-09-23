# What the metrics mean and how they are computed

Companion to `progress/onset-ladder.png` and the tables printed by `tools/correlate.py`,
`tools/onset.py` and `tools/scrim-study.py`. Everything here is derived from `.bc20` replays by
`tools/replaydump/ReplayDump.java`, which replays the engine's own event stream (spawns, moves,
deaths, the fourteen action codes, dirt/water/soup changes, pollution, blockchain traffic,
bytecodes) and reconstructs every robot and tile round by round. Nothing is estimated.

## Names

`<quantity>` is our own value, `<quantity> (theirs)` the opponent's, `<quantity> (us-them)` the
difference. A trailing `[inverted]` means the raw quantity is better when smaller and has been
negated (see Orientation). "Us" is the team our bot played, whichever side of the map; the study
script reads the side out of the replay filename, so A/B never leaks into the numbers.

## Quantities (sampled every 50 rounds to r1200; cumulative counts are since round 1)

| name | meaning |
|---|---|
| `soup` | refined soup in the team pool |
| `hq` | 1 while our HQ stands |
| `hqBuried` `[inverted]` | dirt currently on our HQ (dies at 50) |
| `hqElev` `[unoriented]` | elevation of the HQ's tile (a map property) |
| `worth` | net worth: pool soup + build cost of every living robot (the engine's third tiebreak) |
| `robots` (derived) | living robots of every type, the engine's second tiebreak |
| `units` (derived) | miners + landscapers + drones |
| `miners` `landscapers` `drones` `refineries` `vaporators` `schools` `centers` `netguns` | living count by type |
| `spawned` `spawnCost` | robots built so far and the soup they cost |
| `died` `drowned` `shot` `buriedDeaths` `[inverted]` | deaths so far, by cause where the engine records one |
| `mines` `soupDeps` `refines` | mining actions (each takes up to 7 soup), deposits into a refinery/HQ, refining turns |
| `digs` `dirtDeps` | landscaper dig and deposit actions |
| `pickups` `drops` `shots` | drone pickups and drops, net-gun/HQ shots |
| `moves` | successful moves so far |
| `cov` | per mille of the map's tiles some robot of the team has ever stood on |
| `aba` `[inverted]` | moves that returned to the tile of two moves earlier (oscillation) |
| `bcOver` `[inverted]` | robot-turns at or over the bytecode limit |
| `water` `flooded` `pollution` | the round's water level, flooded tile count and global pollution (map-wide, both teams) |
| `firstHQ` `[unoriented]` | round of first contact with the enemy HQ (within r2 35) |

## Orientation

Defined in `tools/polarity.py`. Each quantity is marked higher-is-better (+1) or
lower-is-better (-1) and multiplied by that sign before anything is correlated; the opponent's
value is negated as well; a difference is multiplied by the metric's own sign. The single reading
rule: **a positive correlation always means "this being better goes with us winning"**.

## Reading correlations

`tools/correlate.py` prints, per round, each metric's point-biserial correlation with the result
(`corr`), the same correlation after removing each opponent's own mean (`within`, so a metric
cannot score merely by identifying weak opponents), and the medians in wins and losses.
`tools/onset.py` prints the correlation at every sampled round and the **onset**: the first round
where it reaches +0.30 and holds (an `anti` column flags a metric predicting the result
backwards). Act on the earliest onset; late-round correlations are the scoreboard.
