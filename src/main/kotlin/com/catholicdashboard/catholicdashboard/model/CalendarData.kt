package com.catholicdashboard.catholicdashboard.model

data class CalendarData(
    val year: String,
    val months: Map<Int, Map<Int, Day>>,
){
    data class Day(
        val date: String,
        val season: String,
        val color: String,
        val saints: List<Saint>,
    )

    data class Saint(
        val name: String,
        val title: String,
    )
}