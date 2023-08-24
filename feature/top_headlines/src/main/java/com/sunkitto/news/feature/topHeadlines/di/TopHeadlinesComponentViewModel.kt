package com.sunkitto.news.feature.topHeadlines.di

import androidx.lifecycle.ViewModel

class TopHeadlinesComponentViewModel : ViewModel() {

    val topHeadlinesComponent: TopHeadlinesComponent by lazy {
        DaggerTopHeadlinesComponent.builder()
            .dependencies(TopHeadlinesDependencyProvider.dependencies)
            .build()
    }
}