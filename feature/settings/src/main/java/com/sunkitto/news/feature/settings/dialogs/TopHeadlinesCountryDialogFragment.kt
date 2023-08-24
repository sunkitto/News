package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.feature.settings.R

class TopHeadlinesCountryDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val topHeadlinesCountries = TopHeadlinesCountry.values()
        val topHeadlinesCountryNames = topHeadlinesCountries
            .map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.top_headlines_country)
            .setSingleChoiceItems(
                topHeadlinesCountryNames,
                requireArguments().getInt(CURRENT_TOP_HEADLINES_COUNTRY_KEY),
            ) { dialog, which ->
                val selectedTopHeadlinesCountry = topHeadlinesCountries[which]
                requireActivity().supportFragmentManager
                    .setFragmentResult(
                        TOP_HEADLINES_DIALOG_REQUEST_KEY,
                        bundleOf(
                            Pair(
                                SELECTED_TOP_HEADLINE_KEY,
                                selectedTopHeadlinesCountry,
                            ),
                        ),
                    )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "TopHeadlinesCountryDialog"
        const val TOP_HEADLINES_DIALOG_REQUEST_KEY = "TOP_HEADLINES_DIALOG_REQUEST_KEY"
        const val SELECTED_TOP_HEADLINE_KEY = "SELECTED_TOP_HEADLINE_KEY"
        const val CURRENT_TOP_HEADLINES_COUNTRY_KEY = "CURRENT_TOP_HEADLINES_COUNTRY_KEY"

        @JvmStatic
        fun newInstance(currentTopHeadlinesCountry: Int): TopHeadlinesCountryDialogFragment {
            val fragment = TopHeadlinesCountryDialogFragment()
            val bundle = Bundle()
            bundle.putInt(CURRENT_TOP_HEADLINES_COUNTRY_KEY, currentTopHeadlinesCountry)
            fragment.arguments = bundle
            return fragment
        }
    }
}