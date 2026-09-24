# Which metric starts predicting the result first -- every recorded block of g_iter6 merged

786 games, 458 wins. Noise floor about 0.07; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.44 | +0.18 | +0.36 | +0.41 | +0.41 | +0.44 | +0.40 |
| r200 | - | mines (us-them) | +0.40 | +0.14 | +0.31 | +0.35 | +0.36 | +0.37 | +0.38 |
| r200 | - | robots (us-them) | +0.52 | +0.13 | +0.33 | +0.41 | +0.42 | +0.46 | +0.50 |
| r200 | - | spawned (us-them) | +0.52 | +0.13 | +0.33 | +0.39 | +0.40 | +0.43 | +0.49 |
| r200 | - | worth (us-them) ~avg | +0.45 | +0.15 | +0.32 | +0.38 | +0.41 | +0.44 | +0.45 |
| r250 | - | spawned (us-them) ~avg | +0.52 | +0.08 | +0.25 | +0.34 | +0.39 | +0.43 | +0.47 |
| r300 | - | mines (us-them) ~avg | +0.40 | +0.11 | +0.25 | +0.32 | +0.35 | +0.37 | +0.39 |
| r300 | - | robots (us-them) ~avg | +0.55 | +0.08 | +0.25 | +0.35 | +0.40 | +0.46 | +0.51 |
| r300 | - | units (us-them) | +0.53 | +0.09 | +0.25 | +0.33 | +0.34 | +0.41 | +0.49 |
| r350 | - | landscapers (us-them) | +0.58 | -0.07 | +0.18 | +0.26 | +0.34 | +0.49 | +0.56 |
| r400 | - | units (us-them) ~avg | +0.53 | +0.07 | +0.19 | +0.27 | +0.32 | +0.40 | +0.47 |
| r500 | - | landscapers (us-them) ~avg | +0.59 | -0.07 | +0.09 | +0.16 | +0.24 | +0.41 | +0.56 |
| r550 | - | digs (us-them) | +0.59 | -0.08 | +0.04 | +0.12 | +0.19 | +0.39 | +0.55 |
| r550 | - | dirtDeps (us-them) | +0.58 | -0.06 | +0.05 | +0.10 | +0.16 | +0.38 | +0.54 |
| r550 | - | pickups (us-them) | +0.37 | +0.12 | +0.15 | +0.19 | +0.26 | +0.32 | +0.30 |
| r650 | - | pickups (us-them) ~avg | +0.37 | +0.12 | +0.17 | +0.19 | +0.23 | +0.29 | +0.32 |
| r700 | - | digs (us-them) ~avg | +0.53 | -0.08 | +0.01 | +0.06 | +0.12 | +0.25 | +0.44 |
| r700 | - | dirtDeps (us-them) ~avg | +0.52 | -0.05 | +0.02 | +0.05 | +0.10 | +0.23 | +0.43 |
| r1200 | - | cov (us-them) | +0.30 | -0.09 | -0.04 | +0.08 | +0.12 | +0.21 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.21 | +0.02 | -0.01 | +0.01 | +0.01 | -0.00 | -0.09 |
| - | - | aba (us-them) [inverted] ~avg | -0.11 | -0.00 | -0.00 | +0.01 | +0.01 | +0.01 | -0.03 |
| - | - | cov (us-them) ~avg | +0.26 | -0.07 | -0.06 | +0.01 | +0.06 | +0.15 | +0.21 |
| - | - | died (us-them) [inverted] | +0.16 | -0.06 | +0.03 | +0.09 | +0.11 | +0.16 | -0.00 |
| - | - | died (us-them) [inverted] ~avg | +0.17 | -0.06 | -0.00 | +0.08 | +0.11 | +0.17 | +0.07 |
| - | - | drones (us-them) | +0.29 | +0.20 | +0.20 | +0.14 | +0.08 | +0.10 | +0.16 |
| - | - | drones (us-them) ~avg | +0.24 | +0.20 | +0.24 | +0.22 | +0.15 | +0.12 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.14 | +0.13 | +0.13 | +0.09 | +0.07 | +0.06 | +0.06 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.15 | +0.13 | +0.13 | +0.10 | +0.08 | +0.07 | +0.05 |
| - | - | miners (us-them) | +0.19 | +0.14 | +0.13 | +0.14 | +0.12 | +0.14 | +0.14 |
| - | - | miners (us-them) ~avg | +0.22 | +0.10 | +0.13 | +0.16 | +0.16 | +0.16 | +0.18 |
| - | - | moves (us-them) | +0.30 | +0.02 | +0.04 | +0.13 | +0.17 | +0.19 | +0.23 |
| - | - | moves (us-them) ~avg | +0.25 | +0.05 | +0.04 | +0.09 | +0.13 | +0.17 | +0.20 |
| - | - | netguns (us-them) | +0.25 | -0.04 | +0.07 | +0.17 | +0.19 | +0.17 | +0.19 |
| - | - | netguns (us-them) ~avg | +0.21 | -0.04 | +0.04 | +0.13 | +0.16 | +0.18 | +0.17 |
| - | - | soup (us-them) | +0.15 | +0.07 | +0.01 | +0.01 | +0.02 | +0.03 | +0.04 |
| - | - | soup (us-them) ~avg | +0.16 | +0.10 | +0.09 | +0.06 | +0.04 | +0.01 | +0.05 |
| - | - | vaporators (us-them) | +0.28 | +0.10 | +0.16 | +0.18 | +0.21 | +0.28 | +0.24 |
| - | - | vaporators (us-them) ~avg | +0.28 | +0.11 | +0.16 | +0.17 | +0.20 | +0.27 | +0.27 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
