package com.sunkitto.news.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sunkitto.news.core.data.asArticle
import com.sunkitto.news.core.data.remoteMediator.AllNewsRemoteMediator
import com.sunkitto.news.core.data.remoteMediator.TopHeadlinesRemoteMediator
import com.sunkitto.news.core.database.dao.AllNewsRemoteKeyDao
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.dao.TopHeadlinesDao
import com.sunkitto.news.core.database.dao.TopHeadlinesRemoteKeyDao
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.retrofit.NewsService
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val topHeadlinesDao: TopHeadlinesDao,
    private val articlesDao: ArticlesDao,
    private val topHeadlinesRemoteKeyDao: TopHeadlinesRemoteKeyDao,
    private val allNewsRemoteKeyDao: AllNewsRemoteKeyDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val settingsRepository: SettingsRepository,
) : NewsRepository {

    override fun getTopHeadlines(category: TopHeadlinesCategory): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
                prefetchDistance = TOP_HEADLINES_PREFETCH_DISTANCE,
            ),
            remoteMediator = TopHeadlinesRemoteMediator(
                topHeadlinesDao = topHeadlinesDao,
                newsNetworkDataSource = newsNetworkDataSource,
                topHeadlinesCategory = category,
                topHeadlinesRemoteKeyDao = topHeadlinesRemoteKeyDao,
                settingsRepository = settingsRepository,
            ),
            pagingSourceFactory = {
                topHeadlinesDao.getTopHeadlines()
            },
        ).flow
            .map { pagingData ->
                pagingData.map { topHeadlinesEntity -> topHeadlinesEntity.asArticle() }
            }

    override fun getAllNews(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
                prefetchDistance = ALL_NEWS_PREFETCH_DISTANCE,
            ),
            remoteMediator = AllNewsRemoteMediator(
                articlesDao = articlesDao,
                allNewsRemoteKeyDao = allNewsRemoteKeyDao,
                newsNetworkDataSource = newsNetworkDataSource,
                query = query,
            ),
            pagingSourceFactory = { articlesDao.getArticles() },
        ).flow
            .map { pagingData ->
                pagingData.map { newsEntity ->
                    newsEntity.asArticle()
                }
            }

    companion object {
        private const val TOP_HEADLINES_PREFETCH_DISTANCE = 3
        private const val ALL_NEWS_PREFETCH_DISTANCE = 6
    }
}