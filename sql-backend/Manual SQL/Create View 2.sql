CREATE VIEW Milwaukee2022 

AS

(SELECT        TeamNumber, TotalMatches, NumberTaxi, TotalAutoLow, TotalAutoHigh, TotalLow, TotalHigh, MaxLow, MaxHigh, LowGoalAccuracy, HighGoalAccuracy, NumClimb1, NumClimb2, NumClimb3, NumClimb4, TotalDefensivePlays, 
                         MaxDefensivePlays, NumWins, NumLosses, NumTies, AvgTaxiScore, AvgAutoLowGoalMake, AvgAutoHighGoalMake, AvgTelopLowGoalMake, AvgTelopHighGoalMake, EndGame1, EndGame2, EndGame3, EndGame4, 
                         AvgTaxiScore + AvgAutoLowGoalMake + AvgAutoHighGoalMake + AvgTelopLowGoalMake + AvgTelopHighGoalMake + EndGame1 + EndGame2 + EndGame3 + EndGame4 AS SuperScore
FROM            (SELECT        TeamNumber, COUNT(TeamNumber) AS TotalMatches, SUM(Taxi) AS NumberTaxi, SUM(AutoLowGoalMake) AS TotalAutoLow, SUM(AutoHighGoalMake) AS TotalAutoHigh, SUM(TelopLowGoalMake) AS TotalLow, 
                                                    SUM(TelopHighGoalMake) AS TotalHigh, MAX(TelopLowGoalMake) AS MaxLow, MAX(TelopHighGoalMake) AS MaxHigh, SUM(CAST(TelopLowGoalMake AS FLOAT)) / CASE WHEN (SUM((CAST((TelopLowGoalMake) 
                                                    AS FLOAT) + CAST((TelopLowGoalMiss) AS FLOAT))) = 0) THEN NULL ELSE SUM((CAST((TelopLowGoalMake) AS FLOAT) + CAST((TelopLowGoalMiss) AS FLOAT))) END AS LowGoalAccuracy, 
                                                    SUM(CAST(TelopHighGoalMake AS FLOAT)) / CASE WHEN (SUM((CAST((TelopHighGoalMake) AS FLOAT) + CAST((TelopHighGoalMiss) AS FLOAT))) = 0) THEN NULL ELSE SUM((CAST((TelopHighGoalMake) AS FLOAT) 
                                                    + CAST((TelopHighGoalMiss) AS FLOAT))) END AS HighGoalAccuracy, SUM(CASE WHEN (ClimbAttemptResult = '3') THEN 1 ELSE 0 END) AS NumClimb1, SUM(CASE WHEN (ClimbAttemptResult = '4') 
                                                    THEN 1 ELSE 0 END) AS NumClimb2, SUM(CASE WHEN (ClimbAttemptResult = '5') THEN 1 ELSE 0 END) AS NumClimb3, SUM(CASE WHEN (ClimbAttemptResult = '6') THEN 1 ELSE 0 END) AS NumClimb4, 
                                                    SUM(DefensePlays) AS TotalDefensivePlays, MAX(DefensePlays) AS MaxDefensivePlays, SUM(CASE WHEN (WinLossTie = '1') THEN 1 ELSE 0 END) AS NumWins, SUM(CASE WHEN (WinLossTie = '2') 
                                                    THEN 1 ELSE 0 END) AS NumLosses, SUM(CASE WHEN (WinLossTie = '3') THEN 1 ELSE 0 END) AS NumTies, AVG(CAST(Taxi AS FLOAT)) * 2 AS AvgTaxiScore, AVG(CAST(AutoLowGoalMake AS FLOAT)) 
                                                    * 2 AS AvgAutoLowGoalMake, AVG(CAST(AutoHighGoalMake AS FLOAT)) * 4 AS AvgAutoHighGoalMake, AVG(CAST(TelopLowGoalMake AS FLOAT)) * 1 AS AvgTelopLowGoalMake, 
                                                    AVG(CAST(TelopHighGoalMake AS FLOAT)) * 2 AS AvgTelopHighGoalMake, SUM(CASE WHEN (ClimbAttemptResult = '3') THEN CAST((1) AS FLOAT) ELSE 0 END) / COUNT(CAST(TeamNumber AS FLOAT)) 
                                                    * 4 AS EndGame1, SUM(CASE WHEN (ClimbAttemptResult = '4') THEN CAST((1) AS FLOAT) ELSE 0 END) / COUNT(CAST(TeamNumber AS FLOAT)) * 6 AS EndGame2, SUM(CASE WHEN (ClimbAttemptResult = '5') 
                                                    THEN CAST((1) AS FLOAT) ELSE 0 END) / COUNT(CAST(TeamNumber AS FLOAT)) * 10 AS EndGame3, SUM(CASE WHEN (ClimbAttemptResult = '6') THEN CAST((1) AS FLOAT) ELSE 0 END) 
                                                    / COUNT(CAST(TeamNumber AS FLOAT)) * 15 AS EndGame4
                          FROM            dbo.MatchMaster2
                          WHERE        (Regional = 'Milwaukee')
                          GROUP BY TeamNumber) AS A)