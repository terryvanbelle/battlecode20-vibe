# Which metric starts predicting the result first

94 games, 84 wins. Noise floor about 0.21; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | digs (us-them) | +0.64 | +0.12 | +0.51 | +0.53 | +0.53 | +0.54 | +0.61 |
| r150 | - | digs (us-them) ~avg | +0.63 | +0.12 | +0.47 | +0.52 | +0.53 | +0.54 | +0.58 |
| r150 | - | dirtDeps (us-them) | +0.64 | +0.17 | +0.50 | +0.53 | +0.53 | +0.54 | +0.61 |
| r150 | - | dirtDeps (us-them) ~avg | +0.63 | +0.17 | +0.47 | +0.53 | +0.53 | +0.54 | +0.58 |
| r150 | - | landscapers (us-them) | +0.55 | +0.25 | +0.48 | +0.47 | +0.49 | +0.54 | +0.47 |
| r150 | - | landscapers (us-them) ~avg | +0.57 | +0.25 | +0.49 | +0.49 | +0.50 | +0.54 | +0.56 |
| r250 | - | worth (us-them) | +0.36 | +0.25 | +0.29 | +0.32 | +0.35 | +0.34 | +0.16 |
| r300 | - | worth (us-them) ~avg | +0.35 | +0.24 | +0.28 | +0.31 | +0.33 | +0.35 | +0.26 |
| r350 | - | robots (us-them) | +0.39 | +0.17 | +0.27 | +0.29 | +0.34 | +0.38 | +0.25 |
| r350 | - | units (us-them) | +0.44 | +0.13 | +0.27 | +0.30 | +0.35 | +0.40 | +0.38 |
| r400 | - | mines (us-them) | +0.32 | +0.23 | +0.27 | +0.28 | +0.30 | +0.31 | +0.26 |
| r400 | - | robots (us-them) ~avg | +0.36 | +0.16 | +0.26 | +0.28 | +0.30 | +0.35 | +0.33 |
| r450 | - | spawned (us-them) | +0.32 | +0.17 | +0.26 | +0.25 | +0.29 | +0.32 | +0.18 |
| r450 | - | units (us-them) ~avg | +0.42 | +0.13 | +0.24 | +0.27 | +0.30 | +0.36 | +0.40 |
| r550 | - | mines (us-them) ~avg | +0.31 | +0.23 | +0.26 | +0.28 | +0.29 | +0.31 | +0.27 |
| r600 | - | spawned (us-them) ~avg | +0.31 | +0.16 | +0.26 | +0.26 | +0.28 | +0.30 | +0.27 |
| - | - | aba (us-them) [inverted] | +0.16 | -0.08 | +0.07 | +0.02 | +0.10 | +0.16 | +0.14 |
| - | - | aba (us-them) [inverted] ~avg | +0.14 | -0.07 | +0.04 | +0.03 | +0.06 | +0.12 | +0.14 |
| - | - | cov (us-them) | +0.20 | +0.05 | +0.10 | +0.19 | +0.20 | +0.14 | +0.16 |
| - | - | cov (us-them) ~avg | +0.17 | +0.05 | +0.08 | +0.14 | +0.17 | +0.16 | +0.13 |
| - | - | died (us-them) [inverted] | -0.10 | -0.01 | -0.01 | +0.06 | +0.07 | +0.08 | +0.09 |
| - | - | died (us-them) [inverted] ~avg | -0.10 | -0.04 | -0.01 | +0.03 | +0.05 | +0.07 | +0.09 |
| - | - | drones (us-them) | +0.25 | -0.10 | -0.04 | +0.06 | +0.14 | +0.20 | +0.24 |
| - | - | drones (us-them) ~avg | +0.25 | -0.10 | -0.06 | -0.00 | +0.06 | +0.14 | +0.17 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.05 | . | -0.04 | +0.04 | -0.04 | -0.04 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.05 | . | -0.04 | +0.03 | -0.04 | -0.04 | -0.05 |
| - | - | miners (us-them) | -0.19 | +0.04 | -0.01 | -0.03 | -0.08 | -0.16 | -0.19 |
| - | - | miners (us-them) ~avg | +0.12 | +0.08 | +0.02 | -0.00 | -0.03 | -0.07 | -0.11 |
| - | - | moves (us-them) | +0.16 | -0.02 | +0.05 | +0.13 | +0.15 | +0.15 | +0.16 |
| - | - | moves (us-them) ~avg | +0.17 | -0.02 | +0.02 | +0.08 | +0.12 | +0.14 | +0.15 |
| - | - | netguns (us-them) | +0.12 | . | -0.04 | -0.06 | -0.06 | +0.12 | +0.09 |
| - | - | netguns (us-them) ~avg | -0.06 | . | -0.04 | -0.05 | -0.05 | +0.04 | +0.05 |
| - | - | pickups (us-them) | +0.10 | -0.05 | +0.01 | -0.02 | -0.00 | +0.07 | +0.09 |
| - | - | pickups (us-them) ~avg | -0.09 | -0.05 | -0.02 | -0.02 | -0.02 | +0.01 | +0.03 |
| - | - | soup (us-them) | +0.14 | +0.10 | +0.07 | +0.04 | +0.08 | +0.08 | +0.10 |
| - | - | soup (us-them) ~avg | +0.14 | +0.12 | +0.03 | +0.04 | +0.06 | +0.07 | +0.08 |
| - | - | vaporators (us-them) | +0.16 | . | +0.01 | +0.14 | +0.16 | +0.13 | -0.03 |
| - | - | vaporators (us-them) ~avg | +0.15 | . | +0.05 | +0.12 | +0.13 | +0.15 | +0.03 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
