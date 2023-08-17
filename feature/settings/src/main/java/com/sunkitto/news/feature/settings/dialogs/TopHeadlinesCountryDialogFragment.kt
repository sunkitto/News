package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.feature.settings.R

class TopHeadlinesCountryDialogFragment(
    private val checkedItem: Int,
    private val onClick: (topHeadlinesCountry: TopHeadlinesCountry) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val topHeadlinesCountries = TopHeadlinesCountry.values()
        val topHeadlinesCountryNames = topHeadlinesCountries
            .map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.top_headlines_country)
            .setSingleChoiceItems(topHeadlinesCountryNames, checkedItem) { dialog, which ->
                onClick(topHeadlinesCountries[which])
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "TopHeadlinesCountryDialog"
    }
}