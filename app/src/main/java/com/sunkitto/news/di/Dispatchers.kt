package com.sunkitto.news.di

import javax.inject.Qualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class Dispatcher(val newsDispatcher: NewsDispatchers)

enum class NewsDispatchers {
    DEFAULT,
    IO,
}