package com.sunkitto.news.core.data

import androidx.annotation.DrawableRes
import com.sunkitto.news.core.database.model.RecentSearchEntity
import com.sunkitto.news.core.database.model.SourceEntity
import com.sunkitto.news.core.database.model.allNews.ArticleEntity
import com.sunkitto.news.core.database.model.topHeadlines.TopHeadlinesEntity
import com.sunkitto.news.core.datastore.model.SettingsPreferences
import com.sunkitto.news.core.model.Article
import com.sunkitto.news.core.model.RecentSearch
import com.sunkitto.news.core.model.Source
import com.sunkitto.news.core.model.settings.Settings
import com.sunkitto.news.core.model.ui.ArticleUi
import com.sunkitto.news.core.model.ui.Visible
import com.sunkitto.news.core.network.model.ArticleDto
import com.sunkitto.news.util.toLocalizedDateTimeString

fun ArticleDto.asTopHeadlinesEntity() =
    TopHeadlinesEntity(
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
    )

fun ArticleDto.asArticleEntity() =
    ArticleEntity(
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

fun TopHeadlinesEntity.asArticle() =
    Article(
        id = this.id,
        source = Source(
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
    )

fun ArticleEntity.asArticle() =
    Article(
        id = this.id,
        source = Source(
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
    )

fun Article.asArticleUi(
    @DrawableRes placeholderResourceId: Int,
): ArticleUi {
    return ArticleUi(
        id = this.id,
        sourceName = this.source.name,
        author = Visible(this.author),
        title = this.title,
        description = Visible(this.description),
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt.toLocalizedDateTimeString(),
        placeholder = placeholderResourceId,
    )
}
