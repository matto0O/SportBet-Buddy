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

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("request", e.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                result = gson.fromJson(response.body!!.string(), Array<NewLeague>::class.java)
            }
        })
        return result
    }

    fun getCompetitions(): String {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//leagues")
            .build()

        var result = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("request", e.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                result = response.body!!.string()
                Log.v("req", result)
                val a = gson.fromJson(result, Array<NewCompetition>::class.java)
                Log.v("req", a[1].toString())
            }
        })
        return result
    }

    fun getGames(): String {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games")
            .build()

        var result = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("request", e.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                result = response.body!!.string()
                val a = gson.fromJson(result, Array<NewGame>::class.java)
            }
        })
        return result
    }

    fun getGamesByLeague(leagueId: Int): String {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-league/$leagueId")
            .build()

        var result = ""

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("request", e.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                result = response.body!!.string()
                Log.v("req", result)
                val a = gson.fromJson(result, Array<NewGame>::class.java)
            }
        })
        return result
    }

//    fun getBets(): String {
//        val request = Request.Builder()
//            .url("http://10.0.2.2:5000//bets")
//            .build()
//
//        var result: String = ""
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("request", e.toString())
//                e.printStackTrace()
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                result = response.body!!.string()
//                val a = gson.fromJson(result, Array<NewBet>::class.java)
//            }
//        })
//        return result
//    }
}