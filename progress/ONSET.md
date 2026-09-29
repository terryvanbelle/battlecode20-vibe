# Which metric starts predicting the result first

46 games, 24 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.47 | +0.28 | +0.35 | +0.47 | +0.43 | +0.35 | +0.34 |
| r50 | - | mines (us-them) ~avg | +0.45 | +0.32 | +0.31 | +0.41 | +0.45 | +0.43 | +0.38 |
| r150 | - | vaporators (us-them) | +0.45 | +0.09 | +0.32 | +0.37 | +0.45 | +0.40 | +0.27 |
| r150 | - | vaporators (us-them) ~avg | +0.42 | +0.15 | +0.33 | +0.37 | +0.39 | +0.42 | +0.35 |
| r200 | - | worth (us-them) | +0.55 | +0.24 | +0.36 | +0.51 | +0.54 | +0.47 | +0.38 |
| r200 | - | worth (us-them) ~avg | +0.52 | +0.25 | +0.32 | +0.44 | +0.51 | +0.51 | +0.43 |
| r250 | - | drones (us-them) | +0.46 | +0.26 | +0.23 | +0.37 | +0.46 | +0.36 | +0.41 |
| r250 | - | miners (us-them) | +0.41 | +0.11 | +0.26 | +0.34 | +0.33 | +0.35 | +0.41 |
| r250 | - | miners (us-them) ~avg | +0.42 | +0.13 | +0.25 | +0.34 | +0.36 | +0.39 | +0.42 |
| r250 | - | robots (us-them) | +0.57 | +0.17 | +0.24 | +0.52 | +0.54 | +0.52 | +0.45 |
| r250 | - | spawned (us-them) | +0.50 | +0.16 | +0.22 | +0.42 | +0.40 | +0.47 | +0.44 |
| r250 | - | units (us-them) | +0.57 | +0.24 | +0.15 | +0.41 | +0.46 | +0.52 | +0.51 |
| r300 | - | drones (us-them) ~avg | +0.42 | +0.23 | +0.23 | +0.32 | +0.41 | +0.41 | +0.40 |
| r300 | - | robots (us-them) ~avg | +0.55 | +0.20 | +0.17 | +0.34 | +0.48 | +0.55 | +0.49 |
| r350 | - | cov (us-them) | +0.42 | -0.01 | +0.27 | +0.28 | +0.38 | +0.38 | +0.34 |
| r350 | - | died (us-them) [inverted] | +0.45 | +0.21 | +0.10 | +0.24 | +0.41 | +0.42 | +0.33 |
| r350 | - | spawned (us-them) ~avg | +0.46 | +0.19 | +0.14 | +0.28 | +0.37 | +0.46 | +0.45 |
| r350 | - | units (us-them) ~avg | +0.53 | +0.22 | +0.12 | +0.26 | +0.38 | +0.52 | +0.52 |
| r400 | - | cov (us-them) ~avg | +0.41 | -0.04 | +0.21 | +0.24 | +0.31 | +0.39 | +0.40 |
| r400 | - | died (us-them) [inverted] ~avg | +0.42 | +0.21 | +0.17 | +0.22 | +0.33 | +0.42 | +0.41 |
| r450 | - | landscapers (us-them) | +0.57 | +0.13 | -0.08 | +0.11 | +0.24 | +0.47 | +0.55 |
| r450 | - | moves (us-them) | +0.48 | +0.08 | +0.25 | +0.22 | +0.29 | +0.44 | +0.48 |
| r450 | - | pickups (us-them) | +0.51 | +0.30 | +0.10 | +0.23 | +0.26 | +0.36 | +0.51 |
| r500 | - | moves (us-them) ~avg | +0.48 | -0.00 | +0.29 | +0.24 | +0.26 | +0.37 | +0.47 |
| r550 | - | pickups (us-them) ~avg | +0.48 | +0.30 | +0.18 | +0.22 | +0.24 | +0.32 | +0.45 |
| r600 | - | digs (us-them) | +0.61 | +0.17 | -0.04 | +0.12 | +0.19 | +0.33 | +0.47 |
| r600 | - | landscapers (us-them) ~avg | +0.55 | +0.11 | -0.17 | -0.11 | +0.04 | +0.33 | +0.47 |
| r650 | - | dirtDeps (us-them) | +0.57 | +0.09 | -0.13 | +0.06 | +0.11 | +0.28 | +0.43 |
| r750 | - | digs (us-them) ~avg | +0.49 | +0.16 | -0.08 | +0.06 | +0.11 | +0.24 | +0.38 |
| r850 | - | dirtDeps (us-them) ~avg | +0.44 | +0.08 | -0.17 | -0.01 | +0.03 | +0.18 | +0.33 |
| - | r1000 | aba (us-them) [inverted] | -0.41 | -0.01 | -0.07 | -0.05 | -0.06 | -0.08 | -0.27 |
| - | r1200 | aba (us-them) [inverted] ~avg | -0.31 | -0.02 | -0.07 | -0.08 | -0.07 | -0.08 | -0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.28 | +0.25 | +0.27 | +0.26 | +0.19 | . | +0.00 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.23 | +0.15 | +0.18 | +0.17 | +0.18 | +0.19 |
| - | - | netguns (us-them) | +0.22 | . | -0.02 | +0.12 | +0.04 | +0.18 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.17 | . | -0.07 | +0.06 | +0.05 | +0.09 | +0.15 |
| - | r650 | soup (us-them) | -0.39 | +0.02 | +0.06 | -0.15 | -0.08 | -0.18 | -0.23 |
| - | - | soup (us-them) ~avg | -0.26 | -0.00 | +0.20 | +0.09 | +0.06 | -0.13 | -0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
