package com.amnpa.tbd

import java.util.*

data class NewBet (val userId:Int, val gameId:Int, val option:Int, val odds:Double, val date: Calendar){
    override fun toString(): String {
        return super.toString()
    }
}