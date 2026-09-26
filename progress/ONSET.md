# Which metric starts predicting the result first

39 games, 20 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | miners (us-them) | +0.37 | +0.27 | +0.28 | +0.13 | +0.17 | +0.03 | +0.00 |
| r50 | - | miners (us-them) ~avg | +0.35 | +0.35 | +0.34 | +0.30 | +0.25 | +0.13 | +0.07 |
| r50 | - | mines (us-them) | +0.55 | +0.46 | +0.30 | +0.36 | +0.40 | +0.42 | +0.42 |
| r50 | - | mines (us-them) ~avg | +0.56 | +0.52 | +0.35 | +0.36 | +0.40 | +0.40 | +0.44 |
| r50 | - | robots (us-them) | +0.53 | +0.45 | +0.35 | +0.40 | +0.53 | +0.34 | +0.44 |
| r50 | - | robots (us-them) ~avg | +0.52 | +0.52 | +0.40 | +0.40 | +0.47 | +0.41 | +0.44 |
| r50 | - | spawned (us-them) | +0.49 | +0.49 | +0.35 | +0.37 | +0.49 | +0.31 | +0.43 |
| r50 | - | spawned (us-them) ~avg | +0.55 | +0.55 | +0.44 | +0.42 | +0.47 | +0.37 | +0.41 |
| r50 | - | units (us-them) | +0.45 | +0.44 | +0.33 | +0.35 | +0.45 | +0.23 | +0.36 |
| r50 | - | units (us-them) ~avg | +0.49 | +0.49 | +0.35 | +0.35 | +0.40 | +0.35 | +0.37 |
| r50 | - | worth (us-them) | +0.47 | +0.41 | +0.23 | +0.32 | +0.34 | +0.36 | +0.42 |
| r50 | - | worth (us-them) ~avg | +0.44 | +0.44 | +0.25 | +0.28 | +0.32 | +0.31 | +0.39 |
| r200 | - | digs (us-them) | +0.53 | +0.34 | +0.33 | +0.37 | +0.40 | +0.35 | +0.41 |
| r200 | - | landscapers (us-them) | +0.48 | +0.18 | +0.48 | +0.32 | +0.34 | +0.19 | +0.38 |
| r200 | - | landscapers (us-them) ~avg | +0.46 | +0.19 | +0.35 | +0.32 | +0.35 | +0.31 | +0.35 |
| r250 | - | digs (us-them) ~avg | +0.46 | +0.36 | +0.29 | +0.35 | +0.38 | +0.36 | +0.39 |
| r250 | - | dirtDeps (us-them) | +0.52 | +0.24 | +0.24 | +0.33 | +0.36 | +0.33 | +0.41 |
| r350 | - | dirtDeps (us-them) ~avg | +0.45 | +0.28 | +0.19 | +0.29 | +0.33 | +0.33 | +0.38 |
| r400 | - | pickups (us-them) | +0.35 | -0.14 | -0.01 | +0.15 | +0.33 | +0.22 | +0.27 |
| r650 | - | netguns (us-them) | +0.49 | . | -0.02 | +0.09 | +0.17 | +0.28 | +0.42 |
| r700 | - | vaporators (us-them) | +0.36 | -0.01 | -0.26 | -0.15 | -0.02 | +0.22 | +0.32 |
| r850 | - | netguns (us-them) ~avg | +0.43 | . | -0.07 | +0.05 | +0.09 | +0.15 | +0.34 |
| r950 | - | moves (us-them) | -0.45 | -0.09 | +0.20 | +0.18 | +0.19 | +0.18 | +0.29 |
| r1150 | - | pickups (us-them) ~avg | +0.31 | -0.14 | -0.03 | +0.09 | +0.21 | +0.24 | +0.25 |
| r1200 | - | moves (us-them) ~avg | -0.45 | -0.23 | +0.17 | +0.18 | +0.17 | +0.17 | +0.23 |
| r1200 | - | vaporators (us-them) ~avg | +0.30 | -0.01 | -0.24 | -0.20 | -0.13 | +0.02 | +0.22 |
| - | - | aba (us-them) [inverted] | +0.22 | +0.18 | -0.01 | +0.10 | +0.15 | +0.18 | +0.09 |
| - | - | aba (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.04 | +0.07 | +0.11 | +0.17 | +0.15 |
| - | - | cov (us-them) | +0.26 | -0.06 | +0.15 | +0.12 | +0.16 | +0.17 | +0.07 |
| - | - | cov (us-them) ~avg | +0.20 | -0.11 | +0.12 | +0.15 | +0.15 | +0.15 | +0.13 |
| - | - | died (us-them) [inverted] | +0.28 | -0.16 | +0.13 | +0.19 | +0.21 | +0.12 | +0.10 |
| - | - | died (us-them) [inverted] ~avg | +0.20 | -0.16 | +0.05 | +0.12 | +0.18 | +0.15 | +0.14 |
| - | - | drones (us-them) | -0.34 | +0.31 | -0.34 | +0.03 | +0.21 | +0.18 | +0.21 |
| - | - | drones (us-them) ~avg | +0.31 | +0.31 | -0.14 | -0.08 | +0.01 | +0.04 | +0.19 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.29 | +0.24 | +0.14 | +0.22 | +0.22 | +0.14 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.26 | +0.14 | +0.23 | +0.23 | +0.14 | +0.14 |
| - | - | soup (us-them) | +0.30 | -0.11 | +0.09 | +0.30 | -0.00 | +0.17 | +0.01 |
| - | - | soup (us-them) ~avg | -0.19 | -0.18 | -0.14 | +0.13 | +0.10 | +0.17 | +0.01 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
