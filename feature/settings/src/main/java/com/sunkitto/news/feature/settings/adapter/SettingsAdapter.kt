package com.sunkitto.news.feature.settings.adapter

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.sunkitto.news.feature.settings.SettingsViewModel
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment

class SettingsAdapter(
    viewModel: SettingsViewModel,
    childFragmentManager: FragmentManager,
) : AsyncListDifferDelegationAdapter<SettingsItem>(DIFF_CALLBACK) {

    init {
        delegatesManager.addDelegate(
            languageAdapterDelegate {
                LanguageDialogFragment
                    .newInstance(viewModel.settings.value.language.ordinal)
                    .show(childFragmentManager, LanguageDialogFragment.TAG)
            }
        )
        delegatesManager.addDelegate(
            topHeadlinesCountryAdapterDelegate {
                TopHeadlinesCountryDialogFragment
                    .newInstance(viewModel.settings.value.topHeadlinesCountry.ordinal)
                    .show(childFragmentManager, TopHeadlinesCountryDialogFragment.TAG)
            }
        )
        delegatesManager.addDelegate(
            themeAdapterDelegate {
                ThemeDialogFragment
                    .newInstance(currentTheme = viewModel.settings.value.theme.ordinal)
                    .show(childFragmentManager, ThemeDialogFragment.TAG)
            }
        )
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SettingsItem>() {

            override fun areItemsTheSame(
                oldItem: SettingsItem,
                newItem: SettingsItem,
            ): Boolean =
                oldItem::class == newItem::class && oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SettingsItem,
                newItem: SettingsItem,
            ): Boolean =
                oldItem == newItem

            override fun getChangePayload(
                oldItem: SettingsItem,
                newItem: SettingsItem
            ): Any =
                oldItem.payload(newItem)
        }
    }
}