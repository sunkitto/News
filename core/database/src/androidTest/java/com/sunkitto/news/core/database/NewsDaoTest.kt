package com.sunkitto.news.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.database.dao.NewsDao
import com.sunkitto.news.core.database.model.NewsEntity
import com.sunkitto.news.core.database.model.SourceEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NewsDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var newsDao: NewsDao

    @BeforeEach
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java
        ).build()
        newsDao = database.newsDao()
    }

    @Test
    fun writeAndReadNewsByDescendingPublishDate() = runTest {
        val fakeNewsEntities = listOf(
            testNews(epochMilliseconds = 1),
            testNews(epochMilliseconds = 3),
            testNews(epochMilliseconds = 2),
        )
        newsDao.upsertNews(
            fakeNewsEntities
        )
        val orderedNews = newsDao.getNews().first()
        assertEquals(
            listOf(3L, 2L, 1L),
            orderedNews.map { news -> news.publishedAt.toEpochMilliseconds() }
        )
    }

    @AfterEach
    fun closeDatabase() {
        database.close()
    }
}

private fun testNews(
    epochMilliseconds: Long = 0
): NewsEntity =
    NewsEntity(
        id = 0,
        source = SourceEntity(
            id = "",
            name = "",
        ),
        author = "",
        title = "",
        description = "",
        url = "",
        urlToImage = "",
        publishedAt = Instant.fromEpochMilliseconds(epochMilliseconds),
        content = "",
        isFollowed = false,
        isHeadline = false,
    )