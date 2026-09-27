# Which metric starts predicting the result first

45 games, 16 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.43 | +0.32 | +0.41 | +0.41 | +0.34 | +0.30 | +0.33 |
| r50 | - | mines (us-them) ~avg | +0.39 | +0.35 | +0.33 | +0.38 | +0.38 | +0.34 | +0.34 |
| r100 | - | drones (us-them) ~avg | +0.44 | +0.30 | +0.33 | +0.38 | +0.39 | +0.37 | +0.43 |
| r100 | - | hqBuried (us-them) [inverted] | +0.37 | +0.32 | +0.21 | . | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.37 | +0.32 | +0.22 | +0.06 | +0.06 | +0.06 | +0.06 |
| r100 | - | vaporators (us-them) | +0.47 | +0.31 | +0.41 | +0.43 | +0.46 | +0.45 | +0.44 |
| r100 | - | vaporators (us-them) ~avg | +0.47 | +0.31 | +0.41 | +0.43 | +0.45 | +0.46 | +0.46 |
| r100 | - | worth (us-them) | +0.55 | +0.33 | +0.49 | +0.47 | +0.43 | +0.46 | +0.48 |
| r100 | - | worth (us-them) ~avg | +0.48 | +0.32 | +0.37 | +0.46 | +0.46 | +0.46 | +0.48 |
| r200 | - | drones (us-them) | +0.46 | +0.29 | +0.30 | +0.31 | +0.35 | +0.38 | +0.46 |
| r200 | - | robots (us-them) | +0.48 | +0.19 | +0.36 | +0.32 | +0.33 | +0.40 | +0.48 |
| r200 | - | spawned (us-them) | +0.44 | +0.19 | +0.31 | +0.29 | +0.26 | +0.34 | +0.42 |
| r250 | - | robots (us-them) ~avg | +0.44 | +0.18 | +0.22 | +0.31 | +0.33 | +0.37 | +0.44 |
| r250 | - | units (us-them) | +0.49 | +0.16 | +0.25 | +0.27 | +0.32 | +0.40 | +0.48 |
| r400 | - | died (us-them) [inverted] | +0.47 | +0.04 | +0.20 | +0.17 | +0.31 | +0.38 | +0.47 |
| r450 | - | landscapers (us-them) | +0.58 | -0.03 | +0.10 | +0.23 | +0.29 | +0.46 | +0.50 |
| r450 | - | pickups (us-them) | +0.48 | +0.10 | +0.22 | +0.16 | +0.30 | +0.48 | +0.34 |
| r450 | - | units (us-them) ~avg | +0.46 | +0.14 | +0.14 | +0.23 | +0.29 | +0.36 | +0.45 |
| r500 | - | died (us-them) [inverted] ~avg | +0.45 | -0.02 | +0.15 | +0.21 | +0.26 | +0.35 | +0.45 |
| r500 | - | digs (us-them) | +0.56 | +0.22 | +0.02 | +0.18 | +0.27 | +0.39 | +0.47 |
| r500 | - | pickups (us-them) ~avg | +0.45 | +0.10 | +0.18 | +0.18 | +0.24 | +0.41 | +0.41 |
| r600 | - | dirtDeps (us-them) | +0.51 | +0.28 | +0.04 | +0.15 | +0.21 | +0.32 | +0.43 |
| r600 | - | landscapers (us-them) ~avg | +0.55 | -0.03 | -0.03 | +0.05 | +0.17 | +0.33 | +0.46 |
| r600 | - | spawned (us-them) ~avg | +0.40 | +0.19 | +0.19 | +0.26 | +0.28 | +0.31 | +0.38 |
| r650 | - | digs (us-them) ~avg | +0.50 | +0.22 | +0.00 | +0.10 | +0.19 | +0.29 | +0.41 |
| r700 | - | moves (us-them) | +0.43 | -0.17 | -0.10 | +0.04 | +0.11 | +0.24 | +0.41 |
| r750 | - | dirtDeps (us-them) ~avg | +0.44 | +0.28 | +0.05 | +0.09 | +0.15 | +0.23 | +0.36 |
| r850 | - | moves (us-them) ~avg | +0.39 | -0.25 | -0.10 | +0.01 | +0.06 | +0.17 | +0.33 |
| r900 | - | miners (us-them) | +0.37 | +0.11 | +0.11 | -0.01 | -0.01 | +0.03 | +0.30 |
| - | r450 | aba (us-them) [inverted] | -0.58 | -0.16 | +0.06 | -0.17 | -0.28 | -0.32 | -0.49 |
| - | r650 | aba (us-them) [inverted] ~avg | -0.51 | -0.16 | -0.01 | -0.11 | -0.21 | -0.29 | -0.40 |
| - | - | cov (us-them) | +0.29 | -0.23 | -0.18 | -0.01 | +0.08 | +0.16 | +0.26 |
| - | - | cov (us-them) ~avg | -0.26 | -0.26 | -0.21 | -0.14 | -0.06 | +0.05 | +0.15 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.28 | +0.10 | +0.09 | +0.09 | +0.06 | +0.06 | +0.18 |
| - | - | netguns (us-them) | +0.27 | . | . | . | . | +0.27 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.27 | . | . | . | . | +0.26 | +0.24 |
| - | - | soup (us-them) | +0.29 | -0.05 | -0.15 | +0.29 | +0.15 | -0.01 | +0.07 |
| - | - | soup (us-them) ~avg | +0.15 | +0.02 | -0.15 | -0.04 | +0.10 | +0.09 | +0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
