# Which metric starts predicting the result first

19 games, 6 wins. Noise floor about 0.46; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r950 | aba (us-them) [inverted] | +0.44 | +0.44 | +0.18 | +0.33 | +0.34 | +0.02 | -0.20 |
| r50 | r1150 | aba (us-them) [inverted] ~avg | +0.45 | +0.45 | +0.24 | +0.36 | +0.38 | +0.05 | -0.08 |
| r50 | - | mines (us-them) | +0.52 | +0.37 | +0.50 | +0.45 | +0.46 | +0.36 | +0.39 |
| r50 | - | mines (us-them) ~avg | +0.49 | +0.36 | +0.49 | +0.46 | +0.48 | +0.39 | +0.40 |
| r50 | - | robots (us-them) | +0.51 | +0.28 | +0.37 | +0.44 | +0.50 | +0.46 | +0.44 |
| r50 | - | robots (us-them) ~avg | +0.51 | +0.31 | +0.42 | +0.44 | +0.48 | +0.44 | +0.47 |
| r50 | - | spawned (us-them) | +0.49 | +0.28 | +0.37 | +0.41 | +0.46 | +0.44 | +0.45 |
| r50 | - | spawned (us-them) ~avg | +0.51 | +0.31 | +0.41 | +0.44 | +0.46 | +0.42 | +0.46 |
| r50 | - | worth (us-them) | +0.62 | +0.49 | +0.62 | +0.57 | +0.58 | +0.54 | +0.47 |
| r50 | - | worth (us-them) ~avg | +0.61 | +0.51 | +0.61 | +0.57 | +0.59 | +0.54 | +0.53 |
| r150 | - | landscapers (us-them) | +0.65 | +0.18 | +0.30 | +0.27 | +0.34 | +0.57 | +0.56 |
| r150 | - | landscapers (us-them) ~avg | +0.61 | +0.18 | +0.37 | +0.30 | +0.32 | +0.53 | +0.59 |
| r150 | - | units (us-them) | +0.53 | +0.25 | +0.27 | +0.33 | +0.40 | +0.41 | +0.46 |
| r150 | - | units (us-them) ~avg | +0.49 | +0.27 | +0.35 | +0.36 | +0.39 | +0.38 | +0.45 |
| r200 | - | soup (us-them) | +0.76 | +0.16 | +0.48 | +0.32 | +0.36 | +0.16 | +0.54 |
| r200 | - | soup (us-them) ~avg | +0.84 | +0.22 | +0.35 | +0.43 | +0.53 | +0.56 | +0.81 |
| r250 | - | pickups (us-them) | +0.46 | . | +0.29 | +0.38 | +0.39 | +0.28 | +0.41 |
| r250 | - | pickups (us-them) ~avg | +0.45 | . | +0.25 | +0.34 | +0.40 | +0.32 | +0.38 |
| r250 | - | vaporators (us-them) | +0.52 | +0.22 | +0.27 | +0.33 | +0.40 | +0.52 | +0.37 |
| r300 | r50 | cov (us-them) | +0.50 | -0.27 | -0.14 | +0.36 | +0.50 | +0.39 | +0.43 |
| r300 | r1000 | died (us-them) [inverted] | +0.47 | . | +0.00 | +0.36 | +0.47 | +0.22 | -0.05 |
| r300 | - | moves (us-them) | +0.36 | -0.14 | +0.03 | +0.35 | +0.36 | +0.26 | +0.31 |
| r350 | - | died (us-them) [inverted] ~avg | +0.45 | . | +0.00 | +0.26 | +0.43 | +0.32 | +0.09 |
| r350 | - | drones (us-them) | +0.37 | +0.15 | +0.22 | +0.29 | +0.33 | +0.18 | +0.34 |
| r350 | - | vaporators (us-them) ~avg | +0.50 | +0.22 | +0.24 | +0.28 | +0.33 | +0.46 | +0.47 |
| r400 | r50 | cov (us-them) ~avg | +0.48 | -0.31 | -0.29 | +0.09 | +0.32 | +0.41 | +0.43 |
| r400 | - | drones (us-them) ~avg | +0.37 | +0.15 | +0.22 | +0.26 | +0.32 | +0.22 | +0.31 |
| r400 | - | moves (us-them) ~avg | +0.36 | -0.20 | -0.06 | +0.23 | +0.32 | +0.28 | +0.31 |
| r600 | - | digs (us-them) | +0.62 | +0.18 | +0.21 | +0.16 | +0.20 | +0.41 | +0.61 |
| r600 | - | dirtDeps (us-them) | +0.61 | +0.24 | +0.19 | +0.09 | +0.12 | +0.33 | +0.60 |
| r650 | - | netguns (us-them) | +0.45 | . | . | . | -0.06 | -0.02 | +0.43 |
| r700 | - | miners (us-them) | +0.41 | +0.16 | -0.02 | +0.14 | +0.25 | +0.26 | +0.33 |
| r750 | - | digs (us-them) ~avg | +0.60 | +0.18 | +0.23 | +0.16 | +0.18 | +0.16 | +0.51 |
| r800 | - | dirtDeps (us-them) ~avg | +0.58 | +0.24 | +0.22 | +0.11 | +0.11 | +0.07 | +0.44 |
| r850 | - | miners (us-them) ~avg | +0.37 | +0.23 | +0.13 | +0.18 | +0.22 | +0.24 | +0.32 |
| r900 | - | netguns (us-them) ~avg | +0.35 | . | . | . | -0.12 | -0.05 | +0.31 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.16 | . | . | . | +0.14 | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | +0.16 | +0.15 | +0.15 | +0.14 | +0.15 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
