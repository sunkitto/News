package com.sunkitto.news.feature.settings.model

import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.feature.settings.adapter.SettingsItem

data class TopHeadlinesCountryUi(
    val topHeadlinesCountry: TopHeadlinesCountry,
    val title: String,
    val description: String,
) : SettingsItem {

    override val id: Int = description.hashCode()

    override fun payload(other: Any): SettingsItem.Payloadable {
        if(other is TopHeadlinesCountryUi) {
            return ChangePayload.DescriptionChanged(description = other.description)
        }
        return SettingsItem.Payloadable.None
    }

    sealed class ChangePayload: SettingsItem.Payloadable {
        data class DescriptionChanged(val description: String): ChangePayload()
    }
}
