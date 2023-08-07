package com.sunkitto.news.core.domain

import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.TopHeadlinesCategory
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository,
) {

    suspend operator fun invoke(category: TopHeadlinesCategory) {
        val settings = settingsRepository.settings.first()
        newsRepository.getTopHeadlines(
            country = settings.topHeadlinesCountry,
            category = category
        )
    }
}