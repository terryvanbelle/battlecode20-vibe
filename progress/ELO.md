# Ladder

8536 scrimmages (ours only), 8012 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1807 +- 39 | 12 of 101 | 336 | 174-162 | 81.5% | 26.0% (vs 11) |
| cand69 | 1777 +- 74 | 14 of 101 | 96 | 46-50 | 80.0% | 25.2% (vs 12) |
| g_iter12 | 1777 +- 40 | 15 of 101 | 336 | 159-177 | 80.0% | 25.2% (vs 12) |
| cand81 | 1775 +- 91 | 16 of 101 | 95 | 67-28 | 79.9% | 25.0% (vs 12) |
| r1s13 | 1773 +- 46 | 17 of 101 | 240 | 118-122 | 79.8% | 24.8% (vs 12) |
| cand86s3 | 1773 +- 46 | 18 of 101 | 240 | 118-122 | 79.8% | 24.8% (vs 12) |
| cand87 | 1754 +- 46 | 19 of 101 | 240 | 112-128 | 78.9% | 23.0% (vs 12) |
| cand81s8 | 1748 +- 48 | 20 of 101 | 240 | 85-155 | 78.6% | 22.4% (vs 12) |
| cand86s2 | 1745 +- 73 | 21 of 101 | 96 | 44-52 | 78.4% | 22.1% (vs 12) |
| cand65 | 1744 +- 74 | 22 of 101 | 96 | 42-54 | 78.4% | 22.1% (vs 12) |
| r1s11 | 1741 +- 46 | 23 of 101 | 240 | 108-132 | 78.2% | 21.8% (vs 12) |
| r1s16 | 1735 +- 49 | 24 of 101 | 240 | 79-161 | 77.9% | 21.3% (vs 12) |
| r1s14 | 1732 +- 46 | 25 of 101 | 240 | 105-135 | 77.7% | 21.0% (vs 12) |
| g_iter11 | 1686 +- 48 | 26 of 101 | 240 | 107-133 | 75.1% | 17.2% (vs 12) |
| cand49b | 1677 +- 76 | 27 of 101 | 96 | 42-54 | 74.6% | 16.5% (vs 12) |
| cand43b | 1673 +- 79 | 28 of 101 | 96 | 64-32 | 74.3% | 16.2% (vs 12) |
| g_iter10 | 1673 +- 48 | 29 of 101 | 240 | 103-137 | 74.3% | 16.2% (vs 12) |
| cand47d | 1666 +- 54 | 30 of 101 | 192 | 73-119 | 73.9% | 15.7% (vs 12) |
| g_iter9 | 1654 +- 50 | 33 of 101 | 240 | 78-162 | 73.1% | 19.7% (vs 14) |
| g_iter8 | 1630 +- 49 | 34 of 101 | 240 | 108-132 | 71.5% | 17.9% (vs 14) |
| cand41b | 1624 +- 76 | 36 of 101 | 96 | 51-45 | 71.1% | 19.6% (vs 15) |
| g_iter5 | 1622 +- 34 | 37 of 101 | 618 | 329-289 | 71.0% | 19.5% (vs 15) |
| r1s8 | 1619 +- 78 | 38 of 101 | 96 | 29-67 | 70.8% | 19.2% (vs 15) |
| g_iter7 | 1618 +- 45 | 39 of 101 | 288 | 135-153 | 70.7% | 19.1% (vs 15) |
| g_iter6 | 1611 +- 26 | 40 of 101 | 886 | 450-436 | 70.2% | 18.6% (vs 15) |
| cand40c | 1609 +- 77 | 41 of 101 | 96 | 57-39 | 70.1% | 18.5% (vs 15) |
| cand42c | 1607 +- 76 | 42 of 101 | 96 | 49-47 | 69.9% | 18.3% (vs 15) |
| r1s3 | 1600 +- 80 | 43 of 101 | 96 | 27-69 | 69.5% | 17.8% (vs 15) |
| iter24 | 1599 +- 131 | 44 of 101 | 46 | 11-35 | 69.3% | 17.7% (vs 15) |
| g_iter3 | 1587 +- 33 | 45 of 101 | 796 | 167-629 | 68.5% | 16.9% (vs 15) |
| cand37 | 1581 +- 76 | 46 of 101 | 96 | 46-50 | 68.0% | 16.4% (vs 15) |
| cand31 | 1570 +- 75 | 47 of 101 | 96 | 52-44 | 67.2% | 15.7% (vs 15) |
| g_iter4 | 1567 +- 65 | 48 of 101 | 217 | 43-174 | 66.9% | 15.5% (vs 15) |
| g_iter2 | 1509 +- 70 | 49 of 101 | 192 | 44-148 | 62.0% | 12.0% (vs 15) |
| g_iter1 | 1503 +- 69 | 50 of 101 | 170 | 95-75 | 61.5% | 11.7% (vs 15) |
| arch_enclosure | 1362 +- 165 | 54 of 101 | 48 | 4-44 | 47.1% | 12.1% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2191 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2182 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2122 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2066 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 2007 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 6 | battlecode20-team4.finalbota | 1999 | 63 | 286 | 254-32 | 3% (r1s16 1-29) |
| 7 | IvanGeffner.finalbota | 1997 | 72 | 197 | 171-26 | 10% (r1s16 3-27) |
| 8 | mvpatel2000.qual | 1910 | 32 | 631 | 483-148 | 29% (g_iter13 12-30) |
| 9 | winkelmantanner.tannerplayer | 1910 | 32 | 644 | 494-150 | 36% (g_iter13 15-27) |
| 10 | rzhan11.quals_bot | 1851 | 39 | 374 | 257-117 | 45% (g_iter13 19-23) |
| 11 | EmaPajic.Qualifications | 1844 | 29 | 694 | 487-207 | 50% (g_iter13 21-21) |
| 12 | **us:g_iter13** | 1807 | 39 | 336 | 174-162 |  |
| 13 | poortho.stable_seeding_bot | 1784 | 25 | 863 | 558-305 | 48% (g_iter13 20-22) |
| 14 | **us:cand69** | 1777 | 74 | 96 | 46-50 |  |
| 15 | **us:g_iter12** | 1777 | 40 | 336 | 159-177 |  |
| 16 | **us:cand81** | 1775 | 91 | 95 | 67-28 |  |
| 17 | **us:r1s13** | 1773 | 46 | 240 | 118-122 |  |
| 18 | **us:cand86s3** | 1773 | 46 | 240 | 118-122 |  |
| 19 | **us:cand87** | 1754 | 46 | 240 | 112-128 |  |
| 20 | **us:cand81s8** | 1748 | 48 | 240 | 85-155 |  |
| 21 | **us:cand86s2** | 1745 | 73 | 96 | 44-52 |  |
| 22 | **us:cand65** | 1744 | 74 | 96 | 42-54 |  |
| 23 | **us:r1s11** | 1741 | 46 | 240 | 108-132 |  |
| 24 | **us:r1s16** | 1735 | 49 | 240 | 79-161 |  |
| 25 | **us:r1s14** | 1732 | 46 | 240 | 105-135 |  |
| 26 | **us:g_iter11** | 1686 | 48 | 240 | 107-133 |  |
| 27 | **us:cand49b** | 1677 | 76 | 96 | 42-54 |  |
| 28 | **us:cand43b** | 1673 | 79 | 96 | 64-32 |  |
| 29 | **us:g_iter10** | 1673 | 48 | 240 | 103-137 |  |
| 30 | **us:cand47d** | 1666 | 54 | 192 | 73-119 |  |
| 31 | cormackikkert.whyPermutator | 1664 | 24 | 853 | 417-436 | 74% (g_iter13 31-11) |
| 32 | laurenschneider.pdx_team_one | 1658 | 24 | 830 | 402-428 | 69% (g_iter13 29-13) |
| 33 | **us:g_iter9** | 1654 | 50 | 240 | 78-162 |  |
| 34 | **us:g_iter8** | 1630 | 49 | 240 | 108-132 |  |
| 35 | benzyx.seeding | 1625 | 25 | 814 | 360-454 | 80% (g_iter13 24-6) |
| 36 | **us:cand41b** | 1624 | 76 | 96 | 51-45 |  |
| 37 | **us:g_iter5** | 1622 | 34 | 618 | 329-289 |  |
| 38 | **us:r1s8** | 1619 | 78 | 96 | 29-67 |  |
| 39 | **us:g_iter7** | 1618 | 45 | 288 | 135-153 |  |
| 40 | **us:g_iter6** | 1611 | 26 | 886 | 450-436 |  |
| 41 | **us:cand40c** | 1609 | 77 | 96 | 57-39 |  |
| 42 | **us:cand42c** | 1607 | 76 | 96 | 49-47 |  |
| 43 | **us:r1s3** | 1600 | 80 | 96 | 27-69 |  |
| 44 | **us:iter24** | 1599 | 131 | 46 | 11-35 |  |
| 45 | **us:g_iter3** | 1587 | 33 | 796 | 167-629 |  |
| 46 | **us:cand37** | 1581 | 76 | 96 | 46-50 |  |
| 47 | **us:cand31** | 1570 | 75 | 96 | 52-44 |  |
| 48 | **us:g_iter4** | 1567 | 65 | 217 | 43-174 |  |
| 49 | **us:g_iter2** | 1509 | 70 | 192 | 44-148 |  |
| 50 | **us:g_iter1** | 1503 | 69 | 170 | 95-75 |  |
| 51 | wpine215.stardustv2 | 1439 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 52 | mhahn2003.nonrush | 1393 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 53 | eggag32.BrutalPigeonBot | 1392 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 54 | **us:arch_enclosure** | 1362 | 165 | 48 | 4-44 |  |
| 55 | cs454-w20-team3.playbot | 1359 | 112 | 56 | 11-45 |  |
| 56 | ARognes.FinalSubmission | 1346 | 144 | 37 | 6-31 |  |
| 57 | TeamSerpentine.noodleBot | 1336 | 144 | 38 | 6-32 |  |
| 58 | yaonam.Robot_1 | 1323 | 154 | 33 | 5-28 |  |
| 59 | opheez.landscapers | 1323 | 268 | 9 | 1-8 |  |
| 60 | ovimura.welovesoup | 1323 | 268 | 9 | 1-8 |  |
| 61 | rsandzimier.SandSibs_seeding | 1323 | 268 | 9 | 1-8 |  |
| 62 | thissop.alpha | 1323 | 268 | 9 | 1-8 |  |
| 63 | VinayaBhat.team10pdx | 1318 | 223 | 15 | 2-13 |  |
| 64 | WilliamYue37.Player1 | 1317 | 140 | 46 | 6-40 |  |
| 65 | Tim-gubski.AngryWaffleMaker | 1311 | 154 | 32 | 5-27 |  |
| 66 | cosimogonnelli.Team3player | 1307 | 275 | 7 | 1-6 |  |
| 67 | LucianCov.ourRobot | 1303 | 384 | 3 | 0-3 |  |
| 68 | MrHoseongLee.Neptune_v3 | 1303 | 384 | 3 | 0-3 |  |
| 69 | Phrancium.Frankplayer1 | 1303 | 384 | 3 | 0-3 |  |
| 70 | Pleket.Bot | 1303 | 384 | 3 | 0-3 |  |
| 71 | Strequals.rw8 | 1303 | 384 | 3 | 0-3 |  |
| 72 | Sukanya-Kothapally.team4player | 1303 | 384 | 3 | 0-3 |  |
| 73 | TeamSerpentine.eendagsvliegjes | 1303 | 384 | 3 | 0-3 |  |
| 74 | Tolsi.mybot | 1303 | 384 | 3 | 0-3 |  |
| 75 | anthonybench.FunkBot | 1303 | 384 | 3 | 0-3 |  |
| 76 | atliSig.buttletplayer | 1303 | 384 | 3 | 0-3 |  |
| 77 | charboltron.team11newbot | 1303 | 384 | 3 | 0-3 |  |
| 78 | djkeyes.addingComm | 1303 | 384 | 3 | 0-3 |  |
| 79 | fewella.FirstPlayer | 1303 | 384 | 3 | 0-3 |  |
| 80 | jmerle.camel_case_sprint | 1303 | 384 | 3 | 0-3 |  |
| 81 | kylittle.qualsbot2 | 1303 | 384 | 3 | 0-3 |  |
| 82 | lfchain.bigBudsBot | 1303 | 384 | 3 | 0-3 |  |
| 83 | luisgonzalex.CodeMonkeys | 1303 | 384 | 3 | 0-3 |  |
| 84 | mama4294.maloneplayer | 1303 | 384 | 3 | 0-3 |  |
| 85 | max-titov.seeding | 1303 | 384 | 3 | 0-3 |  |
| 86 | michaeltliu.beginnerplayer | 1303 | 384 | 3 | 0-3 |  |
| 87 | monmouth-college-cs.MyFirstPlayer | 1303 | 384 | 3 | 0-3 |  |
| 88 | ngkuru.qualifyingtournament | 1303 | 384 | 3 | 0-3 |  |
| 89 | orionquick.aldebaranplayer | 1303 | 384 | 3 | 0-3 |  |
| 90 | snpushpi.whatamidoing | 1303 | 384 | 3 | 0-3 |  |
| 91 | stevetimberman.playerbbbbb | 1303 | 384 | 3 | 0-3 |  |
| 92 | willBoyd8.bb8 | 1303 | 384 | 3 | 0-3 |  |
| 93 | jenlz.bustedJulianbot | 1297 | 262 | 14 | 1-13 |  |
| 94 | A9ine.potato | 1188 | 365 | 6 | 0-6 |  |
| 95 | 9mAhmad.MahinBot | 1162 | 362 | 7 | 0-7 |  |
| 96 | 9mAhmad.lostincoordinates | 1162 | 362 | 7 | 0-7 |  |
| 97 | AllenWang314.bot1 | 1162 | 362 | 7 | 0-7 |  |
| 98 | GabrielDWu.buildawall2 | 1162 | 362 | 7 | 0-7 |  |
| 99 | J-J-Chen.player | 1162 | 362 | 7 | 0-7 |  |
| 100 | KyleHassold.sprintbot | 1162 | 362 | 7 | 0-7 |  |
| 101 | denver-blake.sprint | 1162 | 362 | 7 | 0-7 |  |
