package com.sunkitto.news.core.model.ui

import androidx.annotation.DrawableRes

data class ArticleUi(
    val id: Int,
    val sourceName: String,
    val author: Visible<String?>,
    val title: String,
    val description: Visible<String?>,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    @DrawableRes
    val placeholder: Int,
)