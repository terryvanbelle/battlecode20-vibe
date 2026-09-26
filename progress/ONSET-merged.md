# Which metric starts predicting the result first -- every recorded block of g_iter11 merged

204 games, 99 wins. Noise floor about 0.14; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.43 | +0.33 | +0.35 | +0.39 | +0.40 | +0.43 | +0.42 |
| r100 | - | mines (us-them) ~avg | +0.44 | +0.32 | +0.35 | +0.37 | +0.40 | +0.43 | +0.43 |
| r100 | - | worth (us-them) | +0.50 | +0.34 | +0.35 | +0.42 | +0.43 | +0.50 | +0.47 |
| r100 | - | worth (us-them) ~avg | +0.49 | +0.33 | +0.35 | +0.39 | +0.42 | +0.48 | +0.49 |
| r250 | - | robots (us-them) | +0.59 | +0.27 | +0.26 | +0.36 | +0.40 | +0.56 | +0.59 |
| r250 | - | spawned (us-them) | +0.57 | +0.27 | +0.25 | +0.37 | +0.40 | +0.54 | +0.57 |
| r300 | - | robots (us-them) ~avg | +0.57 | +0.28 | +0.25 | +0.32 | +0.38 | +0.49 | +0.56 |
| r300 | - | spawned (us-them) ~avg | +0.56 | +0.28 | +0.25 | +0.31 | +0.38 | +0.49 | +0.55 |
| r300 | - | vaporators (us-them) | +0.41 | +0.01 | +0.16 | +0.32 | +0.31 | +0.41 | +0.33 |
| r400 | - | pickups (us-them) | +0.44 | +0.08 | +0.15 | +0.26 | +0.34 | +0.37 | +0.44 |
| r400 | - | vaporators (us-them) ~avg | +0.41 | +0.01 | +0.10 | +0.27 | +0.31 | +0.39 | +0.39 |
| r450 | - | landscapers (us-them) | +0.65 | +0.12 | +0.04 | +0.08 | +0.21 | +0.52 | +0.65 |
| r450 | - | pickups (us-them) ~avg | +0.44 | +0.10 | +0.17 | +0.24 | +0.30 | +0.37 | +0.42 |
| r450 | - | units (us-them) | +0.57 | +0.24 | +0.13 | +0.21 | +0.29 | +0.51 | +0.55 |
| r500 | - | units (us-them) ~avg | +0.55 | +0.25 | +0.16 | +0.17 | +0.24 | +0.40 | +0.53 |
| r600 | - | digs (us-them) | +0.59 | +0.12 | +0.02 | +0.02 | +0.11 | +0.30 | +0.47 |
| r600 | - | landscapers (us-them) ~avg | +0.64 | +0.12 | +0.04 | +0.03 | +0.09 | +0.31 | +0.56 |
| r650 | - | dirtDeps (us-them) | +0.58 | +0.13 | +0.01 | +0.00 | +0.07 | +0.26 | +0.45 |
| r650 | - | miners (us-them) | +0.42 | +0.12 | +0.12 | +0.13 | +0.15 | +0.30 | +0.42 |
| r750 | - | drones (us-them) | +0.34 | +0.19 | +0.17 | +0.24 | +0.22 | +0.25 | +0.32 |
| r800 | - | drones (us-them) ~avg | +0.32 | +0.19 | +0.23 | +0.26 | +0.26 | +0.26 | +0.32 |
| r800 | - | moves (us-them) | +0.40 | -0.15 | -0.06 | +0.05 | +0.12 | +0.23 | +0.35 |
| r850 | - | digs (us-them) ~avg | +0.50 | +0.11 | +0.02 | -0.00 | +0.04 | +0.17 | +0.35 |
| r850 | - | miners (us-them) ~avg | +0.36 | +0.18 | +0.15 | +0.16 | +0.17 | +0.22 | +0.31 |
| r900 | - | dirtDeps (us-them) ~avg | +0.48 | +0.12 | +0.01 | -0.02 | +0.00 | +0.13 | +0.32 |
| r1000 | - | moves (us-them) ~avg | +0.34 | -0.18 | -0.08 | +0.02 | +0.08 | +0.16 | +0.26 |
| - | r950 | aba (us-them) [inverted] | -0.33 | +0.08 | +0.08 | +0.00 | -0.01 | -0.11 | -0.29 |
| - | - | aba (us-them) [inverted] ~avg | -0.26 | +0.06 | +0.07 | +0.01 | -0.01 | -0.06 | -0.17 |
| - | - | cov (us-them) | -0.19 | -0.19 | -0.08 | +0.00 | +0.06 | +0.11 | +0.17 |
| - | - | cov (us-them) ~avg | -0.19 | -0.19 | -0.14 | -0.07 | -0.03 | +0.02 | +0.10 |
| - | - | died (us-them) [inverted] | +0.29 | +0.07 | +0.08 | +0.11 | +0.18 | +0.27 | +0.18 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | +0.07 | +0.11 | +0.11 | +0.13 | +0.22 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.14 | +0.13 | +0.13 | +0.10 | +0.07 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.15 | +0.13 | +0.14 | +0.11 | +0.07 | +0.11 | +0.12 |
| - | - | netguns (us-them) | +0.28 | . | +0.07 | +0.12 | +0.07 | +0.21 | +0.22 |
| - | - | netguns (us-them) ~avg | +0.21 | . | +0.04 | +0.09 | +0.09 | +0.14 | +0.19 |
| - | - | soup (us-them) | +0.23 | -0.01 | +0.12 | +0.01 | +0.06 | -0.07 | -0.13 |
| - | - | soup (us-them) ~avg | +0.17 | -0.00 | +0.17 | +0.08 | +0.07 | +0.00 | -0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
