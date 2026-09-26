# Which metric starts predicting the result first -- every recorded block of g_iter12 merged

239 games, 127 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.48 | +0.24 | +0.33 | +0.40 | +0.44 | +0.45 | +0.45 |
| r250 | - | mines (us-them) | +0.42 | +0.29 | +0.29 | +0.38 | +0.42 | +0.40 | +0.40 |
| r250 | - | robots (us-them) | +0.57 | +0.16 | +0.19 | +0.34 | +0.45 | +0.49 | +0.54 |
| r250 | - | worth (us-them) ~avg | +0.50 | +0.23 | +0.29 | +0.36 | +0.41 | +0.45 | +0.47 |
| r300 | - | mines (us-them) ~avg | +0.44 | +0.28 | +0.27 | +0.31 | +0.37 | +0.41 | +0.42 |
| r300 | - | spawned (us-them) | +0.57 | +0.15 | +0.14 | +0.33 | +0.42 | +0.49 | +0.54 |
| r350 | - | robots (us-them) ~avg | +0.57 | +0.16 | +0.16 | +0.26 | +0.35 | +0.47 | +0.55 |
| r350 | - | units (us-them) | +0.56 | +0.25 | +0.15 | +0.25 | +0.36 | +0.46 | +0.53 |
| r400 | - | spawned (us-them) ~avg | +0.56 | +0.15 | +0.12 | +0.22 | +0.31 | +0.44 | +0.53 |
| r450 | - | landscapers (us-them) | +0.55 | +0.10 | +0.09 | +0.12 | +0.24 | +0.46 | +0.55 |
| r450 | - | units (us-them) ~avg | +0.56 | +0.23 | +0.16 | +0.22 | +0.28 | +0.42 | +0.54 |
| r500 | - | vaporators (us-them) | +0.38 | +0.15 | +0.11 | +0.19 | +0.26 | +0.33 | +0.35 |
| r600 | - | digs (us-them) | +0.54 | +0.13 | +0.13 | +0.19 | +0.24 | +0.33 | +0.46 |
| r600 | - | landscapers (us-them) ~avg | +0.57 | +0.11 | +0.05 | +0.08 | +0.15 | +0.34 | +0.53 |
| r650 | - | dirtDeps (us-them) | +0.53 | +0.10 | +0.08 | +0.15 | +0.21 | +0.29 | +0.43 |
| r650 | - | vaporators (us-them) ~avg | +0.40 | +0.15 | +0.09 | +0.13 | +0.20 | +0.29 | +0.34 |
| r700 | - | digs (us-them) ~avg | +0.49 | +0.13 | +0.10 | +0.15 | +0.20 | +0.27 | +0.38 |
| r750 | - | moves (us-them) | +0.41 | +0.14 | +0.15 | +0.15 | +0.17 | +0.26 | +0.36 |
| r800 | - | dirtDeps (us-them) ~avg | +0.46 | +0.09 | +0.05 | +0.10 | +0.16 | +0.23 | +0.35 |
| r800 | - | miners (us-them) | +0.32 | +0.17 | +0.12 | +0.22 | +0.24 | +0.27 | +0.32 |
| r900 | - | miners (us-them) ~avg | +0.33 | +0.18 | +0.15 | +0.22 | +0.25 | +0.27 | +0.30 |
| r900 | - | moves (us-them) ~avg | +0.36 | +0.11 | +0.17 | +0.17 | +0.18 | +0.22 | +0.30 |
| r1050 | - | drones (us-them) | +0.34 | +0.13 | +0.09 | +0.17 | +0.20 | +0.14 | +0.25 |
| r1050 | - | netguns (us-them) | +0.35 | +0.07 | -0.10 | -0.08 | +0.01 | +0.21 | +0.29 |
| - | r1150 | aba (us-them) [inverted] | -0.31 | +0.11 | +0.05 | +0.04 | +0.00 | -0.13 | -0.26 |
| - | - | aba (us-them) [inverted] ~avg | -0.23 | +0.11 | +0.07 | +0.06 | +0.05 | -0.05 | -0.17 |
| - | - | cov (us-them) | +0.25 | +0.06 | +0.10 | +0.11 | +0.19 | +0.24 | +0.21 |
| - | - | cov (us-them) ~avg | +0.23 | +0.03 | +0.07 | +0.07 | +0.12 | +0.18 | +0.20 |
| - | - | died (us-them) [inverted] | +0.22 | +0.13 | +0.19 | +0.08 | +0.19 | +0.13 | +0.11 |
| - | - | died (us-them) [inverted] ~avg | +0.20 | +0.10 | +0.19 | +0.16 | +0.18 | +0.16 | +0.16 |
| - | - | drones (us-them) ~avg | +0.29 | +0.12 | +0.12 | +0.16 | +0.18 | +0.17 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.28 | +0.28 | +0.24 | +0.04 | -0.05 | +0.05 | -0.05 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.28 | +0.28 | +0.21 | -0.00 | -0.06 | -0.04 | -0.05 |
| - | - | netguns (us-them) ~avg | +0.25 | +0.07 | -0.06 | -0.10 | -0.06 | +0.05 | +0.18 |
| - | - | pickups (us-them) | +0.25 | +0.04 | +0.12 | +0.16 | +0.20 | +0.17 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.23 | +0.05 | +0.14 | +0.16 | +0.20 | +0.19 | +0.23 |
| - | - | soup (us-them) | -0.26 | +0.07 | +0.14 | +0.04 | -0.12 | -0.14 | -0.26 |
| - | - | soup (us-them) ~avg | +0.18 | +0.12 | +0.18 | +0.16 | +0.08 | -0.04 | -0.16 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
