# Which metric starts predicting the result first

47 games, 31 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r300 | - | robots (us-them) | +0.60 | +0.04 | +0.12 | +0.34 | +0.45 | +0.43 | +0.57 |
| r350 | - | landscapers (us-them) | +0.68 | -0.04 | +0.07 | +0.23 | +0.50 | +0.49 | +0.64 |
| r350 | - | spawned (us-them) | +0.49 | +0.04 | +0.12 | +0.26 | +0.38 | +0.33 | +0.48 |
| r350 | - | units (us-them) | +0.61 | +0.04 | +0.03 | +0.26 | +0.40 | +0.35 | +0.59 |
| r400 | - | died (us-them) [inverted] ~avg | +0.39 | . | -0.06 | +0.29 | +0.30 | +0.39 | +0.31 |
| r400 | - | robots (us-them) ~avg | +0.61 | +0.00 | +0.01 | +0.17 | +0.32 | +0.44 | +0.61 |
| r450 | - | landscapers (us-them) ~avg | +0.67 | -0.04 | -0.08 | +0.05 | +0.26 | +0.47 | +0.64 |
| r500 | - | died (us-them) [inverted] | +0.36 | . | +0.00 | +0.26 | +0.31 | +0.33 | +0.18 |
| r500 | - | digs (us-them) | +0.62 | +0.07 | -0.17 | -0.01 | +0.15 | +0.41 | +0.57 |
| r500 | - | spawned (us-them) ~avg | +0.49 | +0.00 | +0.01 | +0.13 | +0.25 | +0.34 | +0.49 |
| r500 | - | units (us-them) ~avg | +0.60 | +0.01 | -0.06 | +0.08 | +0.24 | +0.35 | +0.59 |
| r500 | - | worth (us-them) | +0.38 | -0.06 | +0.12 | +0.25 | +0.28 | +0.37 | +0.29 |
| r550 | - | dirtDeps (us-them) | +0.58 | +0.11 | -0.19 | -0.07 | +0.09 | +0.36 | +0.53 |
| r600 | - | worth (us-them) ~avg | +0.38 | -0.08 | +0.03 | +0.16 | +0.23 | +0.32 | +0.36 |
| r650 | - | digs (us-them) ~avg | +0.52 | +0.03 | -0.17 | -0.10 | +0.03 | +0.28 | +0.50 |
| r700 | - | dirtDeps (us-them) ~avg | +0.47 | +0.07 | -0.18 | -0.15 | -0.02 | +0.21 | +0.45 |
| r750 | - | mines (us-them) | +0.39 | -0.13 | +0.06 | +0.14 | +0.19 | +0.26 | +0.39 |
| r750 | - | mines (us-them) ~avg | +0.35 | -0.12 | -0.02 | +0.06 | +0.13 | +0.21 | +0.35 |
| r950 | - | miners (us-them) | +0.40 | +0.05 | -0.09 | +0.10 | +0.14 | +0.15 | +0.30 |
| r950 | - | miners (us-them) ~avg | +0.39 | +0.01 | -0.00 | +0.03 | +0.08 | +0.11 | +0.24 |
| r950 | - | moves (us-them) | +0.40 | -0.05 | -0.17 | -0.19 | -0.06 | +0.07 | +0.25 |
| r1150 | - | moves (us-them) ~avg | +0.32 | -0.02 | -0.07 | -0.15 | -0.10 | +0.00 | +0.13 |
| r1200 | - | cov (us-them) | +0.30 | -0.05 | -0.24 | -0.13 | -0.12 | -0.03 | +0.15 |
| - | r1100 | aba (us-them) [inverted] | -0.34 | +0.07 | +0.03 | +0.06 | -0.05 | -0.07 | -0.11 |
| - | - | aba (us-them) [inverted] ~avg | -0.22 | +0.07 | +0.05 | +0.07 | +0.01 | -0.04 | -0.06 |
| - | - | cov (us-them) ~avg | +0.20 | -0.03 | -0.12 | -0.16 | -0.13 | -0.09 | +0.02 |
| - | - | drones (us-them) | +0.28 | +0.28 | +0.06 | -0.02 | -0.10 | -0.10 | -0.03 |
| - | - | drones (us-them) ~avg | +0.28 | +0.28 | +0.06 | +0.04 | -0.05 | -0.08 | -0.04 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.20 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.20 | . | . | . | . | . |
| - | - | netguns (us-them) | +0.27 | . | . | +0.27 | +0.17 | +0.02 | +0.06 |
| - | - | netguns (us-them) ~avg | +0.24 | . | . | +0.24 | +0.20 | +0.11 | +0.07 |
| - | - | pickups (us-them) | +0.28 | +0.28 | -0.20 | -0.12 | -0.02 | +0.23 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.28 | +0.28 | -0.10 | -0.11 | -0.07 | +0.10 | +0.22 |
| - | - | soup (us-them) | -0.18 | -0.15 | -0.18 | -0.08 | -0.12 | +0.08 | -0.03 |
| - | - | soup (us-them) ~avg | -0.16 | -0.15 | -0.16 | -0.11 | -0.12 | -0.08 | +0.02 |
| - | - | vaporators (us-them) | +0.27 | +0.18 | +0.17 | +0.01 | -0.01 | +0.18 | +0.12 |
| - | - | vaporators (us-them) ~avg | +0.26 | +0.18 | +0.23 | +0.13 | +0.06 | +0.12 | +0.10 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
