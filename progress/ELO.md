# Ladder

10354 scrimmages (ours only), 9830 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1776 +- 35 | 12 of 109 | 432 | 222-210 | 81.0% | 25.1% (vs 11) |
| r1s17 | 1775 +- 40 | 13 of 109 | 332 | 169-163 | 81.0% | 24.9% (vs 11) |
| r4s3 | 1757 +- 46 | 15 of 109 | 240 | 121-119 | 80.1% | 25.4% (vs 12) |
| cand69 | 1754 +- 75 | 16 of 109 | 96 | 46-50 | 80.0% | 25.1% (vs 12) |
| g_iter12 | 1753 +- 40 | 17 of 109 | 336 | 159-177 | 79.9% | 25.0% (vs 12) |
| cand81 | 1751 +- 92 | 18 of 109 | 95 | 67-28 | 79.8% | 24.8% (vs 12) |
| r1s13 | 1747 +- 46 | 19 of 109 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| cand86s3 | 1747 +- 46 | 20 of 109 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| r4s5 | 1739 +- 46 | 21 of 109 | 239 | 115-124 | 79.2% | 23.6% (vs 12) |
| r4s4 | 1736 +- 46 | 22 of 109 | 239 | 114-125 | 79.0% | 23.3% (vs 12) |
| r4s1 | 1734 +- 46 | 23 of 109 | 240 | 114-126 | 79.0% | 23.2% (vs 12) |
| cand87 | 1728 +- 46 | 24 of 109 | 240 | 112-128 | 78.6% | 22.6% (vs 12) |
| cand81s8 | 1725 +- 49 | 25 of 109 | 240 | 85-155 | 78.5% | 22.3% (vs 12) |
| r2s4 | 1725 +- 46 | 26 of 109 | 240 | 111-129 | 78.5% | 22.3% (vs 12) |
| cand65 | 1720 +- 75 | 27 of 109 | 96 | 42-54 | 78.2% | 21.8% (vs 12) |
| cand86s2 | 1719 +- 73 | 28 of 109 | 96 | 44-52 | 78.1% | 21.7% (vs 12) |
| r1s11 | 1715 +- 47 | 29 of 109 | 240 | 108-132 | 77.9% | 21.4% (vs 12) |
| r1s16 | 1712 +- 49 | 30 of 109 | 240 | 79-161 | 77.8% | 21.1% (vs 12) |
| r1s14 | 1705 +- 47 | 31 of 109 | 240 | 105-135 | 77.4% | 20.5% (vs 12) |
| arch_rush | 1678 +- 74 | 32 of 109 | 96 | 39-57 | 75.9% | 18.3% (vs 12) |
| g_iter11 | 1660 +- 48 | 33 of 109 | 240 | 107-133 | 74.8% | 16.9% (vs 12) |
| arch_rush2 | 1653 +- 75 | 34 of 109 | 96 | 36-60 | 74.4% | 16.4% (vs 12) |
| cand49b | 1651 +- 76 | 35 of 109 | 96 | 42-54 | 74.3% | 16.2% (vs 12) |
| g_iter10 | 1646 +- 48 | 36 of 109 | 240 | 103-137 | 74.0% | 15.8% (vs 12) |
| cand43b | 1642 +- 79 | 37 of 109 | 96 | 64-32 | 73.7% | 15.6% (vs 12) |
| cand47d | 1639 +- 55 | 38 of 109 | 192 | 73-119 | 73.5% | 15.4% (vs 12) |
| g_iter9 | 1627 +- 50 | 39 of 109 | 240 | 78-162 | 72.7% | 14.5% (vs 12) |
| g_iter8 | 1599 +- 49 | 42 of 109 | 240 | 108-132 | 70.9% | 17.6% (vs 14) |
| g_iter5 | 1594 +- 34 | 43 of 109 | 618 | 329-289 | 70.5% | 17.2% (vs 14) |
| cand41b | 1594 +- 76 | 44 of 109 | 96 | 51-45 | 70.4% | 17.2% (vs 14) |
| r1s8 | 1592 +- 79 | 45 of 109 | 96 | 29-67 | 70.3% | 17.0% (vs 14) |
| g_iter7 | 1588 +- 45 | 47 of 109 | 288 | 135-153 | 70.1% | 19.0% (vs 15) |
| g_iter6 | 1581 +- 26 | 48 of 109 | 886 | 450-436 | 69.5% | 18.4% (vs 15) |
| cand40c | 1578 +- 76 | 49 of 109 | 96 | 57-39 | 69.3% | 18.2% (vs 15) |
| cand42c | 1576 +- 76 | 50 of 109 | 96 | 49-47 | 69.2% | 18.1% (vs 15) |
| iter24 | 1573 +- 131 | 51 of 109 | 46 | 11-35 | 68.9% | 17.8% (vs 15) |
| r1s3 | 1573 +- 80 | 52 of 109 | 96 | 27-69 | 68.9% | 17.8% (vs 15) |
| g_iter3 | 1560 +- 34 | 53 of 109 | 796 | 167-629 | 68.0% | 16.9% (vs 15) |
| cand37 | 1551 +- 76 | 54 of 109 | 96 | 46-50 | 67.2% | 16.3% (vs 15) |
| cand31 | 1540 +- 75 | 55 of 109 | 96 | 52-44 | 66.4% | 15.5% (vs 15) |
| g_iter4 | 1539 +- 65 | 56 of 109 | 217 | 43-174 | 66.3% | 15.5% (vs 15) |
| g_iter2 | 1481 +- 70 | 57 of 109 | 192 | 44-148 | 61.3% | 12.0% (vs 15) |
| g_iter1 | 1481 +- 68 | 58 of 109 | 170 | 95-75 | 61.3% | 12.0% (vs 15) |
| arch_enclosure | 1335 +- 165 | 62 of 109 | 48 | 4-44 | 46.3% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2164 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2155 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2095 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2041 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1986 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1981 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1973 | 64 | 286 | 254-32 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1907 | 28 | 872 | 673-199 | 23% (r4s5 7-23) |
| 9 | mvpatel2000.qual | 1896 | 27 | 859 | 652-207 | 13% (r4s5 4-26) |
| 10 | EmaPajic.Qualifications | 1824 | 25 | 916 | 629-287 | 27% (r4s3 8-22) |
| 11 | rzhan11.quals_bot | 1816 | 30 | 602 | 392-210 | 43% (r4s5 13-17) |
| 12 | **us:g_iter13** | 1776 | 35 | 432 | 222-210 |  |
| 13 | **us:r1s17** | 1775 | 40 | 332 | 169-163 |  |
| 14 | poortho.stable_seeding_bot | 1758 | 22 | 1091 | 680-411 | 47% (r4s5 14-16) |
| 15 | **us:r4s3** | 1757 | 46 | 240 | 121-119 |  |
| 16 | **us:cand69** | 1754 | 75 | 96 | 46-50 |  |
| 17 | **us:g_iter12** | 1753 | 40 | 336 | 159-177 |  |
| 18 | **us:cand81** | 1751 | 92 | 95 | 67-28 |  |
| 19 | **us:r1s13** | 1747 | 46 | 240 | 118-122 |  |
| 20 | **us:cand86s3** | 1747 | 46 | 240 | 118-122 |  |
| 21 | **us:r4s5** | 1739 | 46 | 239 | 115-124 |  |
| 22 | **us:r4s4** | 1736 | 46 | 239 | 114-125 |  |
| 23 | **us:r4s1** | 1734 | 46 | 240 | 114-126 |  |
| 24 | **us:cand87** | 1728 | 46 | 240 | 112-128 |  |
| 25 | **us:cand81s8** | 1725 | 49 | 240 | 85-155 |  |
| 26 | **us:r2s4** | 1725 | 46 | 240 | 111-129 |  |
| 27 | **us:cand65** | 1720 | 75 | 96 | 42-54 |  |
| 28 | **us:cand86s2** | 1719 | 73 | 96 | 44-52 |  |
| 29 | **us:r1s11** | 1715 | 47 | 240 | 108-132 |  |
| 30 | **us:r1s16** | 1712 | 49 | 240 | 79-161 |  |
| 31 | **us:r1s14** | 1705 | 47 | 240 | 105-135 |  |
| 32 | **us:arch_rush** | 1678 | 74 | 96 | 39-57 |  |
| 33 | **us:g_iter11** | 1660 | 48 | 240 | 107-133 |  |
| 34 | **us:arch_rush2** | 1653 | 75 | 96 | 36-60 |  |
| 35 | **us:cand49b** | 1651 | 76 | 96 | 42-54 |  |
| 36 | **us:g_iter10** | 1646 | 48 | 240 | 103-137 |  |
| 37 | **us:cand43b** | 1642 | 79 | 96 | 64-32 |  |
| 38 | **us:cand47d** | 1639 | 55 | 192 | 73-119 |  |
| 39 | **us:g_iter9** | 1627 | 50 | 240 | 78-162 |  |
| 40 | laurenschneider.pdx_team_one | 1625 | 22 | 1058 | 474-584 | 73% (r4s5 22-8) |
| 41 | cormackikkert.whyPermutator | 1623 | 21 | 1081 | 479-602 | 70% (r4s5 21-9) |
| 42 | **us:g_iter8** | 1599 | 49 | 240 | 108-132 |  |
| 43 | **us:g_iter5** | 1594 | 34 | 618 | 329-289 |  |
| 44 | **us:cand41b** | 1594 | 76 | 96 | 51-45 |  |
| 45 | **us:r1s8** | 1592 | 79 | 96 | 29-67 |  |
| 46 | benzyx.seeding | 1591 | 22 | 1030 | 419-611 | 77% (r4s5 23-7) |
| 47 | **us:g_iter7** | 1588 | 45 | 288 | 135-153 |  |
| 48 | **us:g_iter6** | 1581 | 26 | 886 | 450-436 |  |
| 49 | **us:cand40c** | 1578 | 76 | 96 | 57-39 |  |
| 50 | **us:cand42c** | 1576 | 76 | 96 | 49-47 |  |
| 51 | **us:iter24** | 1573 | 131 | 46 | 11-35 |  |
| 52 | **us:r1s3** | 1573 | 80 | 96 | 27-69 |  |
| 53 | **us:g_iter3** | 1560 | 34 | 796 | 167-629 |  |
| 54 | **us:cand37** | 1551 | 76 | 96 | 46-50 |  |
| 55 | **us:cand31** | 1540 | 75 | 96 | 52-44 |  |
| 56 | **us:g_iter4** | 1539 | 65 | 217 | 43-174 |  |
| 57 | **us:g_iter2** | 1481 | 70 | 192 | 44-148 |  |
| 58 | **us:g_iter1** | 1481 | 68 | 170 | 95-75 |  |
| 59 | wpine215.stardustv2 | 1411 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 60 | mhahn2003.nonrush | 1363 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 61 | eggag32.BrutalPigeonBot | 1363 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 62 | **us:arch_enclosure** | 1335 | 165 | 48 | 4-44 |  |
| 63 | cs454-w20-team3.playbot | 1332 | 112 | 56 | 11-45 |  |
| 64 | ARognes.FinalSubmission | 1320 | 144 | 37 | 6-31 |  |
| 65 | TeamSerpentine.noodleBot | 1311 | 143 | 38 | 6-32 |  |
| 66 | opheez.landscapers | 1302 | 268 | 9 | 1-8 |  |
| 67 | ovimura.welovesoup | 1302 | 268 | 9 | 1-8 |  |
| 68 | rsandzimier.SandSibs_seeding | 1302 | 268 | 9 | 1-8 |  |
| 69 | thissop.alpha | 1302 | 268 | 9 | 1-8 |  |
| 70 | VinayaBhat.team10pdx | 1298 | 222 | 15 | 2-13 |  |
| 71 | yaonam.Robot_1 | 1297 | 154 | 33 | 5-28 |  |
| 72 | WilliamYue37.Player1 | 1291 | 140 | 46 | 6-40 |  |
| 73 | cosimogonnelli.Team3player | 1289 | 274 | 7 | 1-6 |  |
| 74 | LucianCov.ourRobot | 1289 | 383 | 3 | 0-3 |  |
| 75 | MrHoseongLee.Neptune_v3 | 1289 | 383 | 3 | 0-3 |  |
| 76 | Phrancium.Frankplayer1 | 1289 | 383 | 3 | 0-3 |  |
| 77 | Pleket.Bot | 1289 | 383 | 3 | 0-3 |  |
| 78 | Strequals.rw8 | 1289 | 383 | 3 | 0-3 |  |
| 79 | Sukanya-Kothapally.team4player | 1289 | 383 | 3 | 0-3 |  |
| 80 | TeamSerpentine.eendagsvliegjes | 1289 | 383 | 3 | 0-3 |  |
| 81 | Tolsi.mybot | 1289 | 383 | 3 | 0-3 |  |
| 82 | anthonybench.FunkBot | 1289 | 383 | 3 | 0-3 |  |
| 83 | atliSig.buttletplayer | 1289 | 383 | 3 | 0-3 |  |
| 84 | charboltron.team11newbot | 1289 | 383 | 3 | 0-3 |  |
| 85 | djkeyes.addingComm | 1289 | 383 | 3 | 0-3 |  |
| 86 | fewella.FirstPlayer | 1289 | 383 | 3 | 0-3 |  |
| 87 | jmerle.camel_case_sprint | 1289 | 383 | 3 | 0-3 |  |
| 88 | kylittle.qualsbot2 | 1289 | 383 | 3 | 0-3 |  |
| 89 | lfchain.bigBudsBot | 1289 | 383 | 3 | 0-3 |  |
| 90 | luisgonzalex.CodeMonkeys | 1289 | 383 | 3 | 0-3 |  |
| 91 | mama4294.maloneplayer | 1289 | 383 | 3 | 0-3 |  |
| 92 | max-titov.seeding | 1289 | 383 | 3 | 0-3 |  |
| 93 | michaeltliu.beginnerplayer | 1289 | 383 | 3 | 0-3 |  |
| 94 | monmouth-college-cs.MyFirstPlayer | 1289 | 383 | 3 | 0-3 |  |
| 95 | ngkuru.qualifyingtournament | 1289 | 383 | 3 | 0-3 |  |
| 96 | orionquick.aldebaranplayer | 1289 | 383 | 3 | 0-3 |  |
| 97 | snpushpi.whatamidoing | 1289 | 383 | 3 | 0-3 |  |
| 98 | stevetimberman.playerbbbbb | 1289 | 383 | 3 | 0-3 |  |
| 99 | willBoyd8.bb8 | 1289 | 383 | 3 | 0-3 |  |
| 100 | Tim-gubski.AngryWaffleMaker | 1284 | 154 | 32 | 5-27 |  |
| 101 | jenlz.bustedJulianbot | 1276 | 261 | 14 | 1-13 |  |
| 102 | A9ine.potato | 1171 | 365 | 6 | 0-6 |  |
| 103 | 9mAhmad.MahinBot | 1145 | 362 | 7 | 0-7 |  |
| 104 | 9mAhmad.lostincoordinates | 1145 | 362 | 7 | 0-7 |  |
| 105 | AllenWang314.bot1 | 1145 | 362 | 7 | 0-7 |  |
| 106 | GabrielDWu.buildawall2 | 1145 | 362 | 7 | 0-7 |  |
| 107 | J-J-Chen.player | 1145 | 362 | 7 | 0-7 |  |
| 108 | KyleHassold.sprintbot | 1145 | 362 | 7 | 0-7 |  |
| 109 | denver-blake.sprint | 1145 | 362 | 7 | 0-7 |  |
