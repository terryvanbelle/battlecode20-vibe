# Which metric starts predicting the result first

46 games, 27 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] ~avg | +0.32 | +0.28 | +0.32 | +0.19 | +0.18 | +0.09 | -0.10 |
| r50 | - | miners (us-them) | +0.57 | +0.40 | +0.35 | +0.23 | +0.21 | +0.32 | +0.22 |
| r50 | - | miners (us-them) ~avg | +0.58 | +0.53 | +0.49 | +0.42 | +0.34 | +0.30 | +0.23 |
| r50 | - | mines (us-them) | +0.57 | +0.57 | +0.52 | +0.49 | +0.56 | +0.55 | +0.45 |
| r50 | - | mines (us-them) ~avg | +0.61 | +0.61 | +0.57 | +0.57 | +0.58 | +0.58 | +0.51 |
| r50 | - | robots (us-them) | +0.66 | +0.55 | +0.49 | +0.53 | +0.59 | +0.65 | +0.59 |
| r50 | - | robots (us-them) ~avg | +0.66 | +0.64 | +0.56 | +0.57 | +0.59 | +0.65 | +0.64 |
| r50 | - | spawned (us-them) | +0.66 | +0.56 | +0.46 | +0.45 | +0.50 | +0.59 | +0.53 |
| r50 | - | spawned (us-them) ~avg | +0.67 | +0.66 | +0.55 | +0.53 | +0.52 | +0.57 | +0.55 |
| r50 | - | units (us-them) | +0.60 | +0.54 | +0.42 | +0.41 | +0.53 | +0.56 | +0.51 |
| r50 | - | units (us-them) ~avg | +0.61 | +0.61 | +0.48 | +0.48 | +0.50 | +0.58 | +0.56 |
| r50 | - | worth (us-them) | +0.64 | +0.52 | +0.59 | +0.57 | +0.60 | +0.63 | +0.55 |
| r50 | - | worth (us-them) ~avg | +0.65 | +0.53 | +0.60 | +0.62 | +0.63 | +0.65 | +0.59 |
| r100 | - | drones (us-them) ~avg | +0.38 | +0.38 | +0.30 | +0.29 | +0.32 | +0.34 | +0.34 |
| r150 | - | digs (us-them) | +0.66 | +0.16 | +0.28 | +0.30 | +0.37 | +0.50 | +0.63 |
| r150 | - | moves (us-them) | -0.54 | +0.16 | +0.42 | +0.43 | +0.37 | +0.42 | +0.30 |
| r200 | - | died (us-them) [inverted] | +0.45 | -0.18 | +0.34 | +0.29 | +0.37 | +0.40 | +0.45 |
| r200 | - | moves (us-them) ~avg | -0.53 | -0.04 | +0.40 | +0.42 | +0.39 | +0.37 | +0.36 |
| r200 | - | pickups (us-them) ~avg | +0.55 | +0.17 | +0.32 | +0.46 | +0.54 | +0.55 | +0.49 |
| r250 | - | digs (us-them) ~avg | +0.60 | +0.16 | +0.28 | +0.30 | +0.32 | +0.42 | +0.57 |
| r250 | - | pickups (us-them) | +0.57 | +0.17 | +0.28 | +0.47 | +0.57 | +0.53 | +0.39 |
| r300 | - | died (us-them) [inverted] ~avg | +0.44 | -0.18 | +0.27 | +0.33 | +0.37 | +0.39 | +0.44 |
| r300 | - | landscapers (us-them) | +0.59 | +0.17 | +0.27 | +0.33 | +0.46 | +0.55 | +0.51 |
| r350 | - | dirtDeps (us-them) | +0.66 | +0.12 | +0.23 | +0.28 | +0.35 | +0.50 | +0.62 |
| r350 | - | landscapers (us-them) ~avg | +0.59 | +0.17 | +0.19 | +0.27 | +0.35 | +0.53 | +0.59 |
| r400 | - | drones (us-them) | +0.39 | +0.38 | +0.10 | +0.24 | +0.35 | +0.32 | +0.33 |
| r450 | - | dirtDeps (us-them) ~avg | +0.60 | +0.12 | +0.19 | +0.25 | +0.29 | +0.40 | +0.56 |
| r500 | - | vaporators (us-them) | +0.45 | +0.03 | +0.04 | +0.24 | +0.26 | +0.45 | +0.43 |
| r550 | - | vaporators (us-them) ~avg | +0.47 | +0.03 | +0.04 | +0.13 | +0.20 | +0.36 | +0.42 |
| - | - | aba (us-them) [inverted] | -0.30 | +0.23 | +0.21 | +0.18 | +0.19 | -0.02 | -0.20 |
| - | - | cov (us-them) | -0.34 | -0.19 | -0.02 | +0.09 | +0.12 | +0.15 | +0.15 |
| - | r50 | cov (us-them) ~avg | -0.31 | -0.24 | -0.12 | -0.05 | -0.00 | +0.04 | +0.09 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.25 | +0.13 | +0.23 | +0.16 | +0.19 | +0.14 | +0.21 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.25 | +0.13 | +0.01 | +0.01 | +0.18 | +0.11 | +0.19 |
| - | - | netguns (us-them) | +0.25 | -0.13 | -0.02 | -0.01 | +0.04 | +0.23 | +0.20 |
| - | - | netguns (us-them) ~avg | -0.13 | -0.13 | -0.05 | -0.02 | -0.01 | +0.05 | +0.11 |
| - | - | soup (us-them) | -0.29 | -0.13 | +0.14 | -0.03 | +0.13 | -0.11 | -0.09 |
| - | - | soup (us-them) ~avg | -0.27 | -0.22 | +0.04 | +0.08 | +0.15 | +0.06 | -0.05 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
