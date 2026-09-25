# Which metric starts predicting the result first

41 games, 19 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | mines (us-them) | +0.51 | -0.00 | +0.33 | +0.38 | +0.42 | +0.51 | +0.46 |
| r200 | - | worth (us-them) | +0.56 | -0.03 | +0.30 | +0.44 | +0.47 | +0.55 | +0.41 |
| r250 | - | robots (us-them) | +0.65 | -0.14 | +0.26 | +0.51 | +0.55 | +0.62 | +0.59 |
| r250 | - | spawned (us-them) | +0.62 | -0.14 | +0.26 | +0.49 | +0.53 | +0.61 | +0.56 |
| r300 | - | mines (us-them) ~avg | +0.52 | -0.00 | +0.22 | +0.33 | +0.37 | +0.48 | +0.51 |
| r300 | - | robots (us-them) ~avg | +0.68 | -0.13 | +0.10 | +0.33 | +0.44 | +0.59 | +0.65 |
| r300 | - | spawned (us-them) ~avg | +0.62 | -0.13 | +0.10 | +0.32 | +0.42 | +0.58 | +0.60 |
| r300 | - | units (us-them) | +0.64 | -0.22 | +0.12 | +0.32 | +0.41 | +0.59 | +0.62 |
| r300 | - | worth (us-them) ~avg | +0.64 | -0.00 | +0.20 | +0.36 | +0.42 | +0.52 | +0.52 |
| r450 | - | landscapers (us-them) | +0.60 | -0.22 | +0.10 | +0.18 | +0.28 | +0.51 | +0.59 |
| r450 | - | units (us-them) ~avg | +0.66 | -0.18 | -0.00 | +0.17 | +0.26 | +0.50 | +0.63 |
| r550 | - | landscapers (us-them) ~avg | +0.61 | -0.22 | -0.01 | +0.10 | +0.13 | +0.37 | +0.59 |
| r550 | - | pickups (us-them) | +0.33 | -0.02 | +0.05 | +0.16 | +0.22 | +0.32 | +0.32 |
| r600 | - | miners (us-them) | +0.48 | -0.07 | +0.05 | +0.28 | +0.24 | +0.31 | +0.37 |
| r700 | - | digs (us-them) | +0.52 | +0.03 | -0.05 | +0.01 | +0.01 | +0.22 | +0.48 |
| r700 | r100 | moves (us-them) | +0.42 | -0.41 | -0.28 | -0.08 | +0.08 | +0.25 | +0.35 |
| r700 | - | soup (us-them) | +0.31 | +0.23 | +0.01 | -0.12 | +0.01 | +0.20 | +0.11 |
| r750 | - | dirtDeps (us-them) | +0.50 | +0.05 | -0.08 | -0.05 | -0.06 | +0.17 | +0.45 |
| r800 | - | miners (us-them) ~avg | +0.37 | -0.08 | -0.02 | +0.15 | +0.20 | +0.25 | +0.32 |
| r800 | - | pickups (us-them) ~avg | +0.31 | -0.02 | -0.02 | +0.10 | +0.18 | +0.27 | +0.31 |
| r900 | - | digs (us-them) ~avg | +0.44 | +0.03 | -0.05 | -0.01 | -0.04 | +0.09 | +0.30 |
| r950 | r100 | moves (us-them) ~avg | -0.42 | -0.37 | -0.35 | -0.22 | -0.09 | +0.12 | +0.29 |
| r950 | - | vaporators (us-them) ~avg | +0.32 | -0.10 | -0.00 | +0.09 | +0.19 | +0.19 | +0.15 |
| r1000 | - | dirtDeps (us-them) ~avg | +0.40 | +0.05 | -0.08 | -0.07 | -0.10 | +0.02 | +0.24 |
| r1000 | - | vaporators (us-them) | +0.32 | -0.10 | +0.01 | +0.15 | +0.19 | +0.17 | +0.16 |
| r1100 | - | drones (us-them) | +0.37 | +0.16 | +0.05 | +0.03 | +0.21 | +0.24 | +0.20 |
| - | - | aba (us-them) [inverted] | -0.11 | -0.07 | -0.04 | -0.06 | +0.02 | +0.06 | +0.01 |
| - | - | aba (us-them) [inverted] ~avg | -0.07 | -0.05 | -0.06 | -0.06 | -0.02 | +0.02 | +0.05 |
| - | r100 | cov (us-them) | -0.43 | -0.38 | -0.34 | -0.23 | -0.10 | +0.06 | +0.15 |
| - | r100 | cov (us-them) ~avg | -0.40 | -0.35 | -0.39 | -0.33 | -0.28 | -0.13 | +0.04 |
| - | - | died (us-them) [inverted] | +0.24 | . | +0.04 | +0.17 | +0.14 | -0.04 | +0.04 |
| - | - | died (us-them) [inverted] ~avg | +0.20 | . | +0.07 | +0.15 | +0.15 | +0.03 | +0.02 |
| - | - | drones (us-them) ~avg | +0.26 | +0.16 | +0.08 | +0.06 | +0.15 | +0.23 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.20 | . | +0.15 | +0.15 | . | . | +0.15 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | . | +0.15 | +0.15 | . | +0.15 | +0.21 |
| - | - | netguns (us-them) | +0.29 | . | +0.11 | +0.11 | +0.15 | +0.09 | +0.07 |
| - | - | netguns (us-them) ~avg | +0.13 | . | +0.11 | +0.10 | +0.13 | +0.12 | +0.09 |
| - | - | soup (us-them) ~avg | +0.29 | +0.25 | +0.13 | -0.00 | -0.01 | +0.14 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
