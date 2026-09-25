# Which metric starts predicting the result first

39 games, 20 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.46 | +0.33 | +0.44 | +0.44 | +0.31 | +0.26 | +0.23 |
| r100 | - | mines (us-them) ~avg | +0.47 | +0.31 | +0.44 | +0.46 | +0.45 | +0.35 | +0.26 |
| r100 | - | worth (us-them) | +0.60 | +0.37 | +0.47 | +0.52 | +0.51 | +0.50 | +0.56 |
| r100 | - | worth (us-them) ~avg | +0.58 | +0.36 | +0.49 | +0.51 | +0.53 | +0.51 | +0.53 |
| r150 | - | vaporators (us-them) | +0.52 | +0.25 | +0.42 | +0.47 | +0.40 | +0.49 | +0.45 |
| r150 | - | vaporators (us-them) ~avg | +0.53 | +0.15 | +0.38 | +0.45 | +0.46 | +0.50 | +0.51 |
| r200 | - | robots (us-them) | +0.65 | +0.13 | +0.39 | +0.36 | +0.33 | +0.41 | +0.58 |
| r200 | - | robots (us-them) ~avg | +0.60 | +0.20 | +0.30 | +0.37 | +0.40 | +0.41 | +0.49 |
| r200 | - | spawned (us-them) | +0.57 | +0.13 | +0.39 | +0.36 | +0.33 | +0.33 | +0.48 |
| r200 | - | spawned (us-them) ~avg | +0.51 | +0.20 | +0.30 | +0.37 | +0.40 | +0.39 | +0.40 |
| r400 | - | landscapers (us-them) | +0.72 | +0.04 | +0.20 | +0.21 | +0.31 | +0.37 | +0.65 |
| r500 | - | pickups (us-them) | +0.37 | +0.21 | +0.30 | +0.26 | +0.25 | +0.37 | +0.31 |
| r600 | - | digs (us-them) | +0.71 | +0.07 | -0.07 | +0.03 | +0.16 | +0.30 | +0.59 |
| r600 | - | landscapers (us-them) ~avg | +0.69 | +0.11 | +0.11 | +0.12 | +0.20 | +0.32 | +0.56 |
| r650 | - | dirtDeps (us-them) | +0.68 | +0.06 | -0.06 | +0.04 | +0.14 | +0.27 | +0.55 |
| r650 | - | pickups (us-them) ~avg | +0.32 | +0.21 | +0.21 | +0.22 | +0.18 | +0.30 | +0.31 |
| r650 | - | units (us-them) | +0.61 | +0.11 | +0.30 | +0.18 | +0.15 | +0.29 | +0.52 |
| r800 | - | units (us-them) ~avg | +0.49 | +0.21 | +0.23 | +0.22 | +0.22 | +0.24 | +0.36 |
| r850 | - | digs (us-them) ~avg | +0.55 | +0.12 | -0.05 | +0.00 | +0.08 | +0.19 | +0.36 |
| r900 | - | dirtDeps (us-them) ~avg | +0.52 | +0.12 | -0.06 | +0.00 | +0.07 | +0.17 | +0.32 |
| r950 | - | moves (us-them) | +0.45 | -0.21 | -0.19 | -0.10 | -0.11 | -0.00 | +0.22 |
| r1050 | - | cov (us-them) | +0.33 | -0.25 | -0.21 | -0.05 | -0.02 | +0.10 | +0.20 |
| r1200 | - | moves (us-them) ~avg | +0.32 | -0.17 | -0.22 | -0.13 | -0.12 | -0.07 | +0.03 |
| - | - | aba (us-them) [inverted] | -0.23 | -0.10 | -0.17 | -0.19 | -0.10 | -0.12 | -0.15 |
| - | - | aba (us-them) [inverted] ~avg | -0.18 | -0.10 | -0.17 | -0.18 | -0.15 | -0.13 | -0.11 |
| - | - | cov (us-them) ~avg | -0.30 | -0.23 | -0.25 | -0.13 | -0.07 | +0.01 | +0.09 |
| - | - | died (us-them) [inverted] | +0.32 | . | . | -0.03 | -0.03 | +0.24 | +0.25 |
| - | - | died (us-them) [inverted] ~avg | +0.21 | . | . | -0.11 | -0.10 | +0.04 | +0.17 |
| - | - | drones (us-them) | +0.26 | +0.26 | +0.16 | +0.21 | +0.08 | +0.12 | +0.17 |
| - | - | drones (us-them) ~avg | +0.21 | +0.21 | +0.16 | +0.20 | +0.14 | +0.12 | +0.12 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.20 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.26 | +0.16 | +0.16 | +0.16 | +0.16 | . |
| - | - | miners (us-them) | +0.25 | +0.03 | +0.20 | -0.13 | -0.14 | +0.02 | +0.19 |
| - | - | miners (us-them) ~avg | +0.19 | +0.12 | +0.19 | +0.06 | -0.05 | -0.06 | +0.03 |
| - | - | netguns (us-them) | +0.28 | . | +0.16 | +0.21 | +0.24 | +0.16 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.21 | . | +0.16 | +0.19 | +0.21 | +0.18 | +0.17 |
| - | - | soup (us-them) | +0.22 | +0.13 | -0.10 | -0.05 | +0.04 | +0.04 | -0.08 |
| - | - | soup (us-them) ~avg | +0.25 | +0.18 | +0.11 | +0.00 | -0.03 | +0.00 | -0.03 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
