package com.sunkitto.news.core.data

import com.sunkitto.news.core.database.model.ArticleEntity
import com.sunkitto.news.core.database.model.SourceEntity
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