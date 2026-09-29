# Which metric starts predicting the result first -- every recorded block of r5 merged

320 games, 155 wins. Noise floor about 0.11; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | robots (us-them) ~avg | +0.43 | +0.28 | +0.29 | +0.29 | +0.34 | +0.42 | +0.41 |
| r50 | - | spawned (us-them) ~avg | +0.43 | +0.28 | +0.31 | +0.31 | +0.35 | +0.42 | +0.41 |
| r200 | - | mines (us-them) | +0.38 | +0.29 | +0.34 | +0.35 | +0.38 | +0.34 | +0.33 |
| r200 | - | mines (us-them) ~avg | +0.39 | +0.29 | +0.32 | +0.35 | +0.38 | +0.38 | +0.36 |
| r200 | - | worth (us-them) | +0.39 | +0.24 | +0.32 | +0.37 | +0.39 | +0.35 | +0.27 |
| r200 | - | worth (us-them) ~avg | +0.40 | +0.25 | +0.32 | +0.35 | +0.39 | +0.39 | +0.33 |
| r300 | - | spawned (us-them) | +0.44 | +0.23 | +0.28 | +0.30 | +0.37 | +0.44 | +0.37 |
| r350 | - | robots (us-them) | +0.44 | +0.23 | +0.27 | +0.29 | +0.37 | +0.44 | +0.36 |
| r400 | - | units (us-them) | +0.43 | +0.22 | +0.21 | +0.20 | +0.31 | +0.43 | +0.37 |
| r450 | - | landscapers (us-them) | +0.46 | +0.12 | +0.16 | +0.15 | +0.26 | +0.42 | +0.37 |
| r500 | - | units (us-them) ~avg | +0.42 | +0.27 | +0.25 | +0.22 | +0.27 | +0.37 | +0.42 |
| r550 | - | landscapers (us-them) ~avg | +0.44 | +0.12 | +0.15 | +0.13 | +0.19 | +0.35 | +0.44 |
| r600 | - | digs (us-them) | +0.50 | +0.17 | +0.14 | +0.16 | +0.21 | +0.31 | +0.41 |
| r650 | - | dirtDeps (us-them) | +0.50 | +0.18 | +0.15 | +0.15 | +0.18 | +0.29 | +0.40 |
| r750 | - | digs (us-them) ~avg | +0.43 | +0.17 | +0.14 | +0.15 | +0.19 | +0.26 | +0.36 |
| r800 | - | dirtDeps (us-them) ~avg | +0.42 | +0.18 | +0.14 | +0.14 | +0.16 | +0.24 | +0.34 |
| - | - | aba (us-them) [inverted] | +0.16 | +0.13 | +0.03 | +0.08 | +0.16 | +0.11 | +0.00 |
| - | - | aba (us-them) [inverted] ~avg | +0.17 | +0.15 | +0.09 | +0.09 | +0.13 | +0.15 | +0.09 |
| - | - | cov (us-them) | +0.28 | +0.01 | +0.07 | +0.19 | +0.24 | +0.25 | +0.22 |
| - | - | cov (us-them) ~avg | +0.24 | -0.05 | +0.01 | +0.12 | +0.18 | +0.24 | +0.24 |
| - | - | died (us-them) [inverted] | +0.15 | -0.04 | +0.03 | +0.05 | +0.10 | +0.15 | +0.08 |
| - | - | died (us-them) [inverted] ~avg | +0.14 | -0.05 | +0.02 | +0.01 | +0.05 | +0.12 | +0.13 |
| - | - | drones (us-them) | +0.22 | +0.15 | +0.05 | +0.11 | +0.16 | +0.21 | +0.22 |
| - | - | drones (us-them) ~avg | +0.21 | +0.15 | +0.11 | +0.13 | +0.15 | +0.19 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.30 | +0.24 | +0.22 | +0.20 | +0.12 | +0.08 | +0.07 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.28 | +0.24 | +0.20 | +0.17 | +0.11 | +0.10 | +0.08 |
| - | - | miners (us-them) | +0.29 | +0.12 | +0.16 | +0.14 | +0.18 | +0.18 | +0.20 |
| - | - | miners (us-them) ~avg | +0.29 | +0.21 | +0.22 | +0.20 | +0.22 | +0.22 | +0.21 |
| - | - | moves (us-them) | +0.27 | +0.11 | +0.09 | +0.06 | +0.07 | +0.17 | +0.24 |
| - | - | moves (us-them) ~avg | +0.23 | +0.05 | +0.09 | +0.09 | +0.08 | +0.13 | +0.19 |
| - | - | netguns (us-them) | +0.26 | -0.06 | +0.02 | +0.05 | -0.00 | +0.19 | +0.26 |
| - | - | netguns (us-them) ~avg | +0.22 | -0.06 | +0.02 | +0.02 | +0.01 | +0.06 | +0.18 |
| - | - | pickups (us-them) | +0.30 | +0.03 | +0.14 | +0.14 | +0.21 | +0.27 | +0.29 |
| - | - | pickups (us-them) ~avg | +0.30 | +0.03 | +0.11 | +0.14 | +0.19 | +0.24 | +0.29 |
| - | - | soup (us-them) | +0.19 | -0.09 | -0.08 | +0.05 | +0.07 | -0.08 | -0.17 |
| - | - | soup (us-them) ~avg | -0.11 | -0.11 | -0.10 | -0.03 | +0.05 | +0.01 | -0.05 |
| - | - | vaporators (us-them) | +0.24 | +0.10 | +0.13 | +0.19 | +0.20 | +0.23 | +0.19 |
| - | - | vaporators (us-them) ~avg | +0.22 | +0.10 | +0.11 | +0.14 | +0.17 | +0.22 | +0.20 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
