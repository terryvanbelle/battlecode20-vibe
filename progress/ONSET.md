# Which metric starts predicting the result first

12 games, 5 wins. Noise floor about 0.58; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) ~avg | +0.41 | +0.29 | +0.15 | +0.06 | -0.00 | +0.03 | +0.05 |
| r100 | - | digs (us-them) | +0.78 | +0.54 | +0.53 | +0.64 | +0.66 | +0.75 | +0.78 |
| r100 | - | digs (us-them) ~avg | +0.78 | +0.54 | +0.51 | +0.61 | +0.65 | +0.71 | +0.77 |
| r100 | - | dirtDeps (us-them) | +0.78 | +0.54 | +0.54 | +0.63 | +0.66 | +0.75 | +0.78 |
| r100 | - | dirtDeps (us-them) ~avg | +0.78 | +0.54 | +0.53 | +0.61 | +0.65 | +0.72 | +0.78 |
| r100 | - | landscapers (us-them) | +0.53 | +0.44 | +0.37 | +0.20 | +0.26 | +0.38 | +0.37 |
| r100 | - | landscapers (us-them) ~avg | +0.55 | +0.44 | +0.50 | +0.38 | +0.34 | +0.37 | +0.42 |
| r400 | r100 | aba (us-them) [inverted] | +0.36 | -0.30 | -0.28 | -0.10 | +0.36 | -0.17 | -0.24 |
| r500 | - | died (us-them) [inverted] | +0.53 | . | . | -0.29 | +0.05 | +0.47 | +0.47 |
| r550 | - | died (us-them) [inverted] ~avg | +0.55 | . | . | -0.29 | -0.11 | +0.41 | +0.54 |
| r950 | - | robots (us-them) | +0.38 | +0.25 | +0.06 | -0.12 | -0.05 | +0.16 | +0.24 |
| r950 | - | units (us-them) | +0.39 | +0.22 | +0.11 | -0.03 | +0.09 | +0.27 | +0.26 |
| r950 | r300 | vaporators (us-them) | -0.42 | . | -0.08 | -0.35 | -0.42 | -0.30 | -0.11 |
| r1200 | - | units (us-them) ~avg | +0.31 | +0.05 | +0.14 | +0.08 | +0.08 | +0.16 | +0.24 |
| - | r100 | aba (us-them) [inverted] ~avg | -0.33 | -0.30 | -0.31 | -0.22 | +0.01 | +0.06 | -0.18 |
| - | r450 | cov (us-them) | -0.49 | -0.21 | -0.18 | -0.16 | -0.29 | -0.40 | -0.49 |
| - | r50 | cov (us-them) ~avg | -0.44 | -0.25 | -0.23 | -0.20 | -0.23 | -0.32 | -0.41 |
| - | r200 | drones (us-them) | -0.58 | -0.29 | -0.58 | -0.13 | -0.07 | -0.12 | -0.01 |
| - | r200 | drones (us-them) ~avg | -0.47 | -0.29 | -0.45 | -0.35 | -0.23 | -0.15 | -0.11 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | -0.29 | -0.03 | -0.10 | -0.23 | -0.14 | +0.01 | -0.21 |
| - | - | miners (us-them) ~avg | -0.19 | -0.10 | -0.10 | -0.16 | -0.17 | -0.14 | -0.18 |
| - | - | mines (us-them) | +0.41 | +0.22 | -0.01 | -0.12 | -0.06 | +0.10 | +0.06 |
| - | r350 | moves (us-them) | -0.47 | +0.19 | +0.08 | -0.18 | -0.40 | -0.46 | -0.40 |
| - | r500 | moves (us-them) ~avg | -0.41 | +0.14 | +0.12 | -0.04 | -0.21 | -0.38 | -0.41 |
| - | r200 | netguns (us-them) | -0.38 | . | -0.36 | -0.36 | -0.38 | -0.21 | -0.02 |
| - | r200 | netguns (us-them) ~avg | -0.36 | . | -0.36 | -0.36 | -0.34 | -0.34 | -0.22 |
| - | - | pickups (us-them) | -0.36 | -0.36 | -0.06 | +0.01 | +0.15 | +0.10 | +0.20 |
| - | - | pickups (us-them) ~avg | -0.36 | -0.36 | -0.04 | -0.02 | +0.04 | +0.09 | +0.18 |
| - | - | robots (us-them) ~avg | +0.26 | +0.14 | +0.12 | +0.03 | -0.00 | +0.06 | +0.15 |
| - | r200 | soup (us-them) | -0.35 | -0.12 | -0.35 | -0.09 | +0.13 | -0.21 | -0.28 |
| - | - | soup (us-them) ~avg | +0.34 | +0.16 | -0.04 | -0.17 | -0.12 | -0.16 | -0.26 |
| - | - | spawned (us-them) | +0.25 | +0.25 | +0.06 | -0.08 | -0.06 | +0.03 | +0.06 |
| - | - | spawned (us-them) ~avg | +0.16 | +0.14 | +0.12 | +0.05 | +0.01 | +0.01 | +0.02 |
| - | r300 | vaporators (us-them) ~avg | -0.43 | . | -0.21 | -0.34 | -0.41 | -0.39 | -0.34 |
| - | - | worth (us-them) | +0.31 | +0.31 | -0.03 | -0.22 | -0.18 | -0.07 | -0.02 |
| - | - | worth (us-them) ~avg | +0.30 | +0.30 | +0.13 | -0.03 | -0.11 | -0.11 | -0.07 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
