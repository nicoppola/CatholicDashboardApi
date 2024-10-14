package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UsccbParser(date: LocalDate) {

    private val dateFormatted: String = date.format(DateTimeFormatter.ofPattern("MMddyy"))
    private val url = "https://bible.usccb.org/bible/readings/$dateFormatted.cfm"
    private val doc = Jsoup.connect(url).get()

    fun getTitle(): String {
        return doc
            .select("meta")
            .find { it.attr("property") == "og:title" }?.attr("content")
            ?: throw Exception("**********No Title Available!!!!**********")
    }

    fun getReadings(): CalendarData.Readings {
        var readings = CalendarData.Readings(link = url)

        doc.select("h3").forEach {
            val verse = it.siblingElements().first()?.children()?.first()?.text()
                ?: throw Exception("**********No Verse Available!!!!**********")

            val title = it.text().trim()
            readings = when (title) {
                "Reading 1", "Reading I" -> readings.copy(readingOne = verse)
                "Reading 2", "Reading II" -> readings.copy(readingOne = verse)
                "Responsorial Psalm" -> readings.copy(psalm = verse)
                "Gospel" -> readings.copy(gospel = verse)
                else -> readings
            }
        }
        return readings
    }
}