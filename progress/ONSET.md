# Which metric starts predicting the result first

43 games, 35 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r200 | - | landscapers (us-them) | +0.53 | +0.04 | +0.30 | +0.41 | +0.50 | +0.47 | +0.44 |
| r200 | - | spawned (us-them) | +0.33 | +0.04 | +0.33 | +0.30 | +0.29 | +0.14 | +0.10 |
| r250 | - | digs (us-them) | +0.63 | +0.02 | +0.24 | +0.36 | +0.45 | +0.57 | +0.61 |
| r250 | - | dirtDeps (us-them) | +0.63 | +0.09 | +0.29 | +0.37 | +0.44 | +0.57 | +0.61 |
| r250 | - | dirtDeps (us-them) ~avg | +0.61 | +0.08 | +0.25 | +0.33 | +0.39 | +0.50 | +0.60 |
| r350 | - | digs (us-them) ~avg | +0.62 | +0.01 | +0.21 | +0.30 | +0.38 | +0.50 | +0.60 |
| r350 | - | landscapers (us-them) ~avg | +0.52 | +0.02 | +0.20 | +0.28 | +0.41 | +0.49 | +0.52 |
| r350 | - | units (us-them) | +0.34 | +0.07 | +0.22 | +0.29 | +0.34 | +0.24 | +0.22 |
| r400 | - | spawned (us-them) ~avg | +0.30 | +0.01 | +0.21 | +0.27 | +0.30 | +0.24 | +0.16 |
| r450 | - | units (us-them) ~avg | +0.34 | -0.00 | +0.13 | +0.20 | +0.28 | +0.31 | +0.27 |
| r700 | - | died (us-them) [inverted] | +0.31 | . | -0.14 | -0.08 | +0.01 | +0.13 | +0.28 |
| r750 | - | vaporators (us-them) | +0.31 | +0.28 | +0.09 | +0.09 | +0.07 | -0.00 | +0.19 |
| r950 | - | pickups (us-them) ~avg | +0.31 | +0.14 | +0.15 | +0.15 | +0.15 | +0.19 | +0.19 |
| r950 | - | robots (us-them) | +0.32 | +0.04 | +0.31 | +0.26 | +0.29 | +0.20 | +0.22 |
| r950 | - | robots (us-them) ~avg | +0.34 | +0.01 | +0.21 | +0.25 | +0.29 | +0.27 | +0.25 |
| r1100 | - | pickups (us-them) | +0.31 | +0.14 | +0.15 | +0.15 | +0.16 | +0.21 | +0.16 |
| - | - | aba (us-them) [inverted] | -0.26 | -0.11 | +0.14 | +0.21 | +0.21 | +0.09 | -0.08 |
| - | - | aba (us-them) [inverted] ~avg | -0.23 | -0.17 | +0.05 | +0.16 | +0.19 | +0.16 | +0.06 |
| - | r150 | cov (us-them) | -0.33 | -0.23 | -0.27 | -0.33 | -0.23 | +0.18 | +0.06 |
| - | r150 | cov (us-them) ~avg | -0.32 | -0.27 | -0.31 | -0.32 | -0.30 | -0.05 | +0.04 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | . | -0.04 | -0.13 | -0.06 | +0.09 | +0.24 |
| - | - | drones (us-them) | +0.15 | +0.15 | -0.09 | +0.07 | -0.02 | -0.06 | -0.10 |
| - | - | drones (us-them) ~avg | +0.15 | +0.15 | -0.04 | +0.03 | +0.00 | -0.02 | -0.09 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | -0.13 | +0.03 | -0.05 | -0.10 | -0.11 | -0.09 | -0.12 |
| - | - | miners (us-them) ~avg | -0.12 | -0.04 | -0.03 | -0.09 | -0.11 | -0.11 | -0.11 |
| - | - | mines (us-them) | +0.20 | +0.20 | +0.13 | +0.10 | +0.10 | +0.02 | -0.00 |
| - | - | mines (us-them) ~avg | +0.19 | +0.19 | +0.15 | +0.10 | +0.10 | +0.05 | +0.02 |
| - | - | moves (us-them) | +0.12 | +0.00 | -0.01 | +0.01 | +0.05 | +0.12 | +0.07 |
| - | - | moves (us-them) ~avg | +0.10 | +0.04 | +0.05 | +0.05 | +0.06 | +0.08 | +0.09 |
| - | - | netguns (us-them) | +0.22 | . | +0.14 | +0.07 | +0.10 | +0.10 | +0.00 |
| - | - | netguns (us-them) ~avg | +0.22 | . | +0.18 | +0.11 | +0.10 | +0.13 | +0.08 |
| - | - | soup (us-them) | +0.28 | +0.15 | -0.09 | -0.12 | -0.15 | -0.09 | +0.00 |
| - | - | soup (us-them) ~avg | +0.21 | +0.20 | -0.01 | -0.09 | -0.12 | -0.13 | -0.08 |
| - | - | vaporators (us-them) ~avg | +0.28 | +0.28 | +0.15 | +0.10 | +0.10 | +0.06 | +0.16 |
| - | - | worth (us-them) | +0.25 | +0.22 | +0.25 | +0.15 | +0.13 | +0.07 | +0.19 |
| - | - | worth (us-them) ~avg | +0.25 | +0.23 | +0.25 | +0.18 | +0.16 | +0.11 | +0.14 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
