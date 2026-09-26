# Which metric starts predicting the result first

47 games, 29 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.44 | +0.31 | +0.28 | +0.25 | +0.23 | +0.44 | +0.41 |
| r100 | - | robots (us-them) | +0.67 | +0.31 | +0.37 | +0.49 | +0.49 | +0.63 | +0.66 |
| r100 | - | spawned (us-them) | +0.56 | +0.32 | +0.36 | +0.32 | +0.31 | +0.41 | +0.52 |
| r150 | - | landscapers (us-them) | +0.40 | +0.19 | +0.39 | +0.37 | +0.25 | +0.35 | +0.37 |
| r150 | - | landscapers (us-them) ~avg | +0.44 | +0.19 | +0.34 | +0.39 | +0.32 | +0.36 | +0.42 |
| r150 | - | robots (us-them) ~avg | +0.69 | +0.26 | +0.38 | +0.44 | +0.45 | +0.59 | +0.67 |
| r150 | - | spawned (us-them) ~avg | +0.52 | +0.27 | +0.37 | +0.37 | +0.32 | +0.37 | +0.46 |
| r150 | - | units (us-them) | +0.64 | +0.24 | +0.32 | +0.45 | +0.44 | +0.58 | +0.63 |
| r150 | - | units (us-them) ~avg | +0.67 | +0.22 | +0.33 | +0.40 | +0.40 | +0.53 | +0.65 |
| r250 | - | cov (us-them) | +0.57 | +0.10 | +0.21 | +0.42 | +0.49 | +0.56 | +0.51 |
| r300 | - | died (us-them) [inverted] | +0.49 | -0.12 | +0.03 | +0.41 | +0.40 | +0.49 | +0.35 |
| r300 | - | died (us-them) [inverted] ~avg | +0.45 | -0.12 | +0.07 | +0.34 | +0.40 | +0.44 | +0.42 |
| r300 | - | miners (us-them) | +0.58 | +0.11 | +0.09 | +0.34 | +0.39 | +0.50 | +0.57 |
| r300 | - | worth (us-them) | +0.59 | +0.21 | +0.27 | +0.42 | +0.38 | +0.57 | +0.58 |
| r300 | - | worth (us-them) ~avg | +0.58 | +0.16 | +0.27 | +0.35 | +0.35 | +0.49 | +0.57 |
| r350 | - | cov (us-them) ~avg | +0.57 | +0.06 | +0.12 | +0.30 | +0.36 | +0.50 | +0.55 |
| r400 | - | drones (us-them) | +0.51 | +0.06 | +0.05 | +0.16 | +0.42 | +0.40 | +0.45 |
| r400 | - | pickups (us-them) | +0.69 | -0.21 | -0.10 | +0.25 | +0.45 | +0.69 | +0.53 |
| r450 | - | drones (us-them) ~avg | +0.49 | +0.06 | +0.14 | +0.12 | +0.23 | +0.44 | +0.48 |
| r450 | - | miners (us-them) ~avg | +0.53 | +0.13 | +0.16 | +0.21 | +0.29 | +0.40 | +0.51 |
| r450 | - | pickups (us-them) ~avg | +0.64 | -0.21 | -0.16 | +0.09 | +0.29 | +0.60 | +0.62 |
| r550 | - | mines (us-them) ~avg | +0.42 | +0.26 | +0.29 | +0.28 | +0.23 | +0.34 | +0.40 |
| r600 | - | moves (us-them) | +0.57 | -0.12 | +0.12 | +0.18 | +0.20 | +0.33 | +0.51 |
| r700 | - | digs (us-them) | +0.31 | -0.07 | +0.08 | +0.24 | +0.23 | +0.28 | +0.30 |
| r700 | - | moves (us-them) ~avg | +0.51 | -0.13 | +0.08 | +0.15 | +0.16 | +0.25 | +0.41 |
| r700 | - | vaporators (us-them) | +0.44 | -0.01 | -0.19 | -0.11 | -0.05 | +0.23 | +0.41 |
| r800 | - | digs (us-them) ~avg | +0.31 | -0.07 | +0.07 | +0.18 | +0.22 | +0.26 | +0.30 |
| r1050 | - | netguns (us-them) | +0.35 | . | -0.21 | -0.17 | -0.14 | +0.14 | +0.12 |
| r1050 | - | vaporators (us-them) ~avg | +0.38 | -0.01 | -0.16 | -0.15 | -0.13 | +0.02 | +0.24 |
| - | - | aba (us-them) [inverted] | +0.28 | +0.28 | -0.18 | +0.02 | +0.17 | +0.12 | +0.06 |
| - | - | aba (us-them) [inverted] ~avg | +0.30 | +0.30 | -0.09 | -0.07 | +0.07 | +0.13 | +0.09 |
| - | - | dirtDeps (us-them) | +0.30 | -0.04 | +0.08 | +0.23 | +0.21 | +0.27 | +0.28 |
| - | - | dirtDeps (us-them) ~avg | +0.30 | -0.04 | +0.08 | +0.17 | +0.20 | +0.25 | +0.29 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.29 | +0.16 | +0.12 | +0.26 | +0.14 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.29 | +0.16 | +0.02 | +0.25 | +0.17 | +0.18 | +0.19 |
| - | - | netguns (us-them) ~avg | -0.23 | . | -0.18 | -0.21 | -0.21 | -0.10 | +0.01 |
| - | - | soup (us-them) | -0.34 | -0.34 | -0.22 | -0.08 | -0.07 | -0.07 | -0.04 |
| - | r200 | soup (us-them) ~avg | -0.41 | -0.30 | -0.41 | -0.28 | -0.16 | -0.13 | -0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
