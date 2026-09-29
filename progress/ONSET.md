# Which metric starts predicting the result first

47 games, 20 wins. Noise floor about 0.29; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.47 | +0.31 | +0.40 | +0.42 | +0.44 | +0.43 | +0.41 |
| r50 | - | mines (us-them) ~avg | +0.49 | +0.33 | +0.39 | +0.42 | +0.46 | +0.49 | +0.46 |
| r100 | - | hqBuried (us-them) [inverted] | +0.33 | +0.33 | +0.31 | . | . | . | . |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.33 | +0.33 | +0.29 | +0.08 | +0.08 | +0.08 | +0.08 |
| r100 | - | worth (us-them) | +0.49 | +0.32 | +0.43 | +0.49 | +0.47 | +0.46 | +0.42 |
| r100 | - | worth (us-them) ~avg | +0.50 | +0.33 | +0.43 | +0.48 | +0.49 | +0.49 | +0.45 |
| r150 | - | vaporators (us-them) ~avg | +0.33 | +0.05 | +0.33 | +0.28 | +0.24 | +0.22 | +0.28 |
| r200 | - | robots (us-them) | +0.50 | +0.16 | +0.34 | +0.43 | +0.47 | +0.43 | +0.41 |
| r200 | - | spawned (us-them) | +0.44 | +0.15 | +0.30 | +0.38 | +0.44 | +0.36 | +0.40 |
| r250 | - | cov (us-them) | +0.34 | +0.05 | +0.15 | +0.33 | +0.32 | +0.26 | +0.16 |
| r250 | - | died (us-them) [inverted] | +0.45 | +0.18 | +0.25 | +0.33 | +0.39 | +0.43 | +0.26 |
| r250 | - | pickups (us-them) | +0.49 | +0.01 | +0.19 | +0.30 | +0.45 | +0.37 | +0.22 |
| r250 | - | robots (us-them) ~avg | +0.48 | +0.17 | +0.27 | +0.36 | +0.43 | +0.48 | +0.44 |
| r250 | - | units (us-them) | +0.51 | +0.25 | +0.26 | +0.37 | +0.45 | +0.43 | +0.42 |
| r300 | - | died (us-them) [inverted] ~avg | +0.46 | +0.22 | +0.27 | +0.33 | +0.37 | +0.45 | +0.41 |
| r300 | - | digs (us-them) | +0.49 | +0.29 | +0.17 | +0.34 | +0.41 | +0.46 | +0.47 |
| r300 | - | landscapers (us-them) | +0.55 | +0.23 | +0.21 | +0.34 | +0.49 | +0.52 | +0.44 |
| r300 | - | pickups (us-them) ~avg | +0.46 | +0.01 | +0.18 | +0.31 | +0.40 | +0.45 | +0.27 |
| r350 | - | dirtDeps (us-them) | +0.45 | +0.23 | +0.20 | +0.28 | +0.36 | +0.42 | +0.43 |
| r350 | - | spawned (us-them) ~avg | +0.43 | +0.15 | +0.23 | +0.30 | +0.37 | +0.43 | +0.41 |
| r350 | - | units (us-them) ~avg | +0.47 | +0.23 | +0.22 | +0.30 | +0.38 | +0.47 | +0.47 |
| r400 | - | digs (us-them) ~avg | +0.48 | +0.29 | +0.14 | +0.25 | +0.34 | +0.41 | +0.46 |
| r400 | - | landscapers (us-them) ~avg | +0.50 | +0.22 | +0.12 | +0.20 | +0.35 | +0.49 | +0.50 |
| r450 | - | dirtDeps (us-them) ~avg | +0.44 | +0.22 | +0.17 | +0.23 | +0.29 | +0.36 | +0.42 |
| r600 | - | moves (us-them) | +0.35 | +0.08 | +0.01 | +0.10 | +0.20 | +0.30 | +0.35 |
| r700 | - | vaporators (us-them) | +0.33 | +0.05 | +0.28 | +0.27 | +0.17 | +0.23 | +0.32 |
| r850 | - | moves (us-them) ~avg | +0.33 | +0.12 | +0.02 | +0.09 | +0.15 | +0.24 | +0.32 |
| r950 | - | soup (us-them) ~avg | +0.32 | +0.12 | +0.02 | +0.15 | +0.18 | +0.28 | +0.23 |
| r1000 | - | drones (us-them) | +0.33 | +0.19 | +0.18 | +0.11 | +0.20 | +0.18 | +0.29 |
| r1200 | - | drones (us-them) ~avg | +0.30 | +0.19 | +0.19 | +0.18 | +0.18 | +0.20 | +0.25 |
| - | - | aba (us-them) [inverted] | -0.16 | -0.16 | -0.09 | +0.05 | +0.10 | +0.03 | -0.11 |
| - | - | aba (us-them) [inverted] ~avg | -0.12 | -0.12 | -0.09 | -0.03 | +0.05 | +0.07 | -0.03 |
| - | - | cov (us-them) ~avg | +0.30 | +0.05 | +0.04 | +0.20 | +0.27 | +0.30 | +0.26 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.23 | +0.02 | +0.12 | +0.21 | +0.09 | +0.17 | +0.22 |
| - | - | miners (us-them) ~avg | +0.22 | +0.06 | +0.14 | +0.22 | +0.20 | +0.20 | +0.22 |
| - | - | netguns (us-them) | +0.24 | . | +0.03 | +0.05 | +0.07 | +0.20 | +0.20 |
| - | - | netguns (us-them) ~avg | +0.17 | . | +0.03 | +0.02 | +0.05 | +0.10 | +0.17 |
| - | - | soup (us-them) | +0.27 | +0.10 | -0.08 | +0.11 | +0.05 | +0.24 | +0.10 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
