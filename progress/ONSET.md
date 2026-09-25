# Which metric starts predicting the result first

40 games, 14 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | aba (us-them) [inverted] | +0.36 | +0.25 | +0.36 | +0.19 | +0.09 | -0.00 | -0.14 |
| r150 | - | aba (us-them) [inverted] ~avg | +0.36 | +0.25 | +0.36 | +0.30 | +0.19 | +0.08 | -0.03 |
| r200 | - | mines (us-them) | +0.41 | +0.11 | +0.40 | +0.30 | +0.31 | +0.33 | +0.26 |
| r200 | - | worth (us-them) | +0.44 | -0.01 | +0.35 | +0.39 | +0.41 | +0.41 | +0.44 |
| r250 | - | mines (us-them) ~avg | +0.35 | +0.06 | +0.29 | +0.29 | +0.31 | +0.33 | +0.31 |
| r250 | - | robots (us-them) | +0.49 | +0.03 | +0.25 | +0.26 | +0.31 | +0.39 | +0.49 |
| r250 | - | spawned (us-them) | +0.49 | +0.03 | +0.26 | +0.28 | +0.30 | +0.42 | +0.49 |
| r250 | - | worth (us-them) ~avg | +0.44 | -0.02 | +0.22 | +0.32 | +0.38 | +0.42 | +0.43 |
| r300 | - | vaporators (us-them) | +0.43 | +0.09 | -0.02 | +0.30 | +0.36 | +0.41 | +0.39 |
| r400 | - | vaporators (us-them) ~avg | +0.41 | +0.09 | -0.01 | +0.21 | +0.31 | +0.39 | +0.41 |
| r500 | - | robots (us-them) ~avg | +0.48 | +0.04 | +0.12 | +0.16 | +0.24 | +0.35 | +0.45 |
| r550 | - | spawned (us-them) ~avg | +0.47 | +0.04 | +0.13 | +0.17 | +0.24 | +0.36 | +0.46 |
| r650 | - | units (us-them) | +0.43 | -0.03 | +0.22 | +0.14 | +0.16 | +0.27 | +0.42 |
| r700 | - | landscapers (us-them) | +0.45 | +0.01 | +0.18 | +0.09 | +0.00 | +0.14 | +0.45 |
| r750 | - | units (us-them) ~avg | +0.40 | -0.03 | +0.08 | +0.08 | +0.12 | +0.22 | +0.38 |
| r800 | - | landscapers (us-them) ~avg | +0.42 | +0.02 | +0.08 | +0.04 | +0.03 | +0.10 | +0.36 |
| r900 | - | miners (us-them) | +0.30 | -0.01 | +0.17 | +0.10 | +0.25 | +0.25 | +0.30 |
| r1200 | - | drones (us-them) | +0.31 | -0.11 | +0.04 | +0.12 | +0.20 | +0.23 | +0.23 |
| - | - | cov (us-them) | +0.24 | +0.01 | -0.20 | +0.01 | +0.18 | +0.21 | +0.22 |
| - | - | cov (us-them) ~avg | -0.19 | +0.06 | -0.19 | -0.11 | +0.01 | +0.12 | +0.15 |
| - | - | died (us-them) [inverted] | +0.19 | . | -0.05 | -0.02 | +0.12 | +0.09 | +0.18 |
| - | - | died (us-them) [inverted] ~avg | +0.19 | . | -0.05 | -0.07 | +0.02 | +0.11 | +0.14 |
| - | - | digs (us-them) | +0.28 | +0.16 | -0.16 | -0.16 | -0.15 | -0.11 | +0.14 |
| - | - | digs (us-them) ~avg | -0.17 | +0.16 | -0.13 | -0.17 | -0.16 | -0.15 | +0.02 |
| - | - | dirtDeps (us-them) | +0.26 | -0.00 | -0.21 | -0.21 | -0.22 | -0.15 | +0.10 |
| - | - | dirtDeps (us-them) ~avg | -0.22 | -0.00 | -0.19 | -0.22 | -0.22 | -0.21 | -0.03 |
| - | - | drones (us-them) ~avg | +0.25 | -0.11 | -0.02 | +0.09 | +0.15 | +0.19 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.24 | -0.08 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.30 | -0.08 | -0.22 | -0.24 | -0.24 | -0.24 | . |
| - | - | miners (us-them) ~avg | +0.24 | -0.02 | +0.06 | +0.07 | +0.14 | +0.19 | +0.23 |
| - | - | moves (us-them) | +0.29 | -0.19 | -0.19 | +0.00 | +0.09 | +0.15 | +0.24 |
| - | - | moves (us-them) ~avg | +0.23 | -0.14 | -0.22 | -0.10 | -0.00 | +0.10 | +0.16 |
| - | - | netguns (us-them) | -0.22 | . | -0.22 | +0.05 | +0.15 | +0.16 | +0.17 |
| - | - | netguns (us-them) ~avg | -0.22 | . | -0.22 | -0.11 | +0.00 | +0.09 | +0.10 |
| - | - | pickups (us-them) | +0.29 | . | +0.07 | -0.01 | +0.19 | +0.20 | +0.27 |
| - | - | pickups (us-them) ~avg | +0.26 | . | +0.10 | -0.03 | +0.06 | +0.19 | +0.23 |
| - | r750 | soup (us-them) | -0.36 | -0.11 | +0.26 | +0.04 | +0.02 | -0.20 | -0.36 |
| - | r900 | soup (us-them) ~avg | -0.33 | -0.17 | +0.16 | +0.15 | +0.09 | +0.03 | -0.30 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
