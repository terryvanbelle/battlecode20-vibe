# Which metric starts predicting the result first

46 games, 20 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.49 | +0.40 | +0.41 | +0.44 | +0.45 | +0.49 | +0.46 |
| r50 | - | mines (us-them) ~avg | +0.49 | +0.45 | +0.42 | +0.40 | +0.42 | +0.48 | +0.49 |
| r100 | - | worth (us-them) ~avg | +0.47 | +0.31 | +0.31 | +0.40 | +0.42 | +0.44 | +0.46 |
| r200 | - | worth (us-them) | +0.47 | +0.31 | +0.34 | +0.40 | +0.41 | +0.46 | +0.46 |
| r250 | - | robots (us-them) | +0.47 | +0.24 | +0.20 | +0.35 | +0.42 | +0.47 | +0.47 |
| r250 | - | spawned (us-them) | +0.48 | +0.23 | +0.24 | +0.40 | +0.45 | +0.47 | +0.46 |
| r300 | - | drones (us-them) | +0.43 | +0.13 | +0.11 | +0.36 | +0.43 | +0.35 | +0.38 |
| r300 | - | drones (us-them) ~avg | +0.38 | +0.12 | +0.08 | +0.31 | +0.35 | +0.35 | +0.38 |
| r350 | - | cov (us-them) | +0.51 | -0.05 | +0.16 | +0.27 | +0.39 | +0.44 | +0.50 |
| r350 | - | robots (us-them) ~avg | +0.47 | +0.30 | +0.15 | +0.23 | +0.33 | +0.44 | +0.46 |
| r350 | - | spawned (us-them) ~avg | +0.48 | +0.30 | +0.20 | +0.27 | +0.35 | +0.46 | +0.47 |
| r350 | - | units (us-them) | +0.48 | +0.17 | +0.13 | +0.21 | +0.35 | +0.47 | +0.45 |
| r350 | - | vaporators (us-them) | +0.46 | +0.15 | +0.15 | +0.27 | +0.30 | +0.41 | +0.42 |
| r450 | - | landscapers (us-them) | +0.52 | -0.11 | -0.03 | +0.01 | +0.21 | +0.39 | +0.42 |
| r450 | - | pickups (us-them) | +0.43 | -0.04 | -0.06 | +0.09 | +0.27 | +0.39 | +0.41 |
| r450 | - | vaporators (us-them) ~avg | +0.44 | +0.15 | +0.17 | +0.24 | +0.28 | +0.36 | +0.42 |
| r500 | - | cov (us-them) ~avg | +0.48 | -0.12 | +0.07 | +0.14 | +0.23 | +0.37 | +0.45 |
| r500 | - | miners (us-them) | +0.39 | +0.23 | +0.23 | +0.16 | +0.21 | +0.33 | +0.37 |
| r500 | - | units (us-them) ~avg | +0.45 | +0.25 | +0.09 | +0.14 | +0.22 | +0.40 | +0.45 |
| r550 | - | digs (us-them) | +0.68 | -0.23 | -0.14 | +0.03 | +0.17 | +0.36 | +0.58 |
| r650 | - | landscapers (us-them) ~avg | +0.50 | -0.11 | -0.16 | -0.12 | -0.01 | +0.29 | +0.41 |
| r650 | - | pickups (us-them) ~avg | +0.41 | -0.07 | -0.10 | +0.00 | +0.13 | +0.30 | +0.39 |
| r700 | - | dirtDeps (us-them) | +0.58 | -0.23 | -0.12 | +0.01 | +0.11 | +0.26 | +0.50 |
| r700 | - | miners (us-them) ~avg | +0.35 | +0.29 | +0.26 | +0.21 | +0.20 | +0.28 | +0.35 |
| r700 | - | moves (us-them) | +0.39 | +0.05 | +0.08 | +0.13 | +0.14 | +0.26 | +0.36 |
| r750 | - | digs (us-them) ~avg | +0.58 | -0.26 | -0.20 | -0.04 | +0.07 | +0.22 | +0.43 |
| r850 | - | dirtDeps (us-them) ~avg | +0.48 | -0.23 | -0.14 | -0.02 | +0.04 | +0.15 | +0.33 |
| r900 | - | moves (us-them) ~avg | +0.36 | -0.05 | +0.08 | +0.10 | +0.09 | +0.18 | +0.31 |
| - | - | aba (us-them) [inverted] | -0.28 | -0.28 | +0.01 | -0.06 | +0.04 | +0.14 | +0.06 |
| - | - | aba (us-them) [inverted] ~avg | -0.30 | -0.30 | -0.02 | -0.04 | +0.01 | +0.09 | +0.11 |
| - | - | died (us-them) [inverted] | +0.25 | +0.08 | -0.06 | -0.02 | +0.11 | +0.21 | +0.25 |
| - | - | died (us-them) [inverted] ~avg | +0.21 | -0.04 | -0.14 | -0.06 | -0.00 | +0.12 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.17 | +0.13 | +0.16 | +0.01 | +0.11 | +0.11 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.15 | +0.13 | +0.05 | -0.00 | -0.15 | -0.14 | -0.14 |
| - | - | netguns (us-them) | +0.16 | . | +0.03 | +0.09 | -0.01 | +0.06 | +0.11 |
| - | - | netguns (us-them) ~avg | +0.10 | . | +0.03 | +0.05 | +0.02 | +0.05 | +0.07 |
| - | - | soup (us-them) | -0.28 | -0.03 | +0.03 | -0.04 | -0.08 | +0.00 | -0.04 |
| - | - | soup (us-them) ~avg | +0.13 | -0.04 | +0.08 | +0.09 | +0.10 | +0.11 | +0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
