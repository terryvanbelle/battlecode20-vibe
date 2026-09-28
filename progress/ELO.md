# Ladder

10115 scrimmages (ours only), 9591 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1779 +- 35 | 12 of 108 | 432 | 222-210 | 81.1% | 25.1% (vs 11) |
| r1s17 | 1778 +- 39 | 13 of 108 | 332 | 169-163 | 81.0% | 24.9% (vs 11) |
| r4s3 | 1760 +- 46 | 15 of 108 | 240 | 121-119 | 80.1% | 25.4% (vs 12) |
| cand69 | 1757 +- 74 | 16 of 108 | 96 | 46-50 | 80.0% | 25.1% (vs 12) |
| g_iter12 | 1756 +- 40 | 17 of 108 | 336 | 159-177 | 79.9% | 25.0% (vs 12) |
| cand81 | 1754 +- 92 | 18 of 108 | 95 | 67-28 | 79.8% | 24.8% (vs 12) |
| cand86s3 | 1750 +- 46 | 19 of 108 | 240 | 118-122 | 79.6% | 24.5% (vs 12) |
| r1s13 | 1750 +- 46 | 20 of 108 | 240 | 118-122 | 79.6% | 24.5% (vs 12) |
| r4s4 | 1739 +- 46 | 21 of 108 | 239 | 114-125 | 79.0% | 23.3% (vs 12) |
| r4s1 | 1737 +- 46 | 22 of 108 | 240 | 114-126 | 79.0% | 23.2% (vs 12) |
| cand87 | 1731 +- 46 | 23 of 108 | 240 | 112-128 | 78.7% | 22.6% (vs 12) |
| r2s4 | 1728 +- 46 | 24 of 108 | 240 | 111-129 | 78.5% | 22.3% (vs 12) |
| cand81s8 | 1728 +- 49 | 25 of 108 | 240 | 85-155 | 78.5% | 22.3% (vs 12) |
| cand65 | 1722 +- 75 | 26 of 108 | 96 | 42-54 | 78.2% | 21.8% (vs 12) |
| cand86s2 | 1722 +- 73 | 27 of 108 | 96 | 44-52 | 78.2% | 21.8% (vs 12) |
| r1s11 | 1718 +- 46 | 28 of 108 | 240 | 108-132 | 78.0% | 21.4% (vs 12) |
| r1s16 | 1715 +- 49 | 29 of 108 | 240 | 79-161 | 77.8% | 21.1% (vs 12) |
| r1s14 | 1708 +- 47 | 30 of 108 | 240 | 105-135 | 77.4% | 20.6% (vs 12) |
| arch_rush | 1681 +- 74 | 31 of 108 | 96 | 39-57 | 75.9% | 18.3% (vs 12) |
| g_iter11 | 1663 +- 48 | 32 of 108 | 240 | 107-133 | 74.8% | 16.9% (vs 12) |
| arch_rush2 | 1656 +- 75 | 33 of 108 | 96 | 36-60 | 74.4% | 16.4% (vs 12) |
| cand49b | 1654 +- 76 | 34 of 108 | 96 | 42-54 | 74.3% | 16.2% (vs 12) |
| g_iter10 | 1649 +- 48 | 35 of 108 | 240 | 103-137 | 74.0% | 15.9% (vs 12) |
| cand43b | 1646 +- 79 | 36 of 108 | 96 | 64-32 | 73.8% | 15.7% (vs 12) |
| cand47d | 1642 +- 55 | 37 of 108 | 192 | 73-119 | 73.6% | 15.4% (vs 12) |
| g_iter9 | 1630 +- 50 | 38 of 108 | 240 | 78-162 | 72.8% | 14.6% (vs 12) |
| g_iter8 | 1603 +- 49 | 41 of 108 | 240 | 108-132 | 70.9% | 17.6% (vs 14) |
| g_iter5 | 1598 +- 34 | 42 of 108 | 618 | 329-289 | 70.6% | 17.2% (vs 14) |
| cand41b | 1598 +- 76 | 43 of 108 | 96 | 51-45 | 70.5% | 17.2% (vs 14) |
| r1s8 | 1595 +- 78 | 45 of 108 | 96 | 29-67 | 70.4% | 19.2% (vs 15) |
| g_iter7 | 1592 +- 45 | 46 of 108 | 288 | 135-153 | 70.1% | 19.0% (vs 15) |
| g_iter6 | 1584 +- 26 | 47 of 108 | 886 | 450-436 | 69.6% | 18.4% (vs 15) |
| cand40c | 1582 +- 76 | 48 of 108 | 96 | 57-39 | 69.4% | 18.3% (vs 15) |
| cand42c | 1580 +- 76 | 49 of 108 | 96 | 49-47 | 69.3% | 18.1% (vs 15) |
| iter24 | 1576 +- 131 | 50 of 108 | 46 | 11-35 | 69.0% | 17.8% (vs 15) |
| r1s3 | 1576 +- 80 | 51 of 108 | 96 | 27-69 | 69.0% | 17.8% (vs 15) |
| g_iter3 | 1563 +- 34 | 52 of 108 | 796 | 167-629 | 68.0% | 16.9% (vs 15) |
| cand37 | 1554 +- 76 | 53 of 108 | 96 | 46-50 | 67.3% | 16.3% (vs 15) |
| cand31 | 1544 +- 75 | 54 of 108 | 96 | 52-44 | 66.5% | 15.6% (vs 15) |
| g_iter4 | 1543 +- 65 | 55 of 108 | 217 | 43-174 | 66.4% | 15.5% (vs 15) |
| g_iter2 | 1484 +- 70 | 56 of 108 | 192 | 44-148 | 61.4% | 12.0% (vs 15) |
| g_iter1 | 1484 +- 68 | 57 of 108 | 170 | 95-75 | 61.3% | 12.0% (vs 15) |
| arch_enclosure | 1339 +- 165 | 61 of 108 | 48 | 4-44 | 46.4% | 12.3% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2167 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2158 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2099 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2044 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1989 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1984 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1976 | 64 | 286 | 254-32 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1908 | 28 | 842 | 650-192 | 30% (r4s4 9-21) |
| 9 | mvpatel2000.qual | 1893 | 28 | 829 | 626-203 | 17% (r4s4 5-25) |
| 10 | EmaPajic.Qualifications | 1827 | 25 | 887 | 611-276 | 27% (r4s3 8-22) |
| 11 | rzhan11.quals_bot | 1821 | 30 | 572 | 375-197 | 40% (r4s4 12-18) |
| 12 | **us:g_iter13** | 1779 | 35 | 432 | 222-210 |  |
| 13 | **us:r1s17** | 1778 | 39 | 332 | 169-163 |  |
| 14 | poortho.stable_seeding_bot | 1762 | 22 | 1061 | 664-397 | 60% (r4s4 18-12) |
| 15 | **us:r4s3** | 1760 | 46 | 240 | 121-119 |  |
| 16 | **us:cand69** | 1757 | 74 | 96 | 46-50 |  |
| 17 | **us:g_iter12** | 1756 | 40 | 336 | 159-177 |  |
| 18 | **us:cand81** | 1754 | 92 | 95 | 67-28 |  |
| 19 | **us:cand86s3** | 1750 | 46 | 240 | 118-122 |  |
| 20 | **us:r1s13** | 1750 | 46 | 240 | 118-122 |  |
| 21 | **us:r4s4** | 1739 | 46 | 239 | 114-125 |  |
| 22 | **us:r4s1** | 1737 | 46 | 240 | 114-126 |  |
| 23 | **us:cand87** | 1731 | 46 | 240 | 112-128 |  |
| 24 | **us:r2s4** | 1728 | 46 | 240 | 111-129 |  |
| 25 | **us:cand81s8** | 1728 | 49 | 240 | 85-155 |  |
| 26 | **us:cand65** | 1722 | 75 | 96 | 42-54 |  |
| 27 | **us:cand86s2** | 1722 | 73 | 96 | 44-52 |  |
| 28 | **us:r1s11** | 1718 | 46 | 240 | 108-132 |  |
| 29 | **us:r1s16** | 1715 | 49 | 240 | 79-161 |  |
| 30 | **us:r1s14** | 1708 | 47 | 240 | 105-135 |  |
| 31 | **us:arch_rush** | 1681 | 74 | 96 | 39-57 |  |
| 32 | **us:g_iter11** | 1663 | 48 | 240 | 107-133 |  |
| 33 | **us:arch_rush2** | 1656 | 75 | 96 | 36-60 |  |
| 34 | **us:cand49b** | 1654 | 76 | 96 | 42-54 |  |
| 35 | **us:g_iter10** | 1649 | 48 | 240 | 103-137 |  |
| 36 | **us:cand43b** | 1646 | 79 | 96 | 64-32 |  |
| 37 | **us:cand47d** | 1642 | 55 | 192 | 73-119 |  |
| 38 | **us:g_iter9** | 1630 | 50 | 240 | 78-162 |  |
| 39 | laurenschneider.pdx_team_one | 1630 | 22 | 1028 | 466-562 | 73% (r4s4 22-8) |
| 40 | cormackikkert.whyPermutator | 1627 | 22 | 1051 | 470-581 | 57% (r4s4 17-13) |
| 41 | **us:g_iter8** | 1603 | 49 | 240 | 108-132 |  |
| 42 | **us:g_iter5** | 1598 | 34 | 618 | 329-289 |  |
| 43 | **us:cand41b** | 1598 | 76 | 96 | 51-45 |  |
| 44 | benzyx.seeding | 1596 | 22 | 1000 | 412-588 | 57% (r4s4 17-13) |
| 45 | **us:r1s8** | 1595 | 78 | 96 | 29-67 |  |
| 46 | **us:g_iter7** | 1592 | 45 | 288 | 135-153 |  |
| 47 | **us:g_iter6** | 1584 | 26 | 886 | 450-436 |  |
| 48 | **us:cand40c** | 1582 | 76 | 96 | 57-39 |  |
| 49 | **us:cand42c** | 1580 | 76 | 96 | 49-47 |  |
| 50 | **us:iter24** | 1576 | 131 | 46 | 11-35 |  |
| 51 | **us:r1s3** | 1576 | 80 | 96 | 27-69 |  |
| 52 | **us:g_iter3** | 1563 | 34 | 796 | 167-629 |  |
| 53 | **us:cand37** | 1554 | 76 | 96 | 46-50 |  |
| 54 | **us:cand31** | 1544 | 75 | 96 | 52-44 |  |
| 55 | **us:g_iter4** | 1543 | 65 | 217 | 43-174 |  |
| 56 | **us:g_iter2** | 1484 | 70 | 192 | 44-148 |  |
| 57 | **us:g_iter1** | 1484 | 68 | 170 | 95-75 |  |
| 58 | wpine215.stardustv2 | 1414 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 59 | mhahn2003.nonrush | 1367 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 60 | eggag32.BrutalPigeonBot | 1367 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 61 | **us:arch_enclosure** | 1339 | 165 | 48 | 4-44 |  |
| 62 | cs454-w20-team3.playbot | 1335 | 112 | 56 | 11-45 |  |
| 63 | ARognes.FinalSubmission | 1324 | 144 | 37 | 6-31 |  |
| 64 | TeamSerpentine.noodleBot | 1314 | 143 | 38 | 6-32 |  |
| 65 | opheez.landscapers | 1304 | 268 | 9 | 1-8 |  |
| 66 | ovimura.welovesoup | 1304 | 268 | 9 | 1-8 |  |
| 67 | rsandzimier.SandSibs_seeding | 1304 | 268 | 9 | 1-8 |  |
| 68 | thissop.alpha | 1304 | 268 | 9 | 1-8 |  |
| 69 | yaonam.Robot_1 | 1301 | 154 | 33 | 5-28 |  |
| 70 | VinayaBhat.team10pdx | 1300 | 222 | 15 | 2-13 |  |
| 71 | WilliamYue37.Player1 | 1294 | 140 | 46 | 6-40 |  |
| 72 | cosimogonnelli.Team3player | 1292 | 274 | 7 | 1-6 |  |
| 73 | LucianCov.ourRobot | 1291 | 383 | 3 | 0-3 |  |
| 74 | MrHoseongLee.Neptune_v3 | 1291 | 383 | 3 | 0-3 |  |
| 75 | Phrancium.Frankplayer1 | 1291 | 383 | 3 | 0-3 |  |
| 76 | Pleket.Bot | 1291 | 383 | 3 | 0-3 |  |
| 77 | Strequals.rw8 | 1291 | 383 | 3 | 0-3 |  |
| 78 | Sukanya-Kothapally.team4player | 1291 | 383 | 3 | 0-3 |  |
| 79 | TeamSerpentine.eendagsvliegjes | 1291 | 383 | 3 | 0-3 |  |
| 80 | Tolsi.mybot | 1291 | 383 | 3 | 0-3 |  |
| 81 | anthonybench.FunkBot | 1291 | 383 | 3 | 0-3 |  |
| 82 | atliSig.buttletplayer | 1291 | 383 | 3 | 0-3 |  |
| 83 | charboltron.team11newbot | 1291 | 383 | 3 | 0-3 |  |
| 84 | djkeyes.addingComm | 1291 | 383 | 3 | 0-3 |  |
| 85 | fewella.FirstPlayer | 1291 | 383 | 3 | 0-3 |  |
| 86 | jmerle.camel_case_sprint | 1291 | 383 | 3 | 0-3 |  |
| 87 | kylittle.qualsbot2 | 1291 | 383 | 3 | 0-3 |  |
| 88 | lfchain.bigBudsBot | 1291 | 383 | 3 | 0-3 |  |
| 89 | luisgonzalex.CodeMonkeys | 1291 | 383 | 3 | 0-3 |  |
| 90 | mama4294.maloneplayer | 1291 | 383 | 3 | 0-3 |  |
| 91 | max-titov.seeding | 1291 | 383 | 3 | 0-3 |  |
| 92 | michaeltliu.beginnerplayer | 1291 | 383 | 3 | 0-3 |  |
| 93 | monmouth-college-cs.MyFirstPlayer | 1291 | 383 | 3 | 0-3 |  |
| 94 | ngkuru.qualifyingtournament | 1291 | 383 | 3 | 0-3 |  |
| 95 | orionquick.aldebaranplayer | 1291 | 383 | 3 | 0-3 |  |
| 96 | snpushpi.whatamidoing | 1291 | 383 | 3 | 0-3 |  |
| 97 | stevetimberman.playerbbbbb | 1291 | 383 | 3 | 0-3 |  |
| 98 | willBoyd8.bb8 | 1291 | 383 | 3 | 0-3 |  |
| 99 | Tim-gubski.AngryWaffleMaker | 1288 | 154 | 32 | 5-27 |  |
| 100 | jenlz.bustedJulianbot | 1279 | 262 | 14 | 1-13 |  |
| 101 | A9ine.potato | 1174 | 365 | 6 | 0-6 |  |
| 102 | 9mAhmad.MahinBot | 1147 | 362 | 7 | 0-7 |  |
| 103 | 9mAhmad.lostincoordinates | 1147 | 362 | 7 | 0-7 |  |
| 104 | AllenWang314.bot1 | 1147 | 362 | 7 | 0-7 |  |
| 105 | GabrielDWu.buildawall2 | 1147 | 362 | 7 | 0-7 |  |
| 106 | J-J-Chen.player | 1147 | 362 | 7 | 0-7 |  |
| 107 | KyleHassold.sprintbot | 1147 | 362 | 7 | 0-7 |  |
| 108 | denver-blake.sprint | 1147 | 362 | 7 | 0-7 |  |
