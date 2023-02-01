package com.amnpa.sbb

import org.junit.Test

import org.junit.Assert.*
import com.amnpa.sbb.model.ParseJSON
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiTest {

    @Test
    fun fetchGamesByUser_isLenCorrect() {
        val res = ParseJSON.getGamesByUser(1)
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.isNotEmpty())
        }
    }

    @Test
    fun fetchGamesByUser_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getGamesByUser(9999)
        }
    }

    @Test
    fun fetchStats_isDataProper() {
        val res = ParseJSON.getStatsByPlayer(1)
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.success_rate >= 0)
            assertTrue(res.max_streak >= 0)
            assertTrue(res.no_bets >= 0)
            assertTrue(res.total_points >= 0)
        }
    }

    @Test
    fun fetchStats_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getStatsByPlayer(9999)
        }
    }

    @Test
    fun fetchLeagues_isLenCorrect() {
        val res = ParseJSON.getGroups(1, "UI23asa")
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.isNotEmpty())
        }
    }

    @Test
    fun fetchLeagues_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getGroups(9999, "UI23asa")
        }
    }

    @Test
    fun fetchUserByLeague_isLenCorrect() {
        val res = ParseJSON.getUsersByLeague(6)
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.isNotEmpty())
        }
    }

    @Test
    fun fetchUserByLeague_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getUsersByLeague(9999)
        }
    }

    @Test
    fun fetchGames_isLenCorrect() {
        val res = ParseJSON.getGames()
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.isNotEmpty())
        }
    }

    @Test
    fun fetchComps_isLenCorrect() {
        val res = ParseJSON.getCompetitions()
        assertNotNull(res)
        if (res != null) {
            assertTrue(res.isNotEmpty())
        }
    }


}
