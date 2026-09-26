# Which metric starts predicting the result first

47 games, 9 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.52 | +0.37 | +0.34 | +0.40 | +0.49 | +0.46 | +0.48 |
| r50 | - | mines (us-them) ~avg | +0.51 | +0.39 | +0.38 | +0.41 | +0.51 | +0.49 | +0.49 |
| r100 | - | drones (us-them) ~avg | +0.42 | +0.34 | +0.23 | +0.24 | +0.29 | +0.37 | +0.39 |
| r100 | - | miners (us-them) | +0.37 | +0.37 | +0.29 | +0.23 | +0.29 | +0.24 | +0.33 |
| r100 | - | miners (us-them) ~avg | +0.39 | +0.31 | +0.31 | +0.31 | +0.38 | +0.32 | +0.33 |
| r100 | - | robots (us-them) | +0.53 | +0.41 | +0.33 | +0.25 | +0.45 | +0.45 | +0.53 |
| r100 | - | robots (us-them) ~avg | +0.52 | +0.38 | +0.36 | +0.33 | +0.44 | +0.47 | +0.51 |
| r100 | - | spawned (us-them) | +0.52 | +0.41 | +0.32 | +0.23 | +0.44 | +0.44 | +0.52 |
| r100 | - | spawned (us-them) ~avg | +0.51 | +0.38 | +0.36 | +0.33 | +0.43 | +0.46 | +0.50 |
| r100 | - | units (us-them) | +0.53 | +0.42 | +0.24 | +0.13 | +0.41 | +0.42 | +0.53 |
| r100 | - | units (us-them) ~avg | +0.53 | +0.37 | +0.30 | +0.22 | +0.36 | +0.44 | +0.50 |
| r100 | - | worth (us-them) | +0.52 | +0.34 | +0.34 | +0.37 | +0.49 | +0.47 | +0.51 |
| r100 | - | worth (us-them) ~avg | +0.51 | +0.32 | +0.34 | +0.39 | +0.48 | +0.48 | +0.51 |
| r150 | - | moves (us-them) | +0.51 | +0.18 | +0.31 | +0.19 | +0.33 | +0.42 | +0.51 |
| r150 | - | moves (us-them) ~avg | +0.51 | +0.06 | +0.34 | +0.26 | +0.35 | +0.41 | +0.49 |
| r400 | - | drones (us-them) | +0.42 | +0.34 | +0.12 | +0.25 | +0.34 | +0.34 | +0.42 |
| r450 | - | cov (us-them) | +0.50 | -0.10 | +0.05 | +0.21 | +0.30 | +0.42 | +0.45 |
| r450 | - | landscapers (us-them) | +0.57 | -0.01 | +0.04 | -0.07 | +0.30 | +0.42 | +0.54 |
| r450 | - | soup (us-them) ~avg | +0.35 | -0.18 | -0.22 | -0.14 | +0.26 | +0.19 | +0.03 |
| r450 | - | vaporators (us-them) | +0.49 | +0.07 | +0.12 | +0.26 | +0.28 | +0.47 | +0.46 |
| r500 | - | vaporators (us-them) ~avg | +0.47 | +0.07 | +0.14 | +0.23 | +0.23 | +0.38 | +0.47 |
| r550 | - | cov (us-them) ~avg | +0.46 | -0.19 | +0.03 | +0.14 | +0.21 | +0.35 | +0.44 |
| r600 | - | landscapers (us-them) ~avg | +0.59 | -0.01 | +0.03 | -0.04 | +0.14 | +0.31 | +0.50 |
| r600 | - | pickups (us-them) | +0.37 | -0.11 | +0.08 | +0.03 | +0.14 | +0.31 | +0.28 |
| r950 | - | digs (us-them) | +0.50 | +0.04 | -0.00 | +0.03 | +0.04 | +0.11 | +0.28 |
| r950 | - | pickups (us-them) ~avg | +0.34 | -0.11 | +0.07 | +0.06 | +0.08 | +0.24 | +0.30 |
| r1050 | - | dirtDeps (us-them) | +0.43 | -0.00 | +0.01 | +0.01 | +0.01 | +0.05 | +0.21 |
| r1150 | - | digs (us-them) ~avg | +0.33 | +0.04 | -0.03 | +0.01 | +0.03 | +0.05 | +0.16 |
| - | r900 | aba (us-them) [inverted] | -0.41 | -0.03 | -0.00 | -0.02 | +0.02 | -0.17 | -0.31 |
| - | r1050 | aba (us-them) [inverted] ~avg | -0.34 | -0.06 | -0.03 | -0.05 | -0.01 | -0.10 | -0.25 |
| - | - | died (us-them) [inverted] | +0.23 | +0.09 | +0.09 | +0.15 | +0.23 | +0.21 | +0.18 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | +0.09 | +0.10 | +0.12 | +0.18 | +0.21 | +0.25 |
| - | - | dirtDeps (us-them) ~avg | +0.25 | -0.00 | -0.02 | -0.01 | +0.01 | +0.01 | +0.09 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.33 | +0.27 | -0.11 | +0.33 | -0.06 | -0.07 | -0.07 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.27 | -0.06 | +0.23 | -0.05 | -0.06 | -0.07 |
| - | - | netguns (us-them) | +0.28 | . | . | . | +0.10 | +0.25 | +0.25 |
| - | - | netguns (us-them) ~avg | +0.29 | . | . | . | +0.10 | +0.25 | +0.28 |
| - | - | soup (us-them) | +0.42 | -0.19 | -0.03 | +0.21 | +0.42 | -0.05 | -0.16 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
