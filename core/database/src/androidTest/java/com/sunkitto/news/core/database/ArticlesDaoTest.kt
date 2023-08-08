package com.sunkitto.news.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.database.model.SourceEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArticlesDaoTest {

    private lateinit var database: NewsDatabase
    private lateinit var articlesDao: ArticlesDao

    @BeforeEach
    fun create_database() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NewsDatabase::class.java,
        ).build()
        articlesDao = database.articlesDao()
    }

    @Test
    fun search_articles() = runTest {
        val fakeArticleEntities = listOf(
            testArticle(
                title = "Elon Musk changes Twitter logo from blue bird to 'X'",
                description = null,
            ),
            testArticle(
                title = "Meta is set to launch Threads, an app similar to Twitter.",
                description = "Elon Musk’s changes to Twitter have led to a demand " +
                    "for an alternative - and Meta may be about to provide it.",
            ),
            testArticle(
                title = "Judge blocks Arkansas law allowing librarians " +
                    "to be charged over ‘harmful’ books",
                description = "Decision comes as lawmakers in conservative states are pushing...",
            ),
        )
        val query = "%elon mUsK%"
        articlesDao.insertArticles(fakeArticleEntities)
        val searchedArticles = articlesDao.searchArticles(query).first()
        val searchedArticlesIds = searchedArticles.map { it.id }

        assertEquals(listOf(1, 2), searchedArticlesIds)
    }

    @AfterEach
    fun close_database() {
        database.close()
    }
}

private fun testArticle(
    epochMilliseconds: Long = 0,
    title: String = "",
    description: String? = null,
): ArticleEntity =
    ArticleEntity(
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
    )