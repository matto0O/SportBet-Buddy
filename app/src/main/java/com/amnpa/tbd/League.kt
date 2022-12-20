package com.amnpa.tbd

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League (@SerializedName("code") val leagueCode:String,
                   @SerializedName("id") val leagueId:Int,
                   @SerializedName("leagues") val competitions: List<Int>,
                   @SerializedName("members") val players: List<Int>): Parcelable{
    override fun toString(): String {
        return "$leagueCode - $leagueId"
    }
}