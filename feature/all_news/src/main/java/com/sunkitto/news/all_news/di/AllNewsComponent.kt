package com.sunkitto.news.all_news.di

import com.sunkitto.news.all_news.AllNewsFragment
import com.sunkitto.news.core.domain.repository.NewsRepository
import com.sunkitto.news.core.domain.repository.RecentSearchRepository
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@AllNewsScope
@Component(
    dependencies = [AllNewsDependencies::class],
    modules = [AllNewsModule::class],
)
interface AllNewsComponent {

    fun inject(fragment: AllNewsFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(allNewsDependencies: AllNewsDependencies): Builder

        fun build(): AllNewsComponent
    }
}

interface AllNewsDependencies {

    val newsRepository: NewsRepository
    val recentSearchRepository: RecentSearchRepository
}

interface AllNewsDependencyProvider {

    val dependencies: AllNewsDependencies

    companion object : AllNewsDependencyProvider by AllNewsDependencyStore
}

object AllNewsDependencyStore : AllNewsDependencyProvider {

    override var dependencies: AllNewsDependencies by Delegates.notNull()
}

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class AllNewsScope