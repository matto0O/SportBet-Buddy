package com.amnpa.tbd

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class GamesFragment : Fragment() {
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGames: Button
    private lateinit var title: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)
        val placeholderResults = listOf(
            Game("Real Madryd", "FC Barcelona", false, false),
            Game("Man City", "Liverpool", true, false),
            Game("Wisła Płock", "Raków Częstochowa", true, false),
            Game("Bayern Monachium", "FC Barcelona", false, false)
        )

        val placeholderUpcoming = listOf(
            Game("Ślask Wrocław", "Legia", false, true),
        )
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

        resultAdapter = ResultAdapter(mutableListOf(), showPopup)
        resultAdapter.reloadData(placeholderResults)

        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        buttonGames.setOnClickListener {
            when(title.text){
                getString(R.string.results_games) -> title.text = getString(R.string.upcoming_games)
                getString(R.string.upcoming_games) -> title.text = getString(R.string.results_games)
            }

            when(buttonGames.text) {
                getString(R.string.results_games) -> {
                    buttonGames.text = getString(R.string.upcoming_games)
                    resultAdapter.reloadData(placeholderResults)
                }
                getString(R.string.upcoming_games) -> {
                    buttonGames.text = getString(R.string.results_games)
                    resultAdapter.reloadData(placeholderUpcoming)
                }
            }
        }
        return view
    }



}