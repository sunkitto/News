package com.sunkitto.news.di

import android.app.Application
import com.sunkitto.news.core.network.NewsNetworkDataSource
import com.sunkitto.news.core.network.NewsNetworkDataSourceImpl
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiKey(@NewsApiKeyQualifier apiKey: String): Builder

        fun build(): AppComponent
    }
}

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideNewsNetworkDataSource(
        @NewsApiKeyQualifier apiKey: String
    ): NewsNetworkDataSource =
        NewsNetworkDataSourceImpl(apiKey = apiKey)
}

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class AppScope

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NewsApiKeyQualifier