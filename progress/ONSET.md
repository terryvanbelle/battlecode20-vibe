# Which metric starts predicting the result first

44 games, 25 wins. Noise floor about 0.30; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | drones (us-them) | +0.55 | +0.36 | +0.40 | +0.43 | +0.55 | +0.48 | +0.44 |
| r100 | - | drones (us-them) ~avg | +0.55 | +0.36 | +0.42 | +0.43 | +0.51 | +0.54 | +0.49 |
| r100 | - | hqBuried (us-them) [inverted] | +0.36 | +0.33 | +0.25 | +0.30 | +0.20 | +0.20 | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.35 | +0.33 | +0.22 | +0.26 | +0.21 | +0.20 | +0.23 |
| r150 | - | mines (us-them) | +0.55 | +0.18 | +0.38 | +0.36 | +0.51 | +0.52 | +0.47 |
| r150 | - | vaporators (us-them) | +0.38 | +0.19 | +0.35 | +0.36 | +0.26 | +0.30 | +0.24 |
| r150 | - | vaporators (us-them) ~avg | +0.37 | +0.19 | +0.33 | +0.37 | +0.32 | +0.30 | +0.29 |
| r150 | - | worth (us-them) | +0.50 | +0.25 | +0.39 | +0.47 | +0.49 | +0.47 | +0.39 |
| r150 | - | worth (us-them) ~avg | +0.50 | +0.28 | +0.36 | +0.45 | +0.46 | +0.50 | +0.44 |
| r200 | - | mines (us-them) ~avg | +0.55 | +0.23 | +0.33 | +0.37 | +0.42 | +0.55 | +0.52 |
| r250 | - | pickups (us-them) | +0.59 | -0.03 | +0.22 | +0.49 | +0.47 | +0.59 | +0.53 |
| r250 | - | robots (us-them) | +0.56 | +0.16 | +0.29 | +0.39 | +0.52 | +0.52 | +0.48 |
| r250 | - | robots (us-them) ~avg | +0.56 | +0.17 | +0.26 | +0.37 | +0.46 | +0.56 | +0.53 |
| r250 | - | spawned (us-them) | +0.58 | +0.17 | +0.29 | +0.37 | +0.55 | +0.57 | +0.51 |
| r250 | - | spawned (us-them) ~avg | +0.60 | +0.19 | +0.27 | +0.36 | +0.46 | +0.59 | +0.57 |
| r300 | - | pickups (us-them) ~avg | +0.64 | -0.03 | +0.18 | +0.39 | +0.46 | +0.59 | +0.63 |
| r400 | - | units (us-them) | +0.54 | +0.07 | +0.19 | +0.20 | +0.41 | +0.46 | +0.49 |
| r450 | - | units (us-them) ~avg | +0.51 | +0.09 | +0.13 | +0.19 | +0.29 | +0.48 | +0.50 |
| r500 | - | landscapers (us-them) | +0.43 | -0.08 | +0.10 | -0.11 | +0.04 | +0.34 | +0.36 |
| r550 | - | netguns (us-them) | +0.45 | . | +0.16 | +0.29 | +0.23 | +0.45 | +0.33 |
| r550 | - | netguns (us-them) ~avg | +0.38 | . | +0.16 | +0.27 | +0.26 | +0.35 | +0.37 |
| r700 | - | landscapers (us-them) ~avg | +0.38 | +0.00 | +0.07 | +0.00 | -0.04 | +0.23 | +0.32 |
| r850 | - | cov (us-them) | +0.40 | +0.03 | -0.09 | -0.07 | -0.02 | +0.22 | +0.34 |
| r950 | - | digs (us-them) | +0.48 | +0.28 | -0.02 | -0.01 | -0.08 | +0.05 | +0.28 |
| r950 | - | dirtDeps (us-them) | +0.48 | +0.30 | -0.05 | -0.02 | -0.08 | +0.04 | +0.27 |
| r1150 | - | digs (us-them) ~avg | +0.35 | +0.28 | +0.03 | +0.03 | -0.03 | +0.01 | +0.17 |
| r1150 | - | dirtDeps (us-them) ~avg | +0.35 | +0.30 | +0.00 | +0.01 | -0.04 | -0.00 | +0.16 |
| - | r900 | aba (us-them) [inverted] | -0.33 | +0.13 | +0.12 | +0.10 | +0.15 | -0.15 | -0.32 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | +0.12 | +0.17 | +0.13 | +0.14 | +0.03 | -0.20 |
| - | - | cov (us-them) ~avg | +0.29 | -0.06 | -0.11 | -0.11 | -0.14 | +0.05 | +0.19 |
| - | - | died (us-them) [inverted] | +0.19 | -0.13 | +0.05 | +0.19 | +0.13 | +0.06 | +0.02 |
| - | - | died (us-them) [inverted] ~avg | +0.17 | -0.13 | -0.02 | +0.10 | +0.14 | +0.10 | +0.01 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.28 | +0.01 | +0.08 | +0.18 | +0.28 | +0.23 | +0.13 |
| - | - | miners (us-them) ~avg | +0.23 | +0.01 | -0.01 | +0.10 | +0.21 | +0.22 | +0.12 |
| - | - | moves (us-them) | -0.37 | -0.10 | -0.08 | -0.03 | +0.00 | +0.21 | +0.25 |
| - | r50 | moves (us-them) ~avg | -0.43 | -0.27 | -0.18 | -0.10 | -0.06 | +0.09 | +0.13 |
| - | r750 | soup (us-them) | -0.44 | -0.05 | -0.18 | -0.05 | +0.02 | -0.19 | -0.38 |
| - | r750 | soup (us-them) ~avg | -0.43 | -0.05 | -0.20 | -0.20 | -0.20 | -0.15 | -0.37 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
