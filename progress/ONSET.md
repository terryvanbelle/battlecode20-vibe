# Which metric starts predicting the result first

18 games, 5 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.58 | +0.58 | +0.05 | +0.03 | +0.13 | +0.12 | +0.39 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.56 | +0.56 | +0.35 | +0.23 | +0.11 | +0.14 | +0.23 |
| r50 | - | miners (us-them) ~avg | +0.39 | +0.33 | +0.22 | +0.19 | +0.13 | +0.11 | +0.18 |
| r50 | - | robots (us-them) ~avg | +0.44 | +0.30 | +0.25 | +0.39 | +0.40 | +0.42 | +0.39 |
| r50 | - | spawned (us-them) ~avg | +0.44 | +0.29 | +0.27 | +0.43 | +0.44 | +0.43 | +0.40 |
| r50 | - | units (us-them) ~avg | +0.42 | +0.31 | +0.22 | +0.36 | +0.38 | +0.41 | +0.35 |
| r150 | - | cov (us-them) | +0.48 | +0.04 | +0.31 | +0.32 | +0.27 | +0.19 | +0.05 |
| r150 | - | cov (us-them) ~avg | +0.51 | +0.01 | +0.42 | +0.42 | +0.38 | +0.34 | +0.21 |
| r150 | - | mines (us-them) | +0.45 | +0.27 | +0.40 | +0.42 | +0.35 | +0.30 | +0.22 |
| r150 | - | worth (us-them) | +0.49 | +0.24 | +0.37 | +0.47 | +0.40 | +0.39 | +0.47 |
| r200 | - | landscapers (us-them) | +0.56 | +0.10 | +0.40 | +0.56 | +0.48 | +0.45 | +0.16 |
| r200 | - | mines (us-them) ~avg | +0.50 | +0.29 | +0.35 | +0.42 | +0.39 | +0.36 | +0.37 |
| r200 | - | robots (us-them) | +0.46 | +0.22 | +0.31 | +0.45 | +0.42 | +0.39 | +0.33 |
| r200 | - | spawned (us-them) | +0.51 | +0.21 | +0.35 | +0.50 | +0.44 | +0.39 | +0.36 |
| r200 | - | worth (us-them) ~avg | +0.51 | +0.26 | +0.35 | +0.43 | +0.42 | +0.41 | +0.47 |
| r250 | - | landscapers (us-them) ~avg | +0.51 | +0.10 | +0.24 | +0.44 | +0.48 | +0.50 | +0.42 |
| r250 | - | units (us-them) | +0.44 | +0.20 | +0.28 | +0.43 | +0.43 | +0.37 | +0.21 |
| r300 | - | digs (us-them) | +0.45 | +0.24 | +0.17 | +0.32 | +0.32 | +0.37 | +0.40 |
| r300 | - | dirtDeps (us-them) | +0.50 | +0.18 | +0.17 | +0.30 | +0.29 | +0.38 | +0.44 |
| r400 | - | pickups (us-them) | +0.33 | +0.15 | +0.14 | +0.23 | +0.30 | +0.33 | +0.19 |
| r500 | - | digs (us-them) ~avg | +0.45 | +0.24 | +0.15 | +0.27 | +0.28 | +0.33 | +0.35 |
| r500 | - | pickups (us-them) ~avg | +0.33 | +0.15 | +0.15 | +0.19 | +0.27 | +0.33 | +0.26 |
| r550 | - | dirtDeps (us-them) ~avg | +0.49 | +0.18 | +0.16 | +0.24 | +0.25 | +0.33 | +0.37 |
| r550 | - | vaporators (us-them) | +0.51 | +0.22 | +0.12 | +0.18 | +0.19 | +0.36 | +0.48 |
| r650 | - | vaporators (us-them) ~avg | +0.48 | +0.22 | +0.14 | +0.13 | +0.17 | +0.26 | +0.46 |
| r750 | - | soup (us-them) ~avg | +0.50 | -0.23 | +0.10 | -0.01 | -0.01 | -0.05 | +0.28 |
| r1000 | - | drones (us-them) | +0.36 | -0.07 | -0.09 | +0.07 | +0.21 | -0.03 | +0.21 |
| r1000 | - | miners (us-them) | +0.39 | +0.21 | +0.08 | +0.05 | +0.04 | +0.09 | +0.13 |
| r1000 | - | soup (us-them) | +0.62 | -0.13 | -0.03 | -0.07 | -0.07 | -0.14 | +0.21 |
| - | - | died (us-them) [inverted] | +0.26 | +0.15 | -0.19 | +0.04 | +0.10 | +0.09 | +0.01 |
| - | - | died (us-them) [inverted] ~avg | +0.15 | +0.15 | -0.12 | -0.02 | -0.01 | +0.10 | +0.06 |
| - | - | drones (us-them) ~avg | +0.28 | -0.07 | -0.12 | -0.01 | +0.10 | +0.07 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.23 | +0.17 | +0.17 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.17 | +0.17 | . | . | . |
| - | - | moves (us-them) | +0.25 | +0.03 | -0.09 | +0.01 | +0.07 | +0.16 | +0.12 |
| - | - | moves (us-them) ~avg | +0.27 | -0.01 | -0.06 | +0.01 | +0.05 | +0.12 | +0.14 |
| - | - | netguns (us-them) | +0.29 | . | . | . | +0.28 | +0.28 | +0.29 |
| - | - | netguns (us-them) ~avg | +0.30 | . | . | . | +0.26 | +0.27 | +0.29 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
