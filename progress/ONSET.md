# Which metric starts predicting the result first

13 games, 6 wins. Noise floor about 0.55; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r700 | aba (us-them) [inverted] | +0.62 | +0.49 | +0.62 | +0.46 | +0.37 | -0.18 | -0.46 |
| r50 | r950 | aba (us-them) [inverted] ~avg | +0.56 | +0.46 | +0.56 | +0.52 | +0.47 | +0.27 | -0.28 |
| r50 | - | mines (us-them) | +0.82 | +0.30 | +0.50 | +0.66 | +0.81 | +0.73 | +0.69 |
| r50 | - | mines (us-them) ~avg | +0.81 | +0.32 | +0.40 | +0.57 | +0.71 | +0.80 | +0.76 |
| r50 | - | robots (us-them) | +0.84 | +0.29 | +0.40 | +0.75 | +0.84 | +0.77 | +0.68 |
| r50 | - | robots (us-them) ~avg | +0.84 | +0.51 | +0.38 | +0.64 | +0.80 | +0.83 | +0.76 |
| r50 | - | spawned (us-them) | +0.82 | +0.29 | +0.36 | +0.62 | +0.81 | +0.74 | +0.67 |
| r50 | - | spawned (us-them) ~avg | +0.82 | +0.51 | +0.35 | +0.54 | +0.73 | +0.81 | +0.75 |
| r50 | - | units (us-them) ~avg | +0.83 | +0.26 | +0.07 | +0.47 | +0.75 | +0.81 | +0.77 |
| r100 | - | drones (us-them) | +0.67 | +0.56 | +0.67 | +0.50 | +0.61 | +0.54 | +0.54 |
| r100 | - | drones (us-them) ~avg | +0.70 | +0.53 | +0.70 | +0.64 | +0.63 | +0.61 | +0.56 |
| r100 | - | vaporators (us-them) ~avg | +0.61 | +0.51 | +0.36 | +0.38 | +0.43 | +0.52 | +0.59 |
| r150 | - | cov (us-them) | +0.61 | +0.14 | +0.39 | +0.20 | +0.34 | +0.57 | +0.51 |
| r150 | - | pickups (us-them) | +0.75 | . | +0.65 | +0.62 | +0.74 | +0.66 | +0.43 |
| r150 | - | pickups (us-them) ~avg | +0.76 | . | +0.57 | +0.67 | +0.71 | +0.76 | +0.53 |
| r150 | - | worth (us-them) | +0.77 | +0.27 | +0.52 | +0.66 | +0.75 | +0.74 | +0.69 |
| r150 | - | worth (us-them) ~avg | +0.76 | +0.26 | +0.42 | +0.58 | +0.69 | +0.76 | +0.74 |
| r200 | - | cov (us-them) ~avg | +0.52 | +0.04 | +0.32 | +0.30 | +0.33 | +0.44 | +0.50 |
| r200 | - | died (us-them) [inverted] | +0.73 | . | +0.45 | +0.60 | +0.67 | +0.68 | +0.22 |
| r200 | - | died (us-them) [inverted] ~avg | +0.66 | . | +0.45 | +0.57 | +0.61 | +0.64 | +0.46 |
| r200 | - | vaporators (us-them) | +0.65 | +0.51 | +0.34 | +0.36 | +0.44 | +0.56 | +0.61 |
| r250 | - | units (us-them) | +0.83 | +0.09 | +0.18 | +0.72 | +0.83 | +0.75 | +0.68 |
| r300 | r150 | miners (us-them) | +0.64 | -0.13 | -0.24 | +0.55 | +0.58 | +0.64 | +0.49 |
| r350 | - | landscapers (us-them) | +0.84 | -0.03 | -0.03 | +0.20 | +0.53 | +0.80 | +0.78 |
| r350 | - | miners (us-them) ~avg | +0.67 | +0.05 | -0.15 | +0.23 | +0.47 | +0.58 | +0.66 |
| r350 | - | moves (us-them) | +0.87 | -0.05 | -0.19 | +0.01 | +0.69 | +0.87 | +0.82 |
| r350 | - | netguns (us-them) | +0.45 | . | . | . | +0.45 | +0.43 | +0.42 |
| r350 | - | netguns (us-them) ~avg | +0.44 | . | . | . | +0.42 | +0.43 | +0.43 |
| r400 | r50 | soup (us-them) | +0.80 | -0.58 | +0.06 | +0.80 | +0.60 | +0.42 | +0.43 |
| r400 | r50 | soup (us-them) ~avg | -0.79 | -0.78 | -0.43 | +0.21 | +0.39 | +0.46 | +0.45 |
| r450 | - | landscapers (us-them) ~avg | +0.78 | -0.03 | -0.19 | -0.01 | +0.23 | +0.55 | +0.76 |
| r450 | - | moves (us-them) ~avg | +0.88 | -0.08 | -0.11 | -0.07 | +0.25 | +0.84 | +0.86 |
| r600 | - | digs (us-them) | +0.65 | -0.01 | -0.10 | -0.03 | +0.07 | +0.32 | +0.63 |
| r650 | - | dirtDeps (us-them) | +0.61 | -0.02 | -0.20 | -0.11 | +0.01 | +0.23 | +0.56 |
| r800 | - | digs (us-them) ~avg | +0.65 | -0.01 | -0.13 | -0.08 | -0.01 | +0.09 | +0.45 |
| r900 | - | dirtDeps (us-them) ~avg | +0.57 | -0.02 | -0.21 | -0.16 | -0.08 | +0.02 | +0.34 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.27 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.27 | . | . | . | . | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
