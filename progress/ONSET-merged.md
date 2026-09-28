# Which metric starts predicting the result first -- every recorded block of r4s3 merged

231 games, 120 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.43 | +0.41 | +0.35 | +0.36 | +0.38 | +0.40 | +0.39 |
| r50 | - | mines (us-them) ~avg | +0.43 | +0.43 | +0.39 | +0.41 | +0.42 | +0.43 | +0.43 |
| r100 | - | robots (us-them) ~avg | +0.51 | +0.32 | +0.30 | +0.34 | +0.39 | +0.46 | +0.51 |
| r100 | - | spawned (us-them) ~avg | +0.46 | +0.33 | +0.31 | +0.33 | +0.36 | +0.42 | +0.46 |
| r100 | - | units (us-them) ~avg | +0.49 | +0.34 | +0.27 | +0.28 | +0.32 | +0.40 | +0.49 |
| r100 | - | worth (us-them) | +0.49 | +0.32 | +0.34 | +0.40 | +0.42 | +0.48 | +0.44 |
| r100 | - | worth (us-them) ~avg | +0.49 | +0.33 | +0.34 | +0.40 | +0.44 | +0.48 | +0.48 |
| r250 | - | robots (us-them) | +0.52 | +0.33 | +0.29 | +0.36 | +0.40 | +0.48 | +0.49 |
| r300 | - | spawned (us-them) | +0.46 | +0.34 | +0.29 | +0.33 | +0.35 | +0.44 | +0.46 |
| r350 | - | units (us-them) | +0.51 | +0.34 | +0.24 | +0.29 | +0.33 | +0.43 | +0.48 |
| r450 | - | landscapers (us-them) | +0.53 | +0.16 | +0.16 | +0.20 | +0.30 | +0.47 | +0.52 |
| r500 | - | digs (us-them) | +0.59 | +0.20 | +0.14 | +0.20 | +0.27 | +0.37 | +0.50 |
| r500 | - | landscapers (us-them) ~avg | +0.54 | +0.15 | +0.11 | +0.16 | +0.24 | +0.40 | +0.52 |
| r500 | - | vaporators (us-them) | +0.38 | +0.05 | +0.08 | +0.12 | +0.19 | +0.36 | +0.35 |
| r550 | - | dirtDeps (us-them) | +0.59 | +0.14 | +0.12 | +0.18 | +0.25 | +0.36 | +0.49 |
| r650 | - | digs (us-them) ~avg | +0.53 | +0.19 | +0.13 | +0.17 | +0.22 | +0.30 | +0.42 |
| r650 | - | dirtDeps (us-them) ~avg | +0.52 | +0.13 | +0.09 | +0.15 | +0.20 | +0.28 | +0.41 |
| r650 | - | vaporators (us-them) ~avg | +0.35 | +0.03 | +0.06 | +0.08 | +0.14 | +0.28 | +0.35 |
| r700 | - | moves (us-them) | +0.36 | +0.17 | +0.25 | +0.22 | +0.21 | +0.27 | +0.36 |
| r750 | - | miners (us-them) | +0.30 | +0.21 | +0.17 | +0.21 | +0.16 | +0.25 | +0.30 |
| r850 | - | miners (us-them) ~avg | +0.31 | +0.26 | +0.26 | +0.25 | +0.23 | +0.25 | +0.31 |
| r850 | - | moves (us-them) ~avg | +0.35 | +0.11 | +0.27 | +0.24 | +0.23 | +0.24 | +0.32 |
| - | - | aba (us-them) [inverted] | -0.24 | -0.00 | -0.06 | -0.07 | -0.08 | -0.14 | -0.24 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | -0.00 | -0.05 | -0.09 | -0.09 | -0.13 | -0.19 |
| - | - | cov (us-them) | +0.22 | -0.10 | +0.07 | +0.16 | +0.19 | +0.18 | +0.16 |
| - | - | cov (us-them) ~avg | -0.22 | -0.16 | -0.01 | +0.06 | +0.11 | +0.17 | +0.19 |
| - | - | died (us-them) [inverted] | +0.25 | -0.09 | +0.09 | +0.09 | +0.16 | +0.23 | +0.19 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | -0.09 | +0.04 | +0.06 | +0.11 | +0.20 | +0.24 |
| - | - | drones (us-them) | +0.24 | +0.16 | +0.16 | +0.14 | +0.12 | +0.09 | +0.22 |
| - | - | drones (us-them) ~avg | +0.20 | +0.18 | +0.20 | +0.16 | +0.15 | +0.12 | +0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.26 | +0.23 | +0.18 | +0.20 | +0.13 | +0.12 | +0.12 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.12 | +0.12 | +0.09 | +0.09 | +0.12 |
| - | - | netguns (us-them) | +0.28 | . | +0.01 | +0.06 | +0.10 | +0.22 | +0.28 |
| - | - | netguns (us-them) ~avg | +0.23 | . | -0.01 | +0.06 | +0.07 | +0.14 | +0.23 |
| - | - | pickups (us-them) | +0.30 | +0.01 | +0.22 | +0.19 | +0.23 | +0.22 | +0.28 |
| - | - | pickups (us-them) ~avg | +0.29 | +0.02 | +0.20 | +0.20 | +0.25 | +0.23 | +0.29 |
| - | - | soup (us-them) | +0.14 | -0.11 | -0.05 | +0.08 | -0.02 | -0.06 | -0.03 |
| - | - | soup (us-them) ~avg | +0.13 | -0.04 | -0.02 | +0.09 | +0.08 | +0.02 | +0.04 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
