package com.sunkitto.news.core.model.settings

import android.os.Parcelable
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.sunkitto.news.core.model.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Theme(
    @StringRes val nameId: Int,
    val value: Int,
) : Parcelable {
    FOLLOW_SYSTEM(
        R.string.follow_system,
        MODE_NIGHT_FOLLOW_SYSTEM,
    ),
    LIGHT(
        R.string.light,
        MODE_NIGHT_NO,
    ),
    DARK(
        R.string.dark,
        MODE_NIGHT_YES,
    ),
}