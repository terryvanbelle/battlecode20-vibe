#!/usr/bin/env bash
# Report a replay opponent's tier (BENCHMARK.md). Informational since PROMPTS 45 (2026-09-26): games
# against any bot may be reviewed, so this always exits 0. Until then it enforced the 20% rule
# (no review below a 20% win rate) and failed closed; the history is kept below.
#
#   tools/tier-check.sh <replay.bc20>     prints the tier; exit 0
#
# Added 2026-09-20 after I reviewed six awesomelemonade replays while that bot was listed
# `locked`. The rule said "the discipline is on the reader"; discipline that depends on
# remembering to look is the same silent-failure shape as the other three found that day.
set -euo pipefail
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
f="$(basename "${1:?usage: tier-check.sh <replay.bc20>}")"
# Only a gauntlet replay carries an opponent in its name: <opponent>__<map>__bot<side>.bc20.
# A diagnostic replay (diag-i44.bc20) has no "__" and is not about a benchmark bot at all; treating
# its whole filename as an opponent name made the guard refuse our own diagnostics (2026-09-21).
case "$f" in *__*__bot?.bc20) ;; *) exit 0 ;; esac
opp="${f%%__*}"
case "$opp" in *.*) ;; *) exit 0 ;; esac          # our own snapshots and archetypes are unrestricted
# Fail CLOSED if the roster is missing. An opponent name with a dot is a benchmark bot, and
# a rule file that is simply absent must never read as permission (the VM had no BENCHMARK.md
# at all when this guard was first written).
if [ ! -f "$REPO/BENCHMARK.md" ]; then echo "$opp: tier unknown (no BENCHMARK.md)"; exit 0; fi
row="$(grep -F "| \`$opp\` |" "$REPO/BENCHMARK.md" 2>/dev/null | head -1 || true)"
if [ -z "$row" ]; then echo "$opp: not in the roster"; exit 0; fi
tier="$(printf '%s' "$row" | awk -F'|' '{gsub(/ /,"",$6); print $6}')"
pct="$(printf '%s' "$row" | awk -F'|' '{gsub(/ /,"",$4); print $4}')"
echo "$opp: tier $tier (last win rate ${pct}%) -- reviewable (PROMPTS 45)"
exit 0
