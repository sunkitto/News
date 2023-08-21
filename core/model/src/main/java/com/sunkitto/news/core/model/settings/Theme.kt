package com.sunkitto.news.core.model.settings

import android.os.Parcelable
import androidx.annotation.StringRes
import com.sunkitto.news.core.model.R
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Theme(@StringRes val nameId: Int) : Parcelable {
    FOLLOW_SYSTEM(R.string.follow_system),
    LIGHT(R.string.light),
    DARK(R.string.dark),
}