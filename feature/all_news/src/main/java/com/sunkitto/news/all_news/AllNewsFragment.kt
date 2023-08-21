package com.sunkitto.news.all_news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sunkitto.news.all_news.adapters.AllNewsAdapter
import com.sunkitto.news.all_news.adapters.AllNewsLoadStateAdapter
import com.sunkitto.news.all_news.adapters.AllNewsRecyclerViewClickListener
import com.sunkitto.news.all_news.adapters.RecentSearchAdapter
import com.sunkitto.news.all_news.adapters.RecentSearchRecyclerViewOnClickListener
import com.sunkitto.news.all_news.databinding.FragmentAllNewsBinding
import com.sunkitto.news.all_news.di.AllNewsComponentViewModel
import com.sunkitto.news.all_news.di.AllNewsModule.AllNewsViewModelFactory
import com.sunkitto.news.core.model.RecentSearch
import com.sunkitto.news.design_system.GroupVerticalItemDecorator
import com.sunkitto.news.design_system.HorizontalItemDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllNewsFragment :
    Fragment(),
    AllNewsRecyclerViewClickListener,
    RecentSearchRecyclerViewOnClickListener {

    private var _binding: FragmentAllNewsBinding? = null
    private val binding: FragmentAllNewsBinding
        get() = _binding!!

    private val allNewsComponentViewModel: AllNewsComponentViewModel by viewModels()

    @Inject
    @AllNewsViewModelFactory
    internal lateinit var allNewsViewModelFactory: ViewModelProvider.Factory

    private val viewModel: AllNewsViewModel by viewModels {
        allNewsViewModelFactory
    }

    override fun onAttach(context: Context) {
        allNewsComponentViewModel.allNewsComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAllNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val allNewsAdapter = AllNewsAdapter(this)
        val recentSearchAdapter = RecentSearchAdapter(this)

        viewModel.loadRecentSearches()

        binding.searchView
            .editText
            .setOnEditorActionListener { _, _, _ ->
                viewModel.searchNews(binding.searchView.text.toString())
                binding.searchView.hide()
                return@setOnEditorActionListener false
            }

        with(binding.allNewsRecyclerView) {
            allNewsAdapter.withLoadStateFooter(AllNewsLoadStateAdapter())
            adapter = allNewsAdapter
            addItemDecoration(
                HorizontalItemDecorator(100),
            )
            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_all_news, 0, 50),
            )
            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_all_news, 50, 0),
            )
        }

        with(binding.recentSearchesRecyclerView) {
            adapter = recentSearchAdapter
            addItemDecoration(
                HorizontalItemDecorator(100),
            )
            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_recent_search, 0, 50),
            )
            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_recent_search, 50, 0),
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collectLatest { pagingData ->
                    allNewsAdapter.submitData(pagingData)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recentSearches.collectLatest { recentSearches ->
                    recentSearchAdapter.submitList(recentSearches)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onArticleClick(topHeadlineUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(topHeadlineUrl)
        startActivity(intent)
    }

    override fun onRecentSearchClick(query: String) {
        binding.searchView.hide()
        viewModel.searchNews(query)
    }

    override fun onRecentSearchDelete(recentSearch: RecentSearch) {
        viewModel.deleteRecentSearch(recentSearch)
    }
}