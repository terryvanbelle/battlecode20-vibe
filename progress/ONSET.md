# Which metric starts predicting the result first

21 games, 13 wins. Noise floor about 0.44; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.35 | +0.35 | +0.27 | +0.16 | -0.19 | +0.25 | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.35 | +0.35 | +0.30 | +0.30 | +0.24 | +0.22 | -0.20 |
| r150 | - | died (us-them) [inverted] | +0.46 | . | +0.39 | +0.04 | -0.04 | +0.25 | +0.37 |
| r150 | - | died (us-them) [inverted] ~avg | +0.46 | . | +0.38 | +0.19 | -0.06 | +0.10 | +0.44 |
| r150 | - | units (us-them) | +0.52 | +0.17 | +0.30 | +0.15 | -0.07 | +0.04 | +0.33 |
| r200 | - | robots (us-them) | +0.54 | +0.10 | +0.34 | +0.17 | -0.02 | +0.04 | +0.37 |
| r200 | - | spawned (us-them) | +0.43 | +0.10 | +0.31 | +0.16 | -0.01 | -0.02 | +0.19 |
| r250 | - | landscapers (us-them) | +0.44 | +0.09 | +0.28 | +0.25 | +0.21 | +0.29 | +0.39 |
| r300 | - | moves (us-them) | +0.31 | +0.03 | +0.01 | +0.31 | +0.19 | +0.20 | +0.16 |
| r400 | - | digs (us-them) | +0.52 | -0.05 | +0.26 | +0.23 | +0.32 | +0.47 | +0.46 |
| r400 | - | pickups (us-them) | +0.54 | -0.19 | -0.10 | +0.25 | +0.31 | +0.41 | +0.50 |
| r450 | - | dirtDeps (us-them) | +0.51 | +0.00 | +0.24 | +0.16 | +0.25 | +0.41 | +0.43 |
| r500 | - | digs (us-them) ~avg | +0.42 | -0.05 | +0.23 | +0.21 | +0.23 | +0.35 | +0.36 |
| r650 | - | dirtDeps (us-them) ~avg | +0.40 | +0.00 | +0.23 | +0.15 | +0.17 | +0.29 | +0.31 |
| r700 | - | miners (us-them) | +0.72 | +0.10 | +0.07 | -0.09 | -0.16 | -0.13 | +0.58 |
| r850 | - | pickups (us-them) ~avg | +0.45 | -0.19 | -0.16 | +0.02 | +0.14 | +0.24 | +0.37 |
| r1100 | - | landscapers (us-them) ~avg | +0.34 | +0.07 | +0.27 | +0.23 | +0.17 | +0.27 | +0.23 |
| - | r300 | aba (us-them) [inverted] | -0.55 | -0.17 | -0.05 | -0.53 | -0.53 | -0.43 | -0.36 |
| - | r300 | aba (us-them) [inverted] ~avg | -0.50 | -0.20 | -0.13 | -0.37 | -0.50 | -0.43 | -0.32 |
| - | - | cov (us-them) | +0.22 | +0.01 | +0.19 | +0.12 | +0.00 | -0.12 | -0.07 |
| - | - | cov (us-them) ~avg | +0.17 | +0.09 | +0.12 | +0.08 | +0.07 | -0.08 | -0.12 |
| - | - | drones (us-them) | -0.28 | +0.06 | -0.01 | -0.11 | -0.27 | -0.22 | -0.06 |
| - | - | drones (us-them) ~avg | -0.26 | +0.06 | -0.01 | -0.03 | -0.17 | -0.24 | -0.17 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.23 | +0.18 | +0.17 | +0.01 | -0.15 | -0.18 | -0.06 |
| - | - | mines (us-them) | +0.28 | +0.14 | +0.28 | +0.16 | -0.14 | -0.14 | -0.09 |
| - | - | mines (us-them) ~avg | -0.26 | +0.12 | +0.22 | +0.21 | +0.06 | -0.17 | -0.25 |
| - | - | moves (us-them) ~avg | +0.22 | +0.09 | -0.01 | +0.21 | +0.20 | +0.14 | +0.10 |
| - | - | netguns (us-them) | +0.20 | . | +0.17 | +0.17 | +0.12 | +0.03 | +0.07 |
| - | - | netguns (us-them) ~avg | +0.17 | . | +0.17 | +0.16 | +0.14 | +0.08 | +0.10 |
| - | - | robots (us-them) ~avg | +0.30 | +0.10 | +0.27 | +0.23 | +0.05 | -0.03 | +0.04 |
| - | r400 | soup (us-them) | -0.48 | +0.03 | -0.22 | -0.06 | -0.41 | -0.29 | -0.47 |
| - | r450 | soup (us-them) ~avg | -0.46 | +0.02 | +0.01 | -0.01 | -0.24 | -0.43 | -0.42 |
| - | - | spawned (us-them) ~avg | +0.28 | +0.10 | +0.25 | +0.21 | +0.06 | -0.05 | -0.06 |
| - | - | units (us-them) ~avg | +0.32 | +0.17 | +0.29 | +0.23 | +0.02 | -0.03 | +0.03 |
| - | - | vaporators (us-them) | -0.21 | +0.04 | -0.07 | -0.02 | +0.06 | +0.06 | -0.07 |
| - | - | vaporators (us-them) ~avg | -0.17 | +0.04 | -0.13 | -0.06 | +0.04 | +0.03 | -0.04 |
| - | - | worth (us-them) | +0.26 | +0.14 | +0.26 | +0.17 | -0.05 | -0.00 | -0.03 |
| - | - | worth (us-them) ~avg | +0.21 | +0.10 | +0.21 | +0.17 | +0.04 | -0.10 | -0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
