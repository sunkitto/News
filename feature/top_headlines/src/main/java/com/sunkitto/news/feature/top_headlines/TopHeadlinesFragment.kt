package com.sunkitto.news.feature.top_headlines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.sunkitto.news.feature.top_headlines.adapter.GroupVerticalItemDecorator
import com.sunkitto.news.feature.top_headlines.adapter.TopHeadlinesAdapter
import com.sunkitto.news.feature.top_headlines.adapter.TopHeadlinesLoadStateAdapter
import com.sunkitto.news.feature.top_headlines.databinding.FragmentTopHeadlinesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopHeadlinesFragment : Fragment() {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding: FragmentTopHeadlinesBinding
        get() = _binding!!

    private val viewModel: TopHeadlinesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.categoriesChipGroup.setOnCheckedStateChangeListener { group, _ ->
            viewModel.setCategory(binding.root.getViewById(group.checkedChipId) as Chip)
        }

        binding.topHeadlinesRecyclerView.addItemDecoration(
            GroupVerticalItemDecorator(R.layout.item_top_headline, 100, 0)
        )

        val topHeadlinesAdapter = TopHeadlinesAdapter()
        topHeadlinesAdapter.withLoadStateFooter(TopHeadlinesLoadStateAdapter())

        binding.topHeadlinesSwipeRefreshLayout.setOnRefreshListener {
            topHeadlinesAdapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collectLatest { pagingData ->
                    topHeadlinesAdapter.submitData(pagingData)
                }
            }
        }
        binding.topHeadlinesRecyclerView.adapter = topHeadlinesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}