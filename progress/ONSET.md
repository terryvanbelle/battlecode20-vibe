# Which metric starts predicting the result first

23 games, 7 wins. Noise floor about 0.42; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | digs (us-them) ~avg | +0.57 | +0.41 | +0.19 | +0.18 | +0.24 | +0.43 | +0.54 |
| r150 | - | drones (us-them) ~avg | +0.36 | +0.29 | +0.35 | +0.30 | +0.14 | +0.00 | +0.12 |
| r250 | - | mines (us-them) | +0.50 | +0.03 | +0.19 | +0.41 | +0.49 | +0.48 | +0.48 |
| r250 | - | worth (us-them) | +0.52 | +0.10 | +0.23 | +0.50 | +0.49 | +0.48 | +0.44 |
| r300 | - | died (us-them) [inverted] | +0.52 | . | +0.17 | +0.46 | +0.41 | +0.52 | -0.03 |
| r300 | - | died (us-them) [inverted] ~avg | +0.53 | . | +0.17 | +0.44 | +0.49 | +0.52 | +0.24 |
| r300 | - | netguns (us-them) | +0.39 | . | +0.22 | +0.31 | +0.38 | +0.23 | +0.34 |
| r300 | - | robots (us-them) | +0.62 | +0.17 | +0.27 | +0.45 | +0.62 | +0.61 | +0.52 |
| r300 | - | robots (us-them) ~avg | +0.58 | +0.15 | +0.17 | +0.30 | +0.45 | +0.56 | +0.55 |
| r300 | - | spawned (us-them) | +0.56 | +0.17 | +0.26 | +0.35 | +0.54 | +0.54 | +0.55 |
| r300 | - | units (us-them) | +0.59 | +0.17 | +0.18 | +0.30 | +0.57 | +0.59 | +0.48 |
| r300 | - | worth (us-them) ~avg | +0.47 | +0.08 | +0.13 | +0.32 | +0.43 | +0.47 | +0.45 |
| r350 | - | aba (us-them) [inverted] | +0.42 | -0.12 | +0.00 | +0.28 | +0.36 | +0.24 | +0.00 |
| r350 | - | landscapers (us-them) | +0.75 | +0.23 | +0.06 | +0.21 | +0.53 | +0.75 | +0.68 |
| r350 | - | mines (us-them) ~avg | +0.49 | +0.04 | +0.09 | +0.26 | +0.38 | +0.46 | +0.48 |
| r350 | - | netguns (us-them) ~avg | +0.38 | . | +0.22 | +0.29 | +0.33 | +0.35 | +0.35 |
| r350 | - | pickups (us-them) | +0.53 | +0.18 | +0.14 | +0.28 | +0.39 | +0.50 | +0.50 |
| r350 | - | pickups (us-them) ~avg | +0.52 | +0.18 | +0.17 | +0.26 | +0.35 | +0.42 | +0.52 |
| r350 | - | spawned (us-them) ~avg | +0.55 | +0.15 | +0.16 | +0.26 | +0.38 | +0.46 | +0.54 |
| r400 | - | digs (us-them) | +0.59 | +0.41 | +0.14 | +0.18 | +0.31 | +0.49 | +0.55 |
| r400 | - | landscapers (us-them) ~avg | +0.72 | +0.23 | +0.06 | +0.13 | +0.32 | +0.59 | +0.71 |
| r400 | - | units (us-them) ~avg | +0.55 | +0.08 | +0.10 | +0.19 | +0.35 | +0.51 | +0.53 |
| r500 | - | aba (us-them) [inverted] ~avg | +0.39 | -0.02 | -0.03 | +0.15 | +0.25 | +0.37 | +0.20 |
| r500 | - | dirtDeps (us-them) | +0.58 | +0.39 | +0.06 | +0.11 | +0.20 | +0.45 | +0.53 |
| r550 | - | cov (us-them) | +0.47 | +0.17 | -0.05 | +0.05 | +0.19 | +0.32 | +0.44 |
| r550 | - | dirtDeps (us-them) ~avg | +0.55 | +0.39 | +0.11 | +0.10 | +0.15 | +0.36 | +0.50 |
| r800 | - | cov (us-them) ~avg | +0.40 | +0.22 | +0.04 | +0.03 | +0.08 | +0.17 | +0.34 |
| r800 | - | vaporators (us-them) | +0.42 | -0.32 | -0.13 | +0.02 | +0.00 | +0.24 | +0.39 |
| r1000 | r100 | vaporators (us-them) ~avg | +0.38 | -0.32 | -0.19 | -0.13 | -0.05 | +0.09 | +0.25 |
| - | - | drones (us-them) | +0.34 | +0.29 | +0.27 | +0.17 | +0.01 | +0.03 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.24 | +0.24 | . | +0.17 | +0.17 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.24 | +0.24 | +0.17 | +0.17 | +0.19 | +0.17 | +0.16 |
| - | - | miners (us-them) | +0.24 | -0.10 | +0.13 | +0.17 | +0.22 | +0.22 | -0.06 |
| - | - | miners (us-them) ~avg | +0.14 | -0.14 | -0.02 | +0.04 | +0.11 | +0.12 | +0.06 |
| - | - | moves (us-them) | +0.25 | -0.16 | -0.16 | -0.17 | -0.13 | +0.01 | +0.19 |
| - | - | moves (us-them) ~avg | +0.20 | -0.11 | -0.14 | -0.17 | -0.16 | -0.13 | +0.14 |
| - | - | soup (us-them) | +0.35 | -0.14 | -0.02 | +0.16 | +0.21 | -0.06 | -0.23 |
| - | - | soup (us-them) ~avg | +0.32 | -0.20 | -0.00 | +0.22 | +0.25 | +0.14 | -0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
