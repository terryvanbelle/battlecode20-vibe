# Which metric starts predicting the result first

45 games, 17 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.38 | +0.38 | +0.28 | +0.27 | +0.17 | +0.13 | +0.04 |
| r50 | - | mines (us-them) ~avg | +0.39 | +0.39 | +0.29 | +0.32 | +0.26 | +0.18 | +0.14 |
| r50 | - | robots (us-them) | +0.34 | +0.27 | +0.18 | +0.09 | +0.01 | +0.03 | +0.11 |
| r50 | - | robots (us-them) ~avg | +0.33 | +0.31 | +0.19 | +0.14 | +0.07 | +0.03 | +0.14 |
| r50 | - | spawned (us-them) | +0.36 | +0.27 | +0.17 | +0.11 | +0.04 | +0.07 | +0.11 |
| r50 | - | spawned (us-them) ~avg | +0.35 | +0.32 | +0.19 | +0.15 | +0.10 | +0.06 | +0.13 |
| r50 | - | worth (us-them) | +0.41 | +0.41 | +0.23 | +0.19 | +0.13 | +0.17 | +0.18 |
| r50 | - | worth (us-them) ~avg | +0.41 | +0.41 | +0.25 | +0.25 | +0.18 | +0.16 | +0.23 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.50 | +0.50 | +0.27 | +0.17 | +0.08 | +0.08 | +0.08 |
| r400 | - | vaporators (us-them) | +0.35 | +0.02 | +0.18 | +0.26 | +0.35 | +0.30 | +0.18 |
| r500 | - | vaporators (us-them) ~avg | +0.32 | +0.02 | +0.16 | +0.21 | +0.27 | +0.32 | +0.26 |
| r700 | - | digs (us-them) | +0.36 | +0.36 | +0.13 | +0.14 | +0.16 | +0.24 | +0.33 |
| r700 | - | digs (us-them) ~avg | +0.37 | +0.37 | +0.14 | +0.12 | +0.13 | +0.22 | +0.33 |
| r700 | - | dirtDeps (us-them) | +0.37 | +0.37 | +0.16 | +0.16 | +0.19 | +0.28 | +0.34 |
| r700 | - | dirtDeps (us-them) ~avg | +0.37 | +0.37 | +0.14 | +0.14 | +0.16 | +0.26 | +0.34 |
| - | - | aba (us-them) [inverted] | -0.21 | -0.11 | +0.09 | -0.01 | +0.07 | -0.01 | -0.21 |
| - | - | aba (us-them) [inverted] ~avg | -0.12 | -0.11 | +0.01 | -0.02 | +0.03 | +0.02 | -0.09 |
| - | r600 | cov (us-them) | -0.33 | -0.06 | +0.25 | +0.19 | +0.09 | -0.33 | -0.31 |
| - | - | cov (us-them) ~avg | -0.26 | +0.00 | +0.20 | +0.19 | +0.19 | -0.14 | -0.26 |
| - | - | died (us-them) [inverted] | -0.19 | +0.00 | +0.06 | -0.05 | -0.11 | -0.13 | -0.01 |
| - | - | died (us-them) [inverted] ~avg | -0.19 | -0.09 | +0.05 | -0.04 | -0.11 | -0.10 | +0.02 |
| - | - | drones (us-them) | +0.21 | +0.21 | +0.14 | +0.08 | +0.08 | -0.05 | +0.06 |
| - | - | drones (us-them) ~avg | +0.21 | +0.21 | +0.14 | +0.08 | +0.08 | -0.06 | +0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.49 | +0.49 | +0.25 | +0.27 | . | . | . |
| - | - | landscapers (us-them) | +0.25 | +0.21 | -0.05 | +0.16 | +0.06 | +0.16 | +0.25 |
| - | - | landscapers (us-them) ~avg | +0.27 | +0.21 | -0.06 | +0.05 | +0.06 | +0.14 | +0.27 |
| - | r700 | miners (us-them) | -0.39 | +0.17 | +0.16 | -0.02 | -0.12 | -0.27 | -0.34 |
| - | - | miners (us-them) ~avg | -0.23 | +0.21 | +0.20 | +0.10 | +0.01 | -0.12 | -0.19 |
| - | - | moves (us-them) | -0.22 | -0.13 | +0.11 | +0.07 | +0.00 | -0.10 | +0.07 |
| - | - | moves (us-them) ~avg | -0.21 | -0.15 | +0.10 | +0.09 | +0.05 | -0.04 | +0.09 |
| - | - | netguns (us-them) | -0.23 | . | +0.14 | +0.03 | -0.08 | -0.07 | +0.16 |
| - | - | netguns (us-them) ~avg | +0.13 | . | +0.13 | +0.05 | -0.01 | -0.10 | +0.04 |
| - | - | pickups (us-them) | -0.26 | +0.20 | +0.17 | +0.03 | -0.01 | -0.20 | -0.12 |
| - | - | pickups (us-them) ~avg | +0.20 | +0.20 | +0.19 | +0.11 | +0.07 | -0.08 | -0.09 |
| - | - | soup (us-them) | +0.17 | +0.17 | -0.11 | +0.01 | +0.01 | +0.02 | +0.12 |
| - | - | soup (us-them) ~avg | +0.14 | +0.12 | +0.02 | +0.03 | +0.08 | +0.05 | +0.14 |
| - | - | units (us-them) | +0.30 | +0.30 | +0.12 | +0.09 | -0.01 | -0.02 | +0.10 |
| - | - | units (us-them) ~avg | +0.30 | +0.30 | +0.14 | +0.11 | +0.05 | +0.01 | +0.13 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
