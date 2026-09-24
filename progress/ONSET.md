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
| r50 | r700 | aba (us-them) [inverted] | -0.56 | +0.49 | +0.27 | +0.21 | +0.29 | -0.12 | -0.50 |
| r50 | r950 | aba (us-them) [inverted] ~avg | -0.49 | +0.46 | +0.35 | +0.27 | +0.29 | +0.17 | -0.23 |
| r100 | - | vaporators (us-them) | +0.63 | +0.63 | +0.55 | +0.48 | +0.48 | +0.47 | +0.49 |
| r100 | - | vaporators (us-them) ~avg | +0.63 | +0.63 | +0.61 | +0.55 | +0.51 | +0.49 | +0.50 |
| r150 | - | soup (us-them) ~avg | +0.57 | -0.12 | +0.40 | +0.52 | +0.49 | +0.42 | +0.33 |
| r300 | r50 | cov (us-them) | +0.78 | -0.27 | +0.17 | +0.33 | +0.47 | +0.55 | +0.70 |
| r300 | - | drones (us-them) | +0.72 | +0.20 | +0.10 | +0.42 | +0.55 | +0.57 | +0.67 |
| r300 | - | miners (us-them) | +0.63 | -0.11 | +0.03 | +0.34 | +0.41 | +0.62 | +0.46 |
| r300 | - | mines (us-them) | +0.67 | +0.00 | +0.15 | +0.32 | +0.43 | +0.57 | +0.57 |
| r300 | - | soup (us-them) | +0.63 | +0.08 | +0.10 | +0.63 | +0.17 | +0.34 | +0.08 |
| r300 | - | worth (us-them) | +0.72 | +0.18 | +0.11 | +0.37 | +0.56 | +0.69 | +0.65 |
| r350 | - | died (us-them) [inverted] | +0.62 | . | -0.18 | +0.23 | +0.43 | +0.58 | +0.26 |
| r350 | - | drones (us-them) ~avg | +0.67 | +0.24 | +0.09 | +0.22 | +0.41 | +0.56 | +0.61 |
| r350 | - | robots (us-them) | +0.75 | -0.29 | -0.16 | +0.25 | +0.52 | +0.67 | +0.70 |
| r350 | - | spawned (us-them) | +0.68 | -0.29 | -0.15 | +0.23 | +0.46 | +0.59 | +0.67 |
| r350 | r100 | units (us-them) | +0.76 | -0.43 | -0.37 | +0.09 | +0.41 | +0.57 | +0.75 |
| r350 | - | worth (us-them) ~avg | +0.66 | +0.24 | +0.15 | +0.26 | +0.41 | +0.59 | +0.65 |
| r400 | r50 | cov (us-them) ~avg | +0.66 | -0.38 | -0.04 | +0.18 | +0.32 | +0.47 | +0.59 |
| r400 | - | mines (us-them) ~avg | +0.61 | +0.03 | +0.10 | +0.20 | +0.32 | +0.46 | +0.54 |
| r400 | - | pickups (us-them) | +0.65 | . | -0.32 | +0.16 | +0.35 | +0.58 | +0.46 |
| r450 | - | died (us-them) [inverted] ~avg | +0.59 | . | -0.22 | +0.03 | +0.28 | +0.55 | +0.47 |
| r450 | - | miners (us-them) ~avg | +0.59 | +0.03 | -0.01 | +0.18 | +0.30 | +0.47 | +0.56 |
| r450 | - | robots (us-them) ~avg | +0.69 | +0.00 | -0.18 | +0.05 | +0.28 | +0.54 | +0.67 |
| r450 | - | spawned (us-them) ~avg | +0.63 | +0.00 | -0.16 | +0.04 | +0.24 | +0.46 | +0.61 |
| r500 | - | pickups (us-them) ~avg | +0.63 | . | -0.34 | -0.05 | +0.19 | +0.47 | +0.58 |
| r500 | r150 | units (us-them) ~avg | +0.70 | -0.16 | -0.37 | -0.17 | +0.10 | +0.43 | +0.64 |
| r600 | r100 | landscapers (us-them) | +0.66 | -0.42 | -0.59 | -0.38 | -0.04 | +0.33 | +0.66 |
| r700 | r100 | moves (us-them) | +0.47 | -0.40 | -0.25 | -0.15 | -0.02 | +0.25 | +0.41 |
| r750 | r150 | digs (us-them) | +0.73 | -0.04 | -0.45 | -0.35 | -0.19 | +0.04 | +0.49 |
| r850 | r150 | dirtDeps (us-them) | +0.67 | +0.03 | -0.49 | -0.40 | -0.28 | -0.03 | +0.39 |
| r850 | r100 | landscapers (us-them) ~avg | +0.56 | -0.42 | -0.49 | -0.46 | -0.36 | -0.08 | +0.41 |
| r900 | r100 | moves (us-them) ~avg | -0.43 | -0.43 | -0.32 | -0.24 | -0.14 | +0.09 | +0.30 |
| r950 | r150 | digs (us-them) ~avg | +0.56 | -0.04 | -0.46 | -0.41 | -0.30 | -0.14 | +0.19 |
| r1000 | r150 | dirtDeps (us-them) ~avg | +0.48 | +0.03 | -0.47 | -0.45 | -0.37 | -0.21 | +0.08 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r950 | netguns (us-them) | -0.36 | . | . | . | -0.28 | -0.28 | -0.28 |
| - | r950 | netguns (us-them) ~avg | -0.36 | . | . | . | -0.28 | -0.28 | -0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
