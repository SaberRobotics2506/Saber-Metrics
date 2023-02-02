package com.ibxcodecat.frc_scouting.Data

data class SerializationData (
    val TeamNumber: Int = -1,
    val MatchNumber: Int = -1,
    val ScoutedBy: String = "Empty Field",
    val Comments: String = "Empty Field",
    val GameResult: Int = -1,
    val AllianceScore: Int = -1,
    val IsParked: Boolean = false,
    val DidMobility: Boolean = false,
    val AutoHighStep: Int = -1,
    val AutoMidStep: Int = -1,
    val AutoLowStep: Int = -1,
    val AutoBalancePlatforms: Int = -1,
    val NumCubes: Int = -1,
    val NumCones: Int = -1,
    val TeleBalancePlatforms: Int = -1,
    val DefensePlays: Int = -1,
)
