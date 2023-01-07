package com.amnpa.sbb.viewmodel

import android.content.Context.MODE_PRIVATE
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import com.amnpa.sbb.model.*


class GamesFragment : Fragment() {
    private lateinit var betAdapter: BetAdapter
    private lateinit var gameAdapter: GameAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGames: Button
    private lateinit var title: TextView
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView
    private lateinit var competitions: HashMap<Int,Competition>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        recyclerView = view.findViewById(R.id.gamesRecyclerView)
        title = view.findViewById(R.id.textStatDescription)
        buttonGames = view.findViewById(R.id.gamesButton)

        competitions = HashMap()
        betAdapter = BetAdapter(mutableListOf())
        gameAdapter = GameAdapter(mutableListOf(), competitions)

        recyclerView.adapter = betAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        buttonGames.setOnClickListener {

            when(title.text){
                getString(R.string.results_games) -> {
                    ParseJSON.fetchGames(
                        ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importGames)
                    title.text = getString(R.string.upcoming_games)
                    buttonGames.text = getString(R.string.results_games)
                    recyclerView.adapter = gameAdapter
                }
                getString(R.string.upcoming_games) -> {
                    ParseJSON.fetchBets(
                        ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importBets)
                    title.text = getString(R.string.results_games)
                    buttonGames.text = getString(R.string.upcoming_games)
                    recyclerView.adapter = betAdapter
                }
            }
        }
        return view
    }

    override fun onStart() {
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
        val id = requireActivity().getSharedPreferences("prefs", MODE_PRIVATE)
            .getInt("user_id", -1)
        ParseJSON.fetchGamesByUser(id,
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importGames)
        ParseJSON.fetchBets(//id,
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importBets)
        ParseJSON.fetchCompetitions(
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importCompetitions)
        super.onStart()
    }

    override fun onStop() {
        (loading.drawable as AnimationDrawable).stop()
        fragmentContainerView.alpha = 1F
        loading.alpha=0F
        super.onStop()
    }

    private fun triggerLoadingScreen(){
        fragmentContainerView.alpha = 0.2F
        loading.alpha=1F
        (loading.drawable as AnimationDrawable).start()
    }

    private fun dissolveLoadingScreen(){
        (loading.drawable as AnimationDrawable).stop()
        fragmentContainerView.alpha = 1F
        loading.alpha=0F
    }

    private fun importGames(data: Array<Game>?){
        requireActivity().runOnUiThread {
            try {
                gameAdapter.reloadData(data!!.asList(), competitions)
            } catch (e: java.lang.NullPointerException) {
                gameAdapter.reloadData(emptyList(), competitions)
            }
        }
    }

    private fun importBets(data: Array<Bet>?){
        requireActivity().runOnUiThread {
            try {
                betAdapter.reloadData(data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                betAdapter.reloadData(emptyList())
            }
        }
    }

    private fun importCompetitions(data: Array<Competition>?){
        requireActivity().runOnUiThread {
            try {
                data!!.forEach { elem -> competitions += Pair(elem.competitionId, elem) }
            } catch (_: java.lang.NullPointerException) { }
        }
    }
}