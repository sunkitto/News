package com.sunkitto.news.core.model.settings

import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R

enum class Language(
    val index: Int,
    @StringRes val nameId: Int,
    val isoCode: String,
) {
    FOLLOW_SYSTEM(0, R.string.follow_system, ""),
    ENGLISH(1, R.string.english, "en"),
    POLISH(2, R.string.polish, "pl"),
}