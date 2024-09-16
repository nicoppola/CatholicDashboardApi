package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.util.getOrdinal
import com.catholicdashboard.catholicdashboard.util.rangeTo
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

fun CalendarData.addChristmas(): CalendarData {
    val christmasDay = LocalDate.of(this.year.toInt(), Month.DECEMBER, 25)
    val christmasAfterOctave = LocalDate.of(this.year.toInt(), Month.JANUARY, 2)
    val epiphany = this.getEpiphany()
    // Baptism of the Lord

    this.setSolemnity(christmasDay, "Christmas Day", "christmas")

    //octave
    var currDay = christmasDay.plusDays(1)
    var dayCount = 2

    while (currDay.year.toString() == this.year){
        this.setSeasonAndColor(
            currDay,
            "${dayCount.getOrdinal()} day of the Christmas Octave",
            CalendarData.Color.WHITE
        )
        dayCount++
        currDay = currDay.plusDays(1)
    }

    this.setSeasonAndColor(
        christmasAfterOctave.minusDays(1),
        "${dayCount.getOrdinal()} day of the Christmas Octave",
        CalendarData.Color.WHITE
    )

    //season
    for (day in christmasAfterOctave.rangeTo(epiphany.minusDays(1))) {
        this.setSeasonAndColor(
            day,
            "${day.dayOfWeek.name.toFirstLetterCapital()} after Christmas Octave",
            CalendarData.Color.WHITE
        )
    }

    return this
}

// The date for this Feast was set on the Sunday after the Epiphany.
// When the Feast of the Epiphany is not celebrated on 6 January,
// it is celebrated on the Sunday between 2 and 8 January,
// and the Feast of the Baptism of the Lord is celebrated on
// the Monday following the Epiphany
fun CalendarData.getBaptismOfLord(): LocalDate {
    // figure out not usa
    return this.getEpiphanySunday().plusDays(1)
}

// The Epiphany of the Lord is celebrated on 6 January,
// unless, where it is not observed as a holy day of obligation,
// it has been assigned to the Sunday occurring between 2 and 8 January
fun CalendarData.getEpiphany(): LocalDate {
    // if not USA, return Jan6
    return this.getEpiphanySunday()
}

fun CalendarData.getEpiphanySunday(): LocalDate {
    //"Sunday between Jan 2 and 8"
    for (day in LocalDate.of(this.year.toInt(), Month.JANUARY, 2)
        .rangeTo(LocalDate.of(this.year.toInt(), Month.JANUARY, 8))) {
        if (day.dayOfWeek == DayOfWeek.SUNDAY) {
            return day
        }
    }
    throw Exception("Couldn't find Epiphany Sunday")
}