# Which metric starts predicting the result first

43 games, 24 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.35 | +0.35 | +0.22 | -0.17 | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.35 | +0.35 | +0.19 | -0.19 | -0.19 | -0.19 | -0.19 |
| r100 | - | mines (us-them) | +0.56 | +0.35 | +0.50 | +0.56 | +0.54 | +0.38 | +0.31 |
| r100 | - | mines (us-them) ~avg | +0.58 | +0.37 | +0.50 | +0.56 | +0.58 | +0.53 | +0.41 |
| r100 | - | units (us-them) ~avg | +0.55 | +0.31 | +0.26 | +0.29 | +0.30 | +0.41 | +0.48 |
| r150 | - | worth (us-them) | +0.61 | +0.28 | +0.49 | +0.53 | +0.52 | +0.55 | +0.61 |
| r150 | - | worth (us-them) ~avg | +0.62 | +0.29 | +0.48 | +0.54 | +0.56 | +0.57 | +0.60 |
| r200 | - | spawned (us-them) | +0.61 | +0.21 | +0.30 | +0.46 | +0.44 | +0.48 | +0.53 |
| r250 | - | miners (us-them) ~avg | +0.33 | +0.20 | +0.30 | +0.26 | +0.17 | +0.16 | +0.22 |
| r250 | - | robots (us-them) | +0.63 | +0.22 | +0.26 | +0.40 | +0.41 | +0.50 | +0.58 |
| r250 | - | robots (us-them) ~avg | +0.61 | +0.24 | +0.28 | +0.38 | +0.42 | +0.51 | +0.55 |
| r250 | - | spawned (us-them) ~avg | +0.58 | +0.23 | +0.30 | +0.43 | +0.47 | +0.53 | +0.52 |
| r250 | - | units (us-them) | +0.57 | +0.34 | +0.22 | +0.28 | +0.27 | +0.42 | +0.50 |
| r300 | - | landscapers (us-them) | +0.59 | +0.15 | +0.17 | +0.33 | +0.21 | +0.40 | +0.57 |
| r300 | - | vaporators (us-them) | +0.57 | +0.11 | +0.17 | +0.35 | +0.36 | +0.53 | +0.53 |
| r350 | - | pickups (us-them) | +0.50 | -0.10 | -0.12 | +0.22 | +0.38 | +0.48 | +0.43 |
| r400 | - | digs (us-them) | +0.62 | +0.08 | +0.15 | +0.20 | +0.32 | +0.51 | +0.53 |
| r400 | - | vaporators (us-them) ~avg | +0.57 | +0.11 | +0.14 | +0.24 | +0.34 | +0.47 | +0.56 |
| r450 | - | digs (us-them) ~avg | +0.64 | +0.08 | +0.18 | +0.19 | +0.27 | +0.40 | +0.51 |
| r450 | - | dirtDeps (us-them) | +0.61 | +0.19 | +0.14 | +0.18 | +0.30 | +0.50 | +0.52 |
| r450 | - | landscapers (us-them) ~avg | +0.65 | +0.16 | +0.14 | +0.24 | +0.27 | +0.44 | +0.55 |
| r500 | - | dirtDeps (us-them) ~avg | +0.64 | +0.19 | +0.17 | +0.17 | +0.25 | +0.39 | +0.50 |
| r500 | - | pickups (us-them) ~avg | +0.48 | -0.10 | -0.15 | +0.04 | +0.22 | +0.37 | +0.48 |
| r550 | - | netguns (us-them) | +0.54 | . | -0.19 | -0.03 | +0.20 | +0.48 | +0.51 |
| r650 | - | moves (us-them) | +0.59 | +0.12 | +0.16 | +0.22 | +0.22 | +0.29 | +0.44 |
| r650 | - | netguns (us-them) ~avg | +0.51 | . | -0.19 | -0.12 | +0.02 | +0.29 | +0.46 |
| r700 | - | miners (us-them) | +0.40 | +0.19 | +0.26 | -0.03 | +0.11 | +0.17 | +0.39 |
| r700 | - | soup (us-them) | +0.33 | +0.05 | +0.21 | -0.14 | +0.25 | +0.09 | +0.18 |
| r800 | - | died (us-them) [inverted] | +0.30 | +0.16 | -0.08 | -0.11 | +0.04 | +0.13 | +0.29 |
| r850 | - | moves (us-them) ~avg | +0.47 | +0.11 | +0.14 | +0.19 | +0.20 | +0.23 | +0.34 |
| r1050 | - | drones (us-them) | +0.39 | +0.25 | -0.09 | +0.09 | +0.30 | +0.30 | +0.26 |
| r1150 | - | drones (us-them) ~avg | +0.31 | +0.25 | +0.03 | +0.04 | +0.13 | +0.25 | +0.27 |
| - | r900 | aba (us-them) [inverted] | -0.40 | -0.02 | +0.07 | +0.14 | +0.13 | -0.09 | -0.30 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | +0.02 | +0.06 | +0.11 | +0.13 | +0.06 | -0.16 |
| - | - | cov (us-them) | +0.10 | -0.10 | -0.03 | +0.02 | +0.03 | +0.10 | +0.00 |
| - | - | cov (us-them) ~avg | -0.09 | -0.09 | -0.08 | -0.01 | -0.01 | +0.05 | +0.02 |
| - | - | died (us-them) [inverted] ~avg | +0.18 | +0.16 | -0.08 | -0.12 | -0.08 | +0.04 | +0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | soup (us-them) ~avg | +0.25 | +0.06 | +0.18 | +0.18 | +0.19 | +0.12 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
