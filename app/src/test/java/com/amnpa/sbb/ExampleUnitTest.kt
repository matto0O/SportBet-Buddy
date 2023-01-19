package com.amnpa.sbb

import com.amnpa.sbb.model.Group
import org.junit.Test

import org.junit.Assert.*
import com.amnpa.sbb.model.ParseJSON
import java.io.IOException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {

    fun <T> handleResponse(data: Array<T>?) {
        if (data != null) {
            println(data)
            assertTrue(data.isNotEmpty())
        }
    }

    fun handleGroup(data: Group?, id: Int, name: String) {
        if (data != null) {
            assertEquals(data.id, id)
            assertEquals(data.name, name)
        }
    }

    private fun triggerLoadingScreen(){}

    private fun dissolveLoadingScreen(){}

    @Test
    fun fetchGamesByUser_isLenCorrect() {
        ParseJSON.fetchGamesByUser(1, ::triggerLoadingScreen,
            ::dissolveLoadingScreen, ::handleResponse)
    }

    @Test
    fun fetchGamesByUser_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getGamesByUser(9999)
        }
    }

    @Test
    fun fetchLeagues_isLenCorrect() {
        ParseJSON.fetchLeagues(1, "UI23asa", ::triggerLoadingScreen,
            ::dissolveLoadingScreen, ::handleResponse)
    }

    @Test
    fun fetchLeagues_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getGroups(9999, "UI23asa")
        }
    }

    @Test
    fun fetchUserByLeague_isLenCorrect() {
        ParseJSON.fetchUsersByLeague(1, ::triggerLoadingScreen,
            ::dissolveLoadingScreen, ::handleResponse)
    }

    @Test
    fun fetchUserByLeague_shouldThrow() {
        assertThrows("", IOException::class.java) {
            ParseJSON.getUsersByLeague(9999)
        }
    }


    @Test
    fun fetchCreateGroup_isDataCorrect() {
        ParseJSON.fetchCreateGroup("new", 1, arrayListOf(1), ::triggerLoadingScreen,
            ::dissolveLoadingScreen
        ) { data -> handleGroup(data, 1, "new") }
    }
}