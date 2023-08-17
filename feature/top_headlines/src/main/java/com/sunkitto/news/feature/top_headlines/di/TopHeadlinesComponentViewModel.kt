package com.sunkitto.news.feature.top_headlines.di

import androidx.lifecycle.ViewModel

class TopHeadlinesComponentViewModel : ViewModel() {

    val topHeadlinesComponent: TopHeadlinesComponent by lazy {
        DaggerTopHeadlinesComponent.builder()
            .dependencies(TopHeadlinesDependencyProvider.dependencies)
            .build()
    }
}