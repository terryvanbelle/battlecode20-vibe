# Which metric starts predicting the result first -- every recorded block of g_iter14 merged

142 games, 67 wins. Noise floor about 0.17; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r250 | - | robots (us-them) | +0.38 | +0.21 | +0.28 | +0.35 | +0.31 | +0.31 | +0.36 |
| r300 | - | robots (us-them) ~avg | +0.37 | +0.24 | +0.25 | +0.30 | +0.33 | +0.35 | +0.37 |
| r300 | - | units (us-them) | +0.39 | +0.27 | +0.29 | +0.35 | +0.32 | +0.31 | +0.39 |
| r300 | - | units (us-them) ~avg | +0.40 | +0.28 | +0.27 | +0.30 | +0.34 | +0.38 | +0.40 |
| r350 | - | digs (us-them) | +0.54 | +0.15 | +0.18 | +0.29 | +0.35 | +0.48 | +0.49 |
| r350 | - | dirtDeps (us-them) | +0.53 | +0.15 | +0.16 | +0.28 | +0.34 | +0.48 | +0.48 |
| r350 | - | landscapers (us-them) | +0.40 | +0.18 | +0.17 | +0.29 | +0.30 | +0.34 | +0.40 |
| r450 | - | digs (us-them) ~avg | +0.52 | +0.15 | +0.16 | +0.25 | +0.30 | +0.44 | +0.49 |
| r450 | - | dirtDeps (us-them) ~avg | +0.51 | +0.15 | +0.15 | +0.24 | +0.28 | +0.44 | +0.48 |
| r450 | - | landscapers (us-them) ~avg | +0.42 | +0.18 | +0.12 | +0.23 | +0.28 | +0.39 | +0.42 |
| r500 | - | spawned (us-them) ~avg | +0.32 | +0.24 | +0.24 | +0.27 | +0.29 | +0.30 | +0.32 |
| r700 | - | died (us-them) [inverted] | +0.40 | +0.07 | +0.17 | +0.23 | +0.23 | +0.26 | +0.28 |
| r700 | - | died (us-them) [inverted] ~avg | +0.32 | +0.05 | +0.15 | +0.21 | +0.23 | +0.26 | +0.32 |
| r700 | - | worth (us-them) | +0.32 | +0.25 | +0.24 | +0.26 | +0.24 | +0.28 | +0.30 |
| r750 | - | drones (us-them) | +0.33 | +0.20 | +0.22 | +0.23 | +0.23 | +0.20 | +0.33 |
| r750 | - | worth (us-them) ~avg | +0.31 | +0.25 | +0.24 | +0.24 | +0.25 | +0.27 | +0.31 |
| r800 | - | spawned (us-them) | +0.32 | +0.21 | +0.25 | +0.30 | +0.27 | +0.25 | +0.32 |
| - | - | aba (us-them) [inverted] | -0.07 | -0.07 | -0.06 | -0.02 | -0.00 | +0.01 | -0.02 |
| - | - | aba (us-them) [inverted] ~avg | -0.07 | -0.06 | -0.07 | -0.04 | -0.02 | -0.02 | -0.02 |
| - | - | cov (us-them) | +0.19 | +0.08 | +0.18 | +0.12 | +0.18 | +0.11 | +0.10 |
| - | - | cov (us-them) ~avg | +0.16 | +0.07 | +0.16 | +0.11 | +0.14 | +0.12 | +0.12 |
| - | - | drones (us-them) ~avg | +0.30 | +0.19 | +0.23 | +0.22 | +0.25 | +0.21 | +0.30 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.25 | +0.24 | +0.14 | +0.11 | +0.19 | +0.12 | +0.12 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.24 | +0.24 | +0.10 | +0.05 | +0.08 | +0.10 | +0.11 |
| - | - | miners (us-them) | +0.20 | +0.11 | +0.20 | +0.13 | +0.06 | +0.02 | +0.16 |
| - | - | miners (us-them) ~avg | +0.21 | +0.16 | +0.21 | +0.18 | +0.15 | +0.10 | +0.15 |
| - | - | mines (us-them) | +0.29 | +0.26 | +0.29 | +0.28 | +0.25 | +0.19 | +0.24 |
| - | - | mines (us-them) ~avg | +0.29 | +0.28 | +0.29 | +0.26 | +0.28 | +0.27 | +0.26 |
| - | - | moves (us-them) | +0.28 | +0.12 | +0.18 | +0.18 | +0.24 | +0.23 | +0.28 |
| - | - | moves (us-them) ~avg | +0.29 | +0.10 | +0.22 | +0.19 | +0.24 | +0.24 | +0.29 |
| - | - | netguns (us-them) | +0.25 | . | -0.02 | +0.03 | +0.02 | +0.23 | +0.22 |
| - | - | netguns (us-them) ~avg | +0.19 | . | -0.02 | +0.01 | -0.00 | +0.10 | +0.19 |
| - | - | pickups (us-them) | +0.21 | +0.10 | +0.15 | +0.19 | +0.17 | +0.17 | +0.15 |
| - | - | pickups (us-them) ~avg | +0.22 | +0.10 | +0.16 | +0.17 | +0.20 | +0.19 | +0.20 |
| - | - | soup (us-them) | -0.28 | -0.07 | -0.02 | -0.28 | -0.22 | -0.11 | -0.22 |
| - | - | soup (us-them) ~avg | -0.24 | -0.07 | -0.05 | -0.14 | -0.23 | -0.22 | -0.21 |
| - | - | vaporators (us-them) | +0.24 | +0.07 | -0.00 | +0.08 | +0.14 | +0.20 | +0.22 |
| - | - | vaporators (us-them) ~avg | +0.21 | +0.07 | +0.02 | +0.01 | +0.06 | +0.15 | +0.21 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
