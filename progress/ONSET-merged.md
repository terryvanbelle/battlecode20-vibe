# Which metric starts predicting the result first -- every recorded block of cand86s3 merged

221 games, 113 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.31 | +0.31 | +0.22 | -0.03 | +0.04 | -0.00 | +0.11 |
| r200 | - | mines (us-them) | +0.45 | +0.27 | +0.34 | +0.42 | +0.42 | +0.36 | +0.35 |
| r200 | - | mines (us-them) ~avg | +0.44 | +0.25 | +0.31 | +0.38 | +0.43 | +0.42 | +0.39 |
| r200 | - | worth (us-them) | +0.50 | +0.20 | +0.31 | +0.36 | +0.38 | +0.42 | +0.49 |
| r250 | - | spawned (us-them) | +0.49 | +0.18 | +0.20 | +0.34 | +0.34 | +0.37 | +0.45 |
| r300 | - | worth (us-them) ~avg | +0.48 | +0.18 | +0.26 | +0.33 | +0.38 | +0.41 | +0.46 |
| r350 | - | spawned (us-them) ~avg | +0.46 | +0.20 | +0.20 | +0.30 | +0.35 | +0.40 | +0.43 |
| r400 | - | robots (us-them) | +0.50 | +0.18 | +0.19 | +0.28 | +0.30 | +0.37 | +0.47 |
| r400 | - | robots (us-them) ~avg | +0.48 | +0.20 | +0.19 | +0.26 | +0.30 | +0.37 | +0.44 |
| r450 | - | cov (us-them) | +0.31 | -0.03 | +0.19 | +0.27 | +0.29 | +0.25 | +0.22 |
| r500 | - | vaporators (us-them) | +0.43 | +0.08 | +0.08 | +0.20 | +0.22 | +0.36 | +0.43 |
| r550 | - | landscapers (us-them) | +0.43 | +0.10 | +0.11 | +0.13 | +0.15 | +0.29 | +0.43 |
| r600 | - | digs (us-them) | +0.45 | +0.17 | +0.10 | +0.10 | +0.20 | +0.30 | +0.37 |
| r650 | - | dirtDeps (us-them) | +0.44 | +0.14 | +0.07 | +0.10 | +0.19 | +0.30 | +0.36 |
| r650 | - | landscapers (us-them) ~avg | +0.45 | +0.11 | +0.06 | +0.10 | +0.15 | +0.29 | +0.39 |
| r650 | - | vaporators (us-them) ~avg | +0.41 | +0.08 | +0.05 | +0.11 | +0.18 | +0.28 | +0.39 |
| r700 | - | units (us-them) | +0.42 | +0.24 | +0.14 | +0.17 | +0.20 | +0.28 | +0.38 |
| r700 | - | units (us-them) ~avg | +0.40 | +0.24 | +0.17 | +0.18 | +0.20 | +0.28 | +0.37 |
| r850 | - | digs (us-them) ~avg | +0.40 | +0.17 | +0.10 | +0.10 | +0.16 | +0.25 | +0.32 |
| r850 | - | dirtDeps (us-them) ~avg | +0.40 | +0.14 | +0.07 | +0.08 | +0.15 | +0.24 | +0.31 |
| r850 | - | pickups (us-them) | +0.31 | +0.15 | +0.09 | +0.12 | +0.21 | +0.24 | +0.30 |
| r1050 | - | moves (us-them) | +0.34 | +0.03 | +0.08 | +0.07 | +0.06 | +0.14 | +0.29 |
| r1100 | - | pickups (us-them) ~avg | +0.31 | +0.15 | +0.13 | +0.12 | +0.16 | +0.22 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.18 | +0.02 | +0.08 | +0.15 | +0.14 | +0.06 | -0.13 |
| - | - | aba (us-them) [inverted] ~avg | +0.15 | +0.06 | +0.09 | +0.14 | +0.15 | +0.12 | +0.01 |
| - | - | cov (us-them) ~avg | +0.28 | -0.04 | +0.11 | +0.20 | +0.25 | +0.27 | +0.26 |
| - | - | died (us-them) [inverted] | +0.13 | +0.05 | -0.03 | -0.12 | -0.03 | +0.07 | +0.13 |
| - | - | died (us-them) [inverted] ~avg | -0.09 | +0.04 | +0.00 | -0.08 | -0.08 | +0.01 | +0.09 |
| - | - | drones (us-them) | +0.29 | +0.18 | +0.01 | +0.06 | +0.12 | +0.17 | +0.21 |
| - | - | drones (us-them) ~avg | +0.23 | +0.18 | +0.07 | +0.05 | +0.06 | +0.13 | +0.21 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.31 | +0.31 | +0.16 | -0.03 | -0.05 | -0.04 | -0.02 |
| - | - | miners (us-them) | +0.21 | +0.13 | +0.13 | +0.09 | +0.10 | +0.09 | +0.21 |
| - | - | miners (us-them) ~avg | +0.19 | +0.16 | +0.19 | +0.17 | +0.14 | +0.12 | +0.16 |
| - | - | moves (us-them) ~avg | +0.27 | +0.03 | +0.09 | +0.07 | +0.06 | +0.10 | +0.21 |
| - | - | netguns (us-them) | +0.29 | . | -0.02 | -0.02 | +0.01 | +0.24 | +0.28 |
| - | - | netguns (us-them) ~avg | +0.25 | . | -0.01 | -0.03 | -0.02 | +0.10 | +0.22 |
| - | - | soup (us-them) | +0.15 | -0.02 | +0.08 | -0.02 | +0.15 | +0.06 | +0.01 |
| - | - | soup (us-them) ~avg | +0.09 | -0.05 | +0.04 | +0.04 | +0.07 | +0.09 | +0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
