package com.sunkitto.news.feature.topHeadlines

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sunkitto.news.feature.topHeadlines.fragment.TestTopHeadlinesFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TopHeadlinesFragmentTest {

    @Test
    fun top_headlines_recycler_view_shows_when_fragment_launch() {
        launchFragmentInContainer(
            themeResId = com.sunkitto.news.core.design_system.R.style.Fragment_Test,
        ) {
            TestTopHeadlinesFragment()
        }

        onView(withId(R.id.topHeadlinesRecyclerView)).check(matches(isDisplayed()))
    }
}