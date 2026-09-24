# Which metric starts predicting the result first

19 games, 6 wins. Noise floor about 0.46; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.55 | +0.52 | +0.53 | +0.53 | +0.52 | +0.46 | +0.37 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.61 | +0.51 | +0.49 | +0.52 | +0.54 | +0.51 | +0.53 |
| r50 | - | robots (us-them) | +0.61 | +0.33 | +0.23 | +0.45 | +0.52 | +0.59 | +0.43 |
| r50 | - | robots (us-them) ~avg | +0.60 | +0.43 | +0.31 | +0.49 | +0.51 | +0.60 | +0.48 |
| r50 | - | spawned (us-them) | +0.60 | +0.33 | +0.23 | +0.50 | +0.54 | +0.60 | +0.43 |
| r50 | - | spawned (us-them) ~avg | +0.62 | +0.43 | +0.31 | +0.51 | +0.54 | +0.62 | +0.50 |
| r50 | - | units (us-them) ~avg | +0.62 | +0.31 | +0.24 | +0.40 | +0.45 | +0.60 | +0.52 |
| r100 | - | worth (us-them) | +0.54 | +0.33 | +0.31 | +0.43 | +0.50 | +0.53 | +0.38 |
| r100 | - | worth (us-them) ~avg | +0.53 | +0.33 | +0.33 | +0.49 | +0.50 | +0.53 | +0.43 |
| r150 | - | landscapers (us-them) | +0.62 | +0.29 | +0.29 | +0.26 | +0.34 | +0.61 | +0.56 |
| r150 | - | landscapers (us-them) ~avg | +0.64 | +0.29 | +0.35 | +0.27 | +0.32 | +0.60 | +0.58 |
| r150 | - | soup (us-them) | +0.44 | -0.02 | +0.42 | +0.16 | +0.29 | +0.29 | +0.09 |
| r200 | - | soup (us-them) ~avg | -0.39 | -0.13 | +0.31 | +0.28 | +0.34 | +0.35 | +0.26 |
| r300 | - | drones (us-them) | +0.43 | +0.28 | +0.03 | +0.39 | +0.43 | +0.43 | +0.36 |
| r300 | - | drones (us-them) ~avg | +0.43 | +0.28 | +0.19 | +0.39 | +0.42 | +0.43 | +0.38 |
| r300 | - | mines (us-them) | +0.59 | +0.09 | +0.02 | +0.46 | +0.54 | +0.58 | +0.45 |
| r300 | - | mines (us-them) ~avg | +0.59 | +0.07 | +0.08 | +0.50 | +0.52 | +0.58 | +0.49 |
| r300 | - | pickups (us-them) | +0.48 | . | +0.31 | +0.32 | +0.46 | +0.45 | +0.41 |
| r300 | - | pickups (us-them) ~avg | +0.47 | . | +0.30 | +0.31 | +0.39 | +0.45 | +0.42 |
| r300 | - | units (us-them) | +0.62 | +0.19 | +0.16 | +0.40 | +0.49 | +0.62 | +0.48 |
| r400 | - | miners (us-them) | +0.49 | -0.04 | -0.05 | +0.26 | +0.39 | +0.46 | +0.27 |
| r400 | - | miners (us-them) ~avg | +0.49 | +0.17 | +0.01 | +0.25 | +0.30 | +0.40 | +0.31 |
| r500 | - | cov (us-them) | +0.46 | +0.09 | -0.04 | +0.25 | +0.26 | +0.37 | +0.41 |
| r500 | r950 | died (us-them) [inverted] | +0.45 | . | +0.05 | -0.02 | +0.18 | +0.45 | -0.03 |
| r500 | - | digs (us-them) | +0.67 | +0.14 | +0.25 | +0.25 | +0.26 | +0.48 | +0.65 |
| r500 | - | dirtDeps (us-them) | +0.70 | +0.12 | +0.23 | +0.21 | +0.23 | +0.46 | +0.68 |
| r550 | - | digs (us-them) ~avg | +0.66 | +0.14 | +0.25 | +0.23 | +0.25 | +0.36 | +0.51 |
| r550 | - | moves (us-them) | +0.40 | -0.08 | +0.02 | +0.19 | +0.20 | +0.36 | +0.33 |
| r600 | - | cov (us-them) ~avg | +0.41 | -0.06 | -0.08 | +0.13 | +0.21 | +0.30 | +0.33 |
| r600 | - | died (us-them) [inverted] ~avg | +0.33 | . | +0.08 | +0.02 | +0.08 | +0.31 | +0.17 |
| r600 | - | dirtDeps (us-them) ~avg | +0.70 | +0.12 | +0.23 | +0.19 | +0.21 | +0.33 | +0.50 |
| r950 | - | moves (us-them) ~avg | +0.33 | -0.14 | -0.00 | +0.17 | +0.19 | +0.28 | +0.29 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.16 | . | . | . | . | +0.13 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | . | . | . | . | +0.13 |
| - | - | netguns (us-them) | +0.27 | . | . | . | . | +0.00 | +0.05 |
| - | - | netguns (us-them) ~avg | +0.13 | . | . | . | . | +0.00 | +0.05 |
| - | - | vaporators (us-them) | +0.29 | -0.20 | -0.03 | +0.24 | +0.26 | +0.28 | +0.26 |
| - | - | vaporators (us-them) ~avg | +0.28 | -0.20 | -0.11 | +0.22 | +0.24 | +0.27 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
