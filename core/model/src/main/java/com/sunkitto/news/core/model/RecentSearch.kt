package com.sunkitto.news.core.model

import kotlinx.datetime.Instant

data class RecentSearch(
    val query: String,
    val date: Instant,
)
