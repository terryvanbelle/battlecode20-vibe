# Which metric starts predicting the result first

36 games, 15 wins. Noise floor about 0.33; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | digs (us-them) ~avg | +0.43 | +0.33 | +0.12 | +0.07 | +0.17 | +0.29 | +0.42 |
| r200 | - | pickups (us-them) | +0.60 | +0.14 | +0.31 | +0.50 | +0.60 | +0.52 | +0.38 |
| r200 | - | pickups (us-them) ~avg | +0.58 | +0.14 | +0.33 | +0.44 | +0.55 | +0.58 | +0.50 |
| r200 | - | soup (us-them) ~avg | +0.42 | +0.09 | +0.42 | +0.34 | +0.28 | +0.16 | +0.01 |
| r300 | - | drones (us-them) | +0.42 | +0.14 | +0.07 | +0.32 | +0.40 | +0.41 | +0.38 |
| r300 | - | miners (us-them) | +0.39 | +0.09 | +0.13 | +0.34 | +0.37 | +0.39 | +0.31 |
| r300 | - | robots (us-them) | +0.47 | +0.21 | +0.14 | +0.37 | +0.45 | +0.43 | +0.39 |
| r300 | - | spawned (us-them) | +0.41 | +0.22 | +0.15 | +0.30 | +0.39 | +0.39 | +0.38 |
| r300 | - | units (us-them) | +0.49 | +0.22 | +0.12 | +0.35 | +0.46 | +0.44 | +0.38 |
| r300 | - | worth (us-them) | +0.39 | +0.19 | +0.25 | +0.34 | +0.36 | +0.39 | +0.37 |
| r350 | - | died (us-them) [inverted] | +0.51 | -0.20 | -0.16 | +0.24 | +0.48 | +0.48 | +0.26 |
| r350 | - | drones (us-them) ~avg | +0.40 | +0.14 | +0.10 | +0.23 | +0.35 | +0.39 | +0.40 |
| r350 | - | mines (us-them) | +0.45 | +0.20 | +0.28 | +0.30 | +0.34 | +0.43 | +0.44 |
| r350 | - | robots (us-them) ~avg | +0.43 | +0.22 | +0.18 | +0.26 | +0.35 | +0.42 | +0.41 |
| r350 | - | worth (us-them) ~avg | +0.39 | +0.19 | +0.23 | +0.29 | +0.33 | +0.37 | +0.38 |
| r400 | - | landscapers (us-them) | +0.46 | +0.19 | +0.03 | +0.18 | +0.35 | +0.36 | +0.30 |
| r400 | - | mines (us-them) ~avg | +0.45 | +0.23 | +0.25 | +0.28 | +0.31 | +0.37 | +0.43 |
| r400 | - | spawned (us-them) ~avg | +0.39 | +0.22 | +0.19 | +0.23 | +0.30 | +0.37 | +0.39 |
| r400 | - | units (us-them) ~avg | +0.43 | +0.21 | +0.17 | +0.23 | +0.33 | +0.42 | +0.42 |
| r450 | - | died (us-them) [inverted] ~avg | +0.49 | -0.20 | -0.15 | +0.08 | +0.30 | +0.47 | +0.40 |
| r450 | - | digs (us-them) | +0.45 | +0.33 | +0.04 | +0.09 | +0.26 | +0.40 | +0.44 |
| r450 | - | dirtDeps (us-them) | +0.42 | +0.27 | -0.04 | +0.02 | +0.21 | +0.38 | +0.41 |
| r450 | - | miners (us-them) ~avg | +0.36 | +0.11 | +0.13 | +0.21 | +0.28 | +0.36 | +0.34 |
| r500 | - | landscapers (us-them) ~avg | +0.40 | +0.19 | +0.10 | +0.11 | +0.20 | +0.34 | +0.37 |
| r650 | - | netguns (us-them) | +0.35 | . | . | -0.06 | +0.13 | +0.29 | +0.35 |
| r650 | - | vaporators (us-them) | +0.38 | -0.23 | +0.12 | +0.19 | +0.22 | +0.30 | +0.36 |
| r700 | - | cov (us-them) | +0.42 | -0.15 | -0.17 | +0.02 | +0.16 | +0.24 | +0.40 |
| r700 | - | dirtDeps (us-them) ~avg | +0.40 | +0.27 | -0.00 | -0.01 | +0.10 | +0.24 | +0.40 |
| r750 | - | moves (us-them) | +0.31 | -0.23 | -0.15 | -0.06 | +0.03 | +0.22 | +0.30 |
| r850 | - | netguns (us-them) ~avg | +0.33 | . | . | -0.05 | +0.06 | +0.22 | +0.31 |
| r850 | - | vaporators (us-them) ~avg | +0.36 | -0.23 | +0.01 | +0.13 | +0.18 | +0.25 | +0.31 |
| r950 | - | cov (us-them) ~avg | +0.36 | -0.16 | -0.20 | -0.10 | +0.00 | +0.11 | +0.30 |
| - | - | aba (us-them) [inverted] | +0.14 | +0.14 | +0.10 | -0.02 | -0.06 | -0.10 | -0.08 |
| - | - | aba (us-them) [inverted] ~avg | +0.12 | +0.12 | +0.08 | +0.03 | -0.01 | -0.07 | -0.10 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.15 | +0.14 | +0.15 | +0.15 | . | +0.15 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.15 | +0.14 | +0.15 | +0.15 | +0.15 | +0.15 | . |
| - | - | moves (us-them) ~avg | +0.29 | -0.26 | -0.17 | -0.11 | -0.05 | +0.10 | +0.28 |
| - | - | soup (us-them) | +0.44 | +0.17 | +0.44 | +0.25 | +0.04 | -0.01 | -0.17 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
