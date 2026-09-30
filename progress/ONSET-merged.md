# Which metric starts predicting the result first -- every recorded block of g_iter18 merged

319 games, 150 wins. Noise floor about 0.11; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) ~avg | +0.33 | +0.33 | +0.28 | +0.32 | +0.29 | +0.17 | +0.22 |
| r200 | - | mines (us-them) | +0.33 | +0.25 | +0.33 | +0.30 | +0.25 | +0.18 | +0.18 |
| r200 | - | mines (us-them) ~avg | +0.33 | +0.26 | +0.32 | +0.32 | +0.28 | +0.25 | +0.22 |
| r200 | - | robots (us-them) | +0.35 | +0.27 | +0.35 | +0.32 | +0.28 | +0.24 | +0.27 |
| r200 | - | robots (us-them) ~avg | +0.33 | +0.21 | +0.30 | +0.32 | +0.31 | +0.31 | +0.29 |
| r200 | - | spawned (us-them) | +0.33 | +0.27 | +0.33 | +0.27 | +0.21 | +0.18 | +0.24 |
| r200 | - | worth (us-them) | +0.34 | +0.27 | +0.34 | +0.31 | +0.25 | +0.22 | +0.19 |
| r200 | - | worth (us-them) ~avg | +0.32 | +0.26 | +0.31 | +0.32 | +0.28 | +0.26 | +0.22 |
| r450 | - | digs (us-them) | +0.55 | +0.09 | +0.09 | +0.22 | +0.29 | +0.43 | +0.48 |
| r450 | - | dirtDeps (us-them) | +0.54 | +0.07 | +0.07 | +0.21 | +0.28 | +0.43 | +0.47 |
| r450 | - | landscapers (us-them) | +0.41 | +0.05 | +0.20 | +0.22 | +0.26 | +0.37 | +0.36 |
| r500 | - | landscapers (us-them) ~avg | +0.42 | +0.05 | +0.10 | +0.16 | +0.21 | +0.37 | +0.40 |
| r500 | - | units (us-them) | +0.32 | +0.27 | +0.29 | +0.27 | +0.27 | +0.26 | +0.32 |
| r500 | - | units (us-them) ~avg | +0.33 | +0.20 | +0.25 | +0.27 | +0.27 | +0.31 | +0.33 |
| r550 | - | digs (us-them) ~avg | +0.52 | +0.09 | +0.05 | +0.16 | +0.23 | +0.35 | +0.45 |
| r550 | - | dirtDeps (us-them) ~avg | +0.50 | +0.07 | +0.03 | +0.15 | +0.22 | +0.34 | +0.44 |
| - | - | aba (us-them) [inverted] | -0.24 | -0.11 | -0.03 | -0.02 | -0.04 | -0.05 | -0.18 |
| - | - | aba (us-them) [inverted] ~avg | -0.21 | -0.11 | -0.09 | -0.04 | -0.04 | -0.06 | -0.12 |
| - | - | cov (us-them) | +0.20 | +0.04 | +0.16 | +0.20 | +0.16 | +0.09 | +0.04 |
| - | - | cov (us-them) ~avg | +0.16 | +0.02 | +0.11 | +0.15 | +0.15 | +0.14 | +0.08 |
| - | - | died (us-them) [inverted] | +0.26 | +0.07 | +0.12 | +0.19 | +0.24 | +0.23 | +0.17 |
| - | - | died (us-them) [inverted] ~avg | +0.27 | +0.08 | +0.12 | +0.17 | +0.21 | +0.26 | +0.25 |
| - | - | drones (us-them) | +0.34 | +0.34 | +0.20 | +0.25 | +0.24 | +0.09 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.26 | +0.23 | +0.23 | +0.15 | +0.11 | +0.09 | +0.04 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.16 | +0.08 | +0.06 | +0.06 | +0.05 |
| - | - | miners (us-them) | +0.18 | +0.16 | +0.18 | +0.08 | +0.02 | +0.01 | +0.08 |
| - | - | miners (us-them) ~avg | +0.18 | +0.10 | +0.18 | +0.15 | +0.10 | +0.08 | +0.07 |
| - | - | moves (us-them) | +0.27 | +0.13 | +0.24 | +0.23 | +0.20 | +0.22 | +0.27 |
| - | - | moves (us-them) ~avg | +0.26 | +0.11 | +0.25 | +0.24 | +0.22 | +0.22 | +0.25 |
| - | - | netguns (us-them) | +0.29 | . | +0.11 | +0.09 | +0.12 | +0.24 | +0.26 |
| - | - | netguns (us-them) ~avg | +0.30 | . | +0.11 | +0.09 | +0.11 | +0.17 | +0.26 |
| - | - | pickups (us-them) | +0.28 | +0.10 | +0.15 | +0.20 | +0.27 | +0.20 | +0.20 |
| - | - | pickups (us-them) ~avg | +0.26 | +0.09 | +0.17 | +0.20 | +0.24 | +0.26 | +0.24 |
| - | - | soup (us-them) | -0.17 | -0.10 | -0.09 | -0.06 | -0.05 | -0.14 | -0.17 |
| - | - | soup (us-them) ~avg | -0.14 | -0.03 | -0.08 | -0.08 | -0.06 | -0.11 | -0.14 |
| - | - | spawned (us-them) ~avg | +0.30 | +0.21 | +0.28 | +0.29 | +0.26 | +0.24 | +0.23 |
| - | - | vaporators (us-them) | +0.16 | +0.07 | +0.05 | +0.10 | +0.08 | +0.14 | +0.11 |
| - | - | vaporators (us-them) ~avg | +0.15 | +0.06 | +0.08 | +0.07 | +0.07 | +0.11 | +0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
