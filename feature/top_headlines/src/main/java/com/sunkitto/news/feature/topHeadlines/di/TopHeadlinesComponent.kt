package com.sunkitto.news.feature.topHeadlines.di

import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.feature.topHeadlines.TopHeadlinesFragment
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@TopHeadlinesScope
@Component(
    dependencies = [TopHeadlinesDependencies::class],
    modules = [TopHeadlinesModule::class],
)
interface TopHeadlinesComponent {

    fun inject(fragment: TopHeadlinesFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(topHeadlinesDependencies: TopHeadlinesDependencies): Builder

        fun build(): TopHeadlinesComponent
    }
}

interface TopHeadlinesDependencies {

    val newsRepository: NewsRepository
    val settingsRepository: SettingsRepository
}

interface TopHeadlinesDependencyProvider {

    val dependencies: TopHeadlinesDependencies

    companion object : TopHeadlinesDependencyProvider by TopHeadlinesDependencyStore
}

object TopHeadlinesDependencyStore : TopHeadlinesDependencyProvider {

    override var dependencies: TopHeadlinesDependencies by Delegates.notNull()
}

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class TopHeadlinesScope