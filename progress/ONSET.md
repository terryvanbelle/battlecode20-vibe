# Which metric starts predicting the result first

44 games, 28 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.66 | +0.19 | +0.40 | +0.50 | +0.51 | +0.65 | +0.63 |
| r200 | - | landscapers (us-them) | +0.55 | +0.05 | +0.31 | +0.31 | +0.34 | +0.50 | +0.55 |
| r200 | - | robots (us-them) | +0.71 | +0.18 | +0.33 | +0.48 | +0.61 | +0.68 | +0.68 |
| r200 | - | spawned (us-them) | +0.70 | +0.18 | +0.32 | +0.38 | +0.53 | +0.64 | +0.70 |
| r200 | - | worth (us-them) ~avg | +0.64 | +0.16 | +0.33 | +0.40 | +0.45 | +0.55 | +0.62 |
| r250 | - | mines (us-them) | +0.63 | +0.11 | +0.30 | +0.34 | +0.40 | +0.52 | +0.58 |
| r300 | - | died (us-them) [inverted] | +0.34 | . | +0.21 | +0.31 | +0.25 | +0.28 | -0.15 |
| r300 | - | died (us-them) [inverted] ~avg | +0.38 | . | +0.21 | +0.38 | +0.35 | +0.31 | -0.01 |
| r300 | - | robots (us-them) ~avg | +0.69 | +0.07 | +0.25 | +0.33 | +0.45 | +0.58 | +0.69 |
| r300 | - | units (us-them) | +0.71 | +0.09 | +0.25 | +0.40 | +0.53 | +0.62 | +0.71 |
| r350 | - | cov (us-them) | +0.55 | +0.10 | +0.13 | +0.23 | +0.36 | +0.47 | +0.55 |
| r350 | - | miners (us-them) | +0.46 | +0.04 | +0.01 | +0.21 | +0.37 | +0.41 | +0.36 |
| r350 | - | spawned (us-them) ~avg | +0.68 | +0.07 | +0.24 | +0.30 | +0.39 | +0.51 | +0.65 |
| r350 | - | units (us-them) ~avg | +0.71 | +0.01 | +0.17 | +0.26 | +0.37 | +0.52 | +0.68 |
| r400 | - | drones (us-them) | +0.56 | +0.15 | +0.14 | +0.21 | +0.31 | +0.39 | +0.55 |
| r400 | - | mines (us-them) ~avg | +0.55 | +0.06 | +0.22 | +0.28 | +0.32 | +0.42 | +0.49 |
| r450 | - | landscapers (us-them) ~avg | +0.56 | +0.07 | +0.24 | +0.26 | +0.29 | +0.43 | +0.56 |
| r500 | - | cov (us-them) ~avg | +0.54 | +0.15 | +0.13 | +0.18 | +0.26 | +0.37 | +0.50 |
| r500 | - | digs (us-them) | +0.51 | +0.09 | +0.18 | +0.25 | +0.27 | +0.40 | +0.51 |
| r500 | - | dirtDeps (us-them) | +0.49 | +0.10 | +0.18 | +0.24 | +0.27 | +0.40 | +0.49 |
| r500 | - | vaporators (us-them) | +0.47 | +0.17 | +0.20 | +0.13 | +0.19 | +0.45 | +0.34 |
| r600 | - | vaporators (us-them) ~avg | +0.43 | +0.17 | +0.21 | +0.16 | +0.17 | +0.33 | +0.41 |
| r650 | - | digs (us-them) ~avg | +0.45 | +0.07 | +0.15 | +0.21 | +0.23 | +0.30 | +0.39 |
| r650 | - | dirtDeps (us-them) ~avg | +0.44 | +0.08 | +0.16 | +0.22 | +0.24 | +0.30 | +0.37 |
| r650 | - | drones (us-them) ~avg | +0.52 | +0.15 | +0.17 | +0.19 | +0.25 | +0.29 | +0.44 |
| r650 | - | miners (us-them) ~avg | +0.41 | -0.05 | -0.05 | +0.04 | +0.18 | +0.30 | +0.36 |
| r700 | - | moves (us-them) | +0.60 | +0.07 | +0.00 | +0.07 | +0.15 | +0.26 | +0.48 |
| r800 | - | pickups (us-them) | +0.39 | . | +0.03 | -0.00 | +0.03 | +0.26 | +0.33 |
| r900 | - | moves (us-them) ~avg | +0.49 | +0.15 | -0.01 | +0.03 | +0.08 | +0.16 | +0.32 |
| r1100 | - | pickups (us-them) ~avg | +0.32 | . | -0.02 | -0.04 | -0.04 | +0.10 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.26 | -0.18 | -0.11 | -0.07 | +0.02 | -0.11 | -0.08 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | -0.18 | -0.12 | -0.12 | -0.03 | -0.07 | -0.06 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.19 | . | +0.27 | +0.27 | +0.18 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.25 | +0.19 | +0.19 | +0.22 | +0.23 | +0.25 | +0.18 |
| - | - | netguns (us-them) | +0.21 | . | +0.05 | -0.06 | +0.11 | +0.12 | +0.07 |
| - | - | netguns (us-them) ~avg | +0.09 | . | +0.05 | -0.00 | +0.03 | +0.06 | +0.06 |
| - | - | soup (us-them) | +0.29 | -0.01 | +0.06 | +0.29 | -0.04 | +0.13 | +0.18 |
| - | - | soup (us-them) ~avg | +0.22 | +0.04 | +0.05 | +0.19 | +0.14 | +0.10 | +0.15 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
