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

fun CalendarData.setSolemnity(
    localDate: LocalDate,
    season: String,
    key: String,
) {
    this.setSeasonAndColor(localDate, season, CalendarData.Color.WHITE)
    this.addSanctorale(
        localDate,
        listOf(CalendarData.Proper(key = key, rank = "solemnity", title = season))
    )
}

fun CalendarData.addHolyDayOfObligation(
    day: LocalDate,
    key: String,
) {
    val proper =
        this.months[day.month.value]?.get(day.dayOfMonth)?.proper?.find { it.key == key }
    this.months[day.month.value]?.get(day.dayOfMonth)?.proper?.remove(proper)
    if (proper == null) {
        throw Exception("Could not find holy day of obligation $key")
    }
    this.months[day.month.value]?.get(day.dayOfMonth)?.proper?.add(proper.copy(key = "holyDayObligation"))
}

fun CalendarData.addSanctorale(
    day: Int,
    month: Int,
    saints: List<CalendarData.Proper>
) {
    this.months[month]?.get(day)?.proper?.addAll(saints)
}

fun CalendarData.addSanctorale(day: LocalDate, saints: List<CalendarData.Proper>) {
    this.months[day.month.value]?.get(day.dayOfMonth)?.proper?.addAll(saints)
}

fun LocalDate.getFormattedDayOfWeek(): String {
    return this.dayOfWeek.name.toFirstLetterCapital()
}

fun String.toFirstLetterCapital(): String {
    return this.lowercase(Locale.getDefault()).replaceFirstChar { c -> c.uppercaseChar() }
}