package com.sunkitto.news.core.network.model

import com.sunkitto.news.core.network.model.serializer.InstantSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val status: String,
    val totalResults: Int,
    val news: List<ArticleDto>
)

@Serializable
data class ArticleDto(
    val id: Int,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    @Serializable(with = InstantSerializer::class)
    val publishedAt: Instant,
    val content: String,
)
