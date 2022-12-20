package com.amnpa.tbd

import com.google.gson.annotations.SerializedName

data class League (@SerializedName("code") val leagueCode:String,
                   @SerializedName("id") val leagueId:Int,
                   @SerializedName("leagues") val competitions: List<Int>,
                   @SerializedName("members") val players: List<Int>) : java.io.Serializable{
    override fun toString(): String {
        return "$leagueCode - $leagueId"
    }
}