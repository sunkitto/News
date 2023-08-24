package com.sunkitto.news.feature.settings.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sunkitto.news.feature.settings.databinding.ItemSettingsBinding
import com.sunkitto.news.feature.settings.model.LanguageUi
import com.sunkitto.news.feature.settings.model.ThemeUi
import com.sunkitto.news.feature.settings.model.TopHeadlinesCountryUi

fun languageAdapterDelegate(onLanguageClick: () -> Unit) =
    adapterDelegateViewBinding<LanguageUi, SettingsItem, ItemSettingsBinding>(
        { layoutInflater, parent -> ItemSettingsBinding.inflate(layoutInflater, parent, false) }
    ) {
        bind {
            binding.root.setOnClickListener {
                onLanguageClick()
            }
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
        }
    }

fun topHeadlinesCountryAdapterDelegate(onTopHeadlinesCountryClick: () -> Unit) =
    adapterDelegateViewBinding<TopHeadlinesCountryUi, SettingsItem, ItemSettingsBinding>(
        { layoutInflater, parent -> ItemSettingsBinding.inflate(layoutInflater, parent, false) }
    ) {
        bind {
            binding.root.setOnClickListener {
                onTopHeadlinesCountryClick()
            }
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
        }
    }

fun themeAdapterDelegate(onThemeClick: () -> Unit) =
    adapterDelegateViewBinding<ThemeUi, SettingsItem, ItemSettingsBinding>(
        { layoutInflater, parent -> ItemSettingsBinding.inflate(layoutInflater, parent, false) }
    ) {
        bind {
            binding.root.setOnClickListener {
                onThemeClick()
            }
            binding.titleTextView.text = item.title
            binding.descriptionTextView.text = item.description
        }
    }