# Which metric starts predicting the result first

46 games, 25 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] ~avg | +0.31 | +0.26 | +0.03 | -0.00 | +0.02 | +0.02 | +0.02 |
| r50 | - | miners (us-them) | +0.42 | +0.38 | +0.42 | +0.34 | +0.26 | +0.23 | +0.26 |
| r50 | - | miners (us-them) ~avg | +0.49 | +0.41 | +0.48 | +0.48 | +0.40 | +0.32 | +0.29 |
| r50 | - | units (us-them) | +0.52 | +0.47 | +0.49 | +0.49 | +0.45 | +0.39 | +0.36 |
| r50 | - | units (us-them) ~avg | +0.55 | +0.46 | +0.52 | +0.55 | +0.53 | +0.51 | +0.43 |
| r100 | - | mines (us-them) | +0.52 | +0.31 | +0.39 | +0.52 | +0.50 | +0.50 | +0.43 |
| r100 | - | mines (us-them) ~avg | +0.53 | +0.31 | +0.36 | +0.46 | +0.51 | +0.53 | +0.48 |
| r100 | - | moves (us-them) | +0.50 | +0.34 | +0.50 | +0.50 | +0.44 | +0.41 | +0.36 |
| r100 | - | robots (us-them) | +0.53 | +0.48 | +0.45 | +0.47 | +0.48 | +0.45 | +0.45 |
| r100 | - | robots (us-them) ~avg | +0.54 | +0.46 | +0.50 | +0.53 | +0.53 | +0.52 | +0.46 |
| r100 | - | spawned (us-them) | +0.51 | +0.48 | +0.43 | +0.47 | +0.47 | +0.48 | +0.47 |
| r100 | - | spawned (us-them) ~avg | +0.53 | +0.45 | +0.48 | +0.52 | +0.52 | +0.53 | +0.48 |
| r100 | - | worth (us-them) | +0.46 | +0.31 | +0.32 | +0.43 | +0.43 | +0.46 | +0.41 |
| r150 | - | drones (us-them) | +0.40 | +0.25 | +0.25 | +0.26 | +0.22 | +0.25 | +0.28 |
| r150 | - | drones (us-them) ~avg | +0.34 | +0.25 | +0.32 | +0.30 | +0.29 | +0.29 | +0.28 |
| r150 | - | landscapers (us-them) | +0.43 | +0.14 | +0.39 | +0.40 | +0.39 | +0.35 | +0.24 |
| r150 | - | moves (us-them) ~avg | +0.52 | +0.23 | +0.50 | +0.52 | +0.49 | +0.45 | +0.42 |
| r150 | - | worth (us-them) ~avg | +0.46 | +0.27 | +0.34 | +0.41 | +0.44 | +0.46 | +0.44 |
| r200 | - | landscapers (us-them) ~avg | +0.48 | +0.14 | +0.35 | +0.43 | +0.45 | +0.48 | +0.35 |
| r350 | - | digs (us-them) | +0.41 | +0.03 | +0.24 | +0.29 | +0.37 | +0.41 | +0.29 |
| r350 | - | digs (us-them) ~avg | +0.40 | +0.03 | +0.22 | +0.27 | +0.34 | +0.39 | +0.33 |
| r350 | - | dirtDeps (us-them) | +0.40 | +0.00 | +0.20 | +0.26 | +0.35 | +0.39 | +0.27 |
| r400 | - | dirtDeps (us-them) ~avg | +0.38 | +0.00 | +0.18 | +0.25 | +0.31 | +0.38 | +0.31 |
| r550 | - | vaporators (us-them) | +0.38 | -0.08 | -0.22 | -0.10 | +0.09 | +0.33 | +0.33 |
| r750 | - | soup (us-them) ~avg | +0.38 | -0.34 | -0.14 | +0.01 | +0.08 | +0.08 | +0.32 |
| r750 | - | vaporators (us-them) ~avg | +0.34 | -0.08 | -0.21 | -0.20 | -0.08 | +0.18 | +0.34 |
| r1050 | r450 | netguns (us-them) | +0.33 | -0.14 | -0.29 | -0.28 | -0.29 | -0.03 | +0.24 |
| - | - | aba (us-them) [inverted] | +0.31 | +0.22 | -0.07 | -0.00 | +0.03 | +0.01 | +0.06 |
| - | - | cov (us-them) | +0.25 | +0.03 | +0.21 | +0.21 | +0.25 | +0.18 | +0.12 |
| - | - | cov (us-them) ~avg | +0.21 | -0.03 | +0.11 | +0.15 | +0.18 | +0.21 | +0.18 |
| - | - | died (us-them) [inverted] | +0.30 | +0.16 | +0.23 | +0.14 | +0.24 | +0.07 | +0.01 |
| - | - | died (us-them) [inverted] ~avg | +0.27 | +0.16 | +0.26 | +0.21 | +0.22 | +0.15 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | +0.14 | +0.15 | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.16 | +0.17 | +0.09 | +0.09 | +0.15 |
| - | r450 | netguns (us-them) ~avg | -0.30 | -0.14 | -0.26 | -0.29 | -0.30 | -0.25 | -0.09 |
| - | - | pickups (us-them) | +0.17 | -0.12 | +0.06 | -0.06 | -0.05 | -0.03 | +0.04 |
| - | - | pickups (us-them) ~avg | -0.12 | -0.12 | +0.09 | -0.03 | -0.05 | -0.03 | -0.04 |
| - | - | soup (us-them) | -0.33 | -0.33 | +0.04 | +0.31 | +0.05 | -0.00 | +0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
