package com.sunkitto.news.feature.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme

class PreferencesManager(private val context: Context) {
    fun switchLanguage(language: Language) {
        when (language) {
            Language.FOLLOW_SYSTEM -> {
                AppCompatDelegate
                    .setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            context.resources.configuration.locales.get(0).isO3Language,
                        ),
                    )
            }
            Language.ENGLISH -> {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(Language.ENGLISH.isoCode),
                )
            }
            Language.POLISH -> {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(Language.POLISH.isoCode),
                )
            }
        }
    }

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