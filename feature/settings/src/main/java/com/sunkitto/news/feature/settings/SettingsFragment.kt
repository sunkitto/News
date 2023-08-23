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
import com.sunkitto.news.core.model.settings.Language
import com.sunkitto.news.core.model.settings.Theme
import com.sunkitto.news.core.model.settings.TopHeadlinesCountry
import com.sunkitto.news.feature.settings.databinding.FragmentSettingsBinding
import com.sunkitto.news.feature.settings.di.SettingsComponentViewModel
import com.sunkitto.news.feature.settings.di.SettingsModel.SettingsViewModelFactory
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment.Companion.LANGUAGE_DIALOG_REQUEST_KEY
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment.Companion.SELECTED_LANGUAGE_KEY
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment.Companion.SELECTED_THEME_KEY
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment.Companion.THEME_DIALOG_REQUEST_KEY
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment.Companion.SELECTED_TOP_HEADLINE_KEY
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment.Companion.TOP_HEADLINES_DIALOG_REQUEST_KEY
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

        @Suppress("DEPRECATION")
        requireActivity().supportFragmentManager.apply {
            setFragmentResultListener(
                LANGUAGE_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                val language: Language = bundle.getParcelable(SELECTED_LANGUAGE_KEY)!!
                viewModel.setLanguage(language)
                binding.languagePreference.descriptionText = getString(language.nameId)
            }
            setFragmentResultListener(
                TOP_HEADLINES_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                val topHeadlinesCountry: TopHeadlinesCountry = bundle.getParcelable(
                    SELECTED_TOP_HEADLINE_KEY
                )!!
                viewModel.setTopHeadlinesCountry(topHeadlinesCountry)
                binding.topHeadlinesCountryPreference.descriptionText =
                    getString(topHeadlinesCountry.nameId)
            }
            setFragmentResultListener(
                THEME_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                val theme: Theme = bundle.getParcelable(SELECTED_THEME_KEY)!!
                viewModel.setTheme(theme)
                binding.themePreference.descriptionText = getString(theme.nameId)
            }
        }

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
                LanguageDialogFragment
                    .newInstance(
                        checkedItemIndex = viewModel.settings.value.language.ordinal
                    )
                    .show(
                        childFragmentManager,
                        LanguageDialogFragment.TAG,
                    )
            }

            topHeadlinesCountryPreference.setOnClickListener {
                TopHeadlinesCountryDialogFragment
                    .newInstance(
                        checkedItemIndex = viewModel.settings.value.topHeadlinesCountry.ordinal,
                    )
                    .show(
                        childFragmentManager,
                        TopHeadlinesCountryDialogFragment.TAG,
                    )

                requireActivity().supportFragmentManager
                    .setFragmentResult(REFRESH_REQUEST_KEY, bundleOf())
            }

            themePreference.setOnClickListener {
                ThemeDialogFragment
                    .newInstance(
                        checkedItemIndex = viewModel.settings.value.theme.ordinal
                    )
                    .show(
                        childFragmentManager,
                        ThemeDialogFragment.TAG,
                    )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}