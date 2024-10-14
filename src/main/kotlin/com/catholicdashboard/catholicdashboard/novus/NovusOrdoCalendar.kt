package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.model.FileReader
import com.catholicdashboard.catholicdashboard.model.ProperOfSaints
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class NovusOrdoCalendar {

    private val calendar: MutableMap<Int, CalendarData?> = mutableMapOf()

    fun getDay(day: Int, month: Int, year: Int): CalendarData.Day {
        ensureCalendarExists(year)
        val data = calendar[year]?.months?.get(month)?.get(day)
            ?: throw Exception("CANNOT FIND DAY! day: $day month $month year $year")

        val localDate = LocalDate.of(year, month, day)

        val orderedData = setTitleAndColor(data, localDate.dayOfWeek)

        return if (orderedData.readings == null) {
            println("************** Getting Readings $localDate **************")
            val readings = updateCacheWithReadings(localDate)
            orderedData.copy(readings = readings)
        } else {
            println("************** Readings Exist $localDate **************")
            orderedData
        }
    }

    private fun setTitleAndColor(day: CalendarData.Day, dayOfWeek: DayOfWeek): CalendarData.Day {
        val solemnity = day.proper.filter { it.rank == CalendarData.Rank.SOLEMNITY }
        val feast = day.proper.filter { it.rank == CalendarData.Rank.FEAST }
        val memorial = day.proper.filter { it.rank == CalendarData.Rank.MEMORIAL }
        return if (solemnity.isNotEmpty()) {
            if (solemnity.size > 1) println("***** WHY ARE THERE MORE THAN ONE SOLEMNITY IN A DAY")
            day.copy(title = "The Solemnity of " + solemnity.first().title, color = solemnity.first().color)
        } else if (dayOfWeek != DayOfWeek.SUNDAY && feast.isNotEmpty()) {
            if (feast.size > 1) println("***** WHY ARE THERE MORE THAN ONE FEASTS IN A DAY")
            day.copy(title = "The Feast of " + feast.first().title, color = feast.first().color)
        } else if (dayOfWeek != DayOfWeek.SUNDAY && memorial.isNotEmpty()) {
            if (memorial.size > 1) println("***** WHY ARE THERE MORE THAN ONE MEMORIAL IN A DAY")
            day.copy(title = "The Memorial of " + memorial.first().title, color = memorial.first().color)
        } else {
            day
        }
    }

    private fun updateCacheWithReadings(date: LocalDate): CalendarData.Readings {
        val usccb = UsccbParser(date)
        val readings = usccb.getReadings()
        val title = usccb.getTitle()

        calendar[date.year]?.months?.get(date.month.value)?.get(date.dayOfMonth)?.readings =
            readings.copy(title = title)
        calendar[date.year]?.let {
            FileReader.writeToFile(fileName(date.year), it)
        }

        return readings.copy(title = title)
    }

    private fun fileName(year: Int): String {
        return "novus/calendar$year.json"
    }

    private fun ensureCalendarExists(year: Int) {
        if (!calendar.containsKey(year)) {
            val fileName = fileName(year)
            if (FileReader.doesFileExist(fileName)) {
                println("************** Calendar Exists **************")
                calendar[year] = FileReader.getFile<CalendarData>(fileName)
            } else {
                println("************** Generating Calendar **************")
                val calendarData = generateCalendar(year)

                calendar[year] = calendarData
                FileReader.writeToFile(fileName, calendarData)
            }
        }
    }

    fun getCalendar(year: Int): CalendarData {
        ensureCalendarExists(year)
        return calendar[year]
            ?: throw Exception("CANNOT FIND CALENDAR! year $year")
    }

    private fun generateCalendar(year: Int): CalendarData {
        // check cache; if no calendar, generate

        val calendarData =
            getEmptyCalendar(year)
                .addGeneralProperSaints()
                .addUsaProperSaints()
                .addUsaTemporale()
                .addSeasonalData()
                .addTemporale()
                .setUsaHolyDaysOfObligation()

        return calendarData
    }

    private fun CalendarData.addGeneralProperSaints(): CalendarData {

        val saints = FileReader.getFile<ProperOfSaints>("novus/ProperOfSaintsGeneral.json")
        saints?.map?.forEach { (month, day) ->
            day.forEach { (day, saints) ->
                this.addSanctorale(day.toInt(), month.toInt(), saints)
            }
        }

        return this
    }

    private fun CalendarData.addUsaProperSaints(): CalendarData {
        val saints = FileReader.getFile<ProperOfSaints>("novus/ProperOfSaintsUsa.json")
        saints?.map?.forEach { (month, day) ->
            day.forEach { (day, saints) ->
                // fixed days
                if (day != "0") {
                    this.addSanctorale(day.toInt(), month.toInt(), saints)
                }
                // movable
                else {
                    saints.forEach { saint ->
                        when (saint.key) {
                            "unborn" -> {
                                // Moved to 23rd if 22nd falls on a Sunday
                                val date =
                                    LocalDate.of(year.toInt(), Month.of(month.toInt()), day.toInt())
                                val calculatedDay = if (date.dayOfWeek == DayOfWeek.SUNDAY) {
                                    date.plusDays(1)
                                } else {
                                    date
                                }
                                this.addSanctorale(calculatedDay, listOf(saint))
                            }
                        }
                    }
                }
            }
        }
        return this
    }

    private fun CalendarData.addUsaTemporale(): CalendarData {
        // Moved to 23rd if 22nd falls on a Sunday
        var date = LocalDate.of(this.year.toInt(), Month.JANUARY, 23)
        if (date.dayOfWeek == DayOfWeek.SUNDAY) date = date.plusDays(1)
        val proper = CalendarData.Proper(
            key = "unborn",
            rank = CalendarData.Rank.MEMORIAL,
            title = "Day of Prayer for the Legal Protection of Unborn Children",
            color = CalendarData.Color.WHITE,
        )
        this.addSanctorale(date, listOf(proper))
        return this
    }

    private fun CalendarData.setUsaHolyDaysOfObligation(): CalendarData {
        val year = this.year.toInt()
        // transfer to Sunday
        this.addHolyDayOfObligation(this.getAscension(), "ascension")

        // start
        // if falls on Saturday or Monday, Precept to attend Mass if abrogated
        //mary_mother_god
        val maryMotherGod = LocalDate.of(year, Month.JANUARY, 1)
        if (maryMotherGod.dayOfWeek != DayOfWeek.SATURDAY && maryMotherGod.dayOfWeek != DayOfWeek.SUNDAY) {
            this.addHolyDayOfObligation(maryMotherGod, "mary_mother_god")
        }

        val assumption = LocalDate.of(year, Month.AUGUST, 15)
        if (assumption.dayOfWeek != DayOfWeek.SATURDAY && assumption.dayOfWeek != DayOfWeek.SUNDAY) {
            this.addHolyDayOfObligation(assumption, "assumption")
        }

        val allSaints = LocalDate.of(year, Month.NOVEMBER, 1)
        if (allSaints.dayOfWeek != DayOfWeek.SATURDAY && allSaints.dayOfWeek != DayOfWeek.SUNDAY) {
            this.addHolyDayOfObligation(allSaints, "all_saints")
        }
        // end

        val immaculateConception = LocalDate.of(year, Month.DECEMBER, 8)
        this.addHolyDayOfObligation(immaculateConception, "immaculate_conception")

        val christmas = LocalDate.of(year, Month.DECEMBER, 25)
        this.addHolyDayOfObligation(christmas, "christmas")

        return this
    }

    private fun CalendarData.addSeasonalData(): CalendarData {

        this.addAdvent()
        this.addChristmas()
        this.addLent()
        this.addEaster()
        this.addOrdinaryTime()

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
                    title = "",
                    color = CalendarData.Color.UNDEFINED,
                    readings = getReadings(date),
                    office = getOffice(date),
                    proper = mutableListOf(),
                )
            }
            retMap[month] = daysMap
        }
        return retMap
    }

    private fun getOffice(date: LocalDate): CalendarData.Office {
        val dateFormatted = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        return CalendarData.Office(
            link = "https://divineoffice.org/?date=$dateFormatted",
            morning = "https://divineoffice.org/0909-mp/?date=$dateFormatted",
            midMorning = "https://divineoffice.org/ord-w03-mon-dp1-comp/?date=$dateFormatted",
            midday = "https://divineoffice.org/ord-w03-mon-dp2-current/?date=$dateFormatted",
            midAfternoon = "https://divineoffice.org/ord-w03-mon-dp3-comp/?date=$dateFormatted",
            evening = "https://divineoffice.org/0909-ep2/?date=$dateFormatted",
            night = "https://divineoffice.org/ord-mon-np-w1-w3/?date=$dateFormatted",
        )
    }

    private fun getReadings(date: LocalDate): CalendarData.Readings? {
        //NOTE populated as we go from usccb html parser
        return null
//        val dateFormatted = date.format(DateTimeFormatter.ofPattern("MMddyy"))
//        return CalendarData.Readings(
//            link = "https://bible.usccb.org/bible/readings/$dateFormatted.cfm",
//            readingOne = "",
//            psalm = "",
//            readingTwo = "",
//            gospel = "",
//        )
    }
}