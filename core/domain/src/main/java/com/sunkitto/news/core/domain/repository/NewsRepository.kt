package com.sunkitto.news.core.domain.repository

import androidx.paging.PagingData
import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadlines(
        country: TopHeadlinesCountry,
        category: TopHeadlinesCategory,
    ): Flow<PagingData<ArticleEntity>>

    suspend fun getAllNews(query: String): Flow<PagingData<ArticleEntity>>
}