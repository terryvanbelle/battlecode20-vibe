# Which metric starts predicting the result first

18 games, 8 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | robots (us-them) | +0.74 | +0.32 | +0.40 | +0.62 | +0.61 | +0.59 | +0.61 |
| r100 | - | spawned (us-them) | +0.68 | +0.32 | +0.43 | +0.68 | +0.67 | +0.60 | +0.59 |
| r100 | - | worth (us-them) | +0.69 | +0.42 | +0.51 | +0.69 | +0.61 | +0.64 | +0.59 |
| r100 | - | worth (us-them) ~avg | +0.74 | +0.32 | +0.48 | +0.68 | +0.67 | +0.66 | +0.62 |
| r150 | - | soup (us-them) ~avg | +0.57 | +0.29 | +0.52 | +0.57 | +0.48 | +0.40 | -0.09 |
| r200 | - | landscapers (us-them) | +0.73 | -0.08 | +0.44 | +0.56 | +0.54 | +0.54 | +0.61 |
| r200 | - | robots (us-them) ~avg | +0.76 | +0.25 | +0.34 | +0.58 | +0.60 | +0.60 | +0.60 |
| r200 | - | spawned (us-them) ~avg | +0.75 | +0.25 | +0.36 | +0.62 | +0.65 | +0.62 | +0.62 |
| r250 | - | landscapers (us-them) ~avg | +0.69 | -0.08 | +0.25 | +0.44 | +0.50 | +0.51 | +0.54 |
| r250 | - | soup (us-them) | +0.59 | +0.19 | +0.24 | +0.53 | +0.10 | +0.30 | -0.20 |
| r250 | - | units (us-them) | +0.77 | +0.23 | +0.29 | +0.56 | +0.59 | +0.51 | +0.57 |
| r300 | - | drones (us-them) | +0.52 | +0.16 | +0.17 | +0.42 | +0.39 | +0.09 | +0.28 |
| r300 | - | drones (us-them) ~avg | +0.60 | +0.16 | +0.15 | +0.35 | +0.38 | +0.26 | +0.18 |
| r300 | - | miners (us-them) ~avg | +0.50 | +0.26 | +0.10 | +0.32 | +0.34 | +0.40 | +0.28 |
| r300 | - | mines (us-them) | +0.63 | +0.19 | +0.26 | +0.63 | +0.56 | +0.50 | +0.32 |
| r300 | - | mines (us-them) ~avg | +0.67 | +0.21 | +0.26 | +0.66 | +0.63 | +0.56 | +0.44 |
| r300 | - | pickups (us-them) | +0.54 | . | +0.21 | +0.40 | +0.40 | +0.52 | +0.36 |
| r300 | - | pickups (us-them) ~avg | +0.52 | . | +0.22 | +0.34 | +0.36 | +0.48 | +0.45 |
| r300 | - | units (us-them) ~avg | +0.80 | +0.26 | +0.22 | +0.48 | +0.53 | +0.53 | +0.53 |
| r300 | - | vaporators (us-them) | +0.53 | +0.16 | +0.02 | +0.40 | +0.29 | +0.45 | +0.47 |
| r300 | - | vaporators (us-them) ~avg | +0.54 | +0.16 | +0.08 | +0.38 | +0.36 | +0.47 | +0.52 |
| r350 | - | cov (us-them) | +0.60 | -0.10 | +0.12 | +0.21 | +0.49 | +0.43 | +0.47 |
| r350 | - | moves (us-them) | +0.61 | +0.10 | +0.08 | +0.26 | +0.38 | +0.40 | +0.37 |
| r350 | - | netguns (us-them) | +0.49 | . | . | +0.23 | +0.33 | +0.14 | +0.49 |
| r350 | - | netguns (us-them) ~avg | +0.38 | . | . | +0.23 | +0.33 | +0.23 | +0.34 |
| r400 | - | cov (us-them) ~avg | +0.51 | -0.20 | -0.01 | +0.13 | +0.30 | +0.38 | +0.38 |
| r400 | - | miners (us-them) | +0.45 | +0.24 | +0.02 | +0.21 | +0.35 | +0.38 | +0.12 |
| r450 | - | digs (us-them) | +0.56 | -0.16 | -0.01 | +0.16 | +0.27 | +0.35 | +0.36 |
| r450 | - | moves (us-them) ~avg | +0.56 | +0.06 | +0.08 | +0.21 | +0.29 | +0.36 | +0.32 |
| r500 | r250 | died (us-them) [inverted] | +0.53 | . | -0.27 | -0.29 | -0.10 | +0.49 | -0.04 |
| r600 | - | dirtDeps (us-them) | +0.52 | -0.18 | -0.08 | +0.10 | +0.23 | +0.32 | +0.31 |
| r650 | r250 | died (us-them) [inverted] ~avg | -0.33 | . | -0.27 | -0.30 | -0.23 | +0.25 | +0.09 |
| r950 | - | digs (us-them) ~avg | +0.49 | -0.16 | -0.10 | +0.05 | +0.17 | +0.25 | +0.28 |
| r950 | - | dirtDeps (us-them) ~avg | +0.46 | -0.18 | -0.16 | -0.01 | +0.12 | +0.21 | +0.23 |
| - | r650 | aba (us-them) [inverted] | -0.57 | -0.17 | -0.24 | +0.05 | +0.19 | -0.29 | -0.50 |
| - | r800 | aba (us-them) [inverted] ~avg | -0.51 | -0.14 | -0.24 | -0.13 | +0.11 | -0.28 | -0.38 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.22 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.22 | . | . | . | . | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
