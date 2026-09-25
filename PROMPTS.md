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

## 14. 2026-09-24

OK, I've switched you to Opus 5.5.  Please continue

## 15. 2026-09-24

I'd like to hear a summary of your new plan to fix the ladder system

## 16. 2026-09-24

You can go ahead

## 17. 2026-09-24

Yeah, that looks a lot saner now

## 18. 2026-09-24

Make sure that TRAINING_ALGORITHM and the rest of the documents are updated to reflect this

## 19. 2026-09-24

ELO.md has g_iter6 at 1677 and 20th place, which contradicts your statement.  Which is correct?

## 20. 2026-09-24

Thanks.  I frequently look at the docs in the Github repository to get a sense of where we are, so it's important to keep them up to date

## 21. 2026-09-24

Given everything you've learned about Battlecode, what is your estimate for how much better Fable 5.1 is over Opus 5.5 for making progress in the game?

## 22. 2026-09-24

OK, let's look at it from a different perspective.  Given what you know about Fable's general strengths and weaknesses vs. the ones for Opus 5.5,  along with what you know about the things required for Battlecode, would you predict that Fable would be a large improvement, or not much improvement at all, or something else?

## 23. 2026-09-24

OK, I'm going to upgrade you to Fable.  Please make all necessary preparations

## 24. 2026-09-24

OK, you're now back on Fable 5.1

## 25. 2026-09-24

While you're diagnosing game losses, I'd also like you to take into account the data used for ONSET.md, though it's at your discretion to decide how much or how little to weight that evidence

## 26. 2026-09-24

How many blocks are you planning to run?  It seems like we've got the g_iter6 rating nailed down pretty well

## 27. 2026-09-25

Can you move the legend in the onset-ladder.png to the bottom right?

## 28. 2026-09-25

Can you make me a graph of Field Score from ELO.md over time, and do a linear fit extrapolating out to the end of 1 and 2 weeks?

## 29. 2026-09-25

Yeah, it looks like linear fit is not so useful.  Can you recommend a more appropriate fit function?

## 30. 2026-09-25

Agreed, but I'd like to keep this graph updated over time, so the extrapolation will get more and more valid.  Go ahead with your recommended approach

## 31. 2026-09-25

Graph is great, please update it every time you submit a new candidate

## 32. 2026-09-25

Can you explain why cand43b, cand49b, and cand47d weren't accepted?  They all performed better in the ladder than g_iter9

## 33. 2026-09-25

OK, so cand43b just happened to get some more favorable matchups than g_iter9, and that explains its higher ELO?

## 34. 2026-09-25

I'm going to switch you back to Opus 5.5.  Please make the necessary preparations

## 35. 2026-09-25

OK, you're back on Opus 5.5

## 36. 2026-09-25

Please don't wait until the next task check to start new work if you already know that you need to start new work

## 37. 2026-09-25

The loop task check is a failsafe
