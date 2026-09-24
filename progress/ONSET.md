# Which metric starts predicting the result first

30 games, 15 wins. Noise floor about 0.37; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.38 | +0.25 | +0.16 | +0.01 | +0.18 | +0.22 | +0.26 |
| r50 | - | miners (us-them) ~avg | +0.38 | +0.37 | +0.27 | +0.21 | +0.21 | +0.24 | +0.27 |
| r200 | - | died (us-them) [inverted] ~avg | +0.32 | +0.19 | +0.32 | +0.15 | +0.04 | +0.12 | +0.18 |
| r200 | - | drones (us-them) | +0.52 | +0.00 | +0.38 | +0.52 | +0.42 | +0.45 | +0.45 |
| r200 | - | drones (us-them) ~avg | +0.47 | +0.00 | +0.31 | +0.43 | +0.46 | +0.46 | +0.45 |
| r200 | - | vaporators (us-them) ~avg | +0.31 | +0.27 | +0.31 | +0.29 | +0.20 | +0.22 | +0.25 |
| r200 | - | worth (us-them) | +0.51 | -0.26 | +0.38 | +0.47 | +0.42 | +0.46 | +0.46 |
| r250 | r50 | mines (us-them) | +0.45 | -0.25 | +0.17 | +0.39 | +0.41 | +0.43 | +0.45 |
| r250 | - | pickups (us-them) | +0.55 | . | +0.22 | +0.40 | +0.45 | +0.47 | +0.52 |
| r250 | - | pickups (us-them) ~avg | +0.54 | . | +0.22 | +0.36 | +0.41 | +0.48 | +0.52 |
| r250 | - | robots (us-them) | +0.60 | -0.08 | +0.29 | +0.34 | +0.45 | +0.55 | +0.60 |
| r250 | - | soup (us-them) | +0.40 | -0.12 | +0.06 | +0.36 | +0.21 | -0.03 | -0.15 |
| r250 | - | spawned (us-them) | +0.61 | -0.09 | +0.26 | +0.37 | +0.47 | +0.57 | +0.61 |
| r300 | - | robots (us-them) ~avg | +0.56 | -0.02 | +0.18 | +0.31 | +0.40 | +0.49 | +0.56 |
| r300 | - | worth (us-them) ~avg | +0.48 | -0.28 | +0.15 | +0.37 | +0.42 | +0.45 | +0.48 |
| r350 | - | spawned (us-them) ~avg | +0.56 | -0.05 | +0.15 | +0.29 | +0.40 | +0.48 | +0.56 |
| r350 | - | units (us-them) | +0.67 | +0.02 | +0.17 | +0.24 | +0.38 | +0.50 | +0.62 |
| r400 | - | units (us-them) ~avg | +0.58 | +0.18 | +0.16 | +0.23 | +0.31 | +0.41 | +0.55 |
| r450 | - | landscapers (us-them) | +0.62 | -0.22 | -0.03 | +0.16 | +0.24 | +0.36 | +0.57 |
| r450 | r50 | mines (us-them) ~avg | +0.44 | -0.31 | -0.06 | +0.18 | +0.30 | +0.37 | +0.44 |
| r550 | - | cov (us-them) | +0.47 | -0.13 | -0.21 | -0.03 | +0.15 | +0.35 | +0.38 |
| r700 | - | landscapers (us-them) ~avg | +0.53 | -0.22 | -0.13 | -0.01 | +0.10 | +0.21 | +0.45 |
| r900 | - | netguns (us-them) | +0.48 | . | . | +0.25 | +0.20 | +0.14 | +0.30 |
| r1000 | r100 | digs (us-them) | -0.39 | -0.39 | -0.28 | -0.20 | -0.14 | +0.03 | +0.28 |
| r1050 | r100 | dirtDeps (us-them) | -0.42 | -0.36 | -0.30 | -0.21 | -0.16 | +0.00 | +0.26 |
| r1100 | - | cov (us-them) ~avg | +0.33 | -0.13 | -0.20 | -0.14 | -0.04 | +0.14 | +0.26 |
| r1100 | - | moves (us-them) | +0.37 | +0.19 | +0.07 | +0.03 | +0.11 | +0.07 | +0.23 |
| - | r1000 | aba (us-them) [inverted] | -0.35 | -0.14 | -0.29 | -0.18 | -0.21 | -0.17 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | -0.14 | -0.25 | -0.22 | -0.22 | -0.19 | -0.19 |
| - | - | died (us-them) [inverted] | +0.37 | +0.19 | +0.37 | -0.02 | -0.02 | +0.07 | +0.13 |
| - | r100 | digs (us-them) ~avg | -0.40 | -0.39 | -0.32 | -0.25 | -0.20 | -0.13 | +0.12 |
| - | r100 | dirtDeps (us-them) ~avg | -0.43 | -0.36 | -0.35 | -0.26 | -0.21 | -0.16 | +0.09 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | moves (us-them) ~avg | +0.23 | +0.21 | +0.12 | +0.06 | +0.08 | +0.08 | +0.16 |
| - | - | netguns (us-them) ~avg | +0.29 | . | . | +0.22 | +0.22 | +0.19 | +0.16 |
| - | - | soup (us-them) ~avg | +0.27 | -0.15 | -0.17 | +0.22 | +0.27 | +0.11 | -0.03 |
| - | - | vaporators (us-them) | +0.29 | +0.27 | +0.28 | +0.22 | +0.08 | +0.19 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
