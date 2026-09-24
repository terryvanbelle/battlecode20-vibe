# Which metric starts predicting the result first

18 games, 10 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) ~avg | +0.40 | +0.25 | +0.29 | +0.39 | +0.32 | +0.30 | +0.20 |
| r50 | - | worth (us-them) | +0.48 | +0.27 | +0.48 | +0.43 | +0.34 | +0.32 | +0.22 |
| r50 | - | worth (us-them) ~avg | +0.49 | +0.32 | +0.44 | +0.49 | +0.41 | +0.36 | +0.28 |
| r100 | - | digs (us-them) ~avg | +0.50 | +0.44 | +0.33 | +0.35 | +0.27 | +0.39 | +0.50 |
| r100 | - | dirtDeps (us-them) | +0.47 | +0.47 | +0.37 | +0.33 | +0.26 | +0.41 | +0.43 |
| r100 | - | dirtDeps (us-them) ~avg | +0.47 | +0.47 | +0.37 | +0.32 | +0.22 | +0.32 | +0.42 |
| r100 | - | hqBuried (us-them) [inverted] | +0.39 | +0.32 | +0.39 | +0.33 | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.32 | +0.36 | +0.33 | . | . | . |
| r150 | - | landscapers (us-them) | +0.68 | +0.19 | +0.47 | +0.43 | +0.33 | +0.56 | +0.42 |
| r150 | - | landscapers (us-them) ~avg | +0.62 | +0.19 | +0.40 | +0.41 | +0.32 | +0.49 | +0.59 |
| r150 | - | mines (us-them) | +0.40 | +0.21 | +0.26 | +0.40 | +0.26 | +0.19 | +0.09 |
| r150 | - | pickups (us-them) | +0.50 | . | +0.43 | +0.50 | +0.46 | +0.23 | +0.10 |
| r150 | - | pickups (us-them) ~avg | +0.55 | . | +0.43 | +0.50 | +0.55 | +0.41 | +0.21 |
| r150 | - | robots (us-them) | +0.45 | +0.25 | +0.40 | +0.42 | +0.24 | +0.27 | +0.23 |
| r150 | - | robots (us-them) ~avg | +0.45 | +0.26 | +0.36 | +0.45 | +0.33 | +0.30 | +0.28 |
| r150 | - | spawned (us-them) | +0.40 | +0.21 | +0.36 | +0.40 | +0.20 | +0.24 | +0.17 |
| r150 | - | units (us-them) | +0.42 | +0.18 | +0.36 | +0.38 | +0.16 | +0.23 | +0.25 |
| r200 | - | cov (us-them) ~avg | +0.40 | +0.02 | +0.31 | +0.28 | +0.35 | +0.40 | +0.38 |
| r200 | - | died (us-them) [inverted] | +0.47 | +0.27 | +0.47 | +0.08 | +0.29 | +0.28 | +0.35 |
| r200 | - | died (us-them) [inverted] ~avg | +0.47 | +0.27 | +0.38 | +0.47 | +0.36 | +0.32 | +0.39 |
| r200 | - | digs (us-them) | +0.54 | +0.44 | +0.35 | +0.38 | +0.32 | +0.48 | +0.49 |
| r200 | - | spawned (us-them) ~avg | +0.41 | +0.23 | +0.33 | +0.41 | +0.29 | +0.26 | +0.22 |
| r200 | - | units (us-them) ~avg | +0.38 | +0.16 | +0.31 | +0.38 | +0.26 | +0.26 | +0.30 |
| r400 | - | cov (us-them) | +0.44 | +0.03 | +0.29 | +0.20 | +0.44 | +0.37 | +0.27 |
| r400 | - | vaporators (us-them) | +0.34 | -0.06 | +0.04 | +0.21 | +0.31 | +0.31 | +0.17 |
| r550 | - | netguns (us-them) | +0.49 | . | . | . | -0.19 | +0.27 | +0.49 |
| r600 | - | vaporators (us-them) ~avg | +0.30 | -0.06 | +0.02 | +0.12 | +0.25 | +0.30 | +0.23 |
| r750 | - | netguns (us-them) ~avg | +0.41 | . | . | . | -0.19 | +0.03 | +0.39 |
| - | r150 | aba (us-them) [inverted] | -0.50 | +0.02 | -0.36 | -0.38 | -0.48 | -0.48 | -0.36 |
| - | r200 | aba (us-them) [inverted] ~avg | -0.53 | +0.05 | -0.34 | -0.39 | -0.44 | -0.49 | -0.51 |
| - | - | drones (us-them) | +0.18 | +0.06 | -0.09 | -0.08 | -0.01 | +0.17 | +0.14 |
| - | - | drones (us-them) ~avg | +0.13 | +0.06 | +0.03 | +0.00 | +0.06 | +0.07 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.17 | +0.04 | -0.03 | +0.01 | -0.04 | -0.07 | +0.15 |
| - | - | miners (us-them) ~avg | +0.08 | +0.05 | -0.00 | +0.07 | -0.05 | -0.04 | +0.06 |
| - | - | moves (us-them) | +0.26 | -0.07 | +0.04 | +0.13 | +0.12 | +0.20 | +0.26 |
| - | - | moves (us-them) ~avg | +0.24 | -0.01 | -0.00 | +0.11 | +0.09 | +0.15 | +0.24 |
| - | - | soup (us-them) | +0.46 | -0.09 | +0.09 | +0.09 | +0.08 | -0.14 | +0.08 |
| - | - | soup (us-them) ~avg | +0.22 | +0.02 | -0.04 | +0.18 | +0.22 | -0.01 | -0.01 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
