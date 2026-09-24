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
| r50 | - | aba (us-them) [inverted] | +0.37 | +0.32 | +0.02 | +0.18 | +0.26 | -0.01 | -0.16 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.37 | +0.35 | +0.13 | +0.15 | +0.23 | +0.14 | -0.03 |
| r50 | - | moves (us-them) ~avg | +0.32 | +0.29 | +0.08 | +0.08 | +0.09 | +0.20 | +0.25 |
| r250 | r800 | died (us-them) [inverted] | -0.54 | . | +0.20 | +0.32 | +0.31 | +0.27 | -0.36 |
| r250 | - | drones (us-them) | +0.31 | -0.04 | +0.20 | +0.25 | +0.13 | +0.01 | +0.00 |
| r300 | r1050 | died (us-them) [inverted] ~avg | -0.39 | . | +0.20 | +0.32 | +0.32 | +0.34 | -0.06 |
| r300 | - | miners (us-them) | +0.39 | -0.06 | +0.09 | +0.37 | +0.24 | +0.19 | -0.04 |
| r300 | - | mines (us-them) | +0.36 | +0.10 | +0.17 | +0.35 | +0.30 | +0.21 | +0.15 |
| r300 | - | robots (us-them) | +0.47 | -0.06 | +0.16 | +0.41 | +0.37 | +0.32 | +0.10 |
| r300 | - | spawned (us-them) | +0.37 | -0.06 | +0.15 | +0.33 | +0.27 | +0.29 | +0.18 |
| r300 | - | worth (us-them) | +0.43 | -0.06 | +0.26 | +0.42 | +0.37 | +0.28 | +0.09 |
| r350 | - | units (us-them) | +0.40 | -0.01 | +0.07 | +0.27 | +0.26 | +0.34 | +0.15 |
| r350 | - | vaporators (us-them) | +0.31 | -0.04 | +0.20 | +0.11 | +0.25 | +0.18 | +0.02 |
| r350 | - | worth (us-them) ~avg | +0.36 | -0.06 | +0.13 | +0.27 | +0.36 | +0.34 | +0.21 |
| r400 | - | cov (us-them) | +0.39 | +0.11 | +0.02 | +0.15 | +0.32 | +0.32 | +0.33 |
| r400 | - | pickups (us-them) | +0.46 | +0.19 | +0.07 | +0.20 | +0.34 | +0.32 | -0.10 |
| r450 | - | landscapers (us-them) | +0.40 | +0.06 | -0.00 | +0.05 | +0.07 | +0.38 | +0.33 |
| r450 | - | robots (us-them) ~avg | +0.38 | -0.08 | +0.02 | +0.16 | +0.24 | +0.38 | +0.24 |
| r500 | - | pickups (us-them) ~avg | +0.38 | +0.19 | +0.01 | +0.11 | +0.21 | +0.38 | +0.08 |
| r550 | - | mines (us-them) ~avg | +0.30 | +0.08 | +0.13 | +0.22 | +0.27 | +0.30 | +0.23 |
| r550 | - | units (us-them) ~avg | +0.38 | -0.05 | +0.02 | +0.11 | +0.11 | +0.36 | +0.30 |
| r600 | - | spawned (us-them) ~avg | +0.32 | -0.08 | +0.02 | +0.13 | +0.17 | +0.31 | +0.27 |
| r650 | - | landscapers (us-them) ~avg | +0.42 | +0.06 | -0.01 | +0.01 | -0.07 | +0.28 | +0.42 |
| r650 | - | moves (us-them) | +0.32 | +0.24 | +0.04 | +0.10 | +0.17 | +0.28 | +0.20 |
| r850 | - | cov (us-them) ~avg | +0.37 | +0.18 | +0.02 | +0.09 | +0.18 | +0.27 | +0.32 |
| r850 | - | soup (us-them) | +0.38 | +0.07 | -0.19 | +0.21 | +0.20 | -0.00 | +0.26 |
| r1100 | - | dirtDeps (us-them) | +0.34 | +0.09 | -0.18 | -0.12 | -0.13 | +0.10 | +0.26 |
| r1150 | - | digs (us-them) | +0.31 | +0.04 | -0.13 | -0.06 | -0.12 | +0.10 | +0.25 |
| - | - | digs (us-them) ~avg | +0.24 | +0.04 | -0.12 | -0.10 | -0.17 | -0.05 | +0.14 |
| - | - | dirtDeps (us-them) ~avg | +0.26 | +0.09 | -0.15 | -0.15 | -0.18 | -0.06 | +0.15 |
| - | - | drones (us-them) ~avg | +0.20 | -0.04 | +0.09 | +0.20 | +0.14 | +0.06 | +0.02 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | +0.19 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.19 | . | . | . | . | . |
| - | - | miners (us-them) ~avg | +0.28 | -0.09 | +0.02 | +0.15 | +0.26 | +0.26 | +0.12 |
| - | - | netguns (us-them) | +0.20 | . | +0.20 | +0.20 | +0.09 | -0.01 | +0.10 |
| - | - | netguns (us-them) ~avg | +0.20 | . | +0.20 | +0.20 | +0.15 | +0.10 | +0.06 |
| - | - | soup (us-them) ~avg | +0.30 | +0.12 | +0.18 | +0.18 | +0.16 | +0.10 | +0.24 |
| - | - | vaporators (us-them) ~avg | +0.29 | -0.04 | +0.12 | +0.12 | +0.28 | +0.24 | +0.12 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
