package com.sunkitto.news.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.data.remote_mediator.TopHeadlinesRemoteMediator
import com.sunkitto.news.core.data.repository.SettingsRepositoryImpl
import com.sunkitto.news.core.database.NewsDatabase
import com.sunkitto.news.core.database.model.top_headlines.TopHeadlinesEntity
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.core.model.ui.TopHeadlinesCategory
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
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@OptIn(ExperimentalPagingApi::class)
class TopHeadlinesRemoteMediatorTest {

    private val newsDatabase = Room.inMemoryDatabaseBuilder(
        context = ApplicationProvider.getApplicationContext(),
        klass = NewsDatabase::class.java,
    ).build()

    private val newsNetworkDataSource = mockk<NewsNetworkDataSourceImpl>(relaxed = true)
    private val settingsRepository = mockk<SettingsRepositoryImpl>(relaxed = true)

    @Test
    fun load_refresh_returns_success_when_data_is_present() = runTest {
        coEvery {
            newsNetworkDataSource.getTopHeadlines(
                country = TopHeadlinesCountry.POLAND.isoCode,
                category = TopHeadlinesCategory.GENERAL.value,
                page = 1,
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
            )
        }.returns(testNewsDto)

        val subject = TopHeadlinesRemoteMediator(
            topHeadlinesDao = newsDatabase.topHeadlinesDao(),
            topHeadlinesRemoteKeyDao = newsDatabase.topHeadlinesRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            topHeadlinesCategory = TopHeadlinesCategory.GENERAL,
            settingsRepository = settingsRepository,
        )

        val pagingState = PagingState<Int, TopHeadlinesEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        var result = subject.load(LoadType.REFRESH, pagingState)
        Assertions.assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        Assertions.assertFalse(result.endOfPaginationReached)
    }

    @Test
    fun load_prepend_returns_success_and_end_of_pagination() = runTest {
        val subject = TopHeadlinesRemoteMediator(
            topHeadlinesDao = newsDatabase.topHeadlinesDao(),
            topHeadlinesRemoteKeyDao = newsDatabase.topHeadlinesRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            topHeadlinesCategory = TopHeadlinesCategory.GENERAL,
            settingsRepository = settingsRepository,
        )

        val pagingState = PagingState<Int, TopHeadlinesEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        var result = subject.load(LoadType.PREPEND, pagingState)
        Assertions.assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        Assertions.assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun load_append_returns_success_and_end_of_pagination_when_data_not_present() = runTest {
        coEvery {
            newsNetworkDataSource.getTopHeadlines(
                country = TopHeadlinesCountry.POLAND.isoCode,
                category = TopHeadlinesCategory.GENERAL.value,
                page = 1,
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
            )
        }.returns(
            NewsDto(
                status = "",
                totalResults = 1,
                articles = listOf(),
            ),
        )

        val subject = TopHeadlinesRemoteMediator(
            topHeadlinesDao = newsDatabase.topHeadlinesDao(),
            topHeadlinesRemoteKeyDao = newsDatabase.topHeadlinesRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            topHeadlinesCategory = TopHeadlinesCategory.GENERAL,
            settingsRepository = settingsRepository,
        )

        val pagingState = PagingState<Int, TopHeadlinesEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        var result = subject.load(LoadType.REFRESH, pagingState)
        Assertions.assertTrue(result is RemoteMediator.MediatorResult.Success)

        result = result as RemoteMediator.MediatorResult.Success
        Assertions.assertTrue(result.endOfPaginationReached)
    }

    @Test
    fun load_refresh_returns_error_when_error_occurs() = runTest {
        coEvery {
            newsNetworkDataSource.getTopHeadlines(
                country = TopHeadlinesCountry.POLAND.isoCode,
                category = TopHeadlinesCategory.GENERAL.value,
                page = 1,
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
            )
        }.throws(IOException())

        val subject = TopHeadlinesRemoteMediator(
            topHeadlinesDao = newsDatabase.topHeadlinesDao(),
            topHeadlinesRemoteKeyDao = newsDatabase.topHeadlinesRemoteKeysDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            topHeadlinesCategory = TopHeadlinesCategory.GENERAL,
            settingsRepository = settingsRepository,
        )

        val pagingState = PagingState<Int, TopHeadlinesEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE),
            leadingPlaceholderCount = 1,
        )

        val result = subject.load(LoadType.REFRESH, pagingState)

        Assertions.assertTrue(result is RemoteMediator.MediatorResult.Error)
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
            ),
        ),
    )