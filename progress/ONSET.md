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
| r50 | - | mines (us-them) | +0.58 | +0.49 | +0.49 | +0.45 | +0.26 | +0.08 | +0.09 |
| r50 | - | mines (us-them) ~avg | +0.54 | +0.50 | +0.53 | +0.51 | +0.45 | +0.25 | +0.14 |
| r50 | r650 | soup (us-them) ~avg | +0.72 | +0.40 | +0.60 | +0.72 | +0.14 | -0.30 | -0.36 |
| r50 | - | worth (us-them) | +0.56 | +0.51 | +0.47 | +0.55 | +0.38 | +0.31 | +0.45 |
| r50 | - | worth (us-them) ~avg | +0.54 | +0.51 | +0.52 | +0.54 | +0.52 | +0.41 | +0.41 |
| r100 | - | drones (us-them) ~avg | +0.31 | +0.31 | +0.18 | +0.15 | +0.06 | -0.14 | -0.15 |
| r100 | - | miners (us-them) | +0.37 | +0.31 | +0.10 | +0.01 | -0.01 | -0.01 | +0.22 |
| r100 | - | robots (us-them) | +0.59 | +0.38 | +0.30 | +0.42 | +0.32 | +0.31 | +0.54 |
| r100 | - | robots (us-them) ~avg | +0.55 | +0.31 | +0.37 | +0.41 | +0.40 | +0.36 | +0.46 |
| r100 | - | spawned (us-them) | +0.57 | +0.38 | +0.33 | +0.35 | +0.25 | +0.18 | +0.45 |
| r100 | - | spawned (us-them) ~avg | +0.49 | +0.31 | +0.38 | +0.39 | +0.36 | +0.26 | +0.34 |
| r100 | - | units (us-them) | +0.50 | +0.37 | +0.17 | +0.27 | +0.22 | +0.19 | +0.46 |
| r100 | - | units (us-them) ~avg | +0.45 | +0.34 | +0.32 | +0.30 | +0.29 | +0.25 | +0.36 |
| r150 | - | miners (us-them) ~avg | +0.37 | +0.28 | +0.28 | +0.15 | +0.07 | +0.03 | +0.11 |
| r150 | - | moves (us-them) | +0.39 | +0.16 | +0.31 | +0.11 | +0.15 | +0.18 | +0.32 |
| r150 | r500 | soup (us-them) | -0.43 | +0.22 | +0.42 | +0.20 | -0.22 | -0.34 | -0.32 |
| r200 | - | cov (us-them) | +0.35 | +0.11 | +0.34 | +0.33 | +0.35 | +0.22 | +0.17 |
| r200 | - | digs (us-them) | +0.59 | +0.20 | +0.39 | +0.49 | +0.53 | +0.58 | +0.57 |
| r200 | - | digs (us-them) ~avg | +0.59 | +0.20 | +0.35 | +0.45 | +0.50 | +0.55 | +0.59 |
| r200 | - | dirtDeps (us-them) | +0.57 | +0.14 | +0.38 | +0.47 | +0.51 | +0.57 | +0.54 |
| r200 | - | dirtDeps (us-them) ~avg | +0.57 | +0.13 | +0.33 | +0.43 | +0.48 | +0.53 | +0.57 |
| r200 | - | pickups (us-them) | +0.57 | +0.15 | +0.31 | +0.39 | +0.42 | +0.47 | +0.18 |
| r250 | - | cov (us-them) ~avg | +0.35 | +0.09 | +0.26 | +0.33 | +0.35 | +0.30 | +0.23 |
| r300 | - | died (us-them) [inverted] | +0.59 | . | -0.23 | +0.41 | +0.46 | +0.50 | +0.32 |
| r300 | - | died (us-them) [inverted] ~avg | +0.62 | . | -0.22 | +0.30 | +0.42 | +0.60 | +0.50 |
| r300 | - | pickups (us-them) ~avg | +0.49 | +0.15 | +0.29 | +0.33 | +0.38 | +0.49 | +0.34 |
| r350 | - | landscapers (us-them) | +0.61 | +0.07 | +0.12 | +0.29 | +0.38 | +0.58 | +0.58 |
| r400 | - | landscapers (us-them) ~avg | +0.64 | +0.07 | +0.15 | +0.22 | +0.31 | +0.47 | +0.63 |
| r500 | - | vaporators (us-them) | +0.59 | +0.28 | +0.25 | +0.26 | +0.22 | +0.47 | +0.55 |
| r550 | - | vaporators (us-them) ~avg | +0.61 | +0.28 | +0.25 | +0.26 | +0.26 | +0.36 | +0.55 |
| r750 | - | netguns (us-them) | +0.35 | . | . | . | +0.23 | +0.16 | +0.34 |
| r950 | - | netguns (us-them) ~avg | +0.31 | . | . | . | +0.23 | +0.21 | +0.30 |
| r1150 | - | moves (us-them) ~avg | +0.32 | +0.15 | +0.30 | +0.18 | +0.16 | +0.16 | +0.22 |
| - | r450 | aba (us-them) [inverted] | -0.58 | -0.14 | -0.24 | -0.23 | -0.27 | -0.37 | -0.51 |
| - | r150 | aba (us-them) [inverted] ~avg | -0.59 | -0.16 | -0.34 | -0.33 | -0.34 | -0.41 | -0.53 |
| - | - | drones (us-them) | +0.31 | +0.31 | +0.03 | +0.14 | -0.08 | -0.25 | -0.10 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.15 | . | . | +0.16 | +0.23 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.25 | +0.15 | +0.16 | +0.23 | +0.23 | +0.23 | +0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
