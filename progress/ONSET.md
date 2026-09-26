# Which metric starts predicting the result first

41 games, 18 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) ~avg | +0.39 | +0.24 | +0.16 | +0.18 | +0.21 | +0.26 | +0.33 |
| r50 | - | mines (us-them) | +0.51 | +0.45 | +0.49 | +0.48 | +0.42 | +0.49 | +0.48 |
| r50 | - | mines (us-them) ~avg | +0.50 | +0.45 | +0.49 | +0.48 | +0.45 | +0.46 | +0.46 |
| r50 | - | robots (us-them) | +0.51 | +0.42 | +0.42 | +0.47 | +0.44 | +0.50 | +0.50 |
| r50 | - | robots (us-them) ~avg | +0.49 | +0.46 | +0.44 | +0.46 | +0.45 | +0.47 | +0.49 |
| r50 | - | spawned (us-them) | +0.50 | +0.42 | +0.41 | +0.43 | +0.41 | +0.48 | +0.50 |
| r50 | - | spawned (us-them) ~avg | +0.47 | +0.46 | +0.44 | +0.44 | +0.43 | +0.45 | +0.47 |
| r50 | - | units (us-them) | +0.51 | +0.36 | +0.27 | +0.37 | +0.38 | +0.50 | +0.50 |
| r50 | - | units (us-them) ~avg | +0.50 | +0.41 | +0.34 | +0.36 | +0.37 | +0.44 | +0.50 |
| r50 | - | worth (us-them) | +0.51 | +0.51 | +0.48 | +0.50 | +0.40 | +0.43 | +0.37 |
| r50 | - | worth (us-them) ~avg | +0.50 | +0.50 | +0.50 | +0.49 | +0.44 | +0.41 | +0.39 |
| r100 | - | digs (us-them) | +0.58 | +0.42 | +0.14 | +0.17 | +0.25 | +0.38 | +0.48 |
| r100 | - | digs (us-them) ~avg | +0.51 | +0.42 | +0.19 | +0.17 | +0.19 | +0.30 | +0.42 |
| r100 | - | dirtDeps (us-them) ~avg | +0.48 | +0.44 | +0.17 | +0.15 | +0.14 | +0.24 | +0.37 |
| r150 | - | drones (us-them) ~avg | +0.35 | +0.27 | +0.25 | +0.30 | +0.31 | +0.29 | +0.28 |
| r300 | - | died (us-them) [inverted] | +0.45 | . | +0.13 | +0.33 | +0.40 | +0.38 | +0.13 |
| r300 | - | drones (us-them) | +0.37 | +0.27 | +0.06 | +0.31 | +0.30 | +0.25 | +0.26 |
| r300 | - | pickups (us-them) | +0.55 | +0.04 | +0.21 | +0.33 | +0.50 | +0.52 | +0.50 |
| r300 | - | pickups (us-them) ~avg | +0.54 | +0.09 | +0.22 | +0.30 | +0.43 | +0.51 | +0.54 |
| r350 | - | died (us-them) [inverted] ~avg | +0.36 | . | +0.14 | +0.29 | +0.33 | +0.36 | +0.34 |
| r450 | - | landscapers (us-them) | +0.64 | +0.31 | +0.24 | +0.23 | +0.24 | +0.50 | +0.64 |
| r550 | - | landscapers (us-them) ~avg | +0.61 | +0.33 | +0.25 | +0.24 | +0.22 | +0.37 | +0.57 |
| r550 | - | miners (us-them) | +0.39 | +0.03 | +0.12 | +0.20 | +0.23 | +0.33 | +0.37 |
| r600 | - | dirtDeps (us-them) | +0.56 | +0.44 | +0.13 | +0.15 | +0.19 | +0.32 | +0.43 |
| - | - | aba (us-them) [inverted] | +0.20 | +0.17 | +0.17 | +0.08 | +0.07 | +0.02 | -0.18 |
| - | - | aba (us-them) [inverted] ~avg | +0.19 | +0.14 | +0.18 | +0.12 | +0.10 | +0.07 | -0.04 |
| - | r50 | cov (us-them) | -0.48 | -0.40 | -0.25 | -0.00 | +0.19 | +0.18 | +0.18 |
| - | r50 | cov (us-them) ~avg | -0.49 | -0.44 | -0.35 | -0.22 | -0.09 | +0.06 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.14 | +0.14 | +0.14 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.14 | +0.18 | +0.14 | +0.14 | +0.14 | +0.14 |
| - | r50 | moves (us-them) | -0.42 | -0.31 | -0.07 | +0.10 | +0.16 | +0.18 | +0.29 |
| - | r50 | moves (us-them) ~avg | -0.43 | -0.36 | -0.15 | +0.03 | +0.13 | +0.15 | +0.21 |
| - | - | netguns (us-them) | +0.28 | . | +0.18 | +0.25 | +0.28 | +0.28 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.29 | . | +0.14 | +0.19 | +0.23 | +0.28 | +0.26 |
| - | - | soup (us-them) | +0.32 | +0.02 | +0.10 | +0.31 | +0.29 | +0.05 | -0.08 |
| - | - | soup (us-them) ~avg | +0.25 | -0.06 | +0.21 | +0.21 | +0.23 | +0.14 | +0.00 |
| - | - | vaporators (us-them) | +0.29 | -0.28 | +0.11 | +0.16 | +0.17 | +0.29 | +0.20 |
| - | - | vaporators (us-them) ~avg | -0.28 | -0.28 | -0.06 | +0.10 | +0.16 | +0.22 | +0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
