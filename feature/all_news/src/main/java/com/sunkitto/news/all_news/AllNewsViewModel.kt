package com.sunkitto.news.all_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sunkitto.news.core.data.asArticleUi
import com.sunkitto.news.core.domain.GetAllNewsUseCase
import com.sunkitto.news.core.domain.GetRecentSearchesUseCase
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import com.sunkitto.news.core.model.RecentSearch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@OptIn(ExperimentalCoroutinesApi::class)
class AllNewsViewModel(
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val recentSearchRepository: RecentSearchRepository,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private var _recentSearches = MutableStateFlow<List<RecentSearch>>(listOf())
    val recentSearches = _recentSearches.asStateFlow()

    val articles = searchQuery
        .flatMapLatest { searchQuery ->
            getAllNewsUseCase.invoke(query = searchQuery)
                .map { pagingData ->
                    pagingData.map { article ->
                        article.asArticleUi(
                            com.sunkitto.news.core.design_system.R.drawable.placeholder,
                        )
                    }
                }
        }
        .cachedIn(viewModelScope)

    fun searchNews(query: String) {
        if (query.isNotBlank()) {
            viewModelScope.launch {
                recentSearchRepository.upsertRecentSearch(
                    RecentSearch(
                        query = query,
                        date = Clock.System.now(),
                    ),
                )
                searchQuery.emit(query)
                loadRecentSearches()
            }
        }
    }

    fun deleteRecentSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            recentSearchRepository.deleteRecentSearch(recentSearch)
            loadRecentSearches()
        }
    }

    fun loadRecentSearches() {
        viewModelScope.launch {
            getRecentSearchesUseCase.invoke().collectLatest { recentSearches ->
                _recentSearches.value = recentSearches
            }
        }
    }
}