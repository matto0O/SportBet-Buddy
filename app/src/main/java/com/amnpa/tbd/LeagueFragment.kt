package com.amnpa.tbd

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView

class LeagueFragment : Fragment() {

    private lateinit var textEnterCode:EditText
    private lateinit var buttonJoin:Button
    private lateinit var buttonCreate:Button
    private lateinit var listLeagues: ListView
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView
    private lateinit var checkRowAdapter: CheckRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_league, container, false)

        textEnterCode = view.findViewById(R.id.editTextEnterCode)
        buttonJoin = view.findViewById(R.id.buttonJoinLeague)
        buttonCreate = view.findViewById(R.id.buttonCreateLeague)
        listLeagues = view.findViewById(R.id.listViewLeagues)

        listLeagues.setOnItemClickListener { _, _, _, row ->
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

            val selected = listLeagues.adapter.getItem(row.toInt()) as NewLeague
            textLeagueCode.text = selected.code
            textLeagueName.text = selected.code // TODO add name
           // Log.v("aeee", selected)
            listPlayers.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
                emptyList<Int>())

            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        }

        buttonCreate.setOnClickListener {
            val popup = layoutInflater.inflate(R.layout.league_creator_popup, null)
            val popupWindow = PopupWindow(popup, (view.width * 0.75).toInt(),
                    (view.height * 0.75).toInt(), true)

            val textLeagueName = popup.findViewById<EditText>(R.id.editTextLeagueName)
            val buttonCommit = popup.findViewById<Button>(R.id.buttonCommitLeagueCreation)
            val listEvents = popup.findViewById<ListView>(R.id.listViewChooseEvents)

            listEvents.adapter = checkRowAdapter

            listEvents.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                val checkRow: CheckRow = checkRowAdapter.getItem(position)
                checkRow.checked = !checkRow.checked
                checkRowAdapter.notifyDataSetChanged()
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

    override fun onStart() {
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
        ParseJSON.fetchLeagues(
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importLeagues)
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

    private fun importLeagues(data: Array<NewLeague>?){
            requireActivity().runOnUiThread {
                listLeagues.adapter = try {
                    ArrayAdapter(requireContext(),
                        android.R.layout.simple_list_item_1, data!!.asList())
                } catch (e: java.lang.NullPointerException) {
                    ArrayAdapter(requireContext(),
                        android.R.layout.simple_list_item_1, emptyList<NewLeague>())
                }
            }
    }

    private fun importCompetitions(data: Array<NewCompetition>?){
        requireActivity().runOnUiThread {
            checkRowAdapter = try {
                CheckRowAdapter(requireContext(), data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                CheckRowAdapter(requireContext(), emptyList())
            }
        }
    }
}