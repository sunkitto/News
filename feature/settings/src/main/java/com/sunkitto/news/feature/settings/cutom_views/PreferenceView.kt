package com.sunkitto.news.feature.settings.cutom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunkitto.news.feature.settings.R
import com.sunkitto.news.feature.settings.databinding.PreferenceViewBinding

class PreferenceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: PreferenceViewBinding
    var descriptionText: String = ""
        set(value) {
            field = value
            binding.descriptionTextView.text = value
        }

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.preference_view, this, true)
        binding = PreferenceViewBinding.bind(this)
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int,
    ) {
        if (attrs == null) return
        val typedArray = context
            .obtainStyledAttributes(attrs, R.styleable.PreferenceView, defStyleAttr, defStyleRes)

        val titleTextView = typedArray.getString(R.styleable.PreferenceView_titleTextViewText)
        binding.titleTextView.text = titleTextView
        val descriptionTextView = typedArray.getString(
            R.styleable.PreferenceView_descriptionTextViewText,
        )
        binding.descriptionTextView.text = descriptionTextView

        typedArray.recycle()
    }
}