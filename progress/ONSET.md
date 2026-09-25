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
| r50 | - | miners (us-them) ~avg | +0.49 | +0.36 | +0.33 | +0.32 | +0.25 | +0.15 | +0.09 |
| r50 | - | mines (us-them) | +0.45 | +0.38 | +0.25 | +0.33 | +0.40 | +0.42 | +0.42 |
| r50 | - | mines (us-them) ~avg | +0.46 | +0.38 | +0.29 | +0.28 | +0.34 | +0.43 | +0.45 |
| r50 | - | robots (us-them) | +0.46 | +0.34 | +0.33 | +0.43 | +0.37 | +0.43 | +0.45 |
| r50 | - | robots (us-them) ~avg | +0.51 | +0.40 | +0.38 | +0.42 | +0.43 | +0.49 | +0.51 |
| r50 | - | spawned (us-them) | +0.49 | +0.34 | +0.32 | +0.42 | +0.38 | +0.45 | +0.49 |
| r50 | - | spawned (us-them) ~avg | +0.54 | +0.40 | +0.37 | +0.41 | +0.43 | +0.51 | +0.54 |
| r50 | - | units (us-them) | +0.46 | +0.40 | +0.34 | +0.40 | +0.33 | +0.37 | +0.44 |
| r50 | - | units (us-them) ~avg | +0.51 | +0.49 | +0.40 | +0.43 | +0.42 | +0.45 | +0.49 |
| r100 | - | worth (us-them) | +0.46 | +0.36 | +0.24 | +0.28 | +0.38 | +0.43 | +0.44 |
| r100 | - | worth (us-them) ~avg | +0.47 | +0.34 | +0.27 | +0.25 | +0.31 | +0.42 | +0.47 |
| r150 | - | drones (us-them) | +0.37 | +0.24 | +0.35 | +0.22 | +0.12 | +0.30 | +0.37 |
| r150 | - | drones (us-them) ~avg | +0.37 | +0.24 | +0.37 | +0.35 | +0.25 | +0.30 | +0.35 |
| r200 | - | cov (us-them) | +0.45 | +0.11 | +0.38 | +0.45 | +0.41 | +0.30 | +0.25 |
| r200 | - | moves (us-them) | +0.51 | -0.01 | +0.43 | +0.50 | +0.42 | +0.39 | +0.45 |
| r200 | - | moves (us-them) ~avg | +0.50 | -0.02 | +0.37 | +0.50 | +0.47 | +0.43 | +0.45 |
| r250 | - | cov (us-them) ~avg | +0.46 | +0.07 | +0.29 | +0.46 | +0.44 | +0.40 | +0.36 |
| r250 | - | landscapers (us-them) | +0.48 | +0.25 | +0.27 | +0.33 | +0.32 | +0.36 | +0.42 |
| r400 | - | digs (us-them) | +0.60 | +0.18 | +0.19 | +0.25 | +0.32 | +0.37 | +0.48 |
| r400 | - | landscapers (us-them) ~avg | +0.51 | +0.22 | +0.22 | +0.26 | +0.30 | +0.41 | +0.45 |
| r450 | - | dirtDeps (us-them) | +0.60 | +0.15 | +0.20 | +0.24 | +0.30 | +0.37 | +0.47 |
| r500 | - | pickups (us-them) | +0.39 | +0.14 | +0.18 | +0.25 | +0.20 | +0.32 | +0.35 |
| r500 | - | pickups (us-them) ~avg | +0.37 | +0.14 | +0.21 | +0.24 | +0.23 | +0.34 | +0.35 |
| r500 | - | vaporators (us-them) | +0.41 | +0.05 | +0.05 | +0.23 | +0.22 | +0.37 | +0.39 |
| r500 | - | vaporators (us-them) ~avg | +0.42 | +0.05 | +0.03 | +0.12 | +0.23 | +0.35 | +0.42 |
| r600 | - | digs (us-them) ~avg | +0.52 | +0.16 | +0.19 | +0.22 | +0.26 | +0.30 | +0.42 |
| r650 | - | dirtDeps (us-them) ~avg | +0.52 | +0.13 | +0.19 | +0.21 | +0.24 | +0.30 | +0.41 |
| r900 | - | netguns (us-them) | +0.34 | . | +0.05 | +0.05 | +0.09 | +0.30 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.23 | +0.06 | -0.01 | -0.06 | -0.13 | -0.19 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.22 | +0.08 | +0.03 | +0.03 | -0.03 | -0.12 | -0.19 |
| - | - | died (us-them) [inverted] | +0.26 | . | +0.25 | +0.21 | +0.11 | +0.11 | +0.08 |
| - | - | died (us-them) [inverted] ~avg | +0.30 | . | +0.27 | +0.30 | +0.18 | +0.14 | +0.12 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.17 | +0.15 | . | +0.17 | +0.15 | +0.15 | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.17 | +0.15 | +0.15 | +0.17 | +0.16 | +0.15 | +0.15 |
| - | - | miners (us-them) | +0.44 | +0.13 | +0.14 | +0.17 | +0.06 | -0.01 | +0.02 |
| - | - | netguns (us-them) ~avg | +0.27 | . | +0.05 | +0.04 | +0.06 | +0.16 | +0.25 |
| - | - | soup (us-them) | -0.21 | -0.12 | -0.14 | -0.14 | -0.01 | -0.21 | -0.11 |
| - | - | soup (us-them) ~avg | -0.20 | -0.12 | -0.14 | -0.15 | -0.15 | -0.19 | -0.18 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
