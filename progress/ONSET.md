# Which metric starts predicting the result first

40 games, 20 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.32 | +0.32 | +0.29 | +0.16 | +0.22 | +0.06 | +0.25 |
| r100 | - | drones (us-them) ~avg | +0.35 | +0.35 | +0.33 | +0.27 | +0.20 | +0.16 | +0.20 |
| r100 | - | vaporators (us-them) | +0.40 | +0.40 | +0.35 | +0.32 | +0.30 | +0.29 | +0.30 |
| r100 | - | vaporators (us-them) ~avg | +0.40 | +0.40 | +0.39 | +0.33 | +0.30 | +0.26 | +0.28 |
| r150 | - | cov (us-them) | +0.51 | +0.15 | +0.51 | +0.49 | +0.46 | +0.27 | +0.15 |
| r150 | - | hqBuried (us-them) [inverted] | +0.39 | +0.24 | +0.33 | +0.29 | . | . | . |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.23 | +0.28 | +0.22 | +0.20 | +0.23 | +0.26 |
| r200 | - | cov (us-them) ~avg | +0.49 | +0.06 | +0.38 | +0.44 | +0.49 | +0.37 | +0.27 |
| r200 | - | moves (us-them) | +0.38 | +0.09 | +0.33 | +0.33 | +0.30 | +0.33 | +0.35 |
| r250 | - | moves (us-them) ~avg | +0.34 | +0.04 | +0.27 | +0.34 | +0.31 | +0.33 | +0.33 |
| r400 | - | robots (us-them) | +0.48 | +0.02 | +0.01 | +0.20 | +0.37 | +0.39 | +0.39 |
| r400 | - | units (us-them) | +0.49 | +0.19 | -0.15 | +0.07 | +0.31 | +0.37 | +0.37 |
| r450 | - | landscapers (us-them) | +0.44 | +0.04 | -0.26 | -0.11 | +0.15 | +0.41 | +0.39 |
| r450 | - | spawned (us-them) | +0.44 | -0.01 | -0.06 | +0.21 | +0.30 | +0.36 | +0.41 |
| r450 | - | worth (us-them) | +0.38 | +0.18 | +0.18 | +0.18 | +0.26 | +0.32 | +0.36 |
| r500 | - | miners (us-them) | +0.34 | +0.11 | -0.07 | +0.25 | +0.28 | +0.14 | +0.11 |
| r500 | - | robots (us-them) ~avg | +0.41 | -0.06 | -0.13 | +0.00 | +0.13 | +0.37 | +0.40 |
| r550 | - | netguns (us-them) | +0.43 | . | +0.19 | +0.17 | +0.22 | +0.37 | +0.41 |
| r550 | - | units (us-them) ~avg | +0.41 | +0.09 | -0.19 | -0.10 | +0.03 | +0.33 | +0.41 |
| r650 | - | netguns (us-them) ~avg | +0.40 | . | +0.21 | +0.17 | +0.19 | +0.28 | +0.37 |
| r650 | - | spawned (us-them) ~avg | +0.38 | -0.08 | -0.18 | -0.03 | +0.09 | +0.29 | +0.38 |
| r700 | r200 | landscapers (us-them) ~avg | +0.44 | +0.03 | -0.32 | -0.23 | -0.11 | +0.22 | +0.44 |
| r800 | - | worth (us-them) ~avg | +0.31 | +0.12 | +0.09 | +0.13 | +0.15 | +0.28 | +0.31 |
| - | r400 | aba (us-them) [inverted] | -0.35 | +0.08 | +0.05 | -0.08 | -0.30 | -0.30 | -0.34 |
| - | r1100 | aba (us-them) [inverted] ~avg | -0.32 | +0.04 | +0.00 | -0.03 | -0.15 | -0.26 | -0.28 |
| - | - | died (us-them) [inverted] | +0.24 | +0.21 | +0.19 | +0.01 | +0.23 | +0.10 | +0.04 |
| - | - | died (us-them) [inverted] ~avg | +0.23 | +0.21 | +0.17 | +0.09 | +0.13 | +0.20 | +0.17 |
| - | - | digs (us-them) | +0.25 | +0.06 | -0.22 | -0.11 | +0.01 | +0.13 | +0.14 |
| - | - | digs (us-them) ~avg | -0.21 | +0.03 | -0.21 | -0.17 | -0.06 | +0.09 | +0.13 |
| - | - | dirtDeps (us-them) | -0.26 | +0.09 | -0.26 | -0.13 | -0.01 | +0.10 | +0.12 |
| - | - | dirtDeps (us-them) ~avg | -0.29 | +0.07 | -0.29 | -0.20 | -0.08 | +0.08 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.26 | +0.01 | -0.12 | -0.01 | +0.11 | +0.25 | +0.17 |
| - | - | mines (us-them) | +0.29 | +0.24 | +0.06 | +0.08 | +0.17 | +0.28 | +0.29 |
| - | - | mines (us-them) ~avg | +0.23 | +0.20 | +0.03 | +0.03 | +0.07 | +0.20 | +0.22 |
| - | - | pickups (us-them) | +0.09 | +0.07 | +0.09 | -0.00 | +0.01 | -0.08 | -0.04 |
| - | - | pickups (us-them) ~avg | +0.13 | +0.13 | +0.05 | +0.01 | -0.01 | -0.05 | -0.06 |
| - | r300 | soup (us-them) | -0.46 | +0.14 | -0.12 | -0.46 | -0.34 | -0.16 | -0.10 |
| - | r350 | soup (us-them) ~avg | -0.35 | +0.22 | +0.07 | -0.22 | -0.35 | -0.30 | -0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
