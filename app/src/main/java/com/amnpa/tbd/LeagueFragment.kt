package com.amnpa.tbd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LeagueFragment : Fragment() {

    private lateinit var textEnterCode:EditText
    private lateinit var buttonJoin:Button
    private lateinit var buttonCreate:Button
    private lateinit var listLeagues: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_league, container, false)

        textEnterCode = view.findViewById(R.id.editTextEnterCode)
        buttonJoin = view.findViewById(R.id.buttonJoinLeague)
        buttonCreate = view.findViewById(R.id.buttonCreateLeague)
        listLeagues = view.findViewById(R.id.listViewLeagues)

        listLeagues.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, placeholderLeagues())

        buttonJoin.setOnClickListener{
            if (textEnterCode.text.toString() == "")
                Toast.makeText(context, "Invalid league code", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context, "TODO: joined a league of code: "
                        +textEnterCode.text.toString(), Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun placeholderLeagues(): MutableList<League>{
        val leagues = mutableListOf<League>()
        val thisPlayer = Player("User", 100)
        val players = mutableSetOf<Player>()
        for (i in 50 downTo 1){
            players.add(Player("Player $i", i))
        }
        for (i in 5 downTo 1){
            val league = League("League $i", i)
            for(j in 10 downTo 1){
                league.addPlayer(players.random())
            }
            league.addPlayer(thisPlayer)
            leagues.add(league)
        }
        return leagues
    }
}