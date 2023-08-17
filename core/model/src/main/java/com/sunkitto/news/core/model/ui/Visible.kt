package com.sunkitto.news.core.model.ui

import android.view.View

/**
 * Wrapper on nullable value of type [T].
 */
data class Visible<T>(val value: T?) {
    /**
     * Returns [View.GONE] if value is null, otherwise [View.VISIBLE].
     */
    fun isVisible(): Int =
        if (value == null) {
            View.GONE
        } else {
            View.VISIBLE
        }
}