# Which metric starts predicting the result first -- every recorded block of g_iter19 merged

184 games, 77 wins. Noise floor about 0.15; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.37 | +0.26 | +0.26 | +0.30 | +0.29 | +0.34 | +0.35 |
| r300 | - | drones (us-them) | +0.32 | +0.19 | +0.17 | +0.31 | +0.32 | +0.28 | +0.30 |
| r350 | - | mines (us-them) ~avg | +0.36 | +0.28 | +0.28 | +0.29 | +0.29 | +0.32 | +0.34 |
| r550 | - | robots (us-them) | +0.31 | +0.16 | +0.11 | +0.22 | +0.27 | +0.30 | +0.30 |
| r550 | - | spawned (us-them) | +0.31 | +0.16 | +0.13 | +0.25 | +0.29 | +0.30 | +0.30 |
| r550 | - | units (us-them) | +0.33 | +0.11 | +0.06 | +0.18 | +0.25 | +0.30 | +0.32 |
| r550 | - | worth (us-them) | +0.31 | +0.25 | +0.16 | +0.26 | +0.25 | +0.31 | +0.28 |
| r650 | - | digs (us-them) | +0.51 | -0.07 | +0.02 | +0.10 | +0.18 | +0.28 | +0.42 |
| r700 | - | cov (us-them) | +0.36 | -0.02 | +0.17 | +0.24 | +0.27 | +0.28 | +0.34 |
| r800 | - | dirtDeps (us-them) | +0.45 | -0.08 | +0.00 | +0.08 | +0.14 | +0.22 | +0.36 |
| r800 | - | units (us-them) ~avg | +0.32 | +0.09 | +0.05 | +0.13 | +0.18 | +0.27 | +0.31 |
| r850 | - | digs (us-them) ~avg | +0.44 | -0.08 | -0.02 | +0.06 | +0.13 | +0.20 | +0.33 |
| r850 | - | landscapers (us-them) | +0.36 | -0.04 | -0.01 | +0.08 | +0.18 | +0.26 | +0.33 |
| r950 | - | cov (us-them) ~avg | +0.33 | -0.07 | +0.08 | +0.16 | +0.20 | +0.25 | +0.30 |
| r950 | - | drones (us-them) ~avg | +0.31 | +0.19 | +0.15 | +0.26 | +0.28 | +0.28 | +0.30 |
| r950 | - | landscapers (us-them) ~avg | +0.35 | -0.05 | -0.07 | -0.00 | +0.09 | +0.22 | +0.30 |
| r950 | - | robots (us-them) ~avg | +0.30 | +0.14 | +0.10 | +0.17 | +0.22 | +0.28 | +0.30 |
| r950 | - | spawned (us-them) ~avg | +0.31 | +0.15 | +0.12 | +0.19 | +0.24 | +0.29 | +0.30 |
| r1000 | - | dirtDeps (us-them) ~avg | +0.38 | -0.09 | -0.03 | +0.05 | +0.10 | +0.16 | +0.27 |
| - | - | aba (us-them) [inverted] | -0.17 | +0.01 | -0.04 | +0.00 | +0.06 | +0.08 | -0.00 |
| - | - | aba (us-them) [inverted] ~avg | -0.16 | -0.02 | -0.06 | -0.05 | +0.01 | +0.06 | +0.06 |
| - | - | died (us-them) [inverted] | +0.14 | +0.04 | -0.05 | +0.01 | +0.05 | +0.13 | +0.11 |
| - | - | died (us-them) [inverted] ~avg | +0.10 | -0.04 | -0.07 | -0.02 | +0.01 | +0.06 | +0.10 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.24 | +0.03 | +0.19 | +0.04 | +0.10 | +0.06 | +0.06 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.20 | +0.03 | +0.07 | -0.06 | -0.11 | -0.07 | -0.03 |
| - | - | miners (us-them) | +0.20 | +0.09 | +0.03 | +0.06 | +0.08 | +0.14 | +0.17 |
| - | - | miners (us-them) ~avg | +0.17 | +0.08 | +0.08 | +0.08 | +0.06 | +0.11 | +0.14 |
| - | - | moves (us-them) | +0.26 | +0.09 | +0.13 | +0.12 | +0.12 | +0.16 | +0.23 |
| - | - | moves (us-them) ~avg | +0.24 | +0.04 | +0.14 | +0.13 | +0.11 | +0.13 | +0.20 |
| - | - | netguns (us-them) | +0.19 | -0.09 | +0.00 | +0.05 | -0.02 | +0.13 | +0.18 |
| - | - | netguns (us-them) ~avg | +0.13 | -0.09 | -0.05 | -0.00 | -0.01 | +0.04 | +0.11 |
| - | - | pickups (us-them) | +0.29 | +0.05 | -0.00 | +0.15 | +0.18 | +0.26 | +0.25 |
| - | - | pickups (us-them) ~avg | +0.27 | +0.04 | +0.01 | +0.09 | +0.14 | +0.21 | +0.27 |
| - | - | soup (us-them) | -0.24 | -0.04 | -0.07 | +0.03 | -0.03 | +0.15 | +0.02 |
| - | - | soup (us-them) ~avg | +0.19 | -0.02 | -0.01 | +0.02 | +0.01 | +0.15 | +0.16 |
| - | - | vaporators (us-them) | +0.24 | +0.09 | +0.11 | +0.12 | +0.14 | +0.23 | +0.21 |
| - | - | vaporators (us-them) ~avg | +0.21 | +0.08 | +0.11 | +0.09 | +0.11 | +0.18 | +0.21 |
| - | - | worth (us-them) ~avg | +0.29 | +0.23 | +0.18 | +0.23 | +0.24 | +0.28 | +0.29 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
