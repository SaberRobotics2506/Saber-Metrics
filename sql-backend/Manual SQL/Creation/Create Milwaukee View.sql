--This query is to create a view of the match data. A SQL View is more or less a saved query, that will automatically populate a table of the desired information.
--So MatchMaster has all of the raw data(each robot per each match has its own row)
--And this View has a compiled result set of each robot(each team# has compiled information for all their matches)
CREATE VIEW MilwaukeeData AS

--Outer query selects everything from the inner query, and adds a separate column for the super score
--Super score is a sum of all the averages that a team is responsible for.
(SELECT        TeamNumber, TotalMatches, NumberTaxi, TotalAutoLow, TotalAutoHigh, TotalLow, TotalHigh, MaxLow, MaxHigh, LowGoalAccuracy, HighGoalAccuracy, NumClimb1, NumClimb2, NumClimb3, NumClimb4, TotalDefensivePlays,
               MaxDefensivePlays, NumWins, NumLosses, NumTies, AvgTaxiScore, AvgAutoLowGoalMake, AvgAutoHighGoalMake, AvgTelopLowGoalMake, AvgTelopHighGoalMake, EndGame1, EndGame2, EndGame3, EndGame4,
               AvgTaxiScore + AvgAutoLowGoalMake + AvgAutoHighGoalMake + AvgTelopLowGoalMake + AvgTelopHighGoalMake + EndGame1 + EndGame2 + EndGame3 + EndGame4 AS SuperScore
FROM(
	--Inner query selects and aggregates data from MatchMaster and groups by team#
	SELECT
	TeamNumber,
	COUNT(TeamNumber) AS TotalMatches,
	SUM(Taxi) AS NumberTaxi,
	SUM(AutoLowGoalMake) AS TotalAutoLow,
	SUM(AutoHighGoalMake) AS TotalAutoHigh,
	SUM(TelopLowGoalMake) AS TotalLow,
	SUM(TelopHighGoalMake) AS TotalHigh,
	MAX(TelopLowGoalMake) AS MaxLow,
	MAX(TelopHighGoalMake) AS MaxHigh,
	--Necessary to cast as float in order for the decimals to be calculated for accuracy
	SUM(CAST((TelopLowGoalMake) AS FLOAT)) / CASE WHEN (SUM((CAST((TelopLowGoalMake)AS FLOAT) + CAST((TelopLowGoalMiss)AS FLOAT)))=0) THEN Null Else SUM((CAST((TelopLowGoalMake)AS FLOAT) + CAST((TelopLowGoalMiss)AS FLOAT))) END AS LowGoalAccuracy,
	SUM(CAST((TelopHighGoalMake) AS FLOAT)) / CASE WHEN (SUM((CAST((TelopHighGoalMake)AS FLOAT) + CAST((TelopHighGoalMiss)AS FLOAT)))=0) THEN Null Else SUM((CAST((TelopHighGoalMake)AS FLOAT) + CAST((TelopHighGoalMiss)AS FLOAT))) END AS HighGoalAccuracy,
	SUM(CASE WHEN(ClimbAttemptResult='3') THEN 1 ELSE 0 END) AS NumClimb1,
	SUM(CASE WHEN(ClimbAttemptResult='4') THEN 1 ELSE 0 END) AS NumClimb2,
	SUM(CASE WHEN(ClimbAttemptResult='5') THEN 1 ELSE 0 END) AS NumClimb3,
	SUM(CASE WHEN(ClimbAttemptResult='6') THEN 1 ELSE 0 END) AS NumClimb4,
	SUM(DefensePlays) AS TotalDefensivePlays,
	MAX(DefensePlays) AS MaxDefensivePlays,
	SUM(CASE WHEN(WinLossTie='1') THEN 1 ELSE 0 END) AS NumWins,
	SUM(CASE WHEN(WinLossTie='2') THEN 1 ELSE 0 END) AS NumLosses,
	SUM(CASE WHEN(WinLossTie='3') THEN 1 ELSE 0 END) AS NumTies,
	--Necessary to cast as Float in order for the calculations to work
	AVG(CAST((Taxi) AS FLOAT))*2 AS AvgTaxiScore,
	AVG(CAST((AutoLowGoalMake) AS FLOAT))*2 AS AvgAutoLowGoalMake,
	AVG(CAST((AutoHighGoalMake) AS FLOAT))*4 AS AvgAutoHighGoalMake,
	AVG(CAST((TelopLowGoalMake) AS FLOAT))*1 AS AvgTelopLowGoalMake,
	AVG(CAST((TelopHighGoalMake) AS FLOAT))*2 AS AvgTelopHighGoalMake,
	--Since EndGameSuccess is saved in the same field, need to use Cases to differentiate the scenarios
	((SUM(CASE WHEN(ClimbAttemptResult='3') THEN CAST((1)AS FLOAT) ELSE 0 END)/(COUNT(CAST((TeamNumber)AS FLOAT))))*4) AS EndGame1,
	((SUM(CASE WHEN(ClimbAttemptResult='4') THEN CAST((1)AS FLOAT) ELSE 0 END)/(COUNT(CAST((TeamNumber)AS FLOAT))))*6) AS EndGame2,
	((SUM(CASE WHEN(ClimbAttemptResult='5') THEN CAST((1)AS FLOAT) ELSE 0 END)/(COUNT(CAST((TeamNumber)AS FLOAT))))*10) AS EndGame3,
	((SUM(CASE WHEN(ClimbAttemptResult='6') THEN CAST((1)AS FLOAT) ELSE 0 END)/(COUNT(CAST((TeamNumber)AS FLOAT))))*15) AS EndGame4
	FROM [Scouting 2022].[dbo].[MatchMaster2]
	--This WHERE clause differentiates which regional should be used to aggregate on.
	WHERE Regional = 'Milwaukee'
	GROUP BY TeamNumber
)A
--Need to name the inner query, SQL syntax requirement
)