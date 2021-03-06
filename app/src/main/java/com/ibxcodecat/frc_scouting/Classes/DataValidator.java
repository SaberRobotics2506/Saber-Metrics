package com.ibxcodecat.frc_scouting.Classes;

/// This class is for validating data that could be inacurate due to human error
public class DataValidator {

    final int NAME_CHAR_LIMIT = 50;
    final int COMMENTS_CHAR_LIMIT = 500;
  
    final int MAX_SCORE = 200;

    //Checks data in DataEntryActivity, returns -1 for pass, and a positive int for the problematic field
    public enum DataError {MatchNumberError, ScoutedByError, ScoreError, CommentsError, ClimbAttemptError, GameResultError, NameError, NONE }

    public DataError CheckData(String matchNumber, String scoutedBy, String score, String comments)
    {
        if(!ValidateMatchNumber(matchNumber))
        {
            return DataError.MatchNumberError;
        }
      
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

    //This is an actual programming problem if this ever gets thrown
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

    private boolean ValidateScoutedBy(String scoutedBy)
    {
        return scoutedBy.length() < NAME_CHAR_LIMIT && scoutedBy.length() > 0;
    }

    private boolean ValidateComments(String comments)
    {
        return comments.length() < COMMENTS_CHAR_LIMIT && comments.length() > 0;
    }
}
