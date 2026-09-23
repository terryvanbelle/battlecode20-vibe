# Which metric starts predicting the result first

12 games, 6 wins. Noise floor about 0.58; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r750 | miners (us-them) | +0.48 | +0.34 | +0.27 | +0.30 | +0.26 | +0.21 | -0.33 |
| r50 | - | miners (us-them) ~avg | +0.48 | +0.42 | +0.35 | +0.33 | +0.32 | +0.29 | +0.13 |
| r50 | - | robots (us-them) ~avg | +0.61 | +0.32 | +0.08 | +0.09 | +0.19 | +0.41 | +0.54 |
| r50 | - | spawned (us-them) ~avg | +0.68 | +0.32 | +0.08 | +0.08 | +0.20 | +0.47 | +0.63 |
| r50 | - | units (us-them) ~avg | +0.52 | +0.32 | -0.01 | -0.07 | -0.01 | +0.23 | +0.41 |
| r100 | r250 | drones (us-them) | +0.65 | +0.35 | +0.49 | -0.38 | -0.24 | +0.14 | +0.09 |
| r100 | - | drones (us-them) ~avg | +0.56 | +0.35 | +0.56 | -0.09 | -0.22 | -0.05 | +0.04 |
| r100 | - | pickups (us-them) | +0.60 | +0.30 | +0.39 | +0.60 | +0.42 | +0.44 | +0.50 |
| r100 | - | pickups (us-them) ~avg | +0.57 | +0.30 | +0.45 | +0.55 | +0.54 | +0.48 | +0.48 |
| r150 | - | vaporators (us-them) | +0.71 | . | +0.58 | +0.71 | +0.65 | +0.16 | +0.38 |
| r150 | - | vaporators (us-them) ~avg | +0.71 | . | +0.53 | +0.64 | +0.71 | +0.51 | +0.52 |
| r250 | - | netguns (us-them) | +0.67 | . | . | +0.52 | +0.65 | +0.65 | +0.65 |
| r250 | - | netguns (us-them) ~avg | +0.65 | . | . | +0.44 | +0.60 | +0.64 | +0.65 |
| r350 | - | robots (us-them) | +0.71 | +0.19 | -0.04 | +0.15 | +0.36 | +0.55 | +0.64 |
| r350 | - | spawned (us-them) | +0.75 | +0.19 | -0.04 | +0.14 | +0.44 | +0.67 | +0.73 |
| r350 | - | worth (us-them) | +0.70 | -0.08 | -0.06 | +0.25 | +0.54 | +0.55 | +0.70 |
| r400 | - | mines (us-them) | +0.66 | +0.04 | -0.02 | +0.14 | +0.33 | +0.57 | +0.66 |
| r450 | - | worth (us-them) ~avg | +0.64 | -0.08 | -0.03 | +0.09 | +0.28 | +0.48 | +0.60 |
| r500 | r100 | landscapers (us-them) | +0.67 | -0.43 | -0.62 | -0.10 | +0.08 | +0.47 | +0.64 |
| r500 | - | units (us-them) | +0.70 | +0.20 | -0.17 | -0.05 | +0.10 | +0.47 | +0.55 |
| r550 | - | mines (us-them) ~avg | +0.59 | -0.04 | +0.01 | +0.09 | +0.17 | +0.37 | +0.54 |
| r750 | r100 | digs (us-them) | -0.62 | -0.62 | -0.52 | -0.38 | -0.28 | +0.05 | +0.44 |
| r750 | r100 | landscapers (us-them) ~avg | -0.70 | -0.43 | -0.70 | -0.52 | -0.28 | +0.15 | +0.45 |
| r800 | r100 | dirtDeps (us-them) | -0.70 | -0.60 | -0.54 | -0.39 | -0.34 | +0.00 | +0.44 |
| r1000 | r100 | digs (us-them) ~avg | -0.62 | -0.62 | -0.57 | -0.45 | -0.38 | -0.20 | +0.22 |
| r1050 | r100 | dirtDeps (us-them) ~avg | -0.68 | -0.60 | -0.60 | -0.47 | -0.41 | -0.25 | +0.19 |
| - | - | aba (us-them) [inverted] | +0.16 | +0.16 | +0.11 | +0.08 | +0.04 | -0.14 | -0.10 |
| - | - | aba (us-them) [inverted] ~avg | +0.16 | +0.16 | +0.08 | +0.10 | +0.09 | -0.03 | -0.10 |
| - | - | cov (us-them) | -0.22 | +0.12 | +0.11 | -0.06 | +0.03 | +0.14 | +0.09 |
| - | - | cov (us-them) ~avg | -0.22 | +0.03 | +0.08 | +0.03 | +0.03 | +0.05 | +0.08 |
| - | r400 | died (us-them) [inverted] | -0.49 | . | . | +0.00 | -0.36 | -0.28 | -0.46 |
| - | r450 | died (us-them) [inverted] ~avg | -0.52 | . | . | +0.00 | -0.22 | -0.32 | -0.51 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | moves (us-them) | -0.30 | +0.05 | +0.04 | -0.08 | -0.06 | +0.01 | -0.05 |
| - | - | moves (us-them) ~avg | -0.30 | -0.01 | +0.05 | -0.03 | -0.05 | -0.03 | -0.01 |
| - | r300 | soup (us-them) | -0.53 | +0.04 | -0.27 | -0.46 | -0.06 | -0.00 | -0.43 |
| - | r50 | soup (us-them) ~avg | -0.45 | -0.30 | -0.17 | -0.25 | -0.29 | -0.23 | -0.34 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
