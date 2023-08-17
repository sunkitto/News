package com.sunkitto.news.feature.top_headlines

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.google.android.material.chip.Chip
import com.sunkitto.news.core.model.SharedConstants.REFRESH_REQUEST_KEY
import com.sunkitto.news.design_system.GroupVerticalItemDecorator
import com.sunkitto.news.design_system.HorizontalItemDecorator
import com.sunkitto.news.feature.top_headlines.adapter.TopHeadlinesAdapter
import com.sunkitto.news.feature.top_headlines.adapter.TopHeadlinesLoadStateAdapter
import com.sunkitto.news.feature.top_headlines.adapter.TopHeadlinesRecyclerViewClickListener
import com.sunkitto.news.feature.top_headlines.databinding.FragmentTopHeadlinesBinding
import com.sunkitto.news.feature.top_headlines.di.TopHeadlinesComponentViewModel
import com.sunkitto.news.feature.top_headlines.di.TopHeadlinesModule.TopHeadlinesViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopHeadlinesFragment : Fragment(), TopHeadlinesRecyclerViewClickListener {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding: FragmentTopHeadlinesBinding
        get() = _binding!!

    private val topHeadlinesComponentViewModel: TopHeadlinesComponentViewModel by viewModels()

    @Inject
    @TopHeadlinesViewModelFactory
    lateinit var topHeadlinesViewModelFactory: ViewModelProvider.Factory

    private val viewModel: TopHeadlinesViewModel by viewModels {
        topHeadlinesViewModelFactory
    }

    private var refreshLoadState: LoadState? = null

    override fun onAttach(context: Context) {
        topHeadlinesComponentViewModel.topHeadlinesComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val topHeadlinesAdapter = TopHeadlinesAdapter(this)

        requireActivity().supportFragmentManager
            .setFragmentResultListener(REFRESH_REQUEST_KEY, viewLifecycleOwner) { _, _ ->
                topHeadlinesAdapter.refresh()
            }

        with(binding) {
            categoriesChipGroup.forEach { child ->
                (child as Chip).setOnCheckedChangeListener { _, _ ->
                    viewModel.setCategory(child)
                }
            }
        }

        with(binding.topHeadlinesRecyclerView) {
            topHeadlinesAdapter.withLoadStateFooter(TopHeadlinesLoadStateAdapter())
            adapter = topHeadlinesAdapter

            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_top_headline, 0, 50)
            )
            addItemDecoration(
                GroupVerticalItemDecorator(R.layout.item_top_headline, 50, 0)
            )
            addItemDecoration(
                HorizontalItemDecorator(100)
            )
        }

        with(binding.topHeadlinesSwipeRefreshLayout) {
            setOnRefreshListener {
                topHeadlinesAdapter.refresh()
                isRefreshing =
                    when(refreshLoadState) {
                        is LoadState.Loading -> true
                        is LoadState.NotLoading -> false
                        is LoadState.Error -> false
                        null -> false
                    }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collectLatest {
                    topHeadlinesAdapter.submitData(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                topHeadlinesAdapter.loadStateFlow.collectLatest { loadStates ->
                    refreshLoadState = loadStates.mediator?.refresh
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTopHeadlineClick(topHeadlineUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(topHeadlineUrl)
        startActivity(intent)
    }
}