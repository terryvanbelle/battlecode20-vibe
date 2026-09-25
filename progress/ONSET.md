# Which metric starts predicting the result first

45 games, 19 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | r1050 | aba (us-them) [inverted] | +0.36 | +0.35 | +0.04 | -0.14 | -0.08 | -0.09 | -0.24 |
| r100 | - | aba (us-them) [inverted] ~avg | +0.35 | +0.35 | +0.14 | -0.07 | -0.11 | -0.10 | -0.16 |
| r100 | - | drones (us-them) | +0.45 | +0.32 | +0.29 | +0.24 | +0.21 | +0.25 | +0.39 |
| r100 | - | drones (us-them) ~avg | +0.40 | +0.32 | +0.35 | +0.37 | +0.31 | +0.24 | +0.37 |
| r150 | - | pickups (us-them) | +0.50 | +0.18 | +0.26 | +0.34 | +0.39 | +0.33 | +0.45 |
| r150 | - | pickups (us-them) ~avg | +0.47 | +0.18 | +0.30 | +0.33 | +0.40 | +0.39 | +0.44 |
| r300 | - | mines (us-them) | +0.47 | +0.10 | +0.16 | +0.31 | +0.35 | +0.34 | +0.45 |
| r300 | - | robots (us-them) | +0.64 | +0.10 | +0.25 | +0.42 | +0.45 | +0.48 | +0.58 |
| r300 | - | spawned (us-them) | +0.62 | +0.10 | +0.25 | +0.40 | +0.44 | +0.42 | +0.55 |
| r300 | - | units (us-them) | +0.56 | +0.15 | +0.16 | +0.33 | +0.37 | +0.43 | +0.53 |
| r300 | - | worth (us-them) | +0.62 | +0.06 | +0.15 | +0.33 | +0.38 | +0.42 | +0.56 |
| r350 | - | robots (us-them) ~avg | +0.58 | +0.13 | +0.17 | +0.29 | +0.39 | +0.46 | +0.55 |
| r350 | - | spawned (us-them) ~avg | +0.55 | +0.13 | +0.17 | +0.28 | +0.37 | +0.42 | +0.49 |
| r350 | - | worth (us-them) ~avg | +0.54 | +0.04 | +0.13 | +0.25 | +0.33 | +0.39 | +0.48 |
| r400 | - | mines (us-them) ~avg | +0.41 | +0.07 | +0.16 | +0.26 | +0.32 | +0.34 | +0.39 |
| r400 | - | units (us-them) ~avg | +0.53 | +0.15 | +0.13 | +0.21 | +0.30 | +0.38 | +0.51 |
| r450 | - | landscapers (us-them) | +0.60 | +0.00 | +0.08 | +0.23 | +0.30 | +0.39 | +0.60 |
| r500 | - | moves (us-them) | +0.55 | -0.17 | +0.07 | +0.14 | +0.26 | +0.35 | +0.50 |
| r500 | - | vaporators (us-them) | +0.59 | -0.20 | +0.02 | +0.09 | +0.18 | +0.33 | +0.53 |
| r550 | - | netguns (us-them) | +0.40 | . | +0.17 | +0.11 | +0.12 | +0.37 | +0.37 |
| r600 | - | died (us-them) [inverted] | +0.35 | . | -0.04 | +0.14 | +0.19 | +0.32 | +0.20 |
| r600 | - | landscapers (us-them) ~avg | +0.55 | +0.00 | +0.02 | +0.08 | +0.18 | +0.31 | +0.48 |
| r650 | - | moves (us-them) ~avg | +0.50 | -0.17 | +0.00 | +0.11 | +0.20 | +0.30 | +0.43 |
| r700 | - | digs (us-them) | +0.46 | -0.14 | +0.07 | +0.09 | +0.15 | +0.23 | +0.34 |
| r750 | - | cov (us-them) | +0.35 | -0.10 | +0.13 | +0.06 | +0.11 | +0.21 | +0.30 |
| r750 | - | netguns (us-them) ~avg | +0.37 | . | +0.18 | +0.14 | +0.13 | +0.23 | +0.34 |
| r750 | - | vaporators (us-them) ~avg | +0.53 | -0.20 | -0.03 | +0.04 | +0.10 | +0.25 | +0.39 |
| r950 | - | dirtDeps (us-them) | +0.44 | -0.08 | +0.02 | +0.05 | +0.11 | +0.20 | +0.29 |
| r1050 | - | cov (us-them) ~avg | +0.31 | -0.08 | +0.03 | +0.03 | +0.06 | +0.13 | +0.25 |
| r1100 | - | digs (us-them) ~avg | +0.34 | -0.14 | +0.04 | +0.05 | +0.10 | +0.17 | +0.26 |
| r1200 | - | dirtDeps (us-them) ~avg | +0.31 | -0.08 | -0.01 | -0.01 | +0.05 | +0.13 | +0.22 |
| - | - | died (us-them) [inverted] ~avg | +0.27 | . | -0.04 | +0.05 | +0.14 | +0.22 | +0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.21 | +0.21 | +0.13 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.21 | +0.21 | +0.13 | . | . | . | . |
| - | - | miners (us-them) | +0.18 | +0.15 | +0.18 | +0.13 | +0.14 | +0.15 | +0.12 |
| - | - | miners (us-them) ~avg | +0.17 | +0.16 | +0.17 | +0.16 | +0.17 | +0.16 | +0.16 |
| - | - | soup (us-them) | -0.29 | +0.05 | -0.23 | -0.16 | -0.09 | +0.11 | +0.02 |
| - | - | soup (us-them) ~avg | -0.29 | -0.04 | -0.07 | -0.10 | -0.09 | +0.02 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
