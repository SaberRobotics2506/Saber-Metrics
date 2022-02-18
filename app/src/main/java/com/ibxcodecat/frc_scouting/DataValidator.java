package com.ibxcodecat.frc_scouting;

public class DataValidator {

    final int TOTAL_MATCHES = 95;
    final int SHORT_STRING_LEN = 50;

    int[] validTeams = { 93, 167, 876, 1675, 1732, 2052, 2175, 2227, 2239, 2264, 2501, 2503, 2506, 2526, 2531, 2861, 2977, 3058, 3102, 3122, 3134, 3197, 3275, 3276, 3291, 3294, 3740, 4009, 4181, 4230, 4238, 4511, 4607, 4656, 4674, 4728, 4741, 5125, 5143, 5253, 5348, 5464, 5653, 5690, 5826, 5847, 6047, 6146, 6160, 6217, 6318, 6574, 7048, 7068, 7311, 7797, 7893, 8836 };

    //Checks data in DataEntryActivity, returns -1 for pass, and a positive int for the problematic field
    public enum DataError { TeamNumberError, MatchNumberError, ScoutedByErrror, NONE };
    private static DataError dataError;

    public DataError CheckData(String teamNumber, String matchNumber, String scoutedBy)
    {
        if(!ValidateTeamNumber(teamNumber))
        {
            return dataError.TeamNumberError;
        }

        if(!ValidateMatchNumber(matchNumber))
        {
            return dataError.MatchNumberError;
        }

        if(!ValidateScoutedBy(scoutedBy))
        {
            return dataError.ScoutedByErrror;
        }

        return dataError.NONE;
    }

    private boolean ValidateTeamNumber(String teamNumber) {
        for(int i : validTeams)
        {
            int team = -1;

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

    private boolean ValidateMatchNumber(String matchNumber)
    {
        int match = -1;

        try
        {
            match = Integer.parseInt(matchNumber);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }

        if(match < TOTAL_MATCHES)
        {
            return true;
        }

        return false;
    }

    private boolean ValidateScoutedBy(String scoutedBy)
    {
        if(scoutedBy.length() < SHORT_STRING_LEN && scoutedBy.length() > 0)
        {
            return true;
        }

        return false;
    }
}
