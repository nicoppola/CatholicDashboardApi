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

    fun getMemorials(): List<CalendarData.Proper> {
        return doc
            .select("ul.nested").select("li").map {
                CalendarData.Proper(
                    key = null,
                    rank = it.text().getRank(),
                    title = it.text().removeMemorialPrefixes(),
                )
            }
    }

    private fun String.getRank(): CalendarData.Rank {
        val str = this.removePrefix("Readings for the ")
        return when{
            str.startsWith("Memorial") -> CalendarData.Rank.MEMORIAL
            str.startsWith("Optional Memorial") -> CalendarData.Rank.OPTIONAL_MEMORIAL
            else -> CalendarData.Rank.UNKNOWN
        }
    }

    private fun String.removeMemorialPrefixes(): String {
        return this
            .removePrefix("Readings for the ")
            .removePrefix("Optional Memorial ")
            .removePrefix("Memorial ")
            .removePrefix("of ")
            .removePrefix("the ")
    }

    fun getReadings(): CalendarData.Readings {
        var readings = CalendarData.Readings(link = url)

        doc.select("h3").forEach {
            val verse = it.siblingElements().first()?.children()?.first()?.text()

            val title = it.text().trim()
            readings = when (title) {
                "Reading 1", "Reading I" -> readings.copy(
                    readingOne = verse
                        ?: throw Exception("**********No Verse Available Reading 1**********")
                )

                "Reading 2", "Reading II" -> readings.copy(
                    readingTwo = verse
                        ?: throw Exception("**********No Verse Available Reading 2**********")
                )

                "Responsorial Psalm" -> readings.copy(
                    psalm = verse ?: throw Exception("**********No Verse Available Psalm**********")
                )

                "Gospel" -> readings.copy(
                    gospel = verse
                        ?: throw Exception("**********No Verse Available Gospel**********")
                )

                else -> readings
            }
        }
        return readings
    }
}