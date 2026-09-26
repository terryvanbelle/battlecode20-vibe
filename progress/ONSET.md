# Which metric starts predicting the result first

37 games, 21 wins. Noise floor about 0.33; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.46 | +0.26 | +0.12 | +0.26 | +0.28 | +0.34 | +0.41 |
| r50 | - | miners (us-them) ~avg | +0.42 | +0.32 | +0.29 | +0.32 | +0.33 | +0.29 | +0.37 |
| r50 | - | robots (us-them) | +0.66 | +0.26 | +0.29 | +0.55 | +0.63 | +0.64 | +0.62 |
| r50 | - | robots (us-them) ~avg | +0.68 | +0.33 | +0.29 | +0.44 | +0.56 | +0.64 | +0.67 |
| r50 | - | spawned (us-them) | +0.74 | +0.26 | +0.32 | +0.56 | +0.69 | +0.74 | +0.70 |
| r50 | - | spawned (us-them) ~avg | +0.76 | +0.33 | +0.30 | +0.43 | +0.57 | +0.72 | +0.76 |
| r50 | - | units (us-them) | +0.64 | +0.34 | +0.30 | +0.47 | +0.52 | +0.57 | +0.58 |
| r50 | - | units (us-them) ~avg | +0.64 | +0.38 | +0.33 | +0.43 | +0.50 | +0.57 | +0.64 |
| r100 | - | hqBuried (us-them) [inverted] | +0.30 | +0.30 | +0.18 | -0.22 | -0.18 | -0.13 | -0.17 |
| r100 | - | mines (us-them) | +0.68 | +0.34 | +0.47 | +0.61 | +0.68 | +0.63 | +0.63 |
| r100 | - | mines (us-them) ~avg | +0.69 | +0.34 | +0.39 | +0.54 | +0.63 | +0.68 | +0.68 |
| r100 | - | worth (us-them) ~avg | +0.67 | +0.31 | +0.36 | +0.55 | +0.63 | +0.66 | +0.66 |
| r200 | - | landscapers (us-them) | +0.68 | +0.29 | +0.34 | +0.48 | +0.53 | +0.61 | +0.66 |
| r200 | - | soup (us-them) | +0.41 | +0.11 | +0.31 | +0.33 | +0.24 | +0.22 | +0.19 |
| r200 | - | soup (us-them) ~avg | +0.48 | +0.06 | +0.32 | +0.45 | +0.45 | +0.46 | +0.38 |
| r200 | - | worth (us-them) | +0.67 | +0.29 | +0.42 | +0.60 | +0.64 | +0.65 | +0.61 |
| r250 | - | landscapers (us-them) ~avg | +0.75 | +0.35 | +0.28 | +0.42 | +0.52 | +0.69 | +0.75 |
| r300 | - | digs (us-them) | +0.70 | +0.27 | +0.13 | +0.31 | +0.41 | +0.54 | +0.67 |
| r350 | - | cov (us-them) | +0.34 | +0.17 | +0.26 | +0.27 | +0.31 | +0.33 | +0.33 |
| r350 | - | digs (us-them) ~avg | +0.69 | +0.32 | +0.16 | +0.25 | +0.34 | +0.44 | +0.61 |
| r350 | - | vaporators (us-them) | +0.52 | -0.15 | +0.16 | +0.28 | +0.33 | +0.50 | +0.50 |
| r400 | - | dirtDeps (us-them) | +0.67 | +0.12 | +0.10 | +0.23 | +0.33 | +0.45 | +0.63 |
| r500 | - | vaporators (us-them) ~avg | +0.54 | -0.15 | +0.07 | +0.19 | +0.27 | +0.42 | +0.52 |
| r550 | - | dirtDeps (us-them) ~avg | +0.65 | +0.11 | +0.11 | +0.17 | +0.26 | +0.35 | +0.54 |
| r650 | - | cov (us-them) ~avg | +0.33 | +0.12 | +0.26 | +0.27 | +0.30 | +0.29 | +0.33 |
| r700 | - | drones (us-them) | +0.42 | -0.07 | -0.18 | +0.09 | +0.05 | +0.22 | +0.32 |
| r700 | - | moves (us-them) | +0.43 | +0.20 | +0.16 | +0.12 | +0.17 | +0.24 | +0.37 |
| r850 | - | moves (us-them) ~avg | +0.38 | +0.15 | +0.18 | +0.17 | +0.18 | +0.22 | +0.33 |
| r1050 | - | drones (us-them) ~avg | +0.34 | -0.13 | -0.18 | -0.03 | +0.01 | +0.09 | +0.28 |
| - | - | aba (us-them) [inverted] | -0.22 | +0.14 | -0.03 | -0.04 | -0.11 | -0.14 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.23 | +0.18 | +0.06 | +0.00 | -0.08 | -0.16 | -0.22 |
| - | r950 | died (us-them) [inverted] | -0.34 | . | -0.05 | +0.16 | +0.15 | -0.10 | -0.23 |
| - | - | died (us-them) [inverted] ~avg | -0.27 | . | +0.05 | +0.20 | +0.21 | -0.01 | -0.14 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.29 | +0.29 | +0.25 | -0.14 | -0.17 | -0.11 | -0.13 |
| - | - | netguns (us-them) | +0.29 | . | -0.26 | -0.17 | -0.10 | +0.10 | +0.18 |
| - | - | netguns (us-them) ~avg | -0.26 | . | -0.26 | -0.20 | -0.17 | -0.08 | +0.04 |
| - | - | pickups (us-them) | +0.23 | -0.03 | -0.07 | +0.19 | +0.22 | +0.20 | +0.13 |
| - | - | pickups (us-them) ~avg | +0.23 | -0.03 | +0.02 | +0.17 | +0.21 | +0.20 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
