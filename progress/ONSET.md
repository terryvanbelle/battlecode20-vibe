# Which metric starts predicting the result first

40 games, 17 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | vaporators (us-them) | +0.52 | +0.23 | +0.39 | +0.49 | +0.50 | +0.49 | +0.40 |
| r150 | - | vaporators (us-them) ~avg | +0.54 | +0.23 | +0.43 | +0.52 | +0.54 | +0.53 | +0.48 |
| r200 | - | mines (us-them) | +0.49 | +0.16 | +0.39 | +0.49 | +0.48 | +0.42 | +0.44 |
| r200 | - | mines (us-them) ~avg | +0.53 | +0.16 | +0.35 | +0.46 | +0.50 | +0.49 | +0.51 |
| r200 | - | worth (us-them) | +0.59 | +0.14 | +0.46 | +0.55 | +0.59 | +0.56 | +0.51 |
| r200 | - | worth (us-them) ~avg | +0.60 | +0.20 | +0.39 | +0.52 | +0.57 | +0.58 | +0.58 |
| r250 | - | miners (us-them) ~avg | +0.32 | +0.26 | +0.28 | +0.32 | +0.29 | +0.26 | +0.31 |
| r250 | - | robots (us-them) | +0.62 | +0.06 | +0.23 | +0.46 | +0.47 | +0.50 | +0.62 |
| r250 | - | spawned (us-them) | +0.67 | +0.05 | +0.21 | +0.45 | +0.43 | +0.50 | +0.67 |
| r300 | - | robots (us-them) ~avg | +0.62 | +0.10 | +0.19 | +0.36 | +0.45 | +0.51 | +0.61 |
| r300 | - | spawned (us-them) ~avg | +0.67 | +0.10 | +0.18 | +0.35 | +0.42 | +0.51 | +0.64 |
| r300 | - | units (us-them) | +0.61 | +0.04 | +0.09 | +0.33 | +0.31 | +0.43 | +0.61 |
| r350 | - | landscapers (us-them) | +0.64 | -0.20 | -0.00 | +0.25 | +0.33 | +0.47 | +0.61 |
| r400 | - | units (us-them) ~avg | +0.59 | +0.10 | +0.11 | +0.24 | +0.30 | +0.40 | +0.53 |
| r550 | - | landscapers (us-them) ~avg | +0.67 | -0.15 | -0.06 | +0.09 | +0.20 | +0.37 | +0.60 |
| r650 | - | cov (us-them) | +0.44 | -0.00 | -0.10 | -0.01 | +0.04 | +0.27 | +0.39 |
| r650 | - | digs (us-them) | +0.55 | -0.20 | -0.20 | -0.07 | +0.06 | +0.29 | +0.49 |
| r700 | - | dirtDeps (us-them) | +0.53 | -0.24 | -0.23 | -0.12 | +0.01 | +0.25 | +0.46 |
| r750 | - | cov (us-them) ~avg | +0.40 | +0.01 | -0.05 | -0.04 | -0.00 | +0.20 | +0.35 |
| r750 | - | moves (us-them) | +0.44 | -0.11 | -0.09 | +0.07 | +0.15 | +0.21 | +0.35 |
| r750 | - | moves (us-them) ~avg | +0.44 | -0.08 | -0.09 | +0.02 | +0.12 | +0.22 | +0.36 |
| r900 | - | digs (us-them) ~avg | +0.49 | -0.21 | -0.25 | -0.16 | -0.04 | +0.10 | +0.31 |
| r950 | r150 | dirtDeps (us-them) ~avg | +0.46 | -0.25 | -0.29 | -0.21 | -0.09 | +0.05 | +0.26 |
| r1000 | - | drones (us-them) | +0.41 | +0.15 | +0.06 | +0.01 | +0.01 | +0.24 | +0.30 |
| r1200 | - | miners (us-them) | +0.31 | +0.28 | +0.22 | +0.23 | +0.15 | +0.21 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.28 | +0.10 | +0.05 | +0.05 | -0.14 | -0.13 | -0.04 |
| - | r1100 | aba (us-them) [inverted] ~avg | -0.31 | +0.14 | +0.07 | +0.03 | -0.03 | -0.15 | -0.13 |
| - | r750 | died (us-them) [inverted] | -0.40 | +0.19 | +0.19 | +0.13 | +0.28 | -0.02 | -0.35 |
| - | r900 | died (us-them) [inverted] ~avg | -0.43 | +0.19 | +0.21 | +0.22 | +0.27 | +0.08 | -0.32 |
| - | - | drones (us-them) ~avg | +0.28 | +0.15 | +0.12 | +0.04 | -0.01 | +0.17 | +0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.19 | +0.13 | . | -0.11 | +0.04 | -0.19 | -0.03 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.13 | +0.16 | +0.15 | +0.14 | +0.10 | -0.08 |
| - | - | netguns (us-them) | +0.27 | . | -0.04 | +0.04 | +0.10 | +0.21 | +0.27 |
| - | - | netguns (us-them) ~avg | +0.24 | . | -0.04 | +0.01 | +0.04 | +0.15 | +0.22 |
| - | - | pickups (us-them) | +0.26 | +0.04 | +0.07 | +0.23 | +0.25 | +0.19 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.21 | +0.04 | +0.03 | +0.15 | +0.20 | +0.17 | +0.21 |
| - | - | soup (us-them) | +0.20 | +0.07 | +0.07 | -0.06 | +0.20 | +0.12 | -0.02 |
| - | - | soup (us-them) ~avg | +0.17 | +0.11 | +0.10 | +0.05 | +0.06 | +0.15 | +0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
