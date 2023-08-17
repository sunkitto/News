package com.sunkitto.news.all_news.di

import androidx.lifecycle.ViewModel

class AllNewsComponentViewModel : ViewModel() {

    val allNewsComponent: AllNewsComponent by lazy {
        DaggerAllNewsComponent.builder()
            .dependencies(AllNewsDependencyProvider.dependencies)
            .build()
    }
}