# Which metric starts predicting the result first -- every recorded block of g_iter10 merged

219 games, 97 wins. Noise floor about 0.14; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | mines (us-them) | +0.44 | +0.34 | +0.37 | +0.41 | +0.44 | +0.39 | +0.40 |
| r100 | - | mines (us-them) ~avg | +0.45 | +0.32 | +0.36 | +0.39 | +0.43 | +0.44 | +0.42 |
| r100 | - | worth (us-them) | +0.50 | +0.33 | +0.37 | +0.43 | +0.48 | +0.48 | +0.48 |
| r100 | - | worth (us-them) ~avg | +0.50 | +0.31 | +0.37 | +0.41 | +0.47 | +0.49 | +0.50 |
| r250 | - | robots (us-them) | +0.52 | +0.27 | +0.30 | +0.38 | +0.44 | +0.49 | +0.52 |
| r250 | - | robots (us-them) ~avg | +0.54 | +0.29 | +0.30 | +0.34 | +0.42 | +0.50 | +0.54 |
| r250 | - | spawned (us-them) | +0.51 | +0.26 | +0.29 | +0.37 | +0.41 | +0.47 | +0.51 |
| r250 | - | spawned (us-them) ~avg | +0.52 | +0.28 | +0.29 | +0.33 | +0.39 | +0.48 | +0.52 |
| r400 | - | landscapers (us-them) | +0.57 | +0.09 | +0.14 | +0.18 | +0.34 | +0.47 | +0.57 |
| r400 | - | units (us-them) | +0.48 | +0.22 | +0.23 | +0.27 | +0.32 | +0.40 | +0.48 |
| r400 | - | units (us-them) ~avg | +0.50 | +0.24 | +0.24 | +0.25 | +0.30 | +0.40 | +0.50 |
| r400 | - | vaporators (us-them) | +0.41 | -0.02 | +0.11 | +0.24 | +0.31 | +0.41 | +0.36 |
| r500 | - | landscapers (us-them) ~avg | +0.61 | +0.08 | +0.13 | +0.11 | +0.22 | +0.43 | +0.58 |
| r500 | - | vaporators (us-them) ~avg | +0.40 | +0.01 | +0.08 | +0.17 | +0.24 | +0.36 | +0.40 |
| r600 | - | digs (us-them) | +0.59 | +0.02 | +0.14 | +0.14 | +0.20 | +0.34 | +0.50 |
| r600 | - | dirtDeps (us-them) | +0.57 | +0.03 | +0.10 | +0.11 | +0.16 | +0.32 | +0.48 |
| r600 | - | netguns (us-them) | +0.32 | -0.08 | +0.01 | +0.04 | +0.06 | +0.32 | +0.28 |
| r700 | - | digs (us-them) ~avg | +0.53 | -0.00 | +0.12 | +0.11 | +0.15 | +0.25 | +0.42 |
| r700 | - | dirtDeps (us-them) ~avg | +0.51 | +0.01 | +0.09 | +0.08 | +0.11 | +0.22 | +0.40 |
| r700 | - | moves (us-them) | +0.39 | -0.12 | +0.07 | +0.14 | +0.20 | +0.28 | +0.36 |
| r800 | - | moves (us-them) ~avg | +0.37 | -0.15 | +0.00 | +0.11 | +0.15 | +0.23 | +0.32 |
| r1200 | - | drones (us-them) | +0.31 | +0.23 | +0.17 | +0.20 | +0.13 | +0.14 | +0.28 |
| - | - | aba (us-them) [inverted] | -0.22 | +0.17 | +0.04 | -0.05 | -0.01 | -0.11 | -0.19 |
| - | - | aba (us-them) [inverted] ~avg | +0.20 | +0.20 | +0.10 | +0.02 | +0.00 | -0.06 | -0.13 |
| - | - | cov (us-them) | +0.22 | -0.18 | -0.08 | +0.04 | +0.08 | +0.14 | +0.18 |
| - | - | cov (us-them) ~avg | +0.18 | -0.16 | -0.13 | -0.04 | +0.01 | +0.08 | +0.13 |
| - | - | died (us-them) [inverted] | +0.26 | +0.11 | +0.09 | +0.12 | +0.23 | +0.24 | +0.13 |
| - | - | died (us-them) [inverted] ~avg | +0.24 | +0.11 | +0.11 | +0.13 | +0.19 | +0.23 | +0.22 |
| - | - | drones (us-them) ~avg | +0.27 | +0.24 | +0.23 | +0.25 | +0.21 | +0.15 | +0.24 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.15 | +0.15 | +0.09 | +0.03 | +0.06 | +0.06 | +0.02 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.15 | +0.15 | +0.12 | +0.08 | +0.08 | +0.09 | +0.09 |
| - | - | miners (us-them) | +0.24 | +0.14 | +0.14 | +0.11 | +0.04 | +0.11 | +0.22 |
| - | - | miners (us-them) ~avg | +0.24 | +0.21 | +0.20 | +0.20 | +0.14 | +0.10 | +0.16 |
| - | - | netguns (us-them) ~avg | +0.27 | -0.08 | -0.01 | +0.02 | +0.04 | +0.16 | +0.26 |
| - | - | pickups (us-them) | +0.30 | +0.13 | +0.23 | +0.26 | +0.29 | +0.23 | +0.24 |
| - | - | pickups (us-them) ~avg | +0.30 | +0.13 | +0.23 | +0.25 | +0.30 | +0.27 | +0.26 |
| - | - | soup (us-them) | +0.13 | +0.00 | +0.07 | +0.01 | +0.01 | +0.02 | -0.01 |
| - | - | soup (us-them) ~avg | +0.09 | -0.03 | +0.08 | +0.09 | +0.07 | +0.02 | +0.01 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
