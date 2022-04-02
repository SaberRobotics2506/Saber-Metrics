package com.ibxcodecat.frc_scouting;

public class DataValidator {

    final int TOTAL_MATCHES = 99;
    final int NAME_CHAR_LIMIT = 50;
    final int COMMENTS_CHAR_LIMIT = 500;
    final int MAX_SCORE = 200;

    int[] validTeams = { 93, 167, 876, 1675, 1732, 2052, 2175, 2227, 2239, 2264, 2501, 2503, 2506, 2526, 2531, 2861, 2977, 3058, 3102, 3122, 3134, 3197, 3275, 3276, 3291, 3294, 3740, 4009, 4181, 4230, 4238, 4511, 4607, 4656, 4674, 4728, 4741, 5125, 5143, 5253, 5348, 5464, 5653, 5690, 5826, 5847, 6047, 6146, 6160, 6217, 6318, 6574, 7048, 7068, 7311, 7797, 7893, 8836 };

    //Checks data in DataEntryActivity, returns -1 for pass, and a positive int for the problematic field
<<<<<<< Updated upstream
    public enum DataError { TeamNumberError, MatchNumberError, ScoutedByError, ScoreError, CommentsError, ClimbAttemptError, ClimbLevelError, GameResultError, NONE }

    public DataError CheckData(String teamNumber, int matchNumber, String scoutedBy, String score, String comments)
    {
        if(!ValidateTeamNumber(teamNumber))
        {
            return DataError.TeamNumberError;
        }

=======
    public enum DataError {ScoutedByError, ScoreError, CommentsError, TeamNumberError, MatchNumberError, NONE }

    public DataError CheckData(String matchNumber, String scoutedBy, String score, String comments)
    {
>>>>>>> Stashed changes
        if(!ValidateMatchNumber(matchNumber))
        {
            return DataError.MatchNumberError;
        }
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        if(!ValidateScore(score))
        {
            return DataError.ScoreError;
        }

        if(!ValidateScoutedBy(scoutedBy))
        {
            return DataError.ScoutedByError;
        }

        if(!ValidateComments(comments))
        {
            return DataError.CommentsError;
        }
        return DataError.NONE;
    }

    private boolean ValidateTeamNumber(String teamNumber) {
        for(int i : validTeams)
        {
            int team;

            try
            {
                team = Integer.parseInt(teamNumber);
            }
            catch(NumberFormatException ex)
            {
                return false;
            }

            if(i == team)
            {
                return true;
            }
        }

        return false;
    }

    private boolean ValidateMatchNumber(int matchNumber)
    {
        return matchNumber <= TOTAL_MATCHES && matchNumber > 0;
    }

    private boolean ValidateScore(String allianceScore)
    {
        int score;

        try
        {
            score = Integer.parseInt(allianceScore);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }

        return score <= MAX_SCORE;
    }

    private boolean ValidateScoutedBy(String scoutedBy)
    {
        return scoutedBy.length() < NAME_CHAR_LIMIT && scoutedBy.length() > 0;
    }

    private boolean ValidateComments(String comments) {
        return comments.length() < COMMENTS_CHAR_LIMIT && comments.length() > 0;
    }

    //Potentially unnecessary but it's there to cover just in case
    private boolean ValidateMatchNumber(String matchNumber)
    {
        int matchNum;
        try
        {
            matchNum = Integer.parseInt(matchNumber);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }

        return true;
    }
}
