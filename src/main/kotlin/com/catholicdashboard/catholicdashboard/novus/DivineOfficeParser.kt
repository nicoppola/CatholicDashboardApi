package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DivineOfficeParser(date: LocalDate) {
    private val dateFormatted = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    private val url = "https://divineoffice.org/?date=$dateFormatted"
    private val doc = Jsoup.connect(url).get()

    fun getOffice(): CalendarData.Office {

        return CalendarData.Office(
            link = url,
            morning = getLink("nav-morningprayer"),
            midMorning = getLink("nav-midmorningprayer"),
            midday = getLink("nav-daytimeprayer"),
            midAfternoon = getLink("nav-middaytimeprayer"),
            evening = getLink("nav-eveningprayer"),
            night = getLink("nav-nightprayer"),
            officeOfReadings = getLink("nav-officeofreadings"),
        )
    }

    private fun getLink(id: String): String? {
        return doc.getElementById(id)?.select("a")?.attr("href")
    }
}