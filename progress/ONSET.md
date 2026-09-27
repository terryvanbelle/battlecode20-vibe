# Which metric starts predicting the result first

47 games, 24 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.51 | +0.48 | +0.49 | +0.45 | +0.38 | +0.35 | +0.30 |
| r50 | - | mines (us-them) ~avg | +0.55 | +0.50 | +0.54 | +0.54 | +0.50 | +0.44 | +0.36 |
| r50 | - | robots (us-them) | +0.50 | +0.36 | +0.46 | +0.40 | +0.36 | +0.36 | +0.44 |
| r50 | - | robots (us-them) ~avg | +0.49 | +0.39 | +0.46 | +0.49 | +0.47 | +0.43 | +0.43 |
| r50 | - | spawned (us-them) | +0.43 | +0.35 | +0.43 | +0.35 | +0.29 | +0.27 | +0.29 |
| r50 | - | spawned (us-them) ~avg | +0.45 | +0.39 | +0.42 | +0.45 | +0.42 | +0.35 | +0.29 |
| r50 | - | worth (us-them) | +0.53 | +0.46 | +0.45 | +0.46 | +0.42 | +0.40 | +0.49 |
| r50 | - | worth (us-them) ~avg | +0.55 | +0.48 | +0.51 | +0.55 | +0.51 | +0.46 | +0.47 |
| r100 | - | miners (us-them) ~avg | +0.32 | +0.30 | +0.29 | +0.26 | +0.17 | +0.14 | +0.15 |
| r100 | - | units (us-them) | +0.42 | +0.39 | +0.37 | +0.30 | +0.30 | +0.28 | +0.34 |
| r100 | - | units (us-them) ~avg | +0.41 | +0.38 | +0.38 | +0.41 | +0.40 | +0.37 | +0.36 |
| r150 | - | pickups (us-them) | +0.60 | +0.25 | +0.38 | +0.52 | +0.58 | +0.51 | +0.52 |
| r150 | - | pickups (us-them) ~avg | +0.61 | +0.25 | +0.36 | +0.46 | +0.54 | +0.61 | +0.59 |
| r200 | - | cov (us-them) | +0.39 | +0.04 | +0.30 | +0.36 | +0.28 | +0.10 | +0.02 |
| r250 | - | moves (us-them) | -0.38 | +0.02 | +0.29 | +0.35 | +0.31 | +0.25 | +0.23 |
| r300 | - | digs (us-them) | +0.68 | +0.30 | +0.26 | +0.36 | +0.45 | +0.48 | +0.54 |
| r300 | - | digs (us-them) ~avg | +0.60 | +0.30 | +0.24 | +0.34 | +0.41 | +0.48 | +0.52 |
| r300 | - | dirtDeps (us-them) | +0.66 | +0.21 | +0.21 | +0.34 | +0.42 | +0.46 | +0.52 |
| r300 | - | dirtDeps (us-them) ~avg | +0.58 | +0.21 | +0.17 | +0.31 | +0.38 | +0.45 | +0.50 |
| r300 | - | landscapers (us-them) ~avg | +0.43 | +0.11 | +0.24 | +0.32 | +0.37 | +0.40 | +0.37 |
| r300 | - | moves (us-them) ~avg | -0.38 | -0.08 | +0.22 | +0.31 | +0.32 | +0.29 | +0.22 |
| r350 | - | landscapers (us-them) | +0.44 | +0.11 | +0.30 | +0.28 | +0.34 | +0.27 | +0.30 |
| r400 | - | netguns (us-them) | +0.48 | . | +0.16 | +0.25 | +0.34 | +0.42 | +0.44 |
| r450 | - | died (us-them) [inverted] ~avg | +0.53 | +0.15 | +0.23 | +0.24 | +0.29 | +0.40 | +0.48 |
| r450 | - | netguns (us-them) ~avg | +0.45 | . | +0.16 | +0.27 | +0.29 | +0.42 | +0.43 |
| r500 | - | died (us-them) [inverted] | +0.46 | +0.15 | +0.18 | +0.20 | +0.30 | +0.44 | +0.45 |
| r550 | - | vaporators (us-them) | +0.45 | +0.16 | +0.11 | +0.30 | +0.22 | +0.38 | +0.44 |
| r650 | - | vaporators (us-them) ~avg | +0.42 | +0.16 | +0.09 | +0.18 | +0.21 | +0.30 | +0.42 |
| r800 | - | miners (us-them) | +0.35 | +0.29 | +0.19 | +0.07 | -0.02 | +0.15 | +0.33 |
| - | - | aba (us-them) [inverted] | +0.24 | +0.09 | -0.02 | -0.04 | +0.04 | +0.07 | +0.08 |
| - | - | aba (us-them) [inverted] ~avg | +0.24 | +0.15 | +0.05 | -0.01 | +0.01 | +0.04 | +0.08 |
| - | - | cov (us-them) ~avg | +0.29 | -0.02 | +0.15 | +0.28 | +0.29 | +0.20 | +0.13 |
| - | - | drones (us-them) | +0.27 | +0.17 | +0.13 | +0.07 | +0.03 | +0.14 | +0.18 |
| - | - | drones (us-them) ~avg | +0.17 | +0.17 | +0.14 | +0.07 | +0.07 | +0.07 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.18 | +0.16 | +0.16 | -0.16 | -0.16 | -0.16 | -0.15 |
| - | r150 | hqBuried (us-them) [inverted] ~avg | -0.34 | +0.16 | -0.31 | -0.23 | -0.20 | -0.18 | -0.16 |
| - | - | soup (us-them) | +0.28 | +0.11 | -0.10 | -0.12 | +0.01 | -0.06 | +0.11 |
| - | - | soup (us-them) ~avg | +0.24 | +0.12 | +0.07 | +0.05 | -0.02 | -0.04 | +0.03 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
