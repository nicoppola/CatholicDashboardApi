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
        val readings: Readings,
        val office: Office,
        val proper: MutableList<Proper>,
    ): Serializable

    data class Office(
        val link: String,
        val morning: String,
        val midMorning: String,
        val midday: String,
        val midAfternoon: String,
        val evening: String,
        val night: String,
    )

    data class Readings(
        val link: String,
//        val readingOne: String,
//        val psalm: String,
//        val readingTwo: String?,
//        val gospel: String,
    ): Serializable

    data class Proper(
        val key: String?,
        val rank: String?,
        val title: String?,
    ): Serializable

    enum class Color(value: String){
        GREEN("green"),
        PURPLE("purple"),
        ROSE("rose"),
        WHITE("white"),
        RED("red"),
    }
}