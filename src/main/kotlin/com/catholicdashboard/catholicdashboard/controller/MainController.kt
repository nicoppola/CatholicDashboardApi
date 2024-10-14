package com.catholicdashboard.catholicdashboard.controller

import com.catholicdashboard.catholicdashboard.model.CalendarData
import com.catholicdashboard.catholicdashboard.model.FileReader
import com.catholicdashboard.catholicdashboard.novus.NovusOrdoCalendar
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController @Autowired constructor(fileReader: FileReader) {

    val novusCal = NovusOrdoCalendar(fileReader)

    @GetMapping("/novus/{day}-{month}-{year}")
    fun getDay(@PathVariable day: Int, @PathVariable month: Int, @PathVariable year: Int): CalendarData.Day {
        return novusCal.getDay(day, month, year)
    }

    @GetMapping("/today")
    fun getToday(): CalendarData.Day {
        return novusCal.getDay(19, 9, 2024)
    }

    @GetMapping("/calendar")
    fun getCalendar(): CalendarData {
        return novusCal.getCalendar(2024)
    }

}