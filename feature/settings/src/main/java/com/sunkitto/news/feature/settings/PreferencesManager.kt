package com.sunkitto.news.feature.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme

class PreferencesManager(private val context: Context) {
    fun switchLanguage(language: Language) =
        AppCompatDelegate.setApplicationLocales(
            when(language) {
                Language.FOLLOW_SYSTEM -> LocaleListCompat.forLanguageTags(
                    context.resources.configuration.locales.get(0).isO3Language,
                )
                else -> LocaleListCompat.forLanguageTags(language.isoCode)
            }
        )

    fun switchTheme(theme: Theme) {
        when (theme) {
            Theme.FOLLOW_SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                )
            }
            Theme.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO,
                )
            }
            Theme.DARK -> {
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES,
                )
            }
        }
    }
}