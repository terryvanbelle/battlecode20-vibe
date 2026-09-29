# Which metric starts predicting the result first -- every recorded block of g_iter15 merged

725 games, 379 wins. Noise floor about 0.07; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | mines (us-them) | +0.36 | +0.24 | +0.30 | +0.33 | +0.36 | +0.31 | +0.29 |
| r200 | - | worth (us-them) | +0.39 | +0.24 | +0.31 | +0.36 | +0.39 | +0.36 | +0.33 |
| r250 | - | mines (us-them) ~avg | +0.36 | +0.24 | +0.29 | +0.31 | +0.35 | +0.35 | +0.31 |
| r250 | - | worth (us-them) ~avg | +0.38 | +0.22 | +0.29 | +0.33 | +0.38 | +0.38 | +0.35 |
| r300 | - | robots (us-them) | +0.41 | +0.22 | +0.26 | +0.33 | +0.35 | +0.40 | +0.39 |
| r350 | - | robots (us-them) ~avg | +0.40 | +0.23 | +0.24 | +0.29 | +0.34 | +0.39 | +0.39 |
| r400 | - | digs (us-them) | +0.62 | +0.16 | +0.14 | +0.24 | +0.32 | +0.45 | +0.56 |
| r400 | - | spawned (us-them) | +0.37 | +0.21 | +0.24 | +0.28 | +0.31 | +0.37 | +0.37 |
| r450 | - | dirtDeps (us-them) | +0.61 | +0.16 | +0.12 | +0.22 | +0.29 | +0.44 | +0.55 |
| r450 | - | landscapers (us-them) | +0.50 | +0.13 | +0.14 | +0.22 | +0.29 | +0.49 | +0.50 |
| r450 | - | spawned (us-them) ~avg | +0.36 | +0.22 | +0.23 | +0.26 | +0.30 | +0.35 | +0.36 |
| r450 | - | units (us-them) | +0.41 | +0.26 | +0.21 | +0.26 | +0.29 | +0.39 | +0.41 |
| r500 | - | digs (us-them) ~avg | +0.57 | +0.15 | +0.12 | +0.20 | +0.27 | +0.37 | +0.50 |
| r500 | - | landscapers (us-them) ~avg | +0.52 | +0.13 | +0.07 | +0.15 | +0.23 | +0.41 | +0.50 |
| r500 | - | units (us-them) ~avg | +0.40 | +0.26 | +0.22 | +0.25 | +0.28 | +0.36 | +0.40 |
| r550 | - | dirtDeps (us-them) ~avg | +0.57 | +0.15 | +0.10 | +0.18 | +0.25 | +0.35 | +0.48 |
| - | - | aba (us-them) [inverted] | -0.27 | +0.07 | +0.04 | +0.04 | +0.02 | -0.04 | -0.21 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | +0.10 | +0.06 | +0.04 | +0.03 | -0.00 | -0.11 |
| - | - | cov (us-them) | +0.19 | +0.04 | +0.13 | +0.15 | +0.17 | +0.18 | +0.17 |
| - | - | cov (us-them) ~avg | +0.17 | +0.02 | +0.09 | +0.13 | +0.14 | +0.17 | +0.17 |
| - | - | died (us-them) [inverted] | +0.22 | +0.09 | +0.11 | +0.19 | +0.20 | +0.21 | +0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.23 | +0.07 | +0.13 | +0.18 | +0.21 | +0.23 | +0.20 |
| - | - | drones (us-them) | +0.26 | +0.19 | +0.14 | +0.16 | +0.17 | +0.14 | +0.25 |
| - | - | drones (us-them) ~avg | +0.22 | +0.19 | +0.20 | +0.19 | +0.19 | +0.16 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.25 | +0.23 | +0.20 | +0.15 | +0.08 | -0.00 | -0.00 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.22 | +0.13 | +0.10 | +0.06 | +0.03 | +0.01 |
| - | - | miners (us-them) | +0.18 | +0.13 | +0.15 | +0.13 | +0.08 | +0.08 | +0.12 |
| - | - | miners (us-them) ~avg | +0.19 | +0.17 | +0.19 | +0.18 | +0.15 | +0.13 | +0.11 |
| - | - | moves (us-them) | +0.29 | +0.10 | +0.14 | +0.12 | +0.13 | +0.20 | +0.28 |
| - | - | moves (us-them) ~avg | +0.27 | +0.07 | +0.16 | +0.14 | +0.13 | +0.16 | +0.23 |
| - | - | netguns (us-them) | +0.28 | . | +0.00 | +0.05 | +0.08 | +0.24 | +0.26 |
| - | - | netguns (us-them) ~avg | +0.24 | . | -0.00 | +0.04 | +0.06 | +0.14 | +0.22 |
| - | - | pickups (us-them) | +0.24 | +0.12 | +0.16 | +0.16 | +0.18 | +0.19 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.25 | +0.12 | +0.18 | +0.18 | +0.19 | +0.20 | +0.22 |
| - | - | soup (us-them) | -0.15 | -0.03 | +0.00 | +0.02 | -0.03 | -0.14 | -0.09 |
| - | - | soup (us-them) ~avg | -0.10 | -0.04 | +0.02 | +0.01 | +0.01 | -0.10 | -0.10 |
| - | - | vaporators (us-them) | +0.29 | +0.03 | +0.11 | +0.15 | +0.24 | +0.28 | +0.23 |
| - | - | vaporators (us-them) ~avg | +0.27 | +0.03 | +0.09 | +0.12 | +0.18 | +0.25 | +0.26 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
