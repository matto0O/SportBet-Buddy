package com.amnpa.tbd

import android.util.Log
import android.widget.*
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

object ParseJSON {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getGroups(): Array<NewLeague>? {
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

    fun getCompetitions(): Array<NewCompetition>? {
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

    fun getGames(): Array<NewGame>? {
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

    fun getGamesByLeague(leagueId: Int): Array<NewGame>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-league/$leagueId")
            .build()

        var result: Array<NewGame>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<NewGame>::class.java)
        }
        return result
    }

    fun getBets(): Array<NewBet>? {
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
}