package com.sunkitto.news.core.network.model

import kotlinx.datetime.Instant
import kotlinx.datetime.serializers.InstantIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
data class NewsDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>,
)

@Serializable
data class ArticleDto(
    val source: SourceDto,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    @Serializable(with = InstantIso8601Serializer::class)
    val publishedAt: Instant,
    val content: String,
)

@Serializable
data class SourceDto(
    val id: String?,
    val name: String,
)