package com.sunkitto.news.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunkitto.news.core.database.converters.InstantConverter
import com.sunkitto.news.core.database.dao.NewsDao
import com.sunkitto.news.core.database.model.NewsEntity

@Database(
    entities = [NewsEntity::class],
    version = 1
)
@TypeConverters(
    InstantConverter::class
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}
