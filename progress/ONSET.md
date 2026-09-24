# Which metric starts predicting the result first

40 games, 26 wins. Noise floor about 0.32; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | cov (us-them) | +0.53 | +0.23 | +0.35 | +0.37 | +0.40 | +0.47 | +0.52 |
| r200 | - | drones (us-them) | +0.47 | . | +0.33 | +0.24 | +0.22 | +0.30 | +0.33 |
| r200 | - | drones (us-them) ~avg | +0.42 | . | +0.37 | +0.31 | +0.31 | +0.33 | +0.38 |
| r200 | - | robots (us-them) | +0.55 | -0.02 | +0.38 | +0.41 | +0.52 | +0.52 | +0.47 |
| r200 | - | spawned (us-them) | +0.56 | -0.02 | +0.36 | +0.44 | +0.52 | +0.49 | +0.53 |
| r200 | - | worth (us-them) | +0.43 | -0.10 | +0.34 | +0.39 | +0.43 | +0.36 | +0.19 |
| r250 | - | units (us-them) | +0.56 | -0.04 | +0.30 | +0.30 | +0.45 | +0.50 | +0.56 |
| r300 | - | mines (us-them) | +0.43 | -0.07 | +0.27 | +0.31 | +0.30 | +0.34 | +0.43 |
| r300 | - | robots (us-them) ~avg | +0.56 | -0.12 | +0.16 | +0.30 | +0.44 | +0.55 | +0.55 |
| r350 | - | cov (us-them) ~avg | +0.55 | +0.22 | +0.22 | +0.30 | +0.33 | +0.39 | +0.53 |
| r350 | - | landscapers (us-them) | +0.56 | -0.24 | +0.20 | +0.24 | +0.40 | +0.53 | +0.54 |
| r350 | - | spawned (us-them) ~avg | +0.54 | -0.12 | +0.14 | +0.30 | +0.43 | +0.51 | +0.54 |
| r350 | - | worth (us-them) ~avg | +0.39 | -0.16 | +0.15 | +0.29 | +0.37 | +0.37 | +0.26 |
| r400 | - | moves (us-them) | +0.62 | +0.16 | +0.26 | +0.27 | +0.31 | +0.42 | +0.59 |
| r400 | - | units (us-them) ~avg | +0.56 | -0.06 | +0.10 | +0.20 | +0.33 | +0.49 | +0.56 |
| r450 | - | moves (us-them) ~avg | +0.63 | +0.21 | +0.23 | +0.25 | +0.28 | +0.36 | +0.55 |
| r550 | - | landscapers (us-them) ~avg | +0.50 | -0.27 | -0.04 | +0.02 | +0.13 | +0.41 | +0.50 |
| r650 | - | mines (us-them) ~avg | +0.39 | -0.18 | +0.08 | +0.19 | +0.24 | +0.28 | +0.39 |
| r750 | - | digs (us-them) | -0.42 | -0.42 | -0.09 | +0.02 | +0.05 | +0.23 | +0.39 |
| r800 | r100 | dirtDeps (us-them) | -0.39 | -0.39 | -0.11 | -0.01 | +0.03 | +0.19 | +0.36 |
| r800 | - | pickups (us-them) | +0.41 | . | . | +0.14 | +0.14 | +0.26 | +0.32 |
| r950 | - | miners (us-them) | +0.42 | +0.23 | +0.13 | +0.07 | +0.20 | +0.09 | +0.22 |
| r950 | - | miners (us-them) ~avg | +0.38 | +0.14 | +0.15 | +0.14 | +0.18 | +0.14 | +0.25 |
| r950 | - | netguns (us-them) | +0.51 | . | . | +0.08 | +0.09 | +0.03 | +0.22 |
| r1200 | - | netguns (us-them) ~avg | +0.31 | . | . | +0.04 | +0.03 | -0.02 | +0.08 |
| r1200 | - | pickups (us-them) ~avg | +0.30 | . | . | +0.13 | +0.16 | +0.20 | +0.24 |
| - | r750 | aba (us-them) [inverted] | -0.40 | +0.08 | +0.06 | -0.08 | -0.11 | -0.23 | -0.38 |
| - | r750 | aba (us-them) [inverted] ~avg | -0.39 | +0.05 | +0.07 | -0.02 | -0.10 | -0.23 | -0.35 |
| - | - | died (us-them) [inverted] | +0.23 | . | +0.23 | -0.09 | +0.06 | -0.04 | -0.14 |
| - | - | died (us-them) [inverted] ~avg | +0.23 | . | +0.23 | +0.00 | +0.05 | -0.03 | -0.18 |
| - | r100 | digs (us-them) ~avg | -0.42 | -0.42 | -0.19 | -0.11 | -0.08 | -0.02 | +0.16 |
| - | r100 | dirtDeps (us-them) ~avg | -0.39 | -0.39 | -0.21 | -0.13 | -0.10 | -0.06 | +0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | soup (us-them) | -0.25 | -0.07 | -0.14 | -0.15 | -0.01 | -0.18 | -0.23 |
| - | - | soup (us-them) ~avg | -0.12 | +0.02 | -0.06 | -0.07 | -0.08 | -0.09 | -0.06 |
| - | - | vaporators (us-them) | +0.18 | +0.18 | +0.09 | +0.17 | +0.06 | +0.07 | +0.02 |
| - | - | vaporators (us-them) ~avg | +0.18 | +0.18 | +0.13 | +0.15 | +0.12 | +0.03 | -0.04 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
