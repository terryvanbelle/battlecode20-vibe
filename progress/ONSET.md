# Which metric starts predicting the result first

40 games, 26 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | vaporators (us-them) ~avg | +0.37 | +0.37 | +0.23 | +0.17 | +0.13 | +0.20 | +0.24 |
| r200 | - | worth (us-them) | +0.32 | +0.03 | +0.32 | +0.24 | +0.18 | +0.24 | +0.23 |
| r250 | - | soup (us-them) | +0.39 | +0.06 | +0.14 | +0.38 | +0.24 | -0.12 | -0.14 |
| r250 | - | soup (us-them) ~avg | +0.33 | +0.12 | +0.23 | +0.33 | +0.25 | +0.07 | -0.02 |
| r350 | - | units (us-them) | +0.53 | -0.14 | +0.10 | +0.26 | +0.28 | +0.32 | +0.47 |
| r450 | - | landscapers (us-them) | +0.49 | -0.29 | +0.11 | +0.21 | +0.29 | +0.35 | +0.47 |
| r450 | - | robots (us-them) ~avg | +0.52 | -0.12 | +0.14 | +0.20 | +0.29 | +0.37 | +0.44 |
| r500 | - | digs (us-them) | +0.53 | -0.12 | +0.03 | +0.14 | +0.25 | +0.39 | +0.46 |
| r500 | - | spawned (us-them) ~avg | +0.42 | -0.10 | +0.13 | +0.18 | +0.27 | +0.34 | +0.37 |
| r500 | - | units (us-them) ~avg | +0.51 | -0.12 | +0.04 | +0.08 | +0.22 | +0.37 | +0.45 |
| r550 | - | dirtDeps (us-them) | +0.50 | -0.12 | +0.04 | +0.10 | +0.19 | +0.35 | +0.41 |
| r550 | - | worth (us-them) ~avg | +0.33 | +0.02 | +0.26 | +0.27 | +0.28 | +0.31 | +0.33 |
| r600 | - | landscapers (us-them) ~avg | +0.47 | -0.29 | -0.01 | +0.05 | +0.16 | +0.33 | +0.44 |
| r600 | - | robots (us-them) | +0.53 | -0.09 | +0.23 | +0.26 | +0.22 | +0.33 | +0.45 |
| r650 | - | digs (us-them) ~avg | +0.47 | -0.12 | -0.01 | +0.07 | +0.16 | +0.29 | +0.39 |
| r650 | - | spawned (us-them) | +0.46 | -0.08 | +0.19 | +0.24 | +0.20 | +0.28 | +0.39 |
| r850 | - | dirtDeps (us-them) ~avg | +0.41 | -0.11 | +0.01 | +0.03 | +0.11 | +0.23 | +0.33 |
| r900 | - | miners (us-them) | +0.35 | +0.18 | -0.05 | -0.00 | +0.09 | +0.17 | +0.33 |
| - | - | aba (us-them) [inverted] | -0.33 | -0.14 | -0.05 | +0.11 | +0.06 | -0.08 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.33 | -0.21 | -0.09 | +0.05 | +0.08 | -0.02 | -0.09 |
| - | - | cov (us-them) | +0.10 | +0.00 | +0.02 | +0.03 | +0.06 | +0.10 | +0.08 |
| - | - | cov (us-them) ~avg | +0.18 | +0.02 | +0.02 | +0.02 | +0.04 | +0.14 | +0.17 |
| - | - | died (us-them) [inverted] | +0.29 | -0.20 | +0.29 | +0.13 | +0.21 | +0.24 | -0.03 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | -0.20 | +0.14 | +0.14 | +0.17 | +0.24 | +0.07 |
| - | - | drones (us-them) | +0.28 | -0.14 | +0.11 | +0.24 | -0.03 | -0.05 | +0.06 |
| - | - | drones (us-them) ~avg | +0.18 | -0.14 | +0.04 | +0.12 | +0.18 | +0.07 | +0.04 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.20 | +0.20 | . | +0.21 | +0.21 | +0.20 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.20 | +0.20 | +0.21 | +0.21 | +0.21 | +0.20 |
| - | - | miners (us-them) ~avg | +0.30 | +0.07 | +0.07 | +0.05 | +0.08 | +0.16 | +0.25 |
| - | - | mines (us-them) | +0.26 | +0.02 | +0.26 | +0.18 | +0.15 | +0.19 | +0.16 |
| - | - | mines (us-them) ~avg | +0.27 | -0.01 | +0.20 | +0.21 | +0.23 | +0.27 | +0.24 |
| - | - | moves (us-them) | +0.30 | +0.15 | -0.02 | -0.00 | +0.07 | +0.08 | +0.15 |
| - | - | moves (us-them) ~avg | +0.30 | +0.19 | +0.03 | +0.02 | +0.03 | +0.10 | +0.15 |
| - | - | netguns (us-them) | -0.24 | . | -0.17 | -0.13 | -0.18 | -0.11 | -0.11 |
| - | - | netguns (us-them) ~avg | -0.22 | . | -0.15 | -0.19 | -0.16 | -0.13 | -0.14 |
| - | - | pickups (us-them) | -0.18 | -0.18 | -0.05 | -0.01 | +0.04 | +0.11 | +0.07 |
| - | - | pickups (us-them) ~avg | -0.18 | -0.18 | -0.06 | -0.04 | +0.01 | +0.09 | +0.09 |
| - | - | vaporators (us-them) | +0.37 | +0.37 | +0.22 | -0.00 | +0.03 | +0.21 | +0.19 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
