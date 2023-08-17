package com.sunkitto.news.di

import android.content.Context
import com.sunkitto.news.MainActivity
import com.sunkitto.news.all_news.di.AllNewsDependencies
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.di.modules.AppModule
import com.sunkitto.news.di.modules.CoroutineScopesModule
import com.sunkitto.news.di.modules.DataStoreModule
import com.sunkitto.news.di.modules.DatabaseModule
import com.sunkitto.news.di.modules.DispatchersModule
import com.sunkitto.news.di.modules.NetworkModule
import com.sunkitto.news.feature.settings.PreferencesManager
import com.sunkitto.news.feature.settings.di.SettingsDependencies
import com.sunkitto.news.feature.top_headlines.di.TopHeadlinesDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        DataStoreModule::class,
        DispatchersModule::class,
        NetworkModule::class,
        CoroutineScopesModule::class,
    ]
)
interface AppComponent : TopHeadlinesDependencies, AllNewsDependencies, SettingsDependencies {

    override val newsRepository: NewsRepository
    override val settingsRepository: SettingsRepository
    override val recentSearchRepository: RecentSearchRepository
    override val preferencesManager: PreferencesManager

    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(@ApplicationContext context: Context): Builder

        @BindsInstance
        fun apiKey(@NewsApiKeyQualifier apiKey: String): Builder

        fun build(): AppComponent
    }
}

