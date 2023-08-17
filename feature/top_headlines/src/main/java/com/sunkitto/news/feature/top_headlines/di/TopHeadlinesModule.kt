package com.sunkitto.news.feature.top_headlines.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.core.domain.GetTopHeadlinesUseCase
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.feature.top_headlines.TopHeadlinesViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
class TopHeadlinesModule {

    @Provides
    fun provideGetTopHeadlinesUseCase(
        newsRepository: NewsRepository,
    ) =
        GetTopHeadlinesUseCase(newsRepository = newsRepository)

    @Provides
    @TopHeadlinesViewModelFactory
    fun provideTopHeadlinesViewModelFactory(
        getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                TopHeadlinesViewModel(getTopHeadlinesUseCase = getTopHeadlinesUseCase)
            }
        }

    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class TopHeadlinesViewModelFactory
}