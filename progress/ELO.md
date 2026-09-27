# Ladder

8728 scrimmages (ours only), 8204 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1801 +- 39 | 12 of 103 | 336 | 174-162 | 81.4% | 26.0% (vs 11) |
| cand69 | 1772 +- 74 | 14 of 103 | 96 | 46-50 | 80.0% | 25.2% (vs 12) |
| g_iter12 | 1771 +- 40 | 15 of 103 | 336 | 159-177 | 80.0% | 25.2% (vs 12) |
| cand81 | 1769 +- 91 | 16 of 103 | 95 | 67-28 | 79.9% | 24.9% (vs 12) |
| cand86s3 | 1767 +- 46 | 17 of 103 | 240 | 118-122 | 79.8% | 24.8% (vs 12) |
| r1s13 | 1767 +- 46 | 18 of 103 | 240 | 118-122 | 79.8% | 24.8% (vs 12) |
| cand87 | 1748 +- 46 | 19 of 103 | 240 | 112-128 | 78.8% | 22.9% (vs 12) |
| cand81s8 | 1743 +- 48 | 20 of 103 | 240 | 85-155 | 78.5% | 22.4% (vs 12) |
| cand65 | 1739 +- 74 | 21 of 103 | 96 | 42-54 | 78.3% | 22.1% (vs 12) |
| cand86s2 | 1739 +- 73 | 22 of 103 | 96 | 44-52 | 78.3% | 22.1% (vs 12) |
| r1s11 | 1735 +- 46 | 23 of 103 | 240 | 108-132 | 78.1% | 21.7% (vs 12) |
| r1s16 | 1729 +- 49 | 24 of 103 | 240 | 79-161 | 77.8% | 21.2% (vs 12) |
| r1s14 | 1726 +- 46 | 25 of 103 | 240 | 105-135 | 77.6% | 20.9% (vs 12) |
| arch_rush | 1699 +- 74 | 26 of 103 | 96 | 39-57 | 76.1% | 18.6% (vs 12) |
| g_iter11 | 1681 +- 48 | 27 of 103 | 240 | 107-133 | 75.1% | 17.2% (vs 12) |
| arch_rush2 | 1674 +- 75 | 28 of 103 | 96 | 36-60 | 74.7% | 16.7% (vs 12) |
| cand49b | 1671 +- 76 | 29 of 103 | 96 | 42-54 | 74.5% | 16.5% (vs 12) |
| g_iter10 | 1667 +- 48 | 30 of 103 | 240 | 103-137 | 74.2% | 16.1% (vs 12) |
| cand43b | 1667 +- 79 | 31 of 103 | 96 | 64-32 | 74.2% | 16.1% (vs 12) |
| cand47d | 1660 +- 54 | 32 of 103 | 192 | 73-119 | 73.8% | 15.6% (vs 12) |
| g_iter9 | 1648 +- 50 | 35 of 103 | 240 | 78-162 | 73.0% | 19.8% (vs 14) |
| g_iter8 | 1624 +- 49 | 36 of 103 | 240 | 108-132 | 71.4% | 17.9% (vs 14) |
| cand41b | 1618 +- 76 | 38 of 103 | 96 | 51-45 | 71.0% | 19.6% (vs 15) |
| g_iter5 | 1616 +- 34 | 39 of 103 | 618 | 329-289 | 70.9% | 19.5% (vs 15) |
| r1s8 | 1613 +- 78 | 40 of 103 | 96 | 29-67 | 70.7% | 19.2% (vs 15) |
| g_iter7 | 1611 +- 45 | 41 of 103 | 288 | 135-153 | 70.6% | 19.1% (vs 15) |
| g_iter6 | 1604 +- 26 | 42 of 103 | 886 | 450-436 | 70.1% | 18.6% (vs 15) |
| cand40c | 1603 +- 77 | 43 of 103 | 96 | 57-39 | 69.9% | 18.5% (vs 15) |
| cand42c | 1600 +- 76 | 44 of 103 | 96 | 49-47 | 69.8% | 18.3% (vs 15) |
| r1s3 | 1594 +- 80 | 45 of 103 | 96 | 27-69 | 69.3% | 17.8% (vs 15) |
| iter24 | 1593 +- 131 | 46 of 103 | 46 | 11-35 | 69.2% | 17.7% (vs 15) |
| g_iter3 | 1581 +- 33 | 47 of 103 | 796 | 167-629 | 68.4% | 16.9% (vs 15) |
| cand37 | 1575 +- 76 | 48 of 103 | 96 | 46-50 | 67.9% | 16.5% (vs 15) |
| cand31 | 1564 +- 75 | 49 of 103 | 96 | 52-44 | 67.0% | 15.7% (vs 15) |
| g_iter4 | 1560 +- 65 | 50 of 103 | 217 | 43-174 | 66.8% | 15.5% (vs 15) |
| g_iter2 | 1502 +- 70 | 51 of 103 | 192 | 44-148 | 61.9% | 12.0% (vs 15) |
| g_iter1 | 1498 +- 68 | 52 of 103 | 170 | 95-75 | 61.5% | 11.8% (vs 15) |
| arch_enclosure | 1356 +- 165 | 56 of 103 | 48 | 4-44 | 46.9% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2185 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2176 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2116 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2060 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 2001 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 6 | battlecode20-team4.finalbota | 1993 | 63 | 286 | 254-32 | 3% (r1s16 1-29) |
| 7 | IvanGeffner.finalbota | 1992 | 72 | 197 | 171-26 | 10% (r1s16 3-27) |
| 8 | winkelmantanner.tannerplayer | 1906 | 32 | 668 | 514-154 | 36% (g_iter13 15-27) |
| 9 | mvpatel2000.qual | 1903 | 32 | 655 | 501-154 | 29% (g_iter13 12-30) |
| 10 | EmaPajic.Qualifications | 1843 | 28 | 718 | 508-210 | 50% (g_iter13 21-21) |
| 11 | rzhan11.quals_bot | 1842 | 37 | 398 | 273-125 | 45% (g_iter13 19-23) |
| 12 | **us:g_iter13** | 1801 | 39 | 336 | 174-162 |  |
| 13 | poortho.stable_seeding_bot | 1780 | 24 | 887 | 575-312 | 48% (g_iter13 20-22) |
| 14 | **us:cand69** | 1772 | 74 | 96 | 46-50 |  |
| 15 | **us:g_iter12** | 1771 | 40 | 336 | 159-177 |  |
| 16 | **us:cand81** | 1769 | 91 | 95 | 67-28 |  |
| 17 | **us:cand86s3** | 1767 | 46 | 240 | 118-122 |  |
| 18 | **us:r1s13** | 1767 | 46 | 240 | 118-122 |  |
| 19 | **us:cand87** | 1748 | 46 | 240 | 112-128 |  |
| 20 | **us:cand81s8** | 1743 | 48 | 240 | 85-155 |  |
| 21 | **us:cand65** | 1739 | 74 | 96 | 42-54 |  |
| 22 | **us:cand86s2** | 1739 | 73 | 96 | 44-52 |  |
| 23 | **us:r1s11** | 1735 | 46 | 240 | 108-132 |  |
| 24 | **us:r1s16** | 1729 | 49 | 240 | 79-161 |  |
| 25 | **us:r1s14** | 1726 | 46 | 240 | 105-135 |  |
| 26 | **us:arch_rush** | 1699 | 74 | 96 | 39-57 |  |
| 27 | **us:g_iter11** | 1681 | 48 | 240 | 107-133 |  |
| 28 | **us:arch_rush2** | 1674 | 75 | 96 | 36-60 |  |
| 29 | **us:cand49b** | 1671 | 76 | 96 | 42-54 |  |
| 30 | **us:g_iter10** | 1667 | 48 | 240 | 103-137 |  |
| 31 | **us:cand43b** | 1667 | 79 | 96 | 64-32 |  |
| 32 | **us:cand47d** | 1660 | 54 | 192 | 73-119 |  |
| 33 | cormackikkert.whyPermutator | 1654 | 24 | 877 | 423-454 | 74% (g_iter13 31-11) |
| 34 | laurenschneider.pdx_team_one | 1651 | 24 | 854 | 412-442 | 69% (g_iter13 29-13) |
| 35 | **us:g_iter9** | 1648 | 50 | 240 | 78-162 |  |
| 36 | **us:g_iter8** | 1624 | 49 | 240 | 108-132 |  |
| 37 | benzyx.seeding | 1618 | 24 | 838 | 369-469 | 80% (g_iter13 24-6) |
| 38 | **us:cand41b** | 1618 | 76 | 96 | 51-45 |  |
| 39 | **us:g_iter5** | 1616 | 34 | 618 | 329-289 |  |
| 40 | **us:r1s8** | 1613 | 78 | 96 | 29-67 |  |
| 41 | **us:g_iter7** | 1611 | 45 | 288 | 135-153 |  |
| 42 | **us:g_iter6** | 1604 | 26 | 886 | 450-436 |  |
| 43 | **us:cand40c** | 1603 | 77 | 96 | 57-39 |  |
| 44 | **us:cand42c** | 1600 | 76 | 96 | 49-47 |  |
| 45 | **us:r1s3** | 1594 | 80 | 96 | 27-69 |  |
| 46 | **us:iter24** | 1593 | 131 | 46 | 11-35 |  |
| 47 | **us:g_iter3** | 1581 | 33 | 796 | 167-629 |  |
| 48 | **us:cand37** | 1575 | 76 | 96 | 46-50 |  |
| 49 | **us:cand31** | 1564 | 75 | 96 | 52-44 |  |
| 50 | **us:g_iter4** | 1560 | 65 | 217 | 43-174 |  |
| 51 | **us:g_iter2** | 1502 | 70 | 192 | 44-148 |  |
| 52 | **us:g_iter1** | 1498 | 68 | 170 | 95-75 |  |
| 53 | wpine215.stardustv2 | 1433 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 54 | mhahn2003.nonrush | 1386 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 55 | eggag32.BrutalPigeonBot | 1386 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 56 | **us:arch_enclosure** | 1356 | 165 | 48 | 4-44 |  |
| 57 | cs454-w20-team3.playbot | 1353 | 112 | 56 | 11-45 |  |
| 58 | ARognes.FinalSubmission | 1340 | 144 | 37 | 6-31 |  |
| 59 | TeamSerpentine.noodleBot | 1330 | 144 | 38 | 6-32 |  |
| 60 | opheez.landscapers | 1318 | 268 | 9 | 1-8 |  |
| 61 | ovimura.welovesoup | 1318 | 268 | 9 | 1-8 |  |
| 62 | rsandzimier.SandSibs_seeding | 1318 | 268 | 9 | 1-8 |  |
| 63 | thissop.alpha | 1318 | 268 | 9 | 1-8 |  |
| 64 | yaonam.Robot_1 | 1317 | 154 | 33 | 5-28 |  |
| 65 | VinayaBhat.team10pdx | 1314 | 222 | 15 | 2-13 |  |
| 66 | WilliamYue37.Player1 | 1311 | 140 | 46 | 6-40 |  |
| 67 | Tim-gubski.AngryWaffleMaker | 1305 | 154 | 32 | 5-27 |  |
| 68 | cosimogonnelli.Team3player | 1303 | 274 | 7 | 1-6 |  |
| 69 | LucianCov.ourRobot | 1300 | 384 | 3 | 0-3 |  |
| 70 | MrHoseongLee.Neptune_v3 | 1300 | 384 | 3 | 0-3 |  |
| 71 | Phrancium.Frankplayer1 | 1300 | 384 | 3 | 0-3 |  |
| 72 | Pleket.Bot | 1300 | 384 | 3 | 0-3 |  |
| 73 | Strequals.rw8 | 1300 | 384 | 3 | 0-3 |  |
| 74 | Sukanya-Kothapally.team4player | 1300 | 384 | 3 | 0-3 |  |
| 75 | TeamSerpentine.eendagsvliegjes | 1300 | 384 | 3 | 0-3 |  |
| 76 | Tolsi.mybot | 1300 | 384 | 3 | 0-3 |  |
| 77 | anthonybench.FunkBot | 1300 | 384 | 3 | 0-3 |  |
| 78 | atliSig.buttletplayer | 1300 | 384 | 3 | 0-3 |  |
| 79 | charboltron.team11newbot | 1300 | 384 | 3 | 0-3 |  |
| 80 | djkeyes.addingComm | 1300 | 384 | 3 | 0-3 |  |
| 81 | fewella.FirstPlayer | 1300 | 384 | 3 | 0-3 |  |
| 82 | jmerle.camel_case_sprint | 1300 | 384 | 3 | 0-3 |  |
| 83 | kylittle.qualsbot2 | 1300 | 384 | 3 | 0-3 |  |
| 84 | lfchain.bigBudsBot | 1300 | 384 | 3 | 0-3 |  |
| 85 | luisgonzalex.CodeMonkeys | 1300 | 384 | 3 | 0-3 |  |
| 86 | mama4294.maloneplayer | 1300 | 384 | 3 | 0-3 |  |
| 87 | max-titov.seeding | 1300 | 384 | 3 | 0-3 |  |
| 88 | michaeltliu.beginnerplayer | 1300 | 384 | 3 | 0-3 |  |
| 89 | monmouth-college-cs.MyFirstPlayer | 1300 | 384 | 3 | 0-3 |  |
| 90 | ngkuru.qualifyingtournament | 1300 | 384 | 3 | 0-3 |  |
| 91 | orionquick.aldebaranplayer | 1300 | 384 | 3 | 0-3 |  |
| 92 | snpushpi.whatamidoing | 1300 | 384 | 3 | 0-3 |  |
| 93 | stevetimberman.playerbbbbb | 1300 | 384 | 3 | 0-3 |  |
| 94 | willBoyd8.bb8 | 1300 | 384 | 3 | 0-3 |  |
| 95 | jenlz.bustedJulianbot | 1292 | 262 | 14 | 1-13 |  |
| 96 | A9ine.potato | 1185 | 365 | 6 | 0-6 |  |
| 97 | 9mAhmad.MahinBot | 1158 | 362 | 7 | 0-7 |  |
| 98 | 9mAhmad.lostincoordinates | 1158 | 362 | 7 | 0-7 |  |
| 99 | AllenWang314.bot1 | 1158 | 362 | 7 | 0-7 |  |
| 100 | GabrielDWu.buildawall2 | 1158 | 362 | 7 | 0-7 |  |
| 101 | J-J-Chen.player | 1158 | 362 | 7 | 0-7 |  |
| 102 | KyleHassold.sprintbot | 1158 | 362 | 7 | 0-7 |  |
| 103 | denver-blake.sprint | 1158 | 362 | 7 | 0-7 |  |
