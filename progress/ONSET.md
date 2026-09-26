# Which metric starts predicting the result first

38 games, 19 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | r900 | soup (us-them) ~avg | +0.39 | +0.39 | +0.25 | +0.26 | +0.08 | -0.04 | -0.31 |
| r150 | - | died (us-them) [inverted] | +0.33 | +0.25 | +0.31 | +0.02 | +0.05 | +0.21 | -0.02 |
| r150 | - | died (us-them) [inverted] ~avg | +0.38 | +0.26 | +0.36 | +0.20 | +0.12 | +0.17 | +0.06 |
| r150 | - | hqBuried (us-them) [inverted] | +0.36 | +0.26 | +0.36 | +0.15 | +0.15 | . | +0.15 |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.26 | +0.31 | +0.06 | +0.07 | +0.08 | +0.05 |
| r250 | - | aba (us-them) [inverted] | +0.38 | +0.17 | +0.21 | +0.31 | +0.32 | +0.01 | -0.21 |
| r250 | - | aba (us-them) [inverted] ~avg | +0.40 | +0.15 | +0.20 | +0.36 | +0.36 | +0.19 | -0.02 |
| r250 | - | robots (us-them) | +0.60 | +0.01 | +0.24 | +0.37 | +0.47 | +0.49 | +0.60 |
| r250 | - | spawned (us-them) | +0.57 | -0.03 | +0.16 | +0.38 | +0.46 | +0.46 | +0.56 |
| r250 | - | worth (us-them) | +0.48 | +0.17 | +0.22 | +0.38 | +0.41 | +0.44 | +0.39 |
| r300 | - | mines (us-them) | +0.40 | +0.20 | +0.18 | +0.35 | +0.37 | +0.38 | +0.31 |
| r300 | - | robots (us-them) ~avg | +0.57 | +0.02 | +0.12 | +0.32 | +0.39 | +0.48 | +0.56 |
| r300 | - | worth (us-them) ~avg | +0.43 | +0.17 | +0.17 | +0.34 | +0.37 | +0.43 | +0.41 |
| r350 | - | spawned (us-them) ~avg | +0.54 | -0.02 | +0.06 | +0.27 | +0.36 | +0.44 | +0.53 |
| r350 | - | units (us-them) | +0.58 | +0.08 | +0.13 | +0.25 | +0.34 | +0.44 | +0.58 |
| r400 | - | mines (us-them) ~avg | +0.39 | +0.20 | +0.16 | +0.28 | +0.32 | +0.37 | +0.38 |
| r400 | - | units (us-them) ~avg | +0.54 | +0.09 | +0.12 | +0.28 | +0.31 | +0.43 | +0.52 |
| r500 | - | landscapers (us-them) | +0.54 | -0.03 | +0.05 | +0.17 | +0.23 | +0.43 | +0.54 |
| r550 | - | landscapers (us-them) ~avg | +0.53 | -0.03 | +0.02 | +0.21 | +0.21 | +0.36 | +0.50 |
| r600 | - | digs (us-them) | +0.59 | +0.26 | +0.18 | +0.20 | +0.22 | +0.33 | +0.52 |
| r650 | - | dirtDeps (us-them) | +0.59 | +0.18 | +0.10 | +0.16 | +0.19 | +0.30 | +0.51 |
| r800 | - | digs (us-them) ~avg | +0.54 | +0.26 | +0.16 | +0.20 | +0.21 | +0.26 | +0.38 |
| r800 | - | pickups (us-them) | +0.35 | -0.02 | +0.03 | +0.08 | +0.18 | +0.22 | +0.34 |
| r850 | - | dirtDeps (us-them) ~avg | +0.53 | +0.19 | +0.03 | +0.14 | +0.16 | +0.22 | +0.36 |
| r950 | - | vaporators (us-them) | +0.34 | -0.01 | -0.03 | +0.02 | +0.07 | +0.26 | +0.30 |
| r1050 | - | pickups (us-them) ~avg | +0.30 | -0.02 | +0.01 | +0.03 | +0.09 | +0.20 | +0.29 |
| r1150 | - | drones (us-them) | +0.35 | -0.08 | -0.09 | -0.01 | +0.03 | +0.17 | +0.23 |
| r1150 | - | netguns (us-them) | +0.33 | . | +0.15 | +0.15 | +0.24 | +0.18 | +0.11 |
| - | - | cov (us-them) | +0.26 | -0.06 | +0.12 | +0.06 | +0.20 | +0.20 | +0.26 |
| - | - | cov (us-them) ~avg | +0.20 | -0.02 | +0.11 | +0.03 | +0.10 | +0.16 | +0.20 |
| - | - | drones (us-them) ~avg | +0.23 | -0.08 | -0.08 | -0.07 | -0.04 | +0.05 | +0.20 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.32 | +0.17 | +0.23 | +0.14 | +0.23 | +0.28 | +0.32 |
| - | - | miners (us-them) ~avg | +0.28 | +0.15 | +0.21 | +0.25 | +0.26 | +0.27 | +0.28 |
| - | - | moves (us-them) | +0.28 | -0.15 | -0.00 | +0.04 | +0.06 | +0.17 | +0.23 |
| - | - | moves (us-them) ~avg | +0.21 | -0.09 | -0.01 | +0.04 | +0.06 | +0.12 | +0.18 |
| - | - | netguns (us-them) ~avg | +0.27 | . | +0.15 | +0.15 | +0.20 | +0.22 | +0.17 |
| - | r800 | soup (us-them) | -0.41 | +0.36 | +0.02 | +0.05 | -0.14 | -0.23 | -0.41 |
| - | - | vaporators (us-them) ~avg | +0.26 | -0.01 | -0.05 | -0.09 | -0.02 | +0.11 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
