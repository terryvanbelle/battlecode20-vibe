# Which metric starts predicting the result first -- every recorded block of g_iter15 merged

454 games, 234 wins. Noise floor about 0.09; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.42 | +0.23 | +0.35 | +0.40 | +0.41 | +0.38 | +0.37 |
| r200 | - | mines (us-them) | +0.38 | +0.25 | +0.33 | +0.37 | +0.37 | +0.31 | +0.28 |
| r200 | - | mines (us-them) ~avg | +0.37 | +0.25 | +0.31 | +0.35 | +0.37 | +0.36 | +0.31 |
| r200 | - | worth (us-them) ~avg | +0.41 | +0.21 | +0.31 | +0.37 | +0.40 | +0.40 | +0.38 |
| r250 | - | robots (us-them) | +0.42 | +0.24 | +0.30 | +0.36 | +0.37 | +0.41 | +0.39 |
| r250 | - | robots (us-them) ~avg | +0.41 | +0.24 | +0.29 | +0.34 | +0.37 | +0.41 | +0.40 |
| r300 | - | spawned (us-them) | +0.38 | +0.23 | +0.26 | +0.31 | +0.32 | +0.37 | +0.37 |
| r350 | - | digs (us-them) | +0.61 | +0.14 | +0.16 | +0.27 | +0.33 | +0.45 | +0.56 |
| r350 | - | spawned (us-them) ~avg | +0.37 | +0.23 | +0.26 | +0.30 | +0.32 | +0.37 | +0.36 |
| r350 | - | units (us-them) ~avg | +0.40 | +0.28 | +0.28 | +0.30 | +0.31 | +0.37 | +0.40 |
| r400 | - | landscapers (us-them) | +0.50 | +0.12 | +0.18 | +0.24 | +0.32 | +0.50 | +0.49 |
| r450 | - | digs (us-them) ~avg | +0.57 | +0.13 | +0.14 | +0.23 | +0.29 | +0.38 | +0.50 |
| r450 | - | dirtDeps (us-them) | +0.60 | +0.14 | +0.14 | +0.25 | +0.30 | +0.44 | +0.54 |
| r450 | - | landscapers (us-them) ~avg | +0.52 | +0.12 | +0.11 | +0.19 | +0.26 | +0.43 | +0.51 |
| r450 | - | units (us-them) | +0.40 | +0.29 | +0.27 | +0.29 | +0.30 | +0.39 | +0.40 |
| r500 | - | dirtDeps (us-them) ~avg | +0.56 | +0.13 | +0.12 | +0.20 | +0.26 | +0.36 | +0.48 |
| r500 | - | vaporators (us-them) | +0.35 | +0.02 | +0.13 | +0.19 | +0.28 | +0.33 | +0.29 |
| r650 | - | vaporators (us-them) ~avg | +0.32 | +0.02 | +0.10 | +0.14 | +0.21 | +0.29 | +0.32 |
| r850 | - | moves (us-them) | +0.32 | +0.09 | +0.19 | +0.16 | +0.16 | +0.24 | +0.31 |
| r1150 | - | moves (us-them) ~avg | +0.30 | +0.06 | +0.19 | +0.18 | +0.16 | +0.20 | +0.26 |
| r1200 | - | drones (us-them) | +0.30 | +0.22 | +0.18 | +0.20 | +0.20 | +0.17 | +0.28 |
| - | - | aba (us-them) [inverted] | -0.27 | +0.08 | +0.02 | +0.04 | +0.03 | -0.02 | -0.21 |
| - | - | aba (us-them) [inverted] ~avg | -0.19 | +0.10 | +0.04 | +0.04 | +0.03 | +0.01 | -0.10 |
| - | - | cov (us-them) | +0.17 | -0.00 | +0.11 | +0.15 | +0.16 | +0.17 | +0.17 |
| - | - | cov (us-them) ~avg | +0.17 | -0.02 | +0.07 | +0.12 | +0.14 | +0.17 | +0.16 |
| - | - | died (us-them) [inverted] | +0.27 | +0.07 | +0.16 | +0.22 | +0.26 | +0.26 | +0.20 |
| - | - | died (us-them) [inverted] ~avg | +0.28 | +0.05 | +0.18 | +0.22 | +0.25 | +0.28 | +0.26 |
| - | - | drones (us-them) ~avg | +0.27 | +0.22 | +0.25 | +0.23 | +0.23 | +0.20 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.25 | +0.23 | +0.20 | +0.10 | +0.09 | +0.04 | +0.07 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.22 | +0.13 | +0.01 | +0.02 | +0.03 | +0.06 |
| - | - | miners (us-them) | +0.19 | +0.15 | +0.19 | +0.14 | +0.06 | +0.07 | +0.13 |
| - | - | miners (us-them) ~avg | +0.23 | +0.17 | +0.23 | +0.21 | +0.16 | +0.13 | +0.10 |
| - | - | netguns (us-them) | +0.27 | . | +0.01 | +0.08 | +0.10 | +0.25 | +0.25 |
| - | - | netguns (us-them) ~avg | +0.26 | . | +0.00 | +0.06 | +0.08 | +0.17 | +0.24 |
| - | - | pickups (us-them) | +0.23 | +0.13 | +0.19 | +0.17 | +0.19 | +0.18 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.24 | +0.13 | +0.22 | +0.20 | +0.20 | +0.21 | +0.22 |
| - | - | soup (us-them) | -0.16 | -0.05 | +0.01 | +0.01 | -0.04 | -0.15 | -0.06 |
| - | - | soup (us-them) ~avg | -0.13 | -0.05 | +0.01 | +0.01 | -0.01 | -0.12 | -0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
