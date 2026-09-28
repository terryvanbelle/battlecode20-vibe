# Which metric starts predicting the result first -- every recorded block of r1s17 merged

316 games, 164 wins. Noise floor about 0.11; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | mines (us-them) | +0.36 | +0.28 | +0.32 | +0.32 | +0.36 | +0.34 | +0.33 |
| r150 | - | worth (us-them) | +0.39 | +0.28 | +0.35 | +0.35 | +0.39 | +0.36 | +0.30 |
| r200 | - | mines (us-them) ~avg | +0.36 | +0.27 | +0.30 | +0.33 | +0.35 | +0.36 | +0.34 |
| r200 | - | worth (us-them) ~avg | +0.39 | +0.26 | +0.34 | +0.36 | +0.38 | +0.38 | +0.34 |
| r300 | - | robots (us-them) | +0.41 | +0.21 | +0.29 | +0.30 | +0.35 | +0.39 | +0.38 |
| r400 | - | robots (us-them) ~avg | +0.38 | +0.20 | +0.25 | +0.29 | +0.31 | +0.38 | +0.38 |
| r400 | - | spawned (us-them) | +0.38 | +0.21 | +0.29 | +0.28 | +0.32 | +0.38 | +0.37 |
| r450 | - | landscapers (us-them) | +0.49 | +0.06 | +0.20 | +0.18 | +0.24 | +0.41 | +0.49 |
| r450 | - | spawned (us-them) ~avg | +0.37 | +0.20 | +0.24 | +0.28 | +0.29 | +0.36 | +0.37 |
| r450 | - | units (us-them) | +0.42 | +0.24 | +0.25 | +0.25 | +0.30 | +0.39 | +0.42 |
| r500 | - | digs (us-them) | +0.59 | +0.14 | +0.15 | +0.22 | +0.26 | +0.40 | +0.50 |
| r500 | - | dirtDeps (us-them) | +0.59 | +0.16 | +0.14 | +0.22 | +0.25 | +0.39 | +0.49 |
| r500 | - | units (us-them) ~avg | +0.40 | +0.21 | +0.21 | +0.24 | +0.26 | +0.36 | +0.40 |
| r550 | - | landscapers (us-them) ~avg | +0.47 | +0.07 | +0.12 | +0.15 | +0.19 | +0.34 | +0.42 |
| r550 | - | netguns (us-them) | +0.37 | -0.08 | -0.02 | +0.06 | +0.10 | +0.34 | +0.35 |
| r550 | - | pickups (us-them) | +0.33 | +0.16 | +0.11 | +0.20 | +0.27 | +0.30 | +0.31 |
| r600 | - | digs (us-them) ~avg | +0.52 | +0.15 | +0.11 | +0.19 | +0.22 | +0.32 | +0.42 |
| r600 | - | dirtDeps (us-them) ~avg | +0.52 | +0.16 | +0.11 | +0.18 | +0.21 | +0.31 | +0.41 |
| r600 | - | pickups (us-them) ~avg | +0.32 | +0.15 | +0.15 | +0.20 | +0.24 | +0.30 | +0.31 |
| r900 | - | netguns (us-them) ~avg | +0.33 | -0.08 | -0.03 | +0.01 | +0.06 | +0.17 | +0.30 |
| r950 | - | moves (us-them) | +0.30 | +0.10 | +0.12 | +0.13 | +0.14 | +0.21 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.22 | +0.10 | +0.03 | +0.01 | +0.02 | -0.11 | -0.20 |
| - | - | aba (us-them) [inverted] ~avg | -0.19 | +0.11 | +0.04 | +0.01 | +0.02 | -0.05 | -0.15 |
| - | - | cov (us-them) | +0.21 | +0.02 | +0.02 | +0.13 | +0.13 | +0.21 | +0.14 |
| - | - | cov (us-them) ~avg | +0.16 | +0.01 | -0.05 | +0.02 | +0.06 | +0.15 | +0.14 |
| - | - | died (us-them) [inverted] | +0.21 | +0.09 | +0.08 | +0.13 | +0.21 | +0.15 | +0.10 |
| - | - | died (us-them) [inverted] ~avg | +0.16 | +0.04 | +0.07 | +0.09 | +0.14 | +0.16 | +0.13 |
| - | - | drones (us-them) | +0.29 | +0.19 | +0.16 | +0.19 | +0.28 | +0.26 | +0.28 |
| - | - | drones (us-them) ~avg | +0.30 | +0.18 | +0.22 | +0.23 | +0.26 | +0.30 | +0.29 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.29 | +0.26 | +0.18 | +0.16 | +0.12 | +0.10 | +0.17 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.28 | +0.26 | +0.13 | +0.11 | +0.10 | +0.07 | -0.01 |
| - | - | miners (us-them) | +0.18 | +0.18 | +0.13 | +0.15 | +0.11 | +0.08 | +0.13 |
| - | - | miners (us-them) ~avg | +0.17 | +0.16 | +0.15 | +0.17 | +0.15 | +0.13 | +0.11 |
| - | - | moves (us-them) ~avg | +0.28 | +0.07 | +0.12 | +0.14 | +0.14 | +0.18 | +0.24 |
| - | - | soup (us-them) | +0.08 | -0.01 | -0.02 | +0.08 | +0.07 | -0.02 | -0.05 |
| - | - | soup (us-them) ~avg | +0.11 | +0.01 | +0.01 | +0.07 | +0.11 | +0.06 | +0.02 |
| - | - | vaporators (us-them) | +0.24 | +0.12 | +0.09 | +0.14 | +0.19 | +0.21 | +0.15 |
| - | - | vaporators (us-them) ~avg | +0.21 | +0.12 | +0.10 | +0.12 | +0.15 | +0.20 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
