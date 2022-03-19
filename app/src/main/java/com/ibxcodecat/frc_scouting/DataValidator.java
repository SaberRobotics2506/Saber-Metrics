package com.ibxcodecat.frc_scouting;

/// This class is for validating data that could be inacurate due to human error
public class DataValidator {

    final int NAME_CHAR_LIMIT = 50;
    final int COMMENTS_CHAR_LIMIT = 500;
    final int MAX_SCORE = 100;

    //Checks data in DataEntryActivity, returns -1 for pass, and a positive int for the problematic field
    public enum DataError { ScoutedByError, ScoreError, CommentsError, NONE }

    public DataError CheckData(String scoutedBy, String score, String comments)
    {

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

    private boolean ValidateComments(String comments)
    {
        return comments.length() < COMMENTS_CHAR_LIMIT && comments.length() > 0;
    }
}
