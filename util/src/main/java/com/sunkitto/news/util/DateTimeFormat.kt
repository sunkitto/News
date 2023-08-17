package com.sunkitto.news.util

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant

/**
 * Returns localized time in short format like: 08.08.2023, 18:24
 */
fun Instant.toLocalizedDateTimeString(): String {
    val zonedTime = java.time.ZonedDateTime.ofInstant(
        this.toJavaInstant(),
        java.time.ZoneId.systemDefault(),
    )
    val formatter = java.time.format.DateTimeFormatter.ofLocalizedDateTime(
        java.time.format.FormatStyle.SHORT,
    )
    return zonedTime.format(formatter)
}