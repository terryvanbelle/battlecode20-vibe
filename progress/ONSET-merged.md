# Which metric starts predicting the result first -- every recorded block of g_iter15 merged

410 games, 214 wins. Noise floor about 0.10; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.42 | +0.22 | +0.35 | +0.40 | +0.41 | +0.39 | +0.37 |
| r200 | - | mines (us-them) | +0.37 | +0.24 | +0.32 | +0.36 | +0.37 | +0.32 | +0.30 |
| r200 | - | mines (us-them) ~avg | +0.38 | +0.24 | +0.31 | +0.34 | +0.37 | +0.36 | +0.32 |
| r200 | - | worth (us-them) ~avg | +0.41 | +0.21 | +0.32 | +0.37 | +0.40 | +0.41 | +0.38 |
| r250 | - | robots (us-them) | +0.44 | +0.23 | +0.30 | +0.37 | +0.38 | +0.43 | +0.41 |
| r250 | - | robots (us-them) ~avg | +0.43 | +0.23 | +0.29 | +0.35 | +0.37 | +0.43 | +0.42 |
| r300 | - | spawned (us-them) | +0.39 | +0.22 | +0.26 | +0.30 | +0.32 | +0.39 | +0.39 |
| r300 | - | units (us-them) | +0.43 | +0.29 | +0.28 | +0.30 | +0.32 | +0.42 | +0.42 |
| r300 | - | units (us-them) ~avg | +0.42 | +0.27 | +0.28 | +0.31 | +0.32 | +0.40 | +0.42 |
| r350 | - | digs (us-them) | +0.63 | +0.15 | +0.18 | +0.28 | +0.34 | +0.46 | +0.57 |
| r350 | - | spawned (us-them) ~avg | +0.38 | +0.23 | +0.26 | +0.30 | +0.32 | +0.38 | +0.38 |
| r400 | - | dirtDeps (us-them) | +0.63 | +0.15 | +0.15 | +0.26 | +0.31 | +0.45 | +0.57 |
| r400 | - | landscapers (us-them) | +0.51 | +0.12 | +0.18 | +0.25 | +0.33 | +0.51 | +0.51 |
| r450 | - | digs (us-them) ~avg | +0.58 | +0.14 | +0.15 | +0.24 | +0.30 | +0.39 | +0.51 |
| r450 | - | landscapers (us-them) ~avg | +0.54 | +0.12 | +0.11 | +0.19 | +0.26 | +0.44 | +0.51 |
| r500 | - | died (us-them) [inverted] ~avg | +0.31 | +0.05 | +0.19 | +0.24 | +0.28 | +0.31 | +0.29 |
| r500 | - | dirtDeps (us-them) ~avg | +0.57 | +0.14 | +0.13 | +0.21 | +0.27 | +0.37 | +0.50 |
| r500 | - | vaporators (us-them) | +0.35 | +0.00 | +0.12 | +0.17 | +0.26 | +0.33 | +0.29 |
| r700 | - | vaporators (us-them) ~avg | +0.32 | +0.00 | +0.09 | +0.12 | +0.19 | +0.28 | +0.32 |
| r800 | - | moves (us-them) | +0.34 | +0.07 | +0.18 | +0.16 | +0.16 | +0.25 | +0.33 |
| r1000 | - | moves (us-them) ~avg | +0.32 | +0.05 | +0.18 | +0.18 | +0.16 | +0.21 | +0.27 |
| r1150 | - | drones (us-them) | +0.32 | +0.24 | +0.21 | +0.23 | +0.22 | +0.19 | +0.29 |
| - | - | aba (us-them) [inverted] | -0.27 | +0.09 | +0.05 | +0.07 | +0.05 | -0.02 | -0.22 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | +0.11 | +0.06 | +0.06 | +0.06 | +0.02 | -0.11 |
| - | - | cov (us-them) | +0.19 | +0.01 | +0.11 | +0.17 | +0.18 | +0.18 | +0.18 |
| - | - | cov (us-them) ~avg | +0.19 | -0.01 | +0.07 | +0.13 | +0.15 | +0.18 | +0.18 |
| - | - | died (us-them) [inverted] | +0.30 | +0.08 | +0.18 | +0.26 | +0.30 | +0.29 | +0.22 |
| - | - | drones (us-them) ~avg | +0.30 | +0.23 | +0.28 | +0.27 | +0.25 | +0.22 | +0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.26 | +0.23 | +0.20 | +0.09 | +0.10 | +0.04 | +0.08 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.12 | -0.00 | +0.01 | +0.02 | +0.06 |
| - | - | miners (us-them) | +0.19 | +0.14 | +0.19 | +0.15 | +0.09 | +0.09 | +0.16 |
| - | - | miners (us-them) ~avg | +0.23 | +0.17 | +0.22 | +0.22 | +0.17 | +0.15 | +0.13 |
| - | - | netguns (us-them) | +0.27 | . | +0.02 | +0.09 | +0.11 | +0.27 | +0.25 |
| - | - | netguns (us-them) ~avg | +0.26 | . | +0.01 | +0.06 | +0.08 | +0.18 | +0.24 |
| - | - | pickups (us-them) | +0.24 | +0.13 | +0.22 | +0.19 | +0.21 | +0.20 | +0.23 |
| - | - | pickups (us-them) ~avg | +0.26 | +0.13 | +0.24 | +0.22 | +0.22 | +0.22 | +0.24 |
| - | - | soup (us-them) | -0.17 | -0.04 | +0.01 | +0.00 | -0.05 | -0.17 | -0.06 |
| - | - | soup (us-them) ~avg | -0.15 | -0.05 | +0.00 | +0.00 | -0.01 | -0.13 | -0.14 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
