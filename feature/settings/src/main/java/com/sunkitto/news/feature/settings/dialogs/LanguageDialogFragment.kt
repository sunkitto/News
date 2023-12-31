package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
                requireArguments().getInt(CURRENT_LANGUAGE_KEY),
            ) { dialog, which ->
                requireActivity().supportFragmentManager
                    .setFragmentResult(
                        LANGUAGE_DIALOG_REQUEST_KEY,
                        bundleOf(Pair(SELECTED_LANGUAGE_KEY, languages[which])),
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
        const val CURRENT_LANGUAGE_KEY = "CURRENT_LANGUAGE_KEY"

        @JvmStatic
        fun newInstance(currentLanguage: Int): LanguageDialogFragment {
            val fragment = LanguageDialogFragment()
            val bundle = Bundle()
            bundle.putInt(CURRENT_LANGUAGE_KEY, currentLanguage)
            fragment.arguments = bundle
            return fragment
        }
    }
}