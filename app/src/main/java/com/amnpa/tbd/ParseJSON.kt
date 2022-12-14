package com.amnpa.tbd

import android.widget.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

object ParseJSON {

    private val client = OkHttpClient()
    private val gson = Gson()

    private fun getGroups(): Array<NewLeague>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//groups-by-user/1")
            .build()

        var result: Array<NewLeague>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewLeague>::class.java)
        }
        return result
    }

    private fun getCompetitions(): Array<NewCompetition>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//leagues")
            .build()

        var result: Array<NewCompetition>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewCompetition>::class.java)
        }
        return result
    }

    private fun getGames(): Array<NewGame>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games")
            .build()

        var result: Array<NewGame>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewGame>::class.java)
        }
        return result
    }

    private fun getGame(gameId: Int): NewGame? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//game/$gameId")
            .build()

        var result: NewGame? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), NewGame::class.java)
        }
        return result
    }

    private fun getGamesByCompetition(competitionId: Int): Array<NewGame>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-league/$competitionId")
            .build()

        var result: Array<NewGame>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewGame>::class.java)
        }
        return result
    }

    private fun getGamesByLeague(leagueId: Int): Array<NewGame>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-group/$leagueId")
            .build()

        var result: Array<NewGame>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewGame>::class.java)
        }
        return result
    }

    private fun getGamesByUser(userId: Int): Array<NewGame>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-group/$userId")
            .build()

        var result: Array<NewGame>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewGame>::class.java)
        }
        return result
    }

    private fun getBets(): Array<NewBet>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//bets")
            .build()

        var result: Array<NewBet>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewBet>::class.java)
        }
        return result
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGames(startupFun: () -> Unit, cleanupFun: () -> Unit,
                   transferData: (Array<NewGame>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGames()
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewGame>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBets(startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Array<NewBet>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getBets()
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewBet>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchCompetitions(startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Array<NewCompetition>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getCompetitions()
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewCompetition>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByCompetition(competitionId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                          transferData: (Array<NewGame>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGamesByCompetition(competitionId)
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewGame>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByLeague(leagueId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                           transferData: (Array<NewGame>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGamesByLeague(leagueId)
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewGame>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByUser(userId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                           transferData: (Array<NewGame>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGamesByUser(userId)
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewGame>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchLeagues(startupFun: () -> Unit, cleanupFun: () -> Unit,
                     transferData: (Array<NewLeague>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGroups()
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<NewLeague>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGame(gameId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (NewGame?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGame(gameId)
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            else -> throw e
                        }
                    }
                }
            }.await() as NewGame?
            cleanupFun()
            transferData(data)
        }
    }
}