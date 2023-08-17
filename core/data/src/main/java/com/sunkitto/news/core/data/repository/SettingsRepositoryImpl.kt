package com.sunkitto.news.core.data.repository

import com.sunkitto.news.core.data.asSettings
import com.sunkitto.news.core.datastore.SettingsDataSource
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
) : SettingsRepository {

    override val settings: Flow<Settings>
        get() = settingsDataSource.settings.map { settingsPreferences ->
            settingsPreferences.asSettings()
        }

    override suspend fun setLanguage(language: Language) =
        settingsDataSource.setLanguage(language)

    override suspend fun setTheme(theme: Theme) =
        settingsDataSource.setTheme(theme)

    override suspend fun setTopHeadlinesCountry(topHeadlinesCountry: TopHeadlinesCountry) =
        settingsDataSource.setTopHeadlinesCountry(topHeadlinesCountry)
}