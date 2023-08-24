package com.sunkitto.news.designSystem

import androidx.recyclerview.widget.DiffUtil
import com.sunkitto.news.core.model.ui.ArticleUi

object ArticlesUiDiffCallback : DiffUtil.ItemCallback<ArticleUi>() {

    override fun areItemsTheSame(
        oldItem: ArticleUi,
        newItem: ArticleUi,
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ArticleUi,
        newItem: ArticleUi,
    ): Boolean =
        oldItem == newItem
}