package com.sunkitto.news.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunkitto.news.core.database.converters.InstantConverter
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.dao.RecentSearchDao
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.database.model.RecentSearchEntity

@Database(
    entities = [
        ArticleEntity::class,
        RecentSearchEntity::class,
    ],
    version = 1,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun articlesDao(): ArticlesDao

    abstract fun recentSearchDao(): RecentSearchDao
}
