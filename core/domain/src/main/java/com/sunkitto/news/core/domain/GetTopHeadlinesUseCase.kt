package com.sunkitto.news.core.domain

import androidx.paging.PagingData
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.TopHeadlinesCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(category: TopHeadlinesCategory): Flow<PagingData<Article>> {
        val settings = settingsRepository.settings.first()
        return newsRepository.getTopHeadlines(
            country = settings.topHeadlinesCountry,
            category = category
        )
    }
}