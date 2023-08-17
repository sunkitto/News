package com.sunkitto.news.feature.settings.di

import androidx.lifecycle.ViewModel

class SettingsComponentViewModel : ViewModel() {

    val settingsComponent: SettingsComponent by lazy {
        DaggerSettingsComponent.builder()
            .dependencies(SettingsDependencyProvider.dependencies)
            .build()
    }
}