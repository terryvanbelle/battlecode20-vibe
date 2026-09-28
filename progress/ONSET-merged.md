# Which metric starts predicting the result first -- every recorded block of r4s5 merged

228 games, 107 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.46 | +0.32 | +0.33 | +0.43 | +0.46 | +0.44 | +0.36 |
| r50 | - | mines (us-them) ~avg | +0.47 | +0.34 | +0.31 | +0.38 | +0.44 | +0.47 | +0.42 |
| r100 | - | units (us-them) ~avg | +0.48 | +0.31 | +0.30 | +0.34 | +0.36 | +0.45 | +0.46 |
| r100 | - | worth (us-them) | +0.50 | +0.31 | +0.35 | +0.46 | +0.49 | +0.48 | +0.39 |
| r100 | - | worth (us-them) ~avg | +0.51 | +0.30 | +0.33 | +0.41 | +0.47 | +0.50 | +0.45 |
| r150 | - | moves (us-them) | +0.33 | +0.19 | +0.30 | +0.25 | +0.21 | +0.26 | +0.30 |
| r200 | - | moves (us-them) ~avg | +0.32 | +0.14 | +0.32 | +0.28 | +0.24 | +0.24 | +0.28 |
| r200 | - | robots (us-them) | +0.50 | +0.28 | +0.36 | +0.42 | +0.44 | +0.49 | +0.43 |
| r200 | - | robots (us-them) ~avg | +0.50 | +0.30 | +0.33 | +0.39 | +0.42 | +0.50 | +0.48 |
| r200 | - | spawned (us-them) | +0.46 | +0.27 | +0.32 | +0.37 | +0.39 | +0.46 | +0.42 |
| r200 | - | units (us-them) | +0.46 | +0.29 | +0.31 | +0.35 | +0.35 | +0.45 | +0.41 |
| r250 | - | spawned (us-them) ~avg | +0.46 | +0.29 | +0.29 | +0.34 | +0.38 | +0.45 | +0.44 |
| r350 | - | digs (us-them) | +0.53 | +0.15 | +0.17 | +0.27 | +0.34 | +0.45 | +0.46 |
| r350 | - | landscapers (us-them) | +0.50 | +0.07 | +0.22 | +0.25 | +0.33 | +0.50 | +0.43 |
| r400 | - | dirtDeps (us-them) | +0.53 | +0.14 | +0.11 | +0.24 | +0.32 | +0.43 | +0.45 |
| r450 | - | digs (us-them) ~avg | +0.48 | +0.15 | +0.13 | +0.23 | +0.30 | +0.38 | +0.43 |
| r450 | - | dirtDeps (us-them) ~avg | +0.47 | +0.15 | +0.07 | +0.20 | +0.28 | +0.37 | +0.42 |
| r450 | - | landscapers (us-them) ~avg | +0.51 | +0.07 | +0.11 | +0.22 | +0.28 | +0.44 | +0.48 |
| r550 | - | vaporators (us-them) | +0.32 | -0.02 | +0.04 | +0.14 | +0.24 | +0.31 | +0.25 |
| r950 | - | drones (us-them) | +0.33 | +0.24 | +0.16 | +0.15 | +0.15 | +0.21 | +0.27 |
| r950 | - | vaporators (us-them) ~avg | +0.31 | -0.02 | +0.01 | +0.06 | +0.14 | +0.26 | +0.29 |
| r1100 | - | drones (us-them) ~avg | +0.31 | +0.24 | +0.25 | +0.20 | +0.18 | +0.21 | +0.26 |
| - | - | aba (us-them) [inverted] | +0.16 | +0.16 | +0.07 | +0.06 | +0.09 | +0.01 | -0.11 |
| - | - | aba (us-them) [inverted] ~avg | +0.15 | +0.15 | +0.11 | +0.08 | +0.10 | +0.06 | -0.03 |
| - | - | cov (us-them) | +0.17 | -0.07 | +0.11 | +0.13 | +0.11 | +0.17 | +0.14 |
| - | - | cov (us-them) ~avg | +0.19 | -0.10 | +0.03 | +0.06 | +0.07 | +0.15 | +0.16 |
| - | - | died (us-them) [inverted] | +0.30 | +0.11 | +0.25 | +0.25 | +0.27 | +0.25 | +0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.28 | +0.11 | +0.26 | +0.26 | +0.26 | +0.28 | +0.23 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.16 | +0.16 | +0.16 | +0.11 | +0.01 | -0.03 | -0.06 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | +0.06 | +0.00 | -0.01 | -0.01 | -0.06 |
| - | - | miners (us-them) | +0.26 | +0.22 | +0.26 | +0.22 | +0.11 | +0.14 | +0.13 |
| - | - | miners (us-them) ~avg | +0.30 | +0.27 | +0.28 | +0.27 | +0.23 | +0.19 | +0.16 |
| - | - | netguns (us-them) | +0.25 | -0.07 | -0.13 | -0.12 | -0.06 | +0.18 | +0.23 |
| - | - | netguns (us-them) ~avg | +0.19 | -0.07 | -0.12 | -0.14 | -0.10 | -0.00 | +0.11 |
| - | - | pickups (us-them) | +0.25 | -0.00 | +0.15 | +0.09 | +0.13 | +0.18 | +0.22 |
| - | - | pickups (us-them) ~avg | +0.23 | -0.00 | +0.16 | +0.12 | +0.11 | +0.16 | +0.19 |
| - | - | soup (us-them) | +0.16 | +0.02 | -0.07 | +0.06 | +0.07 | +0.02 | +0.14 |
| - | - | soup (us-them) ~avg | +0.23 | +0.01 | -0.01 | +0.03 | +0.08 | +0.10 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
