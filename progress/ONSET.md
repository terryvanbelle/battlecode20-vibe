# Which metric starts predicting the result first

39 games, 20 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.53 | +0.27 | +0.38 | +0.45 | +0.53 | +0.40 | +0.36 |
| r50 | - | mines (us-them) ~avg | +0.49 | +0.28 | +0.34 | +0.38 | +0.46 | +0.48 | +0.41 |
| r150 | - | robots (us-them) | +0.55 | +0.13 | +0.38 | +0.45 | +0.55 | +0.44 | +0.41 |
| r150 | - | spawned (us-them) | +0.48 | +0.13 | +0.34 | +0.38 | +0.48 | +0.41 | +0.43 |
| r150 | - | worth (us-them) | +0.57 | +0.28 | +0.46 | +0.49 | +0.57 | +0.48 | +0.42 |
| r150 | - | worth (us-them) ~avg | +0.54 | +0.28 | +0.43 | +0.46 | +0.53 | +0.53 | +0.46 |
| r250 | - | robots (us-them) ~avg | +0.53 | +0.09 | +0.29 | +0.35 | +0.49 | +0.51 | +0.48 |
| r250 | - | spawned (us-them) ~avg | +0.51 | +0.09 | +0.27 | +0.29 | +0.40 | +0.48 | +0.48 |
| r250 | - | units (us-them) | +0.49 | +0.05 | +0.27 | +0.34 | +0.49 | +0.37 | +0.40 |
| r250 | - | vaporators (us-them) | +0.45 | -0.01 | +0.21 | +0.33 | +0.35 | +0.44 | +0.35 |
| r300 | - | drones (us-them) | +0.37 | +0.29 | +0.18 | +0.32 | +0.28 | +0.19 | +0.32 |
| r300 | - | drones (us-them) ~avg | +0.33 | +0.29 | +0.19 | +0.30 | +0.31 | +0.22 | +0.30 |
| r350 | - | died (us-them) [inverted] | +0.44 | . | +0.21 | +0.25 | +0.40 | +0.41 | +0.19 |
| r350 | - | died (us-them) [inverted] ~avg | +0.41 | . | +0.23 | +0.26 | +0.35 | +0.40 | +0.36 |
| r350 | - | landscapers (us-them) | +0.62 | +0.12 | +0.24 | +0.24 | +0.55 | +0.43 | +0.54 |
| r350 | - | pickups (us-them) | +0.41 | -0.16 | +0.07 | +0.16 | +0.32 | +0.36 | +0.35 |
| r350 | - | units (us-them) ~avg | +0.51 | -0.03 | +0.18 | +0.22 | +0.38 | +0.46 | +0.47 |
| r400 | - | landscapers (us-them) ~avg | +0.63 | +0.12 | +0.24 | +0.17 | +0.37 | +0.55 | +0.62 |
| r400 | - | vaporators (us-them) ~avg | +0.43 | +0.07 | +0.20 | +0.28 | +0.31 | +0.38 | +0.37 |
| r450 | - | digs (us-them) | +0.66 | +0.18 | +0.16 | +0.17 | +0.29 | +0.45 | +0.61 |
| r500 | - | dirtDeps (us-them) | +0.66 | +0.27 | +0.16 | +0.15 | +0.23 | +0.43 | +0.59 |
| r500 | - | pickups (us-them) ~avg | +0.39 | -0.16 | -0.02 | +0.10 | +0.25 | +0.33 | +0.35 |
| r550 | - | digs (us-them) ~avg | +0.65 | +0.13 | +0.15 | +0.15 | +0.23 | +0.36 | +0.55 |
| r550 | - | netguns (us-them) | +0.32 | . | -0.07 | -0.04 | -0.04 | +0.32 | +0.18 |
| r600 | - | dirtDeps (us-them) ~avg | +0.64 | +0.24 | +0.13 | +0.12 | +0.18 | +0.33 | +0.52 |
| r700 | - | moves (us-them) | +0.35 | -0.25 | -0.14 | -0.05 | +0.12 | +0.28 | +0.32 |
| r800 | - | moves (us-them) ~avg | +0.35 | -0.22 | -0.18 | -0.09 | -0.01 | +0.17 | +0.32 |
| - | r900 | aba (us-them) [inverted] | -0.32 | +0.28 | +0.07 | -0.01 | -0.03 | -0.24 | -0.30 |
| - | r1000 | aba (us-them) [inverted] ~avg | -0.31 | +0.28 | +0.21 | +0.08 | +0.03 | -0.11 | -0.26 |
| - | - | cov (us-them) | -0.25 | -0.23 | -0.22 | -0.18 | -0.15 | -0.01 | +0.06 |
| - | - | cov (us-them) ~avg | -0.23 | -0.17 | -0.21 | -0.20 | -0.20 | -0.13 | -0.04 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.20 | +0.16 | +0.09 | +0.16 | . | -0.02 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.20 | +0.22 | +0.19 | +0.20 | +0.18 | +0.09 |
| - | - | miners (us-them) | +0.27 | -0.25 | -0.11 | +0.02 | -0.10 | +0.07 | -0.02 |
| - | - | miners (us-them) ~avg | -0.25 | -0.25 | -0.22 | -0.12 | -0.11 | -0.06 | +0.01 |
| - | - | netguns (us-them) ~avg | +0.21 | . | -0.07 | -0.06 | -0.06 | +0.13 | +0.18 |
| - | - | soup (us-them) | +0.23 | -0.01 | +0.09 | -0.22 | +0.00 | -0.02 | -0.14 |
| - | - | soup (us-them) ~avg | +0.27 | +0.03 | +0.03 | -0.02 | -0.01 | +0.01 | -0.16 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
