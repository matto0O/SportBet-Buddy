package com.amnpa.sbb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (@SerializedName("code") val leagueCode:String,
                   @SerializedName("id") val leagueId:Int,
                   @SerializedName("leagues") val competitions: List<Int>,
                   @SerializedName("members") val players: List<Int>,
                   @SerializedName("name") val leagueName: String): Parcelable{
    override fun toString(): String {
        return "$leagueName - $leagueCode"
    }
}