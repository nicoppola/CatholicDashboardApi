package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.novus.data.EasterDates
import com.catholicdashboard.catholicdashboard.util.getOrdinal
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.io.IOException
import java.time.DayOfWeek

fun CalendarData.addEaster(): CalendarData {
    val easterDay = EasterDates.get(this.year) ?: throw IOException("Must have an easter date")
    val divineMercySunday = easterDay.plusWeeks(1)
    val pentecostSunday = easterDay.plusWeeks(7)

    this.setSeasonAndColor(
        easterDay,
        "Easter Sunday of the Resurrection of the Lord",
        CalendarData.Color.WHITE
    )

    // octave
    for (day in easterDay.plusDays(1).rangeTo(divineMercySunday.minusDays(1))) {
        this.setSeasonAndColor(
            day,
            "${day.dayOfWeek.name.toFirstLetterCapital()} within the Octave of Easter",
            CalendarData.Color.WHITE
        )
    }

    this.setSeasonAndColor(
        divineMercySunday,
        "Divine Mercy Sunday, Second Sunday of Easter",
        CalendarData.Color.WHITE
    )

    var currDay = divineMercySunday.plusDays(1)
    var weekCount = 2
    while (currDay != pentecostSunday){
        val seasonString = if(currDay.dayOfWeek == DayOfWeek.SUNDAY){
            weekCount ++
            "${currDay.dayOfWeek.value.getOrdinal()} Sunday of Easter"
        } else {
            "${currDay.dayOfWeek.value.getOrdinal()} Week of Easter"
        }
        val color = CalendarData.Color.PURPLE

        this.setSeasonAndColor(currDay, seasonString, color)
        currDay = currDay.plusDays(1)
    }

    this.setSeasonAndColor(
        pentecostSunday,
        "Pentecost Sunday",
        CalendarData.Color.WHITE
    )

    // todo Most Holy Trinity?
    this.setSeasonAndColor(
        pentecostSunday.plusWeeks(1),
        "The Most Holy Trinity",
        CalendarData.Color.WHITE
    )

    //todo corpus cristi

    return this
}