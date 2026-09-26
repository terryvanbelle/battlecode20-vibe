# Which metric starts predicting the result first -- every recorded block of g_iter12 merged

197 games, 105 wins. Noise floor about 0.14; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.51 | +0.25 | +0.33 | +0.43 | +0.47 | +0.46 | +0.46 |
| r250 | - | mines (us-them) | +0.43 | +0.29 | +0.28 | +0.38 | +0.43 | +0.42 | +0.41 |
| r250 | - | robots (us-them) | +0.58 | +0.15 | +0.19 | +0.38 | +0.47 | +0.50 | +0.56 |
| r250 | - | worth (us-them) ~avg | +0.53 | +0.24 | +0.29 | +0.38 | +0.44 | +0.48 | +0.48 |
| r300 | - | mines (us-them) ~avg | +0.46 | +0.28 | +0.26 | +0.32 | +0.38 | +0.43 | +0.44 |
| r300 | - | spawned (us-them) | +0.60 | +0.13 | +0.13 | +0.35 | +0.44 | +0.49 | +0.56 |
| r350 | - | robots (us-them) ~avg | +0.59 | +0.13 | +0.14 | +0.27 | +0.37 | +0.49 | +0.57 |
| r350 | - | units (us-them) | +0.56 | +0.23 | +0.14 | +0.26 | +0.36 | +0.46 | +0.53 |
| r400 | - | spawned (us-them) ~avg | +0.59 | +0.12 | +0.10 | +0.22 | +0.32 | +0.45 | +0.54 |
| r450 | - | landscapers (us-them) | +0.56 | +0.09 | +0.06 | +0.10 | +0.23 | +0.46 | +0.56 |
| r450 | - | units (us-them) ~avg | +0.57 | +0.21 | +0.14 | +0.22 | +0.29 | +0.43 | +0.55 |
| r500 | - | vaporators (us-them) | +0.40 | +0.14 | +0.15 | +0.25 | +0.29 | +0.34 | +0.35 |
| r550 | - | vaporators (us-them) ~avg | +0.41 | +0.14 | +0.12 | +0.18 | +0.24 | +0.32 | +0.35 |
| r600 | - | digs (us-them) | +0.56 | +0.17 | +0.13 | +0.19 | +0.24 | +0.33 | +0.46 |
| r600 | - | landscapers (us-them) ~avg | +0.59 | +0.10 | +0.03 | +0.06 | +0.14 | +0.34 | +0.54 |
| r650 | - | dirtDeps (us-them) | +0.54 | +0.11 | +0.07 | +0.14 | +0.20 | +0.29 | +0.44 |
| r700 | - | digs (us-them) ~avg | +0.50 | +0.16 | +0.11 | +0.15 | +0.20 | +0.27 | +0.39 |
| r750 | - | moves (us-them) | +0.42 | +0.10 | +0.16 | +0.17 | +0.18 | +0.26 | +0.36 |
| r800 | - | dirtDeps (us-them) ~avg | +0.48 | +0.10 | +0.04 | +0.09 | +0.15 | +0.23 | +0.35 |
| r800 | - | miners (us-them) | +0.31 | +0.18 | +0.15 | +0.24 | +0.25 | +0.26 | +0.31 |
| r900 | - | moves (us-them) ~avg | +0.37 | +0.07 | +0.16 | +0.19 | +0.19 | +0.23 | +0.31 |
| r900 | - | netguns (us-them) | +0.38 | . | -0.08 | -0.06 | +0.03 | +0.25 | +0.31 |
| r950 | - | miners (us-them) ~avg | +0.32 | +0.16 | +0.16 | +0.24 | +0.26 | +0.27 | +0.30 |
| r1050 | - | drones (us-them) | +0.35 | +0.12 | +0.08 | +0.19 | +0.19 | +0.14 | +0.24 |
| - | r900 | aba (us-them) [inverted] | -0.35 | +0.12 | +0.09 | +0.08 | -0.02 | -0.17 | -0.31 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | +0.11 | +0.10 | +0.11 | +0.05 | -0.08 | -0.22 |
| - | - | cov (us-them) | +0.23 | +0.00 | +0.12 | +0.12 | +0.18 | +0.21 | +0.19 |
| - | - | cov (us-them) ~avg | +0.21 | -0.03 | +0.07 | +0.07 | +0.12 | +0.16 | +0.19 |
| - | - | died (us-them) [inverted] | +0.23 | +0.14 | +0.18 | +0.11 | +0.20 | +0.11 | +0.07 |
| - | - | died (us-them) [inverted] ~avg | +0.22 | +0.12 | +0.20 | +0.19 | +0.20 | +0.16 | +0.15 |
| - | - | drones (us-them) ~avg | +0.29 | +0.11 | +0.11 | +0.16 | +0.18 | +0.16 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.27 | +0.23 | +0.05 | -0.06 | -0.04 | -0.06 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.27 | +0.18 | -0.01 | -0.06 | -0.05 | -0.06 |
| - | - | netguns (us-them) ~avg | +0.28 | . | -0.05 | -0.08 | -0.04 | +0.09 | +0.21 |
| - | - | pickups (us-them) | +0.24 | +0.05 | +0.10 | +0.16 | +0.19 | +0.16 | +0.20 |
| - | - | pickups (us-them) ~avg | +0.21 | +0.06 | +0.12 | +0.16 | +0.19 | +0.18 | +0.21 |
| - | - | soup (us-them) | -0.28 | +0.13 | +0.14 | -0.03 | -0.11 | -0.16 | -0.28 |
| - | - | soup (us-them) ~avg | +0.20 | +0.18 | +0.20 | +0.17 | +0.08 | -0.05 | -0.18 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
