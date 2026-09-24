# Which metric starts predicting the result first

40 games, 19 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.55 | +0.32 | +0.36 | +0.39 | +0.39 | +0.46 | +0.45 |
| r50 | - | mines (us-them) ~avg | +0.53 | +0.33 | +0.36 | +0.39 | +0.40 | +0.43 | +0.43 |
| r50 | - | worth (us-them) | +0.44 | +0.43 | +0.35 | +0.40 | +0.40 | +0.44 | +0.37 |
| r50 | - | worth (us-them) ~avg | +0.44 | +0.44 | +0.38 | +0.39 | +0.41 | +0.43 | +0.40 |
| r100 | - | vaporators (us-them) | +0.37 | +0.36 | +0.30 | +0.34 | +0.34 | +0.32 | +0.31 |
| r100 | - | vaporators (us-them) ~avg | +0.37 | +0.36 | +0.36 | +0.36 | +0.36 | +0.34 | +0.33 |
| r150 | - | drones (us-them) | +0.46 | +0.12 | +0.46 | +0.40 | +0.38 | +0.41 | +0.37 |
| r150 | - | drones (us-them) ~avg | +0.42 | +0.12 | +0.41 | +0.42 | +0.41 | +0.41 | +0.39 |
| r200 | - | robots (us-them) | +0.50 | +0.14 | +0.32 | +0.39 | +0.39 | +0.49 | +0.43 |
| r200 | - | spawned (us-them) | +0.49 | +0.18 | +0.34 | +0.38 | +0.40 | +0.48 | +0.45 |
| r200 | - | spawned (us-them) ~avg | +0.51 | +0.25 | +0.30 | +0.36 | +0.39 | +0.45 | +0.45 |
| r250 | - | robots (us-them) ~avg | +0.48 | +0.22 | +0.27 | +0.35 | +0.38 | +0.45 | +0.44 |
| r350 | - | cov (us-them) | +0.42 | -0.06 | -0.07 | +0.22 | +0.35 | +0.34 | +0.33 |
| r400 | - | miners (us-them) | +0.35 | -0.29 | +0.01 | +0.26 | +0.31 | +0.35 | +0.29 |
| r400 | - | pickups (us-them) | +0.48 | -0.15 | -0.16 | +0.21 | +0.36 | +0.45 | +0.42 |
| r400 | - | units (us-them) | +0.51 | -0.12 | +0.13 | +0.27 | +0.32 | +0.51 | +0.47 |
| r450 | - | died (us-them) [inverted] | +0.40 | -0.17 | -0.05 | +0.27 | +0.26 | +0.38 | -0.09 |
| r500 | - | died (us-them) [inverted] ~avg | +0.35 | -0.17 | -0.15 | +0.13 | +0.22 | +0.34 | +0.10 |
| r500 | - | landscapers (us-them) | +0.52 | +0.10 | +0.03 | +0.05 | +0.13 | +0.47 | +0.51 |
| r500 | - | pickups (us-them) ~avg | +0.46 | -0.15 | -0.18 | +0.07 | +0.23 | +0.38 | +0.46 |
| r500 | - | units (us-them) ~avg | +0.50 | -0.04 | +0.03 | +0.15 | +0.24 | +0.41 | +0.44 |
| r600 | - | digs (us-them) | +0.53 | +0.17 | -0.04 | +0.04 | +0.10 | +0.30 | +0.44 |
| r600 | - | landscapers (us-them) ~avg | +0.51 | +0.10 | -0.01 | +0.01 | +0.07 | +0.32 | +0.43 |
| r650 | - | cov (us-them) ~avg | +0.45 | -0.08 | -0.09 | +0.07 | +0.21 | +0.29 | +0.31 |
| r650 | - | dirtDeps (us-them) | +0.51 | +0.19 | -0.07 | +0.01 | +0.06 | +0.27 | +0.42 |
| r900 | - | digs (us-them) ~avg | +0.48 | +0.17 | -0.05 | +0.01 | +0.06 | +0.19 | +0.33 |
| r950 | - | dirtDeps (us-them) ~avg | +0.45 | +0.19 | -0.08 | -0.02 | +0.03 | +0.15 | +0.29 |
| r950 | - | miners (us-them) ~avg | +0.34 | -0.17 | -0.10 | +0.07 | +0.19 | +0.28 | +0.28 |
| r1000 | r150 | moves (us-them) | -0.39 | -0.22 | -0.30 | -0.21 | -0.06 | +0.13 | +0.24 |
| - | - | aba (us-them) [inverted] | -0.23 | +0.19 | +0.17 | +0.17 | +0.17 | +0.19 | -0.09 |
| - | - | aba (us-them) [inverted] ~avg | +0.19 | +0.18 | +0.18 | +0.18 | +0.18 | +0.19 | +0.08 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.22 | . | . | . | . | +0.16 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.22 | +0.16 | +0.16 | +0.16 | +0.22 | +0.20 |
| - | r150 | moves (us-them) ~avg | -0.32 | -0.18 | -0.32 | -0.27 | -0.17 | +0.01 | +0.15 |
| - | - | netguns (us-them) | +0.11 | . | -0.07 | +0.11 | +0.10 | +0.10 | +0.03 |
| - | - | netguns (us-them) ~avg | +0.09 | . | -0.07 | +0.07 | +0.08 | +0.09 | +0.05 |
| - | - | soup (us-them) | -0.20 | +0.16 | +0.00 | +0.07 | +0.08 | -0.03 | -0.20 |
| - | - | soup (us-them) ~avg | +0.14 | +0.13 | +0.09 | +0.08 | +0.10 | +0.09 | -0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
