# Which metric starts predicting the result first

45 games, 20 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.46 | +0.39 | +0.30 | +0.31 | +0.44 | +0.43 | +0.37 |
| r100 | - | mines (us-them) ~avg | +0.43 | +0.38 | +0.33 | +0.30 | +0.37 | +0.43 | +0.40 |
| r100 | - | worth (us-them) | +0.48 | +0.38 | +0.28 | +0.32 | +0.45 | +0.47 | +0.46 |
| r100 | - | worth (us-them) ~avg | +0.47 | +0.40 | +0.35 | +0.32 | +0.39 | +0.46 | +0.47 |
| r350 | - | robots (us-them) | +0.53 | +0.21 | +0.07 | +0.24 | +0.38 | +0.44 | +0.53 |
| r350 | - | spawned (us-them) | +0.50 | +0.21 | +0.06 | +0.19 | +0.35 | +0.45 | +0.50 |
| r400 | - | landscapers (us-them) | +0.61 | -0.02 | +0.02 | +0.08 | +0.36 | +0.43 | +0.53 |
| r400 | - | robots (us-them) ~avg | +0.50 | +0.28 | +0.19 | +0.20 | +0.31 | +0.44 | +0.48 |
| r450 | - | spawned (us-them) ~avg | +0.48 | +0.28 | +0.19 | +0.17 | +0.28 | +0.44 | +0.47 |
| r450 | - | units (us-them) | +0.56 | +0.13 | +0.04 | +0.13 | +0.28 | +0.34 | +0.50 |
| r450 | - | vaporators (us-them) | +0.40 | -0.01 | +0.06 | +0.22 | +0.27 | +0.37 | +0.36 |
| r500 | - | landscapers (us-them) ~avg | +0.60 | -0.02 | +0.02 | -0.01 | +0.16 | +0.45 | +0.54 |
| r550 | - | digs (us-them) | +0.59 | -0.16 | +0.20 | +0.20 | +0.22 | +0.39 | +0.49 |
| r550 | - | dirtDeps (us-them) | +0.58 | -0.14 | +0.17 | +0.17 | +0.21 | +0.38 | +0.48 |
| r550 | - | netguns (us-them) | +0.38 | . | +0.04 | +0.02 | +0.02 | +0.36 | +0.31 |
| r550 | - | units (us-them) ~avg | +0.48 | +0.23 | +0.15 | +0.12 | +0.20 | +0.33 | +0.41 |
| r550 | - | vaporators (us-them) ~avg | +0.40 | -0.01 | +0.02 | +0.12 | +0.18 | +0.35 | +0.40 |
| r650 | - | digs (us-them) ~avg | +0.53 | -0.17 | +0.16 | +0.18 | +0.20 | +0.29 | +0.43 |
| r650 | - | dirtDeps (us-them) ~avg | +0.52 | -0.16 | +0.14 | +0.15 | +0.18 | +0.27 | +0.42 |
| r1150 | - | moves (us-them) | +0.32 | -0.08 | -0.03 | -0.02 | +0.03 | +0.14 | +0.23 |
| - | - | aba (us-them) [inverted] | -0.22 | +0.06 | +0.11 | +0.02 | -0.02 | -0.14 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.13 | +0.12 | +0.07 | -0.05 | -0.14 |
| - | - | cov (us-them) | -0.30 | -0.25 | -0.25 | -0.09 | -0.04 | +0.00 | -0.04 |
| - | - | cov (us-them) ~avg | -0.28 | -0.24 | -0.27 | -0.16 | -0.11 | -0.07 | -0.07 |
| - | - | died (us-them) [inverted] | +0.24 | . | +0.15 | +0.15 | +0.20 | +0.17 | +0.24 |
| - | - | died (us-them) [inverted] ~avg | +0.22 | . | +0.14 | +0.17 | +0.18 | +0.15 | +0.20 |
| - | - | drones (us-them) | +0.30 | +0.14 | +0.10 | +0.16 | +0.18 | +0.26 | +0.24 |
| - | - | drones (us-them) ~avg | +0.23 | +0.16 | +0.16 | +0.19 | +0.18 | +0.21 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.13 | +0.12 | +0.13 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.12 | +0.21 | +0.13 | +0.13 | +0.13 | +0.14 |
| - | - | miners (us-them) | +0.28 | +0.17 | -0.01 | -0.00 | -0.10 | -0.05 | +0.28 |
| - | - | miners (us-them) ~avg | +0.29 | +0.29 | +0.19 | +0.18 | +0.04 | -0.06 | +0.06 |
| - | - | moves (us-them) ~avg | +0.22 | -0.13 | -0.05 | -0.02 | -0.02 | +0.06 | +0.15 |
| - | - | netguns (us-them) ~avg | +0.28 | . | +0.08 | +0.04 | +0.03 | +0.15 | +0.28 |
| - | - | pickups (us-them) | +0.32 | +0.08 | +0.23 | +0.32 | +0.15 | +0.11 | +0.12 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.08 | +0.18 | +0.26 | +0.24 | +0.14 | +0.11 |
| - | - | soup (us-them) | +0.21 | +0.10 | +0.20 | +0.02 | -0.01 | +0.01 | +0.11 |
| - | - | soup (us-them) ~avg | +0.21 | +0.11 | +0.20 | +0.21 | +0.17 | -0.02 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
