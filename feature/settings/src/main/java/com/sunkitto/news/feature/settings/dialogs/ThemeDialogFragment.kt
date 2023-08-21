package com.sunkitto.news.feature.settings.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.feature.settings.R

class ThemeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val themes = Theme.values()
        val themeNames = themes.map { getString(it.nameId) }.toTypedArray()

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.theme)
            .setSingleChoiceItems(
                themeNames,
                requireArguments().getInt(THEME_CHECKED_ITEM_INDEX_KEY),
            ) { dialog, which ->
                val selectedTheme = themes[which]
                requireActivity().supportFragmentManager
                    .setFragmentResult(
                        THEME_DIALOG_REQUEST_KEY,
                        bundleOf(Pair(SELECTED_THEME_KEY, selectedTheme)),
                    )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .create()
    }

    companion object {
        const val TAG = "ThemeDialog"
        const val THEME_DIALOG_REQUEST_KEY = "THEME_DIALOG_REQUEST_KEY"
        const val SELECTED_THEME_KEY = "SELECTED_THEME_KEY"
        const val THEME_CHECKED_ITEM_INDEX_KEY = "THEME_CHECKED_ITEM_INDEX_KEY"

        @JvmStatic
        fun newInstance(checkedItemIndex: Int): ThemeDialogFragment {
            val fragment = ThemeDialogFragment()
            val bundle = Bundle()
            bundle.putInt(THEME_CHECKED_ITEM_INDEX_KEY, checkedItemIndex)
            fragment.arguments = bundle
            return fragment
        }
    }
}