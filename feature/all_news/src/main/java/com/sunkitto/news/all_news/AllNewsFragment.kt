package com.sunkitto.news.all_news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sunkitto.news.all_news.adapters.AllNewsAdapter
import com.sunkitto.news.all_news.adapters.AllNewsLoadStateAdapter
import com.sunkitto.news.all_news.databinding.FragmentAllNewsBinding
import com.sunkitto.news.design_system.GroupVerticalItemDecorator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AllNewsFragment : Fragment() {

    private var _binding: FragmentAllNewsBinding? = null
    private val binding: FragmentAllNewsBinding
        get() = _binding!!

    private val viewModel: AllNewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchView.editText
            .setOnEditorActionListener { textView, _, _ ->
                viewModel.searchNews(textView.text.toString())
                return@setOnEditorActionListener false
            }

        binding.allNewsRecyclerView.addItemDecoration(
            GroupVerticalItemDecorator(R.layout.item_all_news, 100, 0)
        )

        val topHeadlinesAdapter = AllNewsAdapter()
        topHeadlinesAdapter.withLoadStateFooter(AllNewsLoadStateAdapter())

        binding.allNewsSwipeRefreshLayout.setOnRefreshListener {
            topHeadlinesAdapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collectLatest { pagingData ->
                    topHeadlinesAdapter.submitData(pagingData)
                }
            }
        }
        binding.allNewsRecyclerView.adapter = topHeadlinesAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}