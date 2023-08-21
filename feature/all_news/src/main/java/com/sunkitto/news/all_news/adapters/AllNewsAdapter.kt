package com.sunkitto.news.all_news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sunkitto.news.all_news.R
import com.sunkitto.news.all_news.databinding.ItemAllNewsBinding
import com.sunkitto.news.core.model.ui.ArticleUi
import com.sunkitto.news.design_system.ArticleUiDiffCallback

class AllNewsAdapter(
    private val listener: AllNewsRecyclerViewClickListener,
) : PagingDataAdapter<ArticleUi, RecyclerView.ViewHolder>(ArticleUiDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder =
        AllNewsViewHolder.create(parent)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val articleUi = getItem(position)
        articleUi?.let {
            if (holder is AllNewsViewHolder) {
                holder.bind(
                    articleUi = articleUi,
                    listener = listener,
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_all_news
    }

    class AllNewsViewHolder(
        private val binding: ItemAllNewsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            articleUi: ArticleUi,
            listener: AllNewsRecyclerViewClickListener,
        ) {
            with(binding) {
                this.root.setOnClickListener {
                    listener.onArticleClick(articleUi.url)
                }

                if(articleUi.urlToImage == null) {
                    articleImageView.setImageResource(articleUi.placeholder)
                } else {
                    Glide.with(articleImageView.context)
                        .load(articleUi.urlToImage)
                        .error(articleUi.placeholder)
                        .into(articleImageView)
                }

                titleTextView.text = articleUi.title
                sourceTextView.text = articleUi.sourceName
                dateTextView.text = articleUi.publishedAt
            }
        }

        companion object {
            @JvmStatic
            fun create(parent: ViewGroup): AllNewsViewHolder =
                AllNewsViewHolder(
                    ItemAllNewsBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false,
                    ),
                )
        }
    }
}

interface AllNewsRecyclerViewClickListener {
    fun onArticleClick(topHeadlineUrl: String)
}