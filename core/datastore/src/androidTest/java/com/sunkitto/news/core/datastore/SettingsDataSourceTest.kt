package com.sunkitto.news.core.datastore

import android.content.Context
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SettingsDataSourceTest {

    private lateinit var subject: SettingsDataSource

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @BeforeEach
    fun createDataStore() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val settingsDataStore = DataStoreFactory.create(
            serializer = SettingsPreferencesSerializer,
            scope = testScope,
            produceFile = {
                context.dataStoreFile("testSettingsPreferences.json")
            }
        )

        subject = SettingsDataSource(
            settingsDataStore = settingsDataStore
        )
    }

    @Test
    fun writeAndReadToSettingsDataStore() =
        testScope.runTest {
            val expected = SettingsPreferences(
                language = Language.POLISH,
                theme = Theme.DARK,
                topHeadlinesCountry = TopHeadlinesCountry.POLAND
            )
            subject.apply {
                setLanguage(Language.POLISH)
                setTheme(Theme.DARK)
                setTopHeadlinesCountry(TopHeadlinesCountry.POLAND)
            }
            val actual = subject.settings.first()
            assertEquals(expected, actual)
        }
}