# Which metric starts predicting the result first

21 games, 10 wins. Noise floor about 0.44; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | worth (us-them) | +0.53 | +0.28 | +0.23 | +0.53 | +0.50 | +0.46 | +0.41 |
| r50 | - | worth (us-them) ~avg | +0.52 | +0.30 | +0.25 | +0.50 | +0.52 | +0.50 | +0.44 |
| r100 | - | cov (us-them) | +0.38 | +0.35 | +0.22 | +0.38 | +0.35 | +0.28 | +0.18 |
| r100 | - | cov (us-them) ~avg | +0.40 | +0.34 | +0.28 | +0.40 | +0.39 | +0.29 | +0.20 |
| r150 | - | drones (us-them) | +0.60 | +0.27 | +0.33 | +0.60 | +0.42 | +0.31 | +0.30 |
| r150 | - | drones (us-them) ~avg | +0.63 | +0.27 | +0.35 | +0.63 | +0.56 | +0.47 | +0.33 |
| r150 | - | pickups (us-them) | +0.55 | +0.21 | +0.28 | +0.53 | +0.55 | +0.46 | +0.47 |
| r150 | r850 | soup (us-them) ~avg | +0.33 | +0.09 | +0.28 | +0.03 | -0.14 | -0.22 | -0.32 |
| r250 | - | netguns (us-them) | +0.47 | . | +0.27 | +0.34 | +0.34 | +0.40 | +0.40 |
| r250 | - | netguns (us-them) ~avg | +0.44 | . | +0.27 | +0.33 | +0.33 | +0.41 | +0.44 |
| r300 | - | died (us-them) [inverted] | +0.43 | . | -0.12 | +0.43 | +0.42 | +0.36 | +0.15 |
| r300 | - | died (us-them) [inverted] ~avg | +0.44 | . | -0.07 | +0.31 | +0.43 | +0.39 | +0.29 |
| r300 | - | mines (us-them) | +0.38 | +0.08 | +0.03 | +0.38 | +0.32 | +0.31 | +0.28 |
| r300 | - | mines (us-them) ~avg | +0.41 | +0.09 | +0.05 | +0.41 | +0.40 | +0.39 | +0.31 |
| r300 | - | pickups (us-them) ~avg | +0.54 | +0.21 | +0.29 | +0.47 | +0.54 | +0.49 | +0.48 |
| r300 | - | robots (us-them) | +0.53 | -0.01 | +0.05 | +0.36 | +0.44 | +0.51 | +0.46 |
| r300 | - | vaporators (us-them) | +0.52 | +0.09 | +0.09 | +0.50 | +0.46 | +0.38 | +0.37 |
| r300 | - | vaporators (us-them) ~avg | +0.51 | +0.09 | +0.08 | +0.50 | +0.51 | +0.43 | +0.40 |
| r350 | - | spawned (us-them) | +0.52 | -0.01 | +0.06 | +0.28 | +0.39 | +0.49 | +0.46 |
| r400 | - | robots (us-them) ~avg | +0.49 | +0.15 | +0.02 | +0.23 | +0.33 | +0.48 | +0.48 |
| r450 | - | spawned (us-them) ~avg | +0.47 | +0.15 | +0.03 | +0.20 | +0.27 | +0.46 | +0.46 |
| r450 | - | units (us-them) | +0.53 | -0.25 | -0.08 | +0.03 | +0.21 | +0.51 | +0.46 |
| r500 | - | landscapers (us-them) | +0.60 | -0.14 | -0.08 | -0.29 | -0.07 | +0.50 | +0.59 |
| r600 | - | units (us-them) ~avg | +0.46 | -0.11 | -0.14 | -0.06 | +0.03 | +0.35 | +0.46 |
| r800 | r300 | landscapers (us-them) ~avg | +0.56 | -0.14 | -0.10 | -0.33 | -0.27 | +0.15 | +0.42 |
| r850 | r250 | digs (us-them) | +0.54 | -0.11 | -0.27 | -0.43 | -0.37 | +0.03 | +0.37 |
| r900 | r200 | dirtDeps (us-them) | +0.50 | -0.25 | -0.31 | -0.44 | -0.40 | -0.01 | +0.33 |
| r1100 | r300 | digs (us-them) ~avg | -0.42 | -0.11 | -0.22 | -0.41 | -0.41 | -0.21 | +0.13 |
| r1150 | r250 | dirtDeps (us-them) ~avg | -0.44 | -0.25 | -0.29 | -0.44 | -0.43 | -0.25 | +0.09 |
| - | - | aba (us-them) [inverted] | +0.46 | +0.00 | +0.08 | +0.10 | +0.03 | +0.00 | -0.15 |
| - | - | aba (us-them) [inverted] ~avg | +0.46 | +0.11 | +0.09 | +0.14 | +0.09 | -0.02 | -0.07 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r500 | hqBuried (us-them) [inverted] | -0.38 | -0.23 | . | -0.28 | -0.28 | -0.30 | -0.34 |
| - | r700 | hqBuried (us-them) [inverted] ~avg | -0.38 | -0.23 | -0.20 | -0.25 | -0.25 | -0.29 | -0.33 |
| - | - | miners (us-them) | -0.29 | -0.24 | -0.22 | +0.13 | +0.16 | +0.08 | +0.20 |
| - | - | miners (us-them) ~avg | +0.25 | -0.11 | -0.22 | -0.01 | +0.06 | +0.05 | +0.14 |
| - | - | moves (us-them) | +0.29 | -0.15 | -0.29 | +0.00 | +0.10 | +0.23 | +0.29 |
| - | - | moves (us-them) ~avg | -0.25 | -0.16 | -0.25 | -0.10 | +0.00 | +0.13 | +0.24 |
| - | r250 | soup (us-them) | -0.42 | +0.21 | +0.12 | -0.42 | -0.27 | -0.22 | -0.32 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
