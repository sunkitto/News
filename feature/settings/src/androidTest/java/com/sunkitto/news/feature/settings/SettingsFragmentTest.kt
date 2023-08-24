package com.sunkitto.news.feature.settings

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunkitto.news.feature.settings.fragment.TestSettingsFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest {

    @Test
    fun settings_recycler_view_shows_with_settings() {

        launchFragmentInContainer(themeResId = R.style.Fragment_Test,) {
            TestSettingsFragment()
        }

        onView(withId(R.id.preferencesRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<ViewHolder>(
                    hasDescendant(withText(R.string.interface_language)),
                ),
            )

        onView(withId(R.id.preferencesRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<ViewHolder>(
                    hasDescendant(withText(R.string.top_headlines_country)),
                ),
            )

        onView(withId(R.id.preferencesRecyclerView))
            .perform(
                RecyclerViewActions.scrollTo<ViewHolder>(
                    hasDescendant(withText(R.string.theme)),
                ),
            )
    }

    @Test
    fun language_item_click_shows_language_dialog() {

        launchFragmentInContainer(themeResId = R.style.Fragment_Test,) {
            TestSettingsFragment()
        }

        onView(withId(R.id.preferencesRecyclerView))
            .perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        onView(withText(R.string.interface_language)).inRoot(isDialog()).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
        onView(withText(com.sunkitto.news.core.model.R.string.english))
            .perform(click())
    }

    @Test
    fun top_headlines_country_item_click_shows_top_headlines_country_dialog() {

        launchFragmentInContainer(themeResId = R.style.Fragment_Test,) {
            TestSettingsFragment()
        }

        onView(withId(R.id.preferencesRecyclerView))
            .perform(actionOnItemAtPosition<ViewHolder>(1, click()))

        onView(withText(R.string.top_headlines_country)).inRoot(isDialog()).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
        onView(withText(com.sunkitto.news.core.model.R.string.united_kingdom))
            .perform(click())
    }

    @Test
    fun theme_item_click_shows_theme_dialog() {

        launchFragmentInContainer(themeResId = R.style.Fragment_Test,) {
            TestSettingsFragment()
        }

        onView(withId(R.id.preferencesRecyclerView))
            .perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        onView(withText(R.string.theme)).inRoot(isDialog()).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        )
        onView(withText(com.sunkitto.news.core.model.R.string.dark))
            .perform(click())
    }
}