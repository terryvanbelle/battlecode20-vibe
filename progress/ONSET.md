# Which metric starts predicting the result first

41 games, 19 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | pickups (us-them) ~avg | +0.41 | +0.33 | +0.34 | +0.37 | +0.41 | +0.36 | +0.39 |
| r200 | - | pickups (us-them) | +0.42 | +0.33 | +0.38 | +0.38 | +0.41 | +0.30 | +0.38 |
| r250 | - | landscapers (us-them) | +0.62 | +0.05 | +0.13 | +0.34 | +0.34 | +0.51 | +0.62 |
| r250 | - | worth (us-them) | +0.59 | +0.10 | +0.23 | +0.33 | +0.35 | +0.54 | +0.56 |
| r300 | - | digs (us-them) | +0.51 | +0.18 | +0.23 | +0.32 | +0.34 | +0.47 | +0.51 |
| r300 | - | digs (us-them) ~avg | +0.49 | +0.18 | +0.23 | +0.31 | +0.33 | +0.41 | +0.49 |
| r300 | - | dirtDeps (us-them) | +0.49 | +0.19 | +0.21 | +0.31 | +0.32 | +0.45 | +0.49 |
| r350 | - | dirtDeps (us-them) ~avg | +0.47 | +0.19 | +0.18 | +0.29 | +0.32 | +0.40 | +0.47 |
| r350 | - | worth (us-them) ~avg | +0.56 | +0.09 | +0.15 | +0.28 | +0.32 | +0.46 | +0.54 |
| r400 | - | landscapers (us-them) ~avg | +0.59 | +0.05 | +0.08 | +0.29 | +0.31 | +0.49 | +0.56 |
| r400 | - | mines (us-them) | +0.31 | +0.00 | +0.21 | +0.29 | +0.31 | +0.30 | +0.18 |
| r450 | - | robots (us-them) | +0.61 | -0.04 | +0.06 | +0.22 | +0.25 | +0.54 | +0.58 |
| r500 | - | died (us-them) [inverted] | +0.46 | +0.07 | +0.22 | +0.14 | +0.21 | +0.37 | +0.46 |
| r500 | - | units (us-them) | +0.58 | -0.02 | +0.02 | +0.17 | +0.22 | +0.48 | +0.55 |
| r500 | - | vaporators (us-them) | +0.48 | -0.11 | +0.02 | +0.09 | +0.20 | +0.44 | +0.46 |
| r550 | - | robots (us-them) ~avg | +0.56 | -0.04 | -0.01 | +0.15 | +0.19 | +0.38 | +0.54 |
| r600 | - | died (us-them) [inverted] ~avg | +0.45 | -0.02 | +0.19 | +0.18 | +0.18 | +0.30 | +0.44 |
| r600 | - | mines (us-them) ~avg | +0.30 | +0.05 | +0.14 | +0.23 | +0.28 | +0.30 | +0.20 |
| r600 | - | netguns (us-them) | +0.50 | . | +0.15 | +0.17 | +0.12 | +0.37 | +0.37 |
| r600 | - | spawned (us-them) | +0.46 | -0.04 | -0.03 | +0.16 | +0.17 | +0.35 | +0.42 |
| r600 | - | units (us-them) ~avg | +0.51 | -0.07 | -0.03 | +0.11 | +0.14 | +0.32 | +0.49 |
| r600 | - | vaporators (us-them) ~avg | +0.42 | -0.11 | -0.03 | +0.02 | +0.12 | +0.32 | +0.42 |
| r750 | - | miners (us-them) | +0.37 | -0.14 | -0.21 | -0.15 | -0.17 | -0.01 | +0.36 |
| r850 | - | netguns (us-them) ~avg | +0.46 | . | +0.14 | +0.15 | +0.15 | +0.21 | +0.32 |
| r900 | - | spawned (us-them) ~avg | +0.38 | -0.04 | -0.06 | +0.08 | +0.11 | +0.23 | +0.31 |
| - | - | aba (us-them) [inverted] | -0.25 | +0.20 | +0.10 | +0.20 | +0.08 | -0.04 | -0.25 |
| - | - | aba (us-them) [inverted] ~avg | +0.20 | +0.15 | +0.12 | +0.20 | +0.16 | +0.06 | -0.14 |
| - | - | cov (us-them) | -0.25 | -0.16 | -0.24 | +0.06 | +0.11 | +0.10 | -0.08 |
| - | - | cov (us-them) ~avg | -0.30 | -0.21 | -0.27 | -0.14 | -0.03 | +0.08 | -0.01 |
| - | - | drones (us-them) | +0.23 | +0.22 | +0.17 | +0.04 | +0.11 | +0.07 | +0.18 |
| - | - | drones (us-them) ~avg | +0.22 | +0.22 | +0.22 | +0.13 | +0.12 | +0.07 | +0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.13 | +0.23 | +0.21 | +0.21 | . | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.13 | +0.13 | +0.08 | +0.08 | +0.08 | +0.08 | +0.00 |
| - | - | miners (us-them) ~avg | -0.21 | -0.16 | -0.21 | -0.17 | -0.20 | -0.15 | +0.00 |
| - | r150 | moves (us-them) | -0.41 | -0.28 | -0.41 | -0.22 | -0.14 | -0.00 | +0.17 |
| - | r150 | moves (us-them) ~avg | -0.42 | -0.28 | -0.42 | -0.33 | -0.24 | -0.11 | +0.05 |
| - | - | soup (us-them) | +0.23 | +0.13 | +0.08 | +0.10 | +0.13 | +0.02 | +0.09 |
| - | - | soup (us-them) ~avg | +0.23 | +0.11 | +0.13 | +0.10 | +0.15 | +0.08 | +0.09 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
