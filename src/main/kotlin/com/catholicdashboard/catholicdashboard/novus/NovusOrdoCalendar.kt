package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.model.FileReader
import com.catholicdashboard.catholicdashboard.model.ProperOfSaints
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
                .addUsaHolyDaysOfObligation()
                .addSeasonalData()
                .addOrdinaryTime()

        return calendarData
    }

    private fun CalendarData.addGeneralProperSaints(): CalendarData {
        val saints = FileReader.getFile<ProperOfSaints>("novus/ProperOfSaintsGeneral.json")
        saints?.map?.forEach { (month, day) ->
            day.forEach { (day, saints) ->
                if (day != "0") {
                    this.months[month.toInt()]?.get(day.toInt())?.saints?.addAll(saints)
                } else {
                    //figure out movable feasts later
                }
            }
        }
        return this
    }

    private fun CalendarData.addUsaProperSaints(): CalendarData {
        val saints = FileReader.getFile<ProperOfSaints>("novus/ProperOfSaintsUsa.json")
        saints?.map?.forEach { (month, day) ->
            day.forEach { (day, saints) ->
                if (day != "0") {
                    this.months[month.toInt()]?.get(day.toInt())?.saints?.addAll(saints)
                } else {
                    //figure out movable feasts later
                }
            }
        }
        return this
    }

    private fun CalendarData.addUsaHolyDaysOfObligation(): CalendarData {
        val saints = FileReader.getFile<ProperOfSaints>("novus/HolyDaysOfObligationUsa.json")
        saints?.map?.forEach { (month, day) ->
            day.forEach { (day, saints) ->
                if (day != "0") {
                    this.months[month.toInt()]?.get(day.toInt())?.saints?.addAll(saints)
                } else {
                    //figure out movable feasts later
                }
            }
        }
        return this
    }

    private fun CalendarData.addSeasonalData(): CalendarData {

        this.addAdvent()
        this.addChristmas()
        this.addLent()
        this.addEaster()
        //Ordinary


        return this
    }

    private fun getEmptyCalendar(year: Int): CalendarData {
        return CalendarData(
            year = year.toString(),
            months = getMonths(year)
        )
    }

    private fun getMonths(year: Int): MutableMap<Int, MutableMap<Int, CalendarData.Day>> {
        val retMap = mutableMapOf<Int, MutableMap<Int, CalendarData.Day>>()
        for (month in 1..12) {
            val daysInMonth = YearMonth.of(year, month).lengthOfMonth()
            val daysMap = mutableMapOf<Int, CalendarData.Day>()
            for (day in 1..daysInMonth) {
                val date = LocalDate.of(year, month, day)
                daysMap[day] = CalendarData.Day(
                    date = date.format(DateTimeFormatter.ofPattern("MMMM d, u")),
                    season = "",
                    color = "",
                    saints = mutableListOf(),
                )
            }
            retMap[month] = daysMap
        }
        return retMap
    }

}