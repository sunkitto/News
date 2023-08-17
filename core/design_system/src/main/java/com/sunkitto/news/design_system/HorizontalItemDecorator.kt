package com.sunkitto.news.design_system

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalItemDecorator(
    private val divider: Int
) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val oneSideDivider = divider / 2

        with(outRect) {
            left = oneSideDivider
            right = oneSideDivider
        }
    }
}