package com.amnpa.sbb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Group (@SerializedName("name") val name:String,
                  @SerializedName("code") val code:String,
                  @SerializedName("id") val id:Int): Parcelable{
    override fun toString(): String {
        return "$id - $name"
    }
}