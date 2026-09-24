# Which metric starts predicting the result first

22 games, 5 wins. Noise floor about 0.43; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | soup (us-them) | +0.64 | +0.24 | +0.37 | +0.64 | +0.23 | -0.03 | +0.07 |
| r200 | - | soup (us-them) ~avg | +0.54 | +0.25 | +0.31 | +0.47 | +0.42 | +0.47 | +0.11 |
| r200 | - | worth (us-them) | +0.49 | +0.06 | +0.32 | +0.49 | +0.43 | +0.33 | +0.12 |
| r250 | - | mines (us-them) | +0.44 | +0.01 | +0.26 | +0.44 | +0.40 | +0.34 | +0.25 |
| r250 | - | robots (us-them) | +0.47 | +0.00 | +0.09 | +0.40 | +0.41 | +0.31 | +0.12 |
| r250 | - | spawned (us-them) | +0.46 | +0.00 | +0.10 | +0.40 | +0.43 | +0.32 | +0.14 |
| r250 | - | worth (us-them) ~avg | +0.45 | +0.08 | +0.25 | +0.42 | +0.45 | +0.41 | +0.26 |
| r300 | - | mines (us-them) ~avg | +0.42 | +0.06 | +0.19 | +0.39 | +0.42 | +0.41 | +0.34 |
| r350 | - | landscapers (us-them) | +0.56 | +0.01 | -0.06 | +0.24 | +0.33 | +0.48 | +0.41 |
| r350 | - | robots (us-them) ~avg | +0.40 | +0.01 | +0.07 | +0.25 | +0.35 | +0.39 | +0.27 |
| r350 | - | spawned (us-them) ~avg | +0.41 | +0.01 | +0.07 | +0.25 | +0.35 | +0.40 | +0.29 |
| r350 | - | units (us-them) | +0.46 | +0.12 | +0.01 | +0.30 | +0.34 | +0.32 | +0.15 |
| r450 | - | units (us-them) ~avg | +0.38 | +0.10 | +0.04 | +0.17 | +0.27 | +0.38 | +0.32 |
| r500 | - | landscapers (us-them) ~avg | +0.56 | +0.01 | -0.05 | +0.08 | +0.19 | +0.44 | +0.53 |
| r650 | - | digs (us-them) | +0.38 | +0.21 | -0.11 | -0.08 | +0.05 | +0.28 | +0.35 |
| r700 | - | cov (us-them) | +0.51 | +0.20 | +0.06 | +0.14 | +0.20 | +0.25 | +0.40 |
| r700 | - | dirtDeps (us-them) | +0.37 | +0.21 | -0.09 | -0.08 | +0.03 | +0.26 | +0.33 |
| r900 | - | cov (us-them) ~avg | +0.46 | +0.22 | +0.12 | +0.10 | +0.14 | +0.19 | +0.30 |
| r1050 | - | digs (us-them) ~avg | +0.34 | +0.21 | -0.08 | -0.11 | -0.03 | +0.13 | +0.29 |
| r1200 | - | dirtDeps (us-them) ~avg | +0.31 | +0.21 | -0.07 | -0.11 | -0.04 | +0.12 | +0.26 |
| - | r300 | aba (us-them) [inverted] | -0.32 | +0.18 | +0.20 | -0.32 | -0.24 | -0.25 | -0.22 |
| - | r950 | aba (us-them) [inverted] ~avg | -0.32 | +0.17 | +0.21 | -0.17 | -0.26 | -0.25 | -0.27 |
| - | - | died (us-them) [inverted] | -0.31 | . | -0.07 | +0.10 | +0.08 | +0.00 | -0.09 |
| - | - | died (us-them) [inverted] ~avg | -0.19 | . | -0.10 | +0.08 | +0.11 | +0.07 | -0.04 |
| - | - | drones (us-them) | +0.15 | +0.04 | +0.15 | +0.07 | +0.04 | -0.06 | -0.10 |
| - | - | drones (us-them) ~avg | +0.12 | +0.04 | +0.12 | +0.10 | +0.09 | +0.01 | -0.07 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.12 | +0.12 | . | . | . | . | -0.11 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.13 | +0.12 | +0.12 | +0.11 | +0.11 | +0.11 | +0.10 |
| - | - | miners (us-them) | +0.29 | +0.12 | +0.05 | +0.22 | +0.20 | +0.02 | -0.07 |
| - | - | miners (us-them) ~avg | +0.21 | +0.11 | +0.09 | +0.17 | +0.21 | +0.16 | +0.05 |
| - | - | moves (us-them) | +0.18 | +0.01 | -0.10 | +0.05 | +0.11 | +0.14 | +0.12 |
| - | - | moves (us-them) ~avg | +0.18 | +0.06 | -0.08 | +0.04 | +0.08 | +0.12 | +0.13 |
| - | - | netguns (us-them) | +0.27 | . | . | . | +0.16 | +0.04 | +0.19 |
| - | - | netguns (us-them) ~avg | +0.21 | . | . | . | +0.16 | +0.10 | +0.13 |
| - | - | pickups (us-them) | +0.29 | . | +0.22 | +0.22 | +0.13 | -0.05 | -0.19 |
| - | - | pickups (us-them) ~avg | +0.27 | . | +0.20 | +0.25 | +0.21 | +0.08 | -0.11 |
| - | - | vaporators (us-them) | +0.26 | +0.01 | +0.20 | +0.10 | +0.18 | +0.26 | +0.05 |
| - | - | vaporators (us-them) ~avg | +0.22 | +0.01 | +0.16 | +0.18 | +0.18 | +0.22 | +0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
