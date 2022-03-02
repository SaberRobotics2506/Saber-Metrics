package com.ibxcodecat.frc_scouting

data class SerializationData (
    val TeamNumber: Int = -1,
    val MatchNumber: Int = -1,

    val ScoutedBy: String = "Empty Field",
    val Regional: String = "Empty Field",

    val Taxi: Boolean = false,

    val AllianceScore: Int = -1,

    val Comments: String = "Empty Field",

    val AutoLowGoalMake: Int = -1,
    val AutoLowGoalMiss: Int = -1,
    val AutoHighGoalMake: Int = -1,
    val AutoHighGoalMiss: Int = -1,

    val TelopLowGoalMake: Int = -1,
    val TelopLowGoalMiss: Int = -1,
    val TelopHighGoalMake: Int = -1,
    val TelopHighGoalMiss: Int = -1,

    /*

    Not Yet Implemented

    val defensePlays: Int = -1,
    val endGameStatus: Int = -1,
    val winLossTie: Char,
     */
)
