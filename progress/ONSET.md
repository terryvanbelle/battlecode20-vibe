# Which metric starts predicting the result first

41 games, 20 wins. Noise floor about 0.31; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.57 | +0.45 | +0.32 | +0.47 | +0.57 | +0.47 | +0.40 |
| r50 | - | mines (us-them) ~avg | +0.54 | +0.44 | +0.37 | +0.39 | +0.45 | +0.52 | +0.50 |
| r100 | - | hqBuried (us-them) [inverted] | +0.39 | +0.39 | +0.24 | +0.05 | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.40 | +0.40 | +0.23 | +0.07 | -0.15 | -0.14 | -0.14 |
| r100 | - | worth (us-them) | +0.65 | +0.42 | +0.43 | +0.61 | +0.65 | +0.51 | +0.50 |
| r100 | - | worth (us-them) ~avg | +0.65 | +0.39 | +0.50 | +0.58 | +0.65 | +0.60 | +0.55 |
| r250 | - | died (us-them) [inverted] | +0.42 | -0.02 | +0.29 | +0.25 | +0.39 | +0.26 | +0.29 |
| r250 | - | miners (us-them) | +0.49 | +0.27 | +0.29 | +0.35 | +0.29 | +0.35 | +0.41 |
| r250 | - | robots (us-them) | +0.61 | +0.25 | +0.10 | +0.43 | +0.52 | +0.52 | +0.61 |
| r300 | - | miners (us-them) ~avg | +0.50 | +0.28 | +0.23 | +0.30 | +0.30 | +0.31 | +0.34 |
| r300 | - | spawned (us-them) | +0.59 | +0.26 | +0.03 | +0.38 | +0.43 | +0.47 | +0.58 |
| r350 | - | died (us-them) [inverted] ~avg | +0.39 | -0.02 | +0.20 | +0.27 | +0.37 | +0.32 | +0.31 |
| r350 | - | robots (us-them) ~avg | +0.61 | +0.25 | +0.14 | +0.25 | +0.41 | +0.54 | +0.61 |
| r350 | - | vaporators (us-them) | +0.48 | +0.26 | +0.16 | +0.29 | +0.38 | +0.46 | +0.39 |
| r350 | - | vaporators (us-them) ~avg | +0.48 | +0.26 | +0.15 | +0.24 | +0.34 | +0.47 | +0.46 |
| r400 | - | units (us-them) | +0.58 | +0.34 | +0.10 | +0.25 | +0.32 | +0.47 | +0.58 |
| r500 | - | spawned (us-them) ~avg | +0.63 | +0.25 | +0.11 | +0.19 | +0.28 | +0.46 | +0.57 |
| r550 | - | landscapers (us-them) | +0.51 | +0.09 | -0.09 | -0.07 | +0.07 | +0.40 | +0.50 |
| r550 | - | units (us-them) ~avg | +0.58 | +0.34 | +0.15 | +0.18 | +0.23 | +0.36 | +0.57 |
| r600 | - | digs (us-them) | +0.50 | -0.00 | +0.27 | +0.27 | +0.26 | +0.32 | +0.48 |
| r600 | - | dirtDeps (us-them) | +0.49 | +0.14 | +0.26 | +0.25 | +0.25 | +0.30 | +0.46 |
| r600 | - | moves (us-them) | +0.52 | +0.23 | +0.26 | +0.24 | +0.23 | +0.30 | +0.42 |
| r650 | - | pickups (us-them) | +0.37 | +0.10 | +0.05 | +0.23 | +0.30 | +0.30 | +0.34 |
| r700 | - | pickups (us-them) ~avg | +0.33 | +0.10 | +0.05 | +0.19 | +0.27 | +0.29 | +0.33 |
| r750 | - | digs (us-them) ~avg | +0.48 | -0.02 | +0.19 | +0.24 | +0.24 | +0.25 | +0.38 |
| r750 | - | landscapers (us-them) ~avg | +0.47 | +0.11 | -0.10 | -0.12 | -0.07 | +0.11 | +0.45 |
| r750 | - | moves (us-them) ~avg | +0.44 | +0.23 | +0.26 | +0.25 | +0.23 | +0.25 | +0.35 |
| r800 | - | dirtDeps (us-them) ~avg | +0.46 | +0.11 | +0.21 | +0.22 | +0.22 | +0.23 | +0.35 |
| r900 | r200 | netguns (us-them) | +0.32 | . | -0.32 | -0.19 | -0.19 | +0.26 | +0.30 |
| r1150 | - | drones (us-them) | +0.36 | +0.20 | +0.08 | +0.29 | +0.29 | +0.12 | +0.30 |
| - | r750 | aba (us-them) [inverted] | -0.41 | +0.16 | -0.17 | -0.09 | +0.03 | -0.22 | -0.37 |
| - | r1050 | aba (us-them) [inverted] ~avg | -0.35 | +0.16 | -0.11 | -0.10 | -0.01 | -0.10 | -0.28 |
| - | - | cov (us-them) | -0.24 | -0.02 | -0.15 | -0.19 | -0.05 | -0.03 | -0.09 |
| - | - | cov (us-them) ~avg | -0.28 | -0.06 | -0.21 | -0.25 | -0.23 | -0.12 | -0.12 |
| - | - | drones (us-them) ~avg | +0.29 | +0.20 | +0.18 | +0.27 | +0.29 | +0.20 | +0.26 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r200 | netguns (us-them) ~avg | -0.32 | . | -0.32 | -0.23 | -0.21 | -0.02 | +0.16 |
| - | r700 | soup (us-them) | -0.47 | +0.08 | +0.22 | +0.16 | +0.03 | -0.21 | -0.40 |
| - | - | soup (us-them) ~avg | +0.28 | +0.12 | +0.24 | +0.25 | +0.27 | +0.10 | -0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
