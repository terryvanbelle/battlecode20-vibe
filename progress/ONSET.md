# Which metric starts predicting the result first

47 games, 26 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) ~avg | +0.32 | +0.28 | +0.20 | +0.14 | +0.12 | +0.09 | +0.08 |
| r50 | - | worth (us-them) | +0.31 | +0.30 | +0.28 | +0.23 | +0.10 | +0.14 | +0.12 |
| r50 | - | worth (us-them) ~avg | +0.32 | +0.32 | +0.27 | +0.26 | +0.19 | +0.16 | +0.16 |
| r150 | - | died (us-them) [inverted] ~avg | +0.48 | +0.16 | +0.27 | +0.40 | +0.43 | +0.42 | +0.46 |
| r150 | - | pickups (us-them) | +0.63 | +0.20 | +0.30 | +0.56 | +0.59 | +0.54 | +0.45 |
| r150 | - | pickups (us-them) ~avg | +0.64 | +0.20 | +0.32 | +0.52 | +0.59 | +0.64 | +0.56 |
| r200 | - | hqBuried (us-them) [inverted] | +0.34 | +0.22 | +0.34 | +0.20 | +0.30 | +0.21 | +0.21 |
| r200 | - | robots (us-them) | +0.35 | +0.31 | +0.35 | +0.23 | +0.06 | +0.18 | +0.23 |
| r250 | - | drones (us-them) | +0.46 | +0.18 | +0.23 | +0.43 | +0.36 | +0.12 | +0.28 |
| r300 | - | died (us-them) [inverted] | +0.46 | +0.16 | +0.25 | +0.37 | +0.43 | +0.38 | +0.42 |
| r300 | - | digs (us-them) | +0.65 | +0.32 | +0.25 | +0.34 | +0.35 | +0.48 | +0.60 |
| r300 | - | digs (us-them) ~avg | +0.63 | +0.32 | +0.25 | +0.33 | +0.35 | +0.41 | +0.54 |
| r300 | - | dirtDeps (us-them) | +0.63 | +0.19 | +0.27 | +0.34 | +0.34 | +0.48 | +0.58 |
| r300 | - | dirtDeps (us-them) ~avg | +0.61 | +0.19 | +0.26 | +0.34 | +0.35 | +0.42 | +0.52 |
| r300 | - | drones (us-them) ~avg | +0.51 | +0.18 | +0.25 | +0.51 | +0.48 | +0.28 | +0.29 |
| r500 | - | landscapers (us-them) | +0.49 | +0.12 | +0.24 | +0.19 | -0.01 | +0.45 | +0.47 |
| r500 | - | units (us-them) | +0.35 | +0.26 | +0.29 | +0.27 | +0.13 | +0.28 | +0.35 |
| r600 | - | landscapers (us-them) ~avg | +0.50 | +0.15 | +0.18 | +0.15 | +0.12 | +0.31 | +0.46 |
| r700 | - | units (us-them) ~avg | +0.34 | +0.25 | +0.23 | +0.25 | +0.23 | +0.29 | +0.34 |
| r1100 | - | netguns (us-them) | +0.31 | . | +0.08 | +0.01 | -0.05 | +0.08 | +0.15 |
| - | r950 | aba (us-them) [inverted] | -0.34 | -0.26 | -0.03 | +0.08 | +0.08 | -0.15 | -0.26 |
| - | r1100 | aba (us-them) [inverted] ~avg | -0.32 | -0.24 | -0.12 | -0.03 | +0.05 | -0.05 | -0.22 |
| - | - | cov (us-them) | -0.25 | -0.04 | +0.11 | +0.23 | +0.20 | +0.07 | +0.12 |
| - | - | cov (us-them) ~avg | -0.25 | -0.12 | +0.04 | +0.07 | +0.14 | +0.16 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.22 | +0.20 | +0.09 | +0.21 | +0.19 | +0.20 |
| - | - | miners (us-them) | +0.16 | +0.14 | +0.06 | -0.02 | +0.04 | +0.04 | +0.12 |
| - | - | miners (us-them) ~avg | +0.13 | +0.13 | +0.07 | -0.01 | +0.01 | +0.06 | +0.09 |
| - | - | mines (us-them) | +0.32 | +0.24 | +0.19 | +0.13 | +0.08 | +0.05 | +0.08 |
| - | - | moves (us-them) | +0.30 | +0.11 | +0.02 | -0.06 | -0.07 | +0.07 | +0.30 |
| - | - | moves (us-them) ~avg | +0.24 | +0.01 | +0.08 | -0.03 | -0.05 | +0.00 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.24 | . | +0.08 | +0.03 | -0.01 | -0.01 | +0.10 |
| - | - | robots (us-them) ~avg | +0.30 | +0.30 | +0.30 | +0.29 | +0.21 | +0.20 | +0.23 |
| - | - | soup (us-them) | +0.29 | -0.15 | -0.22 | +0.29 | +0.20 | -0.19 | -0.13 |
| - | - | soup (us-them) ~avg | -0.21 | -0.07 | -0.16 | -0.21 | -0.08 | -0.09 | -0.14 |
| - | - | spawned (us-them) | +0.30 | +0.30 | +0.27 | +0.02 | -0.15 | -0.02 | +0.13 |
| - | - | spawned (us-them) ~avg | +0.29 | +0.29 | +0.24 | +0.13 | +0.01 | -0.03 | +0.05 |
| - | - | vaporators (us-them) | +0.16 | +0.16 | -0.06 | -0.05 | +0.06 | +0.09 | +0.00 |
| - | - | vaporators (us-them) ~avg | +0.16 | +0.16 | +0.00 | -0.05 | +0.00 | +0.06 | +0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
