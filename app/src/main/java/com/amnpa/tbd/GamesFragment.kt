package com.amnpa.tbd

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GamesFragment : Fragment() {
    private lateinit var betAdapter: BetAdapter
    private lateinit var gameAdapter: GameAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGames: Button
    private lateinit var title: TextView
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)

        recyclerView = view.findViewById(R.id.gamesRecyclerView)
        title = view.findViewById(R.id.gamesText)
        buttonGames = view.findViewById(R.id.gamesButton)

        val showPopup: (String, String) -> Unit = { t1: String, t2: String ->
            val popup: View = layoutInflater.inflate(R.layout.game_popup, null)
            val popupWindow = PopupWindow(popup, (view.width * 0.75).toInt(),
                (view.height * 0.75).toInt(), true)

            val team1 = popup.findViewById<TextView>(R.id.selectTeam1)
            team1.text = t1
            team1.setOnClickListener { popupWindow.dismiss() }
            val team2 = popup.findViewById<TextView>(R.id.selectTeam2)
            team2.text = t2
            team2.setOnClickListener { popupWindow.dismiss() }
            popup.findViewById<TextView>(R.id.selectDraw)
                .setOnClickListener { popupWindow.dismiss() }

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        }

        betAdapter = BetAdapter(mutableListOf(), showPopup)
        gameAdapter = GameAdapter(mutableListOf(), showPopup)

        recyclerView.adapter = betAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        buttonGames.setOnClickListener {

            when(title.text){
                getString(R.string.results_games) -> {
                    title.text = getString(R.string.upcoming_games)
                    buttonGames.text = getString(R.string.results_games)
                    recyclerView.adapter = gameAdapter
                    ParseJSON.fetchGames(
                        ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importGames)
                }
                getString(R.string.upcoming_games) -> {
                    title.text = getString(R.string.results_games)
                    buttonGames.text = getString(R.string.upcoming_games)
                    recyclerView.adapter = betAdapter
                    ParseJSON.fetchBets(
                        ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importBets)
                }
            }
        }
        return view
    }

    override fun onStart() {
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
        ParseJSON.fetchGames(
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importGames)
        ParseJSON.fetchBets(
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importBets)
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

    private fun importGames(data: Array<NewGame>?){
        requireActivity().runOnUiThread {
            try {
                gameAdapter.reloadData(data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                gameAdapter.reloadData(emptyList())
            }
        }
    }

    private fun importBets(data: Array<NewBet>?){
        requireActivity().runOnUiThread {
            try {
                betAdapter.reloadData(data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                betAdapter.reloadData(emptyList())
            }
        }
    }
}