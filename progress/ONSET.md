# Which metric starts predicting the result first

47 games, 21 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.40 | +0.33 | +0.28 | +0.15 | +0.09 | +0.01 | -0.00 |
| r50 | - | mines (us-them) ~avg | +0.40 | +0.36 | +0.28 | +0.24 | +0.19 | +0.06 | +0.01 |
| r50 | - | worth (us-them) | +0.37 | +0.37 | +0.28 | +0.15 | +0.12 | +0.12 | +0.06 |
| r50 | - | worth (us-them) ~avg | +0.37 | +0.37 | +0.30 | +0.24 | +0.16 | +0.13 | +0.08 |
| r100 | - | robots (us-them) ~avg | +0.33 | +0.33 | +0.26 | +0.19 | +0.11 | +0.05 | +0.10 |
| r100 | - | spawned (us-them) ~avg | +0.34 | +0.34 | +0.26 | +0.20 | +0.10 | -0.02 | +0.09 |
| r150 | - | drones (us-them) ~avg | +0.33 | +0.29 | +0.26 | +0.20 | -0.01 | -0.10 | +0.06 |
| r200 | - | digs (us-them) | +0.58 | +0.28 | +0.32 | +0.37 | +0.47 | +0.56 | +0.55 |
| r200 | - | digs (us-them) ~avg | +0.65 | +0.28 | +0.33 | +0.36 | +0.45 | +0.54 | +0.59 |
| r200 | - | dirtDeps (us-them) | +0.57 | +0.23 | +0.33 | +0.36 | +0.45 | +0.56 | +0.53 |
| r200 | - | dirtDeps (us-them) ~avg | +0.64 | +0.23 | +0.36 | +0.36 | +0.44 | +0.53 | +0.58 |
| r300 | - | landscapers (us-them) | +0.46 | +0.31 | +0.25 | +0.38 | +0.33 | +0.38 | +0.38 |
| r300 | - | landscapers (us-them) ~avg | +0.47 | +0.31 | +0.22 | +0.31 | +0.33 | +0.43 | +0.43 |
| r500 | - | died (us-them) [inverted] | +0.32 | -0.03 | +0.07 | +0.07 | +0.23 | +0.31 | -0.06 |
| r1050 | - | units (us-them) | +0.32 | +0.32 | +0.15 | +0.10 | +0.04 | +0.06 | +0.21 |
| - | r1000 | aba (us-them) [inverted] | -0.35 | +0.12 | -0.12 | +0.07 | -0.04 | -0.08 | -0.29 |
| - | - | aba (us-them) [inverted] ~avg | -0.28 | +0.12 | -0.14 | +0.02 | -0.02 | -0.04 | -0.17 |
| - | - | cov (us-them) | -0.28 | -0.14 | -0.14 | -0.08 | -0.19 | -0.27 | -0.09 |
| - | - | cov (us-them) ~avg | -0.27 | -0.18 | -0.19 | -0.16 | -0.20 | -0.27 | -0.22 |
| - | - | died (us-them) [inverted] ~avg | +0.22 | -0.03 | +0.06 | +0.04 | +0.07 | +0.19 | +0.09 |
| - | - | drones (us-them) | +0.29 | +0.29 | +0.08 | +0.00 | -0.13 | -0.09 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.34 | +0.30 | +0.30 | +0.34 | +0.27 | +0.12 | +0.13 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.34 | +0.30 | +0.30 | +0.34 | +0.23 | +0.16 | +0.16 |
| - | r300 | miners (us-them) | -0.39 | +0.00 | -0.15 | -0.34 | -0.30 | -0.21 | -0.27 |
| - | r350 | miners (us-them) ~avg | -0.40 | +0.11 | -0.04 | -0.26 | -0.32 | -0.35 | -0.36 |
| - | - | moves (us-them) | +0.29 | -0.02 | -0.16 | -0.15 | -0.21 | -0.12 | +0.23 |
| - | - | moves (us-them) ~avg | -0.24 | -0.09 | -0.13 | -0.14 | -0.17 | -0.19 | -0.02 |
| - | - | netguns (us-them) | +0.23 | . | +0.12 | -0.17 | -0.20 | +0.20 | +0.20 |
| - | - | netguns (us-them) ~avg | -0.22 | . | -0.07 | -0.20 | -0.21 | -0.06 | +0.12 |
| - | - | pickups (us-them) | +0.29 | -0.01 | -0.14 | +0.02 | +0.28 | +0.27 | +0.19 |
| - | - | pickups (us-them) ~avg | +0.24 | -0.01 | -0.04 | -0.01 | +0.14 | +0.24 | +0.17 |
| - | - | robots (us-them) | +0.34 | +0.34 | +0.21 | +0.11 | +0.06 | +0.09 | +0.18 |
| - | r650 | soup (us-them) | -0.44 | -0.21 | +0.03 | -0.33 | -0.28 | -0.17 | -0.38 |
| - | r800 | soup (us-them) ~avg | -0.36 | -0.11 | -0.14 | -0.16 | -0.24 | -0.09 | -0.36 |
| - | - | spawned (us-them) | +0.34 | +0.34 | +0.21 | +0.09 | -0.03 | -0.02 | +0.20 |
| - | - | units (us-them) ~avg | +0.32 | +0.32 | +0.20 | +0.15 | +0.08 | +0.02 | +0.09 |
| - | - | vaporators (us-them) | +0.15 | +0.11 | -0.07 | +0.02 | +0.08 | +0.13 | -0.01 |
| - | - | vaporators (us-them) ~avg | +0.11 | +0.11 | -0.04 | -0.03 | -0.02 | +0.06 | +0.06 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
