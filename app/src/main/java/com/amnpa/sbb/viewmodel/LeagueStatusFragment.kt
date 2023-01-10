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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnpa.sbb.R
import com.amnpa.sbb.model.Group
import com.amnpa.sbb.model.ParseJSON
import com.amnpa.sbb.model.Player
import com.amnpa.sbb.model.PlayerAdapter

class LeagueStatusFragment : Fragment() {
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView
    private lateinit var listPlayers: RecyclerView
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
        listPlayers = view.findViewById(R.id.recyclerPlayers)
        playerAdapter = PlayerAdapter(mutableListOf())

        listPlayers.adapter = playerAdapter
        listPlayers.layoutManager = LinearLayoutManager(context)

        buttonQuit.setOnClickListener {
            Toast.makeText(context, "Left ${textLeagueName.text}", Toast.LENGTH_SHORT)
                .show()
            requireActivity().runOnUiThread {
                val sharedPref = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
                if (sharedPref != null) {
                    ParseJSON.fetchLeaveGroup(
                        args.league.leagueId,
                        sharedPref.getInt("user_id", -1),
                        ::triggerLoadingScreen,
                        ::dissolveLoadingScreen,
                        ::handleGroupLeave,
                        context
                    )
                }
            }
        }

        val selected = args.league
        textLeagueCode.text = selected.leagueCode
        textLeagueName.text = selected.leagueId.toString() // TODO add name
        ParseJSON.fetchUsersByLeague(
            selected.leagueId,
            ::triggerLoadingScreen, ::dissolveLoadingScreen, ::importStandings)

        // TODO browse players' bets onclick

        return view
    }

    override fun onStart() {
        super.onStart()
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
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

    private fun importStandings(data: Array<Player>?){
        requireActivity().runOnUiThread {
            try {
                playerAdapter.reloadData(data!!.asList())
            } catch (e: java.lang.NullPointerException) {
                playerAdapter.reloadData(emptyList())
            }
        }
    }

    private fun handleGroupLeave(data: Group?){
        requireActivity().runOnUiThread {
            if (data != null && data.name != null) {
                findNavController().navigate(
                    LeagueStatusFragmentDirections
                        .actionLeagueStatusFragmentToLeagueOverviewFragment()
                )
            }
        }
    }

}