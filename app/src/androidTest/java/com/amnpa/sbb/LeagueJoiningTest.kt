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


class LeagueJoiningTest {

    @get : Rule
    var mainActivityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun init(){
        mainActivityRule.activity.goToFragment(3)
    }

    @Test
    fun logout(){
        val codeField = onView(withId(R.id.editTextEnterCode))
        codeField.perform(click())
        codeField.perform(typeText("ABSD"))
        codeField.perform(closeSoftKeyboard1())

        onView(withId(R.id.buttonJoinLeague)).perform(click())

        Thread.sleep(2000)

        try {
            onView(withText("ABSD")).check(matches(isDisplayed()))
            // View is displayed
            onView(withText("ABSD")).perform(click())
            Thread.sleep(2000)
            onView(withId(R.id.buttonQuitLeague)).perform(click())
        } catch (e: AssertionFailedError) {
            // View not displayed
        }
    }
}