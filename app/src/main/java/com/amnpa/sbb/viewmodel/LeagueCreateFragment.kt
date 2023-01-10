package com.amnpa.sbb.viewmodel

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.fragment.findNavController
import com.amnpa.sbb.R
import com.amnpa.sbb.model.*

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
                val list = arrayListOf<Int>()
                for(i in 0..checkRowAdapter.count-1) {
                    if(checkRowAdapter.getItem(i).checked)
                        list.add(i+1)
                }

                requireActivity().runOnUiThread {
                    val sharedPref = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        ParseJSON.fetchCreateGroup(
                            textLeagueName.text.toString(),
                            sharedPref.getInt("user_id", -1),
                            list,
                            ::triggerLoadingScreen,
                            ::dissolveLoadingScreen,
                            ::handleGroupCreation,
                            context
                        )
                    }
                }

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


    private fun handleGroupCreation(data: Group?){
        requireActivity().runOnUiThread {
            if (data != null && data.name != null) {
                findNavController().navigate(
                    LeagueCreateFragmentDirections
                        .actionLeagueCreateFragmentToLeagueOverviewFragment()
                )
            }
        }
    }


}