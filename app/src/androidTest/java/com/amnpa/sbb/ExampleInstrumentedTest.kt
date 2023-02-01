package com.amnpa.sbb

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amnpa.sbb.model.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun checkRowAdapterTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val comps = listOf(
            Competition("Poland", listOf(), 1, "Ekstraklasa"),
            Competition("Poland", listOf(), 2, "1 Liga"),
            Competition("Germany", listOf(), 3, "Bundesliga")

        )
        val checkRowAdapter = CheckRowAdapter(appContext, comps)
        assertEquals(3, checkRowAdapter.count)
        assertEquals(1, checkRowAdapter.getItem(0).competition.competitionId)
        assertEquals("Poland", checkRowAdapter.getItem(1).competition.country)
        assertEquals("Bundesliga", checkRowAdapter.getItem(2).competition.name)

    }

    @Test
    fun gameAdapterTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val games = mutableListOf(
            Game("Tue, 10 Jan 2023 20:59:37 GMT", 1.2, 1, 1,
                1, "Barcelona", 0.7, "Real Madryt", 2.1),
            Game("Tue, 10 Jan 2023 21:59:37 GMT", 1.2, 2, 2,
                1, "Raków", 0.5, "Wisła", 3.1),
            Game("Tue, 10 Jan 2023 22:59:37 GMT", 0.2, 3, 2,
                1, "Legia", 0.7, "Śląsk", 0.1),
        )
        val gamesAdapter = GameAdapter(games, HashMap(), 1)
        assertEquals(gamesAdapter.itemCount, 3)
    }
}