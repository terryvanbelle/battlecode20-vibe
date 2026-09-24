# Which metric starts predicting the result first

17 games, 8 wins. Noise floor about 0.49; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r800 | aba (us-them) [inverted] | -0.49 | +0.28 | +0.01 | -0.15 | -0.16 | -0.24 | -0.34 |
| r50 | r1150 | aba (us-them) [inverted] ~avg | +0.43 | +0.31 | +0.16 | -0.04 | -0.07 | -0.13 | -0.23 |
| r50 | - | mines (us-them) | +0.54 | +0.26 | +0.15 | +0.12 | +0.22 | +0.47 | +0.51 |
| r50 | - | mines (us-them) ~avg | +0.51 | +0.28 | +0.18 | +0.16 | +0.18 | +0.48 | +0.51 |
| r150 | - | digs (us-them) ~avg | +0.31 | +0.23 | +0.25 | +0.11 | +0.00 | -0.05 | -0.01 |
| r150 | - | dirtDeps (us-them) ~avg | +0.32 | +0.22 | +0.24 | +0.09 | -0.01 | -0.08 | -0.04 |
| r200 | - | died (us-them) [inverted] | +0.50 | . | +0.34 | +0.42 | +0.36 | +0.48 | +0.33 |
| r200 | - | died (us-them) [inverted] ~avg | +0.55 | . | +0.31 | +0.43 | +0.39 | +0.54 | +0.47 |
| r250 | - | cov (us-them) | +0.49 | +0.11 | +0.30 | +0.46 | +0.40 | +0.44 | +0.32 |
| r250 | - | cov (us-them) ~avg | +0.58 | +0.03 | +0.15 | +0.45 | +0.48 | +0.55 | +0.45 |
| r350 | - | miners (us-them) | +0.63 | +0.06 | +0.08 | +0.30 | +0.31 | +0.62 | +0.53 |
| r400 | - | robots (us-them) | +0.62 | +0.17 | +0.11 | +0.24 | +0.30 | +0.58 | +0.58 |
| r450 | - | spawned (us-them) | +0.59 | +0.17 | +0.09 | +0.10 | +0.22 | +0.53 | +0.56 |
| r450 | - | units (us-them) | +0.65 | +0.14 | +0.11 | +0.22 | +0.25 | +0.56 | +0.52 |
| r500 | - | landscapers (us-them) | +0.57 | +0.11 | +0.09 | +0.01 | +0.06 | +0.50 | +0.50 |
| r500 | - | miners (us-them) ~avg | +0.61 | +0.08 | +0.05 | +0.16 | +0.23 | +0.53 | +0.61 |
| r500 | - | moves (us-them) | +0.57 | +0.17 | -0.02 | +0.07 | +0.17 | +0.48 | +0.55 |
| r500 | - | pickups (us-them) | +0.45 | . | -0.16 | +0.12 | +0.25 | +0.37 | +0.36 |
| r500 | - | robots (us-them) ~avg | +0.61 | +0.14 | +0.11 | +0.16 | +0.21 | +0.60 | +0.61 |
| r500 | - | spawned (us-them) ~avg | +0.58 | +0.14 | +0.10 | +0.10 | +0.12 | +0.49 | +0.56 |
| r500 | - | units (us-them) ~avg | +0.59 | +0.13 | +0.13 | +0.17 | +0.19 | +0.54 | +0.58 |
| r500 | - | vaporators (us-them) | +0.48 | . | -0.01 | +0.08 | +0.19 | +0.47 | +0.44 |
| r500 | - | vaporators (us-them) ~avg | +0.47 | . | -0.04 | +0.03 | +0.11 | +0.37 | +0.44 |
| r500 | - | worth (us-them) | +0.59 | +0.33 | +0.09 | +0.14 | +0.27 | +0.57 | +0.55 |
| r500 | - | worth (us-them) ~avg | +0.59 | +0.33 | +0.16 | +0.13 | +0.18 | +0.54 | +0.58 |
| r550 | - | moves (us-them) ~avg | +0.55 | +0.03 | +0.04 | +0.04 | +0.11 | +0.35 | +0.51 |
| r600 | - | pickups (us-them) ~avg | +0.46 | . | -0.22 | -0.03 | +0.13 | +0.32 | +0.40 |
| r650 | - | landscapers (us-them) ~avg | +0.53 | +0.11 | +0.14 | +0.08 | +0.06 | +0.27 | +0.46 |
| r700 | - | drones (us-them) | +0.52 | +0.16 | +0.04 | +0.08 | +0.06 | +0.05 | +0.31 |
| r700 | - | netguns (us-them) | +0.48 | . | . | . | . | +0.21 | +0.47 |
| r750 | - | netguns (us-them) ~avg | +0.49 | . | . | . | . | +0.21 | +0.40 |
| r950 | - | drones (us-them) ~avg | +0.46 | +0.16 | +0.08 | +0.07 | +0.05 | +0.14 | +0.26 |
| r1050 | - | soup (us-them) | +0.36 | +0.22 | +0.01 | -0.15 | -0.00 | +0.23 | +0.20 |
| r1100 | - | digs (us-them) | +0.40 | +0.20 | +0.23 | +0.05 | -0.06 | -0.01 | +0.21 |
| r1150 | - | dirtDeps (us-them) | +0.34 | +0.20 | +0.22 | +0.04 | -0.07 | -0.04 | +0.17 |
| r1150 | - | soup (us-them) ~avg | +0.33 | +0.26 | +0.26 | +0.09 | +0.10 | +0.18 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.22 | . | . | . | +0.21 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.22 | +0.22 | +0.22 | +0.22 | +0.21 | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
