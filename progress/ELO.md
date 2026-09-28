# Ladder

9205 scrimmages (ours only), 8681 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| r1s17 | 1802 +- 47 | 12 of 105 | 237 | 130-107 | 81.8% | 26.8% (vs 11) |
| g_iter13 | 1794 +- 39 | 13 of 105 | 336 | 174-162 | 81.4% | 26.0% (vs 11) |
| cand69 | 1764 +- 74 | 15 of 105 | 96 | 46-50 | 79.9% | 25.1% (vs 12) |
| g_iter12 | 1764 +- 40 | 16 of 105 | 336 | 159-177 | 79.9% | 25.1% (vs 12) |
| cand81 | 1762 +- 91 | 17 of 105 | 95 | 67-28 | 79.8% | 24.9% (vs 12) |
| cand86s3 | 1760 +- 46 | 18 of 105 | 240 | 118-122 | 79.7% | 24.7% (vs 12) |
| r1s13 | 1760 +- 46 | 19 of 105 | 240 | 118-122 | 79.7% | 24.7% (vs 12) |
| cand87 | 1741 +- 46 | 20 of 105 | 240 | 112-128 | 78.7% | 22.9% (vs 12) |
| r2s4 | 1738 +- 46 | 21 of 105 | 240 | 111-129 | 78.6% | 22.6% (vs 12) |
| cand81s8 | 1736 +- 48 | 22 of 105 | 240 | 85-155 | 78.5% | 22.4% (vs 12) |
| cand86s2 | 1731 +- 73 | 23 of 105 | 96 | 44-52 | 78.3% | 22.0% (vs 12) |
| cand65 | 1731 +- 74 | 24 of 105 | 96 | 42-54 | 78.2% | 22.0% (vs 12) |
| r1s11 | 1728 +- 46 | 25 of 105 | 240 | 108-132 | 78.1% | 21.7% (vs 12) |
| r1s16 | 1723 +- 49 | 26 of 105 | 240 | 79-161 | 77.8% | 21.2% (vs 12) |
| r1s14 | 1718 +- 46 | 27 of 105 | 240 | 105-135 | 77.5% | 20.8% (vs 12) |
| arch_rush | 1691 +- 74 | 28 of 105 | 96 | 39-57 | 76.0% | 18.5% (vs 12) |
| g_iter11 | 1673 +- 48 | 29 of 105 | 240 | 107-133 | 74.9% | 17.1% (vs 12) |
| arch_rush2 | 1666 +- 75 | 30 of 105 | 96 | 36-60 | 74.6% | 16.6% (vs 12) |
| cand49b | 1664 +- 76 | 31 of 105 | 96 | 42-54 | 74.4% | 16.4% (vs 12) |
| g_iter10 | 1659 +- 48 | 32 of 105 | 240 | 103-137 | 74.1% | 16.0% (vs 12) |
| cand43b | 1659 +- 79 | 33 of 105 | 96 | 64-32 | 74.1% | 16.0% (vs 12) |
| cand47d | 1652 +- 54 | 34 of 105 | 192 | 73-119 | 73.7% | 15.6% (vs 12) |
| g_iter9 | 1640 +- 50 | 37 of 105 | 240 | 78-162 | 72.9% | 19.7% (vs 14) |
| g_iter8 | 1615 +- 49 | 38 of 105 | 240 | 108-132 | 71.2% | 17.8% (vs 14) |
| cand41b | 1609 +- 76 | 39 of 105 | 96 | 51-45 | 70.8% | 17.3% (vs 14) |
| g_iter5 | 1609 +- 34 | 40 of 105 | 618 | 329-289 | 70.8% | 17.3% (vs 14) |
| r1s8 | 1605 +- 78 | 41 of 105 | 96 | 29-67 | 70.5% | 17.0% (vs 14) |
| g_iter7 | 1603 +- 45 | 43 of 105 | 288 | 135-153 | 70.4% | 19.1% (vs 15) |
| g_iter6 | 1596 +- 26 | 44 of 105 | 886 | 450-436 | 69.9% | 18.6% (vs 15) |
| cand40c | 1594 +- 77 | 45 of 105 | 96 | 57-39 | 69.8% | 18.4% (vs 15) |
| cand42c | 1592 +- 76 | 46 of 105 | 96 | 49-47 | 69.6% | 18.3% (vs 15) |
| r1s3 | 1587 +- 80 | 47 of 105 | 96 | 27-69 | 69.2% | 17.9% (vs 15) |
| iter24 | 1585 +- 131 | 48 of 105 | 46 | 11-35 | 69.1% | 17.7% (vs 15) |
| g_iter3 | 1573 +- 33 | 49 of 105 | 796 | 167-629 | 68.2% | 16.9% (vs 15) |
| cand37 | 1566 +- 76 | 50 of 105 | 96 | 46-50 | 67.7% | 16.4% (vs 15) |
| cand31 | 1556 +- 75 | 51 of 105 | 96 | 52-44 | 66.8% | 15.7% (vs 15) |
| g_iter4 | 1553 +- 65 | 52 of 105 | 217 | 43-174 | 66.6% | 15.5% (vs 15) |
| g_iter2 | 1495 +- 70 | 53 of 105 | 192 | 44-148 | 61.6% | 12.0% (vs 15) |
| g_iter1 | 1492 +- 68 | 54 of 105 | 170 | 95-75 | 61.4% | 11.9% (vs 15) |
| arch_enclosure | 1348 +- 165 | 58 of 105 | 48 | 4-44 | 46.6% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2177 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2168 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2109 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2053 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 1994 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 6 | battlecode20-team4.finalbota | 1986 | 63 | 286 | 254-32 | 3% (r1s16 1-29) |
| 7 | IvanGeffner.finalbota | 1984 | 72 | 197 | 171-26 | 10% (r1s16 3-27) |
| 8 | winkelmantanner.tannerplayer | 1904 | 30 | 728 | 558-170 | 33% (r1s17 10-20) |
| 9 | mvpatel2000.qual | 1894 | 30 | 715 | 540-175 | 40% (r1s17 12-18) |
| 10 | rzhan11.quals_bot | 1838 | 35 | 458 | 310-148 | 33% (r1s17 10-20) |
| 11 | EmaPajic.Qualifications | 1834 | 27 | 775 | 540-235 | 27% (r2s4 8-22) |
| 12 | **us:r1s17** | 1802 | 47 | 237 | 130-107 |  |
| 13 | **us:g_iter13** | 1794 | 39 | 336 | 174-162 |  |
| 14 | poortho.stable_seeding_bot | 1775 | 24 | 947 | 609-338 | 40% (r1s17 12-18) |
| 15 | **us:cand69** | 1764 | 74 | 96 | 46-50 |  |
| 16 | **us:g_iter12** | 1764 | 40 | 336 | 159-177 |  |
| 17 | **us:cand81** | 1762 | 91 | 95 | 67-28 |  |
| 18 | **us:cand86s3** | 1760 | 46 | 240 | 118-122 |  |
| 19 | **us:r1s13** | 1760 | 46 | 240 | 118-122 |  |
| 20 | **us:cand87** | 1741 | 46 | 240 | 112-128 |  |
| 21 | **us:r2s4** | 1738 | 46 | 240 | 111-129 |  |
| 22 | **us:cand81s8** | 1736 | 48 | 240 | 85-155 |  |
| 23 | **us:cand86s2** | 1731 | 73 | 96 | 44-52 |  |
| 24 | **us:cand65** | 1731 | 74 | 96 | 42-54 |  |
| 25 | **us:r1s11** | 1728 | 46 | 240 | 108-132 |  |
| 26 | **us:r1s16** | 1723 | 49 | 240 | 79-161 |  |
| 27 | **us:r1s14** | 1718 | 46 | 240 | 105-135 |  |
| 28 | **us:arch_rush** | 1691 | 74 | 96 | 39-57 |  |
| 29 | **us:g_iter11** | 1673 | 48 | 240 | 107-133 |  |
| 30 | **us:arch_rush2** | 1666 | 75 | 96 | 36-60 |  |
| 31 | **us:cand49b** | 1664 | 76 | 96 | 42-54 |  |
| 32 | **us:g_iter10** | 1659 | 48 | 240 | 103-137 |  |
| 33 | **us:cand43b** | 1659 | 79 | 96 | 64-32 |  |
| 34 | **us:cand47d** | 1652 | 54 | 192 | 73-119 |  |
| 35 | cormackikkert.whyPermutator | 1645 | 23 | 937 | 442-495 | 80% (r1s17 24-6) |
| 36 | laurenschneider.pdx_team_one | 1645 | 23 | 914 | 434-480 | 70% (r1s17 21-9) |
| 37 | **us:g_iter9** | 1640 | 50 | 240 | 78-162 |  |
| 38 | **us:g_iter8** | 1615 | 49 | 240 | 108-132 |  |
| 39 | **us:cand41b** | 1609 | 76 | 96 | 51-45 |  |
| 40 | **us:g_iter5** | 1609 | 34 | 618 | 329-289 |  |
| 41 | **us:r1s8** | 1605 | 78 | 96 | 29-67 |  |
| 42 | benzyx.seeding | 1604 | 24 | 898 | 378-520 | 80% (r1s17 24-6) |
| 43 | **us:g_iter7** | 1603 | 45 | 288 | 135-153 |  |
| 44 | **us:g_iter6** | 1596 | 26 | 886 | 450-436 |  |
| 45 | **us:cand40c** | 1594 | 77 | 96 | 57-39 |  |
| 46 | **us:cand42c** | 1592 | 76 | 96 | 49-47 |  |
| 47 | **us:r1s3** | 1587 | 80 | 96 | 27-69 |  |
| 48 | **us:iter24** | 1585 | 131 | 46 | 11-35 |  |
| 49 | **us:g_iter3** | 1573 | 33 | 796 | 167-629 |  |
| 50 | **us:cand37** | 1566 | 76 | 96 | 46-50 |  |
| 51 | **us:cand31** | 1556 | 75 | 96 | 52-44 |  |
| 52 | **us:g_iter4** | 1553 | 65 | 217 | 43-174 |  |
| 53 | **us:g_iter2** | 1495 | 70 | 192 | 44-148 |  |
| 54 | **us:g_iter1** | 1492 | 68 | 170 | 95-75 |  |
| 55 | wpine215.stardustv2 | 1425 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 56 | mhahn2003.nonrush | 1378 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 57 | eggag32.BrutalPigeonBot | 1378 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 58 | **us:arch_enclosure** | 1348 | 165 | 48 | 4-44 |  |
| 59 | cs454-w20-team3.playbot | 1346 | 112 | 56 | 11-45 |  |
| 60 | ARognes.FinalSubmission | 1333 | 144 | 37 | 6-31 |  |
| 61 | TeamSerpentine.noodleBot | 1323 | 144 | 38 | 6-32 |  |
| 62 | opheez.landscapers | 1312 | 268 | 9 | 1-8 |  |
| 63 | ovimura.welovesoup | 1312 | 268 | 9 | 1-8 |  |
| 64 | rsandzimier.SandSibs_seeding | 1312 | 268 | 9 | 1-8 |  |
| 65 | thissop.alpha | 1312 | 268 | 9 | 1-8 |  |
| 66 | yaonam.Robot_1 | 1311 | 154 | 33 | 5-28 |  |
| 67 | VinayaBhat.team10pdx | 1308 | 222 | 15 | 2-13 |  |
| 68 | WilliamYue37.Player1 | 1304 | 140 | 46 | 6-40 |  |
| 69 | cosimogonnelli.Team3player | 1298 | 274 | 7 | 1-6 |  |
| 70 | Tim-gubski.AngryWaffleMaker | 1298 | 154 | 32 | 5-27 |  |
| 71 | LucianCov.ourRobot | 1296 | 383 | 3 | 0-3 |  |
| 72 | MrHoseongLee.Neptune_v3 | 1296 | 383 | 3 | 0-3 |  |
| 73 | Phrancium.Frankplayer1 | 1296 | 383 | 3 | 0-3 |  |
| 74 | Pleket.Bot | 1296 | 383 | 3 | 0-3 |  |
| 75 | Strequals.rw8 | 1296 | 383 | 3 | 0-3 |  |
| 76 | Sukanya-Kothapally.team4player | 1296 | 383 | 3 | 0-3 |  |
| 77 | TeamSerpentine.eendagsvliegjes | 1296 | 383 | 3 | 0-3 |  |
| 78 | Tolsi.mybot | 1296 | 383 | 3 | 0-3 |  |
| 79 | anthonybench.FunkBot | 1296 | 383 | 3 | 0-3 |  |
| 80 | atliSig.buttletplayer | 1296 | 383 | 3 | 0-3 |  |
| 81 | charboltron.team11newbot | 1296 | 383 | 3 | 0-3 |  |
| 82 | djkeyes.addingComm | 1296 | 383 | 3 | 0-3 |  |
| 83 | fewella.FirstPlayer | 1296 | 383 | 3 | 0-3 |  |
| 84 | jmerle.camel_case_sprint | 1296 | 383 | 3 | 0-3 |  |
| 85 | kylittle.qualsbot2 | 1296 | 383 | 3 | 0-3 |  |
| 86 | lfchain.bigBudsBot | 1296 | 383 | 3 | 0-3 |  |
| 87 | luisgonzalex.CodeMonkeys | 1296 | 383 | 3 | 0-3 |  |
| 88 | mama4294.maloneplayer | 1296 | 383 | 3 | 0-3 |  |
| 89 | max-titov.seeding | 1296 | 383 | 3 | 0-3 |  |
| 90 | michaeltliu.beginnerplayer | 1296 | 383 | 3 | 0-3 |  |
| 91 | monmouth-college-cs.MyFirstPlayer | 1296 | 383 | 3 | 0-3 |  |
| 92 | ngkuru.qualifyingtournament | 1296 | 383 | 3 | 0-3 |  |
| 93 | orionquick.aldebaranplayer | 1296 | 383 | 3 | 0-3 |  |
| 94 | snpushpi.whatamidoing | 1296 | 383 | 3 | 0-3 |  |
| 95 | stevetimberman.playerbbbbb | 1296 | 383 | 3 | 0-3 |  |
| 96 | willBoyd8.bb8 | 1296 | 383 | 3 | 0-3 |  |
| 97 | jenlz.bustedJulianbot | 1287 | 262 | 14 | 1-13 |  |
| 98 | A9ine.potato | 1180 | 365 | 6 | 0-6 |  |
| 99 | 9mAhmad.MahinBot | 1154 | 362 | 7 | 0-7 |  |
| 100 | 9mAhmad.lostincoordinates | 1154 | 362 | 7 | 0-7 |  |
| 101 | AllenWang314.bot1 | 1154 | 362 | 7 | 0-7 |  |
| 102 | GabrielDWu.buildawall2 | 1154 | 362 | 7 | 0-7 |  |
| 103 | J-J-Chen.player | 1154 | 362 | 7 | 0-7 |  |
| 104 | KyleHassold.sprintbot | 1154 | 362 | 7 | 0-7 |  |
| 105 | denver-blake.sprint | 1154 | 362 | 7 | 0-7 |  |
