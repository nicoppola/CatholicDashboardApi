package com.catholicdashboard.catholicdashboard.model

enum class SeasonColor(val value: String){
    GREEN("green"),
    RED("red"),
    PURPLE("purple"),
    PINK("pink"),
}

data class DayData(
    val date: String,
    val season: String,
    val color: String,
)
