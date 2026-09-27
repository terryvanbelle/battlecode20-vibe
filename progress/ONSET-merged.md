# Which metric starts predicting the result first -- every recorded block of r2s4 merged

229 games, 107 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | mines (us-them) | +0.37 | +0.29 | +0.34 | +0.35 | +0.36 | +0.36 | +0.34 |
| r150 | - | worth (us-them) | +0.41 | +0.28 | +0.37 | +0.40 | +0.41 | +0.37 | +0.32 |
| r150 | - | worth (us-them) ~avg | +0.43 | +0.27 | +0.33 | +0.41 | +0.43 | +0.41 | +0.37 |
| r200 | - | mines (us-them) ~avg | +0.38 | +0.29 | +0.30 | +0.34 | +0.37 | +0.37 | +0.36 |
| r250 | - | robots (us-them) | +0.45 | +0.27 | +0.27 | +0.31 | +0.39 | +0.45 | +0.40 |
| r250 | - | spawned (us-them) | +0.46 | +0.27 | +0.27 | +0.33 | +0.39 | +0.46 | +0.40 |
| r300 | - | spawned (us-them) ~avg | +0.44 | +0.29 | +0.27 | +0.30 | +0.36 | +0.43 | +0.44 |
| r350 | - | robots (us-them) ~avg | +0.43 | +0.29 | +0.27 | +0.30 | +0.35 | +0.42 | +0.43 |
| r400 | - | units (us-them) | +0.43 | +0.25 | +0.20 | +0.19 | +0.30 | +0.40 | +0.38 |
| r450 | - | landscapers (us-them) | +0.41 | +0.09 | +0.13 | +0.11 | +0.22 | +0.36 | +0.39 |
| r550 | - | pickups (us-them) | +0.34 | +0.06 | +0.03 | +0.15 | +0.23 | +0.33 | +0.31 |
| r550 | - | units (us-them) ~avg | +0.40 | +0.27 | +0.22 | +0.21 | +0.25 | +0.35 | +0.40 |
| r650 | - | landscapers (us-them) ~avg | +0.38 | +0.09 | +0.08 | +0.08 | +0.14 | +0.28 | +0.38 |
| r750 | - | digs (us-them) | +0.47 | +0.08 | +0.06 | +0.04 | +0.10 | +0.23 | +0.38 |
| r800 | - | dirtDeps (us-them) | +0.47 | +0.03 | +0.04 | +0.02 | +0.07 | +0.22 | +0.37 |
| r800 | - | pickups (us-them) ~avg | +0.32 | +0.06 | +0.05 | +0.13 | +0.19 | +0.27 | +0.32 |
| r1050 | - | digs (us-them) ~avg | +0.38 | +0.08 | +0.04 | +0.04 | +0.07 | +0.15 | +0.29 |
| r1050 | - | dirtDeps (us-them) ~avg | +0.37 | +0.03 | +0.03 | +0.01 | +0.04 | +0.13 | +0.28 |
| - | - | aba (us-them) [inverted] | +0.19 | +0.16 | +0.08 | +0.09 | +0.14 | +0.07 | -0.06 |
| - | - | aba (us-them) [inverted] ~avg | +0.20 | +0.20 | +0.14 | +0.10 | +0.13 | +0.10 | +0.01 |
| - | - | cov (us-them) | +0.28 | -0.07 | +0.04 | +0.17 | +0.22 | +0.26 | +0.16 |
| - | - | cov (us-them) ~avg | +0.23 | -0.09 | -0.01 | +0.08 | +0.13 | +0.23 | +0.22 |
| - | - | died (us-them) [inverted] | +0.08 | -0.04 | +0.06 | -0.02 | +0.04 | +0.02 | +0.04 |
| - | - | died (us-them) [inverted] ~avg | +0.05 | -0.04 | +0.04 | +0.02 | +0.02 | +0.01 | +0.00 |
| - | - | drones (us-them) | +0.24 | +0.13 | +0.07 | +0.11 | +0.19 | +0.23 | +0.21 |
| - | - | drones (us-them) ~avg | +0.24 | +0.13 | +0.11 | +0.13 | +0.16 | +0.22 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.22 | +0.17 | +0.16 | -0.07 | +0.05 | -0.01 | -0.04 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.17 | +0.10 | -0.04 | -0.03 | -0.06 | -0.07 |
| - | - | miners (us-them) | +0.23 | +0.19 | +0.19 | +0.16 | +0.19 | +0.22 | +0.14 |
| - | - | miners (us-them) ~avg | +0.24 | +0.24 | +0.24 | +0.23 | +0.23 | +0.22 | +0.19 |
| - | - | moves (us-them) | +0.21 | +0.04 | +0.15 | +0.13 | +0.11 | +0.18 | +0.21 |
| - | - | moves (us-them) ~avg | +0.21 | -0.03 | +0.14 | +0.14 | +0.12 | +0.14 | +0.18 |
| - | - | netguns (us-them) | +0.27 | -0.01 | +0.00 | +0.05 | +0.07 | +0.24 | +0.24 |
| - | - | netguns (us-them) ~avg | +0.23 | -0.01 | +0.01 | +0.03 | +0.05 | +0.15 | +0.21 |
| - | - | soup (us-them) | +0.11 | -0.09 | +0.03 | +0.11 | +0.09 | -0.01 | -0.01 |
| - | - | soup (us-them) ~avg | +0.13 | -0.09 | -0.01 | +0.10 | +0.12 | +0.07 | +0.04 |
| - | - | vaporators (us-them) | +0.27 | +0.11 | +0.10 | +0.19 | +0.21 | +0.26 | +0.23 |
| - | - | vaporators (us-them) ~avg | +0.26 | +0.11 | +0.07 | +0.16 | +0.19 | +0.25 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
