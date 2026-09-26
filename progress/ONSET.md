# Which metric starts predicting the result first

39 games, 21 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.39 | +0.31 | +0.31 | +0.36 | +0.37 | +0.33 | +0.28 |
| r50 | - | mines (us-them) ~avg | +0.39 | +0.34 | +0.32 | +0.35 | +0.38 | +0.38 | +0.34 |
| r50 | - | worth (us-them) ~avg | +0.45 | +0.36 | +0.33 | +0.37 | +0.39 | +0.43 | +0.43 |
| r100 | - | drones (us-them) | +0.50 | +0.37 | +0.26 | +0.25 | +0.29 | +0.36 | +0.48 |
| r100 | - | drones (us-them) ~avg | +0.48 | +0.37 | +0.38 | +0.31 | +0.31 | +0.37 | +0.48 |
| r100 | - | miners (us-them) ~avg | +0.39 | +0.39 | +0.31 | +0.27 | +0.18 | +0.19 | +0.25 |
| r100 | - | robots (us-them) | +0.67 | +0.41 | +0.35 | +0.36 | +0.40 | +0.57 | +0.65 |
| r100 | - | robots (us-them) ~avg | +0.64 | +0.37 | +0.34 | +0.40 | +0.42 | +0.52 | +0.61 |
| r100 | - | spawned (us-them) | +0.63 | +0.41 | +0.34 | +0.41 | +0.42 | +0.56 | +0.61 |
| r100 | - | spawned (us-them) ~avg | +0.62 | +0.37 | +0.34 | +0.40 | +0.43 | +0.52 | +0.58 |
| r100 | - | units (us-them) ~avg | +0.67 | +0.38 | +0.27 | +0.29 | +0.35 | +0.50 | +0.63 |
| r100 | - | worth (us-them) | +0.45 | +0.37 | +0.32 | +0.35 | +0.37 | +0.43 | +0.39 |
| r250 | - | pickups (us-them) | +0.58 | +0.24 | +0.17 | +0.36 | +0.45 | +0.49 | +0.57 |
| r300 | - | pickups (us-them) ~avg | +0.59 | +0.24 | +0.20 | +0.33 | +0.43 | +0.56 | +0.58 |
| r350 | - | landscapers (us-them) | +0.74 | +0.03 | +0.10 | +0.20 | +0.35 | +0.65 | +0.73 |
| r350 | - | units (us-them) | +0.68 | +0.42 | +0.25 | +0.27 | +0.36 | +0.58 | +0.67 |
| r450 | - | digs (us-them) | +0.69 | +0.02 | +0.05 | +0.17 | +0.29 | +0.50 | +0.61 |
| r500 | - | died (us-them) [inverted] | +0.37 | . | +0.09 | -0.01 | +0.15 | +0.34 | +0.31 |
| r500 | - | dirtDeps (us-them) | +0.69 | +0.02 | +0.03 | +0.15 | +0.26 | +0.48 | +0.60 |
| r500 | - | landscapers (us-them) ~avg | +0.73 | +0.02 | +0.04 | +0.12 | +0.25 | +0.44 | +0.64 |
| r500 | - | vaporators (us-them) | +0.36 | +0.01 | +0.12 | +0.24 | +0.19 | +0.30 | +0.25 |
| r550 | - | netguns (us-them) | +0.50 | . | +0.18 | +0.12 | +0.18 | +0.39 | +0.39 |
| r600 | - | digs (us-them) ~avg | +0.62 | +0.02 | +0.03 | +0.12 | +0.22 | +0.34 | +0.49 |
| r600 | - | dirtDeps (us-them) ~avg | +0.62 | +0.02 | +0.01 | +0.10 | +0.19 | +0.31 | +0.48 |
| r600 | - | netguns (us-them) ~avg | +0.40 | . | +0.18 | +0.14 | +0.16 | +0.30 | +0.37 |
| r700 | - | died (us-them) [inverted] ~avg | +0.35 | . | +0.14 | +0.07 | +0.11 | +0.27 | +0.35 |
| r700 | - | vaporators (us-them) ~avg | +0.33 | +0.01 | +0.07 | +0.16 | +0.20 | +0.29 | +0.28 |
| r800 | - | moves (us-them) | +0.48 | +0.18 | +0.25 | +0.16 | +0.13 | +0.21 | +0.38 |
| r950 | - | miners (us-them) | +0.41 | +0.41 | +0.27 | +0.10 | -0.03 | +0.15 | +0.29 |
| r950 | - | moves (us-them) ~avg | +0.40 | +0.11 | +0.25 | +0.23 | +0.18 | +0.20 | +0.30 |
| - | r700 | aba (us-them) [inverted] | -0.54 | -0.10 | -0.21 | -0.19 | -0.03 | -0.23 | -0.45 |
| - | r850 | aba (us-them) [inverted] ~avg | -0.47 | -0.08 | -0.19 | -0.23 | -0.12 | -0.15 | -0.34 |
| - | - | cov (us-them) | -0.33 | -0.22 | -0.18 | -0.18 | -0.18 | -0.16 | -0.05 |
| - | r50 | cov (us-them) ~avg | -0.35 | -0.27 | -0.20 | -0.20 | -0.20 | -0.14 | -0.12 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.18 | . | +0.18 | +0.18 | +0.18 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.18 | . | +0.18 | +0.18 | +0.18 | . | . |
| - | r450 | soup (us-them) | -0.39 | -0.07 | -0.13 | -0.25 | -0.17 | -0.34 | -0.29 |
| - | r600 | soup (us-them) ~avg | -0.33 | +0.00 | -0.03 | -0.11 | -0.15 | -0.32 | -0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
