package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.novus.data.EasterDates
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.io.IOException

fun CalendarData.addLent(): CalendarData {
    val easterDay = EasterDates.get(this.year) ?: throw IOException("Must have an easter date")
    val ashWednesday = easterDay.minusDays(46)
    val firstSundayLent = ashWednesday.plusDays(4)
    val secondSundayLent = firstSundayLent.plusWeeks(1)
    val thirdSundayLent = secondSundayLent.plusWeeks(1)
    val fourthSundayLent = thirdSundayLent.plusWeeks(1)
    val fifthSundayLent = fourthSundayLent.plusWeeks(1)
    val palmSunday = fifthSundayLent.plusWeeks(1)

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

    this.setSeasonAndColor(
        firstSundayLent,
        "First Sunday of Lent",
        CalendarData.Color.PURPLE
    )
    for (day in firstSundayLent.plusDays(1).rangeTo(secondSundayLent.minusDays(1))) {
        this.setSeasonAndColor(day, "First Week of Lent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        secondSundayLent,
        "Second Sunday of Lent",
        CalendarData.Color.PURPLE
    )
    for (day in secondSundayLent.plusDays(1).rangeTo(thirdSundayLent.minusDays(1))) {
        this.setSeasonAndColor(day, "Second Week of Lent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        thirdSundayLent,
        "Third Sunday of Lent",
        CalendarData.Color.PURPLE
    )
    for (day in thirdSundayLent.plusDays(1).rangeTo(fourthSundayLent.minusDays(1))) {
        this.setSeasonAndColor(day, "Third Week of Lent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        fourthSundayLent,
        "Laetare Sunday, Fourth Sunday of Lent",
        CalendarData.Color.ROSE
    )
    for (day in fourthSundayLent.plusDays(1).rangeTo(fifthSundayLent.minusDays(1))) {
        this.setSeasonAndColor(day, "Fourth Week of Lent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        fifthSundayLent,
        "Fifth Sunday of Lent",
        CalendarData.Color.PURPLE
    )
    for (day in fifthSundayLent.plusDays(1).rangeTo(fourthSundayLent.minusDays(1))) {
        this.setSeasonAndColor(day, "Fourth Week of Lent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        palmSunday,
        "Palm Sunday",
        CalendarData.Color.RED,
    )
    for (day in palmSunday.plusDays(1).rangeTo(palmSunday.plusDays(3))) {
        this.setSeasonAndColor(
            day,
            "${day.dayOfWeek.name.toFirstLetterCapital()} of Holy Week",
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