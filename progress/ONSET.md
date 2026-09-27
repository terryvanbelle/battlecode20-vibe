# Which metric starts predicting the result first

45 games, 29 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.62 | +0.34 | +0.27 | +0.39 | +0.48 | +0.62 | +0.58 |
| r50 | - | mines (us-them) ~avg | +0.59 | +0.35 | +0.27 | +0.31 | +0.41 | +0.55 | +0.59 |
| r50 | - | robots (us-them) ~avg | +0.60 | +0.31 | +0.19 | +0.19 | +0.24 | +0.54 | +0.60 |
| r50 | - | spawned (us-them) ~avg | +0.67 | +0.33 | +0.17 | +0.14 | +0.19 | +0.55 | +0.67 |
| r50 | - | units (us-them) ~avg | +0.50 | +0.26 | +0.14 | +0.11 | +0.09 | +0.32 | +0.49 |
| r50 | - | worth (us-them) | +0.74 | +0.35 | +0.36 | +0.50 | +0.60 | +0.74 | +0.64 |
| r50 | - | worth (us-them) ~avg | +0.71 | +0.38 | +0.33 | +0.42 | +0.53 | +0.70 | +0.68 |
| r200 | r950 | died (us-them) [inverted] | +0.41 | -0.17 | +0.41 | +0.25 | +0.31 | +0.13 | -0.06 |
| r200 | - | vaporators (us-them) | +0.70 | +0.09 | +0.32 | +0.47 | +0.57 | +0.70 | +0.57 |
| r250 | - | died (us-them) [inverted] ~avg | +0.31 | -0.17 | +0.22 | +0.30 | +0.24 | +0.15 | +0.05 |
| r250 | - | vaporators (us-them) ~avg | +0.69 | +0.09 | +0.25 | +0.37 | +0.48 | +0.68 | +0.66 |
| r400 | - | miners (us-them) | +0.38 | +0.05 | +0.07 | +0.20 | +0.32 | +0.32 | +0.34 |
| r400 | - | robots (us-them) | +0.65 | +0.20 | +0.14 | +0.25 | +0.33 | +0.64 | +0.62 |
| r450 | - | landscapers (us-them) | +0.54 | +0.09 | +0.09 | +0.03 | -0.00 | +0.43 | +0.53 |
| r450 | - | spawned (us-them) | +0.71 | +0.21 | +0.08 | +0.18 | +0.25 | +0.68 | +0.68 |
| r450 | - | units (us-them) | +0.58 | +0.17 | +0.08 | +0.11 | +0.13 | +0.46 | +0.58 |
| r500 | - | pickups (us-them) | +0.41 | +0.04 | +0.07 | -0.05 | +0.22 | +0.29 | +0.41 |
| r550 | - | miners (us-them) ~avg | +0.35 | +0.19 | +0.14 | +0.16 | +0.21 | +0.31 | +0.34 |
| r650 | - | landscapers (us-them) ~avg | +0.50 | +0.09 | +0.10 | +0.04 | +0.02 | +0.26 | +0.44 |
| r750 | - | digs (us-them) | +0.53 | +0.15 | +0.21 | +0.11 | +0.09 | +0.22 | +0.41 |
| r750 | - | drones (us-them) | +0.42 | +0.17 | -0.09 | +0.04 | +0.08 | +0.19 | +0.42 |
| r800 | - | dirtDeps (us-them) | +0.53 | +0.14 | +0.10 | +0.03 | +0.03 | +0.20 | +0.41 |
| r800 | - | moves (us-them) | +0.41 | +0.05 | +0.12 | +0.12 | +0.09 | +0.22 | +0.36 |
| r800 | - | pickups (us-them) ~avg | +0.38 | +0.04 | +0.02 | -0.07 | +0.04 | +0.21 | +0.35 |
| r1000 | - | digs (us-them) ~avg | +0.39 | +0.15 | +0.20 | +0.16 | +0.12 | +0.15 | +0.28 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.39 | +0.14 | +0.10 | +0.06 | +0.04 | +0.11 | +0.26 |
| r1050 | - | drones (us-them) ~avg | +0.36 | +0.17 | -0.04 | -0.02 | +0.00 | +0.07 | +0.28 |
| - | r650 | aba (us-them) [inverted] | -0.40 | +0.17 | +0.23 | +0.12 | -0.12 | -0.29 | -0.40 |
| - | r1150 | aba (us-them) [inverted] ~avg | -0.31 | +0.15 | +0.21 | +0.18 | +0.03 | -0.16 | -0.30 |
| - | r50 | cov (us-them) | -0.37 | -0.28 | -0.30 | -0.04 | +0.04 | +0.06 | +0.13 |
| - | r50 | cov (us-them) ~avg | -0.39 | -0.35 | -0.39 | -0.21 | -0.09 | +0.02 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | +0.14 | -0.18 | +0.17 | +0.18 | +0.14 | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.21 | +0.14 | -0.21 | -0.04 | +0.09 | +0.05 | +0.10 |
| - | - | moves (us-them) ~avg | +0.30 | -0.03 | +0.11 | +0.12 | +0.09 | +0.13 | +0.23 |
| - | - | netguns (us-them) | -0.26 | -0.12 | -0.17 | -0.19 | -0.24 | +0.16 | +0.23 |
| - | - | netguns (us-them) ~avg | -0.25 | -0.12 | -0.17 | -0.20 | -0.22 | -0.14 | +0.06 |
| - | - | soup (us-them) | -0.20 | +0.04 | +0.08 | -0.07 | +0.09 | -0.10 | +0.04 |
| - | - | soup (us-them) ~avg | -0.10 | +0.01 | +0.02 | -0.02 | -0.00 | -0.10 | -0.04 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
