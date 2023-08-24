package com.sunkitto.news.feature.top_headlines.fragment

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.core.domain.GetTopHeadlinesUseCase
import com.sunkitto.news.feature.top_headlines.TopHeadlinesFragment
import com.sunkitto.news.feature.top_headlines.TopHeadlinesViewModel
import io.mockk.mockk

class TestTopHeadlinesFragment : TopHeadlinesFragment() {

    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase = mockk(relaxed = true)

    override fun injectMembers() {

        this.topHeadlinesViewModelFactory = viewModelFactory {
            initializer {
                TopHeadlinesViewModel(
                    getTopHeadlinesUseCase = getTopHeadlinesUseCase
                )
            }
        }
    }
}