package com.amnpa.sbb.model

import com.google.gson.annotations.SerializedName

data class Game (
    val date: String,//Calendar,    TODO proper date type
    @SerializedName("draw_odds") val drawOdds: Double,
    @SerializedName("id") val gameId: Int,
    @SerializedName("league") val competitionId: Int,
    val result:Int,
    val team1:String,
    @SerializedName("team1_odds") val team1Odds: Double,
    val team2:String,
    @SerializedName("team2_odds") val team2Odds: Double
    )

{
    override fun toString(): String {
        return "$team1 - $team2"
    }
}