package com.sunkitto.news.feature.settings

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunkitto.news.feature.settings.dialogs.LanguageDialogFragment
import com.sunkitto.news.feature.settings.dialogs.ThemeDialogFragment
import com.sunkitto.news.feature.settings.dialogs.TopHeadlinesCountryDialogFragment
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DialogFragmentTest {

    @Test
    fun testDismissLanguageDialogFragment() {
        with(launchFragment<LanguageDialogFragment>(
            themeResId = com.sunkitto.news.core.design_system.R.style.AlertDialog_Test,
            fragmentArgs = bundleOf()
        )) {
            onFragment { fragment ->
                assertNotNull(fragment.dialog)
                assertTrue(fragment.requireDialog().isShowing)
                fragment.dismiss()
                fragment.parentFragmentManager.executePendingTransactions()
                assertNull(fragment.dialog)
            }
        }
        onView(withText(R.string.cancel)).check(doesNotExist())
    }

    @Test
    fun testDismissTopHeadlinesCountryDialogFragment() {
        with(launchFragment<TopHeadlinesCountryDialogFragment>(
            themeResId = com.sunkitto.news.core.design_system.R.style.AlertDialog_Test,
            fragmentArgs = bundleOf()
        )) {
            onFragment { fragment ->
                assertNotNull(fragment.dialog)
                assertTrue(fragment.requireDialog().isShowing)
                fragment.dismiss()
                fragment.parentFragmentManager.executePendingTransactions()
                assertNull(fragment.dialog)
            }
        }
        onView(withText(R.string.cancel)).check(doesNotExist())
    }

    @Test
    fun testDismissThemeDialogFragment() {
        with(launchFragment<ThemeDialogFragment>(
            themeResId = com.sunkitto.news.core.design_system.R.style.AlertDialog_Test,
            fragmentArgs = bundleOf()
        )) {
            onFragment { fragment ->
                assertNotNull(fragment.dialog)
                assertTrue(fragment.requireDialog().isShowing)
                fragment.dismiss()
                fragment.parentFragmentManager.executePendingTransactions()
                assertNull(fragment.dialog)
            }
        }
        onView(withText(R.string.cancel)).check(doesNotExist())
    }
}