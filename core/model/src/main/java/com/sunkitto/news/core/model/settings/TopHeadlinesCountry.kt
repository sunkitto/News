package com.sunkitto.news.core.model.settings

import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R

enum class TopHeadlinesCountry(
    val index: Int,
    @StringRes val nameId: Int,
    val isoCode: String
) {
    CZECHIA(0, R.string.czechia,"cz"),
    GERMANY(1, R.string.germany,"de"),
    LITHUANIA(2, R.string.lithuania,"lt"),
    LATVIA(3, R.string.latvia,"lv"),
    POLAND(4, R.string.poland,"pl"),
    UKRAINE(5, R.string.ukraine,"ua"),
    USA(6, R.string.usa,"us"),
}