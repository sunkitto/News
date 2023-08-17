package com.sunkitto.news.all_news.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.all_news.AllNewsViewModel
import com.sunkitto.news.core.domain.GetAllNewsUseCase
import com.sunkitto.news.core.domain.GetRecentSearchesUseCase
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object AllNewsModule {

    @Provides
    fun providesGetRecentSearchUseCase(
        recentSearchRepository: RecentSearchRepository,
    ): GetRecentSearchesUseCase =
        GetRecentSearchesUseCase(recentSearchRepository = recentSearchRepository)

    @Provides
    fun provideGetAllNewsUseCase(newsRepository: NewsRepository): GetAllNewsUseCase =
        GetAllNewsUseCase(newsRepository = newsRepository)

    @Provides
    @AllNewsViewModelFactory
    fun provideAllNewsViewModelFactory(
        getAllNewsUseCase: GetAllNewsUseCase,
        getRecentSearchesUseCase: GetRecentSearchesUseCase,
        recentSearchRepository: RecentSearchRepository,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                AllNewsViewModel(
                    getAllNewsUseCase = getAllNewsUseCase,
                    getRecentSearchesUseCase = getRecentSearchesUseCase,
                    recentSearchRepository = recentSearchRepository,
                )
            }
        }

    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class AllNewsViewModelFactory
}