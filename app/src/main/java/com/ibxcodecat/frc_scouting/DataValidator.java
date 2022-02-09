package com.ibxcodecat.frc_scouting;

public class DataValidator {

    int[] validTeams = { 93, 167, 876, 1675, 1732, 2052, 2175, 2227, 2239, 2264, 2501, 2503, 2506, 2526, 2531, 2861, 2977, 3058, 3102, 3122, 3134, 3197, 3275, 3276, 3291, 3294, 3740, 4009, 4181, 4230, 4238, 4511, 4607, 4656, 4674, 4728, 4741, 5125, 5143, 5253, 5348, 5464, 5653, 5690, 5826, 5847, 6047, 6146, 6160, 6217, 6318, 6574, 7048, 7068, 7311, 7797, 7893, 8836 };

    public boolean ValidateTeamNumber(int teamNumber) {
        for(int i : validTeams)
        {
            if(i == teamNumber)
            {
                return true;
            }
        }

        return false;
    }
}
