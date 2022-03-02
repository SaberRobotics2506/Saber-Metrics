package com.ibxcodecat.frc_scouting

data class SerializationData (
    val team: Int = -1,
    val match: Int = -1,

    val scouter: String = "Empty Field",
    val regional: String = "Empty Field",

    val taxi: Boolean = false,

    val allianceScore: Int = -1,

    val comments: String = "Empty Field",

    val autoLowGoalSuccess: Int = -1,
    val autoLowGoalFail: Int = -1,
    val autoHighGoalSuccess: Int = -1,
    val autoHighGoalFail: Int = -1,

    val teleopLowGoalSuccess: Int = -1,
    val teleopLowGoalFail: Int = -1,
    val teleopHighGoalSuccess: Int = -1,
    val teleopHighGoalFail: Int = -1,

    /*

    Not Yet Implemented

    val defensePlays: Int = -1,
    val endGameStatus: Int = -1,
    val winLossTie: Char,
     */
)
