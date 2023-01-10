package com.amnpa.sbb.model

import android.content.Context
import android.widget.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException

object ParseJSON {

    private val client = OkHttpClient()
    private val gson = Gson()

    private fun getGroups(userId: Int): Array<League>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//groups-by-user/$userId")
            .build()

        var result: Array<League>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<League>::class.java)
        }
        return result
    }

    private fun getCompetitions(): Array<Competition>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//leagues")
            .build()

        var result: Array<Competition>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Competition>::class.java)
        }
        return result
    }

    private fun getGames(): Array<Game>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games")
            .build()

        var result: Array<Game>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Game>::class.java)
        }
        return result
    }

    private fun getGame(gameId: Int): Game? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//game/$gameId")
            .build()

        var result: Game? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Game::class.java)
        }
        return result
    }

    private fun getGamesByCompetition(competitionId: Int): Array<Game>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-league/$competitionId")
            .build()

        var result: Array<Game>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Game>::class.java)
        }
        return result
    }

    private fun getGamesByLeague(leagueId: Int): Array<Game>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-group/$leagueId")
            .build()

        var result: Array<Game>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Game>::class.java)
        }
        return result
    }

    private fun getGamesByUser(userId: Int): Array<Game>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//games-by-user/$userId")
            .build()

        var result: Array<Game>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Game>::class.java)
        }
        return result
    }

    private fun getUsersByLeague(leagueId: Int): Array<Player>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//users-by-group/$leagueId")
            .build()

        var result: Array<Player>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Player>::class.java)
        }
        return result
    }

    private fun getBets(): Array<Bet>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//bets")
            .build()

        var result: Array<Bet>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Bet>::class.java)
        }
        return result
    }

    private fun getBetsByPlayer(userId: Int): Array<Bet>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//bets-by-player/$userId")
            .build()

        var result: Array<Bet>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Bet>::class.java)
        }
        return result
    }

    private fun getBetsByUserAndLeague(userId: Int, leagueId: Int): Array<Bet>? {
        val request = Request.Builder()
            .url("http://10.0.2.2:5000//bets-by-player-and-group/$userId/$leagueId")
            .build()

        var result: Array<Bet>? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            result = gson.fromJson(response.body!!.string(), Array<Bet>::class.java)
        }
        return result
    }

    private fun postLogin(username: String, password: String): Login? {
        val json = """{
            "username":"$username",
            "password":"$password"}
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/login")
            .build()

        var result: Login? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println("lol")
            result = gson.fromJson(response.body!!.string(), Login::class.java)
        }
        return result
    }

    private fun postRegister(username: String, password: String, context: Context?): Register? {
        val json = """{
            "username":"$username",
            "password":"$password"}
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/register")
            .build()

        var result: Register? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println()
            result = gson.fromJson(response.body!!.string(), Register::class.java)
        }
        return result
    }


    private fun createGroup(name: String, user: Int, leagues: ArrayList<Int>, context: Context?): Group? {
        val leagues_str = leagues.joinToString (
            prefix = "[",
            separator = ", ",
            postfix = "]",
            transform = { "$it" }
        )
        println(leagues_str)
        val json = """{
            "name":"$name",
            "user":"$user",
            "leagues":$leagues_str}
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/create-group")
            .build()

        var result: Group? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println()
            result = gson.fromJson(response.body!!.string(), Group::class.java)
        }
        return result
    }

    private fun joinGroup(code: String, user: Int, context: Context?): Group? {
        val json = """{
            "code":"$code",
            "user":"$user"}
        """.trimIndent()

        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/join-group")
            .build()

        var result: Group? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println()
            result = gson.fromJson(response.body!!.string(), Group::class.java)
        }
        return result
    }

    private fun leaveGroup(group: Int, user: Int, context: Context?): Group? {
        val json = """{
            "group":"$group",
            "user":"$user"}
        """.trimIndent()
        
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/leave-group")
            .build()

        var result: Group? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println()
            result = gson.fromJson(response.body!!.string(), Group::class.java)
        }
        return result
    }

    private fun postBet(user: Int, game: Game, option: Int): Bet? {
        val json = """{
            "user":$user,
            "game":${game.gameId/*game.toJSON()*/},
            "option":$option}
        """.trimIndent()

        println(json)
        
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = json.toRequestBody(mediaType)

        val request = Request.Builder()
            .method("POST", requestBody)
            .url("http://10.0.2.2:5000/post-bet")
            .build()

        var result: Bet? = null

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) println("lol")
            result = gson.fromJson(response.body!!.string(), Bet::class.java)
        }
        return result
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGames(startupFun: () -> Unit, cleanupFun: () -> Unit,
                   transferData: (Array<Game>?) -> Unit){
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
            }.await() as Array<Game>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBets(startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Array<Bet>?) -> Unit){
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
            }.await() as Array<Bet>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBetsByUser(userId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Array<Bet>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getBetsByPlayer(userId)
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
            }.await() as Array<Bet>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchCompetitions(startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Array<Competition>?) -> Unit){
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
            }.await() as Array<Competition>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByCompetition(competitionId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                          transferData: (Array<Game>?) -> Unit){
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
            }.await() as Array<Game>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByLeague(leagueId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                           transferData: (Array<Game>?) -> Unit){
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
            }.await() as Array<Game>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGamesByUser(userId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                           transferData: (Array<Game>?) -> Unit){
        println(userId)
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
            }.await() as Array<Game>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBetsByLeagueAndPlayer(userId: Int, leagueId: Int,
                                   //startupFun: () -> Unit, cleanupFun: () -> Unit,
                                    transferData: (Array<Bet>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            //startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getBetsByUserAndLeague(userId, leagueId)
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
            }.await() as Array<Bet>?
            //cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchUsersByLeague(leagueId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                         transferData: (Array<Player>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getUsersByLeague(leagueId)
                    } catch (e:Exception) {
                        when(e){
                            is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                            is java.net.ConnectException,
                            is java.net.SocketTimeoutException ->
                                continue
                            is IOException ->
                            {
                                return@async null
                            }
                            else -> throw e
                        }
                    }
                }
            }.await() as Array<Player>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchLeagues(userId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                     transferData: (Array<League>?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async getGroups(userId)
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
            }.await() as Array<League>?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchGame(gameId: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                  transferData: (Game?) -> Unit){
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
            }.await() as Game?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchAuthData(login: String, password: String, startupFun: () -> Unit, cleanupFun: () -> Unit,
                      transferData: (Login?) -> Unit){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                while (true){
                    try {
                        return@async postLogin(login, password)
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
            }.await() as Login?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchRegistration(login: String, password: String, startupFun: () -> Unit, cleanupFun: () -> Unit,
                          transferData: (Register?) -> Unit, context: Context?
    ){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                try {
                    return@async postRegister(login, password, context)
                } catch (e:Exception) {
                    when(e){
                        is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                        is java.net.ConnectException,
                        is java.net.SocketTimeoutException ->
                            println()
                        else -> throw e
                    }
                }
            }.await() as Register?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchCreateGroup(name: String, user: Int, leagues: ArrayList<Int>, startupFun: () -> Unit, cleanupFun: () -> Unit,
                          transferData: (Group?) -> Unit, context: Context?
    ){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                try {
                    return@async createGroup(name, user, leagues, context)
                } catch (e:Exception) {
                    when(e){
                        is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                        is java.net.ConnectException,
                        is java.net.SocketTimeoutException ->
                            println()
                        else -> throw e
                    }
                }
            }.await() as Group?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchJoinGroup(code: String, user: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                         transferData: (Group?) -> Unit, context: Context?
    ){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                try {
                    return@async joinGroup(code, user, context)
                } catch (e:Exception) {
                    when(e){
                        is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                        is java.net.ConnectException,
                        is java.net.SocketTimeoutException ->
                            println()
                        else -> throw e
                    }
                }
            }.await() as Group?
            cleanupFun()
            transferData(data)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchLeaveGroup(group: Int, user: Int, startupFun: () -> Unit, cleanupFun: () -> Unit,
                       transferData: (Group?) -> Unit, context: Context?
    ){
        GlobalScope.launch(Dispatchers.IO){
            startupFun()
            val data = async {
                try {
                    return@async leaveGroup(group, user, context)
                } catch (e:Exception) {
                    when(e){
                        is java.net.ProtocolException,      // TODO Toasty dla różnych wyjątków
                        is java.net.ConnectException,
                        is java.net.SocketTimeoutException ->
                            println()
                        else -> throw e
                    }
                }
            }.await() as Group?
            cleanupFun()
            transferData(data)
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    fun fetchBetPost(userId: Int, game: Game, option: Int){
        GlobalScope.launch(Dispatchers.IO){
            async {
                while (true){
                    try {
                        return@async postBet(userId, game, option)
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
            }
        }
    }
}



