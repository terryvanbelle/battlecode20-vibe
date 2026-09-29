# Which metric starts predicting the result first

47 games, 27 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.40 | +0.27 | +0.33 | +0.33 | +0.23 | +0.10 | +0.02 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.39 | +0.29 | +0.36 | +0.37 | +0.30 | +0.20 | +0.11 |
| r50 | - | robots (us-them) | +0.50 | +0.28 | +0.43 | +0.47 | +0.50 | +0.49 | +0.46 |
| r50 | - | robots (us-them) ~avg | +0.50 | +0.34 | +0.43 | +0.47 | +0.49 | +0.50 | +0.49 |
| r50 | - | spawned (us-them) | +0.49 | +0.28 | +0.42 | +0.41 | +0.44 | +0.49 | +0.49 |
| r50 | - | spawned (us-them) ~avg | +0.50 | +0.34 | +0.41 | +0.44 | +0.45 | +0.48 | +0.49 |
| r50 | - | units (us-them) | +0.54 | +0.25 | +0.43 | +0.53 | +0.53 | +0.53 | +0.47 |
| r50 | - | units (us-them) ~avg | +0.56 | +0.33 | +0.41 | +0.48 | +0.51 | +0.55 | +0.55 |
| r50 | - | worth (us-them) | +0.46 | +0.29 | +0.38 | +0.44 | +0.46 | +0.46 | +0.40 |
| r50 | - | worth (us-them) ~avg | +0.47 | +0.31 | +0.39 | +0.45 | +0.46 | +0.47 | +0.45 |
| r100 | - | digs (us-them) | +0.63 | +0.38 | +0.31 | +0.35 | +0.36 | +0.47 | +0.58 |
| r100 | - | digs (us-them) ~avg | +0.61 | +0.38 | +0.29 | +0.33 | +0.34 | +0.41 | +0.53 |
| r100 | - | dirtDeps (us-them) | +0.64 | +0.31 | +0.32 | +0.34 | +0.36 | +0.47 | +0.59 |
| r100 | - | dirtDeps (us-them) ~avg | +0.61 | +0.31 | +0.30 | +0.31 | +0.33 | +0.41 | +0.54 |
| r100 | - | hqBuried (us-them) [inverted] | +0.32 | +0.31 | +0.24 | -0.04 | -0.22 | -0.19 | -0.19 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.31 | +0.26 | +0.00 | -0.18 | -0.19 | -0.19 |
| r100 | - | mines (us-them) | +0.54 | +0.32 | +0.32 | +0.41 | +0.47 | +0.54 | +0.48 |
| r150 | - | landscapers (us-them) | +0.48 | +0.22 | +0.37 | +0.34 | +0.36 | +0.47 | +0.47 |
| r150 | - | mines (us-them) ~avg | +0.52 | +0.29 | +0.33 | +0.42 | +0.44 | +0.51 | +0.50 |
| r150 | - | pickups (us-them) ~avg | +0.47 | +0.19 | +0.33 | +0.37 | +0.37 | +0.41 | +0.45 |
| r200 | - | landscapers (us-them) ~avg | +0.52 | +0.22 | +0.32 | +0.31 | +0.34 | +0.44 | +0.49 |
| r250 | - | pickups (us-them) | +0.49 | +0.19 | +0.28 | +0.32 | +0.39 | +0.42 | +0.48 |
| r300 | - | died (us-them) [inverted] | +0.33 | -0.05 | +0.17 | +0.33 | +0.27 | +0.05 | +0.04 |
| r300 | - | drones (us-them) | +0.45 | +0.14 | +0.19 | +0.37 | +0.44 | +0.45 | +0.38 |
| r300 | - | drones (us-them) ~avg | +0.48 | +0.14 | +0.24 | +0.41 | +0.42 | +0.45 | +0.45 |
| r300 | - | miners (us-them) | +0.35 | +0.06 | +0.19 | +0.35 | +0.24 | +0.02 | +0.00 |
| r300 | - | miners (us-them) ~avg | +0.36 | +0.21 | +0.29 | +0.36 | +0.33 | +0.23 | +0.14 |
| r300 | - | soup (us-them) | +0.37 | -0.11 | +0.04 | +0.37 | +0.17 | -0.00 | -0.13 |
| r350 | - | netguns (us-them) ~avg | +0.45 | . | +0.12 | +0.28 | +0.31 | +0.39 | +0.42 |
| r350 | - | soup (us-them) ~avg | +0.31 | -0.13 | -0.04 | +0.26 | +0.30 | +0.18 | -0.01 |
| r400 | - | netguns (us-them) | +0.46 | . | +0.25 | +0.28 | +0.30 | +0.40 | +0.38 |
| r500 | - | vaporators (us-them) | +0.36 | -0.05 | -0.25 | -0.15 | +0.19 | +0.33 | +0.30 |
| r650 | - | moves (us-them) | +0.50 | -0.01 | +0.11 | +0.15 | +0.17 | +0.28 | +0.50 |
| r700 | - | moves (us-them) ~avg | +0.48 | +0.01 | +0.06 | +0.15 | +0.14 | +0.22 | +0.43 |
| r950 | - | vaporators (us-them) ~avg | +0.32 | -0.05 | -0.20 | -0.20 | -0.04 | +0.21 | +0.30 |
| - | - | cov (us-them) | +0.29 | +0.14 | +0.23 | +0.19 | +0.20 | +0.29 | +0.22 |
| - | - | cov (us-them) ~avg | +0.27 | +0.13 | +0.17 | +0.18 | +0.18 | +0.24 | +0.26 |
| - | - | died (us-them) [inverted] ~avg | +0.29 | -0.01 | +0.20 | +0.29 | +0.29 | +0.19 | +0.14 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
