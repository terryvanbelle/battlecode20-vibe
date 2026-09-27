# Which metric starts predicting the result first

48 games, 22 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | moves (us-them) | +0.45 | +0.45 | +0.25 | +0.35 | +0.35 | +0.30 | +0.34 |
| r100 | - | moves (us-them) ~avg | +0.37 | +0.37 | +0.30 | +0.32 | +0.37 | +0.34 | +0.34 |
| r200 | - | hqBuried (us-them) [inverted] | +0.35 | +0.21 | +0.35 | +0.31 | +0.23 | +0.23 | +0.23 |
| r200 | - | worth (us-them) | +0.35 | +0.07 | +0.34 | +0.20 | +0.31 | +0.32 | +0.35 |
| r250 | - | miners (us-them) | +0.37 | +0.13 | +0.19 | +0.32 | +0.08 | +0.16 | +0.20 |
| r250 | - | miners (us-them) ~avg | +0.32 | +0.16 | +0.20 | +0.32 | +0.27 | +0.20 | +0.19 |
| r250 | - | robots (us-them) | +0.38 | +0.09 | +0.28 | +0.25 | +0.19 | +0.28 | +0.35 |
| r250 | - | worth (us-them) ~avg | +0.36 | +0.05 | +0.27 | +0.29 | +0.34 | +0.34 | +0.35 |
| r500 | - | vaporators (us-them) | +0.35 | +0.14 | +0.13 | +0.23 | +0.28 | +0.35 | +0.32 |
| r600 | - | vaporators (us-them) ~avg | +0.35 | +0.14 | +0.11 | +0.13 | +0.19 | +0.30 | +0.35 |
| r750 | - | robots (us-them) ~avg | +0.32 | +0.11 | +0.17 | +0.25 | +0.29 | +0.28 | +0.32 |
| r750 | - | units (us-them) | +0.34 | +0.17 | +0.25 | +0.21 | +0.16 | +0.23 | +0.32 |
| r850 | - | spawned (us-them) | +0.34 | +0.08 | +0.22 | +0.20 | +0.17 | +0.22 | +0.32 |
| r900 | - | units (us-them) ~avg | +0.30 | +0.18 | +0.18 | +0.23 | +0.26 | +0.24 | +0.30 |
| r1200 | - | landscapers (us-them) | +0.30 | -0.01 | +0.20 | -0.08 | +0.08 | +0.18 | +0.27 |
| r1200 | - | spawned (us-them) ~avg | +0.30 | +0.11 | +0.14 | +0.19 | +0.25 | +0.24 | +0.28 |
| - | - | aba (us-them) [inverted] | +0.21 | +0.03 | +0.19 | +0.12 | +0.14 | +0.19 | +0.03 |
| - | - | aba (us-them) [inverted] ~avg | +0.19 | +0.02 | +0.15 | +0.15 | +0.16 | +0.19 | +0.14 |
| - | - | cov (us-them) | +0.19 | -0.02 | +0.07 | +0.14 | +0.18 | +0.18 | +0.18 |
| - | - | cov (us-them) ~avg | +0.18 | -0.04 | +0.03 | +0.06 | +0.13 | +0.16 | +0.18 |
| - | - | died (us-them) [inverted] | +0.21 | +0.13 | +0.14 | +0.06 | +0.11 | +0.21 | +0.20 |
| - | - | died (us-them) [inverted] ~avg | +0.19 | +0.13 | +0.13 | +0.12 | +0.09 | +0.16 | +0.19 |
| - | - | digs (us-them) | +0.29 | +0.08 | -0.03 | -0.02 | +0.09 | +0.15 | +0.21 |
| - | - | digs (us-them) ~avg | +0.21 | +0.08 | -0.06 | -0.03 | +0.05 | +0.11 | +0.17 |
| - | - | dirtDeps (us-them) | +0.30 | -0.02 | +0.06 | +0.03 | +0.11 | +0.17 | +0.23 |
| - | - | dirtDeps (us-them) ~avg | +0.22 | -0.02 | +0.03 | +0.02 | +0.08 | +0.14 | +0.19 |
| - | - | drones (us-them) | +0.26 | +0.19 | +0.07 | +0.22 | +0.23 | +0.21 | +0.26 |
| - | - | drones (us-them) ~avg | +0.27 | +0.19 | +0.16 | +0.19 | +0.21 | +0.21 | +0.27 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.26 | +0.21 | +0.17 | +0.24 | +0.17 | +0.20 | +0.21 |
| - | - | landscapers (us-them) ~avg | +0.25 | -0.01 | +0.05 | -0.02 | +0.04 | +0.14 | +0.22 |
| - | - | mines (us-them) | +0.21 | +0.08 | +0.20 | +0.14 | +0.20 | +0.13 | +0.18 |
| - | - | mines (us-them) ~avg | +0.21 | +0.12 | +0.17 | +0.16 | +0.21 | +0.20 | +0.19 |
| - | - | netguns (us-them) | -0.24 | . | -0.09 | -0.22 | -0.24 | +0.15 | +0.06 |
| - | - | netguns (us-them) ~avg | -0.21 | . | -0.13 | -0.19 | -0.21 | -0.11 | -0.01 |
| - | - | pickups (us-them) | +0.24 | -0.09 | +0.11 | +0.08 | +0.17 | +0.18 | +0.24 |
| - | - | pickups (us-them) ~avg | +0.22 | -0.09 | +0.12 | +0.09 | +0.14 | +0.16 | +0.20 |
| - | - | soup (us-them) | +0.20 | -0.04 | -0.02 | -0.12 | -0.04 | -0.14 | -0.05 |
| - | - | soup (us-them) ~avg | -0.15 | -0.08 | +0.08 | +0.09 | +0.14 | -0.06 | -0.04 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
