# Which metric starts predicting the result first

42 games, 26 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) ~avg | +0.41 | +0.31 | +0.31 | +0.35 | +0.33 | +0.33 | +0.41 |
| r50 | - | robots (us-them) | +0.62 | +0.34 | +0.39 | +0.45 | +0.48 | +0.61 | +0.57 |
| r50 | - | robots (us-them) ~avg | +0.60 | +0.38 | +0.41 | +0.45 | +0.48 | +0.54 | +0.59 |
| r50 | - | spawned (us-them) | +0.58 | +0.34 | +0.41 | +0.44 | +0.48 | +0.58 | +0.55 |
| r50 | - | spawned (us-them) ~avg | +0.57 | +0.38 | +0.42 | +0.45 | +0.47 | +0.52 | +0.55 |
| r50 | - | units (us-them) | +0.70 | +0.27 | +0.33 | +0.33 | +0.34 | +0.59 | +0.65 |
| r50 | - | units (us-them) ~avg | +0.64 | +0.34 | +0.33 | +0.35 | +0.36 | +0.46 | +0.59 |
| r50 | - | worth (us-them) | +0.58 | +0.35 | +0.33 | +0.48 | +0.51 | +0.58 | +0.52 |
| r50 | - | worth (us-them) ~avg | +0.56 | +0.38 | +0.34 | +0.43 | +0.48 | +0.56 | +0.56 |
| r100 | - | mines (us-them) | +0.53 | +0.34 | +0.34 | +0.48 | +0.51 | +0.53 | +0.52 |
| r100 | - | mines (us-them) ~avg | +0.52 | +0.33 | +0.34 | +0.45 | +0.49 | +0.52 | +0.52 |
| r150 | - | pickups (us-them) ~avg | +0.41 | +0.29 | +0.25 | +0.26 | +0.30 | +0.34 | +0.37 |
| r250 | - | drones (us-them) | +0.49 | +0.17 | +0.20 | +0.36 | +0.25 | +0.27 | +0.23 |
| r250 | - | drones (us-them) ~avg | +0.42 | +0.17 | +0.24 | +0.35 | +0.34 | +0.28 | +0.27 |
| r300 | - | miners (us-them) | +0.52 | +0.22 | +0.30 | +0.34 | +0.28 | +0.37 | +0.52 |
| r300 | - | vaporators (us-them) | +0.51 | -0.13 | +0.03 | +0.33 | +0.42 | +0.50 | +0.41 |
| r400 | - | pickups (us-them) | +0.46 | +0.29 | +0.19 | +0.20 | +0.34 | +0.40 | +0.34 |
| r400 | - | vaporators (us-them) ~avg | +0.48 | -0.13 | -0.01 | +0.18 | +0.33 | +0.48 | +0.45 |
| r500 | - | landscapers (us-them) | +0.72 | +0.08 | +0.20 | +0.11 | +0.22 | +0.59 | +0.68 |
| r550 | - | died (us-them) [inverted] | +0.35 | . | -0.20 | +0.06 | +0.03 | +0.34 | +0.18 |
| r550 | - | landscapers (us-them) ~avg | +0.65 | +0.08 | +0.13 | +0.14 | +0.18 | +0.39 | +0.60 |
| r600 | - | cov (us-them) | +0.39 | +0.10 | +0.02 | +0.11 | +0.12 | +0.30 | +0.34 |
| r650 | - | digs (us-them) | +0.66 | -0.03 | +0.06 | +0.03 | +0.08 | +0.26 | +0.52 |
| r650 | - | dirtDeps (us-them) | +0.65 | -0.01 | +0.08 | +0.04 | +0.09 | +0.24 | +0.51 |
| r700 | - | died (us-them) [inverted] ~avg | +0.35 | . | -0.25 | -0.06 | +0.01 | +0.26 | +0.27 |
| r800 | - | moves (us-them) | +0.47 | -0.14 | -0.16 | -0.03 | +0.03 | +0.19 | +0.36 |
| r850 | - | digs (us-them) ~avg | +0.52 | -0.03 | +0.04 | +0.01 | +0.05 | +0.15 | +0.35 |
| r900 | - | dirtDeps (us-them) ~avg | +0.51 | -0.01 | +0.06 | +0.02 | +0.06 | +0.14 | +0.33 |
| r1050 | - | moves (us-them) ~avg | +0.37 | -0.16 | -0.14 | -0.08 | -0.03 | +0.09 | +0.24 |
| r1200 | - | cov (us-them) ~avg | +0.30 | +0.07 | +0.07 | +0.05 | +0.08 | +0.19 | +0.29 |
| - | r1050 | aba (us-them) [inverted] | -0.41 | +0.08 | -0.02 | +0.10 | +0.12 | -0.04 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.25 | +0.03 | +0.00 | +0.04 | +0.10 | +0.02 | -0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | . | +0.20 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | . | +0.20 | . | . | . | . |
| - | - | netguns (us-them) | +0.22 | . | -0.03 | +0.06 | +0.16 | +0.19 | +0.12 |
| - | - | netguns (us-them) ~avg | +0.16 | . | -0.03 | +0.03 | +0.09 | +0.15 | +0.15 |
| - | - | soup (us-them) | +0.27 | +0.03 | +0.01 | +0.17 | -0.07 | +0.05 | +0.21 |
| - | - | soup (us-them) ~avg | +0.24 | +0.08 | +0.03 | +0.12 | +0.05 | +0.09 | +0.15 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
