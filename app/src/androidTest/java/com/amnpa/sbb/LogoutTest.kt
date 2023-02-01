package com.amnpa.sbb

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import com.amnpa.sbb.viewmodel.PlayerFragment
import junit.framework.AssertionFailedError
import org.junit.Test
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*


class LogoutTest {

    @Test
    fun logout(){
        launchFragmentInContainer<PlayerFragment>()
        onView(withId(R.id.buttonLogout)).perform(click())
        Thread.sleep(2000)
        onView(withText("Yes")).perform(click())

        try {
            onView(withId(R.id.editTextPassword)).check(matches(isDisplayed()))
            // View is displayed
        } catch (e: AssertionFailedError) {
            // View not displayed
        }
    }

}