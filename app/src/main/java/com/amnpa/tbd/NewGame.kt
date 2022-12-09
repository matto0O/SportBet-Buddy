package com.amnpa.tbd

import java.util.*

data class NewGame (
    val date: String,//Calendar,
    val drawOdds:Double,
    val gameId:Int,
    val result:Int,
    val team1:String,
    val team1Odds:Double,
    val team2:String,
    val team2Odds:Double
    )

{
    override fun toString(): String {
        return "$team1 - $team2"
    }

    fun fullInfo(): String{
        return "$team1, $team2, $date"
    }
}