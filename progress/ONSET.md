# Which metric starts predicting the result first

47 games, 24 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) ~avg | +0.38 | +0.28 | +0.20 | +0.16 | +0.09 | +0.09 | +0.30 |
| r50 | - | mines (us-them) | +0.52 | +0.39 | +0.36 | +0.41 | +0.38 | +0.45 | +0.52 |
| r50 | - | mines (us-them) ~avg | +0.57 | +0.45 | +0.41 | +0.51 | +0.48 | +0.48 | +0.56 |
| r50 | - | robots (us-them) | +0.61 | +0.25 | +0.28 | +0.35 | +0.34 | +0.43 | +0.58 |
| r50 | - | robots (us-them) ~avg | +0.64 | +0.32 | +0.28 | +0.38 | +0.38 | +0.44 | +0.64 |
| r50 | - | spawned (us-them) | +0.53 | +0.25 | +0.23 | +0.35 | +0.31 | +0.41 | +0.53 |
| r50 | - | spawned (us-them) ~avg | +0.57 | +0.32 | +0.26 | +0.38 | +0.38 | +0.42 | +0.56 |
| r50 | - | units (us-them) ~avg | +0.56 | +0.31 | +0.22 | +0.31 | +0.30 | +0.35 | +0.56 |
| r50 | - | worth (us-them) | +0.61 | +0.30 | +0.35 | +0.41 | +0.36 | +0.41 | +0.59 |
| r50 | - | worth (us-them) ~avg | +0.60 | +0.37 | +0.38 | +0.51 | +0.48 | +0.46 | +0.60 |
| r100 | - | moves (us-them) | +0.39 | +0.31 | +0.26 | +0.21 | +0.17 | +0.16 | +0.31 |
| r150 | - | drones (us-them) | +0.35 | +0.06 | +0.34 | +0.18 | -0.03 | -0.00 | +0.14 |
| r150 | - | moves (us-them) ~avg | +0.36 | +0.22 | +0.34 | +0.27 | +0.22 | +0.19 | +0.29 |
| r200 | - | drones (us-them) ~avg | +0.33 | +0.12 | +0.33 | +0.24 | +0.14 | +0.03 | +0.08 |
| r200 | - | hqBuried (us-them) [inverted] | +0.36 | +0.22 | +0.30 | +0.21 | +0.20 | +0.20 | +0.21 |
| r250 | - | pickups (us-them) ~avg | +0.33 | +0.05 | +0.28 | +0.28 | +0.27 | +0.22 | +0.29 |
| r350 | - | digs (us-them) | +0.61 | +0.16 | +0.14 | +0.27 | +0.36 | +0.40 | +0.53 |
| r350 | - | landscapers (us-them) | +0.56 | +0.11 | +0.11 | +0.27 | +0.36 | +0.38 | +0.52 |
| r350 | - | landscapers (us-them) ~avg | +0.63 | +0.11 | +0.07 | +0.28 | +0.34 | +0.48 | +0.61 |
| r350 | - | units (us-them) | +0.52 | +0.21 | +0.18 | +0.28 | +0.26 | +0.32 | +0.50 |
| r400 | - | digs (us-them) ~avg | +0.58 | +0.16 | +0.12 | +0.23 | +0.30 | +0.38 | +0.49 |
| r450 | - | dirtDeps (us-them) | +0.59 | +0.16 | +0.11 | +0.23 | +0.29 | +0.36 | +0.50 |
| r550 | - | dirtDeps (us-them) ~avg | +0.55 | +0.16 | +0.08 | +0.18 | +0.24 | +0.33 | +0.45 |
| r700 | - | miners (us-them) | +0.54 | +0.13 | +0.03 | +0.12 | -0.04 | +0.15 | +0.38 |
| r700 | - | netguns (us-them) | +0.31 | . | +0.10 | +0.23 | +0.25 | +0.20 | +0.26 |
| r700 | - | netguns (us-them) ~avg | +0.34 | . | -0.03 | +0.22 | +0.23 | +0.25 | +0.33 |
| r700 | - | vaporators (us-them) | +0.53 | +0.00 | +0.06 | +0.06 | +0.08 | +0.26 | +0.51 |
| r750 | - | vaporators (us-them) ~avg | +0.45 | +0.00 | +0.01 | +0.01 | +0.05 | +0.17 | +0.41 |
| - | - | aba (us-them) [inverted] | +0.22 | +0.14 | +0.00 | +0.16 | +0.21 | +0.20 | +0.04 |
| - | - | aba (us-them) [inverted] ~avg | +0.18 | +0.15 | -0.00 | +0.05 | +0.12 | +0.17 | +0.09 |
| - | - | cov (us-them) | -0.35 | -0.08 | +0.03 | +0.22 | +0.27 | +0.17 | +0.07 |
| - | - | cov (us-them) ~avg | -0.39 | -0.24 | -0.06 | +0.08 | +0.17 | +0.20 | +0.20 |
| - | - | died (us-them) [inverted] | +0.28 | . | +0.28 | +0.10 | +0.09 | +0.04 | +0.23 |
| - | - | died (us-them) [inverted] ~avg | +0.26 | . | +0.26 | +0.14 | +0.11 | +0.09 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.22 | +0.27 | +0.19 | +0.20 | +0.20 | +0.21 |
| - | - | pickups (us-them) | +0.30 | +0.05 | +0.26 | +0.27 | +0.21 | +0.18 | +0.18 |
| - | - | soup (us-them) | +0.25 | +0.01 | -0.06 | +0.03 | -0.06 | +0.02 | +0.11 |
| - | - | soup (us-them) ~avg | +0.19 | +0.05 | +0.07 | +0.18 | +0.12 | +0.09 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
