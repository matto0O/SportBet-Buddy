package com.amnpa.tbd

class CheckRow(var competition: Competition, var checked: Boolean = false){
    override fun toString(): String {
        return competition.name
    }
}
