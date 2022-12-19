package com.amnpa.tbd

import java.util.*

data class NewBet (val date: String, val game:NewGame,
                   val betId:Int, val odds:Double, val option:Int, val userId:Int){
    override fun toString(): String {
        return super.toString()
    }
}