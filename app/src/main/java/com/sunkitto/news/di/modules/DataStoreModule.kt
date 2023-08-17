package com.sunkitto.news.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.sunkitto.news.core.datastore.SettingsDataSource
import com.sunkitto.news.core.datastore.model.SettingsPreferences
import com.sunkitto.news.core.datastore.serializers.SettingsPreferencesSerializer
import com.sunkitto.news.di.ApplicationContext
import com.sunkitto.news.di.ApplicationScope
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun providesSettingsDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope coroutineScope: CoroutineScope
    ) =
        DataStoreFactory.create(
            serializer = SettingsPreferencesSerializer,
            scope = coroutineScope,
            produceFile = {
                context.dataStoreFile(SettingsDataSource.SETTINGS_DATA_STORE_FILE_NAME)
            }
        )

    @Provides
    fun provideSettingsDataSource(
        settingsDataStore: DataStore<SettingsPreferences>
    ): SettingsDataSource =
        SettingsDataSource(settingsDataStore = settingsDataStore)
}