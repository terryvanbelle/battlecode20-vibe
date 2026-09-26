# Which metric starts predicting the result first

39 games, 21 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] ~avg | +0.30 | +0.25 | +0.03 | +0.04 | +0.01 | -0.08 | -0.00 |
| r50 | - | units (us-them) | +0.46 | +0.32 | +0.29 | +0.29 | +0.35 | +0.39 | +0.16 |
| r50 | - | units (us-them) ~avg | +0.47 | +0.35 | +0.36 | +0.37 | +0.39 | +0.46 | +0.43 |
| r100 | - | digs (us-them) ~avg | +0.31 | +0.31 | +0.15 | +0.16 | +0.14 | +0.19 | +0.30 |
| r100 | - | hqBuried (us-them) [inverted] | +0.34 | +0.34 | -0.01 | +0.23 | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.34 | +0.34 | -0.08 | -0.01 | -0.20 | -0.20 | -0.19 |
| r100 | - | landscapers (us-them) | +0.44 | +0.44 | +0.34 | +0.18 | +0.23 | +0.36 | +0.13 |
| r100 | - | landscapers (us-them) ~avg | +0.51 | +0.47 | +0.32 | +0.33 | +0.31 | +0.43 | +0.42 |
| r100 | - | robots (us-them) ~avg | +0.49 | +0.32 | +0.40 | +0.44 | +0.46 | +0.49 | +0.40 |
| r100 | - | spawned (us-them) ~avg | +0.52 | +0.32 | +0.40 | +0.44 | +0.47 | +0.52 | +0.43 |
| r150 | - | worth (us-them) | +0.49 | +0.21 | +0.47 | +0.48 | +0.39 | +0.28 | +0.23 |
| r150 | - | worth (us-them) ~avg | +0.50 | +0.19 | +0.46 | +0.50 | +0.48 | +0.42 | +0.32 |
| r200 | - | mines (us-them) | +0.46 | +0.23 | +0.41 | +0.45 | +0.36 | +0.29 | +0.23 |
| r200 | - | mines (us-them) ~avg | +0.47 | +0.22 | +0.39 | +0.47 | +0.46 | +0.42 | +0.36 |
| r200 | - | robots (us-them) | +0.47 | +0.30 | +0.37 | +0.42 | +0.44 | +0.38 | +0.23 |
| r200 | - | spawned (us-them) | +0.49 | +0.30 | +0.36 | +0.42 | +0.47 | +0.40 | +0.26 |
| r350 | - | pickups (us-them) ~avg | +0.32 | -0.13 | +0.26 | +0.24 | +0.31 | +0.27 | +0.26 |
| r800 | - | digs (us-them) | +0.32 | +0.31 | +0.15 | +0.12 | +0.11 | +0.23 | +0.32 |
| r900 | - | dirtDeps (us-them) | +0.30 | +0.29 | +0.12 | +0.10 | +0.10 | +0.21 | +0.30 |
| r950 | - | aba (us-them) [inverted] | +0.33 | +0.22 | -0.02 | -0.02 | -0.03 | -0.16 | +0.14 |
| r1150 | - | pickups (us-them) | +0.31 | -0.13 | +0.21 | +0.17 | +0.20 | +0.24 | +0.29 |
| - | - | cov (us-them) | +0.17 | +0.04 | +0.12 | +0.04 | +0.06 | +0.17 | +0.10 |
| - | - | cov (us-them) ~avg | +0.17 | +0.02 | -0.02 | -0.04 | +0.03 | +0.09 | +0.11 |
| - | - | died (us-them) [inverted] | +0.22 | +0.01 | +0.10 | +0.02 | -0.06 | -0.09 | -0.05 |
| - | - | died (us-them) [inverted] ~avg | -0.15 | +0.01 | +0.12 | +0.04 | +0.02 | -0.06 | -0.09 |
| - | - | dirtDeps (us-them) ~avg | +0.29 | +0.29 | +0.13 | +0.14 | +0.14 | +0.18 | +0.29 |
| - | - | drones (us-them) | +0.28 | +0.01 | +0.17 | +0.28 | +0.03 | +0.04 | -0.02 |
| - | - | drones (us-them) ~avg | +0.18 | +0.01 | +0.16 | +0.18 | +0.15 | +0.14 | +0.09 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.32 | +0.04 | +0.10 | +0.19 | +0.28 | +0.26 | +0.18 |
| - | - | miners (us-them) ~avg | +0.32 | +0.15 | +0.23 | +0.22 | +0.26 | +0.28 | +0.23 |
| - | - | moves (us-them) | -0.35 | -0.35 | +0.01 | -0.01 | +0.07 | +0.17 | +0.11 |
| - | r100 | moves (us-them) ~avg | -0.43 | -0.43 | -0.09 | -0.05 | +0.02 | +0.13 | +0.15 |
| - | - | netguns (us-them) | +0.26 | . | +0.15 | +0.19 | +0.24 | +0.23 | +0.21 |
| - | - | netguns (us-them) ~avg | +0.26 | . | +0.15 | +0.17 | +0.20 | +0.26 | +0.22 |
| - | - | soup (us-them) | -0.32 | -0.32 | -0.13 | +0.23 | +0.14 | +0.00 | +0.08 |
| - | - | soup (us-them) ~avg | -0.34 | -0.34 | -0.19 | -0.10 | +0.00 | -0.04 | +0.02 |
| - | - | vaporators (us-them) | +0.29 | +0.01 | +0.13 | +0.26 | +0.19 | +0.16 | +0.19 |
| - | - | vaporators (us-them) ~avg | +0.27 | +0.01 | +0.13 | +0.20 | +0.21 | +0.20 | +0.19 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
