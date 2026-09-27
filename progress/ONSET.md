# Which metric starts predicting the result first

46 games, 22 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.50 | +0.30 | +0.46 | +0.29 | +0.27 | +0.29 | +0.50 |
| r50 | - | miners (us-them) ~avg | +0.50 | +0.36 | +0.46 | +0.47 | +0.37 | +0.35 | +0.44 |
| r50 | - | mines (us-them) | +0.63 | +0.49 | +0.41 | +0.50 | +0.47 | +0.44 | +0.58 |
| r50 | - | mines (us-them) ~avg | +0.65 | +0.49 | +0.45 | +0.50 | +0.52 | +0.49 | +0.63 |
| r50 | - | units (us-them) | +0.74 | +0.49 | +0.40 | +0.45 | +0.46 | +0.36 | +0.65 |
| r50 | - | units (us-them) ~avg | +0.73 | +0.47 | +0.45 | +0.48 | +0.46 | +0.46 | +0.68 |
| r100 | - | landscapers (us-them) | +0.62 | +0.31 | +0.26 | +0.39 | +0.49 | +0.30 | +0.53 |
| r100 | - | landscapers (us-them) ~avg | +0.62 | +0.31 | +0.27 | +0.33 | +0.38 | +0.46 | +0.57 |
| r100 | - | robots (us-them) | +0.77 | +0.49 | +0.46 | +0.50 | +0.49 | +0.47 | +0.68 |
| r100 | - | robots (us-them) ~avg | +0.77 | +0.45 | +0.48 | +0.54 | +0.50 | +0.51 | +0.71 |
| r100 | - | spawned (us-them) | +0.73 | +0.49 | +0.50 | +0.55 | +0.49 | +0.40 | +0.66 |
| r100 | - | spawned (us-them) ~avg | +0.72 | +0.45 | +0.49 | +0.56 | +0.51 | +0.47 | +0.66 |
| r100 | - | worth (us-them) | +0.70 | +0.47 | +0.38 | +0.48 | +0.49 | +0.49 | +0.62 |
| r100 | - | worth (us-them) ~avg | +0.70 | +0.44 | +0.45 | +0.49 | +0.51 | +0.50 | +0.64 |
| r150 | - | digs (us-them) | +0.64 | +0.03 | +0.32 | +0.33 | +0.42 | +0.53 | +0.59 |
| r150 | - | moves (us-them) | +0.43 | +0.20 | +0.43 | +0.37 | +0.25 | +0.16 | +0.30 |
| r150 | - | moves (us-them) ~avg | +0.44 | +0.12 | +0.44 | +0.39 | +0.30 | +0.21 | +0.28 |
| r250 | - | dirtDeps (us-them) | +0.61 | -0.05 | +0.30 | +0.31 | +0.41 | +0.52 | +0.56 |
| r300 | - | cov (us-them) | +0.43 | -0.02 | +0.20 | +0.31 | +0.26 | +0.31 | +0.39 |
| r300 | - | digs (us-them) ~avg | +0.61 | +0.03 | +0.25 | +0.31 | +0.36 | +0.48 | +0.56 |
| r300 | - | dirtDeps (us-them) ~avg | +0.57 | -0.05 | +0.23 | +0.30 | +0.36 | +0.47 | +0.53 |
| r500 | - | pickups (us-them) | +0.43 | -0.10 | +0.02 | +0.17 | +0.29 | +0.30 | +0.27 |
| r500 | - | vaporators (us-them) | +0.55 | -0.03 | +0.05 | +0.11 | +0.14 | +0.42 | +0.48 |
| r600 | - | pickups (us-them) ~avg | +0.41 | -0.10 | +0.03 | +0.15 | +0.23 | +0.30 | +0.29 |
| r650 | - | vaporators (us-them) ~avg | +0.56 | -0.09 | +0.01 | +0.05 | +0.10 | +0.28 | +0.47 |
| r700 | - | cov (us-them) ~avg | +0.41 | -0.11 | +0.07 | +0.23 | +0.22 | +0.27 | +0.38 |
| r850 | - | drones (us-them) | +0.49 | +0.16 | -0.02 | +0.12 | +0.02 | +0.07 | +0.37 |
| r850 | - | netguns (us-them) | +0.35 | . | +0.06 | +0.05 | -0.08 | +0.09 | +0.30 |
| r1050 | - | drones (us-them) ~avg | +0.40 | +0.16 | +0.03 | +0.10 | +0.04 | -0.03 | +0.18 |
| - | - | aba (us-them) [inverted] | +0.21 | -0.01 | -0.09 | +0.01 | +0.07 | +0.17 | +0.18 |
| - | - | aba (us-them) [inverted] ~avg | +0.22 | +0.00 | -0.06 | -0.04 | +0.00 | +0.13 | +0.21 |
| - | - | died (us-them) [inverted] | +0.25 | . | -0.25 | -0.08 | +0.14 | +0.24 | +0.25 |
| - | - | died (us-them) [inverted] ~avg | +0.29 | . | -0.24 | -0.14 | +0.01 | +0.15 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.35 | +0.15 | +0.02 | -0.17 | -0.16 | -0.13 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.35 | +0.15 | +0.12 | -0.16 | -0.20 | -0.18 | -0.10 |
| - | - | netguns (us-them) ~avg | +0.26 | . | +0.08 | +0.10 | +0.01 | -0.03 | +0.14 |
| - | - | soup (us-them) | -0.22 | -0.08 | -0.13 | -0.12 | +0.11 | -0.10 | -0.12 |
| - | - | soup (us-them) ~avg | +0.19 | -0.01 | -0.01 | -0.07 | +0.16 | +0.13 | -0.12 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
