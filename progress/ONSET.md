# Which metric starts predicting the result first

47 games, 4 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r250 | - | drones (us-them) | +0.37 | +0.13 | +0.24 | +0.32 | +0.22 | +0.08 | +0.16 |
| r250 | - | drones (us-them) ~avg | +0.36 | +0.13 | +0.25 | +0.36 | +0.31 | +0.18 | +0.16 |
| r250 | - | worth (us-them) | +0.33 | +0.06 | +0.22 | +0.31 | +0.26 | +0.14 | +0.03 |
| r1000 | - | digs (us-them) | +0.33 | -0.09 | -0.25 | -0.17 | -0.01 | +0.18 | +0.27 |
| r1000 | - | dirtDeps (us-them) | +0.32 | -0.11 | -0.28 | -0.21 | -0.05 | +0.16 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.22 | -0.09 | -0.19 | -0.09 | -0.05 | -0.13 | -0.19 |
| - | - | aba (us-them) [inverted] ~avg | -0.20 | -0.09 | -0.16 | -0.16 | -0.10 | -0.11 | -0.16 |
| - | - | cov (us-them) | -0.24 | -0.11 | +0.04 | +0.06 | +0.14 | +0.18 | +0.10 |
| - | - | cov (us-them) ~avg | -0.24 | -0.16 | -0.04 | -0.01 | +0.05 | +0.13 | +0.13 |
| - | - | died (us-them) [inverted] | +0.16 | . | +0.05 | +0.01 | +0.10 | +0.12 | -0.00 |
| - | - | died (us-them) [inverted] ~avg | +0.10 | . | +0.05 | +0.04 | +0.07 | +0.08 | +0.04 |
| - | - | digs (us-them) ~avg | +0.29 | -0.09 | -0.25 | -0.22 | -0.12 | +0.06 | +0.21 |
| - | - | dirtDeps (us-them) ~avg | +0.27 | -0.11 | -0.26 | -0.26 | -0.16 | +0.03 | +0.18 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.07 | +0.07 | +0.05 | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.07 | +0.07 | +0.05 | . | . | . | . |
| - | - | landscapers (us-them) | +0.30 | -0.12 | -0.07 | +0.09 | +0.22 | +0.27 | +0.26 |
| - | - | landscapers (us-them) ~avg | +0.27 | -0.12 | -0.15 | -0.05 | +0.08 | +0.21 | +0.25 |
| - | - | miners (us-them) | +0.22 | +0.16 | +0.15 | +0.16 | +0.08 | +0.09 | +0.11 |
| - | - | miners (us-them) ~avg | +0.18 | +0.13 | +0.18 | +0.18 | +0.15 | +0.13 | +0.11 |
| - | - | mines (us-them) | +0.28 | +0.17 | +0.23 | +0.28 | +0.25 | +0.13 | +0.13 |
| - | - | mines (us-them) ~avg | +0.27 | +0.14 | +0.21 | +0.26 | +0.26 | +0.22 | +0.18 |
| - | - | moves (us-them) | +0.25 | -0.08 | +0.10 | +0.15 | +0.22 | +0.25 | +0.23 |
| - | - | moves (us-them) ~avg | +0.24 | -0.07 | +0.07 | +0.12 | +0.17 | +0.24 | +0.24 |
| - | - | netguns (us-them) | -0.31 | . | -0.31 | -0.14 | -0.03 | +0.14 | +0.18 |
| - | - | netguns (us-them) ~avg | -0.31 | . | -0.31 | -0.18 | -0.11 | -0.00 | +0.11 |
| - | - | pickups (us-them) | +0.29 | -0.23 | -0.22 | +0.17 | +0.29 | +0.22 | +0.20 |
| - | - | pickups (us-them) ~avg | +0.24 | -0.23 | -0.21 | -0.05 | +0.13 | +0.24 | +0.22 |
| - | - | robots (us-them) | +0.25 | +0.07 | +0.16 | +0.25 | +0.23 | +0.16 | +0.12 |
| - | - | robots (us-them) ~avg | +0.23 | +0.02 | +0.13 | +0.20 | +0.22 | +0.21 | +0.16 |
| - | - | soup (us-them) | +0.35 | -0.04 | +0.11 | +0.21 | +0.23 | +0.13 | +0.18 |
| - | - | soup (us-them) ~avg | +0.32 | +0.09 | +0.15 | +0.27 | +0.24 | +0.21 | +0.21 |
| - | - | spawned (us-them) | +0.25 | +0.07 | +0.15 | +0.25 | +0.22 | +0.15 | +0.11 |
| - | - | spawned (us-them) ~avg | +0.22 | +0.02 | +0.13 | +0.20 | +0.22 | +0.20 | +0.15 |
| - | - | units (us-them) | +0.28 | +0.11 | +0.11 | +0.26 | +0.26 | +0.22 | +0.24 |
| - | - | units (us-them) ~avg | +0.25 | +0.09 | +0.11 | +0.19 | +0.23 | +0.25 | +0.24 |
| - | - | vaporators (us-them) | +0.18 | +0.16 | +0.13 | +0.16 | +0.11 | +0.03 | -0.13 |
| - | - | vaporators (us-them) ~avg | +0.19 | +0.16 | +0.17 | +0.16 | +0.16 | +0.08 | -0.03 |
| - | - | worth (us-them) ~avg | +0.29 | +0.05 | +0.19 | +0.28 | +0.28 | +0.21 | +0.12 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
