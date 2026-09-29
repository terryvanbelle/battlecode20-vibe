# Which metric starts predicting the result first

46 games, 23 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r300 | - | cov (us-them) | +0.43 | +0.11 | +0.20 | +0.31 | +0.39 | +0.40 | +0.30 |
| r350 | - | cov (us-them) ~avg | +0.42 | +0.01 | +0.16 | +0.26 | +0.33 | +0.41 | +0.39 |
| r350 | - | drones (us-them) | +0.36 | +0.24 | +0.05 | +0.25 | +0.32 | +0.34 | +0.34 |
| r400 | - | pickups (us-them) | +0.39 | +0.15 | +0.02 | +0.17 | +0.32 | +0.34 | +0.38 |
| r400 | - | robots (us-them) | +0.37 | +0.08 | +0.10 | +0.20 | +0.34 | +0.32 | +0.13 |
| r400 | - | spawned (us-them) | +0.35 | +0.10 | +0.11 | +0.18 | +0.33 | +0.35 | +0.15 |
| r400 | - | worth (us-them) | +0.30 | +0.04 | +0.04 | +0.25 | +0.30 | +0.13 | +0.01 |
| r450 | - | drones (us-them) ~avg | +0.36 | +0.24 | +0.13 | +0.25 | +0.30 | +0.36 | +0.35 |
| r450 | - | units (us-them) | +0.41 | +0.09 | +0.05 | +0.12 | +0.29 | +0.40 | +0.18 |
| r500 | - | pickups (us-them) ~avg | +0.39 | +0.15 | +0.10 | +0.15 | +0.24 | +0.33 | +0.36 |
| r500 | - | robots (us-them) ~avg | +0.33 | +0.12 | +0.11 | +0.15 | +0.23 | +0.33 | +0.21 |
| r500 | - | spawned (us-them) ~avg | +0.33 | +0.14 | +0.12 | +0.15 | +0.22 | +0.33 | +0.22 |
| r550 | - | landscapers (us-them) | +0.32 | +0.01 | +0.03 | +0.00 | +0.20 | +0.31 | +0.02 |
| r550 | - | units (us-them) ~avg | +0.36 | +0.12 | +0.05 | +0.09 | +0.16 | +0.34 | +0.31 |
| r900 | - | mines (us-them) | +0.32 | +0.06 | +0.06 | +0.21 | +0.30 | +0.21 | +0.30 |
| - | - | aba (us-them) [inverted] | +0.23 | +0.18 | +0.03 | -0.06 | +0.04 | +0.01 | -0.04 |
| - | - | aba (us-them) [inverted] ~avg | +0.23 | +0.19 | +0.15 | -0.02 | +0.01 | +0.04 | +0.01 |
| - | - | died (us-them) [inverted] | +0.35 | -0.09 | +0.01 | +0.17 | +0.24 | +0.16 | -0.07 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | -0.13 | +0.01 | +0.07 | +0.16 | +0.23 | +0.09 |
| - | - | digs (us-them) | +0.30 | +0.03 | -0.11 | -0.08 | -0.06 | +0.02 | +0.12 |
| - | - | digs (us-them) ~avg | +0.16 | +0.02 | -0.13 | -0.10 | -0.08 | -0.02 | +0.05 |
| - | - | dirtDeps (us-them) | +0.30 | -0.01 | -0.10 | -0.11 | -0.08 | +0.01 | +0.11 |
| - | - | dirtDeps (us-them) ~avg | +0.15 | -0.02 | -0.13 | -0.12 | -0.10 | -0.03 | +0.04 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.37 | +0.16 | +0.21 | +0.18 | +0.20 | +0.28 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.35 | +0.16 | +0.20 | +0.19 | +0.19 | +0.20 | +0.21 |
| - | - | landscapers (us-them) ~avg | +0.25 | -0.01 | -0.01 | -0.01 | +0.06 | +0.22 | +0.17 |
| - | - | miners (us-them) | +0.25 | +0.02 | +0.05 | +0.14 | +0.17 | +0.25 | +0.22 |
| - | - | miners (us-them) ~avg | +0.24 | +0.09 | +0.06 | +0.09 | +0.14 | +0.24 | +0.23 |
| - | - | mines (us-them) ~avg | +0.28 | +0.10 | +0.03 | +0.12 | +0.21 | +0.25 | +0.25 |
| - | - | moves (us-them) | +0.20 | +0.15 | +0.03 | -0.05 | -0.04 | +0.20 | +0.17 |
| - | - | moves (us-them) ~avg | +0.17 | +0.09 | +0.09 | +0.01 | -0.01 | +0.13 | +0.17 |
| - | - | netguns (us-them) | -0.25 | . | -0.25 | -0.13 | -0.04 | +0.14 | +0.19 |
| - | - | netguns (us-them) ~avg | +0.17 | . | -0.12 | -0.12 | -0.08 | -0.01 | +0.12 |
| - | r850 | soup (us-them) | -0.37 | -0.21 | -0.18 | +0.27 | +0.14 | -0.21 | -0.34 |
| - | - | soup (us-them) ~avg | -0.29 | -0.20 | -0.25 | -0.07 | +0.09 | +0.10 | -0.06 |
| - | - | vaporators (us-them) | +0.17 | +0.17 | +0.07 | +0.06 | +0.05 | -0.03 | -0.05 |
| - | - | vaporators (us-them) ~avg | +0.17 | +0.17 | +0.08 | +0.06 | +0.06 | -0.02 | -0.07 |
| - | - | worth (us-them) ~avg | +0.26 | +0.06 | +0.05 | +0.15 | +0.24 | +0.21 | +0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
