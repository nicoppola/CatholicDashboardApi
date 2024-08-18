package com.catholicdashboard.catholicdashboard.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/today")
class MainController {

    @GetMapping
    fun getToday(): String {
        return LocalDate.now().toString()
    }

}