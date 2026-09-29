# Which metric starts predicting the result first

46 games, 27 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.51 | +0.39 | +0.51 | +0.50 | +0.40 | +0.33 | +0.21 |
| r50 | - | mines (us-them) ~avg | +0.51 | +0.42 | +0.49 | +0.51 | +0.49 | +0.43 | +0.32 |
| r50 | - | robots (us-them) ~avg | +0.50 | +0.26 | +0.30 | +0.32 | +0.38 | +0.46 | +0.49 |
| r50 | - | spawned (us-them) ~avg | +0.45 | +0.26 | +0.28 | +0.31 | +0.35 | +0.44 | +0.43 |
| r50 | - | worth (us-them) | +0.56 | +0.49 | +0.53 | +0.53 | +0.42 | +0.46 | +0.40 |
| r50 | - | worth (us-them) ~avg | +0.58 | +0.53 | +0.55 | +0.58 | +0.54 | +0.49 | +0.45 |
| r200 | - | landscapers (us-them) | +0.65 | +0.23 | +0.34 | +0.38 | +0.52 | +0.62 | +0.62 |
| r200 | - | robots (us-them) | +0.51 | +0.19 | +0.32 | +0.40 | +0.37 | +0.47 | +0.50 |
| r250 | - | digs (us-them) | +0.73 | +0.24 | +0.28 | +0.39 | +0.54 | +0.63 | +0.68 |
| r250 | - | dirtDeps (us-them) | +0.73 | +0.26 | +0.30 | +0.38 | +0.51 | +0.62 | +0.67 |
| r250 | - | spawned (us-them) | +0.44 | +0.19 | +0.29 | +0.37 | +0.39 | +0.44 | +0.43 |
| r300 | - | digs (us-them) ~avg | +0.70 | +0.24 | +0.25 | +0.35 | +0.47 | +0.58 | +0.67 |
| r300 | - | dirtDeps (us-them) ~avg | +0.70 | +0.26 | +0.29 | +0.35 | +0.44 | +0.56 | +0.66 |
| r300 | - | vaporators (us-them) | +0.40 | +0.05 | +0.20 | +0.36 | +0.37 | +0.40 | +0.34 |
| r300 | - | vaporators (us-them) ~avg | +0.40 | +0.05 | +0.21 | +0.32 | +0.36 | +0.40 | +0.37 |
| r350 | - | landscapers (us-them) ~avg | +0.67 | +0.23 | +0.26 | +0.29 | +0.44 | +0.60 | +0.66 |
| r450 | - | pickups (us-them) | +0.44 | -0.09 | +0.08 | +0.20 | +0.29 | +0.42 | +0.42 |
| r450 | - | units (us-them) | +0.54 | +0.16 | +0.26 | +0.26 | +0.29 | +0.44 | +0.54 |
| r500 | - | pickups (us-them) ~avg | +0.47 | -0.09 | +0.08 | +0.16 | +0.22 | +0.38 | +0.44 |
| r500 | - | units (us-them) ~avg | +0.53 | +0.21 | +0.23 | +0.22 | +0.25 | +0.39 | +0.50 |
| r600 | - | died (us-them) [inverted] | +0.55 | +0.13 | +0.21 | +0.06 | +0.14 | +0.34 | +0.53 |
| r700 | - | died (us-them) [inverted] ~avg | +0.46 | +0.13 | +0.20 | +0.09 | +0.13 | +0.23 | +0.46 |
| r950 | - | moves (us-them) | +0.39 | -0.17 | +0.05 | -0.04 | -0.14 | -0.02 | +0.23 |
| r950 | - | netguns (us-them) | +0.32 | . | -0.14 | +0.01 | +0.09 | +0.25 | +0.29 |
| r1100 | - | drones (us-them) | +0.32 | +0.11 | -0.01 | +0.12 | +0.06 | +0.20 | +0.29 |
| - | r1000 | aba (us-them) [inverted] | -0.39 | +0.16 | +0.12 | +0.06 | +0.18 | +0.10 | -0.19 |
| - | - | aba (us-them) [inverted] ~avg | -0.24 | +0.18 | +0.12 | +0.07 | +0.13 | +0.14 | -0.02 |
| - | - | cov (us-them) | -0.21 | -0.17 | -0.03 | +0.07 | +0.05 | +0.03 | -0.05 |
| - | - | cov (us-them) ~avg | -0.21 | -0.19 | -0.13 | -0.01 | +0.02 | +0.04 | -0.04 |
| - | - | drones (us-them) ~avg | +0.29 | +0.11 | +0.08 | +0.13 | +0.10 | +0.17 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.24 | +0.10 | +0.10 | +0.13 | . | +0.14 | +0.24 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.27 | +0.10 | -0.07 | -0.09 | -0.21 | -0.21 | -0.25 |
| - | r350 | miners (us-them) | -0.34 | -0.04 | +0.01 | -0.23 | -0.33 | -0.23 | +0.09 |
| - | - | miners (us-them) ~avg | +0.27 | +0.09 | +0.09 | -0.04 | -0.19 | -0.23 | -0.15 |
| - | - | moves (us-them) ~avg | +0.27 | -0.21 | +0.03 | -0.01 | -0.09 | -0.09 | +0.08 |
| - | - | netguns (us-them) ~avg | +0.26 | . | -0.18 | -0.05 | -0.01 | +0.08 | +0.19 |
| - | r750 | soup (us-them) | -0.35 | +0.22 | +0.09 | -0.14 | -0.20 | -0.16 | -0.35 |
| - | r900 | soup (us-them) ~avg | -0.30 | +0.27 | +0.17 | +0.07 | -0.02 | -0.12 | -0.30 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
