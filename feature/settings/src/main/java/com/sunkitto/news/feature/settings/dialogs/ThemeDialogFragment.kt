package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.feature.settings.R

class ThemeDialogFragment(
    private val checkedItem: Int,
    private val onClick: (theme: Theme) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val themes = Theme.values()
        val themeNames = themes.map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.theme)
            .setSingleChoiceItems(themeNames, checkedItem) { dialog, which ->
                onClick(themes[which])
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "ThemeDialog"
    }
}