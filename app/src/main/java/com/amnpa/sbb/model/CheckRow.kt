package com.amnpa.sbb.model

class CheckRow(var competition: Competition, var checked: Boolean = false){
    override fun toString(): String {
        return competition.name
    }
}
