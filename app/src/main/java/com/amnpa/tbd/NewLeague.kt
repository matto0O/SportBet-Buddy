package com.amnpa.tbd

data class NewLeague (val code:String, /*val name:String,*/ val leagueId:Int,
                      val competitions: List<NewCompetition>, val players: List<Int>){
    override fun toString(): String {
        return "$code - $leagueId"
    }
}