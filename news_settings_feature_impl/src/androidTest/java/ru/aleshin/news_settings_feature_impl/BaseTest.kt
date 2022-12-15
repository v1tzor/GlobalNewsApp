package ru.aleshin.news_settings_feature_impl

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.aleshin.news_settings_feature_impl.presentation.ui.settings.SettingsFragment

/**
 * @author Stanislav Aleshin on 04.11.2022.
 */
@RunWith(value = AndroidJUnit4::class)
internal class BaseTest {

    private lateinit var scenario: FragmentScenario<SettingsFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = ru.aleshin.core_ui.R.style.Theme_App_GlobalNews)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun test() {
        onView(withId(R.id.mainTitle)).check(matches(withText(R.string.main_settings_title)))
    }
}