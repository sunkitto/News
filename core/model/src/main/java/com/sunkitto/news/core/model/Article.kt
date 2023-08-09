package com.sunkitto.news.core.model

import kotlinx.datetime.Instant

data class Article(
    val id: Int,
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: Instant,
    val content: String,
)

data class Source(
    val id: String?,
    val name: String,
)