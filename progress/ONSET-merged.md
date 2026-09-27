# Which metric starts predicting the result first -- every recorded block of r1s14 merged

225 games, 101 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.38 | +0.33 | +0.17 | +0.18 | +0.13 | +0.14 | +0.12 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.37 | +0.33 | +0.18 | +0.18 | +0.16 | +0.17 | +0.13 |
| r200 | - | mines (us-them) | +0.44 | +0.23 | +0.34 | +0.36 | +0.40 | +0.44 | +0.37 |
| r200 | - | worth (us-them) | +0.47 | +0.23 | +0.34 | +0.39 | +0.43 | +0.47 | +0.40 |
| r250 | - | mines (us-them) ~avg | +0.43 | +0.24 | +0.28 | +0.32 | +0.36 | +0.43 | +0.39 |
| r250 | - | worth (us-them) ~avg | +0.46 | +0.23 | +0.30 | +0.35 | +0.39 | +0.46 | +0.42 |
| r300 | - | robots (us-them) | +0.50 | +0.23 | +0.26 | +0.33 | +0.43 | +0.50 | +0.46 |
| r350 | - | robots (us-them) ~avg | +0.47 | +0.25 | +0.23 | +0.28 | +0.35 | +0.46 | +0.46 |
| r350 | - | spawned (us-them) | +0.49 | +0.23 | +0.24 | +0.29 | +0.40 | +0.49 | +0.44 |
| r400 | - | spawned (us-them) ~avg | +0.45 | +0.25 | +0.22 | +0.25 | +0.32 | +0.44 | +0.44 |
| r400 | - | units (us-them) | +0.44 | +0.26 | +0.16 | +0.20 | +0.32 | +0.44 | +0.41 |
| r450 | - | vaporators (us-them) | +0.35 | +0.11 | +0.18 | +0.27 | +0.29 | +0.34 | +0.30 |
| r500 | - | landscapers (us-them) | +0.39 | +0.12 | +0.13 | +0.06 | +0.16 | +0.38 | +0.36 |
| r500 | - | pickups (us-them) | +0.38 | +0.02 | +0.13 | +0.17 | +0.26 | +0.35 | +0.37 |
| r500 | - | units (us-them) ~avg | +0.40 | +0.24 | +0.16 | +0.17 | +0.24 | +0.38 | +0.40 |
| r550 | - | pickups (us-them) ~avg | +0.39 | +0.02 | +0.15 | +0.16 | +0.21 | +0.32 | +0.39 |
| r550 | - | vaporators (us-them) ~avg | +0.33 | +0.11 | +0.16 | +0.22 | +0.26 | +0.32 | +0.32 |
| r650 | - | landscapers (us-them) ~avg | +0.35 | +0.13 | +0.06 | +0.06 | +0.09 | +0.28 | +0.34 |
| r800 | - | digs (us-them) | +0.47 | +0.24 | +0.01 | +0.05 | +0.08 | +0.23 | +0.37 |
| r800 | - | dirtDeps (us-them) | +0.46 | +0.24 | -0.01 | +0.04 | +0.07 | +0.22 | +0.36 |
| r1050 | - | digs (us-them) ~avg | +0.37 | +0.24 | +0.02 | +0.05 | +0.06 | +0.15 | +0.27 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.37 | +0.24 | -0.01 | +0.03 | +0.04 | +0.14 | +0.26 |
| - | - | aba (us-them) [inverted] | +0.18 | +0.16 | +0.14 | +0.17 | +0.17 | -0.01 | -0.15 |
| - | - | aba (us-them) [inverted] ~avg | +0.19 | +0.17 | +0.18 | +0.18 | +0.19 | +0.11 | -0.05 |
| - | - | cov (us-them) | +0.27 | +0.02 | +0.11 | +0.18 | +0.20 | +0.27 | +0.23 |
| - | - | cov (us-them) ~avg | +0.23 | -0.01 | +0.07 | +0.12 | +0.16 | +0.21 | +0.23 |
| - | - | died (us-them) [inverted] | +0.20 | +0.06 | +0.13 | +0.16 | +0.19 | +0.13 | +0.07 |
| - | - | died (us-them) [inverted] ~avg | +0.18 | +0.03 | +0.14 | +0.14 | +0.17 | +0.17 | +0.10 |
| - | - | drones (us-them) | +0.29 | +0.19 | +0.10 | +0.16 | +0.29 | +0.28 | +0.27 |
| - | - | drones (us-them) ~avg | +0.28 | +0.18 | +0.15 | +0.17 | +0.22 | +0.28 | +0.28 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.27 | +0.14 | +0.10 | +0.21 | +0.25 | +0.26 | +0.27 |
| - | - | miners (us-them) ~avg | +0.26 | +0.15 | +0.14 | +0.16 | +0.22 | +0.25 | +0.23 |
| - | - | moves (us-them) | +0.25 | +0.06 | +0.15 | +0.14 | +0.16 | +0.24 | +0.25 |
| - | - | moves (us-them) ~avg | -0.22 | -0.02 | +0.14 | +0.13 | +0.15 | +0.19 | +0.20 |
| - | - | netguns (us-them) | +0.28 | . | +0.07 | +0.11 | +0.12 | +0.28 | +0.24 |
| - | - | netguns (us-them) ~avg | +0.25 | . | +0.07 | +0.09 | +0.11 | +0.20 | +0.24 |
| - | - | soup (us-them) | -0.14 | -0.08 | -0.04 | -0.03 | -0.01 | -0.02 | -0.14 |
| - | - | soup (us-them) ~avg | -0.12 | -0.12 | -0.05 | -0.05 | -0.07 | -0.04 | -0.10 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
