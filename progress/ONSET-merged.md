# Which metric starts predicting the result first -- every recorded block of g_iter15 merged

319 games, 167 wins. Noise floor about 0.11; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | units (us-them) | +0.43 | +0.31 | +0.31 | +0.31 | +0.32 | +0.43 | +0.42 |
| r150 | - | worth (us-them) | +0.40 | +0.23 | +0.35 | +0.38 | +0.40 | +0.39 | +0.39 |
| r200 | - | mines (us-them) | +0.37 | +0.24 | +0.32 | +0.34 | +0.37 | +0.34 | +0.34 |
| r200 | - | mines (us-them) ~avg | +0.38 | +0.23 | +0.31 | +0.33 | +0.36 | +0.37 | +0.34 |
| r200 | - | robots (us-them) | +0.44 | +0.26 | +0.33 | +0.37 | +0.38 | +0.43 | +0.42 |
| r200 | - | robots (us-them) ~avg | +0.43 | +0.25 | +0.32 | +0.36 | +0.38 | +0.43 | +0.42 |
| r200 | - | units (us-them) ~avg | +0.42 | +0.29 | +0.32 | +0.33 | +0.33 | +0.40 | +0.42 |
| r200 | - | worth (us-them) ~avg | +0.40 | +0.20 | +0.31 | +0.35 | +0.38 | +0.39 | +0.38 |
| r250 | - | drones (us-them) ~avg | +0.30 | +0.24 | +0.30 | +0.28 | +0.25 | +0.21 | +0.25 |
| r250 | - | spawned (us-them) | +0.40 | +0.26 | +0.29 | +0.31 | +0.34 | +0.40 | +0.40 |
| r250 | - | spawned (us-them) ~avg | +0.39 | +0.25 | +0.29 | +0.32 | +0.33 | +0.38 | +0.38 |
| r300 | - | digs (us-them) | +0.63 | +0.15 | +0.21 | +0.31 | +0.36 | +0.49 | +0.58 |
| r350 | - | digs (us-them) ~avg | +0.58 | +0.15 | +0.19 | +0.28 | +0.33 | +0.41 | +0.52 |
| r350 | - | dirtDeps (us-them) | +0.63 | +0.16 | +0.19 | +0.29 | +0.35 | +0.48 | +0.58 |
| r400 | - | dirtDeps (us-them) ~avg | +0.58 | +0.16 | +0.17 | +0.25 | +0.31 | +0.40 | +0.51 |
| r400 | - | landscapers (us-them) | +0.53 | +0.13 | +0.24 | +0.27 | +0.35 | +0.53 | +0.51 |
| r450 | - | landscapers (us-them) ~avg | +0.53 | +0.13 | +0.17 | +0.23 | +0.30 | +0.46 | +0.52 |
| r550 | - | netguns (us-them) | +0.31 | . | +0.04 | +0.09 | +0.13 | +0.31 | +0.29 |
| r600 | - | vaporators (us-them) | +0.36 | -0.02 | +0.07 | +0.11 | +0.21 | +0.31 | +0.30 |
| r800 | - | moves (us-them) | +0.33 | +0.08 | +0.18 | +0.17 | +0.16 | +0.25 | +0.32 |
| r800 | - | vaporators (us-them) ~avg | +0.32 | -0.03 | +0.03 | +0.06 | +0.13 | +0.24 | +0.31 |
| r1100 | - | drones (us-them) | +0.33 | +0.25 | +0.21 | +0.23 | +0.20 | +0.18 | +0.29 |
| r1150 | - | moves (us-them) ~avg | +0.31 | +0.05 | +0.17 | +0.18 | +0.16 | +0.20 | +0.26 |
| r1150 | - | netguns (us-them) ~avg | +0.30 | . | +0.04 | +0.08 | +0.10 | +0.20 | +0.28 |
| - | - | aba (us-them) [inverted] | -0.24 | +0.11 | +0.09 | +0.08 | +0.06 | -0.04 | -0.21 |
| - | - | aba (us-them) [inverted] ~avg | -0.18 | +0.13 | +0.12 | +0.09 | +0.07 | +0.01 | -0.10 |
| - | - | cov (us-them) | +0.22 | +0.02 | +0.12 | +0.20 | +0.20 | +0.21 | +0.20 |
| - | - | cov (us-them) ~avg | +0.22 | +0.01 | +0.06 | +0.14 | +0.17 | +0.21 | +0.20 |
| - | - | died (us-them) [inverted] | +0.29 | +0.05 | +0.20 | +0.25 | +0.27 | +0.28 | +0.20 |
| - | - | died (us-them) [inverted] ~avg | +0.30 | +0.04 | +0.21 | +0.24 | +0.26 | +0.29 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.27 | +0.23 | +0.17 | +0.10 | +0.08 | +0.08 | +0.09 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.23 | +0.23 | +0.10 | -0.01 | -0.02 | +0.03 | +0.06 |
| - | - | miners (us-them) | +0.19 | +0.16 | +0.19 | +0.15 | +0.08 | +0.09 | +0.12 |
| - | - | miners (us-them) ~avg | +0.23 | +0.17 | +0.22 | +0.22 | +0.17 | +0.15 | +0.11 |
| - | - | pickups (us-them) | +0.25 | +0.13 | +0.23 | +0.19 | +0.21 | +0.21 | +0.24 |
| - | - | pickups (us-them) ~avg | +0.25 | +0.13 | +0.25 | +0.21 | +0.22 | +0.22 | +0.23 |
| - | - | soup (us-them) | -0.16 | -0.09 | -0.03 | +0.00 | -0.03 | -0.16 | -0.04 |
| - | - | soup (us-them) ~avg | -0.14 | -0.09 | -0.06 | -0.05 | -0.04 | -0.13 | -0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
