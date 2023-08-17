package com.sunkitto.news.feature.top_headlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunkitto.news.core.model.ui.ArticleUi
import com.sunkitto.news.design_system.ArticleUiDiffCallback
import com.sunkitto.news.feature.top_headlines.R
import com.sunkitto.news.feature.top_headlines.databinding.ItemTopHeadlineBinding

class TopHeadlinesAdapter(
    private val listener: TopHeadlinesRecyclerViewClickListener,
) : PagingDataAdapter<ArticleUi, RecyclerView.ViewHolder>(ArticleUiDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        TopHeadlineViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val articleUi = getItem(position)
        articleUi?.let {
            if (holder is TopHeadlineViewHolder) {
                holder.bind(
                    articleUi = articleUi,
                    listener = listener,
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_top_headline
    }

    class TopHeadlineViewHolder(
        private val binding: ItemTopHeadlineBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            articleUi: ArticleUi,
            listener: TopHeadlinesRecyclerViewClickListener,
        ) {
            with(binding) {
                this.root.setOnClickListener {
                    listener.onTopHeadlineClick(articleUi.url)
                }

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

        companion object {
            @JvmStatic
            fun create(parent: ViewGroup): TopHeadlineViewHolder =
                TopHeadlineViewHolder(
                    ItemTopHeadlineBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
        }
    }
}

interface TopHeadlinesRecyclerViewClickListener {
    fun onTopHeadlineClick(topHeadlineUrl: String)
}