# Which metric starts predicting the result first

19 games, 6 wins. Noise floor about 0.46; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.50 | +0.48 | +0.34 | +0.50 | +0.25 | +0.18 | +0.06 |
| r50 | - | miners (us-them) ~avg | +0.53 | +0.53 | +0.45 | +0.47 | +0.44 | +0.33 | +0.19 |
| r50 | - | robots (us-them) | +0.48 | +0.47 | +0.47 | +0.43 | +0.31 | +0.22 | +0.20 |
| r50 | - | robots (us-them) ~avg | +0.50 | +0.47 | +0.49 | +0.49 | +0.44 | +0.30 | +0.24 |
| r50 | - | spawned (us-them) | +0.49 | +0.47 | +0.49 | +0.48 | +0.36 | +0.23 | +0.22 |
| r50 | - | spawned (us-them) ~avg | +0.51 | +0.47 | +0.50 | +0.51 | +0.48 | +0.33 | +0.27 |
| r50 | - | units (us-them) | +0.51 | +0.46 | +0.45 | +0.51 | +0.34 | +0.28 | +0.27 |
| r50 | - | units (us-them) ~avg | +0.51 | +0.49 | +0.49 | +0.51 | +0.47 | +0.35 | +0.32 |
| r100 | - | mines (us-them) | +0.48 | +0.41 | +0.43 | +0.37 | +0.31 | +0.27 | +0.29 |
| r100 | - | mines (us-them) ~avg | +0.45 | +0.37 | +0.45 | +0.43 | +0.39 | +0.30 | +0.30 |
| r100 | - | worth (us-them) | +0.43 | +0.34 | +0.41 | +0.27 | +0.25 | +0.17 | +0.08 |
| r100 | - | worth (us-them) ~avg | +0.43 | +0.32 | +0.43 | +0.36 | +0.31 | +0.21 | +0.14 |
| r150 | - | digs (us-them) | +0.58 | +0.05 | +0.42 | +0.36 | +0.38 | +0.32 | +0.46 |
| r150 | - | moves (us-them) | +0.56 | +0.18 | +0.44 | +0.56 | +0.44 | +0.31 | +0.25 |
| r200 | - | digs (us-them) ~avg | +0.47 | +0.05 | +0.38 | +0.38 | +0.38 | +0.31 | +0.38 |
| r200 | - | dirtDeps (us-them) | +0.56 | +0.01 | +0.42 | +0.33 | +0.36 | +0.33 | +0.45 |
| r200 | - | dirtDeps (us-them) ~avg | +0.46 | +0.01 | +0.38 | +0.36 | +0.36 | +0.31 | +0.39 |
| r200 | - | landscapers (us-them) | +0.47 | +0.06 | +0.31 | +0.39 | +0.40 | +0.44 | +0.45 |
| r200 | - | moves (us-them) ~avg | +0.52 | +0.04 | +0.39 | +0.51 | +0.50 | +0.37 | +0.29 |
| r250 | - | landscapers (us-them) ~avg | +0.49 | +0.06 | +0.28 | +0.34 | +0.39 | +0.43 | +0.49 |
| r250 | r800 | soup (us-them) | +0.46 | -0.11 | -0.14 | +0.46 | +0.25 | +0.31 | -0.34 |
| r450 | - | soup (us-them) ~avg | +0.40 | -0.11 | -0.05 | +0.21 | +0.28 | +0.40 | -0.10 |
| r750 | - | netguns (us-them) | +0.49 | . | . | . | +0.23 | +0.15 | +0.45 |
| r750 | - | netguns (us-them) ~avg | +0.47 | . | . | . | +0.23 | +0.22 | +0.39 |
| r950 | - | cov (us-them) | +0.47 | -0.19 | +0.17 | +0.11 | +0.00 | +0.09 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.28 | +0.02 | +0.16 | +0.12 | +0.18 | -0.16 | -0.19 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | -0.02 | +0.08 | +0.10 | +0.17 | -0.05 | -0.17 |
| - | - | cov (us-them) ~avg | +0.29 | -0.21 | -0.06 | +0.04 | +0.04 | +0.02 | +0.15 |
| - | r1100 | died (us-them) [inverted] | -0.35 | -0.16 | -0.35 | -0.22 | -0.03 | +0.08 | -0.18 |
| - | r200 | died (us-them) [inverted] ~avg | -0.31 | -0.16 | -0.31 | -0.30 | -0.18 | -0.04 | -0.13 |
| - | - | drones (us-them) | +0.29 | +0.15 | +0.29 | +0.10 | +0.04 | +0.08 | +0.18 |
| - | - | drones (us-them) ~avg | +0.22 | +0.15 | +0.21 | +0.18 | +0.11 | +0.05 | +0.14 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | pickups (us-them) | +0.29 | +0.16 | +0.16 | +0.21 | +0.28 | +0.14 | -0.20 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.16 | +0.18 | +0.22 | +0.26 | +0.22 | -0.08 |
| - | - | vaporators (us-them) | -0.29 | -0.11 | -0.03 | -0.06 | +0.06 | +0.03 | +0.01 |
| - | - | vaporators (us-them) ~avg | -0.25 | -0.11 | -0.14 | -0.08 | -0.01 | +0.02 | +0.02 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
