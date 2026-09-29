# Ladder

11264 scrimmages (ours only), 10740 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter14 | 1783 +- 29 | 12 of 111 | 619 | 322-297 | 81.6% | 26.3% (vs 11) |
| r6 | 1770 +- 60 | 13 of 111 | 143 | 67-76 | 81.0% | 25.0% (vs 11) |
| g_iter13 | 1769 +- 30 | 14 of 111 | 576 | 297-279 | 80.9% | 24.9% (vs 11) |
| r4s3 | 1750 +- 46 | 16 of 111 | 240 | 121-119 | 80.0% | 25.3% (vs 12) |
| cand69 | 1748 +- 75 | 17 of 111 | 96 | 46-50 | 79.9% | 25.1% (vs 12) |
| g_iter12 | 1747 +- 40 | 18 of 111 | 336 | 159-177 | 79.9% | 25.0% (vs 12) |
| cand81 | 1745 +- 92 | 19 of 111 | 95 | 67-28 | 79.8% | 24.8% (vs 12) |
| cand86s3 | 1740 +- 46 | 20 of 111 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| r1s13 | 1740 +- 46 | 21 of 111 | 240 | 118-122 | 79.6% | 24.4% (vs 12) |
| r5 | 1736 +- 39 | 22 of 111 | 336 | 163-173 | 79.3% | 23.9% (vs 12) |
| r4s5 | 1732 +- 47 | 23 of 111 | 239 | 115-124 | 79.1% | 23.5% (vs 12) |
| r4s4 | 1729 +- 47 | 24 of 111 | 239 | 114-125 | 79.0% | 23.2% (vs 12) |
| r4s1 | 1728 +- 46 | 25 of 111 | 240 | 114-126 | 78.9% | 23.1% (vs 12) |
| cand87 | 1721 +- 47 | 26 of 111 | 240 | 112-128 | 78.6% | 22.5% (vs 12) |
| cand81s8 | 1719 +- 49 | 27 of 111 | 240 | 85-155 | 78.4% | 22.3% (vs 12) |
| r2s4 | 1718 +- 47 | 28 of 111 | 240 | 111-129 | 78.4% | 22.2% (vs 12) |
| cand65 | 1713 +- 75 | 29 of 111 | 96 | 42-54 | 78.1% | 21.8% (vs 12) |
| cand86s2 | 1712 +- 73 | 30 of 111 | 96 | 44-52 | 78.1% | 21.7% (vs 12) |
| r1s11 | 1708 +- 47 | 31 of 111 | 240 | 108-132 | 77.9% | 21.3% (vs 12) |
| r1s16 | 1706 +- 49 | 32 of 111 | 240 | 79-161 | 77.7% | 21.1% (vs 12) |
| r1s14 | 1698 +- 47 | 33 of 111 | 240 | 105-135 | 77.3% | 20.5% (vs 12) |
| arch_rush | 1671 +- 74 | 34 of 111 | 96 | 39-57 | 75.8% | 18.2% (vs 12) |
| g_iter11 | 1654 +- 48 | 35 of 111 | 240 | 107-133 | 74.7% | 16.9% (vs 12) |
| arch_rush2 | 1646 +- 75 | 36 of 111 | 96 | 36-60 | 74.3% | 16.3% (vs 12) |
| cand49b | 1645 +- 76 | 37 of 111 | 96 | 42-54 | 74.2% | 16.2% (vs 12) |
| g_iter10 | 1640 +- 49 | 38 of 111 | 240 | 103-137 | 73.9% | 15.8% (vs 12) |
| cand43b | 1635 +- 79 | 39 of 111 | 96 | 64-32 | 73.6% | 15.5% (vs 12) |
| cand47d | 1633 +- 55 | 40 of 111 | 192 | 73-119 | 73.4% | 15.3% (vs 12) |
| g_iter9 | 1621 +- 50 | 41 of 111 | 240 | 78-162 | 72.7% | 14.5% (vs 12) |
| g_iter8 | 1592 +- 49 | 44 of 111 | 240 | 108-132 | 70.7% | 17.5% (vs 14) |
| g_iter5 | 1587 +- 34 | 45 of 111 | 618 | 329-289 | 70.4% | 17.2% (vs 14) |
| cand41b | 1586 +- 76 | 46 of 111 | 96 | 51-45 | 70.3% | 17.1% (vs 14) |
| r1s8 | 1584 +- 79 | 47 of 111 | 96 | 29-67 | 70.2% | 17.0% (vs 14) |
| g_iter7 | 1581 +- 45 | 49 of 111 | 288 | 135-153 | 69.9% | 18.9% (vs 15) |
| g_iter6 | 1573 +- 26 | 50 of 111 | 886 | 450-436 | 69.3% | 18.4% (vs 15) |
| cand40c | 1571 +- 76 | 51 of 111 | 96 | 57-39 | 69.2% | 18.2% (vs 15) |
| cand42c | 1569 +- 76 | 52 of 111 | 96 | 49-47 | 69.0% | 18.0% (vs 15) |
| iter24 | 1566 +- 131 | 53 of 111 | 46 | 11-35 | 68.8% | 17.9% (vs 15) |
| r1s3 | 1566 +- 80 | 54 of 111 | 96 | 27-69 | 68.8% | 17.8% (vs 15) |
| g_iter3 | 1553 +- 34 | 55 of 111 | 796 | 167-629 | 67.8% | 16.9% (vs 15) |
| cand37 | 1543 +- 76 | 56 of 111 | 96 | 46-50 | 67.0% | 16.2% (vs 15) |
| cand31 | 1532 +- 75 | 57 of 111 | 96 | 52-44 | 66.2% | 15.5% (vs 15) |
| g_iter4 | 1532 +- 65 | 58 of 111 | 217 | 43-174 | 66.2% | 15.5% (vs 15) |
| g_iter1 | 1475 +- 68 | 59 of 111 | 170 | 95-75 | 61.2% | 12.1% (vs 15) |
| g_iter2 | 1474 +- 70 | 60 of 111 | 192 | 44-148 | 61.1% | 12.0% (vs 15) |
| arch_enclosure | 1329 +- 165 | 64 of 111 | 48 | 4-44 | 46.0% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2157 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2148 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2089 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2034 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1981 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1975 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1966 | 57 | 322 | 281-41 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1909 | 26 | 986 | 760-226 | 28% (g_iter14 22-56) |
| 9 | mvpatel2000.qual | 1893 | 26 | 973 | 732-241 | 37% (g_iter14 29-49) |
| 10 | EmaPajic.Qualifications | 1813 | 23 | 1028 | 688-340 | 66% (g_iter14 48-25) |
| 11 | rzhan11.quals_bot | 1805 | 27 | 716 | 452-264 | 36% (g_iter14 28-50) |
| 12 | **us:g_iter14** | 1783 | 29 | 619 | 322-297 |  |
| 13 | **us:r6** | 1770 | 60 | 143 | 67-76 |  |
| 14 | **us:g_iter13** | 1769 | 30 | 576 | 297-279 |  |
| 15 | poortho.stable_seeding_bot | 1752 | 21 | 1205 | 736-469 | 42% (g_iter14 33-45) |
| 16 | **us:r4s3** | 1750 | 46 | 240 | 121-119 |  |
| 17 | **us:cand69** | 1748 | 75 | 96 | 46-50 |  |
| 18 | **us:g_iter12** | 1747 | 40 | 336 | 159-177 |  |
| 19 | **us:cand81** | 1745 | 92 | 95 | 67-28 |  |
| 20 | **us:cand86s3** | 1740 | 46 | 240 | 118-122 |  |
| 21 | **us:r1s13** | 1740 | 46 | 240 | 118-122 |  |
| 22 | **us:r5** | 1736 | 39 | 336 | 163-173 |  |
| 23 | **us:r4s5** | 1732 | 47 | 239 | 115-124 |  |
| 24 | **us:r4s4** | 1729 | 47 | 239 | 114-125 |  |
| 25 | **us:r4s1** | 1728 | 46 | 240 | 114-126 |  |
| 26 | **us:cand87** | 1721 | 47 | 240 | 112-128 |  |
| 27 | **us:cand81s8** | 1719 | 49 | 240 | 85-155 |  |
| 28 | **us:r2s4** | 1718 | 47 | 240 | 111-129 |  |
| 29 | **us:cand65** | 1713 | 75 | 96 | 42-54 |  |
| 30 | **us:cand86s2** | 1712 | 73 | 96 | 44-52 |  |
| 31 | **us:r1s11** | 1708 | 47 | 240 | 108-132 |  |
| 32 | **us:r1s16** | 1706 | 49 | 240 | 79-161 |  |
| 33 | **us:r1s14** | 1698 | 47 | 240 | 105-135 |  |
| 34 | **us:arch_rush** | 1671 | 74 | 96 | 39-57 |  |
| 35 | **us:g_iter11** | 1654 | 48 | 240 | 107-133 |  |
| 36 | **us:arch_rush2** | 1646 | 75 | 96 | 36-60 |  |
| 37 | **us:cand49b** | 1645 | 76 | 96 | 42-54 |  |
| 38 | **us:g_iter10** | 1640 | 49 | 240 | 103-137 |  |
| 39 | **us:cand43b** | 1635 | 79 | 96 | 64-32 |  |
| 40 | **us:cand47d** | 1633 | 55 | 192 | 73-119 |  |
| 41 | **us:g_iter9** | 1621 | 50 | 240 | 78-162 |  |
| 42 | laurenschneider.pdx_team_one | 1618 | 21 | 1172 | 508-664 | 73% (g_iter14 57-21) |
| 43 | cormackikkert.whyPermutator | 1615 | 20 | 1195 | 511-684 | 76% (g_iter14 59-19) |
| 44 | **us:g_iter8** | 1592 | 49 | 240 | 108-132 |  |
| 45 | **us:g_iter5** | 1587 | 34 | 618 | 329-289 |  |
| 46 | **us:cand41b** | 1586 | 76 | 96 | 51-45 |  |
| 47 | **us:r1s8** | 1584 | 79 | 96 | 29-67 |  |
| 48 | benzyx.seeding | 1582 | 22 | 1108 | 436-672 | 83% (g_iter14 40-8) |
| 49 | **us:g_iter7** | 1581 | 45 | 288 | 135-153 |  |
| 50 | **us:g_iter6** | 1573 | 26 | 886 | 450-436 |  |
| 51 | **us:cand40c** | 1571 | 76 | 96 | 57-39 |  |
| 52 | **us:cand42c** | 1569 | 76 | 96 | 49-47 |  |
| 53 | **us:iter24** | 1566 | 131 | 46 | 11-35 |  |
| 54 | **us:r1s3** | 1566 | 80 | 96 | 27-69 |  |
| 55 | **us:g_iter3** | 1553 | 34 | 796 | 167-629 |  |
| 56 | **us:cand37** | 1543 | 76 | 96 | 46-50 |  |
| 57 | **us:cand31** | 1532 | 75 | 96 | 52-44 |  |
| 58 | **us:g_iter4** | 1532 | 65 | 217 | 43-174 |  |
| 59 | **us:g_iter1** | 1475 | 68 | 170 | 95-75 |  |
| 60 | **us:g_iter2** | 1474 | 70 | 192 | 44-148 |  |
| 61 | wpine215.stardustv2 | 1404 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 62 | mhahn2003.nonrush | 1356 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 63 | eggag32.BrutalPigeonBot | 1356 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 64 | **us:arch_enclosure** | 1329 | 165 | 48 | 4-44 |  |
| 65 | cs454-w20-team3.playbot | 1325 | 112 | 56 | 11-45 |  |
| 66 | ARognes.FinalSubmission | 1314 | 144 | 37 | 6-31 |  |
| 67 | TeamSerpentine.noodleBot | 1304 | 143 | 38 | 6-32 |  |
| 68 | opheez.landscapers | 1297 | 268 | 9 | 1-8 |  |
| 69 | ovimura.welovesoup | 1297 | 268 | 9 | 1-8 |  |
| 70 | rsandzimier.SandSibs_seeding | 1297 | 268 | 9 | 1-8 |  |
| 71 | thissop.alpha | 1297 | 268 | 9 | 1-8 |  |
| 72 | VinayaBhat.team10pdx | 1292 | 222 | 15 | 2-13 |  |
| 73 | yaonam.Robot_1 | 1291 | 154 | 33 | 5-28 |  |
| 74 | LucianCov.ourRobot | 1285 | 383 | 3 | 0-3 |  |
| 75 | MrHoseongLee.Neptune_v3 | 1285 | 383 | 3 | 0-3 |  |
| 76 | Phrancium.Frankplayer1 | 1285 | 383 | 3 | 0-3 |  |
| 77 | Pleket.Bot | 1285 | 383 | 3 | 0-3 |  |
| 78 | Strequals.rw8 | 1285 | 383 | 3 | 0-3 |  |
| 79 | Sukanya-Kothapally.team4player | 1285 | 383 | 3 | 0-3 |  |
| 80 | TeamSerpentine.eendagsvliegjes | 1285 | 383 | 3 | 0-3 |  |
| 81 | Tolsi.mybot | 1285 | 383 | 3 | 0-3 |  |
| 82 | anthonybench.FunkBot | 1285 | 383 | 3 | 0-3 |  |
| 83 | atliSig.buttletplayer | 1285 | 383 | 3 | 0-3 |  |
| 84 | charboltron.team11newbot | 1285 | 383 | 3 | 0-3 |  |
| 85 | djkeyes.addingComm | 1285 | 383 | 3 | 0-3 |  |
| 86 | fewella.FirstPlayer | 1285 | 383 | 3 | 0-3 |  |
| 87 | jmerle.camel_case_sprint | 1285 | 383 | 3 | 0-3 |  |
| 88 | kylittle.qualsbot2 | 1285 | 383 | 3 | 0-3 |  |
| 89 | lfchain.bigBudsBot | 1285 | 383 | 3 | 0-3 |  |
| 90 | luisgonzalex.CodeMonkeys | 1285 | 383 | 3 | 0-3 |  |
| 91 | mama4294.maloneplayer | 1285 | 383 | 3 | 0-3 |  |
| 92 | max-titov.seeding | 1285 | 383 | 3 | 0-3 |  |
| 93 | michaeltliu.beginnerplayer | 1285 | 383 | 3 | 0-3 |  |
| 94 | monmouth-college-cs.MyFirstPlayer | 1285 | 383 | 3 | 0-3 |  |
| 95 | ngkuru.qualifyingtournament | 1285 | 383 | 3 | 0-3 |  |
| 96 | orionquick.aldebaranplayer | 1285 | 383 | 3 | 0-3 |  |
| 97 | snpushpi.whatamidoing | 1285 | 383 | 3 | 0-3 |  |
| 98 | stevetimberman.playerbbbbb | 1285 | 383 | 3 | 0-3 |  |
| 99 | willBoyd8.bb8 | 1285 | 383 | 3 | 0-3 |  |
| 100 | cosimogonnelli.Team3player | 1285 | 274 | 7 | 1-6 |  |
| 101 | WilliamYue37.Player1 | 1284 | 140 | 46 | 6-40 |  |
| 102 | Tim-gubski.AngryWaffleMaker | 1278 | 154 | 32 | 5-27 |  |
| 103 | jenlz.bustedJulianbot | 1271 | 261 | 14 | 1-13 |  |
| 104 | A9ine.potato | 1167 | 365 | 6 | 0-6 |  |
| 105 | 9mAhmad.MahinBot | 1140 | 362 | 7 | 0-7 |  |
| 106 | 9mAhmad.lostincoordinates | 1140 | 362 | 7 | 0-7 |  |
| 107 | AllenWang314.bot1 | 1140 | 362 | 7 | 0-7 |  |
| 108 | GabrielDWu.buildawall2 | 1140 | 362 | 7 | 0-7 |  |
| 109 | J-J-Chen.player | 1140 | 362 | 7 | 0-7 |  |
| 110 | KyleHassold.sprintbot | 1140 | 362 | 7 | 0-7 |  |
| 111 | denver-blake.sprint | 1140 | 362 | 7 | 0-7 |  |
