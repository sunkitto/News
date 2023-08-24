package com.sunkitto.news.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sunkitto.news.core.database.converters.InstantConverter
import com.sunkitto.news.core.database.dao.AllNewsRemoteKeyDao
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.dao.RecentSearchDao
import com.sunkitto.news.core.database.dao.TopHeadlinesDao
import com.sunkitto.news.core.database.dao.TopHeadlinesRemoteKeyDao
import com.sunkitto.news.core.database.model.RecentSearchEntity
import com.sunkitto.news.core.database.model.allNews.AllNewsRemoteKey
import com.sunkitto.news.core.database.model.allNews.ArticleEntity
import com.sunkitto.news.core.database.model.topHeadlines.TopHeadlinesEntity
import com.sunkitto.news.core.database.model.topHeadlines.TopHeadlinesRemoteKey

@Database(
    entities = [
        TopHeadlinesEntity::class,
        ArticleEntity::class,
        RecentSearchEntity::class,
        TopHeadlinesRemoteKey::class,
        AllNewsRemoteKey::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun topHeadlinesDao(): TopHeadlinesDao

    abstract fun articlesDao(): ArticlesDao

    abstract fun recentSearchDao(): RecentSearchDao

    abstract fun topHeadlinesRemoteKeysDao(): TopHeadlinesRemoteKeyDao

    abstract fun allNewsRemoteKeysDao(): AllNewsRemoteKeyDao
}
