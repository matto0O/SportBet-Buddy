package com.amnpa.tbd

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView

class LeagueCreateFragment : Fragment() {
    private lateinit var checkRowAdapter: CheckRowAdapter
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView
    private lateinit var listEvents: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_league_create, container, false)

        val textLeagueName = view.findViewById<EditText>(R.id.editTextLeagueName)
        val buttonCommit = view.findViewById<Button>(R.id.buttonCommitLeagueCreation)
        listEvents = view.findViewById(R.id.listViewChooseEvents)

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
            }
        }
        return view
    }

    override fun onStart() {
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
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

    private fun importCompetitions(data: Array<Competition>?){
        requireActivity().runOnUiThread {
            checkRowAdapter = try {
                CheckRowAdapter(requireContext(), data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                CheckRowAdapter(requireContext(), emptyList())
            }
            listEvents.adapter = checkRowAdapter
        }
    }

}