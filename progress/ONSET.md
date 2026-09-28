# Which metric starts predicting the result first

46 games, 18 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | drones (us-them) ~avg | +0.47 | +0.23 | +0.31 | +0.33 | +0.38 | +0.45 | +0.47 |
| r200 | - | pickups (us-them) | +0.47 | +0.21 | +0.30 | +0.47 | +0.40 | +0.43 | +0.41 |
| r200 | - | pickups (us-them) ~avg | +0.51 | +0.21 | +0.31 | +0.40 | +0.45 | +0.45 | +0.46 |
| r200 | - | robots (us-them) | +0.51 | +0.03 | +0.32 | +0.33 | +0.41 | +0.50 | +0.47 |
| r200 | - | worth (us-them) | +0.44 | +0.14 | +0.35 | +0.35 | +0.42 | +0.43 | +0.36 |
| r250 | - | digs (us-them) | +0.69 | +0.06 | +0.25 | +0.32 | +0.34 | +0.50 | +0.57 |
| r250 | - | drones (us-them) | +0.49 | +0.23 | +0.29 | +0.31 | +0.42 | +0.43 | +0.49 |
| r300 | - | died (us-them) [inverted] | +0.45 | +0.18 | +0.28 | +0.44 | +0.45 | +0.35 | +0.17 |
| r300 | - | died (us-them) [inverted] ~avg | +0.42 | +0.18 | +0.28 | +0.34 | +0.40 | +0.40 | +0.32 |
| r300 | - | dirtDeps (us-them) | +0.68 | +0.11 | +0.21 | +0.31 | +0.34 | +0.49 | +0.56 |
| r300 | - | units (us-them) | +0.58 | -0.02 | +0.28 | +0.32 | +0.39 | +0.58 | +0.56 |
| r300 | - | worth (us-them) ~avg | +0.43 | +0.12 | +0.29 | +0.31 | +0.37 | +0.42 | +0.40 |
| r350 | - | digs (us-them) ~avg | +0.61 | +0.07 | +0.20 | +0.30 | +0.33 | +0.45 | +0.52 |
| r350 | - | dirtDeps (us-them) ~avg | +0.60 | +0.13 | +0.19 | +0.29 | +0.32 | +0.43 | +0.51 |
| r350 | - | mines (us-them) | +0.45 | +0.10 | +0.28 | +0.29 | +0.37 | +0.45 | +0.43 |
| r400 | - | landscapers (us-them) | +0.59 | -0.20 | +0.30 | +0.21 | +0.31 | +0.59 | +0.52 |
| r400 | - | mines (us-them) ~avg | +0.43 | +0.10 | +0.23 | +0.25 | +0.31 | +0.40 | +0.43 |
| r400 | - | robots (us-them) ~avg | +0.46 | +0.03 | +0.21 | +0.25 | +0.33 | +0.44 | +0.45 |
| r400 | - | spawned (us-them) | +0.47 | -0.00 | +0.26 | +0.21 | +0.32 | +0.46 | +0.47 |
| r400 | - | units (us-them) ~avg | +0.54 | -0.05 | +0.14 | +0.23 | +0.31 | +0.49 | +0.52 |
| r500 | - | landscapers (us-them) ~avg | +0.55 | -0.19 | +0.15 | +0.17 | +0.24 | +0.44 | +0.46 |
| r500 | - | moves (us-them) | +0.48 | +0.07 | -0.01 | +0.10 | +0.21 | +0.38 | +0.48 |
| r500 | - | spawned (us-them) ~avg | +0.42 | +0.01 | +0.15 | +0.17 | +0.24 | +0.38 | +0.42 |
| r550 | - | netguns (us-them) | +0.43 | . | . | +0.20 | +0.26 | +0.41 | +0.30 |
| r600 | - | cov (us-them) | +0.37 | -0.02 | -0.05 | +0.06 | +0.11 | +0.31 | +0.33 |
| r600 | - | moves (us-them) ~avg | +0.44 | +0.07 | -0.01 | +0.07 | +0.14 | +0.32 | +0.44 |
| r600 | - | netguns (us-them) ~avg | +0.41 | . | . | +0.20 | +0.22 | +0.34 | +0.40 |
| - | r850 | aba (us-them) [inverted] | -0.43 | -0.02 | +0.20 | +0.19 | +0.05 | -0.17 | -0.35 |
| - | r950 | aba (us-them) [inverted] ~avg | -0.39 | -0.02 | +0.16 | +0.20 | +0.14 | -0.14 | -0.25 |
| - | - | cov (us-them) ~avg | +0.30 | +0.02 | -0.11 | -0.07 | +0.01 | +0.23 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.31 | +0.19 | +0.31 | +0.12 | -0.05 | +0.21 | +0.22 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.32 | +0.19 | +0.32 | +0.13 | +0.06 | -0.07 | -0.02 |
| - | - | miners (us-them) | +0.26 | +0.08 | -0.01 | +0.18 | +0.17 | +0.17 | +0.24 |
| - | - | miners (us-them) ~avg | +0.23 | +0.01 | -0.05 | +0.05 | +0.11 | +0.17 | +0.23 |
| - | - | soup (us-them) | -0.27 | +0.05 | -0.16 | +0.10 | -0.04 | +0.09 | -0.11 |
| - | - | soup (us-them) ~avg | -0.13 | +0.01 | -0.13 | +0.05 | +0.04 | +0.07 | +0.04 |
| - | - | vaporators (us-them) | +0.27 | +0.24 | +0.10 | +0.10 | +0.23 | +0.20 | +0.20 |
| - | - | vaporators (us-them) ~avg | +0.25 | +0.24 | +0.12 | +0.09 | +0.15 | +0.19 | +0.24 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
