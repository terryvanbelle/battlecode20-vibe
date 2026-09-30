# Which metric starts predicting the result first

45 games, 24 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.59 | +0.53 | +0.59 | +0.58 | +0.52 | +0.35 | +0.25 |
| r50 | - | miners (us-them) ~avg | +0.65 | +0.54 | +0.57 | +0.65 | +0.64 | +0.54 | +0.40 |
| r50 | - | mines (us-them) | +0.72 | +0.67 | +0.69 | +0.70 | +0.65 | +0.58 | +0.54 |
| r50 | - | mines (us-them) ~avg | +0.71 | +0.65 | +0.68 | +0.71 | +0.70 | +0.64 | +0.58 |
| r50 | - | robots (us-them) | +0.74 | +0.53 | +0.68 | +0.72 | +0.69 | +0.58 | +0.47 |
| r50 | - | robots (us-them) ~avg | +0.73 | +0.54 | +0.62 | +0.72 | +0.72 | +0.66 | +0.56 |
| r50 | - | spawned (us-them) | +0.73 | +0.53 | +0.67 | +0.73 | +0.70 | +0.57 | +0.51 |
| r50 | - | spawned (us-them) ~avg | +0.74 | +0.55 | +0.62 | +0.72 | +0.74 | +0.67 | +0.59 |
| r50 | - | units (us-them) | +0.71 | +0.57 | +0.64 | +0.69 | +0.70 | +0.56 | +0.46 |
| r50 | - | units (us-them) ~avg | +0.72 | +0.56 | +0.59 | +0.69 | +0.71 | +0.67 | +0.57 |
| r50 | - | worth (us-them) | +0.66 | +0.53 | +0.63 | +0.62 | +0.59 | +0.56 | +0.45 |
| r50 | - | worth (us-them) ~avg | +0.66 | +0.54 | +0.63 | +0.66 | +0.64 | +0.59 | +0.51 |
| r100 | - | drones (us-them) | +0.47 | +0.35 | +0.45 | +0.43 | +0.46 | +0.41 | +0.43 |
| r100 | - | drones (us-them) ~avg | +0.52 | +0.35 | +0.50 | +0.50 | +0.49 | +0.47 | +0.46 |
| r150 | - | hqBuried (us-them) [inverted] | +0.37 | +0.27 | +0.32 | . | . | . | . |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.37 | +0.27 | +0.32 | +0.21 | +0.21 | +0.22 | +0.23 |
| r150 | - | moves (us-them) | +0.61 | +0.18 | +0.57 | +0.60 | +0.59 | +0.56 | +0.54 |
| r150 | - | moves (us-them) ~avg | +0.60 | +0.09 | +0.51 | +0.59 | +0.60 | +0.58 | +0.56 |
| r200 | - | cov (us-them) | +0.52 | -0.12 | +0.36 | +0.47 | +0.51 | +0.40 | +0.36 |
| r200 | - | landscapers (us-them) | +0.62 | +0.04 | +0.41 | +0.43 | +0.57 | +0.53 | +0.42 |
| r250 | - | landscapers (us-them) ~avg | +0.57 | +0.05 | +0.22 | +0.37 | +0.48 | +0.57 | +0.49 |
| r300 | - | cov (us-them) ~avg | +0.47 | -0.19 | +0.17 | +0.31 | +0.41 | +0.44 | +0.41 |
| r300 | - | digs (us-them) | +0.62 | +0.04 | -0.02 | +0.32 | +0.47 | +0.54 | +0.53 |
| r350 | - | dirtDeps (us-them) | +0.60 | +0.10 | -0.08 | +0.28 | +0.41 | +0.50 | +0.51 |
| r350 | - | pickups (us-them) | +0.45 | +0.01 | +0.14 | +0.26 | +0.30 | +0.42 | +0.41 |
| r400 | - | digs (us-them) ~avg | +0.53 | +0.02 | -0.08 | +0.22 | +0.35 | +0.44 | +0.46 |
| r400 | - | vaporators (us-them) | +0.41 | +0.08 | +0.15 | +0.26 | +0.30 | +0.40 | +0.37 |
| r450 | - | dirtDeps (us-them) ~avg | +0.51 | +0.09 | -0.13 | +0.18 | +0.30 | +0.40 | +0.43 |
| r500 | - | vaporators (us-them) ~avg | +0.43 | +0.08 | +0.18 | +0.19 | +0.25 | +0.36 | +0.39 |
| r550 | - | netguns (us-them) | +0.39 | . | +0.19 | +0.04 | +0.07 | +0.39 | +0.31 |
| r550 | - | pickups (us-them) ~avg | +0.48 | +0.01 | +0.09 | +0.23 | +0.27 | +0.35 | +0.43 |
| r750 | - | netguns (us-them) ~avg | +0.36 | . | +0.19 | +0.10 | +0.08 | +0.23 | +0.33 |
| - | - | aba (us-them) [inverted] | -0.30 | -0.29 | -0.30 | -0.07 | +0.08 | +0.02 | -0.15 |
| - | - | aba (us-them) [inverted] ~avg | -0.27 | -0.22 | -0.27 | -0.16 | -0.02 | -0.01 | -0.05 |
| - | - | died (us-them) [inverted] | -0.23 | -0.14 | +0.19 | +0.02 | +0.10 | +0.19 | -0.07 |
| - | - | died (us-them) [inverted] ~avg | -0.14 | -0.14 | +0.13 | -0.04 | +0.01 | +0.09 | -0.00 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r850 | soup (us-them) | -0.35 | -0.08 | -0.29 | -0.27 | -0.19 | -0.05 | -0.30 |
| - | - | soup (us-them) ~avg | -0.25 | -0.08 | -0.13 | -0.19 | -0.19 | -0.16 | -0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
