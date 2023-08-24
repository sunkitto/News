package com.sunkitto.news.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant

/**
 * Returns localized time in short format like: 08.08.2023, 18:24
 */
fun Instant.toLocalizedDateTimeString(): String {
    val zonedTime = ZonedDateTime.ofInstant(
        this.toJavaInstant(),
        ZoneId.systemDefault(),
    )
    val formatter = DateTimeFormatter.ofLocalizedDateTime(
        FormatStyle.SHORT,
    )
    return zonedTime.format(formatter)
}