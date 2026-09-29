# Which metric starts predicting the result first

45 games, 21 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.50 | +0.42 | +0.40 | +0.44 | +0.40 | +0.22 | +0.21 |
| r50 | - | mines (us-them) ~avg | +0.52 | +0.43 | +0.40 | +0.48 | +0.46 | +0.34 | +0.25 |
| r50 | - | robots (us-them) ~avg | +0.35 | +0.31 | +0.27 | +0.31 | +0.34 | +0.29 | +0.32 |
| r50 | - | spawned (us-them) | +0.41 | +0.30 | +0.30 | +0.41 | +0.39 | +0.25 | +0.35 |
| r50 | - | spawned (us-them) ~avg | +0.40 | +0.33 | +0.29 | +0.37 | +0.39 | +0.32 | +0.31 |
| r100 | - | drones (us-them) | +0.34 | +0.32 | +0.10 | +0.07 | +0.00 | -0.04 | +0.21 |
| r100 | - | worth (us-them) | +0.43 | +0.42 | +0.33 | +0.40 | +0.40 | +0.32 | +0.36 |
| r100 | - | worth (us-them) ~avg | +0.48 | +0.39 | +0.38 | +0.43 | +0.42 | +0.35 | +0.35 |
| r150 | - | drones (us-them) ~avg | +0.37 | +0.27 | +0.33 | +0.28 | +0.18 | +0.02 | +0.10 |
| r250 | - | pickups (us-them) ~avg | +0.38 | +0.18 | +0.26 | +0.33 | +0.37 | +0.36 | +0.34 |
| r250 | - | robots (us-them) | +0.39 | +0.28 | +0.26 | +0.36 | +0.35 | +0.22 | +0.38 |
| r250 | - | units (us-them) | +0.45 | +0.26 | +0.20 | +0.27 | +0.31 | +0.17 | +0.41 |
| r250 | - | vaporators (us-them) ~avg | +0.34 | +0.12 | +0.29 | +0.30 | +0.30 | +0.33 | +0.33 |
| r350 | - | landscapers (us-them) | +0.52 | +0.09 | +0.13 | +0.19 | +0.34 | +0.28 | +0.44 |
| r350 | - | moves (us-them) | +0.37 | -0.14 | +0.14 | +0.25 | +0.31 | +0.29 | +0.36 |
| r350 | - | pickups (us-them) | +0.37 | +0.18 | +0.23 | +0.29 | +0.36 | +0.34 | +0.33 |
| r400 | - | digs (us-them) | +0.62 | +0.15 | +0.19 | +0.28 | +0.31 | +0.39 | +0.49 |
| r450 | - | dirtDeps (us-them) | +0.60 | +0.20 | +0.20 | +0.25 | +0.29 | +0.37 | +0.47 |
| r450 | - | moves (us-them) ~avg | +0.36 | -0.22 | +0.07 | +0.19 | +0.29 | +0.29 | +0.33 |
| r500 | - | vaporators (us-them) | +0.36 | +0.12 | +0.25 | +0.24 | +0.28 | +0.34 | +0.30 |
| r600 | - | digs (us-them) ~avg | +0.53 | +0.15 | +0.17 | +0.22 | +0.25 | +0.31 | +0.41 |
| r600 | - | netguns (us-them) | +0.38 | -0.16 | -0.09 | +0.08 | +0.15 | +0.32 | +0.38 |
| r700 | - | dirtDeps (us-them) ~avg | +0.51 | +0.21 | +0.19 | +0.19 | +0.22 | +0.28 | +0.38 |
| r700 | - | landscapers (us-them) ~avg | +0.47 | +0.09 | +0.10 | +0.11 | +0.22 | +0.30 | +0.38 |
| r850 | - | units (us-them) ~avg | +0.40 | +0.27 | +0.22 | +0.24 | +0.28 | +0.24 | +0.32 |
| r950 | - | died (us-them) [inverted] ~avg | +0.32 | -0.13 | -0.00 | -0.01 | +0.00 | +0.01 | +0.15 |
| - | r650 | aba (us-them) [inverted] | -0.53 | -0.01 | -0.03 | -0.02 | -0.06 | -0.26 | -0.53 |
| - | r850 | aba (us-them) [inverted] ~avg | -0.48 | -0.01 | -0.05 | -0.05 | -0.05 | -0.17 | -0.38 |
| - | - | cov (us-them) | +0.26 | -0.11 | +0.26 | +0.17 | +0.13 | -0.05 | -0.06 |
| - | - | cov (us-them) ~avg | -0.13 | -0.12 | +0.09 | +0.01 | +0.08 | -0.02 | -0.07 |
| - | - | died (us-them) [inverted] | +0.25 | -0.09 | -0.04 | +0.07 | +0.04 | +0.01 | +0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.34 | +0.20 | +0.26 | +0.22 | +0.20 | +0.21 | +0.21 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.20 | +0.24 | +0.20 | +0.14 | +0.20 | +0.23 |
| - | - | miners (us-them) | +0.28 | +0.12 | +0.21 | +0.25 | +0.09 | +0.04 | +0.27 |
| - | - | miners (us-them) ~avg | +0.31 | +0.18 | +0.15 | +0.21 | +0.18 | +0.08 | +0.16 |
| - | - | netguns (us-them) ~avg | +0.30 | -0.16 | -0.14 | -0.05 | +0.05 | +0.17 | +0.28 |
| - | - | soup (us-them) | +0.15 | +0.04 | -0.03 | +0.07 | -0.01 | +0.10 | +0.07 |
| - | - | soup (us-them) ~avg | +0.19 | +0.01 | +0.03 | +0.13 | +0.18 | +0.14 | +0.10 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
