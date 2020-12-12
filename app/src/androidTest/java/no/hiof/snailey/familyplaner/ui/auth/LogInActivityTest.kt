package no.hiof.snailey.familyplaner.ui.auth

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4

import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import no.hiof.snailey.familyplaner.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LogInActivityTest

    @get:Rule
    var activityRule: ActivityScenarioRule<LogInActivity>
            = ActivityScenarioRule(LogInActivity::class.java)

    private lateinit var stringToBetyped: String

   @Before
    fun initValidString() {
        // Specify a valid string.
        stringToBetyped = "Espresso"
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("no.hiof.snailey.familyplaner", appContext.packageName)
    }

    @Test
    fun changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUserInput))
            .perform(typeText(stringToBetyped)
        onView(withId(R.id.changeTextBt)).perform(click())

        // Check that the text was changed.
        onView(withId(R.id.textToBeChanged))
            .check(matches(withText(stringToBetyped)))
    }
