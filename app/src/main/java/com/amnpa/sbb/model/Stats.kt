package com.amnpa.sbb.model

import kotlin.random.Random

object Stats {
    private val map = HashMap<String, Any>()

    init {
        map["Total points"] = 0
        map["Total events bet on"] = 0
        map["Success rate"] = 0.0
        map["Biggest win"] = 0.0
        map["Best streak"] = 0
        map["Most frequently bet on"] = ""
        map["Favourite sport"] = "Football"
        map["Bet on different disciplines"] = 0
    }

    fun fetchStats(){
        // TODO fetch stats
    }

    fun randomStats(amount: Int): List<Pair<String, Any>>{
        val list = map.toList()
        while(list.size > amount){
            list.drop(Random.nextInt(list.size-1))
        }
        return list
    }
}