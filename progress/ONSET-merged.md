# Which metric starts predicting the result first -- every recorded block of g_iter8 merged

197 games, 102 wins. Noise floor about 0.14; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r150 | - | mines (us-them) | +0.43 | +0.21 | +0.36 | +0.42 | +0.42 | +0.39 | +0.37 |
| r150 | - | worth (us-them) | +0.54 | +0.21 | +0.37 | +0.45 | +0.50 | +0.53 | +0.50 |
| r200 | - | mines (us-them) ~avg | +0.44 | +0.20 | +0.33 | +0.40 | +0.43 | +0.44 | +0.43 |
| r200 | - | robots (us-them) | +0.62 | +0.07 | +0.30 | +0.42 | +0.44 | +0.50 | +0.59 |
| r200 | - | worth (us-them) ~avg | +0.59 | +0.21 | +0.34 | +0.42 | +0.48 | +0.52 | +0.55 |
| r250 | - | spawned (us-them) | +0.58 | +0.07 | +0.30 | +0.42 | +0.43 | +0.47 | +0.56 |
| r300 | - | robots (us-them) ~avg | +0.62 | +0.11 | +0.24 | +0.35 | +0.43 | +0.50 | +0.58 |
| r300 | - | spawned (us-them) ~avg | +0.59 | +0.11 | +0.23 | +0.35 | +0.42 | +0.49 | +0.54 |
| r350 | - | vaporators (us-them) | +0.38 | +0.06 | +0.18 | +0.26 | +0.30 | +0.38 | +0.32 |
| r450 | - | landscapers (us-them) | +0.61 | -0.03 | +0.11 | +0.18 | +0.29 | +0.44 | +0.60 |
| r450 | - | units (us-them) | +0.59 | +0.04 | +0.19 | +0.26 | +0.29 | +0.41 | +0.56 |
| r450 | - | units (us-them) ~avg | +0.57 | +0.10 | +0.15 | +0.22 | +0.27 | +0.39 | +0.50 |
| r450 | - | vaporators (us-them) ~avg | +0.42 | +0.04 | +0.16 | +0.22 | +0.29 | +0.37 | +0.36 |
| r550 | - | landscapers (us-them) ~avg | +0.62 | -0.00 | +0.05 | +0.11 | +0.18 | +0.37 | +0.56 |
| r650 | - | digs (us-them) | +0.57 | -0.02 | -0.07 | +0.02 | +0.09 | +0.29 | +0.51 |
| r650 | - | dirtDeps (us-them) | +0.55 | -0.04 | -0.08 | -0.01 | +0.05 | +0.26 | +0.49 |
| r850 | - | digs (us-them) ~avg | +0.49 | -0.01 | -0.07 | -0.02 | +0.03 | +0.15 | +0.35 |
| r850 | - | miners (us-them) | +0.38 | +0.05 | +0.20 | +0.15 | +0.10 | +0.21 | +0.31 |
| r900 | - | dirtDeps (us-them) ~avg | +0.47 | -0.02 | -0.09 | -0.05 | -0.00 | +0.11 | +0.32 |
| r950 | - | moves (us-them) | +0.36 | -0.20 | -0.09 | +0.03 | +0.08 | +0.17 | +0.27 |
| r1100 | - | moves (us-them) ~avg | +0.32 | -0.18 | -0.14 | -0.03 | +0.03 | +0.12 | +0.22 |
| - | - | aba (us-them) [inverted] | -0.19 | -0.00 | -0.04 | -0.03 | -0.05 | -0.07 | -0.10 |
| - | - | aba (us-them) [inverted] ~avg | -0.12 | +0.01 | -0.03 | -0.04 | -0.04 | -0.07 | -0.07 |
| - | - | cov (us-them) | +0.28 | -0.15 | -0.11 | +0.02 | +0.07 | +0.17 | +0.23 |
| - | - | cov (us-them) ~avg | +0.25 | -0.13 | -0.15 | -0.06 | -0.01 | +0.09 | +0.18 |
| - | - | died (us-them) [inverted] | +0.11 | -0.00 | +0.06 | +0.04 | +0.08 | +0.07 | -0.00 |
| - | - | died (us-them) [inverted] ~avg | +0.08 | -0.00 | +0.07 | +0.07 | +0.07 | +0.04 | -0.01 |
| - | - | drones (us-them) | +0.26 | +0.19 | +0.10 | +0.09 | +0.09 | +0.11 | +0.16 |
| - | - | drones (us-them) ~avg | +0.18 | +0.18 | +0.14 | +0.11 | +0.10 | +0.12 | +0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.15 | +0.15 | +0.10 | +0.07 | +0.10 | +0.00 | +0.08 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.16 | +0.16 | +0.14 | +0.12 | +0.14 | +0.14 | +0.09 |
| - | - | miners (us-them) ~avg | +0.30 | +0.11 | +0.17 | +0.21 | +0.17 | +0.17 | +0.24 |
| - | - | netguns (us-them) | +0.22 | . | +0.11 | +0.10 | +0.13 | +0.12 | +0.14 |
| - | - | netguns (us-them) ~avg | +0.14 | . | +0.11 | +0.11 | +0.12 | +0.12 | +0.12 |
| - | - | pickups (us-them) | +0.28 | +0.12 | +0.14 | +0.18 | +0.21 | +0.27 | +0.28 |
| - | - | pickups (us-them) ~avg | +0.28 | +0.12 | +0.10 | +0.15 | +0.17 | +0.23 | +0.27 |
| - | - | soup (us-them) | +0.14 | +0.14 | +0.00 | -0.03 | +0.10 | +0.10 | +0.03 |
| - | - | soup (us-them) ~avg | +0.17 | +0.14 | +0.12 | +0.05 | +0.05 | +0.09 | +0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
