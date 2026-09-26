# Which metric starts predicting the result first

42 games, 21 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.48 | +0.43 | +0.35 | +0.32 | +0.32 | +0.43 | +0.45 |
| r50 | - | mines (us-them) ~avg | +0.46 | +0.41 | +0.41 | +0.37 | +0.35 | +0.39 | +0.43 |
| r50 | - | worth (us-them) | +0.66 | +0.43 | +0.37 | +0.39 | +0.45 | +0.57 | +0.58 |
| r50 | - | worth (us-them) ~avg | +0.61 | +0.43 | +0.42 | +0.40 | +0.42 | +0.52 | +0.56 |
| r100 | - | robots (us-them) | +0.62 | +0.31 | +0.27 | +0.39 | +0.37 | +0.56 | +0.60 |
| r200 | - | robots (us-them) ~avg | +0.60 | +0.28 | +0.31 | +0.38 | +0.41 | +0.52 | +0.59 |
| r200 | - | spawned (us-them) ~avg | +0.60 | +0.27 | +0.31 | +0.37 | +0.42 | +0.50 | +0.59 |
| r250 | - | spawned (us-them) | +0.62 | +0.30 | +0.27 | +0.40 | +0.39 | +0.54 | +0.60 |
| r250 | - | vaporators (us-them) | +0.65 | +0.07 | +0.21 | +0.52 | +0.54 | +0.63 | +0.65 |
| r300 | - | vaporators (us-them) ~avg | +0.68 | +0.07 | +0.14 | +0.38 | +0.52 | +0.59 | +0.64 |
| r450 | - | units (us-them) | +0.55 | +0.24 | +0.19 | +0.21 | +0.24 | +0.46 | +0.54 |
| r500 | - | landscapers (us-them) | +0.61 | +0.20 | +0.16 | +0.13 | +0.20 | +0.44 | +0.61 |
| r500 | - | pickups (us-them) | +0.44 | . | -0.01 | +0.13 | +0.16 | +0.39 | +0.44 |
| r500 | - | units (us-them) ~avg | +0.52 | +0.21 | +0.23 | +0.23 | +0.24 | +0.38 | +0.52 |
| r550 | - | moves (us-them) | +0.43 | -0.27 | -0.07 | +0.17 | +0.24 | +0.34 | +0.42 |
| r600 | - | miners (us-them) | +0.51 | +0.03 | -0.00 | +0.15 | +0.12 | +0.33 | +0.50 |
| r600 | - | pickups (us-them) ~avg | +0.43 | . | +0.04 | +0.10 | +0.13 | +0.32 | +0.43 |
| r650 | - | landscapers (us-them) ~avg | +0.59 | +0.20 | +0.16 | +0.13 | +0.14 | +0.29 | +0.52 |
| r700 | - | drones (us-them) | +0.35 | +0.07 | +0.13 | +0.06 | +0.08 | +0.24 | +0.35 |
| r700 | - | moves (us-them) ~avg | +0.42 | -0.27 | -0.15 | +0.07 | +0.19 | +0.28 | +0.38 |
| r750 | - | drones (us-them) ~avg | +0.34 | +0.07 | +0.12 | +0.13 | +0.12 | +0.20 | +0.34 |
| r800 | - | miners (us-them) ~avg | +0.43 | +0.07 | +0.07 | +0.12 | +0.14 | +0.19 | +0.35 |
| r850 | - | digs (us-them) | +0.50 | -0.00 | +0.03 | -0.00 | +0.01 | +0.14 | +0.37 |
| r850 | - | dirtDeps (us-them) | +0.50 | +0.05 | +0.05 | -0.00 | -0.02 | +0.09 | +0.35 |
| r1050 | - | digs (us-them) ~avg | +0.41 | -0.00 | +0.02 | -0.01 | -0.03 | +0.05 | +0.22 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.40 | +0.05 | +0.06 | -0.01 | -0.05 | +0.02 | +0.19 |
| - | r500 | aba (us-them) [inverted] | -0.41 | +0.14 | -0.11 | -0.28 | -0.30 | -0.39 | -0.41 |
| - | r500 | aba (us-them) [inverted] ~avg | -0.42 | +0.13 | -0.06 | -0.22 | -0.28 | -0.36 | -0.42 |
| - | - | cov (us-them) | +0.18 | -0.09 | -0.02 | +0.04 | +0.06 | +0.14 | +0.16 |
| - | - | cov (us-them) ~avg | +0.13 | -0.03 | -0.07 | -0.01 | +0.03 | +0.05 | +0.13 |
| - | - | died (us-them) [inverted] | +0.25 | +0.16 | +0.06 | +0.11 | +0.12 | +0.25 | +0.18 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | +0.16 | +0.09 | +0.10 | +0.11 | +0.23 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.17 | +0.16 | +0.17 | +0.17 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.16 | +0.17 | +0.19 | +0.18 | +0.18 | +0.18 |
| - | - | netguns (us-them) | -0.20 | . | -0.20 | -0.06 | -0.15 | +0.11 | +0.15 |
| - | - | netguns (us-them) ~avg | -0.20 | . | -0.20 | -0.11 | -0.13 | -0.07 | +0.05 |
| - | - | soup (us-them) | +0.34 | +0.02 | +0.18 | -0.04 | -0.00 | +0.19 | -0.00 |
| - | - | soup (us-them) ~avg | +0.34 | +0.11 | +0.25 | +0.05 | +0.02 | +0.07 | +0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
