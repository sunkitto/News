package com.sunkitto.news.feature.top_headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.material.chip.Chip
import com.sunkitto.news.core.data.asArticleUi
import com.sunkitto.news.core.domain.GetTopHeadlinesUseCase
import com.sunkitto.news.core.model.TopHeadlinesCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale
import com.sunkitto.news.core.design_system.R as DesignSystem

class TopHeadlinesViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
) : ViewModel() {

    private val category = MutableStateFlow(TopHeadlinesCategory.GENERAL)

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles = category.flatMapLatest { topHeadlinesCategory ->
        getTopHeadlinesUseCase.invoke(category = topHeadlinesCategory)
            .map { pagingData ->
                pagingData.map { article ->
                    article.asArticleUi(DesignSystem.drawable.placeholder)
                }
            }
            .cachedIn(viewModelScope)
    }

    fun setCategory(chip: Chip) {
        viewModelScope.launch {
            val selectedCategory = TopHeadlinesCategory.valueOf(chip.text.toString().uppercase(Locale.ROOT))
            category.emit(selectedCategory)
        }
    }
}