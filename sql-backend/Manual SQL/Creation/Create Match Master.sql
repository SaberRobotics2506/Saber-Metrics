CREATE TABLE MatchMaster2(
TeamNumber Int,
MatchNumber Int,
ScoutedBy VarChar (100),
Regional VarChar (100),
AutoLowGoalMake Int,
AutoHighGoalMake Int,
Taxi Int,
TelopLowGoalMake Int,
TelopLowGoalMiss Int,
TelopHighGoalMake Int,
TelopHighGoalMiss Int,
DefensePlays Int,
ClimbAttemptResult Int,
--1=did not attempt, 2= failed, 3=Level 1, 4=Level 2, 5=Level 3, 6=Level 4
AllianceScore Int,
WinLossTie Int,
--1=W, 2=L, 3=T
Comments VarChar (500)
);