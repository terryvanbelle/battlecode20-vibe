# Which metric starts predicting the result first

38 games, 16 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | vaporators (us-them) | +0.42 | +0.23 | +0.32 | +0.40 | +0.38 | +0.40 | +0.35 |
| r250 | - | drones (us-them) | +0.44 | -0.04 | +0.28 | +0.41 | +0.43 | +0.39 | +0.38 |
| r250 | - | drones (us-them) ~avg | +0.41 | -0.04 | +0.20 | +0.34 | +0.41 | +0.38 | +0.37 |
| r250 | - | miners (us-them) | +0.54 | +0.13 | +0.26 | +0.40 | +0.47 | +0.54 | +0.51 |
| r250 | - | miners (us-them) ~avg | +0.48 | +0.17 | +0.28 | +0.36 | +0.42 | +0.47 | +0.48 |
| r250 | - | mines (us-them) | +0.67 | +0.07 | +0.19 | +0.47 | +0.58 | +0.63 | +0.66 |
| r250 | - | vaporators (us-them) ~avg | +0.43 | +0.23 | +0.28 | +0.40 | +0.41 | +0.43 | +0.39 |
| r250 | - | worth (us-them) | +0.64 | +0.07 | +0.21 | +0.50 | +0.61 | +0.61 | +0.52 |
| r300 | - | robots (us-them) | +0.71 | +0.12 | +0.11 | +0.45 | +0.60 | +0.71 | +0.61 |
| r300 | - | spawned (us-them) | +0.68 | +0.12 | +0.09 | +0.43 | +0.60 | +0.67 | +0.64 |
| r300 | - | worth (us-them) ~avg | +0.58 | +0.07 | +0.13 | +0.34 | +0.49 | +0.58 | +0.55 |
| r350 | - | mines (us-them) ~avg | +0.60 | +0.06 | +0.12 | +0.29 | +0.44 | +0.56 | +0.60 |
| r350 | - | robots (us-them) ~avg | +0.64 | +0.14 | +0.07 | +0.26 | +0.46 | +0.62 | +0.62 |
| r350 | - | spawned (us-them) ~avg | +0.63 | +0.14 | +0.05 | +0.24 | +0.45 | +0.60 | +0.62 |
| r350 | - | units (us-them) | +0.72 | +0.03 | -0.09 | +0.18 | +0.48 | +0.72 | +0.60 |
| r450 | r750 | died (us-them) [inverted] | -0.43 | . | +0.11 | +0.18 | +0.24 | +0.17 | -0.24 |
| r450 | r150 | landscapers (us-them) | +0.70 | -0.03 | -0.33 | -0.21 | +0.18 | +0.66 | +0.62 |
| r450 | - | units (us-them) ~avg | +0.62 | +0.08 | -0.09 | +0.01 | +0.25 | +0.52 | +0.61 |
| r550 | - | cov (us-them) | +0.45 | +0.00 | +0.23 | +0.19 | +0.26 | +0.38 | +0.43 |
| r550 | - | moves (us-them) | +0.48 | -0.06 | +0.03 | +0.03 | +0.15 | +0.36 | +0.42 |
| r650 | r200 | landscapers (us-them) ~avg | +0.63 | -0.04 | -0.31 | -0.31 | -0.18 | +0.23 | +0.54 |
| r700 | - | cov (us-them) ~avg | +0.36 | -0.06 | +0.12 | +0.15 | +0.19 | +0.26 | +0.34 |
| r850 | - | moves (us-them) ~avg | +0.37 | -0.07 | +0.04 | +0.04 | +0.09 | +0.22 | +0.31 |
| r900 | r200 | digs (us-them) | +0.52 | -0.11 | -0.31 | -0.31 | -0.19 | +0.07 | +0.33 |
| r900 | r200 | dirtDeps (us-them) | +0.51 | -0.15 | -0.36 | -0.34 | -0.22 | +0.04 | +0.31 |
| r900 | - | pickups (us-them) | +0.38 | -0.15 | +0.14 | +0.20 | +0.29 | +0.16 | +0.32 |
| r1150 | r200 | digs (us-them) ~avg | +0.35 | -0.11 | -0.30 | -0.33 | -0.28 | -0.12 | +0.13 |
| r1150 | - | pickups (us-them) ~avg | +0.32 | -0.15 | +0.08 | +0.17 | +0.22 | +0.21 | +0.24 |
| r1200 | r200 | dirtDeps (us-them) ~avg | -0.36 | -0.15 | -0.35 | -0.36 | -0.31 | -0.15 | +0.09 |
| - | - | aba (us-them) [inverted] | +0.21 | +0.08 | +0.21 | +0.19 | +0.17 | +0.12 | -0.07 |
| - | - | aba (us-them) [inverted] ~avg | +0.16 | +0.04 | +0.13 | +0.15 | +0.14 | +0.15 | +0.11 |
| - | - | died (us-them) [inverted] ~avg | -0.28 | . | +0.21 | +0.22 | +0.23 | +0.24 | -0.08 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.16 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | . | . | . | . | +0.14 |
| - | - | netguns (us-them) | -0.19 | . | -0.04 | -0.09 | -0.14 | +0.05 | +0.06 |
| - | - | netguns (us-them) ~avg | -0.19 | . | -0.10 | -0.10 | -0.12 | -0.07 | -0.03 |
| - | - | soup (us-them) | +0.27 | -0.15 | +0.07 | +0.24 | +0.17 | +0.07 | +0.07 |
| - | - | soup (us-them) ~avg | +0.18 | -0.15 | +0.04 | +0.14 | +0.18 | +0.05 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
