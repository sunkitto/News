package com.sunkitto.news.core.model.settings

import android.os.Parcelable
import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Language(
    @StringRes val nameId: Int,
    val isoCode: String,
) : Parcelable {
    FOLLOW_SYSTEM(R.string.follow_system, ""),
    ENGLISH(R.string.english, "en"),
    POLISH(R.string.polish, "pl"),
    UKRAINIAN(R.string.ukrainian, "uk"),
}