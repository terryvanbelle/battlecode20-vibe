# Which metric starts predicting the result first

24 games, 11 wins. Noise floor about 0.41; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | miners (us-them) | +0.53 | +0.37 | +0.50 | +0.31 | +0.26 | +0.33 | +0.33 |
| r100 | - | miners (us-them) ~avg | +0.61 | +0.44 | +0.55 | +0.55 | +0.43 | +0.36 | +0.36 |
| r100 | - | moves (us-them) | +0.55 | +0.37 | +0.55 | +0.45 | +0.38 | +0.31 | +0.27 |
| r100 | - | units (us-them) | +0.56 | +0.37 | +0.36 | +0.37 | +0.41 | +0.30 | +0.45 |
| r100 | - | units (us-them) ~avg | +0.51 | +0.41 | +0.36 | +0.47 | +0.51 | +0.44 | +0.45 |
| r150 | - | cov (us-them) | +0.51 | +0.27 | +0.51 | +0.29 | +0.23 | +0.26 | +0.12 |
| r150 | - | moves (us-them) ~avg | +0.55 | +0.24 | +0.54 | +0.52 | +0.45 | +0.37 | +0.32 |
| r200 | - | cov (us-them) ~avg | +0.46 | +0.20 | +0.42 | +0.42 | +0.35 | +0.33 | +0.23 |
| r250 | - | mines (us-them) | +0.41 | +0.03 | +0.20 | +0.36 | +0.36 | +0.30 | +0.21 |
| r250 | - | robots (us-them) | +0.57 | +0.31 | +0.24 | +0.34 | +0.38 | +0.33 | +0.50 |
| r250 | - | robots (us-them) ~avg | +0.53 | +0.31 | +0.25 | +0.43 | +0.49 | +0.45 | +0.49 |
| r250 | - | spawned (us-them) | +0.51 | +0.31 | +0.24 | +0.43 | +0.41 | +0.26 | +0.33 |
| r250 | - | spawned (us-them) ~avg | +0.51 | +0.31 | +0.25 | +0.44 | +0.51 | +0.41 | +0.36 |
| r350 | - | digs (us-them) | +0.51 | -0.33 | +0.04 | +0.24 | +0.37 | +0.44 | +0.50 |
| r350 | - | dirtDeps (us-them) | +0.49 | -0.26 | +0.04 | +0.24 | +0.37 | +0.43 | +0.48 |
| r350 | - | mines (us-them) ~avg | +0.43 | +0.06 | +0.12 | +0.28 | +0.41 | +0.40 | +0.29 |
| r450 | - | digs (us-them) ~avg | +0.49 | -0.33 | -0.00 | +0.15 | +0.27 | +0.39 | +0.47 |
| r450 | - | dirtDeps (us-them) ~avg | +0.48 | -0.26 | +0.01 | +0.15 | +0.28 | +0.38 | +0.45 |
| r450 | - | pickups (us-them) | +0.39 | -0.03 | +0.25 | +0.28 | +0.27 | +0.38 | +0.33 |
| r500 | - | pickups (us-them) ~avg | +0.42 | -0.03 | +0.23 | +0.25 | +0.26 | +0.36 | +0.38 |
| r700 | - | landscapers (us-them) | +0.51 | +0.25 | +0.16 | +0.23 | +0.27 | +0.23 | +0.42 |
| r700 | - | worth (us-them) | +0.32 | +0.16 | -0.02 | -0.10 | -0.05 | +0.17 | +0.30 |
| r750 | - | landscapers (us-them) ~avg | +0.41 | +0.25 | +0.13 | +0.21 | +0.25 | +0.26 | +0.37 |
| - | - | aba (us-them) [inverted] | +0.23 | -0.20 | -0.02 | +0.12 | +0.11 | +0.22 | +0.21 |
| - | - | aba (us-them) [inverted] ~avg | +0.25 | -0.19 | -0.06 | +0.07 | +0.09 | +0.17 | +0.20 |
| - | r300 | died (us-them) [inverted] | -0.37 | . | . | -0.37 | -0.26 | +0.18 | +0.19 |
| - | r300 | died (us-them) [inverted] ~avg | -0.35 | . | . | -0.35 | -0.32 | +0.02 | +0.17 |
| - | - | drones (us-them) | +0.23 | +0.06 | -0.09 | -0.14 | +0.09 | +0.05 | +0.14 |
| - | - | drones (us-them) ~avg | +0.17 | +0.06 | +0.05 | -0.07 | +0.01 | +0.08 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.25 | +0.25 | . | +0.20 | . | +0.20 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.25 | +0.25 | +0.20 | +0.20 | +0.20 | +0.20 | +0.20 |
| - | - | netguns (us-them) | -0.11 | . | . | -0.07 | +0.07 | +0.04 | +0.04 |
| - | - | netguns (us-them) ~avg | -0.11 | . | . | -0.09 | -0.02 | +0.03 | +0.00 |
| - | r900 | soup (us-them) | -0.39 | +0.06 | +0.15 | -0.22 | -0.39 | -0.14 | -0.34 |
| - | - | soup (us-them) ~avg | -0.29 | +0.06 | +0.25 | +0.11 | -0.10 | -0.20 | -0.27 |
| - | r100 | vaporators (us-them) | -0.56 | -0.56 | -0.33 | -0.35 | -0.36 | -0.03 | +0.18 |
| - | r100 | vaporators (us-them) ~avg | -0.56 | -0.56 | -0.42 | -0.38 | -0.38 | -0.31 | -0.10 |
| - | - | worth (us-them) ~avg | +0.30 | +0.15 | +0.01 | -0.04 | -0.05 | +0.08 | +0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
