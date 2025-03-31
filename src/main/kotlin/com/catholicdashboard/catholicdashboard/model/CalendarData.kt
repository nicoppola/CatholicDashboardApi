package com.catholicdashboard.catholicdashboard.model

import java.io.Serializable

data class CalendarData(
    val year: String,
    val months: MutableMap<Int, MutableMap<Int, Day>>,
) : Serializable {
    data class Day(
        val date: String,
        var title: String,
        var color: Color,
        var readings: Readings?,
        var office: Office?,
        var propers: MutableList<Proper>,
    ) : Serializable

    data class Office(
        val link: String,
        val morning: String? = null,
        val midMorning: String? = null,
        val midday: String? = null,
        val midAfternoon: String? = null,
        val evening: String? = null,
        val night: String? = null,
        val officeOfReadings: String? = null,
        val officeOfReadingsSubtitle: String? = null,
    )

    data class Readings(
        val link: String = "",
        val readingOne: String = "",
        val psalm: String = "",
        val readingTwo: String? = null,
        val gospel: String = "",
        val title: String = "",
    ) : Serializable

    data class Proper(
        val key: String?,
        val rank: Rank = Rank.OPTIONAL_MEMORIAL,
        val title: String = "",
        val color: Color = Color.GREEN,
    ) : Serializable

    enum class Color(value: String) : Serializable {
        GREEN("green"),
        PURPLE("purple"),
        ROSE("rose"),
        WHITE("white"),
        RED("red"),
        UNDEFINED("undefined");
    }

    //rank order 0 is highest
    enum class Rank(value: String, rank: Int) {
        UNKNOWN("",0),
        SOLEMNITY("Solemnity", 1),
        SUNDAY("Sunday", 2),
        FEAST("Feast", 3),
        MEMORIAL("Memorial", 4),
        OPTIONAL_MEMORIAL("Optional Memorial", 5),
        FERIA("Feria", 6);
    }
}