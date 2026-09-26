# Which metric starts predicting the result first

40 games, 22 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | landscapers (us-them) | +0.57 | +0.41 | +0.40 | +0.24 | +0.23 | +0.31 | +0.54 |
| r100 | - | landscapers (us-them) ~avg | +0.52 | +0.41 | +0.49 | +0.36 | +0.33 | +0.31 | +0.48 |
| r100 | - | robots (us-them) | +0.52 | +0.41 | +0.42 | +0.27 | +0.31 | +0.41 | +0.52 |
| r100 | - | robots (us-them) ~avg | +0.47 | +0.32 | +0.41 | +0.36 | +0.34 | +0.35 | +0.46 |
| r100 | - | spawned (us-them) | +0.55 | +0.39 | +0.38 | +0.23 | +0.33 | +0.42 | +0.55 |
| r100 | - | units (us-them) | +0.51 | +0.44 | +0.41 | +0.21 | +0.24 | +0.32 | +0.42 |
| r100 | - | units (us-them) ~avg | +0.44 | +0.37 | +0.43 | +0.35 | +0.32 | +0.30 | +0.40 |
| r100 | - | worth (us-them) | +0.52 | +0.31 | +0.39 | +0.48 | +0.51 | +0.50 | +0.44 |
| r150 | - | died (us-them) [inverted] | +0.31 | +0.23 | +0.29 | +0.25 | +0.13 | +0.19 | +0.19 |
| r150 | - | died (us-them) [inverted] ~avg | +0.37 | +0.27 | +0.37 | +0.32 | +0.25 | +0.19 | +0.21 |
| r150 | - | digs (us-them) | +0.64 | +0.06 | +0.49 | +0.57 | +0.58 | +0.62 | +0.63 |
| r150 | - | digs (us-them) ~avg | +0.64 | +0.11 | +0.50 | +0.58 | +0.59 | +0.61 | +0.64 |
| r150 | - | dirtDeps (us-them) | +0.63 | +0.13 | +0.50 | +0.62 | +0.62 | +0.62 | +0.62 |
| r150 | - | dirtDeps (us-them) ~avg | +0.64 | +0.19 | +0.53 | +0.64 | +0.64 | +0.64 | +0.64 |
| r150 | - | spawned (us-them) ~avg | +0.49 | +0.29 | +0.37 | +0.32 | +0.32 | +0.35 | +0.47 |
| r150 | - | worth (us-them) ~avg | +0.48 | +0.28 | +0.35 | +0.45 | +0.48 | +0.47 | +0.47 |
| r200 | - | miners (us-them) | +0.32 | +0.21 | +0.32 | +0.17 | +0.07 | +0.19 | +0.16 |
| r250 | - | soup (us-them) | +0.43 | -0.26 | -0.06 | +0.43 | +0.39 | +0.06 | -0.16 |
| r300 | - | miners (us-them) ~avg | +0.36 | +0.19 | +0.23 | +0.36 | +0.29 | +0.23 | +0.20 |
| r300 | - | mines (us-them) | +0.47 | +0.26 | +0.11 | +0.46 | +0.46 | +0.37 | +0.26 |
| r300 | - | mines (us-them) ~avg | +0.49 | +0.22 | +0.15 | +0.45 | +0.49 | +0.44 | +0.37 |
| r300 | - | soup (us-them) ~avg | +0.48 | -0.16 | -0.09 | +0.48 | +0.46 | +0.27 | -0.04 |
| r350 | - | vaporators (us-them) | +0.49 | +0.11 | +0.07 | +0.27 | +0.37 | +0.49 | +0.33 |
| r500 | - | vaporators (us-them) ~avg | +0.42 | +0.11 | -0.02 | +0.09 | +0.23 | +0.41 | +0.38 |
| r1150 | - | drones (us-them) | +0.39 | -0.16 | -0.27 | -0.12 | +0.11 | +0.08 | +0.12 |
| - | - | aba (us-them) [inverted] | -0.28 | -0.13 | -0.18 | -0.14 | -0.13 | -0.12 | -0.20 |
| - | - | aba (us-them) [inverted] ~avg | -0.22 | -0.15 | -0.19 | -0.10 | -0.09 | -0.05 | -0.07 |
| - | - | cov (us-them) | -0.22 | -0.12 | -0.02 | -0.19 | -0.22 | -0.04 | +0.07 |
| - | - | cov (us-them) ~avg | -0.28 | -0.16 | -0.12 | -0.26 | -0.28 | -0.18 | -0.11 |
| - | - | drones (us-them) ~avg | -0.29 | -0.16 | -0.27 | -0.20 | -0.08 | +0.01 | +0.06 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.19 | +0.22 | +0.00 | . | . | +0.23 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.24 | +0.19 | +0.18 | -0.24 | -0.23 | -0.23 | -0.18 |
| - | - | moves (us-them) | +0.27 | +0.20 | +0.27 | +0.03 | +0.01 | +0.06 | +0.16 |
| - | - | moves (us-them) ~avg | +0.29 | +0.20 | +0.29 | +0.12 | +0.06 | +0.06 | +0.10 |
| - | r200 | netguns (us-them) | -0.31 | . | -0.31 | -0.21 | -0.21 | -0.09 | +0.02 |
| - | - | netguns (us-them) ~avg | -0.31 | . | -0.31 | -0.21 | -0.21 | -0.20 | -0.10 |
| - | - | pickups (us-them) | +0.22 | -0.06 | +0.08 | +0.19 | +0.03 | +0.09 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.23 | -0.06 | +0.11 | +0.23 | +0.15 | +0.05 | +0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
