package com.sunkitto.news.core.datastore

import com.sunkitto.news.core.datastore.model.SettingsPreferences
import com.sunkitto.news.core.datastore.serializers.SettingsPreferencesSerializer
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SettingsPreferencesSerializerTest {

    @Test
    fun settings_preferences_serialize_and_deserialize() = runTest {
        val serializer = SettingsPreferencesSerializer
        val output = ByteArrayOutputStream()

        val settingsPreferences = SettingsPreferences(
            language = Language.ENGLISH,
            theme = Theme.DARK,
            topHeadlinesCountry = TopHeadlinesCountry.ARGENTINA,
        )
        serializer.writeTo(settingsPreferences, output)

        val inputStream = ByteArrayInputStream(output.toByteArray())
        val actualSettings = serializer.readFrom(inputStream)

        assertEquals(settingsPreferences, actualSettings)
    }
}