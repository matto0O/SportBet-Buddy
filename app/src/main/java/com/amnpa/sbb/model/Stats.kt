package com.amnpa.sbb.model

import kotlin.random.Random

object Stats {
    private val map = HashMap<String, Any>()

    init {
        map["Total points"] = 0.0
        map["Total events bet on"] = 0
        map["Success rate"] = 0.0
        map["Biggest win"] = 0.0
        map["Best streak"] = 0
        map["Most frequently bet on"] = "-"
        map["Favourite sport"] = "Football"
        map["Bet on different disciplines"] = 0
    }

    fun fetchStats(userId: Int){
        ParseJSON.fetchStats(
            userId,
            ::triggerLoadingScreen,
            ::dissolveLoadingScreen,
            ::handleStats)
    }

    private fun triggerLoadingScreen(){
//        fragmentContainerView.alpha = 0.2F
//        loading.alpha=1F
//        (loading.drawable as AnimationDrawable).start()
    }

    private fun dissolveLoadingScreen(){
//        (loading.drawable as AnimationDrawable).stop()
//        fragmentContainerView.alpha = 1F
//        loading.alpha=0F
    }

    private fun handleStats(data: StatsData?){
        if(data != null){
            map["Total points"] = data.total_points
            map["Total events bet on"] = data.no_bets
            map["Success rate"] = data.success_rate
            map["Biggest win"] = data.max_win
            map["Best streak"] = data.max_streak
            map["Most frequently bet on"] = data.most_freq_team
        }
    }

    fun randomStats(amount: Int = map.size): List<Pair<String, Any>>{
        val list = map.toList()
        while(list.size > amount){
            list.drop(Random.nextInt(list.size-1))
        }
        return list
    }
}