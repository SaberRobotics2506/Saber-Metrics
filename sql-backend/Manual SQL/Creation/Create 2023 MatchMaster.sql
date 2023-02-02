CREATE TABLE MidwestScouting2023(
TeamNumber Int,
MatchNumber Int,
ScoutedBy VarChar (100),
Comments varChar (500),

--0 = loss 1 = win 2 = tie
GameResult Int,
AllianceScore Int,
IsParked Bit,
DidMobility Bit,

--variables for autonomous incrementers
AutoHighStep Int,
AutoMidStep Int,
AutoLowStep Int,

--0 = none 1 = docked not engaged 2 = engaged
AutoBalancePlatforms int,

--Teliop Thingys
NumCubes Int,
NumCones Int,
--0 = none 1 = docked not engaged 2 = engaged
TeleBalancePlatforms int,

--Defensive Plays
DefensePlays Int

);