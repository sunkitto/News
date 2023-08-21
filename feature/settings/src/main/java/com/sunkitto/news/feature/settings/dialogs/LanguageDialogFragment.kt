package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.SharedConstants
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.feature.settings.R

class LanguageDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val languages = Language.values()
        val languageNames = languages.map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.interface_language)
            .setSingleChoiceItems(
                languageNames,
                requireArguments().getInt(LANGUAGE_CHECKED_ITEM_INDEX_KEY),
            ) { dialog, which ->
                val selectedLanguage = languages[which]
                requireActivity().supportFragmentManager
                    .setFragmentResult(
                        SharedConstants.REFRESH_REQUEST_KEY,
                        bundleOf(Pair(SELECTED_LANGUAGE_KEY, selectedLanguage)),
                    )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "LanguageDialog"
        const val LANGUAGE_DIALOG_REQUEST_KEY = "LANGUAGE_DIALOG_REQUEST_KEY"
        const val SELECTED_LANGUAGE_KEY = "SELECTED_LANGUAGE_KEY"
        const val LANGUAGE_CHECKED_ITEM_INDEX_KEY = "LANGUAGE_CHECKED_ITEM_INDEX_KEY"

        @JvmStatic
        fun newInstance(checkedItemIndex: Int): LanguageDialogFragment {
            val fragment = LanguageDialogFragment()
            val bundle = Bundle()
            bundle.putInt(LANGUAGE_CHECKED_ITEM_INDEX_KEY, checkedItemIndex)
            fragment.arguments = bundle
            return fragment
        }
    }
}