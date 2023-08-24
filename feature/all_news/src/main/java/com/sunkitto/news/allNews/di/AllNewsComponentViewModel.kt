package com.sunkitto.news.allNews.di

import androidx.lifecycle.ViewModel

class AllNewsComponentViewModel : ViewModel() {

    val allNewsComponent: AllNewsComponent by lazy {
        DaggerAllNewsComponent.builder()
            .dependencies(AllNewsDependencyProvider.dependencies)
            .build()
    }
}