package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.feature.settings.R

class LanguageDialogFragment(
    private val checkedItemIndex: Int,
    private val onClick: (language: Language) -> Unit,
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val languages = Language.values()
        val languageNames = languages.map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.interface_language)
            .setSingleChoiceItems(languageNames, checkedItemIndex) { dialog, which ->
                onClick(languages[which])
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "LanguageDialog"
    }
}