# Which metric starts predicting the result first

41 games, 10 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.55 | +0.28 | +0.50 | +0.48 | +0.51 | +0.50 | +0.49 |
| r50 | - | mines (us-them) ~avg | +0.52 | +0.30 | +0.45 | +0.46 | +0.48 | +0.52 | +0.52 |
| r100 | - | miners (us-them) | +0.36 | +0.36 | +0.30 | +0.24 | +0.24 | +0.31 | +0.34 |
| r100 | - | miners (us-them) ~avg | +0.37 | +0.35 | +0.35 | +0.33 | +0.31 | +0.32 | +0.34 |
| r150 | - | cov (us-them) | +0.48 | +0.27 | +0.45 | +0.47 | +0.48 | +0.28 | +0.12 |
| r150 | - | cov (us-them) ~avg | +0.53 | +0.17 | +0.44 | +0.51 | +0.53 | +0.46 | +0.32 |
| r150 | - | moves (us-them) ~avg | +0.30 | +0.27 | +0.30 | +0.29 | +0.21 | +0.20 | +0.24 |
| r150 | - | worth (us-them) | +0.56 | +0.25 | +0.48 | +0.46 | +0.47 | +0.50 | +0.44 |
| r150 | - | worth (us-them) ~avg | +0.51 | +0.25 | +0.42 | +0.46 | +0.48 | +0.50 | +0.49 |
| r200 | - | robots (us-them) ~avg | +0.48 | +0.28 | +0.31 | +0.33 | +0.36 | +0.41 | +0.48 |
| r200 | - | spawned (us-them) ~avg | +0.52 | +0.28 | +0.30 | +0.36 | +0.41 | +0.46 | +0.52 |
| r250 | - | robots (us-them) | +0.49 | +0.24 | +0.30 | +0.33 | +0.36 | +0.46 | +0.47 |
| r250 | - | spawned (us-them) | +0.53 | +0.24 | +0.30 | +0.43 | +0.44 | +0.49 | +0.51 |
| r300 | - | aba (us-them) [inverted] | +0.38 | +0.14 | +0.27 | +0.34 | +0.36 | +0.30 | +0.12 |
| r300 | - | aba (us-them) [inverted] ~avg | +0.37 | +0.18 | +0.28 | +0.32 | +0.34 | +0.36 | +0.27 |
| r350 | - | vaporators (us-them) | +0.46 | -0.09 | +0.07 | +0.23 | +0.42 | +0.41 | +0.32 |
| r400 | - | vaporators (us-them) ~avg | +0.42 | -0.09 | -0.03 | +0.13 | +0.30 | +0.42 | +0.38 |
| r600 | - | landscapers (us-them) | +0.49 | -0.08 | +0.14 | +0.13 | +0.15 | +0.33 | +0.47 |
| r600 | - | units (us-them) | +0.45 | +0.26 | +0.25 | +0.24 | +0.21 | +0.33 | +0.43 |
| r650 | - | pickups (us-them) | +0.31 | +0.13 | -0.02 | -0.23 | +0.04 | +0.25 | +0.27 |
| r700 | - | units (us-them) ~avg | +0.44 | +0.30 | +0.27 | +0.27 | +0.24 | +0.27 | +0.38 |
| r800 | - | landscapers (us-them) ~avg | +0.46 | -0.08 | +0.05 | +0.09 | +0.11 | +0.21 | +0.36 |
| r900 | - | digs (us-them) | +0.46 | -0.07 | -0.03 | +0.12 | +0.21 | +0.19 | +0.32 |
| r900 | - | dirtDeps (us-them) | +0.46 | -0.11 | -0.07 | +0.02 | +0.10 | +0.16 | +0.31 |
| r1100 | - | digs (us-them) ~avg | +0.36 | -0.07 | -0.06 | +0.05 | +0.13 | +0.16 | +0.24 |
| r1100 | - | dirtDeps (us-them) ~avg | +0.35 | -0.11 | -0.08 | -0.03 | +0.02 | +0.09 | +0.21 |
| r1200 | - | moves (us-them) | +0.31 | +0.29 | +0.28 | +0.22 | +0.14 | +0.20 | +0.27 |
| - | - | died (us-them) [inverted] | -0.26 | . | +0.14 | -0.26 | -0.13 | +0.11 | -0.07 |
| - | - | died (us-them) [inverted] ~avg | -0.23 | . | +0.16 | -0.20 | -0.20 | -0.03 | -0.02 |
| - | - | drones (us-them) | +0.25 | +0.14 | +0.02 | +0.17 | +0.01 | +0.07 | +0.18 |
| - | - | drones (us-them) ~avg | +0.17 | +0.14 | +0.08 | +0.09 | +0.08 | +0.02 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.16 | +0.12 | +0.10 | +0.09 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | +0.14 | +0.13 | +0.09 | . | . |
| - | - | netguns (us-them) | -0.28 | . | -0.14 | -0.01 | +0.05 | +0.11 | -0.12 |
| - | - | netguns (us-them) ~avg | -0.28 | . | -0.18 | -0.07 | -0.02 | +0.03 | -0.01 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.13 | +0.02 | -0.17 | -0.09 | +0.13 | +0.27 |
| - | - | soup (us-them) | +0.24 | +0.09 | +0.13 | +0.21 | +0.10 | +0.02 | +0.06 |
| - | - | soup (us-them) ~avg | +0.19 | +0.05 | +0.11 | +0.15 | +0.19 | +0.16 | +0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
