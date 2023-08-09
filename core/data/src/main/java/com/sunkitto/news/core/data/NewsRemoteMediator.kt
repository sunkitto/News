package com.sunkitto.news.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.sunkitto.news.core.database.dao.ArticlesDao
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.model.NewsType
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.retrofit.NewsService

@OptIn(ExperimentalPagingApi::class)
internal class NewsRemoteMediator(
    private val articlesDao: ArticlesDao,
    private val newsNetworkDataSource: NewsNetworkDataSource,
    private val newsType: NewsType,
): RemoteMediator<Int, ArticleEntity>() {

    private lateinit var articles: List<ArticleEntity>

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>,
    ): MediatorResult {
        return try {
            val page: Int = when(loadType) {
                LoadType.REFRESH -> START_PAGE
                // Not supported in this app
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        START_PAGE
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            when(newsType) {
                is NewsType.AllNews -> {
                    articles = newsNetworkDataSource
                        .getAllNews(
                            query = newsType.query,
                            page = page,
                            pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
                        )
                        .articles.map { articleDto ->
                            articleDto.asArticleEntity()
                        }
                }
                is NewsType.TopHeadlines -> {
                    articles = newsNetworkDataSource
                        .getTopHeadlines(
                            country = newsType.country.isoCode,
                            category = newsType.category.value,
                            page = page,
                            pageSize = NewsService.DEFAULT_ALL_NEWS_PAGE_SIZE,
                        )
                        .articles.map { articleDto ->
                            articleDto.asArticleEntity()
                        }
                }
            }

            if(loadType == LoadType.REFRESH) {
                articlesDao.deleteAllAndInsertNewArticles(articles)
            } else {
                articlesDao.insertArticles(articles)
            }

            MediatorResult.Success(
                endOfPaginationReached = articles.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(throwable = e)
        }
    }

    companion object {
        const val START_PAGE = 1
    }
}