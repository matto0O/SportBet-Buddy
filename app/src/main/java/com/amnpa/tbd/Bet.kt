package com.amnpa.tbd

import com.google.gson.annotations.SerializedName

data class Bet (val date: String,               // TODO proper date type
                val game: Game,
                @SerializedName("id") val betId:Int,
                val odds: Double,
                val option: Int,
                @SerializedName("user") val userId:Int){
    override fun toString(): String {
        return super.toString()
    }
}