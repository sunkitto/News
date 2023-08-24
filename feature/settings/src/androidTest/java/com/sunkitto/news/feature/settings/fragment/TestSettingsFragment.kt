package com.sunkitto.news.feature.settings.fragment

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sunkitto.news.core.data.repository.SettingsRepositoryImpl
import com.sunkitto.news.feature.settings.PreferencesManager
import com.sunkitto.news.feature.settings.SettingsFragment
import com.sunkitto.news.feature.settings.SettingsViewModel
import io.mockk.mockk

class TestSettingsFragment : SettingsFragment() {

    private val settingsRepository: SettingsRepositoryImpl = mockk(relaxed = true)
    private val preferencesManager: PreferencesManager = mockk(relaxed = true)

    override fun injectMembers() {
        this.settingsViewModelFactory = viewModelFactory {
            initializer {
                SettingsViewModel(
                    settingsRepository,
                    preferencesManager,
                )
            }
        }
    }
}