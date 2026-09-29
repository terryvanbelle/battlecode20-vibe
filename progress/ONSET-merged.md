# Which metric starts predicting the result first -- every recorded block of g_iter14 merged

276 games, 128 wins. Noise floor about 0.12; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r500 | - | digs (us-them) | +0.52 | +0.11 | +0.07 | +0.17 | +0.25 | +0.38 | +0.45 |
| r500 | - | dirtDeps (us-them) | +0.50 | +0.12 | +0.07 | +0.16 | +0.22 | +0.37 | +0.43 |
| r500 | - | landscapers (us-them) | +0.40 | +0.10 | +0.05 | +0.21 | +0.26 | +0.34 | +0.40 |
| r500 | - | robots (us-them) ~avg | +0.33 | +0.24 | +0.18 | +0.23 | +0.28 | +0.32 | +0.33 |
| r550 | - | digs (us-them) ~avg | +0.48 | +0.13 | +0.06 | +0.13 | +0.19 | +0.33 | +0.41 |
| r550 | - | units (us-them) ~avg | +0.35 | +0.24 | +0.16 | +0.21 | +0.26 | +0.31 | +0.35 |
| r600 | - | dirtDeps (us-them) ~avg | +0.45 | +0.15 | +0.07 | +0.12 | +0.17 | +0.31 | +0.39 |
| r600 | - | landscapers (us-them) ~avg | +0.41 | +0.11 | -0.01 | +0.09 | +0.18 | +0.31 | +0.37 |
| r700 | - | robots (us-them) | +0.34 | +0.22 | +0.21 | +0.30 | +0.30 | +0.29 | +0.34 |
| r700 | - | units (us-them) | +0.36 | +0.25 | +0.18 | +0.28 | +0.28 | +0.29 | +0.36 |
| r700 | - | worth (us-them) | +0.31 | +0.25 | +0.22 | +0.27 | +0.27 | +0.28 | +0.28 |
| r700 | - | worth (us-them) ~avg | +0.31 | +0.26 | +0.21 | +0.23 | +0.26 | +0.29 | +0.30 |
| r850 | - | spawned (us-them) | +0.31 | +0.22 | +0.19 | +0.25 | +0.26 | +0.26 | +0.31 |
| r900 | - | drones (us-them) | +0.30 | +0.24 | +0.16 | +0.18 | +0.19 | +0.18 | +0.30 |
| r900 | - | spawned (us-them) ~avg | +0.30 | +0.23 | +0.18 | +0.21 | +0.24 | +0.28 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.17 | -0.08 | -0.09 | -0.07 | -0.06 | -0.07 | -0.12 |
| - | - | aba (us-them) [inverted] ~avg | -0.13 | -0.08 | -0.09 | -0.09 | -0.08 | -0.08 | -0.11 |
| - | - | cov (us-them) | +0.19 | +0.11 | +0.16 | +0.10 | +0.09 | +0.06 | +0.08 |
| - | - | cov (us-them) ~avg | +0.16 | +0.08 | +0.15 | +0.10 | +0.10 | +0.08 | +0.08 |
| - | - | died (us-them) [inverted] | +0.26 | +0.10 | +0.09 | +0.21 | +0.24 | +0.20 | +0.21 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | +0.07 | +0.08 | +0.17 | +0.22 | +0.24 | +0.25 |
| - | - | drones (us-them) ~avg | +0.28 | +0.23 | +0.21 | +0.20 | +0.21 | +0.19 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.31 | +0.25 | +0.18 | +0.08 | +0.16 | +0.09 | +0.09 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.31 | +0.25 | +0.16 | +0.03 | +0.07 | +0.08 | +0.09 |
| - | - | miners (us-them) | +0.19 | +0.14 | +0.19 | +0.14 | +0.09 | +0.05 | +0.17 |
| - | - | miners (us-them) ~avg | +0.18 | +0.14 | +0.18 | +0.16 | +0.15 | +0.12 | +0.14 |
| - | - | mines (us-them) | +0.29 | +0.26 | +0.26 | +0.26 | +0.26 | +0.21 | +0.23 |
| - | - | mines (us-them) ~avg | +0.29 | +0.28 | +0.25 | +0.24 | +0.26 | +0.27 | +0.26 |
| - | - | moves (us-them) | +0.26 | +0.14 | +0.17 | +0.16 | +0.19 | +0.21 | +0.26 |
| - | - | moves (us-them) ~avg | +0.25 | +0.11 | +0.19 | +0.16 | +0.19 | +0.21 | +0.25 |
| - | - | netguns (us-them) | +0.27 | +0.05 | +0.01 | +0.08 | +0.07 | +0.27 | +0.22 |
| - | - | netguns (us-them) ~avg | +0.23 | +0.05 | +0.03 | +0.07 | +0.06 | +0.16 | +0.23 |
| - | - | pickups (us-them) | +0.20 | +0.06 | +0.08 | +0.17 | +0.18 | +0.20 | +0.18 |
| - | - | pickups (us-them) ~avg | +0.23 | +0.05 | +0.08 | +0.13 | +0.18 | +0.20 | +0.22 |
| - | - | soup (us-them) | -0.19 | -0.13 | -0.02 | -0.19 | -0.14 | -0.07 | -0.13 |
| - | - | soup (us-them) ~avg | -0.16 | -0.12 | -0.04 | -0.12 | -0.16 | -0.13 | -0.12 |
| - | - | vaporators (us-them) | +0.24 | +0.19 | +0.10 | +0.14 | +0.17 | +0.21 | +0.19 |
| - | - | vaporators (us-them) ~avg | +0.22 | +0.19 | +0.13 | +0.11 | +0.13 | +0.19 | +0.22 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
