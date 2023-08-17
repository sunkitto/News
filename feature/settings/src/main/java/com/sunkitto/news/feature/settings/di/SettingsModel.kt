package com.sunkitto.news.feature.settings.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.feature.settings.PreferencesManager
import com.sunkitto.news.feature.settings.SettingsViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Module
object SettingsModel {

    @Provides
    @SettingsViewModelFactory
    fun provideSettingsViewModelFactory(
        settingsRepository: SettingsRepository,
        preferencesManager: PreferencesManager,
    ): ViewModelProvider.Factory =
        viewModelFactory {
            initializer {
                SettingsViewModel(
                    settingsRepository = settingsRepository,
                    preferencesManager = preferencesManager,
                )
            }
        }

    @MustBeDocumented
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class SettingsViewModelFactory
}