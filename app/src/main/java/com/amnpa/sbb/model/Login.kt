package com.amnpa.sbb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login (@SerializedName("user_id") val userId:Int,
                  @SerializedName("username") val username:String,
                  @SerializedName("token") val token:String): Parcelable{
    override fun toString(): String {
        return "$userId - $username"
    }
}