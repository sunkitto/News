package com.sunkitto.news.util

import kotlinx.datetime.toInstant
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DateFormatterTest {

    @Test
    fun from_instant_to_localized_date_time_string() {
        val instant = "2023-08-08T16:24:00Z".toInstant()
        assertEquals("08.08.2023, 18:24", instant.toLocalizedDateTimeString())
    }
}