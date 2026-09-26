# Which metric starts predicting the result first

44 games, 23 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.36 | +0.33 | +0.36 | +0.34 | +0.34 | +0.30 | +0.25 |
| r100 | - | mines (us-them) ~avg | +0.43 | +0.32 | +0.37 | +0.36 | +0.39 | +0.42 | +0.35 |
| r150 | - | worth (us-them) | +0.53 | +0.28 | +0.35 | +0.38 | +0.38 | +0.50 | +0.51 |
| r150 | - | worth (us-them) ~avg | +0.55 | +0.23 | +0.35 | +0.38 | +0.42 | +0.50 | +0.54 |
| r300 | - | pickups (us-them) | +0.47 | +0.26 | +0.22 | +0.32 | +0.29 | +0.34 | +0.42 |
| r300 | - | pickups (us-them) ~avg | +0.44 | +0.26 | +0.29 | +0.30 | +0.32 | +0.30 | +0.38 |
| r300 | - | vaporators (us-them) | +0.47 | +0.04 | +0.12 | +0.32 | +0.32 | +0.47 | +0.37 |
| r350 | - | vaporators (us-them) ~avg | +0.48 | +0.04 | +0.09 | +0.29 | +0.33 | +0.46 | +0.46 |
| r500 | - | landscapers (us-them) | +0.72 | +0.08 | +0.03 | +0.03 | +0.09 | +0.44 | +0.72 |
| r500 | - | robots (us-them) | +0.64 | +0.08 | +0.14 | +0.22 | +0.23 | +0.53 | +0.61 |
| r500 | - | robots (us-them) ~avg | +0.61 | +0.12 | +0.10 | +0.11 | +0.20 | +0.42 | +0.58 |
| r500 | - | spawned (us-them) | +0.55 | +0.08 | +0.13 | +0.23 | +0.21 | +0.50 | +0.55 |
| r500 | - | spawned (us-them) ~avg | +0.56 | +0.12 | +0.09 | +0.11 | +0.21 | +0.41 | +0.53 |
| r550 | - | netguns (us-them) | +0.47 | . | +0.20 | +0.28 | +0.15 | +0.31 | +0.37 |
| r550 | - | units (us-them) | +0.60 | +0.11 | +0.03 | +0.05 | +0.07 | +0.38 | +0.58 |
| r600 | - | digs (us-them) | +0.68 | +0.18 | +0.12 | +0.01 | +0.07 | +0.33 | +0.56 |
| r650 | - | dirtDeps (us-them) | +0.67 | +0.20 | +0.11 | -0.04 | +0.02 | +0.27 | +0.53 |
| r650 | - | landscapers (us-them) ~avg | +0.71 | +0.07 | +0.03 | -0.07 | -0.00 | +0.26 | +0.61 |
| r650 | - | netguns (us-them) ~avg | +0.40 | . | +0.20 | +0.26 | +0.24 | +0.28 | +0.35 |
| r700 | - | died (us-them) [inverted] | +0.39 | . | +0.09 | +0.06 | +0.12 | +0.25 | +0.39 |
| r700 | - | miners (us-them) | +0.46 | -0.02 | -0.13 | -0.09 | -0.01 | +0.18 | +0.45 |
| r700 | - | units (us-them) ~avg | +0.58 | +0.12 | +0.06 | -0.04 | +0.01 | +0.22 | +0.48 |
| r800 | - | digs (us-them) ~avg | +0.60 | +0.16 | +0.09 | -0.02 | +0.02 | +0.16 | +0.42 |
| r850 | - | dirtDeps (us-them) ~avg | +0.57 | +0.18 | +0.08 | -0.08 | -0.04 | +0.10 | +0.37 |
| r900 | r100 | moves (us-them) | +0.43 | -0.30 | -0.30 | -0.14 | -0.01 | +0.11 | +0.32 |
| r950 | - | died (us-them) [inverted] ~avg | +0.33 | . | +0.09 | -0.01 | +0.04 | +0.12 | +0.29 |
| r1050 | - | miners (us-them) ~avg | +0.34 | +0.05 | -0.03 | -0.11 | -0.09 | +0.03 | +0.20 |
| r1100 | - | drones (us-them) | +0.34 | +0.25 | +0.13 | +0.22 | +0.06 | +0.13 | +0.27 |
| r1100 | - | drones (us-them) ~avg | +0.33 | +0.26 | +0.22 | +0.24 | +0.17 | +0.13 | +0.24 |
| r1100 | r100 | moves (us-them) ~avg | +0.35 | -0.32 | -0.30 | -0.21 | -0.13 | +0.01 | +0.19 |
| - | r650 | aba (us-them) [inverted] | -0.42 | +0.10 | +0.14 | -0.05 | -0.13 | -0.26 | -0.37 |
| - | r750 | aba (us-them) [inverted] ~avg | -0.40 | +0.10 | +0.13 | +0.00 | -0.09 | -0.24 | -0.34 |
| - | - | cov (us-them) | -0.21 | -0.20 | -0.11 | -0.02 | -0.01 | +0.01 | +0.14 |
| - | - | cov (us-them) ~avg | -0.18 | -0.14 | -0.16 | -0.07 | -0.05 | -0.11 | +0.02 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.24 | +0.24 | +0.18 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.25 | +0.24 | +0.22 | +0.15 | +0.15 | +0.15 | +0.15 |
| - | - | soup (us-them) | +0.33 | +0.10 | +0.22 | -0.01 | +0.09 | -0.20 | -0.25 |
| - | - | soup (us-them) ~avg | +0.30 | +0.05 | +0.30 | +0.24 | +0.22 | +0.04 | -0.09 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
