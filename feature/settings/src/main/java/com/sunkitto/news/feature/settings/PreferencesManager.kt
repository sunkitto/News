package com.sunkitto.news.feature.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme

class PreferencesManager(private val context: Context) {
    fun switchLanguage(language: Language) =
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                if (language == Language.FOLLOW_SYSTEM) {
                    context.resources.configuration.locales.get(0).isO3Language
                } else {
                    language.isoCode
                },
            ),
        )

    fun switchTheme(theme: Theme) {
        AppCompatDelegate.setDefaultNightMode(theme.value)
    }
}