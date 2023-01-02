package com.amnpa.sbb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Register (@SerializedName("username") val username:String,
                     @SerializedName("created") val created:Boolean): Parcelable{
    override fun toString(): String {
        return username
    }
}