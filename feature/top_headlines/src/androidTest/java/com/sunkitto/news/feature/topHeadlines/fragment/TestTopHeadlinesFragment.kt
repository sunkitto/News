package com.sunkitto.news.feature.topHeadlines.fragment

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.core.domain.GetTopHeadlinesUseCase
import com.sunkitto.news.feature.topHeadlines.TopHeadlinesFragment
import com.sunkitto.news.feature.topHeadlines.TopHeadlinesViewModel
import io.mockk.mockk

class TestTopHeadlinesFragment : TopHeadlinesFragment() {

    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = mockk(relaxed = true)

    override fun injectMembers() {
        this.topHeadlinesViewModelFactory = viewModelFactory {
            initializer {
                TopHeadlinesViewModel(
                    getTopHeadlinesUseCase = getTopHeadlinesUseCase,
                )
            }
        }
    }
}