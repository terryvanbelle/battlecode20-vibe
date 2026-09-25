# Which metric starts predicting the result first

43 games, 15 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.41 | +0.29 | +0.31 | +0.31 | +0.20 | +0.32 | +0.39 |
| r50 | - | miners (us-them) ~avg | +0.41 | +0.39 | +0.36 | +0.40 | +0.36 | +0.36 | +0.38 |
| r50 | - | mines (us-them) | +0.59 | +0.59 | +0.56 | +0.46 | +0.44 | +0.51 | +0.52 |
| r50 | - | mines (us-them) ~avg | +0.57 | +0.56 | +0.57 | +0.54 | +0.50 | +0.52 | +0.53 |
| r50 | - | robots (us-them) | +0.62 | +0.49 | +0.46 | +0.38 | +0.41 | +0.60 | +0.52 |
| r50 | - | robots (us-them) ~avg | +0.61 | +0.51 | +0.51 | +0.48 | +0.45 | +0.57 | +0.60 |
| r50 | - | spawned (us-them) | +0.63 | +0.49 | +0.47 | +0.43 | +0.43 | +0.62 | +0.56 |
| r50 | - | spawned (us-them) ~avg | +0.63 | +0.51 | +0.52 | +0.50 | +0.48 | +0.59 | +0.63 |
| r50 | - | units (us-them) | +0.65 | +0.38 | +0.35 | +0.27 | +0.28 | +0.53 | +0.58 |
| r50 | - | units (us-them) ~avg | +0.63 | +0.43 | +0.42 | +0.36 | +0.32 | +0.47 | +0.63 |
| r50 | - | worth (us-them) | +0.56 | +0.55 | +0.54 | +0.46 | +0.46 | +0.55 | +0.43 |
| r50 | - | worth (us-them) ~avg | +0.57 | +0.53 | +0.57 | +0.53 | +0.50 | +0.54 | +0.51 |
| r100 | - | drones (us-them) ~avg | +0.34 | +0.31 | +0.34 | +0.31 | +0.28 | +0.25 | +0.27 |
| r150 | - | pickups (us-them) ~avg | +0.39 | +0.19 | +0.32 | +0.35 | +0.37 | +0.35 | +0.39 |
| r200 | - | drones (us-them) | +0.31 | +0.29 | +0.31 | +0.26 | +0.19 | +0.13 | +0.29 |
| r250 | - | moves (us-them) | +0.42 | -0.12 | +0.17 | +0.37 | +0.33 | +0.40 | +0.42 |
| r250 | - | pickups (us-them) | +0.37 | +0.19 | +0.29 | +0.37 | +0.37 | +0.29 | +0.37 |
| r350 | - | moves (us-them) ~avg | +0.42 | -0.17 | +0.07 | +0.29 | +0.33 | +0.35 | +0.41 |
| r350 | - | vaporators (us-them) | +0.45 | +0.23 | +0.19 | +0.28 | +0.32 | +0.45 | +0.30 |
| r450 | - | landscapers (us-them) | +0.60 | +0.09 | +0.17 | +0.10 | +0.21 | +0.47 | +0.59 |
| r450 | - | vaporators (us-them) ~avg | +0.42 | +0.23 | +0.16 | +0.23 | +0.28 | +0.41 | +0.36 |
| r500 | - | cov (us-them) | +0.38 | -0.23 | -0.01 | +0.23 | +0.25 | +0.37 | +0.34 |
| r500 | - | cov (us-them) ~avg | +0.38 | -0.22 | -0.10 | +0.05 | +0.14 | +0.38 | +0.36 |
| r550 | - | netguns (us-them) | +0.39 | . | +0.11 | +0.22 | +0.26 | +0.39 | +0.32 |
| r550 | - | netguns (us-them) ~avg | +0.38 | . | +0.11 | +0.18 | +0.24 | +0.35 | +0.36 |
| r600 | - | landscapers (us-them) ~avg | +0.62 | +0.09 | +0.20 | +0.12 | +0.15 | +0.34 | +0.61 |
| r700 | - | digs (us-them) | +0.57 | +0.22 | +0.19 | +0.09 | +0.08 | +0.19 | +0.47 |
| r700 | - | dirtDeps (us-them) | +0.56 | +0.18 | +0.13 | +0.06 | +0.05 | +0.17 | +0.45 |
| r800 | - | digs (us-them) ~avg | +0.49 | +0.22 | +0.21 | +0.11 | +0.09 | +0.12 | +0.37 |
| r850 | - | dirtDeps (us-them) ~avg | +0.47 | +0.18 | +0.15 | +0.06 | +0.05 | +0.09 | +0.34 |
| - | - | aba (us-them) [inverted] | +0.26 | +0.15 | +0.17 | +0.03 | +0.18 | +0.16 | +0.03 |
| - | - | aba (us-them) [inverted] ~avg | +0.21 | +0.15 | +0.20 | +0.14 | +0.16 | +0.17 | +0.10 |
| - | - | died (us-them) [inverted] | +0.17 | . | +0.01 | +0.10 | +0.17 | +0.10 | -0.06 |
| - | - | died (us-them) [inverted] ~avg | +0.15 | . | -0.03 | +0.07 | +0.12 | +0.14 | +0.07 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.22 | +0.00 | . | . | . | +0.11 | +0.11 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.14 | +0.00 | +0.05 | -0.00 | -0.06 | +0.02 | +0.13 |
| - | - | soup (us-them) | +0.23 | -0.13 | +0.23 | +0.16 | +0.05 | +0.02 | -0.09 |
| - | - | soup (us-them) ~avg | -0.17 | -0.17 | +0.09 | +0.14 | +0.11 | +0.03 | -0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
