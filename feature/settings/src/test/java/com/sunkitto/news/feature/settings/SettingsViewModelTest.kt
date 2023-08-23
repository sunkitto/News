package com.sunkitto.news.feature.settings

import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.feature.settings.repository.TestSettingsRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {

    private val preferencesManager: PreferencesManager = mockk(relaxed = true)
    private val settingsRepository = TestSettingsRepository()

    private lateinit var settingsViewModel: SettingsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        settingsViewModel = SettingsViewModel(
            settingsRepository = settingsRepository,
            preferencesManager = preferencesManager,
        )
    }

    @Test
    fun settings_state_is_updated_when_language_is_set() {
        settingsViewModel.setLanguage(Language.POLISH)
        assertEquals(
            Language.POLISH,
            settingsViewModel.settings.value.language
        )
    }

    @Test
    fun settings_state_is_updated_when_top_headlines_country_is_set() {
        settingsViewModel.setTopHeadlinesCountry(TopHeadlinesCountry.ARGENTINA)
        assertEquals(
            TopHeadlinesCountry.ARGENTINA,
            settingsViewModel.settings.value.topHeadlinesCountry
        )
    }

    @Test
    fun settings_state_is_updated_when_theme_is_set() {
        settingsViewModel.setTheme(Theme.DARK)
        assertEquals(
            Theme.DARK,
            settingsViewModel.settings.value.theme
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}