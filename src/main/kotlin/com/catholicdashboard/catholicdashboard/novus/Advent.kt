package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.util.getOrdinal
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
    val firstSundayOfAdvent = this.getFirstSundayOfAdvent()
    val christmasDay = LocalDate.of(this.year.toInt(), Month.DECEMBER, 25)

    var currDay = firstSundayOfAdvent
    var weekCount = 0
    while (currDay != christmasDay){
        var seasonString = if(currDay.dayOfWeek == DayOfWeek.SUNDAY){
            weekCount ++
            "${weekCount.getOrdinal()} Sunday of Advent"
        } else {
            "${weekCount.getOrdinal()} Week of Advent"
        }
        var color = CalendarData.Color.PURPLE

        //Gaudete Sunday
        if(currDay.dayOfWeek == DayOfWeek.SUNDAY && weekCount == 3){
            seasonString = "Gaudete Sunday, Third Sunday of Advent"
            color = CalendarData.Color.ROSE
        }

        this.setSeasonAndColor(currDay, seasonString, color)
        currDay = currDay.plusDays(1)
    }

    return this
}


// The Sunday closest to the Feast of St. Andrew the Apostle on November 30, whether before or after
// It is possible to compute the date of Advent Sunday by adding three days to the date of the last Thursday of November
fun CalendarData.getFirstSundayOfAdvent(): LocalDate {
    val lastDayOfMonth = YearMonth.of(this.year.toInt(), Month.NOVEMBER).lengthOfMonth()
    var lastThursOfNovember = LocalDate.of(this.year.toInt(), Month.NOVEMBER, lastDayOfMonth)
    while (lastThursOfNovember.dayOfWeek != DayOfWeek.THURSDAY) {
        lastThursOfNovember = lastThursOfNovember.minusDays(1)
    }
    return lastThursOfNovember.plusDays(3)
}