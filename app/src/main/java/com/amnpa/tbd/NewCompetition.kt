package com.amnpa.tbd

data class NewCompetition (val games: List<NewGame>, val competitionId: Int, val name: String,){
    override fun toString(): String {
        return "$name, $competitionId, ${games[0].fullInfo()}"
    }
}