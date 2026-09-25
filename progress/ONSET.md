# Which metric starts predicting the result first

44 games, 30 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r550 | - | landscapers (us-them) | +0.46 | +0.13 | +0.19 | +0.14 | +0.18 | +0.30 | +0.44 |
| r550 | - | landscapers (us-them) ~avg | +0.42 | +0.21 | +0.24 | +0.19 | +0.18 | +0.32 | +0.38 |
| r650 | - | pickups (us-them) | +0.42 | +0.20 | +0.03 | -0.02 | +0.08 | +0.27 | +0.40 |
| r700 | - | digs (us-them) | +0.49 | +0.02 | +0.11 | +0.00 | +0.03 | +0.24 | +0.41 |
| r750 | - | dirtDeps (us-them) | +0.48 | -0.04 | +0.12 | -0.00 | +0.03 | +0.23 | +0.40 |
| r750 | - | robots (us-them) | +0.37 | +0.26 | +0.14 | +0.16 | +0.22 | +0.24 | +0.35 |
| r750 | - | robots (us-them) ~avg | +0.33 | +0.30 | +0.22 | +0.18 | +0.18 | +0.26 | +0.32 |
| r750 | - | spawned (us-them) | +0.38 | +0.27 | +0.13 | +0.16 | +0.21 | +0.27 | +0.37 |
| r750 | - | spawned (us-them) ~avg | +0.33 | +0.30 | +0.22 | +0.18 | +0.18 | +0.27 | +0.33 |
| r750 | - | units (us-them) | +0.43 | +0.14 | +0.16 | +0.11 | +0.18 | +0.24 | +0.39 |
| r750 | - | units (us-them) ~avg | +0.37 | +0.18 | +0.23 | +0.16 | +0.17 | +0.28 | +0.34 |
| r750 | - | worth (us-them) | +0.34 | +0.08 | +0.14 | +0.20 | +0.24 | +0.27 | +0.33 |
| r850 | - | pickups (us-them) ~avg | +0.37 | +0.20 | +0.11 | +0.01 | +0.04 | +0.15 | +0.33 |
| r850 | - | worth (us-them) ~avg | +0.31 | +0.11 | +0.13 | +0.15 | +0.15 | +0.23 | +0.31 |
| r1000 | - | digs (us-them) ~avg | +0.37 | +0.06 | +0.15 | +0.06 | +0.04 | +0.14 | +0.25 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.36 | +0.00 | +0.16 | +0.05 | +0.03 | +0.13 | +0.24 |
| - | - | aba (us-them) [inverted] | -0.14 | +0.03 | -0.06 | -0.08 | -0.03 | -0.12 | -0.06 |
| - | - | aba (us-them) [inverted] ~avg | -0.14 | -0.00 | -0.02 | -0.10 | -0.06 | -0.08 | -0.07 |
| - | - | cov (us-them) | +0.23 | +0.10 | +0.03 | +0.06 | +0.09 | +0.00 | +0.17 |
| - | - | cov (us-them) ~avg | +0.10 | +0.06 | +0.00 | +0.01 | +0.05 | +0.01 | +0.04 |
| - | - | died (us-them) [inverted] | -0.25 | -0.06 | +0.11 | -0.00 | +0.03 | -0.13 | -0.09 |
| - | - | died (us-them) [inverted] ~avg | +0.19 | +0.11 | +0.18 | -0.01 | -0.02 | -0.08 | -0.14 |
| - | - | drones (us-them) | +0.22 | +0.17 | +0.00 | +0.04 | -0.16 | -0.07 | +0.04 |
| - | - | drones (us-them) ~avg | +0.24 | +0.17 | +0.13 | +0.05 | -0.08 | -0.08 | +0.02 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | . | +0.20 | +0.11 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | . | +0.20 | +0.11 | . | . | . |
| - | - | miners (us-them) | +0.19 | -0.02 | -0.01 | -0.01 | +0.19 | +0.17 | +0.14 |
| - | - | miners (us-them) ~avg | +0.17 | +0.02 | -0.01 | -0.03 | +0.07 | +0.13 | +0.17 |
| - | - | mines (us-them) | +0.23 | -0.02 | +0.02 | +0.03 | +0.04 | +0.18 | +0.22 |
| - | - | mines (us-them) ~avg | +0.23 | -0.04 | +0.03 | +0.02 | +0.00 | +0.13 | +0.21 |
| - | - | moves (us-them) | -0.18 | +0.08 | -0.15 | -0.13 | -0.15 | -0.14 | -0.11 |
| - | - | moves (us-them) ~avg | -0.20 | +0.07 | -0.16 | -0.18 | -0.18 | -0.15 | -0.10 |
| - | - | netguns (us-them) | -0.19 | . | -0.19 | +0.10 | +0.09 | +0.03 | +0.06 |
| - | - | netguns (us-them) ~avg | -0.20 | . | -0.20 | -0.02 | +0.04 | +0.06 | +0.05 |
| - | - | soup (us-them) | -0.29 | -0.29 | -0.02 | -0.05 | -0.04 | +0.09 | +0.14 |
| - | r100 | soup (us-them) ~avg | -0.33 | -0.33 | -0.19 | -0.09 | -0.09 | -0.05 | +0.09 |
| - | - | vaporators (us-them) | +0.27 | +0.13 | +0.07 | +0.18 | +0.20 | +0.24 | +0.25 |
| - | - | vaporators (us-them) ~avg | +0.22 | +0.13 | +0.01 | +0.07 | +0.12 | +0.16 | +0.22 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
