package com.sunkitto.news.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import com.sunkitto.news.core.data.remoteMediator.AllNewsRemoteMediator
import com.sunkitto.news.core.database.NewsDatabase
import com.sunkitto.news.core.database.model.allNews.ArticleEntity
import com.sunkitto.news.core.network.NewsNetworkDataSourceImpl
import com.sunkitto.news.core.network.model.ArticleDto
import com.sunkitto.news.core.network.model.NewsDto
import com.sunkitto.news.core.network.model.SourceDto
import com.sunkitto.news.core.network.retrofit.NewsService
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@OptIn(ExperimentalPagingApi::class)
@RunWith(RobolectricTestRunner::class)
class AllNewsRemoteMediatorTest {
    private val newsDatabase = Room.inMemoryDatabaseBuilder(
        context = RuntimeEnvironment.getApplication().applicationContext,
        klass = NewsDatabase::class.java,
    ).allowMainThreadQueries().build()

    private val newsNetworkDataSource = mockk<NewsNetworkDataSourceImpl>(relaxed = true)

    @Test
    fun load_refresh_returns_success_when_data_is_present() = runTest {
        coEvery {
            newsNetworkDataSource.getAllNews(
                query = "",
                page = 1,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
            )
        }.returns(testNewsDto)

        val subject = AllNewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            allNewsRemoteKeyDao = newsDatabase.allNewsRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            query = "",
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        var result = subject.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun load_prepend_returns_success_and_end_of_pagination() = runTest {
        val subject = AllNewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            allNewsRemoteKeyDao = newsDatabase.allNewsRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            query = "",
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1,
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
                page = 1,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
            )
        }.returns(
            NewsDto(
                status = "",
                totalResults = 1,
                articles = listOf(),
            ),
        )

        val subject = AllNewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            allNewsRemoteKeyDao = newsDatabase.allNewsRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            query = "",
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1,
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
                page = 1,
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
            )
        }.throws(IOException())

        val subject = AllNewsRemoteMediator(
            articlesDao = newsDatabase.articlesDao(),
            allNewsRemoteKeyDao = newsDatabase.allNewsRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            query = "",
        )

        val pagingState = PagingState<Int, ArticleEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        val result = subject.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @After
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
            ),
        ),
    )