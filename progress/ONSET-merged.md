# Which metric starts predicting the result first -- every recorded block of r1s17 merged

227 games, 126 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | worth (us-them) | +0.37 | +0.27 | +0.34 | +0.35 | +0.37 | +0.31 | +0.24 |
| r200 | - | mines (us-them) | +0.33 | +0.28 | +0.30 | +0.30 | +0.33 | +0.28 | +0.29 |
| r200 | - | worth (us-them) ~avg | +0.37 | +0.25 | +0.32 | +0.35 | +0.37 | +0.35 | +0.29 |
| r300 | - | mines (us-them) ~avg | +0.33 | +0.28 | +0.29 | +0.31 | +0.33 | +0.32 | +0.30 |
| r350 | - | robots (us-them) | +0.39 | +0.22 | +0.28 | +0.30 | +0.34 | +0.35 | +0.33 |
| r350 | - | spawned (us-them) | +0.37 | +0.21 | +0.28 | +0.29 | +0.31 | +0.34 | +0.33 |
| r450 | - | landscapers (us-them) | +0.47 | +0.09 | +0.16 | +0.17 | +0.24 | +0.38 | +0.47 |
| r450 | - | robots (us-them) ~avg | +0.36 | +0.19 | +0.23 | +0.27 | +0.30 | +0.36 | +0.35 |
| r450 | - | spawned (us-them) ~avg | +0.35 | +0.19 | +0.23 | +0.27 | +0.29 | +0.35 | +0.34 |
| r450 | - | units (us-them) | +0.38 | +0.25 | +0.22 | +0.22 | +0.27 | +0.32 | +0.38 |
| r550 | - | digs (us-them) | +0.58 | +0.12 | +0.10 | +0.16 | +0.21 | +0.37 | +0.49 |
| r550 | - | dirtDeps (us-them) | +0.58 | +0.14 | +0.09 | +0.16 | +0.19 | +0.36 | +0.48 |
| r550 | - | netguns (us-them) | +0.39 | -0.09 | -0.00 | +0.05 | +0.09 | +0.34 | +0.37 |
| r550 | - | units (us-them) ~avg | +0.37 | +0.22 | +0.19 | +0.21 | +0.23 | +0.32 | +0.37 |
| r600 | - | landscapers (us-them) ~avg | +0.47 | +0.10 | +0.08 | +0.12 | +0.16 | +0.32 | +0.43 |
| r700 | - | digs (us-them) ~avg | +0.52 | +0.13 | +0.07 | +0.12 | +0.15 | +0.27 | +0.41 |
| r700 | - | dirtDeps (us-them) ~avg | +0.51 | +0.14 | +0.05 | +0.10 | +0.14 | +0.26 | +0.39 |
| r900 | - | pickups (us-them) | +0.32 | +0.13 | +0.07 | +0.14 | +0.26 | +0.24 | +0.32 |
| r1000 | - | netguns (us-them) ~avg | +0.33 | -0.09 | -0.02 | +0.01 | +0.05 | +0.16 | +0.30 |
| r1100 | - | pickups (us-them) ~avg | +0.31 | +0.13 | +0.09 | +0.15 | +0.20 | +0.26 | +0.28 |
| - | - | aba (us-them) [inverted] | -0.19 | +0.13 | +0.03 | -0.01 | +0.01 | -0.14 | -0.19 |
| - | - | aba (us-them) [inverted] ~avg | -0.18 | +0.14 | +0.05 | -0.01 | +0.01 | -0.07 | -0.16 |
| - | - | cov (us-them) | +0.17 | +0.01 | -0.02 | +0.10 | +0.12 | +0.16 | +0.06 |
| - | - | cov (us-them) ~avg | +0.13 | +0.00 | -0.07 | +0.00 | +0.04 | +0.13 | +0.10 |
| - | - | died (us-them) [inverted] | +0.14 | +0.05 | +0.03 | +0.03 | +0.14 | +0.09 | +0.06 |
| - | - | died (us-them) [inverted] ~avg | +0.10 | +0.02 | +0.01 | +0.02 | +0.07 | +0.10 | +0.08 |
| - | - | drones (us-them) | +0.23 | +0.13 | +0.09 | +0.11 | +0.18 | +0.16 | +0.22 |
| - | - | drones (us-them) ~avg | +0.23 | +0.12 | +0.14 | +0.16 | +0.18 | +0.19 | +0.22 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.28 | +0.26 | +0.16 | +0.17 | +0.17 | +0.10 | +0.14 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.26 | +0.12 | +0.10 | +0.12 | +0.09 | +0.03 |
| - | - | miners (us-them) | +0.20 | +0.20 | +0.17 | +0.15 | +0.09 | +0.04 | +0.07 |
| - | - | miners (us-them) ~avg | +0.19 | +0.17 | +0.18 | +0.19 | +0.17 | +0.12 | +0.07 |
| - | - | moves (us-them) | +0.23 | +0.10 | +0.09 | +0.10 | +0.11 | +0.14 | +0.21 |
| - | - | moves (us-them) ~avg | +0.20 | +0.07 | +0.11 | +0.12 | +0.11 | +0.12 | +0.15 |
| - | - | soup (us-them) | +0.12 | -0.01 | -0.01 | +0.10 | +0.12 | -0.03 | -0.03 |
| - | - | soup (us-them) ~avg | +0.14 | +0.02 | +0.03 | +0.08 | +0.14 | +0.06 | +0.02 |
| - | - | vaporators (us-them) | +0.19 | +0.12 | +0.08 | +0.11 | +0.14 | +0.17 | +0.08 |
| - | - | vaporators (us-them) ~avg | +0.17 | +0.12 | +0.10 | +0.11 | +0.12 | +0.16 | +0.14 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
