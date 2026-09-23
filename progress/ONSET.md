# Which metric starts predicting the result first

34 games, 15 wins. Noise floor about 0.34; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.45 | +0.43 | +0.28 | +0.26 | +0.21 | +0.09 | -0.04 |
| r50 | - | mines (us-them) ~avg | +0.51 | +0.51 | +0.38 | +0.33 | +0.28 | +0.19 | +0.01 |
| r100 | - | worth (us-them) | +0.32 | +0.32 | +0.28 | +0.25 | +0.23 | +0.21 | +0.18 |
| r100 | - | worth (us-them) ~avg | +0.38 | +0.38 | +0.33 | +0.29 | +0.27 | +0.24 | +0.16 |
| r250 | - | cov (us-them) | +0.31 | +0.11 | +0.29 | +0.28 | +0.24 | +0.25 | +0.07 |
| r500 | - | cov (us-them) ~avg | +0.36 | +0.06 | +0.21 | +0.27 | +0.27 | +0.34 | +0.22 |
| r550 | - | vaporators (us-them) ~avg | +0.30 | +0.23 | +0.28 | +0.30 | +0.26 | +0.30 | +0.26 |
| r650 | - | vaporators (us-them) | +0.31 | +0.23 | +0.27 | +0.28 | +0.22 | +0.29 | +0.23 |
| - | - | aba (us-them) [inverted] | +0.23 | +0.22 | -0.06 | -0.07 | -0.15 | -0.17 | -0.17 |
| - | r950 | aba (us-them) [inverted] ~avg | -0.36 | +0.23 | +0.00 | -0.06 | -0.09 | -0.14 | -0.19 |
| - | - | died (us-them) [inverted] | -0.27 | -0.20 | -0.27 | -0.10 | +0.11 | +0.13 | +0.15 |
| - | - | died (us-them) [inverted] ~avg | -0.25 | -0.20 | -0.25 | -0.16 | -0.01 | +0.11 | +0.15 |
| - | - | digs (us-them) | +0.23 | +0.07 | -0.03 | +0.07 | +0.15 | +0.21 | +0.15 |
| - | - | digs (us-them) ~avg | +0.12 | +0.00 | -0.07 | +0.02 | +0.08 | +0.11 | +0.10 |
| - | - | dirtDeps (us-them) | +0.22 | +0.08 | -0.03 | +0.06 | +0.12 | +0.18 | +0.14 |
| - | - | dirtDeps (us-them) ~avg | +0.11 | +0.02 | -0.06 | +0.01 | +0.06 | +0.08 | +0.09 |
| - | - | drones (us-them) | +0.25 | +0.08 | +0.24 | +0.25 | +0.13 | +0.03 | -0.02 |
| - | - | drones (us-them) ~avg | +0.23 | +0.08 | +0.21 | +0.23 | +0.19 | +0.10 | -0.01 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.20 | . | . | . | +0.20 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.20 | . | . | . | +0.20 | . |
| - | - | landscapers (us-them) | +0.27 | -0.06 | +0.08 | +0.03 | +0.20 | +0.16 | +0.24 |
| - | - | landscapers (us-them) ~avg | -0.14 | -0.08 | -0.05 | -0.01 | +0.07 | +0.12 | +0.12 |
| - | - | miners (us-them) | +0.14 | +0.13 | -0.08 | +0.06 | +0.11 | +0.11 | +0.04 |
| - | - | miners (us-them) ~avg | +0.18 | +0.18 | +0.08 | +0.07 | +0.10 | +0.15 | +0.06 |
| - | - | moves (us-them) | +0.13 | -0.05 | -0.04 | -0.09 | -0.03 | +0.10 | +0.08 |
| - | - | moves (us-them) ~avg | +0.17 | -0.02 | +0.01 | -0.02 | -0.01 | +0.05 | +0.06 |
| - | - | netguns (us-them) | +0.21 | . | -0.03 | -0.08 | -0.11 | +0.08 | +0.21 |
| - | - | netguns (us-them) ~avg | -0.12 | . | -0.03 | -0.07 | -0.10 | -0.07 | +0.05 |
| - | - | pickups (us-them) | -0.19 | -0.19 | -0.09 | +0.01 | +0.11 | +0.02 | +0.10 |
| - | - | pickups (us-them) ~avg | -0.26 | -0.26 | -0.13 | -0.09 | -0.01 | +0.02 | +0.05 |
| - | - | robots (us-them) | +0.22 | +0.09 | +0.19 | +0.19 | +0.22 | +0.17 | +0.13 |
| - | - | robots (us-them) ~avg | +0.22 | +0.14 | +0.19 | +0.20 | +0.22 | +0.21 | +0.10 |
| - | r500 | soup (us-them) | -0.34 | +0.09 | -0.21 | -0.19 | -0.06 | -0.25 | +0.10 |
| - | - | soup (us-them) ~avg | -0.18 | +0.12 | +0.07 | -0.02 | -0.04 | -0.14 | -0.04 |
| - | - | spawned (us-them) | +0.24 | +0.14 | +0.24 | +0.24 | +0.22 | +0.15 | +0.11 |
| - | - | spawned (us-them) ~avg | +0.25 | +0.18 | +0.23 | +0.25 | +0.25 | +0.21 | +0.08 |
| - | - | units (us-them) | +0.21 | +0.07 | +0.08 | +0.13 | +0.21 | +0.14 | +0.12 |
| - | - | units (us-them) ~avg | +0.18 | +0.11 | +0.08 | +0.11 | +0.17 | +0.17 | +0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
