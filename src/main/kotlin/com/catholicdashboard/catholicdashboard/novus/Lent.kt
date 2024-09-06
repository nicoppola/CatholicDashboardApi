package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.novus.data.EasterDates
import com.catholicdashboard.catholicdashboard.util.getOrdinal
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.io.IOException
import java.time.DayOfWeek

fun CalendarData.addLent(): CalendarData {
    val easterDay = EasterDates.get(this.year) ?: throw IOException("Must have an easter date")
    val ashWednesday = easterDay.minusDays(46)
    val palmSunday = easterDay.minusWeeks(1)

    this.setSeasonAndColor(ashWednesday, "Ash Wednesday", CalendarData.Color.WHITE)
    this.setSeasonAndColor(
        ashWednesday.plusDays(1),
        "Thursday after Ash Wednesday",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        ashWednesday.plusDays(2),
        "Friday after Ash Wednesday",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        ashWednesday.plusDays(3),
        "Saturday after Ash Wednesday",
        CalendarData.Color.WHITE
    )

    var currDay = ashWednesday.plusDays(4)
    var weekCount = 0
    while (currDay != palmSunday){
        var seasonString = if(currDay.dayOfWeek == DayOfWeek.SUNDAY){
            weekCount ++
            "${currDay.dayOfWeek.value.getOrdinal()} Sunday of Lent"
        } else {
            "${currDay.dayOfWeek.value.getOrdinal()} Week of Lent"
        }
        var color =  CalendarData.Color.PURPLE

        // Laetare Sunday
        if(currDay.dayOfWeek == DayOfWeek.SUNDAY && weekCount == 4){
            seasonString = "Laetare Sunday, Fourth Sunday of Advent"
            color = CalendarData.Color.ROSE
        }

        this.setSeasonAndColor(currDay, seasonString, color)
        currDay = currDay.plusDays(1)
    }

    this.setSeasonAndColor(
        palmSunday,
        "Palm Sunday",
        CalendarData.Color.RED,
    )
    for (day in palmSunday.plusDays(1).rangeTo(palmSunday.plusDays(3))) {
        this.setSeasonAndColor(
            day,
            "${day.getFormattedDayOfWeek()} of Holy Week",
            CalendarData.Color.PURPLE
        )
    }

    this.setSeasonAndColor(
        palmSunday.plusDays(4),
        "Holy Thursday",
        CalendarData.Color.WHITE,
    )

    this.setSeasonAndColor(
        palmSunday.plusDays(5),
        "Good Friday",
        CalendarData.Color.RED,
    )

    this.setSeasonAndColor(
        palmSunday.plusDays(6),
        "Holy Saturday",
        CalendarData.Color.WHITE,
    )

    return this
}