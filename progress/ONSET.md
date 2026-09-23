# Which metric starts predicting the result first

46 games, 40 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.72 | +0.45 | +0.47 | +0.58 | +0.64 | +0.58 | +0.72 |
| r50 | - | mines (us-them) ~avg | +0.77 | +0.46 | +0.47 | +0.51 | +0.58 | +0.62 | +0.77 |
| r50 | - | robots (us-them) | +0.75 | +0.27 | +0.36 | +0.49 | +0.52 | +0.57 | +0.69 |
| r50 | - | robots (us-them) ~avg | +0.72 | +0.35 | +0.37 | +0.41 | +0.48 | +0.53 | +0.72 |
| r50 | - | spawned (us-them) | +0.76 | +0.26 | +0.33 | +0.44 | +0.48 | +0.55 | +0.72 |
| r50 | - | spawned (us-them) ~avg | +0.71 | +0.34 | +0.33 | +0.37 | +0.43 | +0.48 | +0.71 |
| r50 | - | units (us-them) ~avg | +0.67 | +0.27 | +0.31 | +0.33 | +0.37 | +0.37 | +0.66 |
| r50 | - | worth (us-them) | +0.71 | +0.48 | +0.51 | +0.56 | +0.66 | +0.64 | +0.64 |
| r50 | - | worth (us-them) ~avg | +0.76 | +0.49 | +0.53 | +0.55 | +0.62 | +0.67 | +0.74 |
| r100 | - | dirtDeps (us-them) | +0.71 | +0.31 | +0.57 | +0.67 | +0.70 | +0.70 | +0.69 |
| r150 | - | digs (us-them) | +0.71 | +0.27 | +0.52 | +0.67 | +0.71 | +0.71 | +0.70 |
| r150 | - | digs (us-them) ~avg | +0.72 | +0.26 | +0.46 | +0.62 | +0.68 | +0.71 | +0.68 |
| r150 | - | dirtDeps (us-them) ~avg | +0.71 | +0.30 | +0.51 | +0.64 | +0.68 | +0.71 | +0.67 |
| r150 | - | landscapers (us-them) | +0.65 | +0.25 | +0.48 | +0.56 | +0.60 | +0.56 | +0.63 |
| r150 | - | landscapers (us-them) ~avg | +0.62 | +0.24 | +0.49 | +0.53 | +0.59 | +0.60 | +0.62 |
| r150 | - | netguns (us-them) | +0.38 | . | +0.35 | +0.35 | +0.04 | +0.24 | +0.29 |
| r150 | - | netguns (us-them) ~avg | +0.45 | . | +0.35 | +0.35 | +0.28 | +0.32 | +0.37 |
| r200 | - | units (us-them) | +0.76 | +0.21 | +0.33 | +0.36 | +0.40 | +0.41 | +0.76 |
| r300 | - | vaporators (us-them) | +0.52 | . | -0.11 | +0.36 | +0.46 | +0.51 | +0.47 |
| r350 | - | cov (us-them) | +0.67 | -0.11 | +0.12 | +0.28 | +0.36 | +0.34 | +0.62 |
| r400 | - | vaporators (us-them) ~avg | +0.54 | . | -0.18 | +0.14 | +0.38 | +0.50 | +0.52 |
| r550 | - | cov (us-them) ~avg | +0.69 | -0.11 | +0.04 | +0.19 | +0.27 | +0.31 | +0.62 |
| r600 | - | drones (us-them) | +0.69 | -0.11 | -0.09 | -0.09 | +0.03 | +0.37 | +0.65 |
| r650 | - | pickups (us-them) | +0.69 | . | +0.03 | -0.02 | +0.12 | +0.23 | +0.66 |
| r750 | - | drones (us-them) ~avg | +0.68 | -0.11 | -0.10 | -0.10 | -0.07 | +0.00 | +0.55 |
| r750 | - | moves (us-them) | +0.53 | -0.10 | -0.04 | +0.04 | +0.06 | +0.11 | +0.52 |
| r750 | - | pickups (us-them) ~avg | +0.71 | . | +0.04 | -0.02 | +0.02 | +0.09 | +0.60 |
| r900 | - | moves (us-them) ~avg | +0.45 | -0.13 | -0.08 | -0.02 | +0.00 | +0.06 | +0.31 |
| r1200 | - | soup (us-them) | +0.35 | +0.25 | +0.28 | +0.01 | +0.09 | +0.07 | +0.08 |
| - | - | aba (us-them) [inverted] | -0.28 | -0.13 | +0.01 | +0.03 | -0.02 | -0.08 | -0.07 |
| - | - | aba (us-them) [inverted] ~avg | +0.22 | -0.10 | +0.01 | +0.03 | +0.02 | -0.04 | +0.09 |
| - | - | died (us-them) [inverted] | +0.19 | +0.12 | +0.19 | -0.00 | +0.07 | +0.06 | +0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.17 | +0.12 | +0.17 | +0.09 | +0.07 | +0.07 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.10 | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.10 | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.32 | +0.13 | +0.18 | +0.14 | +0.11 | +0.02 | +0.15 |
| - | - | miners (us-them) ~avg | +0.34 | +0.23 | +0.18 | +0.18 | +0.16 | +0.11 | +0.18 |
| - | - | soup (us-them) ~avg | -0.28 | +0.16 | +0.25 | +0.21 | +0.17 | +0.18 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
