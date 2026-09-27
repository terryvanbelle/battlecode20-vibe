# Which metric starts predicting the result first -- every recorded block of cand87 merged

232 games, 111 wins. Noise floor about 0.13; a correlation inside that band is not evidence.

Every metric is oriented so **higher is better for us**, so a positive correlation always means
"this being better goes with winning". `~avg` is the running mean over all rounds so far rather than
the snapshot at that round. **Onset** is the first round where the correlation reaches *+*threshold and holds.
**Anti** is the first round where it reaches *-*threshold and holds: there the metric predicts the result
backwards, which means either the orientation is wrong or something counter-intuitive is happening early.
A metric with an early anti and a late onset is changing sign, not rising early.
See `progress/METRICS.md` for how each quantity is computed.

| onset | anti | metric | peak corr | r100 | r200 | r300 | r400 | r600 | r900 |
|---|---|---|---|---|---|---|---|---|---|
| r100 | - | hqBuried (us-them) [inverted] | +0.32 | +0.30 | +0.32 | +0.23 | +0.17 | +0.15 | +0.11 |
| r100 | - | hqBuried (us-them) [inverted] ~avg | +0.30 | +0.30 | +0.28 | +0.23 | +0.14 | +0.16 | +0.10 |
| r150 | - | mines (us-them) | +0.33 | +0.29 | +0.29 | +0.33 | +0.31 | +0.31 | +0.32 |
| r150 | - | worth (us-them) | +0.41 | +0.25 | +0.36 | +0.36 | +0.35 | +0.38 | +0.40 |
| r200 | - | robots (us-them) | +0.44 | +0.24 | +0.33 | +0.38 | +0.36 | +0.42 | +0.44 |
| r200 | - | spawned (us-them) | +0.42 | +0.23 | +0.30 | +0.36 | +0.35 | +0.38 | +0.42 |
| r200 | - | worth (us-them) ~avg | +0.39 | +0.25 | +0.32 | +0.36 | +0.38 | +0.39 | +0.39 |
| r250 | - | mines (us-them) ~avg | +0.34 | +0.30 | +0.28 | +0.31 | +0.33 | +0.34 | +0.31 |
| r250 | - | robots (us-them) ~avg | +0.44 | +0.25 | +0.29 | +0.37 | +0.40 | +0.44 | +0.43 |
| r250 | - | spawned (us-them) ~avg | +0.41 | +0.25 | +0.27 | +0.35 | +0.38 | +0.41 | +0.40 |
| r250 | - | units (us-them) | +0.39 | +0.30 | +0.26 | +0.29 | +0.29 | +0.37 | +0.37 |
| r350 | - | units (us-them) ~avg | +0.39 | +0.29 | +0.24 | +0.29 | +0.32 | +0.37 | +0.39 |
| r550 | - | landscapers (us-them) | +0.39 | +0.17 | +0.21 | +0.14 | +0.21 | +0.32 | +0.39 |
| r600 | - | vaporators (us-them) | +0.36 | +0.09 | +0.13 | +0.18 | +0.19 | +0.30 | +0.32 |
| r650 | - | digs (us-them) | +0.41 | +0.25 | +0.06 | +0.11 | +0.19 | +0.28 | +0.35 |
| r650 | - | dirtDeps (us-them) | +0.42 | +0.23 | +0.07 | +0.13 | +0.20 | +0.29 | +0.35 |
| r650 | - | moves (us-them) | +0.36 | +0.08 | +0.19 | +0.25 | +0.28 | +0.30 | +0.34 |
| r700 | - | landscapers (us-them) ~avg | +0.39 | +0.16 | +0.11 | +0.12 | +0.16 | +0.27 | +0.35 |
| r750 | - | vaporators (us-them) ~avg | +0.33 | +0.09 | +0.13 | +0.12 | +0.16 | +0.25 | +0.33 |
| r900 | - | dirtDeps (us-them) ~avg | +0.35 | +0.24 | +0.06 | +0.09 | +0.14 | +0.22 | +0.30 |
| r900 | - | moves (us-them) ~avg | +0.33 | +0.03 | +0.17 | +0.23 | +0.27 | +0.28 | +0.31 |
| r1050 | - | digs (us-them) ~avg | +0.35 | +0.25 | +0.06 | +0.08 | +0.14 | +0.22 | +0.30 |
| r1150 | - | netguns (us-them) | +0.32 | . | +0.01 | +0.04 | +0.06 | +0.28 | +0.28 |
| - | - | aba (us-them) [inverted] | +0.20 | +0.10 | -0.07 | -0.09 | +0.02 | +0.03 | -0.04 |
| - | - | aba (us-them) [inverted] ~avg | +0.20 | +0.14 | -0.04 | -0.09 | -0.03 | +0.02 | +0.00 |
| - | - | cov (us-them) | +0.20 | -0.01 | +0.16 | +0.18 | +0.17 | +0.20 | +0.17 |
| - | - | cov (us-them) ~avg | +0.20 | -0.03 | +0.09 | +0.14 | +0.16 | +0.20 | +0.19 |
| - | - | died (us-them) [inverted] | +0.20 | +0.08 | +0.12 | +0.04 | +0.07 | +0.18 | +0.16 |
| - | - | died (us-them) [inverted] ~avg | +0.17 | +0.06 | +0.14 | +0.07 | +0.06 | +0.13 | +0.17 |
| - | - | drones (us-them) | +0.21 | +0.19 | +0.04 | +0.12 | +0.12 | +0.12 | +0.18 |
| - | - | drones (us-them) ~avg | +0.20 | +0.19 | +0.15 | +0.13 | +0.13 | +0.12 | +0.16 |
| - | - | drowned (us-them) [inverted] | . | . | . | . | . | . | . |
| - | - | drowned (us-them) [inverted] ~avg | . | . | . | . | . | . | . |
| - | - | miners (us-them) | +0.28 | +0.16 | +0.17 | +0.25 | +0.21 | +0.26 | +0.26 |
| - | - | miners (us-them) ~avg | +0.29 | +0.19 | +0.21 | +0.28 | +0.29 | +0.28 | +0.26 |
| - | - | netguns (us-them) ~avg | +0.29 | . | -0.03 | +0.02 | +0.04 | +0.16 | +0.24 |
| - | - | pickups (us-them) | +0.29 | -0.03 | +0.09 | +0.13 | +0.25 | +0.26 | +0.28 |
| - | - | pickups (us-them) ~avg | +0.27 | -0.05 | +0.11 | +0.11 | +0.20 | +0.25 | +0.27 |
| - | - | soup (us-them) | -0.12 | -0.04 | -0.08 | -0.10 | -0.07 | -0.10 | -0.11 |
| - | - | soup (us-them) ~avg | -0.12 | -0.03 | -0.04 | -0.08 | -0.07 | -0.11 | -0.11 |

**Reading it.** Earliest onset is the first place to look: temporal precedence is the one causal hint a
correlation can honestly give. Late-onset metrics are usually the scoreboard rather than the cause -- by then
the winner leads on everything. A high correlation earns a diagnostic game, not a code change.
