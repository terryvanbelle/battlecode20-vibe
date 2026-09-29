# Which metric starts predicting the result first

46 games, 21 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.33 | +0.25 | +0.19 | +0.02 | +0.03 | +0.01 | +0.05 |
| r50 | - | miners (us-them) ~avg | +0.33 | +0.33 | +0.30 | +0.23 | +0.16 | +0.12 | +0.08 |
| r50 | - | mines (us-them) | +0.56 | +0.56 | +0.46 | +0.44 | +0.40 | +0.31 | +0.26 |
| r50 | - | mines (us-them) ~avg | +0.53 | +0.52 | +0.52 | +0.50 | +0.47 | +0.39 | +0.34 |
| r50 | - | robots (us-them) | +0.56 | +0.48 | +0.56 | +0.46 | +0.43 | +0.39 | +0.40 |
| r50 | - | robots (us-them) ~avg | +0.59 | +0.48 | +0.58 | +0.55 | +0.51 | +0.47 | +0.44 |
| r50 | - | spawned (us-them) | +0.52 | +0.48 | +0.52 | +0.39 | +0.41 | +0.36 | +0.42 |
| r50 | - | spawned (us-them) ~avg | +0.56 | +0.48 | +0.56 | +0.49 | +0.47 | +0.43 | +0.43 |
| r50 | - | units (us-them) | +0.52 | +0.52 | +0.46 | +0.40 | +0.41 | +0.39 | +0.45 |
| r50 | - | units (us-them) ~avg | +0.50 | +0.47 | +0.49 | +0.48 | +0.47 | +0.47 | +0.48 |
| r50 | - | worth (us-them) | +0.59 | +0.57 | +0.59 | +0.51 | +0.45 | +0.42 | +0.33 |
| r50 | - | worth (us-them) ~avg | +0.59 | +0.52 | +0.59 | +0.57 | +0.52 | +0.46 | +0.40 |
| r100 | - | drones (us-them) | +0.44 | +0.39 | +0.40 | +0.17 | +0.20 | +0.18 | +0.38 |
| r100 | - | drones (us-them) ~avg | +0.46 | +0.31 | +0.46 | +0.38 | +0.30 | +0.25 | +0.35 |
| r150 | - | digs (us-them) | +0.72 | +0.29 | +0.33 | +0.36 | +0.42 | +0.46 | +0.56 |
| r150 | - | digs (us-them) ~avg | +0.61 | +0.27 | +0.34 | +0.35 | +0.39 | +0.43 | +0.51 |
| r150 | - | dirtDeps (us-them) | +0.71 | +0.25 | +0.28 | +0.30 | +0.39 | +0.44 | +0.54 |
| r150 | - | dirtDeps (us-them) ~avg | +0.59 | +0.23 | +0.30 | +0.29 | +0.34 | +0.39 | +0.48 |
| r150 | - | hqBuried (us-them) [inverted] | +0.41 | +0.30 | +0.26 | +0.14 | +0.14 | . | . |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.40 | +0.29 | +0.25 | +0.14 | +0.14 | +0.16 | +0.16 |
| r200 | - | landscapers (us-them) | +0.62 | +0.34 | +0.33 | +0.46 | +0.49 | +0.56 | +0.62 |
| r250 | - | died (us-them) [inverted] ~avg | +0.33 | +0.13 | +0.28 | +0.28 | +0.24 | +0.27 | +0.24 |
| r250 | - | landscapers (us-them) ~avg | +0.62 | +0.30 | +0.26 | +0.39 | +0.47 | +0.53 | +0.59 |
| r250 | - | moves (us-them) | +0.38 | -0.08 | +0.28 | +0.36 | +0.35 | +0.36 | +0.38 |
| r250 | - | pickups (us-them) | +0.35 | +0.09 | +0.27 | +0.35 | +0.21 | +0.19 | +0.09 |
| r300 | - | moves (us-them) ~avg | +0.39 | -0.13 | +0.17 | +0.33 | +0.36 | +0.37 | +0.39 |
| r300 | - | pickups (us-them) ~avg | +0.33 | +0.03 | +0.25 | +0.31 | +0.30 | +0.31 | +0.19 |
| r450 | - | netguns (us-them) | +0.55 | . | +0.20 | +0.27 | +0.29 | +0.49 | +0.42 |
| r500 | - | netguns (us-them) ~avg | +0.46 | . | +0.20 | +0.27 | +0.28 | +0.41 | +0.46 |
| r500 | - | vaporators (us-them) | +0.33 | -0.05 | +0.05 | +0.28 | +0.25 | +0.28 | +0.21 |
| r600 | - | vaporators (us-them) ~avg | +0.31 | -0.05 | +0.04 | +0.20 | +0.24 | +0.30 | +0.27 |
| - | - | aba (us-them) [inverted] | -0.30 | +0.19 | -0.02 | +0.11 | +0.07 | -0.19 | -0.26 |
| - | - | aba (us-them) [inverted] ~avg | -0.28 | +0.13 | -0.01 | +0.07 | +0.08 | -0.09 | -0.22 |
| - | - | cov (us-them) | +0.24 | -0.04 | +0.24 | +0.07 | +0.12 | +0.14 | +0.13 |
| - | - | cov (us-them) ~avg | +0.16 | -0.08 | +0.07 | +0.04 | +0.08 | +0.12 | +0.13 |
| - | - | died (us-them) [inverted] | +0.36 | +0.13 | +0.29 | +0.21 | +0.17 | +0.28 | +0.06 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | soup (us-them) | -0.29 | -0.04 | -0.09 | -0.05 | +0.19 | +0.27 | -0.29 |
| - | - | soup (us-them) ~avg | -0.22 | -0.04 | -0.18 | -0.22 | -0.03 | +0.06 | -0.16 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
