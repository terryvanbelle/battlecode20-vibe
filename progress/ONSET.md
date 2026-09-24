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
| r250 | - | landscapers (us-them) | +0.58 | +0.12 | +0.19 | +0.46 | +0.49 | +0.51 | +0.43 |
| r250 | - | robots (us-them) | +0.46 | +0.21 | +0.24 | +0.35 | +0.32 | +0.38 | +0.42 |
| r250 | - | spawned (us-them) | +0.48 | +0.21 | +0.24 | +0.30 | +0.28 | +0.39 | +0.45 |
| r250 | - | worth (us-them) | +0.38 | +0.08 | +0.24 | +0.31 | +0.25 | +0.32 | +0.38 |
| r300 | - | units (us-them) | +0.46 | +0.17 | +0.24 | +0.35 | +0.32 | +0.35 | +0.38 |
| r350 | - | landscapers (us-them) ~avg | +0.62 | +0.12 | +0.13 | +0.24 | +0.39 | +0.60 | +0.59 |
| r450 | - | robots (us-them) ~avg | +0.48 | +0.07 | +0.14 | +0.23 | +0.29 | +0.36 | +0.42 |
| r450 | - | units (us-them) ~avg | +0.47 | +0.05 | +0.13 | +0.23 | +0.29 | +0.36 | +0.40 |
| r500 | - | spawned (us-them) ~avg | +0.47 | +0.07 | +0.15 | +0.22 | +0.26 | +0.35 | +0.41 |
| r550 | - | digs (us-them) | +0.54 | -0.08 | +0.12 | +0.13 | +0.20 | +0.41 | +0.51 |
| r550 | - | dirtDeps (us-them) | +0.52 | -0.06 | +0.13 | +0.11 | +0.17 | +0.39 | +0.49 |
| r600 | - | cov (us-them) | +0.33 | -0.02 | -0.01 | +0.18 | +0.18 | +0.31 | +0.27 |
| r600 | - | vaporators (us-them) | +0.39 | -0.04 | +0.04 | -0.01 | +0.09 | +0.33 | +0.39 |
| r650 | - | digs (us-them) ~avg | +0.53 | -0.08 | +0.09 | +0.09 | +0.14 | +0.28 | +0.44 |
| r700 | - | dirtDeps (us-them) ~avg | +0.51 | -0.07 | +0.10 | +0.07 | +0.11 | +0.26 | +0.42 |
| r750 | - | worth (us-them) ~avg | +0.37 | +0.02 | +0.17 | +0.24 | +0.25 | +0.29 | +0.33 |
| r800 | - | vaporators (us-them) ~avg | +0.36 | -0.04 | +0.06 | -0.00 | -0.00 | +0.20 | +0.34 |
| r1150 | - | moves (us-them) | +0.32 | -0.21 | -0.06 | +0.15 | +0.25 | +0.25 | +0.24 |
| - | - | aba (us-them) [inverted] | +0.19 | +0.04 | +0.13 | +0.12 | +0.07 | +0.19 | +0.02 |
| - | - | aba (us-them) [inverted] ~avg | +0.19 | +0.05 | +0.10 | +0.12 | +0.08 | +0.17 | +0.13 |
| - | - | cov (us-them) ~avg | +0.27 | +0.02 | -0.02 | +0.12 | +0.15 | +0.22 | +0.27 |
| - | - | died (us-them) [inverted] | -0.31 | . | -0.03 | +0.19 | +0.21 | -0.02 | -0.13 |
| - | - | died (us-them) [inverted] ~avg | -0.31 | . | -0.16 | +0.11 | +0.13 | +0.10 | -0.03 |
| - | - | drones (us-them) | +0.19 | +0.06 | -0.06 | -0.13 | -0.15 | -0.05 | +0.07 |
| - | - | drones (us-them) ~avg | -0.14 | +0.06 | -0.04 | -0.06 | -0.12 | -0.11 | -0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.23 | +0.19 | +0.19 | . | . | +0.13 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.19 | +0.19 | . | +0.20 | +0.23 |
| - | - | miners (us-them) | +0.25 | +0.06 | +0.25 | +0.23 | +0.23 | +0.09 | +0.19 |
| - | - | miners (us-them) ~avg | +0.24 | -0.03 | +0.08 | +0.19 | +0.21 | +0.17 | +0.20 |
| - | - | mines (us-them) | +0.28 | -0.02 | +0.18 | +0.26 | +0.24 | +0.28 | +0.24 |
| - | - | mines (us-them) ~avg | -0.26 | -0.10 | +0.09 | +0.16 | +0.20 | +0.24 | +0.23 |
| - | - | moves (us-them) ~avg | +0.23 | -0.11 | -0.10 | +0.04 | +0.15 | +0.23 | +0.22 |
| - | - | netguns (us-them) | +0.19 | . | -0.10 | +0.07 | +0.12 | +0.07 | +0.19 |
| - | - | netguns (us-them) ~avg | -0.12 | . | -0.12 | -0.02 | +0.05 | +0.06 | +0.07 |
| - | - | pickups (us-them) | +0.27 | -0.10 | -0.26 | -0.15 | -0.03 | +0.14 | +0.21 |
| - | - | pickups (us-them) ~avg | -0.25 | -0.10 | -0.23 | -0.22 | -0.15 | +0.04 | +0.15 |
| - | - | soup (us-them) | +0.25 | -0.10 | +0.15 | +0.17 | +0.09 | -0.07 | -0.02 |
| - | - | soup (us-them) ~avg | +0.19 | -0.09 | +0.12 | +0.19 | +0.15 | +0.00 | -0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
