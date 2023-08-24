package com.sunkitto.news.feature.settings.adapter

interface SettingsItem {

    val id: Int

    fun payload(other: Any): Payloadable = Payloadable.None

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int

    /**
     * Simple marker interface for payloads
     */
    interface Payloadable {
        object None: Payloadable
    }
}