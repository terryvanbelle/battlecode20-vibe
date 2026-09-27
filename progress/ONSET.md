# Which metric starts predicting the result first

44 games, 22 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) ~avg | +0.38 | +0.27 | +0.24 | +0.29 | +0.30 | +0.25 | +0.20 |
| r50 | - | robots (us-them) | +0.48 | +0.30 | +0.02 | +0.14 | +0.22 | +0.40 | +0.43 |
| r50 | - | robots (us-them) ~avg | +0.44 | +0.35 | +0.13 | +0.19 | +0.24 | +0.33 | +0.41 |
| r50 | - | spawned (us-them) | +0.50 | +0.30 | -0.02 | +0.22 | +0.28 | +0.42 | +0.47 |
| r50 | - | spawned (us-them) ~avg | +0.47 | +0.35 | +0.09 | +0.20 | +0.27 | +0.36 | +0.45 |
| r50 | - | units (us-them) | +0.40 | +0.35 | +0.05 | +0.08 | +0.12 | +0.30 | +0.30 |
| r50 | - | units (us-them) ~avg | +0.40 | +0.40 | +0.15 | +0.18 | +0.19 | +0.24 | +0.34 |
| r100 | - | drones (us-them) | +0.32 | +0.32 | +0.07 | +0.07 | +0.07 | +0.18 | +0.12 |
| r100 | - | drones (us-them) ~avg | +0.33 | +0.32 | +0.25 | +0.12 | +0.10 | +0.12 | +0.16 |
| r100 | - | mines (us-them) ~avg | +0.46 | +0.31 | +0.21 | +0.19 | +0.30 | +0.37 | +0.43 |
| r150 | - | moves (us-them) ~avg | +0.34 | +0.20 | +0.28 | +0.12 | +0.04 | -0.00 | +0.09 |
| r150 | - | pickups (us-them) | +0.39 | +0.28 | +0.28 | +0.22 | +0.26 | +0.13 | +0.21 |
| r150 | - | pickups (us-them) ~avg | +0.39 | +0.28 | +0.35 | +0.24 | +0.25 | +0.25 | +0.24 |
| r350 | - | mines (us-them) | +0.49 | +0.31 | +0.21 | +0.29 | +0.37 | +0.44 | +0.48 |
| r450 | - | cov (us-them) | +0.33 | -0.03 | +0.21 | +0.27 | +0.29 | +0.23 | +0.22 |
| r500 | - | worth (us-them) | +0.45 | +0.30 | +0.14 | +0.16 | +0.29 | +0.39 | +0.45 |
| r650 | - | landscapers (us-them) | +0.40 | +0.23 | -0.03 | -0.09 | -0.08 | +0.26 | +0.36 |
| r650 | - | vaporators (us-them) | +0.39 | -0.01 | -0.16 | +0.02 | +0.17 | +0.27 | +0.38 |
| r650 | - | worth (us-them) ~avg | +0.40 | +0.27 | +0.12 | +0.09 | +0.19 | +0.30 | +0.37 |
| r900 | - | dirtDeps (us-them) | +0.36 | +0.08 | -0.05 | +0.22 | +0.23 | +0.20 | +0.30 |
| r900 | - | landscapers (us-them) ~avg | +0.39 | +0.26 | -0.09 | -0.04 | -0.03 | +0.10 | +0.32 |
| r950 | - | digs (us-them) | +0.35 | +0.22 | +0.01 | +0.20 | +0.21 | +0.18 | +0.29 |
| r950 | - | vaporators (us-them) ~avg | +0.32 | -0.01 | -0.18 | -0.18 | -0.03 | +0.16 | +0.28 |
| r1100 | - | dirtDeps (us-them) ~avg | +0.32 | +0.10 | -0.07 | +0.21 | +0.23 | +0.23 | +0.26 |
| r1200 | - | digs (us-them) ~avg | +0.31 | +0.23 | +0.03 | +0.20 | +0.22 | +0.21 | +0.24 |
| r1200 | - | moves (us-them) | +0.32 | +0.21 | +0.17 | +0.03 | +0.01 | +0.03 | +0.21 |
| - | - | aba (us-them) [inverted] | +0.29 | +0.07 | +0.20 | +0.29 | +0.23 | +0.13 | -0.04 |
| - | - | aba (us-them) [inverted] ~avg | +0.28 | +0.10 | +0.19 | +0.27 | +0.28 | +0.22 | +0.14 |
| - | - | cov (us-them) ~avg | +0.26 | -0.03 | +0.14 | +0.16 | +0.22 | +0.25 | +0.25 |
| - | - | died (us-them) [inverted] | +0.18 | +0.14 | +0.10 | -0.10 | -0.02 | +0.06 | +0.01 |
| - | - | died (us-them) [inverted] ~avg | +0.19 | +0.14 | +0.15 | +0.03 | +0.00 | +0.01 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.39 | +0.39 | +0.35 | . | +0.25 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.39 | +0.39 | +0.26 | -0.14 | +0.14 | -0.13 | -0.13 |
| - | - | miners (us-them) | +0.38 | +0.16 | +0.14 | +0.22 | +0.27 | +0.14 | +0.17 |
| - | - | netguns (us-them) | +0.29 | . | +0.07 | -0.03 | +0.03 | +0.25 | +0.28 |
| - | - | netguns (us-them) ~avg | +0.24 | . | +0.07 | -0.01 | +0.01 | +0.10 | +0.20 |
| - | - | soup (us-them) | +0.31 | -0.06 | +0.27 | +0.18 | +0.24 | +0.05 | -0.10 |
| - | - | soup (us-them) ~avg | +0.21 | -0.13 | +0.11 | +0.04 | +0.17 | +0.13 | +0.01 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
