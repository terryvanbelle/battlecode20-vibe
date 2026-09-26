# Which metric starts predicting the result first -- every recorded block of cand81s8 merged

140 games, 32 wins. Noise floor about 0.17; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.42 | +0.36 | +0.33 | +0.38 | +0.42 | +0.40 | +0.39 |
| r50 | - | mines (us-them) ~avg | +0.43 | +0.39 | +0.35 | +0.39 | +0.43 | +0.43 | +0.41 |
| r100 | - | drones (us-them) | +0.38 | +0.33 | +0.27 | +0.35 | +0.37 | +0.31 | +0.38 |
| r100 | - | drones (us-them) ~avg | +0.38 | +0.33 | +0.29 | +0.34 | +0.36 | +0.36 | +0.38 |
| r100 | - | robots (us-them) ~avg | +0.50 | +0.33 | +0.31 | +0.37 | +0.43 | +0.50 | +0.50 |
| r100 | - | worth (us-them) | +0.49 | +0.36 | +0.39 | +0.44 | +0.48 | +0.48 | +0.47 |
| r100 | - | worth (us-them) ~avg | +0.50 | +0.36 | +0.38 | +0.45 | +0.48 | +0.50 | +0.49 |
| r200 | - | robots (us-them) | +0.50 | +0.36 | +0.30 | +0.36 | +0.45 | +0.48 | +0.50 |
| r300 | - | spawned (us-them) | +0.48 | +0.36 | +0.26 | +0.30 | +0.40 | +0.45 | +0.47 |
| r300 | - | spawned (us-them) ~avg | +0.47 | +0.33 | +0.28 | +0.32 | +0.38 | +0.45 | +0.47 |
| r300 | - | vaporators (us-them) | +0.43 | +0.13 | +0.16 | +0.30 | +0.34 | +0.42 | +0.37 |
| r350 | - | miners (us-them) | +0.38 | +0.19 | +0.20 | +0.30 | +0.34 | +0.35 | +0.36 |
| r350 | - | miners (us-them) ~avg | +0.41 | +0.16 | +0.22 | +0.28 | +0.36 | +0.41 | +0.41 |
| r350 | - | units (us-them) | +0.49 | +0.39 | +0.23 | +0.25 | +0.39 | +0.46 | +0.48 |
| r350 | - | units (us-them) ~avg | +0.49 | +0.31 | +0.26 | +0.27 | +0.35 | +0.46 | +0.49 |
| r400 | - | cov (us-them) | +0.48 | -0.12 | +0.09 | +0.24 | +0.30 | +0.42 | +0.46 |
| r400 | - | moves (us-them) | +0.47 | -0.01 | +0.22 | +0.24 | +0.33 | +0.44 | +0.47 |
| r400 | - | moves (us-them) ~avg | +0.47 | -0.10 | +0.22 | +0.24 | +0.30 | +0.41 | +0.46 |
| r450 | - | died (us-them) [inverted] | +0.34 | +0.06 | +0.21 | +0.24 | +0.30 | +0.30 | +0.34 |
| r450 | - | pickups (us-them) ~avg | +0.34 | +0.04 | +0.19 | +0.22 | +0.29 | +0.32 | +0.29 |
| r450 | - | vaporators (us-them) ~avg | +0.42 | +0.13 | +0.16 | +0.25 | +0.29 | +0.39 | +0.41 |
| r500 | - | pickups (us-them) | +0.31 | +0.04 | +0.20 | +0.23 | +0.30 | +0.30 | +0.20 |
| r550 | - | cov (us-them) ~avg | +0.45 | -0.18 | +0.02 | +0.15 | +0.21 | +0.34 | +0.42 |
| r550 | - | died (us-them) [inverted] ~avg | +0.36 | +0.02 | +0.19 | +0.24 | +0.27 | +0.31 | +0.36 |
| r550 | - | landscapers (us-them) | +0.43 | +0.19 | +0.04 | -0.02 | +0.16 | +0.36 | +0.39 |
| r750 | - | landscapers (us-them) ~avg | +0.44 | +0.19 | +0.03 | -0.00 | +0.08 | +0.23 | +0.36 |
| r1200 | - | digs (us-them) | +0.31 | +0.28 | +0.08 | +0.06 | +0.02 | +0.06 | +0.19 |
| - | r1050 | aba (us-them) [inverted] | -0.33 | +0.06 | +0.01 | +0.09 | +0.03 | -0.15 | -0.28 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | +0.03 | +0.05 | +0.09 | +0.09 | -0.08 | -0.23 |
| - | - | digs (us-them) ~avg | +0.28 | +0.28 | +0.08 | +0.07 | +0.05 | +0.04 | +0.12 |
| - | - | dirtDeps (us-them) | +0.26 | +0.26 | +0.05 | +0.02 | -0.01 | +0.01 | +0.13 |
| - | - | dirtDeps (us-them) ~avg | +0.26 | +0.26 | +0.04 | +0.02 | -0.00 | -0.01 | +0.06 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.32 | +0.32 | -0.05 | +0.23 | -0.06 | -0.05 | -0.05 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.32 | +0.32 | -0.05 | +0.13 | -0.05 | -0.05 | -0.05 |
| - | - | netguns (us-them) | +0.25 | . | +0.04 | +0.04 | +0.11 | +0.23 | +0.23 |
| - | - | netguns (us-them) ~avg | +0.26 | . | +0.04 | +0.04 | +0.09 | +0.24 | +0.26 |
| - | - | soup (us-them) | -0.28 | -0.28 | -0.06 | -0.03 | +0.13 | +0.00 | -0.05 |
| - | - | soup (us-them) ~avg | -0.26 | -0.26 | -0.19 | -0.14 | -0.05 | -0.01 | -0.03 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
