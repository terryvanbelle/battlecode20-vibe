# Which metric starts predicting the result first

18 games, 7 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.55 | +0.26 | +0.35 | +0.50 | +0.50 | +0.53 | +0.54 |
| r50 | - | mines (us-them) ~avg | +0.57 | +0.31 | +0.31 | +0.40 | +0.48 | +0.53 | +0.56 |
| r50 | - | worth (us-them) | +0.58 | +0.34 | +0.25 | +0.42 | +0.51 | +0.56 | +0.53 |
| r50 | - | worth (us-them) ~avg | +0.56 | +0.37 | +0.23 | +0.31 | +0.41 | +0.51 | +0.56 |
| r100 | - | pickups (us-them) ~avg | +0.48 | +0.36 | +0.26 | +0.30 | +0.35 | +0.41 | +0.48 |
| r200 | - | netguns (us-them) | +0.54 | . | +0.33 | +0.39 | +0.53 | +0.41 | +0.54 |
| r200 | - | netguns (us-them) ~avg | +0.53 | . | +0.33 | +0.36 | +0.43 | +0.45 | +0.52 |
| r300 | - | pickups (us-them) | +0.51 | +0.36 | +0.23 | +0.35 | +0.38 | +0.43 | +0.51 |
| r300 | - | vaporators (us-them) | +0.49 | -0.25 | +0.01 | +0.31 | +0.40 | +0.49 | +0.43 |
| r350 | - | cov (us-them) | +0.30 | -0.13 | +0.21 | +0.25 | +0.28 | +0.20 | +0.20 |
| r400 | - | landscapers (us-them) | +0.73 | +0.15 | +0.01 | -0.01 | +0.30 | +0.59 | +0.62 |
| r400 | - | robots (us-them) | +0.62 | +0.25 | +0.14 | +0.17 | +0.38 | +0.50 | +0.57 |
| r400 | - | spawned (us-them) | +0.59 | +0.25 | +0.12 | +0.14 | +0.38 | +0.48 | +0.59 |
| r450 | - | robots (us-them) ~avg | +0.55 | +0.28 | +0.14 | +0.14 | +0.25 | +0.42 | +0.55 |
| r450 | - | units (us-them) | +0.63 | +0.14 | +0.06 | +0.05 | +0.27 | +0.41 | +0.60 |
| r450 | - | vaporators (us-them) ~avg | +0.50 | -0.25 | -0.04 | +0.07 | +0.23 | +0.43 | +0.49 |
| r500 | - | spawned (us-them) ~avg | +0.53 | +0.28 | +0.13 | +0.13 | +0.24 | +0.40 | +0.53 |
| r600 | - | landscapers (us-them) ~avg | +0.63 | +0.15 | -0.07 | -0.06 | +0.07 | +0.32 | +0.59 |
| r600 | - | units (us-them) ~avg | +0.57 | +0.11 | +0.05 | +0.03 | +0.12 | +0.32 | +0.51 |
| r650 | - | digs (us-them) | +0.57 | +0.40 | -0.12 | -0.05 | +0.06 | +0.27 | +0.51 |
| r650 | - | dirtDeps (us-them) | +0.57 | +0.31 | -0.20 | -0.07 | +0.05 | +0.28 | +0.52 |
| r700 | - | moves (us-them) | +0.43 | -0.04 | +0.04 | +0.01 | +0.04 | +0.25 | +0.38 |
| r750 | - | miners (us-them) | +0.38 | -0.04 | +0.19 | +0.04 | +0.01 | +0.16 | +0.32 |
| r850 | - | digs (us-them) ~avg | +0.45 | +0.40 | -0.07 | -0.07 | +0.00 | +0.15 | +0.34 |
| r850 | - | dirtDeps (us-them) ~avg | +0.46 | +0.31 | -0.18 | -0.12 | -0.02 | +0.14 | +0.36 |
| r950 | - | moves (us-them) ~avg | +0.40 | -0.11 | +0.10 | +0.06 | +0.04 | +0.14 | +0.30 |
| - | - | aba (us-them) [inverted] | +0.30 | +0.08 | +0.30 | +0.10 | +0.15 | -0.14 | -0.23 |
| - | - | aba (us-them) [inverted] ~avg | +0.28 | +0.12 | +0.28 | +0.16 | +0.16 | +0.05 | -0.14 |
| - | - | cov (us-them) ~avg | +0.30 | -0.13 | +0.18 | +0.25 | +0.29 | +0.27 | +0.22 |
| - | - | died (us-them) [inverted] | +0.29 | . | +0.29 | +0.13 | -0.01 | +0.16 | +0.21 |
| - | - | died (us-them) [inverted] ~avg | +0.29 | . | +0.29 | +0.21 | +0.10 | +0.19 | +0.25 |
| - | - | drones (us-them) | +0.32 | +0.32 | -0.02 | +0.15 | +0.06 | +0.01 | +0.17 |
| - | - | drones (us-them) ~avg | +0.32 | +0.32 | +0.17 | +0.18 | +0.15 | +0.05 | +0.06 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.28 | -0.03 | +0.14 | +0.15 | +0.07 | +0.11 | +0.21 |
| - | - | soup (us-them) | +0.30 | +0.16 | +0.21 | +0.30 | -0.23 | -0.23 | -0.01 |
| - | - | soup (us-them) ~avg | +0.29 | +0.06 | +0.27 | +0.28 | +0.25 | +0.09 | -0.00 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
