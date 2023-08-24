package com.sunkitto.news.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val preferencesManager: PreferencesManager,
) : ViewModel() {

    val settings = settingsRepository.settings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Settings(
                Language.FOLLOW_SYSTEM,
                Theme.FOLLOW_SYSTEM,
                TopHeadlinesCountry.USA,
            ),
        )

    fun setLanguage(language: Language) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
            preferencesManager.switchLanguage(language)
        }
    }

    fun setTopHeadlinesCountry(topHeadlinesCountry: TopHeadlinesCountry) {
        viewModelScope.launch {
            settingsRepository.setTopHeadlinesCountry(topHeadlinesCountry)
        }
    }

    fun setTheme(theme: Theme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
            preferencesManager.switchTheme(theme)
        }
    }
}