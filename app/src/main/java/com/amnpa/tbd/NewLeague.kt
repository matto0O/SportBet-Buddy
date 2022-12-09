package com.amnpa.tbd

data class NewLeague (val leagueId:Int, val name:String, val code:String,
                      val competitions: List<NewCompetition>){
    override fun toString(): String {
        return "$name - $code"
    }
}