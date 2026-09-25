# Which metric starts predicting the result first

41 games, 14 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | died (us-them) [inverted] ~avg | +0.34 | +0.22 | +0.27 | +0.07 | +0.15 | +0.22 | +0.30 |
| r400 | - | pickups (us-them) | +0.32 | -0.08 | +0.10 | +0.11 | +0.32 | +0.28 | +0.30 |
| r500 | - | robots (us-them) | +0.49 | +0.16 | +0.23 | +0.17 | +0.27 | +0.33 | +0.49 |
| r500 | - | worth (us-them) | +0.45 | +0.01 | +0.06 | +0.06 | +0.21 | +0.31 | +0.45 |
| r650 | - | units (us-them) | +0.46 | +0.14 | +0.20 | +0.12 | +0.26 | +0.26 | +0.44 |
| r700 | r100 | vaporators (us-them) | -0.46 | -0.46 | -0.26 | -0.06 | -0.03 | +0.27 | +0.37 |
| r750 | - | cov (us-them) | +0.42 | -0.16 | +0.07 | +0.11 | +0.15 | +0.23 | +0.36 |
| r750 | - | landscapers (us-them) | +0.39 | -0.03 | +0.16 | +0.12 | +0.29 | +0.19 | +0.38 |
| r750 | - | robots (us-them) ~avg | +0.43 | +0.18 | +0.24 | +0.19 | +0.24 | +0.27 | +0.39 |
| r750 | - | spawned (us-them) | +0.40 | +0.15 | +0.22 | +0.17 | +0.19 | +0.21 | +0.37 |
| r800 | - | died (us-them) [inverted] | +0.38 | +0.22 | +0.10 | -0.04 | +0.22 | +0.27 | +0.38 |
| r800 | - | units (us-them) ~avg | +0.39 | +0.18 | +0.23 | +0.15 | +0.21 | +0.22 | +0.35 |
| r800 | - | worth (us-them) ~avg | +0.40 | +0.01 | +0.03 | +0.02 | +0.11 | +0.21 | +0.34 |
| r850 | - | mines (us-them) | +0.31 | +0.02 | +0.09 | +0.09 | +0.16 | +0.24 | +0.31 |
| r900 | - | digs (us-them) | +0.31 | +0.03 | +0.23 | +0.21 | +0.25 | +0.26 | +0.30 |
| r900 | - | landscapers (us-them) ~avg | +0.33 | -0.03 | +0.13 | +0.08 | +0.20 | +0.21 | +0.30 |
| r900 | - | pickups (us-them) ~avg | +0.30 | -0.08 | +0.09 | +0.15 | +0.25 | +0.28 | +0.30 |
| r950 | - | drones (us-them) | +0.40 | -0.09 | +0.15 | +0.28 | +0.16 | +0.22 | +0.29 |
| r950 | r100 | vaporators (us-them) ~avg | -0.46 | -0.46 | -0.38 | -0.22 | -0.14 | +0.08 | +0.27 |
| r1000 | - | moves (us-them) | +0.38 | -0.04 | -0.01 | +0.02 | +0.05 | +0.10 | +0.30 |
| r1050 | - | drones (us-them) ~avg | +0.35 | -0.09 | +0.06 | +0.21 | +0.22 | +0.19 | +0.28 |
| r1100 | - | cov (us-them) ~avg | +0.33 | -0.20 | -0.02 | +0.04 | +0.10 | +0.13 | +0.26 |
| r1150 | - | spawned (us-them) ~avg | +0.32 | +0.16 | +0.22 | +0.17 | +0.19 | +0.17 | +0.27 |
| - | - | aba (us-them) [inverted] | +0.26 | -0.01 | -0.01 | +0.12 | +0.20 | +0.25 | +0.10 |
| - | - | aba (us-them) [inverted] ~avg | +0.25 | +0.00 | -0.03 | +0.07 | +0.15 | +0.23 | +0.22 |
| - | - | digs (us-them) ~avg | +0.30 | +0.03 | +0.21 | +0.19 | +0.23 | +0.27 | +0.30 |
| - | - | dirtDeps (us-them) | +0.30 | +0.13 | +0.23 | +0.20 | +0.24 | +0.25 | +0.29 |
| - | - | dirtDeps (us-them) ~avg | +0.29 | +0.13 | +0.21 | +0.17 | +0.22 | +0.25 | +0.29 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.13 | +0.19 | +0.13 | +0.13 | +0.12 | +0.12 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.22 | +0.13 | +0.18 | +0.02 | +0.07 | +0.09 | +0.10 |
| - | - | miners (us-them) | +0.29 | +0.27 | +0.10 | -0.02 | +0.06 | +0.19 | +0.29 |
| - | - | miners (us-them) ~avg | +0.27 | +0.26 | +0.22 | +0.13 | +0.11 | +0.11 | +0.16 |
| - | - | mines (us-them) ~avg | +0.24 | +0.02 | +0.06 | +0.06 | +0.11 | +0.14 | +0.21 |
| - | - | moves (us-them) ~avg | +0.27 | -0.03 | -0.01 | +0.02 | +0.03 | +0.04 | +0.19 |
| - | - | netguns (us-them) | +0.18 | . | +0.04 | -0.02 | -0.03 | +0.05 | +0.05 |
| - | - | netguns (us-them) ~avg | +0.11 | . | +0.07 | +0.03 | +0.01 | -0.03 | +0.01 |
| - | - | soup (us-them) | -0.22 | +0.03 | -0.20 | -0.22 | -0.16 | -0.16 | -0.12 |
| - | - | soup (us-them) ~avg | -0.22 | -0.01 | -0.15 | -0.21 | -0.21 | -0.15 | -0.17 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
