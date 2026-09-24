# Which metric starts predicting the result first

22 games, 6 wins. Noise floor about 0.43; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | digs (us-them) | +0.70 | +0.18 | +0.46 | +0.56 | +0.51 | +0.66 | +0.68 |
| r150 | - | dirtDeps (us-them) | +0.63 | +0.25 | +0.41 | +0.44 | +0.41 | +0.55 | +0.57 |
| r150 | - | dirtDeps (us-them) ~avg | +0.64 | +0.25 | +0.39 | +0.43 | +0.38 | +0.51 | +0.58 |
| r150 | - | landscapers (us-them) | +0.70 | +0.06 | +0.52 | +0.70 | +0.69 | +0.41 | +0.49 |
| r150 | - | landscapers (us-them) ~avg | +0.73 | +0.06 | +0.42 | +0.61 | +0.62 | +0.71 | +0.63 |
| r150 | - | mines (us-them) | +0.66 | +0.29 | +0.44 | +0.51 | +0.52 | +0.59 | +0.66 |
| r150 | - | mines (us-them) ~avg | +0.64 | +0.26 | +0.40 | +0.47 | +0.50 | +0.56 | +0.64 |
| r150 | - | robots (us-them) | +0.63 | +0.26 | +0.60 | +0.60 | +0.52 | +0.47 | +0.48 |
| r150 | - | robots (us-them) ~avg | +0.59 | +0.17 | +0.48 | +0.59 | +0.55 | +0.53 | +0.54 |
| r150 | - | spawned (us-them) | +0.64 | +0.27 | +0.59 | +0.60 | +0.53 | +0.48 | +0.52 |
| r150 | - | spawned (us-them) ~avg | +0.59 | +0.18 | +0.48 | +0.59 | +0.56 | +0.55 | +0.57 |
| r150 | - | units (us-them) | +0.63 | +0.20 | +0.54 | +0.59 | +0.53 | +0.41 | +0.49 |
| r150 | - | worth (us-them) | +0.51 | +0.21 | +0.49 | +0.47 | +0.44 | +0.46 | +0.47 |
| r150 | - | worth (us-them) ~avg | +0.51 | +0.20 | +0.41 | +0.47 | +0.46 | +0.47 | +0.50 |
| r200 | - | digs (us-them) ~avg | +0.73 | +0.18 | +0.42 | +0.52 | +0.48 | +0.63 | +0.70 |
| r200 | - | pickups (us-them) | +0.40 | -0.36 | +0.35 | +0.35 | +0.34 | +0.34 | +0.22 |
| r200 | - | units (us-them) ~avg | +0.57 | +0.06 | +0.41 | +0.57 | +0.53 | +0.52 | +0.55 |
| r300 | - | drones (us-them) | +0.48 | +0.15 | +0.25 | +0.30 | +0.35 | +0.23 | +0.46 |
| r300 | r100 | pickups (us-them) ~avg | +0.38 | -0.36 | +0.10 | +0.31 | +0.31 | +0.38 | +0.26 |
| r350 | - | soup (us-them) | +0.37 | -0.18 | +0.19 | +0.27 | +0.24 | +0.16 | +0.37 |
| r450 | - | cov (us-them) | +0.59 | -0.18 | +0.16 | +0.21 | +0.28 | +0.40 | +0.54 |
| r500 | - | cov (us-them) ~avg | +0.52 | -0.22 | +0.03 | +0.13 | +0.19 | +0.35 | +0.45 |
| r500 | - | drones (us-them) ~avg | +0.43 | +0.15 | +0.22 | +0.26 | +0.29 | +0.29 | +0.43 |
| r500 | - | miners (us-them) | +0.37 | +0.15 | +0.20 | +0.24 | +0.17 | +0.33 | +0.36 |
| r500 | - | moves (us-them) | +0.41 | -0.32 | -0.03 | +0.16 | +0.23 | +0.35 | +0.41 |
| r550 | - | vaporators (us-them) | +0.40 | +0.04 | -0.11 | -0.04 | +0.08 | +0.36 | +0.37 |
| r650 | - | moves (us-them) ~avg | +0.39 | -0.33 | -0.08 | +0.06 | +0.13 | +0.29 | +0.39 |
| r650 | - | netguns (us-them) | +0.45 | . | . | . | +0.12 | +0.18 | +0.35 |
| r700 | - | miners (us-them) ~avg | +0.36 | -0.00 | +0.12 | +0.23 | +0.20 | +0.27 | +0.36 |
| r750 | - | vaporators (us-them) ~avg | +0.35 | +0.04 | -0.10 | -0.07 | +0.06 | +0.21 | +0.34 |
| r800 | - | netguns (us-them) ~avg | +0.35 | . | . | . | +0.12 | +0.15 | +0.32 |
| - | - | aba (us-them) [inverted] | -0.21 | -0.14 | -0.06 | +0.07 | +0.19 | +0.17 | +0.13 |
| - | - | aba (us-them) [inverted] ~avg | -0.21 | -0.16 | -0.11 | -0.01 | +0.11 | +0.17 | +0.16 |
| - | - | died (us-them) [inverted] | +0.29 | -0.13 | +0.23 | +0.24 | +0.24 | +0.20 | -0.11 |
| - | - | died (us-them) [inverted] ~avg | +0.26 | -0.13 | +0.07 | +0.26 | +0.22 | +0.23 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.13 | . | +0.13 | +0.13 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.13 | . | +0.13 | +0.13 | +0.12 | +0.13 | . |
| - | - | soup (us-them) ~avg | +0.28 | -0.10 | -0.02 | +0.10 | +0.25 | +0.21 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
