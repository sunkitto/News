package com.sunkitto.news.core.database.model.top_headlines

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_headlines_remote_keys")
data class TopHeadlinesRemoteKey(
    @PrimaryKey
    val articleUrl: String,
    val nextPage: Int?,
)