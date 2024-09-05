package com.catholicdashboard.catholicdashboard.controller

import com.catholicdashboard.catholicdashboard.novus.NovusOrdoCalendar
import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.model.DayData
import com.catholicdashboard.catholicdashboard.model.SeasonColor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/today")
class MainController {

    @GetMapping
    fun getToday(): DayData {
        val data = DayData(
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM d, u")),
            season = "19th Week in Ordinary Time",
            color = SeasonColor.GREEN.value,
        )
        return data
    }

    @GetMapping("/calendar")
    fun getCalendar(): CalendarData {
        return NovusOrdoCalendar().generateCalendar(2024)
    }
}