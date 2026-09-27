# Which metric starts predicting the result first

46 games, 26 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.34 | +0.27 | +0.00 | -0.17 | +0.02 | -0.01 | -0.07 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.34 | +0.30 | +0.09 | -0.14 | -0.06 | -0.00 | -0.03 |
| r50 | - | mines (us-them) | +0.55 | +0.39 | +0.32 | +0.41 | +0.44 | +0.54 | +0.50 |
| r50 | - | mines (us-them) ~avg | +0.55 | +0.38 | +0.31 | +0.34 | +0.40 | +0.50 | +0.51 |
| r100 | - | hqBuried (us-them) [inverted] | +0.39 | +0.33 | +0.39 | +0.24 | +0.24 | +0.24 | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.33 | +0.32 | +0.25 | +0.22 | +0.21 | -0.19 |
| r100 | - | robots (us-them) ~avg | +0.63 | +0.31 | +0.38 | +0.49 | +0.57 | +0.63 | +0.59 |
| r100 | - | spawned (us-them) ~avg | +0.66 | +0.31 | +0.38 | +0.49 | +0.59 | +0.66 | +0.61 |
| r100 | - | units (us-them) | +0.63 | +0.38 | +0.40 | +0.40 | +0.55 | +0.53 | +0.34 |
| r100 | - | units (us-them) ~avg | +0.62 | +0.35 | +0.38 | +0.47 | +0.54 | +0.61 | +0.54 |
| r100 | - | worth (us-them) | +0.44 | +0.31 | +0.30 | +0.30 | +0.40 | +0.43 | +0.40 |
| r150 | - | drones (us-them) | +0.32 | +0.29 | +0.28 | +0.32 | +0.27 | +0.18 | +0.01 |
| r150 | - | drones (us-them) ~avg | +0.41 | +0.25 | +0.40 | +0.41 | +0.36 | +0.26 | +0.06 |
| r150 | - | worth (us-them) ~avg | +0.42 | +0.29 | +0.29 | +0.26 | +0.37 | +0.41 | +0.38 |
| r200 | - | miners (us-them) | +0.54 | +0.14 | +0.36 | +0.38 | +0.31 | +0.22 | +0.23 |
| r200 | - | miners (us-them) ~avg | +0.56 | +0.18 | +0.39 | +0.56 | +0.47 | +0.39 | +0.29 |
| r200 | - | moves (us-them) | +0.48 | -0.04 | +0.37 | +0.36 | +0.38 | +0.46 | +0.47 |
| r200 | - | moves (us-them) ~avg | +0.46 | -0.10 | +0.38 | +0.42 | +0.40 | +0.41 | +0.45 |
| r200 | - | pickups (us-them) | +0.45 | -0.00 | +0.31 | +0.41 | +0.45 | +0.37 | +0.37 |
| r200 | - | robots (us-them) | +0.64 | +0.31 | +0.43 | +0.46 | +0.57 | +0.59 | +0.54 |
| r200 | - | spawned (us-them) | +0.64 | +0.31 | +0.42 | +0.46 | +0.61 | +0.62 | +0.56 |
| r250 | - | cov (us-them) | +0.34 | -0.03 | +0.25 | +0.34 | +0.25 | +0.28 | +0.25 |
| r250 | - | pickups (us-them) ~avg | +0.49 | -0.07 | +0.28 | +0.40 | +0.49 | +0.41 | +0.36 |
| r300 | - | digs (us-them) | +0.61 | +0.29 | +0.19 | +0.30 | +0.44 | +0.54 | +0.59 |
| r300 | - | dirtDeps (us-them) | +0.60 | +0.28 | +0.18 | +0.32 | +0.43 | +0.53 | +0.58 |
| r350 | - | digs (us-them) ~avg | +0.61 | +0.29 | +0.18 | +0.25 | +0.36 | +0.47 | +0.58 |
| r350 | - | dirtDeps (us-them) ~avg | +0.60 | +0.28 | +0.16 | +0.25 | +0.36 | +0.47 | +0.57 |
| r350 | - | landscapers (us-them) | +0.63 | +0.23 | +0.19 | +0.17 | +0.46 | +0.59 | +0.53 |
| r450 | - | landscapers (us-them) ~avg | +0.64 | +0.24 | +0.11 | +0.12 | +0.30 | +0.58 | +0.64 |
| r550 | - | netguns (us-them) | +0.45 | . | +0.21 | +0.21 | +0.11 | +0.26 | +0.45 |
| r700 | - | vaporators (us-them) | +0.32 | +0.10 | +0.02 | -0.04 | +0.06 | +0.24 | +0.32 |
| r850 | - | netguns (us-them) ~avg | +0.39 | . | +0.21 | +0.23 | +0.16 | +0.24 | +0.34 |
| - | - | cov (us-them) ~avg | +0.29 | -0.05 | +0.15 | +0.26 | +0.27 | +0.29 | +0.27 |
| - | - | died (us-them) [inverted] | +0.25 | . | +0.20 | +0.10 | -0.01 | +0.04 | +0.06 |
| - | - | died (us-them) [inverted] ~avg | +0.18 | . | +0.13 | +0.18 | +0.09 | +0.07 | +0.07 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | soup (us-them) | -0.25 | -0.17 | -0.19 | -0.13 | -0.20 | -0.18 | -0.25 |
| - | - | soup (us-them) ~avg | -0.22 | -0.16 | -0.16 | -0.21 | -0.19 | -0.21 | -0.22 |
| - | - | vaporators (us-them) ~avg | +0.30 | +0.10 | +0.03 | -0.06 | -0.01 | +0.11 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
