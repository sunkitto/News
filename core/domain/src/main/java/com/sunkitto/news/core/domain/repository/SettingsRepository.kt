package com.sunkitto.news.core.domain.repository

import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val settings: Flow<Settings>

    suspend fun setLanguage(language: Language)

    suspend fun setTheme(theme: Theme)

    suspend fun setTopHeadlinesCountry(topHeadlinesCountry: TopHeadlinesCountry)
}