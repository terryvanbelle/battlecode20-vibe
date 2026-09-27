# Which metric starts predicting the result first

45 games, 14 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.40 | +0.40 | +0.25 | +0.33 | +0.34 | +0.28 | +0.06 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.41 | +0.41 | +0.28 | +0.32 | +0.31 | +0.30 | +0.15 |
| r50 | - | miners (us-them) ~avg | +0.34 | +0.31 | +0.18 | +0.14 | +0.10 | +0.04 | +0.01 |
| r50 | - | mines (us-them) | +0.47 | +0.31 | +0.14 | +0.25 | +0.40 | +0.44 | +0.40 |
| r50 | - | mines (us-them) ~avg | +0.45 | +0.37 | +0.20 | +0.23 | +0.34 | +0.44 | +0.40 |
| r50 | - | spawned (us-them) ~avg | +0.43 | +0.43 | +0.26 | +0.20 | +0.27 | +0.38 | +0.36 |
| r50 | - | units (us-them) | +0.41 | +0.39 | +0.02 | +0.06 | +0.26 | +0.37 | +0.37 |
| r50 | - | units (us-them) ~avg | +0.44 | +0.44 | +0.21 | +0.15 | +0.21 | +0.35 | +0.36 |
| r50 | - | worth (us-them) | +0.44 | +0.41 | +0.08 | +0.23 | +0.38 | +0.37 | +0.33 |
| r50 | - | worth (us-them) ~avg | +0.43 | +0.43 | +0.19 | +0.19 | +0.30 | +0.38 | +0.33 |
| r100 | - | robots (us-them) ~avg | +0.41 | +0.41 | +0.26 | +0.19 | +0.27 | +0.37 | +0.37 |
| r150 | - | pickups (us-them) ~avg | +0.40 | +0.14 | +0.40 | +0.35 | +0.37 | +0.38 | +0.37 |
| r200 | - | pickups (us-them) | +0.41 | +0.14 | +0.41 | +0.33 | +0.35 | +0.34 | +0.40 |
| r350 | - | soup (us-them) ~avg | +0.34 | +0.10 | +0.03 | +0.29 | +0.27 | +0.12 | -0.14 |
| r400 | - | landscapers (us-them) | +0.48 | +0.30 | +0.05 | +0.13 | +0.30 | +0.44 | +0.36 |
| r400 | - | robots (us-them) | +0.42 | +0.37 | +0.08 | +0.14 | +0.33 | +0.37 | +0.40 |
| r400 | - | spawned (us-them) | +0.40 | +0.38 | +0.04 | +0.13 | +0.32 | +0.38 | +0.37 |
| r450 | - | cov (us-them) | +0.32 | +0.06 | +0.18 | +0.25 | +0.28 | +0.25 | +0.08 |
| r450 | - | dirtDeps (us-them) | +0.52 | +0.19 | +0.12 | +0.23 | +0.28 | +0.42 | +0.48 |
| r500 | - | digs (us-them) | +0.49 | +0.30 | +0.17 | +0.23 | +0.27 | +0.40 | +0.45 |
| r500 | - | landscapers (us-them) ~avg | +0.43 | +0.28 | +0.10 | +0.12 | +0.21 | +0.42 | +0.40 |
| r550 | - | cov (us-them) ~avg | +0.31 | -0.02 | +0.12 | +0.21 | +0.26 | +0.31 | +0.23 |
| r550 | - | digs (us-them) ~avg | +0.46 | +0.26 | +0.21 | +0.24 | +0.25 | +0.32 | +0.37 |
| r550 | - | dirtDeps (us-them) ~avg | +0.49 | +0.15 | +0.12 | +0.22 | +0.24 | +0.33 | +0.40 |
| r550 | - | netguns (us-them) | +0.33 | . | +0.14 | +0.19 | +0.26 | +0.30 | +0.29 |
| r650 | - | netguns (us-them) ~avg | +0.31 | . | +0.14 | +0.17 | +0.22 | +0.29 | +0.30 |
| - | - | died (us-them) [inverted] | +0.26 | -0.09 | +0.14 | +0.08 | +0.16 | +0.14 | +0.26 |
| - | - | died (us-them) [inverted] ~avg | -0.23 | -0.16 | +0.09 | +0.08 | +0.12 | +0.13 | +0.17 |
| - | - | drones (us-them) | +0.22 | +0.11 | -0.18 | -0.10 | +0.10 | +0.11 | +0.22 |
| - | - | drones (us-them) ~avg | +0.21 | +0.16 | +0.14 | +0.01 | +0.08 | +0.14 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | +0.20 | -0.16 | -0.10 | -0.10 | -0.10 | -0.10 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.22 | +0.20 | -0.20 | -0.22 | -0.20 | -0.17 | -0.14 |
| - | - | miners (us-them) | +0.33 | +0.19 | +0.08 | -0.02 | -0.01 | -0.02 | +0.11 |
| - | - | moves (us-them) | -0.36 | +0.18 | +0.19 | +0.11 | +0.04 | +0.10 | +0.21 |
| - | - | moves (us-them) ~avg | -0.35 | +0.07 | +0.26 | +0.23 | +0.17 | +0.14 | +0.17 |
| - | - | soup (us-them) | -0.26 | +0.00 | +0.07 | +0.10 | +0.11 | -0.05 | -0.16 |
| - | - | vaporators (us-them) | +0.25 | -0.13 | -0.04 | +0.10 | +0.18 | +0.24 | +0.25 |
| - | - | vaporators (us-them) ~avg | -0.24 | -0.23 | -0.18 | -0.06 | +0.05 | +0.18 | +0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
