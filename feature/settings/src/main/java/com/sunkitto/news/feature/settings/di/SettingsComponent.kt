package com.sunkitto.news.feature.settings.di

import com.sunkitto.news.core.domain.repository.SettingsRepository
import com.sunkitto.news.feature.settings.PreferencesManager
import com.sunkitto.news.feature.settings.SettingsFragment
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates

@SettingsScope
@Component(
    dependencies = [SettingsDependencies::class],
    modules = [SettingsModel::class],
)
interface SettingsComponent {

    fun inject(fragment: SettingsFragment)

    @Component.Builder
    interface Builder {

        fun dependencies(settingsDependencies: SettingsDependencies): Builder

        fun build(): SettingsComponent
    }
}

interface SettingsDependencies {

    val settingsRepository: SettingsRepository

    val preferencesManager: PreferencesManager
}

interface SettingsDependencyProvider {

    val dependencies: SettingsDependencies

    companion object : SettingsDependencyProvider by SettingsDependencyStore
}

object SettingsDependencyStore : SettingsDependencyProvider {

    override var dependencies: SettingsDependencies by Delegates.notNull()
}

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class SettingsScope