package com.amnpa.sbb

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import junit.framework.AssertionFailedError
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard as closeSoftKeyboard1


class LeagueCreationTest {

    @get : Rule
    var mainActivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init(){
        mainActivityRule.activity.goToFragment(3)
    }

    @Test
    fun logout(){
        onView(withId(R.id.buttonCreateLeague)).perform(click())
        Thread.sleep(2000)

        onView(withId(R.id.editTextLeagueName)).perform(click())
        onView(withId(R.id.editTextLeagueName)).perform(typeText("TEST LEAGUE"))
        onView(withResourceName("checkbox")).perform(click())
        Thread.sleep(2000)


        onView(withId(R.id.buttonCommitLeagueCreation)).perform(click())

        Thread.sleep(2000)

        try {
            onView(withText("TEST LEAGUE")).check(matches(isDisplayed()))
            // View is displayed
            onView(withText("TEST LEAGUE")).perform(click())
            Thread.sleep(2000)
            onView(withId(R.id.buttonQuitLeague)).perform(click())
        } catch (e: AssertionFailedError) {
            // View not displayed
        }
    }
}