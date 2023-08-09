package com.sunkitto.news.all_news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunkitto.news.all_news.databinding.ItemAllNewsBinding
import com.sunkitto.news.core.model.ArticleUi
import com.sunkitto.news.design_system.ArticleUiDiffCallback

class AllNewsAdapter : PagingDataAdapter<ArticleUi, AllNewsAdapter.ViewHolder>(
    ArticleUiDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder =
        ViewHolder(
            ItemAllNewsBinding.inflate(
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

                com.bumptech.glide.Glide.with(articleImageView.context)
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

    class ViewHolder(val binding: ItemAllNewsBinding) : RecyclerView.ViewHolder(binding.root)
}