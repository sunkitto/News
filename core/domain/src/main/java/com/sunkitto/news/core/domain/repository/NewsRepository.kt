package com.sunkitto.news.core.domain.repository

import androidx.paging.PagingData
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.TopHeadlinesCategory
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlines(category: TopHeadlinesCategory): Flow<PagingData<Article>>

    fun getAllNews(query: String): Flow<PagingData<Article>>
}