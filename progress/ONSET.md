# Which metric starts predicting the result first

18 games, 3 wins. Noise floor about 0.47; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | units (us-them) | +0.43 | +0.12 | +0.25 | +0.34 | +0.21 | +0.33 | +0.40 |
| r200 | - | cov (us-them) | +0.52 | +0.41 | +0.30 | +0.44 | +0.48 | +0.51 | +0.47 |
| r250 | - | cov (us-them) ~avg | +0.53 | +0.32 | +0.29 | +0.38 | +0.44 | +0.48 | +0.51 |
| r250 | - | miners (us-them) | +0.35 | +0.00 | +0.19 | +0.31 | +0.32 | +0.28 | +0.24 |
| r250 | - | mines (us-them) | +0.45 | +0.12 | +0.28 | +0.38 | +0.41 | +0.45 | +0.40 |
| r250 | - | soup (us-them) | +0.69 | +0.28 | +0.09 | +0.48 | +0.56 | +0.09 | +0.46 |
| r250 | - | soup (us-them) ~avg | +0.62 | +0.28 | +0.27 | +0.44 | +0.52 | +0.54 | +0.41 |
| r250 | - | worth (us-them) | +0.47 | +0.14 | +0.25 | +0.32 | +0.32 | +0.36 | +0.43 |
| r300 | - | mines (us-them) ~avg | +0.46 | +0.18 | +0.24 | +0.32 | +0.37 | +0.42 | +0.43 |
| r300 | - | pickups (us-them) | +0.43 | . | +0.25 | +0.32 | +0.42 | +0.31 | +0.28 |
| r300 | - | pickups (us-them) ~avg | +0.41 | . | +0.27 | +0.30 | +0.36 | +0.39 | +0.33 |
| r300 | - | robots (us-them) | +0.43 | +0.13 | +0.26 | +0.33 | +0.27 | +0.35 | +0.41 |
| r300 | - | spawned (us-them) | +0.39 | +0.13 | +0.26 | +0.37 | +0.29 | +0.37 | +0.37 |
| r300 | - | spawned (us-them) ~avg | +0.42 | +0.18 | +0.23 | +0.31 | +0.33 | +0.37 | +0.39 |
| r350 | - | robots (us-them) ~avg | +0.45 | +0.18 | +0.23 | +0.29 | +0.31 | +0.36 | +0.41 |
| r350 | - | units (us-them) ~avg | +0.45 | +0.18 | +0.25 | +0.29 | +0.29 | +0.34 | +0.42 |
| r350 | - | worth (us-them) ~avg | +0.44 | +0.17 | +0.22 | +0.29 | +0.32 | +0.35 | +0.41 |
| r500 | - | landscapers (us-them) | +0.54 | +0.08 | +0.16 | +0.20 | +0.14 | +0.39 | +0.51 |
| r500 | - | miners (us-them) ~avg | +0.32 | +0.11 | +0.19 | +0.26 | +0.28 | +0.32 | +0.31 |
| r550 | - | aba (us-them) [inverted] | +0.37 | +0.37 | -0.08 | +0.01 | +0.13 | +0.27 | +0.11 |
| r600 | - | landscapers (us-them) ~avg | +0.55 | +0.08 | +0.16 | +0.15 | +0.18 | +0.30 | +0.49 |
| r650 | r100 | vaporators (us-them) | -0.60 | -0.60 | -0.02 | +0.04 | +0.17 | +0.30 | +0.37 |
| r700 | - | died (us-them) [inverted] | +0.45 | . | +0.16 | -0.09 | -0.03 | +0.07 | +0.36 |
| r750 | - | dirtDeps (us-them) | +0.57 | +0.33 | +0.11 | +0.06 | +0.07 | +0.20 | +0.47 |
| r750 | - | moves (us-them) | +0.37 | +0.16 | +0.21 | +0.23 | +0.25 | +0.26 | +0.32 |
| r750 | - | netguns (us-them) | +0.75 | . | . | . | +0.12 | +0.23 | +0.75 |
| r750 | - | netguns (us-them) ~avg | +0.64 | . | . | . | +0.12 | +0.22 | +0.58 |
| r800 | - | digs (us-them) | +0.51 | +0.33 | +0.11 | +0.08 | +0.09 | +0.16 | +0.42 |
| r850 | r100 | vaporators (us-them) ~avg | -0.60 | -0.60 | -0.28 | -0.09 | +0.04 | +0.19 | +0.31 |
| r950 | - | dirtDeps (us-them) ~avg | +0.47 | +0.33 | +0.12 | +0.08 | +0.07 | +0.13 | +0.28 |
| r950 | - | moves (us-them) ~avg | +0.35 | +0.12 | +0.20 | +0.24 | +0.25 | +0.25 | +0.30 |
| r1000 | - | digs (us-them) ~avg | +0.43 | +0.33 | +0.13 | +0.10 | +0.10 | +0.11 | +0.25 |
| - | - | aba (us-them) [inverted] ~avg | +0.31 | +0.31 | -0.01 | -0.01 | +0.03 | +0.16 | +0.19 |
| - | - | died (us-them) [inverted] ~avg | +0.28 | . | +0.15 | +0.05 | -0.02 | +0.09 | +0.25 |
| - | - | drones (us-them) | +0.29 | +0.28 | +0.24 | +0.23 | -0.09 | -0.03 | +0.21 |
| - | - | drones (us-them) ~avg | +0.28 | +0.28 | +0.27 | +0.27 | +0.13 | +0.02 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.15 | +0.15 | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.15 | +0.15 | . | . | . | . | . |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
