# Which metric starts predicting the result first

44 games, 22 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.56 | +0.46 | +0.40 | +0.47 | +0.49 | +0.55 | +0.47 |
| r50 | - | mines (us-them) ~avg | +0.54 | +0.47 | +0.42 | +0.47 | +0.49 | +0.53 | +0.51 |
| r50 | - | worth (us-them) | +0.61 | +0.43 | +0.37 | +0.42 | +0.53 | +0.61 | +0.58 |
| r50 | - | worth (us-them) ~avg | +0.59 | +0.43 | +0.42 | +0.45 | +0.51 | +0.57 | +0.59 |
| r100 | - | drones (us-them) | +0.39 | +0.32 | +0.33 | +0.25 | +0.39 | +0.33 | +0.30 |
| r100 | - | drones (us-them) ~avg | +0.37 | +0.32 | +0.32 | +0.30 | +0.36 | +0.36 | +0.32 |
| r100 | - | miners (us-them) ~avg | +0.44 | +0.30 | +0.40 | +0.43 | +0.43 | +0.44 | +0.40 |
| r100 | - | moves (us-them) | +0.51 | +0.34 | +0.50 | +0.29 | +0.19 | +0.25 | +0.34 |
| r100 | - | robots (us-them) | +0.61 | +0.36 | +0.36 | +0.30 | +0.43 | +0.56 | +0.59 |
| r100 | - | robots (us-them) ~avg | +0.58 | +0.37 | +0.38 | +0.38 | +0.40 | +0.49 | +0.56 |
| r100 | - | spawned (us-them) | +0.59 | +0.36 | +0.36 | +0.33 | +0.43 | +0.56 | +0.58 |
| r100 | - | spawned (us-them) ~avg | +0.58 | +0.37 | +0.37 | +0.38 | +0.39 | +0.49 | +0.54 |
| r100 | - | units (us-them) | +0.57 | +0.35 | +0.33 | +0.22 | +0.34 | +0.51 | +0.57 |
| r100 | - | units (us-them) ~avg | +0.56 | +0.34 | +0.34 | +0.32 | +0.32 | +0.43 | +0.52 |
| r150 | - | miners (us-them) | +0.41 | +0.30 | +0.41 | +0.30 | +0.29 | +0.38 | +0.33 |
| r150 | - | moves (us-them) ~avg | +0.53 | +0.23 | +0.53 | +0.41 | +0.29 | +0.26 | +0.26 |
| r200 | r1200 | aba (us-them) [inverted] | +0.32 | +0.22 | +0.31 | +0.23 | +0.10 | -0.04 | -0.16 |
| r250 | - | aba (us-them) [inverted] ~avg | +0.32 | +0.23 | +0.25 | +0.29 | +0.23 | +0.06 | -0.02 |
| r250 | - | hqBuried (us-them) [inverted] | +0.34 | +0.29 | +0.15 | +0.34 | +0.22 | +0.23 | +0.23 |
| r300 | - | vaporators (us-them) | +0.57 | +0.24 | +0.06 | +0.31 | +0.41 | +0.57 | +0.51 |
| r400 | - | digs (us-them) | +0.56 | +0.11 | +0.18 | +0.29 | +0.31 | +0.37 | +0.41 |
| r400 | - | vaporators (us-them) ~avg | +0.55 | +0.24 | +0.07 | +0.19 | +0.31 | +0.51 | +0.55 |
| r450 | - | digs (us-them) ~avg | +0.44 | +0.09 | +0.13 | +0.26 | +0.29 | +0.33 | +0.35 |
| r450 | - | dirtDeps (us-them) | +0.56 | +0.05 | +0.15 | +0.27 | +0.28 | +0.37 | +0.41 |
| r450 | - | landscapers (us-them) | +0.63 | +0.03 | +0.14 | +0.09 | +0.23 | +0.44 | +0.58 |
| r550 | - | dirtDeps (us-them) ~avg | +0.44 | +0.05 | +0.09 | +0.24 | +0.27 | +0.32 | +0.35 |
| r550 | - | netguns (us-them) | +0.33 | . | +0.02 | +0.13 | +0.11 | +0.32 | +0.30 |
| r550 | - | pickups (us-them) | +0.43 | -0.01 | +0.12 | -0.05 | +0.13 | +0.36 | +0.42 |
| r650 | - | landscapers (us-them) ~avg | +0.57 | +0.04 | +0.07 | +0.12 | +0.15 | +0.30 | +0.41 |
| r750 | - | pickups (us-them) ~avg | +0.41 | -0.01 | +0.12 | +0.02 | +0.10 | +0.25 | +0.37 |
| r800 | - | netguns (us-them) ~avg | +0.32 | . | +0.02 | +0.07 | +0.14 | +0.23 | +0.31 |
| - | - | cov (us-them) | +0.25 | -0.09 | +0.25 | +0.22 | +0.18 | +0.17 | +0.14 |
| - | - | cov (us-them) ~avg | -0.25 | -0.16 | +0.09 | +0.19 | +0.17 | +0.18 | +0.14 |
| - | - | died (us-them) [inverted] | +0.17 | . | +0.07 | -0.05 | +0.06 | +0.10 | +0.09 |
| - | - | died (us-them) [inverted] ~avg | +0.19 | . | +0.09 | +0.01 | +0.03 | +0.04 | +0.03 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.30 | +0.27 | -0.02 | +0.30 | +0.24 | +0.24 | +0.26 |
| - | - | soup (us-them) | +0.26 | -0.06 | -0.02 | +0.14 | +0.22 | +0.10 | -0.03 |
| - | - | soup (us-them) ~avg | +0.26 | -0.01 | +0.07 | +0.10 | +0.23 | +0.21 | +0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
