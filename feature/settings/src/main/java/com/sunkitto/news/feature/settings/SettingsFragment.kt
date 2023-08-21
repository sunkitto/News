package com.sunkitto.news.feature.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sunkitto.news.core.model.SharedConstants.REFRESH_REQUEST_KEY
import com.sunkitto.news.feature.settings.databinding.FragmentSettingsBinding
import com.sunkitto.news.feature.settings.di.SettingsComponentViewModel
import com.sunkitto.news.feature.settings.di.SettingsModel.SettingsViewModelFactory
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment
import javax.inject.Inject

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding!!

    private val settingsComponentViewModel: SettingsComponentViewModel by viewModels()

    @Inject
    @SettingsViewModelFactory
    internal lateinit var settingsViewModelFactory: ViewModelProvider.Factory

    private val viewModel: SettingsViewModel by viewModels {
        settingsViewModelFactory
    }

    override fun onAttach(context: Context) {
        settingsComponentViewModel.settingsComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            languagePreference.descriptionText = getString(
                viewModel.settings.value.language.nameId,
            )
            topHeadlinesCountryPreference.descriptionText = getString(
                viewModel.settings.value.topHeadlinesCountry.nameId,
            )
            themePreference.descriptionText = getString(
                viewModel.settings.value.theme.nameId,
            )

            languagePreference.setOnClickListener {
                val currentLanguageIndex = viewModel.settings.value.language.ordinal
                LanguageDialogFragment(currentLanguageIndex) { language ->
                    viewModel.setLanguage(language)
                    languagePreference.descriptionText = getString(language.nameId)
                }.show(
                    childFragmentManager,
                    LanguageDialogFragment.TAG,
                )
            }

            topHeadlinesCountryPreference.setOnClickListener {
                val currentTopHeadlinesCountryIndex =
                    viewModel.settings.value.topHeadlinesCountry.ordinal

                TopHeadlinesCountryDialogFragment(
                    currentTopHeadlinesCountryIndex,
                ) { topHeadlinesCountry ->
                    viewModel.setTopHeadlinesCountry(topHeadlinesCountry)
                    topHeadlinesCountryPreference.descriptionText =
                        getString(topHeadlinesCountry.nameId)
                }.show(
                    childFragmentManager,
                    TopHeadlinesCountryDialogFragment.TAG,
                )

                requireActivity().supportFragmentManager
                    .setFragmentResult(REFRESH_REQUEST_KEY, bundleOf())
            }

            themePreference.setOnClickListener {
                val currentThemeIndex = viewModel.settings.value.theme.ordinal

                ThemeDialogFragment(currentThemeIndex) { theme ->
                    viewModel.setTheme(theme)
                    themePreference.descriptionText = getString(theme.nameId)
                }.show(
                    childFragmentManager,
                    ThemeDialogFragment.TAG,
                )
            }
        }
    }
}