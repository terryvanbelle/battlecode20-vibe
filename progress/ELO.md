# Ladder

8968 scrimmages (ours only), 8444 distinct (a repeated pairing with the same seed replays the same game and counts once), rated by a batch Bradley-Terry fit on the Elo scale (`tools/elolib.py`); each of our builds is its own player. 65 of 65 ladder bots met.

Our builds (rating +- 95%; field score = expected score against every ladder bot, one game each; vs higher = the same against only the ladder bots rated above the build, with their count):

| build | rating | rank | games | record | field score | vs higher |
|---|---|---|---|---|---|---|
| g_iter13 | 1798 +- 39 | 12 of 104 | 336 | 174-162 | 81.4% | 26.0% (vs 11) |
| cand69 | 1769 +- 74 | 14 of 104 | 96 | 46-50 | 80.0% | 25.2% (vs 12) |
| g_iter12 | 1768 +- 40 | 15 of 104 | 336 | 159-177 | 80.0% | 25.2% (vs 12) |
| cand81 | 1766 +- 91 | 16 of 104 | 95 | 67-28 | 79.9% | 25.0% (vs 12) |
| cand86s3 | 1764 +- 46 | 17 of 104 | 240 | 118-122 | 79.8% | 24.7% (vs 12) |
| r1s13 | 1764 +- 46 | 18 of 104 | 240 | 118-122 | 79.8% | 24.7% (vs 12) |
| cand87 | 1745 +- 46 | 19 of 104 | 240 | 112-128 | 78.8% | 22.9% (vs 12) |
| r2s4 | 1741 +- 46 | 20 of 104 | 240 | 111-129 | 78.6% | 22.6% (vs 12) |
| cand81s8 | 1739 +- 48 | 21 of 104 | 240 | 85-155 | 78.5% | 22.4% (vs 12) |
| cand65 | 1736 +- 74 | 22 of 104 | 96 | 42-54 | 78.3% | 22.1% (vs 12) |
| cand86s2 | 1735 +- 73 | 23 of 104 | 96 | 44-52 | 78.3% | 22.0% (vs 12) |
| r1s11 | 1732 +- 46 | 24 of 104 | 240 | 108-132 | 78.1% | 21.7% (vs 12) |
| r1s16 | 1726 +- 49 | 25 of 104 | 240 | 79-161 | 77.8% | 21.2% (vs 12) |
| r1s14 | 1722 +- 46 | 26 of 104 | 240 | 105-135 | 77.6% | 20.9% (vs 12) |
| arch_rush | 1695 +- 74 | 27 of 104 | 96 | 39-57 | 76.1% | 18.6% (vs 12) |
| g_iter11 | 1677 +- 48 | 28 of 104 | 240 | 107-133 | 75.0% | 17.2% (vs 12) |
| arch_rush2 | 1670 +- 75 | 29 of 104 | 96 | 36-60 | 74.6% | 16.6% (vs 12) |
| cand49b | 1668 +- 76 | 30 of 104 | 96 | 42-54 | 74.5% | 16.5% (vs 12) |
| g_iter10 | 1664 +- 48 | 31 of 104 | 240 | 103-137 | 74.2% | 16.1% (vs 12) |
| cand43b | 1663 +- 79 | 32 of 104 | 96 | 64-32 | 74.1% | 16.1% (vs 12) |
| cand47d | 1657 +- 54 | 33 of 104 | 192 | 73-119 | 73.8% | 15.7% (vs 12) |
| g_iter9 | 1645 +- 50 | 36 of 104 | 240 | 78-162 | 73.0% | 19.7% (vs 14) |
| g_iter8 | 1620 +- 49 | 37 of 104 | 240 | 108-132 | 71.4% | 17.8% (vs 14) |
| cand41b | 1614 +- 76 | 38 of 104 | 96 | 51-45 | 71.0% | 17.4% (vs 14) |
| g_iter5 | 1613 +- 34 | 39 of 104 | 618 | 329-289 | 70.9% | 17.3% (vs 14) |
| r1s8 | 1609 +- 78 | 40 of 104 | 96 | 29-67 | 70.6% | 17.0% (vs 14) |
| g_iter7 | 1608 +- 45 | 42 of 104 | 288 | 135-153 | 70.5% | 19.1% (vs 15) |
| g_iter6 | 1601 +- 26 | 43 of 104 | 886 | 450-436 | 70.0% | 18.6% (vs 15) |
| cand40c | 1599 +- 77 | 44 of 104 | 96 | 57-39 | 69.9% | 18.4% (vs 15) |
| cand42c | 1597 +- 76 | 45 of 104 | 96 | 49-47 | 69.7% | 18.3% (vs 15) |
| r1s3 | 1590 +- 80 | 46 of 104 | 96 | 27-69 | 69.3% | 17.8% (vs 15) |
| iter24 | 1589 +- 131 | 47 of 104 | 46 | 11-35 | 69.2% | 17.8% (vs 15) |
| g_iter3 | 1577 +- 33 | 48 of 104 | 796 | 167-629 | 68.3% | 16.9% (vs 15) |
| cand37 | 1571 +- 76 | 49 of 104 | 96 | 46-50 | 67.8% | 16.5% (vs 15) |
| cand31 | 1560 +- 75 | 50 of 104 | 96 | 52-44 | 66.9% | 15.7% (vs 15) |
| g_iter4 | 1557 +- 65 | 51 of 104 | 217 | 43-174 | 66.7% | 15.5% (vs 15) |
| g_iter2 | 1499 +- 70 | 52 of 104 | 192 | 44-148 | 61.8% | 12.0% (vs 15) |
| g_iter1 | 1495 +- 68 | 53 of 104 | 170 | 95-75 | 61.4% | 11.8% (vs 15) |
| arch_enclosure | 1353 +- 165 | 57 of 104 | 48 | 4-44 | 46.8% | 12.2% (vs 18) |

