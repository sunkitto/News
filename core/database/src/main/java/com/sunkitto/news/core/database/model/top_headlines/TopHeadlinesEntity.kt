package com.sunkitto.news.core.database.model.top_headlines

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunkitto.news.core.database.model.SourceEntity
import kotlinx.datetime.Instant

@Entity(tableName = "top_headlines")
data class TopHeadlinesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Embedded("source_")
    val source: SourceEntity,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    @ColumnInfo("url_to_image")
    val urlToImage: String?,
    @ColumnInfo("published_at")
    val publishedAt: Instant,
    val content: String?,
)