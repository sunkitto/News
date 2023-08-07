package com.sunkitto.news.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sunkitto.news.core.data.NewsRemoteMediator
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.model.NewsType
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.retrofit.NewsService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class NewsRepositoryImpl @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
): NewsRepository {

    private fun pager(newsType: NewsType) =
        Pager(
            config = PagingConfig(
                pageSize = NewsService.DEFAULT_TOP_HEADLINES_PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 2
            ),
            remoteMediator = NewsRemoteMediator(
                articlesDao = articlesDao,
                newsNetworkDataSource = newsNetworkDataSource,
                newsType = newsType
            ),
            pagingSourceFactory = { articlesDao.getArticles() }
        ).flow

    override suspend fun getTopHeadlines(
        country: TopHeadlinesCountry,
        category: TopHeadlinesCategory,
    ): Flow<PagingData<ArticleEntity>> =
        pager(
            NewsType.TopHeadlines(
                country = country,
                category = category
            )
        )

    override suspend fun getAllNews(query: String): Flow<PagingData<ArticleEntity>> =
        pager(NewsType.AllNews(query = query))
}