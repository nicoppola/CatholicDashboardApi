package com.catholicdashboard.catholicdashboard

import com.catholicdashboard.catholicdashboard.model.CalendarData
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class NovusOrdoCalendar() {

    fun generateCalendar(year: Int): CalendarData {
        // check cache; if no calendar, generate

        val calendarData =
            getEmptyCalendar(year)
                .addGeneralProperSaints()
                .addUsaProperSaints()
                .addSeasonalData()

        return calendarData
    }

    private fun CalendarData.addGeneralProperSaints(): CalendarData {
        return this
    }

    private fun CalendarData.addUsaProperSaints(): CalendarData {
        return this
    }

    private fun CalendarData.addSeasonalData(): CalendarData {
        return this
    }

    private fun getEmptyCalendar(year: Int): CalendarData {
        return CalendarData(
            year = year.toString(),
            months = getMonths(year)
        )
    }

    private fun getMonths(year: Int): Map<Int, Map<Int, CalendarData.Day>> {
        val retMap = mutableMapOf<Int, Map<Int, CalendarData.Day>>()
        for (month in 1..12) {
            val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
            val daysMap = mutableMapOf<Int, CalendarData.Day>()
            for (day in 1..daysInMonth) {
                val date = LocalDate.of(year, month, day)
                daysMap[day] = CalendarData.Day(
                    date = date.format(DateTimeFormatter.ofPattern("MMMM d, u")),
                    season = "",
                    color = "",
                    saints = emptyList(),
                )
            }
            retMap[month] = daysMap.toMap()
        }
        return retMap.toMap()
    }

}