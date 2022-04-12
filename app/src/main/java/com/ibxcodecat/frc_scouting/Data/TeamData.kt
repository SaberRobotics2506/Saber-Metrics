package com.ibxcodecat.frc_scouting.Data

//Data object where team data is referenced and read to
data class TeamData ( val redTeamNumbers: IntArray, val blueTeamNumbers: IntArray )
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TeamData

        if (!redTeamNumbers.contentEquals(other.redTeamNumbers)) return false
        if (!blueTeamNumbers.contentEquals(other.blueTeamNumbers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = redTeamNumbers.contentHashCode()
        result = 31 * result + blueTeamNumbers.contentHashCode()
        return result
    }

}
