package com.amnpa.tbd

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*


class GamesFragment : Fragment() {
    private lateinit var resultAdapter: ResultAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonGames: Button
    private lateinit var title: TextView
    private lateinit var fragmentContainerView: FragmentContainerView
    private lateinit var loading: ImageView

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
            GlobalScope.launch(Dispatchers.IO) {
                triggerLoadingScreen()
                val v = async {
                    while (true){
                        try {
                            return@async ParseJSON.getGroups()!!
                        } catch (e:Exception) {
                            when(e){
                                is java.net.ProtocolException,
                                is java.net.ConnectException ->
                                    continue
                                else -> throw e
                            }
                        }
                    }
                } as Deferred<Array<NewLeague>?>
                Log.v("games",  v.await()!![0].toString())
                dissolveLoadingScreen()
            }
        }
        return view
    }


    override fun onResume() {
        fragmentContainerView = requireActivity().findViewById(R.id.fragmentContainerView)
        loading = requireActivity().findViewById(R.id.loadingScreen)
        super.onResume()
    }

    override fun onPause() {
        (loading.drawable as AnimationDrawable).stop()
        fragmentContainerView.alpha = 1F
        loading.alpha=0F
        super.onPause()
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
}