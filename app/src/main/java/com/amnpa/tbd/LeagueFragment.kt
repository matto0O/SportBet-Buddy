package com.amnpa.tbd

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

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

        listLeagues.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
            placeholderLeagues())

        listLeagues.setOnItemClickListener { adapterView, v, position, row ->
            val popup = layoutInflater.inflate(R.layout.league_status_popup, null)
            val popupWindow = PopupWindow(popup, (view.width * 0.75).toInt(),
                (view.height * 0.75).toInt(), true)

            val textLeagueCode = popup.findViewById<TextView>(R.id.textViewLeagueCode)
            val textLeagueName = popup.findViewById<TextView>(R.id.textViewLeagueName)
            val buttonQuit = popup.findViewById<Button>(R.id.buttonQuitLeague)
            val listPlayers = popup.findViewById<ListView>(R.id.listViewPlayers)

            buttonQuit.setOnClickListener {
                Toast.makeText(context, "Left ${textLeagueName.text}", Toast.LENGTH_SHORT)
                    .show()
                popupWindow.dismiss()
            }

            val selected = listLeagues.adapter.getItem(row.toInt()) as League
            textLeagueCode.text = selected.getCode().toString()
            textLeagueName.text = selected.getName()
            listPlayers.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
                selected.orderedPlayers())

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        buttonCreate.setOnClickListener {
            val popup = layoutInflater.inflate(R.layout.league_creator_popup, null)
            val popupWindow = PopupWindow(popup, (view.width * 0.75).toInt(),
                    (view.height * 0.75).toInt(), true)

            val textLeagueName = popup.findViewById<EditText>(R.id.editTextLeagueName)
            val buttonCommit = popup.findViewById<Button>(R.id.buttonCommitLeagueCreation)
            val listEvents = popup.findViewById<ListView>(R.id.listViewChooseEvents)

            val checkRowList = mutableListOf(CheckRow("Ekstraklasa"),
                CheckRow("Liga Mistrzow"),
                CheckRow("Mistrzostwa Swiata"),
                CheckRow("NBA"))

            val adapter = CheckRowAdapter(checkRowList, requireContext())
            listEvents.adapter = adapter

            listEvents.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val checkRow: CheckRow = checkRowList[position]
                checkRow.checked = !checkRow.checked
                adapter.notifyDataSetChanged()
            }

            buttonCommit.setOnClickListener {
                if (textLeagueName.text.toString().length < 3)
                    Toast.makeText(context, "Invalid league name", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(context, "Created ${textLeagueName.text}",
                            Toast.LENGTH_SHORT).show()
                    popupWindow.dismiss()
                }
            }

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

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