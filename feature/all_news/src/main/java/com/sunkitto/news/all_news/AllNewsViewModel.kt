package com.sunkitto.news.all_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.sunkitto.news.core.data.asArticleUi
import com.sunkitto.news.core.design_system.R
import com.sunkitto.news.core.domain.GetAllNewsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AllNewsViewModel(
    private val getAllNewsUseCase: GetAllNewsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles = searchQuery.flatMapLatest { searchQuery ->
        getAllNewsUseCase.invoke(query = searchQuery)
            .map { pagingData ->
                pagingData.map { article ->
                    article.asArticleUi(R.drawable.placeholder)
                }
            }
            .cachedIn(viewModelScope)
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            searchQuery.emit(query)
        }
    }
}