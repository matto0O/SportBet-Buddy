package com.amnpa.sbb

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amnpa.sbb.viewmodel.AuthActivity
import org.junit.runner.RunWith
import org.junit.Test
import org.junit.Rule
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard as closeSoftKeyboard1

@RunWith(AndroidJUnit4::class)
class LoginFragmentDissolutionTest {

    @get : Rule
    var authActivityRule = ActivityScenarioRule(AuthActivity::class.java)

    @Test
    fun logIn(){
        onView(withId(R.id.editTextUsername)).perform(click())
        onView(withId(R.id.editTextUsername)).perform(typeText("Adam"))
        onView(withId(R.id.editTextUsername)).perform(closeSoftKeyboard1())
        onView(withId(R.id.editTextPassword)).perform(click())
        onView(withId(R.id.editTextPassword)).perform(typeText("Ekstraklasa"))
        onView(withId(R.id.editTextPassword)).perform(closeSoftKeyboard1())
        onView(withId(R.id.buttonSubmit)).perform(click())

        Thread.sleep(2000)

        println(authActivityRule.scenario.state)
        assert(authActivityRule.scenario.state == Lifecycle.State.DESTROYED)
    }

}