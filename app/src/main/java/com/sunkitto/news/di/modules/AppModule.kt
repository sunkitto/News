package com.sunkitto.news.di.modules

import android.content.Context
import com.sunkitto.news.core.data.repository.NewsRepositoryImpl
import com.sunkitto.news.core.data.repository.RecentSearchRepositoryImpl
import com.sunkitto.news.core.data.repository.SettingsRepositoryImpl
import com.sunkitto.news.core.database.NewsDatabase
import com.sunkitto.news.core.datastore.SettingsDataSource
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.di.ApplicationContext
import com.sunkitto.news.feature.settings.PreferencesManager
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    fun provideNewsRepository(
        newsDatabase: NewsDatabase,
        newsNetworkDataSource: NewsNetworkDataSource,
        settingsRepository: SettingsRepository
    ): NewsRepository =
        NewsRepositoryImpl(
            topHeadlinesDao = newsDatabase.topHeadlinesDao(),
            articlesDao = newsDatabase.articlesDao(),
            newsNetworkDataSource = newsNetworkDataSource,
            topHeadlinesRemoteKeyDao = newsDatabase.topHeadlinesRemoteKeysDao(),
            allNewsRemoteKeyDao = newsDatabase.allNewsRemoteKeysDao(),
            settingsRepository = settingsRepository
        )

    @Provides
    fun provideSettingsRepository(
        settingsDataSource: SettingsDataSource
    ): SettingsRepository =
        SettingsRepositoryImpl(settingsDataSource = settingsDataSource)

    @Provides
    fun provideRecentSearchRepository(
        newsDatabase: NewsDatabase,
    ): RecentSearchRepository =
        RecentSearchRepositoryImpl(recentSearchDao = newsDatabase.recentSearchDao())

    @Provides
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ): PreferencesManager
        = PreferencesManager(context = context)
}