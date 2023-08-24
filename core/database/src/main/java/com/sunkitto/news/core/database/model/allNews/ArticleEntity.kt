package com.sunkitto.news.core.database.model.allNews

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sunkitto.news.core.database.model.SourceEntity
import kotlinx.datetime.Instant

@Entity(tableName = "articles")
data class ArticleEntity(
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
