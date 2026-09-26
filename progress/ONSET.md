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
| r50 | - | units (us-them) | +0.58 | +0.31 | +0.22 | +0.23 | +0.36 | +0.53 | +0.50 |
| r50 | - | units (us-them) ~avg | +0.52 | +0.35 | +0.21 | +0.22 | +0.28 | +0.40 | +0.52 |
| r100 | - | hqBuried (us-them) [inverted] | +0.39 | +0.32 | +0.31 | -0.04 | . | +0.26 | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.38 | +0.32 | +0.31 | -0.01 | -0.04 | +0.04 | -0.03 |
| r100 | - | moves (us-them) ~avg | +0.39 | +0.39 | +0.24 | +0.11 | +0.14 | +0.21 | +0.28 |
| r200 | - | mines (us-them) | +0.34 | +0.25 | +0.33 | +0.33 | +0.34 | +0.33 | +0.34 |
| r200 | - | worth (us-them) | +0.41 | +0.19 | +0.34 | +0.25 | +0.30 | +0.40 | +0.40 |
| r350 | - | landscapers (us-them) | +0.52 | +0.12 | +0.24 | +0.19 | +0.31 | +0.48 | +0.46 |
| r350 | - | robots (us-them) | +0.52 | +0.23 | +0.22 | +0.21 | +0.37 | +0.49 | +0.50 |
| r350 | - | spawned (us-them) | +0.47 | +0.23 | +0.19 | +0.25 | +0.37 | +0.46 | +0.44 |
| r400 | - | drones (us-them) | +0.38 | +0.18 | +0.18 | +0.17 | +0.31 | +0.30 | +0.33 |
| r400 | - | mines (us-them) ~avg | +0.33 | +0.24 | +0.29 | +0.26 | +0.30 | +0.33 | +0.32 |
| r450 | - | cov (us-them) | +0.44 | +0.30 | +0.02 | +0.10 | +0.27 | +0.43 | +0.38 |
| r450 | - | robots (us-them) ~avg | +0.48 | +0.26 | +0.22 | +0.21 | +0.29 | +0.41 | +0.48 |
| r450 | - | spawned (us-them) ~avg | +0.44 | +0.26 | +0.20 | +0.21 | +0.28 | +0.38 | +0.44 |
| r500 | - | worth (us-them) ~avg | +0.38 | +0.18 | +0.29 | +0.23 | +0.28 | +0.35 | +0.38 |
| r550 | - | digs (us-them) | +0.48 | -0.06 | +0.13 | +0.23 | +0.28 | +0.33 | +0.45 |
| r550 | - | landscapers (us-them) ~avg | +0.47 | +0.12 | +0.13 | +0.15 | +0.21 | +0.35 | +0.47 |
| r550 | - | miners (us-them) | +0.40 | +0.15 | +0.01 | +0.08 | +0.17 | +0.31 | +0.40 |
| r600 | - | cov (us-them) ~avg | +0.38 | +0.26 | +0.10 | +0.07 | +0.16 | +0.30 | +0.33 |
| r650 | - | dirtDeps (us-them) | +0.46 | +0.05 | +0.11 | +0.23 | +0.27 | +0.30 | +0.42 |
| r650 | - | drones (us-them) ~avg | +0.35 | +0.18 | +0.23 | +0.21 | +0.26 | +0.30 | +0.34 |
| r650 | - | pickups (us-them) | +0.40 | +0.00 | +0.21 | +0.18 | +0.23 | +0.30 | +0.40 |
| r700 | - | digs (us-them) ~avg | +0.41 | -0.06 | +0.10 | +0.17 | +0.22 | +0.28 | +0.37 |
| r750 | - | died (us-them) [inverted] | +0.36 | +0.00 | +0.25 | -0.02 | +0.16 | +0.28 | +0.36 |
| r750 | - | dirtDeps (us-them) ~avg | +0.38 | +0.05 | +0.10 | +0.19 | +0.22 | +0.27 | +0.35 |
| r750 | - | pickups (us-them) ~avg | +0.38 | +0.00 | +0.26 | +0.21 | +0.26 | +0.27 | +0.36 |
| r800 | - | moves (us-them) | +0.38 | +0.38 | +0.13 | +0.06 | +0.15 | +0.26 | +0.35 |
| r800 | - | vaporators (us-them) | +0.32 | +0.17 | -0.08 | -0.08 | +0.09 | +0.28 | +0.31 |
| r850 | - | miners (us-them) ~avg | +0.35 | +0.23 | +0.13 | +0.14 | +0.19 | +0.25 | +0.33 |
| r1000 | - | vaporators (us-them) ~avg | +0.32 | +0.17 | -0.06 | -0.12 | -0.01 | +0.17 | +0.26 |
| - | - | aba (us-them) [inverted] | -0.20 | +0.11 | -0.16 | -0.13 | +0.17 | +0.11 | +0.02 |
| - | - | aba (us-them) [inverted] ~avg | -0.19 | +0.12 | -0.08 | -0.16 | +0.06 | +0.12 | +0.10 |
| - | - | died (us-them) [inverted] ~avg | +0.29 | +0.00 | +0.20 | +0.05 | +0.11 | +0.20 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) | -0.33 | +0.16 | -0.22 | -0.21 | -0.10 | -0.04 | +0.17 |
| - | - | netguns (us-them) ~avg | -0.23 | +0.16 | -0.13 | -0.22 | -0.20 | -0.19 | +0.00 |
| - | - | soup (us-them) | +0.23 | -0.18 | +0.14 | +0.19 | -0.22 | -0.04 | -0.17 |
| - | - | soup (us-them) ~avg | -0.15 | -0.15 | +0.10 | +0.12 | +0.08 | +0.01 | -0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
