# Ladder

9876 scrimmages (ours only), 9352 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1783 +- 35 | 12 of 107 | 432 | 222-210 | 81.1% | 25.1% (vs 11) |
| r1s17 | 1781 +- 39 | 13 of 107 | 332 | 169-163 | 81.0% | 24.9% (vs 11) |
| r4s3 | 1764 +- 46 | 15 of 107 | 240 | 121-119 | 80.2% | 25.4% (vs 12) |
| cand69 | 1761 +- 74 | 16 of 107 | 96 | 46-50 | 80.0% | 25.1% (vs 12) |
| g_iter12 | 1759 +- 40 | 17 of 107 | 336 | 159-177 | 79.9% | 24.9% (vs 12) |
| cand81 | 1757 +- 92 | 18 of 107 | 95 | 67-28 | 79.8% | 24.8% (vs 12) |
| r1s13 | 1754 +- 46 | 19 of 107 | 240 | 118-122 | 79.7% | 24.4% (vs 12) |
| cand86s3 | 1754 +- 46 | 20 of 107 | 240 | 118-122 | 79.7% | 24.4% (vs 12) |
| r4s1 | 1741 +- 46 | 21 of 107 | 240 | 114-126 | 79.0% | 23.2% (vs 12) |
| cand87 | 1735 +- 46 | 22 of 107 | 240 | 112-128 | 78.7% | 22.6% (vs 12) |
| cand81s8 | 1732 +- 49 | 23 of 107 | 240 | 85-155 | 78.5% | 22.3% (vs 12) |
| r2s4 | 1731 +- 46 | 24 of 107 | 240 | 111-129 | 78.5% | 22.3% (vs 12) |
| cand65 | 1726 +- 75 | 25 of 107 | 96 | 42-54 | 78.2% | 21.8% (vs 12) |
| cand86s2 | 1725 +- 73 | 26 of 107 | 96 | 44-52 | 78.2% | 21.7% (vs 12) |
| r1s11 | 1722 +- 47 | 27 of 107 | 240 | 108-132 | 78.0% | 21.4% (vs 12) |
| r1s16 | 1719 +- 49 | 28 of 107 | 240 | 79-161 | 77.8% | 21.1% (vs 12) |
| r1s14 | 1712 +- 47 | 29 of 107 | 240 | 105-135 | 77.5% | 20.6% (vs 12) |
| arch_rush | 1685 +- 74 | 30 of 107 | 96 | 39-57 | 76.0% | 18.3% (vs 12) |
| g_iter11 | 1667 +- 48 | 31 of 107 | 240 | 107-133 | 74.9% | 16.9% (vs 12) |
| arch_rush2 | 1660 +- 75 | 32 of 107 | 96 | 36-60 | 74.5% | 16.4% (vs 12) |
| cand49b | 1658 +- 76 | 33 of 107 | 96 | 42-54 | 74.3% | 16.2% (vs 12) |
| g_iter10 | 1653 +- 48 | 34 of 107 | 240 | 103-137 | 74.0% | 15.8% (vs 12) |
| cand43b | 1650 +- 79 | 35 of 107 | 96 | 64-32 | 73.8% | 15.6% (vs 12) |
| cand47d | 1646 +- 55 | 36 of 107 | 192 | 73-119 | 73.6% | 15.4% (vs 12) |
| g_iter9 | 1634 +- 50 | 38 of 107 | 240 | 78-162 | 72.8% | 17.2% (vs 13) |
| g_iter8 | 1607 +- 49 | 40 of 107 | 240 | 108-132 | 71.0% | 17.6% (vs 14) |
| cand41b | 1601 +- 76 | 41 of 107 | 96 | 51-45 | 70.6% | 17.2% (vs 14) |
| g_iter5 | 1601 +- 34 | 42 of 107 | 618 | 329-289 | 70.6% | 17.2% (vs 14) |
| r1s8 | 1598 +- 79 | 43 of 107 | 96 | 29-67 | 70.4% | 17.0% (vs 14) |
| g_iter7 | 1595 +- 45 | 45 of 107 | 288 | 135-153 | 70.2% | 18.9% (vs 15) |
| g_iter6 | 1588 +- 26 | 46 of 107 | 886 | 450-436 | 69.7% | 18.4% (vs 15) |
| cand40c | 1585 +- 77 | 47 of 107 | 96 | 57-39 | 69.5% | 18.2% (vs 15) |
| cand42c | 1584 +- 76 | 48 of 107 | 96 | 49-47 | 69.4% | 18.1% (vs 15) |
| r1s3 | 1580 +- 80 | 49 of 107 | 96 | 27-69 | 69.1% | 17.8% (vs 15) |
| iter24 | 1579 +- 131 | 50 of 107 | 46 | 11-35 | 69.0% | 17.8% (vs 15) |
| g_iter3 | 1567 +- 34 | 51 of 107 | 796 | 167-629 | 68.1% | 16.9% (vs 15) |
| cand37 | 1558 +- 76 | 52 of 107 | 96 | 46-50 | 67.4% | 16.3% (vs 15) |
| cand31 | 1547 +- 75 | 53 of 107 | 96 | 52-44 | 66.5% | 15.5% (vs 15) |
| g_iter4 | 1546 +- 65 | 54 of 107 | 217 | 43-174 | 66.5% | 15.5% (vs 15) |
| g_iter2 | 1488 +- 70 | 55 of 107 | 192 | 44-148 | 61.5% | 12.0% (vs 15) |
| g_iter1 | 1486 +- 68 | 56 of 107 | 170 | 95-75 | 61.3% | 11.9% (vs 15) |
| arch_enclosure | 1342 +- 165 | 60 of 107 | 48 | 4-44 | 46.5% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2171 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2162 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2102 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2047 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1993 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1988 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1979 | 64 | 286 | 254-32 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1913 | 29 | 812 | 629-183 | 10% (r4s3 3-27) |
| 9 | mvpatel2000.qual | 1892 | 28 | 799 | 601-198 | 30% (r4s3 9-21) |
| 10 | EmaPajic.Qualifications | 1833 | 26 | 858 | 596-262 | 27% (r4s3 8-22) |
| 11 | rzhan11.quals_bot | 1825 | 31 | 542 | 357-185 | 53% (r4s3 16-14) |
| 12 | **us:g_iter13** | 1783 | 35 | 432 | 222-210 |  |
| 13 | **us:r1s17** | 1781 | 39 | 332 | 169-163 |  |
| 14 | poortho.stable_seeding_bot | 1768 | 23 | 1031 | 652-379 | 50% (r4s3 15-15) |
| 15 | **us:r4s3** | 1764 | 46 | 240 | 121-119 |  |
| 16 | **us:cand69** | 1761 | 74 | 96 | 46-50 |  |
| 17 | **us:g_iter12** | 1759 | 40 | 336 | 159-177 |  |
| 18 | **us:cand81** | 1757 | 92 | 95 | 67-28 |  |
| 19 | **us:r1s13** | 1754 | 46 | 240 | 118-122 |  |
| 20 | **us:cand86s3** | 1754 | 46 | 240 | 118-122 |  |
| 21 | **us:r4s1** | 1741 | 46 | 240 | 114-126 |  |
| 22 | **us:cand87** | 1735 | 46 | 240 | 112-128 |  |
| 23 | **us:cand81s8** | 1732 | 49 | 240 | 85-155 |  |
| 24 | **us:r2s4** | 1731 | 46 | 240 | 111-129 |  |
| 25 | **us:cand65** | 1726 | 75 | 96 | 42-54 |  |
| 26 | **us:cand86s2** | 1725 | 73 | 96 | 44-52 |  |
| 27 | **us:r1s11** | 1722 | 47 | 240 | 108-132 |  |
| 28 | **us:r1s16** | 1719 | 49 | 240 | 79-161 |  |
| 29 | **us:r1s14** | 1712 | 47 | 240 | 105-135 |  |
| 30 | **us:arch_rush** | 1685 | 74 | 96 | 39-57 |  |
| 31 | **us:g_iter11** | 1667 | 48 | 240 | 107-133 |  |
| 32 | **us:arch_rush2** | 1660 | 75 | 96 | 36-60 |  |
| 33 | **us:cand49b** | 1658 | 76 | 96 | 42-54 |  |
| 34 | **us:g_iter10** | 1653 | 48 | 240 | 103-137 |  |
| 35 | **us:cand43b** | 1650 | 79 | 96 | 64-32 |  |
| 36 | **us:cand47d** | 1646 | 55 | 192 | 73-119 |  |
| 37 | laurenschneider.pdx_team_one | 1635 | 22 | 998 | 458-540 | 70% (r4s3 21-9) |
| 38 | **us:g_iter9** | 1634 | 50 | 240 | 78-162 |  |
| 39 | cormackikkert.whyPermutator | 1629 | 22 | 1021 | 457-564 | 97% (r4s3 29-1) |
| 40 | **us:g_iter8** | 1607 | 49 | 240 | 108-132 |  |
| 41 | **us:cand41b** | 1601 | 76 | 96 | 51-45 |  |
| 42 | **us:g_iter5** | 1601 | 34 | 618 | 329-289 |  |
| 43 | **us:r1s8** | 1598 | 79 | 96 | 29-67 |  |
| 44 | benzyx.seeding | 1597 | 23 | 970 | 399-571 | 67% (r4s3 20-10) |
| 45 | **us:g_iter7** | 1595 | 45 | 288 | 135-153 |  |
| 46 | **us:g_iter6** | 1588 | 26 | 886 | 450-436 |  |
| 47 | **us:cand40c** | 1585 | 77 | 96 | 57-39 |  |
| 48 | **us:cand42c** | 1584 | 76 | 96 | 49-47 |  |
| 49 | **us:r1s3** | 1580 | 80 | 96 | 27-69 |  |
| 50 | **us:iter24** | 1579 | 131 | 46 | 11-35 |  |
| 51 | **us:g_iter3** | 1567 | 34 | 796 | 167-629 |  |
| 52 | **us:cand37** | 1558 | 76 | 96 | 46-50 |  |
| 53 | **us:cand31** | 1547 | 75 | 96 | 52-44 |  |
| 54 | **us:g_iter4** | 1546 | 65 | 217 | 43-174 |  |
| 55 | **us:g_iter2** | 1488 | 70 | 192 | 44-148 |  |
| 56 | **us:g_iter1** | 1486 | 68 | 170 | 95-75 |  |
| 57 | wpine215.stardustv2 | 1418 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 58 | mhahn2003.nonrush | 1370 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 59 | eggag32.BrutalPigeonBot | 1370 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 60 | **us:arch_enclosure** | 1342 | 165 | 48 | 4-44 |  |
| 61 | cs454-w20-team3.playbot | 1338 | 112 | 56 | 11-45 |  |
| 62 | ARognes.FinalSubmission | 1326 | 144 | 37 | 6-31 |  |
| 63 | TeamSerpentine.noodleBot | 1317 | 144 | 38 | 6-32 |  |
| 64 | opheez.landscapers | 1307 | 268 | 9 | 1-8 |  |
| 65 | ovimura.welovesoup | 1307 | 268 | 9 | 1-8 |  |
| 66 | rsandzimier.SandSibs_seeding | 1307 | 268 | 9 | 1-8 |  |
| 67 | thissop.alpha | 1307 | 268 | 9 | 1-8 |  |
| 68 | yaonam.Robot_1 | 1303 | 154 | 33 | 5-28 |  |
| 69 | VinayaBhat.team10pdx | 1303 | 222 | 15 | 2-13 |  |
| 70 | WilliamYue37.Player1 | 1297 | 140 | 46 | 6-40 |  |
| 71 | cosimogonnelli.Team3player | 1294 | 274 | 7 | 1-6 |  |
| 72 | LucianCov.ourRobot | 1292 | 383 | 3 | 0-3 |  |
| 73 | MrHoseongLee.Neptune_v3 | 1292 | 383 | 3 | 0-3 |  |
| 74 | Phrancium.Frankplayer1 | 1292 | 383 | 3 | 0-3 |  |
| 75 | Pleket.Bot | 1292 | 383 | 3 | 0-3 |  |
| 76 | Strequals.rw8 | 1292 | 383 | 3 | 0-3 |  |
| 77 | Sukanya-Kothapally.team4player | 1292 | 383 | 3 | 0-3 |  |
| 78 | TeamSerpentine.eendagsvliegjes | 1292 | 383 | 3 | 0-3 |  |
| 79 | Tolsi.mybot | 1292 | 383 | 3 | 0-3 |  |
| 80 | anthonybench.FunkBot | 1292 | 383 | 3 | 0-3 |  |
| 81 | atliSig.buttletplayer | 1292 | 383 | 3 | 0-3 |  |
| 82 | charboltron.team11newbot | 1292 | 383 | 3 | 0-3 |  |
| 83 | djkeyes.addingComm | 1292 | 383 | 3 | 0-3 |  |
| 84 | fewella.FirstPlayer | 1292 | 383 | 3 | 0-3 |  |
| 85 | jmerle.camel_case_sprint | 1292 | 383 | 3 | 0-3 |  |
| 86 | kylittle.qualsbot2 | 1292 | 383 | 3 | 0-3 |  |
| 87 | lfchain.bigBudsBot | 1292 | 383 | 3 | 0-3 |  |
| 88 | luisgonzalex.CodeMonkeys | 1292 | 383 | 3 | 0-3 |  |
| 89 | mama4294.maloneplayer | 1292 | 383 | 3 | 0-3 |  |
| 90 | max-titov.seeding | 1292 | 383 | 3 | 0-3 |  |
| 91 | michaeltliu.beginnerplayer | 1292 | 383 | 3 | 0-3 |  |
| 92 | monmouth-college-cs.MyFirstPlayer | 1292 | 383 | 3 | 0-3 |  |
| 93 | ngkuru.qualifyingtournament | 1292 | 383 | 3 | 0-3 |  |
| 94 | orionquick.aldebaranplayer | 1292 | 383 | 3 | 0-3 |  |
| 95 | snpushpi.whatamidoing | 1292 | 383 | 3 | 0-3 |  |
| 96 | stevetimberman.playerbbbbb | 1292 | 383 | 3 | 0-3 |  |
| 97 | willBoyd8.bb8 | 1292 | 383 | 3 | 0-3 |  |
| 98 | Tim-gubski.AngryWaffleMaker | 1291 | 154 | 32 | 5-27 |  |
| 99 | jenlz.bustedJulianbot | 1281 | 262 | 14 | 1-13 |  |
| 100 | A9ine.potato | 1176 | 365 | 6 | 0-6 |  |
| 101 | 9mAhmad.MahinBot | 1149 | 362 | 7 | 0-7 |  |
| 102 | 9mAhmad.lostincoordinates | 1149 | 362 | 7 | 0-7 |  |
| 103 | AllenWang314.bot1 | 1149 | 362 | 7 | 0-7 |  |
| 104 | GabrielDWu.buildawall2 | 1149 | 362 | 7 | 0-7 |  |
| 105 | J-J-Chen.player | 1149 | 362 | 7 | 0-7 |  |
| 106 | KyleHassold.sprintbot | 1149 | 362 | 7 | 0-7 |  |
| 107 | denver-blake.sprint | 1149 | 362 | 7 | 0-7 |  |
