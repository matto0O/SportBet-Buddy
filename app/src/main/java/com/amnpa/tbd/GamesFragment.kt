package com.amnpa.tbd

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

        val builder = AlertDialog.Builder(context)
        val popup: View = layoutInflater.inflate(R.layout.game_popup, null)
        val showPopup: (String, String) -> Unit = { t1: String, t2: String ->
            popup.findViewById<TextView>(R.id.selectTeam1).text = t1
            popup.findViewById<TextView>(R.id.selectTeam2).text = t2
            builder.setView(popup)
            builder.create().show()
        }

        resultAdapter = ResultAdapter(mutableListOf(), showPopup)
        resultAdapter.reloadData(placeholderResults)

        recyclerView.adapter = resultAdapter
        recyclerView.layoutManager = LinearLayoutManager(getContext())

        buttonGames.setOnClickListener {
            when(title.text){
                "Results" -> title.text = "Upcoming"
                "Upcoming" -> title.text = "Results"
            }

            when(buttonGames.text) {
                "Results" -> {
                    buttonGames.text = "Upcoming"
                    resultAdapter.reloadData(placeholderResults)
                }
                "Upcoming" -> {
                    buttonGames.text = "Results"
                    resultAdapter.reloadData(placeholderUpcoming)
                }
            }
        }
        return view
    }



}