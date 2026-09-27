# Which metric starts predicting the result first

43 games, 18 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.53 | +0.33 | +0.31 | +0.38 | +0.52 | +0.38 | +0.13 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.53 | +0.38 | +0.38 | +0.45 | +0.53 | +0.49 | +0.36 |
| r100 | - | hqBuried (us-them) [inverted] | +0.40 | +0.32 | +0.37 | +0.04 | . | +0.10 | +0.10 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.34 | +0.09 | -0.11 | -0.18 | -0.18 | -0.18 |
| r100 | - | mines (us-them) | +0.38 | +0.36 | +0.34 | +0.26 | +0.28 | +0.36 | +0.35 |
| r100 | - | mines (us-them) ~avg | +0.36 | +0.33 | +0.35 | +0.31 | +0.29 | +0.33 | +0.36 |
| r150 | - | worth (us-them) | +0.48 | +0.12 | +0.48 | +0.35 | +0.39 | +0.38 | +0.34 |
| r200 | - | worth (us-them) ~avg | +0.45 | +0.14 | +0.37 | +0.42 | +0.40 | +0.41 | +0.39 |
| r300 | - | cov (us-them) | +0.52 | +0.19 | +0.01 | +0.32 | +0.45 | +0.50 | +0.46 |
| r300 | - | drones (us-them) | +0.40 | +0.12 | +0.24 | +0.40 | +0.23 | +0.31 | +0.26 |
| r300 | - | drones (us-them) ~avg | +0.35 | +0.08 | +0.24 | +0.33 | +0.31 | +0.33 | +0.32 |
| r300 | - | pickups (us-them) | +0.39 | +0.03 | +0.18 | +0.30 | +0.33 | +0.33 | +0.38 |
| r300 | - | robots (us-them) | +0.48 | +0.02 | +0.12 | +0.33 | +0.29 | +0.47 | +0.42 |
| r350 | - | vaporators (us-them) | +0.32 | +0.08 | +0.22 | +0.29 | +0.29 | +0.28 | +0.25 |
| r400 | - | dirtDeps (us-them) | +0.64 | +0.06 | -0.13 | +0.17 | +0.31 | +0.59 | +0.64 |
| r400 | - | pickups (us-them) ~avg | +0.39 | +0.03 | +0.17 | +0.24 | +0.32 | +0.34 | +0.39 |
| r450 | - | cov (us-them) ~avg | +0.46 | +0.23 | +0.06 | +0.18 | +0.27 | +0.42 | +0.46 |
| r450 | - | digs (us-them) | +0.64 | +0.11 | -0.14 | +0.13 | +0.27 | +0.58 | +0.64 |
| r450 | - | digs (us-them) ~avg | +0.67 | +0.11 | -0.13 | +0.15 | +0.24 | +0.52 | +0.67 |
| r450 | - | dirtDeps (us-them) ~avg | +0.67 | +0.05 | -0.14 | +0.16 | +0.27 | +0.55 | +0.67 |
| r450 | - | robots (us-them) ~avg | +0.47 | +0.09 | +0.01 | +0.20 | +0.26 | +0.41 | +0.47 |
| r450 | - | spawned (us-them) | +0.48 | +0.07 | +0.13 | +0.30 | +0.24 | +0.48 | +0.42 |
| r500 | - | miners (us-them) | +0.39 | -0.00 | -0.23 | +0.00 | +0.08 | +0.31 | +0.39 |
| r500 | - | units (us-them) | +0.50 | +0.06 | -0.03 | +0.27 | +0.18 | +0.45 | +0.43 |
| r550 | - | spawned (us-them) ~avg | +0.44 | +0.14 | +0.07 | +0.19 | +0.22 | +0.34 | +0.44 |
| r600 | - | units (us-them) ~avg | +0.47 | +0.10 | -0.10 | +0.07 | +0.13 | +0.30 | +0.47 |
| r650 | - | landscapers (us-them) | +0.39 | +0.03 | +0.09 | +0.17 | +0.06 | +0.25 | +0.36 |
| r800 | - | landscapers (us-them) ~avg | +0.37 | +0.04 | -0.04 | +0.14 | +0.12 | +0.20 | +0.37 |
| - | - | died (us-them) [inverted] | -0.30 | -0.30 | +0.01 | +0.06 | +0.10 | +0.18 | +0.30 |
| - | - | died (us-them) [inverted] ~avg | -0.30 | -0.30 | -0.16 | +0.02 | +0.07 | +0.18 | +0.26 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.25 | +0.06 | -0.17 | -0.17 | -0.11 | +0.06 | +0.25 |
| - | - | moves (us-them) | +0.17 | +0.05 | -0.06 | -0.01 | -0.02 | +0.03 | +0.17 |
| - | - | moves (us-them) ~avg | -0.15 | +0.07 | -0.10 | -0.11 | -0.14 | -0.09 | +0.03 |
| - | - | netguns (us-them) | +0.21 | . | +0.13 | +0.11 | +0.16 | +0.21 | +0.16 |
| - | - | netguns (us-them) ~avg | +0.21 | . | +0.13 | +0.11 | +0.12 | +0.20 | +0.19 |
| - | - | soup (us-them) | -0.17 | +0.04 | +0.13 | -0.17 | +0.01 | -0.13 | -0.17 |
| - | - | soup (us-them) ~avg | +0.17 | +0.01 | +0.16 | +0.12 | +0.00 | -0.07 | -0.11 |
| - | - | vaporators (us-them) ~avg | +0.29 | +0.08 | +0.20 | +0.21 | +0.26 | +0.29 | +0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
