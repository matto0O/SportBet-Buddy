package com.amnpa.sbb.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Player (@SerializedName("login") val name:String,
                   @SerializedName("id") val playerId:Int,
                   val bets: List<Int>,
                   @SerializedName("memberships") val leagues: List<Int>){

    private val queue: Queue<Double> = LinkedList()

    private fun getScore(leagueId: Int): Double{
        ParseJSON.fetchBetsByLeagueAndPlayer(playerId, leagueId, ::dataTransfer)
        return queue.poll()!!
    }

    private fun dataTransfer(bets: Array<Bet>?){
        var score = 0.0
        try {
            bets!!.forEach { bet ->
                if(bet.option == bet.game.result)
                    score += bet.odds
            }
        } catch (_: java.lang.NullPointerException){}
        queue.add(score)
    }

    override fun toString(): String {
        return name
    }

    fun toString(leagueId: Int): String{
        return "$name - ${getScore(leagueId)}"
    }
}