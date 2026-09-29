# Which metric starts predicting the result first -- every recorded block of g_iter15 merged

182 games, 103 wins. Noise floor about 0.15; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | mines (us-them) | +0.31 | +0.24 | +0.31 | +0.27 | +0.29 | +0.26 | +0.27 |
| r200 | - | mines (us-them) ~avg | +0.31 | +0.22 | +0.31 | +0.29 | +0.29 | +0.29 | +0.26 |
| r200 | - | worth (us-them) | +0.40 | +0.23 | +0.31 | +0.29 | +0.32 | +0.34 | +0.40 |
| r350 | - | digs (us-them) | +0.62 | +0.19 | +0.17 | +0.30 | +0.34 | +0.45 | +0.56 |
| r350 | - | dirtDeps (us-them) | +0.63 | +0.22 | +0.14 | +0.29 | +0.34 | +0.46 | +0.57 |
| r350 | - | worth (us-them) ~avg | +0.37 | +0.20 | +0.28 | +0.29 | +0.31 | +0.33 | +0.35 |
| r450 | - | digs (us-them) ~avg | +0.56 | +0.20 | +0.16 | +0.25 | +0.30 | +0.37 | +0.49 |
| r450 | - | dirtDeps (us-them) ~avg | +0.58 | +0.24 | +0.13 | +0.23 | +0.29 | +0.37 | +0.50 |
| r450 | - | landscapers (us-them) | +0.49 | +0.12 | +0.19 | +0.22 | +0.23 | +0.46 | +0.47 |
| r500 | - | robots (us-them) | +0.43 | +0.23 | +0.24 | +0.26 | +0.24 | +0.37 | +0.43 |
| r500 | - | units (us-them) | +0.46 | +0.29 | +0.23 | +0.22 | +0.17 | +0.34 | +0.41 |
| r550 | - | landscapers (us-them) ~avg | +0.49 | +0.12 | +0.10 | +0.17 | +0.21 | +0.36 | +0.45 |
| r550 | - | netguns (us-them) | +0.39 | . | +0.03 | +0.10 | +0.15 | +0.35 | +0.33 |
| r550 | - | robots (us-them) ~avg | +0.40 | +0.22 | +0.24 | +0.26 | +0.25 | +0.32 | +0.38 |
| r550 | - | spawned (us-them) | +0.41 | +0.23 | +0.21 | +0.22 | +0.20 | +0.31 | +0.38 |
| r600 | - | vaporators (us-them) | +0.40 | +0.05 | +0.03 | +0.02 | +0.16 | +0.32 | +0.34 |
| r700 | - | units (us-them) ~avg | +0.40 | +0.28 | +0.24 | +0.23 | +0.21 | +0.28 | +0.36 |
| r750 | - | netguns (us-them) ~avg | +0.35 | . | +0.01 | +0.08 | +0.11 | +0.23 | +0.32 |
| r800 | - | spawned (us-them) ~avg | +0.36 | +0.22 | +0.22 | +0.23 | +0.22 | +0.28 | +0.32 |
| r800 | - | vaporators (us-them) ~avg | +0.34 | +0.05 | +0.02 | -0.01 | +0.07 | +0.21 | +0.33 |
| r1150 | - | drones (us-them) | +0.35 | +0.21 | +0.14 | +0.20 | +0.14 | +0.10 | +0.27 |
| r1200 | - | moves (us-them) | +0.31 | +0.03 | +0.08 | +0.04 | +0.03 | +0.11 | +0.25 |
| - | - | aba (us-them) [inverted] | +0.21 | +0.12 | +0.15 | +0.11 | +0.11 | +0.02 | -0.16 |
| - | - | aba (us-them) [inverted] ~avg | +0.21 | +0.15 | +0.18 | +0.11 | +0.11 | +0.07 | -0.05 |
| - | - | cov (us-them) | +0.19 | +0.01 | +0.08 | +0.16 | +0.17 | +0.15 | +0.17 |
| - | - | cov (us-them) ~avg | +0.18 | +0.01 | +0.03 | +0.10 | +0.13 | +0.17 | +0.16 |
| - | - | died (us-them) [inverted] | +0.28 | +0.05 | +0.12 | +0.15 | +0.19 | +0.28 | +0.27 |
| - | - | died (us-them) [inverted] ~avg | +0.30 | +0.04 | +0.15 | +0.15 | +0.18 | +0.25 | +0.30 |
| - | - | drones (us-them) ~avg | +0.26 | +0.21 | +0.23 | +0.22 | +0.19 | +0.15 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.29 | +0.29 | +0.11 | +0.05 | +0.12 | +0.12 | +0.09 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.29 | +0.29 | +0.06 | -0.04 | -0.10 | -0.06 | -0.07 |
| - | - | miners (us-them) | +0.20 | +0.15 | +0.12 | +0.03 | -0.05 | -0.03 | +0.02 |
| - | - | miners (us-them) ~avg | +0.20 | +0.17 | +0.19 | +0.12 | +0.04 | +0.02 | -0.01 |
| - | - | moves (us-them) ~avg | +0.24 | +0.01 | +0.06 | +0.03 | +0.01 | +0.06 | +0.15 |
| - | - | pickups (us-them) | +0.27 | +0.16 | +0.26 | +0.23 | +0.23 | +0.16 | +0.26 |
| - | - | pickups (us-them) ~avg | +0.30 | +0.16 | +0.30 | +0.25 | +0.26 | +0.21 | +0.23 |
| - | - | soup (us-them) | -0.18 | -0.05 | +0.10 | +0.04 | +0.07 | -0.18 | -0.06 |
| - | - | soup (us-them) ~avg | -0.16 | -0.04 | +0.06 | +0.09 | +0.12 | -0.09 | -0.15 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
