# Which metric starts predicting the result first

21 games, 7 wins. Noise floor about 0.44; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.59 | +0.43 | +0.58 | +0.58 | +0.42 | +0.38 | +0.42 |
| r50 | - | mines (us-them) ~avg | +0.61 | +0.43 | +0.54 | +0.61 | +0.58 | +0.47 | +0.44 |
| r100 | - | cov (us-them) | +0.63 | +0.44 | +0.54 | +0.56 | +0.63 | +0.45 | +0.34 |
| r100 | - | digs (us-them) | +0.40 | +0.31 | +0.36 | +0.32 | +0.38 | +0.34 | +0.27 |
| r100 | - | digs (us-them) ~avg | +0.37 | +0.31 | +0.34 | +0.33 | +0.36 | +0.35 | +0.32 |
| r100 | - | drones (us-them) | +0.43 | +0.40 | +0.12 | -0.09 | -0.01 | +0.29 | +0.40 |
| r100 | - | drones (us-them) ~avg | +0.40 | +0.40 | +0.29 | +0.10 | +0.04 | +0.13 | +0.30 |
| r100 | - | moves (us-them) | +0.63 | +0.37 | +0.54 | +0.63 | +0.63 | +0.58 | +0.45 |
| r100 | - | moves (us-them) ~avg | +0.64 | +0.32 | +0.51 | +0.62 | +0.64 | +0.61 | +0.57 |
| r100 | - | robots (us-them) | +0.65 | +0.42 | +0.65 | +0.58 | +0.43 | +0.32 | +0.38 |
| r100 | - | robots (us-them) ~avg | +0.66 | +0.37 | +0.60 | +0.66 | +0.60 | +0.45 | +0.41 |
| r100 | - | spawned (us-them) | +0.66 | +0.42 | +0.66 | +0.61 | +0.47 | +0.31 | +0.36 |
| r100 | - | spawned (us-them) ~avg | +0.67 | +0.37 | +0.60 | +0.67 | +0.64 | +0.47 | +0.39 |
| r100 | - | units (us-them) | +0.66 | +0.47 | +0.66 | +0.61 | +0.48 | +0.29 | +0.33 |
| r100 | - | units (us-them) ~avg | +0.66 | +0.39 | +0.61 | +0.66 | +0.62 | +0.47 | +0.40 |
| r100 | - | worth (us-them) | +0.50 | +0.32 | +0.50 | +0.44 | +0.26 | +0.28 | +0.38 |
| r100 | - | worth (us-them) ~avg | +0.51 | +0.31 | +0.49 | +0.51 | +0.42 | +0.32 | +0.36 |
| r150 | - | cov (us-them) ~avg | +0.62 | +0.18 | +0.49 | +0.58 | +0.62 | +0.58 | +0.50 |
| r150 | - | dirtDeps (us-them) | +0.39 | +0.29 | +0.32 | +0.29 | +0.36 | +0.32 | +0.25 |
| r150 | - | dirtDeps (us-them) ~avg | +0.34 | +0.29 | +0.31 | +0.29 | +0.32 | +0.33 | +0.29 |
| r150 | - | landscapers (us-them) | +0.64 | +0.20 | +0.48 | +0.64 | +0.63 | +0.09 | +0.16 |
| r150 | - | landscapers (us-them) ~avg | +0.60 | +0.20 | +0.37 | +0.49 | +0.57 | +0.50 | +0.25 |
| r150 | - | miners (us-them) | +0.49 | +0.25 | +0.47 | +0.44 | +0.36 | +0.42 | +0.34 |
| r200 | - | miners (us-them) ~avg | +0.54 | +0.22 | +0.41 | +0.54 | +0.49 | +0.46 | +0.47 |
| r250 | - | soup (us-them) | +0.30 | +0.09 | +0.26 | +0.28 | +0.09 | -0.27 | +0.01 |
| r300 | - | soup (us-them) ~avg | +0.34 | +0.03 | +0.19 | +0.34 | +0.26 | +0.01 | -0.08 |
| r600 | r850 | netguns (us-them) | +0.44 | . | . | . | . | +0.44 | -0.30 |
| r600 | - | netguns (us-them) ~avg | +0.44 | . | . | . | . | +0.44 | +0.42 |
| r700 | r100 | vaporators (us-them) | +0.37 | -0.36 | -0.27 | -0.16 | -0.08 | +0.23 | +0.34 |
| r1050 | - | pickups (us-them) | +0.35 | . | +0.21 | -0.01 | -0.20 | -0.03 | +0.23 |
| r1150 | r100 | vaporators (us-them) ~avg | -0.36 | -0.36 | -0.31 | -0.23 | -0.15 | +0.05 | +0.22 |
| - | - | aba (us-them) [inverted] | +0.27 | +0.08 | -0.10 | -0.02 | +0.12 | +0.21 | +0.26 |
| - | - | aba (us-them) [inverted] ~avg | +0.27 | +0.16 | -0.07 | -0.07 | +0.01 | +0.15 | +0.24 |
| - | - | died (us-them) [inverted] | +0.28 | . | +0.08 | -0.01 | -0.12 | +0.10 | -0.02 |
| - | - | died (us-them) [inverted] ~avg | +0.22 | . | +0.08 | +0.05 | -0.05 | -0.02 | -0.01 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | +0.17 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.17 | . | . | . | . |
| - | - | pickups (us-them) ~avg | +0.21 | . | +0.21 | +0.07 | -0.12 | -0.10 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
