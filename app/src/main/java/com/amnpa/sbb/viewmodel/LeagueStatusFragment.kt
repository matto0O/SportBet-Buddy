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
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import com.amnpa.sbb.R
import com.amnpa.sbb.model.ParseJSON
import com.amnpa.sbb.model.Player

class LeagueStatusFragment : Fragment() {

    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView
    private lateinit var listPlayers: ListView
    private val args by navArgs<LeagueStatusFragmentArgs>()
    private lateinit var _fragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _fragmentManager = requireActivity().supportFragmentManager
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)

        val view = inflater.inflate(R.layout.fragment_league_status, container, false)

        val textLeagueCode = view.findViewById<TextView>(R.id.textViewLeagueCode)
        val textLeagueName = view.findViewById<TextView>(R.id.textViewLeagueName)
        val buttonQuit = view.findViewById<Button>(R.id.buttonQuitLeague)
        listPlayers = view.findViewById(R.id.listViewPlayers)

        buttonQuit.setOnClickListener {
            Toast.makeText(context, "Left ${textLeagueName.text}", Toast.LENGTH_SHORT)
                .show()
            println(activity?.getPreferences(Context.MODE_PRIVATE)!!.getInt("user_id", -1))
            // TODO real implementation of leaving the league
        }

        val selected = args.league
        textLeagueCode.text = selected.leagueCode
        textLeagueName.text = selected.leagueCode // TODO add name
        ParseJSON.fetchUsersByLeague(
            selected.leagueId,
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importStandings)

        // TODO browse players' bets onclick

        return view
    }

//    override fun onStart() {
//        super.onStart()
//        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
//        loading = requireActivity().findViewById(R.id.loadingScreen)
//    }
//
//    override fun onStop() {
//        (loading.drawable as AnimationDrawable).stop()
//        fragmentContainerView.alpha = 1F
//        loading.alpha=0F
//        super.onStop()
//    }

    private fun triggerLoadingScreen(){
//        fragmentContainerView.alpha = 0.2F
//        loading.alpha=1F
//        (loading.drawable as AnimationDrawable).start()
    }

    private fun dissolveLoadingScreen(){
//        (loading.drawable as AnimationDrawable).stop()
//        fragmentContainerView.alpha = 1F
//        loading.alpha=0F
    }

    private fun importStandings(data: Array<Player>?){
        requireActivity().runOnUiThread {
            listPlayers.adapter = try {
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1,
                    data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                ArrayAdapter(requireContext(),
                    android.R.layout.simple_list_item_1, emptyList<Player>())
            }
        }
    }

}