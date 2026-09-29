# Ladder

10690 scrimmages (ours only), 10166 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1773 +- 35 | 12 of 110 | 432 | 222-210 | 81.0% | 25.0% (vs 11) |
| r1s17 | 1771 +- 40 | 13 of 110 | 332 | 169-163 | 80.9% | 24.9% (vs 11) |
| r4s3 | 1753 +- 46 | 15 of 110 | 240 | 121-119 | 80.1% | 25.4% (vs 12) |
| cand69 | 1751 +- 75 | 16 of 110 | 96 | 46-50 | 80.0% | 25.2% (vs 12) |
| g_iter12 | 1750 +- 40 | 17 of 110 | 336 | 159-177 | 79.9% | 25.1% (vs 12) |
| cand81 | 1748 +- 92 | 18 of 110 | 95 | 67-28 | 79.8% | 24.9% (vs 12) |
| cand86s3 | 1744 +- 46 | 19 of 110 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| r1s13 | 1744 +- 46 | 20 of 110 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| r5 | 1739 +- 39 | 21 of 110 | 336 | 163-173 | 79.3% | 24.0% (vs 12) |
| r4s5 | 1735 +- 46 | 22 of 110 | 239 | 115-124 | 79.1% | 23.6% (vs 12) |
| r4s4 | 1732 +- 46 | 23 of 110 | 239 | 114-125 | 79.0% | 23.3% (vs 12) |
| r4s1 | 1731 +- 46 | 24 of 110 | 240 | 114-126 | 78.9% | 23.2% (vs 12) |
| cand87 | 1724 +- 46 | 25 of 110 | 240 | 112-128 | 78.6% | 22.6% (vs 12) |
| cand81s8 | 1721 +- 49 | 26 of 110 | 240 | 85-155 | 78.4% | 22.3% (vs 12) |
| r2s4 | 1721 +- 46 | 27 of 110 | 240 | 111-129 | 78.4% | 22.3% (vs 12) |
| cand65 | 1717 +- 75 | 28 of 110 | 96 | 42-54 | 78.2% | 21.9% (vs 12) |
| cand86s2 | 1715 +- 73 | 29 of 110 | 96 | 44-52 | 78.1% | 21.7% (vs 12) |
| r1s11 | 1711 +- 47 | 30 of 110 | 240 | 108-132 | 77.9% | 21.4% (vs 12) |
| r1s16 | 1708 +- 49 | 31 of 110 | 240 | 79-161 | 77.7% | 21.1% (vs 12) |
| r1s14 | 1702 +- 47 | 32 of 110 | 240 | 105-135 | 77.4% | 20.5% (vs 12) |
| arch_rush | 1675 +- 74 | 33 of 110 | 96 | 39-57 | 75.8% | 18.3% (vs 12) |
| g_iter11 | 1657 +- 48 | 34 of 110 | 240 | 107-133 | 74.8% | 17.0% (vs 12) |
| arch_rush2 | 1650 +- 75 | 35 of 110 | 96 | 36-60 | 74.3% | 16.4% (vs 12) |
| cand49b | 1648 +- 76 | 36 of 110 | 96 | 42-54 | 74.2% | 16.3% (vs 12) |
| g_iter10 | 1643 +- 49 | 37 of 110 | 240 | 103-137 | 73.9% | 15.9% (vs 12) |
| cand43b | 1639 +- 79 | 38 of 110 | 96 | 64-32 | 73.7% | 15.6% (vs 12) |
| cand47d | 1636 +- 55 | 39 of 110 | 192 | 73-119 | 73.5% | 15.4% (vs 12) |
| g_iter9 | 1624 +- 50 | 40 of 110 | 240 | 78-162 | 72.7% | 14.6% (vs 12) |
| g_iter8 | 1597 +- 49 | 43 of 110 | 240 | 108-132 | 70.8% | 17.6% (vs 14) |
| g_iter5 | 1591 +- 34 | 44 of 110 | 618 | 329-289 | 70.4% | 17.2% (vs 14) |
| cand41b | 1591 +- 76 | 45 of 110 | 96 | 51-45 | 70.4% | 17.2% (vs 14) |
| r1s8 | 1588 +- 79 | 46 of 110 | 96 | 29-67 | 70.2% | 17.0% (vs 14) |
| g_iter7 | 1585 +- 45 | 48 of 110 | 288 | 135-153 | 70.0% | 19.0% (vs 15) |
| g_iter6 | 1577 +- 26 | 49 of 110 | 886 | 450-436 | 69.4% | 18.4% (vs 15) |
| cand40c | 1575 +- 76 | 50 of 110 | 96 | 57-39 | 69.3% | 18.2% (vs 15) |
| cand42c | 1574 +- 76 | 51 of 110 | 96 | 49-47 | 69.2% | 18.1% (vs 15) |
| iter24 | 1570 +- 131 | 52 of 110 | 46 | 11-35 | 68.9% | 17.9% (vs 15) |
| r1s3 | 1569 +- 80 | 53 of 110 | 96 | 27-69 | 68.8% | 17.8% (vs 15) |
| g_iter3 | 1556 +- 34 | 54 of 110 | 796 | 167-629 | 67.9% | 16.9% (vs 15) |
| cand37 | 1548 +- 76 | 55 of 110 | 96 | 46-50 | 67.2% | 16.3% (vs 15) |
| cand31 | 1537 +- 75 | 56 of 110 | 96 | 52-44 | 66.3% | 15.6% (vs 15) |
| g_iter4 | 1536 +- 65 | 57 of 110 | 217 | 43-174 | 66.2% | 15.5% (vs 15) |
| g_iter1 | 1478 +- 68 | 58 of 110 | 170 | 95-75 | 61.3% | 12.1% (vs 15) |
| g_iter2 | 1478 +- 70 | 59 of 110 | 192 | 44-148 | 61.2% | 12.0% (vs 15) |
| arch_enclosure | 1333 +- 165 | 63 of 110 | 48 | 4-44 | 46.2% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2161 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2152 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2092 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2037 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1983 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1978 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1969 | 64 | 286 | 254-32 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1905 | 27 | 914 | 705-209 | 24% (r5 10-32) |
| 9 | mvpatel2000.qual | 1890 | 27 | 901 | 680-221 | 33% (r5 14-28) |
| 10 | EmaPajic.Qualifications | 1825 | 24 | 958 | 660-298 | 26% (r5 11-31) |
| 11 | rzhan11.quals_bot | 1806 | 28 | 644 | 412-232 | 52% (r5 22-20) |
| 12 | **us:g_iter13** | 1773 | 35 | 432 | 222-210 |  |
| 13 | **us:r1s17** | 1771 | 40 | 332 | 169-163 |  |
| 14 | poortho.stable_seeding_bot | 1754 | 21 | 1133 | 701-432 | 50% (r5 21-21) |
| 15 | **us:r4s3** | 1753 | 46 | 240 | 121-119 |  |
| 16 | **us:cand69** | 1751 | 75 | 96 | 46-50 |  |
| 17 | **us:g_iter12** | 1750 | 40 | 336 | 159-177 |  |
| 18 | **us:cand81** | 1748 | 92 | 95 | 67-28 |  |
| 19 | **us:cand86s3** | 1744 | 46 | 240 | 118-122 |  |
| 20 | **us:r1s13** | 1744 | 46 | 240 | 118-122 |  |
| 21 | **us:r5** | 1739 | 39 | 336 | 163-173 |  |
| 22 | **us:r4s5** | 1735 | 46 | 239 | 115-124 |  |
| 23 | **us:r4s4** | 1732 | 46 | 239 | 114-125 |  |
| 24 | **us:r4s1** | 1731 | 46 | 240 | 114-126 |  |
| 25 | **us:cand87** | 1724 | 46 | 240 | 112-128 |  |
| 26 | **us:cand81s8** | 1721 | 49 | 240 | 85-155 |  |
| 27 | **us:r2s4** | 1721 | 46 | 240 | 111-129 |  |
| 28 | **us:cand65** | 1717 | 75 | 96 | 42-54 |  |
| 29 | **us:cand86s2** | 1715 | 73 | 96 | 44-52 |  |
| 30 | **us:r1s11** | 1711 | 47 | 240 | 108-132 |  |
| 31 | **us:r1s16** | 1708 | 49 | 240 | 79-161 |  |
| 32 | **us:r1s14** | 1702 | 47 | 240 | 105-135 |  |
| 33 | **us:arch_rush** | 1675 | 74 | 96 | 39-57 |  |
| 34 | **us:g_iter11** | 1657 | 48 | 240 | 107-133 |  |
| 35 | **us:arch_rush2** | 1650 | 75 | 96 | 36-60 |  |
| 36 | **us:cand49b** | 1648 | 76 | 96 | 42-54 |  |
| 37 | **us:g_iter10** | 1643 | 49 | 240 | 103-137 |  |
| 38 | **us:cand43b** | 1639 | 79 | 96 | 64-32 |  |
| 39 | **us:cand47d** | 1636 | 55 | 192 | 73-119 |  |
| 40 | **us:g_iter9** | 1624 | 50 | 240 | 78-162 |  |
| 41 | laurenschneider.pdx_team_one | 1623 | 21 | 1100 | 490-610 | 62% (r5 26-16) |
| 42 | cormackikkert.whyPermutator | 1620 | 21 | 1123 | 493-630 | 67% (r5 28-14) |
| 43 | **us:g_iter8** | 1597 | 49 | 240 | 108-132 |  |
| 44 | **us:g_iter5** | 1591 | 34 | 618 | 329-289 |  |
| 45 | **us:cand41b** | 1591 | 76 | 96 | 51-45 |  |
| 46 | **us:r1s8** | 1588 | 79 | 96 | 29-67 |  |
| 47 | benzyx.seeding | 1587 | 22 | 1072 | 430-642 | 74% (r5 31-11) |
| 48 | **us:g_iter7** | 1585 | 45 | 288 | 135-153 |  |
| 49 | **us:g_iter6** | 1577 | 26 | 886 | 450-436 |  |
| 50 | **us:cand40c** | 1575 | 76 | 96 | 57-39 |  |
| 51 | **us:cand42c** | 1574 | 76 | 96 | 49-47 |  |
| 52 | **us:iter24** | 1570 | 131 | 46 | 11-35 |  |
| 53 | **us:r1s3** | 1569 | 80 | 96 | 27-69 |  |
| 54 | **us:g_iter3** | 1556 | 34 | 796 | 167-629 |  |
| 55 | **us:cand37** | 1548 | 76 | 96 | 46-50 |  |
| 56 | **us:cand31** | 1537 | 75 | 96 | 52-44 |  |
| 57 | **us:g_iter4** | 1536 | 65 | 217 | 43-174 |  |
| 58 | **us:g_iter1** | 1478 | 68 | 170 | 95-75 |  |
| 59 | **us:g_iter2** | 1478 | 70 | 192 | 44-148 |  |
| 60 | wpine215.stardustv2 | 1408 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 61 | mhahn2003.nonrush | 1360 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 62 | eggag32.BrutalPigeonBot | 1360 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 63 | **us:arch_enclosure** | 1333 | 165 | 48 | 4-44 |  |
| 64 | cs454-w20-team3.playbot | 1329 | 112 | 56 | 11-45 |  |
| 65 | ARognes.FinalSubmission | 1318 | 144 | 37 | 6-31 |  |
| 66 | TeamSerpentine.noodleBot | 1308 | 143 | 38 | 6-32 |  |
| 67 | opheez.landscapers | 1299 | 268 | 9 | 1-8 |  |
| 68 | ovimura.welovesoup | 1299 | 268 | 9 | 1-8 |  |
| 69 | rsandzimier.SandSibs_seeding | 1299 | 268 | 9 | 1-8 |  |
| 70 | thissop.alpha | 1299 | 268 | 9 | 1-8 |  |
| 71 | VinayaBhat.team10pdx | 1295 | 222 | 15 | 2-13 |  |
| 72 | yaonam.Robot_1 | 1295 | 154 | 33 | 5-28 |  |
| 73 | WilliamYue37.Player1 | 1288 | 140 | 46 | 6-40 |  |
| 74 | cosimogonnelli.Team3player | 1287 | 274 | 7 | 1-6 |  |
| 75 | LucianCov.ourRobot | 1287 | 383 | 3 | 0-3 |  |
| 76 | MrHoseongLee.Neptune_v3 | 1287 | 383 | 3 | 0-3 |  |
| 77 | Phrancium.Frankplayer1 | 1287 | 383 | 3 | 0-3 |  |
| 78 | Pleket.Bot | 1287 | 383 | 3 | 0-3 |  |
| 79 | Strequals.rw8 | 1287 | 383 | 3 | 0-3 |  |
| 80 | Sukanya-Kothapally.team4player | 1287 | 383 | 3 | 0-3 |  |
| 81 | TeamSerpentine.eendagsvliegjes | 1287 | 383 | 3 | 0-3 |  |
| 82 | Tolsi.mybot | 1287 | 383 | 3 | 0-3 |  |
| 83 | anthonybench.FunkBot | 1287 | 383 | 3 | 0-3 |  |
| 84 | atliSig.buttletplayer | 1287 | 383 | 3 | 0-3 |  |
| 85 | charboltron.team11newbot | 1287 | 383 | 3 | 0-3 |  |
| 86 | djkeyes.addingComm | 1287 | 383 | 3 | 0-3 |  |
| 87 | fewella.FirstPlayer | 1287 | 383 | 3 | 0-3 |  |
| 88 | jmerle.camel_case_sprint | 1287 | 383 | 3 | 0-3 |  |
| 89 | kylittle.qualsbot2 | 1287 | 383 | 3 | 0-3 |  |
| 90 | lfchain.bigBudsBot | 1287 | 383 | 3 | 0-3 |  |
| 91 | luisgonzalex.CodeMonkeys | 1287 | 383 | 3 | 0-3 |  |
| 92 | mama4294.maloneplayer | 1287 | 383 | 3 | 0-3 |  |
| 93 | max-titov.seeding | 1287 | 383 | 3 | 0-3 |  |
| 94 | michaeltliu.beginnerplayer | 1287 | 383 | 3 | 0-3 |  |
| 95 | monmouth-college-cs.MyFirstPlayer | 1287 | 383 | 3 | 0-3 |  |
| 96 | ngkuru.qualifyingtournament | 1287 | 383 | 3 | 0-3 |  |
| 97 | orionquick.aldebaranplayer | 1287 | 383 | 3 | 0-3 |  |
| 98 | snpushpi.whatamidoing | 1287 | 383 | 3 | 0-3 |  |
| 99 | stevetimberman.playerbbbbb | 1287 | 383 | 3 | 0-3 |  |
| 100 | willBoyd8.bb8 | 1287 | 383 | 3 | 0-3 |  |
| 101 | Tim-gubski.AngryWaffleMaker | 1281 | 154 | 32 | 5-27 |  |
| 102 | jenlz.bustedJulianbot | 1274 | 261 | 14 | 1-13 |  |
| 103 | A9ine.potato | 1169 | 365 | 6 | 0-6 |  |
| 104 | 9mAhmad.MahinBot | 1143 | 362 | 7 | 0-7 |  |
| 105 | 9mAhmad.lostincoordinates | 1143 | 362 | 7 | 0-7 |  |
| 106 | AllenWang314.bot1 | 1143 | 362 | 7 | 0-7 |  |
| 107 | GabrielDWu.buildawall2 | 1143 | 362 | 7 | 0-7 |  |
| 108 | J-J-Chen.player | 1143 | 362 | 7 | 0-7 |  |
| 109 | KyleHassold.sprintbot | 1143 | 362 | 7 | 0-7 |  |
| 110 | denver-blake.sprint | 1143 | 362 | 7 | 0-7 |  |
