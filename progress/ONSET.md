# Which metric starts predicting the result first

48 games, 20 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r350 | - | pickups (us-them) | +0.52 | -0.37 | +0.00 | +0.24 | +0.33 | +0.42 | +0.49 |
| r400 | - | cov (us-them) | +0.56 | +0.14 | +0.06 | +0.18 | +0.37 | +0.45 | +0.56 |
| r400 | - | drones (us-them) | +0.46 | -0.10 | +0.07 | +0.28 | +0.36 | +0.43 | +0.43 |
| r400 | - | mines (us-them) | +0.47 | +0.16 | +0.18 | +0.29 | +0.32 | +0.35 | +0.43 |
| r400 | - | worth (us-them) | +0.55 | +0.13 | +0.22 | +0.28 | +0.33 | +0.42 | +0.55 |
| r450 | - | vaporators (us-them) | +0.49 | +0.19 | +0.07 | +0.16 | +0.25 | +0.42 | +0.48 |
| r450 | - | worth (us-them) ~avg | +0.51 | +0.11 | +0.21 | +0.26 | +0.30 | +0.37 | +0.46 |
| r500 | - | cov (us-them) ~avg | +0.48 | +0.06 | +0.13 | +0.15 | +0.23 | +0.35 | +0.46 |
| r500 | - | drones (us-them) ~avg | +0.44 | -0.10 | -0.04 | +0.18 | +0.25 | +0.36 | +0.44 |
| r500 | - | mines (us-them) ~avg | +0.39 | +0.17 | +0.17 | +0.24 | +0.28 | +0.33 | +0.35 |
| r500 | - | pickups (us-them) ~avg | +0.47 | -0.37 | -0.03 | +0.14 | +0.26 | +0.38 | +0.47 |
| r500 | - | robots (us-them) | +0.55 | +0.05 | +0.14 | +0.17 | +0.25 | +0.38 | +0.53 |
| r550 | - | spawned (us-them) | +0.53 | +0.05 | +0.11 | +0.18 | +0.23 | +0.36 | +0.49 |
| r550 | - | units (us-them) | +0.50 | -0.00 | +0.12 | +0.14 | +0.20 | +0.34 | +0.49 |
| r600 | - | netguns (us-them) | +0.35 | . | -0.07 | -0.05 | +0.03 | +0.35 | +0.23 |
| r600 | - | vaporators (us-them) ~avg | +0.46 | +0.19 | +0.11 | +0.14 | +0.19 | +0.32 | +0.45 |
| r650 | - | robots (us-them) ~avg | +0.49 | +0.05 | +0.12 | +0.12 | +0.18 | +0.29 | +0.41 |
| r800 | - | landscapers (us-them) | +0.46 | +0.09 | +0.12 | +0.13 | +0.17 | +0.26 | +0.39 |
| r800 | - | spawned (us-them) ~avg | +0.45 | +0.05 | +0.11 | +0.12 | +0.16 | +0.26 | +0.36 |
| r800 | - | units (us-them) ~avg | +0.45 | +0.03 | +0.07 | +0.08 | +0.13 | +0.24 | +0.38 |
| r900 | - | moves (us-them) | +0.39 | +0.01 | -0.10 | -0.06 | +0.01 | +0.12 | +0.30 |
| r1000 | - | dirtDeps (us-them) | +0.39 | +0.22 | +0.18 | +0.03 | +0.07 | +0.20 | +0.24 |
| r1050 | - | digs (us-them) | +0.38 | +0.20 | +0.14 | +0.02 | +0.07 | +0.18 | +0.23 |
| r1050 | - | landscapers (us-them) ~avg | +0.37 | +0.09 | +0.08 | +0.04 | +0.10 | +0.21 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.20 | +0.16 | -0.06 | -0.10 | -0.06 | -0.11 | -0.11 |
| - | - | aba (us-them) [inverted] ~avg | +0.17 | +0.17 | +0.03 | -0.08 | -0.08 | -0.08 | -0.09 |
| - | - | died (us-them) [inverted] | +0.28 | . | +0.23 | +0.00 | +0.13 | +0.16 | +0.26 |
| - | - | died (us-them) [inverted] ~avg | +0.26 | . | +0.21 | +0.05 | +0.11 | +0.14 | +0.22 |
| - | - | digs (us-them) ~avg | +0.26 | +0.20 | +0.13 | +0.00 | +0.03 | +0.11 | +0.14 |
| - | - | dirtDeps (us-them) ~avg | +0.28 | +0.22 | +0.18 | +0.02 | +0.05 | +0.12 | +0.15 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.37 | +0.37 | +0.26 | -0.12 | +0.24 | . | -0.25 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.37 | +0.37 | +0.23 | -0.16 | -0.15 | -0.15 | -0.16 |
| - | - | miners (us-them) | +0.27 | -0.04 | +0.04 | -0.02 | +0.01 | +0.12 | +0.25 |
| - | - | miners (us-them) ~avg | +0.15 | +0.01 | +0.04 | +0.04 | +0.03 | +0.05 | +0.13 |
| - | - | moves (us-them) ~avg | +0.28 | -0.07 | -0.07 | -0.06 | -0.03 | +0.05 | +0.17 |
| - | - | netguns (us-them) ~avg | +0.20 | . | -0.07 | -0.07 | -0.03 | +0.14 | +0.19 |
| - | - | soup (us-them) | +0.31 | -0.08 | +0.01 | +0.08 | +0.07 | -0.04 | +0.07 |
| - | - | soup (us-them) ~avg | +0.14 | -0.03 | -0.01 | +0.09 | +0.11 | +0.08 | +0.03 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
