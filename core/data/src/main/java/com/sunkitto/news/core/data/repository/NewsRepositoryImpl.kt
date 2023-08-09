package com.sunkitto.news.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sunkitto.news.core.data.NewsRemoteMediator
import com.sunkitto.news.core.data.asArticle
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.NewsType
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.retrofit.NewsService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
): NewsRepository {

    override fun getTopHeadlines(
        country: TopHeadlinesCountry,
        category: TopHeadlinesCategory,
    ): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = TOP_HEADLINES_PREFETCH_DISTANCE
            ),
            remoteMediator = NewsRemoteMediator(
                articlesDao = articlesDao,
                newsNetworkDataSource = newsNetworkDataSource,
                newsType = NewsType.TopHeadlines(
                    country = country,
                    category = category,
                )
            ),
            pagingSourceFactory = { articlesDao.getArticles() }
        ).flow
            .map { pagingData ->
                pagingData.map { articleEntity ->
                    articleEntity.asArticle()
                }
            }

    override fun getAllNews(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = ALL_NEWS_PREFETCH_DISTANCE
            ),
            remoteMediator = NewsRemoteMediator(
                articlesDao = articlesDao,
                newsNetworkDataSource = newsNetworkDataSource,
                newsType = NewsType.AllNews(query = query)
            ),
            pagingSourceFactory = { articlesDao.getArticles() }
        ).flow
            .map { pagingData ->
                pagingData.map { articleEntity ->
                    articleEntity.asArticle()
                }
            }

    companion object {
        private const val TOP_HEADLINES_PREFETCH_DISTANCE = 3
        private const val ALL_NEWS_PREFETCH_DISTANCE = 6
    }
}