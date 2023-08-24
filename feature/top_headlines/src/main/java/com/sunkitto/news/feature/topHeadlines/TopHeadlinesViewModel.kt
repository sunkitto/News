package com.sunkitto.news.feature.topHeadlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.google.android.material.chip.Chip
import com.sunkitto.news.core.data.asArticleUi
import com.sunkitto.news.core.domain.GetTopHeadlinesUseCase
import com.sunkitto.news.core.model.TopHeadlinesCategory
import com.sunkitto.news.core.model.ui.ArticleUi
import com.sunkitto.news.feature.top_headlines.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class TopHeadlinesViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
) : ViewModel() {

    private val category = MutableStateFlow(TopHeadlinesCategory.GENERAL)

    val articles: Flow<PagingData<ArticleUi>> = category
        .flatMapLatest { topHeadlinesCategory ->
            getTopHeadlinesUseCase.invoke(category = topHeadlinesCategory)
        }
        .map { pagingData ->
            pagingData.map { article ->
                article.asArticleUi(com.sunkitto.news.core.design_system.R.drawable.placeholder)
            }
        }
        .cachedIn(viewModelScope)

    fun setCategory(chip: Chip) {
        viewModelScope.launch {
            val selectedCategory = when (chip.id) {
                R.id.generalChip -> TopHeadlinesCategory.GENERAL
                R.id.businessChip -> TopHeadlinesCategory.BUSINESS
                R.id.technologyChip -> TopHeadlinesCategory.TECHNOLOGY
                R.id.healthChip -> TopHeadlinesCategory.HEALTH
                R.id.scienceChip -> TopHeadlinesCategory.SCIENCE
                R.id.entertainmentChip -> TopHeadlinesCategory.ENTERTAINMENT
                R.id.sportsChip -> TopHeadlinesCategory.SPORTS
                else -> throw IllegalStateException("Category doesn't handled for $chip")
            }
            category.emit(selectedCategory)
        }
    }
}