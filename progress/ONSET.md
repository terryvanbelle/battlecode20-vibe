# Which metric starts predicting the result first

20 games, 8 wins. Noise floor about 0.45; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | vaporators (us-them) | +0.41 | +0.34 | +0.41 | +0.15 | +0.17 | +0.19 | +0.12 |
| r100 | - | vaporators (us-them) ~avg | +0.41 | +0.34 | +0.41 | +0.29 | +0.23 | +0.21 | +0.16 |
| r300 | - | died (us-them) [inverted] | +0.42 | . | -0.37 | +0.42 | +0.30 | +0.30 | +0.29 |
| r300 | - | pickups (us-them) | +0.51 | -0.28 | +0.09 | +0.51 | +0.15 | -0.04 | -0.17 |
| r350 | - | pickups (us-them) ~avg | +0.34 | -0.28 | +0.02 | +0.29 | +0.29 | +0.09 | -0.12 |
| r400 | r200 | died (us-them) [inverted] ~avg | -0.36 | . | -0.36 | +0.21 | +0.31 | +0.33 | +0.35 |
| r750 | - | landscapers (us-them) | +0.36 | -0.25 | -0.06 | +0.16 | +0.12 | +0.21 | +0.31 |
| r950 | - | landscapers (us-them) ~avg | +0.34 | -0.25 | -0.12 | +0.02 | +0.05 | +0.12 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.19 | -0.19 | -0.11 | -0.18 | -0.15 | -0.08 | +0.06 |
| - | - | aba (us-them) [inverted] ~avg | -0.17 | -0.15 | -0.14 | -0.14 | -0.15 | -0.13 | -0.01 |
| - | - | cov (us-them) | -0.19 | -0.15 | -0.01 | -0.11 | -0.17 | -0.02 | -0.00 |
| - | - | cov (us-them) ~avg | -0.22 | -0.13 | -0.13 | -0.12 | -0.15 | -0.13 | -0.06 |
| - | - | digs (us-them) | -0.15 | -0.08 | -0.14 | -0.11 | -0.04 | +0.02 | +0.09 |
| - | - | digs (us-them) ~avg | -0.15 | -0.08 | -0.14 | -0.13 | -0.09 | -0.03 | +0.04 |
| - | - | dirtDeps (us-them) | -0.18 | -0.09 | -0.16 | -0.08 | -0.04 | -0.01 | +0.04 |
| - | - | dirtDeps (us-them) ~avg | -0.17 | -0.09 | -0.17 | -0.12 | -0.08 | -0.04 | +0.00 |
| - | - | drones (us-them) | +0.28 | +0.28 | +0.14 | +0.14 | -0.10 | -0.18 | -0.04 |
| - | - | drones (us-them) ~avg | +0.28 | +0.28 | +0.17 | +0.17 | +0.06 | -0.11 | -0.10 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.28 | -0.28 | . | . | -0.28 | -0.07 | -0.28 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.28 | -0.28 | -0.28 | -0.07 | -0.07 | -0.07 | -0.07 |
| - | - | miners (us-them) | -0.29 | +0.27 | -0.14 | -0.19 | -0.20 | -0.14 | +0.07 |
| - | - | miners (us-them) ~avg | +0.22 | +0.22 | +0.08 | -0.10 | -0.15 | -0.17 | -0.06 |
| - | - | mines (us-them) | -0.24 | +0.21 | +0.19 | -0.02 | -0.14 | -0.19 | -0.16 |
| - | - | mines (us-them) ~avg | -0.22 | +0.17 | +0.19 | +0.13 | +0.00 | -0.13 | -0.16 |
| - | - | moves (us-them) | -0.14 | -0.07 | -0.13 | -0.09 | -0.02 | -0.06 | +0.03 |
| - | - | moves (us-them) ~avg | -0.12 | -0.09 | -0.10 | -0.11 | -0.07 | -0.06 | -0.02 |
| - | - | netguns (us-them) | +0.19 | . | . | . | . | +0.00 | +0.04 |
| - | - | netguns (us-them) ~avg | +0.19 | . | . | . | . | +0.03 | +0.05 |
| - | - | robots (us-them) | +0.19 | +0.15 | +0.01 | +0.02 | -0.03 | -0.01 | +0.14 |
| - | - | robots (us-them) ~avg | +0.17 | +0.11 | +0.11 | +0.07 | +0.02 | -0.02 | +0.08 |
| - | r950 | soup (us-them) | -0.35 | -0.04 | +0.02 | +0.30 | -0.18 | -0.21 | -0.29 |
| - | r1150 | soup (us-them) ~avg | -0.31 | +0.02 | -0.06 | +0.08 | -0.05 | -0.17 | -0.23 |
| - | - | spawned (us-them) | +0.22 | +0.15 | +0.07 | -0.12 | -0.11 | -0.09 | +0.09 |
| - | - | spawned (us-them) ~avg | +0.18 | +0.11 | +0.14 | +0.04 | -0.04 | -0.10 | -0.00 |
| - | - | units (us-them) | +0.21 | +0.21 | -0.12 | +0.01 | -0.13 | -0.08 | +0.12 |
| - | - | units (us-them) ~avg | +0.16 | +0.16 | +0.02 | -0.00 | -0.06 | -0.12 | +0.02 |
| - | - | worth (us-them) | +0.27 | +0.08 | +0.23 | +0.18 | +0.05 | +0.04 | +0.10 |
| - | - | worth (us-them) ~avg | +0.23 | +0.10 | +0.20 | +0.22 | +0.15 | +0.08 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
