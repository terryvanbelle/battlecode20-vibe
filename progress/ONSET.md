# Which metric starts predicting the result first

19 games, 8 wins. Noise floor about 0.46; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | robots (us-them) | +0.52 | +0.33 | +0.38 | +0.47 | +0.46 | +0.51 | +0.48 |
| r50 | - | robots (us-them) ~avg | +0.55 | +0.35 | +0.37 | +0.55 | +0.54 | +0.54 | +0.53 |
| r50 | - | spawned (us-them) | +0.56 | +0.34 | +0.38 | +0.56 | +0.51 | +0.48 | +0.45 |
| r50 | - | spawned (us-them) ~avg | +0.58 | +0.35 | +0.37 | +0.57 | +0.58 | +0.55 | +0.51 |
| r50 | - | units (us-them) | +0.59 | +0.27 | +0.36 | +0.43 | +0.45 | +0.53 | +0.53 |
| r50 | - | units (us-them) ~avg | +0.58 | +0.31 | +0.33 | +0.49 | +0.49 | +0.54 | +0.58 |
| r150 | - | landscapers (us-them) | +0.72 | +0.28 | +0.36 | +0.32 | +0.37 | +0.47 | +0.70 |
| r150 | - | landscapers (us-them) ~avg | +0.69 | +0.28 | +0.38 | +0.40 | +0.40 | +0.45 | +0.69 |
| r200 | - | pickups (us-them) | +0.61 | +0.19 | +0.35 | +0.49 | +0.56 | +0.61 | +0.48 |
| r200 | - | pickups (us-them) ~avg | +0.60 | +0.19 | +0.32 | +0.41 | +0.50 | +0.59 | +0.54 |
| r300 | - | drones (us-them) | +0.43 | +0.09 | +0.29 | +0.32 | +0.22 | +0.43 | +0.37 |
| r300 | - | drones (us-them) ~avg | +0.43 | +0.09 | +0.22 | +0.40 | +0.34 | +0.42 | +0.37 |
| r300 | - | miners (us-them) ~avg | +0.38 | +0.23 | +0.21 | +0.37 | +0.36 | +0.37 | +0.34 |
| r300 | - | mines (us-them) | +0.50 | +0.15 | +0.17 | +0.41 | +0.45 | +0.50 | +0.47 |
| r300 | - | mines (us-them) ~avg | +0.49 | +0.14 | +0.19 | +0.45 | +0.45 | +0.49 | +0.47 |
| r300 | r50 | moves (us-them) | -0.53 | -0.29 | +0.07 | +0.32 | +0.26 | +0.24 | +0.33 |
| r300 | - | worth (us-them) | +0.49 | +0.19 | +0.32 | +0.47 | +0.41 | +0.49 | +0.43 |
| r300 | - | worth (us-them) ~avg | +0.50 | +0.18 | +0.28 | +0.50 | +0.48 | +0.48 | +0.47 |
| r350 | - | miners (us-them) | +0.38 | +0.12 | +0.19 | +0.28 | +0.31 | +0.36 | +0.32 |
| r500 | - | died (us-them) [inverted] | +0.51 | -0.19 | +0.09 | +0.08 | +0.15 | +0.51 | +0.28 |
| r500 | - | digs (us-them) | +0.64 | -0.10 | +0.09 | +0.17 | +0.24 | +0.39 | +0.62 |
| r500 | - | dirtDeps (us-them) | +0.59 | -0.11 | +0.08 | +0.19 | +0.24 | +0.39 | +0.58 |
| r550 | - | vaporators (us-them) | +0.37 | -0.10 | -0.12 | +0.19 | +0.19 | +0.32 | +0.36 |
| r600 | - | died (us-them) [inverted] ~avg | +0.40 | -0.19 | +0.03 | +0.14 | +0.15 | +0.32 | +0.40 |
| r600 | - | digs (us-them) ~avg | +0.55 | -0.11 | +0.07 | +0.13 | +0.20 | +0.31 | +0.55 |
| r600 | - | dirtDeps (us-them) ~avg | +0.52 | -0.13 | +0.06 | +0.15 | +0.20 | +0.31 | +0.52 |
| r800 | - | vaporators (us-them) ~avg | +0.36 | -0.10 | -0.15 | +0.08 | +0.13 | +0.23 | +0.33 |
| r850 | - | netguns (us-them) | +0.34 | . | . | . | +0.18 | . | +0.30 |
| r1050 | - | cov (us-them) | -0.40 | -0.01 | -0.36 | +0.05 | +0.05 | +0.27 | +0.29 |
| r1200 | - | soup (us-them) | +0.34 | -0.27 | +0.21 | +0.34 | +0.02 | +0.33 | -0.31 |
| - | r900 | aba (us-them) [inverted] | -0.40 | -0.15 | -0.20 | -0.17 | -0.01 | +0.23 | -0.31 |
| - | r950 | aba (us-them) [inverted] ~avg | -0.39 | -0.17 | -0.23 | -0.31 | -0.17 | +0.06 | -0.23 |
| - | r200 | cov (us-them) ~avg | -0.36 | -0.14 | -0.33 | -0.23 | -0.11 | +0.09 | +0.16 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r350 | hqBuried (us-them) [inverted] | -0.34 | +0.19 | . | . | -0.34 | -0.34 | . |
| - | r300 | hqBuried (us-them) [inverted] ~avg | -0.34 | +0.19 | . | -0.34 | -0.34 | -0.34 | . |
| - | r50 | moves (us-them) ~avg | -0.51 | -0.41 | -0.01 | +0.26 | +0.27 | +0.26 | +0.27 |
| - | - | netguns (us-them) ~avg | +0.29 | . | . | . | +0.18 | +0.18 | +0.25 |
| - | r50 | soup (us-them) ~avg | -0.38 | -0.33 | -0.10 | +0.15 | +0.14 | +0.22 | -0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
