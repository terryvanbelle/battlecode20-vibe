# Prompt record

User task prompts for this project, in chronological order, recorded verbatim
(spelling, punctuation and whitespace preserved). Append-only for the prompt
text: never edit an entry's words. Each entry is `## <number>. <date>` followed
by the prompt as typed (the date is the day the prompt was given, UTC).

## 1. 2026-09-23

We are going to build a world-class champion Battlecode bot.  Battlecode is a contest where the contestants implement bots to play against other bots in an arena.  Each year’s rules are different from prior years, but they all share some common features.  We have built bots for several prior years already (Github repositories, in order of attempt:  battlecode22-vibe, battlecode26-vibe, battlecode25-vibe, and battlecode21-vibe.  Each attempt was built on previous attempts, so weight the findings of later projects higher than earlier ones).  Read through the code and documentation for these projects thoroughly to learn what has already been done, and what has worked or not worked.  Pay particular attention to files called RESEARCH.md, LEARNINGS.md, DESIGN.md, METHOD.md, TRAINING_LOG.md, and TRAINING_ALGORITHM.md.  Also review all code and documentation from the github repository anicolao/bcenv.  Feel free to steal any code that might be useful to you.

We’re not participating in an actual Battlecode tournament, we’re practicing.  In an actual tournament, you would have two sources of data:  local fights against old versions of yourself, and online scrimmages against a variety of opponents in the tournament standings.  We can’t perfectly replicate this latter source of data, but we should try to get as close as possible. 
Please do a thorough check of the web, especially github, for competitor bots from the relevant year that are publicly accessible.  Download all of them to serve as your benchmark.  You may not read their code.  To avoid over-indexing on bots out of your league, avoid reviewing games against any bot until you can defeat it at least 20% of the time.  Simulate an ELO ladder based on the downloaded bots, where you challenge bots slightly better than you on a random map with a random side.

When you have thoroughly read all recommended repositories, formulate your own TRAINING_ALGORITHM.md file.  This file should be concise, complete, and formulated in year-agnostic terms.  Please do not simply copy a previous year’s TRAINING_ALGORITHM file.  You are forbidden from reading port-mortems from the current year.  Post-mortems from any other year are fair game.

You should start by building a strong, robust foundation in the basics:  good economy management; ensuring that your bots can move freely and efficiently to their destinations; board exploration; exploiting map symmetries; effective combat (e.g. kite and strike); and ensuring no bytecode overruns.  Also invest time at the beginning in building a good code architecture and good tools for understanding everything that happens in a game replay file.  Generate graphs that illustrate your progress, and keep them up to date.  Write unit tests for the bot and all tooling, keep them up to date, and run them after every change.

Make sure that your attempts are a good combination of incremental tweaks and big swings.  If you get stuck for ideas, review principles that have worked in other years.  There will be times when no attempts are successful for a long period.  At those times, it’s important to keep trying new things, and to not give up.  If you believe that a complete rewrite will help, then you should do so.

Starting with this one, save all of my prompts in a document called PROMPTS.md.

This year we will compete in Battlecode 2020.  Store all results in a new Github repository called battlecode20-vibe.  Download the rules and begin.

## 2. 2026-09-23

I'm curious to know how game speeds compare this year vs. 2021

## 3. 2026-09-23

Github tells me my fine-grained personal access token is about to expire.  Can you renew it?

## 4. 2026-09-23

The Github page seems to say that that token is only for terryvanbelle/battlecode22-vibe.  Can you verify that that's true?

## 5. 2026-09-23

OK, I guess it's harmless

## 6. 2026-09-23

/loop 30m task check.  If the VM is idle and nothing is in the workqueue, start a new idea.  Otherwise, carry on as before

## 7. 2026-09-24

What's our progress against a fixed roster of opponents?

## 8. 2026-09-24

Is it possible you're working on beating a roster that's too strong at the moment?

## 9. 2026-09-24

You're approved

## 10. 2026-09-24

This year the games run a lot faster, so it makes sense to run more games to get a good baseline

## 11. 2026-09-24

I didn't mean that the blocks should be bigger, but that you should play more games to determine what the true ladder grade is

## 12. 2026-09-24

But your plan to revisit the ladder grading is a good one

## 13. 2026-09-24

I'm going to switch you to Opus 5.5, but that's going to require a restart of Claude.  Let me know when you're ready
