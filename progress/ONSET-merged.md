# Which metric starts predicting the result first -- every recorded block of r4s4 merged

229 games, 113 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | robots (us-them) | +0.39 | +0.29 | +0.35 | +0.34 | +0.34 | +0.35 | +0.38 |
| r200 | - | robots (us-them) ~avg | +0.42 | +0.28 | +0.33 | +0.35 | +0.36 | +0.38 | +0.41 |
| r200 | - | worth (us-them) | +0.39 | +0.23 | +0.32 | +0.36 | +0.39 | +0.37 | +0.34 |
| r250 | - | landscapers (us-them) | +0.40 | +0.17 | +0.24 | +0.31 | +0.33 | +0.34 | +0.40 |
| r250 | - | spawned (us-them) | +0.40 | +0.28 | +0.30 | +0.30 | +0.32 | +0.34 | +0.38 |
| r250 | - | spawned (us-them) ~avg | +0.41 | +0.27 | +0.29 | +0.32 | +0.33 | +0.35 | +0.40 |
| r250 | - | units (us-them) | +0.40 | +0.29 | +0.30 | +0.31 | +0.32 | +0.34 | +0.39 |
| r250 | - | units (us-them) ~avg | +0.43 | +0.27 | +0.28 | +0.31 | +0.33 | +0.35 | +0.42 |
| r300 | - | mines (us-them) | +0.41 | +0.21 | +0.23 | +0.36 | +0.41 | +0.38 | +0.37 |
| r300 | - | mines (us-them) ~avg | +0.43 | +0.22 | +0.21 | +0.35 | +0.40 | +0.41 | +0.42 |
| r300 | - | worth (us-them) ~avg | +0.41 | +0.22 | +0.28 | +0.35 | +0.39 | +0.40 | +0.38 |
| r400 | - | digs (us-them) | +0.50 | +0.23 | +0.21 | +0.26 | +0.32 | +0.38 | +0.44 |
| r400 | - | dirtDeps (us-them) | +0.50 | +0.18 | +0.20 | +0.24 | +0.31 | +0.37 | +0.43 |
| r400 | - | landscapers (us-them) ~avg | +0.44 | +0.18 | +0.18 | +0.25 | +0.30 | +0.37 | +0.43 |
| r500 | - | digs (us-them) ~avg | +0.46 | +0.23 | +0.20 | +0.23 | +0.27 | +0.34 | +0.41 |
| r550 | - | dirtDeps (us-them) ~avg | +0.46 | +0.18 | +0.17 | +0.20 | +0.25 | +0.33 | +0.40 |
| r1000 | - | drones (us-them) | +0.31 | +0.22 | +0.15 | +0.10 | +0.20 | +0.22 | +0.28 |
| - | - | aba (us-them) [inverted] | +0.25 | -0.04 | +0.08 | +0.17 | +0.23 | +0.22 | -0.01 |
| - | - | aba (us-them) [inverted] ~avg | +0.26 | -0.02 | +0.05 | +0.13 | +0.21 | +0.25 | +0.17 |
| - | - | cov (us-them) | +0.26 | -0.04 | +0.13 | +0.23 | +0.22 | +0.22 | +0.18 |
| - | - | cov (us-them) ~avg | +0.25 | -0.07 | +0.03 | +0.11 | +0.17 | +0.24 | +0.23 |
| - | - | died (us-them) [inverted] | +0.25 | +0.16 | +0.21 | +0.16 | +0.15 | +0.12 | +0.05 |
| - | - | died (us-them) [inverted] ~avg | +0.26 | +0.14 | +0.23 | +0.20 | +0.18 | +0.15 | +0.09 |
| - | - | drones (us-them) ~avg | +0.29 | +0.22 | +0.22 | +0.17 | +0.17 | +0.20 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.24 | +0.24 | +0.22 | +0.21 | +0.11 | +0.11 | +0.06 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.24 | +0.24 | +0.13 | +0.11 | -0.01 | +0.02 | +0.02 |
| - | - | miners (us-them) | +0.17 | +0.16 | +0.17 | +0.16 | +0.11 | +0.12 | +0.07 |
| - | - | miners (us-them) ~avg | +0.21 | +0.19 | +0.19 | +0.21 | +0.20 | +0.17 | +0.16 |
| - | - | moves (us-them) | +0.25 | +0.06 | +0.15 | +0.14 | +0.13 | +0.15 | +0.24 |
| - | - | moves (us-them) ~avg | +0.23 | +0.01 | +0.15 | +0.14 | +0.14 | +0.14 | +0.20 |
| - | - | netguns (us-them) | +0.24 | -0.07 | +0.03 | +0.07 | +0.05 | +0.18 | +0.24 |
| - | - | netguns (us-them) ~avg | +0.23 | -0.07 | -0.00 | +0.06 | +0.06 | +0.10 | +0.20 |
| - | - | pickups (us-them) | +0.27 | +0.15 | +0.18 | +0.20 | +0.21 | +0.21 | +0.24 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.15 | +0.20 | +0.20 | +0.23 | +0.23 | +0.26 |
| - | - | soup (us-them) | +0.21 | -0.15 | -0.14 | -0.01 | +0.15 | +0.06 | -0.01 |
| - | - | soup (us-them) ~avg | -0.18 | -0.14 | -0.16 | -0.11 | +0.06 | +0.11 | +0.10 |
| - | - | vaporators (us-them) | +0.27 | -0.03 | +0.03 | +0.08 | +0.14 | +0.27 | +0.20 |
| - | - | vaporators (us-them) ~avg | +0.27 | -0.03 | -0.00 | +0.04 | +0.09 | +0.20 | +0.23 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
