package com.sunkitto.news.feature.top_headlines.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sunkitto.news.core.model.ArticleUi

class TopHeadlinesDiffCallback : DiffUtil.ItemCallback<ArticleUi>() {

    override fun areItemsTheSame(
        oldItem: ArticleUi,
        newItem: ArticleUi
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ArticleUi,
        newItem: ArticleUi
    ): Boolean =
        oldItem == newItem
}