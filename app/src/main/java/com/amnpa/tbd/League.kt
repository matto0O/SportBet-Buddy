package com.amnpa.tbd

class League(var name: String, var code: Int) {
    private val participants = mutableSetOf<Player>()

    fun addPlayer(player: Player){
        participants.add(player)
    }

    fun getSize(): Int{
        return participants.size
    }

    fun orderedPlayers(): List<Player>{
        return participants.toList().sortedBy { it.getScore() }
    }

    override fun toString(): String {
        return name
    }
}