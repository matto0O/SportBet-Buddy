package com.amnpa.sbb.model

import com.google.gson.annotations.SerializedName

data class Competition (val country: String,        // TODO make use of country in terms of UI
                        val games: List<Game>,
                        @SerializedName("id") val competitionId: Int,
                        val name: String,){
    override fun toString(): String {
        return "$name, $competitionId"
    }
}