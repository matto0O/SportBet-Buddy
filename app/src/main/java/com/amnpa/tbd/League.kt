package com.amnpa.tbd

class League(private var name: String, private var code: Int) {
    private val participants = mutableSetOf<Player>()

    fun addPlayer(player: Player){
        participants.add(player)
    }

    fun getSize(): Int{
        return participants.size
    }

    fun orderedPlayers(): List<Player>{
        return participants.toList().sortedBy { -it.getScore() }
    }

    fun getName(): String{
        return name
    }

    fun getCode(): Int{
        return code
    }

    override fun toString(): String {
        return name
    }
}