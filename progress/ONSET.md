# Which metric starts predicting the result first

24 games, 10 wins. Noise floor about 0.41; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | aba (us-them) [inverted] | +0.67 | +0.56 | +0.60 | +0.57 | +0.17 | +0.00 | -0.01 |
| r50 | - | aba (us-them) [inverted] ~avg | +0.64 | +0.56 | +0.62 | +0.60 | +0.43 | +0.14 | +0.04 |
| r100 | - | drones (us-them) ~avg | +0.32 | +0.32 | +0.10 | -0.04 | -0.06 | -0.08 | -0.11 |
| r150 | - | digs (us-them) | +0.55 | +0.21 | +0.27 | -0.13 | -0.14 | +0.08 | +0.44 |
| r150 | - | digs (us-them) ~avg | +0.47 | +0.21 | +0.32 | +0.01 | -0.09 | -0.05 | +0.21 |
| r250 | - | netguns (us-them) | +0.45 | . | +0.26 | +0.35 | +0.39 | +0.31 | +0.41 |
| r250 | - | netguns (us-them) ~avg | +0.38 | . | +0.26 | +0.36 | +0.38 | +0.37 | +0.38 |
| r300 | - | pickups (us-them) | +0.48 | +0.25 | +0.20 | +0.42 | +0.46 | +0.47 | +0.43 |
| r300 | - | pickups (us-them) ~avg | +0.48 | +0.25 | +0.22 | +0.36 | +0.45 | +0.48 | +0.45 |
| r500 | - | vaporators (us-them) | +0.45 | -0.08 | -0.08 | +0.11 | +0.20 | +0.15 | +0.16 |
| r700 | - | died (us-them) [inverted] | +0.42 | . | +0.00 | +0.01 | +0.14 | -0.10 | +0.37 |
| r700 | r300 | landscapers (us-them) | +0.45 | +0.20 | -0.13 | -0.40 | -0.16 | +0.23 | +0.43 |
| r700 | r100 | miners (us-them) | +0.53 | -0.36 | -0.50 | -0.28 | +0.03 | -0.14 | +0.46 |
| r700 | r200 | robots (us-them) | +0.52 | +0.16 | -0.44 | -0.43 | -0.07 | +0.11 | +0.48 |
| r700 | r200 | units (us-them) | -0.45 | -0.03 | -0.39 | -0.45 | -0.12 | +0.02 | +0.39 |
| r750 | - | dirtDeps (us-them) | +0.57 | +0.15 | +0.19 | -0.15 | -0.17 | +0.14 | +0.47 |
| r850 | r200 | spawned (us-them) | -0.45 | +0.16 | -0.45 | -0.41 | -0.12 | +0.16 | +0.30 |
| r950 | - | dirtDeps (us-them) ~avg | +0.50 | +0.15 | +0.24 | -0.04 | -0.12 | -0.04 | +0.25 |
| r950 | - | vaporators (us-them) ~avg | +0.49 | -0.08 | -0.06 | +0.03 | +0.10 | +0.18 | +0.23 |
| r950 | - | worth (us-them) | +0.42 | +0.03 | -0.28 | -0.26 | +0.00 | -0.00 | +0.22 |
| r1000 | - | landscapers (us-them) ~avg | +0.35 | +0.20 | +0.04 | -0.26 | -0.25 | -0.08 | +0.18 |
| r1000 | r250 | robots (us-them) ~avg | -0.42 | +0.17 | -0.24 | -0.42 | -0.33 | -0.12 | +0.22 |
| r1200 | - | worth (us-them) ~avg | +0.31 | +0.03 | -0.22 | -0.29 | -0.22 | -0.09 | +0.08 |
| - | - | cov (us-them) | -0.28 | -0.16 | -0.00 | -0.15 | -0.18 | -0.21 | -0.23 |
| - | - | cov (us-them) ~avg | -0.22 | -0.11 | -0.04 | -0.06 | -0.12 | -0.16 | -0.20 |
| - | - | died (us-them) [inverted] ~avg | +0.26 | . | +0.00 | -0.06 | +0.09 | +0.03 | +0.26 |
| - | - | drones (us-them) | +0.32 | +0.32 | -0.10 | -0.16 | -0.05 | -0.15 | -0.08 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | -0.23 | -0.14 | +0.15 | -0.03 | -0.13 | -0.23 | -0.23 |
| - | - | hqBuried (us-them) [inverted] ~avg | -0.22 | -0.14 | +0.04 | -0.22 | -0.21 | -0.22 | -0.22 |
| - | r150 | miners (us-them) ~avg | -0.47 | -0.25 | -0.46 | -0.47 | -0.36 | -0.23 | +0.01 |
| - | r200 | mines (us-them) | -0.37 | -0.00 | -0.37 | -0.29 | -0.18 | -0.23 | -0.13 |
| - | r300 | mines (us-them) ~avg | -0.33 | +0.03 | -0.27 | -0.33 | -0.31 | -0.28 | -0.23 |
| - | r200 | moves (us-them) | -0.44 | -0.23 | -0.35 | -0.44 | -0.38 | -0.29 | -0.27 |
| - | r200 | moves (us-them) ~avg | -0.42 | -0.20 | -0.31 | -0.40 | -0.41 | -0.35 | -0.31 |
| - | r450 | soup (us-them) | -0.45 | -0.26 | +0.21 | -0.15 | -0.20 | -0.31 | -0.20 |
| - | r500 | soup (us-them) ~avg | -0.37 | -0.26 | -0.17 | -0.16 | -0.19 | -0.37 | -0.32 |
| - | r250 | spawned (us-them) ~avg | -0.42 | +0.17 | -0.24 | -0.42 | -0.34 | -0.13 | +0.08 |
| - | r300 | units (us-them) ~avg | -0.43 | +0.01 | -0.21 | -0.43 | -0.34 | -0.18 | +0.08 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
