# Ladder

12366 scrimmages (ours only), 11842 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter15 | 1768 +- 28 | 12 of 114 | 671 | 333-338 | 81.2% | 25.4% (vs 11) |
| g_iter14 | 1767 +- 26 | 13 of 114 | 763 | 386-377 | 81.2% | 25.3% (vs 11) |
| r6 | 1761 +- 60 | 14 of 114 | 143 | 67-76 | 80.9% | 24.7% (vs 11) |
| g_iter13 | 1758 +- 30 | 15 of 114 | 576 | 297-279 | 80.7% | 24.4% (vs 11) |
| r8b | 1750 +- 60 | 16 of 114 | 144 | 65-79 | 80.4% | 23.6% (vs 11) |
| r10 | 1745 +- 61 | 18 of 114 | 143 | 64-79 | 80.1% | 25.4% (vs 12) |
| r4s3 | 1739 +- 46 | 19 of 114 | 240 | 121-119 | 79.8% | 24.8% (vs 12) |
| g_iter12 | 1737 +- 40 | 20 of 114 | 336 | 159-177 | 79.7% | 24.5% (vs 12) |
| cand69 | 1736 +- 75 | 21 of 114 | 96 | 46-50 | 79.7% | 24.5% (vs 12) |
| cand81 | 1734 +- 92 | 22 of 114 | 95 | 67-28 | 79.6% | 24.3% (vs 12) |
| r1s13 | 1730 +- 46 | 23 of 114 | 240 | 118-122 | 79.3% | 23.9% (vs 12) |
| cand86s3 | 1730 +- 46 | 24 of 114 | 240 | 118-122 | 79.3% | 23.9% (vs 12) |
| r5 | 1725 +- 39 | 25 of 114 | 336 | 163-173 | 79.1% | 23.4% (vs 12) |
| r4s5 | 1721 +- 47 | 26 of 114 | 239 | 115-124 | 78.9% | 23.1% (vs 12) |
| r4s4 | 1718 +- 47 | 27 of 114 | 239 | 114-125 | 78.7% | 22.8% (vs 12) |
| r4s1 | 1717 +- 46 | 28 of 114 | 240 | 114-126 | 78.7% | 22.7% (vs 12) |
| cand87 | 1710 +- 47 | 29 of 114 | 240 | 112-128 | 78.3% | 22.1% (vs 12) |
| cand81s8 | 1709 +- 49 | 30 of 114 | 240 | 85-155 | 78.3% | 22.0% (vs 12) |
| r2s4 | 1707 +- 47 | 31 of 114 | 240 | 111-129 | 78.2% | 21.8% (vs 12) |
| cand65 | 1703 +- 75 | 32 of 114 | 96 | 42-54 | 77.9% | 21.4% (vs 12) |
| cand86s2 | 1701 +- 73 | 33 of 114 | 96 | 44-52 | 77.8% | 21.3% (vs 12) |
| r1s11 | 1697 +- 47 | 34 of 114 | 240 | 108-132 | 77.6% | 20.9% (vs 12) |
| r1s16 | 1696 +- 49 | 35 of 114 | 240 | 79-161 | 77.6% | 20.8% (vs 12) |
| r1s14 | 1687 +- 47 | 36 of 114 | 240 | 105-135 | 77.1% | 20.1% (vs 12) |
| arch_rush | 1660 +- 74 | 37 of 114 | 96 | 39-57 | 75.5% | 17.9% (vs 12) |
| g_iter11 | 1641 +- 48 | 38 of 114 | 240 | 107-133 | 74.4% | 16.4% (vs 12) |
| arch_rush2 | 1635 +- 75 | 39 of 114 | 96 | 36-60 | 74.0% | 16.0% (vs 12) |
| cand49b | 1632 +- 76 | 40 of 114 | 96 | 42-54 | 73.8% | 15.8% (vs 12) |
| g_iter10 | 1627 +- 49 | 41 of 114 | 240 | 103-137 | 73.5% | 15.4% (vs 12) |
| cand43b | 1625 +- 79 | 42 of 114 | 96 | 64-32 | 73.4% | 15.3% (vs 12) |
| cand47d | 1621 +- 55 | 43 of 114 | 192 | 73-119 | 73.1% | 15.0% (vs 12) |
| g_iter9 | 1610 +- 50 | 45 of 114 | 240 | 78-162 | 72.4% | 17.0% (vs 13) |
| g_iter8 | 1580 +- 49 | 47 of 114 | 240 | 108-132 | 70.3% | 17.2% (vs 14) |
| g_iter5 | 1579 +- 34 | 48 of 114 | 618 | 329-289 | 70.2% | 17.1% (vs 14) |
| cand41b | 1574 +- 76 | 49 of 114 | 96 | 51-45 | 69.9% | 16.8% (vs 14) |
| r1s8 | 1574 +- 79 | 50 of 114 | 96 | 29-67 | 69.8% | 16.8% (vs 14) |
| g_iter7 | 1569 +- 45 | 51 of 114 | 288 | 135-153 | 69.5% | 16.5% (vs 14) |
| g_iter6 | 1563 +- 26 | 53 of 114 | 886 | 450-436 | 69.1% | 18.2% (vs 15) |
| cand40c | 1561 +- 76 | 54 of 114 | 96 | 57-39 | 68.9% | 18.1% (vs 15) |
| iter24 | 1557 +- 132 | 55 of 114 | 46 | 11-35 | 68.6% | 17.8% (vs 15) |
| cand42c | 1557 +- 76 | 56 of 114 | 96 | 49-47 | 68.6% | 17.8% (vs 15) |
| r1s3 | 1555 +- 80 | 57 of 114 | 96 | 27-69 | 68.4% | 17.6% (vs 15) |
| g_iter3 | 1544 +- 34 | 58 of 114 | 796 | 167-629 | 67.6% | 16.9% (vs 15) |
| cand37 | 1531 +- 76 | 59 of 114 | 96 | 46-50 | 66.6% | 16.0% (vs 15) |
| g_iter4 | 1524 +- 66 | 60 of 114 | 217 | 43-174 | 66.0% | 15.6% (vs 15) |
| cand31 | 1522 +- 75 | 61 of 114 | 96 | 52-44 | 65.9% | 15.5% (vs 15) |
| g_iter1 | 1468 +- 68 | 62 of 114 | 170 | 95-75 | 61.2% | 12.2% (vs 15) |
| g_iter2 | 1465 +- 70 | 63 of 114 | 192 | 44-148 | 60.8% | 12.0% (vs 15) |
| arch_enclosure | 1318 +- 165 | 67 of 114 | 48 | 4-44 | 45.5% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2149 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2139 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2080 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2025 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 2000 | 85 | 163 | 145-18 | 7% (g_iter12 2-28) |
| 6 | battlecode20-team4.finalbota | 1977 | 51 | 394 | 341-53 | 17% (g_iter15 6-30) |
| 7 | IvanGeffner.finalbota | 1971 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 8 | winkelmantanner.tannerplayer | 1900 | 24 | 1124 | 857-267 | 35% (g_iter15 29-55) |
| 9 | mvpatel2000.qual | 1886 | 24 | 1111 | 829-282 | 30% (g_iter15 25-59) |
| 10 | rzhan11.quals_bot | 1805 | 25 | 854 | 540-314 | 39% (g_iter15 33-51) |
| 11 | EmaPajic.Qualifications | 1782 | 21 | 1164 | 735-429 | 66% (g_iter15 55-28) |
| 12 | **us:g_iter15** | 1768 | 28 | 671 | 333-338 |  |
| 13 | **us:g_iter14** | 1767 | 26 | 763 | 386-377 |  |
| 14 | **us:r6** | 1761 | 60 | 143 | 67-76 |  |
| 15 | **us:g_iter13** | 1758 | 30 | 576 | 297-279 |  |
| 16 | **us:r8b** | 1750 | 60 | 144 | 65-79 |  |
| 17 | poortho.stable_seeding_bot | 1745 | 19 | 1343 | 807-536 | 46% (g_iter15 39-45) |
| 18 | **us:r10** | 1745 | 61 | 143 | 64-79 |  |
| 19 | **us:r4s3** | 1739 | 46 | 240 | 121-119 |  |
| 20 | **us:g_iter12** | 1737 | 40 | 336 | 159-177 |  |
| 21 | **us:cand69** | 1736 | 75 | 96 | 46-50 |  |
| 22 | **us:cand81** | 1734 | 92 | 95 | 67-28 |  |
| 23 | **us:r1s13** | 1730 | 46 | 240 | 118-122 |  |
| 24 | **us:cand86s3** | 1730 | 46 | 240 | 118-122 |  |
| 25 | **us:r5** | 1725 | 39 | 336 | 163-173 |  |
| 26 | **us:r4s5** | 1721 | 47 | 239 | 115-124 |  |
| 27 | **us:r4s4** | 1718 | 47 | 239 | 114-125 |  |
| 28 | **us:r4s1** | 1717 | 46 | 240 | 114-126 |  |
| 29 | **us:cand87** | 1710 | 47 | 240 | 112-128 |  |
| 30 | **us:cand81s8** | 1709 | 49 | 240 | 85-155 |  |
| 31 | **us:r2s4** | 1707 | 47 | 240 | 111-129 |  |
| 32 | **us:cand65** | 1703 | 75 | 96 | 42-54 |  |
| 33 | **us:cand86s2** | 1701 | 73 | 96 | 44-52 |  |
| 34 | **us:r1s11** | 1697 | 47 | 240 | 108-132 |  |
| 35 | **us:r1s16** | 1696 | 49 | 240 | 79-161 |  |
| 36 | **us:r1s14** | 1687 | 47 | 240 | 105-135 |  |
| 37 | **us:arch_rush** | 1660 | 74 | 96 | 39-57 |  |
| 38 | **us:g_iter11** | 1641 | 48 | 240 | 107-133 |  |
| 39 | **us:arch_rush2** | 1635 | 75 | 96 | 36-60 |  |
| 40 | **us:cand49b** | 1632 | 76 | 96 | 42-54 |  |
| 41 | **us:g_iter10** | 1627 | 49 | 240 | 103-137 |  |
| 42 | **us:cand43b** | 1625 | 79 | 96 | 64-32 |  |
| 43 | **us:cand47d** | 1621 | 55 | 192 | 73-119 |  |
| 44 | laurenschneider.pdx_team_one | 1612 | 20 | 1310 | 557-753 | 65% (g_iter15 55-29) |
| 45 | **us:g_iter9** | 1610 | 50 | 240 | 78-162 |  |
| 46 | cormackikkert.whyPermutator | 1599 | 20 | 1333 | 542-791 | 75% (g_iter15 63-21) |
| 47 | **us:g_iter8** | 1580 | 49 | 240 | 108-132 |  |
| 48 | **us:g_iter5** | 1579 | 34 | 618 | 329-289 |  |
| 49 | **us:cand41b** | 1574 | 76 | 96 | 51-45 |  |
| 50 | **us:r1s8** | 1574 | 79 | 96 | 29-67 |  |
| 51 | **us:g_iter7** | 1569 | 45 | 288 | 135-153 |  |
| 52 | benzyx.seeding | 1569 | 21 | 1138 | 441-697 | 83% (g_iter15 25-5) |
| 53 | **us:g_iter6** | 1563 | 26 | 886 | 450-436 |  |
| 54 | **us:cand40c** | 1561 | 76 | 96 | 57-39 |  |
| 55 | **us:iter24** | 1557 | 132 | 46 | 11-35 |  |
| 56 | **us:cand42c** | 1557 | 76 | 96 | 49-47 |  |
| 57 | **us:r1s3** | 1555 | 80 | 96 | 27-69 |  |
| 58 | **us:g_iter3** | 1544 | 34 | 796 | 167-629 |  |
| 59 | **us:cand37** | 1531 | 76 | 96 | 46-50 |  |
| 60 | **us:g_iter4** | 1524 | 66 | 217 | 43-174 |  |
| 61 | **us:cand31** | 1522 | 75 | 96 | 52-44 |  |
| 62 | **us:g_iter1** | 1468 | 68 | 170 | 95-75 |  |
| 63 | **us:g_iter2** | 1465 | 70 | 192 | 44-148 |  |
| 64 | wpine215.stardustv2 | 1393 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 65 | mhahn2003.nonrush | 1345 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 66 | eggag32.BrutalPigeonBot | 1345 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 67 | **us:arch_enclosure** | 1318 | 165 | 48 | 4-44 |  |
| 68 | cs454-w20-team3.playbot | 1316 | 112 | 56 | 11-45 |  |
| 69 | ARognes.FinalSubmission | 1306 | 144 | 37 | 6-31 |  |
| 70 | TeamSerpentine.noodleBot | 1295 | 143 | 38 | 6-32 |  |
| 71 | opheez.landscapers | 1290 | 267 | 9 | 1-8 |  |
| 72 | ovimura.welovesoup | 1290 | 267 | 9 | 1-8 |  |
| 73 | rsandzimier.SandSibs_seeding | 1290 | 267 | 9 | 1-8 |  |
| 74 | thissop.alpha | 1290 | 267 | 9 | 1-8 |  |
| 75 | VinayaBhat.team10pdx | 1285 | 222 | 15 | 2-13 |  |
| 76 | yaonam.Robot_1 | 1283 | 154 | 33 | 5-28 |  |
| 77 | LucianCov.ourRobot | 1281 | 383 | 3 | 0-3 |  |
| 78 | MrHoseongLee.Neptune_v3 | 1281 | 383 | 3 | 0-3 |  |
| 79 | Phrancium.Frankplayer1 | 1281 | 383 | 3 | 0-3 |  |
| 80 | Pleket.Bot | 1281 | 383 | 3 | 0-3 |  |
| 81 | Strequals.rw8 | 1281 | 383 | 3 | 0-3 |  |
| 82 | Sukanya-Kothapally.team4player | 1281 | 383 | 3 | 0-3 |  |
| 83 | TeamSerpentine.eendagsvliegjes | 1281 | 383 | 3 | 0-3 |  |
| 84 | Tolsi.mybot | 1281 | 383 | 3 | 0-3 |  |
| 85 | anthonybench.FunkBot | 1281 | 383 | 3 | 0-3 |  |
| 86 | atliSig.buttletplayer | 1281 | 383 | 3 | 0-3 |  |
| 87 | charboltron.team11newbot | 1281 | 383 | 3 | 0-3 |  |
| 88 | djkeyes.addingComm | 1281 | 383 | 3 | 0-3 |  |
| 89 | fewella.FirstPlayer | 1281 | 383 | 3 | 0-3 |  |
| 90 | jmerle.camel_case_sprint | 1281 | 383 | 3 | 0-3 |  |
| 91 | kylittle.qualsbot2 | 1281 | 383 | 3 | 0-3 |  |
| 92 | lfchain.bigBudsBot | 1281 | 383 | 3 | 0-3 |  |
| 93 | luisgonzalex.CodeMonkeys | 1281 | 383 | 3 | 0-3 |  |
| 94 | mama4294.maloneplayer | 1281 | 383 | 3 | 0-3 |  |
| 95 | max-titov.seeding | 1281 | 383 | 3 | 0-3 |  |
| 96 | michaeltliu.beginnerplayer | 1281 | 383 | 3 | 0-3 |  |
| 97 | monmouth-college-cs.MyFirstPlayer | 1281 | 383 | 3 | 0-3 |  |
| 98 | ngkuru.qualifyingtournament | 1281 | 383 | 3 | 0-3 |  |
| 99 | orionquick.aldebaranplayer | 1281 | 383 | 3 | 0-3 |  |
| 100 | snpushpi.whatamidoing | 1281 | 383 | 3 | 0-3 |  |
| 101 | stevetimberman.playerbbbbb | 1281 | 383 | 3 | 0-3 |  |
| 102 | willBoyd8.bb8 | 1281 | 383 | 3 | 0-3 |  |
| 103 | cosimogonnelli.Team3player | 1279 | 274 | 7 | 1-6 |  |
| 104 | WilliamYue37.Player1 | 1275 | 140 | 46 | 6-40 |  |
| 105 | Tim-gubski.AngryWaffleMaker | 1269 | 154 | 32 | 5-27 |  |
| 106 | jenlz.bustedJulianbot | 1264 | 261 | 14 | 1-13 |  |
| 107 | A9ine.potato | 1162 | 365 | 6 | 0-6 |  |
| 108 | 9mAhmad.MahinBot | 1134 | 362 | 7 | 0-7 |  |
| 109 | 9mAhmad.lostincoordinates | 1134 | 362 | 7 | 0-7 |  |
| 110 | AllenWang314.bot1 | 1134 | 362 | 7 | 0-7 |  |
| 111 | GabrielDWu.buildawall2 | 1134 | 362 | 7 | 0-7 |  |
| 112 | J-J-Chen.player | 1134 | 362 | 7 | 0-7 |  |
| 113 | KyleHassold.sprintbot | 1134 | 362 | 7 | 0-7 |  |
| 114 | denver-blake.sprint | 1134 | 362 | 7 | 0-7 |  |
