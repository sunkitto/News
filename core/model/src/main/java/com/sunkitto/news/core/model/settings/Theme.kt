package com.sunkitto.news.core.model.settings

import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R

enum class Theme(@StringRes val nameId: Int) {
    FOLLOW_SYSTEM(R.string.follow_system),
    LIGHT(R.string.light),
    DARK(R.string.dark),
}