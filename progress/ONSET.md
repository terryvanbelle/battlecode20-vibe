# Which metric starts predicting the result first

45 games, 28 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | pickups (us-them) | +0.53 | +0.32 | +0.22 | +0.31 | +0.40 | +0.45 | +0.52 |
| r100 | - | pickups (us-them) ~avg | +0.51 | +0.32 | +0.28 | +0.30 | +0.36 | +0.44 | +0.50 |
| r200 | - | mines (us-them) | +0.46 | +0.06 | +0.34 | +0.36 | +0.37 | +0.42 | +0.46 |
| r200 | - | netguns (us-them) | +0.39 | . | +0.31 | +0.39 | +0.32 | +0.32 | +0.37 |
| r200 | - | netguns (us-them) ~avg | +0.41 | . | +0.31 | +0.36 | +0.36 | +0.35 | +0.37 |
| r200 | - | robots (us-them) | +0.68 | -0.06 | +0.33 | +0.45 | +0.48 | +0.53 | +0.61 |
| r200 | - | spawned (us-them) | +0.67 | -0.06 | +0.31 | +0.45 | +0.48 | +0.54 | +0.65 |
| r200 | - | worth (us-them) | +0.44 | +0.07 | +0.44 | +0.40 | +0.41 | +0.39 | +0.34 |
| r200 | - | worth (us-them) ~avg | +0.40 | +0.03 | +0.30 | +0.37 | +0.39 | +0.40 | +0.39 |
| r250 | - | miners (us-them) | +0.45 | +0.10 | +0.24 | +0.45 | +0.44 | +0.31 | +0.25 |
| r250 | - | units (us-them) | +0.67 | -0.03 | +0.23 | +0.39 | +0.45 | +0.52 | +0.62 |
| r300 | - | mines (us-them) ~avg | +0.43 | +0.06 | +0.21 | +0.32 | +0.35 | +0.38 | +0.42 |
| r300 | - | robots (us-them) ~avg | +0.61 | -0.14 | +0.13 | +0.34 | +0.43 | +0.49 | +0.56 |
| r300 | - | spawned (us-them) ~avg | +0.61 | -0.14 | +0.11 | +0.34 | +0.43 | +0.49 | +0.56 |
| r350 | - | cov (us-them) | +0.42 | -0.08 | -0.03 | +0.24 | +0.34 | +0.39 | +0.42 |
| r350 | - | landscapers (us-them) | +0.74 | -0.20 | +0.07 | +0.20 | +0.50 | +0.65 | +0.72 |
| r350 | - | miners (us-them) ~avg | +0.38 | -0.07 | +0.08 | +0.30 | +0.37 | +0.37 | +0.33 |
| r350 | - | units (us-them) ~avg | +0.60 | -0.13 | +0.07 | +0.26 | +0.37 | +0.46 | +0.55 |
| r400 | - | moves (us-them) | +0.33 | -0.05 | -0.02 | +0.21 | +0.31 | +0.31 | +0.27 |
| r450 | - | landscapers (us-them) ~avg | +0.76 | -0.19 | -0.03 | +0.03 | +0.22 | +0.55 | +0.72 |
| r500 | - | digs (us-them) | +0.75 | -0.05 | +0.01 | +0.04 | +0.16 | +0.58 | +0.72 |
| r500 | - | dirtDeps (us-them) | +0.74 | +0.04 | +0.00 | +0.03 | +0.13 | +0.56 | +0.71 |
| r550 | - | cov (us-them) ~avg | +0.38 | -0.11 | -0.08 | +0.09 | +0.21 | +0.33 | +0.38 |
| r600 | - | digs (us-them) ~avg | +0.73 | -0.03 | +0.00 | +0.02 | +0.09 | +0.34 | +0.65 |
| r600 | - | dirtDeps (us-them) ~avg | +0.72 | +0.06 | -0.01 | -0.00 | +0.06 | +0.31 | +0.64 |
| - | - | aba (us-them) [inverted] | +0.23 | +0.00 | +0.23 | +0.07 | +0.05 | +0.14 | +0.08 |
| - | - | aba (us-them) [inverted] ~avg | +0.15 | -0.04 | +0.15 | +0.11 | +0.08 | +0.10 | +0.12 |
| - | - | died (us-them) [inverted] | -0.25 | . | +0.20 | +0.01 | +0.03 | -0.05 | -0.15 |
| - | - | died (us-them) [inverted] ~avg | +0.23 | . | +0.22 | +0.11 | +0.07 | +0.02 | -0.09 |
| - | - | drones (us-them) | +0.37 | +0.37 | +0.18 | +0.16 | +0.22 | +0.24 | +0.25 |
| - | - | drones (us-them) ~avg | +0.37 | +0.37 | +0.22 | +0.22 | +0.22 | +0.24 | +0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.18 | +0.18 | +0.18 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.18 | +0.18 | +0.18 | . | . | +0.12 | +0.12 |
| - | - | moves (us-them) ~avg | +0.30 | -0.03 | -0.03 | +0.09 | +0.21 | +0.29 | +0.28 |
| - | - | soup (us-them) | +0.23 | +0.16 | +0.22 | -0.06 | -0.05 | -0.16 | -0.17 |
| - | - | soup (us-them) ~avg | +0.29 | +0.17 | +0.29 | +0.15 | +0.02 | -0.08 | -0.14 |
| - | - | vaporators (us-them) | +0.28 | +0.21 | +0.21 | +0.18 | +0.10 | +0.20 | -0.05 |
| - | - | vaporators (us-them) ~avg | +0.25 | +0.21 | +0.21 | +0.18 | +0.15 | +0.15 | +0.10 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
