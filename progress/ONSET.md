# Which metric starts predicting the result first

46 games, 13 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.46 | +0.43 | +0.33 | +0.38 | +0.43 | +0.43 | +0.41 |
| r50 | - | mines (us-them) ~avg | +0.46 | +0.46 | +0.35 | +0.39 | +0.40 | +0.45 | +0.42 |
| r100 | - | drones (us-them) | +0.47 | +0.36 | +0.36 | +0.43 | +0.41 | +0.34 | +0.37 |
| r100 | - | drones (us-them) ~avg | +0.44 | +0.36 | +0.37 | +0.42 | +0.40 | +0.37 | +0.42 |
| r100 | - | hqBuried (us-them) [inverted] | +0.36 | +0.36 | -0.09 | +0.15 | -0.12 | -0.11 | -0.12 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.36 | +0.36 | -0.11 | -0.03 | -0.11 | -0.11 | -0.12 |
| r100 | - | worth (us-them) | +0.61 | +0.50 | +0.47 | +0.54 | +0.54 | +0.59 | +0.59 |
| r100 | - | worth (us-them) ~avg | +0.60 | +0.48 | +0.45 | +0.54 | +0.53 | +0.58 | +0.60 |
| r150 | - | died (us-them) [inverted] | +0.52 | +0.13 | +0.37 | +0.37 | +0.37 | +0.41 | +0.51 |
| r150 | - | died (us-them) [inverted] ~avg | +0.51 | +0.13 | +0.34 | +0.39 | +0.37 | +0.41 | +0.51 |
| r200 | - | robots (us-them) | +0.58 | +0.42 | +0.34 | +0.48 | +0.47 | +0.55 | +0.57 |
| r250 | - | cov (us-them) | +0.58 | -0.04 | +0.27 | +0.36 | +0.34 | +0.48 | +0.55 |
| r250 | - | pickups (us-them) | +0.47 | +0.07 | +0.30 | +0.35 | +0.34 | +0.39 | +0.37 |
| r250 | - | pickups (us-them) ~avg | +0.47 | +0.07 | +0.26 | +0.33 | +0.35 | +0.39 | +0.45 |
| r250 | - | robots (us-them) ~avg | +0.57 | +0.37 | +0.29 | +0.41 | +0.44 | +0.53 | +0.57 |
| r250 | - | units (us-them) | +0.55 | +0.41 | +0.24 | +0.36 | +0.36 | +0.51 | +0.50 |
| r250 | - | vaporators (us-them) | +0.52 | +0.25 | +0.29 | +0.38 | +0.38 | +0.49 | +0.46 |
| r250 | - | vaporators (us-them) ~avg | +0.51 | +0.25 | +0.29 | +0.35 | +0.36 | +0.44 | +0.50 |
| r300 | - | cov (us-them) ~avg | +0.55 | -0.11 | +0.16 | +0.30 | +0.31 | +0.43 | +0.51 |
| r300 | - | miners (us-them) | +0.51 | +0.20 | +0.12 | +0.41 | +0.49 | +0.43 | +0.44 |
| r300 | - | spawned (us-them) | +0.55 | +0.42 | +0.20 | +0.36 | +0.34 | +0.47 | +0.53 |
| r300 | - | units (us-them) ~avg | +0.53 | +0.34 | +0.21 | +0.30 | +0.33 | +0.45 | +0.51 |
| r350 | - | moves (us-them) | +0.52 | -0.02 | +0.12 | +0.28 | +0.36 | +0.50 | +0.51 |
| r400 | - | miners (us-them) ~avg | +0.54 | +0.16 | +0.14 | +0.25 | +0.34 | +0.51 | +0.51 |
| r400 | - | spawned (us-them) ~avg | +0.54 | +0.36 | +0.21 | +0.29 | +0.32 | +0.43 | +0.50 |
| r450 | - | moves (us-them) ~avg | +0.52 | -0.11 | +0.14 | +0.23 | +0.29 | +0.45 | +0.50 |
| r950 | - | landscapers (us-them) | +0.42 | +0.22 | +0.05 | +0.01 | +0.05 | +0.30 | +0.30 |
| r1100 | - | landscapers (us-them) ~avg | +0.36 | +0.22 | -0.03 | +0.00 | +0.04 | +0.15 | +0.22 |
| - | r1050 | aba (us-them) [inverted] | -0.33 | -0.15 | +0.22 | +0.03 | -0.01 | -0.13 | -0.28 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | -0.18 | +0.17 | +0.10 | +0.05 | -0.07 | -0.19 |
| - | - | digs (us-them) | +0.31 | +0.31 | +0.08 | +0.04 | -0.02 | -0.03 | +0.11 |
| - | - | digs (us-them) ~avg | +0.31 | +0.31 | +0.08 | +0.05 | +0.01 | -0.02 | +0.03 |
| - | - | dirtDeps (us-them) | +0.31 | +0.31 | +0.04 | -0.01 | -0.06 | -0.09 | +0.05 |
| - | - | dirtDeps (us-them) ~avg | +0.31 | +0.31 | +0.03 | -0.00 | -0.05 | -0.08 | -0.02 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | netguns (us-them) | +0.28 | . | . | . | +0.08 | +0.25 | +0.24 |
| - | - | netguns (us-them) ~avg | +0.28 | . | . | . | +0.08 | +0.25 | +0.27 |
| - | - | soup (us-them) | -0.40 | -0.40 | -0.20 | -0.16 | +0.10 | +0.11 | +0.11 |
| - | - | soup (us-them) ~avg | -0.35 | -0.35 | -0.27 | -0.26 | -0.19 | -0.01 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
