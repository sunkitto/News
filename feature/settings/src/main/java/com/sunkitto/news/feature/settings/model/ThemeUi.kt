package com.sunkitto.news.feature.settings.model

import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.feature.settings.adapter.SettingsItem

data class ThemeUi(
    val theme: Theme,
    val title: String,
    val description: String,
) : SettingsItem {

    override val id: Int = description.hashCode()

    override fun payload(other: Any): SettingsItem.Payloadable {
        if(other is ThemeUi) {
            return ChangePayload.DescriptionChanged(description = other.description)
        }
        return SettingsItem.Payloadable.None
    }

    sealed class ChangePayload: SettingsItem.Payloadable {
        data class DescriptionChanged(val description: String): ChangePayload()
    }
}