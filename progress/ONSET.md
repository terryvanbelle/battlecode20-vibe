# Which metric starts predicting the result first

29 games, 14 wins. Noise floor about 0.37; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | robots (us-them) | +0.67 | -0.05 | +0.33 | +0.31 | +0.39 | +0.62 | +0.57 |
| r200 | - | spawned (us-them) | +0.69 | -0.04 | +0.32 | +0.29 | +0.36 | +0.55 | +0.63 |
| r400 | - | robots (us-them) ~avg | +0.53 | -0.14 | +0.16 | +0.24 | +0.31 | +0.42 | +0.52 |
| r400 | - | vaporators (us-them) | +0.44 | +0.13 | +0.01 | +0.25 | +0.37 | +0.44 | +0.12 |
| r450 | - | spawned (us-them) ~avg | +0.60 | -0.12 | +0.16 | +0.23 | +0.29 | +0.37 | +0.49 |
| r450 | - | units (us-them) | +0.59 | -0.11 | +0.18 | +0.18 | +0.18 | +0.55 | +0.56 |
| r450 | - | vaporators (us-them) ~avg | +0.39 | +0.13 | +0.08 | +0.15 | +0.26 | +0.36 | +0.35 |
| r450 | - | worth (us-them) | +0.49 | +0.08 | +0.29 | +0.17 | +0.26 | +0.43 | +0.46 |
| r500 | r950 | died (us-them) [inverted] | -0.45 | -0.17 | +0.21 | -0.02 | +0.00 | +0.40 | -0.17 |
| r500 | - | hqBuried (us-them) [inverted] | +0.33 | +0.17 | +0.27 | +0.30 | . | . | +0.23 |
| r500 | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.17 | +0.27 | +0.21 | +0.21 | +0.31 | +0.31 |
| r500 | - | landscapers (us-them) | +0.56 | +0.05 | +0.25 | +0.16 | +0.18 | +0.54 | +0.53 |
| r600 | - | digs (us-them) | +0.37 | -0.27 | +0.04 | +0.16 | +0.18 | +0.33 | +0.37 |
| r600 | - | landscapers (us-them) ~avg | +0.44 | +0.03 | +0.18 | +0.17 | +0.18 | +0.31 | +0.43 |
| r650 | - | dirtDeps (us-them) | +0.35 | -0.01 | +0.11 | +0.13 | +0.15 | +0.29 | +0.35 |
| r650 | - | mines (us-them) | +0.58 | +0.05 | +0.18 | -0.00 | +0.15 | +0.23 | +0.50 |
| r650 | - | units (us-them) ~avg | +0.43 | -0.23 | +0.01 | +0.09 | +0.12 | +0.28 | +0.41 |
| r650 | - | worth (us-them) ~avg | +0.48 | +0.08 | +0.23 | +0.19 | +0.21 | +0.28 | +0.42 |
| r700 | - | drones (us-them) | +0.31 | -0.02 | -0.04 | -0.12 | -0.01 | +0.24 | +0.29 |
| r750 | r100 | moves (us-them) | +0.51 | -0.51 | -0.29 | +0.00 | +0.04 | +0.19 | +0.47 |
| r950 | - | mines (us-them) ~avg | +0.42 | +0.04 | +0.12 | +0.05 | +0.07 | +0.06 | +0.28 |
| - | r950 | aba (us-them) [inverted] | -0.51 | +0.14 | +0.23 | +0.00 | -0.07 | -0.18 | -0.30 |
| - | r1100 | aba (us-them) [inverted] ~avg | -0.34 | +0.13 | +0.23 | +0.13 | -0.01 | -0.13 | -0.13 |
| - | - | cov (us-them) | +0.26 | -0.01 | -0.02 | -0.01 | -0.05 | +0.08 | +0.21 |
| - | - | cov (us-them) ~avg | -0.14 | -0.07 | -0.03 | -0.02 | -0.03 | -0.07 | -0.04 |
| - | - | died (us-them) [inverted] ~avg | -0.29 | -0.17 | +0.09 | +0.06 | -0.02 | +0.18 | -0.04 |
| - | - | digs (us-them) ~avg | +0.30 | -0.27 | -0.03 | +0.07 | +0.13 | +0.21 | +0.25 |
| - | - | dirtDeps (us-them) ~avg | +0.26 | -0.01 | +0.06 | +0.07 | +0.11 | +0.17 | +0.22 |
| - | - | drones (us-them) ~avg | -0.09 | -0.02 | -0.02 | -0.08 | -0.09 | -0.00 | +0.04 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | -0.34 | -0.22 | -0.22 | +0.10 | -0.00 | +0.24 | +0.12 |
| - | r50 | miners (us-them) ~avg | -0.34 | -0.33 | -0.26 | -0.10 | -0.05 | -0.10 | +0.03 |
| - | r100 | moves (us-them) ~avg | -0.46 | -0.46 | -0.39 | -0.16 | -0.05 | +0.01 | +0.17 |
| - | - | netguns (us-them) | +0.24 | . | +0.24 | -0.11 | -0.08 | -0.01 | -0.10 |
| - | - | netguns (us-them) ~avg | +0.24 | . | +0.24 | -0.02 | -0.07 | -0.05 | -0.06 |
| - | - | pickups (us-them) | +0.28 | +0.17 | +0.05 | -0.15 | -0.06 | +0.19 | +0.26 |
| - | - | pickups (us-them) ~avg | +0.17 | +0.17 | +0.06 | -0.07 | -0.08 | +0.05 | +0.14 |
| - | r400 | soup (us-them) | -0.45 | -0.02 | -0.22 | -0.31 | -0.35 | -0.32 | -0.00 |
| - | r400 | soup (us-them) ~avg | -0.37 | +0.05 | -0.18 | -0.28 | -0.30 | -0.34 | -0.14 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
