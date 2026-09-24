# Which metric starts predicting the result first

18 games, 4 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r400 | aba (us-them) [inverted] | +0.45 | +0.33 | +0.05 | -0.20 | -0.38 | -0.41 | -0.26 |
| r50 | r500 | aba (us-them) [inverted] ~avg | -0.53 | +0.38 | +0.23 | -0.00 | -0.20 | -0.35 | -0.38 |
| r150 | - | mines (us-them) | +0.57 | +0.17 | +0.57 | +0.38 | +0.21 | +0.11 | -0.03 |
| r200 | - | drones (us-them) | +0.40 | +0.08 | +0.37 | +0.22 | +0.03 | -0.26 | -0.07 |
| r200 | - | drones (us-them) ~avg | +0.37 | +0.10 | +0.31 | +0.33 | +0.22 | -0.08 | -0.16 |
| r200 | - | mines (us-them) ~avg | +0.50 | +0.17 | +0.39 | +0.49 | +0.38 | +0.25 | +0.11 |
| r200 | - | pickups (us-them) | +0.41 | . | +0.31 | +0.37 | +0.20 | -0.10 | +0.06 |
| r200 | - | pickups (us-them) ~avg | +0.42 | . | +0.34 | +0.40 | +0.38 | +0.03 | +0.02 |
| r200 | - | units (us-them) | +0.48 | +0.28 | +0.33 | +0.48 | +0.40 | +0.10 | -0.01 |
| r200 | - | worth (us-them) | +0.52 | +0.23 | +0.47 | +0.44 | +0.24 | +0.11 | +0.06 |
| r200 | - | worth (us-them) ~avg | +0.47 | +0.24 | +0.38 | +0.47 | +0.38 | +0.22 | +0.11 |
| r250 | - | miners (us-them) | +0.42 | +0.14 | +0.28 | +0.42 | +0.40 | +0.33 | +0.13 |
| r250 | - | miners (us-them) ~avg | +0.42 | +0.17 | +0.25 | +0.38 | +0.40 | +0.41 | +0.33 |
| r250 | - | robots (us-them) | +0.52 | +0.16 | +0.27 | +0.49 | +0.34 | +0.11 | +0.00 |
| r250 | - | robots (us-them) ~avg | +0.40 | +0.17 | +0.19 | +0.38 | +0.39 | +0.30 | +0.10 |
| r250 | - | spawned (us-them) | +0.50 | +0.16 | +0.25 | +0.47 | +0.32 | +0.14 | +0.07 |
| r250 | - | spawned (us-them) ~avg | +0.37 | +0.17 | +0.18 | +0.36 | +0.37 | +0.30 | +0.15 |
| r250 | - | units (us-them) ~avg | +0.44 | +0.26 | +0.23 | +0.40 | +0.42 | +0.37 | +0.12 |
| r300 | - | landscapers (us-them) | +0.41 | +0.18 | +0.09 | +0.39 | +0.33 | +0.21 | -0.00 |
| r450 | - | landscapers (us-them) ~avg | +0.39 | +0.18 | -0.08 | +0.13 | +0.25 | +0.39 | +0.15 |
| r450 | - | soup (us-them) ~avg | +0.31 | +0.15 | +0.23 | +0.26 | +0.26 | +0.26 | +0.12 |
| r550 | - | dirtDeps (us-them) | +0.38 | -0.01 | -0.06 | +0.05 | +0.17 | +0.36 | +0.35 |
| r600 | - | digs (us-them) | +0.34 | -0.02 | -0.06 | +0.08 | +0.21 | +0.30 | +0.28 |
| r750 | - | dirtDeps (us-them) ~avg | +0.35 | -0.01 | -0.04 | -0.02 | +0.08 | +0.24 | +0.33 |
| r1200 | - | digs (us-them) ~avg | +0.30 | -0.02 | -0.06 | +0.00 | +0.11 | +0.22 | +0.28 |
| - | - | cov (us-them) | +0.17 | -0.00 | +0.06 | +0.14 | +0.08 | +0.10 | -0.13 |
| - | - | cov (us-them) ~avg | +0.14 | -0.05 | +0.06 | +0.12 | +0.12 | +0.13 | +0.02 |
| - | - | died (us-them) [inverted] | +0.25 | . | +0.25 | +0.17 | +0.07 | -0.05 | -0.23 |
| - | - | died (us-them) [inverted] ~avg | +0.25 | . | +0.25 | +0.21 | +0.15 | +0.04 | -0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.19 | +0.19 | +0.14 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.19 | +0.19 | +0.14 | . | . | . | . |
| - | r50 | moves (us-them) | -0.38 | -0.38 | -0.07 | +0.16 | +0.21 | +0.22 | +0.08 |
| - | r50 | moves (us-them) ~avg | -0.43 | -0.43 | -0.14 | +0.07 | +0.15 | +0.21 | +0.17 |
| - | - | netguns (us-them) | +0.28 | . | . | . | +0.28 | +0.22 | +0.12 |
| - | - | netguns (us-them) ~avg | +0.28 | . | . | . | +0.28 | +0.26 | +0.16 |
| - | - | soup (us-them) | +0.27 | +0.17 | +0.17 | +0.22 | +0.16 | -0.19 | -0.01 |
| - | - | vaporators (us-them) | +0.23 | +0.03 | +0.10 | +0.13 | +0.02 | +0.18 | +0.15 |
| - | - | vaporators (us-them) ~avg | +0.25 | +0.03 | +0.13 | +0.18 | +0.12 | +0.13 | +0.18 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
