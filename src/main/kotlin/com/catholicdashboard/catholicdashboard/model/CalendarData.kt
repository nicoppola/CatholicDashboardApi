package com.catholicdashboard.catholicdashboard.model

import java.io.Serializable

data class CalendarData(
    val year: String,
    val months: MutableMap<Int, MutableMap<Int, Day>>,
): Serializable{
    data class Day(
        val date: String,
        var season: String,
        var color: String,
        val saints: MutableList<Saint>,
    ): Serializable

    data class Saint(
        val key: String?,
        val rank: String?,
        val title: String?,
        val dateNote: String?,
        val note: String?,
    ): Serializable

    enum class Color(value: String){
        GREEN("green"),
        PURPLE("purple"),
        ROSE("rose"),
        WHITE("white"),
        RED("red"),
    }
}