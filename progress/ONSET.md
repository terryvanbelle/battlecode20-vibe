# Which metric starts predicting the result first

43 games, 23 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.41 | +0.33 | +0.33 | +0.33 | +0.25 | +0.33 | +0.27 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.40 | +0.37 | +0.30 | +0.37 | +0.35 | +0.33 | +0.32 |
| r100 | - | pickups (us-them) | +0.49 | +0.49 | +0.12 | +0.08 | +0.20 | +0.16 | +0.14 |
| r100 | - | pickups (us-them) ~avg | +0.49 | +0.49 | +0.27 | +0.14 | +0.15 | +0.17 | +0.15 |
| r150 | - | cov (us-them) | +0.48 | +0.27 | +0.38 | +0.48 | +0.46 | +0.38 | +0.23 |
| r150 | - | cov (us-them) ~avg | +0.46 | +0.23 | +0.34 | +0.44 | +0.44 | +0.45 | +0.34 |
| r150 | - | died (us-them) [inverted] | +0.46 | +0.22 | +0.43 | +0.31 | +0.34 | +0.35 | +0.28 |
| r150 | - | died (us-them) [inverted] ~avg | +0.47 | +0.22 | +0.46 | +0.41 | +0.37 | +0.37 | +0.33 |
| r150 | - | worth (us-them) | +0.39 | +0.23 | +0.38 | +0.37 | +0.36 | +0.38 | +0.26 |
| r200 | - | worth (us-them) ~avg | +0.38 | +0.24 | +0.33 | +0.34 | +0.37 | +0.38 | +0.31 |
| r250 | - | landscapers (us-them) | +0.54 | +0.14 | +0.26 | +0.28 | +0.24 | +0.54 | +0.46 |
| r300 | - | aba (us-them) [inverted] | +0.39 | +0.13 | +0.09 | +0.31 | +0.38 | +0.14 | -0.07 |
| r300 | - | robots (us-them) | +0.44 | +0.11 | +0.25 | +0.32 | +0.34 | +0.44 | +0.37 |
| r350 | - | aba (us-them) [inverted] ~avg | +0.37 | +0.16 | +0.11 | +0.25 | +0.37 | +0.27 | +0.13 |
| r350 | - | units (us-them) | +0.44 | +0.12 | +0.23 | +0.28 | +0.28 | +0.44 | +0.40 |
| r400 | - | miners (us-them) | +0.33 | -0.09 | +0.02 | +0.22 | +0.33 | +0.15 | +0.10 |
| r500 | - | landscapers (us-them) ~avg | +0.50 | +0.19 | +0.24 | +0.23 | +0.24 | +0.39 | +0.45 |
| r500 | - | robots (us-them) ~avg | +0.39 | +0.18 | +0.21 | +0.23 | +0.26 | +0.37 | +0.38 |
| r500 | - | spawned (us-them) | +0.37 | +0.09 | +0.15 | +0.24 | +0.25 | +0.35 | +0.34 |
| r550 | - | units (us-them) ~avg | +0.42 | +0.19 | +0.20 | +0.21 | +0.24 | +0.35 | +0.38 |
| r650 | - | moves (us-them) | +0.33 | +0.33 | +0.10 | +0.18 | +0.16 | +0.28 | +0.26 |
| r850 | - | spawned (us-them) ~avg | +0.33 | +0.17 | +0.12 | +0.13 | +0.17 | +0.27 | +0.31 |
| r1150 | - | digs (us-them) | +0.34 | +0.18 | +0.24 | +0.10 | +0.14 | +0.25 | +0.25 |
| r1150 | - | dirtDeps (us-them) | +0.34 | +0.02 | +0.15 | +0.04 | +0.11 | +0.23 | +0.24 |
| - | - | digs (us-them) ~avg | +0.26 | +0.23 | +0.23 | +0.13 | +0.13 | +0.17 | +0.23 |
| - | - | dirtDeps (us-them) ~avg | +0.25 | +0.14 | +0.14 | +0.03 | +0.09 | +0.14 | +0.20 |
| - | - | drones (us-them) | +0.28 | +0.28 | +0.14 | -0.08 | -0.17 | +0.07 | +0.23 |
| - | - | drones (us-them) ~avg | +0.28 | +0.28 | +0.19 | +0.08 | -0.06 | -0.05 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.25 | +0.06 | -0.02 | +0.09 | +0.24 | +0.22 | +0.16 |
| - | - | mines (us-them) | +0.27 | +0.10 | +0.12 | +0.18 | +0.27 | +0.21 | +0.12 |
| - | - | mines (us-them) ~avg | +0.26 | +0.14 | +0.04 | +0.06 | +0.26 | +0.25 | +0.18 |
| - | - | moves (us-them) ~avg | +0.30 | +0.30 | +0.15 | +0.17 | +0.14 | +0.22 | +0.26 |
| - | r200 | netguns (us-them) | -0.33 | . | -0.32 | -0.33 | -0.26 | -0.09 | -0.13 |
| - | r200 | netguns (us-them) ~avg | -0.33 | . | -0.32 | -0.32 | -0.32 | -0.29 | -0.20 |
| - | - | soup (us-them) | -0.28 | -0.01 | -0.04 | +0.09 | +0.05 | -0.03 | -0.07 |
| - | - | soup (us-them) ~avg | +0.29 | -0.06 | -0.04 | +0.01 | +0.28 | +0.13 | -0.06 |
| - | - | vaporators (us-them) | +0.30 | +0.10 | +0.08 | +0.18 | +0.30 | +0.26 | +0.18 |
| - | - | vaporators (us-them) ~avg | +0.29 | +0.10 | +0.04 | +0.11 | +0.21 | +0.29 | +0.22 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
