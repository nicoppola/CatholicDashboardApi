package com.catholicdashboard.catholicdashboard.model

import java.io.Serializable

data class ProperOfSaints(
    val dataSource: List<String>?,
    val map: Map<String, Map<String, List<CalendarData.Saint>>>,
): Serializable
