package com.amnpa.tbd

import kotlin.random.Random

class Player (name:String, id:Int){
    fun getScore():Int{
        return Random.nextInt(0, 50)
    }

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}