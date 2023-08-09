package com.sunkitto.news.core.domain.repository

import androidx.paging.PagingData
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(
        country: TopHeadlinesCountry,
        category: TopHeadlinesCategory,
    ): Flow<PagingData<Article>>

    fun getAllNews(query: String): Flow<PagingData<Article>>
}