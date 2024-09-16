package com.catholicdashboard.catholicdashboard.novus

import com.catholicdashboard.catholicdashboard.model.CalendarData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

fun CalendarData.addTemporale(): CalendarData {

    // holy family
    this.setSolemnity(
        localDate = this.holyFamilyDate(),
        season = "Holy Family",
        key = "holy_family",
    )

    this.setSolemnity(
        localDate = LocalDate.of(this.year.toInt(), Month.JANUARY, 1),
        season = "Mary, Mother of God",
        key = "mary_mother_god",
    )

    this.setSolemnity(
        localDate = this.getEpiphanySunday(),
        season = "Epiphany of the Lord",
        key = "epiphany",
    )

    this.setSolemnity(
        localDate = this.getBaptismOfLord(),
        season = "Baptism of the Lord",
        key = "baptism",
    )

    // ash wednesday, palm sunday, good friday, holy saturday, set in Lent.kt

    //ascension  todo make holy days of obligation just keys?
    this.setSolemnity(
        localDate = this.getAscension(),
        season = "The Ascension of the Lord",
        key = "ascension",
    )

    // pentecost set in Easter.kt

    this.setSolemnity(
        localDate = this.getPentecost().plusDays(1),
        season = "Mary, Mother of the Church",
        key = "mary_mother_church",
    )

    this.setSolemnity(
        localDate = this.getPentecost().plusDays(4),
        season = "Our Lord Jesus Christ, the Eternal High Priest",
        key = "high_priest",
    )

    this.setSolemnity(
        this.getPentecost().plusWeeks(1),
        "Trinity Sunday",
        key = "trinity",
    )

    // move corpus christi here todo this
    this.setSolemnity(
        localDate = this.getPentecost().plusWeeks(19),
        season = "Corpus Christi (The Body and Blood of Christ)",
        key = "corpus_cristi",
    )

    this.setSolemnity(
        localDate = this.getPentecost().plusDays(19),
        season = "The Sacred Heart of Jesus",
        key = "sacred_heart",
    )

    this.setSolemnity(
        localDate = this.getPentecost().plusDays(20),
        season = "The Immaculate Heart of Mary",
        key = "immaculate_heart",
    )

    this.setSolemnity(
        localDate = this.getFirstSundayOfAdvent().minusWeeks(1),
        season = "Christ the King",
        key = "christ_king",
    )

    return this
}


fun CalendarData.holyFamilyDate(): LocalDate {
    //sunday between christmas and new years day
    var date = LocalDate.of(this.year.toInt(), Month.DECEMBER, 25).plusDays(1)

    while (date.dayOfWeek != DayOfWeek.SUNDAY) {
        date = date.plusDays(1)
    }
    return date
}

fun CalendarData.getAscension(): LocalDate {
    //todo move to sunday if usa
    return this.getEaster().plusWeeks(6)
}