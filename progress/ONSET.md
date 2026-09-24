# Which metric starts predicting the result first

44 games, 33 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.43 | +0.40 | +0.27 | +0.20 | +0.29 | +0.30 | +0.35 |
| r100 | - | drones (us-them) ~avg | +0.40 | +0.40 | +0.30 | +0.25 | +0.24 | +0.26 | +0.25 |
| r100 | - | pickups (us-them) | +0.41 | +0.32 | +0.41 | +0.27 | +0.27 | +0.38 | +0.40 |
| r100 | - | pickups (us-them) ~avg | +0.40 | +0.32 | +0.40 | +0.34 | +0.28 | +0.31 | +0.33 |
| r150 | - | mines (us-them) | +0.50 | +0.22 | +0.40 | +0.47 | +0.44 | +0.47 | +0.48 |
| r150 | - | worth (us-them) | +0.59 | +0.18 | +0.46 | +0.50 | +0.48 | +0.53 | +0.54 |
| r200 | - | netguns (us-them) | +0.40 | . | +0.38 | +0.37 | +0.40 | +0.36 | +0.25 |
| r200 | - | netguns (us-them) ~avg | +0.41 | . | +0.38 | +0.37 | +0.39 | +0.40 | +0.37 |
| r200 | - | robots (us-them) | +0.63 | +0.06 | +0.36 | +0.49 | +0.49 | +0.51 | +0.58 |
| r200 | - | soup (us-them) ~avg | +0.33 | +0.24 | +0.33 | +0.24 | +0.14 | +0.00 | +0.07 |
| r200 | - | spawned (us-them) | +0.62 | +0.05 | +0.37 | +0.48 | +0.49 | +0.52 | +0.61 |
| r200 | - | worth (us-them) ~avg | +0.59 | +0.12 | +0.34 | +0.42 | +0.45 | +0.48 | +0.51 |
| r250 | - | landscapers (us-them) | +0.66 | -0.04 | +0.20 | +0.40 | +0.49 | +0.54 | +0.65 |
| r250 | - | mines (us-them) ~avg | +0.47 | +0.15 | +0.27 | +0.36 | +0.40 | +0.42 | +0.44 |
| r250 | - | units (us-them) | +0.64 | +0.04 | +0.25 | +0.43 | +0.45 | +0.45 | +0.58 |
| r300 | - | digs (us-them) | +0.70 | +0.03 | +0.20 | +0.32 | +0.44 | +0.55 | +0.69 |
| r300 | - | dirtDeps (us-them) | +0.69 | +0.12 | +0.22 | +0.31 | +0.40 | +0.52 | +0.65 |
| r300 | - | robots (us-them) ~avg | +0.60 | +0.02 | +0.18 | +0.33 | +0.43 | +0.47 | +0.52 |
| r300 | - | spawned (us-them) ~avg | +0.59 | +0.01 | +0.18 | +0.32 | +0.42 | +0.46 | +0.52 |
| r350 | - | units (us-them) ~avg | +0.57 | -0.04 | +0.08 | +0.23 | +0.35 | +0.40 | +0.46 |
| r350 | - | vaporators (us-them) | +0.52 | -0.17 | +0.22 | +0.26 | +0.36 | +0.51 | +0.43 |
| r400 | - | landscapers (us-them) ~avg | +0.65 | -0.09 | +0.06 | +0.19 | +0.34 | +0.43 | +0.57 |
| r450 | - | digs (us-them) ~avg | +0.68 | -0.01 | +0.10 | +0.20 | +0.30 | +0.40 | +0.57 |
| r450 | - | dirtDeps (us-them) ~avg | +0.64 | +0.08 | +0.13 | +0.21 | +0.28 | +0.36 | +0.52 |
| r450 | - | vaporators (us-them) ~avg | +0.52 | -0.17 | +0.14 | +0.21 | +0.29 | +0.48 | +0.49 |
| r500 | - | moves (us-them) | +0.50 | -0.09 | +0.02 | +0.14 | +0.22 | +0.34 | +0.38 |
| r550 | - | moves (us-them) ~avg | +0.46 | -0.04 | +0.03 | +0.12 | +0.18 | +0.32 | +0.35 |
| r700 | - | cov (us-them) | +0.39 | -0.20 | -0.18 | -0.10 | +0.06 | +0.29 | +0.33 |
| r1000 | - | miners (us-them) | +0.31 | +0.03 | +0.10 | +0.23 | +0.16 | +0.18 | +0.20 |
| - | r1100 | aba (us-them) [inverted] | -0.37 | -0.13 | -0.17 | -0.07 | -0.04 | -0.16 | -0.24 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | -0.07 | -0.15 | -0.12 | -0.09 | -0.10 | -0.16 |
| - | - | cov (us-them) ~avg | +0.30 | -0.19 | -0.19 | -0.16 | -0.11 | +0.10 | +0.18 |
| - | - | died (us-them) [inverted] | -0.25 | +0.05 | -0.01 | +0.04 | +0.09 | +0.04 | -0.15 |
| - | - | died (us-them) [inverted] ~avg | -0.18 | +0.07 | -0.01 | +0.02 | +0.07 | +0.07 | -0.17 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.26 | -0.03 | +0.01 | +0.12 | +0.17 | +0.22 | +0.21 |
| - | - | soup (us-them) | +0.35 | +0.35 | +0.20 | +0.17 | +0.03 | -0.06 | +0.12 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
