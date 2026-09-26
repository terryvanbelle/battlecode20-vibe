# Which metric starts predicting the result first

43 games, 41 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | digs (us-them) | +0.49 | +0.24 | +0.33 | +0.34 | +0.39 | +0.44 | +0.49 |
| r200 | - | dirtDeps (us-them) | +0.49 | +0.25 | +0.30 | +0.32 | +0.37 | +0.44 | +0.49 |
| r250 | - | landscapers (us-them) | +0.50 | +0.23 | +0.23 | +0.38 | +0.48 | +0.45 | +0.43 |
| r350 | - | landscapers (us-them) ~avg | +0.41 | +0.23 | +0.19 | +0.26 | +0.34 | +0.41 | +0.40 |
| r400 | - | digs (us-them) ~avg | +0.40 | +0.24 | +0.24 | +0.26 | +0.30 | +0.36 | +0.40 |
| r450 | - | dirtDeps (us-them) ~avg | +0.40 | +0.25 | +0.26 | +0.27 | +0.30 | +0.36 | +0.40 |
| r450 | - | units (us-them) | +0.31 | -0.05 | -0.00 | +0.18 | +0.29 | +0.23 | +0.04 |
| - | - | aba (us-them) [inverted] | -0.20 | -0.17 | +0.07 | +0.17 | +0.14 | +0.02 | -0.03 |
| - | - | aba (us-them) [inverted] ~avg | -0.21 | -0.21 | +0.05 | +0.13 | +0.15 | +0.11 | +0.06 |
| - | - | cov (us-them) | -0.15 | -0.03 | +0.09 | +0.11 | +0.11 | +0.02 | -0.08 |
| - | - | cov (us-them) ~avg | +0.11 | -0.01 | +0.05 | +0.08 | +0.11 | +0.08 | -0.01 |
| - | r150 | died (us-them) [inverted] | -0.35 | . | -0.30 | -0.19 | -0.16 | +0.06 | -0.04 |
| - | r150 | died (us-them) [inverted] ~avg | -0.38 | . | -0.33 | -0.38 | -0.29 | -0.06 | -0.11 |
| - | r750 | drones (us-them) | -0.31 | -0.09 | -0.19 | -0.16 | -0.13 | -0.14 | -0.25 |
| - | - | drones (us-them) ~avg | -0.27 | -0.09 | -0.19 | -0.17 | -0.15 | -0.16 | -0.25 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.11 | +0.06 | +0.10 | +0.06 | +0.06 | +0.06 | . |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.11 | +0.06 | +0.09 | +0.07 | +0.06 | +0.06 | . |
| - | r650 | miners (us-them) | -0.32 | -0.18 | -0.23 | -0.19 | -0.24 | -0.28 | -0.29 |
| - | r700 | miners (us-them) ~avg | -0.46 | -0.19 | -0.21 | -0.21 | -0.21 | -0.24 | -0.43 |
| - | - | mines (us-them) | -0.17 | -0.03 | +0.01 | +0.08 | +0.10 | +0.04 | -0.09 |
| - | - | mines (us-them) ~avg | -0.09 | -0.05 | +0.01 | +0.05 | +0.09 | +0.08 | -0.01 |
| - | r100 | moves (us-them) | -0.30 | -0.30 | -0.20 | -0.19 | -0.10 | -0.00 | -0.08 |
| - | - | moves (us-them) ~avg | -0.27 | -0.27 | -0.23 | -0.21 | -0.18 | -0.09 | -0.11 |
| - | - | netguns (us-them) | +0.10 | . | -0.05 | -0.06 | -0.06 | +0.00 | +0.07 |
| - | - | netguns (us-them) ~avg | -0.08 | . | -0.05 | -0.06 | -0.06 | -0.04 | +0.02 |
| - | - | pickups (us-them) | -0.26 | -0.06 | -0.15 | -0.13 | -0.14 | -0.18 | -0.21 |
| - | - | pickups (us-them) ~avg | -0.25 | -0.06 | -0.14 | -0.13 | -0.14 | -0.16 | -0.20 |
| - | - | robots (us-them) | +0.25 | -0.03 | -0.02 | +0.15 | +0.24 | +0.16 | -0.01 |
| - | - | robots (us-them) ~avg | +0.16 | -0.04 | -0.05 | +0.03 | +0.12 | +0.15 | +0.02 |
| - | r300 | soup (us-them) | -0.50 | -0.03 | -0.18 | -0.47 | -0.50 | -0.31 | -0.22 |
| - | r300 | soup (us-them) ~avg | -0.47 | -0.08 | -0.06 | -0.32 | -0.45 | -0.43 | -0.30 |
| - | - | spawned (us-them) | +0.27 | -0.03 | +0.03 | +0.18 | +0.27 | +0.15 | +0.01 |
| - | - | spawned (us-them) ~avg | +0.17 | -0.04 | -0.02 | +0.07 | +0.15 | +0.16 | +0.05 |
| - | - | units (us-them) ~avg | +0.18 | -0.10 | -0.07 | +0.02 | +0.12 | +0.18 | +0.06 |
| - | - | vaporators (us-them) | +0.16 | -0.06 | +0.15 | +0.13 | +0.10 | -0.01 | -0.12 |
| - | - | vaporators (us-them) ~avg | -0.09 | -0.06 | -0.04 | -0.02 | -0.03 | -0.07 | -0.09 |
| - | - | worth (us-them) | -0.14 | +0.03 | +0.01 | +0.09 | +0.11 | +0.04 | -0.13 |
| - | - | worth (us-them) ~avg | -0.13 | +0.01 | -0.00 | +0.03 | +0.06 | +0.04 | -0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
