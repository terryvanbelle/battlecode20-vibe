# Which metric starts predicting the result first -- every recorded block of g_iter16 merged

138 games, 67 wins. Noise floor about 0.17; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.43 | +0.41 | +0.43 | +0.41 | +0.39 | +0.36 | +0.35 |
| r50 | - | mines (us-them) ~avg | +0.48 | +0.41 | +0.45 | +0.48 | +0.45 | +0.40 | +0.38 |
| r50 | - | worth (us-them) | +0.49 | +0.36 | +0.49 | +0.46 | +0.43 | +0.43 | +0.37 |
| r50 | - | worth (us-them) ~avg | +0.50 | +0.36 | +0.46 | +0.50 | +0.47 | +0.45 | +0.41 |
| r100 | - | drones (us-them) | +0.43 | +0.42 | +0.37 | +0.35 | +0.36 | +0.31 | +0.38 |
| r100 | - | drones (us-them) ~avg | +0.45 | +0.39 | +0.44 | +0.43 | +0.43 | +0.38 | +0.39 |
| r100 | - | robots (us-them) | +0.49 | +0.36 | +0.49 | +0.44 | +0.45 | +0.44 | +0.43 |
| r100 | - | robots (us-them) ~avg | +0.50 | +0.33 | +0.45 | +0.50 | +0.49 | +0.49 | +0.46 |
| r100 | - | spawned (us-them) | +0.43 | +0.34 | +0.43 | +0.36 | +0.37 | +0.38 | +0.41 |
| r100 | - | spawned (us-them) ~avg | +0.44 | +0.32 | +0.41 | +0.44 | +0.42 | +0.42 | +0.42 |
| r100 | - | units (us-them) | +0.48 | +0.37 | +0.46 | +0.41 | +0.46 | +0.45 | +0.46 |
| r100 | - | units (us-them) ~avg | +0.51 | +0.33 | +0.41 | +0.47 | +0.47 | +0.51 | +0.50 |
| r200 | - | miners (us-them) ~avg | +0.33 | +0.27 | +0.33 | +0.29 | +0.28 | +0.27 | +0.24 |
| r250 | - | landscapers (us-them) | +0.50 | +0.09 | +0.30 | +0.36 | +0.43 | +0.48 | +0.47 |
| r250 | - | moves (us-them) | +0.42 | +0.05 | +0.30 | +0.36 | +0.33 | +0.39 | +0.41 |
| r250 | - | moves (us-them) ~avg | +0.41 | +0.00 | +0.25 | +0.36 | +0.35 | +0.37 | +0.41 |
| r300 | - | digs (us-them) | +0.65 | +0.11 | +0.21 | +0.34 | +0.40 | +0.49 | +0.56 |
| r300 | - | dirtDeps (us-them) | +0.63 | +0.07 | +0.20 | +0.30 | +0.36 | +0.45 | +0.53 |
| r300 | - | pickups (us-them) | +0.38 | +0.20 | +0.28 | +0.33 | +0.34 | +0.36 | +0.34 |
| r300 | - | pickups (us-them) ~avg | +0.40 | +0.19 | +0.27 | +0.30 | +0.34 | +0.40 | +0.38 |
| r350 | - | digs (us-them) ~avg | +0.59 | +0.11 | +0.18 | +0.29 | +0.34 | +0.43 | +0.52 |
| r350 | - | landscapers (us-them) ~avg | +0.51 | +0.09 | +0.16 | +0.29 | +0.35 | +0.48 | +0.51 |
| r450 | - | died (us-them) [inverted] | +0.37 | +0.14 | +0.27 | +0.23 | +0.28 | +0.36 | +0.26 |
| r450 | - | died (us-them) [inverted] ~avg | +0.35 | +0.13 | +0.25 | +0.28 | +0.29 | +0.34 | +0.33 |
| r450 | - | dirtDeps (us-them) ~avg | +0.56 | +0.07 | +0.17 | +0.25 | +0.29 | +0.39 | +0.48 |
| r650 | - | vaporators (us-them) | +0.32 | +0.02 | +0.01 | +0.22 | +0.22 | +0.29 | +0.26 |
| - | - | aba (us-them) [inverted] | -0.19 | +0.10 | -0.06 | -0.04 | -0.06 | -0.12 | -0.17 |
| - | - | aba (us-them) [inverted] ~avg | -0.17 | +0.08 | -0.03 | -0.04 | -0.04 | -0.09 | -0.14 |
| - | - | cov (us-them) | +0.28 | -0.05 | +0.13 | +0.16 | +0.22 | +0.27 | +0.27 |
| - | - | cov (us-them) ~avg | +0.26 | -0.09 | -0.03 | +0.04 | +0.12 | +0.21 | +0.26 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.33 | +0.27 | +0.18 | +0.18 | +0.08 | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.26 | +0.15 | +0.13 | +0.07 | -0.09 | -0.09 |
| - | - | miners (us-them) | +0.27 | +0.26 | +0.27 | +0.12 | +0.18 | +0.20 | +0.22 |
| - | - | netguns (us-them) | +0.25 | . | -0.05 | +0.02 | -0.03 | +0.24 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.18 | . | -0.07 | -0.02 | -0.03 | +0.07 | +0.18 |
| - | - | soup (us-them) | -0.20 | -0.08 | -0.01 | -0.05 | +0.10 | +0.06 | -0.20 |
| - | - | soup (us-them) ~avg | -0.15 | +0.00 | -0.03 | -0.07 | +0.01 | -0.01 | -0.15 |
| - | - | vaporators (us-them) ~avg | +0.28 | +0.02 | +0.04 | +0.12 | +0.17 | +0.26 | +0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
