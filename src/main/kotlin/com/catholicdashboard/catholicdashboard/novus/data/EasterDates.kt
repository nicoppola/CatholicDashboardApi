package com.catholicdashboard.catholicdashboard.novus.data

import java.time.LocalDate
import java.time.Month

class EasterDates {
    companion object {
        private val dates = mapOf<String, LocalDate>(
            ("2001" to LocalDate.of(2001, Month.APRIL, 15)),
            ("2002" to LocalDate.of(2002, Month.MARCH, 31)),
            ("2003" to LocalDate.of(2003, Month.APRIL, 20)),
            ("2004" to LocalDate.of(2004, Month.APRIL, 11)),
            ("2005" to LocalDate.of(2005, Month.MARCH, 27)),
            ("2006" to LocalDate.of(2006, Month.APRIL, 16)),
            ("2007" to LocalDate.of(2007, Month.APRIL, 8)),
            ("2008" to LocalDate.of(2008, Month.MARCH, 23)),
            ("2009" to LocalDate.of(2009, Month.APRIL, 12)),
            ("2010" to LocalDate.of(2010, Month.APRIL, 4)),
            ("2011" to LocalDate.of(2011, Month.APRIL, 24)),
            ("2012" to LocalDate.of(2012, Month.APRIL, 8)),
            ("2013" to LocalDate.of(2013, Month.MARCH, 31)),
            ("2014" to LocalDate.of(2014, Month.APRIL, 20)),
            ("2015" to LocalDate.of(2015, Month.APRIL, 5)),
            ("2016" to LocalDate.of(2016, Month.MARCH, 27)),
            ("2017" to LocalDate.of(2017, Month.APRIL, 16)),
            ("2018" to LocalDate.of(2018, Month.APRIL, 1)),
            ("2019" to LocalDate.of(2019, Month.APRIL, 21)),
            ("2020" to LocalDate.of(2020, Month.APRIL, 12)),
            ("2021" to LocalDate.of(2021, Month.APRIL, 4)),
            ("2022" to LocalDate.of(2022, Month.APRIL, 17)),
            ("2023" to LocalDate.of(2023, Month.APRIL, 9)),
            ("2024" to LocalDate.of(2024, Month.MARCH, 31)),
            ("2025" to LocalDate.of(2025, Month.APRIL, 20)),
            ("2026" to LocalDate.of(2026, Month.APRIL, 5)),
            ("2027" to LocalDate.of(2027, Month.MARCH, 28)),
            ("2028" to LocalDate.of(2028, Month.APRIL, 16)),
            ("2029" to LocalDate.of(2029, Month.APRIL, 1)),
            ("2030" to LocalDate.of(2030, Month.APRIL, 21)),
            ("2031" to LocalDate.of(2031, Month.APRIL, 13)),
            ("2032" to LocalDate.of(2032, Month.MARCH, 28)),
            ("2033" to LocalDate.of(2033, Month.APRIL, 17)),
            ("2034" to LocalDate.of(2034, Month.APRIL, 9)),
            ("2035" to LocalDate.of(2035, Month.MARCH, 25)),
            ("2036" to LocalDate.of(2036, Month.APRIL, 13)),
            ("2037" to LocalDate.of(2037, Month.APRIL, 5)),
            ("2038" to LocalDate.of(2038, Month.APRIL, 25)),
            ("2039" to LocalDate.of(2039, Month.APRIL, 10)),
            ("2040" to LocalDate.of(2040, Month.APRIL, 1)),
            ("2041" to LocalDate.of(2041, Month.APRIL, 21)),
            ("2042" to LocalDate.of(2042, Month.APRIL, 6)),
            ("2043" to LocalDate.of(2043, Month.MARCH, 29)),
            ("2044" to LocalDate.of(2044, Month.APRIL, 17)),
            ("2045" to LocalDate.of(2045, Month.APRIL, 9)),
            ("2046" to LocalDate.of(2046, Month.MARCH, 25)),
            ("2047" to LocalDate.of(2047, Month.APRIL, 14)),
            ("2048" to LocalDate.of(2048, Month.APRIL, 5)),
            ("2049" to LocalDate.of(2049, Month.APRIL, 18)),
            ("2050" to LocalDate.of(2050, Month.APRIL, 10)),
            ("2051" to LocalDate.of(2051, Month.APRIL, 2)),
            ("2052" to LocalDate.of(2052, Month.APRIL, 21)),
            ("2053" to LocalDate.of(2053, Month.APRIL, 6)),
            ("2054" to LocalDate.of(2054, Month.MARCH, 29)),
            ("2055" to LocalDate.of(2055, Month.APRIL, 18)),
            ("2056" to LocalDate.of(2056, Month.APRIL, 2)),
            ("2057" to LocalDate.of(2057, Month.APRIL, 22)),
            ("2058" to LocalDate.of(2058, Month.APRIL, 14)),
            ("2059" to LocalDate.of(2059, Month.MARCH, 30)),
            ("2060" to LocalDate.of(2060, Month.APRIL, 18)),
            ("2061" to LocalDate.of(2061, Month.APRIL, 10)),
            ("2062" to LocalDate.of(2062, Month.MARCH, 26)),
            ("2063" to LocalDate.of(2063, Month.APRIL, 15)),
            ("2064" to LocalDate.of(2064, Month.APRIL, 6)),
            ("2065" to LocalDate.of(2065, Month.MARCH, 29)),
            ("2066" to LocalDate.of(2066, Month.APRIL, 11)),
            ("2067" to LocalDate.of(2067, Month.APRIL, 3)),
            ("2068" to LocalDate.of(2068, Month.APRIL, 22)),
            ("2069" to LocalDate.of(2069, Month.APRIL, 14)),
            ("2070" to LocalDate.of(2070, Month.MARCH, 30)),
            ("2071" to LocalDate.of(2071, Month.APRIL, 19)),
            ("2072" to LocalDate.of(2072, Month.APRIL, 10)),
            ("2073" to LocalDate.of(2073, Month.MARCH, 26)),
            ("2074" to LocalDate.of(2074, Month.APRIL, 15)),
            ("2075" to LocalDate.of(2075, Month.APRIL, 7)),
            ("2076" to LocalDate.of(2076, Month.APRIL, 19)),
            ("2077" to LocalDate.of(2077, Month.APRIL, 11)),
            ("2078" to LocalDate.of(2078, Month.APRIL, 3)),
            ("2079" to LocalDate.of(2079, Month.APRIL, 23)),
            ("2080" to LocalDate.of(2080, Month.APRIL, 7)),
            ("2081" to LocalDate.of(2081, Month.MARCH, 30)),
            ("2082" to LocalDate.of(2082, Month.APRIL, 19)),
            ("2083" to LocalDate.of(2083, Month.APRIL, 4)),
            ("2084" to LocalDate.of(2084, Month.MARCH, 26)),
            ("2085" to LocalDate.of(2085, Month.APRIL, 15)),
            ("2086" to LocalDate.of(2086, Month.MARCH, 31)),
            ("2087" to LocalDate.of(2087, Month.APRIL, 20)),
            ("2088" to LocalDate.of(2088, Month.APRIL, 11)),
            ("2089" to LocalDate.of(2089, Month.APRIL, 3)),
            ("2090" to LocalDate.of(2090, Month.APRIL, 16)),
            ("2091" to LocalDate.of(2091, Month.APRIL, 8)),
            ("2092" to LocalDate.of(2092, Month.MARCH, 30)),
            ("2093" to LocalDate.of(2093, Month.APRIL, 12)),
            ("2094" to LocalDate.of(2094, Month.APRIL, 4)),
            ("2095" to LocalDate.of(2095, Month.APRIL, 24)),
            ("2096" to LocalDate.of(2096, Month.APRIL, 15)),
            ("2097" to LocalDate.of(2097, Month.MARCH, 31)),
            ("2098" to LocalDate.of(2098, Month.APRIL, 20)),
            ("2099" to LocalDate.of(2099, Month.APRIL, 12)),
            ("2100" to LocalDate.of(2100, Month.MARCH, 28)),
        )

        fun get(year: String): LocalDate? {
            return dates[year]
        }
    }
}