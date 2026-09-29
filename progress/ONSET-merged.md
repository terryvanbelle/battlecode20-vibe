# Which metric starts predicting the result first -- every recorded block of r1s17 merged

453 games, 247 wins. Noise floor about 0.09; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.38 | +0.31 | +0.33 | +0.34 | +0.37 | +0.37 | +0.36 |
| r150 | - | worth (us-them) | +0.41 | +0.30 | +0.38 | +0.38 | +0.41 | +0.38 | +0.31 |
| r150 | - | worth (us-them) ~avg | +0.41 | +0.28 | +0.36 | +0.39 | +0.40 | +0.41 | +0.36 |
| r200 | - | mines (us-them) ~avg | +0.38 | +0.30 | +0.32 | +0.35 | +0.36 | +0.38 | +0.37 |
| r200 | - | robots (us-them) | +0.42 | +0.26 | +0.33 | +0.35 | +0.38 | +0.40 | +0.38 |
| r200 | - | spawned (us-them) | +0.38 | +0.25 | +0.32 | +0.30 | +0.33 | +0.38 | +0.38 |
| r250 | - | robots (us-them) ~avg | +0.41 | +0.25 | +0.30 | +0.33 | +0.36 | +0.41 | +0.40 |
| r300 | - | spawned (us-them) ~avg | +0.38 | +0.25 | +0.28 | +0.31 | +0.32 | +0.37 | +0.37 |
| r300 | - | units (us-them) | +0.42 | +0.28 | +0.29 | +0.31 | +0.34 | +0.41 | +0.42 |
| r350 | - | units (us-them) ~avg | +0.43 | +0.27 | +0.27 | +0.30 | +0.32 | +0.41 | +0.43 |
| r450 | - | digs (us-them) | +0.58 | +0.16 | +0.16 | +0.23 | +0.28 | +0.41 | +0.50 |
| r450 | - | landscapers (us-them) | +0.48 | +0.11 | +0.24 | +0.24 | +0.29 | +0.43 | +0.48 |
| r450 | - | pickups (us-them) | +0.33 | +0.12 | +0.13 | +0.21 | +0.29 | +0.32 | +0.31 |
| r500 | - | dirtDeps (us-them) | +0.58 | +0.17 | +0.16 | +0.23 | +0.27 | +0.40 | +0.50 |
| r500 | - | landscapers (us-them) ~avg | +0.49 | +0.11 | +0.16 | +0.21 | +0.25 | +0.39 | +0.45 |
| r500 | - | pickups (us-them) ~avg | +0.33 | +0.11 | +0.16 | +0.22 | +0.26 | +0.32 | +0.33 |
| r550 | - | digs (us-them) ~avg | +0.53 | +0.17 | +0.13 | +0.20 | +0.24 | +0.33 | +0.44 |
| r550 | - | dirtDeps (us-them) ~avg | +0.53 | +0.18 | +0.14 | +0.19 | +0.23 | +0.33 | +0.43 |
| r550 | - | netguns (us-them) | +0.34 | -0.06 | -0.01 | +0.07 | +0.12 | +0.33 | +0.33 |
| r800 | - | moves (us-them) | +0.34 | +0.10 | +0.14 | +0.16 | +0.17 | +0.24 | +0.33 |
| r1000 | - | moves (us-them) ~avg | +0.32 | +0.07 | +0.14 | +0.17 | +0.17 | +0.20 | +0.28 |
| r1150 | - | netguns (us-them) ~avg | +0.31 | -0.06 | -0.03 | +0.02 | +0.07 | +0.19 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.20 | +0.12 | +0.03 | +0.04 | +0.05 | -0.08 | -0.17 |
| - | - | aba (us-them) [inverted] ~avg | -0.17 | +0.13 | +0.06 | +0.04 | +0.05 | -0.01 | -0.10 |
| - | - | cov (us-them) | +0.24 | +0.06 | +0.11 | +0.18 | +0.19 | +0.24 | +0.18 |
| - | - | cov (us-them) ~avg | +0.21 | +0.03 | +0.04 | +0.10 | +0.13 | +0.20 | +0.20 |
| - | - | died (us-them) [inverted] | +0.24 | +0.07 | +0.12 | +0.18 | +0.24 | +0.17 | +0.10 |
| - | - | died (us-them) [inverted] ~avg | +0.21 | +0.03 | +0.12 | +0.15 | +0.19 | +0.19 | +0.15 |
| - | - | drones (us-them) | +0.29 | +0.20 | +0.16 | +0.23 | +0.29 | +0.26 | +0.29 |
| - | - | drones (us-them) ~avg | +0.30 | +0.19 | +0.21 | +0.26 | +0.28 | +0.30 | +0.30 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.29 | +0.27 | +0.20 | +0.12 | +0.08 | +0.03 | -0.03 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.28 | +0.27 | +0.17 | +0.10 | +0.08 | +0.03 | -0.05 |
| - | - | miners (us-them) | +0.20 | +0.20 | +0.16 | +0.16 | +0.12 | +0.09 | +0.11 |
| - | - | miners (us-them) ~avg | +0.20 | +0.20 | +0.20 | +0.20 | +0.18 | +0.14 | +0.11 |
| - | - | soup (us-them) | +0.10 | -0.05 | -0.01 | +0.09 | +0.08 | +0.03 | -0.00 |
| - | - | soup (us-them) ~avg | +0.12 | -0.03 | -0.00 | +0.06 | +0.11 | +0.09 | +0.06 |
| - | - | vaporators (us-them) | +0.25 | +0.09 | +0.06 | +0.13 | +0.20 | +0.22 | +0.17 |
| - | - | vaporators (us-them) ~avg | +0.21 | +0.09 | +0.07 | +0.09 | +0.14 | +0.20 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
