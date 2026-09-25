# Which metric starts predicting the result first

44 games, 26 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.56 | +0.35 | +0.41 | +0.38 | +0.41 | +0.42 | +0.48 |
| r100 | - | drones (us-them) ~avg | +0.53 | +0.35 | +0.50 | +0.53 | +0.49 | +0.46 | +0.48 |
| r100 | - | vaporators (us-them) | +0.58 | +0.30 | +0.51 | +0.36 | +0.34 | +0.33 | +0.37 |
| r150 | - | mines (us-them) | +0.53 | +0.24 | +0.36 | +0.34 | +0.35 | +0.43 | +0.50 |
| r150 | - | mines (us-them) ~avg | +0.50 | +0.18 | +0.34 | +0.41 | +0.40 | +0.45 | +0.48 |
| r150 | - | robots (us-them) | +0.78 | +0.20 | +0.39 | +0.46 | +0.50 | +0.63 | +0.73 |
| r150 | - | spawned (us-them) | +0.74 | +0.20 | +0.37 | +0.44 | +0.49 | +0.59 | +0.70 |
| r150 | - | vaporators (us-them) ~avg | +0.52 | +0.24 | +0.51 | +0.42 | +0.38 | +0.38 | +0.41 |
| r150 | - | worth (us-them) | +0.65 | +0.26 | +0.47 | +0.52 | +0.56 | +0.61 | +0.62 |
| r150 | - | worth (us-them) ~avg | +0.68 | +0.20 | +0.42 | +0.53 | +0.56 | +0.61 | +0.66 |
| r200 | - | robots (us-them) ~avg | +0.75 | +0.16 | +0.33 | +0.46 | +0.50 | +0.60 | +0.70 |
| r200 | - | spawned (us-them) ~avg | +0.69 | +0.16 | +0.31 | +0.44 | +0.48 | +0.57 | +0.65 |
| r250 | - | pickups (us-them) ~avg | +0.62 | +0.17 | +0.29 | +0.35 | +0.38 | +0.49 | +0.62 |
| r300 | - | miners (us-them) | +0.43 | +0.10 | +0.23 | +0.33 | +0.33 | +0.24 | +0.35 |
| r300 | - | netguns (us-them) | +0.50 | . | +0.25 | +0.31 | +0.36 | +0.32 | +0.44 |
| r300 | - | pickups (us-them) | +0.63 | +0.17 | +0.27 | +0.34 | +0.42 | +0.58 | +0.57 |
| r300 | - | units (us-them) | +0.78 | +0.16 | +0.24 | +0.33 | +0.35 | +0.58 | +0.69 |
| r300 | - | units (us-them) ~avg | +0.69 | +0.14 | +0.21 | +0.31 | +0.36 | +0.50 | +0.63 |
| r350 | - | miners (us-them) ~avg | +0.43 | +0.14 | +0.17 | +0.29 | +0.35 | +0.33 | +0.40 |
| r350 | - | netguns (us-them) ~avg | +0.44 | . | +0.25 | +0.30 | +0.36 | +0.37 | +0.39 |
| r400 | - | moves (us-them) | +0.52 | -0.17 | +0.03 | +0.24 | +0.30 | +0.32 | +0.44 |
| r450 | - | cov (us-them) | +0.48 | -0.13 | +0.05 | +0.23 | +0.29 | +0.37 | +0.47 |
| r500 | - | landscapers (us-them) | +0.73 | +0.02 | +0.10 | +0.12 | +0.15 | +0.61 | +0.68 |
| r550 | - | landscapers (us-them) ~avg | +0.67 | -0.02 | +0.06 | +0.07 | +0.11 | +0.40 | +0.61 |
| r600 | - | digs (us-them) | +0.57 | +0.00 | -0.01 | +0.00 | +0.05 | +0.32 | +0.50 |
| r650 | - | cov (us-them) ~avg | +0.42 | -0.12 | -0.08 | +0.04 | +0.12 | +0.29 | +0.37 |
| r650 | - | dirtDeps (us-them) | +0.55 | +0.00 | -0.03 | -0.04 | -0.01 | +0.27 | +0.47 |
| r800 | - | digs (us-them) ~avg | +0.49 | +0.01 | -0.00 | -0.01 | +0.02 | +0.18 | +0.39 |
| r800 | - | moves (us-them) ~avg | +0.43 | -0.12 | -0.08 | +0.11 | +0.21 | +0.28 | +0.35 |
| r850 | - | dirtDeps (us-them) ~avg | +0.46 | +0.01 | -0.03 | -0.05 | -0.03 | +0.11 | +0.34 |
| - | r400 | aba (us-them) [inverted] | -0.48 | -0.17 | -0.22 | -0.28 | -0.30 | -0.42 | -0.47 |
| - | r500 | aba (us-them) [inverted] ~avg | -0.47 | -0.17 | -0.20 | -0.27 | -0.29 | -0.34 | -0.44 |
| - | r1000 | died (us-them) [inverted] | -0.31 | . | +0.17 | +0.22 | +0.21 | +0.19 | -0.29 |
| - | - | died (us-them) [inverted] ~avg | +0.29 | . | +0.15 | +0.27 | +0.29 | +0.21 | -0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.17 | -0.13 | +0.19 | +0.04 | +0.04 | +0.12 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.17 | +0.17 | -0.13 | +0.08 | +0.08 | -0.11 | -0.08 |
| - | - | soup (us-them) | -0.32 | +0.01 | -0.10 | -0.08 | -0.10 | -0.10 | +0.07 |
| - | - | soup (us-them) ~avg | -0.11 | +0.04 | -0.03 | -0.04 | -0.05 | -0.11 | +0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
