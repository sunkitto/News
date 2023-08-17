package com.sunkitto.news.core.data.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sunkitto.news.core.data.asTopHeadlinesEntity
import com.sunkitto.news.core.database.dao.TopHeadlinesDao
import com.sunkitto.news.core.database.dao.TopHeadlinesRemoteKeyDao
import com.sunkitto.news.core.database.model.top_headlines.TopHeadlinesEntity
import com.sunkitto.news.core.database.model.top_headlines.TopHeadlinesRemoteKey
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.ui.TopHeadlinesCategory
import com.sunkitto.news.core.network.NewsNetworkDataSource
import kotlinx.coroutines.flow.first

@OptIn(ExperimentalPagingApi::class)
internal class TopHeadlinesRemoteMediator(
    private val topHeadlinesDao: TopHeadlinesDao,
    private val topHeadlinesRemoteKeyDao: TopHeadlinesRemoteKeyDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val topHeadlinesCategory: TopHeadlinesCategory,
    private val settingsRepository: SettingsRepository,
): RemoteMediator<Int, TopHeadlinesEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopHeadlinesEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull { page ->
                        page.data.isNotEmpty()
                    }?.data?.lastOrNull()?.let { topHeadlinesEntity ->
                        topHeadlinesRemoteKeyDao.getRemoteKey(topHeadlinesEntity.url)
                    }
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage
                }
            }

            val country = settingsRepository.settings.first().topHeadlinesCountry.isoCode
            val topHeadlines = newsNetworkDataSource.getTopHeadlines(
                country = country,
                category = topHeadlinesCategory.value,
                page = page,
                pageSize = state.config.pageSize,
            ).articles.map { articleDto ->
                articleDto.asTopHeadlinesEntity()
            }

            val endOfPaginationReached = topHeadlines.isEmpty()

            val nextPage = if (endOfPaginationReached) null else page + 1
            val remoteKeys = topHeadlines.map { topHeadlinesEntity ->
                TopHeadlinesRemoteKey(
                    articleUrl = topHeadlinesEntity.url,
                    nextPage = nextPage
                )
            }

            if (loadType == LoadType.REFRESH) {
                topHeadlinesRemoteKeyDao.deleteAllRemoteKeys()
                topHeadlinesDao.deleteAllAndInsertNewTopHeadlines(topHeadlines)
                topHeadlinesRemoteKeyDao.upsertRemoteKeys(remoteKeys)
            } else {
                topHeadlinesRemoteKeyDao.upsertRemoteKeys(remoteKeys)
                topHeadlinesDao.insertTopHeadlines(topHeadlines)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}