package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import java.time.LocalDate
import java.util.Locale

fun CalendarData.setSeasonAndColor(
    localDate: LocalDate,
    season: String,
    color: CalendarData.Color
) {
    this.months[localDate.month.value]?.get(localDate.dayOfMonth)?.season = season
    this.months[localDate.month.value]?.get(localDate.dayOfMonth)?.color = color.name
}

fun String.toFirstLetterCapital(): String{
    return this.lowercase(Locale.getDefault()).replaceFirstChar { c -> c.uppercaseChar() }
}