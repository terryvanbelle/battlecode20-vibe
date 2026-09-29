# Which metric starts predicting the result first

47 games, 33 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | units (us-them) | +0.38 | +0.34 | +0.38 | +0.21 | +0.27 | +0.30 | +0.31 |
| r150 | - | miners (us-them) ~avg | +0.31 | +0.25 | +0.31 | +0.13 | +0.06 | -0.01 | -0.09 |
| r150 | - | mines (us-them) | +0.33 | +0.25 | +0.32 | +0.26 | +0.28 | +0.23 | +0.15 |
| r150 | - | mines (us-them) ~avg | +0.38 | +0.26 | +0.36 | +0.34 | +0.33 | +0.30 | +0.23 |
| r150 | - | moves (us-them) | +0.33 | +0.28 | +0.30 | +0.17 | +0.09 | +0.10 | +0.12 |
| r150 | - | moves (us-them) ~avg | +0.31 | +0.19 | +0.31 | +0.25 | +0.18 | +0.12 | +0.06 |
| r150 | - | pickups (us-them) | +0.52 | +0.17 | +0.34 | +0.43 | +0.43 | +0.38 | +0.25 |
| r150 | - | pickups (us-them) ~avg | +0.54 | +0.17 | +0.39 | +0.51 | +0.48 | +0.44 | +0.32 |
| r150 | - | robots (us-them) | +0.41 | +0.20 | +0.40 | +0.33 | +0.34 | +0.39 | +0.35 |
| r150 | - | spawned (us-them) | +0.35 | +0.22 | +0.35 | +0.26 | +0.31 | +0.29 | +0.29 |
| r150 | - | units (us-them) ~avg | +0.40 | +0.30 | +0.37 | +0.35 | +0.33 | +0.32 | +0.32 |
| r150 | - | worth (us-them) | +0.52 | +0.23 | +0.47 | +0.49 | +0.48 | +0.52 | +0.38 |
| r150 | - | worth (us-them) ~avg | +0.53 | +0.25 | +0.45 | +0.52 | +0.51 | +0.53 | +0.44 |
| r200 | - | drones (us-them) ~avg | +0.32 | +0.25 | +0.32 | +0.24 | +0.14 | +0.01 | +0.09 |
| r200 | - | robots (us-them) ~avg | +0.41 | +0.18 | +0.36 | +0.40 | +0.38 | +0.40 | +0.36 |
| r200 | - | spawned (us-them) ~avg | +0.37 | +0.21 | +0.34 | +0.34 | +0.34 | +0.33 | +0.29 |
| r250 | - | landscapers (us-them) | +0.59 | +0.07 | +0.25 | +0.33 | +0.48 | +0.57 | +0.38 |
| r300 | - | digs (us-them) | +0.84 | +0.16 | +0.17 | +0.33 | +0.47 | +0.62 | +0.74 |
| r300 | - | dirtDeps (us-them) | +0.83 | +0.10 | +0.12 | +0.31 | +0.45 | +0.61 | +0.74 |
| r350 | - | digs (us-them) ~avg | +0.76 | +0.16 | +0.14 | +0.28 | +0.40 | +0.54 | +0.70 |
| r350 | - | landscapers (us-them) ~avg | +0.64 | +0.07 | +0.12 | +0.28 | +0.39 | +0.59 | +0.58 |
| r400 | - | dirtDeps (us-them) ~avg | +0.76 | +0.10 | +0.08 | +0.24 | +0.38 | +0.53 | +0.70 |
| r450 | - | vaporators (us-them) | +0.46 | -0.03 | +0.11 | +0.21 | +0.22 | +0.45 | +0.25 |
| r550 | - | netguns (us-them) | +0.50 | -0.10 | -0.10 | +0.07 | +0.11 | +0.38 | +0.29 |
| r550 | - | vaporators (us-them) ~avg | +0.37 | -0.03 | +0.10 | +0.17 | +0.19 | +0.35 | +0.29 |
| r600 | - | died (us-them) [inverted] | +0.32 | -0.17 | +0.28 | +0.17 | +0.11 | +0.31 | +0.16 |
| r950 | - | netguns (us-them) ~avg | +0.38 | -0.10 | -0.10 | -0.08 | -0.02 | +0.22 | +0.26 |
| - | r900 | aba (us-them) [inverted] | -0.42 | +0.10 | -0.06 | -0.14 | -0.10 | -0.13 | -0.31 |
| - | r1200 | aba (us-them) [inverted] ~avg | -0.31 | +0.17 | +0.04 | -0.11 | -0.10 | -0.10 | -0.21 |
| - | - | cov (us-them) | +0.20 | +0.05 | +0.20 | +0.12 | +0.07 | -0.02 | +0.00 |
| - | - | cov (us-them) ~avg | +0.10 | +0.01 | +0.10 | +0.05 | +0.07 | +0.04 | -0.01 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | -0.17 | +0.17 | +0.20 | +0.16 | +0.23 | +0.19 |
| - | - | drones (us-them) | +0.27 | +0.25 | +0.23 | +0.15 | +0.02 | -0.07 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | +0.13 | +0.13 | +0.14 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.04 | -0.18 | -0.14 | -0.06 | -0.05 |
| - | - | miners (us-them) | +0.32 | +0.29 | +0.23 | -0.10 | -0.08 | -0.06 | -0.07 |
| - | - | soup (us-them) | -0.25 | +0.08 | +0.18 | +0.09 | +0.10 | +0.09 | -0.11 |
| - | - | soup (us-them) ~avg | +0.22 | +0.13 | +0.22 | +0.20 | +0.15 | +0.10 | -0.02 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
