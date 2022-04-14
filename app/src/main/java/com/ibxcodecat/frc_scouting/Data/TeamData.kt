package com.ibxcodecat.frc_scouting.Data

import java.util.*

//Data object where team data is referenced and read to
data class TeamData ( val redTeamNumbers: Object, val blueTeamNumbers: Object )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamData

        if (redTeamNumbers != other.redTeamNumbers) return false
        if (blueTeamNumbers != other.blueTeamNumbers) return false

        return true
    }
}
