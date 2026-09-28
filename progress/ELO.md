# Ladder

9636 scrimmages (ours only), 9112 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1786 +- 35 | 12 of 106 | 432 | 222-210 | 81.1% | 25.2% (vs 11) |
| r1s17 | 1785 +- 39 | 13 of 106 | 332 | 169-163 | 81.0% | 25.0% (vs 11) |
| cand69 | 1763 +- 74 | 15 of 106 | 96 | 46-50 | 80.0% | 25.0% (vs 12) |
| g_iter12 | 1762 +- 40 | 16 of 106 | 336 | 159-177 | 79.9% | 24.9% (vs 12) |
| cand81 | 1760 +- 91 | 17 of 106 | 95 | 67-28 | 79.8% | 24.8% (vs 12) |
| r1s13 | 1757 +- 46 | 18 of 106 | 240 | 118-122 | 79.7% | 24.5% (vs 12) |
| cand86s3 | 1757 +- 46 | 19 of 106 | 240 | 118-122 | 79.7% | 24.5% (vs 12) |
| r4s1 | 1744 +- 46 | 20 of 106 | 240 | 114-126 | 79.1% | 23.3% (vs 12) |
| cand87 | 1738 +- 46 | 21 of 106 | 240 | 112-128 | 78.7% | 22.7% (vs 12) |
| r2s4 | 1735 +- 46 | 22 of 106 | 240 | 111-129 | 78.6% | 22.4% (vs 12) |
| cand81s8 | 1735 +- 48 | 23 of 106 | 240 | 85-155 | 78.5% | 22.3% (vs 12) |
| cand86s2 | 1729 +- 73 | 24 of 106 | 96 | 44-52 | 78.2% | 21.8% (vs 12) |
| cand65 | 1729 +- 75 | 25 of 106 | 96 | 42-54 | 78.2% | 21.8% (vs 12) |
| r1s11 | 1725 +- 46 | 26 of 106 | 240 | 108-132 | 78.0% | 21.5% (vs 12) |
| r1s16 | 1722 +- 49 | 27 of 106 | 240 | 79-161 | 77.9% | 21.2% (vs 12) |
| r1s14 | 1715 +- 47 | 28 of 106 | 240 | 105-135 | 77.5% | 20.6% (vs 12) |
| arch_rush | 1688 +- 74 | 29 of 106 | 96 | 39-57 | 76.0% | 18.4% (vs 12) |
| g_iter11 | 1670 +- 48 | 30 of 106 | 240 | 107-133 | 74.9% | 16.9% (vs 12) |
| arch_rush2 | 1664 +- 75 | 31 of 106 | 96 | 36-60 | 74.5% | 16.4% (vs 12) |
| cand49b | 1660 +- 76 | 32 of 106 | 96 | 42-54 | 74.3% | 16.2% (vs 12) |
| g_iter10 | 1656 +- 48 | 33 of 106 | 240 | 103-137 | 74.0% | 15.9% (vs 12) |
| cand43b | 1654 +- 79 | 34 of 106 | 96 | 64-32 | 73.9% | 15.7% (vs 12) |
| cand47d | 1649 +- 55 | 35 of 106 | 192 | 73-119 | 73.6% | 15.4% (vs 12) |
| g_iter9 | 1637 +- 50 | 38 of 106 | 240 | 78-162 | 72.9% | 19.6% (vs 14) |
| g_iter8 | 1611 +- 49 | 39 of 106 | 240 | 108-132 | 71.1% | 17.6% (vs 14) |
| cand41b | 1605 +- 76 | 40 of 106 | 96 | 51-45 | 70.7% | 17.2% (vs 14) |
| g_iter5 | 1605 +- 34 | 41 of 106 | 618 | 329-289 | 70.7% | 17.2% (vs 14) |
| r1s8 | 1602 +- 78 | 42 of 106 | 96 | 29-67 | 70.5% | 17.0% (vs 14) |
| g_iter7 | 1599 +- 45 | 43 of 106 | 288 | 135-153 | 70.3% | 16.7% (vs 14) |
| g_iter6 | 1592 +- 26 | 45 of 106 | 886 | 450-436 | 69.8% | 18.4% (vs 15) |
| cand40c | 1590 +- 77 | 46 of 106 | 96 | 57-39 | 69.6% | 18.3% (vs 15) |
| cand42c | 1588 +- 76 | 47 of 106 | 96 | 49-47 | 69.5% | 18.1% (vs 15) |
| r1s3 | 1583 +- 80 | 48 of 106 | 96 | 27-69 | 69.1% | 17.8% (vs 15) |
| iter24 | 1582 +- 131 | 49 of 106 | 46 | 11-35 | 69.1% | 17.7% (vs 15) |
| g_iter3 | 1570 +- 34 | 50 of 106 | 796 | 167-629 | 68.2% | 16.9% (vs 15) |
| cand37 | 1562 +- 76 | 51 of 106 | 96 | 46-50 | 67.5% | 16.3% (vs 15) |
| cand31 | 1551 +- 75 | 52 of 106 | 96 | 52-44 | 66.7% | 15.6% (vs 15) |
| g_iter4 | 1550 +- 65 | 53 of 106 | 217 | 43-174 | 66.6% | 15.5% (vs 15) |
| g_iter2 | 1491 +- 70 | 54 of 106 | 192 | 44-148 | 61.6% | 12.0% (vs 15) |
| g_iter1 | 1489 +- 68 | 55 of 106 | 170 | 95-75 | 61.4% | 11.9% (vs 15) |
| arch_enclosure | 1345 +- 165 | 59 of 106 | 48 | 4-44 | 46.5% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2174 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2165 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2106 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2051 | 181 | 39 | 36-3 |  |
| 5 | IvanGeffner.finalbota | 1996 | 70 | 209 | 182-27 | 10% (r1s16 3-27) |
| 6 | ronniesong0809.finalbota | 1991 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 7 | battlecode20-team4.finalbota | 1983 | 64 | 286 | 254-32 | 3% (r1s16 1-29) |
| 8 | winkelmantanner.tannerplayer | 1909 | 29 | 782 | 602-180 | 13% (r4s1 4-26) |
| 9 | mvpatel2000.qual | 1895 | 29 | 769 | 580-189 | 30% (r4s1 9-21) |
| 10 | rzhan11.quals_bot | 1835 | 33 | 512 | 343-169 | 40% (r4s1 12-18) |
| 11 | EmaPajic.Qualifications | 1832 | 26 | 828 | 574-254 | 23% (r4s1 7-23) |
| 12 | **us:g_iter13** | 1786 | 35 | 432 | 222-210 |  |
| 13 | **us:r1s17** | 1785 | 39 | 332 | 169-163 |  |
| 14 | poortho.stable_seeding_bot | 1772 | 23 | 1001 | 637-364 | 60% (r4s1 18-12) |
| 15 | **us:cand69** | 1763 | 74 | 96 | 46-50 |  |
| 16 | **us:g_iter12** | 1762 | 40 | 336 | 159-177 |  |
| 17 | **us:cand81** | 1760 | 91 | 95 | 67-28 |  |
| 18 | **us:r1s13** | 1757 | 46 | 240 | 118-122 |  |
| 19 | **us:cand86s3** | 1757 | 46 | 240 | 118-122 |  |
| 20 | **us:r4s1** | 1744 | 46 | 240 | 114-126 |  |
| 21 | **us:cand87** | 1738 | 46 | 240 | 112-128 |  |
| 22 | **us:r2s4** | 1735 | 46 | 240 | 111-129 |  |
| 23 | **us:cand81s8** | 1735 | 48 | 240 | 85-155 |  |
| 24 | **us:cand86s2** | 1729 | 73 | 96 | 44-52 |  |
| 25 | **us:cand65** | 1729 | 75 | 96 | 42-54 |  |
| 26 | **us:r1s11** | 1725 | 46 | 240 | 108-132 |  |
| 27 | **us:r1s16** | 1722 | 49 | 240 | 79-161 |  |
| 28 | **us:r1s14** | 1715 | 47 | 240 | 105-135 |  |
| 29 | **us:arch_rush** | 1688 | 74 | 96 | 39-57 |  |
| 30 | **us:g_iter11** | 1670 | 48 | 240 | 107-133 |  |
| 31 | **us:arch_rush2** | 1664 | 75 | 96 | 36-60 |  |
| 32 | **us:cand49b** | 1660 | 76 | 96 | 42-54 |  |
| 33 | **us:g_iter10** | 1656 | 48 | 240 | 103-137 |  |
| 34 | **us:cand43b** | 1654 | 79 | 96 | 64-32 |  |
| 35 | **us:cand47d** | 1649 | 55 | 192 | 73-119 |  |
| 36 | laurenschneider.pdx_team_one | 1639 | 23 | 968 | 449-519 | 67% (r4s1 20-10) |
| 37 | cormackikkert.whyPermutator | 1639 | 22 | 991 | 456-535 | 73% (r4s1 22-8) |
| 38 | **us:g_iter9** | 1637 | 50 | 240 | 78-162 |  |
| 39 | **us:g_iter8** | 1611 | 49 | 240 | 108-132 |  |
| 40 | **us:cand41b** | 1605 | 76 | 96 | 51-45 |  |
| 41 | **us:g_iter5** | 1605 | 34 | 618 | 329-289 |  |
| 42 | **us:r1s8** | 1602 | 78 | 96 | 29-67 |  |
| 43 | **us:g_iter7** | 1599 | 45 | 288 | 135-153 |  |
| 44 | benzyx.seeding | 1599 | 23 | 940 | 389-551 | 73% (r4s1 22-8) |
| 45 | **us:g_iter6** | 1592 | 26 | 886 | 450-436 |  |
| 46 | **us:cand40c** | 1590 | 77 | 96 | 57-39 |  |
| 47 | **us:cand42c** | 1588 | 76 | 96 | 49-47 |  |
| 48 | **us:r1s3** | 1583 | 80 | 96 | 27-69 |  |
| 49 | **us:iter24** | 1582 | 131 | 46 | 11-35 |  |
| 50 | **us:g_iter3** | 1570 | 34 | 796 | 167-629 |  |
| 51 | **us:cand37** | 1562 | 76 | 96 | 46-50 |  |
| 52 | **us:cand31** | 1551 | 75 | 96 | 52-44 |  |
| 53 | **us:g_iter4** | 1550 | 65 | 217 | 43-174 |  |
| 54 | **us:g_iter2** | 1491 | 70 | 192 | 44-148 |  |
| 55 | **us:g_iter1** | 1489 | 68 | 170 | 95-75 |  |
| 56 | wpine215.stardustv2 | 1421 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 57 | mhahn2003.nonrush | 1374 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 58 | eggag32.BrutalPigeonBot | 1374 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 59 | **us:arch_enclosure** | 1345 | 165 | 48 | 4-44 |  |
| 60 | cs454-w20-team3.playbot | 1342 | 112 | 56 | 11-45 |  |
| 61 | ARognes.FinalSubmission | 1330 | 144 | 37 | 6-31 |  |
| 62 | TeamSerpentine.noodleBot | 1320 | 144 | 38 | 6-32 |  |
| 63 | opheez.landscapers | 1310 | 268 | 9 | 1-8 |  |
| 64 | ovimura.welovesoup | 1310 | 268 | 9 | 1-8 |  |
| 65 | rsandzimier.SandSibs_seeding | 1310 | 268 | 9 | 1-8 |  |
| 66 | thissop.alpha | 1310 | 268 | 9 | 1-8 |  |
| 67 | yaonam.Robot_1 | 1307 | 154 | 33 | 5-28 |  |
| 68 | VinayaBhat.team10pdx | 1305 | 222 | 15 | 2-13 |  |
| 69 | WilliamYue37.Player1 | 1300 | 140 | 46 | 6-40 |  |
| 70 | cosimogonnelli.Team3player | 1296 | 274 | 7 | 1-6 |  |
| 71 | Tim-gubski.AngryWaffleMaker | 1294 | 154 | 32 | 5-27 |  |
| 72 | LucianCov.ourRobot | 1294 | 383 | 3 | 0-3 |  |
| 73 | MrHoseongLee.Neptune_v3 | 1294 | 383 | 3 | 0-3 |  |
| 74 | Phrancium.Frankplayer1 | 1294 | 383 | 3 | 0-3 |  |
| 75 | Pleket.Bot | 1294 | 383 | 3 | 0-3 |  |
| 76 | Strequals.rw8 | 1294 | 383 | 3 | 0-3 |  |
| 77 | Sukanya-Kothapally.team4player | 1294 | 383 | 3 | 0-3 |  |
| 78 | TeamSerpentine.eendagsvliegjes | 1294 | 383 | 3 | 0-3 |  |
| 79 | Tolsi.mybot | 1294 | 383 | 3 | 0-3 |  |
| 80 | anthonybench.FunkBot | 1294 | 383 | 3 | 0-3 |  |
| 81 | atliSig.buttletplayer | 1294 | 383 | 3 | 0-3 |  |
| 82 | charboltron.team11newbot | 1294 | 383 | 3 | 0-3 |  |
| 83 | djkeyes.addingComm | 1294 | 383 | 3 | 0-3 |  |
| 84 | fewella.FirstPlayer | 1294 | 383 | 3 | 0-3 |  |
| 85 | jmerle.camel_case_sprint | 1294 | 383 | 3 | 0-3 |  |
| 86 | kylittle.qualsbot2 | 1294 | 383 | 3 | 0-3 |  |
| 87 | lfchain.bigBudsBot | 1294 | 383 | 3 | 0-3 |  |
| 88 | luisgonzalex.CodeMonkeys | 1294 | 383 | 3 | 0-3 |  |
| 89 | mama4294.maloneplayer | 1294 | 383 | 3 | 0-3 |  |
| 90 | max-titov.seeding | 1294 | 383 | 3 | 0-3 |  |
| 91 | michaeltliu.beginnerplayer | 1294 | 383 | 3 | 0-3 |  |
| 92 | monmouth-college-cs.MyFirstPlayer | 1294 | 383 | 3 | 0-3 |  |
| 93 | ngkuru.qualifyingtournament | 1294 | 383 | 3 | 0-3 |  |
| 94 | orionquick.aldebaranplayer | 1294 | 383 | 3 | 0-3 |  |
| 95 | snpushpi.whatamidoing | 1294 | 383 | 3 | 0-3 |  |
| 96 | stevetimberman.playerbbbbb | 1294 | 383 | 3 | 0-3 |  |
| 97 | willBoyd8.bb8 | 1294 | 383 | 3 | 0-3 |  |
| 98 | jenlz.bustedJulianbot | 1284 | 262 | 14 | 1-13 |  |
| 99 | A9ine.potato | 1178 | 365 | 6 | 0-6 |  |
| 100 | 9mAhmad.MahinBot | 1151 | 362 | 7 | 0-7 |  |
| 101 | 9mAhmad.lostincoordinates | 1151 | 362 | 7 | 0-7 |  |
| 102 | AllenWang314.bot1 | 1151 | 362 | 7 | 0-7 |  |
| 103 | GabrielDWu.buildawall2 | 1151 | 362 | 7 | 0-7 |  |
| 104 | J-J-Chen.player | 1151 | 362 | 7 | 0-7 |  |
| 105 | KyleHassold.sprintbot | 1151 | 362 | 7 | 0-7 |  |
| 106 | denver-blake.sprint | 1151 | 362 | 7 | 0-7 |  |
