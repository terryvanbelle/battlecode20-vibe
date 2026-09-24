# Which metric starts predicting the result first

18 games, 6 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | vaporators (us-them) | +0.37 | +0.32 | +0.37 | +0.25 | +0.26 | +0.15 | +0.18 |
| r100 | - | vaporators (us-them) ~avg | +0.35 | +0.32 | +0.35 | +0.31 | +0.28 | +0.21 | +0.18 |
| r250 | - | drones (us-them) | +0.34 | -0.09 | +0.22 | +0.31 | +0.26 | +0.26 | +0.20 |
| r250 | - | pickups (us-them) | +0.43 | . | +0.20 | +0.41 | +0.43 | +0.24 | -0.15 |
| r300 | - | pickups (us-them) ~avg | +0.42 | . | +0.15 | +0.32 | +0.41 | +0.37 | +0.01 |
| r300 | - | worth (us-them) | +0.33 | -0.00 | +0.22 | +0.33 | +0.33 | +0.24 | +0.19 |
| r350 | r150 | died (us-them) [inverted] | +0.37 | -0.17 | -0.27 | +0.29 | +0.37 | +0.12 | -0.22 |
| r350 | - | robots (us-them) | +0.38 | -0.01 | +0.14 | +0.29 | +0.38 | +0.28 | +0.18 |
| r350 | - | spawned (us-them) | +0.34 | -0.00 | +0.16 | +0.23 | +0.34 | +0.29 | +0.21 |
| r350 | - | units (us-them) | +0.34 | -0.05 | +0.07 | +0.20 | +0.34 | +0.31 | +0.17 |
| r400 | - | worth (us-them) ~avg | +0.31 | +0.04 | +0.12 | +0.26 | +0.31 | +0.28 | +0.22 |
| r450 | r150 | cov (us-them) | +0.37 | +0.18 | -0.37 | +0.18 | +0.30 | +0.31 | +0.35 |
| r450 | - | landscapers (us-them) | +0.35 | -0.08 | +0.01 | +0.08 | +0.26 | +0.33 | +0.12 |
| r450 | - | robots (us-them) ~avg | +0.31 | +0.05 | +0.08 | +0.15 | +0.28 | +0.30 | +0.24 |
| r500 | - | soup (us-them) | +0.38 | -0.14 | -0.22 | +0.03 | -0.17 | +0.27 | +0.12 |
| r600 | - | units (us-them) ~avg | +0.31 | +0.02 | +0.02 | +0.07 | +0.20 | +0.31 | +0.26 |
| r700 | - | landscapers (us-them) ~avg | +0.32 | -0.08 | -0.04 | +0.00 | +0.11 | +0.26 | +0.28 |
| r750 | - | cov (us-them) ~avg | +0.36 | +0.13 | -0.33 | -0.04 | +0.13 | +0.25 | +0.33 |
| r1150 | - | netguns (us-them) ~avg | +0.31 | . | . | . | +0.17 | +0.22 | +0.22 |
| - | - | aba (us-them) [inverted] | -0.24 | +0.07 | +0.08 | -0.06 | -0.18 | -0.14 | +0.05 |
| - | - | aba (us-them) [inverted] ~avg | -0.21 | +0.10 | +0.07 | +0.00 | -0.14 | -0.19 | -0.05 |
| - | - | died (us-them) [inverted] ~avg | -0.30 | -0.17 | -0.28 | -0.01 | +0.25 | +0.21 | -0.05 |
| - | - | digs (us-them) | +0.35 | +0.35 | -0.14 | -0.06 | -0.03 | +0.19 | +0.26 |
| - | - | digs (us-them) ~avg | +0.35 | +0.35 | -0.12 | -0.08 | -0.05 | +0.07 | +0.21 |
| - | - | dirtDeps (us-them) | +0.24 | +0.10 | -0.14 | -0.08 | -0.03 | +0.19 | +0.24 |
| - | - | dirtDeps (us-them) ~avg | +0.19 | +0.10 | -0.13 | -0.10 | -0.06 | +0.06 | +0.19 |
| - | - | drones (us-them) ~avg | +0.27 | -0.09 | +0.11 | +0.25 | +0.27 | +0.25 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r300 | hqBuried (us-them) [inverted] | -0.34 | -0.34 | . | -0.34 | -0.34 | -0.34 | -0.34 |
| - | r100 | hqBuried (us-them) [inverted] ~avg | -0.34 | -0.34 | -0.34 | -0.34 | -0.34 | -0.34 | -0.34 |
| - | - | miners (us-them) | +0.21 | -0.00 | +0.05 | +0.08 | +0.21 | +0.19 | +0.13 |
| - | - | miners (us-them) ~avg | +0.19 | +0.07 | +0.05 | +0.03 | +0.11 | +0.16 | +0.16 |
| - | - | mines (us-them) | +0.29 | -0.03 | +0.16 | +0.19 | +0.25 | +0.22 | +0.26 |
| - | - | mines (us-them) ~avg | +0.26 | -0.06 | +0.07 | +0.15 | +0.20 | +0.22 | +0.24 |
| - | - | moves (us-them) | -0.21 | -0.07 | -0.21 | -0.14 | +0.02 | +0.14 | +0.17 |
| - | - | moves (us-them) ~avg | -0.17 | -0.10 | -0.17 | -0.16 | -0.08 | +0.06 | +0.16 |
| - | - | netguns (us-them) | +0.29 | . | . | . | +0.17 | +0.22 | +0.25 |
| - | - | soup (us-them) ~avg | -0.20 | -0.13 | -0.20 | -0.10 | -0.13 | +0.01 | +0.17 |
| - | - | spawned (us-them) ~avg | +0.29 | +0.05 | +0.09 | +0.14 | +0.24 | +0.29 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
