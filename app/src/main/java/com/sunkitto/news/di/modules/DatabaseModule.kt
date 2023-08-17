package com.sunkitto.news.di.modules

import android.content.Context
import androidx.room.Room
import com.sunkitto.news.core.database.NewsDatabase
import com.sunkitto.news.di.ApplicationContext
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    fun provideNewsDatabase(
        @ApplicationContext context: Context
    ): NewsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = NewsDatabase::class.java,
            name = "news.db"
        ).build()
}