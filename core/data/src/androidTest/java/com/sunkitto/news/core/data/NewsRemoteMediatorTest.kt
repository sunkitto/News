package com.sunkitto.news.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.data.NewsRemoteMediator.Companion.START_PAGE
import com.sunkitto.news.core.database.NewsDatabase
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.model.NewsType
import com.sunkitto.news.core.network.NewsNetworkDataSourceImpl
import com.sunkitto.news.core.network.model.ArticleDto
import com.sunkitto.news.core.network.model.NewsDto
import com.sunkitto.news.core.network.model.SourceDto
import com.sunkitto.news.core.network.retrofit.NewsService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediatorTest {

    private val newsDatabase = Room.inMemoryDatabaseBuilder(
        context = ApplicationProvider.getApplicationContext(),
        klass = NewsDatabase::class.java,
    ).build()

    private val newsNetworkDataSource = mockk<NewsNetworkDataSourceImpl>(relaxed = true)

    @Test
    fun load_refresh_returns_success_when_data_is_present() = runTest {

        coEvery {
            newsNetworkDataSource.getAllNews(
                query = "",
                page = START_PAGE,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE
            )
        }.returns(testNewsDto)

        val subject = NewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            newsType = NewsType.AllNews(query = "")
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1
        )

        var result = subject.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun load_prepend_returns_success_and_end_of_pagination() = runTest {

        val subject = NewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            newsType = NewsType.AllNews(query = "")
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1
        )

        var result = subject.load(LoadType.PREPEND, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun load_append_returns_success_and_end_of_pagination_when_data_not_present() = runTest {

        coEvery {
            newsNetworkDataSource.getAllNews(
                query = "",
                page = START_PAGE,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE
            )
        }.returns(NewsDto("", 0, listOf()))

        val subject = NewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            newsType = NewsType.AllNews(query = "")
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1
        )

        var result = subject.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun load_refresh_returns_error_when_error_occurs() = runTest {

        coEvery {
            newsNetworkDataSource.getAllNews(
                query = "",
                page = START_PAGE,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE
            )
        }.throws(IOException())

        val subject = NewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            newsType = NewsType.AllNews(query = "")
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1
        )

        val result = subject.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @AfterEach
    fun tear_down() {
        newsDatabase.clearAllTables()
    }
}

private val testNewsDto =
    NewsDto(
        status = "",
        totalResults = 1,
        articles = listOf(
            ArticleDto(
                source = SourceDto(
                    id = "",
                    name = "",
                ),
                author = "",
                title = "",
                description = "",
                url = "",
                urlToImage = "",
                publishedAt = Instant.fromEpochMilliseconds(1),
                content = "",
            )
        )
    )