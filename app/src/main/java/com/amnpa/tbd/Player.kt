package com.amnpa.tbd

import com.google.gson.annotations.SerializedName

data class Player (@SerializedName("login") val name:String,
                   @SerializedName("id") val playerId:Int,
                   val bets: List<Int>,
                   @SerializedName("memberships") val leagues: List<Int>){

    private fun getScore():Int{
        // TODO implement getting players' score

        return 0
    }

    override fun toString(): String {
        return "$name - ${getScore()}"
    }
}