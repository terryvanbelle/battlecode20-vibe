# Which metric starts predicting the result first

41 games, 24 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | worth (us-them) | +0.38 | -0.07 | +0.34 | +0.38 | +0.34 | +0.34 | +0.35 |
| r250 | - | landscapers (us-them) | +0.57 | -0.26 | +0.19 | +0.32 | +0.38 | +0.50 | +0.56 |
| r250 | - | miners (us-them) | +0.35 | +0.05 | +0.21 | +0.29 | +0.27 | +0.16 | +0.14 |
| r250 | - | robots (us-them) | +0.37 | -0.06 | +0.30 | +0.37 | +0.34 | +0.30 | +0.36 |
| r250 | - | spawned (us-them) | +0.41 | -0.06 | +0.29 | +0.37 | +0.35 | +0.35 | +0.40 |
| r250 | - | units (us-them) | +0.37 | -0.08 | +0.24 | +0.31 | +0.30 | +0.27 | +0.35 |
| r300 | - | worth (us-them) ~avg | +0.38 | -0.08 | +0.20 | +0.33 | +0.35 | +0.35 | +0.37 |
| r350 | - | cov (us-them) | +0.38 | +0.25 | +0.16 | +0.25 | +0.30 | +0.25 | +0.30 |
| r350 | - | robots (us-them) ~avg | +0.38 | -0.05 | +0.15 | +0.29 | +0.33 | +0.34 | +0.36 |
| r350 | - | spawned (us-them) ~avg | +0.40 | -0.05 | +0.15 | +0.28 | +0.33 | +0.38 | +0.40 |
| r400 | - | landscapers (us-them) ~avg | +0.58 | -0.26 | +0.07 | +0.22 | +0.30 | +0.46 | +0.58 |
| r400 | - | mines (us-them) | +0.46 | -0.15 | +0.10 | +0.22 | +0.34 | +0.44 | +0.44 |
| r500 | - | cov (us-them) ~avg | +0.35 | +0.25 | +0.20 | +0.23 | +0.28 | +0.30 | +0.28 |
| r500 | - | digs (us-them) | +0.48 | -0.14 | -0.01 | +0.12 | +0.26 | +0.36 | +0.44 |
| r500 | - | dirtDeps (us-them) | +0.47 | -0.19 | -0.02 | +0.14 | +0.25 | +0.35 | +0.42 |
| r550 | - | mines (us-them) ~avg | +0.45 | -0.16 | +0.01 | +0.14 | +0.22 | +0.34 | +0.42 |
| r550 | - | units (us-them) ~avg | +0.37 | -0.04 | +0.12 | +0.23 | +0.28 | +0.31 | +0.33 |
| r650 | - | digs (us-them) ~avg | +0.44 | -0.14 | -0.04 | +0.06 | +0.17 | +0.29 | +0.38 |
| r650 | - | dirtDeps (us-them) ~avg | +0.43 | -0.19 | -0.05 | +0.06 | +0.16 | +0.28 | +0.36 |
| r800 | - | pickups (us-them) | +0.35 | . | -0.05 | +0.05 | +0.09 | +0.24 | +0.31 |
| r1050 | - | vaporators (us-them) | +0.32 | +0.27 | +0.03 | +0.09 | +0.17 | +0.23 | +0.27 |
| r1200 | - | pickups (us-them) ~avg | +0.30 | . | -0.06 | +0.03 | +0.06 | +0.17 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.24 | -0.24 | -0.19 | -0.20 | -0.05 | +0.07 | -0.10 |
| - | - | aba (us-them) [inverted] ~avg | -0.22 | -0.22 | -0.19 | -0.21 | -0.16 | -0.02 | -0.02 |
| - | - | died (us-them) [inverted] | +0.14 | . | +0.13 | -0.00 | +0.05 | -0.07 | -0.08 |
| - | - | died (us-them) [inverted] ~avg | +0.15 | . | +0.13 | +0.05 | +0.05 | -0.05 | -0.08 |
| - | - | drones (us-them) | +0.22 | +0.14 | +0.04 | -0.13 | -0.13 | -0.09 | +0.04 |
| - | - | drones (us-them) ~avg | +0.14 | +0.14 | +0.07 | -0.11 | -0.12 | -0.10 | -0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.28 | +0.05 | +0.12 | +0.23 | +0.27 | +0.25 | +0.20 |
| - | - | moves (us-them) | +0.28 | +0.20 | +0.00 | +0.16 | +0.15 | +0.09 | +0.06 |
| - | - | moves (us-them) ~avg | +0.28 | +0.25 | +0.01 | +0.12 | +0.14 | +0.13 | +0.06 |
| - | - | netguns (us-them) | +0.30 | . | +0.22 | +0.17 | +0.08 | +0.11 | +0.25 |
| - | - | netguns (us-them) ~avg | +0.22 | . | +0.22 | +0.19 | +0.15 | +0.12 | +0.15 |
| - | - | soup (us-them) | +0.28 | -0.05 | +0.10 | +0.28 | +0.17 | +0.14 | +0.13 |
| - | - | soup (us-them) ~avg | +0.30 | -0.02 | -0.00 | +0.27 | +0.30 | +0.15 | +0.18 |
| - | - | vaporators (us-them) ~avg | +0.29 | +0.27 | +0.15 | +0.09 | +0.14 | +0.21 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
