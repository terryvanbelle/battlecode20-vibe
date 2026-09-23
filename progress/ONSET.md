# Which metric starts predicting the result first

8 games, 6 wins. Noise floor about 0.71; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.92 | +0.65 | +0.75 | +0.76 | +0.76 | +0.77 | +0.45 |
| r100 | - | drones (us-them) ~avg | +0.93 | +0.65 | +0.80 | +0.89 | +0.83 | +0.86 | +0.71 |
| r100 | - | pickups (us-them) | +0.77 | +0.65 | +0.52 | +0.74 | +0.70 | +0.74 | +0.71 |
| r100 | - | pickups (us-them) ~avg | +0.73 | +0.65 | +0.62 | +0.70 | +0.70 | +0.72 | +0.73 |
| r100 | r1150 | soup (us-them) | +0.90 | +0.72 | -0.15 | +0.29 | +0.71 | +0.51 | +0.07 |
| r100 | - | soup (us-them) ~avg | +0.87 | +0.54 | +0.87 | +0.70 | +0.62 | +0.53 | +0.43 |
| r100 | - | worth (us-them) | +0.74 | +0.31 | +0.53 | +0.68 | +0.74 | +0.67 | +0.29 |
| r150 | - | aba (us-them) [inverted] | +0.62 | +0.29 | +0.41 | +0.41 | +0.54 | +0.54 | +0.29 |
| r150 | - | aba (us-them) [inverted] ~avg | +0.55 | +0.27 | +0.39 | +0.43 | +0.46 | +0.55 | +0.48 |
| r150 | r1150 | died (us-them) [inverted] | -0.37 | . | +0.33 | +0.29 | +0.26 | +0.27 | -0.15 |
| r150 | - | died (us-them) [inverted] ~avg | +0.33 | . | +0.33 | +0.31 | +0.30 | +0.26 | -0.03 |
| r150 | r950 | vaporators (us-them) | +0.65 | . | +0.65 | +0.54 | +0.47 | +0.20 | -0.17 |
| r150 | - | vaporators (us-them) ~avg | +0.65 | . | +0.65 | +0.61 | +0.60 | +0.44 | +0.21 |
| r150 | - | worth (us-them) ~avg | +0.73 | +0.26 | +0.50 | +0.63 | +0.71 | +0.72 | +0.59 |
| r200 | - | miners (us-them) | +0.48 | +0.10 | +0.48 | +0.32 | +0.41 | +0.32 | +0.09 |
| r250 | - | netguns (us-them) | +0.65 | . | . | +0.65 | +0.60 | +0.46 | +0.26 |
| r250 | - | netguns (us-them) ~avg | +0.65 | . | . | +0.65 | +0.64 | +0.60 | +0.51 |
| r250 | - | robots (us-them) | +0.85 | -0.03 | +0.27 | +0.42 | +0.55 | +0.78 | +0.50 |
| r300 | - | mines (us-them) | +0.61 | -0.01 | +0.18 | +0.43 | +0.45 | +0.60 | +0.47 |
| r300 | - | spawned (us-them) | +0.77 | -0.03 | +0.21 | +0.33 | +0.48 | +0.73 | +0.67 |
| r350 | - | mines (us-them) ~avg | +0.52 | -0.02 | +0.06 | +0.23 | +0.34 | +0.48 | +0.52 |
| r350 | - | robots (us-them) ~avg | +0.71 | +0.04 | +0.13 | +0.28 | +0.39 | +0.65 | +0.68 |
| r400 | - | spawned (us-them) ~avg | +0.69 | +0.04 | +0.10 | +0.21 | +0.31 | +0.56 | +0.66 |
| r450 | - | miners (us-them) ~avg | +0.37 | +0.09 | +0.19 | +0.26 | +0.30 | +0.35 | +0.35 |
| r450 | - | units (us-them) | +0.82 | -0.22 | +0.02 | +0.02 | +0.23 | +0.79 | +0.57 |
| r500 | - | cov (us-them) | +0.39 | -0.08 | +0.15 | +0.02 | +0.26 | +0.35 | +0.32 |
| r500 | r100 | landscapers (us-them) | +0.78 | -0.43 | -0.23 | -0.39 | -0.08 | +0.73 | +0.67 |
| r550 | - | digs (us-them) | +0.89 | -0.05 | -0.19 | -0.16 | -0.04 | +0.48 | +0.85 |
| r550 | - | dirtDeps (us-them) | +0.89 | -0.23 | -0.17 | -0.16 | -0.05 | +0.48 | +0.85 |
| r550 | - | units (us-them) ~avg | +0.64 | -0.13 | -0.08 | -0.02 | +0.06 | +0.42 | +0.61 |
| r700 | - | cov (us-them) ~avg | +0.33 | -0.12 | +0.09 | +0.08 | +0.14 | +0.27 | +0.33 |
| r700 | - | digs (us-them) ~avg | +0.83 | -0.05 | -0.23 | -0.17 | -0.13 | +0.14 | +0.61 |
| r700 | - | dirtDeps (us-them) ~avg | +0.82 | -0.23 | -0.23 | -0.17 | -0.13 | +0.13 | +0.61 |
| r800 | r100 | landscapers (us-them) ~avg | +0.65 | -0.43 | -0.35 | -0.40 | -0.30 | +0.05 | +0.42 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r100 | moves (us-them) | -0.46 | -0.38 | -0.31 | -0.46 | -0.31 | -0.12 | -0.11 |
| - | r100 | moves (us-them) ~avg | -0.44 | -0.35 | -0.32 | -0.43 | -0.41 | -0.26 | -0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
