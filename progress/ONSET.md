# Which metric starts predicting the result first

20 games, 8 wins. Noise floor about 0.45; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | cov (us-them) ~avg | +0.35 | +0.35 | +0.21 | +0.15 | +0.13 | +0.09 | +0.07 |
| r100 | - | miners (us-them) ~avg | +0.31 | +0.30 | +0.30 | +0.11 | +0.10 | -0.03 | -0.19 |
| r100 | - | vaporators (us-them) | +0.41 | +0.41 | +0.27 | +0.22 | +0.19 | +0.11 | +0.07 |
| r100 | - | vaporators (us-them) ~avg | +0.41 | +0.41 | +0.32 | +0.27 | +0.22 | +0.16 | +0.11 |
| r200 | - | mines (us-them) | +0.30 | +0.22 | +0.30 | +0.18 | +0.14 | +0.10 | +0.10 |
| r200 | - | worth (us-them) | +0.34 | +0.07 | +0.31 | +0.28 | +0.21 | +0.11 | +0.05 |
| - | r450 | aba (us-them) [inverted] | -0.41 | -0.06 | -0.18 | -0.12 | -0.28 | -0.37 | -0.09 |
| - | r550 | aba (us-them) [inverted] ~avg | -0.33 | -0.06 | -0.17 | -0.11 | -0.20 | -0.33 | -0.26 |
| - | - | cov (us-them) | +0.35 | +0.35 | +0.05 | +0.10 | +0.10 | +0.03 | +0.04 |
| - | r500 | died (us-them) [inverted] | -0.41 | . | +0.07 | +0.06 | -0.11 | -0.30 | -0.40 |
| - | r750 | died (us-them) [inverted] ~avg | -0.37 | . | +0.12 | +0.12 | +0.03 | -0.22 | -0.36 |
| - | r100 | digs (us-them) | -0.41 | -0.36 | -0.25 | +0.06 | +0.06 | +0.09 | +0.06 |
| - | r100 | digs (us-them) ~avg | -0.44 | -0.36 | -0.33 | -0.07 | +0.02 | +0.06 | +0.06 |
| - | r100 | dirtDeps (us-them) | -0.45 | -0.37 | -0.28 | +0.05 | +0.08 | +0.12 | +0.08 |
| - | r100 | dirtDeps (us-them) ~avg | -0.47 | -0.37 | -0.36 | -0.10 | +0.02 | +0.09 | +0.10 |
| - | - | drones (us-them) | -0.26 | -0.10 | +0.14 | -0.09 | -0.23 | -0.23 | -0.04 |
| - | - | drones (us-them) ~avg | -0.20 | -0.10 | +0.06 | -0.00 | -0.10 | -0.20 | -0.12 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | landscapers (us-them) | +0.28 | -0.24 | +0.12 | +0.19 | +0.16 | +0.17 | +0.17 |
| - | - | landscapers (us-them) ~avg | -0.24 | -0.24 | -0.01 | +0.15 | +0.17 | +0.24 | +0.21 |
| - | r750 | miners (us-them) | -0.33 | +0.29 | +0.22 | -0.15 | +0.05 | -0.19 | -0.33 |
| - | - | mines (us-them) ~avg | +0.24 | +0.15 | +0.21 | +0.23 | +0.20 | +0.15 | +0.12 |
| - | - | moves (us-them) | +0.27 | +0.24 | +0.15 | +0.09 | +0.08 | -0.06 | -0.05 |
| - | - | moves (us-them) ~avg | +0.27 | +0.24 | +0.22 | +0.14 | +0.11 | +0.02 | -0.04 |
| - | - | netguns (us-them) | +0.26 | . | . | +0.19 | +0.19 | +0.17 | +0.05 |
| - | - | netguns (us-them) ~avg | +0.19 | . | . | +0.19 | +0.19 | +0.19 | +0.06 |
| - | r600 | pickups (us-them) | -0.31 | . | +0.22 | +0.06 | -0.15 | -0.30 | -0.23 |
| - | - | pickups (us-them) ~avg | -0.27 | . | +0.26 | +0.16 | +0.03 | -0.21 | -0.25 |
| - | - | robots (us-them) | +0.23 | +0.09 | +0.23 | +0.11 | +0.12 | -0.00 | +0.01 |
| - | - | robots (us-them) ~avg | +0.20 | +0.11 | +0.19 | +0.18 | +0.17 | +0.07 | +0.02 |
| - | - | soup (us-them) | +0.24 | -0.08 | -0.05 | +0.08 | +0.06 | +0.22 | +0.00 |
| - | - | soup (us-them) ~avg | +0.18 | -0.08 | -0.07 | +0.00 | +0.03 | +0.14 | +0.16 |
| - | - | spawned (us-them) | +0.24 | +0.09 | +0.24 | +0.11 | +0.16 | +0.07 | +0.09 |
| - | - | spawned (us-them) ~avg | +0.19 | +0.11 | +0.18 | +0.17 | +0.18 | +0.13 | +0.10 |
| - | - | units (us-them) | +0.22 | +0.09 | +0.22 | +0.03 | +0.05 | -0.05 | -0.00 |
| - | - | units (us-them) ~avg | +0.19 | +0.14 | +0.19 | +0.15 | +0.12 | +0.04 | -0.00 |
| - | - | worth (us-them) ~avg | +0.29 | +0.05 | +0.23 | +0.29 | +0.26 | +0.17 | +0.09 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
