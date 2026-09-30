# Which metric starts predicting the result first

46 games, 12 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) ~avg | +0.36 | +0.33 | +0.24 | +0.19 | +0.21 | +0.29 | +0.35 |
| r150 | - | worth (us-them) | +0.40 | +0.24 | +0.28 | +0.13 | +0.14 | +0.19 | +0.17 |
| r150 | - | worth (us-them) ~avg | +0.36 | +0.23 | +0.32 | +0.14 | +0.14 | +0.17 | +0.21 |
| r200 | - | hqBuried (us-them) [inverted] | +0.41 | +0.20 | +0.41 | -0.11 | -0.06 | -0.05 | -0.08 |
| r200 | - | hqBuried (us-them) [inverted] ~avg | +0.40 | +0.20 | +0.40 | -0.09 | -0.08 | -0.08 | -0.08 |
| r500 | - | drones (us-them) | +0.37 | +0.33 | +0.22 | +0.21 | +0.23 | +0.31 | +0.24 |
| r500 | - | units (us-them) | +0.39 | -0.03 | +0.03 | +0.22 | +0.29 | +0.32 | +0.32 |
| r650 | - | digs (us-them) | +0.44 | +0.04 | +0.10 | +0.15 | +0.21 | +0.29 | +0.33 |
| r700 | - | units (us-them) ~avg | +0.38 | -0.05 | +0.01 | +0.05 | +0.17 | +0.27 | +0.36 |
| r750 | - | robots (us-them) | +0.32 | +0.03 | +0.11 | +0.19 | +0.25 | +0.28 | +0.26 |
| r800 | - | robots (us-them) ~avg | +0.30 | +0.03 | +0.09 | +0.07 | +0.16 | +0.24 | +0.30 |
| r800 | - | spawned (us-them) | +0.31 | +0.03 | +0.06 | +0.11 | +0.19 | +0.26 | +0.26 |
| r900 | - | dirtDeps (us-them) | +0.39 | +0.20 | +0.17 | +0.18 | +0.18 | +0.26 | +0.30 |
| r1000 | - | digs (us-them) ~avg | +0.38 | +0.04 | +0.08 | +0.12 | +0.17 | +0.23 | +0.29 |
| r1000 | - | landscapers (us-them) | +0.32 | +0.03 | +0.22 | +0.28 | +0.27 | +0.26 | +0.24 |
| r1000 | - | landscapers (us-them) ~avg | +0.34 | +0.03 | +0.17 | +0.21 | +0.25 | +0.27 | +0.28 |
| r1100 | - | dirtDeps (us-them) ~avg | +0.34 | +0.20 | +0.20 | +0.18 | +0.18 | +0.21 | +0.26 |
| r1200 | - | pickups (us-them) | +0.31 | +0.28 | +0.08 | +0.16 | +0.28 | +0.20 | +0.23 |
| - | - | aba (us-them) [inverted] | +0.27 | +0.19 | +0.05 | +0.07 | +0.04 | -0.13 | -0.23 |
| - | - | aba (us-them) [inverted] ~avg | +0.27 | +0.22 | +0.08 | +0.08 | +0.07 | -0.03 | -0.14 |
| - | - | cov (us-them) | +0.23 | -0.08 | -0.04 | -0.11 | -0.04 | +0.12 | +0.10 |
| - | - | cov (us-them) ~avg | -0.17 | -0.09 | -0.09 | -0.17 | -0.13 | +0.01 | +0.06 |
| - | - | died (us-them) [inverted] | -0.25 | -0.02 | +0.10 | +0.12 | +0.14 | +0.02 | +0.00 |
| - | - | died (us-them) [inverted] ~avg | -0.25 | -0.09 | +0.08 | +0.05 | +0.10 | +0.09 | +0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | -0.29 | -0.20 | -0.29 | -0.05 | +0.11 | +0.15 | +0.23 |
| - | - | miners (us-them) ~avg | -0.26 | -0.18 | -0.26 | -0.24 | -0.12 | +0.06 | +0.17 |
| - | - | mines (us-them) | +0.30 | +0.24 | +0.15 | +0.07 | +0.05 | +0.23 | +0.25 |
| - | - | mines (us-them) ~avg | +0.28 | +0.25 | +0.21 | +0.07 | +0.07 | +0.14 | +0.22 |
| - | r150 | moves (us-them) | -0.40 | -0.30 | -0.35 | -0.25 | +0.01 | +0.15 | +0.27 |
| - | r100 | moves (us-them) ~avg | -0.42 | -0.30 | -0.42 | -0.36 | -0.19 | +0.05 | +0.21 |
| - | - | netguns (us-them) | -0.28 | . | . | +0.11 | +0.13 | -0.14 | +0.01 |
| - | - | netguns (us-them) ~avg | -0.18 | . | . | +0.11 | +0.13 | -0.18 | -0.07 |
| - | - | pickups (us-them) ~avg | +0.28 | +0.28 | +0.15 | +0.11 | +0.20 | +0.25 | +0.24 |
| - | r500 | soup (us-them) | -0.49 | -0.05 | -0.02 | -0.15 | -0.30 | -0.31 | +0.04 |
| - | r550 | soup (us-them) ~avg | -0.43 | -0.04 | -0.03 | -0.11 | -0.17 | -0.33 | -0.22 |
| - | - | spawned (us-them) ~avg | +0.29 | +0.05 | +0.06 | +0.04 | +0.11 | +0.20 | +0.28 |
| - | - | vaporators (us-them) | +0.22 | +0.07 | +0.15 | +0.02 | +0.03 | +0.10 | +0.03 |
| - | - | vaporators (us-them) ~avg | +0.18 | +0.07 | +0.16 | +0.07 | +0.05 | +0.07 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
