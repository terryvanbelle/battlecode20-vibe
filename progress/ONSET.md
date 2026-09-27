# Which metric starts predicting the result first

45 games, 21 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) ~avg | +0.34 | +0.32 | +0.17 | +0.15 | +0.17 | +0.32 | +0.34 |
| r100 | - | pickups (us-them) ~avg | +0.45 | +0.30 | +0.14 | +0.22 | +0.32 | +0.37 | +0.45 |
| r150 | - | cov (us-them) | +0.34 | +0.29 | +0.31 | +0.19 | +0.09 | +0.08 | +0.13 |
| r150 | - | cov (us-them) ~avg | +0.33 | +0.28 | +0.30 | +0.24 | +0.17 | +0.13 | +0.16 |
| r150 | - | hqBuried (us-them) [inverted] | +0.42 | +0.13 | +0.36 | +0.10 | +0.12 | +0.13 | +0.13 |
| r150 | - | hqBuried (us-them) [inverted] ~avg | +0.39 | +0.13 | +0.31 | -0.11 | +0.02 | +0.06 | +0.07 |
| r300 | - | mines (us-them) | +0.66 | +0.05 | +0.09 | +0.36 | +0.44 | +0.58 | +0.63 |
| r300 | - | robots (us-them) | +0.62 | +0.09 | +0.13 | +0.39 | +0.54 | +0.59 | +0.53 |
| r300 | - | spawned (us-them) | +0.65 | +0.11 | +0.12 | +0.30 | +0.49 | +0.63 | +0.60 |
| r300 | - | worth (us-them) | +0.51 | +0.14 | +0.20 | +0.41 | +0.48 | +0.49 | +0.41 |
| r350 | - | died (us-them) [inverted] | +0.36 | -0.23 | +0.11 | +0.28 | +0.33 | +0.13 | +0.13 |
| r350 | - | landscapers (us-them) | +0.54 | -0.05 | +0.20 | +0.19 | +0.43 | +0.53 | +0.54 |
| r350 | - | mines (us-them) ~avg | +0.60 | +0.07 | +0.07 | +0.25 | +0.35 | +0.47 | +0.60 |
| r350 | - | pickups (us-them) | +0.40 | +0.30 | +0.03 | +0.27 | +0.36 | +0.32 | +0.40 |
| r350 | - | robots (us-them) ~avg | +0.62 | +0.11 | +0.15 | +0.25 | +0.39 | +0.57 | +0.61 |
| r350 | - | units (us-them) | +0.59 | +0.12 | +0.12 | +0.30 | +0.49 | +0.58 | +0.52 |
| r350 | - | worth (us-them) ~avg | +0.52 | +0.10 | +0.19 | +0.26 | +0.39 | +0.48 | +0.49 |
| r400 | - | miners (us-them) | +0.31 | +0.05 | -0.00 | +0.29 | +0.31 | +0.20 | +0.15 |
| r400 | - | spawned (us-them) ~avg | +0.65 | +0.14 | +0.15 | +0.23 | +0.34 | +0.54 | +0.65 |
| r400 | - | units (us-them) ~avg | +0.64 | +0.12 | +0.15 | +0.20 | +0.33 | +0.57 | +0.63 |
| r450 | - | died (us-them) [inverted] ~avg | +0.31 | -0.23 | +0.05 | +0.17 | +0.29 | +0.28 | +0.22 |
| r450 | - | digs (us-them) | +0.55 | +0.04 | +0.09 | +0.14 | +0.27 | +0.45 | +0.54 |
| r450 | - | dirtDeps (us-them) | +0.57 | +0.01 | +0.10 | +0.16 | +0.29 | +0.45 | +0.56 |
| r450 | - | drones (us-them) | +0.33 | +0.32 | +0.04 | +0.09 | +0.25 | +0.31 | +0.31 |
| r450 | - | landscapers (us-them) ~avg | +0.59 | -0.02 | +0.12 | +0.07 | +0.28 | +0.50 | +0.59 |
| r550 | - | digs (us-them) ~avg | +0.53 | +0.07 | +0.09 | +0.13 | +0.19 | +0.34 | +0.48 |
| r550 | - | dirtDeps (us-them) ~avg | +0.54 | +0.03 | +0.09 | +0.13 | +0.21 | +0.35 | +0.49 |
| r600 | - | moves (us-them) | +0.37 | +0.11 | +0.17 | +0.15 | +0.17 | +0.31 | +0.37 |
| r700 | - | miners (us-them) ~avg | +0.33 | +0.07 | +0.05 | +0.23 | +0.26 | +0.27 | +0.29 |
| r700 | - | moves (us-them) ~avg | +0.37 | +0.05 | +0.13 | +0.17 | +0.15 | +0.23 | +0.37 |
| r1200 | - | netguns (us-them) | +0.30 | . | -0.13 | -0.06 | +0.02 | +0.14 | +0.26 |
| - | - | aba (us-them) [inverted] | +0.34 | +0.13 | +0.07 | +0.15 | +0.10 | -0.13 | -0.16 |
| - | - | aba (us-them) [inverted] ~avg | +0.35 | +0.20 | +0.16 | +0.16 | +0.13 | -0.03 | -0.13 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) ~avg | +0.20 | . | -0.13 | -0.12 | -0.06 | +0.03 | +0.16 |
| - | - | soup (us-them) | -0.23 | +0.12 | +0.10 | -0.10 | +0.17 | +0.01 | -0.08 |
| - | - | soup (us-them) ~avg | -0.24 | +0.04 | +0.11 | +0.07 | +0.09 | +0.10 | -0.06 |
| - | - | vaporators (us-them) | +0.28 | -0.11 | -0.07 | +0.22 | +0.19 | +0.18 | +0.26 |
| - | - | vaporators (us-them) ~avg | +0.25 | -0.18 | -0.18 | -0.04 | +0.10 | +0.18 | +0.25 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
