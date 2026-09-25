# Which metric starts predicting the result first -- every recorded block of g_iter7 merged

230 games, 122 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r250 | - | robots (us-them) | +0.45 | +0.04 | +0.24 | +0.34 | +0.35 | +0.44 | +0.43 |
| r250 | - | spawned (us-them) | +0.44 | +0.04 | +0.23 | +0.32 | +0.32 | +0.40 | +0.43 |
| r300 | - | worth (us-them) | +0.39 | +0.06 | +0.24 | +0.32 | +0.31 | +0.38 | +0.32 |
| r350 | - | robots (us-them) ~avg | +0.45 | +0.00 | +0.16 | +0.27 | +0.33 | +0.40 | +0.44 |
| r400 | - | spawned (us-them) ~avg | +0.43 | +0.00 | +0.16 | +0.25 | +0.30 | +0.37 | +0.41 |
| r400 | - | worth (us-them) ~avg | +0.36 | +0.04 | +0.18 | +0.26 | +0.30 | +0.35 | +0.35 |
| r450 | - | units (us-them) | +0.53 | +0.02 | +0.17 | +0.28 | +0.28 | +0.41 | +0.49 |
| r500 | - | landscapers (us-them) | +0.50 | -0.06 | +0.11 | +0.16 | +0.20 | +0.38 | +0.49 |
| r500 | - | mines (us-them) | +0.38 | +0.02 | +0.22 | +0.27 | +0.30 | +0.34 | +0.38 |
| r500 | - | units (us-them) ~avg | +0.49 | -0.00 | +0.11 | +0.21 | +0.26 | +0.36 | +0.45 |
| r550 | - | mines (us-them) ~avg | +0.37 | -0.01 | +0.15 | +0.22 | +0.27 | +0.32 | +0.35 |
| r650 | - | digs (us-them) | +0.47 | -0.17 | -0.02 | +0.08 | +0.15 | +0.27 | +0.41 |
| r650 | - | landscapers (us-them) ~avg | +0.47 | -0.05 | +0.04 | +0.09 | +0.14 | +0.29 | +0.45 |
| r700 | - | dirtDeps (us-them) | +0.46 | -0.14 | -0.01 | +0.06 | +0.13 | +0.25 | +0.39 |
| r900 | - | digs (us-them) ~avg | +0.40 | -0.15 | -0.04 | +0.03 | +0.10 | +0.20 | +0.32 |
| r950 | - | miners (us-them) | +0.33 | +0.10 | +0.10 | +0.19 | +0.17 | +0.27 | +0.29 |
| r950 | - | miners (us-them) ~avg | +0.32 | +0.04 | +0.09 | +0.17 | +0.18 | +0.22 | +0.27 |
| r950 | - | moves (us-them) | +0.35 | -0.01 | +0.00 | +0.09 | +0.11 | +0.19 | +0.29 |
| r1000 | - | cov (us-them) | +0.33 | +0.05 | +0.03 | +0.13 | +0.14 | +0.22 | +0.28 |
| r1000 | - | dirtDeps (us-them) ~avg | +0.38 | -0.12 | -0.03 | +0.01 | +0.08 | +0.17 | +0.30 |
| r1100 | - | drones (us-them) | +0.34 | +0.03 | +0.13 | +0.15 | +0.13 | +0.19 | +0.20 |
| r1200 | - | moves (us-them) ~avg | +0.30 | +0.02 | +0.01 | +0.08 | +0.10 | +0.15 | +0.23 |
| - | - | aba (us-them) [inverted] | -0.14 | +0.04 | +0.08 | +0.10 | +0.12 | +0.07 | -0.04 |
| - | - | aba (us-them) [inverted] ~avg | +0.12 | +0.03 | +0.07 | +0.09 | +0.11 | +0.10 | +0.04 |
| - | - | cov (us-them) ~avg | +0.27 | +0.05 | +0.04 | +0.09 | +0.12 | +0.16 | +0.22 |
| - | - | died (us-them) [inverted] | +0.27 | -0.04 | +0.09 | +0.07 | +0.12 | +0.24 | -0.01 |
| - | - | died (us-them) [inverted] ~avg | +0.21 | -0.07 | +0.08 | +0.12 | +0.12 | +0.19 | +0.07 |
| - | - | drones (us-them) ~avg | +0.24 | +0.03 | +0.14 | +0.17 | +0.16 | +0.17 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.12 | +0.12 | +0.16 | +0.07 | +0.12 | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.12 | +0.14 | +0.10 | +0.10 | +0.19 | +0.18 |
| - | - | netguns (us-them) | +0.10 | . | -0.03 | +0.02 | +0.08 | +0.09 | +0.03 |
| - | - | netguns (us-them) ~avg | -0.08 | . | -0.05 | -0.03 | +0.02 | +0.05 | +0.03 |
| - | - | pickups (us-them) | +0.28 | +0.07 | +0.02 | +0.03 | +0.08 | +0.17 | +0.21 |
| - | - | pickups (us-them) ~avg | +0.22 | +0.07 | +0.04 | +0.04 | +0.05 | +0.11 | +0.18 |
| - | - | soup (us-them) | -0.12 | +0.04 | +0.03 | +0.05 | -0.05 | -0.06 | -0.01 |
| - | - | soup (us-them) ~avg | +0.10 | +0.07 | +0.06 | +0.06 | +0.03 | -0.05 | -0.01 |
| - | - | vaporators (us-them) | +0.27 | +0.03 | +0.04 | +0.10 | +0.16 | +0.27 | +0.18 |
| - | - | vaporators (us-them) ~avg | +0.23 | +0.03 | +0.04 | +0.07 | +0.12 | +0.22 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
