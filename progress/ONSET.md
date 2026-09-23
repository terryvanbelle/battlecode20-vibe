# Which metric starts predicting the result first

26 games, 17 wins. Noise floor about 0.39; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | cov (us-them) | +0.46 | +0.18 | +0.35 | +0.28 | +0.28 | +0.31 | +0.40 |
| r200 | - | cov (us-them) ~avg | +0.39 | +0.15 | +0.31 | +0.29 | +0.29 | +0.29 | +0.34 |
| r200 | - | died (us-them) [inverted] | +0.57 | . | +0.32 | +0.45 | +0.52 | +0.57 | +0.22 |
| r250 | - | died (us-them) [inverted] ~avg | +0.64 | . | +0.29 | +0.57 | +0.60 | +0.58 | +0.52 |
| r250 | - | digs (us-them) | +0.76 | -0.18 | +0.23 | +0.49 | +0.60 | +0.67 | +0.71 |
| r250 | - | dirtDeps (us-them) | +0.75 | -0.21 | +0.17 | +0.45 | +0.55 | +0.65 | +0.70 |
| r250 | - | landscapers (us-them) | +0.74 | -0.13 | +0.26 | +0.52 | +0.62 | +0.68 | +0.73 |
| r250 | - | mines (us-them) | +0.62 | -0.03 | +0.29 | +0.49 | +0.55 | +0.59 | +0.61 |
| r250 | - | pickups (us-them) | +0.48 | +0.26 | +0.20 | +0.33 | +0.38 | +0.36 | +0.47 |
| r250 | - | robots (us-them) | +0.64 | -0.04 | +0.28 | +0.55 | +0.58 | +0.56 | +0.63 |
| r250 | - | spawned (us-them) | +0.60 | -0.04 | +0.25 | +0.46 | +0.48 | +0.50 | +0.60 |
| r250 | - | units (us-them) | +0.65 | -0.03 | +0.23 | +0.52 | +0.61 | +0.55 | +0.62 |
| r250 | - | worth (us-them) | +0.66 | -0.10 | +0.28 | +0.53 | +0.55 | +0.59 | +0.66 |
| r300 | - | digs (us-them) ~avg | +0.72 | -0.15 | +0.13 | +0.37 | +0.50 | +0.61 | +0.66 |
| r300 | - | dirtDeps (us-them) ~avg | +0.70 | -0.20 | +0.09 | +0.32 | +0.45 | +0.57 | +0.63 |
| r300 | - | landscapers (us-them) ~avg | +0.75 | -0.10 | +0.09 | +0.30 | +0.48 | +0.64 | +0.72 |
| r300 | - | mines (us-them) ~avg | +0.61 | -0.11 | +0.09 | +0.35 | +0.44 | +0.55 | +0.60 |
| r300 | - | robots (us-them) ~avg | +0.63 | -0.07 | +0.10 | +0.33 | +0.49 | +0.56 | +0.63 |
| r300 | - | units (us-them) ~avg | +0.64 | -0.03 | +0.09 | +0.30 | +0.47 | +0.56 | +0.64 |
| r300 | - | vaporators (us-them) | +0.61 | +0.08 | +0.24 | +0.32 | +0.34 | +0.51 | +0.55 |
| r300 | - | worth (us-them) ~avg | +0.65 | -0.21 | +0.11 | +0.37 | +0.48 | +0.55 | +0.64 |
| r350 | - | pickups (us-them) ~avg | +0.46 | . | +0.15 | +0.28 | +0.39 | +0.37 | +0.44 |
| r350 | - | spawned (us-them) ~avg | +0.59 | -0.07 | +0.08 | +0.25 | +0.38 | +0.48 | +0.57 |
| r400 | - | soup (us-them) | +0.36 | -0.02 | -0.14 | +0.01 | +0.36 | +0.36 | +0.16 |
| r450 | - | vaporators (us-them) ~avg | +0.59 | +0.08 | +0.18 | +0.23 | +0.30 | +0.42 | +0.59 |
| r550 | - | soup (us-them) ~avg | +0.34 | -0.07 | -0.09 | +0.13 | +0.16 | +0.34 | +0.25 |
| r600 | - | moves (us-them) | +0.53 | +0.04 | +0.06 | +0.09 | +0.19 | +0.31 | +0.48 |
| r700 | - | drones (us-them) | +0.34 | +0.06 | -0.05 | +0.02 | +0.04 | +0.13 | +0.33 |
| r750 | - | netguns (us-them) | +0.36 | . | -0.02 | +0.05 | -0.02 | +0.24 | +0.35 |
| r800 | - | moves (us-them) ~avg | +0.47 | +0.05 | +0.09 | +0.08 | +0.14 | +0.23 | +0.37 |
| r1000 | - | drones (us-them) ~avg | +0.31 | +0.06 | +0.00 | +0.02 | +0.02 | +0.03 | +0.27 |
| - | r850 | aba (us-them) [inverted] | -0.49 | -0.14 | +0.04 | -0.07 | -0.10 | -0.07 | -0.40 |
| - | - | aba (us-them) [inverted] ~avg | -0.30 | -0.19 | -0.01 | -0.03 | -0.07 | -0.07 | -0.16 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.27 | +0.05 | +0.04 | +0.21 | +0.23 | +0.11 | +0.18 |
| - | - | miners (us-them) ~avg | +0.18 | +0.00 | +0.03 | +0.09 | +0.16 | +0.14 | +0.18 |
| - | - | netguns (us-them) ~avg | +0.29 | . | +0.08 | +0.05 | +0.03 | +0.07 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
