package com.sunkitto.news.feature.topHeadlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sunkitto.news.feature.top_headlines.databinding.ItemLoadStateBinding

class TopHeadlinesLoadStateAdapter : LoadStateAdapter<TopHeadlinesLoadStateAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ViewHolder =
        ViewHolder(
            ItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )

    override fun onBindViewHolder(
        holder: ViewHolder,
        loadState: LoadState,
    ) {
        holder.binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    class ViewHolder(val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root)
}