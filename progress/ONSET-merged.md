# Which metric starts predicting the result first -- every recorded block of g_iter18 merged

138 games, 70 wins. Noise floor about 0.17; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r50 | - | mines (us-them) | +0.42 | +0.24 | +0.38 | +0.37 | +0.28 | +0.24 | +0.26 |
| r50 | - | mines (us-them) ~avg | +0.39 | +0.27 | +0.34 | +0.39 | +0.36 | +0.32 | +0.30 |
| r100 | - | drones (us-them) ~avg | +0.43 | +0.31 | +0.28 | +0.43 | +0.37 | +0.23 | +0.28 |
| r100 | - | robots (us-them) | +0.48 | +0.33 | +0.48 | +0.44 | +0.31 | +0.29 | +0.30 |
| r100 | - | spawned (us-them) | +0.45 | +0.33 | +0.45 | +0.34 | +0.19 | +0.17 | +0.25 |
| r100 | - | units (us-them) | +0.42 | +0.32 | +0.41 | +0.41 | +0.33 | +0.33 | +0.38 |
| r150 | - | robots (us-them) ~avg | +0.46 | +0.27 | +0.41 | +0.46 | +0.42 | +0.38 | +0.34 |
| r200 | - | spawned (us-them) ~avg | +0.41 | +0.26 | +0.38 | +0.39 | +0.32 | +0.25 | +0.24 |
| r200 | - | units (us-them) ~avg | +0.42 | +0.25 | +0.35 | +0.40 | +0.39 | +0.42 | +0.41 |
| r200 | - | worth (us-them) | +0.40 | +0.28 | +0.40 | +0.40 | +0.26 | +0.25 | +0.19 |
| r200 | - | worth (us-them) ~avg | +0.40 | +0.26 | +0.34 | +0.40 | +0.35 | +0.30 | +0.25 |
| r300 | - | drones (us-them) | +0.33 | +0.31 | +0.17 | +0.33 | +0.24 | +0.14 | +0.31 |
| r300 | - | landscapers (us-them) | +0.48 | +0.07 | +0.24 | +0.32 | +0.31 | +0.36 | +0.40 |
| r300 | - | pickups (us-them) | +0.45 | +0.08 | +0.17 | +0.41 | +0.43 | +0.39 | +0.34 |
| r300 | - | pickups (us-them) ~avg | +0.46 | +0.08 | +0.19 | +0.38 | +0.43 | +0.45 | +0.44 |
| r350 | - | died (us-them) [inverted] | +0.32 | +0.08 | +0.18 | +0.29 | +0.30 | +0.30 | +0.27 |
| r350 | - | digs (us-them) | +0.60 | +0.18 | +0.16 | +0.29 | +0.35 | +0.45 | +0.51 |
| r350 | - | dirtDeps (us-them) | +0.58 | +0.08 | +0.16 | +0.27 | +0.34 | +0.44 | +0.49 |
| r450 | - | died (us-them) [inverted] ~avg | +0.34 | +0.08 | +0.19 | +0.27 | +0.30 | +0.31 | +0.34 |
| r450 | - | digs (us-them) ~avg | +0.55 | +0.17 | +0.13 | +0.23 | +0.30 | +0.38 | +0.47 |
| r450 | - | dirtDeps (us-them) ~avg | +0.53 | +0.08 | +0.12 | +0.21 | +0.28 | +0.38 | +0.46 |
| r450 | - | landscapers (us-them) ~avg | +0.48 | +0.09 | +0.14 | +0.22 | +0.28 | +0.42 | +0.42 |
| r550 | - | moves (us-them) | +0.38 | +0.18 | +0.27 | +0.30 | +0.28 | +0.32 | +0.38 |
| r550 | - | moves (us-them) ~avg | +0.36 | +0.12 | +0.28 | +0.29 | +0.30 | +0.31 | +0.36 |
| r650 | - | netguns (us-them) | +0.46 | . | +0.13 | +0.09 | +0.11 | +0.27 | +0.36 |
| r750 | - | netguns (us-them) ~avg | +0.43 | . | +0.11 | +0.11 | +0.10 | +0.19 | +0.34 |
| - | r950 | aba (us-them) [inverted] | -0.37 | -0.21 | -0.17 | -0.03 | -0.05 | -0.10 | -0.29 |
| - | - | aba (us-them) [inverted] ~avg | -0.29 | -0.20 | -0.24 | -0.10 | -0.06 | -0.10 | -0.18 |
| - | - | cov (us-them) | +0.22 | +0.02 | +0.17 | +0.22 | +0.14 | +0.07 | +0.01 |
| - | - | cov (us-them) ~avg | +0.14 | -0.03 | +0.09 | +0.11 | +0.14 | +0.11 | +0.05 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | hqBuried (us-them) [inverted] | +0.30 | +0.27 | +0.25 | +0.21 | +0.18 | +0.12 | +0.13 |
| - | - | hqBuried (us-them) [inverted] ~avg | +0.27 | +0.27 | +0.17 | +0.06 | +0.08 | +0.09 | +0.11 |
| - | - | miners (us-them) | +0.32 | +0.21 | +0.32 | +0.11 | +0.07 | +0.15 | +0.18 |
| - | - | miners (us-them) ~avg | +0.28 | +0.13 | +0.28 | +0.22 | +0.16 | +0.17 | +0.18 |
| - | - | soup (us-them) | -0.18 | -0.18 | -0.18 | +0.00 | +0.07 | -0.05 | -0.13 |
| - | - | soup (us-them) ~avg | -0.18 | -0.10 | -0.18 | -0.13 | -0.02 | +0.00 | -0.06 |
| - | - | vaporators (us-them) | +0.15 | +0.08 | -0.00 | +0.01 | -0.00 | +0.12 | +0.05 |
| - | - | vaporators (us-them) ~avg | +0.15 | +0.06 | +0.00 | -0.01 | -0.01 | +0.06 | +0.09 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
