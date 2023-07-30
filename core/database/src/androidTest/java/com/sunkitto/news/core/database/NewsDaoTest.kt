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
        newsDao.upsertNews(fakeNewsEntities)
        val orderedNews = newsDao.getNews().first()
        assertEquals(
            listOf(3L, 2L, 1L),
            orderedNews.map { news -> news.publishedAt.toEpochMilliseconds() }
        )
    }

    @Test
    fun searchNews() = runTest {
        val fakeNewsEntities = listOf(
            testNews(
                title = "Elon Musk changes Twitter logo from blue bird to 'X'",
                description = null,
            ),
            testNews(
                title = "Meta is set to launch Threads, an app similar to Twitter.",
                description = "Elon Musk’s changes to Twitter have led to a demand " +
                        "for an alternative - and Meta may be about to provide it.",
            ),
            testNews(
                title = "Judge blocks Arkansas law allowing librarians " +
                        "to be charged over ‘harmful’ books",
                description = "Decision comes as lawmakers in conservative states are pushing...",
            ),
        )
        val query = "%elon mUsK%"
        newsDao.upsertNews(fakeNewsEntities)
        val searchedNewsIds = newsDao.searchNews(query).first()
        val searchedIds = searchedNewsIds.map { it.id }
        assertEquals(listOf(1, 2), searchedIds)
    }

    @AfterEach
    fun closeDatabase() {
        database.close()
    }
}

private fun testNews(
    epochMilliseconds: Long = 0,
    title: String = "",
    description: String? = null,
): NewsEntity =
    NewsEntity(
        source = SourceEntity(
            id = "",
            name = "",
        ),
        author = "",
        title = title,
        description = description,
        url = "",
        urlToImage = "",
        publishedAt = Instant.fromEpochMilliseconds(epochMilliseconds),
        content = "",
        isFollowed = false,
        isHeadline = false,
    )