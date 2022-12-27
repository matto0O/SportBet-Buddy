package com.amnpa.sbb.viewmodel

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
import com.amnpa.sbb.model.League
import com.amnpa.sbb.model.ParseJSON

class LeagueOverviewFragment : Fragment() {

    private lateinit var textEnterCode: EditText
    private lateinit var buttonJoin: Button
    private lateinit var buttonCreate: Button
    private lateinit var listLeagues: ListView
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_league_overview, container, false)

        textEnterCode = view.findViewById(R.id.editTextEnterCode)
        buttonJoin = view.findViewById(R.id.buttonJoinLeague)
        buttonCreate = view.findViewById(R.id.buttonCreateLeague)
        listLeagues = view.findViewById(R.id.listViewLeagues)

        listLeagues.setOnItemClickListener { _, _, position, _ ->
            val action = LeagueOverviewFragmentDirections
                .actionLeagueOverviewFragmentToLeagueStatusFragment(
                listLeagues.adapter.getItem(position) as League
                )
            findNavController().navigate(action)
        }

        buttonCreate.setOnClickListener {
            findNavController().navigate(
                LeagueOverviewFragmentDirections
                .actionLeagueOverviewFragmentToLeagueCreateFragment()
            )
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

    private fun importLeagues(data: Array<League>?){
        requireActivity().runOnUiThread {
            listLeagues.adapter = try {
                ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, emptyList<League>())
            }
        }
    }
}