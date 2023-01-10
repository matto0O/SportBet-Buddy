package com.amnpa.sbb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StatsData (@SerializedName("total_points") val total_points:Float,
                      @SerializedName("no_bets") val no_bets:Int,
                      @SerializedName("max_win") val max_win:Float,
                      @SerializedName("max_streak") val max_streak:Int,
                      @SerializedName("most_freq_team") val most_freq_team:String,
                      @SerializedName("success_rate") val success_rate:Float): Parcelable{
    override fun toString(): String {
        return most_freq_team
    }
}