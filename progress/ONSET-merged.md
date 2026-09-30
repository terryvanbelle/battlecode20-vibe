# Which metric starts predicting the result first -- every recorded block of g_iter17 merged

454 games, 174 wins. Noise floor about 0.09; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | worth (us-them) | +0.35 | +0.25 | +0.30 | +0.34 | +0.34 | +0.31 | +0.27 |
| r250 | - | robots (us-them) | +0.36 | +0.23 | +0.28 | +0.33 | +0.36 | +0.32 | +0.32 |
| r250 | - | worth (us-them) ~avg | +0.35 | +0.22 | +0.28 | +0.33 | +0.35 | +0.34 | +0.30 |
| r300 | - | mines (us-them) | +0.31 | +0.26 | +0.26 | +0.31 | +0.31 | +0.28 | +0.25 |
| r300 | - | mines (us-them) ~avg | +0.32 | +0.26 | +0.27 | +0.30 | +0.32 | +0.31 | +0.27 |
| r300 | - | robots (us-them) ~avg | +0.35 | +0.22 | +0.26 | +0.31 | +0.34 | +0.35 | +0.33 |
| r300 | - | units (us-them) | +0.36 | +0.24 | +0.24 | +0.30 | +0.34 | +0.33 | +0.35 |
| r400 | - | spawned (us-them) | +0.31 | +0.22 | +0.24 | +0.27 | +0.31 | +0.30 | +0.30 |
| r400 | - | units (us-them) ~avg | +0.35 | +0.23 | +0.23 | +0.27 | +0.32 | +0.35 | +0.35 |
| r450 | - | landscapers (us-them) | +0.38 | -0.01 | +0.11 | +0.20 | +0.29 | +0.38 | +0.38 |
| r450 | - | spawned (us-them) ~avg | +0.31 | +0.21 | +0.23 | +0.26 | +0.29 | +0.31 | +0.29 |
| r500 | - | moves (us-them) | +0.34 | +0.16 | +0.29 | +0.27 | +0.28 | +0.31 | +0.34 |
| r550 | - | digs (us-them) | +0.45 | +0.11 | +0.09 | +0.19 | +0.24 | +0.34 | +0.40 |
| r550 | - | moves (us-them) ~avg | +0.33 | +0.13 | +0.30 | +0.29 | +0.29 | +0.31 | +0.32 |
| r550 | - | pickups (us-them) | +0.32 | +0.06 | +0.15 | +0.23 | +0.27 | +0.32 | +0.19 |
| r600 | - | dirtDeps (us-them) | +0.42 | +0.08 | +0.07 | +0.16 | +0.20 | +0.31 | +0.37 |
| r600 | - | landscapers (us-them) ~avg | +0.40 | -0.01 | +0.03 | +0.11 | +0.20 | +0.32 | +0.38 |
| r600 | - | pickups (us-them) ~avg | +0.32 | +0.06 | +0.15 | +0.21 | +0.25 | +0.31 | +0.25 |
| r700 | - | digs (us-them) ~avg | +0.42 | +0.11 | +0.07 | +0.15 | +0.19 | +0.27 | +0.37 |
| r750 | - | dirtDeps (us-them) ~avg | +0.39 | +0.08 | +0.05 | +0.12 | +0.16 | +0.23 | +0.34 |
| - | r1200 | aba (us-them) [inverted] | -0.30 | +0.09 | +0.05 | +0.00 | +0.01 | -0.10 | -0.23 |
| - | - | aba (us-them) [inverted] ~avg | -0.24 | +0.09 | +0.08 | +0.04 | +0.03 | -0.03 | -0.15 |
| - | - | cov (us-them) | +0.27 | -0.08 | +0.14 | +0.22 | +0.25 | +0.26 | +0.26 |
| - | - | cov (us-them) ~avg | +0.26 | -0.10 | +0.04 | +0.14 | +0.20 | +0.26 | +0.26 |
| - | - | died (us-them) [inverted] | +0.26 | +0.10 | +0.15 | +0.22 | +0.25 | +0.22 | +0.21 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | +0.09 | +0.15 | +0.20 | +0.24 | +0.25 | +0.24 |
| - | - | drones (us-them) | +0.30 | +0.19 | +0.19 | +0.26 | +0.23 | +0.22 | +0.30 |
| - | - | drones (us-them) ~avg | +0.27 | +0.19 | +0.22 | +0.27 | +0.26 | +0.25 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.16 | +0.18 | +0.08 | +0.03 | +0.04 | +0.03 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.18 | +0.16 | +0.08 | -0.01 | -0.02 | +0.00 | +0.01 |
| - | - | miners (us-them) | +0.23 | +0.21 | +0.18 | +0.19 | +0.22 | +0.17 | +0.20 |
| - | - | miners (us-them) ~avg | +0.24 | +0.20 | +0.23 | +0.22 | +0.23 | +0.23 | +0.20 |
| - | - | netguns (us-them) | +0.20 | +0.04 | +0.01 | +0.02 | +0.05 | +0.17 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.17 | +0.04 | +0.01 | +0.03 | +0.04 | +0.11 | +0.17 |
| - | - | soup (us-them) | +0.08 | -0.06 | -0.05 | +0.06 | -0.02 | +0.01 | -0.05 |
| - | - | soup (us-them) ~avg | -0.07 | -0.06 | -0.07 | -0.01 | +0.01 | +0.02 | -0.01 |
| - | - | vaporators (us-them) | +0.25 | +0.09 | +0.10 | +0.16 | +0.20 | +0.23 | +0.17 |
| - | - | vaporators (us-them) ~avg | +0.23 | +0.09 | +0.10 | +0.12 | +0.17 | +0.23 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
