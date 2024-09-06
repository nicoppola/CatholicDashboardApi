package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.novus.data.EasterDates
import com.catholicdashboard.catholicdashboard.util.getOrdinal
import java.io.IOException
import java.time.DayOfWeek

fun CalendarData.addOrdinaryTime(): CalendarData {

    val baptism = this.getBaptismOfLord()
    val easterDay = EasterDates.get(this.year) ?: throw IOException("Must have an easter date")
    val ashWednesday = easterDay.minusDays(46)
    val pentecostSunday = easterDay.plusWeeks(7)
    val firstDayAdvent = this.getFirstSundayOfAdvent()

    var currDay = baptism.plusDays(1)
    var weekCount = 1

    while (currDay != ashWednesday){
        val seasonString = if (currDay.dayOfWeek == DayOfWeek.SUNDAY){
            weekCount ++
            "${weekCount.getOrdinal()} Sunday in Ordinary Time"
        } else {
            "${weekCount.getOrdinal()} Week in Ordinary Time"
        }
        this.setSeasonAndColor(currDay, seasonString, CalendarData.Color.GREEN)
        currDay = currDay.plusDays(1)
    }

    currDay = pentecostSunday.plusDays(1)
    weekCount ++

    while (currDay != firstDayAdvent){
        val seasonString = if (currDay.dayOfWeek == DayOfWeek.SUNDAY){
            weekCount ++
            "${weekCount.getOrdinal()} Sunday of Ordinary Time"
        } else {
            "${weekCount.getOrdinal()} Week of Ordinary Time"
        }
        this.setSeasonAndColor(currDay, seasonString, CalendarData.Color.GREEN)
        currDay = currDay.plusDays(1)
    }

    return this
}

