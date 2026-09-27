# Which metric starts predicting the result first

45 games, 14 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.60 | +0.44 | +0.37 | +0.35 | +0.36 | +0.27 | +0.25 |
| r50 | - | mines (us-them) ~avg | +0.59 | +0.50 | +0.44 | +0.37 | +0.38 | +0.33 | +0.30 |
| r50 | - | worth (us-them) | +0.50 | +0.50 | +0.38 | +0.42 | +0.40 | +0.35 | +0.32 |
| r50 | - | worth (us-them) ~avg | +0.51 | +0.51 | +0.44 | +0.41 | +0.42 | +0.39 | +0.36 |
| r100 | - | hqBuried (us-them) [inverted] | +0.31 | +0.31 | +0.23 | +0.09 | -0.09 | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.31 | +0.31 | +0.19 | +0.04 | +0.01 | -0.05 | -0.04 |
| r100 | - | robots (us-them) ~avg | +0.42 | +0.32 | +0.31 | +0.38 | +0.42 | +0.39 | +0.38 |
| r100 | - | spawned (us-them) ~avg | +0.38 | +0.32 | +0.31 | +0.34 | +0.37 | +0.36 | +0.35 |
| r250 | - | robots (us-them) | +0.44 | +0.32 | +0.28 | +0.44 | +0.42 | +0.35 | +0.35 |
| r250 | - | spawned (us-them) | +0.39 | +0.31 | +0.27 | +0.38 | +0.38 | +0.33 | +0.34 |
| r250 | - | units (us-them) | +0.44 | +0.33 | +0.25 | +0.44 | +0.40 | +0.34 | +0.36 |
| r300 | - | died (us-them) [inverted] | +0.42 | +0.14 | +0.04 | +0.40 | +0.40 | +0.28 | +0.24 |
| r300 | - | digs (us-them) | +0.46 | +0.23 | +0.21 | +0.32 | +0.40 | +0.43 | +0.42 |
| r300 | - | landscapers (us-them) | +0.45 | +0.09 | +0.18 | +0.38 | +0.41 | +0.41 | +0.44 |
| r300 | - | miners (us-them) | +0.34 | +0.21 | +0.15 | +0.34 | +0.32 | +0.28 | +0.28 |
| r300 | - | units (us-them) ~avg | +0.40 | +0.28 | +0.29 | +0.34 | +0.39 | +0.38 | +0.38 |
| r350 | - | died (us-them) [inverted] ~avg | +0.43 | +0.13 | +0.08 | +0.28 | +0.39 | +0.40 | +0.36 |
| r350 | - | digs (us-them) ~avg | +0.49 | +0.23 | +0.21 | +0.28 | +0.36 | +0.40 | +0.43 |
| r350 | - | dirtDeps (us-them) | +0.43 | +0.25 | +0.16 | +0.27 | +0.36 | +0.39 | +0.38 |
| r350 | - | landscapers (us-them) ~avg | +0.47 | +0.09 | +0.11 | +0.23 | +0.36 | +0.43 | +0.47 |
| r350 | - | moves (us-them) | +0.39 | -0.01 | +0.25 | +0.28 | +0.36 | +0.39 | +0.37 |
| r350 | - | moves (us-them) ~avg | +0.39 | -0.09 | +0.25 | +0.29 | +0.33 | +0.39 | +0.39 |
| r350 | - | pickups (us-them) | +0.38 | +0.11 | +0.05 | +0.25 | +0.37 | +0.32 | +0.20 |
| r350 | - | pickups (us-them) ~avg | +0.40 | +0.11 | +0.14 | +0.25 | +0.36 | +0.40 | +0.27 |
| r400 | - | dirtDeps (us-them) ~avg | +0.46 | +0.24 | +0.17 | +0.23 | +0.31 | +0.36 | +0.40 |
| r500 | - | vaporators (us-them) | +0.36 | +0.25 | +0.19 | +0.27 | +0.29 | +0.32 | +0.29 |
| r550 | - | vaporators (us-them) ~avg | +0.38 | +0.25 | +0.19 | +0.25 | +0.28 | +0.31 | +0.30 |
| r700 | - | miners (us-them) ~avg | +0.31 | +0.18 | +0.24 | +0.24 | +0.27 | +0.30 | +0.30 |
| - | r600 | aba (us-them) [inverted] | -0.48 | -0.18 | -0.11 | -0.03 | -0.14 | -0.33 | -0.48 |
| - | r800 | aba (us-them) [inverted] ~avg | -0.42 | -0.20 | -0.13 | -0.09 | -0.10 | -0.21 | -0.36 |
| - | - | cov (us-them) | +0.27 | -0.08 | +0.24 | +0.23 | +0.20 | +0.13 | +0.09 |
| - | - | cov (us-them) ~avg | +0.21 | -0.09 | +0.05 | +0.20 | +0.21 | +0.17 | +0.13 |
| - | - | drones (us-them) | +0.31 | +0.31 | +0.12 | +0.19 | +0.18 | +0.15 | +0.25 |
| - | - | drones (us-them) ~avg | +0.31 | +0.31 | +0.23 | +0.22 | +0.21 | +0.18 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) | +0.24 | . | +0.09 | +0.09 | +0.17 | +0.23 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.27 | . | +0.09 | +0.09 | +0.17 | +0.25 | +0.25 |
| - | - | soup (us-them) | +0.24 | -0.04 | +0.08 | -0.06 | -0.03 | +0.08 | -0.14 |
| - | - | soup (us-them) ~avg | -0.10 | -0.00 | +0.10 | -0.06 | -0.10 | +0.03 | -0.02 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
