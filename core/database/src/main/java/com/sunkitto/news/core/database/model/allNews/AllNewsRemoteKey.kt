package com.sunkitto.news.core.database.model.allNews

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "all_news_remote_keys")
data class AllNewsRemoteKey(
    @PrimaryKey
    val articleUrl: String,
    val nextPage: Int?,
)