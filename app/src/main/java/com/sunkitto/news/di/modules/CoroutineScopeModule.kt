package com.sunkitto.news.di.modules

import com.sunkitto.news.di.Dispatcher
import com.sunkitto.news.di.NewsDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
object CoroutineScopesModule {

    @Provides
    fun providesCoroutineScope(
        @Dispatcher(NewsDispatchers.IO) ioDispatcher: CoroutineDispatcher,
    ): CoroutineScope =
        CoroutineScope(SupervisorJob() + ioDispatcher)
}