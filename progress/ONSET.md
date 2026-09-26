# Which metric starts predicting the result first

41 games, 25 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | died (us-them) [inverted] | +0.40 | +0.16 | +0.29 | +0.17 | +0.16 | +0.19 | +0.32 |
| r150 | - | died (us-them) [inverted] ~avg | +0.37 | +0.12 | +0.32 | +0.31 | +0.24 | +0.19 | +0.35 |
| r150 | - | landscapers (us-them) | +0.72 | +0.09 | +0.35 | +0.13 | +0.11 | +0.37 | +0.66 |
| r150 | - | landscapers (us-them) ~avg | +0.69 | +0.09 | +0.35 | +0.22 | +0.18 | +0.27 | +0.47 |
| r150 | - | pickups (us-them) | +0.42 | +0.13 | +0.38 | +0.32 | +0.22 | +0.22 | +0.39 |
| r150 | - | pickups (us-them) ~avg | +0.44 | +0.13 | +0.39 | +0.39 | +0.34 | +0.24 | +0.44 |
| r150 | - | robots (us-them) | +0.68 | +0.21 | +0.39 | +0.34 | +0.34 | +0.40 | +0.53 |
| r150 | - | robots (us-them) ~avg | +0.65 | +0.16 | +0.35 | +0.37 | +0.37 | +0.41 | +0.52 |
| r150 | - | units (us-them) | +0.62 | +0.22 | +0.40 | +0.24 | +0.24 | +0.35 | +0.49 |
| r150 | - | units (us-them) ~avg | +0.60 | +0.15 | +0.36 | +0.33 | +0.31 | +0.35 | +0.49 |
| r150 | - | worth (us-them) | +0.64 | +0.18 | +0.44 | +0.39 | +0.38 | +0.38 | +0.44 |
| r150 | - | worth (us-them) ~avg | +0.63 | +0.23 | +0.39 | +0.39 | +0.41 | +0.41 | +0.46 |
| r200 | - | digs (us-them) | +0.72 | +0.28 | +0.33 | +0.32 | +0.34 | +0.35 | +0.47 |
| r200 | - | digs (us-them) ~avg | +0.56 | +0.28 | +0.30 | +0.30 | +0.33 | +0.34 | +0.42 |
| r200 | - | mines (us-them) | +0.35 | +0.20 | +0.31 | +0.33 | +0.32 | +0.26 | +0.30 |
| r200 | - | mines (us-them) ~avg | +0.33 | +0.24 | +0.31 | +0.30 | +0.33 | +0.32 | +0.33 |
| r300 | - | drones (us-them) | +0.32 | +0.24 | +0.24 | +0.32 | +0.28 | +0.03 | +0.06 |
| r300 | - | drones (us-them) ~avg | +0.32 | +0.24 | +0.27 | +0.32 | +0.32 | +0.21 | +0.17 |
| r300 | - | vaporators (us-them) | +0.55 | -0.03 | +0.13 | +0.33 | +0.38 | +0.31 | +0.40 |
| r400 | - | vaporators (us-them) ~avg | +0.57 | -0.03 | +0.02 | +0.21 | +0.32 | +0.34 | +0.38 |
| r450 | - | spawned (us-them) | +0.61 | +0.20 | +0.27 | +0.26 | +0.29 | +0.34 | +0.46 |
| r450 | - | spawned (us-them) ~avg | +0.56 | +0.15 | +0.27 | +0.26 | +0.28 | +0.34 | +0.41 |
| r550 | - | netguns (us-them) | +0.51 | . | -0.10 | -0.07 | +0.05 | +0.38 | +0.50 |
| r600 | - | dirtDeps (us-them) | +0.71 | +0.02 | +0.19 | +0.22 | +0.28 | +0.32 | +0.45 |
| r700 | - | dirtDeps (us-them) ~avg | +0.54 | +0.02 | +0.13 | +0.15 | +0.23 | +0.28 | +0.38 |
| r750 | - | netguns (us-them) ~avg | +0.41 | . | -0.10 | -0.14 | -0.04 | +0.17 | +0.38 |
| r900 | - | moves (us-them) | +0.43 | +0.12 | +0.08 | +0.14 | +0.07 | +0.14 | +0.30 |
| r1100 | - | moves (us-them) ~avg | +0.33 | +0.06 | +0.09 | +0.17 | +0.12 | +0.12 | +0.23 |
| - | r850 | aba (us-them) [inverted] | -0.52 | +0.04 | +0.22 | +0.13 | -0.08 | -0.17 | -0.39 |
| - | r1000 | aba (us-them) [inverted] ~avg | -0.41 | +0.07 | +0.21 | +0.17 | +0.02 | -0.09 | -0.25 |
| - | - | cov (us-them) | -0.28 | -0.23 | -0.28 | -0.16 | -0.09 | +0.11 | +0.12 |
| - | r200 | cov (us-them) ~avg | -0.31 | -0.26 | -0.31 | -0.28 | -0.21 | -0.06 | +0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.22 | +0.16 | +0.20 | +0.00 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.29 | +0.22 | +0.08 | -0.29 | -0.28 | -0.28 | -0.27 |
| - | - | miners (us-them) | +0.28 | +0.10 | +0.15 | +0.17 | +0.17 | +0.20 | +0.25 |
| - | - | miners (us-them) ~avg | +0.26 | +0.07 | +0.13 | +0.23 | +0.22 | +0.21 | +0.26 |
| - | r450 | soup (us-them) | +0.47 | -0.09 | +0.24 | -0.13 | -0.14 | -0.27 | -0.44 |
| - | r600 | soup (us-them) ~avg | +0.47 | +0.09 | +0.21 | +0.10 | -0.01 | -0.31 | -0.35 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
