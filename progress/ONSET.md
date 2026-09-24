# Which metric starts predicting the result first

23 games, 11 wins. Noise floor about 0.42; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | died (us-them) [inverted] | +0.55 | +0.32 | +0.20 | +0.07 | +0.22 | +0.40 | +0.49 |
| r100 | - | died (us-them) [inverted] ~avg | +0.53 | +0.30 | +0.30 | +0.07 | +0.15 | +0.28 | +0.53 |
| r100 | - | vaporators (us-them) ~avg | +0.53 | +0.37 | +0.19 | +0.26 | +0.29 | +0.40 | +0.48 |
| r150 | - | drones (us-them) | +0.60 | +0.25 | +0.53 | +0.59 | +0.59 | +0.36 | +0.40 |
| r150 | - | drones (us-them) ~avg | +0.62 | +0.25 | +0.44 | +0.58 | +0.60 | +0.53 | +0.44 |
| r200 | - | robots (us-them) | +0.64 | -0.20 | +0.36 | +0.57 | +0.51 | +0.50 | +0.64 |
| r200 | - | spawned (us-them) | +0.60 | -0.23 | +0.33 | +0.60 | +0.51 | +0.44 | +0.56 |
| r200 | - | worth (us-them) | +0.59 | +0.14 | +0.35 | +0.47 | +0.49 | +0.52 | +0.58 |
| r250 | - | landscapers (us-them) | +0.55 | -0.34 | +0.23 | +0.26 | +0.20 | +0.45 | +0.55 |
| r250 | - | units (us-them) | +0.61 | -0.22 | +0.26 | +0.57 | +0.53 | +0.48 | +0.60 |
| r250 | - | worth (us-them) ~avg | +0.56 | +0.16 | +0.29 | +0.42 | +0.46 | +0.51 | +0.55 |
| r300 | - | cov (us-them) | +0.56 | -0.03 | +0.09 | +0.33 | +0.38 | +0.56 | +0.49 |
| r300 | - | miners (us-them) | +0.47 | -0.05 | -0.08 | +0.34 | +0.28 | +0.28 | +0.43 |
| r300 | - | mines (us-them) | +0.55 | +0.03 | +0.05 | +0.49 | +0.54 | +0.41 | +0.39 |
| r300 | - | mines (us-them) ~avg | +0.52 | +0.06 | +0.05 | +0.41 | +0.49 | +0.51 | +0.43 |
| r300 | - | robots (us-them) ~avg | +0.60 | -0.16 | +0.13 | +0.44 | +0.50 | +0.51 | +0.59 |
| r300 | - | spawned (us-them) ~avg | +0.55 | -0.20 | +0.09 | +0.46 | +0.52 | +0.50 | +0.51 |
| r300 | - | units (us-them) ~avg | +0.56 | -0.22 | +0.01 | +0.36 | +0.49 | +0.51 | +0.56 |
| r400 | - | pickups (us-them) | +0.49 | . | -0.01 | +0.06 | +0.40 | +0.49 | +0.36 |
| r400 | - | vaporators (us-them) | +0.56 | +0.37 | +0.05 | +0.22 | +0.31 | +0.48 | +0.54 |
| r450 | - | cov (us-them) ~avg | +0.52 | -0.01 | +0.03 | +0.19 | +0.29 | +0.42 | +0.52 |
| r450 | - | pickups (us-them) ~avg | +0.50 | . | -0.01 | +0.01 | +0.24 | +0.49 | +0.43 |
| r500 | - | moves (us-them) | +0.50 | -0.02 | -0.02 | +0.14 | +0.23 | +0.40 | +0.49 |
| r550 | - | miners (us-them) ~avg | +0.42 | -0.09 | -0.12 | +0.15 | +0.27 | +0.31 | +0.35 |
| r600 | - | landscapers (us-them) ~avg | +0.51 | -0.34 | -0.02 | +0.10 | +0.19 | +0.33 | +0.49 |
| r600 | - | moves (us-them) ~avg | +0.49 | +0.02 | -0.01 | +0.07 | +0.15 | +0.31 | +0.45 |
| r750 | r150 | digs (us-them) | +0.52 | -0.04 | -0.37 | -0.18 | -0.02 | +0.17 | +0.45 |
| r750 | r150 | dirtDeps (us-them) | +0.50 | -0.03 | -0.37 | -0.21 | -0.08 | +0.13 | +0.42 |
| r1050 | r150 | digs (us-them) ~avg | +0.40 | -0.04 | -0.38 | -0.30 | -0.15 | +0.01 | +0.27 |
| r1100 | r150 | dirtDeps (us-them) ~avg | +0.37 | -0.03 | -0.37 | -0.31 | -0.19 | -0.04 | +0.23 |
| - | r700 | aba (us-them) [inverted] | -0.56 | -0.31 | -0.20 | -0.01 | -0.09 | -0.18 | -0.51 |
| - | r800 | aba (us-them) [inverted] ~avg | -0.52 | -0.24 | -0.19 | -0.06 | -0.08 | -0.19 | -0.38 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | . | . | . | . | . | +0.19 |
| - | - | netguns (us-them) | -0.20 | . | . | . | . | +0.00 | +0.02 |
| - | - | netguns (us-them) ~avg | -0.20 | . | . | . | . | -0.07 | +0.03 |
| - | - | soup (us-them) | +0.29 | +0.22 | -0.07 | -0.10 | +0.20 | -0.06 | -0.14 |
| - | - | soup (us-them) ~avg | +0.27 | +0.27 | +0.10 | -0.02 | +0.03 | +0.05 | -0.12 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
