package com.sunkitto.news.core.datastore

import androidx.datastore.core.DataStore
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry

class SettingsDataSource(
    private val settingsDataStore: DataStore<SettingsPreferences>,
) {

    val settings = settingsDataStore.data

    suspend fun setLanguage(language: Language) {
        settingsDataStore.updateData { settings ->
            settings.copy(language = language)
        }
    }

    suspend fun setTheme(theme: Theme) {
        settingsDataStore.updateData { settings ->
            settings.copy(theme = theme)
        }
    }

    suspend fun setTopHeadlinesCountry(topHeadlinesCountry: TopHeadlinesCountry) {
        settingsDataStore.updateData { settings ->
            settings.copy(topHeadlinesCountry = topHeadlinesCountry)
        }
    }
}