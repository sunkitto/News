package com.sunkitto.news.core.data.remote_mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sunkitto.news.core.data.asArticleEntity
import com.sunkitto.news.core.database.dao.AllNewsRemoteKeyDao
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.model.all_news.AllNewsRemoteKey
import com.sunkitto.news.core.database.model.all_news.ArticleEntity
import com.sunkitto.news.core.network.NewsNetworkDataSource

@OptIn(ExperimentalPagingApi::class)
internal class AllNewsRemoteMediator(
    private val articlesDao: ArticlesDao,
    private val allNewsRemoteKeyDao: AllNewsRemoteKeyDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val query: String,
): RemoteMediator<Int, ArticleEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = state.pages.lastOrNull { page ->
                        page.data.isNotEmpty()
                    }?.data?.lastOrNull()?.let { articleEntity ->
                        allNewsRemoteKeyDao.getRemoteKey(articleEntity.url)
                    }
                    val nextPage = remoteKey?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKey != null
                    )
                    nextPage
                }
            }

            val articles = newsNetworkDataSource.getAllNews(
                query = query,
                page = page,
                pageSize = state.config.pageSize,
            ).articles.map { articleDto ->
                articleDto.asArticleEntity()
            }

            val endOfPaginationReached = articles.isEmpty()

            val nextPage = if (endOfPaginationReached) null else page + 1
            val remoteKeys = articles.map { articleEntity ->
                AllNewsRemoteKey(
                    articleUrl = articleEntity.url,
                    nextPage = nextPage
                )
            }

            if (loadType == LoadType.REFRESH) {
                allNewsRemoteKeyDao.deleteAllRemoteKeys()
                articlesDao.deleteAllAndInsertNewArticles(articles)
                allNewsRemoteKeyDao.upsertRemoteKeys(remoteKeys)
            } else {
                allNewsRemoteKeyDao.upsertRemoteKeys(remoteKeys)
                articlesDao.insertArticles(articles)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}