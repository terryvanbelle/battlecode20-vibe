# Which metric starts predicting the result first

45 games, 23 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.55 | +0.36 | +0.46 | +0.33 | +0.40 | +0.28 | +0.33 |
| r100 | - | drones (us-them) ~avg | +0.53 | +0.36 | +0.53 | +0.43 | +0.42 | +0.35 | +0.34 |
| r150 | - | mines (us-them) | +0.54 | +0.21 | +0.40 | +0.47 | +0.52 | +0.49 | +0.50 |
| r150 | - | mines (us-them) ~avg | +0.58 | +0.19 | +0.37 | +0.44 | +0.55 | +0.57 | +0.55 |
| r150 | - | robots (us-them) | +0.61 | +0.15 | +0.37 | +0.53 | +0.56 | +0.53 | +0.57 |
| r150 | - | spawned (us-them) | +0.59 | +0.15 | +0.38 | +0.49 | +0.57 | +0.53 | +0.55 |
| r150 | - | worth (us-them) | +0.64 | +0.23 | +0.42 | +0.57 | +0.64 | +0.59 | +0.56 |
| r150 | - | worth (us-them) ~avg | +0.65 | +0.21 | +0.38 | +0.47 | +0.58 | +0.65 | +0.62 |
| r200 | - | miners (us-them) | +0.33 | +0.14 | +0.33 | +0.27 | +0.21 | +0.20 | +0.17 |
| r200 | - | pickups (us-them) | +0.52 | +0.22 | +0.33 | +0.41 | +0.33 | +0.46 | +0.47 |
| r200 | - | pickups (us-them) ~avg | +0.54 | +0.22 | +0.33 | +0.39 | +0.40 | +0.45 | +0.50 |
| r200 | - | robots (us-them) ~avg | +0.63 | +0.12 | +0.32 | +0.45 | +0.56 | +0.61 | +0.60 |
| r200 | - | spawned (us-them) ~avg | +0.62 | +0.12 | +0.33 | +0.44 | +0.56 | +0.61 | +0.59 |
| r200 | - | units (us-them) | +0.63 | +0.08 | +0.31 | +0.38 | +0.45 | +0.46 | +0.57 |
| r250 | - | netguns (us-them) | +0.61 | . | +0.15 | +0.35 | +0.46 | +0.45 | +0.51 |
| r300 | - | miners (us-them) ~avg | +0.31 | +0.17 | +0.24 | +0.31 | +0.29 | +0.24 | +0.24 |
| r300 | - | netguns (us-them) ~avg | +0.53 | . | +0.15 | +0.33 | +0.41 | +0.44 | +0.46 |
| r300 | - | units (us-them) ~avg | +0.61 | +0.11 | +0.28 | +0.35 | +0.45 | +0.53 | +0.57 |
| r350 | - | cov (us-them) | +0.46 | +0.20 | +0.20 | +0.29 | +0.35 | +0.43 | +0.42 |
| r350 | - | vaporators (us-them) | +0.36 | +0.10 | +0.18 | +0.29 | +0.29 | +0.26 | +0.27 |
| r400 | - | cov (us-them) ~avg | +0.47 | +0.22 | +0.17 | +0.26 | +0.31 | +0.38 | +0.43 |
| r400 | - | moves (us-them) | +0.41 | +0.12 | +0.14 | +0.27 | +0.32 | +0.33 | +0.38 |
| r450 | - | landscapers (us-them) | +0.63 | -0.11 | +0.14 | +0.14 | +0.24 | +0.57 | +0.57 |
| r500 | - | moves (us-them) ~avg | +0.44 | +0.13 | +0.11 | +0.21 | +0.27 | +0.34 | +0.42 |
| r500 | - | soup (us-them) | +0.30 | +0.17 | +0.17 | +0.01 | +0.08 | +0.20 | +0.04 |
| r550 | - | landscapers (us-them) ~avg | +0.57 | -0.11 | +0.10 | +0.11 | +0.16 | +0.36 | +0.56 |
| r550 | - | vaporators (us-them) ~avg | +0.34 | +0.10 | +0.18 | +0.24 | +0.27 | +0.32 | +0.31 |
| r650 | - | aba (us-them) [inverted] | +0.41 | -0.12 | -0.04 | +0.07 | +0.26 | +0.28 | +0.34 |
| r700 | - | aba (us-them) [inverted] ~avg | +0.38 | -0.09 | -0.06 | -0.02 | +0.09 | +0.23 | +0.38 |
| r700 | - | digs (us-them) | +0.51 | -0.19 | -0.06 | -0.02 | -0.02 | +0.21 | +0.46 |
| r750 | - | dirtDeps (us-them) | +0.52 | -0.20 | -0.04 | -0.03 | -0.02 | +0.19 | +0.47 |
| r1000 | - | digs (us-them) ~avg | +0.40 | -0.19 | -0.08 | -0.04 | -0.03 | +0.07 | +0.28 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.40 | -0.20 | -0.07 | -0.05 | -0.04 | +0.05 | +0.27 |
| - | - | died (us-them) [inverted] | -0.26 | . | -0.06 | +0.16 | -0.05 | -0.12 | -0.15 |
| - | - | died (us-them) [inverted] ~avg | -0.21 | . | -0.12 | +0.10 | +0.04 | -0.04 | -0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.15 | +0.21 | +0.15 | +0.15 | +0.15 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.15 | +0.22 | +0.16 | +0.15 | +0.15 | . |
| - | - | soup (us-them) ~avg | +0.27 | +0.27 | +0.19 | +0.10 | +0.08 | +0.21 | +0.15 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
