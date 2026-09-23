# Which metric starts predicting the result first

23 games, 12 wins. Noise floor about 0.42; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | moves (us-them) | +0.39 | +0.39 | +0.10 | +0.07 | +0.10 | +0.16 | +0.01 |
| r50 | - | moves (us-them) ~avg | +0.41 | +0.41 | +0.17 | +0.11 | +0.10 | +0.14 | +0.03 |
| r200 | - | landscapers (us-them) | +0.68 | -0.23 | +0.50 | +0.42 | +0.46 | +0.52 | +0.53 |
| r200 | - | units (us-them) | +0.34 | -0.05 | +0.32 | +0.34 | +0.33 | +0.22 | +0.20 |
| r250 | - | digs (us-them) | +0.68 | -0.01 | +0.23 | +0.37 | +0.40 | +0.49 | +0.55 |
| r250 | - | dirtDeps (us-them) | +0.68 | -0.04 | +0.22 | +0.36 | +0.38 | +0.47 | +0.55 |
| r250 | - | landscapers (us-them) ~avg | +0.73 | -0.26 | +0.25 | +0.48 | +0.54 | +0.60 | +0.60 |
| r350 | - | aba (us-them) [inverted] | +0.34 | -0.06 | +0.11 | +0.16 | +0.31 | +0.20 | +0.29 |
| r350 | - | digs (us-them) ~avg | +0.62 | -0.07 | +0.14 | +0.29 | +0.35 | +0.42 | +0.47 |
| r350 | - | dirtDeps (us-them) ~avg | +0.63 | -0.10 | +0.14 | +0.28 | +0.33 | +0.40 | +0.47 |
| r350 | - | units (us-them) ~avg | +0.35 | -0.18 | +0.14 | +0.27 | +0.33 | +0.34 | +0.22 |
| r700 | - | aba (us-them) [inverted] ~avg | +0.34 | -0.12 | +0.09 | +0.13 | +0.23 | +0.23 | +0.33 |
| - | - | cov (us-them) | +0.26 | +0.26 | -0.25 | -0.07 | -0.01 | +0.01 | -0.14 |
| - | - | cov (us-them) ~avg | +0.21 | +0.21 | -0.13 | -0.11 | -0.07 | -0.01 | -0.13 |
| - | r200 | died (us-them) [inverted] | -0.32 | . | -0.32 | +0.21 | +0.17 | +0.02 | -0.04 |
| - | r200 | died (us-them) [inverted] ~avg | -0.35 | . | -0.35 | -0.05 | +0.08 | +0.13 | +0.12 |
| - | - | drones (us-them) | -0.23 | -0.23 | -0.03 | -0.03 | +0.05 | -0.04 | +0.11 |
| - | - | drones (us-them) ~avg | -0.23 | -0.23 | -0.11 | -0.05 | -0.04 | -0.05 | -0.02 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | r850 | miners (us-them) | -0.49 | +0.18 | +0.02 | +0.14 | +0.08 | -0.03 | -0.35 |
| - | r1200 | miners (us-them) ~avg | -0.30 | +0.01 | +0.06 | +0.08 | +0.11 | +0.08 | -0.16 |
| - | - | mines (us-them) | +0.11 | -0.04 | +0.07 | +0.07 | +0.10 | +0.07 | -0.05 |
| - | - | mines (us-them) ~avg | +0.09 | -0.07 | +0.03 | +0.05 | +0.08 | +0.08 | -0.07 |
| - | r950 | netguns (us-them) | -0.49 | . | -0.28 | -0.19 | -0.14 | -0.06 | -0.19 |
| - | r950 | netguns (us-them) ~avg | -0.44 | . | -0.27 | -0.27 | -0.20 | -0.16 | -0.25 |
| - | - | pickups (us-them) | +0.22 | . | +0.09 | -0.10 | +0.03 | +0.21 | +0.14 |
| - | - | pickups (us-them) ~avg | +0.19 | . | +0.09 | -0.03 | -0.01 | +0.14 | +0.17 |
| - | - | robots (us-them) | -0.39 | -0.00 | +0.19 | +0.21 | +0.22 | +0.16 | +0.14 |
| - | - | robots (us-them) ~avg | -0.43 | -0.20 | +0.07 | +0.14 | +0.19 | +0.21 | +0.13 |
| - | r500 | soup (us-them) | -0.68 | -0.05 | -0.07 | +0.20 | -0.15 | -0.24 | -0.31 |
| - | r850 | soup (us-them) ~avg | -0.64 | +0.12 | -0.08 | +0.08 | +0.02 | -0.23 | -0.30 |
| - | - | spawned (us-them) | -0.39 | -0.00 | +0.24 | +0.14 | +0.17 | +0.15 | +0.14 |
| - | - | spawned (us-them) ~avg | -0.43 | -0.20 | +0.09 | +0.14 | +0.17 | +0.18 | +0.09 |
| - | r200 | vaporators (us-them) | -0.40 | . | -0.36 | -0.33 | -0.33 | -0.02 | -0.07 |
| - | r200 | vaporators (us-them) ~avg | -0.40 | . | -0.32 | -0.39 | -0.39 | -0.22 | -0.11 |
| - | - | worth (us-them) | -0.26 | -0.17 | -0.06 | -0.02 | -0.05 | -0.02 | -0.06 |
| - | - | worth (us-them) ~avg | -0.24 | -0.23 | -0.11 | -0.07 | -0.06 | -0.04 | -0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
