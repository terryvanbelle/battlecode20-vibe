# Which metric starts predicting the result first

45 games, 26 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r250 | - | units (us-them) | +0.43 | +0.17 | +0.10 | +0.41 | +0.43 | +0.37 | +0.27 |
| r300 | - | died (us-them) [inverted] ~avg | +0.34 | -0.16 | -0.01 | +0.32 | +0.29 | +0.18 | +0.15 |
| r300 | - | miners (us-them) | +0.33 | +0.26 | +0.20 | +0.33 | +0.17 | +0.07 | -0.05 |
| r300 | - | miners (us-them) ~avg | +0.33 | +0.11 | +0.16 | +0.32 | +0.30 | +0.21 | +0.12 |
| r300 | - | robots (us-them) | +0.41 | +0.13 | +0.10 | +0.38 | +0.41 | +0.37 | +0.26 |
| r350 | - | landscapers (us-them) | +0.58 | -0.04 | +0.03 | +0.27 | +0.42 | +0.58 | +0.58 |
| r350 | - | units (us-them) ~avg | +0.42 | +0.03 | +0.07 | +0.28 | +0.39 | +0.42 | +0.38 |
| r400 | - | robots (us-them) ~avg | +0.38 | +0.03 | +0.08 | +0.23 | +0.33 | +0.37 | +0.34 |
| r400 | - | spawned (us-them) | +0.35 | +0.14 | +0.10 | +0.30 | +0.34 | +0.30 | +0.20 |
| r500 | - | digs (us-them) | +0.61 | -0.12 | -0.06 | +0.09 | +0.23 | +0.47 | +0.61 |
| r500 | - | dirtDeps (us-them) | +0.57 | -0.12 | -0.08 | +0.07 | +0.19 | +0.43 | +0.57 |
| r500 | - | landscapers (us-them) ~avg | +0.63 | -0.09 | -0.02 | +0.09 | +0.23 | +0.45 | +0.61 |
| r600 | - | digs (us-them) ~avg | +0.56 | -0.14 | -0.05 | +0.05 | +0.16 | +0.32 | +0.52 |
| r650 | - | dirtDeps (us-them) ~avg | +0.52 | -0.13 | -0.06 | +0.02 | +0.13 | +0.28 | +0.49 |
| - | r300 | aba (us-them) [inverted] | -0.47 | -0.17 | -0.22 | -0.33 | -0.41 | -0.47 | -0.34 |
| - | r350 | aba (us-them) [inverted] ~avg | -0.44 | -0.18 | -0.21 | -0.28 | -0.34 | -0.43 | -0.43 |
| - | - | cov (us-them) | -0.16 | -0.05 | +0.02 | +0.07 | -0.03 | -0.16 | -0.04 |
| - | - | cov (us-them) ~avg | -0.05 | -0.04 | -0.03 | +0.02 | +0.03 | -0.02 | -0.05 |
| - | - | died (us-them) [inverted] | +0.30 | -0.16 | +0.00 | +0.29 | +0.17 | +0.11 | +0.06 |
| - | - | drones (us-them) | -0.18 | +0.10 | -0.09 | +0.00 | -0.11 | -0.09 | -0.18 |
| - | - | drones (us-them) ~avg | -0.13 | +0.10 | +0.04 | +0.03 | -0.01 | -0.09 | -0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.22 | . | . | +0.03 | +0.19 | +0.18 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.28 | +0.28 | +0.24 | +0.24 | +0.19 | +0.22 | +0.25 |
| - | - | mines (us-them) | +0.30 | -0.01 | +0.10 | +0.18 | +0.26 | +0.25 | +0.16 |
| - | - | mines (us-them) ~avg | +0.28 | -0.04 | +0.07 | +0.13 | +0.20 | +0.27 | +0.23 |
| - | - | moves (us-them) | +0.29 | +0.01 | +0.07 | +0.13 | +0.22 | +0.29 | +0.14 |
| - | - | moves (us-them) ~avg | +0.26 | +0.08 | +0.07 | +0.13 | +0.19 | +0.25 | +0.22 |
| - | - | netguns (us-them) | -0.20 | . | +0.12 | -0.14 | -0.12 | -0.16 | -0.15 |
| - | - | netguns (us-them) ~avg | -0.18 | . | +0.12 | -0.09 | -0.12 | -0.15 | -0.18 |
| - | - | pickups (us-them) | +0.28 | +0.23 | +0.14 | +0.01 | -0.10 | +0.04 | +0.16 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.23 | +0.23 | +0.10 | -0.02 | -0.01 | +0.09 |
| - | - | soup (us-them) | +0.26 | -0.05 | +0.09 | -0.03 | -0.24 | -0.19 | -0.06 |
| - | - | soup (us-them) ~avg | -0.22 | -0.01 | +0.07 | +0.13 | -0.03 | -0.18 | -0.15 |
| - | - | spawned (us-them) ~avg | +0.30 | +0.04 | +0.08 | +0.20 | +0.27 | +0.30 | +0.25 |
| - | - | vaporators (us-them) | +0.26 | . | -0.02 | -0.18 | +0.08 | +0.18 | +0.10 |
| - | - | vaporators (us-them) ~avg | +0.17 | . | +0.01 | -0.11 | -0.08 | +0.03 | +0.09 |
| - | - | worth (us-them) | +0.28 | +0.01 | +0.07 | +0.16 | +0.23 | +0.28 | +0.16 |
| - | - | worth (us-them) ~avg | +0.21 | -0.02 | +0.06 | +0.10 | +0.15 | +0.20 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
