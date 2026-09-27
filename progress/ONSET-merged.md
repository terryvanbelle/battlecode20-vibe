# Which metric starts predicting the result first -- every recorded block of r1s13 merged

229 games, 116 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.49 | +0.38 | +0.49 | +0.48 | +0.48 | +0.48 | +0.45 |
| r50 | - | mines (us-them) ~avg | +0.52 | +0.37 | +0.45 | +0.50 | +0.51 | +0.52 | +0.48 |
| r100 | - | units (us-them) | +0.51 | +0.31 | +0.34 | +0.35 | +0.43 | +0.47 | +0.48 |
| r100 | - | worth (us-them) | +0.53 | +0.33 | +0.49 | +0.51 | +0.50 | +0.53 | +0.51 |
| r100 | - | worth (us-them) ~avg | +0.55 | +0.32 | +0.45 | +0.53 | +0.53 | +0.55 | +0.53 |
| r150 | - | hqBuried (us-them) [inverted] | +0.32 | +0.28 | +0.28 | +0.12 | +0.08 | +0.03 | +0.04 |
| r200 | - | miners (us-them) ~avg | +0.30 | +0.20 | +0.30 | +0.26 | +0.25 | +0.23 | +0.25 |
| r200 | - | robots (us-them) | +0.55 | +0.28 | +0.41 | +0.44 | +0.49 | +0.53 | +0.53 |
| r200 | - | robots (us-them) ~avg | +0.57 | +0.29 | +0.39 | +0.45 | +0.49 | +0.54 | +0.57 |
| r200 | - | spawned (us-them) | +0.52 | +0.28 | +0.40 | +0.43 | +0.47 | +0.50 | +0.52 |
| r200 | - | spawned (us-them) ~avg | +0.54 | +0.29 | +0.38 | +0.44 | +0.47 | +0.51 | +0.53 |
| r200 | - | units (us-them) ~avg | +0.53 | +0.30 | +0.34 | +0.38 | +0.41 | +0.48 | +0.53 |
| r250 | - | landscapers (us-them) | +0.51 | +0.20 | +0.30 | +0.33 | +0.40 | +0.44 | +0.50 |
| r300 | - | landscapers (us-them) ~avg | +0.57 | +0.18 | +0.24 | +0.33 | +0.38 | +0.47 | +0.53 |
| r350 | - | digs (us-them) | +0.56 | +0.17 | +0.21 | +0.28 | +0.34 | +0.43 | +0.51 |
| r400 | - | digs (us-them) ~avg | +0.52 | +0.17 | +0.19 | +0.27 | +0.31 | +0.39 | +0.46 |
| r400 | - | dirtDeps (us-them) | +0.55 | +0.22 | +0.19 | +0.26 | +0.32 | +0.41 | +0.49 |
| r450 | - | dirtDeps (us-them) ~avg | +0.51 | +0.21 | +0.17 | +0.23 | +0.29 | +0.36 | +0.44 |
| r500 | - | cov (us-them) | +0.31 | -0.01 | +0.21 | +0.28 | +0.28 | +0.30 | +0.27 |
| r500 | - | vaporators (us-them) | +0.42 | +0.12 | +0.16 | +0.24 | +0.21 | +0.37 | +0.39 |
| r600 | - | moves (us-them) | +0.38 | -0.02 | +0.21 | +0.23 | +0.23 | +0.31 | +0.35 |
| r650 | - | cov (us-them) ~avg | +0.30 | -0.01 | +0.11 | +0.20 | +0.24 | +0.30 | +0.29 |
| r650 | - | vaporators (us-them) ~avg | +0.38 | +0.12 | +0.12 | +0.18 | +0.19 | +0.30 | +0.38 |
| r750 | - | pickups (us-them) | +0.33 | +0.09 | +0.09 | +0.16 | +0.19 | +0.25 | +0.32 |
| r850 | - | moves (us-them) ~avg | +0.35 | -0.04 | +0.17 | +0.22 | +0.23 | +0.28 | +0.32 |
| r900 | - | pickups (us-them) ~avg | +0.32 | +0.09 | +0.10 | +0.15 | +0.18 | +0.23 | +0.31 |
| r1050 | - | drones (us-them) | +0.36 | +0.14 | +0.02 | +0.04 | +0.08 | +0.18 | +0.27 |
| - | - | aba (us-them) [inverted] | -0.19 | +0.06 | -0.01 | -0.03 | +0.02 | -0.09 | -0.17 |
| - | - | aba (us-them) [inverted] ~avg | +0.15 | +0.10 | +0.02 | -0.03 | -0.01 | -0.05 | -0.11 |
| - | - | died (us-them) [inverted] | +0.20 | -0.03 | +0.15 | +0.14 | +0.17 | +0.13 | +0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.18 | -0.02 | +0.16 | +0.16 | +0.17 | +0.17 | +0.15 |
| - | - | drones (us-them) ~avg | +0.28 | +0.14 | +0.06 | +0.05 | +0.06 | +0.13 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.27 | +0.21 | +0.05 | +0.04 | +0.01 | +0.03 |
| - | - | miners (us-them) | +0.29 | +0.15 | +0.24 | +0.17 | +0.19 | +0.22 | +0.27 |
| - | - | netguns (us-them) | +0.23 | . | +0.01 | -0.05 | -0.02 | +0.20 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.15 | . | +0.00 | -0.05 | -0.04 | +0.04 | +0.12 |
| - | - | soup (us-them) | +0.11 | -0.05 | -0.05 | -0.07 | -0.01 | -0.01 | -0.06 |
| - | - | soup (us-them) ~avg | -0.07 | -0.03 | +0.01 | -0.03 | -0.01 | -0.03 | -0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
