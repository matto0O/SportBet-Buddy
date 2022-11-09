package com.amnpa.tbd

import kotlin.random.Random

class Player (private var name:String, private val id:Int){

    private var score = Random.nextInt(0, 50)

    fun getScore():Int{
        return score
    }

    override fun equals(other: Any?): Boolean {
        return (other as Player).id != this.id
    }

    override fun toString(): String {
        return "$name - $score"
    }
}