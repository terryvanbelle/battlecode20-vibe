# Which metric starts predicting the result first

23 games, 7 wins. Noise floor about 0.42; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | soup (us-them) ~avg | +0.30 | +0.30 | +0.20 | +0.22 | +0.22 | +0.22 | +0.26 |
| - | - | aba (us-them) [inverted] | -0.18 | +0.13 | -0.08 | -0.14 | -0.11 | -0.14 | -0.10 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | +0.15 | +0.00 | -0.12 | -0.11 | -0.13 | -0.14 |
| - | - | cov (us-them) | +0.21 | +0.10 | +0.04 | -0.03 | -0.02 | +0.01 | +0.11 |
| - | - | cov (us-them) ~avg | +0.17 | +0.15 | +0.05 | -0.00 | -0.01 | +0.00 | +0.03 |
| - | - | died (us-them) [inverted] | +0.28 | . | +0.12 | +0.28 | +0.17 | +0.06 | -0.07 |
| - | - | died (us-them) [inverted] ~avg | +0.27 | . | +0.12 | +0.25 | +0.24 | +0.16 | +0.00 |
| - | - | digs (us-them) | +0.19 | +0.15 | -0.01 | -0.06 | -0.02 | +0.04 | +0.15 |
| - | - | digs (us-them) ~avg | +0.15 | +0.15 | +0.01 | -0.04 | -0.03 | -0.01 | +0.10 |
| - | - | dirtDeps (us-them) | +0.23 | +0.11 | -0.03 | -0.10 | -0.06 | +0.02 | +0.15 |
| - | - | dirtDeps (us-them) ~avg | +0.12 | +0.11 | -0.01 | -0.07 | -0.07 | -0.04 | +0.08 |
| - | - | drones (us-them) | +0.28 | +0.07 | -0.07 | -0.11 | +0.04 | +0.12 | +0.17 |
| - | - | drones (us-them) ~avg | +0.22 | +0.07 | +0.08 | -0.03 | -0.01 | +0.05 | +0.12 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.14 | +0.14 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.14 | +0.14 | . | . | . | . | . |
| - | - | landscapers (us-them) | -0.22 | -0.04 | -0.08 | -0.22 | -0.21 | +0.08 | +0.15 |
| - | - | landscapers (us-them) ~avg | -0.20 | -0.04 | -0.09 | -0.17 | -0.20 | -0.09 | +0.08 |
| - | - | miners (us-them) | +0.27 | +0.04 | +0.04 | +0.24 | +0.23 | +0.14 | +0.04 |
| - | - | miners (us-them) ~avg | +0.21 | +0.07 | +0.05 | +0.13 | +0.19 | +0.20 | +0.11 |
| - | - | mines (us-them) | +0.28 | +0.08 | -0.01 | +0.08 | +0.11 | +0.16 | +0.22 |
| - | - | mines (us-them) ~avg | +0.22 | +0.09 | +0.00 | +0.04 | +0.08 | +0.12 | +0.17 |
| - | - | moves (us-them) | +0.13 | +0.10 | +0.06 | +0.02 | -0.00 | +0.01 | +0.02 |
| - | - | moves (us-them) ~avg | +0.13 | +0.11 | +0.09 | +0.05 | +0.02 | +0.02 | +0.01 |
| - | r350 | netguns (us-them) | -0.32 | . | . | . | -0.32 | -0.26 | +0.01 |
| - | r350 | netguns (us-them) ~avg | -0.32 | . | . | . | -0.32 | -0.28 | -0.20 |
| - | - | pickups (us-them) | +0.25 | . | +0.25 | +0.19 | -0.03 | -0.04 | -0.06 |
| - | - | pickups (us-them) ~avg | +0.26 | . | +0.26 | +0.24 | +0.10 | +0.01 | -0.04 |
| - | - | robots (us-them) | +0.20 | -0.07 | -0.06 | +0.01 | +0.09 | +0.14 | +0.16 |
| - | - | robots (us-them) ~avg | +0.17 | -0.01 | -0.05 | -0.03 | +0.02 | +0.10 | +0.13 |
| - | - | soup (us-them) | +0.38 | +0.38 | +0.08 | +0.26 | +0.22 | +0.25 | +0.20 |
| - | - | spawned (us-them) | +0.22 | -0.07 | -0.07 | -0.03 | +0.07 | +0.14 | +0.18 |
| - | - | spawned (us-them) ~avg | +0.20 | -0.01 | -0.06 | -0.05 | -0.01 | +0.08 | +0.14 |
| - | - | units (us-them) | +0.19 | +0.02 | -0.05 | -0.05 | +0.01 | +0.13 | +0.13 |
| - | - | units (us-them) ~avg | +0.16 | +0.05 | -0.01 | -0.04 | -0.02 | +0.07 | +0.11 |
| - | - | vaporators (us-them) | -0.25 | -0.25 | +0.22 | +0.18 | +0.22 | +0.15 | +0.20 |
| - | - | vaporators (us-them) ~avg | -0.25 | -0.25 | +0.12 | +0.18 | +0.20 | +0.18 | +0.18 |
| - | - | worth (us-them) | +0.24 | +0.08 | +0.04 | +0.14 | +0.20 | +0.20 | +0.20 |
| - | - | worth (us-them) ~avg | +0.24 | +0.11 | +0.05 | +0.10 | +0.15 | +0.19 | +0.19 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
