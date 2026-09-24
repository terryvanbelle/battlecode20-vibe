# Which metric starts predicting the result first

17 games, 5 wins. Noise floor about 0.49; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.59 | +0.58 | +0.52 | +0.53 | +0.42 | +0.41 | +0.23 |
| r50 | - | miners (us-them) ~avg | +0.65 | +0.64 | +0.62 | +0.59 | +0.57 | +0.52 | +0.41 |
| r50 | - | mines (us-them) | +0.72 | +0.60 | +0.64 | +0.51 | +0.47 | +0.23 | +0.20 |
| r50 | - | mines (us-them) ~avg | +0.72 | +0.64 | +0.65 | +0.61 | +0.57 | +0.45 | +0.30 |
| r50 | - | robots (us-them) | +0.71 | +0.57 | +0.67 | +0.56 | +0.49 | +0.29 | +0.28 |
| r50 | - | robots (us-them) ~avg | +0.70 | +0.61 | +0.70 | +0.66 | +0.59 | +0.49 | +0.35 |
| r50 | - | spawned (us-them) | +0.71 | +0.56 | +0.68 | +0.57 | +0.52 | +0.23 | +0.25 |
| r50 | - | spawned (us-them) ~avg | +0.70 | +0.60 | +0.70 | +0.68 | +0.62 | +0.48 | +0.30 |
| r50 | - | units (us-them) | +0.74 | +0.59 | +0.74 | +0.60 | +0.55 | +0.29 | +0.20 |
| r50 | - | units (us-them) ~avg | +0.74 | +0.62 | +0.74 | +0.72 | +0.67 | +0.57 | +0.33 |
| r50 | - | worth (us-them) | +0.63 | +0.54 | +0.60 | +0.42 | +0.40 | +0.31 | +0.35 |
| r50 | - | worth (us-them) ~avg | +0.63 | +0.58 | +0.63 | +0.53 | +0.46 | +0.41 | +0.36 |
| r100 | - | drones (us-them) | +0.48 | +0.48 | +0.09 | -0.07 | +0.02 | -0.06 | +0.11 |
| r100 | - | drones (us-them) ~avg | +0.48 | +0.48 | +0.26 | +0.09 | +0.10 | +0.03 | +0.03 |
| r100 | - | moves (us-them) | +0.76 | +0.52 | +0.74 | +0.75 | +0.72 | +0.65 | +0.44 |
| r100 | - | moves (us-them) ~avg | +0.78 | +0.43 | +0.72 | +0.76 | +0.77 | +0.71 | +0.58 |
| r150 | - | cov (us-them) | +0.52 | +0.20 | +0.42 | +0.47 | +0.13 | -0.04 | -0.21 |
| r150 | - | cov (us-them) ~avg | +0.47 | +0.14 | +0.40 | +0.47 | +0.43 | +0.28 | +0.02 |
| r150 | - | died (us-them) [inverted] | +0.64 | +0.16 | +0.30 | +0.31 | +0.29 | +0.49 | +0.54 |
| r150 | - | died (us-them) [inverted] ~avg | +0.65 | +0.16 | +0.32 | +0.31 | +0.30 | +0.44 | +0.55 |
| r150 | - | landscapers (us-them) | +0.64 | -0.11 | +0.57 | +0.57 | +0.58 | +0.33 | +0.24 |
| r200 | - | landscapers (us-them) ~avg | +0.63 | -0.11 | +0.39 | +0.53 | +0.62 | +0.60 | +0.41 |
| r200 | - | pickups (us-them) | +0.43 | +0.16 | +0.43 | +0.36 | +0.26 | +0.16 | -0.07 |
| r200 | - | pickups (us-them) ~avg | +0.42 | +0.16 | +0.37 | +0.38 | +0.38 | +0.25 | +0.07 |
| r250 | - | digs (us-them) | +0.44 | -0.09 | +0.28 | +0.43 | +0.42 | +0.42 | +0.28 |
| r250 | - | digs (us-them) ~avg | +0.42 | -0.09 | +0.22 | +0.38 | +0.39 | +0.42 | +0.38 |
| r250 | - | dirtDeps (us-them) | +0.43 | -0.21 | +0.27 | +0.41 | +0.42 | +0.42 | +0.28 |
| r300 | - | dirtDeps (us-them) ~avg | +0.41 | -0.21 | +0.20 | +0.35 | +0.37 | +0.41 | +0.38 |
| r400 | - | netguns (us-them) | +0.37 | . | . | . | +0.35 | +0.28 | +0.34 |
| r400 | - | netguns (us-them) ~avg | +0.39 | . | . | . | +0.33 | +0.36 | +0.35 |
| r500 | - | vaporators (us-them) | +0.49 | +0.16 | +0.19 | +0.22 | +0.25 | +0.39 | +0.40 |
| r600 | - | vaporators (us-them) ~avg | +0.46 | +0.16 | +0.17 | +0.20 | +0.23 | +0.30 | +0.39 |
| - | r200 | aba (us-them) [inverted] | -0.44 | -0.16 | -0.44 | -0.37 | -0.32 | -0.39 | -0.33 |
| - | r200 | aba (us-them) [inverted] ~avg | -0.44 | -0.05 | -0.37 | -0.41 | -0.37 | -0.36 | -0.35 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | +0.17 | +0.17 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.17 | +0.17 | . | . | . |
| - | - | soup (us-them) | -0.45 | +0.09 | +0.16 | -0.10 | -0.17 | -0.29 | +0.18 |
| - | - | soup (us-them) ~avg | +0.24 | +0.08 | +0.24 | -0.01 | -0.05 | -0.13 | +0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
