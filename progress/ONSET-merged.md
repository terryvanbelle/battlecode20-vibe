# Which metric starts predicting the result first -- every recorded block of g_iter9 merged

133 games, 55 wins. Noise floor about 0.17; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r550 | - | pickups (us-them) | +0.31 | +0.06 | +0.04 | +0.11 | +0.22 | +0.31 | +0.26 |
| r600 | - | digs (us-them) | +0.46 | -0.09 | +0.14 | +0.17 | +0.24 | +0.31 | +0.39 |
| r650 | - | dirtDeps (us-them) | +0.44 | -0.09 | +0.11 | +0.13 | +0.19 | +0.29 | +0.38 |
| r700 | - | landscapers (us-them) | +0.39 | +0.06 | +0.12 | +0.13 | +0.17 | +0.28 | +0.35 |
| r700 | - | robots (us-them) | +0.40 | +0.23 | +0.16 | +0.21 | +0.25 | +0.28 | +0.40 |
| r700 | - | robots (us-them) ~avg | +0.39 | +0.25 | +0.20 | +0.21 | +0.24 | +0.28 | +0.36 |
| r700 | - | worth (us-them) | +0.31 | +0.12 | +0.14 | +0.16 | +0.22 | +0.27 | +0.30 |
| r750 | - | spawned (us-them) | +0.37 | +0.23 | +0.16 | +0.24 | +0.26 | +0.26 | +0.35 |
| r750 | - | spawned (us-them) ~avg | +0.36 | +0.25 | +0.20 | +0.22 | +0.25 | +0.28 | +0.32 |
| r750 | - | units (us-them) | +0.39 | +0.25 | +0.18 | +0.19 | +0.20 | +0.22 | +0.35 |
| r800 | - | digs (us-them) ~avg | +0.41 | -0.08 | +0.11 | +0.15 | +0.18 | +0.25 | +0.33 |
| r850 | - | landscapers (us-them) ~avg | +0.37 | +0.07 | +0.10 | +0.09 | +0.11 | +0.22 | +0.32 |
| r900 | - | dirtDeps (us-them) ~avg | +0.40 | -0.08 | +0.10 | +0.10 | +0.13 | +0.21 | +0.31 |
| r950 | - | worth (us-them) ~avg | +0.32 | +0.11 | +0.13 | +0.13 | +0.18 | +0.25 | +0.30 |
| r1000 | - | units (us-them) ~avg | +0.34 | +0.29 | +0.23 | +0.21 | +0.20 | +0.23 | +0.29 |
| - | - | aba (us-them) [inverted] | +0.16 | +0.03 | +0.10 | +0.15 | +0.10 | +0.08 | +0.01 |
| - | - | aba (us-them) [inverted] ~avg | +0.15 | +0.04 | +0.11 | +0.15 | +0.14 | +0.13 | +0.08 |
| - | - | cov (us-them) | +0.29 | +0.11 | +0.29 | +0.22 | +0.20 | +0.12 | +0.04 |
| - | - | cov (us-them) ~avg | +0.28 | +0.09 | +0.26 | +0.28 | +0.26 | +0.21 | +0.13 |
| - | - | died (us-them) [inverted] | +0.19 | -0.07 | +0.12 | -0.08 | +0.04 | +0.14 | +0.15 |
| - | - | died (us-them) [inverted] ~avg | +0.16 | -0.07 | +0.10 | -0.06 | -0.03 | +0.08 | +0.16 |
| - | - | drones (us-them) | +0.20 | +0.13 | -0.01 | +0.04 | +0.02 | +0.02 | +0.13 |
| - | - | drones (us-them) ~avg | +0.15 | +0.13 | +0.08 | +0.06 | +0.04 | +0.01 | +0.08 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.15 | +0.04 | +0.13 | +0.12 | +0.07 | -0.07 | -0.09 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.13 | +0.04 | +0.10 | +0.01 | +0.03 | -0.08 | -0.10 |
| - | - | miners (us-them) | +0.24 | +0.23 | +0.17 | +0.15 | +0.11 | +0.13 | +0.24 |
| - | - | miners (us-them) ~avg | +0.28 | +0.28 | +0.25 | +0.25 | +0.20 | +0.15 | +0.19 |
| - | - | mines (us-them) | +0.27 | +0.15 | +0.21 | +0.26 | +0.26 | +0.19 | +0.15 |
| - | - | mines (us-them) ~avg | +0.27 | +0.16 | +0.20 | +0.23 | +0.26 | +0.26 | +0.20 |
| - | - | moves (us-them) | +0.23 | +0.17 | +0.21 | +0.18 | +0.13 | +0.16 | +0.18 |
| - | - | moves (us-them) ~avg | +0.22 | +0.14 | +0.22 | +0.22 | +0.18 | +0.17 | +0.19 |
| - | - | netguns (us-them) | -0.15 | . | -0.00 | +0.03 | +0.06 | +0.04 | +0.05 |
| - | - | netguns (us-them) ~avg | -0.15 | . | -0.03 | +0.02 | +0.03 | +0.03 | +0.03 |
| - | - | pickups (us-them) ~avg | +0.29 | +0.06 | +0.04 | +0.07 | +0.15 | +0.25 | +0.29 |
| - | - | soup (us-them) | -0.21 | -0.02 | +0.13 | -0.06 | -0.18 | -0.15 | -0.21 |
| - | - | soup (us-them) ~avg | -0.20 | -0.04 | +0.09 | +0.08 | -0.01 | -0.15 | -0.20 |
| - | - | vaporators (us-them) | +0.26 | -0.19 | -0.14 | +0.00 | +0.10 | +0.24 | +0.24 |
| - | - | vaporators (us-them) ~avg | +0.25 | -0.19 | -0.18 | -0.09 | +0.01 | +0.16 | +0.24 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
