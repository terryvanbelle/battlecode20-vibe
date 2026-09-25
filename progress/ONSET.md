# Which metric starts predicting the result first

46 games, 17 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.43 | +0.31 | +0.27 | +0.28 | +0.36 | +0.42 | +0.40 |
| r100 | - | mines (us-them) ~avg | +0.42 | +0.31 | +0.30 | +0.26 | +0.31 | +0.40 | +0.42 |
| r100 | - | worth (us-them) | +0.59 | +0.38 | +0.34 | +0.44 | +0.52 | +0.58 | +0.53 |
| r100 | - | worth (us-them) ~avg | +0.58 | +0.36 | +0.38 | +0.40 | +0.48 | +0.57 | +0.57 |
| r150 | - | soup (us-them) | +0.36 | +0.28 | +0.29 | +0.06 | +0.09 | -0.05 | -0.17 |
| r150 | - | soup (us-them) ~avg | +0.38 | +0.22 | +0.38 | +0.27 | +0.17 | +0.05 | -0.10 |
| r200 | - | vaporators (us-them) | +0.49 | +0.08 | +0.31 | +0.41 | +0.41 | +0.49 | +0.41 |
| r300 | - | vaporators (us-them) ~avg | +0.49 | +0.08 | +0.23 | +0.36 | +0.41 | +0.47 | +0.48 |
| r350 | - | pickups (us-them) | +0.37 | . | +0.14 | +0.22 | +0.29 | +0.34 | +0.32 |
| r400 | - | robots (us-them) | +0.63 | +0.02 | +0.04 | +0.13 | +0.35 | +0.53 | +0.58 |
| r450 | - | spawned (us-them) | +0.63 | +0.02 | +0.01 | +0.09 | +0.28 | +0.49 | +0.59 |
| r500 | - | landscapers (us-them) | +0.62 | -0.04 | -0.09 | -0.11 | +0.02 | +0.42 | +0.60 |
| r500 | - | pickups (us-them) ~avg | +0.36 | . | +0.14 | +0.20 | +0.28 | +0.32 | +0.36 |
| r500 | - | robots (us-them) ~avg | +0.61 | +0.08 | +0.04 | +0.05 | +0.18 | +0.45 | +0.56 |
| r500 | - | units (us-them) | +0.54 | -0.05 | -0.08 | -0.10 | +0.10 | +0.43 | +0.50 |
| r550 | - | spawned (us-them) ~avg | +0.60 | +0.08 | +0.03 | +0.03 | +0.12 | +0.38 | +0.54 |
| r700 | - | digs (us-them) | +0.58 | +0.05 | +0.06 | -0.01 | +0.00 | +0.24 | +0.49 |
| r700 | - | dirtDeps (us-them) | +0.58 | +0.04 | +0.06 | -0.02 | -0.02 | +0.21 | +0.48 |
| r700 | - | landscapers (us-them) ~avg | +0.61 | -0.04 | -0.07 | -0.13 | -0.11 | +0.22 | +0.50 |
| r700 | - | units (us-them) ~avg | +0.51 | -0.00 | -0.05 | -0.11 | -0.06 | +0.24 | +0.45 |
| r750 | - | miners (us-them) | +0.38 | -0.05 | -0.09 | -0.10 | +0.11 | +0.24 | +0.37 |
| r850 | - | digs (us-them) ~avg | +0.51 | +0.05 | +0.07 | +0.00 | -0.02 | +0.09 | +0.35 |
| r850 | - | moves (us-them) | +0.42 | -0.19 | -0.18 | -0.12 | -0.07 | +0.13 | +0.34 |
| r900 | - | dirtDeps (us-them) ~avg | +0.50 | +0.04 | +0.07 | -0.00 | -0.04 | +0.06 | +0.33 |
| r950 | - | netguns (us-them) | +0.33 | . | -0.19 | -0.01 | +0.09 | +0.20 | +0.23 |
| r1150 | - | moves (us-them) ~avg | +0.33 | -0.16 | -0.20 | -0.14 | -0.11 | +0.01 | +0.21 |
| - | r950 | aba (us-them) [inverted] | -0.34 | +0.15 | +0.21 | +0.14 | +0.01 | -0.16 | -0.28 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | +0.13 | +0.17 | +0.15 | +0.11 | -0.07 | -0.20 |
| - | - | cov (us-them) | +0.27 | -0.14 | -0.17 | -0.10 | -0.07 | +0.02 | +0.25 |
| - | - | cov (us-them) ~avg | -0.19 | -0.06 | -0.19 | -0.14 | -0.13 | -0.09 | +0.07 |
| - | - | died (us-them) [inverted] | +0.28 | . | +0.22 | +0.12 | +0.22 | +0.26 | +0.16 |
| - | - | died (us-them) [inverted] ~avg | +0.27 | . | +0.25 | +0.23 | +0.24 | +0.25 | +0.21 |
| - | - | drones (us-them) | +0.28 | +0.04 | +0.08 | +0.12 | +0.11 | +0.17 | +0.24 |
| - | - | drones (us-them) ~avg | +0.25 | +0.04 | +0.09 | +0.09 | +0.13 | +0.14 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.11 | +0.11 | . | +0.11 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.20 | +0.11 | +0.11 | +0.11 | -0.20 | -0.20 | -0.20 |
| - | - | miners (us-them) ~avg | +0.28 | +0.02 | -0.04 | -0.08 | -0.02 | +0.09 | +0.22 |
| - | - | netguns (us-them) ~avg | +0.27 | . | -0.19 | -0.11 | -0.01 | +0.12 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
