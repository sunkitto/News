package com.sunkitto.news.feature.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sunkitto.news.core.model.SharedConstants.REFRESH_REQUEST_KEY
import com.sunkitto.news.feature.settings.adapter.SettingsAdapter
import com.sunkitto.news.feature.settings.databinding.FragmentSettingsBinding
import com.sunkitto.news.feature.settings.di.SettingsComponentViewModel
import com.sunkitto.news.feature.settings.di.SettingsModel.SettingsViewModelFactory
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment.Companion.LANGUAGE_DIALOG_REQUEST_KEY
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment.Companion.SELECTED_LANGUAGE_KEY
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment.Companion.SELECTED_THEME_KEY
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment.Companion.THEME_DIALOG_REQUEST_KEY
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment.Companion.SELECTED_TOP_HEADLINE_KEY
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment.Companion.TOP_HEADLINES_DIALOG_REQUEST_KEY
import com.sunkitto.news.feature.settings.model.LanguageUi
import com.sunkitto.news.feature.settings.model.ThemeUi
import com.sunkitto.news.feature.settings.model.TopHeadlinesCountryUi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        val settingAdapter = SettingsAdapter(
            viewModel = viewModel,
            childFragmentManager = childFragmentManager,
        )
        binding.preferencesRecyclerView.adapter = settingAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.settings.collectLatest { settings ->
                    settingAdapter.items = listOf(
                        LanguageUi(
                            language = settings.language,
                            title = getString(R.string.interface_language),
                            description = getString(settings.language.nameId),
                        ),
                        TopHeadlinesCountryUi(
                            topHeadlinesCountry = settings.topHeadlinesCountry,
                            title = getString(R.string.top_headlines_country),
                            description = getString(settings.topHeadlinesCountry.nameId)
                        ),
                        ThemeUi(
                            theme = settings.theme,
                            title = getString(R.string.theme),
                            description = getString(settings.theme.nameId)
                        )
                    )
                }
            }
        }

        @Suppress("DEPRECATION")
        requireActivity().supportFragmentManager.apply {
            setFragmentResultListener(
                LANGUAGE_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                viewModel.setLanguage(
                    bundle.getParcelable(SELECTED_LANGUAGE_KEY)!!
                )
            }
            setFragmentResultListener(
                TOP_HEADLINES_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                viewModel.setTopHeadlinesCountry(
                    bundle.getParcelable(SELECTED_TOP_HEADLINE_KEY)!!
                )
                requireActivity().supportFragmentManager
                    .setFragmentResult(REFRESH_REQUEST_KEY, bundleOf())
            }
            setFragmentResultListener(
                THEME_DIALOG_REQUEST_KEY,
                viewLifecycleOwner,
            ) { _, bundle ->
                viewModel.setTheme(
                    bundle.getParcelable(SELECTED_THEME_KEY)!!
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}