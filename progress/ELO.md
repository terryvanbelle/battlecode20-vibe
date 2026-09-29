# Ladder

13085 scrimmages (ours only), 12561 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter16 | 1766 +- 39 | 12 of 116 | 336 | 170-166 | 81.3% | 25.5% (vs 11) |
| g_iter14 | 1763 +- 26 | 13 of 116 | 763 | 386-377 | 81.2% | 25.2% (vs 11) |
| g_iter15 | 1759 +- 24 | 14 of 116 | 911 | 457-454 | 81.0% | 24.8% (vs 11) |
| r6 | 1757 +- 60 | 15 of 116 | 143 | 67-76 | 80.9% | 24.6% (vs 11) |
| g_iter13 | 1753 +- 30 | 16 of 116 | 576 | 297-279 | 80.7% | 24.2% (vs 11) |
| r8b | 1745 +- 61 | 17 of 116 | 144 | 65-79 | 80.3% | 23.5% (vs 11) |
| r10 | 1740 +- 61 | 19 of 116 | 143 | 64-79 | 80.1% | 25.2% (vs 12) |
| r4s3 | 1734 +- 47 | 20 of 116 | 240 | 121-119 | 79.8% | 24.7% (vs 12) |
| cand69 | 1731 +- 75 | 21 of 116 | 96 | 46-50 | 79.6% | 24.3% (vs 12) |
| g_iter12 | 1730 +- 40 | 22 of 116 | 336 | 159-177 | 79.6% | 24.3% (vs 12) |
| cand81 | 1729 +- 92 | 23 of 116 | 95 | 67-28 | 79.5% | 24.2% (vs 12) |
| cand86s3 | 1725 +- 47 | 24 of 116 | 240 | 118-122 | 79.3% | 23.7% (vs 12) |
| r1s13 | 1725 +- 47 | 25 of 116 | 240 | 118-122 | 79.3% | 23.7% (vs 12) |
| r5 | 1720 +- 39 | 26 of 116 | 336 | 163-173 | 79.1% | 23.3% (vs 12) |
| r4s5 | 1716 +- 47 | 27 of 116 | 239 | 115-124 | 78.9% | 22.9% (vs 12) |
| r4s4 | 1713 +- 47 | 28 of 116 | 239 | 114-125 | 78.7% | 22.6% (vs 12) |
| r4s1 | 1712 +- 47 | 29 of 116 | 240 | 114-126 | 78.6% | 22.5% (vs 12) |
| cand81s8 | 1705 +- 49 | 30 of 116 | 240 | 85-155 | 78.3% | 21.9% (vs 12) |
| cand87 | 1705 +- 47 | 31 of 116 | 240 | 112-128 | 78.3% | 21.9% (vs 12) |
| r2s4 | 1702 +- 47 | 32 of 116 | 240 | 111-129 | 78.1% | 21.6% (vs 12) |
| cand65 | 1696 +- 75 | 33 of 116 | 96 | 42-54 | 77.8% | 21.2% (vs 12) |
| cand86s2 | 1696 +- 73 | 34 of 116 | 96 | 44-52 | 77.8% | 21.1% (vs 12) |
| r1s16 | 1692 +- 49 | 35 of 116 | 240 | 79-161 | 77.6% | 20.8% (vs 12) |
| r1s11 | 1692 +- 47 | 36 of 116 | 240 | 108-132 | 77.6% | 20.8% (vs 12) |
| r1s14 | 1682 +- 47 | 37 of 116 | 240 | 105-135 | 77.0% | 19.9% (vs 12) |
| r11 | 1672 +- 61 | 38 of 116 | 143 | 61-82 | 76.5% | 19.1% (vs 12) |
| arch_rush | 1655 +- 74 | 39 of 116 | 96 | 39-57 | 75.5% | 17.7% (vs 12) |
| g_iter11 | 1635 +- 48 | 40 of 116 | 240 | 107-133 | 74.2% | 16.2% (vs 12) |
| arch_rush2 | 1630 +- 75 | 41 of 116 | 96 | 36-60 | 73.9% | 15.9% (vs 12) |
| cand49b | 1626 +- 76 | 42 of 116 | 96 | 42-54 | 73.7% | 15.6% (vs 12) |
| g_iter10 | 1621 +- 49 | 43 of 116 | 240 | 103-137 | 73.3% | 15.2% (vs 12) |
| cand43b | 1619 +- 79 | 44 of 116 | 96 | 64-32 | 73.2% | 15.1% (vs 12) |
| cand47d | 1614 +- 55 | 45 of 116 | 192 | 73-119 | 73.0% | 14.8% (vs 12) |
| g_iter9 | 1603 +- 50 | 47 of 116 | 240 | 78-162 | 72.2% | 16.7% (vs 13) |
| g_iter8 | 1573 +- 49 | 49 of 116 | 240 | 108-132 | 70.1% | 17.1% (vs 14) |
| g_iter5 | 1573 +- 34 | 50 of 116 | 618 | 329-289 | 70.1% | 17.0% (vs 14) |
| r1s8 | 1568 +- 79 | 51 of 116 | 96 | 29-67 | 69.7% | 16.7% (vs 14) |
| cand41b | 1567 +- 76 | 52 of 116 | 96 | 51-45 | 69.7% | 16.6% (vs 14) |
| g_iter7 | 1563 +- 45 | 53 of 116 | 288 | 135-153 | 69.4% | 16.3% (vs 14) |
| g_iter6 | 1556 +- 26 | 55 of 116 | 886 | 450-436 | 68.9% | 18.1% (vs 15) |
| cand40c | 1555 +- 76 | 56 of 116 | 96 | 57-39 | 68.7% | 18.0% (vs 15) |
| iter24 | 1551 +- 132 | 57 of 116 | 46 | 11-35 | 68.5% | 17.8% (vs 15) |
| cand42c | 1550 +- 76 | 58 of 116 | 96 | 49-47 | 68.4% | 17.7% (vs 15) |
| r1s3 | 1549 +- 80 | 59 of 116 | 96 | 27-69 | 68.3% | 17.6% (vs 15) |
| g_iter3 | 1539 +- 34 | 60 of 116 | 796 | 167-629 | 67.5% | 16.9% (vs 15) |
| cand37 | 1524 +- 76 | 61 of 116 | 96 | 46-50 | 66.4% | 15.9% (vs 15) |
| g_iter4 | 1519 +- 66 | 62 of 116 | 217 | 43-174 | 65.9% | 15.6% (vs 15) |
| cand31 | 1516 +- 75 | 63 of 116 | 96 | 52-44 | 65.7% | 15.4% (vs 15) |
| g_iter1 | 1464 +- 68 | 64 of 116 | 170 | 95-75 | 61.1% | 12.3% (vs 15) |
| g_iter2 | 1459 +- 70 | 65 of 116 | 192 | 44-148 | 60.7% | 12.0% (vs 15) |
| arch_enclosure | 1312 +- 165 | 69 of 116 | 48 | 4-44 | 45.2% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2143 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2134 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2075 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2020 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 1994 | 85 | 163 | 145-18 | 7% (g_iter12 2-28) |
| 6 | IvanGeffner.finalbota | 1977 | 66 | 233 | 202-31 | 10% (r1s16 3-27) |
| 7 | battlecode20-team4.finalbota | 1971 | 51 | 394 | 341-53 | 17% (g_iter15 6-30) |
| 8 | winkelmantanner.tannerplayer | 1895 | 23 | 1214 | 920-294 | 40% (g_iter16 17-25) |
| 9 | mvpatel2000.qual | 1883 | 23 | 1201 | 894-307 | 26% (g_iter16 11-31) |
| 10 | rzhan11.quals_bot | 1811 | 23 | 944 | 605-339 | 31% (g_iter16 13-29) |
| 11 | EmaPajic.Qualifications | 1771 | 20 | 1253 | 774-479 | 52% (g_iter16 22-20) |
| 12 | **us:g_iter16** | 1766 | 39 | 336 | 170-166 |  |
| 13 | **us:g_iter14** | 1763 | 26 | 763 | 386-377 |  |
| 14 | **us:g_iter15** | 1759 | 24 | 911 | 457-454 |  |
| 15 | **us:r6** | 1757 | 60 | 143 | 67-76 |  |
| 16 | **us:g_iter13** | 1753 | 30 | 576 | 297-279 |  |
| 17 | **us:r8b** | 1745 | 61 | 144 | 65-79 |  |
| 18 | poortho.stable_seeding_bot | 1741 | 19 | 1433 | 855-578 | 55% (g_iter16 23-19) |
| 19 | **us:r10** | 1740 | 61 | 143 | 64-79 |  |
| 20 | **us:r4s3** | 1734 | 47 | 240 | 121-119 |  |
| 21 | **us:cand69** | 1731 | 75 | 96 | 46-50 |  |
| 22 | **us:g_iter12** | 1730 | 40 | 336 | 159-177 |  |
| 23 | **us:cand81** | 1729 | 92 | 95 | 67-28 |  |
| 24 | **us:cand86s3** | 1725 | 47 | 240 | 118-122 |  |
| 25 | **us:r1s13** | 1725 | 47 | 240 | 118-122 |  |
| 26 | **us:r5** | 1720 | 39 | 336 | 163-173 |  |
| 27 | **us:r4s5** | 1716 | 47 | 239 | 115-124 |  |
| 28 | **us:r4s4** | 1713 | 47 | 239 | 114-125 |  |
| 29 | **us:r4s1** | 1712 | 47 | 240 | 114-126 |  |
| 30 | **us:cand81s8** | 1705 | 49 | 240 | 85-155 |  |
| 31 | **us:cand87** | 1705 | 47 | 240 | 112-128 |  |
| 32 | **us:r2s4** | 1702 | 47 | 240 | 111-129 |  |
| 33 | **us:cand65** | 1696 | 75 | 96 | 42-54 |  |
| 34 | **us:cand86s2** | 1696 | 73 | 96 | 44-52 |  |
| 35 | **us:r1s16** | 1692 | 49 | 240 | 79-161 |  |
| 36 | **us:r1s11** | 1692 | 47 | 240 | 108-132 |  |
| 37 | **us:r1s14** | 1682 | 47 | 240 | 105-135 |  |
| 38 | **us:r11** | 1672 | 61 | 143 | 61-82 |  |
| 39 | **us:arch_rush** | 1655 | 74 | 96 | 39-57 |  |
| 40 | **us:g_iter11** | 1635 | 48 | 240 | 107-133 |  |
| 41 | **us:arch_rush2** | 1630 | 75 | 96 | 36-60 |  |
| 42 | **us:cand49b** | 1626 | 76 | 96 | 42-54 |  |
| 43 | **us:g_iter10** | 1621 | 49 | 240 | 103-137 |  |
| 44 | **us:cand43b** | 1619 | 79 | 96 | 64-32 |  |
| 45 | **us:cand47d** | 1614 | 55 | 192 | 73-119 |  |
| 46 | laurenschneider.pdx_team_one | 1607 | 19 | 1400 | 587-813 | 71% (g_iter16 30-12) |
| 47 | **us:g_iter9** | 1603 | 50 | 240 | 78-162 |  |
| 48 | cormackikkert.whyPermutator | 1589 | 19 | 1423 | 561-862 | 86% (g_iter16 36-6) |
| 49 | **us:g_iter8** | 1573 | 49 | 240 | 108-132 |  |
| 50 | **us:g_iter5** | 1573 | 34 | 618 | 329-289 |  |
| 51 | **us:r1s8** | 1568 | 79 | 96 | 29-67 |  |
| 52 | **us:cand41b** | 1567 | 76 | 96 | 51-45 |  |
| 53 | **us:g_iter7** | 1563 | 45 | 288 | 135-153 |  |
| 54 | benzyx.seeding | 1562 | 21 | 1204 | 456-748 | 80% (g_iter15 48-12) |
| 55 | **us:g_iter6** | 1556 | 26 | 886 | 450-436 |  |
| 56 | **us:cand40c** | 1555 | 76 | 96 | 57-39 |  |
| 57 | **us:iter24** | 1551 | 132 | 46 | 11-35 |  |
| 58 | **us:cand42c** | 1550 | 76 | 96 | 49-47 |  |
| 59 | **us:r1s3** | 1549 | 80 | 96 | 27-69 |  |
| 60 | **us:g_iter3** | 1539 | 34 | 796 | 167-629 |  |
| 61 | **us:cand37** | 1524 | 76 | 96 | 46-50 |  |
| 62 | **us:g_iter4** | 1519 | 66 | 217 | 43-174 |  |
| 63 | **us:cand31** | 1516 | 75 | 96 | 52-44 |  |
| 64 | **us:g_iter1** | 1464 | 68 | 170 | 95-75 |  |
| 65 | **us:g_iter2** | 1459 | 70 | 192 | 44-148 |  |
| 66 | wpine215.stardustv2 | 1387 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 67 | mhahn2003.nonrush | 1339 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 68 | eggag32.BrutalPigeonBot | 1339 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 69 | **us:arch_enclosure** | 1312 | 165 | 48 | 4-44 |  |
| 70 | cs454-w20-team3.playbot | 1311 | 112 | 56 | 11-45 |  |
| 71 | ARognes.FinalSubmission | 1300 | 144 | 37 | 6-31 |  |
| 72 | TeamSerpentine.noodleBot | 1290 | 143 | 38 | 6-32 |  |
| 73 | opheez.landscapers | 1286 | 267 | 9 | 1-8 |  |
| 74 | ovimura.welovesoup | 1286 | 267 | 9 | 1-8 |  |
| 75 | rsandzimier.SandSibs_seeding | 1286 | 267 | 9 | 1-8 |  |
| 76 | thissop.alpha | 1286 | 267 | 9 | 1-8 |  |
| 77 | VinayaBhat.team10pdx | 1281 | 222 | 15 | 2-13 |  |
| 78 | LucianCov.ourRobot | 1278 | 383 | 3 | 0-3 |  |
| 79 | MrHoseongLee.Neptune_v3 | 1278 | 383 | 3 | 0-3 |  |
| 80 | Phrancium.Frankplayer1 | 1278 | 383 | 3 | 0-3 |  |
| 81 | Pleket.Bot | 1278 | 383 | 3 | 0-3 |  |
| 82 | Strequals.rw8 | 1278 | 383 | 3 | 0-3 |  |
| 83 | Sukanya-Kothapally.team4player | 1278 | 383 | 3 | 0-3 |  |
| 84 | TeamSerpentine.eendagsvliegjes | 1278 | 383 | 3 | 0-3 |  |
| 85 | Tolsi.mybot | 1278 | 383 | 3 | 0-3 |  |
| 86 | anthonybench.FunkBot | 1278 | 383 | 3 | 0-3 |  |
| 87 | atliSig.buttletplayer | 1278 | 383 | 3 | 0-3 |  |
| 88 | charboltron.team11newbot | 1278 | 383 | 3 | 0-3 |  |
| 89 | djkeyes.addingComm | 1278 | 383 | 3 | 0-3 |  |
| 90 | fewella.FirstPlayer | 1278 | 383 | 3 | 0-3 |  |
| 91 | jmerle.camel_case_sprint | 1278 | 383 | 3 | 0-3 |  |
| 92 | kylittle.qualsbot2 | 1278 | 383 | 3 | 0-3 |  |
| 93 | lfchain.bigBudsBot | 1278 | 383 | 3 | 0-3 |  |
| 94 | luisgonzalex.CodeMonkeys | 1278 | 383 | 3 | 0-3 |  |
| 95 | mama4294.maloneplayer | 1278 | 383 | 3 | 0-3 |  |
| 96 | max-titov.seeding | 1278 | 383 | 3 | 0-3 |  |
| 97 | michaeltliu.beginnerplayer | 1278 | 383 | 3 | 0-3 |  |
| 98 | monmouth-college-cs.MyFirstPlayer | 1278 | 383 | 3 | 0-3 |  |
| 99 | ngkuru.qualifyingtournament | 1278 | 383 | 3 | 0-3 |  |
| 100 | orionquick.aldebaranplayer | 1278 | 383 | 3 | 0-3 |  |
| 101 | snpushpi.whatamidoing | 1278 | 383 | 3 | 0-3 |  |
| 102 | stevetimberman.playerbbbbb | 1278 | 383 | 3 | 0-3 |  |
| 103 | willBoyd8.bb8 | 1278 | 383 | 3 | 0-3 |  |
| 104 | yaonam.Robot_1 | 1277 | 154 | 33 | 5-28 |  |
| 105 | cosimogonnelli.Team3player | 1275 | 274 | 7 | 1-6 |  |
| 106 | WilliamYue37.Player1 | 1270 | 140 | 46 | 6-40 |  |
| 107 | Tim-gubski.AngryWaffleMaker | 1263 | 154 | 32 | 5-27 |  |
| 108 | jenlz.bustedJulianbot | 1259 | 261 | 14 | 1-13 |  |
| 109 | A9ine.potato | 1158 | 365 | 6 | 0-6 |  |
| 110 | 9mAhmad.MahinBot | 1131 | 362 | 7 | 0-7 |  |
| 111 | 9mAhmad.lostincoordinates | 1131 | 362 | 7 | 0-7 |  |
| 112 | AllenWang314.bot1 | 1131 | 362 | 7 | 0-7 |  |
| 113 | GabrielDWu.buildawall2 | 1131 | 362 | 7 | 0-7 |  |
| 114 | J-J-Chen.player | 1131 | 362 | 7 | 0-7 |  |
| 115 | KyleHassold.sprintbot | 1131 | 362 | 7 | 0-7 |  |
| 116 | denver-blake.sprint | 1131 | 362 | 7 | 0-7 |  |
