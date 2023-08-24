package com.sunkitto.news.feature.settings.repository

import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestSettingsRepository : SettingsRepository {

    override val settings: MutableSharedFlow<Settings> = MutableSharedFlow(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST,
    )

    override suspend fun setLanguage(language: Language) {
        settings.tryEmit(
            Settings(
                language = language,
                theme = Theme.FOLLOW_SYSTEM,
                topHeadlinesCountry = TopHeadlinesCountry.USA,
            ),
        )
    }

    override suspend fun setTheme(theme: Theme) {
        settings.tryEmit(
            Settings(
                language = Language.FOLLOW_SYSTEM,
                theme = theme,
                topHeadlinesCountry = TopHeadlinesCountry.USA,
            ),
        )
    }

    override suspend fun setTopHeadlinesCountry(topHeadlinesCountry: TopHeadlinesCountry) {
        settings.tryEmit(
            Settings(
                language = Language.FOLLOW_SYSTEM,
                theme = Theme.FOLLOW_SYSTEM,
                topHeadlinesCountry = topHeadlinesCountry,
            ),
        )
    }
}