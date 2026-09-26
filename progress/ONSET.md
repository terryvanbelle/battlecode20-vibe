# Which metric starts predicting the result first

2 games, 2 wins. Noise floor about 1.41; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| - | - | aba (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | aba (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | cov (us-them) | . | . | . | . | . | . | . |
| - | - | cov (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | died (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | died (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | digs (us-them) | . | . | . | . | . | . | . |
| - | - | digs (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | dirtDeps (us-them) | . | . | . | . | . | . | . |
| - | - | dirtDeps (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | drones (us-them) | . | . | . | . | . | . | . |
| - | - | drones (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | landscapers (us-them) | . | . | . | . | . | . | . |
| - | - | landscapers (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | mines (us-them) | . | . | . | . | . | . | . |
| - | - | mines (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | moves (us-them) | . | . | . | . | . | . | . |
| - | - | moves (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) | . | . | . | . | . | . | . |
| - | - | netguns (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | pickups (us-them) | . | . | . | . | . | . | . |
| - | - | pickups (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | robots (us-them) | . | . | . | . | . | . | . |
| - | - | robots (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | soup (us-them) | . | . | . | . | . | . | . |
| - | - | soup (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | spawned (us-them) | . | . | . | . | . | . | . |
| - | - | spawned (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | units (us-them) | . | . | . | . | . | . | . |
| - | - | units (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | vaporators (us-them) | . | . | . | . | . | . | . |
| - | - | vaporators (us-them) ~avg | . | . | . | . | . | . | . |
| - | - | worth (us-them) | . | . | . | . | . | . | . |
| - | - | worth (us-them) ~avg | . | . | . | . | . | . | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
