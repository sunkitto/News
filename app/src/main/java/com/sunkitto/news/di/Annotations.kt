package com.sunkitto.news.di

import javax.inject.Qualifier
import javax.inject.Scope

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class ApplicationScope

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class NewsApiKeyQualifier

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationContext