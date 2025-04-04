package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UsccbParser(date: LocalDate) {

    private val dateFormatted: String = date.format(DateTimeFormatter.ofPattern("MMddyy"))
    private val url = "https://bible.usccb.org/bible/readings/$dateFormatted.cfm"
    private val baseDoc = Jsoup.connect(url).get()

    fun getTitle(doc: Document = baseDoc): String {
        return doc
            .select("meta")
            .find { it.attr("property") == "og:title" }?.attr("content")
            ?: throw Exception("**********No Title Available!!!!**********")
    }

    fun getMemorials(doc: Document = baseDoc): List<CalendarData.Proper> {
        return doc
            .select("ul.nested").select("li").mapNotNull {
                it.text().getRank()?.let { rank ->
                    CalendarData.Proper(
                        key = null,
                        rank = rank,
                        title = it.text().removeMemorialPrefixes(),
                    )
                }

            }
    }

    private fun String.getRank(): CalendarData.Rank? {
        val str = this.removePrefix("Readings for the ")
        return when {
            str.startsWith("Memorial") -> CalendarData.Rank.MEMORIAL
            str.startsWith("Optional Memorial") -> CalendarData.Rank.OPTIONAL_MEMORIAL
            else -> null
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

    fun getReadings(): List<CalendarData.Readings> {
        val readingsList = mutableListOf<CalendarData.Readings>()

        val oneReading = getOneReading(baseDoc)
        if (oneReading != null) {
            readingsList.add(oneReading)
        } else {
            val test = baseDoc.select("p").select("a").forEach { aTag ->
                val href = aTag.attr("href")
                if (href.contains("$dateFormatted-")) {
                    val newReadings = getOneReading(Jsoup.connect(href).get(), aTag.text())
                    newReadings?.let {
                        readingsList.add(it)
                    }
                }
            }
            println(test)
        }

        return readingsList
    }

    private fun getOneReading(doc: Document, title: String? = null): CalendarData.Readings? {
        var readings = CalendarData.Readings(
            link = doc.location(),
            title = title ?: getTitle(doc)
        )

        doc.select("h3").forEach {
            val verse = it.siblingElements().first()?.children()?.first()?.text()

            val readingTitle = it.text().trim()
            readings = when (readingTitle) {
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

        return if (readings.readingOne == "" &&
            readings.readingTwo == null &&
            readings.psalm == "" &&
            readings.gospel == ""
        ) {
            null
        } else {
            return readings
        }
    }
}