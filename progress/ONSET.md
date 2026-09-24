# Which metric starts predicting the result first

29 games, 16 wins. Noise floor about 0.37; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | miners (us-them) ~avg | +0.40 | +0.33 | +0.37 | +0.35 | +0.21 | +0.13 | -0.00 |
| r100 | - | mines (us-them) | +0.68 | +0.39 | +0.54 | +0.61 | +0.68 | +0.63 | +0.56 |
| r100 | - | mines (us-them) ~avg | +0.67 | +0.39 | +0.50 | +0.56 | +0.61 | +0.67 | +0.63 |
| r100 | - | robots (us-them) | +0.64 | +0.39 | +0.53 | +0.53 | +0.62 | +0.51 | +0.52 |
| r100 | - | robots (us-them) ~avg | +0.66 | +0.39 | +0.53 | +0.58 | +0.62 | +0.65 | +0.61 |
| r100 | - | spawned (us-them) | +0.74 | +0.39 | +0.54 | +0.62 | +0.74 | +0.61 | +0.55 |
| r100 | - | spawned (us-them) ~avg | +0.71 | +0.39 | +0.53 | +0.60 | +0.66 | +0.71 | +0.64 |
| r100 | - | worth (us-them) | +0.65 | +0.46 | +0.55 | +0.58 | +0.65 | +0.57 | +0.48 |
| r100 | - | worth (us-them) ~avg | +0.63 | +0.44 | +0.52 | +0.57 | +0.61 | +0.63 | +0.59 |
| r150 | - | drones (us-them) | +0.47 | +0.27 | +0.32 | +0.29 | +0.33 | +0.38 | +0.34 |
| r150 | - | drones (us-them) ~avg | +0.48 | +0.27 | +0.46 | +0.38 | +0.35 | +0.38 | +0.34 |
| r150 | - | units (us-them) | +0.56 | +0.26 | +0.48 | +0.45 | +0.53 | +0.43 | +0.51 |
| r150 | - | units (us-them) ~avg | +0.60 | +0.27 | +0.45 | +0.51 | +0.54 | +0.60 | +0.55 |
| r200 | - | miners (us-them) | +0.38 | +0.30 | +0.38 | +0.06 | -0.07 | -0.01 | -0.19 |
| r250 | - | landscapers (us-them) | +0.72 | -0.07 | +0.27 | +0.34 | +0.38 | +0.60 | +0.69 |
| r250 | - | moves (us-them) | +0.45 | -0.06 | +0.18 | +0.44 | +0.42 | +0.41 | +0.34 |
| r250 | - | pickups (us-them) | +0.42 | -0.09 | +0.26 | +0.24 | +0.27 | +0.41 | +0.29 |
| r300 | - | cov (us-them) | +0.46 | -0.29 | +0.07 | +0.34 | +0.34 | +0.46 | +0.34 |
| r300 | - | landscapers (us-them) ~avg | +0.78 | -0.07 | +0.23 | +0.30 | +0.35 | +0.51 | +0.72 |
| r350 | - | vaporators (us-them) | +0.37 | +0.03 | +0.26 | +0.23 | +0.33 | +0.36 | +0.25 |
| r400 | - | digs (us-them) | +0.62 | -0.06 | +0.18 | +0.25 | +0.31 | +0.40 | +0.54 |
| r400 | - | dirtDeps (us-them) | +0.60 | -0.20 | +0.14 | +0.25 | +0.31 | +0.40 | +0.53 |
| r400 | - | moves (us-them) ~avg | +0.35 | -0.06 | +0.04 | +0.24 | +0.31 | +0.34 | +0.31 |
| r450 | - | vaporators (us-them) ~avg | +0.35 | +0.03 | +0.17 | +0.21 | +0.28 | +0.35 | +0.29 |
| r500 | - | cov (us-them) ~avg | +0.46 | -0.26 | -0.13 | +0.11 | +0.21 | +0.45 | +0.44 |
| r550 | - | pickups (us-them) ~avg | +0.38 | -0.09 | +0.22 | +0.27 | +0.27 | +0.33 | +0.37 |
| r600 | - | digs (us-them) ~avg | +0.54 | -0.06 | +0.14 | +0.21 | +0.27 | +0.31 | +0.43 |
| r600 | - | dirtDeps (us-them) ~avg | +0.52 | -0.20 | +0.10 | +0.20 | +0.26 | +0.31 | +0.42 |
| r950 | - | netguns (us-them) | +0.41 | -0.17 | -0.03 | +0.06 | +0.15 | +0.14 | +0.28 |
| - | - | aba (us-them) [inverted] | +0.26 | +0.20 | +0.20 | +0.08 | +0.04 | +0.11 | -0.08 |
| - | - | aba (us-them) [inverted] ~avg | +0.21 | +0.11 | +0.20 | +0.14 | +0.10 | +0.12 | +0.04 |
| - | r300 | died (us-them) [inverted] | -0.47 | . | -0.20 | -0.39 | -0.47 | -0.09 | -0.23 |
| - | r400 | died (us-them) [inverted] ~avg | -0.32 | . | -0.20 | -0.27 | -0.32 | -0.18 | -0.14 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) ~avg | +0.22 | -0.17 | -0.11 | -0.02 | +0.04 | +0.10 | +0.11 |
| - | - | soup (us-them) | +0.37 | +0.19 | +0.18 | +0.08 | +0.03 | +0.08 | -0.14 |
| - | - | soup (us-them) ~avg | +0.26 | +0.21 | +0.18 | +0.05 | -0.00 | -0.03 | -0.00 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
