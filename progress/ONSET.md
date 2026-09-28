# Which metric starts predicting the result first

45 games, 29 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | hqBuried (us-them) [inverted] | +0.34 | +0.23 | +0.26 | +0.15 | +0.14 | +0.21 | +0.21 |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.23 | +0.25 | +0.20 | +0.16 | +0.17 | +0.19 |
| r150 | - | mines (us-them) | +0.38 | +0.27 | +0.33 | +0.33 | +0.34 | +0.23 | +0.19 |
| r150 | - | soup (us-them) ~avg | +0.43 | +0.23 | +0.39 | +0.26 | +0.43 | +0.15 | +0.05 |
| r150 | - | worth (us-them) | +0.41 | +0.24 | +0.37 | +0.40 | +0.41 | +0.37 | +0.35 |
| r200 | - | worth (us-them) ~avg | +0.46 | +0.25 | +0.35 | +0.38 | +0.46 | +0.43 | +0.39 |
| r250 | - | drones (us-them) ~avg | +0.33 | +0.13 | +0.29 | +0.27 | +0.19 | +0.20 | +0.27 |
| r250 | - | mines (us-them) ~avg | +0.37 | +0.25 | +0.29 | +0.34 | +0.37 | +0.33 | +0.26 |
| r300 | - | vaporators (us-them) | +0.34 | +0.15 | +0.10 | +0.34 | +0.26 | +0.26 | +0.18 |
| r350 | - | pickups (us-them) | +0.41 | +0.07 | +0.10 | +0.27 | +0.39 | +0.26 | +0.37 |
| r450 | - | landscapers (us-them) | +0.52 | +0.01 | -0.02 | -0.11 | +0.10 | +0.42 | +0.52 |
| r450 | - | pickups (us-them) ~avg | +0.37 | +0.07 | +0.12 | +0.19 | +0.29 | +0.31 | +0.37 |
| r450 | - | robots (us-them) | +0.43 | +0.04 | +0.15 | +0.10 | +0.23 | +0.40 | +0.41 |
| r450 | - | spawned (us-them) | +0.45 | +0.05 | +0.13 | +0.16 | +0.19 | +0.41 | +0.39 |
| r500 | - | units (us-them) | +0.40 | +0.14 | +0.08 | -0.05 | +0.09 | +0.36 | +0.40 |
| r550 | - | robots (us-them) ~avg | +0.41 | +0.08 | +0.13 | +0.14 | +0.20 | +0.37 | +0.41 |
| r550 | - | spawned (us-them) ~avg | +0.38 | +0.10 | +0.12 | +0.16 | +0.18 | +0.36 | +0.38 |
| r650 | - | landscapers (us-them) ~avg | +0.50 | +0.02 | -0.04 | -0.10 | -0.01 | +0.26 | +0.43 |
| r700 | - | digs (us-them) | +0.56 | +0.02 | +0.04 | +0.02 | +0.07 | +0.25 | +0.45 |
| r700 | - | dirtDeps (us-them) | +0.56 | -0.02 | +0.04 | +0.02 | +0.07 | +0.25 | +0.45 |
| r700 | - | units (us-them) ~avg | +0.39 | +0.12 | +0.09 | +0.04 | +0.06 | +0.24 | +0.38 |
| r800 | - | netguns (us-them) | +0.40 | -0.12 | -0.22 | -0.19 | -0.11 | +0.25 | +0.39 |
| r900 | - | digs (us-them) ~avg | +0.51 | +0.02 | -0.00 | +0.00 | +0.05 | +0.15 | +0.31 |
| r900 | - | dirtDeps (us-them) ~avg | +0.52 | -0.02 | -0.00 | -0.02 | +0.05 | +0.15 | +0.32 |
| r1000 | - | moves (us-them) | +0.32 | +0.12 | +0.08 | +0.02 | -0.01 | +0.12 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.27 | +0.21 | +0.15 | +0.04 | +0.01 | -0.18 | -0.27 |
| - | - | aba (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.16 | +0.07 | +0.05 | -0.09 | -0.19 |
| - | - | cov (us-them) | -0.25 | +0.14 | -0.09 | -0.10 | -0.07 | -0.11 | -0.19 |
| - | - | cov (us-them) ~avg | -0.23 | +0.17 | -0.00 | -0.09 | -0.10 | -0.11 | -0.15 |
| - | - | died (us-them) [inverted] | +0.25 | -0.12 | +0.15 | -0.15 | +0.16 | +0.14 | +0.22 |
| - | - | died (us-them) [inverted] ~avg | +0.28 | -0.12 | +0.14 | -0.05 | +0.08 | +0.16 | +0.24 |
| - | - | drones (us-them) | +0.30 | +0.17 | +0.22 | +0.09 | +0.07 | +0.20 | +0.26 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.15 | +0.12 | +0.12 | +0.02 | +0.00 | +0.01 | +0.02 |
| - | - | miners (us-them) ~avg | +0.14 | +0.11 | +0.11 | +0.13 | +0.08 | +0.04 | +0.03 |
| - | - | moves (us-them) ~avg | +0.28 | +0.10 | +0.13 | +0.08 | +0.03 | +0.05 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.25 | -0.12 | -0.19 | -0.21 | -0.19 | -0.06 | +0.18 |
| - | - | soup (us-them) | +0.35 | +0.26 | +0.33 | +0.07 | +0.35 | -0.03 | -0.09 |
| - | - | vaporators (us-them) ~avg | +0.30 | +0.15 | +0.11 | +0.24 | +0.25 | +0.27 | +0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
