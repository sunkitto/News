package com.sunkitto.news.core.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.RemoteMediator
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry

@OptIn(ExperimentalPagingApi::class)
interface NewsRepository {

    suspend fun getTopHeadlines(
        country: TopHeadlinesCountry,
        category: TopHeadlinesCategory,
    ): RemoteMediator<Int, ArticleEntity>

    suspend fun getAllNews(query: String, ): RemoteMediator<Int, ArticleEntity>
}