Our last run = OUR win rate (our W-L) against the bot, by the most recent of our builds that played it 30+ times (+- 18 points at 95% for 30 games, +- 15 for 42; blank if no build has).

| rank | player | rating | +- 95% | games | W-L | our last run |
|---|---|---|---|---|---|---|
| 1 | awesomelemonade.citricsky | 2181 | 200 | 103 | 101-2 | 3% (g_iter3 2-58) |
| 2 | uvafan.v14_final_bot | 2172 | 200 | 98 | 96-2 | 2% (g_iter3 1-53) |
| 3 | AngusRitossa.newbot | 2113 | 174 | 95 | 92-3 | 2% (g_iter3 1-53) |
| 4 | StoneT2000.FinalChowBotStable | 2056 | 181 | 39 | 36-3 |  |
| 5 | ronniesong0809.finalbota | 1998 | 98 | 127 | 114-13 | 7% (g_iter12 2-28) |
| 6 | battlecode20-team4.finalbota | 1990 | 63 | 286 | 254-32 | 3% (r1s16 1-29) |
| 7 | IvanGeffner.finalbota | 1988 | 72 | 197 | 171-26 | 10% (r1s16 3-27) |
| 8 | winkelmantanner.tannerplayer | 1907 | 31 | 698 | 538-160 | 20% (r2s4 6-24) |
| 9 | mvpatel2000.qual | 1899 | 31 | 685 | 522-163 | 30% (r2s4 9-21) |
| 10 | EmaPajic.Qualifications | 1843 | 28 | 748 | 530-218 | 27% (r2s4 8-22) |
| 11 | rzhan11.quals_bot | 1835 | 36 | 428 | 290-138 | 43% (r2s4 13-17) |
| 12 | **us:g_iter13** | 1798 | 39 | 336 | 174-162 |  |
| 13 | poortho.stable_seeding_bot | 1776 | 24 | 917 | 591-326 | 47% (r2s4 14-16) |
| 14 | **us:cand69** | 1769 | 74 | 96 | 46-50 |  |
| 15 | **us:g_iter12** | 1768 | 40 | 336 | 159-177 |  |
| 16 | **us:cand81** | 1766 | 91 | 95 | 67-28 |  |
| 17 | **us:cand86s3** | 1764 | 46 | 240 | 118-122 |  |
| 18 | **us:r1s13** | 1764 | 46 | 240 | 118-122 |  |
| 19 | **us:cand87** | 1745 | 46 | 240 | 112-128 |  |
| 20 | **us:r2s4** | 1741 | 46 | 240 | 111-129 |  |
| 21 | **us:cand81s8** | 1739 | 48 | 240 | 85-155 |  |
| 22 | **us:cand65** | 1736 | 74 | 96 | 42-54 |  |
| 23 | **us:cand86s2** | 1735 | 73 | 96 | 44-52 |  |
| 24 | **us:r1s11** | 1732 | 46 | 240 | 108-132 |  |
| 25 | **us:r1s16** | 1726 | 49 | 240 | 79-161 |  |
| 26 | **us:r1s14** | 1722 | 46 | 240 | 105-135 |  |
| 27 | **us:arch_rush** | 1695 | 74 | 96 | 39-57 |  |
| 28 | **us:g_iter11** | 1677 | 48 | 240 | 107-133 |  |
| 29 | **us:arch_rush2** | 1670 | 75 | 96 | 36-60 |  |
| 30 | **us:cand49b** | 1668 | 76 | 96 | 42-54 |  |
| 31 | **us:g_iter10** | 1664 | 48 | 240 | 103-137 |  |
| 32 | **us:cand43b** | 1663 | 79 | 96 | 64-32 |  |
| 33 | **us:cand47d** | 1657 | 54 | 192 | 73-119 |  |
| 34 | cormackikkert.whyPermutator | 1652 | 23 | 907 | 436-471 | 57% (r2s4 17-13) |
| 35 | laurenschneider.pdx_team_one | 1649 | 23 | 884 | 425-459 | 57% (r2s4 17-13) |
| 36 | **us:g_iter9** | 1645 | 50 | 240 | 78-162 |  |
| 37 | **us:g_iter8** | 1620 | 49 | 240 | 108-132 |  |
| 38 | **us:cand41b** | 1614 | 76 | 96 | 51-45 |  |
| 39 | **us:g_iter5** | 1613 | 34 | 618 | 329-289 |  |
| 40 | **us:r1s8** | 1609 | 78 | 96 | 29-67 |  |
| 41 | benzyx.seeding | 1609 | 24 | 868 | 372-496 | 90% (r2s4 27-3) |
| 42 | **us:g_iter7** | 1608 | 45 | 288 | 135-153 |  |
| 43 | **us:g_iter6** | 1601 | 26 | 886 | 450-436 |  |
| 44 | **us:cand40c** | 1599 | 77 | 96 | 57-39 |  |
| 45 | **us:cand42c** | 1597 | 76 | 96 | 49-47 |  |
| 46 | **us:r1s3** | 1590 | 80 | 96 | 27-69 |  |
| 47 | **us:iter24** | 1589 | 131 | 46 | 11-35 |  |
| 48 | **us:g_iter3** | 1577 | 33 | 796 | 167-629 |  |
| 49 | **us:cand37** | 1571 | 76 | 96 | 46-50 |  |
| 50 | **us:cand31** | 1560 | 75 | 96 | 52-44 |  |
| 51 | **us:g_iter4** | 1557 | 65 | 217 | 43-174 |  |
| 52 | **us:g_iter2** | 1499 | 70 | 192 | 44-148 |  |
| 53 | **us:g_iter1** | 1495 | 68 | 170 | 95-75 |  |
| 54 | wpine215.stardustv2 | 1430 | 36 | 461 | 122-339 | 90% (g_iter11 27-3) |
| 55 | mhahn2003.nonrush | 1383 | 51 | 256 | 57-199 | 80% (g_iter8 24-6) |
| 56 | eggag32.BrutalPigeonBot | 1382 | 58 | 200 | 43-157 | 81% (g_iter6 54-13) |
| 57 | **us:arch_enclosure** | 1353 | 165 | 48 | 4-44 |  |
| 58 | cs454-w20-team3.playbot | 1349 | 112 | 56 | 11-45 |  |
| 59 | ARognes.FinalSubmission | 1337 | 144 | 37 | 6-31 |  |
| 60 | TeamSerpentine.noodleBot | 1327 | 144 | 38 | 6-32 |  |
| 61 | opheez.landscapers | 1315 | 268 | 9 | 1-8 |  |
| 62 | ovimura.welovesoup | 1315 | 268 | 9 | 1-8 |  |
| 63 | rsandzimier.SandSibs_seeding | 1315 | 268 | 9 | 1-8 |  |
| 64 | thissop.alpha | 1315 | 268 | 9 | 1-8 |  |
| 65 | yaonam.Robot_1 | 1314 | 154 | 33 | 5-28 |  |
| 66 | VinayaBhat.team10pdx | 1311 | 222 | 15 | 2-13 |  |
| 67 | WilliamYue37.Player1 | 1308 | 140 | 46 | 6-40 |  |
| 68 | Tim-gubski.AngryWaffleMaker | 1302 | 154 | 32 | 5-27 |  |
| 69 | cosimogonnelli.Team3player | 1301 | 274 | 7 | 1-6 |  |
| 70 | LucianCov.ourRobot | 1298 | 384 | 3 | 0-3 |  |
| 71 | MrHoseongLee.Neptune_v3 | 1298 | 384 | 3 | 0-3 |  |
| 72 | Phrancium.Frankplayer1 | 1298 | 384 | 3 | 0-3 |  |
| 73 | Pleket.Bot | 1298 | 384 | 3 | 0-3 |  |
| 74 | Strequals.rw8 | 1298 | 384 | 3 | 0-3 |  |
| 75 | Sukanya-Kothapally.team4player | 1298 | 384 | 3 | 0-3 |  |
| 76 | TeamSerpentine.eendagsvliegjes | 1298 | 384 | 3 | 0-3 |  |
| 77 | Tolsi.mybot | 1298 | 384 | 3 | 0-3 |  |
| 78 | anthonybench.FunkBot | 1298 | 384 | 3 | 0-3 |  |
| 79 | atliSig.buttletplayer | 1298 | 384 | 3 | 0-3 |  |
| 80 | charboltron.team11newbot | 1298 | 384 | 3 | 0-3 |  |
| 81 | djkeyes.addingComm | 1298 | 384 | 3 | 0-3 |  |
| 82 | fewella.FirstPlayer | 1298 | 384 | 3 | 0-3 |  |
| 83 | jmerle.camel_case_sprint | 1298 | 384 | 3 | 0-3 |  |
| 84 | kylittle.qualsbot2 | 1298 | 384 | 3 | 0-3 |  |
| 85 | lfchain.bigBudsBot | 1298 | 384 | 3 | 0-3 |  |
| 86 | luisgonzalex.CodeMonkeys | 1298 | 384 | 3 | 0-3 |  |
| 87 | mama4294.maloneplayer | 1298 | 384 | 3 | 0-3 |  |
| 88 | max-titov.seeding | 1298 | 384 | 3 | 0-3 |  |
| 89 | michaeltliu.beginnerplayer | 1298 | 384 | 3 | 0-3 |  |
| 90 | monmouth-college-cs.MyFirstPlayer | 1298 | 384 | 3 | 0-3 |  |
| 91 | ngkuru.qualifyingtournament | 1298 | 384 | 3 | 0-3 |  |
| 92 | orionquick.aldebaranplayer | 1298 | 384 | 3 | 0-3 |  |
| 93 | snpushpi.whatamidoing | 1298 | 384 | 3 | 0-3 |  |
| 94 | stevetimberman.playerbbbbb | 1298 | 384 | 3 | 0-3 |  |
| 95 | willBoyd8.bb8 | 1298 | 384 | 3 | 0-3 |  |
| 96 | jenlz.bustedJulianbot | 1290 | 262 | 14 | 1-13 |  |
| 97 | A9ine.potato | 1182 | 365 | 6 | 0-6 |  |
| 98 | 9mAhmad.MahinBot | 1156 | 362 | 7 | 0-7 |  |
| 99 | 9mAhmad.lostincoordinates | 1156 | 362 | 7 | 0-7 |  |
| 100 | AllenWang314.bot1 | 1156 | 362 | 7 | 0-7 |  |
| 101 | GabrielDWu.buildawall2 | 1156 | 362 | 7 | 0-7 |  |
| 102 | J-J-Chen.player | 1156 | 362 | 7 | 0-7 |  |
| 103 | KyleHassold.sprintbot | 1156 | 362 | 7 | 0-7 |  |
| 104 | denver-blake.sprint | 1156 | 362 | 7 | 0-7 |  |
