# Which metric starts predicting the result first

14 games, 7 wins. Noise floor about 0.53; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.53 | +0.37 | +0.18 | +0.53 | +0.18 | +0.15 | +0.48 |
| r50 | - | miners (us-them) ~avg | +0.60 | +0.44 | +0.30 | +0.60 | +0.52 | +0.35 | +0.35 |
| r50 | - | robots (us-them) | +0.67 | +0.53 | +0.57 | +0.67 | +0.61 | +0.53 | +0.67 |
| r50 | - | robots (us-them) ~avg | +0.71 | +0.54 | +0.56 | +0.71 | +0.68 | +0.62 | +0.65 |
| r50 | - | spawned (us-them) | +0.63 | +0.50 | +0.51 | +0.63 | +0.56 | +0.42 | +0.49 |
| r50 | - | spawned (us-them) ~avg | +0.69 | +0.52 | +0.52 | +0.69 | +0.65 | +0.57 | +0.50 |
| r50 | - | units (us-them) | +0.69 | +0.46 | +0.52 | +0.69 | +0.56 | +0.44 | +0.65 |
| r50 | - | units (us-them) ~avg | +0.68 | +0.51 | +0.53 | +0.68 | +0.66 | +0.57 | +0.61 |
| r100 | - | drones (us-them) | +0.86 | +0.75 | +0.71 | +0.70 | +0.23 | +0.04 | -0.05 |
| r100 | - | drones (us-them) ~avg | +0.90 | +0.75 | +0.70 | +0.90 | +0.67 | +0.25 | +0.01 |
| r100 | - | worth (us-them) | +0.59 | +0.32 | +0.59 | +0.58 | +0.49 | +0.43 | +0.39 |
| r150 | - | died (us-them) [inverted] | +0.61 | +0.28 | +0.40 | +0.61 | +0.44 | +0.37 | +0.31 |
| r150 | - | died (us-them) [inverted] ~avg | +0.54 | +0.28 | +0.41 | +0.50 | +0.53 | +0.47 | +0.38 |
| r150 | - | landscapers (us-them) | +0.72 | +0.05 | +0.48 | +0.52 | +0.46 | +0.49 | +0.72 |
| r150 | r50 | soup (us-them) | -0.63 | -0.37 | +0.35 | +0.31 | -0.18 | +0.05 | -0.24 |
| r150 | - | worth (us-them) ~avg | +0.63 | +0.25 | +0.50 | +0.63 | +0.57 | +0.51 | +0.43 |
| r200 | - | digs (us-them) | +0.70 | -0.10 | +0.38 | +0.29 | +0.33 | +0.45 | +0.68 |
| r200 | - | digs (us-them) ~avg | +0.65 | -0.10 | +0.31 | +0.29 | +0.32 | +0.40 | +0.62 |
| r200 | - | dirtDeps (us-them) | +0.69 | -0.21 | +0.34 | +0.26 | +0.31 | +0.47 | +0.66 |
| r200 | - | landscapers (us-them) ~avg | +0.64 | +0.05 | +0.41 | +0.45 | +0.47 | +0.47 | +0.64 |
| r200 | - | pickups (us-them) | +0.93 | . | +0.36 | +0.72 | +0.82 | +0.93 | +0.66 |
| r200 | - | pickups (us-them) ~avg | +0.89 | . | +0.35 | +0.60 | +0.73 | +0.87 | +0.82 |
| r250 | - | cov (us-them) | +0.69 | -0.20 | +0.23 | +0.65 | +0.68 | +0.62 | +0.34 |
| r250 | r50 | cov (us-them) ~avg | +0.66 | -0.30 | +0.06 | +0.45 | +0.59 | +0.65 | +0.57 |
| r250 | - | moves (us-them) | +0.67 | +0.16 | +0.25 | +0.64 | +0.66 | +0.58 | +0.47 |
| r250 | - | moves (us-them) ~avg | +0.65 | +0.06 | +0.24 | +0.60 | +0.64 | +0.64 | +0.55 |
| r300 | - | mines (us-them) | +0.52 | +0.20 | +0.19 | +0.52 | +0.49 | +0.42 | +0.32 |
| r300 | - | mines (us-them) ~avg | +0.58 | +0.18 | +0.20 | +0.58 | +0.56 | +0.51 | +0.40 |
| r300 | - | netguns (us-them) | +0.60 | . | . | +0.30 | +0.30 | +0.60 | +0.48 |
| r300 | - | netguns (us-them) ~avg | +0.57 | . | . | +0.30 | +0.30 | +0.54 | +0.53 |
| r450 | - | dirtDeps (us-them) ~avg | +0.64 | -0.21 | +0.28 | +0.23 | +0.28 | +0.39 | +0.60 |
| r800 | - | vaporators (us-them) | +0.40 | -0.17 | -0.21 | -0.00 | +0.11 | +0.15 | +0.40 |
| - | - | aba (us-them) [inverted] | +0.26 | +0.12 | -0.13 | +0.19 | +0.22 | +0.22 | +0.10 |
| - | - | aba (us-them) [inverted] ~avg | +0.23 | +0.14 | -0.06 | +0.08 | +0.17 | +0.23 | +0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r350 | hqBuried (us-them) [inverted] | -0.30 | +0.28 | +0.27 | . | -0.30 | -0.30 | . |
| - | r250 | hqBuried (us-them) [inverted] ~avg | -0.42 | +0.28 | +0.27 | -0.42 | -0.36 | -0.33 | -0.35 |
| - | r50 | soup (us-them) ~avg | -0.63 | -0.49 | -0.17 | +0.00 | -0.02 | +0.07 | -0.29 |
| - | r150 | vaporators (us-them) ~avg | -0.31 | -0.17 | -0.27 | -0.04 | +0.02 | +0.12 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
