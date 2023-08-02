package com.sunkitto.news.core.datastore

import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.serialization.Serializable

@Serializable
data class SettingsPreferences(
    val language: Language = Language.FOLLOW_SYSTEM,
    val theme: Theme = Theme.FOLLOW_SYSTEM,
    val topHeadlinesCountry: TopHeadlinesCountry = TopHeadlinesCountry.USA
)