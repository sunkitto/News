package com.sunkitto.news.core.model.settings

import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R

enum class Theme(
    val index: Int,
    @StringRes val nameId: Int,
) {
    FOLLOW_SYSTEM(0, R.string.follow_system),
    LIGHT(1, R.string.light),
    DARK(2, R.string.dark),
}