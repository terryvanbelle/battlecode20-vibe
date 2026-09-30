# Which metric starts predicting the result first

45 games, 18 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.36 | +0.25 | +0.18 | +0.19 | +0.17 | +0.09 | +0.08 |
| r50 | - | mines (us-them) ~avg | +0.36 | +0.29 | +0.21 | +0.21 | +0.19 | +0.16 | +0.09 |
| r400 | - | pickups (us-them) ~avg | +0.31 | -0.05 | +0.16 | +0.22 | +0.31 | +0.26 | +0.28 |
| r500 | - | dirtDeps (us-them) | +0.44 | +0.00 | -0.04 | +0.08 | +0.20 | +0.40 | +0.42 |
| r550 | - | digs (us-them) | +0.42 | +0.12 | -0.05 | +0.07 | +0.20 | +0.37 | +0.42 |
| r650 | - | dirtDeps (us-them) ~avg | +0.41 | -0.00 | -0.03 | +0.04 | +0.12 | +0.28 | +0.41 |
| r700 | - | digs (us-them) ~avg | +0.40 | +0.11 | -0.06 | +0.02 | +0.10 | +0.25 | +0.39 |
| - | - | aba (us-them) [inverted] | +0.22 | -0.03 | -0.02 | -0.12 | -0.07 | +0.01 | -0.09 |
| - | - | aba (us-them) [inverted] ~avg | +0.22 | +0.04 | -0.00 | -0.10 | -0.09 | -0.03 | -0.06 |
| - | - | cov (us-them) | +0.18 | +0.09 | +0.18 | +0.16 | +0.09 | -0.09 | -0.13 |
| - | - | cov (us-them) ~avg | -0.17 | -0.00 | +0.14 | +0.15 | +0.13 | +0.05 | -0.05 |
| - | - | died (us-them) [inverted] | -0.19 | -0.19 | +0.01 | +0.12 | +0.08 | +0.02 | +0.11 |
| - | - | died (us-them) [inverted] ~avg | -0.19 | -0.19 | -0.04 | +0.06 | +0.06 | +0.08 | +0.17 |
| - | - | drones (us-them) | +0.28 | +0.17 | +0.19 | +0.16 | +0.16 | -0.01 | +0.12 |
| - | - | drones (us-them) ~avg | +0.28 | +0.17 | +0.26 | +0.28 | +0.20 | +0.08 | +0.10 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.26 | . | +0.27 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.26 | +0.19 | +0.27 | +0.21 | +0.21 | . |
| - | - | landscapers (us-them) | +0.25 | -0.13 | +0.08 | +0.19 | +0.12 | +0.18 | +0.10 |
| - | - | landscapers (us-them) ~avg | +0.20 | -0.13 | -0.07 | +0.06 | +0.11 | +0.20 | +0.14 |
| - | - | miners (us-them) | -0.14 | +0.06 | -0.03 | +0.05 | -0.08 | -0.14 | +0.08 |
| - | - | miners (us-them) ~avg | +0.14 | +0.10 | +0.02 | +0.04 | +0.03 | -0.04 | +0.00 |
| - | - | moves (us-them) | +0.24 | +0.23 | +0.18 | +0.24 | +0.23 | +0.14 | +0.16 |
| - | - | moves (us-them) ~avg | +0.27 | +0.18 | +0.23 | +0.25 | +0.27 | +0.20 | +0.18 |
| - | - | netguns (us-them) | +0.21 | . | -0.14 | -0.06 | -0.06 | +0.12 | +0.21 |
| - | - | netguns (us-them) ~avg | -0.14 | . | -0.14 | -0.08 | -0.09 | -0.03 | +0.08 |
| - | - | pickups (us-them) | +0.28 | -0.05 | +0.19 | +0.22 | +0.28 | +0.26 | +0.25 |
| - | - | robots (us-them) | +0.22 | +0.05 | +0.12 | +0.22 | +0.10 | +0.06 | +0.10 |
| - | - | robots (us-them) ~avg | +0.16 | +0.06 | +0.08 | +0.16 | +0.14 | +0.12 | +0.10 |
| - | - | soup (us-them) | +0.27 | +0.13 | +0.07 | +0.03 | +0.17 | +0.18 | -0.00 |
| - | - | soup (us-them) ~avg | +0.29 | +0.26 | +0.21 | +0.13 | +0.19 | +0.17 | +0.06 |
| - | - | spawned (us-them) | +0.20 | +0.06 | +0.12 | +0.20 | +0.09 | +0.07 | +0.09 |
| - | - | spawned (us-them) ~avg | +0.15 | +0.08 | +0.08 | +0.15 | +0.14 | +0.11 | +0.07 |
| - | - | units (us-them) | +0.19 | +0.01 | +0.08 | +0.19 | +0.09 | +0.03 | +0.12 |
| - | - | units (us-them) ~avg | +0.14 | +0.06 | +0.04 | +0.13 | +0.13 | +0.11 | +0.11 |
| - | - | vaporators (us-them) | +0.17 | +0.17 | +0.07 | +0.12 | +0.10 | +0.14 | +0.04 |
| - | - | vaporators (us-them) ~avg | +0.17 | +0.17 | +0.12 | +0.11 | +0.09 | +0.13 | +0.09 |
| - | - | worth (us-them) | +0.29 | +0.23 | +0.21 | +0.23 | +0.15 | +0.13 | +0.08 |
| - | - | worth (us-them) ~avg | +0.29 | +0.25 | +0.22 | +0.23 | +0.18 | +0.16 | +0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
