package com.sunkitto.news.core.data

import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.database.model.RecentSearchEntity
import com.sunkitto.news.core.database.model.SourceEntity
import com.sunkitto.news.core.datastore.SettingsPreferences
import com.sunkitto.news.core.model.RecentSearch
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.network.model.ArticleDto

fun ArticleDto.asArticleEntity() =
    ArticleEntity(
        id = 0,
        source = SourceEntity(
            id = this.source.id,
            name = this.source.name,
        ),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content,
        isHeadline = false,
    )

fun RecentSearchEntity.asRecentSearch() =
    RecentSearch(
        query = this.query,
        date = this.date,
    )

fun RecentSearch.asRecentSearchEntity() =
    RecentSearchEntity(
        query = this.query,
        date = this.date,
    )

fun SettingsPreferences.asSettings() =
    Settings(
        language = this.language,
        theme = this.theme,
        topHeadlinesCountry = topHeadlinesCountry,
    )