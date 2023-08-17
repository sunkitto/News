package com.sunkitto.news.core.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

internal class InstantConverter {

    @TypeConverter
    fun longToInstant(value: Long): Instant =
        Instant.fromEpochMilliseconds(value)

    @TypeConverter
    fun instantToLong(value: Instant): Long =
        value.toEpochMilliseconds()
}