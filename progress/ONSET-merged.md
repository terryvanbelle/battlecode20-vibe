# Which metric starts predicting the result first -- every recorded block of r4s1 merged

228 games, 108 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.42 | +0.31 | +0.21 | +0.37 | +0.42 | +0.40 | +0.38 |
| r50 | - | mines (us-them) ~avg | +0.43 | +0.34 | +0.24 | +0.33 | +0.41 | +0.43 | +0.42 |
| r50 | - | robots (us-them) | +0.49 | +0.28 | +0.15 | +0.35 | +0.42 | +0.46 | +0.48 |
| r50 | - | robots (us-them) ~avg | +0.49 | +0.33 | +0.21 | +0.28 | +0.35 | +0.44 | +0.48 |
| r50 | - | spawned (us-them) | +0.50 | +0.28 | +0.13 | +0.35 | +0.41 | +0.45 | +0.50 |
| r50 | - | spawned (us-them) ~avg | +0.48 | +0.33 | +0.18 | +0.27 | +0.35 | +0.43 | +0.48 |
| r50 | - | worth (us-them) | +0.45 | +0.31 | +0.27 | +0.40 | +0.43 | +0.44 | +0.43 |
| r50 | - | worth (us-them) ~avg | +0.46 | +0.33 | +0.30 | +0.38 | +0.42 | +0.45 | +0.46 |
| r400 | - | units (us-them) | +0.45 | +0.27 | +0.12 | +0.25 | +0.31 | +0.39 | +0.42 |
| r500 | - | cov (us-them) | +0.35 | -0.01 | +0.13 | +0.20 | +0.23 | +0.34 | +0.32 |
| r500 | - | landscapers (us-them) | +0.47 | +0.15 | +0.09 | +0.18 | +0.25 | +0.35 | +0.47 |
| r500 | - | units (us-them) ~avg | +0.45 | +0.31 | +0.17 | +0.21 | +0.26 | +0.35 | +0.41 |
| r500 | - | vaporators (us-them) | +0.34 | +0.07 | +0.08 | +0.23 | +0.29 | +0.33 | +0.32 |
| r600 | - | landscapers (us-them) ~avg | +0.48 | +0.16 | +0.08 | +0.13 | +0.19 | +0.31 | +0.40 |
| r600 | - | vaporators (us-them) ~avg | +0.35 | +0.07 | +0.05 | +0.14 | +0.22 | +0.31 | +0.35 |
| r650 | - | digs (us-them) | +0.47 | +0.23 | +0.13 | +0.14 | +0.20 | +0.30 | +0.36 |
| r650 | - | dirtDeps (us-them) | +0.47 | +0.17 | +0.08 | +0.11 | +0.18 | +0.29 | +0.36 |
| r750 | - | cov (us-them) ~avg | +0.32 | -0.04 | +0.06 | +0.12 | +0.16 | +0.28 | +0.32 |
| r900 | - | digs (us-them) ~avg | +0.39 | +0.24 | +0.14 | +0.14 | +0.17 | +0.24 | +0.30 |
| r900 | - | moves (us-them) | +0.35 | +0.08 | +0.11 | +0.10 | +0.11 | +0.21 | +0.31 |
| r950 | - | dirtDeps (us-them) ~avg | +0.39 | +0.19 | +0.08 | +0.09 | +0.14 | +0.22 | +0.30 |
| r950 | - | pickups (us-them) ~avg | +0.30 | +0.20 | +0.20 | +0.17 | +0.23 | +0.27 | +0.29 |
| r1200 | - | drones (us-them) | +0.30 | +0.20 | +0.01 | +0.07 | +0.12 | +0.19 | +0.23 |
| - | - | aba (us-them) [inverted] | +0.20 | +0.12 | +0.15 | +0.11 | +0.11 | +0.01 | -0.12 |
| - | - | aba (us-them) [inverted] ~avg | +0.20 | +0.15 | +0.17 | +0.14 | +0.13 | +0.08 | -0.02 |
| - | - | died (us-them) [inverted] | +0.23 | -0.01 | +0.10 | +0.07 | +0.10 | +0.16 | +0.08 |
| - | - | died (us-them) [inverted] ~avg | +0.21 | -0.03 | +0.14 | +0.08 | +0.08 | +0.12 | +0.11 |
| - | - | drones (us-them) ~avg | +0.25 | +0.20 | +0.09 | +0.07 | +0.08 | +0.14 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.32 | +0.27 | +0.18 | +0.11 | +0.03 | +0.06 | +0.04 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.30 | +0.27 | +0.16 | +0.12 | +0.09 | +0.07 | +0.06 |
| - | - | miners (us-them) | +0.28 | +0.13 | +0.12 | +0.19 | +0.21 | +0.23 | +0.15 |
| - | - | miners (us-them) ~avg | +0.28 | +0.22 | +0.17 | +0.21 | +0.22 | +0.23 | +0.22 |
| - | - | moves (us-them) ~avg | +0.29 | +0.01 | +0.09 | +0.10 | +0.09 | +0.15 | +0.24 |
| - | - | netguns (us-them) | +0.29 | . | +0.02 | +0.08 | +0.15 | +0.29 | +0.27 |
| - | - | netguns (us-them) ~avg | +0.28 | . | +0.02 | +0.07 | +0.11 | +0.21 | +0.27 |
| - | - | pickups (us-them) | +0.30 | +0.20 | +0.15 | +0.17 | +0.25 | +0.29 | +0.28 |
| - | - | soup (us-them) | +0.13 | -0.07 | +0.10 | -0.03 | -0.02 | +0.06 | -0.04 |
| - | - | soup (us-them) ~avg | +0.12 | -0.08 | +0.08 | +0.08 | +0.09 | +0.05 | +0.01 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
