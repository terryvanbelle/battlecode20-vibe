# Which metric starts predicting the result first

47 games, 28 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.46 | +0.20 | +0.26 | +0.33 | +0.30 | +0.36 | +0.46 |
| r300 | - | drones (us-them) | +0.32 | +0.20 | +0.13 | +0.32 | +0.22 | +0.16 | +0.21 |
| r300 | - | drones (us-them) ~avg | +0.37 | +0.27 | +0.23 | +0.37 | +0.33 | +0.23 | +0.19 |
| r350 | - | died (us-them) [inverted] | +0.32 | . | +0.32 | +0.24 | +0.26 | +0.20 | +0.19 |
| r350 | - | pickups (us-them) | +0.44 | +0.13 | +0.21 | +0.27 | +0.41 | +0.41 | +0.43 |
| r350 | - | pickups (us-them) ~avg | +0.42 | +0.13 | +0.21 | +0.26 | +0.36 | +0.42 | +0.41 |
| r350 | - | worth (us-them) ~avg | +0.41 | +0.19 | +0.28 | +0.29 | +0.32 | +0.33 | +0.37 |
| r400 | - | robots (us-them) | +0.62 | +0.17 | +0.17 | +0.20 | +0.30 | +0.45 | +0.62 |
| r450 | - | landscapers (us-them) | +0.60 | +0.05 | +0.07 | +0.02 | +0.24 | +0.53 | +0.59 |
| r450 | - | spawned (us-them) | +0.59 | +0.17 | +0.15 | +0.16 | +0.25 | +0.41 | +0.54 |
| r450 | - | units (us-them) | +0.57 | +0.05 | +0.09 | +0.11 | +0.24 | +0.41 | +0.56 |
| r500 | - | vaporators (us-them) | +0.48 | -0.01 | -0.05 | +0.09 | +0.20 | +0.38 | +0.45 |
| r550 | - | robots (us-them) ~avg | +0.53 | +0.20 | +0.21 | +0.18 | +0.22 | +0.35 | +0.49 |
| r600 | - | landscapers (us-them) ~avg | +0.52 | +0.05 | +0.09 | +0.00 | +0.06 | +0.34 | +0.51 |
| r650 | - | spawned (us-them) ~avg | +0.47 | +0.20 | +0.20 | +0.16 | +0.18 | +0.30 | +0.40 |
| r650 | - | units (us-them) ~avg | +0.46 | +0.07 | +0.11 | +0.07 | +0.12 | +0.30 | +0.43 |
| r650 | - | vaporators (us-them) ~avg | +0.42 | -0.01 | -0.08 | +0.05 | +0.15 | +0.28 | +0.41 |
| r700 | - | digs (us-them) | +0.47 | +0.11 | +0.17 | +0.01 | +0.04 | +0.24 | +0.45 |
| r700 | - | dirtDeps (us-them) | +0.47 | +0.12 | +0.19 | -0.00 | +0.02 | +0.22 | +0.45 |
| r750 | - | died (us-them) [inverted] ~avg | +0.32 | . | +0.32 | +0.24 | +0.28 | +0.27 | +0.30 |
| r900 | - | miners (us-them) | +0.31 | -0.04 | +0.00 | +0.03 | +0.01 | +0.02 | +0.31 |
| r1050 | - | digs (us-them) ~avg | +0.36 | +0.11 | +0.18 | +0.04 | +0.03 | +0.13 | +0.29 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.36 | +0.12 | +0.20 | +0.03 | +0.02 | +0.11 | +0.29 |
| r1150 | - | netguns (us-them) | +0.31 | . | -0.03 | +0.13 | +0.15 | +0.16 | +0.23 |
| - | - | aba (us-them) [inverted] | +0.26 | +0.23 | +0.25 | +0.20 | +0.15 | -0.01 | -0.02 |
| - | - | aba (us-them) [inverted] ~avg | +0.26 | +0.25 | +0.26 | +0.24 | +0.20 | +0.08 | +0.02 |
| - | - | cov (us-them) | +0.21 | -0.11 | -0.07 | +0.08 | +0.15 | +0.19 | +0.14 |
| - | - | cov (us-them) ~avg | +0.16 | -0.08 | -0.12 | -0.05 | +0.03 | +0.13 | +0.15 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.15 | +0.22 | . | . | . | +0.19 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.15 | +0.20 | -0.12 | -0.12 | -0.03 | +0.10 |
| - | - | miners (us-them) ~avg | +0.11 | -0.01 | -0.00 | +0.02 | +0.00 | -0.04 | +0.05 |
| - | - | mines (us-them) | +0.28 | +0.19 | +0.24 | +0.25 | +0.24 | +0.21 | +0.21 |
| - | - | mines (us-them) ~avg | +0.25 | +0.14 | +0.24 | +0.25 | +0.25 | +0.21 | +0.17 |
| - | r100 | moves (us-them) | -0.36 | -0.35 | -0.26 | -0.15 | -0.13 | +0.04 | +0.08 |
| - | r100 | moves (us-them) ~avg | -0.36 | -0.32 | -0.34 | -0.23 | -0.22 | -0.10 | -0.02 |
| - | - | netguns (us-them) ~avg | +0.21 | . | -0.03 | +0.08 | +0.11 | +0.15 | +0.18 |
| - | - | soup (us-them) | +0.28 | -0.02 | +0.22 | +0.26 | -0.05 | -0.14 | -0.09 |
| - | - | soup (us-them) ~avg | +0.22 | -0.06 | +0.19 | +0.22 | +0.20 | -0.03 | -0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
