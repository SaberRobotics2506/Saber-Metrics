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

    val DefensePlays: Int = -1,
    val ClimbResult: Int = -1,
    val WinLossTie: Int = -1,
    val TraversalTime: Int = -1,
)
