# Ladder

11552 scrimmages (ours only), 11028 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter14 | 1774 +- 26 | 12 of 112 | 763 | 386-377 | 81.3% | 25.7% (vs 11) |
| g_iter15 | 1770 +- 60 | 13 of 112 | 144 | 68-76 | 81.1% | 25.3% (vs 11) |
| r6 | 1767 +- 60 | 14 of 112 | 143 | 67-76 | 81.0% | 25.0% (vs 11) |
| g_iter13 | 1765 +- 30 | 15 of 112 | 576 | 297-279 | 80.9% | 24.8% (vs 11) |
| r4s3 | 1746 +- 46 | 17 of 112 | 240 | 121-119 | 80.0% | 25.2% (vs 12) |
| cand69 | 1744 +- 75 | 18 of 112 | 96 | 46-50 | 79.9% | 25.0% (vs 12) |
| g_iter12 | 1743 +- 40 | 19 of 112 | 336 | 159-177 | 79.8% | 24.8% (vs 12) |
| cand81 | 1742 +- 92 | 20 of 112 | 95 | 67-28 | 79.7% | 24.7% (vs 12) |
| r1s13 | 1737 +- 46 | 21 of 112 | 240 | 118-122 | 79.5% | 24.3% (vs 12) |
| cand86s3 | 1737 +- 46 | 22 of 112 | 240 | 118-122 | 79.5% | 24.3% (vs 12) |
| r5 | 1732 +- 39 | 23 of 112 | 336 | 163-173 | 79.3% | 23.8% (vs 12) |
| r4s5 | 1728 +- 47 | 24 of 112 | 239 | 115-124 | 79.1% | 23.5% (vs 12) |
| r4s4 | 1725 +- 47 | 25 of 112 | 239 | 114-125 | 78.9% | 23.2% (vs 12) |
| r4s1 | 1724 +- 46 | 26 of 112 | 240 | 114-126 | 78.8% | 23.1% (vs 12) |
| cand87 | 1717 +- 47 | 27 of 112 | 240 | 112-128 | 78.5% | 22.5% (vs 12) |
| cand81s8 | 1715 +- 49 | 28 of 112 | 240 | 85-155 | 78.4% | 22.3% (vs 12) |
| r2s4 | 1714 +- 47 | 29 of 112 | 240 | 111-129 | 78.3% | 22.2% (vs 12) |
| cand65 | 1710 +- 75 | 30 of 112 | 96 | 42-54 | 78.1% | 21.8% (vs 12) |
| cand86s2 | 1708 +- 73 | 31 of 112 | 96 | 44-52 | 78.0% | 21.6% (vs 12) |
| r1s11 | 1704 +- 47 | 32 of 112 | 240 | 108-132 | 77.8% | 21.3% (vs 12) |
| r1s16 | 1703 +- 49 | 33 of 112 | 240 | 79-161 | 77.7% | 21.1% (vs 12) |
| r1s14 | 1695 +- 47 | 34 of 112 | 240 | 105-135 | 77.3% | 20.4% (vs 12) |
| arch_rush | 1668 +- 74 | 35 of 112 | 96 | 39-57 | 75.7% | 18.2% (vs 12) |
| g_iter11 | 1650 +- 48 | 36 of 112 | 240 | 107-133 | 74.6% | 16.8% (vs 12) |
| arch_rush2 | 1643 +- 75 | 37 of 112 | 96 | 36-60 | 74.2% | 16.3% (vs 12) |
| cand49b | 1641 +- 76 | 38 of 112 | 96 | 42-54 | 74.1% | 16.1% (vs 12) |
| g_iter10 | 1635 +- 49 | 39 of 112 | 240 | 103-137 | 73.8% | 15.8% (vs 12) |
| cand43b | 1632 +- 79 | 40 of 112 | 96 | 64-32 | 73.5% | 15.5% (vs 12) |
| cand47d | 1629 +- 55 | 41 of 112 | 192 | 73-119 | 73.3% | 15.3% (vs 12) |
| g_iter9 | 1617 +- 50 | 42 of 112 | 240 | 78-162 | 72.6% | 14.5% (vs 12) |
| g_iter8 | 1588 +- 49 | 45 of 112 | 240 | 108-132 | 70.6% | 17.5% (vs 14) |
| g_iter5 | 1584 +- 34 | 46 of 112 | 618 | 329-289 | 70.3% | 17.2% (vs 14) |
| cand41b | 1582 +- 76 | 47 of 112 | 96 | 51-45 | 70.1% | 17.0% (vs 14) |
| r1s8 | 1581 +- 79 | 48 of 112 | 96 | 29-67 | 70.1% | 16.9% (vs 14) |
| g_iter7 | 1577 +- 45 | 50 of 112 | 288 | 135-153 | 69.8% | 18.9% (vs 15) |
| g_iter6 | 1570 +- 26 | 51 of 112 | 886 | 450-436 | 69.3% | 18.4% (vs 15) |
| cand40c | 1568 +- 76 | 52 of 112 | 96 | 57-39 | 69.1% | 18.2% (vs 15) |
| cand42c | 1565 +- 76 | 53 of 112 | 96 | 49-47 | 68.9% | 18.0% (vs 15) |
| iter24 | 1563 +- 131 | 54 of 112 | 46 | 11-35 | 68.7% | 17.9% (vs 15) |
| r1s3 | 1562 +- 80 | 55 of 112 | 96 | 27-69 | 68.7% | 17.8% (vs 15) |
| g_iter3 | 1549 +- 34 | 56 of 112 | 796 | 167-629 | 67.7% | 16.9% (vs 15) |
| cand37 | 1539 +- 76 | 57 of 112 | 96 | 46-50 | 66.9% | 16.2% (vs 15) |
| g_iter4 | 1529 +- 65 | 58 of 112 | 217 | 43-174 | 66.1% | 15.5% (vs 15) |
| cand31 | 1529 +- 75 | 59 of 112 | 96 | 52-44 | 66.1% | 15.5% (vs 15) |
| g_iter1 | 1473 +- 68 | 60 of 112 | 170 | 95-75 | 61.2% | 12.2% (vs 15) |
| g_iter2 | 1471 +- 70 | 61 of 112 | 192 | 44-148 | 61.0% | 12.0% (vs 15) |
| arch_enclosure | 1325 +- 165 | 65 of 112 | 48 | 4-44 | 45.9% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2154 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2145 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2085 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2031 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1977 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1971 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1971 | 53 | 358 | 310-48 | 19% (g_iter14 7-29) |
| 8 | winkelmantanner.tannerplayer | 1905 | 26 | 1022 | 784-238 | 29% (g_iter14 28-68) |
| 9 | mvpatel2000.qual | 1892 | 25 | 1009 | 760-249 | 35% (g_iter14 34-62) |
| 10 | rzhan11.quals_bot | 1805 | 26 | 752 | 475-277 | 36% (g_iter14 35-61) |
| 11 | EmaPajic.Qualifications | 1803 | 22 | 1064 | 701-363 | 65% (g_iter14 59-32) |
| 12 | **us:g_iter14** | 1774 | 26 | 763 | 386-377 |  |
| 13 | **us:g_iter15** | 1770 | 60 | 144 | 68-76 |  |
| 14 | **us:r6** | 1767 | 60 | 143 | 67-76 |  |
| 15 | **us:g_iter13** | 1765 | 30 | 576 | 297-279 |  |
| 16 | poortho.stable_seeding_bot | 1749 | 20 | 1241 | 754-487 | 45% (g_iter14 43-53) |
| 17 | **us:r4s3** | 1746 | 46 | 240 | 121-119 |  |
| 18 | **us:cand69** | 1744 | 75 | 96 | 46-50 |  |
| 19 | **us:g_iter12** | 1743 | 40 | 336 | 159-177 |  |
| 20 | **us:cand81** | 1742 | 92 | 95 | 67-28 |  |
| 21 | **us:r1s13** | 1737 | 46 | 240 | 118-122 |  |
| 22 | **us:cand86s3** | 1737 | 46 | 240 | 118-122 |  |
| 23 | **us:r5** | 1732 | 39 | 336 | 163-173 |  |
| 24 | **us:r4s5** | 1728 | 47 | 239 | 115-124 |  |
| 25 | **us:r4s4** | 1725 | 47 | 239 | 114-125 |  |
| 26 | **us:r4s1** | 1724 | 46 | 240 | 114-126 |  |
| 27 | **us:cand87** | 1717 | 47 | 240 | 112-128 |  |
| 28 | **us:cand81s8** | 1715 | 49 | 240 | 85-155 |  |
| 29 | **us:r2s4** | 1714 | 47 | 240 | 111-129 |  |
| 30 | **us:cand65** | 1710 | 75 | 96 | 42-54 |  |
| 31 | **us:cand86s2** | 1708 | 73 | 96 | 44-52 |  |
| 32 | **us:r1s11** | 1704 | 47 | 240 | 108-132 |  |
| 33 | **us:r1s16** | 1703 | 49 | 240 | 79-161 |  |
| 34 | **us:r1s14** | 1695 | 47 | 240 | 105-135 |  |
| 35 | **us:arch_rush** | 1668 | 74 | 96 | 39-57 |  |
| 36 | **us:g_iter11** | 1650 | 48 | 240 | 107-133 |  |
| 37 | **us:arch_rush2** | 1643 | 75 | 96 | 36-60 |  |
| 38 | **us:cand49b** | 1641 | 76 | 96 | 42-54 |  |
| 39 | **us:g_iter10** | 1635 | 49 | 240 | 103-137 |  |
| 40 | **us:cand43b** | 1632 | 79 | 96 | 64-32 |  |
| 41 | **us:cand47d** | 1629 | 55 | 192 | 73-119 |  |
| 42 | **us:g_iter9** | 1617 | 50 | 240 | 78-162 |  |
| 43 | laurenschneider.pdx_team_one | 1615 | 20 | 1208 | 520-688 | 69% (g_iter14 66-30) |
| 44 | cormackikkert.whyPermutator | 1610 | 20 | 1231 | 520-711 | 76% (g_iter14 73-23) |
| 45 | **us:g_iter8** | 1588 | 49 | 240 | 108-132 |  |
| 46 | **us:g_iter5** | 1584 | 34 | 618 | 329-289 |  |
| 47 | **us:cand41b** | 1582 | 76 | 96 | 51-45 |  |
| 48 | **us:r1s8** | 1581 | 79 | 96 | 29-67 |  |
| 49 | benzyx.seeding | 1578 | 22 | 1108 | 436-672 | 83% (g_iter14 40-8) |
| 50 | **us:g_iter7** | 1577 | 45 | 288 | 135-153 |  |
| 51 | **us:g_iter6** | 1570 | 26 | 886 | 450-436 |  |
| 52 | **us:cand40c** | 1568 | 76 | 96 | 57-39 |  |
| 53 | **us:cand42c** | 1565 | 76 | 96 | 49-47 |  |
| 54 | **us:iter24** | 1563 | 131 | 46 | 11-35 |  |
| 55 | **us:r1s3** | 1562 | 80 | 96 | 27-69 |  |
| 56 | **us:g_iter3** | 1549 | 34 | 796 | 167-629 |  |
| 57 | **us:cand37** | 1539 | 76 | 96 | 46-50 |  |
| 58 | **us:g_iter4** | 1529 | 65 | 217 | 43-174 |  |
| 59 | **us:cand31** | 1529 | 75 | 96 | 52-44 |  |
| 60 | **us:g_iter1** | 1473 | 68 | 170 | 95-75 |  |
| 61 | **us:g_iter2** | 1471 | 70 | 192 | 44-148 |  |
| 62 | wpine215.stardustv2 | 1400 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 63 | mhahn2003.nonrush | 1352 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 64 | eggag32.BrutalPigeonBot | 1352 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 65 | **us:arch_enclosure** | 1325 | 165 | 48 | 4-44 |  |
| 66 | cs454-w20-team3.playbot | 1322 | 112 | 56 | 11-45 |  |
| 67 | ARognes.FinalSubmission | 1311 | 144 | 37 | 6-31 |  |
| 68 | TeamSerpentine.noodleBot | 1301 | 143 | 38 | 6-32 |  |
| 69 | opheez.landscapers | 1294 | 268 | 9 | 1-8 |  |
| 70 | ovimura.welovesoup | 1294 | 268 | 9 | 1-8 |  |
| 71 | rsandzimier.SandSibs_seeding | 1294 | 268 | 9 | 1-8 |  |
| 72 | thissop.alpha | 1294 | 268 | 9 | 1-8 |  |
| 73 | VinayaBhat.team10pdx | 1290 | 222 | 15 | 2-13 |  |
| 74 | yaonam.Robot_1 | 1288 | 154 | 33 | 5-28 |  |
| 75 | LucianCov.ourRobot | 1284 | 383 | 3 | 0-3 |  |
| 76 | MrHoseongLee.Neptune_v3 | 1284 | 383 | 3 | 0-3 |  |
| 77 | Phrancium.Frankplayer1 | 1284 | 383 | 3 | 0-3 |  |
| 78 | Pleket.Bot | 1284 | 383 | 3 | 0-3 |  |
| 79 | Strequals.rw8 | 1284 | 383 | 3 | 0-3 |  |
| 80 | Sukanya-Kothapally.team4player | 1284 | 383 | 3 | 0-3 |  |
| 81 | TeamSerpentine.eendagsvliegjes | 1284 | 383 | 3 | 0-3 |  |
| 82 | Tolsi.mybot | 1284 | 383 | 3 | 0-3 |  |
| 83 | anthonybench.FunkBot | 1284 | 383 | 3 | 0-3 |  |
| 84 | atliSig.buttletplayer | 1284 | 383 | 3 | 0-3 |  |
| 85 | charboltron.team11newbot | 1284 | 383 | 3 | 0-3 |  |
| 86 | djkeyes.addingComm | 1284 | 383 | 3 | 0-3 |  |
| 87 | fewella.FirstPlayer | 1284 | 383 | 3 | 0-3 |  |
| 88 | jmerle.camel_case_sprint | 1284 | 383 | 3 | 0-3 |  |
| 89 | kylittle.qualsbot2 | 1284 | 383 | 3 | 0-3 |  |
| 90 | lfchain.bigBudsBot | 1284 | 383 | 3 | 0-3 |  |
| 91 | luisgonzalex.CodeMonkeys | 1284 | 383 | 3 | 0-3 |  |
| 92 | mama4294.maloneplayer | 1284 | 383 | 3 | 0-3 |  |
| 93 | max-titov.seeding | 1284 | 383 | 3 | 0-3 |  |
| 94 | michaeltliu.beginnerplayer | 1284 | 383 | 3 | 0-3 |  |
| 95 | monmouth-college-cs.MyFirstPlayer | 1284 | 383 | 3 | 0-3 |  |
| 96 | ngkuru.qualifyingtournament | 1284 | 383 | 3 | 0-3 |  |
| 97 | orionquick.aldebaranplayer | 1284 | 383 | 3 | 0-3 |  |
| 98 | snpushpi.whatamidoing | 1284 | 383 | 3 | 0-3 |  |
| 99 | stevetimberman.playerbbbbb | 1284 | 383 | 3 | 0-3 |  |
| 100 | willBoyd8.bb8 | 1284 | 383 | 3 | 0-3 |  |
| 101 | cosimogonnelli.Team3player | 1283 | 274 | 7 | 1-6 |  |
| 102 | WilliamYue37.Player1 | 1281 | 140 | 46 | 6-40 |  |
| 103 | Tim-gubski.AngryWaffleMaker | 1275 | 154 | 32 | 5-27 |  |
| 104 | jenlz.bustedJulianbot | 1268 | 261 | 14 | 1-13 |  |
| 105 | A9ine.potato | 1165 | 365 | 6 | 0-6 |  |
| 106 | 9mAhmad.MahinBot | 1138 | 362 | 7 | 0-7 |  |
| 107 | 9mAhmad.lostincoordinates | 1138 | 362 | 7 | 0-7 |  |
| 108 | AllenWang314.bot1 | 1138 | 362 | 7 | 0-7 |  |
| 109 | GabrielDWu.buildawall2 | 1138 | 362 | 7 | 0-7 |  |
| 110 | J-J-Chen.player | 1138 | 362 | 7 | 0-7 |  |
| 111 | KyleHassold.sprintbot | 1138 | 362 | 7 | 0-7 |  |
| 112 | denver-blake.sprint | 1138 | 362 | 7 | 0-7 |  |
