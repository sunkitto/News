package com.sunkitto.news.feature.top_headlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunkitto.news.core.model.ArticleUi
import com.sunkitto.news.design_system.ArticleUiDiffCallback
import com.sunkitto.news.feature.top_headlines.databinding.ItemTopHeadlineBinding

class TopHeadlinesAdapter : PagingDataAdapter<ArticleUi, TopHeadlinesAdapter.ViewHolder>(
    ArticleUiDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            ItemTopHeadlineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        getItem(position)?.let { articleUi ->
            with(holder.binding) {

                Glide.with(articleImageView.context)
                    .load(articleUi.urlToImage)
                    .error(articleUi.placeholder)
                    .into(articleImageView)

                titleTextView.text = articleUi.title

                descriptionTextView.text = articleUi.description.value
                descriptionTextView.visibility = articleUi.description.isVisible()

                sourceTextView.text = articleUi.sourceName
                dateTextView.text = articleUi.publishedAt
            }
        }
    }

    class ViewHolder(val binding: ItemTopHeadlineBinding) : RecyclerView.ViewHolder(binding.root)
}