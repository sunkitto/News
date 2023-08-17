package com.sunkitto.news.di.modules

import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.NewsNetworkDataSourceImpl
import com.sunkitto.news.di.NewsApiKeyQualifier
import dagger.Module
import dagger.Provides

@Module
object NetworkModule {

    @Provides
    fun provideNewsNetworkDataSource(
        @NewsApiKeyQualifier apiKey: String,
    ): NewsNetworkDataSource =
        NewsNetworkDataSourceImpl(apiKey = apiKey)
}