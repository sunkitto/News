package com.sunkitto.news.feature.settings.model

import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.feature.settings.adapter.SettingsItem

data class LanguageUi(
    val language: Language,
    val title: String,
    val description: String,
) : SettingsItem {

    override val id: Int = description.hashCode()

    override fun payload(other: Any): SettingsItem.Payloadable {
        if (other is LanguageUi) {
            return ChangePayload.DescriptionChanged(description = other.description)
        }
        return SettingsItem.Payloadable.None
    }

    sealed class ChangePayload : SettingsItem.Payloadable {
        data class DescriptionChanged(val description: String) : ChangePayload()
    }
}
