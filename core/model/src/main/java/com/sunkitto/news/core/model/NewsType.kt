package com.sunkitto.news.core.model

import com.sunkitto.news.core.model.settings.TopHeadlinesCountry

sealed interface NewsType {
    data class AllNews(val query: String) : NewsType
    data class TopHeadlines(
        val country: TopHeadlinesCountry,
        val category: TopHeadlinesCategory,
    ) : NewsType
}
