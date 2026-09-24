# Which metric starts predicting the result first

19 games, 5 wins. Noise floor about 0.46; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | cov (us-them) | +0.63 | +0.39 | +0.21 | +0.15 | +0.30 | +0.37 | +0.51 |
| r100 | - | cov (us-them) ~avg | +0.62 | +0.35 | +0.34 | +0.24 | +0.26 | +0.32 | +0.42 |
| r250 | r950 | aba (us-them) [inverted] | +0.47 | -0.14 | -0.08 | +0.28 | +0.02 | -0.15 | -0.26 |
| r300 | - | worth (us-them) | +0.46 | -0.20 | -0.02 | +0.31 | +0.34 | +0.38 | +0.38 |
| r350 | - | robots (us-them) | +0.51 | -0.12 | -0.05 | +0.29 | +0.34 | +0.40 | +0.42 |
| r350 | - | spawned (us-them) | +0.50 | -0.12 | -0.02 | +0.27 | +0.33 | +0.39 | +0.41 |
| r500 | - | landscapers (us-them) | +0.64 | -0.13 | -0.11 | +0.12 | +0.24 | +0.40 | +0.56 |
| r500 | - | units (us-them) | +0.54 | -0.23 | -0.13 | +0.19 | +0.28 | +0.35 | +0.44 |
| r500 | - | vaporators (us-them) | +0.39 | +0.09 | +0.02 | +0.19 | +0.25 | +0.34 | +0.34 |
| r500 | - | worth (us-them) ~avg | +0.49 | -0.17 | -0.13 | +0.13 | +0.25 | +0.34 | +0.40 |
| r550 | - | mines (us-them) | +0.54 | -0.21 | +0.01 | +0.23 | +0.27 | +0.32 | +0.32 |
| r550 | - | pickups (us-them) | +0.44 | . | +0.08 | +0.12 | +0.16 | +0.32 | +0.35 |
| r550 | - | robots (us-them) ~avg | +0.53 | -0.11 | -0.13 | +0.08 | +0.23 | +0.33 | +0.42 |
| r550 | - | spawned (us-them) ~avg | +0.51 | -0.11 | -0.11 | +0.09 | +0.23 | +0.33 | +0.40 |
| r650 | - | landscapers (us-them) ~avg | +0.59 | -0.13 | -0.13 | -0.02 | +0.11 | +0.29 | +0.48 |
| r650 | - | vaporators (us-them) ~avg | +0.42 | +0.09 | +0.02 | +0.08 | +0.16 | +0.29 | +0.37 |
| r700 | - | pickups (us-them) ~avg | +0.46 | . | +0.00 | +0.04 | +0.10 | +0.25 | +0.35 |
| r700 | - | units (us-them) ~avg | +0.53 | -0.16 | -0.20 | -0.01 | +0.14 | +0.27 | +0.41 |
| r750 | - | digs (us-them) | +0.44 | +0.03 | -0.10 | +0.00 | +0.10 | +0.22 | +0.37 |
| r750 | - | mines (us-them) ~avg | +0.52 | -0.20 | -0.12 | +0.08 | +0.18 | +0.26 | +0.31 |
| r800 | - | dirtDeps (us-them) | +0.38 | +0.04 | -0.09 | -0.02 | +0.06 | +0.19 | +0.33 |
| r850 | - | drones (us-them) | +0.42 | -0.24 | -0.01 | +0.15 | +0.23 | +0.23 | +0.35 |
| r950 | - | died (us-them) [inverted] ~avg | +0.31 | . | -0.24 | -0.03 | +0.10 | +0.07 | +0.18 |
| r950 | - | drones (us-them) ~avg | +0.42 | -0.16 | -0.11 | +0.02 | +0.11 | +0.19 | +0.29 |
| r950 | - | miners (us-them) | +0.33 | -0.05 | -0.06 | +0.09 | +0.12 | +0.11 | +0.18 |
| r950 | r200 | moves (us-them) | +0.37 | -0.07 | -0.34 | -0.31 | -0.15 | +0.03 | +0.22 |
| r1050 | r250 | moves (us-them) ~avg | +0.33 | -0.07 | -0.28 | -0.32 | -0.25 | -0.09 | +0.11 |
| r1150 | - | digs (us-them) ~avg | +0.33 | +0.03 | -0.09 | -0.04 | +0.03 | +0.13 | +0.27 |
| - | r1100 | aba (us-them) [inverted] ~avg | -0.35 | -0.01 | -0.05 | +0.27 | +0.16 | -0.05 | -0.19 |
| - | - | died (us-them) [inverted] | -0.25 | . | -0.25 | +0.20 | +0.14 | +0.08 | +0.12 |
| - | - | dirtDeps (us-them) ~avg | +0.29 | +0.04 | -0.08 | -0.05 | +0.01 | +0.10 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.14 | . | +0.14 | +0.14 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.14 | . | +0.14 | +0.14 | +0.14 | +0.14 | . |
| - | - | miners (us-them) ~avg | +0.29 | -0.06 | -0.08 | -0.01 | +0.04 | +0.07 | +0.14 |
| - | - | netguns (us-them) | +0.26 | . | . | . | +0.14 | +0.24 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.23 | . | . | . | +0.14 | +0.23 | +0.22 |
| - | r500 | soup (us-them) | -0.48 | -0.19 | -0.09 | -0.01 | -0.17 | -0.42 | -0.28 |
| - | r600 | soup (us-them) ~avg | -0.36 | -0.12 | -0.19 | -0.06 | -0.15 | -0.32 | -0.35 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
