package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

fun CalendarData.addAdvent(): CalendarData {
    //Advent
    //purple
    // The Sunday closest to the Feast of St. Andrew the Apostle on November 30, whether before or after
    //https://hallow.com/blog/the-schedule-of-advent-when-does-advent-start/#:~:text=Hallow%20This%20Advent-,How%20are%20the%20dates%20for%20Advent%20decided%3F,30%2C%20whether%20before%20or%20after.
    // 3rd Sunday == Gaudete Sunday -> Rose
    val firstSundayOfAdvent = getFirstSundayOfAdvent(this.year.toInt())
    val secondSundayOfAdvent = firstSundayOfAdvent.plusWeeks(1)
    val thirdSunayOfAdvent = secondSundayOfAdvent.plusWeeks(1)
    val fourthSundayOfAdvent = thirdSunayOfAdvent.plusWeeks(1)
    val christmasDay = LocalDate.of(this.year.toInt(), Month.DECEMBER, 25)

    this.setSeasonAndColor(
        firstSundayOfAdvent,
        "First Sunday of Advent",
        CalendarData.Color.PURPLE
    )
    for (day in firstSundayOfAdvent.plusDays(1).rangeTo(secondSundayOfAdvent.minusDays(1))) {
        this.setSeasonAndColor(day, "First Week of Advent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        secondSundayOfAdvent,
        "Second Sunday of Advent",
        CalendarData.Color.PURPLE
    )
    for (day in secondSundayOfAdvent.plusDays(1).rangeTo(thirdSunayOfAdvent.minusDays(1))) {
        this.setSeasonAndColor(day, "Second Week of Advent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        thirdSunayOfAdvent,
        "Gaudete Sunday, Third Sunday of Advent",
        CalendarData.Color.ROSE
    )
    for (day in thirdSunayOfAdvent.plusDays(1).rangeTo(fourthSundayOfAdvent.minusDays(1))) {
        this.setSeasonAndColor(day, "Third Week of Advent", CalendarData.Color.PURPLE)
    }

    this.setSeasonAndColor(
        fourthSundayOfAdvent,
        "Fourth Sunday of Advent",
        CalendarData.Color.PURPLE
    )
    for (day in fourthSundayOfAdvent.plusDays(1).rangeTo(christmasDay.minusDays(1))) {
        this.setSeasonAndColor(day, "Fourth Week of Advent", CalendarData.Color.PURPLE)
    }

    return this
}

// The Sunday closest to the Feast of St. Andrew the Apostle on November 30, whether before or after
// It is possible to compute the date of Advent Sunday by adding three days to the date of the last Thursday of November
private fun getFirstSundayOfAdvent(year: Int): LocalDate {
    val lastDayOfMonth = YearMonth.of(year, Month.NOVEMBER).lengthOfMonth()
    var lastThursOfNovember = LocalDate.of(year, Month.NOVEMBER, lastDayOfMonth)
    while (lastThursOfNovember.dayOfWeek != DayOfWeek.THURSDAY) {
        lastThursOfNovember = lastThursOfNovember.minusDays(1)
    }
    return lastThursOfNovember.plusDays(3)
}