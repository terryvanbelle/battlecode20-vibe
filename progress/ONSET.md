# Which metric starts predicting the result first

11 games, 3 wins. Noise floor about 0.60; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r350 | aba (us-them) [inverted] | -0.49 | +0.35 | +0.36 | -0.29 | -0.49 | -0.35 | -0.36 |
| r50 | r400 | aba (us-them) [inverted] ~avg | -0.44 | +0.36 | +0.36 | +0.07 | -0.36 | -0.36 | -0.44 |
| r50 | - | mines (us-them) | +0.49 | +0.43 | +0.32 | +0.32 | +0.27 | +0.13 | +0.27 |
| r50 | - | mines (us-them) ~avg | +0.49 | +0.46 | +0.35 | +0.35 | +0.35 | +0.27 | +0.26 |
| r50 | - | soup (us-them) | +0.59 | +0.31 | +0.18 | +0.08 | +0.13 | +0.27 | -0.35 |
| r50 | - | soup (us-them) ~avg | +0.59 | +0.40 | +0.50 | +0.43 | +0.45 | +0.31 | +0.24 |
| r50 | - | worth (us-them) | +0.46 | +0.40 | +0.38 | +0.42 | +0.41 | +0.44 | +0.43 |
| r50 | - | worth (us-them) ~avg | +0.45 | +0.40 | +0.39 | +0.42 | +0.42 | +0.43 | +0.45 |
| r100 | - | drones (us-them) | +0.38 | +0.38 | +0.09 | +0.09 | -0.12 | -0.14 | +0.06 |
| r100 | - | drones (us-them) ~avg | +0.38 | +0.38 | +0.21 | +0.14 | +0.01 | -0.09 | -0.05 |
| r100 | - | miners (us-them) | +0.43 | +0.43 | +0.23 | +0.13 | +0.25 | +0.17 | +0.35 |
| r100 | - | miners (us-them) ~avg | +0.36 | +0.34 | +0.32 | +0.22 | +0.24 | +0.24 | +0.30 |
| r150 | - | cov (us-them) | +0.44 | -0.06 | +0.41 | +0.26 | +0.06 | -0.09 | -0.12 |
| r150 | - | cov (us-them) ~avg | +0.37 | -0.01 | +0.37 | +0.33 | +0.24 | +0.06 | -0.05 |
| r150 | - | moves (us-them) | +0.47 | -0.03 | +0.27 | +0.23 | +0.20 | +0.18 | +0.20 |
| r150 | - | moves (us-them) ~avg | +0.36 | -0.08 | +0.32 | +0.24 | +0.22 | +0.20 | +0.19 |
| r150 | - | vaporators (us-them) | +0.50 | +0.29 | +0.41 | +0.40 | +0.44 | +0.48 | +0.41 |
| r150 | - | vaporators (us-them) ~avg | +0.46 | +0.29 | +0.44 | +0.43 | +0.43 | +0.45 | +0.43 |
| r500 | r950 | died (us-them) [inverted] | +0.43 | . | +0.19 | +0.33 | +0.11 | +0.28 | +0.13 |
| r500 | r100 | landscapers (us-them) | +0.53 | -0.34 | -0.17 | +0.14 | +0.02 | +0.46 | +0.48 |
| r600 | - | digs (us-them) | +0.64 | +0.08 | -0.07 | -0.03 | -0.01 | +0.38 | +0.62 |
| r600 | - | dirtDeps (us-them) | +0.63 | +0.06 | -0.08 | -0.05 | -0.03 | +0.34 | +0.57 |
| r600 | r100 | landscapers (us-them) ~avg | +0.57 | -0.34 | -0.25 | -0.05 | -0.03 | +0.43 | +0.56 |
| r700 | - | died (us-them) [inverted] ~avg | +0.33 | . | +0.19 | +0.25 | +0.17 | +0.28 | +0.29 |
| r700 | - | robots (us-them) | +0.43 | +0.24 | +0.18 | +0.27 | +0.23 | +0.29 | +0.42 |
| r700 | - | robots (us-them) ~avg | +0.40 | +0.14 | +0.15 | +0.23 | +0.24 | +0.28 | +0.37 |
| r700 | - | spawned (us-them) | +0.45 | +0.24 | +0.17 | +0.18 | +0.22 | +0.23 | +0.39 |
| r700 | - | units (us-them) | +0.37 | +0.32 | +0.08 | +0.19 | +0.12 | +0.23 | +0.33 |
| r750 | - | digs (us-them) ~avg | +0.62 | +0.08 | -0.06 | -0.05 | -0.03 | +0.10 | +0.50 |
| r800 | - | dirtDeps (us-them) ~avg | +0.59 | +0.06 | -0.08 | -0.06 | -0.04 | +0.08 | +0.44 |
| r850 | - | spawned (us-them) ~avg | +0.41 | +0.14 | +0.15 | +0.20 | +0.21 | +0.23 | +0.32 |
| r1050 | - | units (us-them) ~avg | +0.34 | +0.25 | +0.16 | +0.18 | +0.15 | +0.21 | +0.30 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | . | . | +0.19 | . | . | -0.19 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | . | . | +0.19 | +0.19 | +0.19 | +0.07 |
| - | - | netguns (us-them) | +0.29 | . | . | . | . | . | +0.29 |
| - | - | netguns (us-them) ~avg | +0.28 | . | . | . | . | . | +0.28 |
| - | r650 | pickups (us-them) | +0.38 | . | +0.38 | +0.23 | -0.17 | -0.26 | -0.26 |
| - | - | pickups (us-them) ~avg | +0.38 | . | +0.38 | +0.24 | -0.00 | -0.18 | -0.27 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
