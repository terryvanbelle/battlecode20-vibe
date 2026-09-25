# Which metric starts predicting the result first

47 games, 23 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) ~avg | +0.39 | +0.27 | +0.33 | +0.25 | +0.14 | +0.09 | +0.22 |
| r50 | - | mines (us-them) | +0.53 | +0.36 | +0.39 | +0.53 | +0.45 | +0.27 | +0.29 |
| r50 | - | robots (us-them) | +0.59 | +0.40 | +0.36 | +0.37 | +0.38 | +0.49 | +0.58 |
| r50 | - | robots (us-them) ~avg | +0.58 | +0.39 | +0.33 | +0.36 | +0.43 | +0.49 | +0.58 |
| r50 | - | spawned (us-them) | +0.57 | +0.39 | +0.35 | +0.42 | +0.37 | +0.46 | +0.57 |
| r50 | - | spawned (us-them) ~avg | +0.57 | +0.38 | +0.31 | +0.36 | +0.41 | +0.47 | +0.55 |
| r50 | - | units (us-them) | +0.52 | +0.37 | +0.32 | +0.23 | +0.19 | +0.35 | +0.49 |
| r50 | - | units (us-them) ~avg | +0.49 | +0.36 | +0.31 | +0.29 | +0.29 | +0.34 | +0.49 |
| r50 | - | worth (us-them) | +0.57 | +0.37 | +0.39 | +0.53 | +0.57 | +0.49 | +0.57 |
| r50 | - | worth (us-them) ~avg | +0.58 | +0.34 | +0.35 | +0.46 | +0.56 | +0.55 | +0.57 |
| r100 | - | mines (us-them) ~avg | +0.50 | +0.32 | +0.34 | +0.43 | +0.50 | +0.43 | +0.35 |
| r150 | - | pickups (us-them) ~avg | +0.40 | +0.25 | +0.34 | +0.15 | +0.25 | +0.24 | +0.18 |
| r300 | - | soup (us-them) ~avg | +0.39 | -0.19 | +0.22 | +0.32 | +0.38 | +0.06 | -0.02 |
| r300 | - | vaporators (us-them) | +0.53 | -0.02 | +0.10 | +0.31 | +0.43 | +0.49 | +0.50 |
| r400 | - | landscapers (us-them) | +0.63 | +0.28 | +0.21 | +0.17 | +0.31 | +0.57 | +0.60 |
| r400 | - | pickups (us-them) | +0.40 | +0.25 | +0.23 | +0.04 | +0.33 | +0.20 | +0.12 |
| r400 | - | vaporators (us-them) ~avg | +0.53 | +0.05 | +0.08 | +0.19 | +0.34 | +0.46 | +0.53 |
| r500 | - | digs (us-them) | +0.68 | +0.14 | +0.11 | +0.10 | +0.20 | +0.43 | +0.61 |
| r500 | - | landscapers (us-them) ~avg | +0.67 | +0.28 | +0.19 | +0.17 | +0.22 | +0.49 | +0.65 |
| r550 | - | dirtDeps (us-them) | +0.67 | +0.10 | +0.07 | +0.08 | +0.16 | +0.39 | +0.58 |
| r650 | - | digs (us-them) ~avg | +0.62 | +0.14 | +0.09 | +0.07 | +0.12 | +0.28 | +0.50 |
| r700 | - | dirtDeps (us-them) ~avg | +0.60 | +0.10 | +0.07 | +0.05 | +0.08 | +0.24 | +0.46 |
| r700 | - | miners (us-them) | +0.40 | +0.19 | +0.34 | +0.07 | +0.02 | +0.08 | +0.38 |
| r750 | - | moves (us-them) | +0.40 | -0.00 | +0.23 | +0.23 | +0.20 | +0.26 | +0.39 |
| r800 | - | moves (us-them) ~avg | +0.40 | -0.07 | +0.14 | +0.20 | +0.20 | +0.26 | +0.33 |
| - | - | aba (us-them) [inverted] | -0.30 | +0.00 | -0.12 | -0.14 | -0.05 | -0.22 | -0.30 |
| - | - | aba (us-them) [inverted] ~avg | -0.25 | -0.01 | -0.10 | -0.10 | -0.07 | -0.18 | -0.25 |
| - | - | cov (us-them) | +0.22 | -0.14 | -0.08 | +0.10 | +0.12 | +0.15 | +0.20 |
| - | - | cov (us-them) ~avg | +0.20 | -0.13 | -0.14 | -0.04 | +0.03 | +0.13 | +0.17 |
| - | - | died (us-them) [inverted] | +0.25 | +0.22 | +0.13 | -0.11 | +0.17 | +0.20 | +0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.22 | +0.22 | +0.20 | +0.03 | +0.10 | +0.18 | +0.19 |
| - | - | drones (us-them) | +0.28 | +0.12 | +0.04 | +0.06 | -0.06 | -0.12 | +0.25 |
| - | - | drones (us-them) ~avg | +0.22 | +0.12 | +0.16 | +0.13 | +0.06 | -0.07 | +0.14 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | +0.19 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.19 | . | . | . | . | . |
| - | - | netguns (us-them) | +0.29 | -0.15 | -0.16 | -0.05 | -0.01 | +0.23 | +0.28 |
| - | - | netguns (us-them) ~avg | +0.22 | -0.15 | -0.16 | -0.11 | -0.06 | +0.03 | +0.19 |
| - | - | soup (us-them) | +0.47 | -0.11 | +0.31 | +0.25 | +0.13 | -0.08 | -0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
