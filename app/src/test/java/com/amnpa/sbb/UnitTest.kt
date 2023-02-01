package com.amnpa.sbb


import com.amnpa.sbb.model.*
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.IOException



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {

    @Test
    fun handleStatsTest() {
        val stats = Stats
        val data = StatsData(10.0f, 20, 5.0f,
            5, "Barcelona", 0.5f)
        stats.handleStats(data)
        assertEquals(stats.map["Total points"], 10.0f)
        assertEquals(stats.map["Total events bet on"], 20)
        assertEquals(stats.map["Success rate"], "50.0%")
        assertEquals(stats.map["Biggest win"], 5.0f)
        assertEquals(stats.map["Best streak"], 5)
        assertEquals(stats.map["Most frequently bet on"], "Barcelona")
    }

    @Test
    fun convertToPercentTest() {
        val stats = Stats
        assertEquals(stats.convertToPercent(0.3f), "30.0%")
        assertEquals(stats.convertToPercent(0.655f), "65.5%")
        assertEquals(stats.convertToPercent(1.0f), "100.0%")
        assertEquals(stats.convertToPercent(0.0f), "0.0%")
        assertEquals(stats.convertToPercent(0.333f), "33.3%")
        assertEquals(stats.convertToPercent(0.25f), "25.0%")
    }

    @Test
    fun gameTest(){
        val g1 = Game("Tue, 10 Jan 2023 20:59:37 GMT", 1.2, 1, 1,
            1, "Barcelona", 0.7, "Real Madryt", 2.1)
        val g2 = Game("Tue, 10 Jan 2023 21:59:37 GMT", 1.2, 2, 2,
            1, "Raków", 0.5, "Wisła", 3.1)

        assertEquals("Barcelona - Real Madryt", g1.toString())
        assertEquals("Raków - Wisła", g2.toString())
    }

    @Test
    fun  competitionTest(){
        val c1 = Competition("Poland", listOf(), 1, "Ekstraklasa")
        val c2 = Competition("Poland", listOf(), 2, "1 Liga")

        assertEquals("Ekstraklasa, 1", c1.toString())
        assertEquals("1 Liga, 2", c2.toString())
    }

}
