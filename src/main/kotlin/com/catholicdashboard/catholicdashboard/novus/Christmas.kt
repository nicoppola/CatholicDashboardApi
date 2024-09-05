package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.time.LocalDate
import java.time.Month

fun CalendarData.addChristmas(): CalendarData {
    val christmasDay = LocalDate.of(this.year.toInt(), Month.DECEMBER, 25)
    val christmasAfterOctave = LocalDate.of(this.year.toInt(), Month.JANUARY, 2)
    val lastDayOfChristmas =
        LocalDate.of(this.year.toInt(), Month.JANUARY, 7) // Baptism of the Lord

    this.setSeasonAndColor(christmasDay, "Christmas Day", CalendarData.Color.WHITE)

    //octave
    this.setSeasonAndColor(
        christmasDay.plusDays(1),
        "2nd day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(2),
        "3rd day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(3),
        "4th day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(4),
        "5th day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(5),
        "6th day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(6),
        "7th day of Christmas Octave",
        CalendarData.Color.WHITE
    )
    this.setSeasonAndColor(
        christmasDay.plusDays(7),
        "8th day of Christmas Octave",
        CalendarData.Color.WHITE
    )

    //season
    for (day in christmasAfterOctave.rangeTo(lastDayOfChristmas)) {
        this.setSeasonAndColor(
            day,
            "${day.dayOfWeek.name.toFirstLetterCapital()} after Christmas Octave)",
            CalendarData.Color.PURPLE
        )
    }

    return this
}