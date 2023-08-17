package com.sunkitto.news.all_news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunkitto.news.all_news.R
import com.sunkitto.news.all_news.databinding.ItemRecentSearchBinding
import com.sunkitto.news.core.model.RecentSearch

class RecentSearchAdapter(
    private val listener: RecentSearchRecyclerViewOnClickListener
) : ListAdapter<RecentSearch, RecentSearchAdapter.ViewHolder>(RecentSearchDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder =
        ViewHolder(
            ItemRecentSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        getItem(position).let { recentSearch ->
            with(holder.binding) {
                this.root.setOnClickListener {
                    listener.onRecentSearchClick(recentSearch.query)
                }
                deleteImageView.setOnClickListener {
                    listener.onRecentSearchDelete(recentSearch)
                }
                queryTextView.text = recentSearch.query
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_recent_search
    }

    class ViewHolder(val binding: ItemRecentSearchBinding): RecyclerView.ViewHolder(binding.root)

    object RecentSearchDiffCallback : DiffUtil.ItemCallback<RecentSearch>() {
        override fun areItemsTheSame(
            oldItem: RecentSearch,
            newItem: RecentSearch
        ): Boolean =
            oldItem.query == newItem.query

        override fun areContentsTheSame(
            oldItem: RecentSearch,
            newItem: RecentSearch
        ): Boolean =
            oldItem == newItem
    }
}

interface RecentSearchRecyclerViewOnClickListener {

    fun onRecentSearchClick(query: String)

    fun onRecentSearchDelete(recentSearch: RecentSearch)
}