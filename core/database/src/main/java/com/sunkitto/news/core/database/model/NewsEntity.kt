package com.sunkitto.news.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @Embedded("source_")
    val source: SourceEntity,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    @ColumnInfo("url_to_image")
    val urlToImage: String?,
    @ColumnInfo("published_at")
    val publishedAt: Instant,
    val content: String,
    @ColumnInfo("is_followed")
    val isFollowed: Boolean,
    @ColumnInfo("is_headline")
    val isHeadline: Boolean,
)

data class SourceEntity(
    val id: String?,
    val name: String,
)