# Ladder

12031 scrimmages (ours only), 11507 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter15 | 1771 +- 33 | 12 of 113 | 479 | 239-240 | 81.2% | 25.4% (vs 11) |
| g_iter14 | 1770 +- 26 | 13 of 113 | 763 | 386-377 | 81.2% | 25.4% (vs 11) |
| r6 | 1764 +- 60 | 14 of 113 | 143 | 67-76 | 80.9% | 24.7% (vs 11) |
| g_iter13 | 1761 +- 30 | 15 of 113 | 576 | 297-279 | 80.8% | 24.5% (vs 11) |
| r8b | 1754 +- 60 | 16 of 113 | 144 | 65-79 | 80.4% | 23.7% (vs 11) |
| r4s3 | 1743 +- 46 | 18 of 113 | 240 | 121-119 | 79.9% | 24.9% (vs 12) |
| g_iter12 | 1740 +- 40 | 19 of 113 | 336 | 159-177 | 79.8% | 24.7% (vs 12) |
| cand69 | 1740 +- 75 | 20 of 113 | 96 | 46-50 | 79.7% | 24.6% (vs 12) |
| cand81 | 1738 +- 92 | 21 of 113 | 95 | 67-28 | 79.6% | 24.4% (vs 12) |
| r1s13 | 1733 +- 46 | 22 of 113 | 240 | 118-122 | 79.4% | 24.0% (vs 12) |
| cand86s3 | 1733 +- 46 | 23 of 113 | 240 | 118-122 | 79.4% | 24.0% (vs 12) |
| r5 | 1729 +- 39 | 24 of 113 | 336 | 163-173 | 79.2% | 23.6% (vs 12) |
| r4s5 | 1725 +- 47 | 25 of 113 | 239 | 115-124 | 79.0% | 23.2% (vs 12) |
| r4s4 | 1722 +- 47 | 26 of 113 | 239 | 114-125 | 78.8% | 22.9% (vs 12) |
| r4s1 | 1720 +- 46 | 27 of 113 | 240 | 114-126 | 78.7% | 22.8% (vs 12) |
| cand87 | 1714 +- 47 | 28 of 113 | 240 | 112-128 | 78.4% | 22.2% (vs 12) |
| cand81s8 | 1712 +- 49 | 29 of 113 | 240 | 85-155 | 78.3% | 22.0% (vs 12) |
| r2s4 | 1710 +- 47 | 30 of 113 | 240 | 111-129 | 78.2% | 21.9% (vs 12) |
| cand65 | 1706 +- 75 | 31 of 113 | 96 | 42-54 | 78.0% | 21.5% (vs 12) |
| cand86s2 | 1705 +- 73 | 32 of 113 | 96 | 44-52 | 77.9% | 21.4% (vs 12) |
| r1s11 | 1701 +- 47 | 33 of 113 | 240 | 108-132 | 77.7% | 21.0% (vs 12) |
| r1s16 | 1699 +- 49 | 34 of 113 | 240 | 79-161 | 77.6% | 20.9% (vs 12) |
| r1s14 | 1691 +- 47 | 35 of 113 | 240 | 105-135 | 77.2% | 20.2% (vs 12) |
| arch_rush | 1664 +- 74 | 36 of 113 | 96 | 39-57 | 75.6% | 18.0% (vs 12) |
| g_iter11 | 1645 +- 48 | 37 of 113 | 240 | 107-133 | 74.5% | 16.5% (vs 12) |
| arch_rush2 | 1639 +- 75 | 38 of 113 | 96 | 36-60 | 74.1% | 16.1% (vs 12) |
| cand49b | 1636 +- 76 | 39 of 113 | 96 | 42-54 | 73.9% | 15.9% (vs 12) |
| g_iter10 | 1631 +- 49 | 40 of 113 | 240 | 103-137 | 73.6% | 15.5% (vs 12) |
| cand43b | 1628 +- 79 | 41 of 113 | 96 | 64-32 | 73.4% | 15.3% (vs 12) |
| cand47d | 1625 +- 55 | 42 of 113 | 192 | 73-119 | 73.2% | 15.1% (vs 12) |
| g_iter9 | 1613 +- 50 | 44 of 113 | 240 | 78-162 | 72.4% | 17.0% (vs 13) |
| g_iter8 | 1584 +- 49 | 46 of 113 | 240 | 108-132 | 70.4% | 17.3% (vs 14) |
| g_iter5 | 1581 +- 34 | 47 of 113 | 618 | 329-289 | 70.2% | 17.1% (vs 14) |
| cand41b | 1578 +- 76 | 48 of 113 | 96 | 51-45 | 70.0% | 16.8% (vs 14) |
| r1s8 | 1577 +- 79 | 49 of 113 | 96 | 29-67 | 70.0% | 16.8% (vs 14) |
| g_iter7 | 1573 +- 45 | 51 of 113 | 288 | 135-153 | 69.7% | 18.8% (vs 15) |
| g_iter6 | 1566 +- 26 | 52 of 113 | 886 | 450-436 | 69.1% | 18.2% (vs 15) |
| cand40c | 1564 +- 76 | 53 of 113 | 96 | 57-39 | 69.0% | 18.1% (vs 15) |
| cand42c | 1560 +- 76 | 54 of 113 | 96 | 49-47 | 68.7% | 17.8% (vs 15) |
| iter24 | 1560 +- 132 | 55 of 113 | 46 | 11-35 | 68.7% | 17.8% (vs 15) |
| r1s3 | 1558 +- 80 | 56 of 113 | 96 | 27-69 | 68.6% | 17.7% (vs 15) |
| g_iter3 | 1547 +- 34 | 57 of 113 | 796 | 167-629 | 67.7% | 16.9% (vs 15) |
| cand37 | 1535 +- 76 | 58 of 113 | 96 | 46-50 | 66.7% | 16.1% (vs 15) |
| g_iter4 | 1527 +- 65 | 59 of 113 | 217 | 43-174 | 66.1% | 15.5% (vs 15) |
| cand31 | 1525 +- 75 | 60 of 113 | 96 | 52-44 | 66.0% | 15.5% (vs 15) |
| g_iter1 | 1471 +- 68 | 61 of 113 | 170 | 95-75 | 61.2% | 12.2% (vs 15) |
| g_iter2 | 1468 +- 70 | 62 of 113 | 192 | 44-148 | 60.9% | 12.0% (vs 15) |
| arch_enclosure | 1321 +- 165 | 66 of 113 | 48 | 4-44 | 45.6% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2152 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2142 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2083 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2028 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 2003 | 85 | 163 | 145-18 | 7% (g_iter12 2-28) |
| 6 | IvanGeffner.finalbota | 1974 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 7 | battlecode20-team4.finalbota | 1968 | 53 | 358 | 310-48 | 19% (g_iter14 7-29) |
| 8 | winkelmantanner.tannerplayer | 1900 | 25 | 1082 | 824-258 | 35% (g_iter15 21-39) |
| 9 | mvpatel2000.qual | 1889 | 24 | 1069 | 800-269 | 27% (g_iter15 16-44) |
| 10 | rzhan11.quals_bot | 1808 | 25 | 812 | 515-297 | 38% (g_iter15 23-37) |
| 11 | EmaPajic.Qualifications | 1793 | 22 | 1123 | 724-399 | 63% (g_iter15 37-22) |
| 12 | **us:g_iter15** | 1771 | 33 | 479 | 239-240 |  |
| 13 | **us:g_iter14** | 1770 | 26 | 763 | 386-377 |  |
| 14 | **us:r6** | 1764 | 60 | 143 | 67-76 |  |
| 15 | **us:g_iter13** | 1761 | 30 | 576 | 297-279 |  |
| 16 | **us:r8b** | 1754 | 60 | 144 | 65-79 |  |
| 17 | poortho.stable_seeding_bot | 1746 | 20 | 1301 | 783-518 | 48% (g_iter15 29-31) |
| 18 | **us:r4s3** | 1743 | 46 | 240 | 121-119 |  |
| 19 | **us:g_iter12** | 1740 | 40 | 336 | 159-177 |  |
| 20 | **us:cand69** | 1740 | 75 | 96 | 46-50 |  |
| 21 | **us:cand81** | 1738 | 92 | 95 | 67-28 |  |
| 22 | **us:r1s13** | 1733 | 46 | 240 | 118-122 |  |
| 23 | **us:cand86s3** | 1733 | 46 | 240 | 118-122 |  |
| 24 | **us:r5** | 1729 | 39 | 336 | 163-173 |  |
| 25 | **us:r4s5** | 1725 | 47 | 239 | 115-124 |  |
| 26 | **us:r4s4** | 1722 | 47 | 239 | 114-125 |  |
| 27 | **us:r4s1** | 1720 | 46 | 240 | 114-126 |  |
| 28 | **us:cand87** | 1714 | 47 | 240 | 112-128 |  |
| 29 | **us:cand81s8** | 1712 | 49 | 240 | 85-155 |  |
| 30 | **us:r2s4** | 1710 | 47 | 240 | 111-129 |  |
| 31 | **us:cand65** | 1706 | 75 | 96 | 42-54 |  |
| 32 | **us:cand86s2** | 1705 | 73 | 96 | 44-52 |  |
| 33 | **us:r1s11** | 1701 | 47 | 240 | 108-132 |  |
| 34 | **us:r1s16** | 1699 | 49 | 240 | 79-161 |  |
| 35 | **us:r1s14** | 1691 | 47 | 240 | 105-135 |  |
| 36 | **us:arch_rush** | 1664 | 74 | 96 | 39-57 |  |
| 37 | **us:g_iter11** | 1645 | 48 | 240 | 107-133 |  |
| 38 | **us:arch_rush2** | 1639 | 75 | 96 | 36-60 |  |
| 39 | **us:cand49b** | 1636 | 76 | 96 | 42-54 |  |
| 40 | **us:g_iter10** | 1631 | 49 | 240 | 103-137 |  |
| 41 | **us:cand43b** | 1628 | 79 | 96 | 64-32 |  |
| 42 | **us:cand47d** | 1625 | 55 | 192 | 73-119 |  |
| 43 | laurenschneider.pdx_team_one | 1613 | 20 | 1268 | 541-727 | 68% (g_iter15 41-19) |
| 44 | **us:g_iter9** | 1613 | 50 | 240 | 78-162 |  |
| 45 | cormackikkert.whyPermutator | 1606 | 20 | 1291 | 535-756 | 73% (g_iter15 44-16) |
| 46 | **us:g_iter8** | 1584 | 49 | 240 | 108-132 |  |
| 47 | **us:g_iter5** | 1581 | 34 | 618 | 329-289 |  |
| 48 | **us:cand41b** | 1578 | 76 | 96 | 51-45 |  |
| 49 | **us:r1s8** | 1577 | 79 | 96 | 29-67 |  |
| 50 | benzyx.seeding | 1573 | 21 | 1132 | 440-692 | 83% (g_iter14 40-8) |
| 51 | **us:g_iter7** | 1573 | 45 | 288 | 135-153 |  |
| 52 | **us:g_iter6** | 1566 | 26 | 886 | 450-436 |  |
| 53 | **us:cand40c** | 1564 | 76 | 96 | 57-39 |  |
| 54 | **us:cand42c** | 1560 | 76 | 96 | 49-47 |  |
| 55 | **us:iter24** | 1560 | 132 | 46 | 11-35 |  |
| 56 | **us:r1s3** | 1558 | 80 | 96 | 27-69 |  |
| 57 | **us:g_iter3** | 1547 | 34 | 796 | 167-629 |  |
| 58 | **us:cand37** | 1535 | 76 | 96 | 46-50 |  |
| 59 | **us:g_iter4** | 1527 | 65 | 217 | 43-174 |  |
| 60 | **us:cand31** | 1525 | 75 | 96 | 52-44 |  |
| 61 | **us:g_iter1** | 1471 | 68 | 170 | 95-75 |  |
| 62 | **us:g_iter2** | 1468 | 70 | 192 | 44-148 |  |
| 63 | wpine215.stardustv2 | 1396 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 64 | mhahn2003.nonrush | 1349 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 65 | eggag32.BrutalPigeonBot | 1349 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 66 | **us:arch_enclosure** | 1321 | 165 | 48 | 4-44 |  |
| 67 | cs454-w20-team3.playbot | 1319 | 112 | 56 | 11-45 |  |
| 68 | ARognes.FinalSubmission | 1308 | 144 | 37 | 6-31 |  |
| 69 | TeamSerpentine.noodleBot | 1298 | 143 | 38 | 6-32 |  |
| 70 | opheez.landscapers | 1292 | 267 | 9 | 1-8 |  |
| 71 | ovimura.welovesoup | 1292 | 267 | 9 | 1-8 |  |
| 72 | rsandzimier.SandSibs_seeding | 1292 | 267 | 9 | 1-8 |  |
| 73 | thissop.alpha | 1292 | 267 | 9 | 1-8 |  |
| 74 | VinayaBhat.team10pdx | 1287 | 222 | 15 | 2-13 |  |
| 75 | yaonam.Robot_1 | 1285 | 154 | 33 | 5-28 |  |
| 76 | LucianCov.ourRobot | 1282 | 383 | 3 | 0-3 |  |
| 77 | MrHoseongLee.Neptune_v3 | 1282 | 383 | 3 | 0-3 |  |
| 78 | Phrancium.Frankplayer1 | 1282 | 383 | 3 | 0-3 |  |
| 79 | Pleket.Bot | 1282 | 383 | 3 | 0-3 |  |
| 80 | Strequals.rw8 | 1282 | 383 | 3 | 0-3 |  |
| 81 | Sukanya-Kothapally.team4player | 1282 | 383 | 3 | 0-3 |  |
| 82 | TeamSerpentine.eendagsvliegjes | 1282 | 383 | 3 | 0-3 |  |
| 83 | Tolsi.mybot | 1282 | 383 | 3 | 0-3 |  |
| 84 | anthonybench.FunkBot | 1282 | 383 | 3 | 0-3 |  |
| 85 | atliSig.buttletplayer | 1282 | 383 | 3 | 0-3 |  |
| 86 | charboltron.team11newbot | 1282 | 383 | 3 | 0-3 |  |
| 87 | djkeyes.addingComm | 1282 | 383 | 3 | 0-3 |  |
| 88 | fewella.FirstPlayer | 1282 | 383 | 3 | 0-3 |  |
| 89 | jmerle.camel_case_sprint | 1282 | 383 | 3 | 0-3 |  |
| 90 | kylittle.qualsbot2 | 1282 | 383 | 3 | 0-3 |  |
| 91 | lfchain.bigBudsBot | 1282 | 383 | 3 | 0-3 |  |
| 92 | luisgonzalex.CodeMonkeys | 1282 | 383 | 3 | 0-3 |  |
| 93 | mama4294.maloneplayer | 1282 | 383 | 3 | 0-3 |  |
| 94 | max-titov.seeding | 1282 | 383 | 3 | 0-3 |  |
| 95 | michaeltliu.beginnerplayer | 1282 | 383 | 3 | 0-3 |  |
| 96 | monmouth-college-cs.MyFirstPlayer | 1282 | 383 | 3 | 0-3 |  |
| 97 | ngkuru.qualifyingtournament | 1282 | 383 | 3 | 0-3 |  |
| 98 | orionquick.aldebaranplayer | 1282 | 383 | 3 | 0-3 |  |
| 99 | snpushpi.whatamidoing | 1282 | 383 | 3 | 0-3 |  |
| 100 | stevetimberman.playerbbbbb | 1282 | 383 | 3 | 0-3 |  |
| 101 | willBoyd8.bb8 | 1282 | 383 | 3 | 0-3 |  |
| 102 | cosimogonnelli.Team3player | 1281 | 274 | 7 | 1-6 |  |
| 103 | WilliamYue37.Player1 | 1278 | 140 | 46 | 6-40 |  |
| 104 | Tim-gubski.AngryWaffleMaker | 1272 | 154 | 32 | 5-27 |  |
| 105 | jenlz.bustedJulianbot | 1266 | 261 | 14 | 1-13 |  |
| 106 | A9ine.potato | 1163 | 365 | 6 | 0-6 |  |
| 107 | 9mAhmad.MahinBot | 1136 | 362 | 7 | 0-7 |  |
| 108 | 9mAhmad.lostincoordinates | 1136 | 362 | 7 | 0-7 |  |
| 109 | AllenWang314.bot1 | 1136 | 362 | 7 | 0-7 |  |
| 110 | GabrielDWu.buildawall2 | 1136 | 362 | 7 | 0-7 |  |
| 111 | J-J-Chen.player | 1136 | 362 | 7 | 0-7 |  |
| 112 | KyleHassold.sprintbot | 1136 | 362 | 7 | 0-7 |  |
| 113 | denver-blake.sprint | 1136 | 362 | 7 | 0-7 |  |
