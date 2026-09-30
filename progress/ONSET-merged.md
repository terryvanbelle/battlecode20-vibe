# Which metric starts predicting the result first -- every recorded block of g_iter19 merged

326 games, 146 wins. Noise floor about 0.11; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r250 | - | mines (us-them) ~avg | +0.31 | +0.28 | +0.29 | +0.31 | +0.29 | +0.29 | +0.29 |
| r600 | - | digs (us-them) | +0.47 | +0.05 | +0.01 | +0.12 | +0.20 | +0.31 | +0.40 |
| r650 | - | pickups (us-them) | +0.31 | +0.08 | -0.01 | +0.16 | +0.20 | +0.29 | +0.28 |
| r750 | - | dirtDeps (us-them) | +0.43 | +0.03 | -0.02 | +0.11 | +0.18 | +0.26 | +0.36 |
| r750 | - | units (us-them) | +0.31 | +0.11 | +0.06 | +0.18 | +0.24 | +0.26 | +0.31 |
| r800 | - | digs (us-them) ~avg | +0.42 | +0.05 | -0.03 | +0.07 | +0.15 | +0.23 | +0.33 |
| r900 | - | landscapers (us-them) | +0.35 | -0.01 | -0.02 | +0.09 | +0.20 | +0.27 | +0.32 |
| r900 | - | landscapers (us-them) ~avg | +0.34 | -0.02 | -0.09 | +0.01 | +0.11 | +0.24 | +0.30 |
| r950 | - | dirtDeps (us-them) ~avg | +0.38 | +0.02 | -0.06 | +0.05 | +0.13 | +0.19 | +0.29 |
| r1050 | - | units (us-them) ~avg | +0.30 | +0.08 | +0.06 | +0.13 | +0.18 | +0.24 | +0.30 |
| - | - | aba (us-them) [inverted] | -0.15 | +0.02 | -0.04 | +0.00 | +0.01 | -0.03 | -0.10 |
| - | - | aba (us-them) [inverted] ~avg | -0.10 | +0.00 | -0.05 | -0.04 | -0.01 | -0.02 | -0.05 |
| - | - | cov (us-them) | +0.22 | -0.05 | +0.18 | +0.22 | +0.19 | +0.16 | +0.19 |
| - | - | cov (us-them) ~avg | +0.18 | -0.07 | +0.07 | +0.15 | +0.15 | +0.18 | +0.18 |
| - | - | died (us-them) [inverted] | +0.14 | +0.00 | -0.03 | +0.04 | +0.09 | +0.14 | +0.12 |
| - | - | died (us-them) [inverted] ~avg | +0.12 | -0.07 | -0.04 | -0.00 | +0.03 | +0.09 | +0.12 |
| - | - | drones (us-them) | +0.29 | +0.26 | +0.22 | +0.29 | +0.28 | +0.23 | +0.29 |
| - | - | drones (us-them) ~avg | +0.29 | +0.26 | +0.23 | +0.28 | +0.27 | +0.24 | +0.28 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.23 | +0.16 | +0.22 | +0.13 | +0.00 | -0.03 | -0.03 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.16 | +0.12 | +0.04 | -0.06 | -0.05 | -0.04 |
| - | - | miners (us-them) | +0.19 | +0.04 | +0.03 | +0.04 | +0.05 | +0.08 | +0.16 |
| - | - | miners (us-them) ~avg | +0.15 | +0.03 | +0.08 | +0.07 | +0.04 | +0.07 | +0.11 |
| - | - | mines (us-them) | +0.30 | +0.26 | +0.29 | +0.29 | +0.26 | +0.28 | +0.27 |
| - | - | moves (us-them) | +0.27 | +0.04 | +0.15 | +0.14 | +0.12 | +0.16 | +0.25 |
| - | - | moves (us-them) ~avg | +0.25 | +0.03 | +0.16 | +0.15 | +0.12 | +0.14 | +0.21 |
| - | - | netguns (us-them) | +0.22 | -0.06 | +0.03 | +0.04 | +0.02 | +0.19 | +0.22 |
| - | - | netguns (us-them) ~avg | +0.18 | -0.06 | -0.00 | +0.02 | +0.02 | +0.10 | +0.17 |
| - | - | pickups (us-them) ~avg | +0.30 | +0.07 | +0.02 | +0.10 | +0.16 | +0.23 | +0.30 |
| - | - | robots (us-them) | +0.29 | +0.13 | +0.12 | +0.23 | +0.25 | +0.27 | +0.29 |
| - | - | robots (us-them) ~avg | +0.28 | +0.12 | +0.11 | +0.19 | +0.21 | +0.26 | +0.28 |
| - | - | soup (us-them) | -0.14 | -0.03 | -0.01 | -0.03 | -0.02 | +0.02 | -0.03 |
| - | - | soup (us-them) ~avg | +0.08 | +0.01 | +0.04 | +0.02 | +0.02 | +0.07 | +0.02 |
| - | - | spawned (us-them) | +0.28 | +0.13 | +0.14 | +0.24 | +0.25 | +0.26 | +0.28 |
| - | - | spawned (us-them) ~avg | +0.28 | +0.12 | +0.13 | +0.21 | +0.23 | +0.26 | +0.28 |
| - | - | vaporators (us-them) | +0.25 | +0.09 | +0.12 | +0.15 | +0.14 | +0.22 | +0.20 |
| - | - | vaporators (us-them) ~avg | +0.21 | +0.08 | +0.12 | +0.13 | +0.12 | +0.19 | +0.21 |
| - | - | worth (us-them) | +0.28 | +0.23 | +0.21 | +0.26 | +0.24 | +0.28 | +0.27 |
| - | - | worth (us-them) ~avg | +0.28 | +0.22 | +0.22 | +0.26 | +0.25 | +0.27 | +0.28 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
